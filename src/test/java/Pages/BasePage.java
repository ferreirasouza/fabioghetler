/*
 * A Base page
 * FGhetler
 */
package Pages;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public abstract class BasePage {
    private boolean varBoolean=false;

    protected final AppiumDriver driver;
    private int cont=0;
    private final int stopLoop=0;
    private final String[] comCharge = {sString1, sString2, sString3, sString4, sString5,sString6,sString7};
    String concS;
    private static final String sString1= "";
    private static final String sString2= "";
    private static final String sString3= "";
    private static final String sString4= "";
    private static final String sString5= "";
    private static final String sString6= "";
    private static final String sString7= "";
    Properties props;

    long start;
    long elapsedTime;
    long seg;
    long minutes;

    protected BasePage(AppiumDriver driver) {
        this.driver = driver;
            PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    protected boolean sendKeysToElement(String input, WebElement element, boolean appendNewLine){
        final int MAX_ATTEMPTS = 3;
        int attempts = 0;
        do {
            waitForElement(driver,10,element);
            element.click();
            element.clear();
            if (appendNewLine) {
                element.sendKeys(input + "\n");
                driver.hideKeyboard();
            } else {
                element.sendKeys(input);
                driver.hideKeyboard();
            }
        } while (!element.getText().contains(input) && ++attempts < MAX_ATTEMPTS);
        return element.getText().contains(input);
    }

    public boolean isElemPresent(WebElement element){
        boolean b =false;
        try {
            if(element.isDisplayed()) {
                b = true;
            }
        }catch(Exception ignore){
            log.println("The element:" + element + "was not found");
        }

        return b;
    }

    public void scrollElementByText(String sString){
        try {
            WebElement element = driver.findElementByXPath("//*[@text='" + sString + "']");
            scrollClickElem(element);
            if (isElemPresent(element)) {
                element.click();
            } else {
                verticalScrollDown(1000);
                element.click();
            }
        }catch(Exception e){
            verticalScrollDown(1000);
        }
    }

    public void verticalScrollDown(int duration)
    {
        //log.println("Method: verticalDown scrolling is in action..." );
        driver.context("NATIVE_APP");
        log.println("Context: " + driver.getContext());
        try {
            Dimension size = driver.manage().window().getSize();
            int startY = (int) (size.height * 0.60);
            int endY = (int) (size.height * 0.30);
            int startX = (int) (size.height * 0.40);
            int endX = (int) (size.width * 0.20);
            log.println("startY: " + startY + "/" + "endY: " + endY + "/" + "startX: " + startX + "/" + "endX: " + endX);
            TouchAction touchAction = new TouchAction(driver);
            PointOption pointStart = PointOption.point(startX, startY);
            PointOption pointEnd = PointOption.point(endX, endY);
            WaitOptions waitOption = WaitOptions.waitOptions(Duration.ofMillis(1000));
            touchAction.press(pointStart).waitAction(waitOption).moveTo(pointEnd).release().perform();
            //log.println("Method: verticalDown scrolling FINISHED" );
        }catch (Exception e) {
            log.checkError();
            e.printStackTrace();
        }
    }
    public void verticalScrollUp(int duration)
    {
        log.println("vertical scrolling up is in action..." );
        //log.println("Context: " + driver.getContext());
        driver.context("NATIVE_APP");
        //log.println("Context: " + driver.getContext());
        try {
            Dimension size = driver.manage().window().getSize();
            int y_start = (int) (size.height * 0.75);
            int y_end = (int) (size.height * 0.90);
            int x = size.width / 2;
            //driver.swipe(x,y_start,x,y_end,1000);
            new TouchAction(driver).tap(
                    new PointOption().withCoordinates(x, y_start)).moveTo(
                    new PointOption().withCoordinates(x, y_end)).perform().release();
            log.println("Method: verticalUp scrolling is finished.");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void takeScreenshot(AppiumDriver driver, String sShotName, String sPageName) {
        String path = null;

        try {
            // Take screenshot and save it in source object
            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            // Define path where Screenshot will be saved
            path = System.getProperty("user.dir") + "/src/test/java/specs/ImageComparison/ScreenShots/" + sPageName + "/" + sShotName + ".png";
            //log.println(path);

            //Copy the source file at the screenshot path
            FileUtils.copyFile(source,  new File(path));
            log.println("Screenshot " + sShotName + " captured");
        } catch (IOException e) {
            log.println("Failed to capture screenshot:" + e.getMessage());
        } catch (WebDriverException wde) {
            log.println("Failed to capture screenshot:" + wde.getMessage());
        }

    }

    public void scrollClickElem(WebElement element) {
        //log.println("scrollClickElem: Started");
        try {
            if (waitForElement(driver,1,element)) {
                element.click();
                log.println("The element => " + element + " received a click.");
            }else{
                verticalScrollDown(1000);
                for(int i=0; i<6; i++) {
                    if (waitForElement(driver,1,element)) {
                        element.click();
                        log.println("The element => " + element + " received a click.");
                        break;
                    }else {
                        log.println("Looping for scrollClickElem: "+ i);
                        verticalScrollDown(1000);
                    }
                }
            }
        }catch(Exception ignore){
        }
        //log.println("scrollClickElem: finished");
    }

    public void fastVerticalScrollDown(){
        log.println("Fast scroll down ");
        try{
            for(int i=0;i<3; i++){
                verticalScrollDown(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void fastVerticalScrollUp(){
        log.println("Fast scroll up ");
        try{
            for(int i=0;i<6; i++){
                verticalScrollUp(1000);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void scrollUpToElem(WebElement element) {
        try{
            if (cont < 16) {
                if (isElemPresent(element)) {
                    log.println("Element is on screen at: " + element.getLocation());
                    takeScreenshot(driver, "elementLocation","General");
                } else {
                    verticalScrollUp(1000);
                    cont++;
                    scrollUpToElem(element);
                }
            } else {
                log.println("The element: " + element + " was not found on the screen.");
            }
        } catch (Exception ignore) {
        }
        cont=0;
        log.println("scrollDownToElem: finished");
    }
    public void scrollDownToElem(WebElement element) {
        boolean b=false;
        try{
        if (cont <=3) {
            if (isElemPresent(element)) {
                log.println("Element is on screen at: " + element.getLocation());
                b=true;
            } else {
                cont++;
                log.println("cont = " + cont);
                verticalScrollDown(1000);
                if(isElemPresent(element)) {
                    log.println("Element is on screen at: " + element.getLocation());
                    b=true;
                }else{
                    verticalScrollDown(1000);
                    scrollDownToElem(element);
                }
            }
        } else {
            log.println("The element: " + element + " was not found on the screen.");
        }
    } catch (Exception ignore) {
    }
        cont=0;
        log.println("scrollDownToElem: finished. " + "Result: " + b);

    }

    public void scrollUpClickElem(WebElement element) {
        //log.println("scrollClickElem: Started");
        try {
            if (isElemPresent(element)) {
                verticalScrollUp(1000);
                element.click();
            }else{
                if(varBoolean){
                    verticalScrollUp(1000);
                    //synchronToken(1000);
                }
                varBoolean=true;
                scrollUpClickElem(element);
            }
        }catch(Exception e){
            varBoolean=true;
            verticalScrollUp(1000);
            scrollUpClickElem(element);
        }
        //log.println("scrollClickElem: finished");
        varBoolean=false;
    }
    public void synchronToken(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignore) {
        }
    }


    public void clickElement(WebElement element){
        if(waitForElement(driver,5,element)){
            element.click();
            log.println("The element " + element + " received a click");
        }else{
             log.println("The element " + element + "was not found");
            }
    }

    public void hideKeyb(){
        try{
            driver.hideKeyboard();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static boolean waitForElement(AppiumDriver driver, int timeLimitInSeconds, WebElement element){
        boolean b = false;
        try{
            WebDriverWait wait = new WebDriverWait(driver, timeLimitInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            b = element.isDisplayed();
            return b;
        }catch(Exception e){
            log.println(e.getMessage());
            return b;
        }
    }

    public static boolean waitElemDisappear(AppiumDriver driver, int timeLimitInSeconds, WebElement element){
        boolean b = false;
        try{
            WebDriverWait wait = new WebDriverWait(driver, timeLimitInSeconds);
            wait.until(ExpectedConditions.invisibilityOf(element));
            b = element.isDisplayed();
            if(b){
                log.println("The element " + element + " did not disappeared when the method was executing." +
                        "Try to add more time to wait.");
            }else{
                b=true;
            }

        }catch(Exception e){
            log.println(e.getMessage());
        }
        return b;
    }

    public void timeStart(){
        try{
            start = System.nanoTime();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void timeEnd(){
        try{
            elapsedTime = System.nanoTime() - start;
            seg = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            minutes = (seg/60);
            long sec = (seg%60);
            long secs = sec * 60;
            log.printf("%02d:%02d", minutes, secs);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public boolean clickElementByText(String sString){
        boolean b = false;
        try {
            WebElement element = driver.findElementByXPath("//*[@text='" + sString + "']");
            if (isElemPresent(element)) {
                element.click();
                b=true;
            }else{
                log.println("The element " + sString + "wasn`t available on the screen.");
            }

        }catch(Exception e){
            log.println("The element " + sString + "wasn`t available on the screen.");
        }
        return b;
    }

}