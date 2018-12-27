package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class JobSeekerReg extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView listView, listView1;
    TextView nodata, count, degreeid, district, birthid, jobstartid, jobendid;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList, arrayList1;
    String id = "", name, result, SetServerString, disid, dis, birth_date, start_date, end_date, gender_id = "0", marriage_id = "0", religion_id = "0", terms = "0";
    TextInputLayout iName, iFatherName, iMotherName, iMobile, iDegree, iOrg, iResult, iUsername, iPassyear, iPass, iConPass, iEmail, iAddress, iCompany, iDesig;
    EditText sName, sFatherName, sMotherName, sMobile, sDegree, sOrg, sResult, sUsername, sPassyear, sPass, sConPass, sEmail, sAddress, sCompany, sDesig;
    private HttpClient Client;
    HttpGet httpget;
    int mYear, mMonth, mDay, count1, count2, cnt = 0, tnc = 0;
    RadioGroup radioGender, radioMarriage, radioReligion;
    RadioButton radioGenderButton, radioMarriageButton, radioReligionButton;
    CheckBox checkBox, checkBox1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jobseeker_reg);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView namepic = (TextView) findViewById(R.id.namepic);
        namepic.setTypeface(fontAwesomeFont);
        TextView mobpic = (TextView) findViewById(R.id.mobpic);
        mobpic.setTypeface(fontAwesomeFont);
        TextView userpic = (TextView) findViewById(R.id.userpic);
        userpic.setTypeface(fontAwesomeFont);
        TextView passpic = (TextView) findViewById(R.id.passpic);
        passpic.setTypeface(fontAwesomeFont);
        TextView conpasspic = (TextView) findViewById(R.id.conpasspic);
        conpasspic.setTypeface(fontAwesomeFont);
        TextView degidpic = (TextView) findViewById(R.id.degidpic);
        degidpic.setTypeface(fontAwesomeFont);
        TextView degtitlepic = (TextView) findViewById(R.id.degtitlepic);
        degtitlepic.setTypeface(fontAwesomeFont);
        TextView inspic = (TextView) findViewById(R.id.inspic);
        inspic.setTypeface(fontAwesomeFont);
        TextView respic = (TextView) findViewById(R.id.respic);
        respic.setTypeface(fontAwesomeFont);
        TextView passyearpic = (TextView) findViewById(R.id.passyearpic);
        passyearpic.setTypeface(fontAwesomeFont);
        TextView birthpic = (TextView) findViewById(R.id.birthpic);
        birthpic.setTypeface(fontAwesomeFont);
        TextView genderpic = (TextView) findViewById(R.id.genderpic);
        genderpic.setTypeface(fontAwesomeFont);
        TextView emailpic = (TextView) findViewById(R.id.emailpic);
        emailpic.setTypeface(fontAwesomeFont);
        TextView addpic = (TextView) findViewById(R.id.addpic);
        addpic.setTypeface(fontAwesomeFont);
        TextView compic = (TextView) findViewById(R.id.compic);
        compic.setTypeface(fontAwesomeFont);
        TextView despic = (TextView) findViewById(R.id.despic);
        despic.setTypeface(fontAwesomeFont);
        TextView startpic = (TextView) findViewById(R.id.jobstartpic);
        startpic.setTypeface(fontAwesomeFont);
        TextView endpic = (TextView) findViewById(R.id.jobendpic);
        endpic.setTypeface(fontAwesomeFont);
        TextView distpic = (TextView) findViewById(R.id.distpic);
        distpic.setTypeface(fontAwesomeFont);
        TextView down = (TextView) findViewById(R.id.down);
        down.setTypeface(fontAwesomeFont);
        TextView down1 = (TextView) findViewById(R.id.down1);
        down1.setTypeface(fontAwesomeFont);
        TextView down2 = (TextView) findViewById(R.id.down2);
        down2.setTypeface(fontAwesomeFont);
        TextView down3 = (TextView) findViewById(R.id.down3);
        down3.setTypeface(fontAwesomeFont);
        TextView down4 = (TextView) findViewById(R.id.down4);
        down4.setTypeface(fontAwesomeFont);
        final TextView viewpass = (TextView) findViewById(R.id.viewpass);
        viewpass.setTypeface(fontAwesomeFont);
        final TextView viewpass1 = (TextView) findViewById(R.id.viewpass1);
        viewpass1.setTypeface(fontAwesomeFont);

        degreeid = (TextView) findViewById(R.id.degid);
        birthid = (TextView) findViewById(R.id.birthid);
        jobstartid = (TextView) findViewById(R.id.jobstartid);
        jobendid = (TextView) findViewById(R.id.jobendid);
        district = (TextView) findViewById(R.id.distid);

        iName = (TextInputLayout) findViewById(R.id.input_name);
        iFatherName = (TextInputLayout) findViewById(R.id.input_father_name);
        iMotherName = (TextInputLayout) findViewById(R.id.input_mother_name);
        iMobile = (TextInputLayout) findViewById(R.id.input_phone);
        iDegree = (TextInputLayout) findViewById(R.id.input_degtitle);
        iOrg = (TextInputLayout) findViewById(R.id.input_ins);
        iResult = (TextInputLayout) findViewById(R.id.input_res);
        iUsername = (TextInputLayout) findViewById(R.id.input_user);
        iPassyear = (TextInputLayout) findViewById(R.id.input_passyear);
        iPass = (TextInputLayout) findViewById(R.id.input_pass);
        iConPass = (TextInputLayout) findViewById(R.id.input_conpass);
        iEmail = (TextInputLayout) findViewById(R.id.input_email);
        iAddress = (TextInputLayout) findViewById(R.id.input_address);
        iCompany = (TextInputLayout) findViewById(R.id.input_company);
        iDesig = (TextInputLayout) findViewById(R.id.input_des);

        sName = (EditText) findViewById(R.id.sign_name);
        sFatherName = (EditText) findViewById(R.id.sign_father_name);
        sMotherName = (EditText) findViewById(R.id.sign_mother_name);
        sMobile = (EditText) findViewById(R.id.sign_phone);
        sDegree = (EditText) findViewById(R.id.sign_degtitle);
        sOrg = (EditText) findViewById(R.id.sign_ins);
        sResult = (EditText) findViewById(R.id.sign_res);
        sUsername = (EditText) findViewById(R.id.sign_user);
        sPassyear = (EditText) findViewById(R.id.sign_passyear);
        sPass = (EditText) findViewById(R.id.sign_pass);
        sConPass = (EditText) findViewById(R.id.sign_conpass);
        sEmail = (EditText) findViewById(R.id.sign_email);
        sAddress = (EditText) findViewById(R.id.sign_address);
        sCompany = (EditText) findViewById(R.id.sign_company);
        sDesig = (EditText) findViewById(R.id.sign_des);

        viewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count1++;
                if (count1 % 2 == 0) {
                    sPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewpass.setText(R.string.eyenot);
                    //Toast.makeText(Login.this, "Even", Toast.LENGTH_SHORT).show();
                } else {
                    sPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewpass.setText(R.string.eye);
                    //Toast.makeText(Login.this, "Odd", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewpass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count2++;
                if (count2 % 2 == 0) {
                    sConPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    viewpass1.setText(R.string.eyenot);
                    //Toast.makeText(Login.this, "Even", Toast.LENGTH_SHORT).show();
                } else {
                    sConPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    viewpass1.setText(R.string.eye);
                    //Toast.makeText(Login.this, "Odd", Toast.LENGTH_SHORT).show();
                }
            }
        });

        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobSeekerReg.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                birthid.setText("Date of Birth : " + year + "-" + d + "-" + day);
                                birth_date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        LinearLayout l14 = (LinearLayout) findViewById(R.id.l14);
        l14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobSeekerReg.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                jobstartid.setText("Job Start : " + year + "-" + d + "-" + day);
                                start_date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        final TextView end = (TextView) findViewById(R.id.jobendid);
        final LinearLayout l15 = (LinearLayout) findViewById(R.id.l15);
        l15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(JobSeekerReg.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                jobendid.setText("Job End : " + year + "-" + d + "-" + day);
                                end_date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        checkBox = (CheckBox) findViewById(R.id.check);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                if (((CheckBox) view).isChecked()) {
                    end_date = "0000-00-00";
                    l15.setClickable(false);
                    end.setPaintFlags(end.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    l15.setClickable(true);
                    end.setPaintFlags(end.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        TextView cont = (TextView) findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                if (cnt % 2 != 0) {
                    end_date = "0000-00-00";
                    checkBox.setChecked(true);
                    l15.setClickable(false);
                    end.setPaintFlags(end.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    checkBox.setChecked(false);
                    l15.setClickable(true);
                    end.setPaintFlags(end.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });

        checkBox1 = (CheckBox) findViewById(R.id.check1);
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tnc++;
                if (((CheckBox) view).isChecked()) {
                    terms = "1";
                    //Toast.makeText(JobSeekerReg.this, "1", Toast.LENGTH_SHORT).show();
                } else {
                    terms = "0";
                    //Toast.makeText(JobSeekerReg.this, "0", Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                       radioGenderButton = (RadioButton) findViewById(checkedId);
                                                       String nature = (String) radioGenderButton.getText();
                                                       if (nature.equals("Male")) {
                                                           gender_id = "Male";
                                                       }
                                                       if (nature.equals("Female")) {
                                                           gender_id = "Female";
                                                       }
                                                       if (nature.equals("Others")) {
                                                           gender_id = "Others";
                                                       }
                                                   }
                                               }
        );

        radioMarriage = (RadioGroup) findViewById(R.id.radioMarital);
        radioMarriage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioMarriageButton = (RadioButton) findViewById(checkedId);
                                                         String nature = (String) radioMarriageButton.getText();
                                                         if (nature.equals("Married")) {
                                                             marriage_id = "Married";
                                                         }
                                                         if (nature.equals("Unmarried")) {
                                                             marriage_id = "Unmarried";
                                                         }
                                                         if (nature.equals("Widowed")) {
                                                             marriage_id = "Widowed";
                                                         }
                                                         if (nature.equals("Separated")) {
                                                             marriage_id = "Separated";
                                                         }
                                                         if (nature.equals("Divorced")) {
                                                             marriage_id = "Divorced";
                                                         }
                                                         if (nature.equals("Single")) {
                                                             marriage_id = "Single";
                                                         }
                                                     }
                                                 }
        );

        radioReligion = (RadioGroup) findViewById(R.id.radioReligion);
        radioReligion.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioReligionButton = (RadioButton) findViewById(checkedId);
                                                         String nature = (String) radioReligionButton.getText();
                                                         if (nature.equals("Islam")) {
                                                             religion_id = "Islam";
                                                         }
                                                         if (nature.equals("Hinduism")) {
                                                             religion_id = "Hinduism";
                                                         }
                                                         if (nature.equals("Buddho")) {
                                                             religion_id = "Buddho";
                                                         }
                                                         if (nature.equals("Christian")) {
                                                             religion_id = "Christian";
                                                         }
                                                     }
                                                 }
        );

        LinearLayout l16 = (LinearLayout) findViewById(R.id.l16);
        l16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                District alert = new District();
                alert.showDialog(JobSeekerReg.this, "");
            }
        });

        LinearLayout l7 = (LinearLayout) findViewById(R.id.l7);
        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobType alert = new JobType();
                alert.showDialog(JobSeekerReg.this, "");
            }
        });

        TextView signin = (TextView) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserRegister();
            }
        });

        TextView terms_n_con = (TextView) findViewById(R.id.terms_n_con);
        terms_n_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), TermsandConditions.class);
                startActivity(intent);
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
                    id = arrayList.get(i).getId();
                    name = arrayList.get(i).getName();
                    degreeid.setText(name);
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

    public class District {
        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.job_type_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText("Select District");
            nodata = (TextView) dialog.findViewById(R.id.nodata);
            count = (TextView) dialog.findViewById(R.id.count);
            gifImageView = (GifImageView) dialog.findViewById(R.id.loader);
            gifImageView.setVisibility(View.VISIBLE);

            listView1 = (ListView) dialog.findViewById(R.id.listView);
            arrayList1 = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON1().execute(Utils.IP + "direct/distriasdfct_lissdftfadsf/12342124");
                }
            });

            //Toast.makeText(JobSeekerReg.this, Utils.IP + "direct/deasdfgreasdfase_lissdfasdt/12342124", Toast.LENGTH_SHORT).show();

            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    disid = arrayList1.get(i).getId();
                    dis = arrayList1.get(i).getName();
                    district.setText(dis);
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

    class ReadJSON1 extends AsyncTask<String, Integer, String> {

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
                JSONArray jsonArray = jsonObject.getJSONArray("district_list");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductJobType(
                            productObject.getString("district_id"),
                            productObject.getString("district_name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nodata.setVisibility(View.VISIBLE);
            }
            final CustomListAdapterDistrict adapter = new CustomListAdapterDistrict(
                    getApplicationContext(), R.layout.jobtype_list_item, arrayList1
            );
            listView1.setAdapter(adapter);
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

    public class CustomListAdapterDistrict extends ArrayAdapter<ProductJobType> {

        ArrayList<ProductJobType> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterDistrict(Context context, int resource, ArrayList<ProductJobType> products) {
            super(context, resource, products);
            this.products = products;
            this.context = context;
            this.resource = resource;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext()
                        .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.jobtype_list_item, null, true);

            }
            ProductJobType product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");

            TextView job = (TextView) convertView.findViewById(R.id.job);
            job.setTypeface(fontAwesomeFont);
            job.setText(getResources().getString(R.string.loc));

            try {
                TextView type = (TextView) convertView.findViewById(R.id.txtName);
                String address = product.getName();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                type.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    public void UserRegister() {
        String n = sName.getText().toString();
        n = n.replace(" ", "%20");
        String fn = sFatherName.getText().toString();
        fn = fn.replace(" ", "%20");
        String mn = sMotherName.getText().toString();
        mn = mn.replace(" ", "%20");
        String em = sEmail.getText().toString();
        em = em.replace(" ", "%20");
        String add = sAddress.getText().toString();
        if (add.contains("\'")) {
            add = add.replace("\'", "'");
        }
        add = add.replace(" ", "%20");
        add = add.replaceAll("\\r?\\n", "\\u000D\\000A");
        String com = sCompany.getText().toString();
        if (com.contains("\'")) {
            com = com.replace("\'", "'");
        }
        com = com.replace(" ", "%20");
        com = com.replaceAll("\\r?\\n", "\\u000D\\000A");
        String desig = sDesig.getText().toString();
        if (desig.contains("\'")) {
            desig = desig.replace("\'", "'");
        }
        desig = desig.replace(" ", "%20");
        desig = desig.replaceAll("\\r?\\n", "\\u000D\\000A");
        String m = sMobile.getText().toString();
        m = m.replace(" ", "%20");
        String d = sDegree.getText().toString();
        if (d.contains("\'")) {
            d = d.replace("\'", "'");
        }
        d = d.replace(" ", "%20");
        String o = sOrg.getText().toString();
        o = o.replace(" ", "%20");
        if (o.contains("\'")) {
            o = o.replace("\'", "'");
        }
        o = o.replaceAll("\\r?\\n", "\\u000D\\000A");
        String r = sResult.getText().toString();
        r = r.replace(" ", "%20");
        String u = sUsername.getText().toString();
        u = u.replace(" ", "%20");
        String py = sPassyear.getText().toString();
        py = py.replace(" ", "%20");
        String p = sPass.getText().toString();
        p = p.replace(" ", "%20");
        String cp = sConPass.getText().toString();
        cp = cp.replace(" ", "%20");
        String js = start_date;
        //Toast.makeText(this, js, Toast.LENGTH_SHORT).show();
        String je = end_date;
        //Toast.makeText(this, je, Toast.LENGTH_SHORT).show();
        String bd = birth_date;
        //Toast.makeText(this, bd, Toast.LENGTH_SHORT).show();
        String gen = gender_id;
        String marriage = marriage_id;
        String religion = religion_id;
        //Toast.makeText(this, gen, Toast.LENGTH_SHORT).show();
        String di = disid;
        String deid = id;
        //char nn = n.charAt(0);
        if (!validateName()) {
            return;
        }
        if (!n.substring(0,1).matches("[a-zA-Z]")) {
            iName.setError("Full Name format is wrong");
            requestFocus(sName);
        }else{
            iName.setErrorEnabled(false);
        }
        if (!validateFatherName()) {
            return;
        }
        if (!fn.substring(0,1).matches("[a-zA-Z]")) {
            iFatherName.setError("Father Name format is wrong");
            requestFocus(sFatherName);
        }else{
            iFatherName.setErrorEnabled(false);
        }
        if (!validateMotherName()) {
            return;
        }
        if (!mn.substring(0,1).matches("[a-zA-Z]")) {
            iMotherName.setError("Mother Name format is wrong");
            requestFocus(sMotherName);
        }else{
            iMotherName.setErrorEnabled(false);
        }
        if (birthid.getText().toString().equals("Date of Birth *")) {
            Toast.makeText(this, "Please select your Birth Date", Toast.LENGTH_SHORT).show();
        }
        if (gender_id.equals("0")) {
            Toast.makeText(this, "Please select your Gender", Toast.LENGTH_SHORT).show();
        }
        if (marriage_id.equals("0")) {
            Toast.makeText(this, "Please select your Marital Status", Toast.LENGTH_SHORT).show();
        }
        if (religion_id.equals("0")) {
            Toast.makeText(this, "Please select your Religion", Toast.LENGTH_SHORT).show();
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateMobile()) {
            return;
        }
        if (!validateAddress()) {
            return;
        }
        if (degreeid.getText().toString().equals("Last Degree *")) {
            Toast.makeText(this, "Please select your recent Degree", Toast.LENGTH_SHORT).show();
        }
        if (!validateDegree()) {
            return;
        }
        if (!validateOrg()) {
            return;
        }
        if (!validateResult()) {
            return;
        }
        if (!validatePassYear()) {
            return;
        }
        if (district.getText().toString().equals("District *")) {
            Toast.makeText(this, "Please select your District", Toast.LENGTH_SHORT).show();
        }
        if (!validateUser()) {
            return;
        }
        if (u.contains("%20") || u.contains(" ")) {
            iUsername.setError("Username format is wrong");
            requestFocus(sUsername);
        }else{
            iUsername.setErrorEnabled(false);
        }
        if (!validatePass()) {
            return;
        }
        if (!validateConPass()) {
            return;
        }
        if (!p.equals(cp)) {
            Toast.makeText(getBaseContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
        }
        if (terms.equals("0")) {
            Toast.makeText(this, "You are not agree with our Terms and Conditions.", Toast.LENGTH_SHORT).show();
        } else {
            Client = new DefaultHttpClient();

            String URL = Utils.IP + "direct/crasdfasdfesdfasdfaadfasdfte_cliesdfassdfasddfnsdfasdfasdft/8345624?user_full_name=" + n + "&mobile_no=" + m + "&degree_id=" + deid + "&degree_title=" + d + "&institution=" + o + "&result=" + r + "&pass_year=" + py + "&user_name=" + u + "&password=" + p + "&birth_date=" + bd + "&gender=" + gen + "&email=" + em + "&address=" + add + "&company_name=" + com + "&designation=" + desig + "&job_start=" + js + "&job_end=" + je + "&district_id=" + di + "&father_name=" + fn + "&mother_name=" + mn + "&marital_status=" + marriage_id + "&religion=" + religion_id;
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

            /*String URL = Utils.IP + "direct/crasdfasdfesdfasdfaadfasdfte_cliesdfassdfasddfnsdfasdfasdft/8345624?user_full_name=" + n + "&mobile_no=" + m + "&degree_id=" + deid + "&degree_title=" + d + "&institution=" + o + "&result=" + r + "&pass_year=" + py + "&user_name=" + u + "&password=" + p + "&birth_date=" + bd + "&gender=" + gen + "&email=" + em + "&address=" + add + "&company_name=" + com + "&designation=" + desig + "&job_start=" + js + "&job_end=" + je + "&district_id=" + di;
            TextView st = (TextView)findViewById(R.id.status);
            st.setText(URL);
            st.setVisibility(View.GONE);*/
            //Toast.makeText(this, "lsfjklk", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(JobSeekerReg.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateName() {
        if (sName.getText().toString().trim().isEmpty()) {
            iName.setError("Full Name field is blank");
            requestFocus(sName);
            return false;
        } else {
            iName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateFatherName() {
        if (sFatherName.getText().toString().trim().isEmpty()) {
            iFatherName.setError("Father Name field is blank");
            requestFocus(sFatherName);
            return false;
        } else {
            iFatherName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMotherName() {
        if (sMotherName.getText().toString().trim().isEmpty()) {
            iMotherName.setError("Mother Name field is blank");
            requestFocus(sMotherName);
            return false;
        } else {
            iMotherName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        if (sEmail.getText().toString().trim().isEmpty()) {
            iEmail.setError("Email field is blank");
            requestFocus(sEmail);
            return false;
        } else {
            iEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        if (sMobile.getText().toString().trim().isEmpty()) {
            iMobile.setError("Mobile no. field is blank");
            requestFocus(sMobile);
            return false;
        } else {
            iMobile.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (sAddress.getText().toString().trim().isEmpty()) {
            iAddress.setError("Address field is blank");
            requestFocus(sAddress);
            return false;
        } else {
            iAddress.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDegree() {
        if (sDegree.getText().toString().trim().isEmpty()) {
            iDegree.setError("Degree Title field is blank");
            requestFocus(sDegree);
            return false;
        } else {
            iDegree.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateOrg() {
        if (sOrg.getText().toString().trim().isEmpty()) {
            iOrg.setError("Institution field is blank");
            requestFocus(sOrg);
            return false;
        } else {
            iOrg.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateResult() {
        if (sResult.getText().toString().trim().isEmpty()) {
            iResult.setError("Result field is blank");
            requestFocus(sResult);
            return false;
        } else {
            iResult.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUser() {
        if (sUsername.getText().toString().trim().isEmpty()) {
            iUsername.setError("Username field is blank");
            requestFocus(sUsername);
            return false;
        } else {
            iUsername.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassYear() {
        if (sPassyear.getText().toString().trim().isEmpty()) {
            iPassyear.setError("Passing Year field is blank");
            requestFocus(sPassyear);
            return false;
        } else {
            iPassyear.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePass() {
        if (sPass.getText().toString().trim().isEmpty()) {
            iPass.setError("Password field is blank");
            requestFocus(sPass);
            return false;
        } else {
            iPass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConPass() {
        if (sConPass.getText().toString().trim().isEmpty()) {
            iConPass.setError("Confirm Password field is blank");
            requestFocus(sConPass);
            return false;
        } else {
            iConPass.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
