package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Map<String, Object>> list;
    private Context context;


    public MyAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }



    @NonNull
    @Override

    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.textView1.setText(list.get(position).get("title").toString());
        holder.textView2.setText(list.get(position).get("hint").toString());
        Glide.with(context).load(list.get(position).get("picture")).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),TextActivity.class);
                intent.putExtra("url",list.get(position).get("url").toString());
                intent.putExtra("id",list.get(position).get("id").toString());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1,textView2;
        private ImageView imageView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.item_picture);
            textView1= itemView.findViewById(R.id.title);
            textView2  = itemView.findViewById(R.id.hint);
        }
    }
}
