package hotelapp;

import hotelapp.cache.HotelCacheThreadSafe;
import hotelapp.cache.HotelCacheThreadSafeBuilder;
import hotelapp.servlet.HotelAttractionsServlet;
import hotelapp.servlet.HotelInfoServlet;
import hotelapp.servlet.HotelReviewsServlet;
import hotelapp.socket.HotelSocket;
import java.nio.file.Paths;
import org.eclipse.jetty.servlet.ServletHandler;

public class Server {

  private static final int PORTSOCKET = 7000;
  private static final int PORTSERVLET = 7050;

  static {
    HotelCacheThreadSafeBuilder builder = new HotelCacheThreadSafeBuilder(
        (HotelCacheThreadSafe) HotelCacheHelper.HOTEL_DATA);
    builder.loadHotelInfo("input/hotels.json");
    builder.loadReviews(Paths.get("input/reviews"));
  }

  public static void main(String[] args) throws Exception {
    //Start Servlet server
    new Thread(() -> {
      org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(PORTSERVLET);
      ServletHandler handler = new ServletHandler();
      handler.addServletWithMapping(HotelInfoServlet.class, "/hotelInfo");
      handler.addServletWithMapping(HotelReviewsServlet.class, "/reviews");
      handler.addServletWithMapping(HotelAttractionsServlet.class, "/attractions");
      server.setHandler(handler);
      try {
        server.start();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    //Start socket server
    new HotelSocket().startServer(PORTSOCKET);
  }
}
