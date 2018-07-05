package com.example.rxjava.hank.rxjavasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.rxjava.hank.rxjavasample.DataInfo.VersionInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Disposable getVersionDisposable = ApiSource.getInstance().postVersion("android")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VersionInfo>() {
                    @Override
                    public void accept(VersionInfo s) throws Exception {
                        Log.d("msg", "getVersion: " + s.toString());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "throwable: " + throwable);
                    }
                });
        mCompositeDisposable.add(getVersionDisposable);

        getVersionDisposable = ApiSource.getInstance().postStation()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object s) throws Exception {
//                        Log.d("msg", "Station: " + s);
                        JSONObject json = new JSONObject(s.toString());
//                        Log.d("msg", "station json: " + json);
                        JSONArray datas = json.getJSONArray("datas");
                        Log.d("msg", "station json: " + datas.getString(1));

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "throwable: " + throwable);
                    }
                });
        mCompositeDisposable.add(getVersionDisposable);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
