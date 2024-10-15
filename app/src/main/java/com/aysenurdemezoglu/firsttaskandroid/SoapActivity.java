package com.aysenurdemezoglu.firsttaskandroid;

import android.icu.util.ULocale;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aysenurdemezoglu.firsttaskandroid.model.Person;
import com.aysenurdemezoglu.firsttaskandroid.model.PersonRequest;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class SoapActivity extends AppCompatActivity {
    EditText editTextName;
    EditText editTextSurname;
    EditText editTextBirthYear;
    EditText editTextTcNo;
    Button buttonCheck;

    private static final String NAMESPACE = "http://example.com/FirstTask/service";
    private static final String METHOD_NAME = "tcnodogrulaRequest";
    private static final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
    private static final String URL = "http://10.0.2.2:8081/service/TcDogrula.wsdl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soap);

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextBirthYear = findViewById(R.id.editTextBirthYear);
        editTextTcNo = findViewById(R.id.editTextTcNo);
        buttonCheck = findViewById(R.id.buttonCheck);

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad = editTextName.getText().toString();
                String soyad = editTextSurname.getText().toString();
                String dogumyili = editTextBirthYear.getText().toString();
                String tc = editTextTcNo.getText().toString();

                if (ad.isEmpty() || soyad.isEmpty() || dogumyili.isEmpty() || tc.isEmpty()) {
                    Toast.makeText(SoapActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                } else {
                    new SoapAsyncTask().execute(tc, ad, soyad, dogumyili);
                }
            }
        });
    }

    private class SoapAsyncTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String tc = params[0];
            String ad = params[1];
            String soyad = params[2];
            String dogumyili = params[3];


            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("tc", Long.parseLong(tc));
                request.addProperty("ad", ad);
                request.addProperty("soyad", soyad);
                request.addProperty("dogumyili", Integer.parseInt(dogumyili));

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transport = new HttpTransportSE(URL);
                transport.call(SOAP_ACTION, envelope);

                System.out.println(envelope.getResponse().toString());
                SoapObject response = (SoapObject) envelope.getResponse();
                SoapObject tcnodogrulaResponse = (SoapObject) response.getProperty(0);
                String resultStr = tcnodogrulaResponse.getProperty("result").toString();
                return Boolean.parseBoolean(resultStr);


            } catch (Exception e) {
                Log.e("SOAP", "Hata: " + e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(SoapActivity.this, "Kişi doğrulandı", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SoapActivity.this, "Kişi doğrulanamadı", Toast.LENGTH_SHORT).show();
            }
        }
    }
}