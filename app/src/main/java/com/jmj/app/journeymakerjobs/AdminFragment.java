package com.jmj.app.journeymakerjobs;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class AdminFragment extends Fragment {
    TextInputLayout InputName, InputPass;
    EditText name, pass;
    TextView sign_up;
    String res, except_double;
    boolean loggedIn;

    public AdminFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_admin, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        InputName = (TextInputLayout) myview.findViewById(R.id.input_name);
        InputPass = (TextInputLayout) myview.findViewById(R.id.input_pass);
        name = (EditText) myview.findViewById(R.id.sign_name);
        pass = (EditText) myview.findViewById(R.id.sign_pass);

        sign_up = (TextView) myview.findViewById(R.id.signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button button = (Button) myview.findViewById(R.id.submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateName()) {
                    return;
                }
                if (!validatePass()) {
                    return;
                } else {
                    final String n = name.getText().toString();
                    final String p = pass.getText().toString();
                    try {
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {

                    }

                    Intent intent = new Intent(getActivity(), AdminHome.class);
                    startActivity(intent);
                }
            }
        });

        LinearLayout forget = (LinearLayout) myview.findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        return myview;
    }

    private boolean validatePass() {
        if (pass.getText().toString().trim().isEmpty()) {
            InputPass.setError("Password field is blank");
            requestFocus(pass);
            return false;
        } else {
            InputPass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            InputName.setError("Username field is blank");
            requestFocus(name);
            return false;
        } else {
            InputName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
