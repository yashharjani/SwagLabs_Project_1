package com.qa.atliq.practical.pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	
//	Elements locators
	
	@FindBy(css = "a#logout_sidebar_link")
	WebElement logoutButton;
	
	@FindBy(id = "react-burger-menu-btn")
	WebElement navbarButton;
	
	@FindBy(id = "logout_sidebar_link")
	WebElement navbarElement;
	
	@FindBy(css = "span.shopping_cart_badge")
	WebElement cartCount;
	
	@FindBy(css = "div.inventory_item")
	List<WebElement> itemLists;
	
	@FindBy(xpath = "//button[@class='btn btn_secondary btn_small cart_button']")
	List<WebElement> removeButtons;
	
	@FindBy(css = "div.inventory_item_name")
	List<WebElement> itemNames;
	
	@FindBy(xpath = "//div[@class='inventory_item_price']")
	List<WebElement> itemPrices;
	
	@FindBy(css = "a.shopping_cart_link")
	WebElement cartButton;
	
	@FindBy(xpath = "//div[@class='cart_item']")
	List<WebElement> totalItemsInCart;
	
	@FindBy(id = "checkout")
	WebElement checkoutButton;
	
	WebDriver webDriver;
	
	public HomePage(WebDriver driver) {
		this.webDriver = driver;
		PageFactory.initElements(driver, this);
	}
	
//	Action methods
	
	public void clickNavBar() {
		navbarButton.click();
	}
	
	public void clickLogoutButton() {
		logoutButton.click();
	}
	
	public boolean checkLogoutButtonIsDisplayed() {
		if(logoutButton.isDisplayed()) {
			System.out.println("\n====THE USER HAS SUCCESSFULLY LOGGED IN====\n");
		}
		return logoutButton.isDisplayed();
	}
	
	public List<WebElement> getAllListedItems() {
		return itemLists;
	}
	
	public WebElement checkCartCountIsDisplayed() {
		return cartCount;
	}
	
	public WebElement cartCount() {
		return cartCount;
	}
	
	public List<WebElement> removeButton() {
		return removeButtons;
	}
	
	public void clickCartButton() {
		cartButton.click();
	}
	
	public List<WebElement> getItemName() {
		return itemNames;
	}
	
	public List<WebElement> getItemPrice() {
		return itemPrices;
	}
	
	public void clickCheckoutButton() {
		checkoutButton.click();
	}

}
