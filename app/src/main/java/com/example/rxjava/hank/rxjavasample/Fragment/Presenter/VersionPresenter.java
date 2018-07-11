package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.rxjava.hank.rxjavasample.ApiSource;
import com.example.rxjava.hank.rxjavasample.DataInfo.VersionInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VersionPresenter implements VersionContract.presenter{

    private Context mContext;
    private VersionContract.view mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public VersionPresenter(Context context, VersionContract.view view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void getVersion() {
        Disposable getVersionDisposable = ApiSource.getInstance().postVersion("android")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VersionInfo>() {
                    @Override
                    public void accept(VersionInfo s) throws Exception {
                        Log.d("msg", "getVersion: " + s.toString());
                        mView.showVersion(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "throwable: " + throwable);
                    }
                });
        mCompositeDisposable.add(getVersionDisposable);
    }
}
