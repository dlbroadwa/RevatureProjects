package auction.web;

import auction.dataaccess.UserDAO;
import auction.json.AuctionJSONConverter;
import auction.json.AuctionJSONWrapper;
import auction.json.AuctionListJSONWrapper;
import auction.models.Auction;
import auction.models.Item;
import auction.models.User;
import auction.services.AuctionJSONService;
import auction.services.AuctionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main REST API for the Auction service, supporting the full range of CRUD operations for auction information.
 */
@WebServlet(urlPatterns = "/auctions/*")
public class AuctionServlet extends HttpServlet {
    private AuctionService service;
    private AuctionJSONService jsonService;
    private AuctionJSONConverter jsonConverter;
    private UserDAO userDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext context = config.getServletContext();
        service = (AuctionService)context.getAttribute("auctionService");
        jsonService = (AuctionJSONService)context.getAttribute("jsonService");
        jsonConverter = (AuctionJSONConverter)context.getAttribute("jsonConverter");
        userDao = (UserDAO)context.getAttribute("userDao");
    }

    /**
     * Gets the currently logged-in user
     * @param cookies the cookies sent by the current request
     * @return the current logged-in <code>User</code>, or <code>null</code> if no user is logged in.
     */
    private User getUser(Cookie[] cookies) {
        if (cookies == null)
            return null;
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("userName")) {
                return userDao.findByUserName(cookie.getValue());
            }
        }
        return null;
    }

    /**
     * Gets the entire request body in string form.
     * @param req the current request
     * @return a <code>String</code> with the contents of the request body
     * @throws IOException
     */
    private String getRequestBody(HttpServletRequest req) throws IOException {
        return req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * Parses a URL path of the form <code>/{id}</code> (optionally ending with a slash) and returns that ID as an integer.
     * @param url the path to parse
     * @return the ID contained in that path
     */
    private static int getID(String url) {
        String[] urlParts = url.split("/");
        if (urlParts.length != 2)
            return -HttpServletResponse.SC_BAD_REQUEST;

        int id = -1;
        try {
            id = Integer.parseInt(urlParts[1]);
        }
        catch (NumberFormatException ex) {
            return -HttpServletResponse.SC_NOT_FOUND;
        }
        return id;
    }

    /**
     * Performs the search functionality of the API. Recognized parameters are
     * query=(item name query) and
     * seller=(seller ID).
     * @param req the current request
     * @param resp the response object
     * @param seller the ID of the current logged in user, used to hide reserve prices from all other sellers.
     * @throws IOException
     */
    private void searchAuctions(HttpServletRequest req, HttpServletResponse resp, int seller) throws IOException {
        String nameQuery = req.getParameter("query");
        String sellerQueryStr = req.getParameter("seller");
        int parsed = 0;
        if (sellerQueryStr != null) {
            try {
                parsed = Integer.parseInt(sellerQueryStr);
            }
            catch (NumberFormatException ex) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
        }
        // Weird workaround to stop Java/IntelliJ from complaining about uninitialized variables or captured lambda variables
        // not being "final or effectively final"
        final int sellerID = parsed;

        // Do the search
        List<Auction> searchResults;
        if (nameQuery == null && sellerQueryStr == null)
            searchResults = service.getAllAuctions();
        else if (nameQuery == null)
            searchResults = service.findBySellerID(sellerID);
        else {
            searchResults = service.findByItemName(nameQuery);
            if (sellerQueryStr != null) {
                searchResults = searchResults.stream().filter(a -> a.getSellerID() == sellerID).collect(Collectors.toList());
            }
        }

        // Convert to JSON
        AuctionListJSONWrapper wrappedResults = jsonService.getAuctionJSONObjects(searchResults, seller);
        String json = jsonConverter.serialize(wrappedResults);
        resp.getWriter().write(json);
    }

    /**
     * Implements the GET functionality for retrieving data about an auction.
     * The reserve price will be returned as "0.00" for all auctions except the ones by the current seller.
     * The basic form is
     * <code>GET /auctions</code> for a list of all auctions,
     * <code>GET /auctions/{id}</code> for information about a particular auction,
     * <code>GET /auctions/search?{parameters}</code> to perform a search.
     * @param req the current request
     * @param resp the response object
     * @throws ServletException something
     * @throws IOException something
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Sellers see more information about their auctions (like reserve price)
        User user = getUser(req.getCookies());
        int sellerID = 0;
        if (user != null) {
            if (user.getRole() == 2)
                sellerID = -1; // Admins can see everything
            else
                sellerID = user.getUserId();
        }

        // I don't know how necessary these next two lines are, or if this is the right place to write them,
        // but I'm gonna put them here anyways
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();

        String url = req.getPathInfo();

        if (url == null || url.equals("") || url.equals("/")) { // GET /auctions
            // Get all auctions
            AuctionListJSONWrapper auctions = jsonService.getAuctionJSONObjects(service.getAllAuctions(), sellerID);
            String json = jsonConverter.serialize(auctions);
            writer.write(json);
        }
        else {
            String[] urlParts = url.split("/");
            if (urlParts.length != 2) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // Distinguish between /{id} and /search
            if (urlParts[1].equals("search")) // /search
                searchAuctions(req, resp, sellerID);
            else { // Try to parse ID for /id
                int id = 0;
                try {
                    id = Integer.parseInt(urlParts[1]);

                    // Get auction by ID
                    Auction auc = service.getAuction(id);
                    if (auc == null)
                        resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    else {
                        AuctionJSONWrapper wrapper = jsonService.getAuctionJSONObject(auc, sellerID);
                        writer.write(jsonConverter.serialize(wrapper));
                    }
                }
                catch (NumberFormatException ex) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }


        }
    }

    /**
     * Implements the POST functionality for posting a new auction.
     * The new auction should be provided in JSON form.
     * @param req the current request
     * @param resp the response object
     * @throws ServletException something
     * @throws IOException something
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User seller = getUser(req.getCookies());
        // Don't allow user to create an auction if they're not logged in or if they're banned
        if (seller == null || seller.getRole() == 3) {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }
        String url = req.getPathInfo();
        boolean success = false;
        if (url == null || url.equals("")) { // Only allow POST to /auctions, not to /auctions/whatever
            String json = getRequestBody(req);
            AuctionJSONWrapper newAuction = jsonConverter.deserializeAuction(json);
            if (newAuction != null) {
                Auction auction = newAuction.toAuction();
                auction.setSellerID(seller.getUserId());
                Item auctionItem = newAuction.toItem();
                Auction ret = service.createAuction(auction, auctionItem);
                if (ret != null) {
                    // Return created object in response body
                    resp.setCharacterEncoding("UTF-8");
                    resp.setContentType("application/json");
                    auctionItem = service.getAuctionItem(ret);
                    AuctionJSONWrapper wrapper = new AuctionJSONWrapper(ret, auctionItem); // Are the IDs
                    resp.getWriter().write(jsonConverter.serialize(wrapper));
                    success = true;
                }
            }
        }

        if (success)
            resp.setStatus(HttpServletResponse.SC_CREATED);
        else
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Implements the PUT functionality for modifying data for an existing auction.
     * @param req the current request
     * @param resp the response object
     * @throws ServletException something
     * @throws IOException something
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO make sure that user is authenticated and has the proper permissions to do this
        String url = req.getPathInfo();
        if (url == null || url.equals("")) // Must PUT on a specific auction ID, not /auctions
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        else {
            int id = getID(url);
            if (id >= 0) {
                // Get updated auction info from request body
                String json = getRequestBody(req);
                AuctionJSONWrapper updatedAuction = jsonConverter.deserializeAuction(json);
                if (updatedAuction != null) {
                    Auction auc = updatedAuction.toAuction();
                    Item aucItem = updatedAuction.toItem();
                    if (service.updateAuction(auc, aucItem))
                        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    else
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
                else
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            else
                resp.sendError(-id);
        }
    }

    /**
     * Implements the DELETE functionality for deleting an auction.
     * This operation requires admin login credentials.
     * @param req the current request
     * @param resp the response object
     * @throws ServletException something
     * @throws IOException something
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getUser(req.getCookies());
        if (user == null || user.getRole() != 2) { // Only admins can delete auctions
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            return;
        }

        String url = req.getPathInfo();
        if (url == null || url.equals("")) // Must DELETE a specific auction, not /auctions
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        else {
            int id = getID(url);
            if (id >= 0) {
                service.removeAuction(id);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else
                resp.sendError(-id);
        }
    }

}
