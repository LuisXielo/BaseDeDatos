package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

// private String NombreBaseDatos = "administracion"- cambiaria todos los " name: "administracion""
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
    // metodo buscar
    public void Buscar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = txt_codigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo =" + codigo, null);

            // al devolver un array es [0,1] proque siempre inicia en 0 el array
            if (fila.moveToFirst()){
                txt_descripcion.setText(fila.getString(0));
                txt_precio.setText(fila.getString(1));
                BaseDeDatos.close();

            }else {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }else{
            Toast.makeText(this, "Debes introducir el codigo", Toast.LENGTH_LONG).show();
        }
    }

    //metodo eliminar producto
    public void Eliminar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = txt_codigo.getText().toString();
        // "!" es para decir que no este vacio
        if(!codigo.isEmpty()){
            int cantidad = BaseDeDatos.delete("articulos", "codigo=" + codigo, null);
            BaseDeDatos.close();

            if(cantidad == 1){
                txt_codigo.setText("");
                txt_descripcion.setText("");
                txt_precio.setText("");
                Toast.makeText(this, "el articulo se ha borrado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "no exite", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "desbes introducir el codigo del articulo", Toast.LENGTH_SHORT).show();
        }
    }
    //metodo de modificar
    public void Modificar (View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = txt_codigo.getText().toString();
        String descripcion = txt_descripcion.getText().toString();
        String precio = txt_precio.getText().toString();

        if (!codigo.isEmpty() && !descripcion.isEmpty() && !precio.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            // en el otro teniamos INSET pero aqui puede ser que solo modifiquemos una cosa y no todas
            int cantidad = BaseDeDatos.update("articulos", registro, "codigo=" + codigo, null);
            BaseDeDatos.close();

            if (cantidad == 1){
                Toast.makeText(this, "El articulo se ha modificado", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "El articulo no exite", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "debes rellenar los campos", Toast.LENGTH_SHORT).show();
        }
    }
}