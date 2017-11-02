package services;

import hotelapp.Address;
import hotelapp.HotelData;
import hotelapp.HotelDataBuilder;
import hotelapp.ThreadSafeHotelData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelInfoAction implements HotelAction {
    private static final HotelData HOTEL_DATA = new ThreadSafeHotelData();;
    private final HotelDataBuilder builder;

    public HotelInfoAction() {
        builder = new HotelDataBuilder((ThreadSafeHotelData) HOTEL_DATA);
        builder.loadHotelInfo("input/hotels.json");
    }

    @Override
    public String doQuery(String query) {
        Pattern pattern = Pattern.compile("hotelId=(.+)");
        Matcher matcher = pattern.matcher(query);
        String hotelName = "";
        StringBuffer sb = new StringBuffer("{"+System.lineSeparator()+"\"success\":");
        while (matcher.find()) {
            String id = matcher.group(1).trim();
            hotelName = HOTEL_DATA.getHotelName(id);
            if(!hotelName.equals("")) {
                Address address = HOTEL_DATA.getAddress(id);
                sb.append("true,"+System.lineSeparator());
                sb.append("\"hotelId\":\""+id+"\","+System.lineSeparator());
                sb.append("\"name\":\""+hotelName+"\","+System.lineSeparator());
                sb.append("\"addr\":\""+address.getStreetAddress()+"\","+System.lineSeparator());
                sb.append("\"city\":\""+address.getCity()+"\","+System.lineSeparator());
                sb.append("\"state\":\""+address.getState()+"\","+System.lineSeparator());
                sb.append("\"lat\":\""+address.getLat()+"\","+System.lineSeparator());
                sb.append("\"lng\":\""+address.getLon()+"\""+System.lineSeparator());
                sb.append("}");
                System.out.println("Hotel: " +System.lineSeparator()+ sb.toString());
            } else {
                sb.append("false,"+System.lineSeparator());
                sb.append("\"invalid\":\""+System.lineSeparator());
                System.out.println("Hotel: "+System.lineSeparator() + sb.toString());
            }
        }
        return sb.toString();
    }
}
