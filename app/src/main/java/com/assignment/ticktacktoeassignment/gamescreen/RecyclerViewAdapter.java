package com.assignment.ticktacktoeassignment.gamescreen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.ticktacktoeassignment.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<RecyclerData> imageList;
    private GameScreenFragment gameFrag;

    public RecyclerViewAdapter(ArrayList<RecyclerData> recyclerDataArrayList, GameScreenFragment gameFrag) {
        this.imageList = recyclerDataArrayList;
        this.gameFrag = gameFrag; // There is probably a better way to do this, but I cant think of it right now
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        RecyclerData recyclerData = imageList.get(position);
        holder.x = recyclerData.x;
        holder.y = recyclerData.y;
        holder.imageView.setImageResource(recyclerData.imageID);
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return imageList.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        public int x;
        public int y;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.gameImageViewCard);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageView.setAlpha(1.0f);
                    gameFrag.placeToken(x, y, imageView);
                    gameFrag.checkWin(x, y);
                }
            });
        }
    }
}