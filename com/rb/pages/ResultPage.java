package rb.pages;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultPage{
	
	private static Logger logger = LogManager.getLogger(ResultPage.class);

	WebDriver driver;
	WebDriverWait wait;
	
	@FindBy(xpath = "//input[@aria-label='manufactureYearRange'][@data-index='0']//following-sibling::*//span[text()]")
	WebElement startYear;

	@FindBy(xpath = "//ul[@id='searchResults']//li//a[@class]")
	private List<WebElement> searchResults;
	
	@FindBy(xpath = "//*[starts-with(text(),'Showing')][contains(text(),'\"Ford F-150\"')]")
	WebElement searchResultText;
	
	@FindBy(xpath = "//input[@id='manufactureYearRange_min']")
	WebElement startYearField;
	
	By bySearchResultText = By.xpath("//*[starts-with(text(),'Showing')][contains(text(),'Ford F-150')]");
	By byFilterOption2010 = By.xpath("//div/button[text()='Clear all']/../preceding-sibling::div[2]//*[starts-with(text(),'Year: 2010')]");
	
	public ResultPage(WebDriver driver) throws IOException {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		PageFactory.initElements(driver, this);
	}
	
	public int getSearchResultCount() throws InterruptedException {
//		waitForWebElementToAppear(bySearchResultText);
		wait.until(ExpectedConditions.presenceOfElementLocated(bySearchResultText));
//		Thread.sleep(5000);
		String strCount = searchResultText.getText();
		logger.info("Search Result Text Captured: "+strCount);
		String[] split = strCount.split(" ");
		int intCount = 0;
		try {
			intCount = Integer.parseInt(split[3]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		logger.info("Search Result Count: "+intCount);
		return intCount;
	}
	
	public boolean verifyFirstResult() {
		String firstResultText = searchResults.getFirst().getText();
		logger.info("First Result full text: "+firstResultText);
		if(firstResultText.contains("Ford F-150")) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	public void applyYearFilter(String strNewYear) throws InterruptedException {
		String currentStartYearVal = startYear.getText();
		int startYearBefore = Integer.parseInt(currentStartYearVal);
		
		startYearField.sendKeys(Keys.CONTROL + "a");
		startYearField.sendKeys(Keys.DELETE);
		logger.info("Setting Start Year Filter as: "+strNewYear);
		startYearField.sendKeys(strNewYear);
		startYearField.sendKeys(Keys.RETURN);
		
//		waitForWebElementToAppear(byFilterOption2010);
		wait.until(ExpectedConditions.presenceOfElementLocated(byFilterOption2010));
//		Thread.sleep(5000);
		
		String newStartYearVal = startYear.getText();
		int startYearAfter = Integer.parseInt(newStartYearVal);
	}
	
	public void tearDown() {
		driver.quit();
		logger.info("Execution Completed");
	}
}