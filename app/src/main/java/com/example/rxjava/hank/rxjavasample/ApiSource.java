package com.example.rxjava.hank.rxjavasample;

import android.util.Log;

import com.example.rxjava.hank.rxjavasample.DataInfo.StationInfo;
import com.example.rxjava.hank.rxjavasample.DataInfo.VersionInfo;
import com.example.rxjava.hank.rxjavasample.Helper.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiSource {

    public interface ApiService {
        @POST("/api_v1/version")
        Flowable<VersionInfo> postVersion(@Body RequestBody body);

        /**
         * 如果回傳 Json 要自己 parse
         * 用 Flowable<Object> 再把 Object 自己轉回 json，自己 parse
         */
        @POST("/api_v1/show_stations")
        Flowable<Object> postStation(@Body RequestBody body);

        @POST("/api_v1/show_stations")
        Flowable<StationInfo> postStationInfo(@Body RequestBody body);
    }

    private ApiService apiService;
    private static ApiSource INSTANCE;
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    public static ApiSource getInstance() {
        if(INSTANCE == null){
            INSTANCE = new ApiSource();
        }
        return INSTANCE;
    }

    public ApiSource() {
        Log.d("msg", "create ApiSource");
        apiService = RetrofitHelper.getRetrofit().create(ApiService.class);
    }

    public Flowable<VersionInfo> postVersion(String device) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
        return apiService.postVersion(body);
    }

    public Flowable<Object> postStation() {
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
        return apiService.postStation(body);
    }

    public Flowable<StationInfo> postStationInfo() {
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
        return apiService.postStationInfo(body);
    }

}
