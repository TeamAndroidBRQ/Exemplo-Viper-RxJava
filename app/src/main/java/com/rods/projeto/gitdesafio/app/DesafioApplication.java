package com.rods.projeto.gitdesafio.app;


import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.rods.projeto.gitdesafio.BuildConfig;
import com.rods.projeto.gitdesafio.rxbus.RxBus;

public class DesafioApplication extends Application {
    private RxBus bus;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxBus();

        //Para JSON dinâmico
        //https://github.com/amitshekhariitbhu/Fast-Android-Networking/issues/64
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory(mapper));
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }
    }

    public RxBus bus() {
        return bus;
    }
}
