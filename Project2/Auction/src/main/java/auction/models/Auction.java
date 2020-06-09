package auction.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Model class representing an auction.
 * This class doesn't store the information about the item being auctioned aside from its item ID.
 */
public class Auction {
    private int auctionID;
    private int itemID;
    private int sellerID;
    private LocalDateTime endDate;
    // Floating point values are bad for currency
    private BigDecimal startingPrice;
    private BigDecimal reservePrice;

    public Auction() {}

    /**
     * Constructs an <code>Auction</code> with the provided information.
     * If this is a new auction that will be inserted into a database, then
     * this is probably not the constructor you want to call.
     * The DAO <code>save()</code> method for this class will ignore any auction ID you set here
     * and replace it with its own from the database.
     * @param auctionID the ID of the auction
     * @param itemID the ID of the item being auctioned
     * @param seller the user ID of the seller
     * @param endDate the ending date of the auction
     * @param startingPrice the starting price for the auction
     * @param reservePrice the reserve price for the auction
     */
    public Auction(int auctionID, int itemID, int seller, LocalDateTime endDate, BigDecimal startingPrice, BigDecimal reservePrice) {
        this.auctionID = auctionID;
        this.itemID = itemID;
        this.sellerID = seller;
        this.endDate = endDate;
        this.startingPrice = startingPrice;
        this.reservePrice = reservePrice;
    }

    /**
     * Constructs an <code>Auction</code> with the provided information.
     * Use this constructor when this auction will be inserted as a new entry into a database.
     * The DAO <code>save()</code> method for this class will fill in the auction ID for you.
     * @param itemID the ID of the item being auctioned
     * @param seller the user ID of the seller
     * @param endDate the ending date of the auction
     * @param startPrice the starting price for the auction
     * @param reservePrice the reserve price for the auction
     */
    public Auction(int itemID, int seller, LocalDateTime endDate, BigDecimal startPrice, BigDecimal reservePrice) {
        this(-1, itemID, seller, endDate, startPrice, reservePrice);
    }
    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        this.auctionID = auctionID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getSellerID() {
        return sellerID;
    }

    public void setSellerID(int sellerID) {
        this.sellerID = sellerID;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(BigDecimal startingPrice) {
        this.startingPrice = startingPrice;
    }

    public BigDecimal getReservePrice() {
        return reservePrice;
    }

    public void setReservePrice(BigDecimal reservePrice) {
        this.reservePrice = reservePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Auction auction = (Auction) o;
        return auctionID == auction.auctionID &&
                itemID == auction.itemID &&
                sellerID == auction.sellerID &&
                Objects.equals(endDate, auction.endDate) &&
                Objects.equals(startingPrice, auction.startingPrice) &&
                Objects.equals(reservePrice, auction.reservePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(auctionID, itemID, sellerID, endDate, startingPrice, reservePrice);
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionID=" + auctionID +
                ", itemID=" + itemID +
                ", sellerID=" + sellerID +
                ", endDate=" + endDate +
                ", startingPrice=" + startingPrice +
                ", reservePrice=" + reservePrice +
                '}';
    }
}
