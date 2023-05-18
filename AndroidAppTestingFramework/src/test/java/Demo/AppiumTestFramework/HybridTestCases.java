package Demo.AppiumTestFramework;

import java.time.Duration;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Demo.AppiumFramework.HybridCapabilities;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.AndroidTouchAction;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;   
import java.io.IOException;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;

public class HybridTestCases extends HybridCapabilities{
	AndroidDriver<AndroidElement> driver ;
	
	@BeforeTest()
	public void setup() throws IOException, InterruptedException {


//		To start Appium by Default sevices
 		startserver().start() ; //	worKing
		
		driver = capability(devicename, platformname, apppackage, appactivity, chromedriver) ; // Don't Working
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS) ;
	}
	
	
	

	@Test( enabled = true , priority = 0 )
	public void login() throws IOException, InterruptedException {
		
 		
		AndroidTouchAction ac = new AndroidTouchAction(driver) ;
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/spinnerCountry")).click() ;
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Angola\"))")) ;
		AndroidElement Ind = driver.findElement(MobileBy.AndroidUIAutomator("text(\"Angola\")")) ;
		
		ac.tap(tapOptions().withElement(element(Ind))).perform();
		
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/nameField")).sendKeys("Shaktimaaan") ;
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/radioMale")).click() ;
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/btnLetsShop")).click() ;
		
//		to stop appium service
		startserver().stop();
		
//		adb -s <device_id> emu kill
//		Runtime.getRuntime().exec("adb -s Aditya emu kill" );
		
	}
	
	
	@Test(enabled = false)
	public void InvalidLogin() {
			
			driver.findElement(MobileBy.id("com.androidsample.generalstore:id/spinnerCountry")).click();
			
			driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Aruba\"))")).click();


	        String selectedCountry = driver.findElement(MobileBy.id("android:id/text1")).getText();
	        Assert.assertEquals(selectedCountry, "Aruba");
			
	        //Not Passing the name in field for getting Error
			driver.findElementById("com.androidsample.generalstore:id/radioMale").click();
			
			driver.findElementById("com.androidsample.generalstore:id/btnLetsShop").click();
		
			//whenever you find any kind of pop up where you are not able to catch the error message locator
			//try this
			String errorMSG = driver.findElementByXPath("//android.widget.Toast[1]").getAttribute("name");
		    System.out.println(errorMSG);
		}
	
	
	
	@Test(enabled = false )
	public void AddToCartSinglepro() {
		AndroidTouchAction ac = new AndroidTouchAction(driver) ;	
		
//		Count of all products for visible Elements
		int p = driver.findElements(MobileBy.className("android.widget.LinearLayout")).size() ;
		System.out.println(p);
		
//		Add to Cart
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/productAddCart")).click() ;
		String prodName = driver.findElement(MobileBy.id("com.androidsample.generalstore:id/productName")).getText() ;
		String count = driver.findElement(MobileBy.id("com.androidsample.generalstore:id/counterText")).getText() ;
		Assert.assertEquals(count, "1") ;
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click() ;
		Assert.assertEquals(prodName, "Air Jordan 4 Retro");
	}
	
	
	@Test(enabled = false )
	public void AddToCart() throws InterruptedException {
		AndroidTouchAction ac = new AndroidTouchAction(driver) ;	

//		Add to Cart 
		driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Air Jordan 9 Retro\"))")) ;
		driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click() ;
		
//	   getting size of all the products List
       int proSize = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size() ;
     
//    loop over the Products
      for( int i = 0 ; i<proSize ; i++ ) {
    	  
//    	  Getting each product name 
    	  String proName =  driver.findElements(By.id("com.androidsample.generalstore:id/productName")).get(i).getText();
    	  
//    	  Checking it is same to our requirements
    	  if( proName.equalsIgnoreCase("Air Jordan 9 Retro") ) {
    		  
//    		  Add to card that particular product 
    		  driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart")).get(i).click();
    		  break; 
    	  }
    	  
      }
	
		
//		String Cartcount = driver.findElement(MobileBy.id("com.androidsample.generalstore:id/counterText")).getText() ;
//		Assert.assertEquals(Cartcount, "1") ;
//		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click() ;
//		Assert.assertEquals(prodName, "Air Jordan 4 Retro");
	}
	
	
	@Test(enabled = false,  priority = 2)
	
	public void checkPrice() throws InterruptedException {
//		      for adding first two Products
		      driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click() ;
		      driver.findElements(By.xpath("//*[@text='ADD TO CART']")).get(0).click() ;
		      
		      driver.findElement(MobileBy.id("com.androidsample.generalstore:id/appbar_btn_cart")).click() ;
		      
		      Thread.sleep(5000);
		      String p1 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(0).getText() ;

//		      Remove '$' symbol
		      p1 = p1.substring(1) ;
		      Double price1 = Double.parseDouble(p1) ;
		      
		      String p2 = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice")).get(1).getText() ;
		      p2 = p2.substring(1) ;
		      Double price2 = Double.parseDouble(p2) ;
		      
		      Double addedPrice = price1 + price2 ;

		      String finalp = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText() ;
		      finalp = finalp.substring(1) ;
		      Double finalPrice = Double.parseDouble(finalp) ;
		      
		      Assert.assertEquals(addedPrice, finalPrice) ;
		      
	}
	
	
	
//	Send me e-mails on discounts related to selected products in future
	
	
	@Test(enabled = false ,   priority = 3)
	public void visitWeb() throws InterruptedException {
		
		AndroidTouchAction ac = new AndroidTouchAction(driver) ;	
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Send me e-mails on discounts related to selected products in future\")")).click() ;
		
		AndroidElement condition = driver.findElement(MobileBy.AndroidUIAutomator("text(\"Please read our terms of conditions\")")) ;
		
		ac.longPress(longPressOptions().withElement(element(condition)).withDuration(Duration.ofSeconds(3))).release().perform() ; 
		
		driver.findElement(MobileBy.id("android:id/button1")).click();
		
		driver.findElement(MobileBy.id("com.androidsample.generalstore:id/btnProceed")).click();
		
		Thread.sleep(10000) ;
		
//		moved to Web application 
//		below code will help to understand the Context of web application
		
		Set<String> contextNames =  driver.getContextHandles();
		
		for( String contextName: contextNames) {

			
//			if this shows netive and web app then is it HYBRID
//			or show Native then it is NATIVE
			System.out.println(contextName);
		}
//	    NATIVE_APP
//	    WEBVIEW_com.androidsample.generalstore
		
//		Switching to Web Application
		driver.context("WEBVIEW_com.androidsample.generalstore") ;
		
//		Doing something in web
		 Boolean title = driver.getTitle().contains("Google") ;
		 Assert.assertTrue(title) ;
		 driver.findElement(By.xpath("//*[@name='q']")).sendKeys("maasai" , Keys.ENTER) ;
		 
//		 Switching again to Native Application
		 driver.context("NATIVE_APP") ;
		 driver.pressKey( new KeyEvent(AndroidKey.APP_SWITCH) ) ;
		 
	}

		
		
	
}