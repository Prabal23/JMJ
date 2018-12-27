package com.jmj.app.journeymakerjobs;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class EditExperience extends AppCompatActivity {
    Typeface fontAwesomeFont;
    String edu_id, deg_title, ins, res, sub, sem, pass, comcom, desdes, resres;
    String uid, id, cn, des, resp, str, end;
    TextInputLayout com, desig, father, mother, respo;
    EditText com1, desig1, fathername, mothername, respo1;
    TextView birth, start, ending;
    int counting;
    TextView nodata, count, degreeid;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList;
    ListView listView;
    int mYear, mMonth, mDay, cnt = 0;
    CheckBox checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_cv_experience);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        uid = getIntent().getStringExtra("uid");
        id = getIntent().getStringExtra("id");
        cn = getIntent().getStringExtra("cn");
        resp = getIntent().getStringExtra("resp");
        resp = resp.replaceAll("u000D000A", "<br/>");
        str = getIntent().getStringExtra("str");
        end = getIntent().getStringExtra("end");
        des = getIntent().getStringExtra("des");

        TextView apply = (TextView) findViewById(R.id.apply);
        TextView down = (TextView) findViewById(R.id.down);
        TextView down1 = (TextView) findViewById(R.id.down1);
        TextView home = (TextView) findViewById(R.id.home);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView back = (TextView) findViewById(R.id.back);
        home.setTypeface(fontAwesomeFont);
        logout.setTypeface(fontAwesomeFont);
        back.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);
        down.setTypeface(fontAwesomeFont);
        down1.setTypeface(fontAwesomeFont);
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

        com = (TextInputLayout) findViewById(R.id.input_com);
        desig = (TextInputLayout) findViewById(R.id.input_des);
        respo = (TextInputLayout) findViewById(R.id.input_res);

        com1 = (EditText) findViewById(R.id.sign_com);
        desig1 = (EditText) findViewById(R.id.sign_des);
        respo1 = (EditText) findViewById(R.id.sign_res);

        com1.setText(cn);
        desig1.setText(des);
        respo1.setText(Html.fromHtml(resp));

        start = (TextView) findViewById(R.id.str);
        start.setText("Job Start : "+str);
        LinearLayout str_linear = (LinearLayout) findViewById(R.id.l3);
        str_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditExperience.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                start.setText("Job Start : " + year + "-" + day + "-" + day);
                                str = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        ending = (TextView) findViewById(R.id.end);
        //ending.setText(end);
        final LinearLayout end_linear = (LinearLayout) findViewById(R.id.l4);
        end_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditExperience.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                ending.setText("Job End : " + year + "-" + day + "-" + day);
                                end = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        checkBox = (CheckBox) findViewById(R.id.check);
        if (end.equals("0000-00-00")) {
            checkBox.setChecked(true);
            end_linear.setClickable(false);
            ending.setText("Job End");
            ending.setPaintFlags(ending.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            cnt++;
        } else {
            checkBox.setChecked(false);
            end_linear.setClickable(true);
            ending.setText("Job End : "+end);
            ending.setPaintFlags(ending.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                if (((CheckBox) view).isChecked()) {
                    end = "0000-00-00";
                    end_linear.setClickable(false);
                    ending.setText("Job End");
                    ending.setPaintFlags(ending.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    end_linear.setClickable(true);
                    ending.setPaintFlags(ending.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        TextView cont = (TextView) findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                if (cnt % 2 != 0) {
                    end = "0000-00-00";
                    checkBox.setChecked(true);
                    end_linear.setClickable(false);
                    ending.setText("Job End");
                    ending.setPaintFlags(ending.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    checkBox.setChecked(false);
                    end_linear.setClickable(true);
                    ending.setPaintFlags(ending.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
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

    public void submit() {
        comcom = com1.getText().toString();
        if (comcom.contains(" ")) {
            comcom = comcom.replace(" ", "%20");
        }
        comcom = comcom.replaceAll("\\r?\\n", "\\u000D\\000A");
        desdes = desig1.getText().toString();
        if (desdes.contains(" ")) {
            desdes = desdes.replace(" ", "%20");
        }
        desdes = desdes.replaceAll("\\r?\\n", "\\u000D\\000A");
        resres = respo1.getText().toString();
        if (resres.contains(" ")) {
            resres = resres.replace(" ", "%20");
        }
        if (resres.contains("\'")) {
            resres = resres.replace("\'", "'");
            //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        }
        if (resres.contains("\\")) {
            resres = resres.replace("\\", "");
            //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
        }
        resres = resres.replaceAll("\\r?\\n", "\\u000D\\000A");

        HttpClient Client = new DefaultHttpClient();

        String URL = Utils.IP + "direct/emasdfploysdfmsdfent_updaasdftefadsfadsf/12342124?user_id=" + uid + "&user_work_id=" + id + "&company_name=" + comcom + "&designation=" + desdes + "&responsibility=" + resres + "&job_start=" + str + "&job_end=" + end;
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
                    Toast.makeText(EditExperience.this, "Success", Toast.LENGTH_LONG).show();
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
