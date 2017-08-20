/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.httputil;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vasil
 */
public class utlhttp {

    private static final Logger log = Logger.getLogger(utlhttp.class);

    public JSONObject doPost(String url, Object params, Map<String, String> headerList) {
        System.out.println("doPost => " + params.toString());
        JSONObject res = new JSONObject();
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Gson gson = new Gson();
            
            HttpPost post = new HttpPost(url);
            System.out.println("");
            System.out.println("params = " + params.toString());
            System.out.println("json param = " + gson.toJson(params));
            
            StringEntity postingString = new StringEntity(gson.toJson(params), "application/json", "UTF-8");
            
            System.out.println("postingString = " + postingString.toString());
            post.setEntity(postingString);
            
            if (headerList != null) {
                headerList.entrySet().stream().forEach((t) -> {
                    Header header = new BasicHeader(t.getKey(), t.getValue());
                    System.out.println("header => " + header);
                    post.setHeader(header);
                });
            }
            HttpResponse response = httpClient.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                json.append(line);
            }

            System.out.println("json = " + json.toString());

            if ((json != null) && (json.toString().equals("Bearer"))) {
                JSONObject obj = new JSONObject();
                obj.put("error", "Bearer");                
                res = obj;
            } else {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(json.toString());
                JSONObject jsonObj = (JSONObject) obj;
                res = jsonObj;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    /**
     * Отправка POST запроса
     */
    public JSONObject doPost(String url, List params, Map<String, String> headerList) {
        JSONObject res = new JSONObject();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);
            if (headerList != null) {
                headerList.entrySet().stream().forEach((t) -> {
                    Header header = new BasicHeader(t.getKey(), t.getValue());
                    post.setHeader(header);
                });
            }

            if (params != null) {
                System.out.println((new UrlEncodedFormEntity(params)).toString());
                post.setEntity(new UrlEncodedFormEntity(params));
            }

            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                json.append(line);
            }

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json.toString());
            JSONObject jsonObj = (JSONObject) obj;
            res = jsonObj;
            System.out.println("access_token : " + res.get("access_token"));

        } catch (IOException | IllegalStateException | ParseException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    /**
     *
     * @param url
     * @param headerList
     * @return
     * @throws ParseException
     */
    public String doGet(String url, Map<String, String> headerList) throws ParseException {
        System.out.println("doGet");
        String res = null;
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;

        if (headerList != null) {
            headerList.entrySet().stream().forEach((t) -> {
                Header header = new BasicHeader(t.getKey(), t.getValue());
                request.setHeader(header);
            });
        }

        try {
            response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                json.append(line);
            }

            System.out.println("json = " + json.toString());
            res = json.toString();
            /*org.json.simple.parser.JSONParser parser = new JSONParser();
            Object obj = parser.parse(json.toString());
            try {
                JSONObject jsonObj = (JSONObject) obj;
                res = jsonObj;
            } catch (Exception e) {
                JSONArray jsonArr = (JSONArray) obj;
                System.out.println("arr = " + jsonArr.toJSONString() );
            }
            System.out.println("res = " + res.toJSONString());*/
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return res;
    }
}
