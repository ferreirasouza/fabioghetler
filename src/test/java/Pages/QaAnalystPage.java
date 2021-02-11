/*
 * An Add Customer page
 * Fghetler
 */
package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.util.NoSuchElementException;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;


public class QaAnalystPage extends BasePage {

    @iOSXCUITFindBy()
    @AndroidFindBy(id="name")
    private WebElement name;

    @iOSXCUITFindBy()
    @AndroidFindBy(id="jenkinsBasics")
    private WebElement jenkins;

    @iOSXCUITFindBy()
    @AndroidFindBy(id="fulltimePosition")
    private WebElement fullTimeRdBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id="contractPosition")
    private WebElement contractRdBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "testAutomationName")
    private WebElement frameWork;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "codeLanguage")
    private WebElement codeLanguage;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "android:id/button1")
    private WebElement yesBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "android:id/button2")
    private WebElement noBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "submit")
    private WebElement submit;

    public QaAnalystPage(AppiumDriver driver) {
        super(driver);
    }

    private void AddName(String sName){
        try{
            waitForElement(driver,5,name);
            name.click();
            name.sendKeys(sName);
            hideKeyb();
        }catch (NoSuchElementException e){
            e.printStackTrace();
            driver.quit();
        }
    }

    private void AddFrameWork(String sTechnology){
        try{
            frameWork.click();
            frameWork.sendKeys(sTechnology);
            hideKeyb();
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
    }

    private void AddCodeLanguage(String sCodeLanguage){
        try{
            codeLanguage.clear();
            codeLanguage.sendKeys(sCodeLanguage);
            hideKeyb();
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    private void selectRadioButton(String rdButton){
        try{
            switch(rdButton){
                case "Contract":
                    log.println("My preference is Full time");
                    break;
                case "Full time":
                    fullTimeRdBtn.click();
                    break;
            }
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }

    }

    private void selectBox(){
        try{
            waitForElement(driver,4,jenkins);
            jenkins.click();
            takeScreenshot(driver,"Jenkins","box");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void submit(){
        try{
            waitForElement(driver,4,submit);
            submit.click();
            takeScreenshot(driver,"Jenkins","box");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void MessageRelay (){
        try{
            waitForElement(driver,4,yesBtn);
            yesBtn.click();
            takeScreenshot(driver,"Message","Alert");
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public boolean QaGhetler(String name,String frame,String language){
        boolean b=false;
        try {
            AddName(name);
            AddFrameWork(frame);
            AddCodeLanguage(language);
            selectRadioButton("Full time");
            selectBox();
            submit();
            MessageRelay();
            b=true;
        }catch(org.openqa.selenium.NoSuchElementException e){
            e.printStackTrace();
        }
        return b;
    }
}