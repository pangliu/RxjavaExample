package com.example.rxjava.hank.rxjavasample.Fragment.Presenter;

public interface VersionContract {

    interface presenter {
        void getVersion();
    }

    interface view {
        void updateVersion();
        void showVersion(String versionString);
    }
}
