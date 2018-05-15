package com.example.kr_pc.myassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class CustomAdapter extends ArrayAdapter {
    ArrayList<String> imageUrlList;
    Context context;

    public CustomAdapter(@NonNull Context context, ArrayList<String> imageUrlList) {
        super(context, R.layout.custom_gridview_item, R.id.grid_item, imageUrlList);
        this.imageUrlList = imageUrlList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridItem = inflater.inflate(R.layout.custom_gridview_item, parent, false);
        final ImageView imageView = gridItem.findViewById(R.id.grid_item);

        Picasso.with(getContext())
                .load(imageUrlList.get(position))
                .placeholder(R.drawable.loading_image)
                .error(R.drawable.error_image)
                .transform(new CropSquareTransformation())
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ImageDetailActivity.class);
                intent.putExtra(ImageDetailActivity.IMAGE_URL, imageUrlList.get(position));
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, imageView, "my_image");
                getContext().startActivity(intent, options.toBundle());
            }
        });

        return gridItem;
    }
}
