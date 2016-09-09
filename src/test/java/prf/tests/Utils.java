package prf.tests;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import framework.ExcelUtils;
import prf.tests.Utils;

public class Utils {
	public static WebDriver Driver;
	protected static String ChromeDriverX86Path = "C:\\Automation\\WebDriver\\chromedriver.exe";
	protected static String UrlSit = System.getProperty("url", "https://www.amazon.com/Kindle-eBooks/b?ie=UTF8&node=154606011");
	static DateFormat df = new SimpleDateFormat("MMddyyyyHHmmss");
	
	
	
	@BeforeClass(alwaysRun = true)
	public static void StartWebDriverandLogin() throws Exception {
		
			
		System.setProperty("webdriver.ie.driver", ChromeDriverX86Path);
		//Driver = new ChromeDriver();
		Driver = new InternetExplorerDriver();
		//Driver = new FirefoxDriver();
		//Driver = new RemoteWebDriver(new URL("http://10.77.121.175:5555/wd/hub"),capability); //new InternetExplorerDriver();
		Driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Driver.manage().window().maximize();
		//Driver.get("https://www.amazon.com/Kindle-eBooks/b?ie=UTF8&node=154606011");
		Driver.navigate().to(UrlSit);
	}
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestContext result) throws Exception{
		takeScreenShot(result.getName(),"Pass");
		Driver.quit();
		StartWebDriverandLogin();
	}
	
	@AfterClass(alwaysRun = true)
	public static void CloseWebdriver() {
		Driver.quit();
	}
	public static void takeScreenShot(String string,String status) throws IOException { 
		Date today = Calendar.getInstance().getTime();
		String filename = string + "_" + df.format(today) +".png";
		File scrFile = ((TakesScreenshot)Driver).getScreenshotAs(OutputType.FILE);
		String path = getPath((filename), status);
		FileUtils.copyFile(scrFile, new File(path)); 
		path = getURLPath(filename, status);
		Reporter.log(path);
	}


	public static String getURLPath(String nameTest,String status) {
		String strBuildFolder = System.getenv("BUILD_ID");
		String strBuildUrl = System.getenv("BUILD_URL");
		String newstr;
		if (strBuildUrl != null){
		int endIndex = strBuildUrl.lastIndexOf("/");
		if (endIndex != -1)  
			{
		    newstr = strBuildUrl.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
		    endIndex = newstr.lastIndexOf("/");
		    strBuildUrl = newstr.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
		    
			}
		String newFileNamePath =  strBuildUrl+ "/ws/test-output/screenshots/" + strBuildFolder +"/"+ status +"/"+ nameTest;
		Utils.printLine(newFileNamePath);
		return newFileNamePath;
		}
		else{
			return nameTest;
		}
		
		
	} 

	private static String getPath(String nameTest,String status) throws IOException {
		File directory = new File(".");
		String newFileNamePath;
		String strBuildFolder;
		
		if (System.getenv("BUILD_ID") == null){ 
			strBuildFolder = "Debug\\" + status;
			
			}
		else{
			strBuildFolder = System.getenv("BUILD_ID") +"\\"+status;
			
		}
		newFileNamePath = directory.getCanonicalPath() + "\\test-output\\screenshots\\" + strBuildFolder +"\\"+ nameTest;
		Utils.printLine(newFileNamePath);;
	
		//String newFileNamePath = "//test-output\\screenshots\\" + strBuildFolder +"\\"+ nameTest;
		//String newFileNamePath = directory.getCanonicalPath() + "\\test-output\\screenshots\\" + strBuildFolder +"\\"+ nameTest;
		return newFileNamePath;
	}
	public static void printLine(Object line){
		String debugStatus = System.getProperty("debug", "true");
		if (debugStatus.equals("true")){
			System.out.println(line.toString());
			}
	}
	
	
	
}
