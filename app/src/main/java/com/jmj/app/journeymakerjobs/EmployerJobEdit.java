package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class EmployerJobEdit extends AppCompatActivity {
    Typeface fontAwesomeFont;
    TextView publish, deadline, category, district, nodata, count;
    ListView lv1, lv2;
    String pub_date, last_date, nature_id, disid, dis, catid, cat, res, id, com_id, t, l, s, v, d, a, ed, emp, n;
    String name, lo, nat, gs, vac, pub, ld, des, ai, edu, exp, jc, dist, status, ji, page;
    int mYear, mMonth, mDay;
    RadioGroup radioNature, radioStatus;
    RadioButton radioNatureButton, radioStatusButton;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList, arrayList1;
    ArrayList<ProductEmpProfile> arrayListCV;
    TextInputLayout iTitle, iLocation, iSalary, iVac, iDescription, iAdditional, iEducational, iEmployment;
    EditText sTitle, sLocation, sSalary, sVac, sDescription, sAdditional, sEducational, sEmployment;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_job_edit);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getSharedPreferences(Utils.SHARED_PREF_NAME_EMP, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF_EMP)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF_EMP, "");
        }
        String[] result = res.split(" ");
        id = result[0];

        ji = getIntent().getStringExtra("ji");
        t = getIntent().getStringExtra("name");
        l = getIntent().getStringExtra("lo");
        nature_id = getIntent().getStringExtra("n");
        s = getIntent().getStringExtra("gs");
        v = getIntent().getStringExtra("v");
        pub_date = getIntent().getStringExtra("pub");
        last_date = getIntent().getStringExtra("ld");
        d = getIntent().getStringExtra("d");
        d = d.replaceAll("u000D000A", "<br/>");
        a = getIntent().getStringExtra("ai");
        ai = ai.replaceAll("u000D000A", "<br/>");
        ed = getIntent().getStringExtra("ed");
        ed = ed.replaceAll("u000D000A", "<br/>");
        emp = getIntent().getStringExtra("e");
        emp = emp.replaceAll("u000D000A", "<br/>");
        catid = getIntent().getStringExtra("cn");
        disid = getIntent().getStringExtra("dist");
        status = getIntent().getStringExtra("status");
        page = getIntent().getStringExtra("page");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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

        TextView d1 = (TextView) findViewById(R.id.down1);
        TextView d2 = (TextView) findViewById(R.id.down2);
        TextView d3 = (TextView) findViewById(R.id.down3);
        TextView d4 = (TextView) findViewById(R.id.down4);
        TextView apply = (TextView) findViewById(R.id.apply);
        d1.setTypeface(fontAwesomeFont);
        d2.setTypeface(fontAwesomeFont);
        d3.setTypeface(fontAwesomeFont);
        d4.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);

        iTitle = (TextInputLayout) findViewById(R.id.input_jobtitle);
        iLocation = (TextInputLayout) findViewById(R.id.input_loc);
        iSalary = (TextInputLayout) findViewById(R.id.input_sal);
        iVac = (TextInputLayout) findViewById(R.id.input_vac);
        iDescription = (TextInputLayout) findViewById(R.id.input_des);
        iAdditional = (TextInputLayout) findViewById(R.id.input_add);
        iEducational = (TextInputLayout) findViewById(R.id.input_edu);
        iEmployment = (TextInputLayout) findViewById(R.id.input_emp);

        sTitle = (EditText) findViewById(R.id.sign_jobtitle);
        sTitle.setText(t);
        sLocation = (EditText) findViewById(R.id.sign_loc);
        sLocation.setText(l);
        sSalary = (EditText) findViewById(R.id.sign_sal);
        sSalary.setText(s);
        sVac = (EditText) findViewById(R.id.sign_vac);
        sVac.setText(v);
        sDescription = (EditText) findViewById(R.id.sign_des);
        sDescription.setText(Html.fromHtml(d));
        sAdditional = (EditText) findViewById(R.id.sign_add);
        sAdditional.setText(Html.fromHtml(a));
        sEducational = (EditText) findViewById(R.id.sign_edu);
        sEducational.setText(Html.fromHtml(ed));
        sEmployment = (EditText) findViewById(R.id.sign_emp);
        sEmployment.setText(Html.fromHtml(emp));

        arrayListCV = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONCV().execute(Utils.IP + "direct/psdfrfasdfofile_asdfasdviadsfadsew/12342124?user_id=" + id);
            }
        });

        publish = (TextView) findViewById(R.id.publish);
        publish.setText("Publish Date : " + pub_date);
        deadline = (TextView) findViewById(R.id.deadline);
        deadline.setText("Deadline : " + last_date);
        category = (TextView) findViewById(R.id.category);
        category.setText(catid);
        district = (TextView) findViewById(R.id.dist);
        district.setText(disid);

        LinearLayout l6 = (LinearLayout) findViewById(R.id.l6);
        LinearLayout l7 = (LinearLayout) findViewById(R.id.l7);
        LinearLayout l12 = (LinearLayout) findViewById(R.id.l12);
        LinearLayout l13 = (LinearLayout) findViewById(R.id.l13);
        LinearLayout r5 = (LinearLayout) findViewById(R.id.r5);

        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EmployerJobEdit.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                publish.setText("Publish Date : " + year + "-" + day + "-" + day);
                                pub_date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        l7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EmployerJobEdit.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                deadline.setText("Deadline : " + year + "-" + day + "-" + day);
                                last_date = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioNature = (RadioGroup) findViewById(R.id.radioNature);
        if (nature_id.equals("Part Time")) {
            radioNature.check(R.id.radiopart);
        }
        if (nature_id.equals("Full Time")) {
            radioNature.check(R.id.radiofull);
        }
        radioNature.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                       radioNatureButton = (RadioButton) findViewById(checkedId);
                                                       String nature = (String) radioNatureButton.getText();
                                                       if (nature.equals("Part Time")) {
                                                           nature_id = "Part Time";
                                                       }
                                                       if (nature.equals("Full Time")) {
                                                           nature_id = "Full Time";
                                                       }
                                                   }
                                               }
        );

        radioStatus = (RadioGroup) findViewById(R.id.radioStatus);
        if (status.equals("Active") || status.equals("active")) {
            radioStatus.check(R.id.radioActive);
        }
        if (status.equals("Inactive") || status.equals("inactive")) {
            radioStatus.check(R.id.radioInactive);
        }
        radioStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                   @Override
                                                   public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                       radioStatusButton = (RadioButton) findViewById(checkedId);
                                                       String nature = (String) radioStatusButton.getText();
                                                       if (nature.equals("Active")) {
                                                           status = "active";
                                                       }
                                                       if (nature.equals("Inactive")) {
                                                           status = "inactive";
                                                       }
                                                   }
                                               }
        );

        arrayList1 = new ArrayList<>();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadJSONPost().execute(Utils.IP + "direct/jadfodfb_casdfatasdfeasgory_asdfinasdfasffo/12342124?user_id=");
            }
        });

        l12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Category alert = new Category();
                alert.showDialog(EmployerJobEdit.this, "");
            }
        });

        l13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                District alert = new District();
                alert.showDialog(EmployerJobEdit.this, "");
            }
        });

        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobPost();
            }
        });
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

            lv1 = (ListView) dialog.findViewById(R.id.listView);
            arrayList = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON().execute(Utils.IP + "direct/distriasdfct_lissdftfadsf/12342124");
                }
            });

            //Toast.makeText(JobSeekerReg.this, Utils.IP + "direct/deasdfgreasdfase_lissdfasdt/12342124", Toast.LENGTH_SHORT).show();

            lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    disid = arrayList.get(i).getId();
                    dis = arrayList.get(i).getName();
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

    public class Category {
        public void showDialog(AppCompatActivity activity, String msg) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.job_type_list);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText("Select Category");
            nodata = (TextView) dialog.findViewById(R.id.nodata);
            count = (TextView) dialog.findViewById(R.id.count);
            gifImageView = (GifImageView) dialog.findViewById(R.id.loader);
            gifImageView.setVisibility(View.VISIBLE);

            lv2 = (ListView) dialog.findViewById(R.id.listView);
            arrayList1 = new ArrayList<>();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new ReadJSON1().execute(Utils.IP + "direct/jadfodfb_casdfatasdfeasgory_asdfinasdfasffo/12342124?user_id=");
                }
            });

            //Toast.makeText(JobSeekerReg.this, Utils.IP + "direct/deasdfgreasdfase_lissdfasdt/12342124", Toast.LENGTH_SHORT).show();

            lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //String quete = arrayList.get(i).getQuete();
                    catid = arrayList1.get(i).getId();
                    cat = arrayList1.get(i).getName();
                    category.setText(cat);
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
                JSONArray jsonArray = jsonObject.getJSONArray("district_list");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductJobType(
                            productObject.getString("district_id"),
                            productObject.getString("district_name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nodata.setVisibility(View.VISIBLE);
            }
            final CustomListAdapterDistrict adapter = new CustomListAdapterDistrict(
                    getApplicationContext(), R.layout.jobtype_list_item, arrayList
            );
            lv1.setAdapter(adapter);
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
                JSONArray jsonArray = jsonObject.getJSONArray("job_category_info");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductJobType(
                            productObject.getString("job_category_id"),
                            productObject.getString("category_name")
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nodata.setVisibility(View.VISIBLE);
            }
            final CustomListAdapterCategory adapter = new CustomListAdapterCategory(
                    getApplicationContext(), R.layout.jobtype_list_item, arrayList1
            );
            lv2.setAdapter(adapter);
        }
    }

    class ReadJSONPost extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                progressBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("job_category_info");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductJobType(
                            productObject.getString("job_category_id"),
                            productObject.getString("category_name")
                    ));

                    String id = arrayList1.get(i).getId();
                    String names = arrayList1.get(i).getName();
                    if (catid.equals(id)) {
                        category.setText(names);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                nodata.setVisibility(View.VISIBLE);
            }
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

    public class CustomListAdapterCategory extends ArrayAdapter<ProductJobType> {

        ArrayList<ProductJobType> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterCategory(Context context, int resource, ArrayList<ProductJobType> products) {
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
            job.setText(getResources().getString(R.string.jobs));

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

    class ReadJSONCV extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("personal_details");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayListCV.add(new ProductEmpProfile(
                            productObject.getString("photo_url"),
                            productObject.getString("username"),
                            productObject.getString("user_full_name"),
                            productObject.getString("email"),
                            productObject.getString("company_id"),
                            productObject.getString("company_name"),
                            productObject.getString("company_type"),
                            productObject.getString("company_address"),
                            productObject.getString("mobile"),
                            productObject.getString("concern_person"),
                            productObject.getString("concern_designation"),
                            productObject.getString("website")
                    ));

                    com_id = arrayListCV.get(i).getCom_id();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void jobPost() {
        t = sTitle.getText().toString();
        if (t.contains(" ")) {
            t = t.replace(" ", "%20");
        }
        l = sLocation.getText().toString();
        if (l.contains(" ")) {
            l = l.replace(" ", "%20");
        }
        s = sSalary.getText().toString();
        if (s.contains(" ")) {
            s = s.replace(" ", "%20");
        }
        v = sVac.getText().toString();
        if (v.contains(" ")) {
            v = v.replace(" ", "%20");
        }
        d = sDescription.getText().toString();
        if (d.contains(" ")) {
            d = d.replace(" ", "%20");
        }
        d = d.replaceAll("\\r?\\n", "\\u000D\\000A");
        a = sAdditional.getText().toString();
        if (a.contains(" ")) {
            a = a.replace(" ", "%20");
        }
        a = a.replaceAll("\\r?\\n", "\\u000D\\000A");
        ed = sEducational.getText().toString();
        if (ed.contains(" ")) {
            ed = ed.replace(" ", "%20");
        }
        ed = ed.replaceAll("\\r?\\n", "\\u000D\\000A");
        emp = sEmployment.getText().toString();
        if (emp.contains(" ")) {
            emp = emp.replace(" ", "%20");
        }
        emp = emp.replaceAll("\\r?\\n", "\\u000D\\000A");
        n = nature_id;
        if (n.contains(" ")) {
            n = n.replace(" ", "%20");
        }
        final String p = pub_date;
        final String ld = last_date;
        final String cat = catid;
        final String dist = disid;
        final String ci = com_id;
        final String st = status;
        final String jobid = ji;

        String URL = Utils.IP + "direct/josdfb_posdfst_edasdfasditafasdf/12342124?job_title=" + t + "&company_id=" + ci + "&job_location=" + l + "&job_nature=" + n + "&vacancy=" + v + "&last_date=" + ld + "&publish_date=" + p + "&job_description=" + d + "&additional_info=" + a + "&education=" + ed + "&experience=" + emp + "&job_category_id=" + cat + "&district_id=" + dist + "&salary=" + s + "&status=" + st + "&job_id=" + jobid;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        Toast.makeText(EmployerJobEdit.this, "Success", Toast.LENGTH_LONG).show();
                        if (page.equals("1")) {
                            Intent intent = new Intent(getBaseContext(), EmployerAllJobs.class);
                            startActivity(intent);
                            finish();
                        }
                        if (page.equals("0")) {
                            Intent intent = new Intent(getBaseContext(), EmployerRunningJobs.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(EmployerJobEdit.this, volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("job_title", t);
                params.put("company_id", ci);
                params.put("job_location", l);
                params.put("job_nature", n);
                params.put("vacancy", v);
                params.put("last_date", ld);
                params.put("publish_date", p);
                params.put("job_description", d);
                params.put("additional_info", a);
                params.put("education", ed);
                params.put("experience", emp);
                params.put("job_category_id", cat);
                params.put("district_id", dist);
                params.put("salary", s);
                params.put("status", st);
                params.put("job_id", jobid);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(EmployerJobEdit.this);
        requestQueue.add(stringRequest);
    }
}
