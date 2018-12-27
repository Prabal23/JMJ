package com.jmj.app.journeymakerjobs;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import pl.droidsonroids.gif.GifImageView;

public class EmployerReg extends AppCompatActivity {
    Typeface fontAwesomeFont;
    ListView listView;
    TextView nodata, count, degreeid;
    GifImageView gifImageView;
    ArrayList<ProductJobType> arrayList;
    String id = "", name, result, SetServerString;
    TextInputLayout iComName, iComName1, iComType, iName, iDesig, iMobile, iEmail, iConEmail, iPass, iConPass, iAddress;
    EditText sComName, sComName1, sComType, sName, sDesig, sMobile, sEmail, sConEmail, sPass, sConPass, sAddress;
    private HttpClient Client;
    HttpGet httpget;
    int count1, count2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employer_reg);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        fontAwesomeFont = Typeface.createFromAsset(getAssets(), "fa-solid-900.ttf");

        TextView namepic = (TextView) findViewById(R.id.namepic);
        namepic.setTypeface(fontAwesomeFont);
        TextView namepic1 = (TextView) findViewById(R.id.namepic1);
        namepic1.setTypeface(fontAwesomeFont);
        TextView mobpic = (TextView) findViewById(R.id.mobpic);
        mobpic.setTypeface(fontAwesomeFont);
        TextView userpic = (TextView) findViewById(R.id.userpic);
        userpic.setTypeface(fontAwesomeFont);
        TextView passpic = (TextView) findViewById(R.id.passpic);
        passpic.setTypeface(fontAwesomeFont);
        TextView conpasspic = (TextView) findViewById(R.id.conpasspic);
        conpasspic.setTypeface(fontAwesomeFont);
        TextView degtitlepic = (TextView) findViewById(R.id.degtitlepic);
        degtitlepic.setTypeface(fontAwesomeFont);
        TextView inspic = (TextView) findViewById(R.id.inspic);
        inspic.setTypeface(fontAwesomeFont);
        TextView respic = (TextView) findViewById(R.id.respic);
        respic.setTypeface(fontAwesomeFont);
        TextView passyearpic = (TextView) findViewById(R.id.passyearpic);
        passyearpic.setTypeface(fontAwesomeFont);
        TextView addpic = (TextView) findViewById(R.id.addpic);
        addpic.setTypeface(fontAwesomeFont);
        final TextView viewpass = (TextView) findViewById(R.id.viewpass);
        viewpass.setTypeface(fontAwesomeFont);
        final TextView viewpass1 = (TextView) findViewById(R.id.viewpass1);
        viewpass1.setTypeface(fontAwesomeFont);

        iComName = (TextInputLayout) findViewById(R.id.input_comname);
        iComName1 = (TextInputLayout) findViewById(R.id.input_comname1);
        iComType = (TextInputLayout) findViewById(R.id.input_type);
        iName = (TextInputLayout) findViewById(R.id.input_name);
        iDesig = (TextInputLayout) findViewById(R.id.input_des);
        iMobile = (TextInputLayout) findViewById(R.id.input_mob);
        iEmail = (TextInputLayout) findViewById(R.id.input_email);
        iConEmail = (TextInputLayout) findViewById(R.id.input_conemail);
        iPass = (TextInputLayout) findViewById(R.id.input_pass);
        iConPass = (TextInputLayout) findViewById(R.id.input_conpass);
        iAddress = (TextInputLayout) findViewById(R.id.input_add);

        sComName = (EditText) findViewById(R.id.sign_comname);
        sComName1 = (EditText) findViewById(R.id.sign_comname1);
        sComType = (EditText) findViewById(R.id.sign_type);
        sName = (EditText) findViewById(R.id.sign_name);
        sDesig = (EditText) findViewById(R.id.sign_des);
        sMobile = (EditText) findViewById(R.id.sign_mob);
        sEmail = (EditText) findViewById(R.id.sign_email);
        sConEmail = (EditText) findViewById(R.id.sign_conemail);
        sPass = (EditText) findViewById(R.id.sign_pass);
        sConPass = (EditText) findViewById(R.id.sign_conpass);
        sAddress = (EditText) findViewById(R.id.sign_add);

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

    }

    public void UserRegister() {
        String cn = sComName.getText().toString();
        cn = cn.replace(" ", "%20");
        String cn1 = sComName1.getText().toString();
        cn1 = cn1.replace(" ", "%20");
        String ct = sComType.getText().toString();
        ct = ct.replace(" ", "%20");
        String n = sName.getText().toString();
        n = n.replace(" ", "%20");
        String d = sDesig.getText().toString();
        d = d.replace(" ", "%20");
        d = d.replaceAll("\\r?\\n", "\\u000D\\000A");
        String m = sMobile.getText().toString();
        m = m.replace(" ", "%20");
        String em = sEmail.getText().toString();
        em = em.replace(" ", "%20");
        String ce = sConEmail.getText().toString();
        ce = ce.replace(" ", "%20");
        String p = sPass.getText().toString();
        p = p.replace(" ", "%20");
        String cp = sConPass.getText().toString();
        cp = cp.replace(" ", "%20");
        String a = sAddress.getText().toString();
        a = a.replace(" ", "%20");
        a = a.replaceAll("\\r?\\n", "\\u000D\\000A");

        if (!validateComName()) {
            return;
        }
        if (!validateComName1()) {
            return;
        }
        if (!validateType()) {
            return;
        }
        if (!validateName()) {
            return;
        }
        if (!validateDes()) {
            return;
        }
        if (!validateMobile()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateConEmail()) {
            return;
        }
        if (!validatePass()) {
            return;
        }
        if (!validateConPass()) {
            return;
        }
        if (!validateAdd()) {
            return;
        }
        if (!em.equals(ce)) {
            Toast.makeText(getBaseContext(), "Email doesn't match", Toast.LENGTH_SHORT).show();
        }
        if (!p.equals(cp)) {
            Toast.makeText(getBaseContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
        } else {
            Client = new DefaultHttpClient();

            String URL = Utils.IP + "direct/emdfgplosdfgyer_accodsfgunt_crsdfgeatsdfge/12342124?company_name=" + cn1 + "&company_real_name=" + cn + "&company_type=" + ct + "&concern_person=" + n + "&concern_designation=" + d + "&mobile=" + m + "&email=" + em + "&password=" + p + "&company_address=" + a;
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
            //Toast.makeText(this, "lsfjklk", Toast.LENGTH_SHORT).show();
        }
    }

    public void insert() {
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            SetServerString = Client.execute(httpget, responseHandler);
            Toast.makeText(EmployerReg.this, "Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateComName() {
        if (sComName.getText().toString().trim().isEmpty()) {
            iComName.setError("Company Name field is blank");
            requestFocus(sComName);
            return false;
        } else {
            iComName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateComName1() {
        if (sComName1.getText().toString().trim().isEmpty()) {
            iComName1.setError("Company Disguise Name field is blank");
            requestFocus(sComName1);
            return false;
        } else {
            iComName1.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateType() {
        if (sComType.getText().toString().trim().isEmpty()) {
            iComType.setError("Company Type field is blank");
            requestFocus(sComType);
            return false;
        } else {
            iComType.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateName() {
        if (sName.getText().toString().trim().isEmpty()) {
            iName.setError("Concern Person Name field is blank");
            requestFocus(sName);
            return false;
        } else {
            iName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateDes() {
        if (sDesig.getText().toString().trim().isEmpty()) {
            iDesig.setError("Concern Person Designation field is blank");
            requestFocus(sDesig);
            return false;
        } else {
            iDesig.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobile() {
        if (sMobile.getText().toString().trim().isEmpty()) {
            iMobile.setError("Mobile No field is blank");
            requestFocus(sMobile);
            return false;
        } else {
            iMobile.setErrorEnabled(false);
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

    private boolean validateConEmail() {
        if (sConEmail.getText().toString().trim().isEmpty()) {
            iConEmail.setError("Confirm Email field is blank");
            requestFocus(sConEmail);
            return false;
        } else {
            iConEmail.setErrorEnabled(false);
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
            iConPass.setError("Retype Password field is blank");
            requestFocus(sConPass);
            return false;
        } else {
            iConPass.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAdd() {
        if (sAddress.getText().toString().trim().isEmpty()) {
            iAddress.setError("Address field is blank");
            requestFocus(sAddress);
            return false;
        } else {
            iAddress.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
