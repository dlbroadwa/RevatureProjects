package auction.services;

import auction.dataaccess.DAO;
import auction.models.Auction;
import auction.models.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Auction service class providing a layer of abstraction from the direct database access methods.
 */
public class AuctionService {
    private final DAO<Auction, Integer> auctionDao;
    private final DAO<Item, Integer> itemDao;

    /**
     * Constructs an <code>AuctionService</code> with the given database connections for
     * <code>Auction</code>s and <code>Item</code>s.
     * @param auctionDao the data access object for the <code>Auction</code> class
     * @param itemDao the data access object for the <code>Item</code> clas
     */
    public AuctionService(DAO<Auction, Integer> auctionDao, DAO<Item, Integer> itemDao) {
        this.auctionDao = auctionDao;
        this.itemDao = itemDao;
    }

    /**
     * Private helper method which checks whether an auction is expired, and also truncates the
     * starting and reserve prices to a whole number of cents.
     * @param auction the <code>Auction</code> to check
     * @return <code>true</code> if the <code>Auction</code> is valid and has an end date in the future.
     */
    private boolean checkAuction(Auction auction) {
        // Check that end date is valid (after today)
        if (auction == null || auction.getEndDate().isBefore(LocalDateTime.now(ZoneOffset.UTC)))
            return false;
        // Round the prices so that they have at most 2 digits after the decimal point
        if (auction.getStartingPrice() != null)
            auction.setStartingPrice(auction.getStartingPrice().setScale(2, RoundingMode.DOWN));
        if (auction.getReservePrice() != null)
            auction.setReservePrice(auction.getReservePrice().setScale(2, RoundingMode.DOWN));

        return true;
    }

    // Wrappers for CRUD operations

    /**
     * Inserts into the database a new auction with the given auction information.
     * Any auction ID assigned to this auction will be ignored and replaced with the inserted ID from the database.
     * @param auction the <code>Auction</code> to insert
     * @return <code>true</code> if the insertion was successful, <code>false</code> otherwise.
     */
    public boolean createAuction(Auction auction) {
        if (auction == null || !checkAuction(auction) || itemDao.retrieveByID(auction.getItemID()) == null)
            return false;
        return auctionDao.save(auction);
    }

    /**
     * Inserts into the database a new auction with the given auction and item information.
     * Any auction ID assigned to this auction will be ignored and replaced with the inserted ID from the database.
     * The item being auctioned may be either a new or an existing item; if it is a new item, then the
     * item ID field for that item must be 0 or negative and the name must be non-null and non-empty, and the
     * database will populate the item ID field with the ID from the database.
     * If it is an existing item, then the item ID field will be used to look up the item information and anything
     * in the name and description fields will be ignored.
     * @param newAuction the <code>Auction</code> to insert
     * @param item the <code>Item</code> to associate with this auction
     * @return the newly-inserted <code>Auction</code>, or <code>null</code> if the auction could not be inserted
     * for any reason.
     */
    public Auction createAuction(Auction newAuction, Item item) {
        if (!checkAuction(newAuction))
            return null;

        // Check whether trying to create a new auction with an existing item
        if (item.getItemID() > 0) {
            item = itemDao.retrieveByID(item.getItemID());
            if (item == null)
                return null;
        }
        else {
            // Item name cannot be null
            if (item.getName() == null || item.getName().trim().equals(""))
                return null;
            // Insert item into the database (which assigns it an item ID)
            if (!itemDao.save(item))
                return null;
        }

        newAuction.setItemID(item.getItemID());

        if (auctionDao.save(newAuction))
            return newAuction;
        else
            return null;
    }

    /**
     * Inserts a new auction into the database with the given information.
     * @param item the item to associate with this auction
     * @param sellerID the user ID of the seller
     * @param endDate the ending date of the auction
     * @param startPrice the starting price for the auction
     * @param reservePrice the reserve price for the auction
     * @return the newly-created <code>Auction</code>, or <code>null</code> if the auction could not be created.
     */
    public Auction createAuction(Item item, int sellerID, LocalDateTime endDate, BigDecimal startPrice, BigDecimal reservePrice) {
        Auction newAuction = new Auction(-1, sellerID, endDate, startPrice, reservePrice);
        return createAuction(newAuction, item);
    }

