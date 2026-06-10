import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class CoffeShopSystemTest {

    @Test
    void testCheckoutFlow() throws Exception {

        ChromeOptions options =
                new ChromeOptions();

        String os =
                System.getProperty("os.name")
                        .toLowerCase();

        if (os.contains("win")) {

            System.setProperty(
                    "webdriver.chrome.driver",
                    "C:\\Users\\ThinkPad\\OneDrive\\Dokumen\\SEMESTER 6\\penjaminan perangkat lunak\\chromedriver-win64\\chromedriver.exe"
            );

            options.setBinary(
                    "C:\\Users\\ThinkPad\\OneDrive\\Dokumen\\SEMESTER 6\\penjaminan perangkat lunak\\chrome-win64\\chrome-win64\\chrome.exe"
            );

        } else {

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");

        }

        WebDriver driver =
                new ChromeDriver(options);

        try {

            driver.get(
                    "http://localhost:8000"
            );

            driver.findElement(
                    By.id("customerName")
            ).sendKeys("Abyan");

            Select member =
                    new Select(
                            driver.findElement(
                                    By.id("isMember")
                            )
                    );

            member.selectByValue("true");

            driver.findElement(
                    By.id("promoCode")
            ).sendKeys("JAVACOFFEE");

            driver.findElement(
                    By.cssSelector(
                            "input[value='P01']"
                    )
            ).click();

            driver.findElement(
                    By.name("qty_P01")
            ).clear();

            driver.findElement(
                    By.name("qty_P01")
            ).sendKeys("10");

            driver.findElement(
                    By.id("btnSubmit")
            ).click();

            Thread.sleep(2000);

            assertTrue(
                    driver.getPageSource()
                            .contains("Pesanan berhasil")
            );

        } finally {

            driver.quit();

        }
    }
}