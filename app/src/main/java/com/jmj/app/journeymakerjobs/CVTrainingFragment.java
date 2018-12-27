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


public class CVTrainingFragment extends Fragment {
    private ListView listView, lv;
    Typeface fontAwesomeFont;
    ArrayList<ProductCV> arrayListCV;
    ArrayList<ProductExpertiseTraining> arrayList, arrayList2, arrayList3;
    String tittit, insins, locloc, durdur, detdet, usern, name, id1 = "", res, id, gender = "", marriage = "", religion = "", bd, user, fulln, basa, mail, con, altcon, baba, ma, lingo, bia, dhormo, bdid, cobj, facebook;
    TextInputLayout tit, ins, loc, dur, det;
    EditText tit1, ins1, loc1, dur1, det1;
    TextView str, end;
    int counting;
    TextView nodata, count, degreeid, not_available, comps;
    GifImageView gifImageView;
    int mYear, mMonth, mDay, c4 = 0;

    public CVTrainingFragment() {
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
        final View myview = inflater.inflate(R.layout.cv_training_fragment, container, false);
        fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            res = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = res.split(" ");
        id = result[0];
        user = result[2];

        tit = (TextInputLayout) myview.findViewById(R.id.input_tit);
        ins = (TextInputLayout) myview.findViewById(R.id.input_ins);
        loc = (TextInputLayout) myview.findViewById(R.id.input_loc);
        dur = (TextInputLayout) myview.findViewById(R.id.input_dur);
        det = (TextInputLayout) myview.findViewById(R.id.input_det);

        tit1 = (EditText) myview.findViewById(R.id.sign_tit);
        ins1 = (EditText) myview.findViewById(R.id.sign_ins);
        loc1 = (EditText) myview.findViewById(R.id.sign_loc);
        dur1 = (EditText) myview.findViewById(R.id.sign_dur);
        det1 = (EditText) myview.findViewById(R.id.sign_det);

        TextView apply = (TextView) myview.findViewById(R.id.apply);
        final TextView add = (TextView) myview.findViewById(R.id.add);
        apply.setTypeface(fontAwesomeFont);
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

        lv = (ListView) myview.findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSON().execute(Utils.IP + "direct/trasfasdfsdfining_desdftasdfailsdfs/12342124?user_id=" + id);

            }
        });

        LinearLayout editclick = (LinearLayout) myview.findViewById(R.id.r5);
        editclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tittit = tit1.getText().toString();
                if (tittit.contains(" ")) {
                    tittit = tittit.replace(" ", "%20");
                }
                tittit = tittit.replaceAll("\\r?\\n", "\\u000D\\000A");
                insins = ins1.getText().toString();
                if (insins.contains(" ")) {
                    insins = insins.replace(" ", "%20");
                }
                insins = insins.replaceAll("\\r?\\n", "\\u000D\\000A");
                locloc = loc1.getText().toString();
                if (locloc.contains(" ")) {
                    locloc = locloc.replace(" ", "%20");
                }
                locloc = locloc.replaceAll("\\r?\\n", "\\u000D\\000A");
                durdur = dur1.getText().toString();
                if (durdur.contains(" ")) {
                    durdur = durdur.replace(" ", "%20");
                }
                durdur = durdur.replaceAll("\\r?\\n", "\\u000D\\000A");
                detdet = det1.getText().toString();
                if (detdet.contains(" ")) {
                    detdet = detdet.replace(" ", "%20");
                }
                detdet = detdet.replaceAll("\\r?\\n", "\\u000D\\000A");

                String URL = Utils.IP + "direct/traasdfindfing_dasdfeasdftails_udfpdatsdfe_dataadsf/12342124?user_id=" + id + "&training_title=" + tittit + "&training_institution=" + insins + "&training_topic_details=" + detdet + "&location=" + locloc + "&duration=" + durdur;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String ServerResponse) {

                                //Toast.makeText(getContext(), ServerResponse, Toast.LENGTH_SHORT).show();
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
                        params.put("training_title", tittit);
                        params.put("training_institution", insins);
                        params.put("training_topic_details", detdet);
                        params.put("location", locloc);
                        params.put("duration", durdur);
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
                JSONArray jsonArray = jsonObject.getJSONArray("training_details");
                c4 = 0;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayList.add(new ProductExpertiseTraining(
                            productObject.getString("user_training_id"),
                            productObject.getString("training_title"),
                            productObject.getString("topic_details"),
                            productObject.getString("institution"),
                            productObject.getString("location"),
                            productObject.getString("duration")
                    ));
                    c4++;
                    if (c4 != 0) {
                        comps.setText("100% Completed");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapterExp adapter = new CustomListAdapterExp(
                    getActivity().getApplicationContext(), R.layout.cv_training_modify_lists, arrayList
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
                convertView = layoutInflater.inflate(R.layout.cv_training_modify_lists, null, true);

            }
            ProductExpertiseTraining product = getItem(position);

            fontAwesomeFont = Typeface.createFromAsset(getContext().getAssets(), "fa-solid-900.ttf");
            TextView ed = (TextView) convertView.findViewById(R.id.edit);
            TextView d = (TextView) convertView.findViewById(R.id.delete);
            ed.setTypeface(fontAwesomeFont);
            d.setTypeface(fontAwesomeFont);
            LinearLayout edit = (LinearLayout) convertView.findViewById(R.id.e);
            LinearLayout delete = (LinearLayout) convertView.findViewById(R.id.d);
            try {
                TextView title = (TextView) convertView.findViewById(R.id.title);
                TextView ins = (TextView) convertView.findViewById(R.id.ins);
                TextView loc = (TextView) convertView.findViewById(R.id.loc);
                TextView dur = (TextView) convertView.findViewById(R.id.dur);
                TextView des = (TextView) convertView.findViewById(R.id.des);
                String address = product.getCom_name();
                address = address.replaceAll("u000D000A", "\n");
                String address1 = product.getDes();
                address1 = address1.replaceAll("u000D000A", "\n");
                String address2 = product.getJob_str();
                address2 = address2.replaceAll("u000D000A", "\n");
                String address3 = product.getJob_end();
                address3 = address3.replaceAll("u000D000A", "\n");
                String address4 = product.getResponse();
                address4 = address4.replaceAll("u000D000A", "<br/>");
                byte[] b1 = address.getBytes("UTF-8");
                String add = new String(b1, "UTF-8");
                title.setText(add);
                des.setText(address1);
                ins.setText(address2);
                loc.setText(address3);
                dur.setText(Html.fromHtml(address4));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id1 = arrayList.get(position).getUser_work_id();
                    String title = arrayList.get(position).getCom_name();
                    String des = arrayList.get(position).getDes();
                    String ins = arrayList.get(position).getJob_str();
                    String loc = arrayList.get(position).getJob_end();
                    String dur = arrayList.get(position).getResponse();

                    Intent intent = new Intent(getContext(), EditTraining.class);
                    intent.putExtra("uid", id);
                    intent.putExtra("id", id1);
                    intent.putExtra("title", title);
                    intent.putExtra("des", des);
                    intent.putExtra("ins", ins);
                    intent.putExtra("loc", loc);
                    intent.putExtra("dur", dur);
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

        public void showDialog(AppCompatActivity activity, final String user_training_id) {
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

                    String URL = Utils.IP + "direct/tasdfraisdfnindfadfg_dsdfeletesdasdf/12342124?user_id=" + id + "&user_training_id=" + user_training_id;
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
            listView.requestLayout();
        }
    }

}
