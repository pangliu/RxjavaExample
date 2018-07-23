package com.example.rxjava.hank.rxjavasample.DataInfo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StationInfo {

    private String msg, lang;
    private int status;
    /**
     * 命名要與回傳 json key 一樣，不然無法對應會拋出 Exception
     */
    private ArrayList<Station> datas;

    public StationInfo(){}

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

    public ArrayList<Station> getStation() {
        return datas;
    }

    public void setStation(ArrayList<Station> stations) {
        this.datas = stations;
    }

    public JSONArray getJsonArrayData() {
        JSONArray array = new JSONArray();
        for(Station item: datas) {
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
            json.put("datas", getJsonArrayData());
            json.put("msg", msg);
            json.put("lang", lang);
            json.put("status", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * Station 要宣告為 static 不然在 DatabaseHelper 會無法被 new 出來
     * 不然就要獨立一個 class
     */
    @DatabaseTable(tableName = "station")
    public static class Station {
        @DatabaseField(id = true, columnName = "id")
        private int id;
        private int lockNum;
        private int lockMax;
        @DatabaseField(columnName = "lat")
        private String lat;
        @DatabaseField(columnName = "lon")
        private String lon;
        @DatabaseField(columnName = "name")
        private String name;

        public Station() { }

        public Station(int id, int lockNum, int lockMax, String lat, String lon, String name) {
            this.id = id;
            this.lockNum = lockNum;
            this.lockMax = lockMax;
            this.lat = lat;
            this.lon = lon;
            this.name = name;
        }

        private int getId() {
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
