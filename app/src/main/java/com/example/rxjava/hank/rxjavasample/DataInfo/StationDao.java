package com.example.rxjava.hank.rxjavasample.DataInfo;

import android.content.Context;
import android.util.Log;

import com.example.rxjava.hank.rxjavasample.Helper.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

public class StationDao {

    private Context mContext;
    private Dao<StationInfo.Station, Integer> stationDaoOpe;
    private DatabaseHelper databaseHelper;

    public StationDao(Context context) {
        this.mContext = context;
        try{
            databaseHelper = DatabaseHelper.getHelper(mContext);
            stationDaoOpe = databaseHelper.getDao(StationInfo.Station.class);
        } catch (SQLException e) {
            Log.d("msg", "stationDao constructor SQLException: " + e);
        } catch (Exception e) {
            Log.d("msg", "stationDao constructor Exception: " + e);
        }
    }

    private void add(StationInfo.Station station) {
        try {
            Log.d("msg", "dao add station: " + station.toString());
            stationDaoOpe.createOrUpdate(station);
        } catch (SQLException e) {
            Log.d("msg", "stationDao add SQLException: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            Log.d("msg", "stationDao add Exception: " + e);
        }
    }

    private void createWithTransaction(final List<StationInfo.Station> stations) {
        try {
            TransactionManager.callInTransaction(databaseHelper.getConnectionSource(), new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    for(StationInfo.Station item: stations) {
                        stationDaoOpe.createOrUpdate(item);
                    }
                    return null;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<StationInfo.Station> getAllStation() {
        List<StationInfo.Station> stations = new ArrayList<StationInfo.Station>();
        try {
            stations = stationDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }

    /**
     * 將讀寫 DB 部分改用 Rx 架構
     * @return
     */
    public Flowable<List<StationInfo.Station>> getStations() {
        Flowable<List<StationInfo.Station>> flowable = Flowable.create(new FlowableOnSubscribe<List<StationInfo.Station>>() {
            @Override
            public void subscribe(FlowableEmitter<List<StationInfo.Station>> e) throws Exception {
                Log.d("msg", "threadName: " + Thread.currentThread().getName());
                List<StationInfo.Station> stations = getAllStation();
                Log.d("msg", "getStation: " + stations.toString());
                e.onNext(stations);
            }
        }, BackpressureStrategy.ERROR);
        return flowable;
    }

    public Flowable<Integer> writeStations(final List<StationInfo.Station> stations) {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.d("msg", "threadName: " + Thread.currentThread().getName());
                createWithTransaction(stations);
                Log.d("msg", "write to db");
                e.onNext(0);
            }
        }, BackpressureStrategy.ERROR);
        return flowable;
    }
}
