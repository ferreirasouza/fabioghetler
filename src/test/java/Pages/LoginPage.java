/*
 * A Login page
 * FGhetler
 */
package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class LoginPage extends BasePage {
    private static final int KEYBOARD_ANIMATION_DELAY = 1000;
    private static final int WAIT_TIME = 2000;
    private static final String sString1= "";
    private static final String sString2= "";
    private static final String sString3= "";
    private static final String sString4= "";
    private static final String sString5= "";
    private static final String sString6= "";
    private static final String sString7= "";

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath="//*[@class='android.widget.Button']")
    private WebElement loginButton;

    @iOSXCUITFindBy()
    @AndroidFindBy(id="android:id/message")
    private WebElement errorMessage;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath="//*[@class='android.widget.ImageView']")
    private WebElement logo;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "login")
    private WebElement firstLoginButton;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "username")
    private WebElement usernameField;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "password")
    private WebElement passwordField;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "android:id/button1")
    private WebElement yesBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "android:id/button2")
    private WebElement noBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "com.android.chrome:id/terms_accept")
    private WebElement privacyBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "com.android.chrome:id/negative_button")
    private WebElement privacyRefuseBtn;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='NÃO, OBRIGADO']")
    private WebElement privacyRefuseBtn2;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath="//*[@class='android.view.View'][1]")
    private WebElement messageLogin;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "RememberLogin")
    private WebElement rememberLoginBtn;

    private NavigationPage navPage;
    private final String[] comCharge = {sString1, sString2, sString3, sString4, sString5,sString6,sString7};
    private final String[] com=null;

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }

    public boolean login(String username, String password, String sString){
        EnvironmentControl environmentControl = new EnvironmentControl();
        environmentControl.loadFile("UserData");
        boolean usernameStatus = false;
        log.println(driver.getContextHandles());
        if(!waitForElement(driver,10,usernameField)){
            checkFirstLogin();
        }
        try{
        if(!waitForElement(driver,2,usernameField)){
            log.println("Looking for possible device warn message...");
            privacyNoticeMsg();
            privacyRefuseBtn();
            }
        if(waitForElement(driver,5,usernameField)){
                usernameStatus = sendKeysToElement(username, usernameField, false);
                waitForElement(driver,2,passwordField);
            passwordField.clear();
            passwordField.sendKeys(password);
            hideKeyb();
            waitForElement(driver, 2,loginButton);
            loginButton.click();
            return usernameStatus;
        }else{
            log.println("Element Username was not found in Login page.");
            driver.quit();
        }
        }catch(Exception w){
            w.printStackTrace();
        }
        driver.context("NATIVE_APP");
        return usernameStatus;
    }

    public void privacyNoticeMsg(){
        if(waitForElement(driver, 1, privacyBtn)){
            log.println("Clicou");
                  privacyBtn.click();
        }
    }


    private void whiteScreen(){
        if(waitForElement(driver, 2, logo)){
            log.println("Login screen loaded");
        }else{
            takeScreenshot(driver,"WhiteScreen","LoginPage");
            driver.quit();
        }
    }
  private void privacyRefuseBtn(){
        if(waitForElement(driver, 1, privacyRefuseBtn)){
            log.println("A click was performed on privacy refuse button.");
            privacyRefuseBtn.click();
        }else if(isElemPresent(privacyRefuseBtn2)){
            log.println("A click was performed on second privacy refuse button.");
            privacyRefuseBtn2.click();
        }else{
            log.println("botao nao encontrado...");
        }
    }


    /**
     * Checks to see if back at login page
     *
     *  is back at login
     */
    private boolean checkFirstLogin() {
        boolean b = false;
        String sContext =  driver.getContext();
        log.println("checkFirstLogin: " + sContext);
            if(waitForElement(driver,1, firstLoginButton)) {
                log.println("Button Text =" + firstLoginButton.getText());
                firstLoginButton.click();
                b=true;
            } else {
                log.println("First Login was not completed.");
        }
            return b;
    }

}
