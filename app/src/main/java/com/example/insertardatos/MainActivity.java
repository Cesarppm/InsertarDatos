package com.example.insertardatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText varId,varNombre,varPrecio;

    Button btnEnviar,consultar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        varId = findViewById(R.id.editTextId);
        varNombre = findViewById(R.id.editTextMarca);
        varPrecio = findViewById(R.id.editTextProcesador);
        btnEnviar = findViewById(R.id.button2);
        consultar = findViewById(R.id.button);

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(view.getContext(), Consultar.class);
                startActivity(inten);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String idjugue = varId.getText().toString();
                String nombre = varNombre.getText().toString();
                String precio = varPrecio.getText().toString();

                if(varId.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo ID vacio",Toast.LENGTH_LONG);

                }
                if(varNombre.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo Nombre vacio",Toast.LENGTH_LONG);
                }
                if(varPrecio.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo Precio vacio", Toast.LENGTH_LONG);
                }

                String url = "http://huasteco.tiburcio.mx/~dam17091172/insertar.php?idjugue="+idjugue+"&nombre="+nombre+"&precio="+precio;
                Tarea T = new Tarea();
                T.execute(url);
                Intent inten = new Intent(v.getContext(), Consultar.class);
                startActivity(inten);
            }
        });


    }
    public class Tarea extends AsyncTask<String, Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String salida = ConexionWeb(strings[0]);
            return salida;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

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