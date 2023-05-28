package com.mislbd.ababil.ibapitest.auth;

import java.io.Serializable;

/**
 * Created by sanjoy on 10/10/18.
 */
public class OauthRequest implements Serializable{
    private String grant_type;
    private String client_id;
    private String username;
    private String password;

    public OauthRequest(String grant_type, String client_id, String username, String password) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.username = username;
        this.password = password;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
