package com.example.rxrecyclerview.doamin;

import androidx.recyclerview.widget.RecyclerView;

import com.example.rxrecyclerview.data.OnEvent;
import com.example.rxrecyclerview.data.entity.RetrofitModel;
import com.example.rxrecyclerview.data.remote.RetrofitHolder;
import com.example.rxrecyclerview.di.DaggerDataComponent;
import com.example.rxrecyclerview.di.DataComponent;
import com.example.rxrecyclerview.presentation.Adapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class GetList implements OnEvent {

    DataComponent component;
    @Inject
    RetrofitHolder retrofitHolder;

    public Observable<List<RetrofitModel>> getPhotosUrl(){

        component = DaggerDataComponent.create();
        component.inject(this);

        return retrofitHolder.getApi()
                .getAllPhotos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Adapter setAdapter(Adapter adapter , RecyclerView recyclerView){
        getPhotosUrl().flatMap(new Function<List<RetrofitModel>, ObservableSource<List<RetrofitModel>>>() {
            @Override
            public ObservableSource<List<RetrofitModel>> apply(@NotNull List<RetrofitModel> retrofitModels) throws Exception {
                adapter.setUrls(retrofitModels);
                recyclerView.setAdapter(adapter);
                return null;
            }
        }).subscribe(new Observer<List<RetrofitModel>>() {
            @Override
            public void onSubscribe(@NotNull Disposable d) {

            }

            @Override
            public void onNext(@NotNull List<RetrofitModel> retrofitModel) {
                System.out.println("URL : " + retrofitModel.get(0).getUrl());
            }

            @Override
            public void onError(@NotNull Throwable e) {
                System.out.println("Error In Subscribing : " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });

        if (adapter.getUrls() == null){
            onError("<< Adapter Is Null >>");
        }else {
            onSuccess("Adapter Is Ok ... ");
        }

        return adapter;
    }

    @Override
    public void onSuccess(String message) { System.out.println(message); }

    @Override
    public void onError(String error) { System.out.println(error); }

    @Inject
    public GetList() {
    }
}
