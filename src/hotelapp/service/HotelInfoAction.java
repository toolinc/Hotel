package hotelapp.service;

import hotelapp.HotelCacheHelper;
import hotelapp.model.Address;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelInfoAction implements HotelAction {

    @Override
    public String doQuery(String query) {

        System.out.println("get Info " + query);

        Pattern pattern = Pattern.compile("hotelId=(\\d+)");
        Matcher matcher = pattern.matcher(query);
        String hotelName = "";
        String id = "";
        StringBuffer sb = new StringBuffer("{" + System.lineSeparator() + "\"success\":");
        while (matcher.find()) {
            id = matcher.group(1).trim();
            hotelName = HotelCacheHelper.HOTEL_DATA.getHotelName(id);
        }

        System.out.println("id " + id);
        System.out.println("name " + HotelCacheHelper.HOTEL_DATA.getHotelName(id));

        if (!hotelName.equals("")) {
            Address address = HotelCacheHelper.HOTEL_DATA.getAddress(id);
            sb.append("true," + System.lineSeparator());
            sb.append("\"hotelId\":\"" + id + "\"," + System.lineSeparator());
            sb.append("\"name\":\"" + hotelName + "\"," + System.lineSeparator());
            sb.append("\"addr\":\"" + address.getStreetAddress() + "\"," + System.lineSeparator());
            sb.append("\"city\":\"" + address.getCity() + "\"," + System.lineSeparator());
            sb.append("\"state\":\"" + address.getState() + "\"," + System.lineSeparator());
            sb.append("\"lat\":\"" + address.getLat() + "\"," + System.lineSeparator());
            sb.append("\"lng\":\"" + address.getLon() + "\"" + System.lineSeparator());
            sb.append("}");
        } else {
            sb.append("false," + System.lineSeparator());
            sb.append("\"hotelId\":");
            sb.append("\"invalid\"" + System.lineSeparator());
            sb.append("}");
        }
        return sb.toString();
    }
}
