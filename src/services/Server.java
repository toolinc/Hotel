package services;

import hotelapp.HotelData;
import hotelapp.HotelDataBuilder;
import hotelapp.ThreadSafeHotelData;
import org.eclipse.jetty.servlet.ServletHandler;

import java.nio.file.Paths;

public class Server {

    private static final int PORTSOCKET = 7000;
    private static final int PORTSERVLET = 7050;
    private static final HotelDataBuilder builder;
    static final HotelData HOTEL_DATA = new ThreadSafeHotelData();;

    static {
        builder = new HotelDataBuilder((ThreadSafeHotelData) HOTEL_DATA);
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
