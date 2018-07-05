package com.example.rxjava.hank.rxjavasample.DataInfo;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VersionInfo {

    private String version, msg, lang;
    private int status;

    public String getVersion() {
        return version;
    }

    public String getMsg() {
        return msg;
    }

    public String getLang() {
        return lang;
    }

    public int getStatus() {
        return status;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("version", version);
            json.put("msg", msg);
            json.put("lang", lang);
            json.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}


