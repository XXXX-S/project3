package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {
    private WebView webView;
    private TextView comment;
    private Button review;
    private Button back, like,collect;

    //取出上个页面传递的数据

    /*Intent intent1= getIntent();
    String URL=intent1.getStringExtra("url");
    String ID = intent1.getStringExtra("id");*/

       protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.maintext);
           webView=findViewById(R.id.WebView);
           Intent intent1= getIntent();
           String URL=intent1.getStringExtra("url");
           final String id = intent1.getStringExtra("id");
           Log.d("yy","id");
           webView.loadUrl(URL);
           review=findViewById(R.id.review);
           review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(TextActivity.this,CommentActivity.class);
                intent2.putExtra("id",id);
                Log.d("yy","id");
                startActivity(intent2);

            }
        });

        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3= new Intent(TextActivity.this,MainActivity.class);
                startActivity(intent3);
            }
        });
        like=findViewById(R.id.like);
        collect=findViewById(R.id.collect);


    }
}