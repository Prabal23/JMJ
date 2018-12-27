package com.jmj.app.journeymakerjobs;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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

public class EditTraining extends AppCompatActivity {
    Typeface fontAwesomeFont;
    String edu_id, deg_title, res, sub, sem, pass, comcom, desdes, resres;
    String uid, id, title, desig, location, inst, duration, tittit, insins, locloc, durdur, detdet;
    TextInputLayout tit, ins, loc, dur, det;
    EditText tit1, ins1, loc1, dur1, det1;
    TextView birth, start, ending;
    int counting;
    TextView nodata, count, degreeid;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList;
    ListView listView;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cv_training);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        uid = getIntent().getStringExtra("uid");
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        location = getIntent().getStringExtra("loc");
        location = location.replaceAll("u000D000A", "<br/>");
        duration = getIntent().getStringExtra("dur");
        inst = getIntent().getStringExtra("ins");
        desig = getIntent().getStringExtra("des");
        desig = desig.replaceAll("u000D000A", "<br/>");

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

        tit = (TextInputLayout) findViewById(R.id.input_tit);
        ins = (TextInputLayout) findViewById(R.id.input_ins);
        loc = (TextInputLayout) findViewById(R.id.input_loc);
        dur = (TextInputLayout) findViewById(R.id.input_dur);
        det = (TextInputLayout) findViewById(R.id.input_det);

        tit1 = (EditText) findViewById(R.id.sign_tit);
        ins1 = (EditText) findViewById(R.id.sign_ins);
        loc1 = (EditText) findViewById(R.id.sign_loc);
        dur1 = (EditText) findViewById(R.id.sign_dur);
        det1 = (EditText) findViewById(R.id.sign_det);

        tit1.setText(title);
        ins1.setText(inst);
        loc1.setText(Html.fromHtml(location));
        dur1.setText(duration);
        det1.setText(Html.fromHtml(desig));

        LinearLayout editclick = (LinearLayout) findViewById(R.id.r5);
        editclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    public void submit() {
        tittit = tit1.getText().toString();
        if (tittit.contains(" ")) {
            tittit = tittit.replace(" ", "%20");
        }
        tittit = tittit.replaceAll("\\r?\\n", "\\u000D\\000A");
        insins = ins1.getText().toString();
        if (insins.contains(" ")) {
            insins = insins.replace(" ", "%20");
        }
        insins = insins.replaceAll("\\r?\\n", "\\u000D\\000A");
        locloc = loc1.getText().toString();
        if (locloc.contains(" ")) {
            locloc = locloc.replace(" ", "%20");
        }
        locloc = locloc.replaceAll("\\r?\\n", "\\u000D\\000A");
        durdur = dur1.getText().toString();
        if (durdur.contains(" ")) {
            durdur = durdur.replace(" ", "%20");
        }
        durdur = durdur.replaceAll("\\r?\\n", "\\u000D\\000A");
        detdet = det1.getText().toString();
        if (detdet.contains(" ")) {
            detdet = detdet.replace(" ", "%20");
        }
        if (detdet.contains("\'")) {
            detdet = detdet.replace("\'", "'");
        }
        if (detdet.contains("\\")) {
            detdet = detdet.replace("\\", "");
        }
        detdet = detdet.replaceAll("\\r?\\n", "\\u000D\\000A");

        HttpClient Client = new DefaultHttpClient();

        String URL = Utils.IP + "direct/trasdfinddfghfing_updateasdfadgfhsdfas/12342124?user_id=" + uid + "&user_training_id=" + id + "&training_title=" + tittit + "&training_institution=" + insins + "&training_topic_details=" + detdet + "&location=" + locloc + "&duration=" + durdur;
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
                    Toast.makeText(EditTraining.this, "Success", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), CV.class);
                    intent.putExtra("page", "0");
                    intent.putExtra("id", uid);
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
