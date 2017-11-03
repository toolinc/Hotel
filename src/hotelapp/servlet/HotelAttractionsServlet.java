package hotelapp.servlet;

import hotelapp.service.HotelAttractionAction;
import org.apache.commons.lang3.StringEscapeUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HotelAttractionsServlet extends HttpServlet {

    private HotelAttractionAction action;

    public HotelAttractionsServlet() {
        action = new HotelAttractionAction();
    }

    /**
     * A method that gets executed when the get request is sent to the
     * HelloServlet
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        String hotelId = request.getParameter("hotelId");
        String radius = request.getParameter("radius");
        if (hotelId == null || hotelId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        hotelId = StringEscapeUtils.escapeHtml4(hotelId); // need to "clean up" whatever
        String query = "";
        if (hotelId != null && radius != null) {
            query = "hotelId=" + hotelId + "&radius=" + radius;
        } else if (hotelId != null) {
            query = "hotelId=" + hotelId;
        }
        out.println(action.doQuery(query));
    }
}
