package Demo.AppiumFramework;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class HybridCapabilities {
	
	
	public static String devicename;
	public static String platformname;
	public static String apppackage;
	public static String appactivity;
	public static String chromedriver;
	public AppiumDriverLocalService service;
	
//	Function - To start the Appium Server
	public AppiumDriverLocalService startserver() {
		
		boolean flag = checkifserverisRunning(4723);
		if(!flag){
				service = AppiumDriverLocalService.buildDefaultService();
		}
		return service;
	}
	
//	Function - To check Default PORT is running or not
	public static boolean checkifserverisRunning(int port)
	{
		boolean isServerRunning=false;
		ServerSocket serversocket;
		try{
			serversocket = new ServerSocket(port);
			serversocket.close();
		}
		catch(IOException e)
		{
			isServerRunning = true;
		}
		finally {
			serversocket=null;
		}
		return isServerRunning;
	}
	
//	Function - To start the Android Emulator
	public static void startemulator() throws InterruptedException, IOException {
		Runtime.getRuntime().exec("C:\\Users\\harsh\\Documents\\workspace-spring-tool-suite-4-4.18.0.RELEASE\\AndroidAppTestingFramework\\emulator.bat" );
		Thread.sleep(10000);
	}
	
	
//	Function - To set All mandatory Capability for Device
	public static AndroidDriver<AndroidElement> capability(String devicename, String platformname,String apppackage,String appactivity,String chromedriver) throws IOException, InterruptedException {
		//this is for fetching data from global poroperties
		FileReader fis = new FileReader(System.getProperty("user.dir")+ "\\src\\main\\java\\Globle.properties" );
		Properties ps = new Properties();
		ps.load(fis);
		
		devicename = ps.getProperty("DeviceName");
		platformname = ps.getProperty("PlatformName");
		apppackage = ps.getProperty("AppPackage");
		appactivity = ps.getProperty("AppActivity");
		chromedriver = ps.getProperty("ChromeExecutable");
		
		if(devicename.contains("Aditya")) {
			startemulator();
		}
		
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability(MobileCapabilityType.DEVICE_NAME, devicename);
		dc.setCapability(MobileCapabilityType.PLATFORM_NAME, platformname);
		dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, apppackage);
		dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, appactivity);
		//below two are added for more info this part is not mandatory   
		dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		dc.setCapability(MobileCapabilityType.NO_RESET, true);
		//hybrid capability //driver
		dc.setCapability(AndroidMobileCapabilityType.CHROMEDRIVER_EXECUTABLE, chromedriver);
		
		
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"),dc);
		return driver;
	}
	
}