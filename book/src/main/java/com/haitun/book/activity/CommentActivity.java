package com.haitun.book.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.adapter.MyCommentAdapter;
import com.haitun.book.api.CommentService;
import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Comment;
import com.haitun.book.utils.HTTPUtil;
import com.haitun.book.utils.RelativeDateFormat;
import com.haitun.book.utils.ToastUtil;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class CommentActivity extends AppCompatActivity {

    @Bind(R.id.tv_load_empty)
    TextView tvLoadEmpty;
    @Bind(R.id.comment_recycler_view)
    RecyclerView commentRecyclerView;
    @Bind(R.id.edit_content)
    EditText editContent;
    @Bind(R.id.send_btn)
    Button sendBtn;

    private MyCommentAdapter adapter;
    private int bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bookId=getIntent().getIntExtra("bookId",0);

        adapter = new MyCommentAdapter(this);
        commentRecyclerView.setAdapter(adapter);

        loadData();

    }

    @OnClick(R.id.send_btn)
    public void sendComment(){
        String content=editContent.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            return ;
        }

        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editContent.getWindowToken(),0);
        Comment comment=new Comment(RelativeDateFormat.date2String(new Date()),content);
        adapter.add(comment);
        editContent.setText(null);
        editContent.clearFocus();

        addComment(content);

    }

    private void addComment(String content) {
        CommentService commentService = HTTPUtil.getInstance().getService(CommentService.class);
        commentService.addComment(content,bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<BaseResponse>() {
            @Override
            public void call(BaseResponse baseResponse) {
                if(baseResponse.getResCode()==1){
                    ToastUtil.showShort(CommentActivity.this, R.string.err_msg);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("CommentActivity", throwable.getMessage());
                ToastUtil.showShort(CommentActivity.this, R.string.err_msg);
            }
        });

    }

    private void loadData() {
        CommentService commentService = HTTPUtil.getInstance().getService(CommentService.class);
        commentService.getCommentList(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<BaseResponse<List<Comment>>>() {
                    @Override
                    public void call(BaseResponse<List<Comment>> listBaseResponse) {

                        if (listBaseResponse.getResCode() == 0) {
                            showData(listBaseResponse.getItem());
                        } else {
                            tvLoadEmpty.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("CommentActivity", throwable.getMessage());
                        ToastUtil.showShort(CommentActivity.this, R.string.err_msg);
                    }
                });


    }

    private void showData(List<Comment> item) {
        adapter.addAll(item);
    }

}
