package com.example.usersmanagerapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.usersmanagerapp.databinding.ItemContactBinding;
import com.example.usersmanagerapp.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private final ArrayList<User> userArrayList;
    private final OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public UserAdapter(ArrayList<User> userArrayList, OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.userArrayList = userArrayList;
        this.onRecycleViewItemClickListener = onRecycleViewItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactBinding binding = ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, onRecycleViewItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemContactBinding binding;

        public ViewHolder(ItemContactBinding binding, OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
            super(binding.getRoot());
            this.binding = binding;

            binding.editContactButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onRecycleViewItemClickListener.onEditClick(position);
                }
            });

            binding.deleteContactButton.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    onRecycleViewItemClickListener.onDeleteClick(position);
                }
            });
        }

        public void bind(User user) {
            binding.contactNameTextView.setText(user.getFirstName() + " " + user.getLastName());
            binding.emailTextView.setText(user.getEmail());
            Glide.with(binding.getRoot().getContext())
                    .load(user.getImageUrl())
                    .into(binding.profileImageView);
        }
    }
}
