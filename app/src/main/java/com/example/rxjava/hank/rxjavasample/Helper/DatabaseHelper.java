package com.example.rxjava.hank.rxjavasample.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "demo.db";
    private static DatabaseHelper instance;
//    private Dao<Station, Integer> stationDao;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null)
                    instance = new DatabaseHelper(context);
            }
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, StationInfo.Station.class);
        } catch (SQLException e) {
            Log.d("msg", "onCreate SQLException: " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, StationInfo.Station.class, true);
        } catch (SQLException e) {
            Log.d("msg", "onUpgrade SQLException: " + e);
            e.printStackTrace();
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        Log.d("msg", "getDao className: " + className);
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            Log.d("msg", "getDao dao=null");
            dao = super.getDao(clazz);
            Log.d("msg", "getDao super getDao: " + dao);
            daos.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();
//        stationDao = null;
        for(String key:daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}
