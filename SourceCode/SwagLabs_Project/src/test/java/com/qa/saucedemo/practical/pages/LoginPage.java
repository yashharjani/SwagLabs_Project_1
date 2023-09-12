package com.qa.saucedemo.practical.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	@FindBy(name = "user-name")
	WebElement usernameInput;

	@FindBy(name = "password")
	WebElement passwordInput;

	@FindBy(css = "input#login-button[value='Login']")
	WebElement loginButton;

	@FindBy(css = "h3[data-test='error']")
	WebElement errorMsgLabel;

	WebDriver webDriver;

	public LoginPage(WebDriver webDriver) {
		this.webDriver = webDriver;
		PageFactory.initElements(webDriver, this);
	}

	public void inputUsername(String username) {
		usernameInput.sendKeys(username);
	}

	public void inputPassword(String password) {
		passwordInput.sendKeys(password);
	}

	public void clickLoginButton() {
		loginButton.click();
	}

	public String getErrorMessage() {
		return errorMsgLabel.getText();
	}
	
	public boolean isLoginButtonVisible() {
		return loginButton.isDisplayed();
	}

}
