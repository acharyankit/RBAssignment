package driverPkg;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseTest {
	
	public WebDriver driver;
	
	public static String homePageUrl;
	public static String textToSearch;
	public static String startYear;
	
	public BaseTest() throws IOException {
		this.driver = initializeDriver(driver);
	}
	
	public WebDriver initializeDriver(WebDriver driver) throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//resources//GlobalData.properties");
		prop.load(fis);
		
		homePageUrl = prop.getProperty("homePageUrl");
		textToSearch = prop.getProperty("textToSearch");
		startYear = prop.getProperty("startYear");
		
		String browserName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");

		if (driver == null && browserName.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\main\\resources\\chromedriver-win64\\chromedriver.exe");
			driver=new ChromeDriver();

		} else if (driver == null && browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "\\src\\main\\resources\\chromedriver-win64\\geckodriver.exe");
			driver = new FirefoxDriver();
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public void waitForWebElementToAppear(By findBy) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.presenceOfElementLocated(findBy));
	}
	public void tempTry() {
//		driver.findElement(with(By.tagName(""))).getText();
		driver.switchTo().newWindow(WindowType.TAB);
	}
}