    /**
     * Updates an existing auction in the database.
     * @param newAuction the new auction information
     * @return <code>true</code> on success, <code>false</code> otherwise.
     */
    public boolean updateAuction(Auction newAuction) {
        if (newAuction == null)
            return false;
        return auctionDao.update(newAuction);
    }

    /**
     * Updates an existing auction in the database along with the item being auctioned.
     * The new item information can be either an entirely new item (in which case the item ID field
     * for that item will be set by the database) or an existing item (in which case the existing information
     * for that item will be updated).
     * @param newAuction the new auction information
     * @param newItem the new item information for that auction
     * @return <code>true</code> on success, <code>false</code> otherwise.
     */
    public boolean updateAuction(Auction newAuction, Item newItem) {
        // Check that the auction data actually exists
        if (newAuction == null || auctionDao.retrieveByID(newAuction.getAuctionID()) == null)
            return false;

        // Check if item already exists
        Item it = itemDao.retrieveByID(newItem.getItemID());
        if (it != null) {
            if (!itemDao.update(newItem))
                return false;
        }
        // Create the item if it doesn't (this sets newItem.itemID)
        else if (!itemDao.save(newItem))
            return false;

        newAuction.setItemID(newItem.getItemID());
        return auctionDao.update(newAuction);
    }

    /**
     * Retrieves the auction with the given auction ID.
     * @param auctionID the auction ID of the auction to search for
     * @return the <code>Auction</code> with that ID, or <code>null</code> if no such auction was found.
     */
    public Auction getAuction(int auctionID) {
        return auctionDao.retrieveByID(auctionID);
    }

    /**
     * Retrieves the item information for the item being auctioned by the auction with the given auction ID.
     * @param auctionID the ID of the auction to retrieve item information from
     * @return the <code>Item</code> being auctioned by this auction
     */
    public Item getAuctionItem(int auctionID) {
        return getAuctionItem(getAuction(auctionID));
    }

    /**
     * Retrieves the item information for the item being auctioned by the given auction
     * @param auction an <code>Auction</code> to retrieve item information from
     * @return the <code>Item</code> being auctioned by this auction
     */
    public Item getAuctionItem(Auction auction) {
        if (auction == null)
            return null;
        return itemDao.retrieveByID(auction.getItemID());
    }

    /**
     * Returns a list of all auctions in the database.
     * @return a list of all saved auctions
     */
    public List<Auction> getAllAuctions() {
        return auctionDao.retrieveAll();
    }

    /**
     * Removes an auction from the database
     * @param auctionID the ID of the auction to remove
     * @return <code>true</code> on success, <code>false</code> if an error occurred.
     */
    public boolean removeAuction(int auctionID) {
        Auction auction = getAuction(auctionID);
        if (auction != null)
            return auctionDao.delete(auction);
        return true;
    }

    // Some other helpers

    /**
     * Checks whether an auction has ended.
     * @param auctionID the ID of the auction to check
     * @return <code>true</code> if this auction has already ended, <code>false</code> otherwise.
     */
    public boolean isAuctionEnded(int auctionID) {
        Auction auction = getAuction(auctionID);
        if (auction == null)
            return false;

        return LocalDateTime.now(ZoneOffset.UTC).isAfter(auction.getEndDate());
    }

    /**
     * Searches for auctions by item name. The search is performed in a case-insensitive way.
     * @param query the item search query
     * @return a list of <code>Auction</code>s whose item matches that query
     */
    public List<Auction> findByItemName(String query) {
        List<Auction> allAuctions = getAllAuctions();
        final String queryUpper = query.toUpperCase();

        // Yay for functional programming!
        return allAuctions.stream()
                .filter(a -> {
                    Item i = itemDao.retrieveByID(a.getItemID());
                    if (i != null)
                        return i.getName().toUpperCase().contains(queryUpper);
                    else
                        return false;
                })
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all auctions by the given seller.
     * @param id the ID of the seller to search for
     * @return a list of <code>Auction</code>s by that seller
     */
    public List<Auction> findBySellerID(int id) {
        List<Auction> allAuctions = getAllAuctions();

        return allAuctions.stream()
                .filter(a -> a.getSellerID() == id)
                .collect(Collectors.toList());
    }
}
