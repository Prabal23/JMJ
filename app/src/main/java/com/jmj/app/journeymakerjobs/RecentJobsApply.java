package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RecentJobsApply extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String imgs, grp, name, res, id, username, pass, s, r, ji, jt, ci, d, ai, n, e, lo, ed, gs, v, ld, cn, jai, SetServerString;
    private ProgressBar progres;
    private TextView error, alert, erroralert, count;
    ArrayList<ProductJobReport> arrayList;
    private HttpClient Client;
    HttpGet httpget;
    RelativeLayout l;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_jobs_apply);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        error = (TextView) findViewById(R.id.error);
        error.setTypeface(fontAwesomeFont);
        erroralert = (TextView) findViewById(R.id.erroralert);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                String answer = "Connected to Wifi";
                erroralert.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                String answer = "Connected to Mobile Network";
                erroralert.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
            }
        } else {
            String answer = "Internet connection is off";
            erroralert.setText(answer);
            erroralert.setVisibility(View.VISIBLE);
            error.setVisibility(View.VISIBLE);
        }

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

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        count = (TextView) findViewById(R.id.count);
        //alert.setVisibility(View.VISIBLE);

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
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

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=" + id);
            }
        });

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        error.setVisibility(View.GONE);
                        erroralert.setVisibility(View.GONE);
                        swipe.setRefreshing(true);
                        swipe.setEnabled(true);
                        progres.setVisibility(View.VISIBLE);
                        //alert.setVisibility(View.VISIBLE);
                        arrayList = new ArrayList<>();
                        lv = (ListView) findViewById(R.id.listView);

                        arrayList = new ArrayList<>();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=" + id);
                            }
                        });

                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        } else {
                            swipe.setRefreshing(true);
                        }
                    }
                }
        );

        l = (RelativeLayout) findViewById(R.id.l);
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progres.setVisibility(View.GONE);
                //alert.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("applied_job_list");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductJobReport(
                            productObject.getString("job_apply_id"),
                            productObject.getString("apply_date"),
                            productObject.getString("expected_salary"),
                            productObject.getString("remarks"),
                            productObject.getString("job_id"),
                            productObject.getString("job_title"),
                            productObject.getString("company_id"),
                            productObject.getString("job_description"),
                            productObject.getString("additional_info"),
                            productObject.getString("job_nature"),
                            productObject.getString("job_location"),
                            productObject.getString("education"),
                            productObject.getString("experience"),
                            productObject.getString("salary"),
                            productObject.getString("vacancy"),
                            productObject.getString("last_date"),
                            productObject.getString("company_name"),
                            productObject.getString("photo_url")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapterApply adapter = new CustomListAdapterApply(getApplicationContext(), R.layout.applied_job_lists, arrayList);
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            String total = String.valueOf(tot);
            //total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            count.setText(total);
            if (total.equals("0")) {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        String answer = "Connected to Wifi";
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No applied job yet");
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        String answer = "Connected to Mobile Network";
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No applied job yet");
                    }
                } else {
                    String answer = "Internet connection is off";
                    erroralert.setText(answer);
                    erroralert.setVisibility(View.VISIBLE);
                    error.setVisibility(View.VISIBLE);
                }
            }
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(RecentJobsApply.this, R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    jai = arrayList.get(i).getJobApplyID();
                    String ad = arrayList.get(i).getApplyDate();
                    s = arrayList.get(i).getSalary();
                    r = arrayList.get(i).getRemarks();
                    ji = arrayList.get(i).getJob_id();
                    jt = arrayList.get(i).getJob_title();
                    ci = arrayList.get(i).getCom_id();
                    d = arrayList.get(i).getDescription();
                    ai = arrayList.get(i).getAddition_info();
                    n = arrayList.get(i).getNature();
                    e = arrayList.get(i).getExperience();
                    lo = arrayList.get(i).getLocation();
                    ed = arrayList.get(i).getEducation();
                    gs = arrayList.get(i).getGiven_salary();
                    v = arrayList.get(i).getVacancy();
                    ld = arrayList.get(i).getLast_date();
                    cn = arrayList.get(i).getCom_name();
                    imgs = arrayList.get(i).getImage();
                    Intent intent = new Intent(RecentJobsApply.this, RecentJobDetails.class);
                    intent.putExtra("gp", "");
                    intent.putExtra("page", "1");
                    intent.putExtra("name", jt);
                    intent.putExtra("cn", cn);
                    intent.putExtra("ji", ji);
                    intent.putExtra("ci", ci);
                    intent.putExtra("d", d);
                    intent.putExtra("ai", ai);
                    intent.putExtra("n", n);
                    intent.putExtra("e", e);
                    intent.putExtra("ed", ed);
                    intent.putExtra("lo", lo);
                    intent.putExtra("gs", gs);
                    intent.putExtra("v", v);
                    intent.putExtra("ld", ld);
                    intent.putExtra("s", s);
                    intent.putExtra("r", r);
                    intent.putExtra("jai", jai);
                    intent.putExtra("img", imgs);
                    startActivity(intent);
                }
            });
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

    public class CustomListAdapterApply extends ArrayAdapter<ProductJobReport> {

        ArrayList<ProductJobReport> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterApply(Context context, int resource, ArrayList<ProductJobReport> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.applied_job_lists, null, true);
            }
            ProductJobReport product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            ImageView image = (ImageView) convertView.findViewById(R.id.p1);
            String img = product.getImage();
            if (!img.equals("")) {
                //Toast.makeText(RecentJobs.this, "l1", Toast.LENGTH_SHORT).show();
                Picasso.with(context).load(img).into(image);
            }
            if (img.endsWith("/") || img.equals("")) {
                image.setImageDrawable(getResources().getDrawable(R.drawable.jobs_icon));
            }

            TextView eye = (TextView) convertView.findViewById(R.id.apply);
            TextView edit = (TextView) convertView.findViewById(R.id.edit);
            TextView delete = (TextView) convertView.findViewById(R.id.delete);
            TextView save = (TextView) convertView.findViewById(R.id.save);
            eye.setTypeface(fontAwesomeFont);
            edit.setTypeface(fontAwesomeFont);
            delete.setTypeface(fontAwesomeFont);
            save.setTypeface(fontAwesomeFont);

            LinearLayout r5 = (LinearLayout) convertView.findViewById(R.id.r5);
            LinearLayout a = (LinearLayout) convertView.findViewById(R.id.a);
            LinearLayout ed = (LinearLayout) convertView.findViewById(R.id.e);
            LinearLayout d = (LinearLayout) convertView.findViewById(R.id.d);

            try {
                TextView post = (TextView) convertView.findViewById(R.id.post);
                String address = product.getJob_title();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                post.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView salary = (TextView) convertView.findViewById(R.id.salary);
                String address = product.getSalary();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                salary.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView date = (TextView) convertView.findViewById(R.id.date);
                String address = product.getLast_date();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                date.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RecentJobsApply.this, RecentJobDetails.class);
                    intent.putExtra("gp", "");
                    intent.putExtra("page", "1");
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });

            ed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    s = arrayList.get(position).getSalary();
                    r = arrayList.get(position).getRemarks();
                    jai = arrayList.get(position).getJobApplyID();
                    ji = arrayList.get(position).getJob_id();
                    name = arrayList.get(position).getJob_title();
                    String p = arrayList.get(position).getImage();
                    Intent intent = new Intent(RecentJobsApply.this, RecentJobRemarks.class);
                    intent.putExtra("salary", s);
                    intent.putExtra("remarks", r);
                    intent.putExtra("name", name);
                    intent.putExtra("jai", jai);
                    intent.putExtra("ji", ji);
                    intent.putExtra("pic", p);
                    startActivity(intent);
                }
            });

            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jai = arrayList.get(position).getJobApplyID();
                    DeleteJob alert1 = new DeleteJob();
                    alert1.showDialog(RecentJobsApply.this, jai);
                }
            });

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(RecentJobsApply.this, RecentJobDetails.class);
                    intent.putExtra("gp", "");
                    intent.putExtra("page", "1");
                    intent.putExtra("name", name);
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    public class DeleteJob {

        public void showDialog(AppCompatActivity activity, final String job_apply_id) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.delete_job_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button yes = (Button) dialog.findViewById(R.id.yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Client = new DefaultHttpClient();

                    String URL = Utils.IP + "direct/applasdfasdfieasdfd_jaasdfsdfasdfob_deletasadsfdfasdfe/64341424?job_apply_id=" + job_apply_id;
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
                    dialog.dismiss();
                }
            });

            Button no = (Button) dialog.findViewById(R.id.no);
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

    public void insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(RecentJobsApply.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), RecentJobsApply.class);
            startActivity(intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
