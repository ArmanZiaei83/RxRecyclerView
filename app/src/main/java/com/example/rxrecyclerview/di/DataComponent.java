package com.example.rxrecyclerview.di;

import com.example.rxrecyclerview.doamin.GetList;
import com.google.android.exoplayer2.C;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = DataModule.class)
public interface DataComponent {
    void inject(GetList getList);
}
