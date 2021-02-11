package util;

import com.jayway.jsonpath.JsonPath;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONTokener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class JsonManipulators {
    public static Properties propertiesData,propertiesCustomerData;


    private static final String PATHTO_CUSTOMER_PROP = "Customer/PathProviderToData.properties";

    public static void setupProperties() {
        propertiesCustomerData = ConnectToPropAPI(PATHTO_CUSTOMER_PROP);
    }

    public static Properties ConnectToPropAPI(String sPathSharedAPIMap) {
        Properties propAPI = null;
        String currentDir = null;

        try {
            propAPI = new Properties();
            currentDir = System.getProperty("user.dir") + "/src/test/java/specs/";
            propAPI.load(new FileInputStream(currentDir + sPathSharedAPIMap));
        } catch (IOException e) {
            System.out.println("File "+currentDir + sPathSharedAPIMap+" didn't load properly!");
        }

        return propAPI;
    }

    public Object JsonObjRetriever(String sSuite,String sKey) {
        List<Object> obj = null;
        try {
            obj = jsonReader("testOne.json", sSuite, sKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    private static JSONObject jsonObjUserPerm;
    private List<Object>  jsonReader(String sDataFileJson, String sSuiteName, String sKey){
        ArrayList<String> vamover;
        List<Object> stringList = null;
        String result="";
        int j=0;
        try{
            jsonObjUserPerm = (JSONObject) new JSONParser().parse(new FileReader(System.getProperty("user.dir") +"\\src\\test\\resources\\" + sDataFileJson));
            List<Object> objList = (JsonPath.read(jsonObjUserPerm, "$..data_name"));
            switch(sSuiteName){
               case "Newton":
                   System.out.println(objList.get(j));
                   vamover= (JsonPath.read(jsonObjUserPerm, "$.data_tests["+j+"].data_result.[*].time"));
                   CheckStatus("pass",vamover,j);
                   CheckStatus("fail",vamover,j);
                   CheckStatus("blocked",vamover,j);
                   Convert(vamover);
                   System.out.println("========= "+objList.get(j) +" end =========\n");

                   stringList= (JsonPath.read(jsonObjUserPerm, "$.."+ sKey +""));

                   break;
               case"Darwin":
                   j=1;
                   System.out.println(objList.get(j));
                   vamover= (JsonPath.read(jsonObjUserPerm, "$.data_tests["+j+"].data_result.[*].time"));
                   CheckStatus("pass",vamover,j);
                   CheckStatus("fail",vamover,j);
                   CheckStatus("blocked",vamover,j);
                   Convert(vamover);
                   System.out.println("========= "+objList.get(j) +" end =========\n");
                   break;
               default:
                   throw new IllegalStateException("Unexpected value: " + sSuiteName);
           }
        }catch(ParseException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return stringList;
    }

    private void Convert(ArrayList<String> list) {
        try {
            String temp[] = new String[list.size()];
            double temp2[] = new double[list.size()];
            int i = 0;
            System.out.println("Tests that took more than 10 seconds to execute:");
            for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); i++) {
                temp[i] = iterator.next();
                if (!temp[i].isEmpty()) {
                    temp2[i] = Double.parseDouble(temp[i]);
                }
                if (temp2[i] > 10) {
                    System.out.println(temp2[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CheckStatus(String sStatus,ArrayList<String> list,int j){
        try{
            switch(sStatus){
                case "pass":
                    list= (JsonPath.read(jsonObjUserPerm, "$.data_tests["+j+"].data_result.[?(@.status == 'pass')]"));
                    System.out.println("Tests Passed: " + list.size());
                    System.out.println("Details:  " + list.toString());
                    break;
                case "fail":
                    list= (JsonPath.read(jsonObjUserPerm, "$.data_tests["+j+"].data_result.[?(@.status == 'fail')]"));
                    System.out.println("Tests Failed: " + list.size());
                    System.out.println("Details:  " + list.toString());
                    break;
                case "blocked":
                    list= (JsonPath.read(jsonObjUserPerm, "$.data_tests["+j+"].data_result.[?(@.status == 'blocked')]"));
                    System.out.println("Tests Blocked: " + list.size());
                    break;

            }
        }catch(Exception exception){
            exception.printStackTrace();
        }
    }

    private JSONObject readFile(String path){
    try{
        jsonObjUserPerm = (JSONObject) new JSONParser().parse(new FileReader(System.getProperty("user.dir")+path));
    }catch(Exception e){
        e.printStackTrace();
    }
        return jsonObjUserPerm;
    }

    private List<Object> objectFinder(JSONObject obj, String sData){
        List<Object> objList = null;
        try {
            objList = (JsonPath.read(obj, "$.."+sData));
        }catch(Exception exception){
            exception.printStackTrace();
        }
        return objList;
    }

    public String getTestLevel(String level){

        try{
            objectFinder(readFile("testOne.json"),"test_name").get(0);

        }catch(Exception e){
           e.printStackTrace();
        }

        return "";

    }
    public static boolean getSchemaValidation(String sPathToSchema, String sPathToFile, String sSchemaFileName, String sResultFileName) {
        try {
            InputStream inputStream = new FileInputStream(sPathToSchema + sSchemaFileName);
            org.json.JSONObject rawSchema = new org.json.JSONObject(new JSONTokener(inputStream));

            InputStream inputStreamResult = new FileInputStream(sPathToFile + sResultFileName);
            org.json.JSONObject rawSchemaResult = new org.json.JSONObject(new JSONTokener(inputStreamResult));

            Schema schema = SchemaLoader.load(rawSchema);

            if (rawSchemaResult.length() > 0 ) {
                schema.validate(rawSchemaResult); // throws a ValidationException if this object is invalid

            }


            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
