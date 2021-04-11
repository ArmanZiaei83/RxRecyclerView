package com.example.rxrecyclerview.di.logic;

import com.example.rxrecyclerview.doamin.GetList;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LogicModule {

    @Singleton
    @Provides
    GetList provideList(){
        return new GetList();
    }
}
