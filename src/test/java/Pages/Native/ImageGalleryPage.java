/*
 * A Base class to build a image compare
 * FGhetler
 */


package Pages.Native;

import Pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ImageGalleryPage extends BasePage {

    @AndroidFindBy(className = "xpath")
    private MobileElement imageGalley;

    public ImageGalleryPage(AppiumDriver driver, MobileElement imageGalley) {
        super(driver);
        this.imageGalley = imageGalley;
    }

    public boolean imageGalleryIsDisplayed() {
        return imageGalley.isDisplayed();
    }
}
