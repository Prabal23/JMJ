package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class EmployerAllJobs extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String grp, name, s, r, imgs, ji, jt, ci, d, ai, n, e, lo, ed, gs, v, ld, cn, dist, status, pub, res, id;
    private ProgressBar progres;
    private ImageView error;
    private TextView alert, erroralert, count;
    ArrayList<ProductJobPostList> arrayList;
    private DatabaseHandler db;
    private byte[] photo;
    Bitmap bp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emp_all_jobs);

        db = new DatabaseHandler(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF_EMP)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF_EMP, "");
        }
        String[] result = res.split(" ");
        id = result[0];

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        count = (TextView) findViewById(R.id.count);
        //alert.setVisibility(View.VISIBLE);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

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
                Intent intent = new Intent(getBaseContext(), EmployerProfile.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerHome.class);
                startActivity(intent);
            }
        });

        arrayList = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSON().execute(Utils.IP + "direct/asdalasdfl_asdfasdfjoasdfb_lasdfasdistsfsadf/12342124?user_id=" + id);
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
                                new ReadJSON().execute(Utils.IP + "direct/asdalasdfl_asdfasdfjoasdfb_lasdfasdistsfsadf/12342124?user_id=" + id);
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
                JSONArray jsonArray = jsonObject.getJSONArray("all_job_list");
                /*int c = jsonArray.length();
                Toast.makeText(RecentJobs.this, "" + c, Toast.LENGTH_SHORT).show();*/
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductJobPostList(
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
                            productObject.getString("job_category_id"),
                            productObject.getString("image_url"),
                            productObject.getString("district_id"),
                            productObject.getString("status"),
                            productObject.getString("publish_date")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.layout.job_lists, arrayList
            );
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            String total = String.valueOf(tot);
            //total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            count.setText(total);
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(EmployerAllJobs.this, R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
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
                    dist = arrayList.get(i).getDist();
                    status = arrayList.get(i).getStatus();
                    pub = arrayList.get(i).getPublish();
                    Intent intent = new Intent(EmployerAllJobs.this, EmployeeJobDetails.class);
                    intent.putExtra("gp", grp);
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
                    intent.putExtra("s", "");
                    intent.putExtra("r", "");
                    intent.putExtra("jai", "");
                    intent.putExtra("img", imgs);
                    intent.putExtra("dist", dist);
                    intent.putExtra("status", status);
                    intent.putExtra("pub", pub);
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

    public class CustomListAdapter extends ArrayAdapter<ProductJobPostList> {

        ArrayList<ProductJobPostList> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;
        Bitmap bit;

        public CustomListAdapter(Context context, int resource, ArrayList<ProductJobPostList> products) {
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
                convertView = layoutInflater.inflate(R.layout.job_lists, null, true);

            }
            final ProductJobPostList product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            final ImageView image = (ImageView) convertView.findViewById(R.id.p1);
            String img = product.getImage();
            if (!img.equals("") && !img.contains("localhost")) {
                Picasso.with(context).load(img).into(image);
            }

            TextView exp = (TextView) convertView.findViewById(R.id.exp);
            TextView time = (TextView) convertView.findViewById(R.id.time);
            TextView date = (TextView) convertView.findViewById(R.id.date);
            TextView eye = (TextView) convertView.findViewById(R.id.apply);
            TextView save = (TextView) convertView.findViewById(R.id.save);
            exp.setTypeface(fontAwesomeFont);
            time.setTypeface(fontAwesomeFont);
            date.setTypeface(fontAwesomeFont);
            eye.setTypeface(fontAwesomeFont);
            save.setTypeface(fontAwesomeFont);

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
                TextView org = (TextView) convertView.findViewById(R.id.org);
                String address = product.getCom_name();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                org.setText("Total Apply : 4");
                org.setVisibility(View.GONE);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView last_date = (TextView) convertView.findViewById(R.id.last_date);
                String address = product.getLast_date();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                last_date.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView exp_ratio = (TextView) convertView.findViewById(R.id.exp_ratio);
                String address = product.getExperience();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                add = add.replaceAll("u000D000A", "<br/>");
                exp_ratio.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView full_part = (TextView) convertView.findViewById(R.id.full_part);
                String address = product.getNature();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                full_part.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            LinearLayout p = (LinearLayout) convertView.findViewById(R.id.r5);
            p.setVisibility(View.VISIBLE);
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String job_id = arrayList.get(position).getJob_id();
                    String pic = arrayList.get(position).getImage();
                    Intent intent = new Intent(EmployerAllJobs.this, AllJobPicChange.class);
                    intent.putExtra("job_id", job_id);
                    intent.putExtra("pic", pic);
                    //Toast.makeText(context, pic, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
