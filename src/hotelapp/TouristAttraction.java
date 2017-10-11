package hotelapp;

public class TouristAttraction {
    private final String attractionId;
    private final String name;
    private final String address;
    private final double rating;

    /** Constructor for TouristAttraction
     *
     * @param id
     * @param name
     * @param rating
     * @param address
     */
    public TouristAttraction(String id, String name, double rating, String address) {
        attractionId = id;
        this.name = name;
        this.rating = rating;
        this.address = address;
    }

    /** @return the attraction Id*/
    public String getAttractionId() {
        return attractionId;
    }

    /** @return the name of the attraction*/
    public String getName() {
        return name;
    }

    /** @return the address of the attraction*/
    public String getAddress() {
        return address;
    }

    /** @return the rating attraction*/
    public double getRating() {
        return rating;
    }

    /** toString() method
     * @return a String representing this
     * TouristAttraction
     */
    @Override
    public String toString() {
        // FILL IN CODE
        return ""; // do not forget to change this
    }
}
