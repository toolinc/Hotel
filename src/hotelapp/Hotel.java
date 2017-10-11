package hotelapp;
/** A class that represents a hotel. Stores hotelId, name, address, and averageRating.
 * Implements Comparable - the hotels are compared based on the hotel names. If the names are the same, hotels
 * are compared based on the hotelId.
 * @author okarpenko
 */
public class Hotel implements Comparable<Hotel>{

	private String hId;
	private String name;
	private Address address;
	private double averageRating;

	/**
	 * Constructor
	 * @param hId - the id of the hotel
	 * @param name - the name of the hotel
	 * address should be set to null.
	 */
	public Hotel(String hId, String name) {
		this.hId = hId;
		this.name = name;
	}

	/**
	 * Constructor
	 * @param hId - the id of the hotel
	 * @param name - the name of the hotel
	 * @param address - the address of the hotel
	 */
	public Hotel(String hId, String name, Address address) {
		this.hId = hId;
		this.name = name;
		this.address = address;
	}

	/**
	 * @return the Id of the Hotel
	 */
	public String gethId() {
		return hId;
	}

	/**
	 * Sets the id of the hotel.
	 * @param hId the hId to set
	 */
	public void sethId(String hId) {
		this.hId = hId;
	}

	/**
	 * @return the name of the Hotel
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the address of the Hotel
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the average rating of the Hotel
	 */
	public double getAverageRating() {
		return averageRating;
	}

	/**
	 * Sets the average Rating.
	 * @param averageRating the address to set
	 */
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	/** Compare hotels based on the name (alphabetically). May use compareTo method in class String.
	 * If the names are the same, compare based on the hotel ids. */
	@Override
	public int compareTo(Hotel o) {
		int compare = this.name.compareTo(o.name);
		if(compare == 0){
			return this.hId.compareTo(o.hId);
		}
		return compare;
	}
	
	/** 
	 * Returns the string representation of the hotel in the following format:
	 * hotelName: hotelID
	 * streetAddress
	 * city, state
	 * 
	 * Example: Travelodge Central San Francisco: 40682 
				1707 Market St
				San Francisco, CA
	 * 
	 * Does not include information about the reviews.
	 */
	public String toString() {
			String res = getName() + ": " + gethId() + System.lineSeparator() + getAddress();
			return res;
	}
	
}
