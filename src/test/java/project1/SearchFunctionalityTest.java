package project1;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.bonigarcia.wdm.WebDriverManager;
import project1.Carouselverify;
import project1.HomePage;
import project1.common;

public class SearchFunctionalityTest {
	private WebDriver driver;
	private HomePage homePage;
	public ExtentReports extent;
	public ExtentTest extenttest;
	private static final Logger logger = LogManager.getLogger(SearchFunctionalityTest.class);

	@BeforeTest
	public void setUp() {
		//initialising extent reports
		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/SearchFunctioanlity.html");
		extent.loadConfig(new File(System.getProperty("user.dir") + "/src/test/java/extent-config.xml"));

	}

	@BeforeMethod
	public void browserOpen() {
		// Set up ChromeDriver path
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");// disable the notification in the homepage
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

	@Test
	public void searchProduct() throws InterruptedException, IOException {
		extenttest = extent.startTest("Searching for a t-shirt Product");

		// Navigate to the webpage
		driver.get("https://www.thesouledstore.com/men");
		extenttest.log(LogStatus.PASS, "Souled Store page has been opened");
		// Enter the search query
		String searchQuery = "Disney T-shirt";
		Thread.sleep(3000);
		HomePage.enterSearchQuery(searchQuery);//this step action i called in homepage
		Thread.sleep(2000);
		extenttest.log(LogStatus.PASS,extenttest.addScreenCapture((common.screenshot(driver, "SearchBoxAccessible")))+ "Input entered in search box");
		// Click the search button
		homePage.clickSearchButton();
		Thread.sleep(2000);
		extenttest.log(LogStatus.PASS,extenttest.addScreenCapture((common.screenshot(driver, "ProductSearchResults")))+ "Search Result loaded");
		// Perform assertions on search results
		// Example: Assert the page title contains the search query
		String ExpectedpageText = "Showing results for Disney T shirt ";
		String pageSpanText = driver.findElement(By.xpath("//h1[@class='search-result']//span")).getText();//for total item of disney t shirt is 97.  
		logger.info(pageSpanText);                                                                         //logging the span text
		String pageText = driver.findElement(By.xpath("//h1[@class='search-result']")).getText().replace(pageSpanText, "");//Replacing "- 97 items" to Null in the String
		logger.info(pageText);
		Assert.assertEquals(ExpectedpageText, pageText);
	}

	@Test
	public void applyFilterThemes() throws InterruptedException, IOException {
		extenttest = extent.startTest("Filtering of a Product");

		// Navigate to the webpage
		driver.get("https://www.thesouledstore.com/men");
		extenttest.log(LogStatus.PASS,extenttest.addScreenCapture((common.screenshot(driver, "HomePageScreenshot")))+ "Next Carousel Loaded");

		// Enter the search query
		String searchQuery = "polos";
		HomePage.enterSearchQuery(searchQuery);
		extenttest.log(LogStatus.PASS,extenttest.addScreenCapture((common.screenshot(driver, "SearchBoxAccessible1")))+ "Next Carousel Loaded");

		// Click the search button
		homePage.clickSearchButton();
		Thread.sleep(2000);
		extenttest.log(LogStatus.PASS,extenttest.addScreenCapture((common.screenshot(driver, "ProductSearchResults1")))+ "Next Carousel Loaded");

		// Apply the "Naruto" filter
		String filter = "Naruto";
		homePage.applyFilter(filter);
		// Wait for the page to load the filtered results
		Thread.sleep(1000);
		extenttest.log(LogStatus.PASS,extenttest.addScreenCapture((common.screenshot(driver, "FilterResults")))+ "Next Carousel Loaded");

		// Perform assertions on the filtered results
		// Example: Assert that the filtered results contain the expected theme
		String filterTitleElement = "Showing results for polos ";
		String pageSpanText = driver.findElement(By.xpath("//h1[@class='search-result']//span")).getText();//for total item of polos shirt is 1.  
		logger.info(pageSpanText);    //it takes the text what is shown in the display
		String filterTitle = driver.findElement(By.xpath("//h1[@class='search-result']")).getText().replace(pageSpanText, "");
		Assert.assertEquals(filterTitle, filterTitleElement, "Filter working as expected");
		extenttest.log(LogStatus.PASS, "Filter functionality working as expected");

	}

	@AfterClass
	public void tearDown() {
		// Quit the browser
		extent.flush();
		extent.close();
	}

}
