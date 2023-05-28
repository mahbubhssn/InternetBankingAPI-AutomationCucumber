package com.mislbd.ababil.ibapitest.auth;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sanjoy on 10/28/18.
 */

@Service
public class AuthToken {

    @Value("${mislbd.oauth.clientId}")
    private String clientId;

    @Value("${mislbd.oauth.secret}")
    private String secret;

    @Value("${mislbd.oauth.baseUrl}")
    private String oauthBaseUrl;

    @Value("${mislbd.oauth.userName}")
    private String userName;

    @Value("${mislbd.oauth.password}")
    private String password;

    @Value("${mislbd.oauth.refreshTokenUrl}")
    private String refreshTokenUrl;


    RestTemplate restTemplate = new RestTemplate();


    public void doAuth() {
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("grant_type", "password");
        map.add("client_id", clientId);
        map.add("username", userName);
        map.add("password", password);
        map.add("client_secret", secret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, getHeaders());
        ResponseEntity<OauthResponse> responseEntity = restTemplate.exchange(oauthBaseUrl + refreshTokenUrl, HttpMethod.POST, request,OauthResponse.class);
        System.out.println(responseEntity);
        AuthData.accessToken=responseEntity.getBody().getAccess_token();
        AuthData.refreshToken=responseEntity.getBody().getRefresh_token();
        System.out.println("Access Token: "+responseEntity.getBody().getAccess_token());
        System.out.println("Refresh Token: "+responseEntity.getBody().getRefresh_token());
    }

    private HttpHeaders getHeaders(){
        String plainCredentials=clientId+":"+secret;
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        HttpHeaders headers = new HttpHeaders();
        System.out.println("Basic "+base64Credentials);
        headers.add("Authorization","Basic "+base64Credentials);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }
}
