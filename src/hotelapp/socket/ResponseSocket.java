package hotelapp.socket;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public final class ResponseSocket {


    public String doResponseHotelInfo(String hotelInfo) {
        StringBuffer sb = new StringBuffer("HTTP/1.1 ");
        if (hotelInfo.contains("true")) {
            sb.append(HttpServletResponse.SC_OK + " OK " + System.lineSeparator());
        } else {
            if (hotelInfo.contains("NOT_FOUND")) {
                sb.append(HttpServletResponse.SC_NOT_FOUND + " Not Found " + System.lineSeparator());
                hotelInfo = "";
            } else if (hotelInfo.contains("SC_METHOD_NOT_ALLOWED")) {
                sb.append(HttpServletResponse.SC_METHOD_NOT_ALLOWED + " Method not allowed " + System.lineSeparator());
                hotelInfo = "";
            } else {
                sb.append(HttpServletResponse.SC_BAD_REQUEST + " Bad Request" + System.lineSeparator());
            }
        }
        sb.append("Date: " + new Date().toString() + System.lineSeparator());
        sb.append("Server: Apache/0.8.4" + System.lineSeparator());
        sb.append("Content-Type: application/json; charset=UTF-8" + System.lineSeparator());
        sb.append("Content-Length: " + (hotelInfo.length() + 1) + System.lineSeparator());
        sb.append("Connection: Close" + System.lineSeparator());
        sb.append(System.lineSeparator() + hotelInfo);
        return sb.toString();
    }
}
