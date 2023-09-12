package com.qa.saucedemo.practical.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutCompletePage {
	
	@FindBy(xpath = "//span[@class='title']")
	WebElement checkoutTitle;
	
	@FindBy(xpath = "//h2[@class='complete-header']")
	WebElement completionMessage;
	
	WebDriver driver;
	public CheckoutCompletePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean checkoutCompleteTitleIsDisplayed() {
		return checkoutTitle.isDisplayed();
	}
	
	public String getCompletionMessage() {
		return completionMessage.getText();
	}

}
