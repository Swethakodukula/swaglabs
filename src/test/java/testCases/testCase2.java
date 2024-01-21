package testCases;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Helpers.baseClass;
import Pages.Login;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class testCase2 extends baseClass {

	Login login;

	@BeforeMethod
	private void set(){
		login = new Login(driver);
	}

	@Test
	private void validate() {
		test = extent.createTest("Validate","Validating correct url is opened");
		String url= prop.getProperty("testUrl");
		gotoUrl(url);
		test.info("navigating to url "+ url );
		if (driver.getCurrentUrl().contains("saucedemo")) {
			if (driver.getTitle().contains("Swag")) {
				test.pass("Successfully went to " + driver.getCurrentUrl() + " and validated title is "
						+ driver.getTitle());
			} else {
				test.fail("Current Url is " + driver.getCurrentUrl() + " and title is " + driver.getTitle());
			}
		} else {
			test.error("Error navigating to saucedemo url");
		}
	}

	@Test(dependsOnMethods = "validate")
	private void inValidLogin() {
		test = extent.createTest("inValid Login","Validating Login");

		login.enterUsername("swetha");
		login.enterPassword("Test");
		login.clickLogin();
		if (driver.findElement(By.xpath("//h3[@data-test='error']")).isDisplayed()) {
			test.info(MarkupHelper.createLabel(driver.findElement(By.xpath("//h3[@data-test='error']")).getText(),ExtentColor.INDIGO));
		} else {
			test.fail("test Failed");
		}
	}

	@Test(dependsOnMethods = "inValidLogin")
	private void ValidLogin() {
		test = extent.createTest("ValidLogin"," Login");

		login.enterUsername("standard_user");
		login.enterPassword("secret_sauce");
		login.clickLogin();

		if (driver.getCurrentUrl().contains("inventory.html")) {
			test.log(Status.PASS,MarkupHelper.createLabel("test Passed",ExtentColor.GREEN));
		} else {
			test.log(Status.FAIL,MarkupHelper.createLabel("test Failed",ExtentColor.RED));
		}
	}

	@Test(dependsOnMethods = "ValidLogin")
	private void getItems() {
		test = extent.createTest("getItems","show Items");
		WebElement inventoryItem1 = driver.findElement(By.id("item_4_title_link"));
		System.out.println(inventoryItem1.getText());

		WebElement inventoryItem2 = driver.findElement(By.id("item_0_title_link"));
		System.out.println(inventoryItem2.getText());
	}


	@Test(dependsOnMethods = "getItems") 
	private void addtocarts()throws InterruptedException {
		test = extent.createTest("addcarts","Items in cart");
		//gotoUrl("https://www.saucedemo.com/"); 
		//String str = "//*[text()='ADD TO CART']";
		//List<WebElement> addtocarts =6
		int i =1;
		/*
		 * List<WebElement> addtocarts = driver.findElements(By.xpath(str));
		 * for(WebElement cart : addtocarts) { i++; cart.click(); }
		 */

		WebElement web1 =   driver.findElement(By.xpath("//*[@id='add-to-cart-sauce-labs-backpack']"));  
		web1.click();

		WebElement web2 =   driver.findElement(By.xpath(" //*[@id='add-to-cart-sauce-labs-bike-light']"));  
		web2.click();

		WebElement web3 =   driver.findElement(By.xpath("//*[@id='add-to-cart-sauce-labs-bolt-t-shirt']"));  
		web3.click();

		driver.findElement(By.xpath("//*[@id='shopping_cart_container']/a/span")).click();

		TimeUnit.SECONDS.sleep(5);
		
		String backpack=  driver.findElement(By.xpath("//*[@id='cart_contents_container']/div/div[1]/div[3]/div[2]/div[2]/div")).getText();
		test.info(backpack);

		String bikelight=   driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[2]/div")).getText();
		test.info(bikelight);
		String tshirt=   driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[5]/div[2]/div[2]/div")).getText();
		test.info(tshirt);

		
		List<WebElement> cartPrices = driver.findElements(By.xpath("//*[text()='Remove']/preceding-sibling::*[@class='inventory_item_price']"));
		double total = 0.0;

		for(WebElement cartPrice: cartPrices) {
			String price = cartPrice.getText();
			System.out.println("--->"+price);
			total=total + Double.valueOf(price.substring(1));
		}
		
		
		System.out.println("The total bill to be checked out is "+ total);

		List<String> lst =new ArrayList<String>();

		WebElement bikelightremove=   driver.findElement(By.xpath ("//*[@id=\"remove-sauce-labs-bike-light\"]"));
		System.out.println(bikelightremove.getText());
		WebElement tshirtremove=   driver.findElement(By.xpath ("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[2]/button"));
		System.out.println(tshirtremove.getText());
		WebElement bagpackremove=   driver.findElement(By.xpath ("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/button"));
		System.out.println(bagpackremove.getText());

		if(bikelightremove.getText().equalsIgnoreCase("remove"))
		{
			lst.add(bikelightremove.getText());
		}
		if(bikelightremove.getText().equalsIgnoreCase("remove"))
		{
			lst.add(tshirtremove.getText());
		}
		if(bikelightremove.getText().equalsIgnoreCase("remove"))
		{
			lst.add(bagpackremove.getText());
		}

		System.out.println("total remove buttons are "+lst.size());

		// //*[@id="cart_contents_container"]/div/div[1]/div[3]/div[2]/div[2]/div
		System.out.println(i+"times"); 
		//  /html/body/div/div/div/div[2]/div/div/div/div[1]/div[2]/div[2]/button

		//List<WebElement> quantity =
		// driver.findElements(By.ByXPath("//*[@id=\"cart_contents_container\"]"));


	}

	@Test(dependsOnMethods = "getItems")
	private void clickAllCartButtons() {
		test = extent.createTest("clickAllCartButtons","Click cart");

		List<WebElement> addtocarts = driver.findElements(By.xpath("//*[text()='ADD TO CART']"));
		int i = 1;
		for (WebElement cart : addtocarts) {
			cart.click();
			i++;
		}
		test.log(Status.INFO, MarkupHelper.createLabel("added to cart" + i + "times", ExtentColor.ORANGE));
		List<WebElement> remove = driver.findElements(By.xpath("//*[starts-with(text(),'REM')]"));
		if (addtocarts.size() == remove.size())
			test.info(remove.size() + " remove buttons available");

	}



}
