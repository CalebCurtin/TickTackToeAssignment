package com.assignment.ticktacktoeassignment.settingsscreen;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.assignment.ticktacktoeassignment.R;

public class ImageSpinnerArrayAdapter extends ArrayAdapter<Integer> {
    private Activity activity;
    public ImageSpinnerArrayAdapter(@NonNull Context context, Activity activity, Integer[] images) {
        super(context, android.R.layout.simple_spinner_item, images);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        int imageID = getItem(position);
        Drawable image = ResourcesCompat.getDrawable(activity.getResources(), imageID, null);

        label.setText("");
        image.setBounds(0, 0, 100, 100);
        label.setCompoundDrawables(null, null, image, null);

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        int imageID = getItem(position);
        Drawable image = ResourcesCompat.getDrawable(activity.getResources(), imageID, null);

        label.setText("");
        image.setBounds(0, 0, 100, 100);
        label.setCompoundDrawables(null, null, image, null);

        return label;
    }
}
