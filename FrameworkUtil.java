package framework;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FrameworkUtil extends BaseClass{
	public FrameworkUtil() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}
	private static final Logger logger = LogManager.getLogger(FrameworkUtil.class);
	
	private WebDriver driver;
	private BaseClass baseLocalObj;
	public  void SelectByText(By xpath,String Text) throws Exception
	{
		try {
			
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();
			
			WebElement ele=driver.findElement(xpath);
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.visibilityOf(ele));
			Select select=new Select(ele);
			select.selectByVisibleText(Text);
			 logger.info("Selected Text :{} ",Text );
			 
		}
		catch (Exception e) {
			testFailureException(e);
		}
		
	}
	public  void SelectByValue(By xpath,String value) throws Exception
	{
		try {
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();
			WebElement ele=driver.findElement(xpath);
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.visibilityOf(ele));
			Select select=new Select(ele);
			select.selectByValue(value);
			 logger.info("Selected Value :{} ",value );
			
		}
		
		 catch (Exception e) {
				testFailureException(e);
			}
	}
	public  void SelectByIndex(By xpath,int index)throws Exception
	{
		try{
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();
			WebElement ele=driver.findElement(xpath);
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.visibilityOf(ele));
			Select select=new Select(ele);
			select.selectByIndex(index); 
			 logger.info("Selected Index :{} ",index );
			
		}
		
		 catch (Exception e) {
				testFailureException(e);
			}
		
	}
	public  void EnterText(By xpath,String data) throws Exception
	{
		try{
			
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();
			WebElement ele=driver.findElement(xpath);
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.visibilityOf(ele));
			ele.sendKeys(data);
			 logger.info("Page Title :{} ", driver.getTitle());
			 logger.info("Inserted Text :{} ", driver.findElement(xpath).getAttribute("value"));
			
		}
		
		 catch (Exception e) {
				testFailureException(e);
			}
	}
	public  void ClickElement(By xpath,String desc) throws Exception
	{
		try
		{
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();
			WebElement ele=driver.findElement(xpath);
			new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
			.until(ExpectedConditions.visibilityOf(ele));
			ele.click();
			 logger.info("Element clicked" );
			 
		}
		
		 catch (Exception e) {
				testFailureException(e);
			}
	}
	public  String getTxt(By xpath)
	{
		try{
			baseLocalObj = BaseClass.getBaseObj();
			driver = baseLocalObj.getDriver();
			WebElement ele=driver.findElement(xpath);
			String text=ele.getText() ;
			return text;
		}
		
		catch (Exception e) {
			testFailureException(e);
		}
		return null;
	}
	public void HighLight(By xpath) throws Exception
	{
		baseLocalObj = BaseClass.getBaseObj();
		driver = baseLocalObj.getDriver();
		try {
			WebElement ele=driver.findElement(xpath);
			JavascriptExecutor  jse=(JavascriptExecutor )driver;
			jse.executeScript("arguments[0].style.border='3px solid red'", ele);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			
		}
		
		 catch (Exception e) {
				testFailureException(e);
			}
	}
	public  boolean waitForAlertPresent(WebDriver driver, int waitTime) {
		baseLocalObj = BaseClass.getBaseObj();
		driver = baseLocalObj.getDriver();
		boolean flag = false;
		new WebDriverWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
				.until(ExpectedConditions.alertIsPresent());
		try{
			driver.switchTo().alert();
			
			return flag = true;
		}
		catch (Exception e) {
			testFailureException(e);
		}
		return flag;
	}
	public  boolean waitForElementToBeVisible(WebDriver driver, By xpath, int waitTime) {
		
		baseLocalObj = BaseClass.getBaseObj();
		driver = baseLocalObj.getDriver();
		boolean flag = false;
		try {
			new WebDriverWait(driver, waitTime).ignoring(StaleElementReferenceException.class)
					.until(ExpectedConditions.visibilityOfElementLocated(xpath));
			flag=true;
			return flag;
		} 
		catch (Exception e) {
			testFailureException(e);
		}
		return flag;
	}

	public  boolean IsElementPresent(By xpath) throws Exception
	{
		try
		{
		baseLocalObj = BaseClass.getBaseObj();
		driver = baseLocalObj.getDriver();
		WebElement ele=driver.findElement(xpath);
		if(ele.isDisplayed())
		{
			logger.info("Element Displayed" );
			
			return true;
		}
			
		else
			return false;	
		}
	
	 catch (Exception e) {
			testFailureException(e);
		}
		return false;
	}
	public void ScrollBy(By xpath) throws InterruptedException
	{
		driver = baseLocalObj.getDriver();
	    WebElement ele=driver.findElement(xpath);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
	}
}
