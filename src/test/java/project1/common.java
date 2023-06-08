package project1;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class common {


	
	
	public static String screenshot(WebDriver driver,String screenshotName) throws IOException
	{
		TakesScreenshot scrShot =((TakesScreenshot)driver);

		File screenshotFile = (scrShot.getScreenshotAs(OutputType.FILE));
        String screenshotPath =  System.getProperty("user.dir") + "/test-output/screenshots/"+screenshotName+".png";
        FileHandler.copy(screenshotFile, new File(screenshotPath));
        System.out.println("Screenshot saved at: " + screenshotPath);
        return screenshotPath;
	}

	
}
