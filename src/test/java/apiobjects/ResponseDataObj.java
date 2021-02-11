package apiobjects;

import org.apache.http.ConnectionClosedException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created on Jan, 2018
 **/

public class ResponseDataObj {
    private JSONObject jsonResponse;
    private JSONArray jsonArrayResponse;
    private HttpResponse httpResponse;
    private long responseTime;
    private int responseCode;

    public void setJsonResponse(HttpResponse httpResponse) throws IOException, ParseException {
        JSONObject jsonResponse = null;
        HttpEntity httpEntity = httpResponse.getEntity();

        try {
            //System.out.println("responseCode: " + getResponseCode());
            if ((httpEntity != null) && (getResponseCode() != 500) && (getResponseCode() != 401) && (getResponseCode() != 403)) {
                //String responseBody = IOUtils.toString(httpResponse.getEntity().getContent());
                //System.out.println("httpEntity: " + EntityUtils.toString(httpEntity));
                InputStream rInputStream = httpEntity.getContent();
                StringBuilder stringBuilder = inputStreamToString(rInputStream);
                //System.out.println("Content: " + stringBuilder.toString());
                //jsonResponse = (JSONObject) new JSONParser().parse(EntityUtils.toString(httpEntity));
                if (stringBuilder.toString().substring(stringBuilder.toString().length() - 1).equals("}")) {
                    jsonResponse = (JSONObject) new JSONParser().parse(stringBuilder.toString());
                } else {
                    jsonResponse = (JSONObject) new JSONParser().parse(stringBuilder.toString()+ "}");
                }
            }
        } catch (ClassCastException e) {
            System.out.println(EntityUtils.toString(httpEntity));
        } catch (Exception e) {
            this.jsonResponse = null;
        }

        this.jsonResponse = jsonResponse;
    }

    // Fast Implementation
    private StringBuilder inputStreamToString(InputStream is) throws IOException{
        String line = "";
        StringBuilder total = new StringBuilder();

        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));

        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (ConnectionClosedException e) {}

        // Return full string
        return total;
    }

    public JSONObject getJsonResponse() {
        if (jsonResponse == null) {
            JSONParser parser = new JSONParser();
            try {
                jsonResponse = (JSONObject) parser.parse("{}");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return jsonResponse;
    }

    public void setResponse(HttpClient httpClient, HttpGet httpGet) throws IOException {
        long start = System.currentTimeMillis();
        setHttpResponse(httpClient, httpGet);
        setResponseCode(httpResponse);
        long end = System.currentTimeMillis();
        this.responseTime = end-start;
    }

    public void setJsonArrayResponse(HttpResponse httpResponse) throws IOException, ParseException {
        JSONArray jsonArrayResponse = null;

        HttpEntity httpEntity = httpResponse.getEntity();

        try {
            if (httpResponse.getEntity() != null) {
                jsonArrayResponse = (JSONArray) new JSONParser().parse(EntityUtils.toString(httpEntity));
            }
        } catch (ClassCastException e) {
        }

        this.jsonArrayResponse = jsonArrayResponse;
    }

    public JSONArray getJsonArrayResponse() {
        return jsonArrayResponse;
    }

    public void setResponse(HttpClient httpClient, HttpPost httpPost) throws IOException {
        long start = System.currentTimeMillis();
        setHttpResponse(httpClient, httpPost);
        setResponseCode(httpResponse);
        long end = System.currentTimeMillis();
        //System.out.println("Thread id = " + Thread.currentThread().getId());
        //System.out.println("Response Time = " + Long.toString(end-start));
        this.responseTime = end-start;
    }

    public void setResponse(HttpClient httpClient, HttpPut httpPut) throws IOException {
        long start = System.currentTimeMillis();
        setHttpResponse(httpClient, httpPut);
        setResponseCode(httpResponse);
        long end = System.currentTimeMillis();
        this.responseTime = end-start;
    }

    public void setResponse(HttpClient httpClient, HttpDelete httpDelete) throws IOException {
        long start = System.currentTimeMillis();
        setHttpResponse(httpClient, httpDelete);
        setResponseCode(httpResponse);
        long end = System.currentTimeMillis();
        this.responseTime = end-start;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseCode(HttpResponse httpResponse) {
        this.responseCode = httpResponse.getStatusLine().getStatusCode();
    }

    public long getResponseCode() {
        return responseCode;
    }

    public void setHttpResponse(HttpClient httpClient, HttpGet httpGet) throws IOException {
        this.httpResponse = httpClient.execute(httpGet);
    }

    public void setHttpResponse(HttpClient httpClient, HttpPost httpPost) throws IOException {
        this.httpResponse = httpClient.execute(httpPost);
    }

    public void setHttpResponse(HttpClient httpClient, HttpPut httpPut) throws IOException {
        this.httpResponse = httpClient.execute(httpPut);
    }

    public void setHttpResponse(HttpClient httpClient, HttpDelete httpDelete) throws IOException {
        this.httpResponse = httpClient.execute(httpDelete);
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

}
