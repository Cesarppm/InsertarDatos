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

public class Mostrar extends AppCompatActivity {

    EditText varId,varNombre,varPrecio;
    String idjugue, nombre, precio;

    Button btnEnviar,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar);

        varId = findViewById(R.id.editId);
        varNombre = findViewById(R.id.editMarca);
        varPrecio = findViewById(R.id.editProcesador);
        btnEnviar = findViewById(R.id.button1);
        c = findViewById(R.id.button2);

        idjugue= getIntent().getExtras().getString("idjugue");
        nombre= getIntent().getExtras().getString("nombre");
        precio= getIntent().getExtras().getString("precio");
        varId.setText(idjugue);
        varNombre.setText(nombre);
        varPrecio.setText(precio);

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idjugue = varId.getText().toString();
                String nombre = varNombre.getText().toString();
                String precio = varPrecio.getText().toString();
                //String ram = etRam.getText().toString();
                if(varId.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo ID vacio",Toast.LENGTH_LONG);

                }else
                if(varNombre.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo Nombre vacio",Toast.LENGTH_LONG);
                }else
                if(varPrecio.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo Precio vacio", Toast.LENGTH_LONG);
                }
                String url = "http://huasteco.tiburcio.mx/~dam17091172/modificar.php?nombre="+nombre+"&precio="+precio+"&idjugue="+idjugue;
                Mostrar.Tarea T = new Mostrar.Tarea();
                T.execute(url);
                Intent inten = new Intent(view.getContext(), Consultar.class);
                startActivity(inten);
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idjugue = varId.getText().toString();
                String nombre = varNombre.getText().toString();
                String precio = varPrecio.getText().toString();
                //String ram = etRam.getText().toString();
                if(varId.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo ID vacio",Toast.LENGTH_LONG);

                }else
                if(varNombre.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Campo Nombre vacio",Toast.LENGTH_LONG);
                }else
                if(varPrecio.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo Precio vacio", Toast.LENGTH_LONG);
                }
                String url = "http://huasteco.tiburcio.mx/~dam17091172/borrar.php?idjugue="+idjugue;
                Mostrar.Tarea T = new Mostrar.Tarea();
                T.execute(url);
                Intent inten = new Intent(view.getContext(), Consultar.class);
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
            Toast.makeText(getApplicationContext(),"Correcto",Toast.LENGTH_LONG).show();
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