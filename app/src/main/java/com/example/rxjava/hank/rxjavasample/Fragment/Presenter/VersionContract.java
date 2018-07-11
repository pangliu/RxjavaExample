package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

import com.example.rxjava.hank.rxjavasample.DataInfo.VersionInfo;

public interface VersionContract {

    interface presenter {
        void getVersion();
    }

    interface view {
        void updateVersion();
        void showVersion(VersionInfo versionInfo);
    }
}
