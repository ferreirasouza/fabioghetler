/*
 * A Login page
 * FGhetler
 */
package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import java.io.File;

public class ExamplePage extends BasePage {

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement loginButton;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement firstLoginButton;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement usernameField;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement passwordField;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement privacyBtn;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement privacyRefuseBtn;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement privacyRefuseBtn2;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement messageLogin;

    @iOSXCUITFindBy(xpath = ".elements()[0]")
    private WebElement rememberLoginBtn;

    private NavigationPage navPage;
    private final AppiumDriver driver;

    public ExamplePage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public boolean takeScreenshot(final String name) {
        String screenshotDirectory = System.getProperty("appium.screenshots.dir", System.getProperty("java.io.tmpdir", ""));
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        return screenshot.renameTo(new File(screenshotDirectory, String.format("%s.png", name)));
    }

    public boolean exClick(){
        boolean b=false;
        try{
            driver.findElement(By.name("Add")).click();
            driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATextField[1]")).sendKeys("Complete Taxes");
            driver.findElement(By.name("Save")).click();
            driver.findElement(By.xpath("//UIAApplication[1]/UIAWindow[1]/UIATableView[1]/UIATableCell[4]")).click();
            b=true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }

}
