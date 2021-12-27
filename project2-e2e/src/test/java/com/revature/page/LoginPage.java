package com.revature.page;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wdw;

	@FindBy(xpath = "//p[@class='control has-icons-left has-icons-right']//input[@placeholder='Username']")
	private WebElement usernameInput;

	@FindBy(xpath = "//p[@class='control has-icons-left']//input[@placeholder='Password']")
	private WebElement passwordInput;

	@FindBy(xpath = "//button[contains(text(),'Login')]")
	private WebElement loginButton;
	
	@FindBy(xpath ="//div[@id='error-message']")
	private WebElement errorMessage;
	

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.wdw = new WebDriverWait(driver, Duration.ofSeconds(15)); // wait for a maximum of 15 seconds before
																		// throwing an exception

		// PageFactory initialization
		PageFactory.initElements(driver, this);
	}

	public WebElement getUsernameInput() {
		return usernameInput;
	}

	public WebElement getPasswordInput() {
		return passwordInput;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}
	
	public WebElement getErrorMessage() {
		return this.wdw.until(ExpectedConditions.visibilityOf(this.errorMessage));
	}
	
	

}
