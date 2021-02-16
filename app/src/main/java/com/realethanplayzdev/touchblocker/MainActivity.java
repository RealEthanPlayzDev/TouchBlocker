package com.realethanplayzdev.touchblocker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    // view definition
    private LinearLayout feedbackLinearLayout;

    // SharedPreferences definition
    private SharedPreferences settings;
    private SharedPreferences activityManager;

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

        // Prevents a crash if the user did a long touch (fact: almost forgot to add this lol)
        settings.edit().putString("onLongClickAmountCounter", "0").apply();

        // onLongClick for feedbackLinearLayout
        feedbackLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!settings.getString("onLongClickToasts", "").equals("false")) {
                    if (!settings.getString("onLongClickSettingsToasts", "").equals("false")) {
                        if ((Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) == 1 || (Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) > 1) {
                            if ((Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) == 2 || (Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) > 2) {
                                settings.edit().putString("onLongClickAmountCounter", "0").apply();
                                Utility.showToast(getApplicationContext(), "TouchBlocker: Opening to the settings activity. Long touch  was blocked.");
                                // TODO: make settings activity and fill here to start a intent to go to the settings activity
                            } else {
                                settings.edit().putString("onLongClickAmountCounter", "2").apply();
                                Utility.showToast(getApplicationContext(), "TouchBlocker: Do another long touch to open the settings (2/3). Long touch was also blocked");
                            }
                        } else {
                            settings.edit().putString("onLongClickAmountCounter", "1").apply();
                            Utility.showToast(getApplicationContext(), "TouchBlocker: Do another long touch 2 times to open the settings (1/3). Long touch was also blocked");
                        }
                    } else {
                        if ((Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) == 1 || (Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) > 1) {
                            if ((Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) == 2 || (Double.parseDouble(settings.getString("onLongClickAmountCounter", ""))) > 2) {
                                settings.edit().putString("onLongClickAmountCounter", "0").apply();
                                Utility.showToast(getApplicationContext(), "TouchBlocker: Long touch  was blocked.");
                                // TODO: make settings activity and fill here to start a intent to go to the settings activity
                            } else {
                                settings.edit().putString("onLongClickAmountCounter", "2").apply();
                                Utility.showToast(getApplicationContext(), "TouchBlocker: Long touch blocked.");
                            }
                        } else {
                            settings.edit().putString("onLongClickAmountCounter", "1").apply();
                            Utility.showToast(getApplicationContext(), "TouchBlocker: Long touch blocked.");
                        }
                    }
                }

                return true;
            }
        });

        // onTouch for feedbackLinearLayout
        feedbackLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.showToast(getApplicationContext(), "TouchBlocker: Touch blocked.");
            }
        });
    }



}