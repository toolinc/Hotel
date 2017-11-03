package hotelapp.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * The class stores information about one hotel review.
 * Stores the id of the review, the id of the corresponding hotel, the rating,
 * the title of the review, the text of the review, the date when the review was posted in
 * the following format: yyyy-MM-ddThh:mm:ss
 * Also stores the nickname of the user who submitted this review,
 * and whether the user recommends the hotel to others or not.
 * Implements Comparable - reviews should be compared based on the date
 * (more recent review is considered "less" that the older one).
 * If the dates are the same, compares reviews based on the user nicknames alphabetically.
 * If the user nicknames are the same, compares based on the review id.
 *
 * @author okarpenko
 */
public final class Review implements Comparable<Review> {

  public static final double MINREVIEW = 1;
  public static final double MAXREVIEW = 5;

  private final String hotelId;
  private final String reviewId;
  private final int rating;
  private final String reviewTitle;
  private final String review;
  private final boolean isRecom;
  private final Date date;
  private final String username;

  /**
   * Constructor
   *
   * @param builder - has all the information to create a Review
   */
  private Review(Builder builder) {
    this.date = builder.date;
    this.hotelId = builder.hotelId;
    this.reviewId = builder.reviewId;
    this.rating = builder.rating;
    this.reviewTitle = builder.reviewTitle;
    this.review = builder.review;
    this.isRecom = builder.isRecom;
    this.username = builder.username;
  }

  /**
   * @return the Id of the Hotel
   */
  public String getHotelId() {
    return hotelId;
  }

  /**
   * @return the review Id of the Hotel
   */
  public String getReviewId() {
    return reviewId;
  }

  /**
   * @return the rating of the Hotel
   */
  public int getRating() {
    return rating;
  }

  /**
   * @return the review Title of the Hotel
   */
  public String getReviewTitle() {
    return reviewTitle;
  }

  /**
   * @return the review text of the Hotel
   */
  public String getReview() {
    return review;
  }

  /**
   * @return if the Hotel is commendend or not
   */
  public boolean isRecom() {
    return isRecom;
  }

  /**
   * @return the date of the review of the Hotel
   */
  public Date getDate() {
    return date;
  }

  /**
   * @return the username of the review
   */
  public String getUsername() {
    return username;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(reviewId);
  }

  @Override
  public boolean equals(Object aThat) {
    if (this == aThat) {
      return true;
    }
    if (!(aThat instanceof Review)) {
      return false;
    }
    Review that = (Review) aThat;
    return Objects.equals(reviewId, that.reviewId);
  }


  /**
   * Compares this review with the review passed as a parameter based on
   * the dates (more recent date is "less" than older date).
   * If the dates are equal, it compares reviews based on the user nicknames, alphabetically.
   * If user nicknames are the same, it compares based on the review ids.
   * Note that we only care about comparing reviews for the same hotel id.
   *
   * @param other review to compare this one with
   * @return -1 if this review is "less than" the argument, 0 if equal 1 if this review is "greater"
   * than the other one
   */
  @Override
  public int compareTo(Review other) {
    int compare = other.date.compareTo(this.date);
    if (compare == 0) {
      compare = this.username.compareTo(other.username);
      if (compare == 0) {
        return this.reviewId.compareTo(other.reviewId);
      }
      return compare;
    }
    return compare;
  }

  /**
   * Return a string representation of this review. Use StringBuilder for efficiency.
   *
   * @return A string in the following format: Review by username on date Rating: rating reviewTitle
   * textOfReview Example: Review by Ben on Tue Aug 16 18:38:29 PDT 2016 Rating: 2 Very bad
   * experience Awaken by noises from top floor at 5AM. Lots of mosquitos too. <p> If the username
   * is null or empty, print "Anonymous" instead of the username
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Review by ").append(getUsername()).append(" on ").append(getDate())
        .append(System.lineSeparator());
    sb.append("Rating: ").append(getRating()).append(System.lineSeparator());
    sb.append(getReviewTitle()).append(System.lineSeparator());
    sb.append(getReview());
    return sb.toString();
  }

  public static final class Builder {

    private String hotelId;
    private String reviewId;
    private int rating;
    private String reviewTitle;
    private String review;
    private boolean isRecom;
    private Date date;
    private String username;

    /**
     * Constructor
     */
    private Builder() {
    }

    /**
     * Create a new Builder
     */
    public static final Builder newBuilder() {
      return new Builder();
    }

    /**
     * Sets the id of the hotel.
     *
     * @param hotelId the hotelId to set
     */
    public Builder setHotelId(String hotelId) {
      this.hotelId = hotelId;
      return this;
    }

    /**
     * Sets the id of the review.
     *
     * @param reviewId the reviewId to set
     */
    public Builder setReviewId(String reviewId) {
      this.reviewId = reviewId;
      return this;
    }

    /**
     * Sets the rating of the hotel.
     *
     * @param rating the rating to set
     * @throws InvalidRatingException - If the rating is out of the correct range from MINREVIEW TO
     * MAXREVIEW
     */
    public Builder setRating(int rating) throws InvalidRatingException {
      if (rating > MAXREVIEW || rating < MINREVIEW) {
        throw new InvalidRatingException("Out of the range rating");
      }
      this.rating = rating;
      return this;
    }

    /**
     * Sets the title of the review.
     *
     * @param reviewTitle the reviewTitle to set
     */
    public Builder setReviewTitle(String reviewTitle) {
      this.reviewTitle = reviewTitle;
      return this;
    }

    /**
     * Sets the review of the hotel.
     *
     * @param review the review to set
     */
    public Builder setReview(String review) {
      this.review = review;
      return this;
    }

    /**
     * Sets the recomendation of the hotel.
     *
     * @param recom the isRecom to set
     */
    public Builder setRecom(boolean recom) {
      isRecom = recom;
      return this;
    }

    /**
     * Sets the date of the review.
     *
     * @param date the date to set
     * @throws ParseException - If date is not valid.
     */
    public Builder setDate(String date) throws ParseException {
      final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
      this.date = format.parse(date);
      return this;
    }

    /**
     * Sets the username of the review.
     *
     * @param username of the review
     */
    public Builder setUsername(String username) {
      this.username = username.equals("") ? "Anonymous" : username;
      return this;
    }

    /**
     * Create a new Review Object
     */
    public Review build() {
      return new Review(this);
    }
  }
}
