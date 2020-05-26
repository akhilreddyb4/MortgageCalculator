package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.GoogleHomePage;
import java.util.concurrent.TimeUnit;

public class BaseTest {
    public WebDriver driver;

    protected GoogleHomePage googleHomePage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://google.com/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        googleHomePage = new GoogleHomePage(driver);
        googleHomePage.setSearchBar();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

}
