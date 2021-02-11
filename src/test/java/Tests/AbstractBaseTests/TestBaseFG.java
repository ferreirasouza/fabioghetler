package Tests.AbstractBaseTests;

import Pages.NavigationPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.annotations.*;
import util.JsonManipulators;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


public abstract class TestBaseFG extends JsonManipulators{
    private static boolean setupIsDone = false;
    public static ExtentReports extentReport;
    public ExtentTest globalTest;
    public Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    public static AppiumDriver<MobileElement> driver;
    public static OS executionOS;
    public static ENVIRONMENT env;
    private final ThreadLocal<NavigationPage> navigationPage;

    protected TestBaseFG() {
        navigationPage = new ThreadLocal<>();
    }

    public abstract String getName();

    @BeforeSuite()
    public void beforeSuite() {
        if (!setupIsDone) {
            setupProperties();// vem da JsonManipulators extends
        }
        extentReport = new ExtentReports(System.getProperty("user.dir") + "/src/test/java/specs/Customer/ExtentReports/ER_Customer_Results.html", true);
        extentReport.loadConfig(new File(System.getProperty("user.dir")+"/src/test/java/specs/Customer/ExtentReports/extent-config.xml"));
        extentReport.addSystemInfo("Created by", propertiesCustomerData.getProperty("userName"));
    }

    @BeforeTest
    public abstract void setUpPage();

