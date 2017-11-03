package hotelapp.model;

/**
 * A class that represents a hotel. Stores hotelId, name, address, and averageRating.
 * Implements Comparable - the hotels are compared based on the hotel names. If the names are the same, hotels
 * are compared based on the hotelId.
 *
 * @author okarpenko
 */
public final class Hotel implements Comparable<Hotel> {

    private final String id;
    private final String name;
    private final Address address;
    private final double averageRating;


    /**
     * Constructor
     *
     * @param builder - has all the information to create a Hotel
     */
    private Hotel(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.address = builder.address;
        this.averageRating = builder.averageRating;
    }

    /**
     * @return the Id of the Hotel
     */
    public String gethId() {
        return id;
    }

    /**
     * @return the name of the Hotel
     */
    public String getName() {
        return name;
    }

    /**
     * @return the address of the Hotel
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @return the average rating of the Hotel
     */
    public double getAverageRating() {
        return averageRating;
    }

    /**
     * Compare hotels based on the name (alphabetically). May use compareTo method in class String.
     * If the names are the same, compare based on the hotel ids.
     */
    @Override
    public int compareTo(Hotel o) {
        int compare = this.name.compareTo(o.name);
        if (compare == 0) {
            return this.id.compareTo(o.id);
        }
        return compare;
    }

    /**
     * Returns the string representation of the hotel in the following format:
     * hotelName: hotelID
     * streetAddress
     * city, state
     * <p>
     * Example: Travelodge Central San Francisco: 40682
     * 1707 Market St
     * San Francisco, CA
     * <p>
     * Does not include information about the reviews.
     */
    public String toString() {
        String res = getName() + ": " + gethId() + System.lineSeparator() + getAddress();
        return res;
    }

    public static final class Builder {

        private String id;
        private String name;
        private Address address;
        private double averageRating;

        /**
         * Constructor
         */
        private Builder() {
        }

        /**
         * Sets the id of the hotel.
         *
         * @param id the hId to set
         */
        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name.
         *
         * @param name the name to set
         */
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the address.
         *
         * @param address the address to set
         */
        public Builder setAddress(Address address) {
            this.address = address;
            return this;
        }

        /**
         * Sets the average Rating.
         *
         * @param averageRating the address to set
         */
        public Builder setAverageRating(double averageRating) {
            this.averageRating = averageRating;
            return this;
        }

        /**
         * Create a new Review Object
         */
        public Hotel build() {
            return new Hotel(this);
        }

        /**
         * Create a new Builder
         */
        public static final Builder newBuilder() {
            return new Builder();
        }

    }

}
