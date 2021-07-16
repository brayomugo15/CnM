package com.project.cmcontractor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.cmcontractor.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ViewOrderFragment extends Fragment {

    private View view;

    private TextView tvOrderNo, tvPlanName, tvOrderDate, tvOrderStatus, tvContractorName, tvDuration, tvTotalCost, tvCostPerMonth;
    private String refKey, orderno, planname, orderdate, orderno_status, contractorname;
    private int duration, cost, costpermonth;

    private DatabaseReference reference, profilereference;

    private ViewOrderFragmentArgs args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_order, container, false);

        // set
        reference = FirebaseDatabase.getInstance().getReference().child("Orders");

        args = ViewOrderFragmentArgs.fromBundle(getArguments());

        refKey = args.getRefKey();
        contractorname = args.getCompanyName();

        // find view by id
        tvOrderNo = view.findViewById(R.id.tv_vieworder_orderno);
        tvPlanName = view.findViewById(R.id.tv_vieworder_planname);
        tvOrderDate = view.findViewById(R.id.tv_vieworder_orderdate);
        tvOrderStatus = view.findViewById(R.id.tv_vieworder_status);
        tvContractorName = view.findViewById(R.id.tv_vieworder_contractorname);
        tvDuration = view.findViewById(R.id.tv_vieworder_duration);
        tvTotalCost = view.findViewById(R.id.tv_vieworder_totalcost);
        tvCostPerMonth = view.findViewById(R.id.tv_vieworder_costpermonth);

        // load / set
        loadData();

        // listeners


        return view;
    }

    private void loadData() {
        reference.orderByKey().equalTo(refKey).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    orderno = snapshot.child("orderno").getValue().toString();
                    orderdate = snapshot.child("orderdate").getValue().toString();
                    orderno_status = snapshot.child("orderno_status").getValue().toString();
                    planname = snapshot.child("planname").getValue().toString();
                    duration = Integer.parseInt(snapshot.child("duration").getValue().toString());
                    cost = Integer.parseInt(snapshot.child("cost").getValue().toString());

                    String [] split = orderno_status.split("_");
                    String status = split[1].toString();

                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    String formatdate = "";
                    try {
                        Date date = format.parse(orderdate);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = formatter.format(date);
                        formatdate = strDate;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    costpermonth = cost / duration;

                    tvOrderNo.setText(orderno);

                    if (status.equals("0")) {
                        tvOrderStatus.setText("New");
                        tvOrderStatus.setTextColor(getContext().getResources().getColor(R.color.darK_gray));
                    } else if (status.equals("1")){
                        tvOrderStatus.setText("Approved");
                        tvOrderStatus.setTextColor(getContext().getResources().getColor(R.color.dark_green));
                    } else if (status.equals("2")){
                        tvOrderStatus.setText("Rejected");
                        tvOrderStatus.setTextColor(getContext().getResources().getColor(R.color.red));
                    }

                    tvOrderDate.setText(formatdate);
                    tvContractorName.setText(contractorname);
                    tvPlanName.setText(planname);
                    tvDuration.setText(String.valueOf(duration) + " months");
                    tvTotalCost.setText("Ksh. " + String.valueOf(cost));
                    tvCostPerMonth.setText("Ksh. " + String.valueOf(costpermonth));
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