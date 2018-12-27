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
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobCategory extends AppCompatActivity {
    ArrayList<ProductJobType> arrayList5;
    ArrayList<ProductNotify> arrayList4;
    ListView lv, lv1;
    String res, id, user, jobid = "", jj, j1, j2 = "", name, f;
    Typeface fontAwesomeFont;
    List<String> allID;
    int s, c = 0, c1, job_count = 0;
    String[] a;
    private ProgressBar progres;
    TextView job_c, error, erroralert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_category);
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);*/

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        user = result[2];

        job_c = (TextView) findViewById(R.id.count);
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

        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);

        lv = (ListView) findViewById(R.id.listView);
        lv.setItemsCanFocus(false);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        lv1 = (ListView) findViewById(R.id.listView1);
        lv1.setItemsCanFocus(false);
        lv1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        arrayList4 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCheck().execute(Utils.IP + "direct/asdfdesiasdasdfred_asdfjasdfasdfoaasdfsdfb_asdfdasdfetaisdfasdls/12342124?user_id=" + id);

            }
        });

        arrayList5 = new ArrayList<>();
        allID = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON4().execute(Utils.IP + "direct/jadfodfb_casdfatasdfeasgory_asdfinasdfasffo/12342124?user_id=" + id);

            }
        });

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        TextView add = (TextView) findViewById(R.id.add);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        add.setTypeface(fontAwesomeFont);
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

        LinearLayout e = (LinearLayout) findViewById(R.id.e);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    class ReadJSON4 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progres.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("job_category_info");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList5.add(new ProductJobType(
                            productObject.getString("job_category_id"),
                            productObject.getString("category_name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterCategory adapter = new CustomListAdapterCategory(
                    getApplicationContext(), R.layout.job_category_list_item, arrayList5
            );
            lv.setAdapter(adapter);
            ListViewUtil1.setListViewHeightBasedOnChildren1(lv);
            TextView views = (TextView) findViewById(R.id.list_empty);
            lv.setEmptyView(views);
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
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("desired_job_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList4.add(new ProductNotify(
                            productObject.getString("user_desired_job_id"),
                            productObject.getString("desired_job_id"),
                            productObject.getString("user_id")
                    ));

                    String id = arrayList4.get(i).getName();
                    if (!id.equals("")) {
                        allID.add(id);
                        StringBuilder builder1 = new StringBuilder();
                        for (String details : allID) {
                            builder1.append(details + ",");
                        }
                        jj = builder1.toString();
                        jj = jj.substring(0, jj.length() - 1);
                        job_count++;
                        if(id.equals("0")){
                            job_count--;
                        }
                    } else {
                        jj = "";
                    }
                }
                job_c.setText(job_count + "");
                jobid = jj;
                //Toast.makeText(JobCategory.this, jobid, Toast.LENGTH_SHORT).show();
                if (jobid == null || jobid.equals("")) {
                    j1 = "";
                } else {
                    j2 = jobid;
                    a = j2.split(",");
                    s = a.length;
                }
                /*CustomListAdapterMyCategory adapter = new CustomListAdapterMyCategory(
                        getApplicationContext(), R.layout.job_category_list_item, arrayList4
                );
                lv1.setAdapter(adapter);
                ListViewUtil2.setListViewHeightBasedOnChildren1(lv1);
                TextView views = (TextView) findViewById(R.id.list_empty1);
                lv1.setEmptyView(views);*/
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

    public class CustomListAdapterMyCategory extends ArrayAdapter<ProductNotify> {

        ArrayList<ProductNotify> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterMyCategory(Context context, int resource, ArrayList<ProductNotify> products) {
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
                convertView = layoutInflater.inflate(R.layout.job_category_list_item, null, true);
            }
            ProductNotify product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check);
            f = product.getName();

            return convertView;
        }
    }

    public class CustomListAdapterCategory extends ArrayAdapter<ProductJobType> {

        ArrayList<ProductJobType> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;
        boolean[] checkBoxState;

        public CustomListAdapterCategory(Context context, int resource, ArrayList<ProductJobType> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
            checkBoxState = new boolean[products.size()];
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.job_category_list_item, null, true);
            }
            final ProductJobType product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            convertView.setTag(context);

            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.check);
            String jid = product.getId();
            if (j2.contains(jid)) {
                for (int i = 0; i < a.length; i++) {
                    checkBox.setChecked(true);
                    products.get(position).setChecked(true);
                }
            } else {
                checkBox.setChecked(false);
            }

            TextView text = (TextView) convertView.findViewById(R.id.txtName);
            name = product.getName();
            text.setText(name);

            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //CheckBox cb = (CheckBox) v.getTag();
                    c++;
                    if (checkBox.isChecked()) {
                        c = +2;
                        //Toast.makeText(context, "" + c, Toast.LENGTH_SHORT).show();
                        checkBox.setChecked(false);
                        String jid = product.getId();
                        if (j2.contains(jid)) {
                            j2 = j2.replace(jid, "");
                            if (j2.endsWith(",")) {
                                j2 = j2.substring(0, j2.length() - 1);
                            }
                            j2 = j2.replace(",,", ",");
                            //Toast.makeText(JobCategory.this, j2, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        //Toast.makeText(context, "" + c, Toast.LENGTH_SHORT).show();
                        if (c % 2 == 0) {
                            checkBox.setChecked(false);
                            String jid = product.getId();
                            if (j2.contains(jid)) {
                                j2 = j2.replace(jid, "");
                                if (j2.endsWith(",")) {
                                    j2 = j2.substring(0, j2.length() - 1);
                                }
                                j2 = j2.replace(",,", ",");
                                //Toast.makeText(JobCategory.this, j2, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            checkBox.setChecked(true);
                            String jid = product.getId();
                            if (c % 2 != 0) {
                                c++;
                            }
                            if (j2.contains(jid)) {
                                //Toast.makeText(JobCategory.this, j2, Toast.LENGTH_SHORT).show();
                            } else {
                                j2 = j2 + "," + jid;
                                //Toast.makeText(JobCategory.this, j2, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
            return convertView;
        }
    }

    public void submit() {
        //Toast.makeText(this, j2, Toast.LENGTH_SHORT).show();
        String URL = Utils.IP + "direct/desdfired_jaasdfsdfob_uasdfdfasdpdasdfatea/12342124?user_id=" + id + "&job_category_id=" + j2;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        Toast.makeText(JobCategory.this, "Success", Toast.LENGTH_LONG).show();
                        startActivity(getIntent());
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(JobCategory.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("user_id", id);
                params.put("job_category_id", j2);
                return params;
            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(JobCategory.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public static class ListViewUtil1 {
        private static final String CNAME = CV.ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren1(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

    public static class ListViewUtil2 {
        private static final String CNAME = CV.ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren1(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }

}
