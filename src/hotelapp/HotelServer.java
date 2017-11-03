package hotelapp;

import hotelapp.servlet.HotelAttractionsServlet;
import hotelapp.servlet.HotelInfoServlet;
import hotelapp.servlet.HotelReviewsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public final class HotelServer {

  public static final int PORT = 8083;

  public static void main(String[] args) throws Exception {
    Server server = new Server(PORT);
    ServletHandler handler = new ServletHandler();
    handler.addServletWithMapping(HotelInfoServlet.class, "/hotelInfo");
    handler.addServletWithMapping(HotelReviewsServlet.class, "/reviews");
    handler.addServletWithMapping(HotelAttractionsServlet.class, "/attractions");
    server.setHandler(handler);
    server.start();
    server.join();
  }
}
