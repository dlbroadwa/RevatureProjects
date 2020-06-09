package auction.dataaccess;

import auction.models.Auction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages a SQL database connection for saving and retrieving
 * <code>Auction</code> objects.
 */
public class AuctionDAO implements DAO<Auction, Integer> {

    private ConnectionUtils connectionUtils = null;

    /**
     * Constructs a new <code>AuctionDAO</code>.
     * @param connectionUtils the database connection properties
     */
    public AuctionDAO(ConnectionUtils connectionUtils) {
        if(connectionUtils != null) {
            this.connectionUtils = connectionUtils;
        }
    }

    /**
     * Inserts a new <code>Auction</code> object into the table.
     * If successful, this method will set the <code>auction_id</code> of the given parameter to the
     * ID assigned to it by the database.
     * @param obj the new auction data to be stored
     * @return <code>true</code> if the insertion was successful; <code>false</code> otherwise.
     */
    @Override
    public boolean save(Auction obj) {
        String saveStatement = "INSERT INTO " + connectionUtils.getDefaultSchema() + "." + "auction"
                + " (itemid, sellerid, enddate, startingprice, reserveprice) VALUES (?,?,?,?,?) returning auctionid";
        try (Connection connection = connectionUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveStatement)){
            preparedStatement.setInt(1, obj.getItemID());
            preparedStatement.setInt(2, obj.getSellerID());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(obj.getEndDate()));
            preparedStatement.setBigDecimal(4, obj.getStartingPrice());
            preparedStatement.setBigDecimal(5, obj.getReservePrice());

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    obj.setAuctionID(rs.getInt(1));
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returns a list of all auctions in the table.
     * @return a list of auctions
     */
    public List<Auction> retrieveAll() {
        ArrayList<Auction> auctions = new ArrayList<>();

        String sql = "SELECT * FROM " + connectionUtils.getDefaultSchema() + "." + "auction";
        try (Connection connection = connectionUtils.getConnection();
            Statement auctionStatement = connection.createStatement();
            ResultSet resultSet = auctionStatement.executeQuery(sql)){

            while (resultSet.next()) {

                auctions.add(new Auction(
                        resultSet.getInt("auctionid"),
                        resultSet.getInt("itemid"),
                        resultSet.getInt("sellerid"),
                        resultSet.getTimestamp("enddate").toLocalDateTime(),
                        resultSet.getBigDecimal("startingprice"),
                        resultSet.getBigDecimal("reserveprice")
                ));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auctions;
    }

    /**
     * Retrieves the auction with the specified auction ID.
     * @param integer the ID of the auction to search for
     * @return the auction with that ID, or <code>null</code> if no such auction was found.
     */
    @Override
    public Auction retrieveByID(Integer integer) {
        Auction auction=null;
        String selectStatement = "SELECT * FROM " + connectionUtils.getDefaultSchema() + "." + "auction"
                + " WHERE auctionid = ?";
        try (Connection connection = connectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectStatement)){
            preparedStatement.setInt(1, integer);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    auction = new Auction();

                    auction.setAuctionID(resultSet.getInt("auctionid"));
                    auction.setItemID(resultSet.getInt("itemid"));
                    auction.setSellerID(resultSet.getInt("sellerid"));
                    auction.setEndDate(resultSet.getTimestamp("enddate").toLocalDateTime());
                    auction.setStartingPrice(resultSet.getBigDecimal("startingprice"));
                    auction.setReservePrice(resultSet.getBigDecimal("reserveprice"));
                } else
                    System.out.println("No user by that id found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return auction;
    }

    /**
     * Removes the auction with the same auction ID as the given parameter from the database.
     * @param obj the object to be deleted.
     * @return <code>true</code> in most cases. <code>false</code> is returned when an issue occurred with
     * accessing the database.
     */
    @Override
    public boolean delete(Auction obj) {
        String deleteStatement = "DELETE FROM " + connectionUtils.getDefaultSchema() + "." + "auction"
                + " WHERE auctionid = ?";
        try (Connection connection = connectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteStatement)){
            preparedStatement.setInt(1, obj.getAuctionID());
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates auction information.
     * @param obj the new auction data
     * @return <code>true</code> if at least one row was affected by the update, <code>false</code> otherwise.
     */
    @Override
    public boolean update(Auction obj) {
        String updateStatement = "UPDATE " + connectionUtils.getDefaultSchema() + ".auction SET itemid = ?, sellerid = ?, " +
                " enddate = ?, startingprice = ?, reserveprice = ? WHERE auctionid = ?";
        try (Connection connection = connectionUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateStatement)){

            preparedStatement.setInt(1,obj.getItemID());
            preparedStatement.setInt(2,obj.getSellerID());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(obj.getEndDate()));
            preparedStatement.setBigDecimal(4,obj.getStartingPrice());
            preparedStatement.setBigDecimal(5,obj.getReservePrice());
            preparedStatement.setInt(6,obj.getAuctionID());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
