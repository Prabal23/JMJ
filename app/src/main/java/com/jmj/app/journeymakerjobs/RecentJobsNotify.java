package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RecentJobsNotify extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    Typeface fontAwesomeFont;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String grp, name, id, res;
    private ProgressBar progres;
    private TextView error, alert, erroralert, count, checking;
    ArrayList<ProductNotify> arrayList;
    ProductNotify product;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_jobs_notify);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        //id = result[0];

        id = getIntent().getStringExtra("id");
        String msg = getIntent().getStringExtra("msg");

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        count = (TextView) findViewById(R.id.count);
        checking = (TextView) findViewById(R.id.checking);
        if (msg.equals("1")) {
            checking.setVisibility(View.VISIBLE);
            checking.setText("No such job has been found!");
        } else {
            checking.setVisibility(View.GONE);
        }
        //alert.setVisibility(View.VISIBLE);

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
                new ReadJSON().execute(Utils.IP + "direct/ffgdsf345gind_notif45sd345354fgi345cation_asdf45sdf54ldf45345gist/12342124?user_id=" + id);
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
                                new ReadJSON().execute(Utils.IP + "direct/ffgdsf345gind_notif45sd345354fgi345cation_asdf45sdf54ldf45345gist/12342124?user_id=" + id);
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
                JSONArray jsonArray = jsonObject.getJSONArray("all_notification");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductNotify(
                            productObject.getString("ref_id"),
                            productObject.getString("description"),
                            productObject.getString("timestamp")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapterNotify adapter = new CustomListAdapterNotify(
                    getApplicationContext(), R.layout.job_notify_lists, arrayList
            );
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
                        erroralert.setText("No alerts for job");
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        String answer = "Connected to Mobile Network";
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No alerts for job");
                    }
                } else {
                    String answer = "Internet connection is off";
                    erroralert.setText(answer);
                    erroralert.setVisibility(View.VISIBLE);
                    error.setVisibility(View.VISIBLE);
                }
            }
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(RecentJobsNotify.this, R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String id = arrayList.get(i).getId();
                    name = arrayList.get(i).getName();
                    Intent intent = new Intent(RecentJobsNotify.this, RecentJobs.class);
                    intent.putExtra("refid", id);
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

    public class CustomListAdapterNotify extends ArrayAdapter<ProductNotify> {

        ArrayList<ProductNotify> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterNotify(Context context, int resource, ArrayList<ProductNotify> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.job_notify_lists, null, true);

            }
            product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            TextView settings = (TextView) convertView.findViewById(R.id.settings);
            settings.setTypeface(fontAwesomeFont);

            try {
                TextView type = (TextView) convertView.findViewById(R.id.post);
                String address = product.getName();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                type.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView type = (TextView) convertView.findViewById(R.id.post_time);
                String address = product.getTime();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                type.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            LinearLayout r5 = (LinearLayout) convertView.findViewById(R.id.r5);
            r5.setVisibility(View.GONE);
            r5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = arrayList.get(position).getName();
                    PopupMenu popup = new PopupMenu(RecentJobsNotify.this, view);
                    popup.setOnMenuItemClickListener(RecentJobsNotify.this);
                    popup.inflate(R.menu.menu_main);
                    popup.show();
                }
            });

            return convertView;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        //Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.more_item:
                // do your code
                return true;
            case R.id.more_jobs_item:
                // do your code
                return true;
            default:
                return false;
        }
    }
}
