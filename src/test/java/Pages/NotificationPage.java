package Pages; /*
 * A Notification page
 * FGhetler
 */

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class NotificationPage extends BasePage {
    private static final int KEYBOARD_ANIMATION_DELAY = 1000;
    private static final int WAIT_TIME = 2000;

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "android:id/big_text")
    private WebElement notificationPush;

    protected NotificationPage(AppiumDriver driver) {
        super(driver);
    }

    private boolean checkNotification(){
        boolean b=false;
        try{
            log.println("Coming soon.");
            b=true;
        }catch(Exception e ){
            e.printStackTrace();
        }

        return b;
    }

    private boolean findNotification(){
        boolean b=false;
        try{
            log.println("Coming soon.");
            b=true;
        }catch(Exception e ){
            e.printStackTrace();
        }

        return b;
    }

    private boolean changeOwner(){
        boolean b=false;
        try{
            log.println("Coming soon.");
            b=true;
        }catch(Exception e ){
            e.printStackTrace();
        }

        return b;
    }

}
