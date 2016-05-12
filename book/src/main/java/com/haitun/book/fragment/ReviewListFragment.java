package com.haitun.book.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.haitun.book.R;
import com.haitun.book.adapter.MyReviewListAdapter;
import com.haitun.book.api.ReviewService;
import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Book;
import com.haitun.book.utils.HTTPUtil;
import com.haitun.book.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ReviewListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @Bind(R.id.book_list_recycler_view)
    RecyclerView bookListRecyclerView;
    @Bind(R.id.book_list_refresh)
    SwipeRefreshLayout bookListRefresh;

    private MyReviewListAdapter adapter;
    private LinearLayoutManager manager;

    public ReviewListFragment() {
    }

    public static ReviewListFragment newInstance(String userId) {
        ReviewListFragment fragment = new ReviewListFragment();
        Bundle args=new Bundle();
        args.putString("userId",userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        manager = new LinearLayoutManager(getActivity());
        adapter=new MyReviewListAdapter(getActivity());
        bookListRecyclerView.setLayoutManager(manager);
        bookListRecyclerView.setAdapter(adapter);
        bookListRefresh.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        bookListRefresh.setOnRefreshListener(this);
        loadData();
    }

    private void loadData() {

        ReviewService service= HTTPUtil.getInstance().getService(ReviewService.class);
        service.getReviewsByUserId(getArguments().getString("userId"))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(new Action0() {
            @Override
            public void call() {
                bookListRefresh.setRefreshing(true);
            }
        }).subscribe(new Action1<BaseResponse<List<Book>>>() {
            @Override
            public void call(BaseResponse<List<Book>> listBaseResponse) {
                bookListRefresh.setRefreshing(false);

                if(listBaseResponse.getResCode()==0){
                    adapter.addAll(listBaseResponse.getItem());
                }else{
                    ToastUtil.showShort(ReviewListFragment.this.getActivity(),"没有更多了");
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                bookListRefresh.setRefreshing(false);
                Log.i("BookListFragment",throwable.getMessage());
                ToastUtil.showShort(ReviewListFragment.this.getActivity(),R.string.err_msg);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_booklist_list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        loadData();
    }




}
