package com.qa.atliq.practical.step.definitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.annotations.Test;

import com.qa.atliq.practical.pages.CheckoutCompletePage;
import com.qa.atliq.practical.pages.CheckoutPage;
import com.qa.atliq.practical.pages.HomePage;
import com.qa.atliq.practical.pages.LoginPage;

import io.cucumber.java.en.*;

public class SauceDemo {
	
	public static WebDriver driver;
	LoginPage loginPage;
	HomePage homePage;
	CheckoutPage checkoutPage;
	CheckoutCompletePage checkoutCompletePage;
	ArrayList<String> tabs;

	private void iWaitFor(long waitTime) throws InterruptedException {
		Thread.sleep(waitTime);
	}

	@Test
	@When("^I wait for (\\d+) seconds")
	public void waitForMillis(long nbSeconds) throws InterruptedException {
		Thread.sleep(nbSeconds*1000);
	}

	@Test
	@When("I am on {string} url")
	public void userIsOnTheGivenUrl(String url) {
		driver.get(url);
	}

	@Test
	@When("^I enter (.*?) and (.*?) as username and password$")
	public void userEntersUsernameAndPasswordInTheField(String username, String password) throws InterruptedException {
		String usernameWithoutQuotes = username.replace("\"", "");
		String passwordWithoutQuotes = password.replace("\"", "");
		loginPage = new LoginPage(driver);
		loginPage.inputUsername(usernameWithoutQuotes);
		loginPage.inputPassword(passwordWithoutQuotes);
	}

	@Test
	@When("I click on Login button")
	public void userClicksOnLoginButton() throws InterruptedException {
		loginPage.clickLoginButton();
	}

	@Test
	@Then("I validate that user has logged in")
	public void validateThatUserHasLoggedIn() throws InterruptedException {
		homePage = new HomePage(driver);
		clickNavBar();
		iWaitFor(500);
		boolean isLogoutVisible = homePage.checkLogoutButtonIsDisplayed();
		Assert.assertEquals(isLogoutVisible, true);
	}

	private void clickNavBar() {
		homePage.clickNavBar();
	}
	
	@Test
	@Then("I validate the error message {string}")
	public void validateTheErrorMessages(String error_msg) {
		loginPage = new LoginPage(driver);
		String msg = loginPage.getErrorMessage();
		if (msg.equals(error_msg)) {
			System.out.print("\n====THE ERROR MESSAGE IS CORRECT=====\n");
		}
	}

	@Test
	@When("^I add items to the cart$")
	public void addItemsToTheCart(Map<String, String> itemsToAdd) throws InterruptedException {
		if (!itemsToAdd.isEmpty()) {
			itemsToAdd.values().forEach(itemLabel -> clickAddToCartButtonForItem(itemLabel));
			countNumberOfItemsExpected(itemsToAdd);
		} else {
			Assert.fail("No items provided for cart. Test scenario failed.");
		}
	}

	public int countNumberOfItemsExpected(Map<String, String> cartItems) {
		return cartItems.size();
	}

