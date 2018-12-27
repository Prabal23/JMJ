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
import android.text.Html;
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
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EmployerProfile extends AppCompatActivity {
    Typeface fontAwesomeFont;
    TextView com, type, phn, email, add, web, name, des;
    String res, res1 = "", id, username, pass, fullname, pic = "", sign, a, n, t, m, em, ad, w, un;
    ArrayList<ProductEmpProfile> arrayListCV;
    CircleImageView img;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_profile);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerHome.class);
                startActivity(intent);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        LinearLayout l = (LinearLayout) findViewById(R.id.l);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout alert = new Logout();
                alert.showDialog(EmployerProfile.this, "");
            }
        });
        back.setTypeface(fontAwesomeFont);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView edit = (TextView) findViewById(R.id.edit);
        TextView pix = (TextView) findViewById(R.id.pix);
        TextView password = (TextView) findViewById(R.id.pass);
        edit.setTypeface(fontAwesomeFont);
        pix.setTypeface(fontAwesomeFont);
        password.setTypeface(fontAwesomeFont);

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF_EMP)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF_EMP, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        pass = result[1];
        //Toast.makeText(this, pass, Toast.LENGTH_SHORT).show();
        username = result[2];

        name = (TextView) findViewById(R.id.name);
        name.setText(username);
        des = (TextView) findViewById(R.id.des);
        com = (TextView) findViewById(R.id.com);
        type = (TextView) findViewById(R.id.type);
        phn = (TextView) findViewById(R.id.mob);
        email = (TextView) findViewById(R.id.email);
        add = (TextView) findViewById(R.id.address);
        web = (TextView) findViewById(R.id.web);
        img = (CircleImageView) findViewById(R.id.pic);

        arrayListCV = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCV().execute(Utils.IP + "direct/psdfrfasdfofile_asdfasdviadsfadsew/12342124?user_id=" + id);

            }
        });

        LinearLayout e = (LinearLayout) findViewById(R.id.e);
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        LinearLayout ps = (LinearLayout) findViewById(R.id.ps);
        ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ChangePasswordEmp.class);
                intent.putExtra("id", id);
                intent.putExtra("pass", pass);
                startActivity(intent);
            }
        });

        LinearLayout p = (LinearLayout) findViewById(R.id.p);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pic();
            }
        });
    }

    class ReadJSONCV extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("personal_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayListCV.add(new ProductEmpProfile(
                            productObject.getString("photo_url"),
                            productObject.getString("username"),
                            productObject.getString("user_full_name"),
                            productObject.getString("email"),
                            productObject.getString("company_id"),
                            productObject.getString("company_name"),
                            productObject.getString("company_type"),
                            productObject.getString("company_address"),
                            productObject.getString("mobile"),
                            productObject.getString("concern_person"),
                            productObject.getString("concern_designation"),
                            productObject.getString("website")
                    ));

                    un = arrayListCV.get(i).getUsername();
                    fullname = arrayListCV.get(i).getCom_name();
                    name.setText(fullname);
                    a = arrayListCV.get(i).getCom_type();
                    des.setText(a);
                    pic = arrayListCV.get(i).getPhoto();
                    //Toast.makeText(ProfileOptions.this, pic, Toast.LENGTH_LONG).show();
                    if (!pic.equals("") && !pic.contains("localhost")) {
                        Picasso.with(EmployerProfile.this).load(pic).into(img);
                    }
                    n = arrayListCV.get(i).getPerson();
                    com.setText(n);
                    t = arrayListCV.get(i).getDesignation();
                    t = t.replaceAll("u000D000A", "<br/>");
                    type.setText(Html.fromHtml(t));
                    m = arrayListCV.get(i).getMobile();
                    phn.setText(m);
                    em = arrayListCV.get(i).getEmail();
                    email.setText(em);
                    ad = arrayListCV.get(i).getAddress();
                    ad = ad.replaceAll("u000D000A", "<br/>");
                    if (ad.equals("null")) {
                        add.setText("");
                    } else {
                        add.setText(Html.fromHtml(ad));
                    }
                    //add.setText(ad);
                    w = arrayListCV.get(i).getWebsite();
                    if (w.equals("null")) {
                        web.setText("");
                    } else {
                        web.setText(w);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void editProfile(){
        Intent intent = new Intent(getBaseContext(), EmployerEdit.class);
        intent.putExtra("cn",fullname);
        intent.putExtra("t",a);
        intent.putExtra("p",n);
        intent.putExtra("d",t);
        intent.putExtra("m",m);
        intent.putExtra("em",em);
        intent.putExtra("ad",ad);
        intent.putExtra("w",w);
        intent.putExtra("un",un);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void pic() {
        if (pic.equals("")) {
            Toast.makeText(this, "Please wait until image is loading", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getBaseContext(), PicChange.class);
            intent.putExtra("id", id);
            intent.putExtra("pic", pic);
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
                    SharedPreferences preferences = getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);
                    //Getting editor
                    SharedPreferences.Editor editor = preferences.edit();

                    //Puting the value false for loggedin
                    editor.putBoolean(Utils.LOGGEDIN_SHARED_PREF_EMP, false);

                    //Putting blank value to email
                    editor.putString(Utils.LOGIN_SHARED_PREF_EMP, "");

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
                    Intent intent = new Intent(EmployerProfile.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
