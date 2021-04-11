package com.example.rxrecyclerview.di.logic;

import com.example.rxrecyclerview.presentation.MainActivity;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = LogicModule.class)
public interface LogicComponent {
    void inject(MainActivity mainActivity);
}
