package com.jmj.app.journeymakerjobs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class JobSeekerHome extends AppCompatActivity {
    Typeface fontAwesomeFont;
    private ProgressDialog pDialog;
    int downloadedSize = 0, totalsize;
    float per = 0;
    String res, id = "", pass = "", username = "", versionName, versionNameURL, error;
    TextView title, total, name, total_percent;
    ArrayList<ProductCV> arrayListCV;
    ArrayList<ProductExpertiseTraining> arrayList, arrayList2, arrayList3;
    ArrayList<ProductEducation> arrayList1;
    ArrayList<ProductJobType> arrayList4, arrayList5;
    ArrayList<ProductNotify> arrayList6;
    ArrayList<ProductExtracurricular> arrayList7;
    CircleImageView pro_pic;
    ProgressBar b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, total_prog, progressBar;
    TextView pp1, pp2, pp3, pp4, pp5, pp6, pp7, pp8, pp9, pp10;
    int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0, c8 = 0, c9 = 0, c10 = 0, total_per = 0;
    HttpResponse response = null;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobseeker_home);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        int versionCode = BuildConfig.VERSION_CODE;
        versionName = BuildConfig.VERSION_NAME;

        HttpClient Client = new DefaultHttpClient();

        String URL = "http://www.journeymakerjobs.com/direct/sdfapp_isdfnfosd/12345";
        //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
        String SetServerString = "";

        HttpGet httpget = new HttpGet(URL);
        //HttpResponse response = null;
        try {
            response = Client.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String res = EntityUtils.toString(httpEntity);
            //Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            try {
                JSONObject jsonObject = new JSONObject(res);
                JSONArray jsonArray = jsonObject.getJSONArray("app_info");
                //Toast.makeText(this, jsonArray + "", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < jsonArray.length(); i++) {
                    int c = jsonArray.length();
                    //Toast.makeText(this, c + "", Toast.LENGTH_SHORT).show();
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    versionNameURL = productObject.getString("version_name");
                    error = productObject.getString("server_activity");
                    //Toast.makeText(this, versionNameURL, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!versionName.equals(versionNameURL)) {
            Update alert = new Update();
            alert.showDialog(JobSeekerHome.this, "");
        }

        if (error.equals("0")) {
            ErrorMsg alert = new ErrorMsg();
            alert.showDialog(JobSeekerHome.this, "");
        }

        Calendar calendar = Calendar.getInstance();
        int yearInt = calendar.get(Calendar.YEAR);
        TextView yearText = (TextView) findViewById(R.id.year);
        yearText.setText(" " + yearInt);

        TextView p1 = (TextView) findViewById(R.id.p1);
        TextView p2 = (TextView) findViewById(R.id.p2);
        TextView p3 = (TextView) findViewById(R.id.p3);
        TextView p4 = (TextView) findViewById(R.id.p4);
        TextView p5 = (TextView) findViewById(R.id.p5);
        TextView p6 = (TextView) findViewById(R.id.p6);
        TextView web = (TextView) findViewById(R.id.web);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView notify = (TextView) findViewById(R.id.notify);
        p1.setTypeface(fontAwesomeFont);
        p2.setTypeface(fontAwesomeFont);
        p3.setTypeface(fontAwesomeFont);
        p4.setTypeface(fontAwesomeFont);
        p5.setTypeface(fontAwesomeFont);
        p6.setTypeface(fontAwesomeFont);
        web.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        notify.setTypeface(fontAwesomeFont);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout alert = new Logout();
                alert.showDialog(JobSeekerHome.this, "");
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Webpage.class);
                intent.putExtra("page", "1");
                startActivity(intent);
            }
        });

        pro_pic = (CircleImageView) findViewById(R.id.pic);
        total_prog = (ProgressBar) findViewById(R.id.b);
        total_prog.setScaleY(1f);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        total_percent = (TextView) findViewById(R.id.total_per);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        pass = result[1];
        //Toast.makeText(this, pass, Toast.LENGTH_SHORT).show();
        username = result[2];
        final TextView total = (TextView) findViewById(R.id.total);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.IP + "direct/sdfgs45dfind_not45is45dfg34545ficatd4535fgi54osdgf3453n/12342124?user_id=" + id,
                        new Response.Listener<String>() {

                            @Override
                            public void onResponse(String response) {
                                res = response.toString();
                                res = res.replace("notification_count", "").replace("{", "").replace("}", "").replace(":", "").replace("\"", "");
                                if (res.equals("0")) {
                                    total.setText(res);
                                    //total.setVisibility(View.GONE);
                                    //Toast.makeText(JobSeekerHome.this, res, Toast.LENGTH_LONG).show();
                                }
                                total.setText(res);
                                //Toast.makeText(JobSeekerHome.this, res, Toast.LENGTH_LONG).show();
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
                        //returning parameter
                        return params;
                    }
                };

                //Adding the string request to the queue
                RequestQueue requestQueue = Volley.newRequestQueue(JobSeekerHome.this);
                requestQueue.add(stringRequest);
            }
        }, 3000);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        LinearLayout r1 = (LinearLayout) findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RecentJobs.class);
                intent.putExtra("refid", "0");
                startActivity(intent);
            }
        });

        LinearLayout r2 = (LinearLayout) findViewById(R.id.r2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RecentJobsApply.class);
                startActivity(intent);
            }
        });

        final LinearLayout r3 = (LinearLayout) findViewById(R.id.r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), SavedJobs.class);
                startActivity(intent);
            }
        });

        RelativeLayout notify_tab = (RelativeLayout) findViewById(R.id.notify_tab);
        notify_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RecentJobsNotify.class);
                intent.putExtra("id", id);
                intent.putExtra("msg", "0");
                startActivity(intent);
            }
        });

        LinearLayout r4 = (LinearLayout) findViewById(R.id.r4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RecentJobsNotify.class);
                intent.putExtra("id", id);
                intent.putExtra("msg", "0");
                startActivity(intent);
            }
        });

        /*LinearLayout pro_id = (LinearLayout) findViewById(R.id.pro_id);
        pro_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProfileOptions.class);
                startActivity(intent);
            }
        });*/

       /* LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProfileOptions.class);
                startActivity(intent);
            }
        });*/

        LinearLayout r6 = (LinearLayout) findViewById(R.id.r6);
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MyChoiceList.class);
                startActivity(intent);
            }
        });

        LinearLayout view = (LinearLayout) findViewById(R.id.view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CV.class);
                intent.putExtra("id", id);
                intent.putExtra("page", "0");
                startActivity(intent);
            }
        });

        LinearLayout settings = (LinearLayout) findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProfileOptions.class);
                startActivity(intent);
            }
        });

        title = (TextView) findViewById(R.id.title);

        b1 = (ProgressBar) findViewById(R.id.b1);
        b1.setScaleY(0.7f);
        b2 = (ProgressBar) findViewById(R.id.b2);
        b2.setScaleY(0.7f);
        b3 = (ProgressBar) findViewById(R.id.b3);
        b3.setScaleY(0.7f);
        b4 = (ProgressBar) findViewById(R.id.b4);
        b4.setScaleY(0.7f);
        b5 = (ProgressBar) findViewById(R.id.b5);
        b5.setScaleY(0.7f);
        b6 = (ProgressBar) findViewById(R.id.b6);
        b6.setScaleY(0.7f);
        b7 = (ProgressBar) findViewById(R.id.b7);
        b7.setScaleY(0.7f);
        b8 = (ProgressBar) findViewById(R.id.b8);
        b8.setScaleY(0.7f);
        b9 = (ProgressBar) findViewById(R.id.b9);
        b9.setScaleY(0.7f);
        b10 = (ProgressBar) findViewById(R.id.b10);
        b10.setScaleY(0.7f);

        pp1 = (TextView) findViewById(R.id.pp1);
        pp2 = (TextView) findViewById(R.id.pp2);
        pp3 = (TextView) findViewById(R.id.pp3);
        pp4 = (TextView) findViewById(R.id.pp4);
        pp5 = (TextView) findViewById(R.id.pp5);
        pp6 = (TextView) findViewById(R.id.pp6);
        pp7 = (TextView) findViewById(R.id.pp7);
        pp8 = (TextView) findViewById(R.id.pp8);
        pp9 = (TextView) findViewById(R.id.pp9);
        pp10 = (TextView) findViewById(R.id.pp10);

        name = (TextView) findViewById(R.id.name);

        arrayListCV = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCV().execute(Utils.IP + "direct/asdfbasiasdfac_deasdfastailsdfasdfasdf/12342124?user_id=" + id);
            }
        });
        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON().execute(Utils.IP + "direct/emasdploymasdent_detsdaasdilasds/12342124?user_id=" + id);
            }
        });
        arrayList1 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON1().execute(Utils.IP + "direct/edusdfgcatifgodnsdfal_detasdfdfggilsASasdD/12342124?user_id=" + id);
            }
        });
        arrayList2 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON2().execute(Utils.IP + "direct/trasfasdfsdfining_desdftasdfailsdfs/12342124?user_id=" + id);
            }
        });
        arrayList4 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON4().execute(Utils.IP + "direct/achiesdfveasdfment_detaisadfls/12342124?user_id=" + id);
            }
        });
        arrayList5 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON5().execute(Utils.IP + "direct/reasdffesdfrensdfce_dsdfetsdfasdfaisdfls/12342124?user_id=" + id);
            }
        });
        arrayList6 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCheck().execute(Utils.IP + "direct/asdfdesiasdasdfred_asdfjasdfasdfoaasdfsdfb_asdfdasdfetaisdfasdls/12342124?user_id=" + id);
            }
        });
        arrayList7 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON6().execute(Utils.IP + "direct/extracurasdfricular_detaasdfiasdfls_allasdfas/12342124?user_id=" + id);
            }
        });
    }

    public class Update {

        public void showDialog(AppCompatActivity activity, String title) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.update_dialog);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView ok = (TextView) dialog.findViewById(R.id.ok);
            TextView later = (TextView) dialog.findViewById(R.id.later);
            TextView ver_name = (TextView) dialog.findViewById(R.id.ver_name);
            ver_name.setText(versionNameURL);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                }
            });

            later.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    public class ErrorMsg {

        public void showDialog(AppCompatActivity activity, String title) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.update_dialog);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView ok = (TextView) dialog.findViewById(R.id.ok);
            TextView later = (TextView) dialog.findViewById(R.id.later);
            TextView ver_name = (TextView) dialog.findViewById(R.id.ver_name);
            TextView msg = (TextView) dialog.findViewById(R.id.msg);
            TextView main_title = (TextView) dialog.findViewById(R.id.title);
            TextView ver_title = (TextView) dialog.findViewById(R.id.ver_title);

            ver_title.setText("Work in progress!");
            ver_name.setText("We will be back soon!");
            main_title.setText("Not available!");
            msg.setText("Some section of this app is under construction. So it is not available right now. Sorry for the inconvenience.");

            LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.linear);
            linearLayout.setVisibility(View.GONE);
            dialog.show();
        }
    }


    class ReadJSONCV extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("personal_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayListCV.add(new ProductCV(
                            productObject.getString("photo_url"),
                            productObject.getString("signature_url"),
                            productObject.getString("user_full_name"),
                            productObject.getString("user_type"),
                            productObject.getString("email"),
                            productObject.getString("address"),
                            productObject.getString("permanent_address"),
                            productObject.getString("father_name"),
                            productObject.getString("mother_name"),
                            productObject.getString("birth_date"),
                            productObject.getString("gender"),
                            productObject.getString("objective"),
                            productObject.getString("marital_status"),
                            productObject.getString("religion"),
                            productObject.getString("national_id"),
                            productObject.getString("mobile"),
                            productObject.getString("alt_mobile"),
                            productObject.getString("facebook_id"),
                            productObject.getString("desired_job"),
                            productObject.getString("reference"),
                            productObject.getString("district_id"),
                            productObject.getString("job_experience"),
                            productObject.getString("username")
                    ));

                    String fullname = arrayListCV.get(i).getUser_full_name();
                    if (!fullname.equals("")) {
                        c1 += 5;
                        name.setText(fullname);
                    }
                    String basa = arrayListCV.get(i).getAddress();
                    if (!basa.equals("")) {
                        c1 += 10;
                    }
                    String mail = arrayListCV.get(i).getEmail();
                    if (!mail.equals("")) {
                        c1 += 10;
                    }
                    String mob = arrayListCV.get(i).getMobile();
                    if (!mob.equals("")) {
                        c1 += 10;
                    }
                    String mob2 = arrayListCV.get(i).getAlt_mobile();
                    if (mob2.equals("null") || mob2.equals("")) {
                        c1 += 0;
                        //Toast.makeText(JobSeekerHome.this, mob2, Toast.LENGTH_SHORT).show();
                    } else {
                        c1 += 10;
                    }
                    String father = arrayListCV.get(i).getFather_name();
                    if (!father.equals("")) {
                        c1 += 10;
                    }
                    String mother = arrayListCV.get(i).getMother_name();
                    if (!mother.equals("")) {
                        c1 += 10;
                    }
                    String birth = arrayListCV.get(i).getBirth_date();
                    if (birth.equals("") || birth.equals("0000-00-00")) {
                        c1 += 0;
                    } else {
                        c1 += 5;
                    }
                    String gen = arrayListCV.get(i).getGender();
                    if (!gen.equals("")) {
                        c1 += 10;
                    }
                    String marriage = arrayListCV.get(i).getMarital_status();
                    if (!marriage.equals("")) {
                        c1 += 2;
                    }
                    String rel = arrayListCV.get(i).getReligion();
                    if (!rel.equals("")) {
                        c1 += 2;
                    }
                    String nid = arrayListCV.get(i).getNid();
                    if (!nid.equals("")) {
                        c1 += 2;
                    }
                    String fbid = arrayListCV.get(i).getFb();
                    String obj = arrayListCV.get(i).getObjective();
                    if (!obj.equals("")) {
                        c1 += 2;
                    }
                    String pic = arrayListCV.get(i).getPhoto();
                    if (pic.equals("") || pic.contains("localhost") || pic.equals("http://www.journeymakerjobs.com/images/jobseeker/jobseekerimage/jobseekeroldimage")) {
                        c9 = 0;
                        pro_pic.setImageDrawable(getResources().getDrawable(R.drawable.member_icon));
                    } else {
                        c9 = 100;
                        Picasso.with(JobSeekerHome.this).load(pic).into(pro_pic);
                    }
                    String sign = arrayListCV.get(i).getSignature();
                    if (!sign.equals("") && !sign.contains("localhost")) {
                        c10 = 100;
                    }
                    String dist = arrayListCV.get(i).getDist();
                    if (!dist.equals("")) {
                        c1 += 2;
                    }
                    String user = arrayListCV.get(i).getUser();
                    if (!user.equals("")) {
                        c1 += 10;
                    }
                }
                //Toast.makeText(JobSeekerHome.this, "" + c1, Toast.LENGTH_SHORT).show();
                b1.setProgress(c1);
                pp1.setText(c1 + "%");
                b9.setProgress(c9);
                pp9.setText(c9 + "%");
                b10.setProgress(c10);
                pp10.setText(c10 + "%");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("employment_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductExpertiseTraining(
                            productObject.getString("user_work_id"),
                            productObject.getString("company_name"),
                            productObject.getString("designation"),
                            productObject.getString("job_start"),
                            productObject.getString("job_end"),
                            productObject.getString("responsibility")
                    ));
                    c3++;
                    if (c3 != 0) {
                        c3 = 100;
                        b3.setProgress(100);
                        pp3.setText(c3 + "%");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSON1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("educational_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductEducation(
                            productObject.getString("user_education_id"),
                            productObject.getString("degree_id"),
                            productObject.getString("degree_title"),
                            productObject.getString("institution"),
                            productObject.getString("result"),
                            productObject.getString("semester"),
                            productObject.getString("pass_year"),
                            productObject.getString("degree_name"),
                            productObject.getString("major")
                    ));
                    String deg_id = arrayList1.get(i).getDegree_id();
                    if (deg_id.equals("102")) {
                        c2 += 30;
                    }
                    if (deg_id.equals("104")) {
                        c2 += 30;
                    }
                    if (deg_id.equals("106")) {
                        c2 += 40;
                    }
                    if (deg_id.equals("108") && deg_id.equals("110")) {
                        c2 += 40;
                    }
                    if (deg_id.equals("108")) {
                        c2 += 20;
                    }
                    if (deg_id.equals("110")) {
                        c2 += 20;
                    }
                }
                b2.setProgress(c2);
                pp2.setText(c2 + "%");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSON2 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("training_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList2.add(new ProductExpertiseTraining(
                            productObject.getString("user_training_id"),
                            productObject.getString("training_title"),
                            productObject.getString("topic_details"),
                            productObject.getString("institution"),
                            productObject.getString("location"),
                            productObject.getString("duration")
                    ));
                    c4++;
                    if (c4 != 0) {
                        c4 = 100;
                        b4.setProgress(100);
                        pp4.setText(c4 + "%");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSON4 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("achievement_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList4.add(new ProductJobType(
                            productObject.getString("user_achievement_id"),
                            productObject.getString("achievement")
                    ));
                    c6++;
                    if (c6 != 0) {
                        c6 = 100;
                        b6.setProgress(100);
                        pp6.setText(c6 + "%");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSON5 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("reference_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList5.add(new ProductJobType(
                            productObject.getString("user_reference_id"),
                            productObject.getString("reference")
                    ));
                    c7++;
                    if (c7 != 0) {
                        c7 = 100;
                        b7.setProgress(100);
                        pp7.setText(c7 + "%");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSON6 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("extracurricular_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList7.add(new ProductExtracurricular(
                            productObject.getString("extracurricular_id"),
                            productObject.getString("extracurricular_organization"),
                            productObject.getString("extracurricular_position"),
                            productObject.getString("extracurricular_duration"),
                            productObject.getString("extracurricular_details_all")
                    ));
                    c5++;
                }
                //Toast.makeText(CV.this, "" + cnt, Toast.LENGTH_SHORT).show();
                if (c5 != 0) {
                    c5 = 100;
                    b5.setProgress(100);
                    pp5.setText(c5 + "%");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class ReadJSONCheck extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("desired_job_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList6.add(new ProductNotify(
                            productObject.getString("user_desired_job_id"),
                            productObject.getString("desired_job_id"),
                            productObject.getString("user_id")
                    ));
                    c8++;
                    if (c8 != 0) {
                        c8 = 100;
                        b8.setProgress(100);
                        pp8.setText(c8 + "%");
                    }
                }
                total_per = c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8 + c9 + c10;
                Float per = (float) total_per;
                per = per / 10;
                total_percent.setText(new DecimalFormat("##.##").format(per) + "%");
                total_prog.setProgress(Math.round(Float.parseFloat(per + "")));
                //Toast.makeText(JobSeekerHome.this, "" + total_per, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public class Logout {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.logout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button official = (Button) dialog.findViewById(R.id.no);
            official.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            //contact.setText(msg3);
            Button personal = (Button) dialog.findViewById(R.id.yes);
            personal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();

                    //Puting the value false for loggedin
                    editor.putBoolean(Utils.LOGGEDIN_SHARED_PREF, false);

                    //Putting blank value to email
                    editor.putString(Utils.LOGIN_SHARED_PREF, "");

                    //Saving the sharedpreferences
                    editor.commit();

                    SharedPreferences preferences1 = getSharedPreferences(Utils.SHARED_PREF_PDF, Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor1 = preferences1.edit();

                    //Puting the value false for loggedin
                    editor1.putBoolean(Utils.PDF_SHARED_PREF, false);

                    //Putting blank value to email
                    editor1.putString(Utils.OK_SHARED_PREF, "");

                    //Saving the sharedpreferences
                    editor1.commit();

                    //Starting login activity
                    Intent intent = new Intent(JobSeekerHome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
