package project1;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.jetty9.util.log.Log;
import org.openqa.selenium.Keys;
import org.openqa.selenium.JavascriptExecutor;

public class HomePage {
	private static WebDriver driver;

	// Locators for search functionality
	private static By searchInputLocator = By.xpath("//*[@id='search']");
	// private By searchButtonLocator = By.xpath("//*[@id='search']");

	// Locators for filter functionality
	private By filterButtonLocator = By.xpath("//a[normalize-space()='Themes']");
	private By carouselItemLocator = By.className("carousel-item");
	private By carouselLocator = By.className("carousel");


	public HomePage(WebDriver driver) {
		HomePage.driver = driver;
	}

	public static void enterSearchQuery(String query) {
		WebElement searchInput = driver.findElement(searchInputLocator);
		searchInput.clear();
		searchInput.sendKeys(query);
	}

	public void clickSearchButton() throws InterruptedException {
		driver.findElement(searchInputLocator).sendKeys(Keys.ENTER);
		Thread.sleep(2000);
	}

	public void applyFilter(String filter) throws InterruptedException {
		// Open the filter section
		driver.findElement(By.xpath("//a[normalize-space()='Products']")).click();
		driver.findElement(filterButtonLocator).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath("//input[@placeholder='Search for Themes']")).click();
		driver.findElement(By.xpath("//input[@placeholder='Search for Themes']")).sendKeys(filter);
		// Select the filter option by text
		WebElement filterOption = driver
				.findElement(By.xpath("//div[@class='maxer card-detail collapse in']//input[@type='checkbox']"));
		WebElement filterOptionLocator = driver.findElement(By.xpath(
				"//div[@class='row filterbtns showdesktop']//button[@type='button'][normalize-space()='Apply']"));
		// scrollToElement(filterOption);
		// scrollToElement(filterOptionLocator);

		Thread.sleep(5000);
		filterOption.click();
		Thread.sleep(5000);

		filterOptionLocator.click();
	}

	public WebElement getCarousel() {
		return driver.findElement(carouselLocator);
	}

	public List<WebElement> getCarouselItems() {
		return driver.findElements(carouselItemLocator);
	}

	public String getActiveItemText() {
		List<WebElement> carouselItems = getCarouselItems();
		for (WebElement item : carouselItems) {
			if (item.getAttribute("class").contains("active")) {
				return item.getText();
			}
		}
		return null;
	}
	
	public void FindCategoryElement(String Name) throws InterruptedException
	{
		WebElement categoryOption = driver.findElement(By.xpath("//h3[normalize-space()='"+Name+"']"));
		scrollToElement(categoryOption);
		//Logger.("Following Catergory is available"+categoryOption);
	}

	private void scrollToElement(WebElement element) throws InterruptedException {
		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'center' });", element);
		Thread.sleep(2000);
	}

	public void clickNextButton() {
		WebElement nextButton = driver
				.findElement(By.xpath("//div[@class='headtop prelative']//button[@aria-label='Next page']"));
		nextButton.click();
	}

	public void clickPreviousButton() {
		WebElement previousButton = driver
				.findElement(By.xpath("//div[@class='headtop prelative']//button[@aria-label='Previous page']"));
		previousButton.click();
	}
	
	public void findProductElement(String Name) throws InterruptedException
	{
		WebElement productOption = driver.findElement(By.xpath("//h5[normalize-space()='"+Name+"']"));
		scrollToElement(productOption);
		productOption.click();
		Thread.sleep(2000);
		//Logger.("Following Catergory is available"+categoryOption);
	}
	
	public void selectSize(String size)
	{
		WebElement productSizes = driver.findElement(By.xpath("//*[@class='oval unselectedSize']//*[normalize-space()='"+size+"']"));// size of the product
		productSizes.click();
	}
}

