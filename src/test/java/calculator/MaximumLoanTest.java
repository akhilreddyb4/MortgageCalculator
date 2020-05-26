package calculator;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import java.text.DecimalFormat;
import pages.MaximumLoanPage;
import pages.MonthlyCostPage;

public class MaximumLoanTest extends BaseTest {

    private MaximumLoanPage maximumLoanPage;
    private MonthlyCostPage monthlyCostPage;

    @DataProvider(name = "testData-MaximumLoan")
    public Object[][] testDataMaximumLoanCalculation() {
        return new Object[][]{
                {450, 10, 20},// monthly payment >interest rate
                {500, 0, 30},// interest rate=0
                {0, 40, 20},//monthly payment=0
                {0, 0, 20},//monthly payment=0 & interest rate=0
                {-450, -10, 20},//monthly payment= -ve & interest rate=-ve
                {-500, 10, 20},//monthly payment= -ve & interest rate=+ve
                {6000, -10, 30},//monthly payment= +ve & interest rate=-ve
                {-800, 0, 30}, //monthly payment= -ve & interest rate=0
                {0, -30, 30}, //monthly payment= 0 & interest rate=-ve
                {1, 300, 30}//monthly payment < interest rate
        };

    }

    @Test(dataProvider = "testData-MaximumLoan")
    public void testMaximumLoanCalculation(double monthlyPayment, double annualInterestRatePercentage, int mortgagePeriodInYears) {
        maximumLoanPage = new MaximumLoanPage(driver);
        maximumLoanPage.clickOnMaximumLoanTab();
        maximumLoanPage.setMonthlyPaymentsInput(monthlyPayment);
        maximumLoanPage.setInterestRate(annualInterestRatePercentage);
        maximumLoanPage.setMortgagePeriodInput(mortgagePeriodInYears);

        String actualMortgageValue = maximumLoanPage.getTotalMortgageCost();
        DecimalFormat formatter = new DecimalFormat("#,###");
        String expectedMortgageValue = "$" + formatter.format(getExpectedTotalMortgageCost(monthlyPayment, mortgagePeriodInYears));
        Assert.assertEquals(actualMortgageValue,expectedMortgageValue, "Mortgage values are not equal");

        String actualBorrowAmountValue = maximumLoanPage.getBorrowAmount();
        String expectedBorrowAmountValue = "$" + formatter.format(getExpectedBorrowAmount(monthlyPayment, annualInterestRatePercentage, mortgagePeriodInYears));
        Assert.assertEquals(actualBorrowAmountValue,expectedBorrowAmountValue,"Borrow amount values are not equal");
    }

    @Test
    public void testMonthlyMaximumLoanDisplay() {
        monthlyCostPage = new MonthlyCostPage(driver);
        Assert.assertTrue(monthlyCostPage.isMortgageCalculatorPresent(), "Mortgage calculator not displayed");
        maximumLoanPage = new MaximumLoanPage(driver);
        maximumLoanPage.clickOnMaximumLoanTab();
        Assert.assertTrue(maximumLoanPage.isMaximumLoanTabSelected(),"Maximum loan tab is not selected");
        monthlyCostPage = new MonthlyCostPage(driver);
        Assert.assertFalse(monthlyCostPage.isMonthlyCostTabSelected(), "Monthly cost tab is selected");
    }

    private long getExpectedTotalMortgageCost(double monthlyPaymentAmount, int periodInYears) {
        int periodInMonths = periodInYears * 12;
        double totalMortgageCost;
        monthlyPaymentAmount = Math.abs(monthlyPaymentAmount);
        totalMortgageCost = monthlyPaymentAmount * periodInMonths;
        return Math.round(totalMortgageCost);
    }

    private long getExpectedBorrowAmount(double monthlyPaymentAmount, double annualPercentageRate, int periodInYears) {
        int periodInMonths = periodInYears * 12;
        double borrowAmount;
        monthlyPaymentAmount = Math.abs(monthlyPaymentAmount);
        annualPercentageRate = Math.abs(annualPercentageRate);
        double monthlyRate = annualPercentageRate / (12 * 100);
        if (annualPercentageRate != 0) {
            borrowAmount = monthlyPaymentAmount * ((Math.pow(1 + monthlyRate, periodInMonths)) - 1) / (monthlyRate * Math.pow(1 + monthlyRate, periodInMonths));
        } else {
            borrowAmount = monthlyPaymentAmount * periodInMonths;
        }
        return Math.round(borrowAmount);
    }

}

