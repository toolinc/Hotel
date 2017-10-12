package hotelapp;
import java.awt.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class TouristAttractionFinder {

    private static final String host = "maps.googleapis.com";
    private static final String path = "/maps/api/place/textsearch/json";
    private static final String FIND_TYPE = "attractions";


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
     */
    public void fetchAttractions(int radiusInMiles) {
        TouristAttraction touristAttraction = new TouristAttraction();
        // FILL IN CODE
        // This method should call getRequest method


        StringBuffer buf = new StringBuffer();

        try (Socket socket = new Socket(host, PORT)) { // create a connection to the
            // web server
            OutputStream out = socket.getOutputStream(); // get the output stream from socket
            InputStream instream = socket.getInputStream(); // get the input stream from socket

            // wrap the input stream to make it easier to read from
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream));

            // create and send request
            String request = getRequest(radiusInMiles);
            System.out.println("Request = " + request);
            out.write(request.getBytes());
            out.flush();

            // receive response
            // note: we are not removing the header (as we should have!)
            String line = reader.readLine();
            while (line != null) {
                buf.append(line + System.lineSeparator());
                line = reader.readLine();
            }

        } catch (IOException e) {
            System.out.println("HTTPFetcher::IOException occured during download: " + e.getMessage());
        }
        //buf.toString(); // all HTML code is in this string

        System.out.println(buf.toString());

        List list = new ArrayList<>();
        list.add(34);
        list.add("3");



    }


    /**
     * A method that creates a GET request for the given host and resource
     * @param host
     * @param pathResourceQuery
     * @return HTTP GET request returned as a string
     */
    private static String getRequest(int radius) {

        //1609.34

        String request = "GET " + path
                + "?location="
                + "&radius=" + radius
                + "&type=" + FIND_TYPE
                + "&key="+ API_KEY
                + " HTTP/1.1" + System.lineSeparator() // GET
                // request
                + "Host: " + host + System.lineSeparator() // Host header required for HTTP/1.1
                + "Connection: close" + System.lineSeparator() // make sure the server closes the
                // connection after we fetch one page
                + System.lineSeparator();


//        https://maps.googleapis.com/maps/api/place/nearbysearch/json
//                ?location=-33.8670522,151.1957362
//                &radius=500
//                &types=food
//                &name=harbour
//                &key=YOUR_API_KEY

        return request;
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


