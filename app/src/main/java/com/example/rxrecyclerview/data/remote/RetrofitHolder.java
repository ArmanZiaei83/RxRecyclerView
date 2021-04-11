package com.example.rxrecyclerview.data.remote;

import com.example.rxrecyclerview.data.OnEvent;
import com.google.android.exoplayer2.metadata.id3.ApicFrame;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHolder implements OnEvent {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public Api getApi(){

        Api api = retrofit.create(Api.class);
        if(api == null){
            onError("Error In Api");
        }else {
            onSuccess("Api Ran Successfully");
        }

        return api;
    }

    @Override
    public void onSuccess(String message) {
        System.out.println(message);
    }

    @Override
    public void onError(String error) {
        System.out.println(error);
    }

    @Inject
    public RetrofitHolder() {
    }
}
