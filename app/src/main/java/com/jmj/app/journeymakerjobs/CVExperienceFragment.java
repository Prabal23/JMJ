package com.jmj.app.journeymakerjobs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;


public class CVExperienceFragment extends Fragment {
    private ListView listView, lv;
    Typeface fontAwesomeFont;
    ArrayList<ProductCV> arrayListCV;
    ArrayList<ProductExpertiseTraining> arrayList, arrayList2, arrayList3;
    String str1 = "0000-00-00", end1 = "0000-00-00", comcom, desdes, resres, usern, name, id1 = "", res, id, gender = "", marriage = "", religion = "", bd, user, fulln, basa, mail, con, altcon, baba, ma, lingo, bia, dhormo, bdid, cobj, facebook;
    TextInputLayout com, des, father, mother, resp;
    EditText com1, des1, fathername, mothername, resp1;
    TextView str, end;
    int counting;
    TextView nodata, count, degreeid, not_available, comps;
    GifImageView gifImageView;
    int mYear, mMonth, mDay, cnt = 0, c3 = 0;
    CheckBox checkBox;

    public CVExperienceFragment() {
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
        final View myview = inflater.inflate(R.layout.cv_experience_fragment, container, false);
        fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        user = result[2];

        com = (TextInputLayout) myview.findViewById(R.id.input_com);
        des = (TextInputLayout) myview.findViewById(R.id.input_des);
        resp = (TextInputLayout) myview.findViewById(R.id.input_res);

        com1 = (EditText) myview.findViewById(R.id.sign_com);
        des1 = (EditText) myview.findViewById(R.id.sign_des);
        resp1 = (EditText) myview.findViewById(R.id.sign_res);

        TextView apply = (TextView) myview.findViewById(R.id.apply);
        TextView down = (TextView) myview.findViewById(R.id.down);
        TextView down1 = (TextView) myview.findViewById(R.id.down1);
        final TextView add = (TextView) myview.findViewById(R.id.add);
        apply.setTypeface(fontAwesomeFont);
        down.setTypeface(fontAwesomeFont);
        down1.setTypeface(fontAwesomeFont);
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

        str = (TextView) myview.findViewById(R.id.str);
        LinearLayout str_linear = (LinearLayout) myview.findViewById(R.id.l3);
        str_linear.setOnClickListener(new View.OnClickListener() {
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
                                str.setText("Job Start : " + year + "-" + d + "-" + day);
                                str1 = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        end = (TextView) myview.findViewById(R.id.end);
        final LinearLayout end_linear = (LinearLayout) myview.findViewById(R.id.l4);
        end_linear.setOnClickListener(new View.OnClickListener() {
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
                                end.setText("Job End : " + year + "-" + d + "-" + day);
                                end1 = year + "-" + d + "-" + day;
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        checkBox = (CheckBox) myview.findViewById(R.id.check);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                if (((CheckBox) view).isChecked()) {
                    end1 = "0000-00-00";
                    end_linear.setClickable(false);
                    end.setPaintFlags(end.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    end_linear.setClickable(true);
                    end.setPaintFlags(end.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        TextView cont = (TextView) myview.findViewById(R.id.cont);
        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++;
                if (cnt % 2 != 0) {
                    end1 = "0000-00-00";
                    checkBox.setChecked(true);
                    end_linear.setClickable(false);
                    end.setPaintFlags(end.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    checkBox.setChecked(false);
                    end_linear.setClickable(true);
                    end.setPaintFlags(end.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            }
        });
        lv = (ListView) myview.findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON().execute(Utils.IP + "direct/emasdploymasdent_detsdaasdilasds/12342124?user_id=" + id);
            }
        });
        LinearLayout editclick = (LinearLayout) myview.findViewById(R.id.r5);
        editclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comcom = com1.getText().toString();
                if (comcom.contains(" ")) {
                    comcom = comcom.replace(" ", "%20");
                }
                comcom = comcom.replaceAll("\\r?\\n", "\\u000D\\000A");
                desdes = des1.getText().toString();
                if (desdes.contains(" ")) {
                    desdes = desdes.replace(" ", "%20");
                }
                desdes = desdes.replaceAll("\\r?\\n", "\\u000D\\000A");
                resres = resp1.getText().toString();
                if (resres.contains(" ")) {
                    resres = resres.replace(" ", "%20");
                }
                resres = resres.replaceAll("\\r?\\n", "\\u000D\\000A");

                String URL = Utils.IP + "direct/emdsfploymasdfent_dasdetasdfils_updatdsfe_ddfataasf/12342124?user_id=" + id + "&company_name=" + comcom + "&designation=" + desdes + "&responsibility=" + resres + "&job_start=" + str1 + "&job_end=" + end1;
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
                        params.put("company_name", comcom);
                        params.put("designation", desdes);
                        params.put("responsibility", resres);
                        params.put("job_start", str1);
                        params.put("job_end", end1);
                        return params;
                    }
                };

                // Creating RequestQueue.
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                // Adding the StringRequest object into requestQueue.
                requestQueue.add(stringRequest);
            }
        });

