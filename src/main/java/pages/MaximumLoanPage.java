package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MaximumLoanPage {
    private WebDriver driver;

    public MaximumLoanPage(WebDriver driver) {
        this.driver = driver;
    }

    private By maximumLoanTab = By.xpath("//div[text()='Maximum loan']");
    private By maximumLoanTabSelected = By.xpath("//div[contains(@class,'mcalc-toggle-selected')][contains(text(),'Maximum loan')]");

    private By monthlyPaymentsInput = By.xpath("//label[text()='Monthly payments']/following-sibling::input");
    private By interestRateInput = By.xpath("//label[text()='Interest rate (%)']/following-sibling::input");
    private By mortgagePeriodDropdown = By.xpath("//div[@role='listbox'][1]");
    private String mortgagePeriodOption = "//div[contains(@class,'goog-menuitem-content')][contains(text(),'%s')]";

    private By totalMortgageCost = By.xpath("//label[text()='Total cost of mortgage']/following-sibling::div[1]");
    private By borrowAmount = By.xpath("//label[text()='You could borrow']/following-sibling::div[1]");

    public void clickOnMaximumLoanTab() {
        driver.findElement(maximumLoanTab).click();
    }

    public boolean isMaximumLoanTabSelected() {
        return driver.findElements(maximumLoanTabSelected).size() > 0;
    }

    public void setMonthlyPaymentsInput(double mortgageAmount) {
        driver.findElement(monthlyPaymentsInput).clear();
        driver.findElement(monthlyPaymentsInput).sendKeys(Double.toString(mortgageAmount));
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

    public String getBorrowAmount() {
        return driver.findElement(borrowAmount).getText();
    }

}

