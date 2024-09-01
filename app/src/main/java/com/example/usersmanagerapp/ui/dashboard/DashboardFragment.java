package com.example.usersmanagerapp.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersmanagerapp.R;
import com.example.usersmanagerapp.adapter.OnRecycleViewItemClickListener;
import com.example.usersmanagerapp.adapter.UserAdapter;
import com.example.usersmanagerapp.databinding.DialogEditUserBinding;
import com.example.usersmanagerapp.databinding.FragmentDashboardBinding;
import com.example.usersmanagerapp.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private final ArrayList<User> userArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private DashboardViewModel dashboardViewModel;
    private UserAdapter userAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        progressBar = binding.progressBar;
        recyclerView = binding.recyclerView;
        binding.buttonAddUser.setOnClickListener(v -> showEditUserDialog(-1));


        setupRecyclerView();
        observeViewModel();

        return root;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(userArrayList, new OnRecycleViewItemClickListener() {
            @Override
            public void onEditClick(int position) {
                showEditUserDialog(position);
            }

            @Override
            public void onDeleteClick(int position) {
                dashboardViewModel.deleteUser(userArrayList.get(position));
                userArrayList.remove(position);
                userAdapter.notifyItemRemoved(position);
            }
        });
        recyclerView.setAdapter(userAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void observeViewModel() {
        dashboardViewModel.getProgressBarLiveData().observe(getViewLifecycleOwner(), progressBar::setVisibility);

        dashboardViewModel.getUsersLiveData().observe(getViewLifecycleOwner(), usersList -> {
            if (usersList != null) {
                userArrayList.clear();
                userArrayList.addAll(usersList);
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    private void showEditUserDialog(int position) {
        DialogEditUserBinding dialogBinding = DialogEditUserBinding.inflate(getLayoutInflater());
        User user = null;
        if (position == -1) {
            dialogBinding.editUserHeader.setText(getText(R.string.add_user));
            user = new User();
            user.setId(userArrayList.get(userArrayList.size() - 1).getId() + 1);
        } else {
            user = userArrayList.get(position);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(dialogBinding.getRoot());

        dialogBinding.editTextImage.setText(user.getImageUrl());
        dialogBinding.editTextFirstName.setText(user.getFirstName());
        dialogBinding.editTextLastName.setText(user.getLastName());
        dialogBinding.editTextEmail.setText(user.getEmail());

        AlertDialog dialog = builder.create();

        User finalUser = user;
        dialogBinding.buttonSaveUser.setOnClickListener(v -> {

            dialogBinding.editTextFirstNameLayout.setError(null);
            dialogBinding.editTextLastNameLayout.setError(null);
            dialogBinding.editTextEmailLayout.setError(null);
            dialogBinding.editTextImageLayout.setError(null);

            finalUser.setImageUrl(Objects.requireNonNull(dialogBinding.editTextImage.getText()).toString().trim());
            finalUser.setFirstName(Objects.requireNonNull(dialogBinding.editTextFirstName.getText()).toString().trim());
            finalUser.setLastName(Objects.requireNonNull(dialogBinding.editTextLastName.getText()).toString().trim());
            finalUser.setEmail(Objects.requireNonNull(dialogBinding.editTextEmail.getText()).toString().trim());
            boolean isValid = true;

            if (finalUser.getFirstName().isEmpty()) {
                dialogBinding.editTextFirstNameLayout.setError("First name is required");
                isValid = false;
            }

            if (finalUser.getLastName().isEmpty()) {
                dialogBinding.editTextLastNameLayout.setError("Last name is required");
                isValid = false;
            }

            if (finalUser.getEmail().isEmpty()) {
                dialogBinding.editTextEmailLayout.setError("Email is required");
                isValid = false;
            }

            if (finalUser.getImageUrl().isEmpty()) {
                dialogBinding.editTextImageLayout.setError("Image URL is required");
                isValid = false;
            }

            if (isValid) {
                dashboardViewModel.insertUser(finalUser);
                if (position == -1) {
                    userArrayList.add(finalUser);
                    userAdapter.notifyItemInserted(userArrayList.size() - 1);
                } else {
                    userAdapter.notifyItemChanged(position);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
