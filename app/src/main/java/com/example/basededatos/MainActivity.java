package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txt_codigo, txt_descripcion, txt_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_codigo = (EditText) findViewById(R.id.txt_codigo);
        txt_descripcion = (EditText) findViewById(R.id.txt_descripcion);
        txt_precio = (EditText) findViewById(R.id.txt_precio);
    }

    //metodo para dar de alta el producto
    public void Registrar (View view){
        // instanciamos la clase de la base de datos
        // tambien se peude hacer asi:
        // String NombreBaseDeDatos = "administracion"; abajo en name pondrias este nombre sin comillas.
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        // abrimos la base de dato en modo lectura y escrita
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = txt_codigo.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String precio = txt_precio.getText().toString();

        // es para hacer que no esten vacios los campos.
        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            // una conversion a la base de dato
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            // insertamos dentro de la tabla "articulos" los registros que hemos quedado.
            BaseDeDatos.insert("articulos", null, registro);

            // cerramos la base de datos
            BaseDeDatos.close();

            txt_codigo.setText("");
            txt_descripcion.setText("");
            txt_precio.setText("");

            Toast.makeText(this, "El producto se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }
}