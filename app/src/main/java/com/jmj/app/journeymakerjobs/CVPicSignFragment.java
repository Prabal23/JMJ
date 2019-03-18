package com.jmj.app.journeymakerjobs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;

public class CVPicSignFragment extends Fragment {
    public static final String UPLOAD_URL_PIC = Utils.IP + "direct/psdfhotsdfasdfo_usdfploasdfadasdfasdf/12342124";
    public static final String UPLOAD_URL_SIGN = Utils.IP + "direct/sasdigsdnasdtuasdre_uasdpldoadasdasd/12342124";
    public static final String UPLOAD_KEY = "image";
    private int PICK_IMAGE_REQUEST = 1;
    private TextView imageChoose, res, comps, comps1;
    private Button buttonUpload, buttonUpload1;
    private ImageView imageView, menu, back, signature;
    private Bitmap bitmap, bitmap1;
    private Uri filePath, filePath1;
    private String id = "", pic = "", sign = "", results = "";
    Typeface fontAwesomeFont;
    Bitmap originalImage;
    int width;
    int height;
    int newWidth = 200;
    int newHeight = 80, counts = 0, counts1 = 0;
    Matrix matrix;
    Bitmap resizedBitmap;
    float scaleWidth;
    float scaleHeight;
    ByteArrayOutputStream outputStream;
    ArrayList<ProductCV> arrayListCV;