    @BeforeTest
    public void initCustomer(final ITestContext testContext) {
        for (int i = 0; i < testContext.getAllTestMethods().length; i++) {
            System.out.println("Test name: " + testContext.getAllTestMethods()[i].getMethodName());
            ExtentTest test = extentReport.startTest(testContext.getAllTestMethods()[i].getMethodName());
            extentTestMap.put(i, test);
        }

    }
        public void setUpAppiumFg(ENVIRONMENT eEnv, OS eEnvOS) {
        JsonManipulators sKey=new JsonManipulators();
        try {
            env = eEnv;
            executionOS = eEnvOS;
            if (driver != null) {
                return;
            }
            DesiredCapabilities capabilities = new DesiredCapabilities();
            log.println("Appium is setting environment test for Android. ");
            final String URL_STRING = "http://localhost:4723/wd/hub";
            URL url = new URL(URL_STRING);
            switch (executionOS) {
                case ANDROID:
                    switch (env) {
                        case PREPROD:
                            File app = new File(System.getProperty("user.dir") + "\\apps\\android\\app-debug.apk");
                            capabilities.setCapability("deviceName", "emulator-5554");
                            capabilities.setCapability("platformName", Platform.ANDROID);
                            capabilities.setCapability("automationName", "UiAutomator2");
                            capabilities.setCapability("orientation","PORTRAIT");
                            capabilities.setCapability("appWaitPackage","com.aboutinf.ghetler.mobileautomation");
                            capabilities.setCapability("appWaitActivity",".ui.login.LoginActivity");
                            capabilities.setCapability("clearSystemFiles", true);
                            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 6000);
                            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
                            capabilities.setCapability("app", app.getAbsolutePath());
                            driver = new AndroidDriver<>(url, capabilities);
                            log.println(url);
                            checkSBrowser();
                            driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
                            log.println("Pre-prod environment was previously configured");
                            break;
                        case PROD:
                            File appP = new File(System.getProperty("user.dir") + "\\apps\\android\\prod-android-app-debug.apk");
                            capabilities.setCapability("deviceName", "33001a529b1472b5");
                            capabilities.setCapability("platformName", Platform.ANDROID);
                            capabilities.setCapability("automationName", "Appium");
                            capabilities.setCapability("orientation","PORTRAIT");
                            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 6000);
                            capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS,true);
                            capabilities.setCapability("app", appP.getAbsolutePath());
                            driver = new AndroidDriver<>(url, capabilities);
                            driver.getSessionDetails();
                            checkSBrowser();
                            driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
                            log.println("Prod environment was previously configured");
                            break;
                    }
                    break;
                case IOS:
                    switch (env) {
                        case PREPROD:
                            File app = new File(System.getProperty("user.dir") + "\\apps\\ios\\pre-prod-ios-app-debug.app");
                            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.2");
                            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
                            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
                            capabilities.setCapability("app", app.getAbsolutePath());
                            driver = new IOSDriver<>(url, capabilities);
                            log.println(url);
                            checkSBrowser();
                            driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
                            log.println("Pre-prod (iOS) environment");
                            break;
                        case PROD:
                            File appP = new File(System.getProperty("user.dir") + "\\apps\\ios\\prod-ios-app-debug.ipa");
                            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "13.2");
                            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
                            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
                            capabilities.setCapability(MobileCapabilityType.UDID, "66616b6520697061");
                            capabilities.setCapability("app", appP.getAbsolutePath());
                            driver = new IOSDriver<>(url, capabilities);
                            driver.getSessionDetails();
                            checkSBrowser();
                            driver.manage().timeouts().implicitlyWait(35, TimeUnit.SECONDS);
                            log.println("Prod (iOS) environment");
                            break;
                    }
            }
            if (getConnection() && isInternetWorking()) {
                driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
                log.println("Session ID: " + driver.getSessionId());
            } else {
                log.println("Wi-Fi or Internet connection was not established.");
            }
            driver.manage().logs().getAvailableLogTypes();
            driver.manage().logs().get("logcat");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BeforeTest()
    public void init(final ITestContext testContext) {
        globalTest = extentReport.startTest((this.getClass().getSimpleName()));
        TimeZone.setDefault(TimeZone.getTimeZone("America/Toronto"));
    }

    public Object[][] genericProvider(String dataType, String sPathToDataFile) {
        JSONParser parser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(sPathToDataFile));
            JSONArray data = (JSONArray) jsonObject.get(dataType);
            ArrayList<Object> zoom = new ArrayList();

            for (int i = 0; i < data.size(); i++) {
                JSONObject pageObj = (JSONObject) data.get(i);
                if (Boolean.parseBoolean(pageObj.get("do_assertions").toString())) {
                    zoom.add(data.get(i));
                }
            }

            Object[][] newData = new Object[zoom.size()][1];
            for (int i = 0; i < zoom.size(); i++) {
                newData[i][0] = zoom.get(i);
            }

            return newData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }



    public void getTimeStatus(){
        try{

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean getConnection() {
        boolean b = false;
        try {
            ConnectionState state = new ConnectionStateBuilder().withWiFiEnabled().build();
            b = state.isWiFiEnabled();
            log.println("connection state for Wi-Fi: " + b);
        } catch (Exception e) {
            log.println("Connection could not be available");
        }
        return b;
    }

    public boolean isInternetWorking() {
        boolean success = false;
        try {
            URL url = new URL("https://google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.connect();
            success = connection.getResponseCode() == 200;
            log.println("conection state for internet: " + success);
        } catch (UnknownHostException e) {
            log.println("It was not possible to get a HTTP Response Code from the server.");
            driver.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean checkSBrowser() {
        boolean b = false;
        String str1 = "";
        String str2 = "";
        //log.println("checking if SBrowser is installed...");
        if (driver.isAppInstalled("com.sec.android.app.sbrowser")) {
            log.println("Sbrowser detected!");
            b = true;
            try {
                log.println("Try removing SBrowser");
                driver.removeApp("com.sec.android.app.sbrowser");
                for (int i = 0; i < 3; i++) {
                    driver.removeApp("com.sec.android.app.sbrowser");
                }
                if (driver.isAppInstalled("com.sec.android.app.sbrowser")) {
                    log.println("It was not possible to remove SBrowser, but maybe is possible to undefine as a default browser.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    @AfterTest(alwaysRun=true)
    public void teardownExtendReport(final ITestContext testContext) {
        for (int i=0; i<testContext.getAllTestMethods().length; i++) {
            extentReport.endTest(extentTestMap.get(i));
        }
    }

    @AfterSuite
    public void tearDownAppium() {
        driver.quit();
    }

    @BeforeClass
    public void navigateTo() throws InterruptedException {
        navigationPage.set(new NavigationPage(driver));
    }

    @AfterClass
    public void restartApp() {
        driver.resetApp();
    }


    public enum OS {
        ANDROID,
        IOS,
    }

    public enum ENVIRONMENT {
        PREPROD,
        PROD,
    }
}