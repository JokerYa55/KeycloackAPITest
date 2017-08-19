/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.REST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static rtk.sso.admintest.utlhttp.doGet;
import static rtk.sso.admintest.utlhttp.doPost;

/**
 *
 * @author vasil
 */
public class apiREST {

    private static final Logger log = Logger.getLogger(apiREST.class);
    private String token;
    private String username;
    private String password;
    private String host;
    private String realm;

    public apiREST(String username, String password, String host, String realm) {
        this.username = username;
        this.password = password;
        this.host = host;
        this.realm = realm;
    }

    public void Init() {
        try {
            this.token = getToken();
        } catch (Exception e) {
        }
    }

    private String getToken() {
        String res = null;
        try {
            String url = "http://" + host + "/auth/realms/master/protocol/openid-connect/token";
            List nameValuePairs = new ArrayList(1);
            nameValuePairs.add(new BasicNameValuePair("client_id", "admin-cli")); //you can as many name value pair as you want in the list.
            nameValuePairs.add(new BasicNameValuePair("username", this.username));
            nameValuePairs.add(new BasicNameValuePair("password", this.password));
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            JSONObject accessJson = doPost(url, nameValuePairs, null);
            res = (String) accessJson.get("access_token");
            log.info("access_token = " + res);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    public void addUser(String token) {
        try {

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public JSONArray getUsers() {
        System.out.println("getUsers");
        JSONArray res = null;
        try {            
            // /admin/realms/{realm}/users
            String url = "http://" + host + "/auth/admin/realms/" + this.realm + "/users";
            System.out.println("url = " + url);
            Map<String, String> mapHeader = new HashMap<>();
            mapHeader.put("Content-Type", "application/json");
            mapHeader.put("Authorization", "Bearer " + this.token);
            doGet(url, mapHeader);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }
}
