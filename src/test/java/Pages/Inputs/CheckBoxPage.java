/*
 * A input page
 * FGhetler
 */

package Pages.Inputs;

import Pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class CheckBoxPage extends BasePage {

    @AndroidFindBy(id = "1254")
    private MobileElement checkBox;

    @AndroidFindBy(id = "sdfdsf")
    private MobileElement display;


    public CheckBoxPage(AppiumDriver driver) {
        super(driver);
    }


    public void touchCheckBox() {
        checkBox.click();
    }


    public String getCheckBoxDisplay(){
        return display.getText();
    }
}
