package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

public class ApplicantList extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String imgs, ui, un, phn, sal, job_id, name;
    private ProgressBar progres;
    private ImageView error;
    private TextView alert, erroralert, count, post_name;
    ArrayList<ProductApplicant> arrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applicant_listing);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        job_id = getIntent().getStringExtra("job_id");
        name = getIntent().getStringExtra("name");

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        count = (TextView) findViewById(R.id.count);
        post_name = (TextView) findViewById(R.id.post_name);
        post_name.setText(name);
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
                new ReadJSON().execute(Utils.IP + "direct/appliasdfsdfcant_listsdfadsfasdf/12342124?job_id=" + job_id);
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
                                new ReadJSON().execute(Utils.IP + "direct/appliasdfsdfcant_listsdfadsfasdf/12342124?job_id=" + job_id);
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
                JSONArray jsonArray = jsonObject.getJSONArray("applicant_list");
                /*int c = jsonArray.length();
                Toast.makeText(RecentJobs.this, "" + c, Toast.LENGTH_SHORT).show();*/
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductApplicant(
                            productObject.getString("user_id"),
                            productObject.getString("user_full_name"),
                            productObject.getString("mobile"),
                            productObject.getString("expected_salary"),
                            productObject.getString("photo_url")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                error.setVisibility(View.VISIBLE);
                erroralert.setVisibility(View.VISIBLE);
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.layout.applicant_lists, arrayList
            );
            lv.setAdapter(adapter);
            int tot = lv.getAdapter().getCount();
            String total = String.valueOf(tot);
            //total = total.replace("0", "০").replace("1", "১").replace("2", "২").replace("3", "৩").replace("4", "৪").replace("5", "৫").replace("6", "৬").replace("7", "৭").replace("8", "৮").replace("9", "৯");
            count.setText(total);
            AnimationSet set = new AnimationSet(true);
            Animation animation = AnimationUtils.loadAnimation(ApplicantList.this, R.anim.alpha1);
            set.addAnimation(animation);
            LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
            lv.setLayoutAnimation(controller);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    ui = arrayList.get(i).getId();
                    un = arrayList.get(i).getName();
                    phn = arrayList.get(i).getPhone();
                    sal = arrayList.get(i).getSalary();
                    imgs = arrayList.get(i).getPhoto();

                    Intent intent = new Intent(getBaseContext(), CV.class);
                    intent.putExtra("id", ui);
                    intent.putExtra("page", "1");
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

    public class CustomListAdapter extends ArrayAdapter<ProductApplicant> {

        ArrayList<ProductApplicant> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;
        Bitmap bit;

        public CustomListAdapter(Context context, int resource, ArrayList<ProductApplicant> products) {
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
                convertView = layoutInflater.inflate(R.layout.applicant_lists, null, true);

            }
            final ProductApplicant product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            final ImageView image = (ImageView) convertView.findViewById(R.id.p1);
            String img = product.getPhoto();
            //Toast.makeText(ApplicantList.this, img, Toast.LENGTH_LONG).show();
            if (!img.equals("") && !img.contains("localhost")) {
                Picasso.get().load(img).into(image);
            }else{
                image.setImageResource(R.drawable.member_icon);
            }

            try {
                TextView post = (TextView) convertView.findViewById(R.id.name);
                String address = product.getName();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                post.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView org = (TextView) convertView.findViewById(R.id.phone);
                String address = product.getPhone();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                org.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView last_date = (TextView) convertView.findViewById(R.id.salary);
                String address = product.getSalary();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                last_date.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

}
