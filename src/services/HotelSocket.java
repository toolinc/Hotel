package services;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HotelSocket {

    private volatile boolean isShutdown = false;

    public void startServer(int PORT) {
        final ExecutorService threads = Executors.newFixedThreadPool(4);

        Runnable serverTask = new Runnable() {

            @Override
            public void run() {
                try {
                    ServerSocket welcomingSocket = new ServerSocket(PORT);
                    System.out.println("Waiting for clients to connect...");
                    while (!isShutdown) {
                        Socket clientSocket = welcomingSocket.accept();
                        threads.submit(new ClientTask(clientSocket));
                    }
                    if (isShutdown) {
                        welcomingSocket.close();
                    }
                } catch (IOException e) {
                    System.err.println("Unable to process client request");
                    e.printStackTrace();
                }
            }
        };
        Thread serverThread = new Thread(serverTask);
        serverThread.start();
    }

    private static final class ClientTask implements Runnable {
        private final Socket connectionSocket;
        private final Pattern pattern;
        private final HotelInfoAction info;


        private ClientTask(Socket connectionSocket) {
            this.connectionSocket = connectionSocket;
            pattern = Pattern.compile("^(GET|POST)\\s((.+?)\\?([^?]*))(HTTP/.+)");
            info = new HotelInfoAction();
        }

        @Override
        public void run() {
            System.out.println("A client connected.");
            PrintWriter out = null;
            BufferedReader in = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()))) {
                out = new PrintWriter(connectionSocket.getOutputStream(), true);
                String input;
                String path = "";
                String query = "";
                while (!connectionSocket.isClosed()) {
                    input = reader.readLine();
                    System.out.println("Server received: " + input); // echo the same string to the console
                    if (input.isEmpty()) {
                        System.out.println("path: " + path);
                        System.out.println("query : " + query);

                    }
                    if (input.matches("(GET|POST).+")) {
                        Matcher matcher = pattern.matcher(input);
                        while (matcher.find()) {
                            path = matcher.group(3);
                            query = matcher.group(4);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                    if (connectionSocket != null) {
                        connectionSocket.close();
                    }
                    out.close();
                    in.close();
                } catch (IOException e) {
                    System.out.println("Can't close the socket : " + e);
                }
                System.out.println("Exit");
            }
        }
    }
}
