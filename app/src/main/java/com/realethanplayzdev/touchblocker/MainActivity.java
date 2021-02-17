package com.realethanplayzdev.touchblocker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // view definition
    private LinearLayout feedbackLinearLayout;

    // SharedPreferences definition
    private SharedPreferences settings;

    private ActivityManager activityManager;
    private ActivityManagerActivity activityManagerActivity;

    private int onLongClickCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            if(getActionBar() != null) { this.getActionBar().hide();}
            if(getSupportActionBar() != null) { this.getSupportActionBar().hide(); }
        } catch(Exception e) {
            e.printStackTrace();
        }

        feedbackLinearLayout = (LinearLayout)findViewById(R.id.feedbackLinearLayout);
        settings = getSharedPreferences("settings", Activity.MODE_PRIVATE);

        activityManager = new ActivityManager(getApplicationContext(), getSharedPreferences("activityManagerData", Activity.MODE_PRIVATE));
        activityManagerActivity = activityManager.initActivity("MainActivity");

        // I'm just gonna be honest but not sure if this is a good practice or not. - Ethan
        if(!settings.getBoolean("firstTimeLaunch",true)) {
            settings.edit().putBoolean("showOnClickToasts", true).apply();
            settings.edit().putBoolean("showOnLongClickToasts", true).apply();
            settings.edit().putBoolean("showOnLongClickCounters", true).apply();
            settings.edit().putBoolean("firstTimeLaunch", true).apply();
        }

        // Prevents a crash if the user did a long touch (fact: almost forgot to add this lol)
        settings.edit().putString("onLongClickAmountCounter", "0").apply();

        // onLongClick for feedbackLinearLayout
        feedbackLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(settings.getBoolean("showOnLongClickToasts", true)) { // showing onLongClick enabled
                    if(settings.getBoolean("showOnLongClickCounters", true)) { // showing settings onLongClick counter enabled
                        if(onLongClickCounter == 1 |  onLongClickCounter  > 1) {
                            if(onLongClickCounter == 2 | onLongClickCounter > 1) {
                                onLongClickCounter = 0;
                                Utility.showToast(getApplicationContext(), "TouchBlocker: Launching TouchBlocker settings. onLongTouch blocked.");
                                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                            } else {
                                onLongClickCounter  = 2;
                                Utility.showToast(getApplicationContext(),"TouchBlocker: Do another long touch to open TouchBlocker settings (2/3). onLongTouch blocked.");
                            }
                        } else {
                            onLongClickCounter = 1;
                            Utility.showToast(getApplicationContext(),"TouchBlocker: Do 2 more long touches to open TouchBlocker settings (1/3). onLongTouch blocked.");
                        }
                    } else { // showing settings onLongClick counter disabled
                        if(onLongClickCounter == 1 |  onLongClickCounter  > 1) {
                            if(onLongClickCounter == 2 | onLongClickCounter > 1) {
                                onLongClickCounter = 0;
                                Utility.showToast(getApplicationContext(), "TouchBlocker: onLongTouch blocked.");
                                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                            } else {
                                onLongClickCounter  = 2;
                                Utility.showToast(getApplicationContext(),"TouchBlocker: onLongTouch blocked.");
                            }
                        } else {
                            onLongClickCounter = 1;
                            Utility.showToast(getApplicationContext(),"TouchBlocker: onLongTouch blocked.");
                        }
                    }
                } else { // showing onLongClick disabled
                    if(onLongClickCounter == 1 |  onLongClickCounter  > 1) {
                        if(onLongClickCounter == 2 | onLongClickCounter > 1) {
                            onLongClickCounter = 0;
                            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        } else {
                            onLongClickCounter  = 2;
                        }
                    } else {
                        onLongClickCounter = 1;
                    }
                }

                return true;
            }
        });

        // onTouch for feedbackLinearLayout
        feedbackLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(settings.getBoolean("showOnClickToasts", true)) {
                    Utility.showToast(getApplicationContext(), "TouchBlocker: Touch blocked.");
                }
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