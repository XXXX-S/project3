package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {
    public String number;
    private List<Map<String, Object>> list = new ArrayList<>();
    private TextView SumComment,LongCommentNumber,ShortCommentNumber;
    private Button back;
    private RecyclerView recyclerView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView1 = findViewById(R.id.recyclerView1);
        back = findViewById(R.id.back);
        SumComment = findViewById(R.id.sum_number);
        LongCommentNumber = findViewById(R.id.LongCommentNumber);
        ShortCommentNumber = findViewById(R.id.ShortCommentNumber);


        Intent intent3 = getIntent();
        final String id = intent3.getStringExtra("id");
        final String comments = intent3.getStringExtra("comments");
//        final int comment = Integer.valueOf(comments);
        final String long_comments = intent3.getStringExtra("long_comments");
//        final int long_comment = Integer.valueOf(long_comments);
        final String short_comments = intent3.getStringExtra("short_comments");
//        final int short_comment = Integer.valueOf(short_comments);
        SumComment.setText(comments);
        LongCommentNumber.setText(long_comments);
        ShortCommentNumber.setText(short_comments);


            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    try {
                        URL url1 = new URL("https://news-at.zhihu.com/api/3/story/" + id + "/long-comments");
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
                        URL url2 = new URL("https://news-at.zhihu.com/api/3/story/" + id + "/short-comments");
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
                        showResponse2(response2.toString());
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


        private void showResponse (String string){
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray1 = jsonObject.getJSONArray("comments");
                final int NUMBER1 = jsonArray1.length();
                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);

                    String author = jsonObject1.getString("author");
                    String avatar = jsonObject1.getString("avatar");
                    String content = jsonObject1.getString("content");
                    String time = jsonObject1.getString("time");
                    Map<String, Object> map = new HashMap<>();
//                    if (NUMBER1!=0) {
//                        map.put("i", i);
//                    }
//                    else{
//                        map.put("i",null);
//                    }
                    map.put("author", author);
                    map.put("picture", avatar);
                    map.put("content", content);
                    map.put("time", time);
                    list.add(map);
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView1.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                        recyclerView1.setAdapter(new CommentAdapter(CommentActivity.this, list));
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        private void showResponse2 (String string){
            try {
                JSONObject jsonObject = new JSONObject(string);
                JSONArray jsonArray = jsonObject.getJSONArray("comments");
                //  final int short_comment = jsonArray.length();
                // final String shortComment = String.valueOf(short_comment);
//                final int NUMBER2 = jsonArray.length();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                    String author2 = jsonObject1.getString("author");
                    String avatar2 = jsonObject1.getString("avatar");
                    String content2 = jsonObject1.getString("content");
                    String time2 = jsonObject1.getString("time");
                    Map<String, Object> map = new HashMap<>();
//                    if (NUMBER2!=0){
//                    map.put("j", j);}
//                    else{
//                        map.put("j",null);
//                    }
                    map.put("author", author2);
                    map.put("picture", avatar2);
                    map.put("content", content2);
                    map.put("time", time2);
                    list.add(map);
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView1.setLayoutManager(new LinearLayoutManager(CommentActivity.this));
                        recyclerView1.setAdapter(new CommentAdapter(CommentActivity.this, list));
                        back.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CommentActivity.this, TextActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

