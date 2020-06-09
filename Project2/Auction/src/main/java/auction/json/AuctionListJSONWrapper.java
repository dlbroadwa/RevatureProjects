package auction.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Wrapper class for serializing/deserializing a collection of <code>Auction</code>s.
 * The serialized format looks like
 * <pre>{auctions: [(list of auctions)]}</pre>
 */
public class AuctionListJSONWrapper {
    public final List<AuctionJSONWrapper> auctions;

    /**
     * Constructs an <code>AuctionListJSONWrapper</code> with an empty auctions list.
     */
    public AuctionListJSONWrapper() {
        auctions = new ArrayList<>();
    }

    /**
     * Constructs an <code>AuctionListJSONWrapper</code> with a given list of auction data
     * @param auctions the auctions to be serialized, in wrapper form
     */
    public AuctionListJSONWrapper(List<AuctionJSONWrapper> auctions) {
        this.auctions = auctions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionListJSONWrapper that = (AuctionListJSONWrapper) o;
        return Objects.equals(auctions, that.auctions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctions);
    }
}
