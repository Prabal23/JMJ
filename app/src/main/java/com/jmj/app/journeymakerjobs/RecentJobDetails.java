package com.jmj.app.journeymakerjobs;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RecentJobDetails extends AppCompatActivity {
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

        String grp = getIntent().getStringExtra("gp");
        String page = getIntent().getStringExtra("page");
        name = getIntent().getStringExtra("name");
        //post.setText(name);
        post.setText(Html.fromHtml(name));
        final String cn = getIntent().getStringExtra("cn");
        TextView org = (TextView) findViewById(R.id.org);
        org.setText(cn);
        org.setText(Html.fromHtml(cn));
        final String ji = getIntent().getStringExtra("ji");
        //Toast.makeText(this, ji, Toast.LENGTH_SHORT).show();
        final String d = getIntent().getStringExtra("d");
        TextView resp = (TextView) findViewById(R.id.resp);
        resp.setText(d);
        resp.setText(Html.fromHtml(d));
        final String ai = getIntent().getStringExtra("ai");
        TextView addinfo = (TextView) findViewById(R.id.addinfo);
        addinfo.setText(ai);
        addinfo.setText(Html.fromHtml(ai));
        final String ed = getIntent().getStringExtra("ed");
        final TextView edu = (TextView) findViewById(R.id.edu);
        edu.setText(ed);
        edu.setText(Html.fromHtml(ed));
        final String e = getIntent().getStringExtra("e");
        final TextView exp = (TextView) findViewById(R.id.exp);
        exp.setText(e);
        exp.setText(Html.fromHtml(e));
        final String lo = getIntent().getStringExtra("lo");
        final TextView loc = (TextView) findViewById(R.id.loc);
        loc.setText(lo);
        loc.setText(Html.fromHtml(lo));
        final String n = getIntent().getStringExtra("n");
        TextView nature = (TextView) findViewById(R.id.nature);
        nature.setText(n);
        nature.setText(Html.fromHtml(n));
        final String gs = getIntent().getStringExtra("gs");
        TextView salary = (TextView) findViewById(R.id.salary);
        salary.setText(gs);
        salary.setText(Html.fromHtml(gs));
        final String v = getIntent().getStringExtra("v");
        final TextView vac = (TextView) findViewById(R.id.vac);
        vac.setText(v);
        vac.setText(Html.fromHtml(v));
        final String ld = getIntent().getStringExtra("ld");
        final TextView date = (TextView) findViewById(R.id.date);
        date.setText(ld);
        date.setText(Html.fromHtml(ld));
        final String s = getIntent().getStringExtra("s");
        final String r = getIntent().getStringExtra("r");
        final String jai = getIntent().getStringExtra("jai");
        final String img = getIntent().getStringExtra("img");

        Uri uris = null;
        final ImageView pic = (ImageView) findViewById(R.id.pic);
        if (!img.equals("") && !img.contains("localhost")) {
            //Picasso.with(this).load(img).into(pic);
            Picasso.get()
                    .load(img)
                    .into(new Target() {

                        @Override
                        public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                            // Set it in the ImageView
                            pic.setImageBitmap(bitmap);
                            bit = bitmap;
                        }

                        @Override
                        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }

                    });
        }else {
            uris = Uri.parse("android.resource://com.jmj.app.journeymakerjobs/drawable/jobs_icon");
            if (uris != null) {
                bit = decodeUri(uris, 100);
            }
        }

        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), RecentJobRemarks.class);
                intent.putExtra("salary", "");
                intent.putExtra("remarks", "");
                intent.putExtra("name", name);
                intent.putExtra("jai", "");
                intent.putExtra("ji", ji);
                intent.putExtra("pic", img);
                startActivity(intent);
            }
        });

        save_page = (LinearLayout) findViewById(R.id.s);
        save_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photo = profileImage(bit);
                db.addContacts(new Contact(ji, name, cn, d, ai, n, lo, ed, e, gs, v, ld, photo));
                Toast.makeText(getApplicationContext(), "Job Saved Successfully", Toast.LENGTH_LONG).show();
            }
        });

        edit_job = (LinearLayout) findViewById(R.id.e);
        edit_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecentJobDetails.this, RecentJobRemarks.class);
                intent.putExtra("salary", s);
                intent.putExtra("remarks", r);
                intent.putExtra("name", name);
                intent.putExtra("jai", jai);
                intent.putExtra("ji", ji);
                intent.putExtra("pic", img);
                startActivity(intent);
            }
        });
        del_job = (LinearLayout) findViewById(R.id.d);
        del_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteJob alert1 = new DeleteJob();
                alert1.showDialog(RecentJobDetails.this, jai);
            }
        });

        if (page.equals("1")) {
            r5.setVisibility(View.GONE);
            save_page.setVisibility(View.GONE);
            edit_job.setVisibility(View.VISIBLE);
            del_job.setVisibility(View.VISIBLE);
            title.setText("Applied Job Details");
        } else {
            r5.setVisibility(View.VISIBLE);
            save_page.setVisibility(View.VISIBLE);
            edit_job.setVisibility(View.GONE);
            del_job.setVisibility(View.GONE);
            title.setText("Recent Job Details");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private byte[] profileImage(Bitmap b) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 0, bos);
        return bos.toByteArray();
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
                    Client = new DefaultHttpClient();

                    String URL = Utils.IP + "direct/applasdfasdfieasdfd_jaasdfsdfasdfob_deletasadsfdfasdfe/64341424?job_apply_id=" + job_apply_id;
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

    public void insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(RecentJobDetails.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), RecentJobsApply.class);
            startActivity(intent);
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
