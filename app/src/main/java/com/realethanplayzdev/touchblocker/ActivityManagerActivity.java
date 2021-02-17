package com.realethanplayzdev.touchblocker;

public class ActivityManagerActivity {
    private boolean activityExists = false;
    private boolean activityDestroyed = false;
    private ActivityState state = ActivityState.STATE_UNDEFINED;
    private String activityName = "UNDEFINED";

    public ActivityManagerActivity(String newActivityName) {
        activityName = newActivityName;
        activityExists = true;
        activityDestroyed = false;
        state = ActivityState.ACTIVITY_IN_FOREGROUND;
    }

    // Get Activity State
    public ActivityState getActivityState() {
        return state;
    }

    // Destroy
    public void onActivityDestroy() {
        activityDestroyed = true;
        activityExists = false;
        state = ActivityState.ACTIVITY_DESTROYED;
    }

    // Get defined activity name
    public String getActivityName() {
        return activityName;
    }
}