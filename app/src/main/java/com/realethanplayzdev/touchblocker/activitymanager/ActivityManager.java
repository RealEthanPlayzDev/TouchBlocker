package com.realethanplayzdev.touchblocker.activitymanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.*;

public class ActivityManager {
    private Context appContext;
    private SharedPreferences activityManagerData;

    public ActivityManager(Context currentAppContext, SharedPreferences dataSP) {
        appContext = currentAppContext;
        activityManagerData = dataSP;
    }

    public ActivityManagerActivity initActivity(String newActivityName) {
        ActivityManagerActivity a = new ActivityManagerActivity(newActivityName);
        activityManagerData.edit().putString(newActivityName, new Gson().toJson(a)).apply();
        return a;
    }

    public void updateActivity(String activityName, ActivityManagerActivity newActivityManagerActivity) {
        activityManagerData.edit().putString(activityName, new Gson().toJson(newActivityManagerActivity)).apply();
    }

    public void destroyActivity(String toBeDestroyedActivityName) {
        activityManagerData.edit().remove(toBeDestroyedActivityName).apply();
    }

    public ActivityManagerActivity getActivityManagerActivityObject(String targetActivityName) {
        String json = activityManagerData.getString(targetActivityName, "");
        if(!json.equals("")) {
            return new Gson().fromJson(json, ActivityManagerActivity.class);
        } else {
            ActivityManagerActivity a = new ActivityManagerActivity(targetActivityName);
            a.setActivityState(ActivityState.STATE_DOESNT_EXIST);
            return a;
        }
    }
}
