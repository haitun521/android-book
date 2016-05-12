package com.haitun.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.model.Comment;
import com.haitun.book.utils.RelativeDateFormat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 笨笨 on 2016/5/1.
 */
public class MyCommentAdapter extends RecyclerView.Adapter<MyCommentAdapter.ViewHolder> {

    private List<Comment> lists;

    public MyCommentAdapter(Context context) {
        this.lists = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment=lists.get(position);
        holder.commentContent.setText(comment.getContent());
        holder.commentDate.setText(RelativeDateFormat.format(comment.getDate()));

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public void addAll(List<Comment> list){
        this.lists.addAll(list);
        notifyDataSetChanged();
    }

    public void add(Comment comment) {
        this.lists.add(comment);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.comment_content)
        TextView commentContent;
        @Bind(R.id.comment_date)
        TextView commentDate;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
