package com.example.rxjava.hank.rxjavasample.DataInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StationInfo {

    private String msg, lang;
    private int status;
    private ArrayList<Data> datas;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public String getLang() {
        return lang;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public ArrayList<Data> getData() {
        return datas;
    }

    public void setData(ArrayList<Data> datas) {
        this.datas = datas;
    }

    public JSONArray getJsonArrayData() {
        JSONArray array = new JSONArray();
        for(Data item: datas) {
            try {
                JSONObject json = new JSONObject(item.toString());
                array.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("data", getJsonArrayData());
            json.put("msg", msg);
            json.put("lang", lang);
            json.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public class Data {
        private int id, lockNum, lockMax;
        private String lat, lon, name;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getLockNum() {
            return lockNum;
        }

        public int getLockMax() {
            return lockMax;
        }

        public String getLat() {
            return lat;
        }

        public String getLon() {
            return lon;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLockNum(int lockNum) {
            this.lockNum = lockNum;
        }

        public void setLockMax(int lockMax) {
            this.lockMax = lockMax;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        @Override
        public String toString() {
            JSONObject json = new JSONObject();
            try {
                json.put("id", id);
                json.put("name", name);
                json.put("lat", lat);
                json.put("lon", lon);
                json.put("lock_num", lockNum);
                json.put("lock_max", lockMax);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json.toString();
        }
    }

}
