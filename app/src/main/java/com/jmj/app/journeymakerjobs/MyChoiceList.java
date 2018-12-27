package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

public class MyChoiceList extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String response, grp, name, imgs, s, r, ji, jt, ci, d, ai, n, e, lo, ed, gs, v, ld, cn, jai, SetServerString, res, id, user;
    private ProgressBar progres;
    private TextView error, alert, erroralert, count;
    ArrayList<ProductwithImage> arrayList;
    int c = 0, c1 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_choice_list);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        user = result[2];

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        count = (TextView) findViewById(R.id.count);
        //alert.setVisibility(View.VISIBLE);

        error = (TextView) findViewById(R.id.error);
        error.setTypeface(fontAwesomeFont);
        erroralert = (TextView) findViewById(R.id.erroralert);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                String answer = "Connected  to Wifi";
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
                new ReadJSON1().execute(Utils.IP + "direct/jgjhklmljhgkjgkjhy_choigfchjrkjgbce_lislkhkhklhklhjt/12342124?user_id=" + id);
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
                                new ReadJSON1().execute(Utils.IP + "direct/jgjhklmljhgkjgkjhy_choigfchjrkjgbce_lislkhkhklhklhjt/12342124?user_id=" + id);
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

    class ReadJSON1 extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progres.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                //content = content.replace("[[", "[").replace("]]", "]");
                JSONArray jsonArray = jsonObject.getJSONArray("category_wise_job");
                String arr = jsonArray.toString();
                arr = arr.replace("[[", "[").replace("]]", "]");
                //arr = "{\"" + "job_cat" + "\":" + arr;
                //JSONObject jsonObject1 = new JSONObject(arr);
                //JSONArray jsonArray1 = jsonObject1.getJSONArray("job_cat");
                //Toast.makeText(MyChoiceList.this, "" + arr, Toast.LENGTH_SHORT).show();
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

                    String tit = arrayList.get(i).getJob_title();
                    //Toast.makeText(MyChoiceList.this, tit, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapterChoice adapter = new CustomListAdapterChoice(getApplicationContext(), R.layout.job_lists, arrayList);
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            String total = String.valueOf(tot);
            //total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            count.setText(total);
            TextView status = (TextView) findViewById(R.id.status);
            status.setText(response);
            if (total.equals("0")) {
                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                if (null != activeNetwork) {
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                        String answer = "Connected to Wifi";
                        //Toast.makeText(MyChoiceList.this, answer, Toast.LENGTH_SHORT).show();
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No job available");
                    }
                    if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                        String answer = "Connected to Mobile Network";
                        //Toast.makeText(MyChoiceList.this, answer, Toast.LENGTH_SHORT).show();
                        error.setVisibility(View.VISIBLE);
                        erroralert.setVisibility(View.VISIBLE);
                        erroralert.setText("No job available");
                    }
                } else {
                    String answer = "Internet connection is off";
                    //Toast.makeText(MyChoiceList.this, answer, Toast.LENGTH_SHORT).show();
                    erroralert.setText(answer);
                    erroralert.setVisibility(View.VISIBLE);
                    error.setVisibility(View.VISIBLE);
                }
            }
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(MyChoiceList.this, R.anim.alpha1);
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
                    Intent intent = new Intent(MyChoiceList.this, RecentJobDetails.class);
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

    public class CustomListAdapterChoice extends ArrayAdapter<ProductwithImage> {

        ArrayList<ProductwithImage> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterChoice(Context context, int resource, ArrayList<ProductwithImage> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.job_lists, null, true);
            }
            ProductwithImage product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            ImageView image = (ImageView) convertView.findViewById(R.id.p1);
            String img = product.getImage();
            if (!img.equals("") && !img.contains("localhost")) {
                Picasso.with(context).load(img).into(image);
            } else {
                Uri uris = Uri.parse("android.resource://com.example.itlab.journeymakerjobs/drawable/jmj");
                if (uris != null) {
                    Bitmap bit = decodeUri(uris, 70);
                    image.setImageBitmap(bit);
                }
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
                    Intent intent = new Intent(getBaseContext(), RecentJobRemarks.class);
                    intent.putExtra("salary", "");
                    intent.putExtra("remarks", "");
                    intent.putExtra("name", name);
                    intent.putExtra("jai", "");
                    intent.putExtra("ji", ji);
                    startActivity(intent);
                }
            });

            LinearLayout r6 = (LinearLayout) convertView.findViewById(R.id.r6);
            r6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            return convertView;
        }
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
