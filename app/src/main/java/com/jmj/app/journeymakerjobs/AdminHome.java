package com.jmj.app.journeymakerjobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {
    Typeface fontAwesomeFont;
    private ProgressDialog pDialog;
    int downloadedSize = 0, totalsize;
    float per = 0;
    String res, id = "", pass = "", username = "";
    TextView title, total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_home);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView p1 = (TextView) findViewById(R.id.p1);
        TextView p2 = (TextView) findViewById(R.id.p2);
        TextView p3 = (TextView) findViewById(R.id.p3);
        TextView p4 = (TextView) findViewById(R.id.p4);
        TextView web = (TextView) findViewById(R.id.web);
        TextView logout = (TextView) findViewById(R.id.logout);
        p1.setTypeface(fontAwesomeFont);
        p2.setTypeface(fontAwesomeFont);
        p3.setTypeface(fontAwesomeFont);
        p4.setTypeface(fontAwesomeFont);
        web.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Webpage.class);
                startActivity(intent);
            }
        });

        LinearLayout r1 = (LinearLayout) findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LinearLayout r2 = (LinearLayout) findViewById(R.id.r2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerSearch.class);
                startActivity(intent);
            }
        });

        LinearLayout r3 = (LinearLayout) findViewById(R.id.r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        LinearLayout r4 = (LinearLayout) findViewById(R.id.r4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        title = (TextView) findViewById(R.id.title);
    }
}
