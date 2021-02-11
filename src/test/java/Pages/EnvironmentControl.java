package Pages;

import Tests.AbstractBaseTests.TestBaseFG;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

public class EnvironmentControl extends TestBaseFG {
    private static final String sString1= "";
    private static final String sString2= "";
    private static final String sString3= "";
    private static final String sString4= "";
    private static final String sString5= "";
    private static final String sString6= "";
    private static final String sString7= "";

    private final String[] comCharge = {sString1, sString2, sString3, sString4, sString5, sString6, sString7};
    private final String[] com = null;
    String concS;
    Properties props;
    static String[] temp;

    public String getName() {
        return null;
    }


    public void setUpPage() {

    }

    public String[] envControl(String sString) {
        String[] array = loadFile(sString);
        String sEnv = array[0];
        String sOS = array[2];
        String sUL = array[1];
        switch (sEnv) {
            case "PROD":
                env = ENVIRONMENT.PROD;
                switch (sOS) {
                    case "ANDROID":
                        executionOS = OS.ANDROID;
                        break;
                    case "IOS":
                        executionOS = OS.IOS;
                        break;
                }
                break;
            case "PREPROD":
                env = ENVIRONMENT.PREPROD;
                switch (sOS) {
                    case "ANDROID":
                        executionOS = OS.ANDROID;
                        break;
                    case "IOS":
                        executionOS = OS.IOS;
                        break;
                }
        }
        setUpAppiumFg(env, executionOS);
        return new String[]{sEnv, sOS, sUL};
    }


    public String[] loadFile(String sString) {
        String[] result = new String[0];
        String[] sEnvi;
        switch (sString) {
            case "env":
                String[] tagName = {"Env", "Lender", "Os", "Ul"};
                sEnvi = new String[tagName.length];
                String[] valor = new String[tagName.length];
                int i = 0;
                try {
                    props = ConnectToProp("enviData.properties");
                    for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements(); ) {
                        String name = (String) e.nextElement();
                        String value = props.getProperty(name);
                        log.println(sEnvi[i] = value);
                        i++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                temp=sEnvi;
                result = sEnvi;
                break;

            case "UserData":
                try {
                    Gerador gerador = new Gerador();
                    String sEnv;
                    sEnv = temp[0];
                    String sUL = temp[1];
                    int k = 0;
                    switch (sEnv) {
                        case "PREPROD":
                            switch (sUL) {
                                case "level1":
                                    props = ConnectToProp("preprod.properties");
                                    break;
                                case "level4":
                                    props = ConnectToProp("preprodFour.properties");
                                    break;
                            }
                            break;
                        case "PROD":
                            switch (sUL) {
                                case "level1":
                                    props = ConnectToProp("loadp.properties");
                                    break;
                                case "level4":
                                    props = ConnectToProp("loadp4.properties");
                                    break;
                            }
                            break;
                    }
                    for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements(); ) {
                        String name = (String) e.nextElement();
                        String value = props.getProperty(name);
                        comCharge[k] = value;
                        k++;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                result= new String[]{"User data loaded."};
                break;
        }
        return result;
    }

    public Properties ConnectToProp(String sPathSharedAPIMap) {
        Properties prop = null;
        InputStream input;
        String currentDir = System.getProperty("user.dir") + "/src/test/resources/";
        try {
            prop = new Properties();
            input = Thread.currentThread().getContextClassLoader().getResourceAsStream(currentDir + sPathSharedAPIMap);
            if (input == null) {
                prop.load(new FileInputStream(currentDir + sPathSharedAPIMap));
            } else {
                prop.load(input);
            }
        } catch (IOException e) {
            log.println("File " + sPathSharedAPIMap + " didn't load properly!");
        }

        return prop;
    }

    private String concL(int c) {
        switch (c) {
            case 1:
                try {
                    String[] strings = {comCharge[3], comCharge[5], comCharge[2], comCharge[4], comCharge[6]};
                    String str = Arrays.toString(strings);
                    //System.out.print("string" + str);
                    concS = str.replaceAll("[.,; \\[\\]'\"\"]", "");
                    //System.out.print("string: " + concS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    String[] strings = {comCharge[1], comCharge[0]};
                    String str = Arrays.toString(strings);
                    //System.out.print("string" + str);
                    concS = str.replaceAll("[.,; \\[\\]'\"\"]", "");
                    //System.out.print("string: " + concS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return concS;
    }

    public String UsL(int i) {
        return setupBuilder(concL(i));
    }

    public String setupBuilder(String sString) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < sString.length() - 1; i += 2) {
            String output = sString.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();

    }

    public void loaderData(String sData){

        try {
            int i=0;
            switch(sData){
                case"data1":
                    props=ConnectToProp("");
                    break;
                case"data2":
                    props=ConnectToProp("");
                    break;
            }
            for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
                String name = (String)e.nextElement();
                String value = props.getProperty(name);
                comCharge[i] = value;
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
