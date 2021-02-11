package apiobjects;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.simple.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created on Jan, 2018
 **/

public class RequestDataObj extends util.JsonManipulators {
    private HttpClient httpClient;
    private JSONObject data, jsonObjParam;
    private HttpPost httpPost;
    private HttpPut httpPut;
    private HttpDelete httpDelete;
    private HttpGet httpGet;
    private String token;
    private URL apiUrl;
    private String apiHost, apiProtocol;


    public HttpClient getHttpClient() {
        return httpClient;
    }

    public HttpGet getHttpGet() {
        return httpGet;
    }

    public HttpPost getHttpPost() {
        return httpPost;
    }

    public HttpPut getHttpPut() {
        return httpPut;
    }

    public HttpDelete getHttpDelete() {
        return httpDelete;
    }

    public String getRequestUrl() {
        String sRequestUrl = null;

        try {
            if (httpGet != null) {
                sRequestUrl = httpGet.getURI().toURL().toString();
            }

            if (httpPost != null) {
                sRequestUrl = httpPost.getURI().toURL().toString();
            }

            if (httpPut != null) {
                sRequestUrl = httpPut.getURI().toURL().toString();
            }

            if (httpDelete != null) {
                sRequestUrl = httpDelete.getURI().toURL().toString();
            }
        } catch (MalformedURLException e) {
            System.out.println(httpPost.getURI());
            e.printStackTrace();
        }

        return sRequestUrl;
    }

    public String getRequestMethod() {
        return jsonObjParam.get("method").toString();
    }


}
