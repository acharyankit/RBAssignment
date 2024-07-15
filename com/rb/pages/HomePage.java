package rb.pages;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage{

	private static Logger logger = LogManager.getLogger(HomePage.class);
	
	WebDriver driver;
	
	@FindBy(xpath = "//input[starts-with(@placeholder,'Search')]")
	WebElement searchField;

	@FindBy(xpath = "//button[@type='submit'][@value='search']/img")
	WebElement searchButton;

	public HomePage(WebDriver driver) throws IOException {
//		super();
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void navigateToUrl(String Url) throws IOException {
		driver.get(Url);
		logger.info("Home page opened...");
	}
	
	public void enterSearchString(String textToSearch) throws IOException {
		searchField.sendKeys(textToSearch);
		logger.info("Text to search entered in Search field...");
	}
	
	public void pressSearchButton() throws IOException {
		searchButton.click();
		logger.info("Clicked Search button...");
	}
}