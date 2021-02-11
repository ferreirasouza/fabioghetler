/*
 * A Native page to be prepared for future
 * FGhetler
 */

package Pages.Native;

import Pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MediaPlayerPage extends BasePage {

    @AndroidFindBy(accessibility = "Content Playing")
    private MobileElement videoView;

    public MediaPlayerPage(AppiumDriver driver) {
        super(driver);
    }

    public String getMediaPlayerStatus() {
        return videoView.getAttribute("name");
    }
}
