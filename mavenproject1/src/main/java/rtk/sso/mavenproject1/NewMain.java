/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.mavenproject1;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author vasil
 */
public class NewMain {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws org.json.simple.parser.ParseException
     */
    private static final Logger log = Logger.getLogger(NewMain.class);

    public static void main(String[] args) throws IOException, ParseException {

        String url = "http://192.168.1.150:8080/auth/realms/master/protocol/openid-connect/token";
        List nameValuePairs = new ArrayList(1);
        nameValuePairs.add(new BasicNameValuePair("client_id", "admin-cli")); //you can as many name value pair as you want in the list.
        nameValuePairs.add(new BasicNameValuePair("username", "vasil"));
        nameValuePairs.add(new BasicNameValuePair("password", "123"));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
        JSONObject accessJson = doPost(url, nameValuePairs, null);
        String access_token = (String) accessJson.get("access_token");
        //System.out.println(access_token);

        keycloakUser user = new keycloakUser();
        user.setEmail("7777@mail.ru");
        user.setEnabled(true);
        user.setFirstName("test777");
        user.setLastName("test777");
        user.setUsername("test777");

        credentialRepresentation credentials = new credentialRepresentation();
        credentials.setType("password");
        credentials.setValue("1234");

        List<credentialRepresentation> tempList = new ArrayList<>();
        tempList.add(credentials);

        user.setCredentials(tempList);

        // Отправляем другой запрос
        url = "http://192.168.1.150:8080/auth/admin/realms/master/users";
        Map<String, String> mapHeader = new HashMap<>();
        mapHeader.put("Content-Type", "application/json");
        mapHeader.put("Authorization", "Bearer " + access_token);
        doPost(url, user, mapHeader);
    }

    private static JSONObject doPost(String url, Object params, Map<String, String> headerList) {
        JSONObject res = new JSONObject();
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            Gson gson = new Gson();
            JSONParser parser = new JSONParser();
            HttpPost post = new HttpPost(url);
            StringEntity postingString = new StringEntity(gson.toJson(params));
            System.out.println("json = " + gson.toJson(params));
            post.setEntity(postingString);
            if (headerList != null) {
                headerList.entrySet().stream().forEach((t) -> {
                    Header header = new BasicHeader(t.getKey(), t.getValue());
                    post.setHeader(header);
                });
            }
            HttpResponse response = httpClient.execute(post);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            StringBuilder json = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                json.append(line);
            }

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
     * Отправка POST запроса
     */
    private static JSONObject doPost(String url, List params, Map<String, String> headerList) {
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

    private static JSONObject doGet(String url, Map<String, String> headerList) throws ParseException {
        JSONObject res = new JSONObject();
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
                System.out.println(line);
                json.append(line);
            }
            org.json.simple.parser.JSONParser parser = new JSONParser();
            Object obj = parser.parse(json.toString());
            JSONObject jsonObj = (JSONObject) obj;
            res = jsonObj;
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
        return res;
    }
}
