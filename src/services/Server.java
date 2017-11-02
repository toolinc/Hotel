package services;

import hotelapp.HotelData;
import hotelapp.HotelDataBuilder;
import hotelapp.ThreadSafeHotelData;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Server {

    private static final int PORTSOCKET = 7000;
    private static final int PORTSERVLET = 7050;
    private static final HotelData HOTEL_DATA = new ThreadSafeHotelData();;
    private final HotelDataBuilder builder;

    public Server() {
        builder = new HotelDataBuilder((ThreadSafeHotelData) HOTEL_DATA);
        builder.loadHotelInfo("input/hotels.json");
    }

    public static void main(String[] args) throws Exception {
        //Start Servlet server
        new Thread() {
            @Override
            public void run() {
                org.eclipse.jetty.server.Server server = new org.eclipse.jetty.server.Server(PORTSERVLET);

                ServletHandler handler = new ServletHandler();
                handler.addServletWithMapping(new ServletHolder(new HotelInfoServlet()), "/messageBoard");

                server.setHandler(handler);
                try {
                    server.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        //Start socket server
        new HotelSocket().startServer(PORTSOCKET);
    }
}
