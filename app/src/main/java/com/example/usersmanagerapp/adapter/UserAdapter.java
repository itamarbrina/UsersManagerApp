package com.example.usersmanagerapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usersmanagerapp.R;
import com.example.usersmanagerapp.models.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final Context context;
    ArrayList<User> userArrayList;

    public UserAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.contactNameTextView.setText(user.getFirstName() + " " + user.getLastName());
        holder.emailTextView.setText(user.getEmail());
        Glide.with(context)
                .load(user.getImageUrl())
                .into(holder.profileImageView);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ShapeableImageView profileImageView;
        private final TextView contactNameTextView;
        private final TextView emailTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImageView = itemView.findViewById(R.id.profileImageView);
            contactNameTextView = itemView.findViewById(R.id.contactNameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
        }
    }
}
