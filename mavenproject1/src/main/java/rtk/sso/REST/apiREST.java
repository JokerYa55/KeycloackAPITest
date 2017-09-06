/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.REST;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import rtk.sso.keycloak.model.keycloakUser;
import rtk.sso.httputil.utlhttp;

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
            utlhttp httpUtil = new utlhttp();
            String url = "http://" + host + "/auth/realms/master/protocol/openid-connect/token";
            List nameValuePairs = new ArrayList(1);
            nameValuePairs.add(new BasicNameValuePair("client_id", "admin-cli")); //you can as many name value pair as you want in the list.
            nameValuePairs.add(new BasicNameValuePair("username", this.username));
            nameValuePairs.add(new BasicNameValuePair("password", this.password));
            nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
            JSONObject accessJson = httpUtil.doPost(url, nameValuePairs, null);
            res = (String) accessJson.get("access_token");
            //log.info("access_token = " + res);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    public String addUser(Object user) {
        String res = null;
        try {
            utlhttp httpUtil = new utlhttp();
            // Отправляем другой запрос
            String url = "http://" + host + "/auth/admin/realms/" + this.realm + "/users";
            Map<String, String> mapHeader = new HashMap<>();
            mapHeader.put("Content-Type", "application/json");
            mapHeader.put("Authorization", "Bearer " + this.token);
            JSONObject res1 = httpUtil.doPost(url, user, mapHeader);
            //System.out.println("res1 = " + res1.toJSONString());

            List<NameValuePair> params = new LinkedList<>();
            params.add(new BasicNameValuePair("search", ((keycloakUser) user).getUsername()));
            JSONArray userJSON = getUsers(params);
            JSONObject userDB = null;
            if (userJSON.size() == 1) {
                userDB = (JSONObject) userJSON.get(0);
            }

            // Change user password
            System.out.println("userID = " + userDB.get("id"));
            changeUserPassword((String) userDB.get("id"), "123"+((keycloakUser) user).getFirstName());
            System.out.println("userJSON = " + userJSON.toJSONString());
            res = (String) res1.get("error");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    public void changeUserPassword(String userID, String password) {
        try {
            System.out.println("changeUserPassword => " + password);
            String url = "http://" + host + "/auth/admin/realms/" + this.realm + "/users/" + userID + "/reset-password";
            utlhttp httpUtil = new utlhttp();

            Map<String, String> mapHeader = new HashMap<>();
            mapHeader.put("Content-Type", "application/json");
            mapHeader.put("charset", "utf-8");
            mapHeader.put("Authorization", "Bearer " + this.token);
            mapHeader.put("Accept-Encoding", "gzip,deflate,sdch");

            Map<String, String> param = new HashMap<>();
            param.put("type", "password");
            param.put("temporary", "false");
            param.put("value", password);
            httpUtil.doPut(url, param, mapHeader);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *
     * @param params
     * @return
     */
    public JSONArray getUsers(List params) {
        System.out.println("getUsers =? " + params.toString());
        JSONArray res = null;
        try {

            utlhttp httpUtil = new utlhttp();
            // /admin/realms/{realm}/users
            String url = "http://" + host + "/auth/admin/realms/" + this.realm + "/users";

            System.out.println("url = " + url);
            Map<String, String> mapHeader = new HashMap<>();
            mapHeader.put("Content-Type", "application/json");
            mapHeader.put("charset", "utf-8");
            mapHeader.put("Authorization", "Bearer " + this.token);
            String arrStr = httpUtil.doGet(url, params, mapHeader);

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(arrStr);
            res = (JSONArray) obj;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        //System.out.println("getUsers res= " + res.toJSONString());
        return res;
    }

}
