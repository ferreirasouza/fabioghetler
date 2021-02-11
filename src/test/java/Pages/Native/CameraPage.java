/*
 * A Helper Class to access camera
 * FGhetler
 */

package Pages.Native;

import Pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class CameraPage extends BasePage {

    @iOSXCUITFindBy()
    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_button")
    private MobileElement cameraPermission;
    private final AppiumDriver driver;

    public CameraPage(AppiumDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void setCameraPermission(){
        if(waitForElement(driver,1,cameraPermission)){
            clickElement(cameraPermission);
        }
    }
}
