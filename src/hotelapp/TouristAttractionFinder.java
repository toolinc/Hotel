package hotelapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;

public class TouristAttractionFinder {

    private static final String host = "maps.googleapis.com";
    private static final String path = "/maps/api/place/textsearch/json";
    private static final String FIND_TYPE = "tourist+attractions+in+";
    private static final String API_KEY_PATH = "input/ApiKey.txt";
    private static final int PORT = 443;
    private static final double MileToMeters = 1609.34;

    private final ThreadSafeHotelData hdata;
    private final Map<String,List<TouristAttraction>> listAttractions;

    /** Constructor for TouristAttractionFinder
     *
     * @param hdata
     */
    public TouristAttractionFinder(ThreadSafeHotelData hdata) {
        this.hdata = hdata;
        listAttractions = new HashMap<>();
    }


    /**
     * Creates a secure socket to communicate with googleapi's server that
     * provides Places API, sends a GET request (to find attractions close to
     * the hotel within a given radius), and gets a response as a string.
     * Removes headers from the response string and parses the remaining json to
     * get Attractions info. Adds attractions to the ThreadSafeHotelData.
     */
    public void fetchAttractions(int radiusInMiles) {
        List<String> hotels= hdata.getHotels();
        StringBuffer sb = new StringBuffer();

        URL url;
        PrintWriter out = null;
        BufferedReader in = null;
        SSLSocket socket = null;
        try {
            url = new URL("https://"+host);
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            socket = (SSLSocket) factory.createSocket(url.getHost(), PORT);

            // output stream for the secure socket
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            for (String id: hotels){

                String request = getRequest(id, radiusInMiles*MileToMeters);
                System.out.println("Request: " + request);

                // send a request to the server
                out.println(request);
                out.flush();

                // input stream for the secure socket.
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // use input stream to read server's response
                String line;
                while ((line = in.readLine()) != null) {
                    if(!line.matches("^[A-Z].*")) {
                        sb.append(line);
                        sb.append(System.lineSeparator());
                    }
                }

                loadAttractions(id, sb.toString());

            }
        } catch (IOException e) {
            System.out.println(
                    "An IOException occured while writing to the socket stream or reading from the stream: " + e);
        } finally {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException e) {
                System.out.println("An exception occured while trying to close the streams or the socket: " + e);
            }
        }
    }


    /**
     * A method that creates a GET request for the given host and resource
     * @param radius
     * @return HTTP GET request returned as a string
     */
    private  String getRequest(String hotelId, double radius) {
        Object[] location = hdata.getLocationHotel(hotelId);
        System.out.println(location[0] +" "+ location[1]);
        String request = "GET " + path
                + "?location="+ location[0] + location[1]
                + "&radius=" + radius
                + "&query=" + FIND_TYPE+((String) location[2]).replace(" ", "+")
                + "&key="+ getApiKey()
                + " HTTP/1.1" + System.lineSeparator()
                + "Host: " + host + System.lineSeparator()
                + "Connection: close" + System.lineSeparator()
                + System.lineSeparator();

        return request;
    }

    /** Print attractions near the hotels to a file.
     * The format is described in the lab description.
     *
     * @param filename
     */
    public void printAttractions(Path filename) {

    }

    /**
     * Get the Api Key
     * The method looks in a file called ApiKey
     * format of the file
     *      key = ApIkEy
     *
     * @return the ApiKey String
     */
    private static String getApiKey() {
        String key = "";
        Path path = Paths.get(API_KEY_PATH);
        try (BufferedReader reader = Files.newBufferedReader(path, Charset.forName("UTF-8"))) {
            String line;
            while ((line=reader.readLine()) != null) {
                key = line.replaceFirst(".*=", "");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return key.trim();
    }

    /**
     * Load the attraction in the Map
     * @param hotelId - Hotel id
     * @param attractionsList - String JSON Object with all the attractions
     */
    private void loadAttractions(String hotelId, String attractionsList){
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(attractionsList);
            JSONArray attractions = (JSONArray) ((JSONObject) obj).get("results");
            Iterator<JSONObject> iterator = attractions.iterator();

            List<TouristAttraction> list = new ArrayList<>();

            while(iterator.hasNext()){
                double rating;
                JSONObject att = iterator.next();

                System.out.println(att.get("id"));
                System.out.println(att.get("name"));
                System.out.println(att.get("formatted_address"));
                System.out.println(att.get("rating"));

                if(att.get("rating") == null) {
                    rating = 0;
                }else if (att.get("rating").getClass() == Long.class){
                    rating = ((Long) att.get("rating")).doubleValue();
                } else {
                    rating = (Double) att.get("rating");
                }

                TouristAttraction attraction = TouristAttraction.Builder.newBuilder()
                        .setId((String) att.get("id"))
                        .setName((String) att.get("name"))
                        .setAddress((String) att.get("formatted_address"))
                        .setRating(rating)
                        .build();

                list.add(attraction);
            }
            listAttractions.put(hotelId, list);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}


