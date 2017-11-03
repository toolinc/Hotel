package hotelapp.servlet;

import hotelapp.service.HotelInfoAction;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringEscapeUtils;

public class HotelInfoServlet extends HttpServlet {

  private HotelInfoAction action;

  public HotelInfoServlet() {
    action = new HotelInfoAction();
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
    if (hotelId == null || hotelId.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
    }
    System.out.println("Hotel Id sev " + hotelId);
    hotelId = StringEscapeUtils.escapeHtml4(hotelId); // need to "clean up" whatever
    out.println(action.doQuery("hotelId=" + hotelId));
  }
}
