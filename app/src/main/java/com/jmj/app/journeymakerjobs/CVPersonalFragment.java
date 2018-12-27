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
import android.os.StrictMode;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class CVPersonalFragment extends Fragment {
    private ListView listView;
    Typeface fontAwesomeFont;
    ArrayList<ProductCV> arrayListCV;
    String dis, disid, experience = "0", res, id, gender = "", marriage = "", religion = "", bd, user, fulln, basa, permanent_basa, mail, con, altcon, baba, ma, lingo, bia, dhormo, bdid, cobj, facebook;
    TextInputLayout username, fullname, father, mother, mob, altmob, email, nid, address, perm_address, obj, fbid, total_experience;
    EditText un, fn, fathername, mothername, phn, altphn, em, natid, add, perm_add, objective, fb, total_exp;
    TextView birth, comps, district, nodata, count;
    private RadioGroup radioSexGroup, radioRelGroup, radioMarriageGroup;
    private RadioButton radioSexButton, radioRelButton, radioMarriageButton;
    int mYear, mMonth, mDay, c1 = 0;
    ArrayList<ProductJobType> arrayList, arrayList1;
    GifImageView gifImageView;
    ListView listView1;

    public CVPersonalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.cv_personal_fragment, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-solid-900.ttf");

        TextView usernamepic = (TextView) myview.findViewById(R.id.usernamepic);
        TextView namepic = (TextView) myview.findViewById(R.id.namepic);
        TextView fathernamepic = (TextView) myview.findViewById(R.id.fathernamepic);
        TextView mothernamepic = (TextView) myview.findViewById(R.id.mothernamepic);
        TextView birthpic = (TextView) myview.findViewById(R.id.birthpic);
        TextView phonepic = (TextView) myview.findViewById(R.id.phonepic);
        TextView altphonepic = (TextView) myview.findViewById(R.id.altphonepic);
        TextView emailpic = (TextView) myview.findViewById(R.id.emailpic);
        TextView genderpic = (TextView) myview.findViewById(R.id.genderpic);
        TextView marriagepic = (TextView) myview.findViewById(R.id.marriagepic);
        TextView religionpic = (TextView) myview.findViewById(R.id.religionpic);
        TextView nidpic = (TextView) myview.findViewById(R.id.nidpic);
        TextView addresspic = (TextView) myview.findViewById(R.id.addresspic);
        TextView objectivepic = (TextView) myview.findViewById(R.id.objectivepic);
        TextView apply = (TextView) myview.findViewById(R.id.apply);
        TextView down = (TextView) myview.findViewById(R.id.down);
        TextView down1 = (TextView) myview.findViewById(R.id.down1);

        usernamepic.setTypeface(fontAwesomeFont);
        namepic.setTypeface(fontAwesomeFont);
        fathernamepic.setTypeface(fontAwesomeFont);
        mothernamepic.setTypeface(fontAwesomeFont);
        birthpic.setTypeface(fontAwesomeFont);
        phonepic.setTypeface(fontAwesomeFont);
        altphonepic.setTypeface(fontAwesomeFont);
        emailpic.setTypeface(fontAwesomeFont);
        genderpic.setTypeface(fontAwesomeFont);
        marriagepic.setTypeface(fontAwesomeFont);
        religionpic.setTypeface(fontAwesomeFont);
        nidpic.setTypeface(fontAwesomeFont);
        addresspic.setTypeface(fontAwesomeFont);
        objectivepic.setTypeface(fontAwesomeFont);
        apply.setTypeface(fontAwesomeFont);
        down.setTypeface(fontAwesomeFont);
        down1.setTypeface(fontAwesomeFont);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        user = result[2];

        username = (TextInputLayout) myview.findViewById(R.id.input_username);
        fullname = (TextInputLayout) myview.findViewById(R.id.input_name);
        father = (TextInputLayout) myview.findViewById(R.id.input_father_name);
        mother = (TextInputLayout) myview.findViewById(R.id.input_mother_name);
        email = (TextInputLayout) myview.findViewById(R.id.input_email);
        mob = (TextInputLayout) myview.findViewById(R.id.input_phone);
        altmob = (TextInputLayout) myview.findViewById(R.id.input_altphone);
        nid = (TextInputLayout) myview.findViewById(R.id.input_nid);
        address = (TextInputLayout) myview.findViewById(R.id.input_address);
        perm_address = (TextInputLayout) myview.findViewById(R.id.input_address1);
        obj = (TextInputLayout) myview.findViewById(R.id.input_objective);
        fbid = (TextInputLayout) myview.findViewById(R.id.input_fbid);
        fbid = (TextInputLayout) myview.findViewById(R.id.input_fbid);
        total_experience = (TextInputLayout) myview.findViewById(R.id.input_exp);

        un = (EditText) myview.findViewById(R.id.sign_username);
        fn = (EditText) myview.findViewById(R.id.sign_name);
        fathername = (EditText) myview.findViewById(R.id.sign_father_name);
        mothername = (EditText) myview.findViewById(R.id.sign_mother_name);
        em = (EditText) myview.findViewById(R.id.sign_email);
        phn = (EditText) myview.findViewById(R.id.sign_phone);
        altphn = (EditText) myview.findViewById(R.id.sign_altphone);
        natid = (EditText) myview.findViewById(R.id.sign_nid);
        add = (EditText) myview.findViewById(R.id.sign_address);
        perm_add = (EditText) myview.findViewById(R.id.sign_address1);
        objective = (EditText) myview.findViewById(R.id.sign_objective);
        fb = (EditText) myview.findViewById(R.id.sign_fbid);
        total_exp = (EditText) myview.findViewById(R.id.sign_exp);
        total_exp.setText(experience);

        district = (TextView) myview.findViewById(R.id.distid);

        birth = (TextView) myview.findViewById(R.id.birthid);
        LinearLayout birth_linear = (LinearLayout) myview.findViewById(R.id.l4);
        birth_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                int d = monthOfYear + 1;
                                int day = dayOfMonth;
                                birth.setText("Date of Birth : " + year + "-" + day + "-" + day);
                                bd = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        radioSexGroup = (RadioGroup) myview.findViewById(R.id.radioSex);
        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioSexButton = (RadioButton) myview.findViewById(checkedId);
                                                         //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                         gender = (String) radioSexButton.getText();
                                                         //Toast.makeText(getContext(), gender, Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
        );

        radioMarriageGroup = (RadioGroup) myview.findViewById(R.id.radioMarital);
        radioMarriageGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                              radioMarriageButton = (RadioButton) myview.findViewById(checkedId);
                                                              //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                              marriage = (String) radioMarriageButton.getText();
                                                              //Toast.makeText(getContext(), marriage, Toast.LENGTH_SHORT).show();
                                                          }
                                                      }
        );

        radioRelGroup = (RadioGroup) myview.findViewById(R.id.radioReligion);
        radioRelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                         radioRelButton = (RadioButton) myview.findViewById(checkedId);
                                                         //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                                                         religion = (String) radioRelButton.getText();
                                                         //Toast.makeText(getContext(), religion, Toast.LENGTH_SHORT).show();
                                                     }
                                                 }
        );

        arrayListCV = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCV().execute(Utils.IP + "direct/asdfbasiasdfac_deasdfastailsdfasdfasdf/12342124?user_id=" + id);
            }
        });

        LinearLayout l16 = (LinearLayout) myview.findViewById(R.id.l16);
        l16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                District alert = new District();
                alert.showDialog(getActivity(), "");
            }
        });

        LinearLayout editclick = (LinearLayout) myview.findViewById(R.id.r5);
        editclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        comps = (TextView) myview.findViewById(R.id.comps);

        return myview;
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
                c1 = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayListCV.add(new ProductCV(
                            productObject.getString("photo"),
                            productObject.getString("signature"),
                            productObject.getString("user_full_name"),
                            productObject.getString("user_type"),
                            productObject.getString("email"),
                            productObject.getString("address"),
                            productObject.getString("permanent_address"),
                            productObject.getString("father_name"),
                            productObject.getString("mother_name"),
                            productObject.getString("birth_date"),
                            productObject.getString("gender"),
                            productObject.getString("objective"),
                            productObject.getString("marital_status"),
                            productObject.getString("religion"),
                            productObject.getString("national_id"),
                            productObject.getString("mobile"),
                            productObject.getString("alt_mobile"),
                            productObject.getString("facebook_id"),
                            productObject.getString("desired_job"),
                            productObject.getString("reference"),
                            productObject.getString("district_id"),
                            productObject.getString("job_experience"),
                            productObject.getString("username")
                    ));
                    String fullname = arrayListCV.get(i).getUser_full_name();
                    if (!fullname.equals("")) {
                        c1 += 5;
                    }
                    String basa = arrayListCV.get(i).getAddress();
                    if (!basa.equals("")) {
                        c1 += 10;
                    }
                    String mail = arrayListCV.get(i).getEmail();
                    if (!mail.equals("")) {
                        c1 += 10;
                    }
                    String mob = arrayListCV.get(i).getMobile();
                    if (!mob.equals("")) {
                        c1 += 10;
                    }
                    String mob2 = arrayListCV.get(i).getAlt_mobile();
                    if (!mob2.equals("null") || !mob2.equals("")) {
                        c1 += 10;
                    }
                    String father = arrayListCV.get(i).getFather_name();
                    if (!father.equals("")) {
                        c1 += 10;
                    }
                    String mother = arrayListCV.get(i).getMother_name();
                    if (!mother.equals("")) {
                        c1 += 10;
                    }
                    String birthdate = arrayListCV.get(i).getBirth_date();
                    if (!birthdate.equals("") || !birthdate.equals("0000-00-00")) {
                        c1 += 5;
                    }
                    String gen = arrayListCV.get(i).getGender();
                    if (!gen.equals("")) {
                        c1 += 10;
                    }
                    String marriage = arrayListCV.get(i).getMarital_status();
                    if (!marriage.equals("")) {
                        c1 += 2;
                    }
                    String rel = arrayListCV.get(i).getReligion();
                    if (!rel.equals("")) {
                        c1 += 2;
                    }
                    String nid = arrayListCV.get(i).getNid();
                    if (!nid.equals("")) {
                        c1 += 2;
                    }
                    String fbid = arrayListCV.get(i).getFb();
                    String obj = arrayListCV.get(i).getObjective();
                    if (!obj.equals("")) {
                        c1 += 2;
                    }
                    String dist = arrayListCV.get(i).getDist();
                    if (!dist.equals("")) {
                        c1 += 2;
                        disid = dist;
                        arrayList1 = new ArrayList<>();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new ReadJSONCheck().execute(Utils.IP + "direct/distriasdfct_lissdftfadsf/12342124");
                            }
                        });
                    }
                    String user = arrayListCV.get(i).getUser();
                    if (!user.equals("")) {
                        c1 += 10;
                    }
                    comps.setText(c1 + "% Completed");
                    un.setText(user);
                    fulln = arrayListCV.get(i).getUser_full_name();
                    fn.setText(fulln);
                    basa = arrayListCV.get(i).getAddress();
                    basa = basa.replaceAll("u000D000A", "\n");
                    add.setText(basa);
                    permanent_basa = arrayListCV.get(i).getPerm_address();
                    permanent_basa = permanent_basa.replaceAll("u000D000A", "\n");
                    perm_add.setText(permanent_basa);
                    mail = arrayListCV.get(i).getEmail();
                    em.setText(mail);
                    con = arrayListCV.get(i).getMobile();
                    phn.setText(con);
                    altcon = arrayListCV.get(i).getAlt_mobile();
                    altphn.setText(altcon);
                    baba = arrayListCV.get(i).getFather_name();
                    fathername.setText(baba);
                    ma = arrayListCV.get(i).getMother_name();
                    mothername.setText(ma);
                    bd = arrayListCV.get(i).getBirth_date();
                    birth.setText("Birth Date : " + bd);
                    gender = arrayListCV.get(i).getGender();
                    if (gender.equals("Male")) {
                        radioSexGroup.check(R.id.radioMale);
                    }
                    if (gender.equals("Female")) {
                        radioSexGroup.check(R.id.radioFemale);
                    }
                    if (gender.equals("Other")) {
                        radioSexGroup.check(R.id.radioOther);
                    }
                    marriage = arrayListCV.get(i).getMarital_status();
                    if (marriage.contains("Married")) {
                        radioMarriageGroup.check(R.id.radioMarried);
                    }
                    if (marriage.contains("Unmarried")) {
                        radioMarriageGroup.check(R.id.radioUnmarried);
                    }
                    if (marriage.contains("Widowed")) {
                        radioMarriageGroup.check(R.id.radioWidowed);
                    }
                    if (marriage.contains("Separated")) {
                        radioMarriageGroup.check(R.id.radioSeperated);
                    }
                    if (marriage.contains("Divorced")) {
                        radioMarriageGroup.check(R.id.radioDivorced);
                    }
                    if (marriage.contains("Single")) {
                        radioMarriageGroup.check(R.id.radioSingle);
                    }
                    religion = arrayListCV.get(i).getReligion();
                    if (religion.contains("Islam")) {
                        radioRelGroup.check(R.id.radioMuslim);
                    }
                    if (religion.contains("Hinduism") || religion.contains("Hindu")) {
                        radioRelGroup.check(R.id.radioHindu);
                    }
                    if (religion.contains("Buddho")) {
                        radioRelGroup.check(R.id.radioBuddho);
                    }
                    if (religion.contains("Christian")) {
                        radioRelGroup.check(R.id.radioChristian);
                    }
                    bdid = arrayListCV.get(i).getNid();
                    natid.setText(bdid);
                    cobj = arrayListCV.get(i).getObjective();
                    if(cobj.contains("u000D000A")) {
                        cobj = cobj.replaceAll("u000D000A", "<br/>");
                    }
                    objective.setText(Html.fromHtml(cobj));
                    facebook = arrayListCV.get(i).getFb();
                    fb.setText(facebook);
                    experience = arrayListCV.get(i).getExp();
                    experience = experience.replaceAll("u000D000A", "\n");
                    total_exp.setText(experience);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class District {
        public void showDialog(FragmentActivity activity, String msg) {
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
            getActivity().runOnUiThread(new Runnable() {
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
                    district.setText("District - " + dis);
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
                    getActivity().getApplicationContext(), R.layout.jobtype_list_item, arrayList1
            );
            listView1.setAdapter(adapter);
        }
    }
    class ReadJSONCheck extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                //alert.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("district_list");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductJobType(
                            productObject.getString("district_id"),
                            productObject.getString("district_name")
                    ));

                    String id = arrayList1.get(i).getId();
                    if (disid.equals(id)) {
                        String name = arrayList1.get(i).getName();
                        district.setText("District - " + name);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
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

    public void submit() {
        fulln = fn.getText().toString();
        if (fulln.contains(" ")) {
            fulln = fulln.replace(" ", "%20");
        }
        basa = add.getText().toString();
        if (basa.contains(" ")) {
            basa = basa.replace(" ", "%20");
        }
        basa = basa.replaceAll("\\r?\\n", "\\u000D\\000A");
        permanent_basa = perm_add.getText().toString();
        if (permanent_basa.contains(" ")) {
            permanent_basa = permanent_basa.replace(" ", "%20");
        }
        permanent_basa = permanent_basa.replaceAll("\\r?\\n", "\\u000D\\000A");
        mail = em.getText().toString();
        if (mail.contains(" ")) {
            mail = mail.replace(" ", "%20");
        }
        con = phn.getText().toString();
        if (con.contains(" ")) {
            con = con.replace(" ", "%20");
        }
        altcon = altphn.getText().toString();
        if (altcon.contains(" ")) {
            altcon = altcon.replace(" ", "%20");
        }
        baba = fathername.getText().toString();
        if (baba.contains(" ")) {
            baba = baba.replace(" ", "%20");
        }
        ma = mothername.getText().toString();
        if (ma.contains(" ")) {
            ma = ma.replace(" ", "%20");
        }
        bdid = natid.getText().toString();
        if (bdid.contains(" ")) {
            bdid = bdid.replace(" ", "%20");
        }
        cobj = objective.getText().toString();
        if (cobj.contains(" ")) {
            cobj = cobj.replace(" ", "%20");
        }
        cobj = cobj.replaceAll("\\r?\\n", "\\u000D\\000A");
        /*String[] lines2 = cobj.split("\\r?\\n");
        String l3 = null;
        for (int i = 0; i < lines2.length; i++) {
            //chartnames.add("" + lines[i]);
            l3 = "" + lines2[i];
            //Toast.makeText(getActivity(), cobj, Toast.LENGTH_SHORT).show();
        }
        cobj = l3;*/
        //Toast.makeText(getActivity(), cobj, Toast.LENGTH_SHORT).show();
        facebook = fb.getText().toString();
        if (facebook.contains(" ")) {
            facebook = facebook.replace(" ", "%20");
        }
        experience = total_exp.getText().toString();
        if (experience.contains(" ")) {
            experience = experience.replace(" ", "%20");
        }
        experience = experience.replaceAll("\\r?\\n", "\\u000D\\000A");
        //Toast.makeText(getActivity(), marriage, Toast.LENGTH_SHORT).show();

        HttpClient Client = new DefaultHttpClient();

        String URL = Utils.IP + "direct/badfasdfsicsd_detadfils_upsdfdasdfate_dataasdfasdf/12342124?user_id=" + id + "&user_full_name=" + fulln + "&user_dob=" + bd + "&father_name=" + baba + "&mother_name=" + ma + "&phone_no=" + con + "&alt_mobile=" + altcon + "&email=" + mail + "&gender=" + gender + "&marital_status=" + marriage + "&religion=" + religion + "&national_id=" + bdid + "&facebook_id=" + facebook + "&address=" + basa + "&objective=" + cobj + "&user_name=" + user + "&permanent_address=" + permanent_basa + "&job_experience=" + experience + "&district_id=" + disid;
        //Toast.makeText(getActivity(), URL, Toast.LENGTH_LONG).show();
        String SetServerString = "";

        HttpGet httpget = new HttpGet(URL);
        HttpResponse response = null;
        try {
            response = Client.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String res = EntityUtils.toString(httpEntity);
            //Toast.makeText(getContext(), res, Toast.LENGTH_LONG).show();
            if (res.contains("Success")) {
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                try {
                    SetServerString = Client.execute(httpget, responseHandler);
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getBaseContext(), CV.class);
                    intent.putExtra("id", id);
                    intent.putExtra("page", "0");
                    startActivity(intent);
                    getActivity().finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
