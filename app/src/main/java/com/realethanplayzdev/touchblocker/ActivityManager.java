package com.realethanplayzdev.touchblocker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.*;

public class ActivityManager {
    private SharedPreferences activityManagerData;
    private Context appContext;

    public ActivityManager(Context newAppContext) {
        appContext = newAppContext;
        activityManagerData = appContext.getSharedPreferences("activityManagerDataStorage", Activity.MODE_PRIVATE);
    }

    public ActivityManagerActivity initializeActivity(String activityName) {
        ActivityManagerActivity a = new ActivityManagerActivity(activityName, appContext);
        activityManagerData.edit().putString(activityName, new Gson().toJson(a)).apply();
        return a;
    }

    public void updateActivity(String activityName, ActivityManagerActivity  newActivityManagerActivity) {
        // This doesn't have a security check so yeah

        activityManagerData.edit().putString(activityName, new Gson().toJson(newActivityManagerActivity)).apply();
    }

    public void destroyActivity(String destroyedActivityName) {
        try {
            activityManagerData.edit().remove(destroyedActivityName).apply();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
