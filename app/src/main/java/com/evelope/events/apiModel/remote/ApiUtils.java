package com.evelope.events.apiModel.remote;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://10.0.2.2:8081";

    public static HttpService getHttpService() {

        return RetrofitClient.getClient(BASE_URL).create(HttpService.class);
    }
}
