import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class CoffeShopCoverageTest {

    @Test
    void coverAllDiscountPaths() {

        CoffeShopEngine.OrderHandler h =
                new CoffeShopEngine.OrderHandler();

        assertEquals(
                150000,
                h.calculateTotal(10, 20000, true, "")
        );

        assertEquals(
                180000,
                h.calculateTotal(10, 20000, false, "")
        );

        assertEquals(
                85000,
                h.calculateTotal(5, 20000, true, "")
        );

        assertEquals(
                95000,
                h.calculateTotal(5, 20000, false, "")
        );

        assertEquals(
                19600,
                h.calculateTotal(1, 20000, true, "")
        );

        assertEquals(
                20000,
                h.calculateTotal(1, 20000, false, "")
        );

        assertEquals(
                19000,
                h.calculateTotal(1, 20000, false, "JAVACOFFEE")
        );

        assertEquals(
                140000,
                h.calculateTotal(10, 20000, true, "JAVACOFFEE")
        );
    }

    @Test
    void coverAdditionalBranches() {

        CoffeShopEngine.OrderHandler h =
                new CoffeShopEngine.OrderHandler();

        h.calculateTotal(4, 10000, true, "");
        h.calculateTotal(4, 10000, false, "");

        h.calculateTotal(9, 10000, true, "");
        h.calculateTotal(9, 10000, false, "");

        h.calculateTotal(11, 10000, true, "");
        h.calculateTotal(11, 10000, false, "");

        h.calculateTotal(1, 10000, false, "SALAH");
        h.calculateTotal(0, 10000, false, "");
    }

    @Test
    void coverHandleMethodPOST() throws Exception {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(9090),
                        0
                );

        server.createContext(
                "/api/order",
                new CoffeShopEngine.OrderHandler()
        );

        server.start();

        try {

            URL url =
                    new URL(
                            "http://localhost:9090/api/order"
                    );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

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

            OutputStream os =
                    conn.getOutputStream();

            os.write(json.getBytes());
            os.flush();
            os.close();

            assertEquals(
                    200,
                    conn.getResponseCode()
            );

        } finally {

            server.stop(0);

        }
    }

    @Test
    void coverHandleMethodGET() throws Exception {

        HttpServer server =
                HttpServer.create(
                        new InetSocketAddress(9091),
                        0
                );

        server.createContext(
                "/api/order",
                new CoffeShopEngine.OrderHandler()
        );

        server.start();

        try {

            URL url =
                    new URL(
                            "http://localhost:9091/api/order"
                    );

            HttpURLConnection conn =
                    (HttpURLConnection)
                            url.openConnection();

            conn.setRequestMethod("GET");

            assertEquals(
                    405,
                    conn.getResponseCode()
            );

        } finally {

            server.stop(0);

        }
    }
}