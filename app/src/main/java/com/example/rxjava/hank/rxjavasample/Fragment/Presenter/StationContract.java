package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;

import java.util.List;

public interface StationContract {

    interface presenter {
        void getStationFromApi();
        void getStationFromDb();
        void writeStationToDb(List<StationInfo.Station> station);
    }

    interface view {
        void showStationFromApi(StationInfo stationInfo, int errorCode);
        void showStationFromDb(List<StationInfo.Station> stations);
    }
}
