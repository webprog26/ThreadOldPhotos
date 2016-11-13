package com.example.webprog26.threadsoldphotos;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by webprog26 on 13.11.2016.
 */

public class GridItemAdapter extends ArrayAdapter<Bitmap> {

    private Activity mActivity;
    public GridItemAdapter(Activity activity, ArrayList<Bitmap> bitmaps) {
        super(activity, 0, bitmaps);
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mActivity.getLayoutInflater().inflate(R.layout.grid_item, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        imageView.setImageBitmap(getItem(position));
        return convertView;
    }
}
