package com.example.rxrecyclerview.data.remote;

import com.example.rxrecyclerview.data.entity.RetrofitModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {
    @GET("/photos")
    Observable<List<RetrofitModel>> getAllPhotos();
}
