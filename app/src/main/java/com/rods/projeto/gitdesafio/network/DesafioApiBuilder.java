package com.rods.projeto.gitdesafio.network;

import com.rx2androidnetworking.Rx2ANRequest;
import com.rx2androidnetworking.Rx2AndroidNetworking;

public class DesafioApiBuilder {

    private static final String API_BASE_URL = "http://api.github.com/";

    public static Rx2ANRequest.GetRequestBuilder getRequestBuilder(String methodEndpoint) {
        String url = API_BASE_URL + methodEndpoint;
        return Rx2AndroidNetworking.get(url);
    }

}
