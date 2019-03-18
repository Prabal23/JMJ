package com.jmj.app.journeymakerjobs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class EmployeeJobDetails extends AppCompatActivity {
    Typeface fontAwesomeFont;
    TextView post, save, edit, del, title;
    String name, SetServerString;
    LinearLayout save_page, edit_job, del_job;
    private HttpClient Client;
    HttpGet httpget;
    Bitmap bit;
    private DatabaseHandler db;
    private byte[] photo;
    Bitmap bp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_job_details);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        TextView apply = (TextView) findViewById(R.id.apply);
        post = (TextView) findViewById(R.id.post);
        save = (TextView) findViewById(R.id.save);
        edit = (TextView) findViewById(R.id.edit);
        del = (TextView) findViewById(R.id.delete);
        title = (TextView) findViewById(R.id.title);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);
        save.setTypeface(fontAwesomeFont);
        edit.setTypeface(fontAwesomeFont);
        del.setTypeface(fontAwesomeFont);
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

        String grp = getIntent().getStringExtra("gp");
        final String page = getIntent().getStringExtra("page");
        name = getIntent().getStringExtra("name");
        name = name.replace("</li>", "</li><br>");
        post.setText(name);
        post.setText(Html.fromHtml(name));
        String cn = getIntent().getStringExtra("cn");
        cn = cn.replaceAll("</li>", "</li><br>");
        TextView org = (TextView) findViewById(R.id.org);
        org.setText(cn);
        org.setText(Html.fromHtml(cn));
        final String ji = getIntent().getStringExtra("ji");
        String d = getIntent().getStringExtra("d");
        d = d.replaceAll("</li>", "</li><br>");
        TextView resp = (TextView) findViewById(R.id.resp);
        resp.setText(d);
        resp.setText(Html.fromHtml(d));
        String ai = getIntent().getStringExtra("ai");
        ai = ai.replaceAll("</li>", "</li><br>");
        TextView addinfo = (TextView) findViewById(R.id.addinfo);
        addinfo.setText(ai);
        addinfo.setText(Html.fromHtml(ai));
        String ed = getIntent().getStringExtra("ed");
        ed = ed.replaceAll("</li>", "</li><br>");
        final TextView edu = (TextView) findViewById(R.id.edu);
        edu.setText(ed);
        edu.setText(Html.fromHtml(ed));
        String e = getIntent().getStringExtra("e");
        e = e.replaceAll("</li>", "</li><br/>");
        final TextView exp = (TextView) findViewById(R.id.exp);
        exp.setText(e);
        exp.setText(Html.fromHtml(e));
        String lo = getIntent().getStringExtra("lo");
        lo = lo.replaceAll("</li>", "</li><br>");
        final TextView loc = (TextView) findViewById(R.id.loc);
        loc.setText(lo);
        loc.setText(Html.fromHtml(lo));
        String n = getIntent().getStringExtra("n");
        n = n.replaceAll("</li>", "</li><br>");
        TextView nature = (TextView) findViewById(R.id.nature);
        nature.setText(n);
        nature.setText(Html.fromHtml(n));
        String gs = getIntent().getStringExtra("gs");
        gs = gs.replaceAll("</li>", "</li><br>");
        TextView salary = (TextView) findViewById(R.id.salary);
        salary.setText(gs);
        salary.setText(Html.fromHtml(gs));
        String v = getIntent().getStringExtra("v");
        v = v.replaceAll("</li>", "</li><br>");
        final TextView vac = (TextView) findViewById(R.id.vac);
        vac.setText(v);
        vac.setText(Html.fromHtml(v));
        String ld = getIntent().getStringExtra("ld");
        ld = ld.replaceAll("</li>", "</li><br>");
        final TextView date = (TextView) findViewById(R.id.date);
        date.setText(ld);
        date.setText(Html.fromHtml(ld));
        final String s = getIntent().getStringExtra("s");
        final String r = getIntent().getStringExtra("r");
        final String jai = getIntent().getStringExtra("jai");
        final String img = getIntent().getStringExtra("img");

        final ImageView pic = (ImageView) findViewById(R.id.pic);
        if (!img.equals("") && !img.contains("localhost")) {
            Picasso.get().load(img).into(pic);
        }

        final String dist = getIntent().getStringExtra("dist");
        final String status = getIntent().getStringExtra("status");
        final String publish = getIntent().getStringExtra("pub");

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);

        edit_job = (LinearLayout) findViewById(R.id.e);
        final String finalCn = cn;
        final String finalD = d;
        final String finalAi = ai;
        final String finalN = n;
        final String finalE = e;
        final String finalEd = ed;
        final String finalLo = lo;
        final String finalGs = gs;
        final String finalV = v;
        final String finalLd = ld;
        edit_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeeJobDetails.this, EmployerJobEdit.class);
                intent.putExtra("name", name);
                intent.putExtra("cn", finalCn);
                intent.putExtra("ji", ji);
                intent.putExtra("d", finalD);
                intent.putExtra("ai", finalAi);
                intent.putExtra("n", finalN);
                intent.putExtra("e", finalE);
                intent.putExtra("ed", finalEd);
                intent.putExtra("lo", finalLo);
                intent.putExtra("gs", finalGs);
                intent.putExtra("v", finalV);
                intent.putExtra("ld", finalLd);
                intent.putExtra("img", img);
                intent.putExtra("dist", dist);
                intent.putExtra("status", status);
                intent.putExtra("pub", publish);
                intent.putExtra("page", page);
                startActivity(intent);
            }
        });

        del_job = (LinearLayout) findViewById(R.id.d);
        del_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ApplicantList.class);
                intent.putExtra("job_id", ji);
                intent.putExtra("name", name);
                startActivity(intent);
            }
        });

        if (page.equals("1")) {
            r5.setVisibility(View.GONE);
            title.setText("All Job Details");
        } else {
            r5.setVisibility(View.GONE);
            title.setText("Running Job Details");
        }
    }
}
