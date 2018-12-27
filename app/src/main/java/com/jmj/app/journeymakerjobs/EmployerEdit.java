package com.jmj.app.journeymakerjobs;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
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

import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class EmployerEdit extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView listView;
    TextView nodata, count, degreeid;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList;
    String id = "", name, result, SetServerString;
    TextInputLayout iComName, iComType, iName, iDesig, iMobile, iEmail, iWeb, iUser, iAddress;
    EditText sComName, sComType, sName, sDesig, sMobile, sEmail, sWeb, sUser, sAddress;
    private HttpClient Client;
    HttpGet httpget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_edit);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        String cn = getIntent().getStringExtra("cn");
        String t = getIntent().getStringExtra("t");
        String p = getIntent().getStringExtra("p");
        String d = getIntent().getStringExtra("d");
        d = d.replaceAll("u000D000A", "<br/>");
        String m = getIntent().getStringExtra("m");
        String em = getIntent().getStringExtra("em");
        String ad = getIntent().getStringExtra("ad");
        ad = ad.replaceAll("u000D000A", "<br/>");
        String w = getIntent().getStringExtra("w");
        String un = getIntent().getStringExtra("un");
        id = getIntent().getStringExtra("id");

        TextView namepic = (TextView) findViewById(R.id.namepic);
        namepic.setTypeface(fontAwesomeFont);
        TextView mobpic = (TextView) findViewById(R.id.mobpic);
        mobpic.setTypeface(fontAwesomeFont);
        TextView degtitlepic = (TextView) findViewById(R.id.degtitlepic);
        degtitlepic.setTypeface(fontAwesomeFont);
        TextView inspic = (TextView) findViewById(R.id.inspic);
        inspic.setTypeface(fontAwesomeFont);
        TextView respic = (TextView) findViewById(R.id.respic);
        respic.setTypeface(fontAwesomeFont);
        TextView passyearpic = (TextView) findViewById(R.id.passyearpic);
        passyearpic.setTypeface(fontAwesomeFont);
        TextView addpic = (TextView) findViewById(R.id.addpic);
        addpic.setTypeface(fontAwesomeFont);
        TextView webpic = (TextView) findViewById(R.id.webpic);
        webpic.setTypeface(fontAwesomeFont);
        TextView userpic = (TextView) findViewById(R.id.unpic);
        userpic.setTypeface(fontAwesomeFont);

        iComName = (TextInputLayout) findViewById(R.id.input_comname);
        iComType = (TextInputLayout) findViewById(R.id.input_type);
        iName = (TextInputLayout) findViewById(R.id.input_name);
        iDesig = (TextInputLayout) findViewById(R.id.input_des);
        iMobile = (TextInputLayout) findViewById(R.id.input_mob);
        iEmail = (TextInputLayout) findViewById(R.id.input_email);
        iAddress = (TextInputLayout) findViewById(R.id.input_add);
        iWeb = (TextInputLayout) findViewById(R.id.input_web);
        iUser = (TextInputLayout) findViewById(R.id.input_user);

        sComName = (EditText) findViewById(R.id.sign_comname);
        sComName.setText(cn);
        sComType = (EditText) findViewById(R.id.sign_type);
        sComType.setText(t);
        sName = (EditText) findViewById(R.id.sign_name);
        sName.setText(p);
        sDesig = (EditText) findViewById(R.id.sign_des);
        sDesig.setText(Html.fromHtml(d));
        sMobile = (EditText) findViewById(R.id.sign_mob);
        sMobile.setText(m);
        sEmail = (EditText) findViewById(R.id.sign_email);
        sEmail.setText(em);
        sAddress = (EditText) findViewById(R.id.sign_add);
        sAddress.setText(Html.fromHtml(ad));
        sWeb = (EditText) findViewById(R.id.sign_web);
        sWeb.setText(w);
        sUser = (EditText) findViewById(R.id.sign_user);
        sUser.setText(un);

        TextView apply = (TextView) findViewById(R.id.apply);
        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);
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

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegister();
            }
        });
    }

    public void UserRegister() {
        String cn = sComName.getText().toString();
        cn = cn.replace(" ", "%20");
        String ct = sComType.getText().toString();
        ct = ct.replace(" ", "%20");
        String n = sName.getText().toString();
        n = n.replace(" ", "%20");
        String d = sDesig.getText().toString();
        d = d.replace(" ", "%20");
        d = d.replaceAll("\\r?\\n", "\\u000D\\000A");
        String m = sMobile.getText().toString();
        m = m.replace(" ", "%20");
        String em = sEmail.getText().toString();
        em = em.replace(" ", "%20");
        String a = sAddress.getText().toString();
        a = a.replace(" ", "%20");
        a = a.replaceAll("\\r?\\n", "\\u000D\\000A");
        String w = sWeb.getText().toString();
        w = w.replace(" ", "%20");
        String un = sUser.getText().toString();
        un = un.replace(" ", "%20");

        Client = new DefaultHttpClient();

        String URL = Utils.IP + "direct/empsdfasdloyer_pasdfrofiasdfle_updasdfte/12342124?user_id=" + id + "&company_name=" + cn + "&company_type=" + ct + "&concern_person=" + n + "&concern_designation=" + d + "&mobile=" + m + "&email=" + em + "&company_address=" + a + "&website=" + w + "&user_name=" + un;
        //Toast.makeText(getBaseContext(), URL, Toast.LENGTH_LONG).show();
        SetServerString = "";

        httpget = new HttpGet(URL);
        HttpResponse response = null;
        try {
            response = Client.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String res = EntityUtils.toString(httpEntity);
            //Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            if (res.contains("Success")) {
                insert();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Toast.makeText(this, "lsfjklk", Toast.LENGTH_SHORT).show();
    }

    public void insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(EmployerEdit.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), EmployerProfile.class);
            startActivity(intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
