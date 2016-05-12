package com.haitun.book.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
import com.haitun.book.api.ReviewService;
import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Book;
import com.haitun.book.utils.HTTPUtil;
import com.haitun.book.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 笨笨 on 2016/4/30.
 */
public class MyReviewListAdapter extends RecyclerView.Adapter<MyReviewListAdapter.ViewHolder> {

    private List<Book> lists;
    private Context context;

    public MyReviewListAdapter(Context context) {
        this.context = context;
        this.lists = new ArrayList<>();
    }

    public void addAll(List<Book> list) {
        this.lists.addAll(list);
        notifyDataSetChanged();
    }

    public void insertAll(List<Book> list) {
        this.lists.addAll(0, list);
        notifyItemRangeInserted(0, list.size());
    }


    public void clear() {
        this.lists.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booklist, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Book book = lists.get(position);
        Picasso.with(this.context).load(book.getImage()).into(holder.BookItemImage);
        holder.BookItemAuthor.setText(book.getAuthor());
        holder.BookItemPrice.setText(String.valueOf(book.getPrice()));
        holder.BookItemPubdate.setText(book.getDate());
        holder.BookItemTitle.setText(book.getName());
        holder.BookItemPublisher.setText(book.getPublisher());
        /*holder.fragmentBookItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("position", position + "");
                return false;
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return lists.size();
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

        private int position;

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

        @OnLongClick(R.id.fragment_book_item)
        public boolean onLongClick() {
            Log.i("position", getAdapterPosition() + "");
            position = getAdapterPosition();
            showAlertDialog();
            return false;
        }

        private void showAlertDialog() {

            new AlertDialog.Builder(context)
                    .setTitle("是否删除？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            delCacheData();
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }

        private void delCacheData() {
            int id=lists.get(position).getReviewId();
            lists.remove(position);
            notifyItemRemoved(position);
            ReviewService service = HTTPUtil.getInstance().getService(ReviewService.class);
            service.delete(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<BaseResponse>() {
                        @Override
                        public void call(BaseResponse listBaseResponse) {
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.i("ReviewListFragment", throwable.getMessage());
                            ToastUtil.showShort(context, R.string.err_msg);
                        }
                    });
        }

    }

}
