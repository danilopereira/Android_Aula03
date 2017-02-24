package com.project.danilopereira.aula03;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.project.danilopereira.aula03.adapter.ListaAndroidAdapter;
import com.project.danilopereira.aula03.model.AndroidVersao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView rvLista;
    private ListaAndroidAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        rvLista = (RecyclerView) findViewById(R.id.rvLista);

        new BuscaDados().execute("http://www.mocky.io/v2/58af1fb21000001e1cc94547");

    }

    private class BuscaDados extends AsyncTask<String, Void, String>{

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ListaActivity.this);
            progressDialog.setMessage("Carregando os Dados");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setReadTimeout(15000);
                connection.setConnectTimeout(10000);

                connection.setRequestMethod("GET");

                connection.setDoOutput(true);

                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    InputStream is = connection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(is));

                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = buffer.readLine()) != null){
                        result.append(line);
                    }

                    return result.toString();
                }

                connection.disconnect();
            }
            catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if(s == null){
                Toast.makeText(ListaActivity.this, "Deu Ruim!", Toast.LENGTH_LONG).show();
            }
            else{
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("android");
                    List<AndroidVersao> versoes = new ArrayList<>();

                    for(int i = 0; i < jsonArray.length(); i ++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        AndroidVersao androidVersao = new AndroidVersao();

                        androidVersao.setApi(data.getString("api"));
                        androidVersao.setNome(data.getString("nome"));
                        androidVersao.setVersao(data.getString("versao"));
                        androidVersao.setUrlImagem(data.getString("urlImagem"));

                        versoes.add(androidVersao);

                    }

                    setupLista(versoes);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        private void setupLista(List<AndroidVersao> versoes) {
            adapter = new ListaAndroidAdapter(ListaActivity.this, versoes);
            rvLista.setLayoutManager(new LinearLayoutManager(ListaActivity.this));
            rvLista.setAdapter(adapter);
        }
    }
}
