package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MonthlyCostPage {
    private WebDriver driver;

    public MonthlyCostPage(WebDriver driver) {
        this.driver = driver;
    }

    private By mortgageCalculator = By.xpath("//div[@data-currency-code='USD']");
    private By monthlyCostTabSelected = By.xpath("//div[contains(@class,'mcalc-toggle-selected')][contains(text(),'Monthly cost')]");

    private By mortgageAmountInput = By.xpath("//label[text()='Mortgage amount']/following-sibling::input");
    private By interestRateInput = By.xpath("//label[text()='Interest rate (%)']/following-sibling::input");
    private By mortgagePeriodDropdown = By.xpath("//div[@role='listbox'][1]");
    private String mortgagePeriodOption = "//div[contains(@class,'goog-menuitem-content')][contains(text(),'%s')]";

    private By totalMortgageCost = By.xpath("//label[text()='Total cost of mortgage']/following-sibling::div[1]");
    private By monthlyPayments = By.xpath("//label[text()='Total cost of mortgage']/following-sibling::div[2]//div");

    public boolean isMortgageCalculatorPresent() {
        return driver.findElements(mortgageCalculator).size() > 0;
    }

    public boolean isMonthlyCostTabSelected() {
        return driver.findElements(monthlyCostTabSelected).size() > 0;
    }

    public void setMortgageAmountInput(double mortgageAmount) {
        driver.findElement(mortgageAmountInput).clear();
        driver.findElement(mortgageAmountInput).sendKeys(Double.toString(mortgageAmount));
    }

    public void setInterestRate(double interestRate) {
        driver.findElement(interestRateInput).clear();
        driver.findElement(interestRateInput).sendKeys(Double.toString(interestRate));
    }

    public void setMortgagePeriodInput(int mortgagePeriod) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(mortgagePeriodDropdown));
        driver.findElement(mortgagePeriodDropdown).click();
        By mortgagePeriodOption1 = By.xpath(String.format(mortgagePeriodOption, mortgagePeriod));
        driver.findElement(mortgagePeriodOption1).click();
    }

    public String getTotalMortgageCost() {
        return driver.findElement(totalMortgageCost).getText();
    }

    public String getMonthlyPayments() {
        return driver.findElement(monthlyPayments).getText();
    }

}
