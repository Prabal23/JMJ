package com.jmj.app.journeymakerjobs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class EmployerHome extends AppCompatActivity {
    Typeface fontAwesomeFont;
    private ProgressDialog pDialog;
    int downloadedSize = 0, totalsize;
    float per = 0;
    String res, id = "", pass = "", username = "";
    TextView title, total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_home);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        Calendar calendar = Calendar.getInstance();
        int yearInt = calendar.get(Calendar.YEAR);
        TextView yearText = (TextView) findViewById(R.id.year);
        yearText.setText(" " + yearInt);

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
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout alert = new Logout();
                alert.showDialog(EmployerHome.this, "");
            }
        });

        web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Webpage.class);
                intent.putExtra("page", "2");
                startActivity(intent);
            }
        });

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

        LinearLayout r1 = (LinearLayout) findViewById(R.id.r1);
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerProfile.class);
                startActivity(intent);
            }
        });

        LinearLayout r2 = (LinearLayout) findViewById(R.id.r2);
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerJobPost.class);
                startActivity(intent);
            }
        });

        LinearLayout r3 = (LinearLayout) findViewById(R.id.r3);
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerRunningJobs.class);
                startActivity(intent);
            }
        });

        LinearLayout r4 = (LinearLayout) findViewById(R.id.r4);
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), EmployerAllJobs.class);
                startActivity(intent);
            }
        });

        title = (TextView) findViewById(R.id.title);
    }

    public class Logout {

        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.logout);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

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

                    //Putting the value false for loggedin
                    editor1.putBoolean(Utils.PDF_SHARED_PREF, false);

                    //Putting blank value to email
                    editor1.putString(Utils.OK_SHARED_PREF, "");

                    //Saving the sharedpreferences
                    editor1.commit();

                    //Starting login activity
                    Intent intent = new Intent(EmployerHome.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    }
}
