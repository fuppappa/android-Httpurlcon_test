package com.example.fuppa.httptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText editTextUrl;
    Button buttonGet;
    TextView editTextResponse;
    long startTime;
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = (EditText)findViewById(R.id.ip);
        buttonGet = (Button)findViewById(R.id.get);
        editTextResponse = (TextView)findViewById(R.id.ReturntextView);

    }

    //ボタンクリックの動作確認用
    public void onButtonGetTest(View view) {
        Toast.makeText(this,"click test",Toast.LENGTH_SHORT).show();
    }

    public void onButtonGet(View view) {
        Toast.makeText(this,editTextUrl.getText().toString(),Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(editTextUrl.getText().toString());
                    // 処理開始時刻
                    startTime = System.currentTimeMillis();
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    final String str = InputStreamToString(con.getInputStream());
                    // 処理終了時刻
                    endTime = System.currentTimeMillis();
                    Log.d("HTTP", str);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextResponse.setText(String.valueOf(str));
                        }
                    });
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
    }

    // InputStream -> String
    static String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}

