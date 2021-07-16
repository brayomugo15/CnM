package com.project.cmcontractor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.cmcontractor.R;
import com.project.cmcontractor.adapters.PlansAdapter;
import com.project.cmcontractor.models.Plans;
import com.project.cmcontractor.utils.BottomNavLocker;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;

    private RecyclerView recyclerview;
    private LinearLayoutManager layoutManager;
    private ArrayList<Plans> data;
    private PlansAdapter adapter;

    private FirebaseUser user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //set
        ((BottomNavLocker) getActivity()).BottomNavLocked(false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview_plans);

        plansRecyclerView();

        return view;
    }

    private void plansRecyclerView() {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(layoutManager);

        recyclerview.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<Plans>();
        for (int i = 0; i < com.project.cmcontractor.data.Plans.planname.length; i++) {
            data.add(
                    new Plans(
                            com.project.cmcontractor.data.Plans.planname[i],
                            com.project.cmcontractor.data.Plans.plantype[i],
                            com.project.cmcontractor.data.Plans.planimage[i]
                    ));
        }

        adapter = new PlansAdapter(data, getContext());
        recyclerview.setAdapter(adapter);
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
        switch (item.getItemId()) {
            case R.id.nav_login:
                HomeFragmentDirections.NavigateToLoginFromHome action =
                        HomeFragmentDirections.navigateToLoginFromHome("companyid", "planname");
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