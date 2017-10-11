package hotelapp;

/** The class that represents the address of a hotel in USA. Stores the following data about the address:
 * city, state, street address, latitude and longitude. 
 */
public class Address {

	private String city;
	private String state;
	private String streetAddress;
	private double lat;
	private double lon;

	/** 
	 * Constructor that takes city, state, streetAddress, latitude and longitude
	 */
	public Address(String city, String state, String streetAddress, double lat, double lon) {
		this.city = city;
		this.state = state;
		this.streetAddress = streetAddress;
		this.lat =lat;
		this.lon = lon;
	}

	/**
	 * @return the city of the Hotel
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state of the Hotel
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the street of the Hotel
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Sets the street.
	 * @param streetAddress the streetAddress to set
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * @return the latitude of the Hotel
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Sets the latitude.
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}

	/**
	 * @return the longitude of the Hotel
	 */
	public double getLon() {
		return lon;
	}

	/**
	 * Sets the Longitude.
	 * @param lon the lon to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}

	/** Return the string representing the address in the following format:
	 * street address on the first line,
	 * city, state on the second line. Example:
	 17 Green st.
	 San Francisco, CA
	 * @return string representing the address of the hotel
	 */
	public String toString() {
		String res = "";
		res += getStreetAddress() + System.lineSeparator() + getCity() + ", " + getState();
		return res;
	}
}

