package com.assignment.ticktacktoeassignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AvatarRecyclerViewAdapter extends RecyclerView.Adapter<AvatarRecyclerViewAdapter.AvatarViewHolder> {

    private ArrayList<AvatarData> avatarData;

    public AvatarRecyclerViewAdapter(ArrayList<AvatarData> avatarData) {
        this.avatarData = avatarData;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avatar_layout, parent, false);
        AvatarViewHolder avatarViewHolder = new AvatarViewHolder(view);
        return new AvatarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {
        AvatarData avatar = avatarData.get(position);

        int imageResourceId = holder.itemView.getContext().getResources().getIdentifier(
                avatar.getImageId(), "drawable", holder.itemView.getContext().getPackageName());

        if (imageResourceId != 0) {
            holder.avatarImage.setImageResource(imageResourceId);
        } else {
            Log.e("AvatarRecyclerViewAdapter", "Image file not found for: " + avatar.getImageId());
        }
    }
    @Override
    public int getItemCount() {
        return avatarData.size();
    }

    public class AvatarViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImage;

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.imageView);
        }
    }
}
