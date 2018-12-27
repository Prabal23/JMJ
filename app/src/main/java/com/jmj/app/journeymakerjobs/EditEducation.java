package com.jmj.app.journeymakerjobs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class EditEducation extends AppCompatActivity {
    Typeface fontAwesomeFont;
    String id, edu_id, deg_title, ins, res, sub, sem, pass;
    String degid, major, usern, name, id1 = "", gender = "", marriage = "", religion = "", bd, user, fulln, basa, mail, con, altcon, baba, ma, lingo, bia, dhormo, bdid, cobj, facebook;
    TextInputLayout username, fullname, father, mother, mob, altmob, email, nid, address, obj, fbid;
    EditText un, fn, fathername, mothername, phn, altphn, em, natid, add, objective, fb;
    TextView birth;
    int counting;
    TextView nodata, count, degreeid;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList;
    ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cv_academic);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        baba = getIntent().getStringExtra("sem");
        ma = getIntent().getStringExtra("pass");
        id = getIntent().getStringExtra("id");
        user = getIntent().getStringExtra("user_edu_id");
        degid = getIntent().getStringExtra("degree_id");
        usern = getIntent().getStringExtra("degree_title");
        usern = usern.replaceAll("u000D000A", "<br/>");
        fulln = getIntent().getStringExtra("ins");
        fulln = fulln.replaceAll("u000D000A", "<br/>");
        con = getIntent().getStringExtra("res");
        con = con.replaceAll("u000D000A", "<br/>");
        altcon = getIntent().getStringExtra("degree");
        altcon = altcon.replaceAll("u000D000A", "<br/>");
        major = getIntent().getStringExtra("major");
        major = major.replaceAll("u000D000A", "<br/>");

        TextView apply = (TextView) findViewById(R.id.apply);
        TextView down = (TextView) findViewById(R.id.down);
        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);
        down.setTypeface(fontAwesomeFont);
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

        username = (TextInputLayout) findViewById(R.id.input_username);
        fullname = (TextInputLayout) findViewById(R.id.input_name);
        father = (TextInputLayout) findViewById(R.id.input_father_name);
        mother = (TextInputLayout) findViewById(R.id.input_mother_name);
        mob = (TextInputLayout) findViewById(R.id.input_phone);
        altmob = (TextInputLayout) findViewById(R.id.input_altphone);

        un = (EditText) findViewById(R.id.sign_username);
        un.setText(Html.fromHtml(usern));
        fn = (EditText) findViewById(R.id.sign_name);
        fn.setText(Html.fromHtml(fulln));
        fathername = (EditText) findViewById(R.id.sign_father_name);
        fathername.setText(Html.fromHtml(con));
        mothername = (EditText) findViewById(R.id.sign_mother_name);
        mothername.setText(Html.fromHtml(major));
        phn = (EditText) findViewById(R.id.sign_phone);
        phn.setText(Html.fromHtml(baba));
        altphn = (EditText) findViewById(R.id.sign_altphone);
        altphn.setText(Html.fromHtml(ma));

        birth = (TextView) findViewById(R.id.birthid);
        birth.setText(altcon);
        LinearLayout birth_linear = (LinearLayout) findViewById(R.id.l4);
        birth_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobType alert = new JobType();
                alert.showDialog(EditEducation.this, "");
            }
        });

        LinearLayout editclick = (LinearLayout) findViewById(R.id.r5);
        editclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    public class JobType {
        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.job_type_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            nodata = (TextView) dialog.findViewById(R.id.nodata);
            count = (TextView) dialog.findViewById(R.id.count);
            gifImageView = (GifImageView) dialog.findViewById(R.id.loader);
            gifImageView.setVisibility(View.VISIBLE);

            listView = (ListView) dialog.findViewById(R.id.listView);
            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute(Utils.IP + "direct/deasdfgreasdfase_lissdfasdt/12342124");
                }
            });

            //Toast.makeText(JobSeekerReg.this, Utils.IP + "direct/deasdfgreasdfase_lissdfasdt/12342124", Toast.LENGTH_SHORT).show();

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    id1 = arrayList.get(i).getId();
                    name = arrayList.get(i).getName();
                    birth.setText(name);
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

    class ReadJSON extends AsyncTask<String, Integer, String> {

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
                JSONArray jsonArray = jsonObject.getJSONArray("degree_list");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductJobType(
                            productObject.getString("degree_id"),
                            productObject.getString("degree_name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nodata.setVisibility(View.VISIBLE);
            }
            final CustomListAdapterJobType adapter = new CustomListAdapterJobType(
                    getApplicationContext(), R.layout.jobtype_list_item, arrayList
            );
            listView.setAdapter(adapter);
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

    public void submit() {
        usern = un.getText().toString();
        if (usern.contains(" ")) {
            usern = usern.replace(" ", "%20");
        }
        usern = usern.replaceAll("\\r?\\n", "\\u000D\\000A");
        fulln = fn.getText().toString();
        if (fulln.contains(" ")) {
            fulln = fulln.replace(" ", "%20");
        }
        fulln = fulln.replaceAll("\\r?\\n", "\\u000D\\000A");
        con = phn.getText().toString();
        if (con.contains(" ")) {
            con = con.replace(" ", "%20");
        }
        con = con.replaceAll("\\r?\\n", "\\u000D\\000A");
        altcon = altphn.getText().toString();
        if (altcon.contains(" ")) {
            altcon = altcon.replace(" ", "%20");
        }
        altcon = altcon.replaceAll("\\r?\\n", "\\u000D\\000A");
        baba = fathername.getText().toString();
        if (baba.contains(" ")) {
            baba = baba.replace(" ", "%20");
        }
        baba = baba.replaceAll("\\r?\\n", "\\u000D\\000A");
        ma = mothername.getText().toString();
        if (ma.contains(" ")) {
            ma = ma.replace(" ", "%20");
        }
        ma = ma.replaceAll("\\r?\\n", "\\u000D\\000A");

        HttpClient Client = new DefaultHttpClient();

        String URL = Utils.IP + "direct/updfasddatsdfe_educatadsfasdfional_insdfasdffdsfasdo/12342124?user_id=" + id + "&user_education_id=" + user + "&degree_id=" + degid + "&degree_title=" + usern + "&institution=" + fulln + "&result=" + baba + "&major=" + ma + "&semester=" + con + "&pass_year=" + altcon;
        //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
        String SetServerString = "";

        HttpGet httpget = new HttpGet(URL);
        HttpResponse response = null;
        try {
            response = Client.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String res = EntityUtils.toString(httpEntity);
            //Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            if (res.contains("Success")) {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    SetServerString = Client.execute(httpget, responseHandler);
                    Toast.makeText(EditEducation.this, "Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), CV.class);
                    intent.putExtra("page", "0");
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
