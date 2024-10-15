package com.aysenurdemezoglu.firsttaskandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button buttonRest;
    private Button buttonSoap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonRest = findViewById(R.id.restButton);
        buttonSoap = findViewById(R.id.soapButton);

        buttonRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RestActivity.class);
                startActivity(intent);
            }
        });

        buttonSoap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SoapActivity.class);
                startActivity(intent);
            }
        });
    }
}
