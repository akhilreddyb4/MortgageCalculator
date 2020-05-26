package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class GoogleHomePage {
    private WebDriver driver;

    private By searchBar = By.xpath("//input[@title='Search']");

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void setSearchBar() {
        driver.findElement(searchBar).sendKeys("Google Mortgage Calculator");
        driver.findElement(searchBar).sendKeys(Keys.ENTER);
    }

}
