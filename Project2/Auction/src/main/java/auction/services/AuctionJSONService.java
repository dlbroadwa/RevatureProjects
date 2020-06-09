package auction.services;

import auction.json.AuctionJSONWrapper;
import auction.json.AuctionListJSONWrapper;
import auction.models.Auction;
import auction.models.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for turning <code>Auction</code>s and lists of <code>Auction</code>s
 * into a form which is more easily serialized/deserialized to/from JSON.
 */
public class AuctionJSONService {
    private final AuctionService service;

    /**
     * Constructs a new <code>AuctionJSONService</code>.
     * The <code>AuctionService</code> is necessary for retrieving the item data for an auction,
     * since the <code>Auction</code> class does not actually store its item information.
     * @param service the <code>AuctionService</code> to use for accessing auction/item information
     */
    public AuctionJSONService(AuctionService service) {
        this.service = service;
    }

    /**
     * Gets the <code>AuctionJSONWrapper</code> corresponding to the given <code>Auction</code>.
     * @param auction an <code>Auction</code>
     * @return an <code>AuctionJSONWrapper</code> containing that auction's information
     */
    public AuctionJSONWrapper getAuctionJSONObject(Auction auction) {
        if (auction == null)
            return null;
        Item item = service.getAuctionItem(auction);
        if (item == null)
            return null;
        return new AuctionJSONWrapper(auction, item);
    }

    /**
     * Gets the <code>AuctionJSONWrapper</code> corresponding to the given <code>Auction</code>.
     * If <code>hideReserveExceptForID == -1</code>, then this function behaves identically to the single-arg
     * overload of this function; otherwise, the function hides the reserve price for the auction
     * (by setting it to "0.00") unless the seller ID for the auction equals <code>hideReserveExceptForID</code>.
     * @param auction an <code>Auction</code>
     * @param hideReserveExceptForID the ID of the seller whose reserve price information should NOT be hidden.
     *                               If -1, no reserve price information is hidden.
     * @return an <code>AuctionJSONWrapper</code> containing that auction's information
     */
    public AuctionJSONWrapper getAuctionJSONObject(Auction auction, int hideReserveExceptForID) {
        AuctionJSONWrapper wrapper = getAuctionJSONObject(auction);
        if (wrapper != null && hideReserveExceptForID != -1 && wrapper.seller_id != hideReserveExceptForID)
            wrapper.reserve_price = "0.00";
        return wrapper;
    }

    /**
     * Gets the <code>AuctionListJSONWrapper</code> for the given list of <code>Auction</code>s.
     * @param auctions a list of <code>Auction</code>s
     * @return a <code>AuctionListJSONWrapper</code> corresponding to that list of auctions.
     */
    public AuctionListJSONWrapper getAuctionJSONObjects(List<Auction> auctions) {
        if (auctions == null)
            return null;
        List<AuctionJSONWrapper> ret = new ArrayList<>();
        for (Auction auction: auctions) {
            Item item = service.getAuctionItem(auction);
            ret.add(new AuctionJSONWrapper(auction, item));
        }

        return new AuctionListJSONWrapper(ret);
    }

    /**
     * Gets the <code>AuctionListJSONWrapper</code> for the given list of <code>Auction</code>s.
     * If <code>hideReserveExceptForID == -1</code>, then this function behaves identically to the single-arg
     * overload of this function; otherwise, the function hides all reserve prices (by setting them to "0.00")
     * except for the ones with seller ID equal to <code>hideReserveExceptForID</code>.
     * @param auctions a list of <code>Auction</code>s
     * @param hideReserveExceptForID the ID of the seller whose reserve price information should NOT be hidden.
     *                               If -1, no reserve price information is hidden.
     * @return an <code>AuctionListJSONWrapper</code> corresponding to that list of auctions
     */
    public AuctionListJSONWrapper getAuctionJSONObjects(List<Auction> auctions, int hideReserveExceptForID) {
        AuctionListJSONWrapper wrapper = getAuctionJSONObjects(auctions);
        if (wrapper != null && hideReserveExceptForID != -1) {
            for (AuctionJSONWrapper w: wrapper.auctions) {
                if (w.seller_id != hideReserveExceptForID)
                    w.reserve_price = "0.00";
            }
        }
        return wrapper;
    }
}
