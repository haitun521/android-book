package com.haitun.book.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.activity.BookDetailActivity;
import com.haitun.book.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 笨笨 on 2016/4/30.
 */
public class MyBookListAdapter extends RecyclerView.Adapter<MyBookListAdapter.ViewHolder> {

    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;
    private List<Book> lists;
    private Context context;

    public MyBookListAdapter(Context context) {
        this.context = context;
        this.lists = new ArrayList<>();
    }

    public void addAll(List<Book> list) {
        this.lists.addAll(list);
        notifyDataSetChanged();
    }
    public void insertAll(List<Book> list) {
        this.lists.addAll(0,list);
        notifyItemRangeInserted(0,list.size());
    }


    public void clear() {
        this.lists.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == NORMAL_ITEM) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booklist, parent, false);
            return new ViewHolder(v);
        } else if (viewType == GROUP_ITEM) {

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booklist_group_item, parent, false);
            return new GroupViewHolder(v);
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (holder instanceof GroupViewHolder) {
            if (position == 0) {
                ((GroupViewHolder) holder).title.setText(R.string.you_can_like);
            } else {
                ((GroupViewHolder) holder).title.setText(R.string.all_book_list);

            }
        }

        Book book = lists.get(position);
        Picasso.with(this.context).load(book.getImage()).into(holder.BookItemImage);
        holder.BookItemAuthor.setText(book.getAuthor());
        holder.BookItemPrice.setText(String.valueOf(book.getPrice()));
        holder.BookItemPubdate.setText(book.getDate());
        holder.BookItemTitle.setText(book.getName());
        holder.BookItemPublisher.setText(book.getPublisher());


    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return GROUP_ITEM;
        }
        return position == 3 ? GROUP_ITEM : NORMAL_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.fragment_book_item_image)
        ImageView BookItemImage;
        @Bind(R.id.fragment_book_item_title)
        TextView BookItemTitle;
        @Bind(R.id.fragment_book_item_author)
        TextView BookItemAuthor;
        @Bind(R.id.fragment_book_item_publisher)
        TextView BookItemPublisher;
        @Bind(R.id.fragment_book_item_pubdate)
        TextView BookItemPubdate;
        @Bind(R.id.fragment_book_item_price)
        TextView BookItemPrice;
        @Bind(R.id.fragment_book_item)
        CardView fragmentBookItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.fragment_book_item)
        public void onClick() {
            Log.i("position", getAdapterPosition() + "");
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra("id", lists.get(getAdapterPosition()).getId());
            context.startActivity(intent);
        }
    }

    public class GroupViewHolder extends ViewHolder {

        @Bind(R.id.grep_item_title)
        TextView title;

        public GroupViewHolder(View itemView) {
            super(itemView);
        }
    }
}
