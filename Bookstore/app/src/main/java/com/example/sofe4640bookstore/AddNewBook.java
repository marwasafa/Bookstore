package com.example.sofe4640bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AddNewBook extends AppCompatActivity {

    Button btnParse = null;
    HttpURLConnection conn= null;
    TextView jsonView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);


        Intent recMsg = getIntent();
        String tempMsg = recMsg.getStringExtra("msg1");

        TextView txtview = (TextView) findViewById(R.id.lblMessage);
        txtview.setText(tempMsg);
        btnParse = (Button) findViewById(R.id.btnParse);
        jsonView = (TextView) findViewById(R.id.lblMessage);

        btnParse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //"https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt"
                JSONTask myTask = new JSONTask();
                myTask.execute("https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoItem.txt");

            }
        });
    }



    class JSONTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5000); //5 sec.
                conn.connect();
                if (conn.getResponseCode()== HttpURLConnection.HTTP_OK){

                    InputStream stream = conn.getInputStream();
                    BufferedReader reader = new BufferedReader( new InputStreamReader(stream));
                    StringBuffer buffer = new StringBuffer();
                    String line= "";
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }


                    return buffer.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }finally {
                conn.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //jsonView.setText(result.toString());

            //parsing Jason data
          /*
            {
                "movies": [
                {
                    "movie": "Avengers",
                        "year": 2012
                }

            }
            */

            String jsonString = result.toString();
            try {
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("movies");
                JSONObject firstObj = jsonArray.getJSONObject(0);
                String movie = firstObj.getString("movie");
                int year =firstObj.getInt("year");
                String output = movie+ " " + year;

                jsonView.setText(output);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}