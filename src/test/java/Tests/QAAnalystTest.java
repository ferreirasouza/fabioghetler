/*
 * A Simple Example of my Job
 * FG - This class is reserved for assertions only, do not manipulate page objects please.
 */

package Tests;

import Pages.EnvironmentControl;
import Pages.LoginPage;
import Pages.QaAnalystPage;
import Tests.AbstractBaseTests.TestBaseFG;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class QAAnalystTest extends TestBaseFG {

    private static final String CHECK_ADD_CUSTOMER_ERROR="THE TEST FAILED TO ADD A CUSTOMER.";
    private QaAnalystPage qaAnalyst;
    private static String sPathToFile, sDataFileJson;
    private final String DATA = "addCustomerData";
    private final String ADD_CUSTOMER_NAME = "addCustomer";
    private static final int NUM_THREADS = 3;

    EnvironmentControl environmentControl = new EnvironmentControl();

    @BeforeSuite
    public void preSetup(){
        try{
            EnvironmentControl environmentControl = new EnvironmentControl();
            environmentControl.envControl("env");
            sPathToFile = System.getProperty("user.dir") + propertiesCustomerData.getProperty("dataCustomerPath_file");
            sDataFileJson = propertiesCustomerData.getProperty("json_CustomerFileData");
            //globalTest.assignCategory("Customer");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @BeforeTest
    @Override
    public void setUpPage(){
        switch (executionOS) {
            case ANDROID:
                LoginPage loginPage = new LoginPage(driver);
                qaAnalyst = new QaAnalystPage(driver);
                environmentControl.loadFile("UserData");
                loginPage.login(environmentControl.UsL(1), environmentControl.UsL(2),null);
                break;
            case IOS:
                break;
        }
    }

    //Two ways to get Data from JSon Objects
    @Test(dataProvider=DATA, threadPoolSize=NUM_THREADS, priority=1)
    void checkQaAnalyst(JSONObject data){
        //First option: Get JSon from Data Provider
        JSONObject body = (JSONObject) data.get("body");
        try {
            body =(JSONObject) body.get("Customer");
            Assert.assertTrue(qaAnalyst.QaGhetler(body.get("Name")
                    .toString(),body.get("FrameWork").toString(),body.get("CodeLanguages").toString()),CHECK_ADD_CUSTOMER_ERROR);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        //Second Option: Get Json direct from a Method
        JsonObjRetriever("Newton","test_name");

    }

    @Test(priority = 2)
    void checkQaAnalystSecond(){
        //Assert.assertTrue(qaAnalyst.QaGhetler("option2"),CHECK_ADD_CUSTOMER_ERROR);
    }

    @DataProvider(name = DATA)
    public Object[][] getNecessaryData() {
        String sPathToDataFile = sPathToFile + sDataFileJson;
        return genericProvider(ADD_CUSTOMER_NAME, sPathToDataFile);
        // ADD_CUSTOMER_NAME == dataType
        // passa como parametro a raiz do JSON
    }

    @Override
    public String getName() {
        return "QAAnalystPage";
    }
}