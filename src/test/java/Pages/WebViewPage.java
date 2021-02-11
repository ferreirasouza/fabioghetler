/*
 * A Helper page
 * FGhetler
 */

package Pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebViewPage extends BasePage {
    private static final String WEB_HEADER_CSS_NAME = "a[name=\"\"]";
    private static final String TEXT_FIELD_CLASS = "android.widget.EditText";
    private static final int MAX_WEBSITE_LOAD_TIME = 10;
    private final ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();

    public WebViewPage(AndroidDriver driver) {
        super(driver);
        this.driver.set(driver);
    }


    public boolean goToUrl(String url) {
        MobileElement navBar = (MobileElement) driver.get().findElementByClassName(TEXT_FIELD_CLASS);
        return sendKeysToElement(url, navBar, true);
    }


    public boolean webDescriptionIsLoaded() {
        Object[] contextHandles = driver.get().getContextHandles().toArray();
        String androidVersion = driver.get().getCapabilities().getCapability("platformVersion").toString();

        if (versionLessThan(androidVersion, "4.4")) {
            return true;
        } else if (versionGreaterThanOrEqual(androidVersion, "6.0")) {
            return false;
        }

        String webViewContent = (String) contextHandles[contextHandles.length - 1];
        WebDriver webDriver = driver.get().context(webViewContent);

        try {
            WebDriverWait wait = new WebDriverWait(webDriver, MAX_WEBSITE_LOAD_TIME);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(WEB_HEADER_CSS_NAME)));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(WEB_HEADER_CSS_NAME)));
        } catch (TimeoutException e) {
            return false; // MAX_WEBSITE_LOAD_TIME timeout exceeded - bad internet connection.
        }

        WebElement webHeader = webDriver.findElement(By.cssSelector(WEB_HEADER_CSS_NAME));
        return webHeader.isDisplayed();
    }

    private int compareVersions(String version1, String version2) {
        String[] versions1 = version1.split("\\.");
        String[] versions2 = version2.split("\\.");

        int maxLength = Math.max(versions1.length, versions2.length);

        for (int i = 0; i < maxLength; i++) {
            int versionNumber1 = i < versions1.length ? Integer.parseInt(versions1[i]) : -1;
            int versionNumber2 = i < versions2.length ? Integer.parseInt(versions2[i]) : -1;

            if (versionNumber1 < versionNumber2) {
                return -1;
            } else if (versionNumber1 > versionNumber2) {
                return 1;
            }
        }

        return 0;
    }


    private boolean versionGreaterThanOrEqual(String version1, String version2) {
        return compareVersions(version1, version2) >= 0;
    }


    private boolean versionLessThan(String version1, String version2) {
        return compareVersions(version1, version2) == -1;
    }
}
