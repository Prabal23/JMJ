package com.jmj.app.journeymakerjobs;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class SavedJobDetails extends AppCompatActivity {
    Typeface fontAwesomeFont;
    TextView post, save, edit, del, title;
    String name, SetServerString, id;
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
        setContentView(R.layout.recent_job_details);

        db = new DatabaseHandler(this);

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

        id = getIntent().getStringExtra("id");
        final String job_id = getIntent().getStringExtra("job_id");
        name = getIntent().getStringExtra("name");
        post.setText(Html.fromHtml(name));
        final String cn = getIntent().getStringExtra("cn");
        TextView org = (TextView) findViewById(R.id.org);
        org.setText(Html.fromHtml(cn));
        final String d = getIntent().getStringExtra("d");
        TextView resp = (TextView) findViewById(R.id.resp);
        resp.setText(Html.fromHtml(d));
        final String ai = getIntent().getStringExtra("ai");
        TextView addinfo = (TextView) findViewById(R.id.addinfo);
        addinfo.setText(Html.fromHtml(ai));
        final String ed = getIntent().getStringExtra("ed");
        final TextView edu = (TextView) findViewById(R.id.edu);
        edu.setText(Html.fromHtml(ed));
        final String e = getIntent().getStringExtra("e");
        final TextView exp = (TextView) findViewById(R.id.exp);
        exp.setText(Html.fromHtml(e));
        final String lo = getIntent().getStringExtra("lo");
        final TextView loc = (TextView) findViewById(R.id.loc);
        loc.setText(Html.fromHtml(lo));
        final String n = getIntent().getStringExtra("n");
        TextView nature = (TextView) findViewById(R.id.nature);
        nature.setText(Html.fromHtml(n));
        final String gs = getIntent().getStringExtra("gs");
        TextView salary = (TextView) findViewById(R.id.salary);
        salary.setText(Html.fromHtml(gs));
        final String v = getIntent().getStringExtra("v");
        final TextView vac = (TextView) findViewById(R.id.vac);
        vac.setText(Html.fromHtml(v));
        final String ld = getIntent().getStringExtra("ld");
        final TextView date = (TextView) findViewById(R.id.date);
        date.setText(Html.fromHtml(ld));
        final Bitmap myBitmap = (Bitmap) getIntent().getParcelableExtra("img");
        final ImageView pic = (ImageView) findViewById(R.id.pic);
        pic.setImageBitmap(myBitmap);

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SavedJobDetails.this, job_id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), SavedJobRemarks.class);
                intent.putExtra("salary", "");
                intent.putExtra("remarks", "");
                intent.putExtra("name", name);
                intent.putExtra("jai", "");
                intent.putExtra("ji", job_id);
                intent.putExtra("pic", myBitmap);
                startActivity(intent);
            }
        });

        del_job = (LinearLayout) findViewById(R.id.d);
        del_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteJob alert1 = new DeleteJob();
                alert1.showDialog(SavedJobDetails.this, id);
            }
        });
        save_page = (LinearLayout) findViewById(R.id.s);
        save_page.setVisibility(View.GONE);
        edit_job = (LinearLayout) findViewById(R.id.e);
        edit_job.setVisibility(View.GONE);
        del_job.setVisibility(View.VISIBLE);
        title.setText("Saved Job Details");
    }

    public class DeleteJob {

        public void showDialog(AppCompatActivity activity, final String job_apply_id) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.delete_job_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            Button yes = (Button) dialog.findViewById(R.id.yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int i = Integer.parseInt(id);
                    db.deleteContact(i);
                    Intent intent = new Intent(getBaseContext(), SavedJobs.class);
                    startActivity(intent);
                    finish();
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
}
