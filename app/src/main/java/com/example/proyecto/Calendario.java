package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AbsListView;

import com.example.proyecto.R;

public class Calendario extends AppCompatActivity {

    CustomCalendarView customCalendarView;
    private int idusuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        idusuario = bundle.getInt("idusuario");
        setContentView(R.layout.activity_calendar_main);
        customCalendarView = (CustomCalendarView)findViewById(R.id.custom_calendar_view);
    }
}
