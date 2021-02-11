package Tests.AbstractBaseTests;

import Pages.TabViewPage;
import Pages.WaitConfig;
import org.testng.annotations.BeforeClass;

public abstract class TabTestBase extends TestBaseFG {

    public abstract int pageIndex();


    @BeforeClass
    @Override
    public void navigateTo() throws InterruptedException {
        super.navigateTo();
        TabViewPage tabViewPage = new TabViewPage(driver);
        Thread.sleep(WaitConfig.VIEWPAGE_BEFORE_WAIT);
        for (int i = 0; i < pageIndex(); i++) {
            tabViewPage.turnPageLeft();
            Thread.sleep(WaitConfig.VIEWPAGE_BEFORE_AFTER);
        }
    }
}
