package com.example.utrodeoinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.utrodeoinventario.basededatos.BDHelper;
import com.example.utrodeoinventario.basededatos.BDUsuarios;
import com.example.utrodeoinventario.basededatos.entidades.Usuario;

import java.util.ArrayList;

public class Registrarse extends AppCompatActivity {

    ArrayList<Usuario> listaUsuarios;
    BDUsuarios bdUsuarios;
    String usuario, nombre, apellido, matricula, contra, confContra;
    EditText etNUsuario, etNNombre, etNApellido, etNMatricula, etNContra, etNConfContra;
    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        BDHelper bdHelper = new BDHelper(Registrarse.this);
        SQLiteDatabase bd = bdHelper.getWritableDatabase();
        bdUsuarios = new BDUsuarios(Registrarse.this);
        listaUsuarios = new ArrayList<>();

        etNUsuario = findViewById(R.id.etNUsuario);
        etNNombre = findViewById(R.id.etNNombre);
        etNApellido = findViewById(R.id.etNApellido);
        etNMatricula = findViewById(R.id.etNMatricula);
        etNContra = findViewById(R.id.etNContra);
        etNConfContra = findViewById(R.id.etNConfContra);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = etNUsuario.getText().toString();
                nombre = etNNombre.getText().toString();
                apellido = etNApellido.getText().toString();
                matricula = etNMatricula.getText().toString();
                contra = etNContra.getText().toString();
                confContra = etNConfContra.getText().toString();
                NuevoRegistro();
            }
        });
    }

    private void NuevoRegistro(){
        if(usuario.length() == 0) Toast.makeText(Registrarse.this,"Usuario vacío",Toast.LENGTH_SHORT).show();
        else {
            if(nombre.length() == 0) Toast.makeText(Registrarse.this,"Nombre vacío",Toast.LENGTH_SHORT).show();
            else {
                if(apellido.length() == 0) Toast.makeText(Registrarse.this,"Apellido vacío",Toast.LENGTH_SHORT).show();
                else {
                    if(matricula.length() == 0) Toast.makeText(Registrarse.this,"Matricula vacía",Toast.LENGTH_SHORT).show();
                    else {
                        if(contra.length() == 0) Toast.makeText(Registrarse.this,"Contraseña vacía",Toast.LENGTH_SHORT).show();
                        else {
                            if(confContra.length() == 0) Toast.makeText(Registrarse.this,"Confirmar contraseña vacío",Toast.LENGTH_SHORT).show();
                            else {
                                if(!(contra.equals(confContra))) Toast.makeText(Registrarse.this,"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                                else{
                                    if(!UsuarioValido()) Toast.makeText(Registrarse.this,"El usuario ya existe",Toast.LENGTH_SHORT).show();
                                    else {
                                        bdUsuarios.insertarUsuario(usuario,nombre,apellido,matricula,contra);
                                        Toast.makeText(Registrarse.this,"Usuario registrado",Toast.LENGTH_SHORT).show();
                                        Vaciar();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean UsuarioValido(){
        boolean valido = true;
        listaUsuarios = bdUsuarios.obtenerUsuarios();
        if(listaUsuarios.isEmpty()) Toast.makeText(Registrarse.this,"No hay usuarios registrados",Toast.LENGTH_SHORT).show();
        else {
            int nu = listaUsuarios.size();
            int cont = 0;
            Usuario usuarioBD;
            while (cont<nu){
                usuarioBD = listaUsuarios.get(cont);
                cont++;
                if(usuarioBD.getUsuario().equals(usuario)) return false;

            }
        }
        return valido;
    }

    private void Vaciar(){
        etNUsuario.setText("");
        etNNombre.setText("");
        etNApellido.setText("");
        etNMatricula.setText("");
        etNContra.setText("");
        etNConfContra.setText("");
    }
}