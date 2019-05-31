package com.example.paper_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.app.ProgressDialog;

import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.paper_app.handler.CoreHandler;

public class MainActivity extends AppCompatActivity {

    private final int MP = ViewGroup.LayoutParams.MATCH_PARENT;
    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private static ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final LinearLayout linearLayout = findViewById(R.id.main_constraint_layout);

        pd = new ProgressDialog(MainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage(getString(R.string.app_wait));
        pd.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL(getString(R.string.api_level_url));
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in, getString(R.string.app_charset_name)));

                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    JSONArray jsonArr = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArr.length(); i++) {
                        JSONObject tmpJson = (JSONObject) jsonArr.get(i);
                        setBtnText(linearLayout,
                                tmpJson.getString(getString(R.string.app_level_id)),
                                tmpJson.getString(getString(R.string.app_level_name)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                    pd.dismiss();
                }
            }
        }).start();
    }

    protected void setBtnText(LinearLayout layout, final String id, String text) {
        Button btn = new Button(getBaseContext());
        btn.setText(text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AnswerActivity.class);
                //intent.putExtra("id", id);
                CoreHandler.setChoiceLevel(id);
                startActivity(intent);
            }
        });
        layout.addView(btn, new LinearLayout.LayoutParams(MP, WC));
    }
}
