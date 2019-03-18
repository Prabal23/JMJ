package com.jmj.app.journeymakerjobs;

import android.app.Activity;
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
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class CVAcademicFragment extends Fragment {
    private ListView listView, lv;
    Typeface fontAwesomeFont;
    ArrayList<ProductCV> arrayListCV;
    ArrayList<ProductEducation> arrayList1;
    String usern, name, id1 = "", res, id, gender = "", marriage = "", religion = "", bd, user, fulln, basa, mail, con, altcon, baba, ma, lingo, bia, dhormo, bdid, cobj, facebook;
    TextInputLayout username, fullname, father, mother, mob, altmob, email, nid, address, obj, fbid;
    EditText un, fn, fathername, mothername, phn, altphn, em, natid, add, objective, fb;
    TextView birth;
    int counting, c2 = 0;
    TextView nodata, count, degreeid, not_available, comps;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList;

    public CVAcademicFragment() {
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
        final View myview = inflater.inflate(R.layout.cv_academic_fragment, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-solid-900.ttf");

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
        mob = (TextInputLayout) myview.findViewById(R.id.input_phone);
        altmob = (TextInputLayout) myview.findViewById(R.id.input_altphone);

        un = (EditText) myview.findViewById(R.id.sign_username);
        fn = (EditText) myview.findViewById(R.id.sign_name);
        fathername = (EditText) myview.findViewById(R.id.sign_father_name);
        mothername = (EditText) myview.findViewById(R.id.sign_mother_name);
        phn = (EditText) myview.findViewById(R.id.sign_phone);
        altphn = (EditText) myview.findViewById(R.id.sign_altphone);

        TextView apply = (TextView) myview.findViewById(R.id.apply);
        TextView down = (TextView) myview.findViewById(R.id.down);
        final TextView add = (TextView) myview.findViewById(R.id.add);
        apply.setTypeface(fontAwesomeFont);
        down.setTypeface(fontAwesomeFont);
        add.setTypeface(fontAwesomeFont);

        final TextView adding = (TextView) myview.findViewById(R.id.adding);
        final LinearLayout linear = (LinearLayout) myview.findViewById(R.id.linear);
        LinearLayout a = (LinearLayout) myview.findViewById(R.id.a);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counting++;
                if (counting % 2 == 0) {
                    adding.setText("Add");
                    add.setText(getActivity().getResources().getString(R.string.plus));
                    linear.setVisibility(View.GONE);
                } else {
                    adding.setText("Minimize");
                    add.setText(getActivity().getResources().getString(R.string.minus));
                    linear.setVisibility(View.VISIBLE);
                }
            }
        });

        birth = (TextView) myview.findViewById(R.id.birthid);
        LinearLayout birth_linear = (LinearLayout) myview.findViewById(R.id.l4);
        birth_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobType alert = new JobType();
                alert.showDialog((AppCompatActivity) getContext(), "");
            }
        });

        lv = (ListView) myview.findViewById(R.id.listView);

        arrayList1 = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON1().execute(Utils.IP + "direct/edusdfgcatifgodnsdfal_detasdfdfggilsASasdD/12342124?user_id=" + id);

            }
        });
        LinearLayout editclick = (LinearLayout) myview.findViewById(R.id.r5);
        editclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        not_available = (TextView) myview.findViewById(R.id.not_available);
        comps = (TextView) myview.findViewById(R.id.comps);

        return myview;
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
            getActivity().runOnUiThread(new Runnable() {
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
                    id1 = arrayList.get(i).getId();
                    name = arrayList.get(i).getName();
                    birth.setText(name);
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
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("educational_details");

                c2 = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList1.add(new ProductEducation(
                            productObject.getString("user_education_id"),
                            productObject.getString("degree_id"),
                            productObject.getString("degree_title"),
                            productObject.getString("institution"),
                            productObject.getString("result"),
                            productObject.getString("semester"),
                            productObject.getString("pass_year"),
                            productObject.getString("degree_name"),
                            productObject.getString("major")
                    ));
                    String deg_id = arrayList1.get(i).getDegree_id();
                    if (deg_id.equals("102")) {
                        c2 += 30;
                    }
                    if (deg_id.equals("104")) {
                        c2 += 30;
                    }
                    if (deg_id.equals("106")) {
                        c2 += 40;
                    }
                    if (deg_id.equals("108") && deg_id.equals("110")) {
                        c2 += 40;
                    }
                    if (deg_id.equals("108")) {
                        c2 += 20;
                    }
                    if (deg_id.equals("110")) {
                        c2 += 20;
                    }
                    comps.setText(c2 + "% Completed");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterEdu adapter = new CustomListAdapterEdu(
                    getActivity().getApplicationContext(), R.layout.cv_edu_modify_lists, arrayList1
            );
            lv.setAdapter(adapter);
            if (adapter.getCount() == 0) {
                not_available.setVisibility(View.VISIBLE);
                lv.setVisibility(View.GONE);
            } else {
                not_available.setVisibility(View.GONE);
                lv.setVisibility(View.VISIBLE);
            }
            ListViewUtil1.setListViewHeightBasedOnChildren1(lv);
            /*TextView views = (TextView) getActivity().findViewById(R.id.list_empty);
            lv.setEmptyView(views);*/
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
                    getActivity().getApplicationContext(), R.layout.jobtype_list_item, arrayList
            );
            listView.setAdapter(adapter);
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

    public void submit() {
        usern = un.getText().toString();
        if (usern.contains(" ")) {
            usern = usern.replace(" ", "%20");
        }
        usern = usern.replaceAll("\\r?\\n", "\\u000D\\000A");
        fulln = fn.getText().toString();
        if (fulln.contains(" ")) {
            fulln = fulln.replace(" ", "%20");
        }
        fulln = fulln.replaceAll("\\r?\\n", "\\u000D\\000A");
        con = phn.getText().toString();
        if (con.contains(" ")) {
            con = con.replace(" ", "%20");
        }
        con = con.replaceAll("\\r?\\n", "\\u000D\\000A");
        altcon = altphn.getText().toString();
        if (altcon.contains(" ")) {
            altcon = altcon.replace(" ", "%20");
        }
        altcon = altcon.replaceAll("\\r?\\n", "\\u000D\\000A");
        baba = fathername.getText().toString();
        if (baba.contains(" ")) {
            baba = baba.replace(" ", "%20");
        }
        baba = baba.replaceAll("\\r?\\n", "\\u000D\\000A");
        ma = mothername.getText().toString();
        if (ma.contains(" ")) {
            ma = ma.replace(" ", "%20");
        }
        ma = ma.replaceAll("\\r?\\n", "\\u000D\\000A");

        String URL = Utils.IP + "direct/asdeducatasfional_sdfdetaidfls_adfuasdfpdatsdfe_adfdasdfta/12342124?user_id=" + id + "&degree_id=" + id1 + "&degree_title=" + usern + "&institution=" + fulln + "&result=" + baba + "&major=" + ma + "&semester=" + con + "&pass_year=" + altcon;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getActivity().getBaseContext(), CV.class);
                        intent.putExtra("id", id);
                        intent.putExtra("page", "0");
                        startActivity(intent);
                        getActivity().finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.
                params.put("user_id", id);
                params.put("degree_id", id1);
                params.put("degree_title", usern);
                params.put("institution", fulln);
                params.put("result", baba);
                params.put("major", ma);
                params.put("semester", con);
                params.put("pass_year", altcon);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    public class CustomListAdapterEdu extends ArrayAdapter<ProductEducation> {

        ArrayList<ProductEducation> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterEdu(Context context, int resource, ArrayList<ProductEducation> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_edu_modify_lists, null, true);

            }
            ProductEducation product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            TextView ed = (TextView) convertView.findViewById(R.id.edit);
            TextView d = (TextView) convertView.findViewById(R.id.delete);
            ed.setTypeface(fontAwesomeFont);
            d.setTypeface(fontAwesomeFont);
            LinearLayout edit = (LinearLayout) convertView.findViewById(R.id.e);
            LinearLayout delete = (LinearLayout) convertView.findViewById(R.id.d);

            try {
                TextView pass_year = (TextView) convertView.findViewById(R.id.pass_year);
                TextView title = (TextView) convertView.findViewById(R.id.title);
                String address = product.getPass_year();
                address= address.replaceAll("u000D000A", "\n");
                String address1 = product.getSemester();
                address1 = address1.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                byte[] b2 = address1.getBytes("UTF-8");
                String add1 = new String(b2, "UTF-8");
                pass_year.setText(add);
                title.setText("Passing Year");
                if (add.equals("null") || add.equals("")) {
                    pass_year.setText(add1);
                    title.setText("Currently in");
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView degree = (TextView) convertView.findViewById(R.id.degree);
                String address = product.getDegree_title();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                degree.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView ins = (TextView) convertView.findViewById(R.id.ins);
                String address = product.getInstitution();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                ins.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView cgpa = (TextView) convertView.findViewById(R.id.cgpa);
                String address = product.getResult();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                String major = product.getMajor();
                major = major.replaceAll("u000D000A", "\n");
                cgpa.setText(add);
                if (!major.equals("")) {
                    cgpa.setText(add + " (" + major + ")");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    baba = arrayList1.get(position).getSemester();
                    //Toast.makeText(context, baba, Toast.LENGTH_SHORT).show();
                    ma = arrayList1.get(position).getPass_year();
                    //Toast.makeText(context, ma, Toast.LENGTH_SHORT).show();
                    String userid = arrayList1.get(position).getUser_edu_id();
                    //Toast.makeText(context, userid, Toast.LENGTH_SHORT).show();
                    usern = arrayList1.get(position).getDegree_title();
                    //Toast.makeText(context, usern, Toast.LENGTH_SHORT).show();
                    fulln = arrayList1.get(position).getInstitution();
                    //Toast.makeText(context, fulln, Toast.LENGTH_SHORT).show();
                    con = arrayList1.get(position).getResult();
                    //Toast.makeText(context, con, Toast.LENGTH_SHORT).show();
                    altcon = arrayList1.get(position).getDegree();
                    //Toast.makeText(context, altcon, Toast.LENGTH_SHORT).show();
                    String major = arrayList1.get(position).getMajor();
                    //Toast.makeText(context, altcon, Toast.LENGTH_SHORT).show();
                    String degid = arrayList1.get(position).getDegree_id();

                    Intent intent = new Intent(getContext(), EditEducation.class);
                    intent.putExtra("sem", baba);
                    intent.putExtra("pass", ma);
                    intent.putExtra("id", id);
                    intent.putExtra("user_edu_id", userid);
                    intent.putExtra("degree_id", degid);
                    intent.putExtra("degree_title", usern);
                    intent.putExtra("ins", fulln);
                    intent.putExtra("res", con);
                    intent.putExtra("degree", altcon);
                    intent.putExtra("major", major);
                    startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = arrayList1.get(position).getUser_edu_id();
                    //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                    DeleteJob alert1 = new DeleteJob();
                    alert1.showDialog((AppCompatActivity) getActivity(), id);
                }
            });

            return convertView;
        }
    }

    public class DeleteJob {

        public void showDialog(AppCompatActivity activity, final String user_education_id) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.delete_job_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView title = (TextView) dialog.findViewById(R.id.title);
            title.setText("Are you sure to delete this information?");
            Button yes = (Button) dialog.findViewById(R.id.yes);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HttpClient Client = new DefaultHttpClient();

                    String URL = Utils.IP + "direct/dsdfasdelssdfdfetesdf_asdfedudfcationasdfasdadfl/12342124?user_id=" + id + "&user_education_id=" + user_education_id;
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
                                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), CV.class);
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

    public static class ListViewUtil1 {
        private static final String CNAME = CV.ListViewUtil.class.getSimpleName();

        public static void setListViewHeightBasedOnChildren1(ListView listView) {
            /*ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                // pre-condition
                return;
            }

            int totalHeight = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();
            }

            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
            listView.requestLayout();*/
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)
                return;

            int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int totalHeight = 0;
            View view = null;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                view = listAdapter.getView(i, view, listView);
                if (i == 0)
                    view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

                view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += view.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }
}
