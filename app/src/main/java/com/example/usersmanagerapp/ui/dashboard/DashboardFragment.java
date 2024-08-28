package com.example.usersmanagerapp.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usersmanagerapp.adapter.UserAdapter;
import com.example.usersmanagerapp.databinding.FragmentDashboardBinding;
import com.example.usersmanagerapp.models.User;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private ArrayList<User> userArrayList = new ArrayList<>();
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private DashboardViewModel dashboardViewModel;
    private UserAdapter userAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.progressBar = binding.progressBar;
        this.recyclerView = binding.recyclerView;

        dashboardViewModel.getProgressBarLiveData().observe(getViewLifecycleOwner(), progressBar::setVisibility);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(userArrayList);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setHasFixedSize(true);

        getUsers();

        return root;
    }

    private void getUsers() {
        dashboardViewModel.setProgressBarLiveData(ProgressBar.VISIBLE);
        dashboardViewModel.getUsersResponseLiveData().observe(getViewLifecycleOwner(), usersResponse -> {
            if (usersResponse != null && usersResponse.getUsers() != null && !usersResponse.getUsers().isEmpty()) {
                dashboardViewModel.setProgressBarLiveData(ProgressBar.GONE);
                userArrayList.addAll(usersResponse.getUsers());
                userAdapter.notifyDataSetChanged();
            } else {
                Log.d("TAG", "getUsers: " + usersResponse);
                dashboardViewModel.setProgressBarLiveData(ProgressBar.GONE);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}