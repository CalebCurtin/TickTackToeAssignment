package com.assignment.ticktacktoeassignment.gamescreen;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment.ticktacktoeassignment.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private ArrayList<RecyclerData> imageList;
    private GameScreenFragment gameFrag;
    private int gridSize;
    private GridLayoutManager gridLayoutManager;

    public RecyclerViewAdapter(ArrayList<RecyclerData> recyclerDataArrayList, GameScreenFragment gameFrag, GridLayoutManager gridLayoutManager) {
        this.imageList = recyclerDataArrayList;
        this.gameFrag = gameFrag; // There is probably a better way to do this, but I cant think of it right now
        this.gridLayoutManager = gridLayoutManager;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_card, parent, false);
        if (view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            this.gridSize = gridLayoutManager.getWidth() / gridLayoutManager.getSpanCount();
        } else {
            this.gridSize = gridLayoutManager.getHeight() / gridLayoutManager.getSpanCount();
        }
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        // Set the data to the imageview.
        RecyclerData recyclerData = imageList.get(position);
        holder.x = recyclerData.x;
        holder.y = recyclerData.y;
        holder.imageView.setImageResource(recyclerData.imageID);
        gameFrag.addImageViewToBoard(holder.x, holder.y, holder.imageView);

        // Work out how large the images should be based on the width of the screen
        Log.println(Log.INFO, "santasspy", "gridSquareWidth: " + gridSize);
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.width = gridSize;
        layoutParams.height = gridSize;

        holder.imageView.setLayoutParams(layoutParams);
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
            imageView.setImageAlpha(0);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gameFrag.playerClickedOn(x, y);
                }
            });
        }
    }
}
