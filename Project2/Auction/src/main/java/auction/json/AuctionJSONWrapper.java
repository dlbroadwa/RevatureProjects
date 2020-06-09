package auction.json;

import auction.models.Auction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * Wrapper class used for serializing <code>Auction</code> objects into JSON
 * and deserializing that JSON back into <code>Auction</code> objects.
 */
public class AuctionJSONWrapper {
    public int id;
    public int seller_id;
    public long end_date;
    public String starting_price;
    public String reserve_price;
    public static class ItemWrapper {
        public int id;
        public String name;
        public String desc;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemWrapper it = (ItemWrapper) o;
            return id == it.id &&
                    Objects.equals(name, it.name) &&
                    Objects.equals(desc, it.desc);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, desc);
        }
    }
    public ItemWrapper item;

    public AuctionJSONWrapper() {}

    /**
     * Constructs a new <code>AuctionJSONWrapper</code>.
     * @param auction the <code>Auction</code> to serialize
     * @param it the <code>Item</code> corresponding to that <code>Auction</code>
     */
    public AuctionJSONWrapper(Auction auction, auction.models.Item it) {
        id = auction.getAuctionID();
        seller_id = auction.getSellerID();
        end_date = auction.getEndDate().atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
        starting_price = auction.getStartingPrice().toString();
        reserve_price = auction.getReservePrice().toString();
        item = new ItemWrapper();
        item.id = it.getItemID();
        item.name = it.getName();
        item.desc = it.getDescription();
    }

    /**
     * Returns an <code>Auction</code> with the data contained in this object.
     * @return an <code>Auction</code> with this object's information
     */
    public Auction toAuction() {
        LocalDateTime endDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(end_date), ZoneId.systemDefault());
        return new Auction(id, item.id, seller_id, endDate, new BigDecimal(starting_price), new BigDecimal(reserve_price));
    }

    /**
     * Returns an <code>Item</code> with the data contained in this object.
     * @return the <code>Item</code> data in this object
     */
    public auction.models.Item toItem() {
        return new auction.models.Item(item.id, item.name, item.desc);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuctionJSONWrapper wrapper = (AuctionJSONWrapper) o;
        return id == wrapper.id &&
                seller_id == wrapper.seller_id &&
                end_date == wrapper.end_date &&
                Objects.equals(starting_price, wrapper.starting_price) &&
                Objects.equals(reserve_price, wrapper.reserve_price) &&
                Objects.equals(item, wrapper.item);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seller_id, end_date, starting_price, reserve_price, item);
    }
}
