package com.example.utrodeoinventario.basededatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.utrodeoinventario.basededatos.entidades.Usuario;

import java.util.ArrayList;

public class BDUsuarios extends BDHelper{
    Context context;

    public BDUsuarios(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario(String usuario, String nombre, String apellido, String matricula, String contrasena){
        long id = 0;
        try {
            BDHelper bdHelper = new BDHelper(context);
            SQLiteDatabase bd = bdHelper.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("usuario",usuario);
            valores.put("nombre",nombre);
            valores.put("apellido",apellido);
            valores.put("matricula",matricula);
            valores.put("contrasena",contrasena);

            id = bd.insert(TABLA_USUARIOS,null,valores);
        } catch (Exception e){
            e.toString();
        }
        return id;
    }

    public long insertarRegistro(String fecha, String lugar, String etiqueta, String codigo){
        long id = 0;
        try {
            BDHelper bdHelper = new BDHelper(context);
            SQLiteDatabase bd = bdHelper.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("fecha",fecha);
            valores.put("lugar",lugar);
            valores.put("etiqueta",etiqueta);
            valores.put("codigo",codigo);

            id = bd.insert(TABLA_REGISTROS,null,valores);
        } catch (Exception e){
            e.toString();
        }
        return id;
    }

    public ArrayList<Usuario> obtenerUsuarios(){
        ArrayList<Usuario> usuarios = new ArrayList<>();

        BDHelper bdHelper = new BDHelper(context);
        SQLiteDatabase bd = bdHelper.getWritableDatabase();

        Usuario usuario = null;
        Cursor cursorU = null;

        cursorU = bd.rawQuery("SELECT * FROM " + TABLA_USUARIOS,null);
        if(cursorU.moveToFirst()){
            do {
                usuario = new Usuario();
                usuario.setUsuario(cursorU.getString(0));
                usuario.setNombre(cursorU.getString(1));
                usuario.setApellido(cursorU.getString(2));
                usuario.setMatricula(cursorU.getString(3));
                usuario.setContrasena(cursorU.getString(4));
                usuarios.add(usuario);
            } while (cursorU.moveToNext());
        }
        cursorU.close();

        return usuarios;
    }
}
