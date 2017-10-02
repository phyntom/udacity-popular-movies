package com.phyntom.android.popular_movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;

// Based on : https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-concepts/en/Unit%204/92_c_app_settings.html
// the code was adapted from the reference above. The blog explains how to use settings
// 2017-09-30

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager().beginTransaction().
                replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String key) {
            setPreferencesFromResource(R.xml.preferences, key);
        }
    }
}
