package com.jmj.app.journeymakerjobs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class SavedJobs extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView lv;
    private SwipeRefreshLayout swipe;
    private String grp, name, s, r, imgs, ji, jt, ci, d, ai, n, e, lo, ed, gs, v, ld, cn, jai, SetServerString;
    private ProgressBar progres;
    private ImageView error;
    private TextView alert, erroralert, count;
    ArrayList<ProductwithImage> arrayList;
    private DatabaseHandler db;
    private byte[] photo;
    Bitmap bp;
    private Contact dataModel;
    private dataAdapter data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saved_jobs);

        db = new DatabaseHandler(this);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        lv = (ListView) findViewById(R.id.listView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        progres = (ProgressBar) findViewById(R.id.progressBar);
        progres.setVisibility(View.VISIBLE);
        alert = (TextView) findViewById(R.id.alert);
        count = (TextView) findViewById(R.id.count);
        //alert.setVisibility(View.VISIBLE);

        error = (ImageView) findViewById(R.id.error);
        erroralert = (TextView) findViewById(R.id.erroralert);

        ShowRecords();

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

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        error.setVisibility(View.GONE);
                        erroralert.setVisibility(View.GONE);
                        swipe.setRefreshing(true);
                        swipe.setEnabled(true);
                        progres.setVisibility(View.VISIBLE);
                        //alert.setVisibility(View.VISIBLE);
                        arrayList = new ArrayList<>();
                        lv = (ListView) findViewById(R.id.listView);

                        ShowRecords();

                        if (swipe.isRefreshing()) {
                            swipe.setRefreshing(false);
                        } else {
                            swipe.setRefreshing(true);
                        }
                    }
                }
        );
    }

    private void ShowRecords() {
        final ArrayList<Contact> contacts = new ArrayList<>(db.getAllContacts());
        data = new dataAdapter(this, contacts);
        progres.setVisibility(View.GONE);
        alert.setVisibility(View.GONE);
        lv.setAdapter(data);
        int tot = lv.getAdapter().getCount();
        String total = String.valueOf(tot);
        count.setText(total);
        AnimationSet set = new AnimationSet(true);
        Animation animation = AnimationUtils.loadAnimation(SavedJobs.this, R.anim.alpha1);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);
        lv.setLayoutAnimation(controller);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                dataModel = contacts.get(position);
                int idd = dataModel.getID();
                String id1 = String.valueOf(idd);
                String job_id = dataModel.getJob_id();
                String title = dataModel.getTitle();
                String com = dataModel.getCompany();
                String des = dataModel.getDescription();
                String ai = dataModel.getAdd_info();
                String nat = dataModel.getNature();
                String loc = dataModel.getLocation();
                String edu = dataModel.getEducation();
                String exp = dataModel.getExperience();
                String sal = dataModel.getSalary();
                String vac = dataModel.getVacancy();
                String ld = dataModel.getLast_date();
                String img = "";
                byte[] pic = dataModel.getImage();
                int l = pic.length;
                String imglength = String.valueOf(l);
                String imageString = Base64.encodeToString(pic, Base64.DEFAULT);
                //Toast.makeText(MyDonor.this, l+"", Toast.LENGTH_LONG).show();
                Bitmap bitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                Intent intent = new Intent(SavedJobs.this, SavedJobDetails.class);
                intent.putExtra("id", id1);
                intent.putExtra("job_id", job_id);
                intent.putExtra("name", title);
                intent.putExtra("cn", com);
                intent.putExtra("d", des);
                intent.putExtra("ai", ai);
                intent.putExtra("n", nat);
                intent.putExtra("lo", loc);
                intent.putExtra("ed", edu);
                intent.putExtra("e", exp);
                intent.putExtra("gs", sal);
                intent.putExtra("v", vac);
                intent.putExtra("ld", ld);
                intent.putExtra("img", bitmap);
                intent.putExtra("imglength", imglength);
                startActivity(intent);
            }
        });
    }

}
