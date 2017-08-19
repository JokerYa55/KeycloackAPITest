/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.admintest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import static rtk.sso.admintest.utlhttp.doPost;


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

    
}
