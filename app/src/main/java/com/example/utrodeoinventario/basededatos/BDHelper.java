package com.example.utrodeoinventario.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BDHelper extends SQLiteOpenHelper {
    private static final int BASE_DATOS_VERSION = 1;
    private static final String BASE_DATOS_NOMBRE = "Registro de inventario";
    public static final String TABLA_USUARIOS = "Usuarios";
    public static final String TABLA_REGISTROS = "Registros";

    public BDHelper(@Nullable Context context) {
        super(context, BASE_DATOS_NOMBRE, null, BASE_DATOS_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("Create Table " + TABLA_USUARIOS  + "(" +
                "usuario TEXT PRIMARY KEY NOT NULL," +
                "nombre TEXT NOT NULL," +
                "apellido TEXT NOT NULL," +
                "matricula TEXT NOT NULL," +
                "contrasena TEXT NOT NULL)");

        sqLiteDatabase.execSQL("Create Table " + TABLA_REGISTROS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "fecha TEXT NOT NULL," +
                "lugar TEXT NOT NULL," +
                "etiqueta TEXT NOT NULL," +
                "codigo TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_USUARIOS);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLA_REGISTROS);

        onCreate(sqLiteDatabase);
    }
}
