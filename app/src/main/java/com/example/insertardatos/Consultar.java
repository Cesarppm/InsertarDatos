package com.example.insertardatos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Consultar extends AppCompatActivity {

    Toast mensaje;
    private ListView listview;
    private ArrayList<String> nom,idjugue,nombre,precio;
    Button insertar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar);

        nom = new ArrayList<String>();
        idjugue = new ArrayList<String>();
        nombre = new ArrayList<String>();
        precio = new ArrayList<String>();

        insertar=findViewById(R.id.insetarbtn);
        listview = (ListView) findViewById(R.id.list_view);

        insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(view.getContext(), MainActivity.class);
                startActivity(inten);
            }
        });
        Tarea T = new Tarea();
        T.execute("http://huasteco.tiburcio.mx/~dam17091172/consultar.php");

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    startActivity(new Intent( Consultar.this, Mostrar.class).putExtra("idjugue",idjugue.get(position)).putExtra("nombre",nombre.get(position))
                            .putExtra("precio", precio.get(position)) );
                }
                catch (Exception e)
                {
                    mensaje = Toast.makeText(getApplicationContext(), "Error "+ e, Toast.LENGTH_LONG);
                    mensaje.show();
                }
            }
        });

    }
    public void mostrar(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nom);
        listview.setAdapter(adapter);
    }

    public class Tarea extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String salida = ConexionWeb(strings[0]);
            return salida;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONArray Juguetes = new JSONArray(s);
                for (int i =0;i<Juguetes.length();i++) {
                    JSONObject ObjPC = Juguetes.getJSONObject(i);

                    String todo = "idjugue: " + ObjPC.getString("idjugue") + "\n" ;
                    todo += "nombre: "+ ObjPC.getString("nombre") + "\n" ;
                    todo += "precio: "+ ObjPC.getString("precio") + "\n" ;

                    todo += "-----------------------------------------------------------"+ "\n";
                    nom.add(todo);
                    String idl = ObjPC.getString("idjugue");
                    String m =ObjPC.getString("nombre");
                    String p = ObjPC.getString("precio");
                    idjugue.add(idl);
                    nombre.add(m);
                    precio.add(p);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Consultar.this, android.R.layout.simple_list_item_1, nom);
                listview.setAdapter(adapter);
            }
            catch (Exception e){
                mensaje = Toast.makeText(getApplicationContext(), "Error de lectura", Toast.LENGTH_LONG);
                mensaje.show();

            }
        }
    }

    String ConexionWeb(String direccion) {

        String pagina="";
        try {
            URL url = new URL(direccion);

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();


            if (conexion.getResponseCode() == HttpURLConnection.HTTP_OK) {

                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conexion.getInputStream()));

                String linea = reader.readLine();

                while (linea != null) {
                    pagina += linea + "\n";
                    linea = reader.readLine();
                }
                reader.close();

            } else {
                pagina += "ERROR: " + conexion.getResponseMessage() + "\n";
            }
            conexion.disconnect();
        }
        catch (Exception e){
            pagina+=e.getMessage();
        }
        return pagina;

    }
}