package hotelapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** The class stores information about one hotel review.
 *  Stores the id of the review, the id of the corresponding hotel, the rating, 
 *  the title of the review, the text of the review, the date when the review was posted in 
 *  the following format: yyyy-MM-ddThh:mm:ss
 *  Also stores the nickname of the user who submitted this review, 
 *  and whether the user recommends the hotel to others or not.
 *  Implements Comparable - reviews should be compared based on the date
 *  (more recent review is considered "less" that the older one).
 *  If the dates are the same, compares reviews based on the user nicknames alphabetically.
 *  If the user nicknames are the same, compares based on the review id.
 * @author okarpenko
 *
 */
public class Review implements Comparable<Review> {

	public static final double MINREVIEW = 1;
	public static final double MAXREVIEW = 5;

	private String hotelId;
	private String reviewId;
	private int rating;
	private String reviewTitle;
	private String review;
	private boolean isRecom;
	private Date date;
	private String username;

	/**
	 * Default constructor.
	 */
	public Review() {

	}

	/**
	 * Constructor
	 * 
	 * @param hotelId
	 *            - the id of the hotel that is being reviewed
	 * @param reviewId
	 *            = the id of the review
	 * By default, the hotel is recommended.
	 */
	public Review(String hotelId, String reviewId) {
		this.hotelId = hotelId;
		this.reviewId = reviewId;
	}

	/**
	 * Constructor
	 * 
	 * @param hotelId
	 *            - id of the hotel that is being reviewed
	 * @param reviewId
	 *            - id of the review
	 * @param rating
	 *            - integer rating from 1 to 5
	 * @param reviewTitle
	 *            - the title of the review
	 * @param review
	 *            - text of the review.
	 * @param isRecom
	 *            - boolean, whether the user recommends it or not
	 * @param date
	 *            - date of the review in the format yyyy-MM-ddThh:mm:ss
	 * @param username
	 *            - the nickname of the user writing the review. If empty, save it as  "Anonymous"
	 * @throws ParseException
	 *             - If date is not valid.
	 * @throws InvalidRatingException
	 * 			   - If the rating is out of the correct range from MINREVIEW TO MAXREVIEW
	 */
	public Review(String hotelId, String reviewId, int rating, String reviewTitle, String review, boolean isRecom,
			String date, String username) throws ParseException, InvalidRatingException {
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		if(rating > MAXREVIEW || rating < MINREVIEW) throw new InvalidRatingException("Out of the range rating");
		this.date = format.parse(date);
		this.hotelId = hotelId;
		this.reviewId = reviewId;
		this.rating = rating;
		this.reviewTitle = reviewTitle;
		this.review = review;
		this.isRecom = isRecom;
		this.username = username.equals("") ? "Anonymous" : username;
	}


	/**
	 * @return the Id of the Hotel
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * Sets the id of the hotel.
	 * @param hotelId the hotelId to set
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**
	 * @return the review Id of the Hotel
	 */
	public String getReviewId() {
		return reviewId;
	}

	/**
	 * Sets the id of the review.
	 * @param reviewId the reviewId to set
	 */
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}

	/**
	 * @return the rating of the Hotel
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * Sets the rating of the hotel.
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the review Title of the Hotel
	 */
	public String getReviewTitle() {
		return reviewTitle;
	}

	/**
	 * Sets the title of the review.
	 * @param reviewTitle the reviewTitle to set
	 */
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	/**
	 * @return the review text of the Hotel
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Sets the review of the hotel.
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * @return if the Hotel is commendend or not
	 */
	public boolean isRecom() {
		return isRecom;
	}

	/**
	 * Sets the recomendation of the hotel.
	 * @param recom the isRecom to set
	 */
	public void setRecom(boolean recom) {
		isRecom = recom;
	}

	/**
	 * @return the date of the review of the Hotel
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date of the review.
	 * @param date the date to set
	 */
	public void setDate(String date) throws ParseException {
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		this.date = format.parse(date);
	}

	/**
	 * @return the username of the review
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the user of the review.
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/** Compares this review with the review passed as a parameter based on
	 *  the dates (more recent date is "less" than older date).
	 *  If the dates are equal, it compares reviews based on the user nicknames, alphabetically.
	 *  If user nicknames are the same, it compares based on the review ids.
	 *  Note that we only care about comparing reviews for the same hotel id.
	 *  @param other review to compare this one with
	 *  @return 
	 *  	-1 if this review is "less than" the argument,
	 *       0 if equal
	 *  	 1 if this review is "greater" than the other one
	 */
	@Override
	public int compareTo(Review other) {
		int compare = other.date.compareTo(this.date);
		if(compare == 0){
			compare = this.username.compareTo(other.username);
			if(compare == 0){
				return this.reviewId.compareTo(other.reviewId);
			}
			return compare;
		}
		return compare;
	}

	/** Return a string representation of this review. Use StringBuilder for efficiency.
	 * @return A string in the following format: 
	 				Review by username on date
					Rating: rating
	 				reviewTitle
	 				textOfReview
	 * Example:
	 				Review by Ben on Tue Aug 16 18:38:29 PDT 2016
					Rating: 2
					Very bad experience 
					Awaken by noises from top floor at 5AM. Lots of mosquitos too.
					
	 * If the username is null or empty, print "Anonymous" instead of the username
	 */
	public String toString() {
		StringBuilder sb= new StringBuilder();
		sb.append("Review by ").append(getUsername()).append(" on ").append(getDate()).append(System.lineSeparator());
		sb.append("Rating: ").append(getRating()).append(System.lineSeparator());
		sb.append(getReviewTitle()).append(System.lineSeparator());
		sb.append(getReview());
		return sb.toString();
	}
}