	private void clickAddToCartButtonForItem(String itemLabelIN) {
		String buttonXPath = getAddToCartButtonXPathFromItemLabel(itemLabelIN);
		driver.findElement(By.xpath(buttonXPath)).click();
		try {
			iWaitFor(750);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getAddToCartButtonXPathFromItemLabel(String labelIN) {
		return "//div[@class='inventory_item_name' and text()='" + labelIN + "']/following::button[text()='Add to cart'][1]";
	}

	@Test
	@Then("cart count should be displayed")
	public void cartCountShouldBeDisplayed() {
		homePage = new HomePage(driver);
		WebElement checkCount = homePage.checkCartCountIsDisplayed();
		boolean isDisplayed = checkCount.isDisplayed();
		Assert.assertEquals(isDisplayed, true);
	}

	@Test
	@Then("^I verify the (.*)$")
	public void verifyTheDetails(String verificationDetails, Map<String, String> userInputsForVerification) {
		if (verificationDetails.toLowerCase().matches(".*(count|counts).*")) {
			verifyCartCount(userInputsForVerification);
		}
		else if(verificationDetails.toLowerCase().matches(".*(name|names).*")) {
			verifyProductName(userInputsForVerification);
		}
		else if(verificationDetails.toLowerCase().matches(".*(total|totals).*")) {
			verifyItemTotalPrice(userInputsForVerification);
		}
		else if(verificationDetails.toLowerCase().matches(".*(completion|complete|thank|thankyou).*")) {
			verifyCompletionMessage(userInputsForVerification);
		}
	}

	private void verifyCartCount(Map<String, String> expectedCartCount) {
		homePage = new HomePage(driver);
		String expectedCount = cartCountKey(expectedCartCount);
		String countValue = homePage.cartCount().getText();
		Collection<String> expectedCountCollection = Collections.singletonList(expectedCount);
		Collection<String> actualCountCollection = Collections.singletonList(countValue);
		System.out.println("<< EXPECTED >>"+expectedCountCollection);
		System.out.println("<< ACTUAL >>"+actualCountCollection);
		Assert.assertEquals(expectedCountCollection, actualCountCollection);
	}

	private String cartCountKey(Map<String, String> cartItemsKey) {
		Set<String> keys = cartItemsKey.keySet();
		String cartCountKey = keys.iterator().next();
		String expectedCountValue = cartItemsKey.get(cartCountKey);
		System.out.print("First name: " + cartCountKey);
		return expectedCountValue;
	}

	private void verifyProductName(Map<String, String> userGivenProductName) {
		List<WebElement> productName = homePage.getItemName();
		int itemsDisplayed = productName.size();
		for(int i=0;i<itemsDisplayed;i++) {
			int index=i+1;
			String  nameKey = itemNameKey(userGivenProductName,index);
			String actualCartItemName = productName.get(i).getText();
			Collection<String> expectedNameCollection = Collections.singletonList(nameKey);
			Collection<String> actualNameCollection = Collections.singletonList(actualCartItemName);
			Assert.assertEquals(expectedNameCollection, actualNameCollection);
		}
	}

	private String itemNameKey(Map<String, String> cartItemsKey, int index) {
		String productNameKey = "productName" + index;
		return cartItemsKey.get(productNameKey);
	}
	
	private void verifyItemTotalPrice(Map<String, String> itemTotalCost) {
		checkoutPage = new CheckoutPage(driver);
		List<WebElement> productPrices = checkoutPage.getItemPrices();
		int countTotalItem = productPrices.size();
		double totalPrice = 0;
		for (int i = 0; i < countTotalItem; i++) {
			String originalPriceString = productPrices.get(i).getText();
			String priceWithoutDollarSign = originalPriceString.replace("$", "");
			double productPrice = Double.valueOf(priceWithoutDollarSign);
			totalPrice = totalPrice + productPrice;
		}
		String actualTotalPrice = "$" + totalPrice;
		System.out.println("<< Actual Total Price >>" + actualTotalPrice);
		String expectedItemTotal = getUserItemTotalInput(itemTotalCost);
		Assert.assertEquals(expectedItemTotal, actualTotalPrice);
	}
	
	private String getUserItemTotalInput(Map<String, String> itemTotalByUser) {
		String expectedTotalKey = null;
		for(String userInput : itemTotalByUser.keySet()) {
			expectedTotalKey = itemTotalByUser.get(userInput);
		}
		return expectedTotalKey;
	}

	private void verifyCompletionMessage(Map<String, String> completionMsg) {
		checkoutCompletePage = new CheckoutCompletePage(driver);
		String actualCompletionMsg = checkoutCompletePage.getCompletionMessage().toLowerCase();
		for(String thankyouMessageKey : completionMsg.keySet()) {
			String expectedCompletionMsg = completionMsg.get(thankyouMessageKey).toLowerCase();
			Assert.assertEquals(expectedCompletionMsg, actualCompletionMsg);
		}
	}

	@Test
	@When("I check the cart items by clicking on the cart button")
	public void clickOnCartButton() throws InterruptedException {
		homePage = new HomePage(driver);
		homePage.clickCartButton();
	}
	
	@Test
	@When("I click on checkout button")
	public void userClicksOnCheckoutButton() throws InterruptedException {
		homePage = new HomePage(driver);
		homePage.clickCheckoutButton();
	}
	
	@Test
	@When("I enter checkout details")
	public void userEntersCheckoutDetails(Map<String, String> checkoutDetails) throws InterruptedException {
		checkoutPage = new CheckoutPage(driver);
		for (String userInfo : checkoutDetails.keySet()) { 
			String userInput = checkoutDetails.get(userInfo);
// userInput != null is written because while calling method isEmpty on a null object, it would result in a NullPointerException.
			if (userInfo.isEmpty() || (userInput != null && userInput.isEmpty())) {
				Assert.fail("Empty key or value found in checkout details. Test scenario failed.");
			}
			enterDetails(userInfo, userInput);
		}
	}

	private void enterDetails(String userDetails, String userInput) throws InterruptedException {
		if (userInput != null) {
			getXpathOfFields(userDetails).sendKeys(userInput);
			iWaitFor(500);
		} else {
			Assert.fail("Null value found in checkout details. Test scenario failed.");
		}
	}

	private WebElement getXpathOfFields(String userDetails) {
		return driver.findElement(By.xpath("//input[@name='" + userDetails + "']"));
	}

	@Test
	@When("I click on continue button")
	public void clicksOnContinueButton() throws InterruptedException {
		checkoutPage.clickContinueButton();
		iWaitFor(1000);
	}

	@Test
	@Then("I verify checkout title is displayed")
	public void verifyCheckoutTitleIsDisplayed() {
		Assert.assertEquals(checkoutPage.checkoutTitleIsDisplayed(), true);
	}
	
	@Test
	@When("I click on finish button")
	public void clickOnFinishButton() {
		checkoutPage = new CheckoutPage(driver);
		checkoutPage.clickFinishButton();
	}
	
	@Test
	@Then("I verify that the checkout title is displayed after placing an order")
	public void verifyCheckoutTitleDisplayedAfterPlacingOrder() {
		checkoutCompletePage = new CheckoutCompletePage(driver);
		Assert.assertEquals(checkoutCompletePage.checkoutCompleteTitleIsDisplayed(), true);
	}
	
	@Test
	@When("I open a new tab in the browser")
	public void openNewTab() {
		driver.switchTo().newWindow(WindowType.TAB);
	}
	
	@Test
	@When("I go back to previous tab")
	public void goBackToPreviousTab() {
		tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(0));
	}
	
	@Test
	@When("I click on nav bar")
	public void clickNavigationBar() {
		homePage = new HomePage(driver);
		clickNavBar();
	}
	
	@Test
	@When("I click on Logout button")
	public void clickLogoutButton() {
		homePage.clickLogoutButton();
	}
	
	@Test
	@Then("I verify Login button is visible")
	public void verifyLoginIsVisible() {
		loginPage = new LoginPage(driver);
		boolean isLoginVisible = loginPage.isLoginButtonVisible();
		Assert.assertEquals(isLoginVisible, true);
	}
	
	@Test
	@When("I click on remove button to remove items from the cart")
	public void removeItemsFromTheCart() {
		homePage = new HomePage(driver);
		List<WebElement> removeButtons = homePage.removeButton();
		removeButtons.forEach(removeItem -> {
			try {
				clickRemoveButtonForItem(removeItem);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void clickRemoveButtonForItem(WebElement removeItem) throws InterruptedException {
		removeItem.click();
		iWaitFor(750);
	}

}
