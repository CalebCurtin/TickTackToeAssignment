package com.assignment.ticktacktoeassignment.settingsscreen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

public class ImageSpinnerArrayAdapter extends ArrayAdapter<Drawable> {
    public ImageSpinnerArrayAdapter(@NonNull Context context, int resource, Drawable[] images) {
        super(context, android.R.layout.simple_spinner_item, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        Drawable image = getItem(position);
        label.setText("");
        image.setBounds(0, 0, 100, 100);
        label.setCompoundDrawables(null, null, image, null);

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        Drawable image = getItem(position);
        label.setText("");
        image.setBounds(0, 0, 100, 100);
        label.setCompoundDrawables(null, null, image, null);

        return label;
    }
}
