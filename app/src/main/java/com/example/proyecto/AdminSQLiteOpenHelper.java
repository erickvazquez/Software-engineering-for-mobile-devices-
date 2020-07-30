package com.example.proyecto;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{


    public AdminSQLiteOpenHelper( Context context, String name,  SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table usuario(idusuario integer primary key autoincrement, correo text , contrasenia text)");
        db.execSQL("create table medicamento(idmedicamento integer primary key autoincrement, idusuario int, nombre text, tipo text, padecimiento text, doctor text, imgenvase blob, imgmedicamento blob, tipoperiodo text, numperiodos int, dosis text, FOREIGN KEY(idmedicamento) REFERENCES usuario(idusuario))");
        db.execSQL("create table dias(iddias integer primary key autoincrement, idmedicamento int, lu int, ma int, mi int, ju int, vi int, sa int, do int, FOREIGN KEY(idmedicamento) REFERENCES medicamento(idmedicamento) on delete cascade)");
        db.execSQL("create table horario(idhorario integer primary key autoincrement, idmed int, hora time, FOREIGN KEY(idmed) REFERENCES medicamento(idmedicamento) on delete cascade)");
       // db.execSQL("insert into usuario values('admin', 'admin')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
