package rb.tests;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import driverPkg.BaseTest;
import rb.pages.HomePage;
import rb.pages.ResultPage;
import rb.utils.Reporting;

public class TestCases {
	
	HomePage hp;
	ResultPage rp;
	static int intResultCount;
	static int intResultCountNew;
	
	Reporting util;
	
	@BeforeSuite
	public void init() throws IOException {
		System.out.println("BeforeSuite");
		util = new Reporting();
	}
	
	@AfterMethod
	public void flush() {
		System.out.println("AfterMethod");
		util.generateReport();
	}
	
//	@Test
	public void tempTry() throws IOException {
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1000));
//		System.out.println();
		
		List<String> names = Arrays.asList("Ankit", "Alekhya", "Rama", "Don", "John", "Rama");
		names.stream().filter(s->s.startsWith("A")).forEach(s->System.out.println(s));
		List<String> ls = names.stream().filter(s->s.startsWith("A")).collect(Collectors.toList());
		System.out.println(ls.getFirst());
		names.stream().distinct().forEach(s->System.out.println(s));
	}
	
	@Test(priority=1)
	public void searchItem() throws IOException {
		hp = new HomePage(util.driver);
		try {
			hp.navigateToUrl(BaseTest.homePageUrl);
			hp.enterSearchString(BaseTest.textToSearch);
			hp.pressSearchButton();
			util.pass("Search for item on Home Page", "Success");
		} catch (Exception e) {
			util.fail("Search for item on Home Page", "Failed");
			e.printStackTrace();
		}
	}
	
	@Test(priority=2)
	public void getResult() throws IOException {
		rp = new ResultPage(util.driver);
		try {
			intResultCount = rp.getSearchResultCount();
			if (intResultCount == 0) {
				util.fail("Get Result Count Failed. Either error while capturing the count or actual count is 0", "Failed");
			}	else {
				util.pass("Get Result Count. Count is: "+intResultCount, "Success");
			}
		} catch (Exception e) {
			util.fail("Get Result Count", "Failed");
			e.printStackTrace();
		}
	}
	
	@Test(priority=3)
	public void verifyResult() throws IOException {
		rp = new ResultPage(util.driver);
		try {
			rp.verifyFirstResult();
			util.pass("Verify First Result Text", "Success");
		} catch (Exception e) {
			util.fail("Verify First Result Text", "Failed");
//			e.printStackTrace();
		}
	}
	
	@Test(priority=4)
	public void applyYearFilter() throws IOException {
		rp = new ResultPage(util.driver);
		try {
			rp.applyYearFilter(BaseTest.startYear);
			util.pass("Apply Year Filter", "Success");
		} catch (Exception e) {
			util.fail("Apply Year Filter", "Failed");
//			e.printStackTrace();
		}
	}
	
	@Test(priority=5)
	public void compareResults() throws IOException {
		rp = new ResultPage(util.driver);
		try {
			intResultCountNew = rp.getSearchResultCount();
			if (intResultCount != intResultCountNew) {
				util.pass("Result Count Comparision after Applying Filter. New Count is: "+intResultCountNew, "Success");
				if (intResultCount > intResultCountNew) {
					util.pass("Result Count Comparision after Applying Filter. New Count is LESS than Old Count", "Success");
				}	else {
					util.pass("Result Count Comparision after Applying Filter. New Count is MORE than Old Count", "Success");
				}
			}	else {
					util.fail("Result Count Comparision after Applying Filter", "Failed");
			}
		} catch (Exception e) {
			util.fail("Result Count Comparision after Applying Filter", "Failed");
//			e.printStackTrace();
		}
		rp.tearDown();
	}
}