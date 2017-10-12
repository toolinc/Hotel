package hotelapp;


import java.util.Objects;

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
	private Address(Builder builder) {
		this.city = builder.city;
		this.state = builder.state;
		this.streetAddress = builder.streetAddress;
		this.lat = builder.lat;
		this.lon = builder.lon;
	}

	/**
	 * @return the city of the Hotel
	 */
	public String getCity() {
		return city;
	}


	/**
	 * @return the state of the Hotel
	 */
	public String getState() {
		return state;
	}


	/**
	 * @return the street of the Hotel
	 */
	public String getStreetAddress() {
		return streetAddress;
	}


	/**
	 * @return the latitude of the Hotel
	 */
	public double getLat() {
		return lat;
	}


	/**
	 * @return the longitude of the Hotel
	 */
	public double getLon() {
		return lon;
	}


	@Override
	public int hashCode() {
		return Objects.hash(streetAddress, city, state, lat, lon);
	}

	@Override
	public boolean equals(Object aThat) {
		if (this == aThat) {
			return true;
		}
		if (!(aThat instanceof Address)) {
			return false;
		}
		Address that = (Address) aThat;
		return Objects.equals(streetAddress, that.streetAddress)
				&& Objects.equals(city, that.city)
				&& Objects.equals(state, that.state)
				&& Objects.equals(lat, that.lat)
				&& Objects.equals(lon, that.lon);
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


	public static final class Builder {

		private String city;
		private String state;
		private String streetAddress;
		private double lat;
		private double lon;

		public Builder() {
		}

		public Builder setCity(String city) {
			this.city = city;
			return this;
		}

		public Builder setState(String state) {
			this.state = state;
			return this;
		}

		public Builder setStreetAddress(String streetAddress) {
			this.streetAddress = streetAddress;
			return this;
		}

		public Builder setLat(double lat) {
			this.lat = lat;
			return this;
		}

		public Builder setLon(double lon) {
			this.lon = lon;
			return this;
		}

		public Address build() {
			return new Address(this);
		}

		public static final Builder newBuilder() {
			return new Builder();
		}
	}
}

