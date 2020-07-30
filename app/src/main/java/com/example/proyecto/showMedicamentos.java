package com.example.proyecto;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class showMedicamentos extends AppCompatActivity{

    int idusuario, idMedicamento, idNotificacion;
    private Toolbar toolbar;
    private RecyclerView recyclerviewMedicamento;
    private RecyclerViewAdaptador adaptadorMedicamento;
    List<Medicamento> medicamentos;
    private Bitmap bmp;
    NotificationCompat.Builder notificacion;

    Calendar today = Calendar.getInstance();
    ArrayList<Calendar> calendarArray = new ArrayList<Calendar>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showmedicamentos);
        Bundle bundle = getIntent().getExtras();
        idusuario = bundle.getInt("idusuario");
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor usuario = db.rawQuery ("Select * from usuario where idusuario=" + idusuario, null);
        usuario.moveToFirst();
        getSupportActionBar().setTitle("Usuario: " + usuario.getString(1));
        usuario.close();
        db.close();

        recyclerviewMedicamento = (RecyclerView)findViewById(R.id.recycler_medicamento);
        recyclerviewMedicamento.setLayoutManager(new LinearLayoutManager(this));

        adaptadorMedicamento = new RecyclerViewAdaptador(this, obtenerMedicamentos());
        recyclerviewMedicamento.setAdapter(adaptadorMedicamento);



    }


    public List<Medicamento> obtenerMedicamentos(){


         medicamentos = new ArrayList<>();
        String nombre, tipo, padecimiento, doctor, dias, periodo, horario, dosis;
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila = db.rawQuery ("Select * from medicamento where idusuario=" + idusuario, null);
        Cursor fila2, fila3;
        while(fila.moveToNext()){

            if(fila.getBlob(7) != null)
                bmp = BitmapFactory.decodeByteArray(fila.getBlob(7), 0, fila.getBlob(7).length);
            idMedicamento = Integer.parseInt(fila.getString(0));
            nombre = "Nombre: " + fila.getString(2);
            tipo = "Tipo: " + fila.getString(3);
            padecimiento = "Padecimiento: " + fila.getString(4);
            doctor = "Doctor: " + fila.getString(5);
            periodo = "Periodo: " + fila.getString(9) + " " + fila.getString(8);
            dosis = "Dosis: " + fila.getString(10);
            dias = "Dias: ";
            horario = "Horario: ";



            fila3 = db.rawQuery ("Select * from horario where idmed=" + fila.getString(0), null);
            String[] horaPartes = new String[0];
            if(fila3.moveToFirst()){
                horario += fila3.getString(2);
                horaPartes = fila3.getString(2).split(":");
            }


            final String [] horaPartesToUse = horaPartes;
            final String nombreToUse = nombre;
            final String horarioToUse = horario;


            fila2 = db.rawQuery ("Select * from dias where idmedicamento=" + idMedicamento, null);
            if(fila2.moveToFirst())
                for(int i = 2; i < 9; i++){
                    if(Integer.parseInt(fila2.getString(i)) == 1){
                        switch(i) {
                            case 2:
                                dias += "lunes, ";
                                break;

                            case 3:
                                dias += "martes, ";
                                break;

                            case 4:
                                dias += "miercoles, ";
                                break;

                            case 5:
                                dias += "jueves, ";
                                break;

                            case 6:
                                dias += "viernes, ";

                                break;

                            case 7:
                                dias += "sabado, ";

                                break;

                            case 8:
                                dias += "domingo, ";
                                break;
                        }
                    }
                }
            medicamentos.add(new Medicamento(nombre, tipo, padecimiento, doctor, dias, periodo, horario, idusuario, idMedicamento, bmp, dosis));
            System.out.println("Nombre del medicamento: " + fila.getString(2));
        }
        db.close();
        return medicamentos;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LayoutInflater inflater = getLayoutInflater();
        getMenuInflater().inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.calendario:
                Toast.makeText(this, "Calendario", Toast.LENGTH_SHORT).show();
                Intent launchactivity0 = new Intent(showMedicamentos.this, Calendario.class);
                launchactivity0.putExtra("idusuario", idusuario);
                startActivity(launchactivity0);
                break;

            case R.id.registrarMedicamento:
                Toast.makeText(this, "Registrar medicamento", Toast.LENGTH_SHORT).show();
                Intent launchactivity = new Intent(showMedicamentos.this, Crear.class);
                launchactivity.putExtra("idusuario", idusuario);
                startActivity(launchactivity);
                break;
            case R.id.cerrarsesion:
                Toast.makeText(this, "Hasta luego", Toast.LENGTH_SHORT).show();
                Intent launchactivity2 = new Intent(showMedicamentos.this, MainActivity.class);
                startActivity(launchactivity2);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void setAlarm(Calendar calendar, String event, String time, int RequestCode){
        Intent intent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("event", event);
        intent.putExtra("time", time);
        intent.putExtra("id", RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)this.getApplicationContext().getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }


}
