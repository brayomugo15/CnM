package com.project.cmcontractor.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.freddygenicho.mpesa.stkpush.Mode;
import com.freddygenicho.mpesa.stkpush.api.response.STKPushResponse;
import com.freddygenicho.mpesa.stkpush.interfaces.STKListener;
import com.freddygenicho.mpesa.stkpush.interfaces.TokenListener;
import com.freddygenicho.mpesa.stkpush.model.Mpesa;
import com.freddygenicho.mpesa.stkpush.model.STKPush;
import com.freddygenicho.mpesa.stkpush.model.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.cmcontractor.R;
import com.project.cmcontractor.models.Order;
import com.project.cmcontractor.models.Payment;
import com.project.cmcontractor.utils.Config;
import com.project.cmcontractor.utils.Transaction;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private View view;

    private BottomSheetFragmentArgs args;

    private String companyid, planname, companyid_planname, email, orderno, companyname, item;
    private int leastduration, costmonth, consultationfee;

    private int maxid = 0;
    private int monthitem, total, phoneno;

    private TextView tvPlanName, tvPlanCost;
    private EditText txtLocation;
    private Spinner spinnerDuration;
    private Button btnConsult;

    private DatabaseReference planreference, companyreference, orderreference, paymentreference, clientreference;

    private FirebaseUser user;

    private Mpesa mpesa;

    private SweetAlertDialog sweetAlertDialog;

    public BottomSheetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        // set
        args = BottomSheetFragmentArgs.fromBundle(getArguments());

        companyid = args.getCompanyid();
        planname = args.getPlanname();
        leastduration = args.getLeastDuration();
        costmonth = args.getCostMonth();
        consultationfee = args.getConsultationFee();

        companyid_planname = companyid + "_" + planname;

        user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        companyreference = FirebaseDatabase.getInstance().getReference().child("Profile");
        planreference = FirebaseDatabase.getInstance().getReference().child("CompanyPlans");
        orderreference = FirebaseDatabase.getInstance().getReference().child("Orders");
        clientreference = FirebaseDatabase.getInstance().getReference().child("Client");
        paymentreference = FirebaseDatabase.getInstance().getReference().child("Payments");

        orderreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                maxid = (int) (snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // find view by id
        tvPlanName = view.findViewById(R.id.tv_bottom_planname);
        tvPlanCost = view.findViewById(R.id.tv_bottom_cost);
        txtLocation = view.findViewById(R.id.txt_bottom_location);
        spinnerDuration = view.findViewById(R.id.spinner_bottom_duration);

        btnConsult = view.findViewById(R.id.btn_bottom_consult);

        // load data
        tvPlanName.setText(planname);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.bottom_months, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(adapter);

        loadClientData();
        loadData();

        mpesa = new Mpesa(Config.CONSUMER_KEY, Config.CONSUMER_SECRET, Mode.SANDBOX);

        sweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Connecting to Safaricom");
        sweetAlertDialog.setContentText("Please wait...");
        sweetAlertDialog.setCancelable(false);

        // on Click
        spinnerDuration.setOnItemSelectedListener(spinnerListener);
        btnConsult.setOnClickListener(consultListener);

        return view;
    }

    private void loadClientData() {
        clientreference.orderByChild("email").equalTo(email).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    phoneno = snapshot.child("phoneno").getValue(Integer.class);
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

    private void loadData() {

        companyreference.orderByChild("companyid").equalTo(companyid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot != null) {
                    companyname = snapshot.child("companyname").getValue().toString();
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
//                    Calculate the cost from database
                    int total = costmonth * leastduration;

                    tvPlanCost.setText(String.valueOf(0));
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


    private void saveData() {

        if (item.equals("-- Select Contract Months --")) {
            Toast.makeText(getContext(), "Select months", Toast.LENGTH_SHORT).show();
        } else {
            if (monthitem < leastduration) {
                Toast.makeText(getContext(), "Duration is less than our duration", Toast.LENGTH_SHORT).show();
            } else {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Do you wish to proceed");
                builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
                        String strdate = formatter.format(date);

                        String orderno = "CONTRACT-" + strdate + "-" + String.valueOf(maxid + 100);

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        String orderdate = timestamp.toString();
                        String location = txtLocation.getText().toString().trim();

                        Order order = new Order(orderno, companyid, planname, email, orderdate, orderno + "_0", location, monthitem, total);

                        orderreference.push().setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Payment payment = new Payment(orderno, orderdate, consultationfee);

                                    paymentreference.push().setValue(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();

                                                getDialog().dismiss();

                                                // Mpesa consultation fee
                                            } else {
                                                Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        }

    }


    private View.OnClickListener consultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final SweetAlertDialog dialog = new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE);
            dialog.setTitle("MPESA Payment");
            dialog.setContentText("Pay Consultation Fee : " + String.valueOf(consultationfee));
            dialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog alertDialog) {
                    dialog.dismissWithAnimation();

                    try {
                        sweetAlertDialog.show();
                        mpesa.getToken(tokenListener);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }).setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    dialog.dismissWithAnimation();
                }
            });
            dialog.show();
        }
    };

    private AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

            item = parent.getItemAtPosition(position).toString();

            if (!item.equals("-- Select Contract Months --")) {
                monthitem = Integer.parseInt(item);
                total = monthitem * costmonth;
                tvPlanCost.setText(String.valueOf(total));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    private TokenListener tokenListener = new TokenListener() {
        @Override
        public void onToken(final Token token) {
            final STKPush stkPush = new STKPush();
            stkPush.setBusinessShortCode(Config.BUSINESS_SHORT_CODE);
            stkPush.setPassword(STKPush.getPassword(Config.BUSINESS_SHORT_CODE, Config.PASSKEY, STKPush.getTimestamp()));
            stkPush.setTimestamp(STKPush.getTimestamp());
            stkPush.setTransactionType(Transaction.CUSTOMER_PAY_BILL_ONLINE);
            stkPush.setAmount(String.valueOf(total));
            stkPush.setPartyA(STKPush.sanitizePhoneNumber("254" + String.valueOf(phoneno)));
            stkPush.setPartyB(Config.PARTYB);
            stkPush.setPhoneNumber(STKPush.sanitizePhoneNumber("254" + String.valueOf(phoneno)));
            stkPush.setCallBackURL(Config.CALLBACKURL);
            stkPush.setAccountReference(companyname);
            stkPush.setTransactionDesc("testing");

            mpesa.startStkPush(token, stkPush, new STKListener() {
                @Override
                public void onResponse(STKPushResponse stkPushResponse) {
                    Log.e("Checkout", "onResponse: " + stkPushResponse.toJson(stkPushResponse));
                    String message = "Please enter your pin to complete transaction";
                    sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("Transaction started");
                    sweetAlertDialog.setContentText(message);
                    saveData();
                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("Checkout", "stk onError: " + throwable.getMessage());
                    sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("Error");
                    sweetAlertDialog.setContentText(throwable.getMessage());
                }
            });
        }

        @Override
        public void OnError(Throwable throwable) {

        }
    };

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle
            savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
