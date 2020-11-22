package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    public String number;
    private List<Map<String, Object>> list;
    private TextView ShortCommentNumber,LongCommentNumber,SumComment;
    private Button back;
    private RecyclerView recyclerView1,recyclerView2;
    private String Number1,Number2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView1 = findViewById(R.id.recyclerView1);
        recyclerView2 = findViewById(R.id.recyclerView2);
        SumComment = findViewById(R.id.sum_number);


        String SumNember= Number1+Number2;
        SumComment.setText(SumNember);
        ShortCommentNumber=findViewById(R.id.ShortCommentNumber);
        LongCommentNumber = findViewById(R.id.LongCommentNumber);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(CommentActivity.this,TextActivity.class);
               startActivity(intent);
            }
        });

        Intent intent3 = getIntent();
        final String id = intent3.getStringExtra("id");
        Log.d("yy","id");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url1 = new URL("https://news-at.zhihu.com/api/3/story/"+id+"/short-comments");
                    connection = (HttpURLConnection) url1.openConnection();
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
                   URL url2 = new URL("https://news-at.zhihu.com/api/3/story/"+id+"/short-comments");
                   connection = (HttpURLConnection) url2.openConnection();
                   connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                   InputStream in2 = connection.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(in2));
                   StringBuilder response2 = new StringBuilder();
                   String line2;
                   while ((line2 = reader.readLine()) != null) {
                       response2.append(line2);
                    }
                   showResponse(response2.toString());
                } catch (Exception e) {
                    e.printStackTrace();//这里记得要加上一个如果没联网的话显示的东西
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


    private void showResponse(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            Log.d("yyy",string);
            JSONArray jsonArray1 = jsonObject.getJSONArray("comments");
            final int number1 = jsonArray1.length();
            Number1=String.valueOf(number1);
            Log.d("zyr",Number1);
            for (int i = 0; i < number1; i++) {
                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                final String author = jsonObject1.getString("author");
                final String avatar = jsonObject1.getString("avatar");
                final String content = jsonObject1.getString("content");
                final String time = jsonObject1.getString("time");
                final String id = jsonObject1.getString("id");
                Log.i("yyy",author);
                Map<String, Object> map = new HashMap<>();
                map.put("number",number);
                map.put("author", author);
                map.put("avatar", avatar);
                map.put("id", id);
                map.put("content", content);
                map.put("time", time);

                list.add(map);
            }



            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("yyy","回到了主线程");
                    ShortCommentNumber.setText("Number1");
                    recyclerView1.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                    recyclerView1.setAdapter(new CommentAdapter(CommentActivity.this, list));
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showResponse2(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            JSONArray jsonArray = jsonObject.getJSONArray("comments");
            final int number2 = jsonArray.length();
            Number2=String.valueOf(number2);
            Log.i("yyy",Number2);
            for (int i = 0; i < number2; i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                final String author2 = jsonObject1.getString("author");
                final String avatar2 = jsonObject1.getString("avatar");
                final String content2 = jsonObject1.getString("content");
                final String time2 = jsonObject1.getString("time");
                final String id = jsonObject1.getString("id");
                final String likes = jsonObject1.getString("likes");
                Map<String, Object> map = new HashMap<>();
                map.put("number",number2);
                map.put("author", author2);
                map.put("avatar", avatar2);
                map.put("id", id);
                map.put("content", content2);
                map.put("time", time2);

                list.add(map);
            }


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LongCommentNumber.setText(number2);

                    recyclerView2.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                    recyclerView2.setAdapter(new LcommmentAdapter(CommentActivity.this, list));

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

