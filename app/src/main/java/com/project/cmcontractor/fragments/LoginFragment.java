package com.project.cmcontractor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.project.cmcontractor.R;
import com.project.cmcontractor.utils.BottomNavLocker;
import com.project.cmcontractor.utils.ValidationsClass;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class LoginFragment extends Fragment {

    private View view;

    private EditText txtLoginEmail;
    private ShowHidePasswordEditText txtLoginPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private TextView tvCreateAccount;

    private String companyid, planname;

    private LoginFragmentArgs args;

    private FirebaseAuth mAuth;

    private ValidationsClass validate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);

        // set
        ((BottomNavLocker) getActivity()).BottomNavLocked(true);

        validate = new ValidationsClass();
        mAuth = FirebaseAuth.getInstance();

        args = LoginFragmentArgs.fromBundle(getArguments());

        companyid = args.getCompanyid();
        planname = args.getPlanname();

        // find view by id
        txtLoginEmail = view.findViewById(R.id.txt_login_email);
        txtLoginPassword = view.findViewById(R.id.txt_login_password);
        btnLogin = view.findViewById(R.id.btn_login);
        progressBar = view.findViewById(R.id.progressbar_login);
        tvCreateAccount = view.findViewById(R.id.tv_createaccount);

        // set/load

        // on click
        btnLogin.setOnClickListener(loginListener);
        tvCreateAccount.setOnClickListener(createListener);

        return view;
    }


    private View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (login(txtLoginEmail, txtLoginPassword)) {
                String email = txtLoginEmail.getText().toString().trim();
                String password = txtLoginPassword.getText().toString().trim();

                loginUser(email, password);
            }
        }
    };


    private boolean login(EditText txtMail, ShowHidePasswordEditText txtPass) {
        boolean valid = false;

        String email = txtMail.getText().toString().trim();
        String password = txtPass.getText().toString().trim();

        validate.setEmail(email);
        validate.setPassword(password);

        switch (validate.validateEmail()) {
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
                switch (validate.validatePassword()) {
                    case 0:
                        txtMail.setError(null);
                        txtPass.setError("Text field is empty");
                        txtPass.setFocusable(true);
                        valid = false;
                        break;
                    case 1:
                        txtMail.setError(null);
                        txtPass.setError("Password should be 6 or more characters");
                        txtPass.setFocusable(true);
                        valid = false;
                        break;
                    case 2:
                        txtMail.setError(null);
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
        return valid;
    }

    private void loginUser(String email, String password) {
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            progressBar.setVisibility(View.GONE);

                            Toast.makeText(getContext(), "Sucessful login", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigateUp();
                        } else {
                            progressBar.setVisibility(View.GONE);
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getContext(), "Invalid User Credentials.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(getContext(), "Email does not exist", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    }
                });
    }

    private View.OnClickListener createListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LoginFragmentDirections.NavigateToCreate action = LoginFragmentDirections.navigateToCreate(companyid, planname);
            NavHostFragment.findNavController(getParentFragment()).navigate(action);
        }
    };

    public void updateUI(FirebaseUser account) {

        if (account != null) {
            Toast.makeText(getContext(), "U Signed In successfully", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getContext(), "U Didnt signed in", Toast.LENGTH_LONG).show();
        }

    }
}