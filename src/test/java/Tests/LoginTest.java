package Tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

	String baseURL= "https://www.saucedemo.com/";
	By username = By.id("user-name");
	By password = By.id("password");
	By loginButton = By.id("login-button");

	public void login(String user, String pass) {

		try { 
			driver.get(baseURL);
			driver.findElement(username).sendKeys(user);
			driver.findElement(password).sendKeys(pass);
			driver.findElement(loginButton).click();
		} catch (Exception e) {
			System.out.println("Exception during login: " + e.getMessage());
			Assert.fail("Login function encountered an exception.");
		}

	}

	@Test
	public void testValidLogin() {

		try {
			login("standard_user", "secret_sauce");
			Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"));
		} catch (Exception e) {
			System.out.println("Exception in testValidLogin:"+e.getMessage());
		}
	}
	
	@Test
	public void testBlankUsername() {
		try {
			login("", "secret_sauce");
			WebElement error= driver.findElement(By.className("error-message-container"));
			Assert.assertTrue(error.getText().contains("Username is required"));
			
		} catch (Exception e) {
			System.out.println("Exception in testBlankUsername:" +e.getMessage());
		}
	}

	@Test
	public void testInvalidUsername() {
		try {
			login("invalid-user", "secret_sauce");
			WebElement error= driver.findElement(By.className("error-message-container"));
			Assert.assertTrue(error.getText().contains("Username and password do not match any user in this service"));
			
		} catch (Exception e) {
			System.out.println("Exception in testInvalidUsername:" +e.getMessage());
		}
	}
	
	@Test
	public void testBlankPassword() {
		try {
			login("standard_user", "");
			WebElement error=driver.findElement(By.className("error-message-container"));
			Assert.assertTrue(error.getText().contains("Password is required"));
		} catch (Exception e) {
			System.out.println("Exception in testBlankPassword:"+e.getMessage());
		}
	}

	@Test
	public void testInvalidPassword() {
		try {
			login("standard_user", "invalid_pass");
			WebElement error=driver.findElement(By.className("error-message-container"));
			Assert.assertTrue(error.getText().contains("Username and password do not match any user in this service"));
		} catch (Exception e) {
			System.out.println("Exception in testInvalidPassword:"+e.getMessage());
		}
	}
	
	@Test
	public void testEmptyFields() {
		try {
			login("","");
			WebElement error=driver.findElement(By.className("error-message-container"));
			Assert.assertTrue(error.getText().contains("Username is required"));
		} catch (Exception e) {
			System.out.println("Exception in testEmptyFields:" +e.getMessage());
		}
		
	}
	
	@Test
	public void testSqlINjection() {
		try {
			login("'OR'1'='1","'OR'1'='1");
			WebElement error=driver.findElement(By.className("error-message-container"));
			Assert.assertTrue(error.getText().contains("Username and password do not match any user in this service"));
		} catch (Exception e) {
			System.out.println("Exception in testSqlInjection:" +e.getMessage());
		}
	}
	
	@Test
	public void testPasswordMasking() {
		try {
			driver.get(baseURL);
			WebElement passwordField=driver.findElement(password);
			Assert.assertEquals(passwordField.getAttribute("type"), "password");
			
		} catch (Exception e) {
			System.out.println("Exception in testPasswordMasking:"+e.getMessage());
		}
	}
	

}
