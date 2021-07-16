package com.project.cmcontractor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.cmcontractor.R;

public class SettingsFragment extends Fragment {

    private View view;

    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        // set
        user = FirebaseAuth.getInstance().getCurrentUser();


        // find view by id


        // load data / set


        // listeners

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.home_menu, menu);
    }

    private boolean isUserExist() {
        boolean value = false;
        if (user != null) {
            value = true;
        } else {
            value = false;
        }
        return value;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {

        MenuItem login = menu.findItem(R.id.nav_login);
        MenuItem logout = menu.findItem(R.id.nav_logout);

        if (isUserExist()) {
            login.setVisible(false);
            logout.setVisible(true);
        } else {
            login.setVisible(true);
            logout.setVisible(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_login:
                SettingsFragmentDirections.NavigateToLoginFromSettings action =
                        SettingsFragmentDirections.navigateToLoginFromSettings("companyid", "planname");
                Navigation.findNavController(view).navigate(action);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "You have Logged out", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

}