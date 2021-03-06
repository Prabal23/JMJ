package com.jmj.app.journeymakerjobs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SavedJobRemarks extends AppCompatActivity {
    Typeface fontAwesomeFont;
    String beton, rem, name, SetServerString, jai, ji, res, id, pic, pass;
    TextInputLayout iSalary, iRemarks;
    EditText tSalary, tRemarks;
    TextView text_press, post, status;
    private HttpClient Client;
    HttpGet httpget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rec_job_rem);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        TextView apply = (TextView) findViewById(R.id.apply);
        TextView salary = (TextView) findViewById(R.id.salarypic);
        TextView remarks = (TextView) findViewById(R.id.rempic);
        text_press = (TextView) findViewById(R.id.text_press);
        post = (TextView) findViewById(R.id.text);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
        salary.setTypeface(fontAwesomeFont);
        remarks.setTypeface(fontAwesomeFont);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProfileOptions.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), JobSeekerHome.class);
                startActivity(intent);
            }
        });

        status = (TextView) findViewById(R.id.status);

        beton = getIntent().getStringExtra("salary");
        rem = getIntent().getStringExtra("remarks");
        name = getIntent().getStringExtra("name");
        jai = getIntent().getStringExtra("jai");
        ji = getIntent().getStringExtra("ji");
        final Bitmap myBitmap = (Bitmap) getIntent().getParcelableExtra("pic");
        post.setText(name);
        ImageView img = (ImageView) findViewById(R.id.pic);
        img.setImageBitmap(myBitmap);

        iSalary = (TextInputLayout) findViewById(R.id.input_salary);
        iRemarks = (TextInputLayout) findViewById(R.id.input_remarks);
        tSalary = (EditText) findViewById(R.id.sign_salary);
        tRemarks = (EditText) findViewById(R.id.sign_remarks);
        tRemarks.setInputType(InputType.TYPE_CLASS_TEXT);
        tSalary.setText(beton);
        tRemarks.setText(rem);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        pass = result[2];

        if (!beton.equals("")) {
            text_press.setText("Edit");
            apply.setText(getResources().getString(R.string.edit));
            tRemarks.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!beton.equals("")) {
                    String sal = tSalary.getText().toString();
                    if (sal.contains(" ")) {
                        sal = sal.replace(" ", "%20");
                    }
                    if (sal.equals("") || rem.equals("")) {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Salary must not be blank");
                        //Toast.makeText(SavedJobRemarks.this, "Salary and Password must not be blank", Toast.LENGTH_LONG).show();
                    } else {
                        Client = new DefaultHttpClient();

                        String URL = Utils.IP + "direct/appdfasasdfdlied_joasdfb_updasdfaateasdfasdf/64341424?job_apply_id=" + jai + "&expected_salary=" + sal /*+ "&remarks=" + rem*/ + "&job_id=" + ji;
                        //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
                        SetServerString = "";

                        httpget = new HttpGet(URL);
                        HttpResponse response = null;
                        try {
                            response = Client.execute(httpget);
                            HttpEntity httpEntity = response.getEntity();
                            String res = EntityUtils.toString(httpEntity);
                            //Toast.makeText(this, res, Toast.LENGTH_LONG).show();
                            if (res.contains("Success")) {
                                insert();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    String sal = tSalary.getText().toString();
                    if (sal.contains(" ")) {
                        sal = sal.replace(" ", "%20");
                    }
                    if (sal.equals("")) {
                        status.setVisibility(View.VISIBLE);
                        status.setText("Salary must not be blank");
                        //Toast.makeText(SavedJobRemarks.this, "Salary and Remarks must not be blank", Toast.LENGTH_LONG).show();
                    } else {
                        //Client = new DefaultHttpClient();

                        String URL = Utils.IP + "direct/asdfjoasfasfbasfasfsd_asfappasdfasdflydsgfadsfaasdfasdf/64341424?user_id=" + id + "&expected_salary=" + sal /*+ "&remarks=" + rem*/ + "&job_id=" + ji;
                        final String finalSal = sal;
                        final String finalRem = rem;
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String ServerResponse) {
                                        status.setVisibility(View.VISIBLE);
                                        status.setText("Success");
                                        //status.setTextColor(Color.parseColor("#5dable"));
                                        //Toast.makeText(SavedJobRemarks.this, "Success", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getBaseContext(), RecentJobsApply.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Toast.makeText(SavedJobRemarks.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() {

                                // Creating Map String Params.
                                Map<String, String> params = new HashMap<String, String>();

                                // Adding All values to Params.
                                params.put("user_id", id);
                                params.put("expected_salary", finalSal);
                                params.put("job_id", ji);

                                return params;
                            }

                        };

                        // Creating RequestQueue.
                        RequestQueue requestQueue = Volley.newRequestQueue(SavedJobRemarks.this);

                        // Adding the StringRequest object into requestQueue.
                        requestQueue.add(stringRequest);
                    }
                }
            }
        });
    }

    public void insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(SavedJobRemarks.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), RecentJobsApply.class);
            startActivity(intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insert1() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(SavedJobRemarks.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), RecentJobsApply.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
