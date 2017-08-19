/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.admintest;

import java.io.IOException;
import org.apache.log4j.Logger;
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
        keycloak.getUsers();

        /*keycloakUser user = new keycloakUser();
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
        doPost(url, user, mapHeader);*/
    }

}
