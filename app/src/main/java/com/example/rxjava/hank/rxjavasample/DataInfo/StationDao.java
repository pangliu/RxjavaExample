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

    public void add(StationInfo.Station station) {
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

    public void createWithTransaction(final List<StationInfo.Station> stations) {
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

    public List<StationInfo.Station> getAllStation() {
        List<StationInfo.Station> stations = new ArrayList<StationInfo.Station>();
        try {
            stations = stationDaoOpe.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stations;
    }
}
