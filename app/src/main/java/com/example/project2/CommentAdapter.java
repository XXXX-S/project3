package com.example.project2;

import android.content.Context;
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

        public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

            private List<Map<String, Object>> list;
            private Context context;

            public CommentAdapter(Context context, List<Map<String, Object>> list) {
                this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override

    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, final int position) {
        holder.numbers.setText(list.get(position).get("number").toString());
        holder.author.setText(list.get(position).get("author").toString());
        holder.content.setText(list.get(position).get("content").toString());
        holder.time.setText(list.get(position).get("time").toString());
        Glide.with(context).load(list.get(position).get("picture")).into(holder.avater);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView numbers, author, content, time;
        private ImageView avater;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            numbers = itemView.findViewById(R.id.ShortCommentNumber);
            author = itemView.findViewById(R.id.author1);
            content = itemView.findViewById(R.id.short_Content);
            time = itemView.findViewById(R.id.time1);
            avater = itemView.findViewById(R.id.avater1);
        }
    }
}
