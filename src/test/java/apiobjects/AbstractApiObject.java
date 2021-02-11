package apiobjects;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import util.JsonManipulators;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created on Jan, 2018
 **/

public class AbstractApiObject extends util.JsonManipulators {

    public ResponseDataObj setResponseDataObj(JSONObject data, JSONObject jsonObjParam, String sPathToResult, String sObjType, RequestDataObj request) throws IOException, ParseException {
        ResponseDataObj response = new ResponseDataObj();

        switch (jsonObjParam.get("method").toString()) {
            case "POST":
                response.setResponse(request.getHttpClient(), request.getHttpPost());
                break;

            case "POST FormData":
                response.setResponse(request.getHttpClient(), request.getHttpPost());
                break;

            case "POST_URL_ENCODED":
                response.setResponse(request.getHttpClient(), request.getHttpPost());
                break;

            case "PUT":
                response.setResponse(request.getHttpClient(), request.getHttpPut());
                break;

            case "DELETE":
                response.setResponse(request.getHttpClient(), request.getHttpDelete());
                break;

            case "GET":
                response.setResponse(request.getHttpClient(), request.getHttpGet());
                break;
        }

        try {
            System.out.println(data.get("name").toString() + ": Response Time is: " + response.getResponseTime() + " ms");
        } catch (NullPointerException e) {
            //System.out.println("Response Time is: " + responseDataObj.getResponseTime() + " ms");
        }

        response.setResponseCode(response.getHttpResponse());
        try {
            System.out.println(data.get("name").toString() + ": Response Code of is: " + response.getResponseCode());
        } catch (NullPointerException e) {
            //System.out.println("Response Code of is: " + responseDataObj.getResponseCode());
        }

        switch (sObjType) {
            case "JSON_OBJ":
                response.setJsonResponse(response.getHttpResponse());
                break;

            case "JSON_ARR":
                response.setJsonArrayResponse(response.getHttpResponse());
                break;

            default:
                response.setJsonResponse(response.getHttpResponse());
                break;
        }

        try {
            FileWriter writeFile = new FileWriter(sPathToResult + "result" + data.get("name").toString() + ".json");
            writeFile.write(response.getJsonResponse().toJSONString().replace("\\", ""));
            writeFile.flush();
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        } catch (IOException e) {
                e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return response;
    }

    public ResponseDataObj setResponseDataObj(JSONObject data, JSONObject jsonObjParam, String sObjType, RequestDataObj request) throws IOException, ParseException {
        ResponseDataObj response = new ResponseDataObj();

        switch (jsonObjParam.get("method").toString()) {
            case "POST":
                response.setResponse(request.getHttpClient(), request.getHttpPost());
                break;

            case "PUT":
                response.setResponse(request.getHttpClient(), request.getHttpPut());
                break;

            case "DELETE":
                response.setResponse(request.getHttpClient(), request.getHttpDelete());
                break;

            case "GET":
                response.setResponse(request.getHttpClient(), request.getHttpGet());
                break;
        }

        try {
            System.out.println(data.get("name").toString() + ": Response Time is: " + response.getResponseTime() + " ms");
        } catch (NullPointerException e) {
            //System.out.println("Response Time is: " + responseDataObj.getResponseTime() + " ms");
        }

        response.setResponseCode(response.getHttpResponse());
        try {
            System.out.println(data.get("name").toString() + ": Response Code of is: " + response.getResponseCode());
        } catch (NullPointerException e) {
            //System.out.println("Response Code of is: " + responseDataObj.getResponseCode());
        }

        switch (sObjType) {
            case "JSON_OBJ":
                response.setJsonResponse(response.getHttpResponse());
                break;

            case "JSON_ARR":
                response.setJsonArrayResponse(response.getHttpResponse());
                break;
        }

        return response;
    }

    public ResponseDataObj setResponseDataObj(String name, RequestDataObj request, String sPathToResult) {
        ResponseDataObj response = new ResponseDataObj();

        try {
            switch (request.getRequestMethod()) {
                case "POST":
                    response.setResponse(request.getHttpClient(), request.getHttpPost());
                    break;

                case "PUT":
                    response.setResponse(request.getHttpClient(), request.getHttpPut());
                    break;

                case "DELETE":
                    response.setResponse(request.getHttpClient(), request.getHttpDelete());
                    break;

                case "GET":
                    response.setResponse(request.getHttpClient(), request.getHttpGet());
                    break;
            }

            response.setJsonResponse(response.getHttpResponse());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writeFile = new FileWriter(sPathToResult + "result"+name+".json");
            writeFile.write(response.getJsonResponse().toJSONString().replace("\\", ""));
            writeFile.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        }

        return response;
    }

    public boolean getSchemaValidation(JSONObject data, String sPathToSchema, String sPathToResult, String sSchemaFileJson){
        String sResultFileName = "result" + data.get("name").toString() + ".json";
        return JsonManipulators.getSchemaValidation(sPathToSchema, sPathToResult, sSchemaFileJson, sResultFileName);
    }

}
