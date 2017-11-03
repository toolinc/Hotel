package hotelapp.model;

/**
 * Custom exception class for entering invalid hotel rating
 */
public final class InvalidRatingException extends Exception {

  public InvalidRatingException(String message) {
    super(message);
    System.out.println("The value of the hotel rating is out of range");
  }

}
