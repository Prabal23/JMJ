package com.jmj.app.journeymakerjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ForgetPassword extends AppCompatActivity {
    Typeface fontAwesomeFont;
    TextInputLayout iOldPass, iNewPass, iConPass;
    EditText tOldPass, tNewPass, tConPass;
    String id, pass;
    TextView error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        TextView apply = (TextView) findViewById(R.id.apply);
        TextView oldpasspic = (TextView) findViewById(R.id.oldpasspic);
        TextView passpic = (TextView) findViewById(R.id.passpic);
        TextView conpasspic = (TextView) findViewById(R.id.conpasspic);

        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        apply.setTypeface(fontAwesomeFont);
        oldpasspic.setTypeface(fontAwesomeFont);
        passpic.setTypeface(fontAwesomeFont);
        conpasspic.setTypeface(fontAwesomeFont);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProfileOptions.class);
                startActivity(intent);
            }
        });

        iOldPass = (TextInputLayout) findViewById(R.id.input_old);
        iNewPass = (TextInputLayout) findViewById(R.id.input_pass);
        iConPass = (TextInputLayout) findViewById(R.id.input_conpass);
        tOldPass = (EditText) findViewById(R.id.sign_old);
        tNewPass = (EditText) findViewById(R.id.sign_pass);
        tConPass = (EditText) findViewById(R.id.sign_conpass);

        error = (TextView) findViewById(R.id.error);

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String oldpass = tOldPass.getText().toString();
                final String newpass = tNewPass.getText().toString();
                String conpass = tConPass.getText().toString();

                if (oldpass.equals("")) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Username shouldn't be blank");
                }
                else if (newpass.equals("")) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("New password shouldn't be blank");
                }
                else if (conpass.equals("")) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("Confirm password shouldn't be blank");
                }
                else if (!newpass.equals(conpass)) {
                    error.setVisibility(View.VISIBLE);
                    error.setText("New password & Confirm password doesn't match");
                } else {
                    error.setVisibility(View.GONE);
                    String URL = Utils.IP + "direct/fodfrsdfgesdft_pasdfassasdfwroasdfasdfd/12342124?username=" + oldpass + "&new_pass=" + newpass;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    if(ServerResponse.contains("User Name Not Matched")){
                                        error.setVisibility(View.VISIBLE);
                                        error.setText("Username doesn't match");
                                    }else{
                                        Toast.makeText(ForgetPassword.this, "Success", Toast.LENGTH_LONG).show();
                                        SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                        //Getting editor
                                        SharedPreferences.Editor editor = preferences.edit();

                                        //Puting the value false for loggedin
                                        editor.putBoolean(Utils.LOGGEDIN_SHARED_PREF, false);

                                        //Putting blank value to email
                                        editor.putString(Utils.LOGIN_SHARED_PREF, "");

                                        //Saving the sharedpreferences
                                        editor.commit();
                                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(ForgetPassword.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {

                            // Creating Map String Params.
                            Map<String, String> params = new HashMap<String, String>();

                            // Adding All values to Params.
                            params.put("username", oldpass);
                            params.put("new_pass", newpass);
                            return params;
                        }
                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(ForgetPassword.this);

                    // Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
}
