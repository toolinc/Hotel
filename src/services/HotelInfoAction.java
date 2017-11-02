package services;

import hotelapp.Address;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelInfoAction implements HotelAction {

//    @Override
//    public String doQuery(String hId) {
//        String hotelName = hotelName = Server.HOTEL_DATA.getHotelName(hId);
//        StringBuffer sb = new StringBuffer("{" + System.lineSeparator() + "\"success\":");
//        System.out.println("hotelName " + hotelName);
//        if (!hotelName.equals("")) {
//            Address address = Server.HOTEL_DATA.getAddress(hId);
//            sb.append("true," + System.lineSeparator());
//            sb.append("\"hotelId\":\"" + hId + "\"," + System.lineSeparator());
//            sb.append("\"name\":\"" + hotelName + "\"," + System.lineSeparator());
//            sb.append("\"addr\":\"" + address.getStreetAddress() + "\"," + System.lineSeparator());
//            sb.append("\"city\":\"" + address.getCity() + "\"," + System.lineSeparator());
//            sb.append("\"state\":\"" + address.getState() + "\"," + System.lineSeparator());
//            sb.append("\"lat\":\"" + address.getLat() + "\"," + System.lineSeparator());
//            sb.append("\"lng\":\"" + address.getLon() + "\"" + System.lineSeparator());
//            sb.append("}");
//            System.out.println("Hotel: " + System.lineSeparator() + sb.toString());
//        } else {
//            sb.append("false," + System.lineSeparator());
//            sb.append("\"hotelId\":");
//            sb.append("\"invalid\":\"" + System.lineSeparator());
//            sb.append("}");
//            System.out.println("Hotel: " + System.lineSeparator() + sb.toString());
//        }
//        return sb.toString();
//    }

    @Override
    public String doQuery(String query) {
        System.out.println("get Info ");
        Pattern pattern = Pattern.compile("hotelId=(.+)");
        Matcher matcher = pattern.matcher(query);
        String hotelName = "";
        StringBuffer sb = new StringBuffer("{"+System.lineSeparator()+"\"success\":");
        while (matcher.find()) {
            String id = matcher.group(1).trim();
            System.out.println("ID "+id);
            hotelName = Server.HOTEL_DATA.getHotelName(id);
            System.out.println("hotelName "+hotelName);
            if(!hotelName.equals("")) {
                Address address = Server.HOTEL_DATA.getAddress(id);
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
                sb.append("\"hotelId\":");
                sb.append("\"invalid\":\""+System.lineSeparator());
                sb.append("}");
                System.out.println("Hotel: "+System.lineSeparator() + sb.toString());
            }
        }
        return sb.toString();
    }
}
