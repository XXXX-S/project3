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

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Map<String, Object>> list;
    private Context context;

    public static final int item1 = 1;
    public static final int item2 = 2;
    public MyAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;

    }


    @Override
    public int getItemViewType(final int position) {
        if (list.get(position).get("i").equals(0)) {
            return item2;
        } else {
            return item1;
        }
    }

    // @NonNull
    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == item1) {
             view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
            return new viewholder1(view);
        } else  {
             view = LayoutInflater.from(context).inflate(R.layout.activity_item2, parent, false);
            return new viewholder2(view);
        }
        }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TextActivity.class);
                intent.putExtra("url", list.get(position).get("url").toString());
                intent.putExtra("id", list.get(position).get("id").toString());
                v.getContext().startActivity(intent);
            }
        });
        if (holder instanceof viewholder1) {
            viewholder1 holder1 = (viewholder1) holder;
            holder1.itemView.setTag(position);
            holder1.textView1.setText(list.get(position).get("title").toString());
            holder1.textView2.setText(list.get(position).get("hint").toString());
            Glide.with(context).load(list.get(position).get("picture")).into(holder1.imageView);
        } else if (holder instanceof viewholder2) {
            viewholder2 holder2 = (viewholder2) holder;
            holder2.year.setText(list.get(position).get("year").toString());
            holder2.month.setText(list.get(position).get("month").toString());
            holder2.day.setText(list.get(position).get("day").toString());
            holder2.itemView.setTag(position);
            holder2.textView1.setText(list.get(position).get("title").toString());
            holder2.textView2.setText(list.get(position).get("hint").toString());
            Glide.with(context).load(list.get(position).get("picture")).into(holder2.imageView);

        }


    }

    @Override
    public int getItemCount() {

        return list.size();
    }




    public class viewholder1 extends RecyclerView.ViewHolder {
        private TextView textView1,textView2;
        private ImageView imageView;
        viewholder1(@NonNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.item_picture);
            textView1= itemView.findViewById(R.id.title);
            textView2  = itemView.findViewById(R.id.hint);
        }
    }
    public class viewholder2 extends RecyclerView.ViewHolder {
        private TextView textView1,textView2,year,month,day;
        private ImageView imageView;
        viewholder2(@NonNull View itemView) {

            super(itemView);
            year  = itemView.findViewById(R.id.year);
            month  = itemView.findViewById(R.id.month);
            day = itemView.findViewById(R.id.day);
            imageView=itemView.findViewById(R.id.item_picture);
            textView1= itemView.findViewById(R.id.title);
            textView2  = itemView.findViewById(R.id.hint);
        }
    }


}
