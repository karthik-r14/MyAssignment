package com.example.kr_pc.myassignment;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText searchImageText;
    Button searchButton;
    ProgressBar progressBar;
    GridView gridView;
    ArrayList<String> imageUrlList;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.custom_search, null);

        progressBar = findViewById(R.id.progress_bar);
        gridView = findViewById(R.id.image_grid);
        searchImageText = view.findViewById(R.id.search_image_text);
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                loadImagesInGrid(searchImageText.getText().toString());

            }
        });

        actionBar.setCustomView(view);
    }

    private void loadImagesInGrid(String searchString) {
        int numOfImages = 40;
        String JSON_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&per_page=" + numOfImages + "&nojsoncallback=1&format=json&tags=" + searchString + "&api_key=2500f26f948508b2ec2144ad835d5508";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        progressBar.setVisibility(View.GONE);

                        try {
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            //Toast.makeText(getApplicationContext(), "Response : " + response, Toast.LENGTH_SHORT).show();
                            JSONArray photoArray = obj.getJSONObject("photos").getJSONArray("photo");
                            //Toast.makeText(getApplicationContext(), "photoArray : " + photoArray, Toast.LENGTH_SHORT).show();

                            imageUrlList = new ArrayList<>();
                            for (int i = 0; i < photoArray.length(); i++) {
                                JSONObject photoObject = photoArray.getJSONObject(i);
                                String imageUrl = "https://farm" + photoObject.getString("farm") + ".staticflickr.com/" + photoObject.getString("server") + "/" + photoObject.getString("id") + "_" + photoObject.getString("secret") + ".jpg";
                                imageUrlList.add(imageUrl);
                            }

                            adapter = new CustomAdapter(getApplicationContext(), imageUrlList);
                            gridView.setAdapter(adapter);
                            gridView.setVisibility(View.VISIBLE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.two_columns:
                gridView.setNumColumns(2);
                return true;
            case R.id.three_columns:
                gridView.setNumColumns(3);
                return true;
            case R.id.four_columns:
                gridView.setNumColumns(4);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}