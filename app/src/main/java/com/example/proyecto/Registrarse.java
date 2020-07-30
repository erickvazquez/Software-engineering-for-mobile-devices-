package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Registrarse extends AppCompatActivity {

    private EditText et_correo, et_contra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);
    }

    public void Registrar(View view){
        et_correo = (EditText)findViewById(R.id.correoRegistro);
        et_contra = (EditText)findViewById(R.id.contraRegistro);
        AdminSQLiteOpenHelper admin  = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();

        String correo = et_correo.getText().toString();
        String contrasenia = et_contra.getText().toString();

        if(!correo.isEmpty() && !contrasenia.isEmpty()){

            ContentValues registro = new ContentValues();

            registro.put("correo", correo);
            registro.put("contrasenia", contrasenia);

            db.insert("usuario", null, registro);

            db.close();
            et_contra.setText("");
            et_correo.setText("");
            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}