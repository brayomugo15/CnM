package com.project.cmcontractor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.cmcontractor.R;
import com.project.cmcontractor.models.Client;
import com.project.cmcontractor.utils.BottomNavLocker;
import com.project.cmcontractor.utils.ValidationsClass;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;


public class CreateAccountFragment extends Fragment {

    private View view;

    private EditText txtCreateName, txtCreateEmail, txtPhoneno;
    private ShowHidePasswordEditText txtCreatePassword;
    private Button btnCreate;
    private TextView tvLogin;
    private ProgressBar progressBar;

    private String companyid, planname;

    private CreateAccountFragmentArgs args;

    private FirebaseAuth mAuth;

    private ValidationsClass validate;

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_account, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        validate = new ValidationsClass();
        mAuth = FirebaseAuth.getInstance();

        reference = FirebaseDatabase.getInstance().getReference().child("Client");

        args = CreateAccountFragmentArgs.fromBundle(getArguments());

        companyid = args.getCompanyid();
        planname = args.getPlanname();

        // find view by id
        txtCreateName = view.findViewById(R.id.txt_create_name);
        txtCreateEmail = view.findViewById(R.id.txt_create_email);
        txtCreatePassword = view.findViewById(R.id.txt_create_password);
        txtPhoneno = view.findViewById(R.id.txt_create_phone);
        btnCreate = view.findViewById(R.id.btn_createaccount);
        tvLogin = view.findViewById(R.id.tv_login);
        progressBar = view.findViewById(R.id.progressbar_create);

        // set / load


        // on click
        btnCreate.setOnClickListener(createListener);
        tvLogin.setOnClickListener(loginListener);

        return view;
    }

    private View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String txtName = txtCreateName.getText().toString().trim();
            String txtPhone = txtPhoneno.getText().toString().trim();

            if (!txtName.isEmpty()) {

                if (create(txtCreateEmail, txtPhoneno, txtCreatePassword)) {
                    String email = txtCreateEmail.getText().toString().trim();
                    String name = txtCreateName.getText().toString().trim();
                    int phone = Integer.parseInt(txtPhoneno.getText().toString().trim());
                    String password = txtCreatePassword.getText().toString().trim();

                    Client client = new Client(name, email, phone);

                    reference.push().setValue(client).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                register(email, password);
                            } else {
                                Toast.makeText(getContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            } else {
                txtCreateName.setError("Text field is empty");
                txtCreateName.setFocusable(true);
            }
        }
    };

    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Navigation.findNavController(view).navigateUp();
        }
    };


    private boolean create(EditText txtMail, EditText txtNo, ShowHidePasswordEditText txtPass) {

        boolean valid = false;

        String email = txtMail.getText().toString().trim();
        String phoneno = txtNo.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        validate.setEmail(email);
        validate.setPhoneNo(phoneno);
        validate.setPassword(password);

        switch (validate.validateEmail()){
            case 0:
                txtMail.setError("Text field is empty");
                txtMail.setFocusable(true);
                valid = false;
                break;
            case 1:
                txtMail.setError("Invalid Email");
                txtMail.setFocusable(true);
                valid = false;
                break;
            case 2:
                switch (validate.validatePhoneNo()) {
                    case 0:
                        txtMail.setError(null);
                        txtNo.setError("Text Field is empty");
                        txtNo.setFocusable(true);
                        valid = false;
                        break;
                    case 1:
                        txtMail.setError(null);
                        txtNo.setError("Length should be 10");
                        txtNo.setFocusable(true);
                        valid = false;
                        break;
                    case 2:
                        txtMail.setError(null);
                        txtNo.setError("Should contain numbers only");
                        txtNo.setFocusable(true);
                        valid = false;
                        break;
                    case 3:
                        switch (validate.validatePassword()){
                            case 0:
                                txtMail.setError(null);
                                txtPass.setError(null);
                                txtPass.setError("Text field is empty");
                                txtPass.setFocusable(true);
                                valid = false;
                                break;
                            case 1:
                                txtMail.setError(null);
                                txtPass.setError(null);
                                txtPass.setError("Password should be 6 or more characters");
                                txtPass.setFocusable(true);
                                Toast.makeText(getContext(), "Feedback = " + validate.validatePassword(), Toast.LENGTH_LONG).show();
                                valid = false;
                                break;
                            case 2:
                                txtMail.setError(null);
                                txtPass.setError(null);
                                txtPass.setError("At least one uppercase letter, one number");
                                txtPass.setFocusable(true);
                                valid = false;
                                break;
                            case 3:
                                valid = true;
                                txtMail.setError(null);
                                txtPass.setError(null);
                                break;
                        }
                        break;
                }
                break;
        }

        return valid;
    }


    private void register(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getContext(), "Sucessful Registration", Toast.LENGTH_SHORT).show();

                            CreateAccountFragmentDirections.NavigateToPortfolioFromCreate action = CreateAccountFragmentDirections.navigateToPortfolioFromCreate(companyid, planname);
                            NavHostFragment.findNavController(getParentFragment()).navigate(action);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                Toast.makeText(getContext(), "Weak Password", Toast.LENGTH_SHORT).show();
                            }

                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getContext(), "User Already exists", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}