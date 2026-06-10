import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoffeShopEngineTest {

    @Test
    void testQty10Member() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        10,
                        20000,
                        true,
                        ""
                );

        assertEquals(150000, result);
    }

    @Test
    void testQty10NonMember() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        10,
                        20000,
                        false,
                        ""
                );

        assertEquals(180000, result);
    }

    @Test
    void testQty5Member() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        5,
                        20000,
                        true,
                        ""
                );

        assertEquals(85000, result);
    }

    @Test
    void testQty5NonMember() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        5,
                        20000,
                        false,
                        ""
                );

        assertEquals(95000, result);
    }

    @Test
    void testQty1Member() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        1,
                        20000,
                        true,
                        ""
                );

        assertEquals(19600, result);
    }

    @Test
    void testQty1NonMember() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        1,
                        20000,
                        false,
                        ""
                );

        assertEquals(20000, result);
    }

    @Test
    void testPromoOnly() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        1,
                        20000,
                        false,
                        "JAVACOFFEE"
                );

        assertEquals(19000, result);
    }

    @Test
    void testMemberAndPromo() {
        CoffeShopEngine.OrderHandler handler =
                new CoffeShopEngine.OrderHandler();

        double result =
                handler.calculateTotal(
                        10,
                        20000,
                        true,
                        "JAVACOFFEE"
                );

        assertEquals(140000, result);
    }
}