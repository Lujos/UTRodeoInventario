package com.example.utrodeoinventario;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utrodeoinventario.basededatos.BDHelper;
import com.example.utrodeoinventario.basededatos.BDUsuarios;
import com.example.utrodeoinventario.basededatos.entidades.Usuario;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etUsuario, etContra;
    TextView tvRegistrarse;
    Button btnEntrar;
    String usuario,contra;
    ArrayList<Usuario> listaUsuarios;
    BDUsuarios bdUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BDHelper bdHelper = new BDHelper(MainActivity.this);
        SQLiteDatabase bd = bdHelper.getWritableDatabase();
        bdUsuarios = new BDUsuarios(MainActivity.this);
        listaUsuarios = new ArrayList<>();

        etUsuario = findViewById(R.id.etUsuario);
        etContra = findViewById(R.id.etContra);
        tvRegistrarse = findViewById(R.id.tvRegistrarse);
        btnEntrar = findViewById(R.id.btnEntrar);

        tvRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VarciarDatos();
                Intent i = new Intent(MainActivity.this,Registrarse.class);
                startActivity(i);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = etUsuario.getText().toString();
                contra = etContra.getText().toString();
                listaUsuarios = bdUsuarios.obtenerUsuarios();
                if(!getVUsuario()) Toast.makeText(MainActivity.this,"Usuario vacío",Toast.LENGTH_SHORT).show();
                else {
                    if (!getVContra()) Toast.makeText(MainActivity.this,"Contraseña vacía",Toast.LENGTH_SHORT).show();
                    else {
                        if(listaUsuarios.isEmpty()) Toast.makeText(MainActivity.this,"No hay usuarios registrados",Toast.LENGTH_SHORT).show();
                        else{
                            if(!usuarioValido()) Toast.makeText(MainActivity.this,"Usuario y/o contraseña incorrecta",Toast.LENGTH_SHORT).show();
                            else {
                                VarciarDatos();
                                Intent i = new Intent(MainActivity.this,Home.class);
                                startActivity(i);
                            }
                        }
                    }
                }
            }
        });
    }

    private boolean getVUsuario(){
        return usuario.length() > 0;
    }

    private boolean getVContra(){
        return contra.length() > 0;
    }

    private void VarciarDatos(){
        etUsuario.setText("");
        etContra.setText("");
    }

    private boolean usuarioValido() {
        boolean valido = false;
        int nu = listaUsuarios.size();
        int cont = 0;
        Usuario usuarioBD;
        while (cont < nu) {
            usuarioBD = listaUsuarios.get(cont);
            cont++;
            if (usuarioBD.getUsuario().equals(usuario) && usuarioBD.getContrasena().equals(contra))
                return true;


        }
        return valido;
    }


}