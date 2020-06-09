package auction.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Helper class for converting between raw JSON (in string form) and auction data.
 *
 * The basic format of a JSON Auction is:
 * <pre>
 * {
 *  "id": {id},
 *  "seller_id": {sellerID},
 *  "end_date": {endDate},
 *  "starting_price": {startingPrice},
 *  "reserve_price": {reservePrice},
 *  "item":
 *    {"id": {id},
 * 	   "name": {name},
 * 	   "desc": {description}}
 * }
 * </pre>
 */
public class AuctionJSONConverter {
    private final ObjectMapper mapper;

    /**
     * Constructs a new <code>AuctionJSONConverter</code>.
     */
    public AuctionJSONConverter() {
        mapper = new ObjectMapper();
    }

    /**
     * Takes the input auction info and outputs a JSON string representation of it
     * @param auction the auction data to serialize
     * @return a JSON string representing that auction, or <code>null</code> if an error occurred.
     */
    public String serialize(AuctionJSONWrapper auction) {
        String result = null;
        try {
            result = mapper.writeValueAsString(auction);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Serializes data on multiple auctions.
     * The output is returned as a JSON object with an <code>auctions</code> array containing the individual
     * auction objects.
     * @param auctions the auctions to serialize
     * @return a JSON string representing that set of auctions, or <code>null</code> if an error occurred.
     */
    public String serialize(AuctionListJSONWrapper auctions) {
        String result = null;
        try {
            result = mapper.writeValueAsString(auctions);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Deserializes a JSON string into auction data for a single auction.
     * @param json the JSON to parse
     * @return an <code>AuctionJSONWrapper</code> with the deserialized data.
     */
    public AuctionJSONWrapper deserializeAuction(String json) {
        AuctionJSONWrapper ret = null;
        try {
            ret = mapper.readValue(json, AuctionJSONWrapper.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Deserializes a JSON string into auction data for multiple auctions.
     * @param json the JSON to parse
     * @return an <code>AuctionListJSONWrapper</code> containing the data for all of the objects in the given
     * JSON string.
     */
    public AuctionListJSONWrapper deserializeAuctions(String json) {
        AuctionListJSONWrapper ret = null;
        try {
            ret = mapper.readValue(json, AuctionListJSONWrapper.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ret;
    }
}
