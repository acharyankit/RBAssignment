package rb.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import driverPkg.BaseTest;

@SuppressWarnings("deprecation")
public class Reporting extends BaseTest{
	
	private static Logger logger = LogManager.getLogger(Reporting.class);

	private String strScreenshotPath;
	private String strReportPath = System.getProperty("user.dir") + "/Report/Report_"+new SimpleDateFormat("ddMMyyHHmmsss.SSS").format(new Date())+"/ExtentReport.html";
	protected static ExtentReports extentReports = new ExtentReports();
	private ExtentTest testCase;

	public Reporting() throws IOException {
		super();
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(strReportPath);
		extentReports.attachReporter(htmlReporter);
	}
	
	public void generateReport() {
		extentReports.flush();
		logger.info("HTML extent report generated.");
	}

	private void createTestCase(String strTestCaseName) {
		testCase = extentReports.createTest(strTestCaseName);
	}

	public void pass(String strTestCaseName, String strResult) throws IOException {
		createTestCase(strTestCaseName);
		getScreenshot();
		testCase.pass(strResult, MediaEntityBuilder.createScreenCaptureFromPath(strScreenshotPath).build());
	}
	
	public void fail(String strTestCaseName, String strResult) throws IOException {
		createTestCase(strTestCaseName);
		getScreenshot();
		testCase.fail(strResult, MediaEntityBuilder.createScreenCaptureFromPath(strScreenshotPath).build());
	}
	
	public void info(String strTestCaseName, String strResult) throws IOException {
		createTestCase(strTestCaseName);
		getScreenshot();
		testCase.info(strResult, MediaEntityBuilder.createScreenCaptureFromPath(strScreenshotPath).build());
	}
	
	private void getScreenshot() throws IOException {
		strScreenshotPath = System.getProperty("user.dir") + "/Screenshots/SS_"+new SimpleDateFormat("ddMMyyHHmmsss.SSS").format(new Date())+".png";
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(strScreenshotPath);
		FileUtils.copyFile(source, file);
	}
}