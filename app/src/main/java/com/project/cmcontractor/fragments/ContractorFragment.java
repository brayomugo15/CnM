package com.project.cmcontractor.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.cmcontractor.R;
import com.project.cmcontractor.adapters.ContractorAdapter;
import com.project.cmcontractor.models.CompanyProfile;
import com.project.cmcontractor.utils.BottomNavLocker;


public class ContractorFragment extends Fragment {

    private View view;
    private TextView tvPlanName;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private ContractorFragmentArgs args;

    private String planname, companyid;

    private FirebaseUser user;
    private FirebaseRecyclerOptions<CompanyProfile> options;
    private DatabaseReference profilereference, plansreference;

    private ContractorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contractor, container, false);

        // set values
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        args = ContractorFragmentArgs.fromBundle(getArguments());
        planname = args.getPlanName().toLowerCase();

        plansreference = FirebaseDatabase.getInstance().getReference().child("CompanyPlans");
        profilereference = FirebaseDatabase.getInstance().getReference().child("Profile");

        // find view by id
        tvPlanName = view.findViewById(R.id.tv_contractor_planname);
        recyclerView = view.findViewById(R.id.recyclerview_contractor);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // set listeners

        loadData();

        return view;
    }


    private void loadData() {

        plansreference.orderByChild("planname").equalTo(planname).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    companyid = snap.child("companyid").getValue().toString();
                    Toast.makeText(getContext(), "ID = " + companyid, Toast.LENGTH_SHORT).show();
                }

                options = new FirebaseRecyclerOptions.Builder<CompanyProfile>()
                        .setQuery(profilereference.orderByChild("companyid").equalTo(companyid), CompanyProfile.class)
                        .build();

                adapter = new ContractorAdapter(options, getActivity(), planname);
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }
}