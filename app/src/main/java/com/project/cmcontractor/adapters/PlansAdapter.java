package com.project.cmcontractor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.project.cmcontractor.R;
import com.project.cmcontractor.fragments.HomeFragmentDirections;
import com.project.cmcontractor.models.Plans;

import java.util.ArrayList;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.MyViewHolder> {

    private ArrayList<Plans> dataSet;
    private Context context;

    public PlansAdapter(ArrayList<Plans> dataSet, Context context) {
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plan, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView tvPlanName = holder.tvPlanName;
        TextView tvPlanType = holder.tvPlanType;
        ImageView imgPlan = holder.imgPlan;

        tvPlanName.setText(dataSet.get(position).getPlan_name());
        tvPlanType.setText(dataSet.get(position).getPlan_type());
        imgPlan.setImageResource(dataSet.get(position).getPlan_image());

        imgPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.NavigateToContractor action = HomeFragmentDirections.navigateToContractor(dataSet.get(position).getPlan_name());
                Navigation.findNavController(view).navigate(action);
            }
        });

        tvPlanName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragmentDirections.NavigateToContractor action = HomeFragmentDirections.navigateToContractor(dataSet.get(position).getPlan_name());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlanName, tvPlanType;
        ImageView imgPlan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPlanName = itemView.findViewById(R.id.tv_planname);
            tvPlanType = itemView.findViewById(R.id.tv_plantype);
            imgPlan = itemView.findViewById(R.id.image_plan);
        }
    }
}