        not_available = (TextView) myview.findViewById(R.id.not_available);
        comps = (TextView) myview.findViewById(R.id.comps);

        return myview;
    }

    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("employment_details");
                c3 = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductExpertiseTraining(
                            productObject.getString("user_work_id"),
                            productObject.getString("company_name"),
                            productObject.getString("designation"),
                            productObject.getString("job_start"),
                            productObject.getString("job_end"),
                            productObject.getString("responsibility")
                    ));
                    c3++;
                    if (c3 != 0) {
                        comps.setText("100% Completed");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterExp adapter = new CustomListAdapterExp(
                    getActivity().getApplicationContext(), R.layout.cv_exp_modify_lists, arrayList
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

    public class CustomListAdapterExp extends ArrayAdapter<ProductExpertiseTraining> {

        ArrayList<ProductExpertiseTraining> products;
        Context context;
        int resource;
        Typeface fontAwesomeFont;

        public CustomListAdapterExp(Context context, int resource, ArrayList<ProductExpertiseTraining> products) {
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
                convertView = layoutInflater.inflate(R.layout.cv_exp_modify_lists, null, true);

            }
            ProductExpertiseTraining product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            TextView ed = (TextView) convertView.findViewById(R.id.edit);
            TextView d = (TextView) convertView.findViewById(R.id.delete);
            ed.setTypeface(fontAwesomeFont);
            d.setTypeface(fontAwesomeFont);
            LinearLayout edit = (LinearLayout) convertView.findViewById(R.id.e);
            LinearLayout delete = (LinearLayout) convertView.findViewById(R.id.d);
            TextView down = (TextView) convertView.findViewById(R.id.downpic);
            down.setTypeface(fontAwesomeFont);
            try {
                TextView date = (TextView) convertView.findViewById(R.id.date);
                TextView date1 = (TextView) convertView.findViewById(R.id.date1);
                String address = product.getJob_str();
                String address1 = product.getJob_end();
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                byte[] b2 = address1.getBytes("UTF-8");
                String add1 = new String(b2, "UTF-8");
                date.setText(add);
                if (add1.equals("0000-00-00")) {
                    date1.setText("Continuing");
                } else {
                    date1.setText(add1);
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView org = (TextView) convertView.findViewById(R.id.org);
                String address = product.getCom_name();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                org.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView des = (TextView) convertView.findViewById(R.id.des);
                String address = product.getDes();
                address = address.replaceAll("u000D000A", "\n");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                des.setText(add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            try {
                TextView resp = (TextView) convertView.findViewById(R.id.resp);
                String address = product.getResponse();
                address = address.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                resp.setText(Html.fromHtml(add));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id1 = arrayList.get(position).getUser_work_id();
                    String cn = arrayList.get(position).getCom_name();
                    String des = arrayList.get(position).getDes();
                    String str = arrayList.get(position).getJob_str();
                    String end = arrayList.get(position).getJob_end();
                    String res = arrayList.get(position).getResponse();

                    Intent intent = new Intent(getContext(), EditExperience.class);
                    intent.putExtra("uid", id);
                    intent.putExtra("id", id1);
                    intent.putExtra("cn", cn);
                    intent.putExtra("des", des);
                    intent.putExtra("str", str);
                    intent.putExtra("end", end);
                    intent.putExtra("resp", res);
                    startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = arrayList.get(position).getUser_work_id();
                    //Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                    DeleteJob alert1 = new DeleteJob();
                    alert1.showDialog((AppCompatActivity) getActivity(), id);
                }
            });

            return convertView;
        }
    }

    public class DeleteJob {

        public void showDialog(AppCompatActivity activity, final String user_work_id) {
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

                    String URL = Utils.IP + "direct/desdfletesdfr_empasdfloymsdfadsenasdfasdt/12342124?user_id=" + id + "&user_work_id=" + user_work_id;
                    //Toast.makeText(getContext(), URL, Toast.LENGTH_LONG).show();
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
