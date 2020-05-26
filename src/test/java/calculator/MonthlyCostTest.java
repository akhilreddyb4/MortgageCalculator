package calculator;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.text.DecimalFormat;

import pages.MaximumLoanPage;
import pages.MonthlyCostPage;

public class MonthlyCostTest extends BaseTest {

    private MonthlyCostPage monthlyCostPage;
    private MaximumLoanPage maximumLoanPage;

    @DataProvider(name = "testData-monthlyCost")
    public Object[][] testDataMonthlyCostCalculation() {
        return new Object[][]{
                {5000, 10, 30},// mortgage amount >interest rate
                {5000, 0, 20},// interest rate=0
                {0, 40, 20},//mortgage amount=0
                {0, 0, 20},//mortgage amount=0 & interest rate=0
                {-5000, -10, 20},//mortgage amount= -ve & interest rate=-ve
                {-5000, 10, 20},//mortgage amount= -ve & interest rate=+ve
                {5000, -10, 30},//mortgage amount= +ve & interest rate=-ve
                {-5000, 0, 30},//mortgage amount= -ve & interest rate=0
                {0, -20, 30},//mortgage amount= 0 & interest rate=-ve
                {1, 400, 30}//mortgage amount < interest rate

        };
    }

    @Test(dataProvider = "testData-monthlyCost")
    public void testMonthlyCostCalculation(double mortgageAmount, double annualInterestRatePercentage, int mortgagePeriodInYears) {
        monthlyCostPage = new MonthlyCostPage(driver);
        monthlyCostPage.setMortgageAmountInput(mortgageAmount);
        monthlyCostPage.setInterestRate(annualInterestRatePercentage);
        monthlyCostPage.setMortgagePeriodInput(mortgagePeriodInYears);

        String actualTotalMortgageCost = monthlyCostPage.getTotalMortgageCost();
        DecimalFormat formatter = new DecimalFormat("#,###");
        String expectedTotalMortgageCost = "$" + formatter.format(getExpectedTotalMortgageCost(mortgageAmount, annualInterestRatePercentage, mortgagePeriodInYears));
        Assert.assertEquals(actualTotalMortgageCost, expectedTotalMortgageCost, "Total Mortgage cost values are not equal");

        String actualMonthlyPayments = monthlyCostPage.getMonthlyPayments();
        String expectedMonthlyPayments = "$" + formatter.format(getExpectedMonthlyPayment(mortgageAmount, annualInterestRatePercentage, mortgagePeriodInYears));
        Assert.assertEquals(actualMonthlyPayments, expectedMonthlyPayments, "Monthly payments values are not equal");
    }

    @Test
    public void testMonthlyCostDisplay() {
        monthlyCostPage = new MonthlyCostPage(driver);
        Assert.assertTrue(monthlyCostPage.isMortgageCalculatorPresent(), "Mortgage calculator not displayed");
        Assert.assertTrue(monthlyCostPage.isMonthlyCostTabSelected(), "Monthly cost tab is not selected");
        maximumLoanPage = new MaximumLoanPage(driver);
        Assert.assertFalse(maximumLoanPage.isMaximumLoanTabSelected(), "Maximum loan tab is selected");
    }

    private long getExpectedTotalMortgageCost(double amount, double annualInterestRate, int periodInYears) {
        int periodInMonths = periodInYears * 12;
        double totalMortgageCost;
        amount = Math.abs(amount);
        annualInterestRate = Math.abs(annualInterestRate);
        double monthlyRate = annualInterestRate / (12 * 100);
        if (annualInterestRate != 0) {
            totalMortgageCost = amount * monthlyRate * periodInMonths / (1 - Math.pow(1 + monthlyRate, -periodInMonths));
        } else {
            totalMortgageCost = amount;
        }
        return Math.round(totalMortgageCost);
    }

    private long getExpectedMonthlyPayment(double amount, double annualInterestRate, int periodInYears) {
        int periodInMonths = periodInYears * 12;
        double monthlyPayment;
        amount = Math.abs(amount);
        annualInterestRate = Math.abs(annualInterestRate);
        double monthlyRate = annualInterestRate / (12 * 100);
        if (annualInterestRate != 0) {
            monthlyPayment = amount * monthlyRate * Math.pow(1 + monthlyRate, periodInMonths) / (Math.pow(1 + monthlyRate, periodInMonths) - 1);
        } else {
            monthlyPayment = amount / periodInMonths;
        }
        return Math.round(monthlyPayment);
    }
}
