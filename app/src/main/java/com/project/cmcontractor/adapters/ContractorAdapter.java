package com.project.cmcontractor.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.project.cmcontractor.R;
import com.project.cmcontractor.fragments.ContractorFragmentDirections;
import com.project.cmcontractor.models.CompanyProfile;

import org.w3c.dom.Text;

public class ContractorAdapter extends FirebaseRecyclerAdapter<CompanyProfile, ContractorAdapter.MyViewHolder> {

    private Context context;
    private String planname;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ContractorAdapter(@NonNull FirebaseRecyclerOptions<CompanyProfile> options, Context context, String planname) {
        super(options);
        this.context = context;
        this.planname = planname;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull CompanyProfile model) {

        TextView tvContractorName = holder.tvContractorName;
        TextView tvContractorType = holder.tvContractorType;
        TextView tvContractorLocation = holder.tvContractorLocation;
        TextView tvContractorRating = holder.tvContractorRating;
        TextView tvContractorRatingNo = holder.tvContractorRatingNo;
        Button btnContractorView = holder.btnView;

        tvContractorName.setText(model.getCompanyname());
        tvContractorType.setText(planname);
        tvContractorLocation.setText(model.getCompanylocation());
        tvContractorRating.setText(String.valueOf(model.getCompanyrating()));
        tvContractorRatingNo.setText(String.valueOf(model.getCompanyratingno()));

        btnContractorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String companyid = model.getCompanyid();

                ContractorFragmentDirections.NavigateToPortfolioFragment action =
                        ContractorFragmentDirections.navigateToPortfolioFragment(companyid, planname);

                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_contractor, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvContractorName, tvContractorType, tvContractorLocation, tvContractorRating, tvContractorRatingNo;
        Button btnView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvContractorName = itemView.findViewById(R.id.tv_contractor_name);
            tvContractorType = itemView.findViewById(R.id.tv_contractor_type);
            tvContractorLocation = itemView.findViewById(R.id.tv_contractor_location);
            tvContractorRating = itemView.findViewById(R.id.tv_contractor_rating);
            tvContractorRatingNo = itemView.findViewById(R.id.tv_contractor_ratingno);
            btnView = itemView.findViewById(R.id.btn_contractor_view);
        }
    }
}
