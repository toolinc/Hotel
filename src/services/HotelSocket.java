package services;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HotelSocket {

    private static final int PORT = 8000;
    private volatile boolean isShutdown = false;

    public static void main(String[] args) {
        new HotelSocket().startServer();
    }

    public void startServer() {
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

    private class ClientTask implements Runnable {
        private final Socket connectionSocket;
        

        private ClientTask(Socket connectionSocket) {
            this.connectionSocket = connectionSocket;

        }

        @Override
        public void run() {
            System.out.println("A client connected.");

            PrintWriter out = null;
            BufferedReader in = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()))) {

                out = new PrintWriter(connectionSocket.getOutputStream(), true);


            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                    if (connectionSocket != null) {
                        connectionSocket.close();
                    }

                } catch (IOException e) {
                    System.out.println("Can't close the socket : " + e);
                }
                System.out.println("Exit");
            }
        }
    }
}
