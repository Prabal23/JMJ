package com.jmj.app.journeymakerjobs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class AllJobPicChange extends AppCompatActivity {
    //public static final String UPLOAD_URL = "http://192.168.0.115/PictureBlood/upload_img.php";
    public static final String UPLOAD_URL_PIC = Utils.IP + "/direct/jasdfasdfob_podfasdfst_imdfasdageasasdfasd/12342124";
    private TextView imageChoose;
    private Button buttonUpload, buttonUpload1;
    private ImageView imageView, menu, back, signature;
    private Bitmap bitmap, bitmap1;
    private Uri filePath, filePath1;
    private String id = "", pic = "", sign = "";
    Typeface fontAwesomeFont;
    Bitmap originalImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_job_pic_change);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        id = getIntent().getStringExtra("job_id");
        pic = getIntent().getStringExtra("pic");
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, pic, Toast.LENGTH_SHORT).show();

        buttonUpload = (Button) findViewById(R.id.submit);
        imageChoose = (TextView) findViewById(R.id.t2);
        imageView = (ImageView) findViewById(R.id.logo);

        if (pic.equals("") || pic.contains("localhost")) {
            Uri uri = Uri.parse("android.resource://com.jmj.app.journeymakerjobs/drawable/jmj");
            if (uri != null) {
                bitmap = decodeUri(uri, 70);
                originalImage = getResizedBitmap(bitmap, 134, 304);
                //imageView.setImageResource(R.drawable.member_icon);
            }
            //Toast.makeText(this, "Lol", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"p1 "+ pic, Toast.LENGTH_SHORT).show();
        }
        else {
            Picasso.with(AllJobPicChange.this).load(pic).into(imageView);
            //Glide.with(MyProfile.this).load(picture).into(profile_pic);
            //Toast.makeText(this, "Lol 1", Toast.LENGTH_SHORT).show();
            //Toast.makeText(this,"p2 "+ pic, Toast.LENGTH_SHORT).show();
            try {
                URL url = new URL(pic);
                HttpURLConnection connection = null;
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                originalImage = getResizedBitmap(bitmap, 134, 304);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPic();
            }
        });

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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    filePath = data.getData();

                    if (filePath != null) {

                        bitmap = decodeUri(filePath, 70);
                        originalImage = getResizedBitmap(bitmap, 134, 304);
                        imageView.setImageBitmap(bitmap);
                    }
                    //super.onActivityResult(requestCode, resultCode, data);
                }
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
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);
        return resizedBitmap;
    }

    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);
            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;
            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }
            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
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

    public void uploadPic() {
        class UploadImage extends AsyncTask<Bitmap, Void, String> {

            ProgressDialog loading;
            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(AllJobPicChange.this, "Uploading...", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                TextView res = (TextView) findViewById(R.id.res);
                if (s.contains("Success") || s.contains("success") || s.contains("ok") || s.contains("OK") || s.contains("Ok")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText("Uploaded Successfully");
                    res.setTextColor(Color.parseColor("#006400"));
                    Toast.makeText(AllJobPicChange.this, "Picture Uploaded Successfully", Toast.LENGTH_LONG).show();
                    finish();
                    Intent intent = new Intent(getBaseContext(), EmployerAllJobs.class);
                    startActivity(intent);
                }
                /*if (s.contains("সফলভাবে আপলোড করা হয়েছে") || s.contains("সফলভাবে আপডেট করা হয়েছে")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#006400"));
                }*/
                if (s.contains("success") || s.contains("Success")) {
                    res.setVisibility(View.VISIBLE);
                    res.setText(s + "");
                    res.setTextColor(Color.parseColor("#e83d3d"));
                }
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                //Toast.makeText(PicChange.this, uploadImage, Toast.LENGTH_SHORT).show();
                String userid = id;

                HashMap<String, String> data = new HashMap<>();

                data.put("images", uploadImage);
                data.put("job_id", userid);
                //data.put("image", uploadImage);
                //data.put("userid", userid);
                String result = rh.sendPostRequest(UPLOAD_URL_PIC, data);

                return result;
            }
        }

        UploadImage ui = new UploadImage();
        ui.execute(originalImage);
    }
}
