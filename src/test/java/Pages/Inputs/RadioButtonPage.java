
package Pages.Inputs;

import Pages.BasePage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class RadioButtonPage extends BasePage {

    @AndroidFindBy(id = "asa")
    private MobileElement radioButton1;

    @AndroidFindBy(id = "ddfds")
    private MobileElement radioButton2;

    @AndroidFindBy(id = "erwf")
    private MobileElement radioButton3;

    @AndroidFindBy(id = "etgd")
    private MobileElement radioText;

    public RadioButtonPage(AppiumDriver driver) {
        super(driver);
    }

    public String getRadioText() {
        return radioText.getText();
    }

    public void clickRadioButton1() {
        radioButton1.click();
    }

    public void clickRadioButton2() {
        radioButton2.click();
    }

    public void clickRadioButton3() {
        radioButton3.click();
    }
}
