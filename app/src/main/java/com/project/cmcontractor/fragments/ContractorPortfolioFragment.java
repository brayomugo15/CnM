package com.project.cmcontractor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.cmcontractor.R;


public class ContractorPortfolioFragment extends Fragment {

    private View view;

    private ImageView imgPortfolio;
    private LinearLayout lytPlanning, lytConstruction, lytFabrication;
    private TextView tvPortfolioName, tvPortfolioPlanName, tvPortfolioCostMonth, tvPortfolioLeastDuration, tvPortfolioInsuranceType, tvPortfolioPolicyNo, tvPortfolioConsultationFee;
    private Button btnPortfolioConsult;

    private String companyid, planname, email;
    private int duration, costmonth, consultationfee;

    private DatabaseReference profilereference, planreference, qualificationreference, servicereference;
    private FirebaseUser user;

    private ContractorPortfolioFragmentArgs args;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contractor_portfolio, container, false);

        // set
        user = FirebaseAuth.getInstance().getCurrentUser();

        profilereference = FirebaseDatabase.getInstance().getReference().child("Profile");
        planreference = FirebaseDatabase.getInstance().getReference().child("CompanyPlans");
        qualificationreference = FirebaseDatabase.getInstance().getReference().child("CompanyQualification");
        servicereference = FirebaseDatabase.getInstance().getReference().child("CompanyServices");

        args = ContractorPortfolioFragmentArgs.fromBundle(getArguments());

        companyid = args.getCompanyId();
        planname = args.getPlanName();

        // find view by id
        imgPortfolio = view.findViewById(R.id.portfolio_image);
        lytPlanning = view.findViewById(R.id.lyt_portfolio_planning);
        lytConstruction = view.findViewById(R.id.lyt_portfolio_construction);
        lytFabrication = view.findViewById(R.id.lyt_portfolio_fabrication);

        tvPortfolioName = view.findViewById(R.id.tv_portfolio_name);
        tvPortfolioPlanName = view.findViewById(R.id.tv_portfolio_planname);
        tvPortfolioCostMonth = view.findViewById(R.id.tv_portfolio_costmonth);
        tvPortfolioLeastDuration = view.findViewById(R.id.tv_portfolio_leastduration);
        tvPortfolioInsuranceType = view.findViewById(R.id.tv_portfolio_insurancetype);
        tvPortfolioPolicyNo = view.findViewById(R.id.tv_portfolio_policyno);
        tvPortfolioConsultationFee = view.findViewById(R.id.tv_portfolio_consultationfee);

        btnPortfolioConsult = view.findViewById(R.id.btn_portfolio_consult);


        // load data
        loadData();

        // listeners
        btnPortfolioConsult.setOnClickListener(consultListener);

        return view;
    }

    private View.OnClickListener consultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (user != null) {
                ContractorPortfolioFragmentDirections.NavigateToBottom action =
                        ContractorPortfolioFragmentDirections.navigateToBottom(companyid, planname);
                action.setLeastDuration(duration);
                action.setCostMonth(costmonth);
                action.setConsultationFee(consultationfee);

                NavHostFragment.findNavController(getParentFragment()).navigate(action);
            } else {
                ContractorPortfolioFragmentDirections.ActionContractorPortfolioFragmentToLoginFragment action
                        = ContractorPortfolioFragmentDirections.actionContractorPortfolioFragmentToLoginFragment(companyid, planname);
                NavHostFragment.findNavController(getParentFragment()).navigate(action);
            }
        }
    };


    private void loadData() {
        profilereference.orderByChild("companyid").equalTo(companyid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    String companyname = snapshot.child("companyname").getValue().toString();
                    String companyimage = snapshot.child("companyimage").getValue().toString();

                    tvPortfolioName.setText(companyname);
                    Glide.with(getContext()).load(companyimage).into(imgPortfolio);
                }
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

        planreference.orderByChild("companyid").equalTo(companyid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    duration = snapshot.child("durationest").getValue(Integer.class);
                    costmonth = snapshot.child("plancost").getValue(Integer.class);
                    consultationfee = snapshot.child("consultationfee").getValue(Integer.class);

                    tvPortfolioLeastDuration.setText(String.valueOf(duration));
                    tvPortfolioCostMonth.setText(String.valueOf(costmonth));
                    tvPortfolioConsultationFee.setText(String.valueOf(consultationfee));
                }
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

        qualificationreference.orderByChild("companyid").equalTo(companyid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    tvPortfolioInsuranceType.setText(snapshot.child("companyinsurancetype").getValue().toString());
                    tvPortfolioPolicyNo.setText(snapshot.child("companyinsurancepolicy").getValue().toString());
                }
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

        servicereference.orderByChild("companyid").equalTo(companyid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    boolean planning = Boolean.parseBoolean(snapshot.child("planning").getValue().toString());
                    boolean construction = Boolean.parseBoolean(snapshot.child("construction").getValue().toString());
                    boolean fabrication = Boolean.parseBoolean(snapshot.child("fabrication").getValue().toString());

                    if (planning) {
                        lytPlanning.setVisibility(View.VISIBLE);
                    }

                    if (construction) {
                        lytConstruction.setVisibility(View.VISIBLE);
                    }

                    if (fabrication) {
                        lytFabrication.setVisibility(View.VISIBLE);
                    }
                }
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

}