package prf.tests;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import framework.ExcelUtils;

import org.openqa.selenium.support.ui.WebDriverWait;


public class Search extends Utils{

	
	@Test(dataProvider="Keyword")
	public void find_book_with_keyword_search(String ASIN,String Title,String Keyword){
		System.out.println("Searching: " + Keyword + " for "+ Title);
		String sResults = "false";
		Driver.findElement(By.id("twotabsearchtextbox")).clear();
		Driver.findElement(By.id("twotabsearchtextbox")).sendKeys(Keyword);
		Driver.findElement(By.cssSelector("#nav-search > form > div.nav-right > div > input")).click();
		WebDriverWait wait = new WebDriverWait(Driver, 15);
		wait.until(ExpectedConditions.elementToBeClickable(By.id("s-results-list-atf")));
		int l = 0;
		int r = 1;
		int intPage = 1;
		while (sResults.equals("false")){
			
			
			for (int i=0;i<=15;i++)
			{
				
				String sValue = null;
				WebElement element = Driver.findElement(By.cssSelector("#result_" + l));
				sValue = Driver.findElement(By.cssSelector("#result_" + l)).getAttribute("data-asin");
				//System.out.println(sValue);
				
				if(sValue.equals(ASIN))
				{
				((JavascriptExecutor) Driver).executeScript("arguments[0].scrollIntoView(true);",element);
			      System.out.println(Title + " found on page:" + intPage +" line "+ (i+1));
			      Reporter.log(Title + " found on page:" + intPage +" line "+ (i+1));
			      sResults = "true";		
			       
				}
				l++;
			}
			if (sResults.equals("false")){
				//System.out.println("Could not find on page: " + intPage);
//				if (r == 29){
//					System.out.println(intPage +".");	
//					r = 1;
//				}
//				else{
//					System.out.print(intPage +".");	
//					r++;
//					}
				wait.until(ExpectedConditions.elementToBeClickable(By.id("pagnNextString")));				
				Driver.findElement(By.id("pagnNextString")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.id("s-results-list-atf")));
				intPage++;	
			}
			
			
			}
		
	}
	
	
}
