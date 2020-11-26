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

        public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

            private List<Map<String, Object>> list;
            private Context context;
            public static final int item0 = 0;
            public static final int item1 = 1;
            public static final int item2 = 2;
            public CommentAdapter(Context context, List<Map<String, Object>> list) {
                this.context = context;
                this.list = list;
    }
    @Override
    public int getItemViewType(int position){
                if (list.get(position).get("i").equals(0)){
                    return item1;    //这是有头标的
                }
                else if (list.get(position).get("i").equals(null)){
                    return 3;
                }
                else if (list.get(position).get("j").equals(null)){
                    return 3;
                }
                else if(list.get(position).get("j").equals(0)){
                    return item2;   //这个也是有头标的
            }
                else  {
                    return item0;  //这个是没有头标的
                }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view;
        if (viewType == item1) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_litem_comment, parent, false);
            return new CommentAdapter.viewholder4(view);

        }
        else if (viewType == item2){
            view = LayoutInflater.from(context).inflate(R.layout.activity_litem_comment,parent,false);
            return new CommentAdapter.viewholder5(view);
        }
        else if(viewType==item0)  {
            view = LayoutInflater.from(context).inflate(R.layout.activity_item_comment, parent, false);
            return new CommentAdapter.viewholder3(view);
        }
        else {
            return null;
        }
    }

    @Override

    public void onBindViewHolder( RecyclerView.ViewHolder holder, final int position) {
//                int viewType=getItemViewType(position);
        if (holder instanceof viewholder3) {
            CommentAdapter.viewholder3 holder3 = (CommentAdapter.viewholder3) holder;
            holder3.author1.setText(list.get(position).get("author").toString());
            holder3.content1.setText(list.get(position).get("content").toString());
            holder3.time1.setText(list.get(position).get("time").toString());
            Glide.with(context).load(list.get(position).get("picture")).into(holder3.avater1);
        }
        else if(holder instanceof viewholder4){
            CommentAdapter.viewholder4 holder4 = (CommentAdapter.viewholder4) holder;
            holder4.type.setText("长");
            holder4.author2.setText(list.get(position).get("author").toString());
            holder4.content2.setText(list.get(position).get("content").toString());
            holder4.time2.setText(list.get(position).get("time").toString());
            Glide.with(context).load(list.get(position).get("picture")).into(holder4.avater2);
        }
        else if(holder instanceof viewholder5){
            CommentAdapter.viewholder5 holder5 = (CommentAdapter.viewholder5) holder;
            holder5.type.setText("短");
            holder5.author2.setText(list.get(position).get("author").toString());
            holder5.content2.setText(list.get(position).get("content").toString());
            holder5.time2.setText(list.get(position).get("time").toString());
            Glide.with(context).load(list.get(position).get("picture")).into(holder5.avater2);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

            public class viewholder3 extends RecyclerView.ViewHolder {
                private TextView author1,content1,time1;
                private ImageView avater1;
                viewholder3(@NonNull View itemView) {

                    super(itemView);
                    author1 =itemView.findViewById(R.id.author1);
                    content1= itemView.findViewById(R.id.Content1);
                    time1 = itemView.findViewById(R.id.time1);
                    avater1 = itemView.findViewById(R.id.avater1);
                }
            }
            public class viewholder4 extends RecyclerView.ViewHolder {//长
                private TextView commment,author2,content2,time2,type;
                private ImageView avater2;
                viewholder4(@NonNull View itemView) {

                    super(itemView);
                    type = itemView.findViewById(R.id.type);
                    commment= itemView.findViewById(R.id.ShortCommentNumber);
                    author2=itemView.findViewById(R.id.author2);
                    time2  = itemView.findViewById(R.id.time2);
                    content2= itemView.findViewById(R.id.Content2);
                    avater2  = itemView.findViewById(R.id.avater2);
                }
            }
            public class viewholder5 extends RecyclerView.ViewHolder { //短
                private TextView commment,author2,content2,time2,type;
                private ImageView avater2;
                viewholder5(@NonNull View itemView) {

                    super(itemView);
                    type = itemView.findViewById(R.id.type);
                    commment= itemView.findViewById(R.id.ShortCommentNumber);
                    author2=itemView.findViewById(R.id.author2);
                    time2  = itemView.findViewById(R.id.time2);
                    content2= itemView.findViewById(R.id.Content2);
                    avater2  = itemView.findViewById(R.id.avater2);
                }
            }
}
