package com.qa.saucedemo.practical.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutPage {
	
	@FindBy(id = "first-name")
	WebElement firstNameInput;
	
	@FindBy(id = "last-name")
	WebElement lastNameInput;
	
	@FindBy(id = "postal-code")
	WebElement postalCodeInput;
	
	@FindBy(id = "continue")
	WebElement continueButton;
	
	@FindBy(xpath = "//span[@class='title']")
	WebElement checkoutTitle;
	
	@FindBy(xpath = "//h3[@data-test='error']")
	WebElement errorMessage;
	
	@FindBy(xpath = "//div[@class='inventory_item_price']")
	List<WebElement> itemPriceInCheckoutPage;
	
	@FindBy(xpath = "//button[@id='finish']")
	WebElement finishButton;
	
	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enterFirstName(String firstName) {
		firstNameInput.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName) {
		lastNameInput.sendKeys(lastName);
	}
	
	public void enterPostalCode(String postalCode) {
		postalCodeInput.sendKeys(postalCode);
	}
	
	public void clickContinueButton() {
		continueButton.click();
	}
	
	public boolean checkoutTitleIsDisplayed() {
		return checkoutTitle.isDisplayed();
	}
	
	public String getTheErrorMessage() {
		return errorMessage.getText();
	}
	
	public List<WebElement> getItemPrices() {
		return itemPriceInCheckoutPage;
	}
	
	public void clickFinishButton() {
		finishButton.click();
	}

}
