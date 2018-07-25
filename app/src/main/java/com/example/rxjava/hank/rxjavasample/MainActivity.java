package com.example.rxjava.hank.rxjavasample;

import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;
import com.example.rxjava.hank.rxjavasample.DataInfo.VersionInfo;
import com.example.rxjava.hank.rxjavasample.Fragment.FragmentTest1;
import com.example.rxjava.hank.rxjavasample.Fragment.FragmentTest2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static int
            NOW_FLAG = -1,
            FLAG_FRAGMENT_1 = 0,
            FLAG_FRAGMENT_2 = 1;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private Button
            btnTest,
            btnF1,
            btnF2,
            btnF3,
            btnF4;
    private TextView tvHello;
    private ProgressBar progressBar;
    private Boolean
            isGetVersion = false,
            isGetStation = false;
    private FrameLayout frameLayout;
    private ArrayList<Fragment> fragmentArrayList;
    private int selectedFlag = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("msg", "onResume");
        initFragment();
    }

    private void findView() {
        tvHello = (TextView)findViewById(R.id.tv_hello);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        frameLayout = (FrameLayout) findViewById(R.id.container);
        btnTest = (Button) findViewById(R.id.btn_switch_fragment);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData();
                rxJavaTest();
            }
        });
        btnF1 = (Button) findViewById(R.id.btn_fragment1);
        btnF1.setOnClickListener(onBtnCallback);
        btnF2 = (Button) findViewById(R.id.btn_fragment2);
        btnF2.setOnClickListener(onBtnCallback);
        btnF3 = (Button) findViewById(R.id.btn_fragment3);
        btnF3.setOnClickListener(onBtnCallback);
        btnF4 = (Button) findViewById(R.id.btn_fragment4);
        btnF4.setOnClickListener(onBtnCallback);
    }

    private void initFragment() {
        if(null == fragmentArrayList || 0 == fragmentArrayList.size()) {
            fragmentArrayList = new ArrayList<Fragment>();
            fragmentArrayList.add(FragmentTest1.newInstance());
            fragmentArrayList.add(FragmentTest2.newInstance());
        }
        Log.d("msg", "fragmentArrayList size: " + fragmentArrayList.size());
    }

    private void rxJavaTest() {
        /**
         * step1: 宣告一個 observable(被觀察者)
         * observable: (上游)發送命令，決定異步操作的順序及次數
         * 在 RxJava2 中以 flowable(支持被壓) 取代 observable
         */
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if(emitter.isDisposed()) {
                    Log.d("msg", "observable isDisposed: " + emitter.isDisposed());
                    return;
                }
                // 異步處理: call api or access DB
                Log.d("msg", "observable thread: " + Thread.currentThread().getName());
                SystemClock.sleep(2000);
                emitter.onNext("next");
                SystemClock.sleep(2000);
                emitter.onNext("hello world");
                SystemClock.sleep(2000);
                emitter.onNext("haha");
                emitter.onComplete();
            }
        });
        /**
         * step2: 宣告一個 observer(觀察者)
         * observer: (下游)可在不同 thread 執行，等待 observable 的通知
         */
        DisposableObserver<String> observer = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.d("msg", "observer thread: " + Thread.currentThread().getName());
                Log.d("msg", "onNext: " + s);
            }
            @Override
            public void onError(Throwable e) {
                Log.d("msg", "onNext: " + e);
            }
            @Override
            public void onComplete() {
                Log.d("msg", "onComplete");
            }
        };
        /**
         * step3: 訂閱事件
         */
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
        /**
         * step4: 將需要被管理的 observer(觀察者) 加入管理集合
         */
        mCompositeDisposable.add(observer);
    }

    private void getData() {
        Log.d("msg", "getData");
        progressBar.setVisibility(View.VISIBLE);
        // 呼叫 getVersion api

        Disposable getVersionDisposable = ApiSource.getInstance().postVersion("android")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VersionInfo>() {
                    @Override
                    public void accept(VersionInfo s) throws Exception {
                        Log.d("msg", "getVersion: " + s.toString());
                        isGetVersion = true;
                        if(isGetVersion && isGetStation) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "throwable: " + throwable);
                    }
                });
        mCompositeDisposable.add(getVersionDisposable);

        // 呼叫 getStation api
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
//                        Log.d("msg", "station json: " + datas.getString(1));
                        isGetStation = true;
                        if(isGetVersion && isGetStation) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "throwable: " + throwable);
                    }
                });
        mCompositeDisposable.add(getVersionDisposable);
        getVersionDisposable = ApiSource.getInstance().postStationInfo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<StationInfo>() {
                    @Override
                    public void accept(StationInfo info) throws Exception {
//                        Log.d("msg", "Station: " + s);
                        Log.d("msg", "station status: " + info.toString());
                        isGetStation = true;
                        if(isGetVersion && isGetStation) {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("msg", "throwable: " + throwable);
                    }
                });
        mCompositeDisposable.add(getVersionDisposable);
    }

    private View.OnClickListener onBtnCallback = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_fragment1:
                    btnF1.setSelected(true);
                    btnF2.setSelected(false);
                    selectedFlag = FLAG_FRAGMENT_1;
                    Log.d("msg", "ft1");
                    break;
                case R.id.btn_fragment2:
                    btnF1.setSelected(false);
                    btnF2.setSelected(true);
                    Log.d("msg", "ft2");
                    selectedFlag = FLAG_FRAGMENT_2;
                    break;
                case R.id.btn_fragment3:
                    break;
                case R.id.btn_fragment4:
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            Fragment selectedFragment = fragmentArrayList.get(selectedFlag);
            Log.d("msg", "NOW_FLAG: " + NOW_FLAG);
            if(NOW_FLAG == -1) {
                // 目前沒有任何 fragment 被 add;
                Log.d("msg", "目前沒有任何 fragment add");
                transaction
                        .add(R.id.container, selectedFragment, selectedFragment.getClass().getName())
                        .commit();
            } else {
                /**
                 * 目前已有 fragment，要判斷當前 fragment 是否為所選之 fragment
                 */
                Fragment nowfragment = getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(NOW_FLAG).getClass().getName());
                Log.d("msg", "nowfragment: " + nowfragment.getTag());
                if(selectedFlag != NOW_FLAG) {
                    if(selectedFragment.isAdded()) {
                        // 所選的 fragment 曾經被加入過
                        Log.d("msg", "所選的 fragment 曾經被加入過");
                        transaction
                                .hide(nowfragment)
                                .show(selectedFragment)
                                .commit();
                    } else {
                        // 所選的 fragment 不曾加入過
                        Log.d("msg", "所選的 fragment 不曾加入過");
                        transaction
                                .hide(nowfragment)
                                .add(R.id.container, selectedFragment, selectedFragment.getClass().getName())
                                .commit();
                    }
                }
            }
            NOW_FLAG = selectedFlag;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("msg", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("msg", "onDestroy");
        mCompositeDisposable.clear();
        NOW_FLAG = -1;
    }
}