    public CVPicSignFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.cv_picsign_fragment, container, false);

        fontAwesomeFont = Typeface.createFromAsset(getActivity().getAssets(), "fa-solid-900.ttf");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Utils.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        if (sharedPreferences.contains(Utils.LOGIN_SHARED_PREF)) {
            results = (sharedPreferences).getString(Utils.LOGIN_SHARED_PREF, "");
        }
        String[] result = results.split(" ");
        id = result[0];

        buttonUpload = (Button) myview.findViewById(R.id.submit);
        buttonUpload1 = (Button) myview.findViewById(R.id.submit1);
        imageChoose = (TextView) myview.findViewById(R.id.t2);
        imageView = (ImageView) myview.findViewById(R.id.logo);
        signature = (ImageView) myview.findViewById(R.id.signature);
        comps = (TextView) myview.findViewById(R.id.comps);
        comps1 = (TextView) myview.findViewById(R.id.comps1);

        arrayListCV = new ArrayList<>();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //new ReadJSON().execute(Utils.IP + "direct/appasdfasdfasliasdfasded_jasdfasdfob_lasdfasdfistasdfasdf/12342124?user_id=6668");
                new ReadJSONCV().execute(Utils.IP + "direct/asdfbasiasdfac_deasdfastailsdfasdfasdf/12342124?user_id=" + id);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            }
        });

        imageChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 2);
            }
        });

        signature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 3);
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
            }
        });
        buttonUpload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadSign();
            }
        });

        res = (TextView) myview.findViewById(R.id.res);

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

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject productObject = jsonArray.getJSONObject(i);
                    arrayListCV.add(new ProductCV(
                            productObject.getString("photo_url"),
                            productObject.getString("signature_url"),
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

                    /*pic = arrayListCV.get(i).getPhoto();
                    if (!pic.equals("") || !pic.contains("localhost")) {
                        counts += 100;
                    }
                    sign = arrayListCV.get(i).getSignature();
                    if (!sign.equals("") && !sign.contains("localhost")) {
                        counts += 100;
                    }*/

                    pic = arrayListCV.get(i).getPhoto();
                    //Toast.makeText(getContext(), pic, Toast.LENGTH_LONG).show();
                    if (pic.equals("") || pic.contains("localhost") || pic.equals("http://www.journeymakerjobs.com/images/jobseeker/jobseekerimage/jobseekeroldimage")) {
                        counts = 0;
                        Uri uri = Uri.parse("android.resource://com.jmj.app.journeymakerjobs/drawable/profile_icon");
                        if (uri != null) {
                            bitmap = decodeUri(uri, 100);
                            originalImage = getResizedBitmap(bitmap, 581, 472);
                        }
                        imageView.setImageDrawable(getResources().getDrawable(R.drawable.member_icon));
                    } else {
                        counts = 100;
                        try {
                            URL url = new URL(pic);
                            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            //bitmap = BitmapFactory.decodeStream(input);
                            originalImage = getResizedBitmap(bitmap, 581, 472);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Picasso.get().load(pic).into(imageView);
                    }
                    sign = arrayListCV.get(i).getSignature();
                    //Toast.makeText(getContext(), sign, Toast.LENGTH_LONG).show();
                    if (sign.equals("") || sign.contains("localhost") || sign.equals("http://www.journeymakerjobs.com/images/jobseeker/jobseekerimage/jobseekeroldimage")) {
                        counts1 = 0;
                        Uri uri1 = Uri.parse("android.resource://com.jmj.app.journeymakerjobs/drawable/signature1");
                        if (uri1 != null) {
                            bitmap1 = decodeUri1(uri1, 100);
                            //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
                            resizedBitmap = getResizedBitmap(bitmap1, 150, 200);
                        }
                        signature.setImageDrawable(getResources().getDrawable(R.drawable.signature1));
                    } else {
                        counts1 = 100;
                        try {
                            URL url = new URL(sign);
                            bitmap1 = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            //bitmap1 = BitmapFactory.decodeStream(input);
                            resizedBitmap = getResizedBitmap(bitmap1, 150, 200);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Picasso.get().load(sign).into(signature);
                    }

                    comps.setText(counts + "% Completed");
                    comps1.setText(counts1 + "% Completed");
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    filePath = data.getData();

                    if (filePath != null) {

                        bitmap = decodeUri(filePath, 100);
                        originalImage = getResizedBitmap(bitmap, 581, 472);
                        imageView.setImageBitmap(bitmap);
                    }
                    //super.onActivityResult(requestCode, resultCode, data);
                }
                break;

            case 3:
                if (resultCode == RESULT_OK) {
                    filePath1 = data.getData();

                    if (filePath1 != null) {

                        bitmap1 = decodeUri1(filePath1, 100);
                        resizedBitmap = getResizedBitmap(bitmap1, 150, 200);
                        signature.setImageBitmap(bitmap1);
                    }
                    //super.onActivityResult(requestCode, resultCode, data);
                }
                break;
            default:
                break;
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // RECREATE THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Bitmap decodeUri1(Uri selectedImage, int REQUIRED_SIZE) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o);
            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(selectedImage), null, o2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public String getStringImage1(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadPic() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if (s.contains("Success") || s.contains("success") || s.contains("ok") || s.contains("OK") || s.contains("Ok")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText("Uploaded Successfully");
                    res.setTextColor(Color.parseColor("#006400"));
                    Toast.makeText(getActivity(), "Picture Uploaded Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getBaseContext(), JobSeekerHome.class);
                    startActivity(intent);
                }
                /*if (s.contains("সফলভাবে আপলোড করা হয়েছে") || s.contains("সফলভাবে আপডেট করা হয়েছে")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#006400"));
                }*/
                /*if (s.contains("success") || s.contains("Success")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#e83d3d"));
                }*/
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                //Toast.makeText(PicChange.this, uploadImage, Toast.LENGTH_SHORT).show();
                String userid = id;

                HashMap<String, String> data = new HashMap<>();

                data.put("user_file_3", uploadImage);
                data.put("user_id", userid);
                String result = rh.sendPostRequest(UPLOAD_URL_PIC, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(originalImage);
    }

    public void uploadSign() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(), "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if (s.contains("Success") || s.contains("success") || s.contains("ok") || s.contains("OK") || s.contains("Ok")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText("Uploaded Successfully");
                    res.setTextColor(Color.parseColor("#006400"));
                    Toast.makeText(getActivity(), "Signature Uploaded Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity().getBaseContext(), JobSeekerHome.class);
                    startActivity(intent);
                }
                /*if (s.contains("সফলভাবে আপলোড করা হয়েছে") || s.contains("সফলভাবে আপডেট করা হয়েছে")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#006400"));
                }*/
                /*if (s.contains("success") || s.contains("Success")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#e83d3d"));
                }*/
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage1(bitmap);
                //Toast.makeText(PicChange.this, uploadImage, Toast.LENGTH_SHORT).show();
                String userid = id;

                HashMap<String, String> data = new HashMap<>();

                data.put("user_file_3", uploadImage);
                data.put("user_id", userid);
                String result = rh.sendPostRequest(UPLOAD_URL_SIGN, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(resizedBitmap);
    }

}
