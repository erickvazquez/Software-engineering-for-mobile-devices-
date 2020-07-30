package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    private Button button;
    private EditText et_correo, et_contra;
    private int idusuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void Entrar(View view){
        et_correo = (EditText)findViewById(R.id.et_correo);
        et_contra = (EditText)findViewById(R.id.et_contra);
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String correo = et_correo.getText().toString();
        String contra = et_contra.getText().toString();
        if(!correo.isEmpty() && !contra.isEmpty()){
            Cursor fila = db.rawQuery
                    ("Select * from usuario where correo=? and contrasenia=?", new String[]{correo, contra});

            if(fila.moveToFirst()){
                idusuario = fila.getColumnIndex("idusuario");
                System.out.println("El indice de esta madre es: " + idusuario);
                System.out.println("El contenido de esta madre es: " + fila.getString(idusuario));
                Intent launchactivity = new Intent(Login.this, showMedicamentos.class);
                launchactivity.putExtra("idusuario", Integer.parseInt(fila.getString(idusuario)));
                startActivity(launchactivity);
                db.close();
                Toast.makeText(this,"entraste rey", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                db.close();
            }

        } else {
            Toast.makeText(this, "Llene los campos solicidatos", Toast.LENGTH_SHORT).show();
        }
    }
}
