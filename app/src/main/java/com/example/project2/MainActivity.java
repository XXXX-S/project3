    package com.example.project2;

    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;


    import android.os.Bundle;

    import android.util.Log;

    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.scwang.smartrefresh.layout.api.RefreshLayout;
    import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
    import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
    import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
    import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
    import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;
    import java.io.BufferedReader;
    import java.io.IOException;
    import java.io.InputStream;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;

    public class MainActivity extends AppCompatActivity {
        public int dates;
        public String data_str = String.valueOf(dates);
        public int date2;
        public String data_str1;


        private TextView day, month;
        private List<Map<String, Object>> list = new ArrayList<>();
        private RecyclerView recyclerView;
        private MyAdapter myAdapter;
        private Object StringUtils;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.mainlist);
            recyclerView = findViewById(R.id.recyclerView);




            //获取时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月 ");
            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd日 ");
            final Date date = new Date(System.currentTimeMillis());
              day = findViewById(R.id.day);
              day.setText(simpleDateFormat1.format(date));
              month = findViewById(R.id.month);
              month.setText(simpleDateFormat.format(date));



            //设置刷新加载
            final RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
            //Heard为贝塞尔雷达样式
            refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
            //设置footer为脉冲球模式
            refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreshLayout.finishRefresh(2000);//传入false 表示刷新失败
                }
            });
            refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
                @Override
                public void onLoadmore(RefreshLayout refreshlayout) {
                    refreshLayout.finishLoadmore(2000);//传入false表示加载失败
                    date2 = dates - 1;
                    data_str1 = String.valueOf(date2);
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpURLConnection connection = null;
                            BufferedReader reader = null;
                            try {
                                URL url2 = new URL("https://news-at.zhihu.com/api/3/news/before/" + data_str1);
                                Log.d("sss",data_str1);
                                connection = (HttpURLConnection) url2.openConnection();
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
                                showResponse1(response.toString());
                            } catch (Exception e) {
                                e.printStackTrace();//这里记得要加上一个如果没联网的话显示的东西
                                Toast.makeText(MainActivity.this, "您的网络崩溃了！请查看您的网络设置！", Toast.LENGTH_SHORT).show();
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

                public void showResponse1(final String string) {

                    try {
                        JSONObject jsonObject = new JSONObject(string);
                        dates= jsonObject.getInt("date");
                        JSONArray jsonArray = jsonObject.getJSONArray("stories");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            final String title = jsonObject1.getString("title");
                            final JSONArray imagesArray = jsonObject1.getJSONArray("images");
                            String images = imagesArray.getString(0);//这一步真的非常重要
                            final String id = jsonObject1.getString("id");
                            final String hint = jsonObject1.getString("hint");
                            final String url = jsonObject1.getString("url");
                            Map<String, Object> map = new HashMap<>();
                            map.put("title", title); //标题
                            map.put("picture", images);  //高清大图图片
                            map.put("id", id);  //章对应的id
                            map.put("url", url);//详情页的url
                            map.put("hint", hint);
                            list.add(map);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                recyclerView.setAdapter(new MyAdapter(MainActivity.this, list));
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });



            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    BufferedReader reader = null;
                    try {
                        URL url1 = new URL("https://news-at.zhihu.com/api/3/stories/latest");
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
                            } catch (Exception e) {
                        e.printStackTrace();//这里记得要加上一个如果没联网的话显示的东西
                        Toast.makeText(MainActivity.this, "您的网络崩溃了！请查看您的网络设置！", Toast.LENGTH_SHORT).show();
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
                dates= jsonObject.getInt("date");
                JSONArray jsonArray = jsonObject.getJSONArray("stories");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    final String title = jsonObject1.getString("title");
                    final JSONArray imagesArray = jsonObject1.getJSONArray("images");
                    String images = imagesArray.getString(0);//这一步真的非常重要
                    final String id = jsonObject1.getString("id");
                    final String hint = jsonObject1.getString("hint");
                    final String url = jsonObject1.getString("url");
                    Map<String, Object> map = new HashMap<>();
                    map.put("title", title); //标题
                    map.put("picture", images);  //高清大图图片
                    map.put("id", id);  //章对应的id
                    map.put("url", url);//详情页的url
                    map.put("hint", hint);
                    list.add(map);
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        recyclerView.setAdapter(new MyAdapter(MainActivity.this, list));



                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

























