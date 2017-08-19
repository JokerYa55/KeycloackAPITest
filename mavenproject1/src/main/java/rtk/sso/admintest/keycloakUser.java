/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.admintest;

import java.util.List;

/**
 *
 * @author vasil
 *
 */
public class keycloakUser {

    private String username;
    private boolean enabled;
    private String email;
    private String firstName;
    private String lastName;
    private List<credentialRepresentation> credentials;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<credentialRepresentation> getCredentials() {
        return credentials;
    }

    public void setCredentials(List<credentialRepresentation> credentials) {
        this.credentials = credentials;
    }
}
