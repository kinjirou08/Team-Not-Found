package com.revature.gluecode;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.page.LoginPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {

	private WebDriver driver;
	private LoginPage loginPage;

	@Given("I am at the login page")
	public void i_am_at_the_login_page() {
		System.setProperty("webdriver.chrome.driver", "C:/webdrivers/chromedriver.exe");

		this.driver = new ChromeDriver();

		this.driver.get("http://localhost:4200");
		this.loginPage = new LoginPage(driver);
	}

	@When("I type in a username of {string}")
	public void i_type_in_a_username_of(String string) {
		this.loginPage.getUsernameInput().sendKeys(string);
	}

	@When("I type in a password of {string}")
	public void i_type_in_a_password_of(String string) {
		this.loginPage.getPasswordInput().sendKeys(string);
	}

	@When("I click the login button")
	public void i_click_the_login_button() {
		this.loginPage.getLoginButton().click();
	}

	@Then("I should be redirected to the admin homepage")
	public void i_should_be_redirected_to_the_admin_homepage() throws InterruptedException {

		Thread.sleep(1200);

		Assertions.assertEquals(this.driver.getCurrentUrl(), "http://localhost:4200/admin");

		driver.quit();
	}

	@Then("the customer page should show")
	public void the_customer_page_should_show() throws InterruptedException {
		Thread.sleep(1200);

		Assertions.assertEquals(this.driver.getCurrentUrl(), "http://localhost:4200/customer");

		driver.quit();
	}

}
