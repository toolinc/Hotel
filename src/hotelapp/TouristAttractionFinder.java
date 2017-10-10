package hotelapp;
import java.nio.file.*;

public class TouristAttractionFinder {

    private static final String host = "maps.googleapis.com";
    private static final String path = "/maps/api/place/textsearch/json";

    private ThreadSafeHotelData hdata;

    // FILL IN CODE: add data structures to store attractions
    // Alternatively, you can store these data structures in ThreadSafeHotelData

    /** Constructor for TouristAttractionFinder
     *
     * @param hdata
     */
    public TouristAttractionFinder(ThreadSafeHotelData hdata) {
        // FILL IN CODE

    }


    /**
     * Creates a secure socket to communicate with googleapi's server that
     * provides Places API, sends a GET request (to find attractions close to
     * the hotel within a given radius), and gets a response as a string.
     * Removes headers from the response string and parses the remaining json to
     * get Attractions info. Adds attractions to the ThreadSafeHotelData.
     *
     * @return A String of the response.
     */
    public void fetchAttractions(int radiusInMiles) {
        // FILL IN CODE
        // This method should call getRequest method

    }


    /** Print attractions near the hotels to a file.
     * The format is described in the lab description.
     *
     * @param filename
     */
    public void printAttractions(Path filename) {
        // FILL IN CODE
    }

    // FILL IN CODE: add other helper methods as needed

}
