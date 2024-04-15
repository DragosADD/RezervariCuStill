package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class CustomHttpServer {
    public static void main(String[] args) throws IOException {
        int port = 8000;
        // Create an instance of HttpServer, not CustomHttpServer
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        System.out.println("Server started at " + port);

        // Register the context and handler
        server.createContext("/api/login", new LoginHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class LoginHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                handlePostRequest(exchange);
            } else {
                String response = "Not Supported";
                exchange.sendResponseHeaders(405, response.getBytes().length); // 405 Method Not Allowed
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private void handlePostRequest(HttpExchange exchange) throws IOException {
            // Assuming the request body is a simple string for demonstration
            java.util.Scanner scanner = new java.util.Scanner(exchange.getRequestBody()).useDelimiter("\\A");
            String requestBody = scanner.hasNext() ? scanner.next() : "";

            System.out.println("Received: " + requestBody);

            // Here, you would add logic to validate the user credentials
            String response = "Login successful! Received: " + requestBody;
            exchange.sendResponseHeaders(200, response.getBytes().length); // 200 OK
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
