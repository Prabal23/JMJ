package com.jmj.app.journeymakerjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class EmployerFragment extends Fragment {
    TextInputLayout InputName, InputPass;
    EditText name, pass;
    TextView sign_up;
    String res, except_double;
    boolean loggedIn;
    int count;
    Typeface fontAwesomeFont;

    public EmployerFragment() {
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
        View myview =  inflater.inflate(R.layout.fragment_employer, container, false);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-solid-900.ttf");

        InputName = (TextInputLayout) myview.findViewById(R.id.input_name);
        InputPass = (TextInputLayout) myview.findViewById(R.id.input_pass);
        name = (EditText) myview.findViewById(R.id.sign_name);
        pass = (EditText) myview.findViewById(R.id.sign_pass);

        sign_up = (TextView) myview.findViewById(R.id.signup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EmployerReg.class);
                startActivity(intent);
            }
        });

        final TextView viewpass = (TextView) myview.findViewById(R.id.viewpass);
        viewpass.setTypeface(fontAwesomeFont);
        viewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count % 2 == 0) {
                    pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewpass.setText(R.string.eyenot);
                    //Toast.makeText(Login.this, "Even", Toast.LENGTH_SHORT).show();
                } else {
                    pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewpass.setText(R.string.eye);
                    //Toast.makeText(Login.this, "Odd", Toast.LENGTH_SHORT).show();
                }
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

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.IP + "direct/empladfadfadoyer_logafsasdfasdin/12342124?user=" + n + "&pass=" + p,
                            new Response.Listener<String>() {

                                @Override
                                public void onResponse(String response) {
                                    res = response.toString();
                                    //Toast.makeText(getApplicationContext(), res, Toast.LENGTH_LONG).show();
                                    except_double = res.replace("\"", "").replace("{", "").replace("}", "").replace(":", "").replace("result", "").replace("user_id", "").replace("success", "");
                                    //Toast.makeText(getActivity().getApplicationContext(), except_double, Toast.LENGTH_LONG).show();
                                    if (except_double.contains("failed") || except_double.contains("fails")) {
                                        Toast.makeText(getContext(), "Username or Password is incorrect", Toast.LENGTH_LONG).show();
                                    } else {
                                        //Creating a shared preference
                                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);

                                        //Creating editor to store values to shared preferences
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        //Adding values to editor
                                        editor.putBoolean(Utils.LOGGEDIN_SHARED_PREF_EMP, true);
                                        editor.putString(Utils.LOGIN_SHARED_PREF_EMP, except_double + " " + p + " " + n);
                                        //editor.putString(Config.NAME_SHARED_PREF, email);

                                        //Saving values to editor
                                        editor.commit();

                                        Intent intent = new Intent(getActivity(), EmployerHome.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    //You can handle error here if you want
                                }
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put(Utils.KEY_USER, n);
                            params.put(Utils.KEY_PASSWORD, p);

                            //returning parameter
                            return params;
                        }
                    };

                    //Adding the string request to the queue
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                    requestQueue.add(stringRequest);
                }
            }
        });

        LinearLayout forget = (LinearLayout)myview.findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(intent);
            }
        });

        return  myview;
    }

    @Override
    public void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Utils.LOGGEDIN_SHARED_PREF_EMP, false);

        //If we will get true
        if (loggedIn) {
            //We will start the Profile Activity
            Intent intent = new Intent(getContext(), EmployerHome.class);
            startActivity(intent);
            getActivity().finish();
        }
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
