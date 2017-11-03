package services;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class ResponseSocket {


    public String doResponseHotelInfo(String hotelInfo){
        StringBuffer sb = new StringBuffer("HTTP/1.1 ");
        if(hotelInfo.contains("true")) {
            sb.append(HttpServletResponse.SC_OK +" OK "+ System.lineSeparator());
        } else {
            sb.append(HttpServletResponse.SC_BAD_REQUEST +" Bad Request"+ System.lineSeparator());
        }
        sb.append("Date: "+new Date().toString()+System.lineSeparator());
        sb.append("Server: Apache/0.8.4"+System.lineSeparator());
        sb.append("Content-Type: application/json; charset=UTF-8"+System.lineSeparator());
        sb.append("Content-Length: "+hotelInfo.length()+System.lineSeparator());
        sb.append("Connection: Close"+System.lineSeparator());
        sb.append(System.lineSeparator()+hotelInfo);

        System.out.println("Repsonse\n"+sb.toString());

        return sb.toString();
    }
}
