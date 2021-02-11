/*
* A Helper page
        * FGhetler
        **/

package Pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class TabViewPage extends BasePage{
    private static final double START_OFFSET = 0.95;
    private static final double END_OFFSET = 0.05;
    private static final int ALERT_POP_UP_DELAY = 1000;

    @AndroidFindBy(id = "container_body")
    private MobileElement tabViewContainer;
    private final AppiumDriver driver;


    public TabViewPage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void turnPageLeft() throws InterruptedException {
        acceptBadVideoAlert();
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * START_OFFSET);
        int endX = (int) (size.width * END_OFFSET);
        int startY = size.height / 4;
        //driver.swipe(startX, startY, endX, startY, SWIPE_DURATION);

        acceptBadVideoAlert();
    }

    private void acceptBadVideoAlert() throws InterruptedException {
        Thread.sleep(ALERT_POP_UP_DELAY);

        if (!driver.findElementsById("android:id/message").isEmpty()) {
            WebElement okButton = driver.findElementById("android:id/button1");
            okButton.click();
        }
    }

    public void acceptMessage() {
        try {
            acceptBadVideoAlert();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public boolean findTextOnScreen(String sText){
        boolean b=false;
        try{
            WebElement we = driver.findElementByXPath(sText);
            b=true;
        }catch(NoSuchElementException elementException){
            elementException.printStackTrace();
        }
        return b;
    }
}
