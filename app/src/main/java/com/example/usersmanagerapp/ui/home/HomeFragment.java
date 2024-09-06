package com.example.usersmanagerapp.ui.home;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.usersmanagerapp.R;
import com.example.usersmanagerapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button buttonShowUsers = binding.buttonShowUsers;
        NavController navController = NavHostFragment.findNavController(this);

        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this.getContext(),
                R.animator.enter_easy_in);
        set.setTarget(buttonShowUsers);
        set.start();

        buttonShowUsers.setOnClickListener(v -> navController.navigate(R.id.action_navigation_home_to_navigation_dashboard));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}