package com.example.administrator.jsonparser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {


    TextView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.enableDefaults();
        resultView=(TextView) findViewById(R.id.result);
        getData();

    }

    public void getData(){
        String result="";
        InputStream isr=null;
        try{
            HttpClient httpclient=new DefaultHttpClient();
            HttpPost httppost=new HttpPost("http://ip.jsontest.com");
            HttpResponse response=httpclient.execute(httppost);
            HttpEntity entity=response.getEntity();
            isr=entity.getContent();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         catch(Exception e){
             Log.e("log_tag","Error in http connection "+e.toString());
             resultView.setText("Couldn't connect  to database");
         }

        try {
            BufferedReader reader=new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb =new StringBuilder();
            String line = null;
            if (reader!=null) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            }
            isr.close();
            result=sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            Log.e("log_tag", "Error converting result " + e.toString());
        }

        try{
            String s="";
            JSONArray jArray =new JSONArray(result);
            for( int i=0 ; i<jArray.length();i++){
                JSONObject json=jArray.getJSONObject(i);
                //s=s+"Name :"+json.getString("name")+"\n "+"Hobbies :"+json.getString("hobbies")+"\n"+"Occupation :"+json.getString("occupation");
                s=s+"IP :"+json.getString("ip");
            }
            resultView.setText(s);
        } catch (Exception e){
            Log.e("log_tag","Error Parsing Data "+e.toString());
        }

    }
}
