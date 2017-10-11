package hotelapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.*;

/**
 * Class HotelData - a data structure that stores information about hotels and
 * hotel reviews. Allows to quickly lookup a Hotel given the hotel id. (use TreeMap).
 * Allows to efficiently find hotel reviews for a given hotelID (use a TreeMap,
 * where for each hotelId, the value is a TreeSet). Reviews for a
 * given hotel id are sorted by date from most recent to oldest;
 * if the dates are the same, the reviews are sorted by user nickname,
 * and the user nicknames are the same, by the reviewId.
 * You may NOT modify the signatures of methods in the starter code.
 */
public class HotelData {

    private final static Logger log = LogManager.getRootLogger();
    private Map<String, Hotel> hotels;
    private Map<String, TreeSet<Review>> reviews;

    /**
     * Default constructor.
     */
    public HotelData() {
        hotels = new TreeMap<>();
        reviews = new TreeMap<>();
    }

    /**
     * Create a Hotel given the parameters, and add it to the appropriate data
     * structure.
     *
     * @param hotelId       - the id of the hotel
     * @param hotelName     - the name of the hotel
     * @param city          - the city where the hotel is located
     * @param state         - the state where the hotel is located.
     * @param streetAddress - the building number and the street
     * @param lat           latitude
     * @param lon           longitude
     */
    public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat, double lon) {
        Address newAddress = new Address(city, state, streetAddress, lat, lon);
        Hotel newHotel = new Hotel(hotelId, hotelName, newAddress);
        hotels.put(hotelId, newHotel);
    }

    /**
     * Add a new hotel review. Add it to the map (to the TreeSet of reviews for a given key=hotelId).
     *
     * @param hotelId     - the id of the hotel reviewed
     * @param reviewId    - the id of the review
     * @param rating      - integer rating 1-5.
     * @param reviewTitle - the title of the review
     * @param review      - text of the review
     * @param isRecom     - whether the user recommends it or not
     * @param date        - date of the review in the format yyyy-MM-ddThh:mm:ss, e.g. "2016-06-29T17:50:37"
     * @param username    - the nickname of the user writing the review.
     * @return true if successful, false if unsuccessful because of invalid hotelId, invalid date
     * or rating. Needs to catch and handle the following exceptions:
     * ParseException if the date is invalid
     * InvalidRatingException if the rating is out of range.
     */
    public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,
                             boolean isRecom, String date, String username) {

        //if (!hotels.containsKey(hotelId)) return false;
        try {
            Review newReview = new Review(hotelId, reviewId, rating, reviewTitle, review, isRecom, date, username);
            if (reviews.containsKey(hotelId)) {
                reviews.get(hotelId).add(newReview);
            } else {
                TreeSet<Review> treeSetReview = new TreeSet<Review>();
                treeSetReview.add(newReview);
                reviews.put(hotelId, treeSetReview);
            }
            return true;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidRatingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Merge the local Thread information in the main memory
     *
     * @param hdata -local information
     **/
    public void mergeReviews(HotelData hdata) {
        hdata.reviews.forEach((key, values) -> {
            this.reviews.put(key, values);
        });
    }

    /**
     * Returns a string representing information about the hotel with the given
     * id, including all the reviews for this hotel separated by --------------------
     * Format of the string:
     * HotelName: hotelId
     * streetAddress
     * city, state
     * --------------------
     * Review by username on date
     * Rating: rating
     * ReviewTitle
     * ReviewText
     * --------------------
     * Review by username on date
     * Rating: rating
     * ReviewTitle
     * ReviewText ...
     *
     * @param hotelId
     * @return - output string.
     */
    public String toString(String hotelId) {
        StringBuilder sb = new StringBuilder();
        if (hotels.get(hotelId) != null) {
            sb.append(hotels.get(hotelId));
            if (reviews.containsKey(hotelId)) {
                for (Review review : reviews.get(hotelId)) {
                    sb.append(System.lineSeparator());
                    sb.append("--------------------");
                    sb.append(System.lineSeparator());
                    sb.append(review);
                }
            }
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }


    /**
     * Return a list of hotel ids, in alphabetical order of hotelIds
     *
     * @return
     */
    public List<String> getHotels() {
        List<String> hotelIdList = new ArrayList<String>(hotels.keySet());
        return hotelIdList;
    }

    /**
     * Return the average rating for the given hotelId.
     *
     * @param hotelId- the id of the hotel
     * @return average rating or 0 if no ratings for the hotel
     */
    public double getRating(String hotelId) {
        if (hotels.containsKey(hotelId)) {
            TreeSet<Review> reviewSet = reviews.get(hotelId);
            Iterator<Review> iterator = reviewSet.iterator();
            int sumRating = 0;
            while (iterator.hasNext()) {
                sumRating = iterator.next().getRating();
            }
            return sumRating / reviewSet.size();
        }
        return 0;
    }


    /**
     * Set the average rating for a specif hotel
     *
     * @param hotelId -Hotel Id
     **/
    public void setAvgHotel(String hotelId) {
        hotels.get(hotelId).setAverageRating(getRating(hotelId));
    }


    /**
     * Read the given json file with information about the hotels (check hotels.json to see the expected format)
     * and load it into the appropriate data structure(s).
     * Do not hardcode the name of the file! the could should work on any json file in the same format.
     * You may use JSONSimple library for parsing a JSON file.
     */
    public void loadHotelInfo(String jsonFilename) {
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new String(Files.readAllBytes(Paths.get(jsonFilename))));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray hotelsArray = (JSONArray) jsonObject.get("sr");
            for (int i = 0; i < hotelsArray.size(); i++) {
                Map<Object, Object> mapHotleJson = new HashMap<>();
                mapHotleJson = (Map<Object, Object>) hotelsArray.get(i);
                Map<Object, Object> mapLLHotel = (Map<Object, Object>) mapHotleJson.get("ll");
                addHotel(((String) mapHotleJson.get("id")), ((String) mapHotleJson.get("f")), ((String) mapHotleJson.get("ci")),
                        ((String) mapHotleJson.get("pr")), ((String) mapHotleJson.get("ad")),
                        Double.parseDouble((String) mapLLHotel.get("lat")), Double.parseDouble((String) mapLLHotel.get("lng")));
            }
        } catch (FileSystemNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Find all review files in the given path (including in subfolders and subsubfolders etc),
     * read them, parse them using JSONSimple library, and
     * load review info to the TreeMap that contains a TreeSet of Review-s for each hotel id (you should
     * have defined this instance variable above)
     *
     * @param path
     */
    public void loadReviews(Path path) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    loadReviews(entry);
                }
                if (entry.toString().endsWith(".json")) {
                    try {
                        JSONParser parser = new JSONParser();
                        JSONObject obj = (JSONObject) parser.parse(new String(Files.readAllBytes(Paths.get(String.valueOf(entry)))));
                        JSONObject reviewDetails = (JSONObject) obj.get("reviewDetails");
                        HashMap<Object, Object> reviewCollection = (HashMap<Object, Object>) reviewDetails.get("reviewCollection");
                        JSONArray reviewArray = (JSONArray) reviewCollection.get("review");
                        Iterator<Review> iterator = reviewArray.iterator();
                        while (iterator.hasNext()) {
                            Map<Object, Object> mapReviewJson = new HashMap<>();
                            mapReviewJson = (Map<Object, Object>) iterator.next();
                            addReview(((String) mapReviewJson.get("hotelId")), ((String) mapReviewJson.get("reviewId")),
                                    Integer.valueOf(((Long) mapReviewJson.get("ratingOverall")).intValue()),
                                    ((String) mapReviewJson.get("title")), ((String) mapReviewJson.get("reviewText")),
                                    Boolean.parseBoolean(((String) mapReviewJson.get("isRecommended")).toLowerCase()),
                                    ((String) mapReviewJson.get("reviewSubmissionTime")),
                                    ((String) mapReviewJson.get("userNickname")));
                        }
                    } catch (FileSystemNotFoundException | ParseException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Save the string representation of the hotel data to the file specified by
     * filename in the following format (see "expectedOutput" in the test folder):
     * an empty line
     * A line of 20 asterisks ********************
     * on the next line information for each hotel, printed
     * in the format described in the toString method of this class.
     * <p>
     * The hotels in the file should be sorted by hotel ids
     *
     * @param filename - Path specifying where to save the output.
     */
    public void printToFile(Path filename) {
        StringBuilder sb = new StringBuilder();
        List<String> hotelsList = getHotels();

        hotelsList.forEach((hotel) -> {
            sb.append(System.lineSeparator());
            sb.append("********************");
            sb.append(System.lineSeparator());
            sb.append(toString(hotel));
        });
        try (PrintWriter writer = new PrintWriter(filename.toString())) {
            writer.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
