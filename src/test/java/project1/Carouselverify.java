package project1;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import project1.HomePage;
import project1.common;
import org.testng.*;
import java.util.List;
import java.util.ArrayList;

public class Carouselverify {
	public WebDriver driver;
	private HomePage homePage;
	public ExtentReports extent;
	public ExtentTest extenttest;
	private static final Logger logger = LogManager.getLogger(Carouselverify.class);

	@BeforeClass
	public void setUp() {
		// Set up ChromeDriver path
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/CarouselVerificationTest.html");
		extent.loadConfig(new File(System.getProperty("user.dir") + "/src/test/java/extent-config.xml"));

	}

	@BeforeMethod
	public void browserOpen() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications"); // handling the notification in home page
		WebDriverManager.chromedriver().setup();

		// Initialize ChromeDriver instance
		driver = new ChromeDriver(options);

		// Maximize browser window
		driver.manage().window().maximize();

		// Instantiate the HomePage class
		homePage = new HomePage(driver);
	}

	@AfterMethod
	public void closeBrowser() {
		extent.endTest(extenttest);
		driver.quit();
	}

	@AfterClass
	public void tearDown() {

		extent.flush();
		extent.close();
	}

	@Test
	public void CarouseNextButtonCheck() throws InterruptedException, IOException {
		extenttest = extent.startTest("Verifying Carousel Next Button"); // extent report for carousel next button
		WebDriverWait wait = new WebDriverWait(driver, 15);
		driver.get("https://www.thesouledstore.com/men");
		extenttest.log(LogStatus.PASS, "Souled Store page has been opened");
		Thread.sleep(1000); // Add a delay to observe the transition
		WebElement initialCard = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='VueCarousel-slide']")));// wait
																													// and																										// find
																													// the																											// xpath
																												// of
																													// carousel
		for (int i = 1; i <= 4; i++) {

			homePage.clickNextButton();
			Thread.sleep(1000);
			// Wait for the next card to load
			WebElement nextCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@class='VueCarousel-slide VueCarousel-slide-active VueCarousel-slide-center']")));
			// Verify that the next card is different from the initial card
			Assert.assertNotEquals(nextCard, initialCard, "Next card is loaded as expected"); // that application
																								// behavior is working
																								// as expected test is
																								// pass or fail
			extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "NextCarousel" + i)))
					+ "Next Carousel Loaded"); // next carousel buttonn screenshot

		}
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "CarouselFinal")))
				+ "Carousel Next Navigation working");

	}

	@Test
	public void CarouselBackButtonTest() throws InterruptedException, IOException {
		extenttest = extent.startTest("Verifying Carousel Back button");
		WebDriverWait wait = new WebDriverWait(driver, 15);

		driver.get("https://www.thesouledstore.com/men");
		Thread.sleep(1000); // Add a delay to observe the transition
		WebElement initialCard = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='VueCarousel-slide']")));
		for (int i = 1; i <= 4; i++) {
			homePage.clickPreviousButton();
			Thread.sleep(1000);
			// Wait for the next card to load
			WebElement previousCard = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//*[@class='VueCarousel-slide VueCarousel-slide-active VueCarousel-slide-center']")));
			Assert.assertNotEquals(previousCard, initialCard, "Previous card is loaded as expected");
			extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "previouscarousel")))
					+ "Previous Carousel Loaded");
		}
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "Carouselfinal")))
				+ "Carousel back Navigation working");

	}

	@Test
	public void MenSectionValidateCategory() throws InterruptedException // different sections on the page when you
			, IOException
	// scroll
	{
		extenttest = extent.startTest("Verifying Categories of Men's page");

		driver.get("https://www.thesouledstore.com/men");
		extenttest.log(LogStatus.PASS, "Souled Store page has been opened");
		Thread.sleep(5000);
		homePage.FindCategoryElement("COLLECTIONS");
		extenttest.addScreenCapture((common.screenshot(driver, "Collectionscategory")));
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "Collectionscategory")))
				+ "Collection Category is present as expected");
		homePage.FindCategoryElement("Official Merchandise");
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "OfficialMerchandise")))
				+ "Official Merchandise Category is present as expected");
		homePage.FindCategoryElement("Top Selling");
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "TopSelling")))
				+ "Top Selling Category is present as expected");
		homePage.FindCategoryElement("Membership");
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "Membership")))
				+ "Membership Category is present as expected");
		homePage.FindCategoryElement("Straight Out Of Celebrity Closets");
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "FinalScreenshot")))
				+ "Straight Out Of Celebrity Closets Category is present as expected");

	}

	@Test
	public void getProductdetails() throws InterruptedException, IOException {
		extenttest = extent.startTest("verify the product details,size and rate of particular product");
		driver.get("https://www.thesouledstore.com/men");
		Thread.sleep(3000);

		// Find the product details, rates, and sizes in the men's section
		WebElement product = driver.findElement(By.xpath("//img[@title='artists/naruto-merchandise']"));// clicking the
																										// Naruto card
		product.click();
		Thread.sleep(2000);
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "ProductListpage")))
				+ "Successfully scrolled to product and clicked");
		homePage.findProductElement("Naruto: Sasuke");// product name
		Thread.sleep(2000);
		String productDetails = driver.findElement(By.xpath("//*[@class='card-block']")).getText(); // product
		String productRate = driver.findElement(By.xpath("//span[@class='offer']")).getText();// price of the above
																								// product
		Thread.sleep(3000);
		logger.info("Product Details : " + productDetails);
		logger.info("Product Rate : " + productRate);
		// homePage.selectSize("L");
		WebElement sizeList = driver.findElement(By.xpath("//ul[@class='sizelist']"));

		// Get all the size options
		List<WebElement> sizeOptions = sizeList.findElements(By.xpath(".//li[@class='oval unselectedSize']"));

		// Log and display the product sizes
		logger.info("Available Product Sizes:");
		for (WebElement sizeOption : sizeOptions) {
			String size = sizeOption.getText().trim();
			if (!size.isEmpty()) {
				logger.info(size);
			}
		}
		extenttest.log(LogStatus.PASS, extenttest.addScreenCapture((common.screenshot(driver, "ProductDetailspage")))
				+ "Product details, size and rate are fetched");

	}

}