package com.realethanplayzdev.touchblocker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.realethanplayzdev.touchblocker.activitymanager.ActivityManager;
import com.realethanplayzdev.touchblocker.activitymanager.ActivityManagerActivity;

public class SettingsActivity extends AppCompatActivity {

    // view definition
    private Switch showoctoasts;
    private Switch showolctoasts;
    private Switch showolcsettingscounter;
    private Button launchnewwindow;
    private Button resetsettings;

    // SharedPrefences
    private SharedPreferences settings;

    private Intent activityChange;

    private ActivityManager activityManager;
    private ActivityManagerActivity activityManagerActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        showoctoasts = (Switch)findViewById(R.id.showoctoasts);
        showolctoasts = (Switch)findViewById(R.id.showolctoasts);
        showolcsettingscounter = (Switch)findViewById(R.id.showolcsettingscounter);
        launchnewwindow = (Button)findViewById(R.id.launchnewindow);
        resetsettings = (Button)findViewById(R.id.resetsettings);

        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);

        // Fetch the current settings and then apply it to the switches
        showoctoasts.setChecked(settings.getBoolean("showOnClickToasts", true));
        showolctoasts.setChecked(settings.getBoolean("showOnLongClickToasts", true));
        showolcsettingscounter.setChecked(settings.getBoolean("showOnLongClickCounters", true));

        activityManager = new ActivityManager(getApplicationContext(), getSharedPreferences("activityManagerData", Activity.MODE_PRIVATE));
        activityManagerActivity = activityManager.initActivity("SettingsActivity");

        showoctoasts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settings.edit().putBoolean("showOnClickToasts", true).apply();
                } else {
                    settings.edit().putBoolean("showOnClickToasts", false).apply();
                }
            }
        });

        showolctoasts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settings.edit().putBoolean("showOnLongClickToasts", true).apply();
                } else {
                    settings.edit().putBoolean("showOnLongClickToasts", false).apply();
                }
            }
        });

        showolcsettingscounter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settings.edit().putBoolean("showOnLongClickCounters", true).apply();
                } else {
                    settings.edit().putBoolean("showOnLongClickCounters", false).apply();
                }
            }
        });

        launchnewwindow.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        resetsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.edit().clear().apply();
                AlertDialog notifDiag = new AlertDialog.Builder(SettingsActivity.this)
                        .setTitle("Reset settings")
                        .setMessage("Please restart the app and close remaining running activities first.")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();

                notifDiag.show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityManagerActivity.onActivityDestroy();
        activityManager.destroyActivity(activityManagerActivity.getActivityName());
    }

}