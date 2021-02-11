/*
 * A Helper page
 * FGhetler
 */

package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;



public class NavigationPage extends BasePage{

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='\uE5D2']")
    private WebElement menu;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='\uE5C4']")
    private WebElement back;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='Log Out']")
    private WebElement logOut;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = "//*[@class='android.widget.ProgressBar")
    private WebElement progressBar;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='Add New']")
    private WebElement btnAddCustomer;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='Customers']")
    private WebElement btnCustomerList;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='\uE5C4']")
    private WebElement backNav;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "com.android.chrome:id/toolbar")
    private WebElement toolBar;

    @iOSXCUITFindBy()
    @AndroidFindBy(xpath = ".//*[@text='More Filters']")
    private WebElement moreFilters;
    private final AppiumDriver driver;


    public NavigationPage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean logout() {
        boolean b = false;
        try {
            log.println("logout in action...");
            if (waitForElement(driver, 30, menu)) {
                while (!waitForElement(driver, 1, btnAddCustomer)) {
                    menu.click();
                }
            }
                waitForElement(driver, 10, logOut);
                logOut.click();
                if (waitForElement(driver, 20, toolBar)) {
                    b = true;
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return b;
    }

    private void accessMenu(){
        try {
            log.println("Opening Menu...");
            if(waitForElement(driver,30,menu)){
                menu.click();
            }
            if(!waitForElement(driver,5,logOut)){
                log.println("Trying to open Menu again...");
                menu.click();
            }
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }

    public void accessCustomer(){
        try{
            waitForElement(driver,10,back);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void backOneScreen(){
        try {
            log.println("Navigation is backing one screen");
            if(waitForElement(driver, 25, backNav)){
                backNav.click();
                log.println("Back one screen executed successfully");
            }else{
                log.println("The element Nav back button wasn`t available on the screen." +
                        "The method wasn`t executed. ");
            }



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void accessAddCustomer(){
        try{
            log.println("Accessing Menu...");
            accessMenu();
            if(waitForElement(driver,30,btnAddCustomer)){
                log.println(btnAddCustomer.getText());
                btnAddCustomer.click();
                log.println("The Add Customer button received a click.");
                if(isElemPresent(moreFilters)){
                    accessAddCustomer();
                }
            }else{
                log.println("It took more then 30 seconds" +
                        " to find Add Customer button on the menu. Test aborted.");
                driver.quit();
            }

        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
    }
}
