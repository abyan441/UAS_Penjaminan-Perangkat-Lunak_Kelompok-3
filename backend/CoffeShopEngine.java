import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class CoffeShopEngine{

    public static void main(String[] args) throws IOException{
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/order", new OrderHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Backend berjalan di port 8080");
    }

    static class OrderHandler implements HttpHandler{
        @Override
        public void handle(HttpExchange exchange) throws IOException{
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStream is = exchange.getRequestBody();
                String requestBody = new String(is.readAllBytes(), StandardCharsets.UTF_8);

                String productId = extractValue(requestBody, "productId");
                String customerName = extractValue(requestBody, "customerName");
                boolean isMember = Boolean.parseBoolean(extractValue(requestBody, "isMember"));
                String promoCode = extractValue(requestBody, "promoCode");
                int qty = Integer.parseInt(extractValue(requestBody, "qty"));
                double price = Double.parseDouble(extractValue(requestBody, "price"));

                double finalTotal = calculateTotal(qty, price, isMember, promoCode);

                String response = "{\"status\":\"SUCCESS\", \"total\":" + finalTotal + "}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }

        private String extractValue(String json, String key){
            String searchKey = "\"" + key + "\":";
            int startIndex = json.indexOf(searchKey);
            if (startIndex == -1) return "";
            startIndex += searchKey.length();

            int endIndex = json.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = json.indexOf("}", startIndex);
            }

            String value = json.substring(startIndex, endIndex).trim();
            if (value.startsWith("\"") && value.endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }
            return value;
        }

        public double calculateTotal(int qty, double price, boolean isMember, String promoCode){
            double total = qty * price;
            double discount = 0.0;

            if (qty >= 10) {
                if (isMember) {
                    discount = 0.25;
                } else {
                    discount = 0.10;
                }
            } else if (qty >= 5) {
                if (isMember) {
                    discount = 0.15;
                } else {
                    discount = 0.05;
                }
            } else {
                if (isMember) {
                    discount = 0.02;
                }
            }

            if (promoCode.equals("JAVACOFFEE")) {
                discount += 0.05;
            }

            if (discount > 0.40) {
                discount = 0.40;
            }

            return total - (total * discount);
        }
    }
}