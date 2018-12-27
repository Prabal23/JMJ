package com.jmj.app.journeymakerjobs;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class CV extends AppCompatActivity implements View.OnClickListener {
    Typeface fontAwesomeFont;
    ListView lv, lv1, lv2, lv3, lv4, lv5, lv6;
    ArrayList<ProductCV> arrayListCV;
    ArrayList<ProductExpertiseTraining> arrayList, arrayList2, arrayList3;
    ArrayList<ProductEducation> arrayList1;
    ArrayList<ProductJobType> arrayList4, arrayList5;
    ArrayList<ProductExtracurricular> arrayList6;
    String id, page, fullname, basa, mail, father, mother, birth, gen, obj, marriage, rel, nid, mob, mob2, fbid, jobs, ref;
    TextView name, mailing, thikana, father_name, mother_name, jonmo, gender, objective, married, religion, natid, phn, phn2, facebook, myjobs, reference;
    TextView emp, emp1;
    LinearLayout statuses, pdf_linear, p, extra;
    ScrollView sc;
    ImageView img, signature;
    public static int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save;
    Bitmap bitmap;
    ProgressDialog progressDialog;
    String res, res1 = "", username, pdf_ok = "", sign, pic;
    private ProgressBar progres;
    int cnt = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cv);

        id = getIntent().getStringExtra("id");
        page = getIntent().getStringExtra("page");
        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        if (page.equals("0")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

            if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
                res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
            }
            String[] result = res.split(" ");
            username = result[2];

            SharedPreferences sharedPreferences1 = getSharedPreferences(Utils.SHARED_PREF_PDF, Context.MODE_PRIVATE);

            if (sharedPreferences1.contains(Utils.OK_SHARED_PREF)) {
                res1 = (sharedPreferences).getString(Utils.OK_SHARED_PREF, "");
            }
            if (!sharedPreferences1.contains(Utils.OK_SHARED_PREF)) {
                res1 = "";
            }
            //Toast.makeText(this, res1, Toast.LENGTH_SHORT).show();
        }
        if (page.equals("1")) {
            SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);

            if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF_EMP)) {
                res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF_EMP, "");
            }
            String[] result = res.split(" ");
            username = result[2];
        }

        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);

        TextView homepic = (TextView) findViewById(R.id.homepic);
        TextView emailpic = (TextView) findViewById(R.id.emailpic);
        TextView phonepic = (TextView) findViewById(R.id.phonepic);
        TextView edit = (TextView) findViewById(R.id.edit);
        TextView pdf = (TextView) findViewById(R.id.pdf);

        homepic.setTypeface(fontAwesomeFont);
        emailpic.setTypeface(fontAwesomeFont);
        phonepic.setTypeface(fontAwesomeFont);
        edit.setTypeface(fontAwesomeFont);
        pdf.setTypeface(fontAwesomeFont);

        name = (TextView) findViewById(R.id.name);
        thikana = (TextView) findViewById(R.id.basa);
        mailing = (TextView) findViewById(R.id.email);
        phn = (TextView) findViewById(R.id.phone);
        objective = (TextView) findViewById(R.id.obj);
        father_name = (TextView) findViewById(R.id.baba);
        mother_name = (TextView) findViewById(R.id.ma);
        jonmo = (TextView) findViewById(R.id.jonmodin);
        gender = (TextView) findViewById(R.id.gender);
        married = (TextView) findViewById(R.id.married);
        religion = (TextView) findViewById(R.id.religion);
        natid = (TextView) findViewById(R.id.nid);
        facebook = (TextView) findViewById(R.id.fb);

        emp = (TextView) findViewById(R.id.list_empty);
        emp1 = (TextView) findViewById(R.id.list_empty1);
        img = (ImageView) findViewById(R.id.pic);
        signature = (ImageView) findViewById(R.id.signature);

        statuses = (LinearLayout) findViewById(R.id.statuses);
        sc = (ScrollView) findViewById(R.id.sc);
        sc.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (sc != null) {
                    if (sc.getChildAt(0).getBottom() <= (sc.getHeight() + sc.getScrollY())) {
                        statuses.setVisibility(View.VISIBLE);
                    } else {
                        statuses.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        extra = (LinearLayout) findViewById(R.id.extra);

        init();
        fn_permission();

        LinearLayout editing = (LinearLayout) findViewById(R.id.e);
        editing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CVModification.class);
                startActivity(intent);
            }
        });
        if (page.equals("1")) {
            editing.setVisibility(View.GONE);
        }

        lv = (ListView) findViewById(R.id.listView);
        lv1 = (ListView) findViewById(R.id.listView1);
        lv2 = (ListView) findViewById(R.id.listView2);
        lv3 = (ListView) findViewById(R.id.listView3);
        lv4 = (ListView) findViewById(R.id.listView4);
        lv5 = (ListView) findViewById(R.id.listView5);
        lv6 = (ListView) findViewById(R.id.listView6);

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
        arrayList3 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON3().execute(Utils.IP + "direct/trasfasdfsdfining_desdftasdfailsdfs/12342124?user_id=" + id);
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
                new ReadJSON6().execute(Utils.IP + "direct/extracurasdfricular_detaasdfiasdfls_allasdfas/12342124?user_id=" + id);
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

        if (page.equals("0")) {
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
        }
        if (page.equals("1")) {
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

                    fullname = arrayListCV.get(i).getUser_full_name();
                    name.setText(fullname);
                    basa = arrayListCV.get(i).getAddress();
                    basa = basa.replaceAll("u000D000A", "\n");
                    thikana.setText(basa);
                    mail = arrayListCV.get(i).getEmail();
                    mailing.setText(mail);
                    mob = arrayListCV.get(i).getMobile();
                    mob2 = arrayListCV.get(i).getAlt_mobile();
                    if (mob2.equals("null") || mob2.equals("")) {
                        phn.setText(mob);
                    } else {
                        phn.setText(mob + "/" + mob2);
                    }
                    father = arrayListCV.get(i).getFather_name();
                    father_name.setText(father);
                    mother = arrayListCV.get(i).getMother_name();
                    mother_name.setText(mother);
                    birth = arrayListCV.get(i).getBirth_date();
                    jonmo.setText(birth);
                    gen = arrayListCV.get(i).getGender();
                    gender.setText(gen);
                    marriage = arrayListCV.get(i).getMarital_status();
                    married.setText(marriage);
                    rel = arrayListCV.get(i).getReligion();
                    religion.setText(rel);
                    nid = arrayListCV.get(i).getNid();
                    natid.setText(nid);
                    fbid = arrayListCV.get(i).getFb();
                    facebook.setText(fbid);
                    obj = arrayListCV.get(i).getObjective();
                    obj = obj.replaceAll("u000D000A", "\n");
                    objective.setText(Html.fromHtml(obj));
                    pic = arrayListCV.get(i).getPhoto();
                    //Toast.makeText(CV.this, pic, Toast.LENGTH_LONG).show();
                    if (!pic.equals("") && !pic.contains("localhost")) {
                        Picasso.with(CV.this).load(pic).into(img);
                    }
                    sign = arrayListCV.get(i).getSignature();
                    if (!sign.equals("") && !sign.contains("localhost")) {
                        Picasso.with(CV.this).load(sign).into(signature);
                    }
                }
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterExp adapter = new CustomListAdapterExp(
                    getApplicationContext(), R.layout.cv_exp_lists, arrayList
            );
            lv1.setAdapter(adapter);
            ListViewUtil.setListViewHeightBasedOnChildren(lv1);
            lv1.setEmptyView(findViewById(R.id.list_empty1));
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterEdu adapter = new CustomListAdapterEdu(
                    getApplicationContext(), R.layout.cv_edu_lists, arrayList1
            );
            lv.setAdapter(adapter);
            ListViewUtil1.setListViewHeightBasedOnChildren1(lv);
            lv.setEmptyView(findViewById(R.id.list_empty));
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterSkill adapter = new CustomListAdapterSkill(
                    getApplicationContext(), R.layout.cv_training_lists, arrayList2
            );
            lv2.setAdapter(adapter);
            ListViewUtil2.setListViewHeightBasedOnChildren2(lv2);
            lv2.setEmptyView(findViewById(R.id.list_empty2));
        }
    }

    class ReadJSON3 extends AsyncTask<String, Integer, String> {

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
                    arrayList3.add(new ProductExpertiseTraining(
                            productObject.getString("user_training_id"),
                            productObject.getString("training_title"),
                            productObject.getString("topic_details"),
                            productObject.getString("institution"),
                            productObject.getString("location"),
                            productObject.getString("duration")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterSkill adapter = new CustomListAdapterSkill(
                    getApplicationContext(), R.layout.cv_training_lists, arrayList3
            );
            lv3.setAdapter(adapter);
            ListViewUtil3.setListViewHeightBasedOnChildren3(lv3);
            lv3.setEmptyView(findViewById(R.id.list_empty3));
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterAchievement adapter = new CustomListAdapterAchievement(
                    getApplicationContext(), R.layout.cv_skill_tra_ach_ref_lists, arrayList4
            );
            lv4.setAdapter(adapter);
            ListViewUtil4.setListViewHeightBasedOnChildren4(lv4);
            lv4.setEmptyView(findViewById(R.id.list_empty4));
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
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterReference adapter = new CustomListAdapterReference(
                    getApplicationContext(), R.layout.cv_skill_tra_ach_ref_lists, arrayList5
            );
            lv5.setAdapter(adapter);
            ListViewUtil4.setListViewHeightBasedOnChildren4(lv5);
            lv5.setEmptyView(findViewById(R.id.list_empty5));
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
                progres.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("extracurricular_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList6.add(new ProductExtracurricular(
                            productObject.getString("extracurricular_id"),
                            productObject.getString("extracurricular_organization"),
                            productObject.getString("extracurricular_position"),
                            productObject.getString("extracurricular_duration"),
                            productObject.getString("extracurricular_details_all")
                    ));
                    //cnt++;
                }
                p.setClickable(true);
                listener();
                //Toast.makeText(CV.this, "" + cnt, Toast.LENGTH_SHORT).show();
                /*if (cnt != 0) {
                    extra.setVisibility(View.VISIBLE);
                } else {
                    extra.setVisibility(View.GONE);
                }*/
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterExtra adapter = new CustomListAdapterExtra(
                    getApplicationContext(), R.layout.cv_extracurricular_lists, arrayList6
            );
            lv6.setAdapter(adapter);
            ListViewUtil6.setListViewHeightBasedOnChildren6(lv6);
            lv6.setEmptyView(findViewById(R.id.list_empty6));
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

    public class CustomListAdapterExp extends ArrayAdapter<ProductExpertiseTraining> {

        ArrayList<ProductExpertiseTraining> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterExp(Context context, int resource, ArrayList<ProductExpertiseTraining> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_exp_lists, null, true);

            }
            ProductExpertiseTraining product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            TextView down = (TextView) convertView.findViewById(R.id.downpic);
            down.setTypeface(fontAwesomeFont);

            try {
                TextView date = (TextView) convertView.findViewById(R.id.date);
                TextView date1 = (TextView) convertView.findViewById(R.id.date1);
                String address = product.getJob_str();
                String address1 = product.getJob_end();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");

                byte[] b2 = address1.getBytes("UTF-8");
                String add1 = new String(b2, "UTF-8");
                date.setText(add);
                if(add1.equals("0000-00-00")){
                    date1.setText("Continuing");
                }else{
                    date1.setText(add1);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView org = (TextView) convertView.findViewById(R.id.org);
                String address = product.getCom_name();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                org.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView des = (TextView) convertView.findViewById(R.id.des);
                String address = product.getDes();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                des.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView resp = (TextView) convertView.findViewById(R.id.resp);
                String address = product.getResponse();
                address = address.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                resp.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class CustomListAdapterEdu extends ArrayAdapter<ProductEducation> {

        ArrayList<ProductEducation> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterEdu(Context context, int resource, ArrayList<ProductEducation> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_edu_lists, null, true);

            }
            ProductEducation product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            try {
                TextView pass_year = (TextView) convertView.findViewById(R.id.pass_year);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                String address = product.getPass_year();
                address = address.replaceAll("u000D000A", "\n");
                String address1 = product.getSemester();
                address1 = address1.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                byte[] b2 = address1.getBytes("UTF-8");
                String add1 = new String(b2, "UTF-8");
                pass_year.setText(add);
                title.setText("Passing Year");
                if (add.equals("null") || add.equals("")) {
                    pass_year.setText(add1);
                    title.setText("Currently in");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView degree = (TextView) convertView.findViewById(R.id.degree);
                String address = product.getDegree_title();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                degree.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView ins = (TextView) convertView.findViewById(R.id.ins);
                String address = product.getInstitution();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                ins.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView cgpa = (TextView) convertView.findViewById(R.id.cgpa);
                String address = product.getResult();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                String major = product.getMajor();
                major = major.replaceAll("u000D000A", "\n");
                cgpa.setText(add);
                if (!major.equals("")) {
                    cgpa.setText(add + " (" + major + ")");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class CustomListAdapterSkill extends ArrayAdapter<ProductExpertiseTraining> {

        ArrayList<ProductExpertiseTraining> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterSkill(Context context, int resource, ArrayList<ProductExpertiseTraining> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_training_lists, null, true);

            }
            ProductExpertiseTraining product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            try {
                TextView title = (TextView) convertView.findViewById(R.id.title);
                TextView ins = (TextView) convertView.findViewById(R.id.ins);
                TextView loc = (TextView) convertView.findViewById(R.id.loc);
                TextView dur = (TextView) convertView.findViewById(R.id.dur);
                TextView des = (TextView) convertView.findViewById(R.id.des);
                String address = product.getCom_name();
                address = address.replaceAll("u000D000A", "\n");
                String address1 = product.getDes();
                address1 = address1.replaceAll("u000D000A", "\n");
                String address2 = product.getJob_str();
                address2 = address2.replaceAll("u000D000A", "\n");
                String address3 = product.getJob_end();
                address3 = address3.replaceAll("u000D000A", "\n");
                String address4 = product.getResponse();
                address4 = address4.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                title.setText(add);
                des.setText(address1);
                ins.setText(address2);
                loc.setText(address3);
                dur.setText(Html.fromHtml(address4));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class CustomListAdapterAchievement extends ArrayAdapter<ProductJobType> {

        ArrayList<ProductJobType> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterAchievement(Context context, int resource, ArrayList<ProductJobType> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_skill_tra_ach_ref_lists, null, true);

            }
            ProductJobType product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            try {
                TextView text = (TextView) convertView.findViewById(R.id.text);
                String address = product.getName();
                address = address.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                text.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class CustomListAdapterReference extends ArrayAdapter<ProductJobType> {

        ArrayList<ProductJobType> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterReference(Context context, int resource, ArrayList<ProductJobType> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_skill_tra_ach_ref_lists, null, true);

            }
            ProductJobType product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            try {
                TextView text = (TextView) convertView.findViewById(R.id.text);
                String address = product.getName();
                address = address.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                text.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public class CustomListAdapterExtra extends ArrayAdapter<ProductExtracurricular> {

        ArrayList<ProductExtracurricular> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterExtra(Context context, int resource, ArrayList<ProductExtracurricular> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_extracurricular_lists, null, true);

            }
            ProductExtracurricular product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            try {
                TextView org = (TextView) convertView.findViewById(R.id.org);
                String address = product.getExtracurricular_organization();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                org.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView pos = (TextView) convertView.findViewById(R.id.pos);
                String address = product.getExtracurricular_position();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                pos.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView dur = (TextView) convertView.findViewById(R.id.dur);
                String address = product.getExtracurricular_duration();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                dur.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView det = (TextView) convertView.findViewById(R.id.det);
                String address = product.getExtracurricular_details_all();
                address = address.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                det.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public static class ListViewUtil {
        private static final String CNAME = ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren(ListView listView) {
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

    public static class ListViewUtil1 {
        private static final String CNAME = ListViewUtil.class.getSimpleName();

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
        private static final String CNAME = ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren2(ListView listView) {
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

    public static class ListViewUtil3 {
        private static final String CNAME = ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren3(ListView listView) {
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

    public static class ListViewUtil4 {
        private static final String CNAME = ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren4(ListView listView) {
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

    public static class ListViewUtil6 {
        private static final String CNAME = ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren6(ListView listView) {
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

    private void init() {
        pdf_linear = (LinearLayout) findViewById(R.id.pdf_linear);
        p = (LinearLayout) findViewById(R.id.p);
        p.setClickable(false);
    }

    private void listener() {
        p.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.p:
                if (boolean_save) {

                } else {
                    if (boolean_permission) {
                        progres.setVisibility(View.VISIBLE);
                        bitmap = loadBitmapFromView(pdf_linear, pdf_linear.getWidth(), pdf_linear.getHeight());
                        createPdf();

                        progres.setVisibility(View.GONE);

                        String targetPdf = "/sdcard/" + fullname + " CV.pdf";
                        Snackbar snackbar = Snackbar
                                .make(pdf_linear, "Saved to - " + targetPdf, Snackbar.LENGTH_LONG);
                                /*.setAction("View", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (boolean_save) {
                                            Intent intent = new Intent(getBaseContext(), CVPDFViewActivity.class);
                                            intent.putExtra("name", fullname);
                                            startActivity(intent);
                                        }
                                    }
                                });*/

                        snackbar.setActionTextColor(Color.RED);

                        View sbView = snackbar.getView();
                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackbar.show();

                    } else {

                    }

                    //createPdf();
                    break;
                }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels;
        float width = displaymetrics.widthPixels;

        int convertHighet = (int) hight, convertWidth = (int) width;

//        Resources mResources = getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        //PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 2).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);

        // write the document content
        String targetPdf = "/sdcard/" + fullname + " CV.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            boolean_save = true;
            SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_PDF, Context.MODE_PRIVATE);
            //Creating editor to store values to shared preferences
            SharedPreferences.Editor editor = sharedPreferences.edit();

            //Adding values to editor
            editor.putString(Utils.OK_SHARED_PREF, "1");
            editor.putBoolean(Utils.PDF_SHARED_PREF, true);
            //editor.putString(Config.NAME_SHARED_PREF, email);

            //Saving values to editor
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
        //Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    private void fn_permission() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CV.this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(CV.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }

            if ((ActivityCompat.shouldShowRequestPermissionRationale(CV.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            } else {
                ActivityCompat.requestPermissions(CV.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);

            }
        } else {
            boolean_permission = true;


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                boolean_permission = true;


            } else {
                Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();

            }
        }
    }
}
