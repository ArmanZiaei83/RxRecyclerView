package com.example.rxrecyclerview.di;

import com.example.rxrecyclerview.data.remote.RetrofitHolder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Singleton
    @Provides
    RetrofitHolder propvideRetrofit(){
        return new RetrofitHolder();
    }
}
