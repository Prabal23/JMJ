package com.jmj.app.journeymakerjobs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileOptions extends AppCompatActivity {
    Typeface fontAwesomeFont;
    TextView edit, del, name, basa, total;
    String res, res1 = "", id, username, pass, fullname, pic = "0", sign = "", percent;
    CircleImageView img;
    ProgressBar progressBar, total_prog;
    ArrayList<ProductCV> arrayListCV;
    ArrayList<ProductExpertiseTraining> arrayList, arrayList2, arrayList3;
    ArrayList<ProductEducation> arrayList1;
    ArrayList<ProductJobType> arrayList4, arrayList5;
    ArrayList<ProductNotify> arrayList6;
    ArrayList<ProductExtracurricular> arrayList7;
    int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6 = 0, c7 = 0, c8 = 0, c9 = 0, c10 = 0, total_per = 0;
    float p = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_options);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView p1 = (TextView) findViewById(R.id.p1);
        TextView p2 = (TextView) findViewById(R.id.p2);
        TextView p3 = (TextView) findViewById(R.id.p3);
        TextView p4 = (TextView) findViewById(R.id.p4);
        TextView p5 = (TextView) findViewById(R.id.p5);
        TextView p7 = (TextView) findViewById(R.id.p7);
        edit = (TextView) findViewById(R.id.edit);
        del = (TextView) findViewById(R.id.delete);
        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        p1.setTypeface(fontAwesomeFont);
        p2.setTypeface(fontAwesomeFont);
        p3.setTypeface(fontAwesomeFont);
        p4.setTypeface(fontAwesomeFont);
        p5.setTypeface(fontAwesomeFont);
        p7.setTypeface(fontAwesomeFont);
        edit.setTypeface(fontAwesomeFont);
        del.setTypeface(fontAwesomeFont);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        total_prog = (ProgressBar) findViewById(R.id.b);

        LinearLayout l = (LinearLayout) findViewById(R.id.l);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout alert = new Logout();
                alert.showDialog(ProfileOptions.this, "");
            }
        });
        back.setTypeface(fontAwesomeFont);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), JobSeekerHome.class);
                startActivity(intent);
            }
        });

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

        name = (TextView) findViewById(R.id.name);
        name.setText(username);
        basa = (TextView) findViewById(R.id.basa);
        img = (CircleImageView) findViewById(R.id.pic);
        total = (TextView) findViewById(R.id.total);

        percent = getIntent().getStringExtra("percent");
        p = Float.parseFloat(percent);
        total.setText(new DecimalFormat("##.##").format(p) + "%");
        total_prog.setProgress(Math.round(Float.parseFloat(p + "")));

        arrayListCV = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCV().execute(Utils.IP + "direct/asdfbasiasdfac_deasdfastailsdfasdfasdf/12342124?user_id=" + id);

            }
        });

        LinearLayout r1 = (LinearLayout) findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CV.class);
                intent.putExtra("id", id);
                intent.putExtra("page", "0");
                startActivity(intent);
            }
        });
        LinearLayout r2 = (LinearLayout) findViewById(R.id.r2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CVModification.class);
                startActivity(intent);
            }
        });
        LinearLayout r3 = (LinearLayout) findViewById(R.id.r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic();
            }
        });
        LinearLayout r6 = (LinearLayout) findViewById(R.id.r6);
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ChangePassword.class);
                intent.putExtra("id", id);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });
        LinearLayout r7 = (LinearLayout) findViewById(R.id.r7);
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), JobCategory.class);
                startActivity(intent);
            }
        });
        LinearLayout r4 = (LinearLayout) findViewById(R.id.r4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), CVModification.class);
                startActivity(intent);
            }
        });
        final LinearLayout de = (LinearLayout) findViewById(R.id.d);
        //de.setClickable(false);

        SharedPreferences sharedPreferences1 = getSharedPreferences(Utils.SHARED_PREF_PDF, Context.MODE_PRIVATE);

        /*arrayList = new ArrayList<>();
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
        });*/
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
                    }
                    String basano = arrayListCV.get(i).getAddress();
                    if (!basano.equals("")) {
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
                    pic = arrayListCV.get(i).getPhoto();
                    if (!pic.equals("") || !pic.contains("localhost")) {
                        c9 = 100;
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
                    fullname = arrayListCV.get(i).getUser_full_name();
                    name.setText(fullname);
                    String a = arrayListCV.get(i).getAddress();
                    a = a.replaceAll("u000D000A", "\n");
                    basa.setText(a);
                    pic = arrayListCV.get(i).getPhoto();
                    //Toast.makeText(ProfileOptions.this, pic, Toast.LENGTH_LONG).show();
                    if (pic.equals("") || pic.contains("localhost") || pic.equals("http://www.journeymakerjobs.com/images/jobseeker/jobseekerimage/jobseekeroldimage")) {
                        c9 = 0;
                        img.setImageDrawable(getResources().getDrawable(R.drawable.member_icon));
                    } else {
                        c9 = 100;
                        Picasso.with(ProfileOptions.this).load(pic).into(img);
                    }
                    progressBar.setVisibility(View.GONE);
                    sign = arrayListCV.get(i).getSignature();
                    //Toast.makeText(ProfileOptions.this, sign, Toast.LENGTH_LONG).show();
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
                    c3++;
                    if (c3 != 0) {
                        c3 = 100;
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
                    }
                }
                total_per = c1 + c2 + c3 + c4 + c5 + c6 + c7 + c8 + c9 + c10;
                Float per = (float) total_per;
                per = per / 10;
                total.setText(new DecimalFormat("##.##").format(per) + "%");
                total_prog.setProgress(Math.round(Float.parseFloat(per + "")));
                //Toast.makeText(JobSeekerHome.this, "" + total_per, Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void pic() {
        if (pic.equals("0")) {
            Toast.makeText(this, "Please wait until image and signature is loading", Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();
            //Toast.makeText(this, sign, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), PicSignChange.class);
            intent.putExtra("id", id);
            intent.putExtra("pic", pic);
            intent.putExtra("sign", sign);
            startActivity(intent);
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
                    Intent intent = new Intent(ProfileOptions.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }

}
