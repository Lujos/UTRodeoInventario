package com.example.utrodeoinventario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utrodeoinventario.basededatos.BDHelper;
import com.example.utrodeoinventario.basededatos.BDUsuarios;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

public class Home extends AppCompatActivity {

    TextView tvFecha, tvEscanear;
    Spinner spLugar, spEtiqueta;
    Button btnGuardar;
    ArrayList<String> lugares = new ArrayList<>();
    ArrayList<String> etiquetas = new ArrayList<>();
    String fecha, lugar, etiqueta, codigo, fechaSinF;
    boolean escaneado;
    long ahora;
    Date fechaC;
    BDUsuarios bdUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Llenarlugares();
        LlenarEtiquetas();
        escaneado = false;
        BDHelper bdHelper = new BDHelper(Home.this);
        SQLiteDatabase bd = bdHelper.getWritableDatabase();
        bdUsuarios = new BDUsuarios(Home.this);

        tvFecha = findViewById(R.id.tvFecha);
        spLugar = findViewById(R.id.spLugar);
        spEtiqueta = findViewById(R.id.spEtiqueta);
        btnGuardar = findViewById(R.id.btnGuardar);
        tvEscanear = findViewById(R.id.tvEscanear);

        ActualizarFecha();
        fecha = ObtenerFecha();
        tvFecha.setText(fecha);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        ArrayAdapter adapLugares = new ArrayAdapter(Home.this, android.R.layout.simple_spinner_item,lugares);
        ArrayAdapter adapEtiquetas = new ArrayAdapter(Home.this, android.R.layout.simple_spinner_item,etiquetas);
        spLugar.setAdapter(adapLugares);
        spEtiqueta.setAdapter(adapEtiquetas);

        tvEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escaneado = false;
                tvEscanear.setText("Escanear");
                IntentIntegrator integrador = new IntentIntegrator(Home.this);
                integrador.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrador.setPrompt("Lector de código");
                integrador.setCameraId(0);
                integrador.setBeepEnabled(true);
                integrador.setBarcodeImageEnabled(true);
                integrador.initiateScan();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!escaneado) Toast.makeText(Home.this,"No se ha escaneado código",Toast.LENGTH_SHORT).show();
                else {
                    lugar = spLugar.getSelectedItem().toString();
                    etiqueta = spEtiqueta.getSelectedItem().toString();
                    codigo = tvEscanear.getText().toString();
                    Vaciar();
                    bdUsuarios.insertarRegistro(fecha,lugar,etiqueta,codigo);
                    Toast.makeText(Home.this, "Registro guardado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void ActualizarFecha(){
        ahora = System.currentTimeMillis();
        fechaC = new Date(ahora);
        fechaSinF = fechaC.toString();
    }

    private void Llenarlugares(){
        lugares.clear();
        lugares.add("Laboratorio planta alta");
        lugares.add("Laboratorio planta baja");
        lugares.add("Laboratorio pesado");
        lugares.add("Laboratorio de inglés");
        lugares.add("Laboratorio de base de datos");
        lugares.add("Salones");
        lugares.add("Biblioteca");
    }

    private void LlenarEtiquetas(){
        etiquetas.clear();
        etiquetas.add("Monitores");
        etiquetas.add("CPU");
        etiquetas.add("Teclado");
        etiquetas.add("Otros");
    }

    private String ObtenerFecha(){
        String fecha = "";
        String m = fechaSinF.substring(4,7);
        String dia = fechaSinF.substring(8,10);
        String anio = fechaSinF.substring(24);
        String mes = "";

        switch (m){
            case "Jan":
                mes = "Enero";
                break;
            case "Feb":
                mes = "Febrero";
                break;
            case "Mar":
                mes = "Marzo";
                break;
            case "Apr":
                mes = "Abril";
                break;
            case "May":
                mes = "Mayo";
                break;
            case "Jun":
                mes = "Junio";
                break;
            case "Jul":
                mes = "Julio";
                break;
            case "Aug":
                mes = "Agosto";
                break;
            case "Sep":
                mes = "Septiembre";
                break;
            case "Oct":
                mes = "Octubre";
                break;
            case "Nov":
                mes = "Noviembre";
                break;
            case "Dec":
                mes = "Diciembre";
                break;
        }

        fecha = dia + " de " + mes + " de " + anio;
        return fecha;
    }

    private String ObtenerFechaSimple(){
        String fecha = "";
        String m = fechaSinF.substring(4,7);
        String dia = fechaSinF.substring(8,10);
        String anio = fechaSinF.substring(24);
        String mes = "";

        switch (m){
            case "Jan":
                mes = "01";
                break;
            case "Feb":
                mes = "02";
                break;
            case "Mar":
                mes = "03";
                break;
            case "Apr":
                mes = "04";
                break;
            case "May":
                mes = "05";
                break;
            case "Jun":
                mes = "06";
                break;
            case "Jul":
                mes = "07";
                break;
            case "Aug":
                mes = "08";
                break;
            case "Sep":
                mes = "09";
                break;
            case "Oct":
                mes = "10";
                break;
            case "Nov":
                mes = "11";
                break;
            case "Dec":
                mes = "12";
                break;
        }

        fecha = anio + "-" + mes + "-" + dia;
        return fecha;
    }

    private String ObtenerHora(){
        String hora = "";
        String hor = fechaSinF.substring(11,13);
        String min = fechaSinF.substring(14,17);
        int h = Integer.parseInt(hor);
        String merid = "";
        if(h > 12) {
            h = h - 12;
            merid = "p.m.";
        }
        else merid = "a.m.";
        String hs = "";
        if(h < 10) {
            hs = "0";
        }
        hs = hs + h;
        hora = hs + ":" + min + merid;
        return hora;
    }

    protected void onActivityResult(int codigo, int resultado, Intent datos) {
        IntentResult result = IntentIntegrator.parseActivityResult(codigo,resultado,datos);
        if(result != null){
            if (result.getContents() == null) Toast.makeText(this, "Lectura cancelada", Toast.LENGTH_SHORT).show();
            else {
                tvEscanear.setText(result.getContents());
                escaneado = true;
            }
        } else super.onActivityResult(codigo, resultado, datos);
    }

    private void Vaciar(){
        tvEscanear.setText("Escanear");
        escaneado = false;
        codigo = "";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_pdf,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.mnuDescargar){
            GenerarPDF();
        }
        return super.onOptionsItemSelected(item);
    }

    private void GenerarPDF() {
        try {

            String direccion = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Mis Pdf";
            File file = new File(direccion);
            if(!file.exists()) file.mkdirs();

            File pdfFile = new File(file.getAbsolutePath() + "/Hoja de inventario_" + ObtenerFechaSimple() + " " + ObtenerHora() + " " + ".pdf");
            if(!pdfFile.exists()) pdfFile.createNewFile();

            Document documento = new Document(PageSize.A4.rotate(),20,20,20,20);
            PdfWriter.getInstance(documento,new FileOutputStream(pdfFile.getAbsoluteFile()));

            documento.open();

            Paragraph titulo = new Paragraph();
            titulo.add("Que onda raza como andamos");
            titulo.setSpacingAfter(10.0f);
            titulo.setAlignment(Element.ALIGN_CENTER);

            PdfPTable tabla = new PdfPTable(11);
            
            documento.add(titulo);
            documento.add(tabla);
            documento.close();

            Toast.makeText(Home.this, "Hoja de inventario creada", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            e.printStackTrace();
        } catch (DocumentException e){
            e.printStackTrace();
        }
    }
}