package framework;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.internal.TestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import InitilizeRunConfiguration.ExcelData;
import constants.CommonProperties;
import constants.SystemVariables;
import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.util.ResultsUtils;
import listeners.WebDriverListener;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


 public class BaseClass extends ExcelData{

	public BaseClass() throws Exception {
		super("");
		// TODO Auto-generated constructor stub
	} 
	public static String URL;
	private static final Logger logger = LogManager.getLogger(BaseClass.class);
	private static final ThreadLocal<BaseClass> baseClassObj = new ThreadLocal<>();

	private static ExtentReports extentReports;
	private static final int EXPLICIT_TIMEOUT_SECONDS = 3;
	private WebDriver driver;
	public static String methodName;
	public static String browserType;
	private ExtentTest extentTest;
	public void fetchUrl() {

		try {
			 logger.info("Properties file loaded...!");
			
			  if (CommonProperties.getUrl()!=null) { URL = CommonProperties.getUrl();;
			  logger.info("Method Name :{}", URL); } else URL =GetTestData("Data","URL",2);
			 

			logger.info("Test Url : {}", URL);
			driver.manage().deleteAllCookies();
			driver.manage().window().maximize();
			driver.get(URL);
		} catch (

		Exception e) {
			testFailureException(e);
		}

	}
	@BeforeClass
	public void setup()
	{
		CommonProperties.loadProperties();
	}
	public void browserSelection() throws ClassNotFoundException, SQLException {
		logger.info("Driver loaded successfully..!");
		try {
			
			 CommonProperties.loadProperties(); if(CommonProperties.Browser_type==null)
			  browserType=GetTestData("Data","Browser",2); else browserType =
			  CommonProperties.Browser_type; logger.info("Browser Name :{} ",
			  browserType);
			 

			if (browserType.equals("Chrome")) {
				//System.setProperty("webdriver.chrome.driver",SystemVariables.Drivers+"chrome.exe");
				//driver = new ChromeDriver();
				WebDriverManager.chromedriver().browserVersion("123.0.6312.106").setup();
				ChromeOptions options = new ChromeOptions();
				driver = new ChromeDriver(options);
				System.out.println(driver);
				logger.info("Chrome instance started");
			} else if (browserType.equals("FIREFOX")) {
				driver = new FirefoxDriver();
				logger.info("Firefox instance started");
			} 
			else {
				System.setProperty("webdriver.chrome.driver",SystemVariables.Drivers+"chrome.exe");
				WebDriverManager.chromedriver().browserVersion("123.0.6312.106").setup();
				ChromeOptions options = new ChromeOptions(); 
				driver = new ChromeDriver(options);
				logger.info("Default browser chrome instance started");
			}

			if (CommonProperties.getScreenshotFlag().equals(true)) {
				EventFiringWebDriver eventDriver = new EventFiringWebDriver(driver);
				WebDriverListener webDriverListener = new WebDriverListener();
				eventDriver.register(webDriverListener);
				driver = eventDriver;
			}
			logger.info("Take screenshots for all Steps :{} ", CommonProperties.getScreenshotFlag());
			
		} catch (Exception e) {
			testFailureException(e);
		}

	}
public void saveTestRunDetails(ITestResult result) {

		String testCaseID;
		Double testSetNumber;
		long tcStartTime;
		long tcEndTime;
		boolean executionStatus;

		tcStartTime = result.getStartMillis();
		logger.info("Test Start Time :{} ", tcStartTime);

		testCaseID = result.getMethod().getMethodName();
		logger.info("Method Name :{} ", testCaseID);

		testSetNumber = ((TestResult) result).getParameterIndex() + 1.0;
		logger.info("Test Set ID :{} ", testSetNumber);

		tcEndTime = result.getEndMillis();
		logger.info("Test End Time :{} ", tcEndTime);

		executionStatus = result.isSuccess();
		logger.info("Test Execution Passed :{} ", executionStatus);

		StringBuilder bld = new StringBuilder();
		bld.append(testCaseID);
		bld.append("_");
		bld.append(testSetNumber);

		testCaseID = bld.toString();

		logger.info(testCaseID);

		List<Long> testRunInfo = new ArrayList<>();
		testRunInfo.add(tcStartTime);
		testRunInfo.add(tcEndTime);
		if (executionStatus) {
			testRunInfo.add(1L);
		} else {
			testRunInfo.add(0L);
		}

		logger.info("testRunInfo :{} ", testRunInfo);
		logger.info("Saved test Start Time :{} ", tcStartTime);
		logger.info("End Time :{} ", tcEndTime);

	}
	public void closeBrowsers() {

		if (driver != null) {
			driver.quit();
			logger.info("All browser instances are closed");
		} else {
			logger.info("No browser instances are open");
		}

	}
	public void testFailureException(Exception e) {

		logger.error("Error Log :{} ", e);

		takeScreenshot();
		closeBrowsers();
		extentTest.log(Status.FAIL, e);
		Assert.assertTrue(false);
	}
	public void explicitWait(By by) {

		WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT_SECONDS);
		wait.until(ExpectedConditions.visibilityOfElementLocated((by)));

	}
	public static void step() {
		String uuid = UUID.randomUUID().toString();
		StepResult result = new StepResult().setName(methodName);
		Allure.getLifecycle().startStep(uuid, result);
		try {
			Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(io.qameta.allure.model.Status.PASSED));
			} 
		catch (Throwable e)
			{
				Allure.getLifecycle().updateStep(uuid, s -> s.setStatus(ResultsUtils.getStatus(e).orElse(io.qameta.allure.model.Status.BROKEN)).setStatusDetails(ResultsUtils.getStatusDetails(e).orElse(null)));
				throw e;
			} 
		finally 
			{
			 Allure.getLifecycle().stopStep(uuid);
			}
}
	public void takeScreenshot() {

		String screenshotName;
		String screenshotPath;

		screenshotName = methodName + "_" + System.currentTimeMillis() + ".jpg";

		try {

			TakesScreenshot takeScreenshot = (TakesScreenshot) driver;
			File srcFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
			logger.info("Taken Screenshot for Step");

			File tgtFile = new File(SystemVariables.OUTPUT_FOLDER + "Screenshots" + File.separator, screenshotName);
			FileUtils.copyFile(srcFile, tgtFile);
			logger.info("Saved screenshot to results with Name :{} ", screenshotName);

			Reporter.log("<br><img src='Screenshots/" + screenshotName + ".jpg' height='400' width='400'/><br>");

			screenshotPath = SystemVariables.OUTPUT_FOLDER + "Screenshots" + File.separator + screenshotName;
			logger.info("Screenshot Path :{} ", screenshotPath);

			getExtentTest().addScreenCaptureFromPath(screenshotPath);

		} catch (Exception e) {
			logger.error("Error in Capturing Screenshot for :{} ", driver.getTitle());
		}

	}
	public static void configureExtentReports() {

		try {
			String outputDirectory = SystemVariables.OUTPUT_FOLDER + "ExecutionReport.html";

			ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(outputDirectory);
			extentSparkReporter.config().setReportName("Summury Report Test");
			extentSparkReporter.config().setDocumentTitle("Test Execution");
			extentSparkReporter.config().enableOfflineMode(false);
			extentSparkReporter.config().setTheme(Theme.DARK);

			extentReports = new ExtentReports();
			extentReports.attachReporter(extentSparkReporter);
			extentReports.setSystemInfo(SystemVariables.USER_NAME.name(), SystemVariables.USER_NAME.toString());
			extentReports.setSystemInfo("Browser", "Chrome");
			extentReports.setSystemInfo(SystemVariables.OS_VERSION.name(), SystemVariables.OS_VERSION.toString());
			extentReports.setSystemInfo(SystemVariables.OS_NAME.name(), SystemVariables.OS_NAME.toString());
			
		} catch (Exception e) {
			logger.error("Error in configuring extent report settings :{} ", e);
		}
	}


	public ExtentTest getExtentTest() {
		return extentTest;
	}

	public void setExtentTest(ITestResult Result) {
		
		this.extentTest = extentReports.createTest(Result.getName());
		methodName=Result.getName();
	}
	public static String Method_name()
	{
		return methodName;
	}
	public static ExtentReports getExtentReports() {
		return extentReports;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public static void setBaseObj(BaseClass baseObj) {
		baseClassObj.set(baseObj);
	}

	public static BaseClass getBaseObj() {
		return baseClassObj.get();
	}

	public static void removeBaseObj() {
		baseClassObj.remove();
	}

}
