package com.mislbd.ababil.ibapitest.error;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by sanjoy on 10/27/18.
 */
public class ErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        ByteArrayInputStream arrayInputStream = (ByteArrayInputStream) clientHttpResponse.getBody();
        Scanner scanner = new Scanner(arrayInputStream);
        scanner.useDelimiter("\\Z");
        String data = "";
        if (scanner.hasNext())
            data = scanner.next();
        System.out.println(data);
    }
}
