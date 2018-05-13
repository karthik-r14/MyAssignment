package com.example.kr_pc.myassignment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter {
    ArrayList<String> imageUrlList;

    public CustomAdapter(@NonNull Context context, ArrayList<String> imageUrlList) {
        super(context, R.layout.custom_gridview_item, R.id.grid_item, imageUrlList);
        this.imageUrlList = imageUrlList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridItem = inflater.inflate(R.layout.custom_gridview_item, parent, false);
        ImageView imageView = gridItem.findViewById(R.id.grid_item);

        Picasso.with(getContext())
                .load(imageUrlList.get(position))
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.error_image)
                .transform(new CropSquareTransformation())
                .into(imageView);

        return gridItem;
    }
}
