package com.example.rxjava.hank.rxjavasample;

import android.util.Log;

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
        apiService = RetrofitHelper.getVersion().create(ApiService.class);
    }

    public Flowable<VersionInfo> postVersion(String device) {
        Log.d("msg", "postVersion: " + device);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("device", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE, jsonObject.toString());
        Log.d("msg", "requestBody: " + body);
        return apiService.postVersion(body);
    }

}
