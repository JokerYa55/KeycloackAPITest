/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rtk.sso.admintest;

import java.util.HashMap;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author vasil
 */
public class credentialRepresentation {

//    private String algorithm;
//    private HashMap config = new HashMap();
//    private int counter;
//    private long createdDate;
//    private String device;
//    private int digits;
//    private int hashIterations;
//    private String hashedSaltedValue;
//    private int period;
//    private String salt;
//    private boolean temporary;
    private String type;
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
