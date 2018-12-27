package com.jmj.app.journeymakerjobs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class RecentJobs extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView lv, lv2;
    private SwipeRefreshLayout swipe;
    private String total, category, catid = "", grp, name, s, r, imgs, ji, jt, ci, d, ai, n, e, lo, ed, gs, v, ld, cn, jai, refid, res, id, rid;
    private ProgressBar progres;
    private TextView error, status, wrong, alert, erroralert, count, jobs, apply, cat, nodata, counter;
    ArrayList<ProductwithImage> arrayList;
    ArrayList<ProductJobType> arrayList1;
    GifImageView gifImageView;
    private DatabaseHandler db;
    private byte[] photo;
    Bitmap bp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_jobs);

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

        db = new DatabaseHandler(this);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];

        refid = getIntent().getStringExtra("refid");
        //Toast.makeText(this, refid, Toast.LENGTH_SHORT).show();

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        status = (TextView) findViewById(R.id.status);
        wrong = (TextView) findViewById(R.id.wrong);
        wrong.setTypeface(fontAwesomeFont);
        jobs = (TextView) findViewById(R.id.jobs);
        jobs.setTypeface(fontAwesomeFont);
        apply = (TextView) findViewById(R.id.apply);
        apply.setTypeface(fontAwesomeFont);
        cat = (TextView) findViewById(R.id.cat);
        cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category alert = new Category();
                alert.showDialog(RecentJobs.this, "");
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progres.setVisibility(View.VISIBLE);
                applyCategory();
            }
        });

        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                status.setVisibility(View.GONE);
                cat.setText("Select Job Category");
                catid = "";
                wrong.setVisibility(View.GONE);
            }
        });

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
                new ReadJSON().execute(Utils.IP + "direct/adsfasdf546_dfbsfgsdgfdgfsdfgsdf354fghsfg/12342124?employee_id=&job_category_id=");
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
                        swiping();
                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        } else {
                            swipe.setRefreshing(true);
                        }
                    }
                }
        );
    }

    public class Category {
        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.job_type_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText("Select Job Category");
            nodata = (TextView) dialog.findViewById(R.id.nodata);
            count = (TextView) dialog.findViewById(R.id.count);
            gifImageView = (GifImageView) dialog.findViewById(R.id.loader);
            gifImageView.setVisibility(View.VISIBLE);

            lv2 = (ListView) dialog.findViewById(R.id.listView);
            arrayList1 = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON1().execute(Utils.IP + "direct/jadfodfb_casdfatasdfeasgory_asdfinasdfasffo/12342124?user_id=");
                }
            });

            //Toast.makeText(JobSeekerReg.this, Utils.IP + "direct/deasdfgreasdfase_lissdfasdt/12342124", Toast.LENGTH_SHORT).show();

            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    catid = arrayList1.get(i).getId();
                    //Toast.makeText(RecentJobs.this, catid, Toast.LENGTH_SHORT).show();
                    category = arrayList1.get(i).getName();
                    cat.setText(category);
                    wrong.setVisibility(View.VISIBLE);
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

    class ReadJSON1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                gifImageView.setVisibility(View.GONE);
                //alert.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("job_category_info");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductJobType(
                            productObject.getString("job_category_id"),
                            productObject.getString("category_name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nodata.setVisibility(View.VISIBLE);
            }
            final CustomListAdapterCategory adapter = new CustomListAdapterCategory(
                    getApplicationContext(), R.layout.jobtype_list_item, arrayList1
            );
            lv2.setAdapter(adapter);
        }
    }

    public void applyCategory() {
        if (catid.equals("")) {
            error.setVisibility(View.GONE);
            erroralert.setVisibility(View.GONE);
            status.setVisibility(View.VISIBLE);
            progres.setVisibility(View.GONE);
            status.setText("** Please select a category first **");
            //Toast.makeText(this, "Please select a category first", Toast.LENGTH_SHORT).show();
        } else {
            total = "";
            error.setVisibility(View.GONE);
            erroralert.setVisibility(View.GONE);
            status.setVisibility(View.GONE);
            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute(Utils.IP + "direct/adsfasdf546_dfbsfgsdgfdgfsdfgsdf354fghsfg/12342124?employee_id=&job_category_id=" + catid);
                }
            });
        }
    }

    public void swiping() {
        if (catid.equals("")) {
            status.setVisibility(View.GONE);
            arrayList = new ArrayList<>();
            lv = (ListView) findViewById(R.id.listView);

            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute(Utils.IP + "direct/adsfasdf546_dfbsfgsdgfdgfsdfgsdf354fghsfg/12342124?employee_id=&job_category_id=");
                }
            });
        } else {
            status.setVisibility(View.GONE);
            lv = (ListView) findViewById(R.id.listView);

            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute(Utils.IP + "direct/adsfasdf546_dfbsfgsdgfdgfsdfgsdf354fghsfg/12342124?employee_id=&job_category_id=" + catid);
                }
            });
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
                progres.setVisibility(View.GONE);
                erroralert.setVisibility(View.GONE);
                error.setVisibility(View.GONE);
                //alert.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("running_job_list");
                int cc = 0;
                /*int c = jsonArray.length();
                Toast.makeText(RecentJobs.this, "" + c, Toast.LENGTH_SHORT).show();*/
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductwithImage(
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
                            productObject.getString("photo_url"),
                            productObject.getString("image_url"),
                            productObject.getString("photo"),
                            productObject.getString("photo_2")
                    ));
                    imgs = arrayList.get(i).getImage();
                    if (imgs.endsWith("/") || imgs.equals("")) {
                        cc++;
                    }
                    rid = arrayList.get(i).getJob_id();
                    if (rid.equals(refid)) {
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
                        Intent intent = new Intent(RecentJobs.this, RecentJobDetails.class);
                        intent.putExtra("gp", grp);
                        intent.putExtra("page", "0");
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
                        startActivity(intent);
                        finish();
                    } else if (refid.equals("0")) {

                    } else {
                        finish();
                        //Toast.makeText(RecentJobs.this, "No such job has been found!", Toast.LENGTH_SHORT).show();
                    }
                }
                /*if(!rid.equals(refid)){
                    Intent intent = new Intent(getBaseContext(), RecentJobsNotify.class);
                    intent.putExtra("id", id);
                    intent.putExtra("msg", "1");
                    startActivity(intent);
                    //Toast.makeText(RecentJobs.this, "No such job has been found!", Toast.LENGTH_SHORT).show();
                    finish();
                }*/
                // Toast.makeText(RecentJobs.this, "" + cc, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapter adapter = new CustomListAdapter(getApplicationContext(), R.layout.job_lists, arrayList);
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            total = String.valueOf(tot);
            //Toast.makeText(RecentJobs.this, total, Toast.LENGTH_SHORT).show();
            count = (TextView) findViewById(R.id.count);
            count.setText(total);
            if (total.equals("0")) {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        String answer = "Connected to Wifi";
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No job available for this category");
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        String answer = "Connected to Mobile Network";
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No job available for this category");
                    }
                } else {
                    String answer = "Internet connection is off";
                    erroralert.setText(answer);
                    erroralert.setVisibility(View.VISIBLE);
                    error.setVisibility(View.VISIBLE);
                }
            }
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(RecentJobs.this, R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
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
                    Intent intent = new Intent(RecentJobs.this, RecentJobDetails.class);
                    intent.putExtra("gp", grp);
                    intent.putExtra("page", "0");
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

    public class CustomListAdapter extends ArrayAdapter<ProductwithImage> {

        ArrayList<ProductwithImage> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;
        Bitmap bit;

        private class ViewHolder {
            ImageView image;
        }

        public CustomListAdapter(Context context, int resource, ArrayList<ProductwithImage> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.job_lists, null, true);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final ProductwithImage product = getItem(position);

            int cc = 0;
            Uri uris = null;
            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            holder.image = (ImageView) convertView.findViewById(R.id.p1);
            String img = product.getImage();
            String img_url = product.getImage_url();
            String pht = product.getPhoto();
            String photo_2 = product.getPhoto_2();
            if (!img.equals("")) {
                //Toast.makeText(RecentJobs.this, "l1", Toast.LENGTH_SHORT).show();
                Picasso.with(context)
                        .load(img)
                        .into(holder.image);
            }
            if (img.endsWith("/") || img.equals("")) {
                holder.image.setImageDrawable(getResources().getDrawable(R.drawable.jobs_icon));
            }
                /*else {
                if (!(pht.equals("null") || pht.equals(""))) {
                    //Toast.makeText(RecentJobs.this, "l2", Toast.LENGTH_SHORT).show();
                    Picasso.with(context)
                            .load(img)
                            .into(holder.image);
                }
                if (!(photo_2.equals("null") || photo_2.equals(""))) {
                    //Toast.makeText(RecentJobs.this, "l3", Toast.LENGTH_SHORT).show();
                    Picasso.with(context)
                            .load(img)
                            .into(holder.image);
                }
            }*/

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
                //post.setText(add);
                post.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView org = (TextView) convertView.findViewById(R.id.org);
                String address = product.getCom_name();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                //org.setText(add);
                org.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView last_date = (TextView) convertView.findViewById(R.id.last_date);
                String address = product.getLast_date();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                //last_date.setText(add);
                last_date.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView exp_ratio = (TextView) convertView.findViewById(R.id.exp_ratio);
                String address = product.getExperience();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                //exp_ratio.setText(add);
                exp_ratio.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView full_part = (TextView) convertView.findViewById(R.id.full_part);
                String address = product.getNature();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                //full_part.setText(add);
                full_part.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            LinearLayout r5 = (LinearLayout) convertView.findViewById(R.id.r5);
            r5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    name = arrayList.get(position).getJob_title();
                    ji = arrayList.get(position).getJob_id();
                    String p = arrayList.get(position).getImage();
                    Intent intent = new Intent(getBaseContext(), RecentJobRemarks.class);
                    intent.putExtra("salary", "");
                    intent.putExtra("remarks", "");
                    intent.putExtra("name", name);
                    intent.putExtra("jai", "");
                    intent.putExtra("ji", ji);
                    intent.putExtra("pic", p);
                    startActivity(intent);
                }
            });

            LinearLayout r6 = (LinearLayout) convertView.findViewById(R.id.r6);
            r6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String title = product.getJob_title();
                    String com = product.getCom_name();
                    String des = product.getDescription();
                    String add = product.getAddition_info();
                    String nat = product.getNature();
                    String loc = product.getLocation();
                    String edu = product.getEducation();
                    String exp = product.getExperience();
                    String sal = product.getGiven_salary();
                    String vac = product.getVacancy();
                    String date = product.getLast_date();
                    String pix = product.getImage();
                    /*if(!pix.equals("") || !pix.contains("localhost")){
                        photo = profileImage(bit);
                        db.addContacts(new Contact(title, com, des, add, nat, loc, edu, exp, sal, vac, date, photo));
                        Toast.makeText(getApplicationContext(), "Job Saved Successfully", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(RecentJobs.this, "Please wait until image is loading", Toast.LENGTH_SHORT).show();
                    }*/
                    photo = profileImage(bit);
                    /*db.addContacts(new Contact(title, com, des, add, nat, loc, edu, exp, sal, vac, date, photo));
                    Toast.makeText(getApplicationContext(), "Job Saved Successfully", Toast.LENGTH_LONG).show();*/
                }
            });

            return convertView;
        }
    }

    public class CustomListAdapterCategory extends ArrayAdapter<ProductJobType> {

        ArrayList<ProductJobType> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterCategory(Context context, int resource, ArrayList<ProductJobType> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.jobtype_list_item, null, true);

            }
            ProductJobType product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            TextView job = (TextView) convertView.findViewById(R.id.job);
            job.setTypeface(fontAwesomeFont);
            job.setText(getResources().getString(R.string.jobs));

            try {
                TextView type = (TextView) convertView.findViewById(R.id.txtName);
                String address = product.getName();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                type.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
