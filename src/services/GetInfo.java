package services;

import hotelapp.Address;
import hotelapp.HotelData;
import hotelapp.HotelDataBuilder;
import hotelapp.ThreadSafeHotelData;
import org.apache.logging.log4j.message.MapMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetInfo {
    private static final HotelData hdata  = new ThreadSafeHotelData();;
    private final HotelDataBuilder builder;

    public GetInfo() {
        builder = new HotelDataBuilder((ThreadSafeHotelData) hdata);
        builder.loadHotelInfo("input/hotels.json");
//        builder.loadReviews(Paths.get("input/reviews"));
    }

    public String getHotelInfo(String query){
        Pattern pattern = Pattern.compile("hotelId=(.+)");
        Matcher matcher = pattern.matcher(query);
        String hotelName = "";
        StringBuffer sb = new StringBuffer("{"+System.lineSeparator()+"\"success\":");
        while (matcher.find()){

            String id = matcher.group(1).trim();
            hotelName = hdata.getHotelName(id);

            if(!hotelName.equals("")) {
                Address address = hdata.getAddress(id);

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
            } else{
                sb.append("false,"+System.lineSeparator());
                sb.append("\"invalid\":\""+System.lineSeparator());
                System.out.println("Hotel: "+System.lineSeparator() + sb.toString());
            }
        }
        return sb.toString();
    }

    public void getReviews(String query){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("success",false);
        jsonArray.add(jsonObject);


    }

    public void getAttractions(String query){

    }
}
