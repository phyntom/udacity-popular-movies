package com.phyntom.android.popular_movies;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class NoInternet extends AppCompatActivity {

    private TextView noInternetTextView;

    private ImageView noInternetImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        noInternetTextView =(TextView) findViewById(R.id.tv_no_internet);
        noInternetImageView = (ImageView) findViewById(R.id.iv_no_internet);
        String noInternet=getString(R.string.error_internet);
        noInternetTextView.setText(noInternet);
    }
}
