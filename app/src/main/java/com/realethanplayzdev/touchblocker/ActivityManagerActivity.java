package com.realethanplayzdev.touchblocker;

import android.app.Activity;
import android.content.Context;

public class ActivityManagerActivity {
    private boolean activityExists = false;
    private boolean activityDestroyed = false;
    private ActivityManagerActivityState activityState = ActivityManagerActivityState.ACTIVITY_STATE_UNDEFINED;
    private String activityName;

    private Context appContext;

    public ActivityManagerActivity(String newActivityName, Context newAppContext) {
        activityName = newActivityName;
        activityExists = true;
        activityState = ActivityManagerActivityState.ACTIVITY_IN_FOREGROUND;
        appContext = newAppContext;
    }

    // Activity state
    public void setActivityState(ActivityManagerActivityState newState) {
        activityState = newState;
    }

    public ActivityManagerActivityState getActivityState() {
        return activityState;
    }

    // Get defined activity name
    public String getActivityName() {
        return activityName;
    }

    // Destroyed
    public void destroy() {
        activityExists = false;
        activityState = ActivityManagerActivityState.ACTIVITY_DESTROYED;
        activityDestroyed = true;
        new ActivityManager(appContext).destroyActivity(activityName);
    }
}
