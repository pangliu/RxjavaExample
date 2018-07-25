package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.rxjava.hank.rxjavasample.ApiSource;
import com.example.rxjava.hank.rxjavasample.DataInfo.StationDao;
import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class StationPresenter implements StationContract.presenter{

    private Context mContext;
    private StationContract.view mView;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private StationDao stationDao;

    public StationPresenter(Context context, StationContract.view view) {
        this.mContext = context;
        this.mView = view;
        this.stationDao = new StationDao(mContext);
    }

    @Override
    public void getStationFromApi() {
        Disposable getStationDisposable = ApiSource.getInstance().postStationInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StationInfo>() {
                    @Override
                    public void accept(StationInfo stationInfo) throws Exception {
                        Log.d("msg", "getStation: " + stationInfo.toString());
                        mView.showStationFromApi(stationInfo, 0);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "subscribe throwable: " + throwable);
                        mView.showStationFromApi(null, throwable.hashCode());
                    }
                });
        mCompositeDisposable.add(getStationDisposable);
    }

    @Override
    public void getStationFromDb() {
        Disposable getStationDisposable = stationDao.getStations()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StationInfo.Station>>() {
                    @Override
                    public void accept(List<StationInfo.Station> stations) throws Exception {
                        Log.d("msg", "subscribe threadName: " + Thread.currentThread().getName());
                        mView.showStationFromDb(stations);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "subscribe throwable threadName: " + Thread.currentThread().getName());
                    }
                });
        mCompositeDisposable.add(getStationDisposable);
    }

    @Override
    public void writeStationToDb(List<StationInfo.Station> stations) {
        Disposable getStationDisposable = stationDao.writeStations(stations)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d("msg", "subscribe threadName: " + Thread.currentThread().getName());
//                        mView.showStationFromDb(stations);
                        Log.d("msg", "errorCode: " + integer);
//                        if(integer == 0) {
//                            mView.showStationFromDb();
//                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "subscribe throwable threadName: " + Thread.currentThread().getName());
                    }
                });
        mCompositeDisposable.add(getStationDisposable);
    }
}
