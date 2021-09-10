package com.my.first.taller_sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewsDeatilActivity extends AppCompatActivity {
    String title, content, desc, imageURL,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_deatil);
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");
        desc = getIntent().getStringExtra("desc");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");
    }
}