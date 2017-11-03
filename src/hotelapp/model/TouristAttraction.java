package hotelapp.model;

public final class TouristAttraction {

    private final String id;
    private final String name;
    private final String address;
    private final double rating;

    /** Constructor for TouristAttraction
     *
     * @param builder - has id, name, rating and address
     *
     */
    private TouristAttraction(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.rating = builder.rating;
        this.address = builder.address;
    }

    /** @return the attraction Id*/
    public String getId() {
        return id;
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
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("; ");
        sb.append(address);
        return sb.toString();
    }

    public static final class Builder{
        private String id;
        private String name;
        private String address;
        private double rating;
        private double rating1;

        /**
         * Constructor
         */
        private Builder(){}


        /**
         * Sets the id of the attraction.
         *
         * @param id
         */
        public Builder setId(String id){
            this.id = id;
            return this;
        }

        /**
         * Sets the name of the attraction.
         *
         * @param name
         */
        public Builder setName(String name){
            this.name = name;
            return this;
        }

        /**
         * Sets the address of the attraction..
         *
         * @param address
         */
        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        /**
         * Sets the rating of the attraction..
         *
         * @param rating
         */
        public Builder setRating(double rating) {
            this.rating = rating;
            return this;
        }

        /**
         * Create a new Review Object
         */
        public TouristAttraction build(){
            return new TouristAttraction(this);
        }

        /**
         * Create a new Builder
         */
        public static final Builder newBuilder(){
            return new Builder();
        }

    }
}
