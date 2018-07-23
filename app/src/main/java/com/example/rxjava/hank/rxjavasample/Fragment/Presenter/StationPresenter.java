package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.rxjava.hank.rxjavasample.ApiSource;
import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StationPresenter implements StationContract.presenter{

    private Context mContext;
    private StationContract.view mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public StationPresenter(Context context, StationContract.view view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void getStation() {
        Disposable getStationDisposable = ApiSource.getInstance().postStationInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StationInfo>() {
                    @Override
                    public void accept(StationInfo stationInfo) throws Exception {
                        Log.d("msg", "getStation: " + stationInfo.toString());
                        mView.showStation(stationInfo, 0);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "subscribe throwable: " + throwable);
                        mView.showStation(null, throwable.hashCode());
                    }
                });
        mCompositeDisposable.add(getStationDisposable);
    }
}
