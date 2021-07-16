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
import com.project.cmcontractor.fragments.OrdersFragmentDirections;
import com.project.cmcontractor.models.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderAdapter extends FirebaseRecyclerAdapter<Order, OrderAdapter.MyViewHolder> {

    private Context context;
    private String companyname;

    public OrderAdapter(@NonNull FirebaseRecyclerOptions<Order> options, Context context, String companyname) {
        super(options);
        this.context = context;
        this.companyname = companyname;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Order model) {

        TextView tvOrderNo = holder.tvOrderNo;
        TextView tvOrderDate = holder.tvOrderDate;
        TextView tvOrderPlanName = holder.tvOrderPlanName;
        Button btnOrderView = holder.btnOrderView;

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String formatdate = "";
        try {
            Date date = format.parse(model.getOrderdate());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formatter.format(date);
            formatdate = strDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvOrderNo.setText(model.getOrderno());
        tvOrderDate.setText(formatdate);
        tvOrderPlanName.setText(model.getPlanname() + " by " + companyname);

        btnOrderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String refKey = getRef(position).getKey();

                OrdersFragmentDirections.NavigateToViewOrder action =
                        OrdersFragmentDirections.navigateToViewOrder(refKey, companyname);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list_order, parent, false);
        OrderAdapter.MyViewHolder myViewHolder = new OrderAdapter.MyViewHolder(view);

        return myViewHolder;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvOrderNo, tvOrderDate, tvOrderPlanName;
        Button btnOrderView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOrderNo = itemView.findViewById(R.id.tv_order_no);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvOrderPlanName = itemView.findViewById(R.id.tv_order_planname);
            btnOrderView = itemView.findViewById(R.id.btn_order_view);
        }
    }
}
