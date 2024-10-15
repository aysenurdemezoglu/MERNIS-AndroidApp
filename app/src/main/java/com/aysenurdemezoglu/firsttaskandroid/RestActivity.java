package com.aysenurdemezoglu.firsttaskandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aysenurdemezoglu.firsttaskandroid.model.Person;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextBirthYear;
    private EditText editTextTckn;
    private Button buttonSubmit;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextBirthYear = findViewById(R.id.editTextBirthYear);
        editTextTckn = findViewById(R.id.editTextTckn);
        buttonSubmit = findViewById(R.id.buttonCheck);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder().addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/json; charset=utf-8")
                            .build();
                    return chain.proceed(request);
                }).build())
                .build();


        apiService = retrofit.create(ApiService.class);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String birthYear = editTextBirthYear.getText().toString();
                String tckn = editTextTckn.getText().toString();

                if (name.isEmpty() || surname.isEmpty() || birthYear.isEmpty() || tckn.isEmpty()) {
                    Toast.makeText(RestActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                } else {
                    makeApiCall(name, surname, tckn, birthYear);
                }
            }
        });
    }

    private void makeApiCall(String name, String surname, String tckn, String birthYear) {
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setBirthYear(Integer.parseInt(birthYear));
        person.setTc(Long.parseLong(tckn));

        Call<Person> call = apiService.checkUser(person);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String result = response.body().getName();

                    if (!result.isEmpty()) {
                        Log.d("API", "Kullanıcı doğrulandı ve kaydedildi.");
                        Toast.makeText(RestActivity.this, "Kullanıcı doğrulandı ve kaydedildi.", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("API", "Kullanıcı doğrulanamadı.");
                        Toast.makeText(RestActivity.this, "Kullanıcı doğrulanamadı.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RestActivity.this, "kullanıcı bulunamadı", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Toast.makeText(RestActivity.this, "Başarısız: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
