package com.haitun.book.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.api.BookService;
import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Book;
import com.haitun.book.utils.HTTPUtil;
import com.haitun.book.utils.ToastUtil;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BookDetailActivity extends AppCompatActivity {


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.book_detail_image)
    ImageView bookDetailImage;
    @Bind(R.id.book_detail_title)
    TextView bookDetailTitle;
    @Bind(R.id.book_detail_author)
    TextView bookDetailAuthor;
    @Bind(R.id.book_detail_publisher)
    TextView bookDetailPublisher;
    @Bind(R.id.book_detail_pubdate)
    TextView bookDetailPubdate;
    @Bind(R.id.book_detail_price)
    TextView bookDetailPrice;
    @Bind(R.id.book_detail_page)
    TextView bookDetailPage;
    @Bind(R.id.book_detail_status)
    TextView bookDetailStatus;
    @Bind(R.id.book_detail_mysummary)
    TextView bookDetailMysummary;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.book_detail_isbn)
    TextView bookDetailIsbn;
    @Bind(R.id.book_detail_mine)
    CardView bookDetailMine;
    @BindColor(R.color.colorPrimary)
    int color;

    private int id;
    private String isbn;
    private SweetAlertDialog pDialog;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessActivity();
            }
        });

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(color);
        pDialog.setTitleText("加载中");
        pDialog.setCancelable(false);

        userId = getSharedPreferences("user", MODE_PRIVATE).getString("id", null);


        id = getIntent().getIntExtra("id", 0);
        isbn = getIntent().getStringExtra("ISBN");

        if (id != 0) {
            loadData();
        } else if (isbn != null) {
            loadDataByISBN();
            bookDetailStatus.setVisibility(View.GONE);
        }
        if (userId == null) {
            bookDetailStatus.setVisibility(View.GONE);
        } else {
            addReview();
        }

    }

    private void addReview() {
        BookService bookService = HTTPUtil.getInstance().getService(BookService.class);
        bookService.addReview(userId, id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseResponse>() {
                    @Override
                    public void call(BaseResponse baseResponse) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("BookDetailActivity", throwable.getMessage());
                        ToastUtil.showShort(BookDetailActivity.this, R.string.err_msg);
                    }
                });
    }

    private void loadDataByISBN() {

        BookService bookService = HTTPUtil.getInstance().getService(BookService.class);
        bookService.getBookByISBN(isbn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pDialog.show();
                    }
                })
                .subscribe(new Action1<BaseResponse<Book>>() {
                    @Override
                    public void call(BaseResponse<Book> bookBaseResponse) {
                        pDialog.dismissWithAnimation();
                        if (bookBaseResponse.getResCode() == 0) {
                            showData(bookBaseResponse.getItem());
                        } else {
                            ToastUtil.showShort(BookDetailActivity.this, R.string.err_msg);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        pDialog.dismissWithAnimation();
                        Log.i("BookDetailActivity", throwable.getMessage());
                        ToastUtil.showShort(BookDetailActivity.this, R.string.err_msg);
                    }
                });

    }

    private void accessActivity() {
        Intent intent = new Intent(BookDetailActivity.this, CommentActivity.class);
        intent.putExtra("bookId", id);
        startActivity(intent);

    }

    private void loadData() {

        BookService bookService = HTTPUtil.getInstance().getService(BookService.class);
        bookService.getBook(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pDialog.show();
                    }
                })
                .subscribe(new Action1<BaseResponse<Book>>() {
                    @Override
                    public void call(BaseResponse<Book> bookBaseResponse) {
                        pDialog.dismissWithAnimation();
                        if (bookBaseResponse.getResCode() == 0) {
                            showData(bookBaseResponse.getItem());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        pDialog.dismissWithAnimation();
                        Log.i("BookDetailActivity", throwable.getMessage());
                        ToastUtil.showShort(BookDetailActivity.this, R.string.err_msg);
                    }
                });

    }

    private void showData(Book book) {

        Picasso.with(BookDetailActivity.this).load(book.getImage().replace("mpic", "lpic")).into(bookDetailImage);
        bookDetailTitle.setText(book.getName());
        bookDetailAuthor.setText(book.getAuthor());
        bookDetailMysummary.setText(book.getSummary());
        bookDetailPage.setText(String.valueOf(book.getPage()));
        bookDetailPrice.setText(String.valueOf(book.getPrice()));
        bookDetailPubdate.setText(book.getDate());
        bookDetailPublisher.setText(book.getPublisher());
        bookDetailIsbn.setText(book.getIsbn());
        id = book.getId();
    }

}
