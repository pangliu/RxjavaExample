package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;

public interface StationContract {

    interface presenter {
        void getStation();
    }

    interface view {
        void showStation(StationInfo stationInfo, int errorCode);
    }
}
