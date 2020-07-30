package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class Editar extends AppCompatActivity {

    private EditText nombre, tipo, padecimiento, doctor, hora, numPeriodos, dosis;
    private RadioButton lu, ma, mi, ju, vi, sa, dom;
    private Spinner periodo;
    private int idusuario, idmedicamento;
    private Button btnImgMed, btnImgEnv, editarButton;
    final int REQUEST_CODE_GALLERY = 999;
    private ImageView imageView1, imageView2;
    private Bitmap myBitmap;
    boolean clicked1, clicked2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Bundle bundle = getIntent().getExtras();
        idusuario = bundle.getInt("idUsuario");
        idmedicamento = bundle.getInt("idMedicamento");

        nombre = (EditText)findViewById(R.id.nombreMedicamento);
        tipo = (EditText)findViewById(R.id.tipo);
        padecimiento = (EditText)findViewById(R.id.padecimiento);
        doctor = (EditText)findViewById(R.id.Doctor);
        hora = (EditText)findViewById(R.id.hora);
        numPeriodos = (EditText)findViewById(R.id.numero);
        btnImgMed = (Button)findViewById(R.id.button2);
        btnImgEnv = (Button)findViewById(R.id.button3);
        dosis = (EditText)findViewById(R.id.dosis);
        editarButton = (Button)findViewById(R.id.editarMedicamento);

        lu = (RadioButton)findViewById(R.id.lunes);
        ma = (RadioButton)findViewById(R.id.martes);
        mi = (RadioButton)findViewById(R.id.miercoles);
        ju = (RadioButton)findViewById(R.id.jueves);
        vi = (RadioButton)findViewById(R.id.viernes);
        sa = (RadioButton)findViewById(R.id.sabado);
        dom = (RadioButton)findViewById(R.id.domingo);
        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        periodo = (Spinner)findViewById(R.id.planets_spinner);


        btnImgMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Editar.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                clicked1 = true;
                clicked2 = false;
            }
        });

        btnImgEnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(Editar.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
                clicked1 = false;
                clicked2 = true;
            }
        });

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();
        Cursor fila1 = db.rawQuery ("select * from medicamento where idmedicamento=" + idmedicamento, null);
        fila1.moveToFirst();
        nombre.setText(fila1.getString(2));
        tipo.setText(fila1.getString(3));
        padecimiento.setText(fila1.getString(4));
        doctor.setText(fila1.getString(5));
        numPeriodos.setText(fila1.getString(9));
        dosis.setText(fila1.getString(10));
        if(fila1.getString(8).equals("Semanas"))
            periodo.setSelection(0);
        else if(fila1.getString(8).equals("Meses"))
            periodo.setSelection(1);
        else if(fila1.getString(8).equals("Años"))
            periodo.setSelection(2);
        imageView1.setImageBitmap(BitmapFactory.decodeByteArray(fila1.getBlob(7), 0, fila1.getBlob(7).length));
        imageView2.setImageBitmap(BitmapFactory.decodeByteArray(fila1.getBlob(6), 0, fila1.getBlob(6).length));
        Cursor fila2 = db.rawQuery("select * from dias where idmedicamento=" + idmedicamento, null);
        fila2.moveToFirst();
        Cursor fila3 = db.rawQuery("select * from horario where idmed=" + idmedicamento, null);
        fila3.moveToFirst();
        hora.setText(fila3.getString(2));

        editarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editar();
                String[] horaPartes = hora.getText().toString().split(":");
                if(lu.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString() , hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "1"), img);
                }
                if(ma.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.TUESDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString(), hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "2"), img);
                }
                if(mi.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.WEDNESDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString(), hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "3"), img);
                }
                if(ju.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.THURSDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString() , hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "4"), img);
                }
                if(vi.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.FRIDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString(), hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "5"), img);
                }
                if(sa.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString() , hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "6"), img);
                }
                if(dom.isChecked()) {
                    byte[] img = imageViewToByte(imageView1);
                    Calendar currentDate = Calendar.getInstance();
                    while (currentDate.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        currentDate.add(Calendar.DATE, 1);
                    }
                    currentDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(horaPartes[0]));
                    currentDate.set(Calendar.MINUTE, Integer.parseInt(horaPartes[1]));
                    currentDate.set(Calendar.SECOND, 0);
                    currentDate.set(Calendar.MILLISECOND, 0);
                    setAlarm(currentDate, nombre.getText().toString(), hora.getText().toString(), dosis.getText().toString(), Integer.parseInt(Integer.toString(idmedicamento) + "7"), img);
                }

                Intent launchactivity = new Intent(Editar.this, showMedicamentos.class);
                launchactivity.putExtra("idusuario", idusuario);
                startActivity(launchactivity);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(), "no tienes acceso a la ubicacion del archivo", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                myBitmap = BitmapFactory.decodeStream(inputStream);
                if(clicked1)
                    imageView1.setImageBitmap(myBitmap);
                else if (clicked2)
                    imageView2.setImageBitmap(myBitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void setAlarm(Calendar calendar, String event, String time, String dosis, int RequestCode, byte[] img){
        Intent intent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        System.out.println( "Nombre evento: " + event);
        intent.putExtra("event", event);
        intent.putExtra("time", time);
        intent.putExtra("dosis", dosis);
        intent.putExtra("img", img);
        intent.putExtra("idusuario", RequestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)this.getApplicationContext().getSystemService(this.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(int RequestCode, byte[] img){
        Intent intent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("img", img);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, RequestCode, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager)this.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }

    public void editar() {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String nombreMedicamento = nombre.getText().toString();
        String tipoMedicamento = tipo.getText().toString();
        String padecimientoPaciente = padecimiento.getText().toString();
        String doctorReceta = doctor.getText().toString();
        String horaSuministro = hora.getText().toString();
        String numeroPeriodos = numPeriodos.getText().toString();
        String numDosis = dosis.getText().toString();
        int lunes, martes, miercoles, jueves, viernes, sabado, domingo;
        if(lu.isChecked()) lunes = 1;
        else lunes = 0;
        if(ma.isChecked()) martes = 1;
        else martes = 0;
        if(mi.isChecked()) miercoles = 1;
        else miercoles = 0;
        if(ju.isChecked()) jueves = 1;
        else jueves = 0;
        if(vi.isChecked()) viernes = 1;
        else viernes = 0;
        if(sa.isChecked()) sabado = 1;
        else sabado = 0;
        if(dom.isChecked()) domingo = 1;
        else domingo = 0;

        String tipoPeriodo = periodo.getSelectedItem().toString();

        ContentValues registro = new ContentValues();

        registro.put("idusuario", idusuario);
        registro.put("nombre", nombreMedicamento);
        registro.put("tipo", tipoMedicamento);
        registro.put("padecimiento", padecimientoPaciente);
        registro.put("doctor", doctorReceta);
        registro.put("tipoperiodo", tipoPeriodo);
        registro.put("numperiodos", numeroPeriodos );
        registro.put("dosis", numDosis);
        registro.put("imgenvase", imageViewToByte(imageView2));
        registro.put("imgmedicamento", imageViewToByte(imageView1));

        db.update("medicamento",registro, "idusuario=" + idusuario + " AND idmedicamento=" + idmedicamento, null);


        Cursor fila = db.rawQuery
                ("select * from medicamento where nombre =?", new String[]{nombreMedicamento});
        if(fila.moveToFirst()) {
            int idMedicamento = Integer.parseInt(fila.getString(0));

            ContentValues registro2 = new ContentValues();

            registro2.put("idmedicamento", idMedicamento);
            registro2.put("lu", lunes);
            registro2.put("ma", martes);
            registro2.put("mi", miercoles);
            registro2.put("ju", jueves);
            registro2.put("vi", viernes);
            registro2.put("sa", sabado);
            registro2.put("do", domingo);

            db.update("dias", registro2, "idmedicamento=" + idmedicamento, null);

            ContentValues registro3 = new ContentValues();

            registro3.put("idmed", idMedicamento);
            registro3.put("hora", horaSuministro);

            db.update("horario", registro3, "idmed=" + idmedicamento, null);
            Toast.makeText(this, "Medicamento actualizado", Toast.LENGTH_SHORT).show();

            fila.close();
            db.close();

        }
        else System.out.println("Algo salió mal rey");
    }


}
