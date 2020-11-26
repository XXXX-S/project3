package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TextActivity extends AppCompatActivity {
    private WebView webView;
    private TextView comment,popularities;
    private Button review;
    private Button back, like,collect;
    public String comments,long_comments,short_comments,popularity;


        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.maintext);
           webView=findViewById(R.id.WebView);
           Intent intent1= getIntent();
           final String URL=intent1.getStringExtra("url");
           final String id = intent1.getStringExtra("id");
           webView.loadUrl(URL);
           review=findViewById(R.id.review);
           comment=findViewById(R.id.comment_number);
            popularities=findViewById(R.id.popularity);
          // comment.setText(CommentNumber);
           review.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent2=new Intent(TextActivity.this,CommentActivity.class);
                   intent2.putExtra("id",id);
                   intent2.putExtra("comments",comments);
                   intent2.putExtra("long_comments",long_comments);
                   intent2.putExtra("short_comments",short_comments);
                   startActivity(intent2);



               }
           });

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(TextActivity.this,MainActivity.class);
                startActivity(intent3);
                finish();
            }
        });
        like=findViewById(R.id.like);
        collect=findViewById(R.id.collect);
           Thread thread = new Thread(new Runnable() {
               @Override
               public void run() {
                   HttpURLConnection connection = null;
                   BufferedReader reader = null;
                   try {
                       URL url = new URL("https://news-at.zhihu.com/api/3/story-extra/"+id);
                       connection = (HttpURLConnection) url.openConnection();
                       connection.setRequestMethod("GET");
                       connection.setConnectTimeout(8000);
                       connection.setReadTimeout(8000);
                       InputStream in = connection.getInputStream();
                       reader = new BufferedReader(new InputStreamReader(in));
                       StringBuilder response = new StringBuilder();
                       String line;
                       while ((line = reader.readLine()) != null) {
                           response.append(line);
                       }
                       showResponse(response.toString());
                   } catch (Exception e) {
                       e.printStackTrace();

                   } finally {
                       if (reader != null) {
                           try {
                               reader.close();
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       }

                   }
               }
           });

           thread.start();
       }

    public void showResponse(final String string) {


        try {
            JSONObject jsonObject = new JSONObject(string);
             comments = jsonObject.getString("comments");
             long_comments = jsonObject.getString("long_comments");
             short_comments = jsonObject.getString("short_comments");
             popularity  = jsonObject.getString("popularity");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    comment.setText(comments);
                    popularities.setText(popularity);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}



