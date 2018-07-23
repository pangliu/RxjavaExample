package com.example.rxjava.hank.rxjavasample.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxjava.hank.rxjavasample.DataInfo.StationDao;
import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;
import com.example.rxjava.hank.rxjavasample.Dialog.LoadingDialog;
import com.example.rxjava.hank.rxjavasample.Fragment.Presenter.StationContract;
import com.example.rxjava.hank.rxjavasample.Fragment.Presenter.StationPresenter;
import com.example.rxjava.hank.rxjavasample.Helper.DatabaseHelper;
import com.example.rxjava.hank.rxjavasample.R;

import java.sql.SQLException;
import java.util.ArrayList;


public class FragmentTest2 extends Fragment implements StationContract.view{

    private Context mContext;
    private StationPresenter mPresenter;
    private Button btnGetStation, btnShowDb;
    private TextView tvStation, tvStationFromDb;
    private LoadingDialog loadingDialog;
    private DatabaseHelper databaseHelper;

    public FragmentTest2() {
        // Required empty public constructor
    }

    public static FragmentTest2 newInstance() {
        FragmentTest2 fragment = new FragmentTest2();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("msg", "fragment2 onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("msg", "fragment2 onCreate");
        mContext = getActivity();
        mPresenter = new StationPresenter(mContext, this);
        loadingDialog = new LoadingDialog(mContext);
        databaseHelper = DatabaseHelper.getHelper(mContext);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("msg", "fragment2 onCreateView");
        View view = inflater.inflate(R.layout.fragment_test2, container, false);
        btnGetStation = (Button) view.findViewById(R.id.btn_get_station);
        tvStation = (TextView) view.findViewById(R.id.tv_station);
        btnShowDb = (Button) view.findViewById(R.id.btn_show_db);
        tvStationFromDb = (TextView) view.findViewById(R.id.tv_station_from_db);
        btnGetStation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                mPresenter.getStation();
            }
        });

        btnShowDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                try {
                    StationDao stationDao = new StationDao(mContext);
                    ArrayList<StationInfo.Station> stations = (ArrayList<StationInfo.Station>) stationDao.getAllStation();
                    for(StationInfo.Station item:stations){
                        Log.d("msg", "item: " + item.toString());
                    }
//                    tvStationFromDb.setText(station.getName());
//                } catch (SQLException e) {
//                    Log.d("msg", "read to Db SQLException: " + e);
//                    e.printStackTrace();
//                }
                tvStationFromDb.setText(stations.toString());
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("msg", "fragment2 onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("msg", "fragment2 onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("msg", "fragment2 onDetach");
//        mListener = null;
    }

    @Override
    public void showStation(StationInfo stationInfo, int errorCode) {
        Log.d("msg","fragment showStation error: " + errorCode);
        loadingDialog.dismiss();
        if(null != stationInfo) {
            // 測試寫入 DB
//            try {
//                StationInfo.Station station = stationInfo.getStation().get(0);
//                Log.d("msg","station: " + station);
//                StationDao stationDao = new StationDao(mContext);
//                stationDao.add(station);
//            } catch (Exception e) {
//                Log.d("msg", "Exception: " + e);
//            }
            StationDao stationDao = new StationDao(mContext);
            stationDao.createWithTransaction(stationInfo.getStation());
            tvStation.setText(stationInfo.getStation().toString());
        } else {
            tvStation.setText("取得資料失敗");
        }
    }
}
