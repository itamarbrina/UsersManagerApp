package com.example.usersmanagerapp.ui.dashboard;

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

import com.example.usersmanagerapp.adapter.OnRecycleViewItemClickListener;
import com.example.usersmanagerapp.adapter.UserAdapter;
import com.example.usersmanagerapp.databinding.FragmentDashboardBinding;
import com.example.usersmanagerapp.models.User;

import java.util.ArrayList;

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


        setupRecyclerView();
        observeViewModel();

        return root;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter(userArrayList, new OnRecycleViewItemClickListener() {
            @Override
            public void onEditClick(int position) {
                dashboardViewModel.insertUser(userArrayList.get(position));
                userAdapter.notifyItemChanged(position);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
