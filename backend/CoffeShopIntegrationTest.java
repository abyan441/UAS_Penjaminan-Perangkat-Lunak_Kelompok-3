import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CoffeShopIntegrationTest {

    @Test
    void testOrderAPI() throws Exception {

        URL url = new URL("http://localhost:8080/api/order");

        HttpURLConnection conn =
                (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        conn.setDoOutput(true);

        String json =
                "{"
                + "\"productId\":\"P01\","
                + "\"customerName\":\"Abyan\","
                + "\"isMember\":true,"
                + "\"promoCode\":\"JAVACOFFEE\","
                + "\"qty\":10,"
                + "\"price\":20000"
                + "}";

        OutputStream os = conn.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();

        Scanner scanner =
                new Scanner(conn.getInputStream());

        StringBuilder response =
                new StringBuilder();

        while(scanner.hasNextLine()){
            response.append(scanner.nextLine());
        }

        scanner.close();

        assertEquals(200, statusCode);

        assertTrue(
                response.toString()
                        .contains("\"SUCCESS\"")
        );

        assertTrue(
                response.toString()
                        .contains("140000")
        );
    }
}