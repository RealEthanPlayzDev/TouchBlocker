package com.realethanplayzdev.touchblocker;

import android.content.Intent;
import android.os.Build;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import com.realethanplayzdev.touchblocker.activitymanager.ActivityManager;
import com.realethanplayzdev.touchblocker.activitymanager.ActivityManagerActivity;
import com.realethanplayzdev.touchblocker.activitymanager.ActivityState;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.N)
public class QuickSettingsHandleService extends TileService {
    private ActivityManager activityManager;
    private ActivityManagerActivity MainActivityAM;
    private ActivityManagerActivity SettingsActivityAM;

    //private boolean mainActivityExists = false;
    //private boolean settingsActivityExists = false;

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        final Tile qsTile = getQsTile();
        activityManager = new ActivityManager(getApplicationContext(), getSharedPreferences("activityManagerData", 0)); // 0 is Activity.MODE_PRIVATE I think
        MainActivityAM = activityManager.getActivityManagerActivityObject("MainActivity");
        SettingsActivityAM = activityManager.getActivityManagerActivityObject("SettingsActivity");

        if(MainActivityAM.getActivityState() == ActivityState.ACTIVITY_IN_FOREGROUND) {
            qsTile.setState(Tile.STATE_ACTIVE);
        } else {
            if(SettingsActivityAM.getActivityState() == ActivityState.ACTIVITY_IN_FOREGROUND) {
                qsTile.setState(Tile.STATE_ACTIVE);
            } else {
                qsTile.setState(Tile.STATE_INACTIVE);
            }
        }
        qsTile.updateTile();
    }

    @Override
    public void onClick() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK));
        final Tile qsTile = getQsTile();
        qsTile.setState(qsTile.STATE_ACTIVE);
        qsTile.updateTile();
    }
}
