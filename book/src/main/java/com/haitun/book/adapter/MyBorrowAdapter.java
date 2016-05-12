package com.haitun.book.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.model.Borrow;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 笨笨 on 2016/4/29.
 */
public class MyBorrowAdapter extends RecyclerView.Adapter<MyBorrowAdapter.ViewHodler> {
    private List<Borrow> mLists;

    public MyBorrowAdapter(List<Borrow> lists) {
        this.mLists = lists;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_borrow, parent, false);
        ViewHodler hodler = new ViewHodler(view);
        return hodler;
    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {
        Borrow b=mLists.get(position);
        holder.borrowName.setText(b.getBookName());
        holder.borrowDate.setText(b.getDate());

    }

    @Override
    public int getItemCount() {
        return mLists == null ? 0 : mLists.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        @Bind(R.id.borrow_name)
        TextView borrowName;
        @Bind(R.id.borrow_date)
        TextView borrowDate;
        public ViewHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
