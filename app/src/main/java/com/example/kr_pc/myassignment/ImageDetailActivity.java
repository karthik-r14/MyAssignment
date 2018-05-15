package com.example.kr_pc.myassignment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDetailActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "image_url";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);

        imageView = findViewById(R.id.image);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(IMAGE_URL);

        Picasso.with(getApplicationContext())
                .load(imageUrl)
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.error_image)
                .into(imageView);
    }
}
