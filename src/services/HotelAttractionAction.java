package services;

import hotelapp.ThreadSafeHotelData;
import hotelapp.TouristAttractionFinder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelAttractionAction implements HotelAction {

    @Override
    public String doQuery(String query) {
        Pattern pattern = Pattern.compile("hotelId=(\\d+)(&radius=(\\d+))*");
        Matcher matcher = pattern.matcher(query);
        StringBuffer sb = new StringBuffer("{" + System.lineSeparator() + "\"success\":");
        String id = "";
        int radius = 0;

        while (matcher.find()) {
            id = matcher.group(1);
            if (matcher.group(3) != null) {
                radius = Integer.valueOf(matcher.group(3));
            }
        }
        if (!Server.HOTEL_DATA.getHotelName(id).equals("") && radius != 0) {
            TouristAttractionFinder attractionFinder = new TouristAttractionFinder((ThreadSafeHotelData) Server.HOTEL_DATA);
            return attractionFinder.getAttractions(id, radius);
        } else {
            sb.append("false," + System.lineSeparator());
            if (id.equals("")) {
                sb.append("\"hotelId\":");
            } else {
                sb.append("\"radius\":");
            }
            sb.append("\"invalid\"" + System.lineSeparator());
            sb.append("}");
        }
        return sb.toString();
    }
}
