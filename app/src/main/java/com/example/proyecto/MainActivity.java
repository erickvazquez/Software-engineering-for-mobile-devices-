package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1=(Button)findViewById(R.id.iniciar);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchactivity = new Intent(MainActivity.this, Login.class);
                startActivity(launchactivity);
            }

        });

        button2=(Button)findViewById(R.id.registrarse);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchactivity2 = new Intent(MainActivity.this, Registrarse.class);
                startActivity(launchactivity2);
            }

        });
    }

    @Override
    public void onBackPressed() {

    }

}
