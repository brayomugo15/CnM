package com.project.cmcontractor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.cmcontractor.R;
import com.project.cmcontractor.adapters.ContractorAdapter;
import com.project.cmcontractor.adapters.OrderAdapter;
import com.project.cmcontractor.models.CompanyProfile;
import com.project.cmcontractor.models.Order;

public class OrdersFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;

    private String email, companyname, companyid;

    private FirebaseUser user;
    private DatabaseReference companyreference, orderreference;
    private FirebaseRecyclerOptions<Order> options;

    private OrderAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_orders, container, false);

        // set
        companyreference = FirebaseDatabase.getInstance().getReference().child("Profile");
        orderreference = FirebaseDatabase.getInstance().getReference().child("Orders");

        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            email = user.getEmail();

            loadData();
        } else {
            Toast.makeText(getContext(), "You have not logged in", Toast.LENGTH_SHORT).show();
        }

        // find view by id
        recyclerView = view.findViewById(R.id.recyclerview_orders);

        // set / load


        return view;
    }

    private void loadRecyclerData() {
        options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(orderreference.orderByChild("email").equalTo(email), Order.class)
                .build();

        adapter = new OrderAdapter(options, getActivity(), companyname);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void loadData() {
        orderreference.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    companyid = snapshot.child("companyid").getValue().toString();
                }

                companyreference.orderByChild("companyid").equalTo(companyid).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if (snapshot != null) {
                            companyname = snapshot.child("companyname").getValue().toString();
                        }

                        loadRecyclerData();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
                OrdersFragmentDirections.NavigateToLoginFromOrders action =
                        OrdersFragmentDirections.navigateToLoginFromOrders("companyid", "planname");
                Navigation.findNavController(view).navigate(action);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "You have Logged out", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}