/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.admintest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import rtk.sso.REST.apiREST;

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

        apiREST keycloak = new apiREST("vasil", "123", "192.168.1.150:8080", "videomanager");
        keycloak.Init();

        //keycloak.changeUserPassword("f:1eab3a50-cc74-46da-9e1f-cd77d775d7d6:8984", "12345");
//        List<NameValuePair> params = new LinkedList<>();
//        params.add(new BasicNameValuePair("search", "user_007"));
//        JSONArray userList = keycloak.getUsers(params);
//        for (Object item : userList) {
//            System.out.println("item = " + item);
//
//        }
        for (int i = 0; i < 5; i++) {
            keycloakUser user = new keycloakUser();
            user.setEmail("user_00" + i + "@mail.ru");
            user.setEnabled(true);
            user.setFirstName("user_00" + i);
            user.setLastName("user_00" + i);
            user.setUsername("user_00" + i);

            String s1 = "пр. Чекистов 37, кв. " + i;
            //String addr = new String(s1.getBytes("ISO-8859-1"), "UTF-8");
            System.out.println("addr = " + s1);
            HashMap<String, String> attr = new HashMap<>();
            attr.put("elk_id", "100" + i);
            attr.put("elk_b2b_id", "105" + i);
            attr.put("address", s1);

            user.setAttributes(attr);

            credentialRepresentation credentials = new credentialRepresentation();
            credentials.setType("password");
            credentials.setValue("123");

            List<credentialRepresentation> tempList = new ArrayList<>();
            tempList.add(credentials);

            user.setCredentials(tempList);

            System.out.println("user = " + user);
            String resObj = keycloak.addUser(user);
            if ((resObj != null) && (resObj.equals("Bearer"))) {
                keycloak.Init();
                keycloak.addUser(user);
            }
        }

    }

}
