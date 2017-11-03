package services;

import hotelapp.Review;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReviewInfoAction implements HotelAction {
    @Override
    public String doQuery(String query) {
        Pattern pattern = Pattern.compile("hotelId=(.+)&num=(\\d+)");
        Matcher matcher = pattern.matcher(query);
        StringBuffer sb = new StringBuffer("{" + System.lineSeparator() + "\"success\":");
        String id = "";
        int numReviews = 0;
        while (matcher.find()) {
            id = matcher.group(1);
            numReviews = Integer.valueOf(matcher.group(2));
        }
        TreeSet<Review> reviews = Server.HOTEL_DATA.getReviews(id);
        if (reviews != null && numReviews != 0 && numReviews < reviews.size()) {
            sb.append("true," + System.lineSeparator());
            sb.append("\"hotelId\":\"" + id + "\"," + System.lineSeparator());
            sb.append("\"reviews\": [");
            Iterator<Review> iterator = reviews.iterator();
            int count = 0;
            while (iterator.hasNext() && count < numReviews) {
                Review review = iterator.next();
                sb.append(System.lineSeparator() + "{" + System.lineSeparator());
                sb.append("\"reviewId\": \"" + review.getReviewId() + "\"," + System.lineSeparator());
                sb.append("\"title\": \"" + review.getReviewTitle() + "\"," + System.lineSeparator());
                sb.append("\"user\":\"" + review.getUsername() + "\"," + System.lineSeparator());
                sb.append("\"\"reviewText\":\"" + review.getReview() + "\"," + System.lineSeparator());
                sb.append("\"date\":\"" + review.getDate() + "\"," + System.lineSeparator());
                count++;
                if (count == numReviews) {
                    sb.append("}" + System.lineSeparator());
                } else {
                    sb.append("},");
                }
            }
            sb.append("]" + System.lineSeparator() + "}");
        } else {
            sb.append("false," + System.lineSeparator());
            sb.append("\"hotelId\":");
            sb.append("\"invalid\":\"" + System.lineSeparator());
            sb.append("}");
        }
        return sb.toString();
    }
}
