package com.haitun.book.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.haitun.book.R;
import com.haitun.book.adapter.MyBookListAdapter;
import com.haitun.book.api.BookService;
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

public class SearchByCategoryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.book_list_recycler_view)
    RecyclerView bookListRecyclerView;
    @Bind(R.id.book_list_refresh)
    SwipeRefreshLayout bookListRefresh;

    private MyBookListAdapter adapter;
    private LinearLayoutManager manager;
    private String query;

    public SearchByCategoryFragment() {
    }

    public static SearchByCategoryFragment newInstance() {
        SearchByCategoryFragment fragment = new SearchByCategoryFragment();
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
        adapter = new MyBookListAdapter(getActivity());
        bookListRecyclerView.setLayoutManager(manager);
        bookListRecyclerView.setAdapter(adapter);
        bookListRefresh.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        bookListRefresh.setOnRefreshListener(this);
        bookListRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        if (!bookListRefresh.isRefreshing()) {

                        }
                    }
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    isSlidingToLast = false;
                } else {
                    isSlidingToLast = true;
                }
            }
        });
    }

    public void search(String s) {
        this.query = s;
        Log.i("ss",s);
        loadData(s);
    }

    private void loadData(String s) {
        BookService bookService = HTTPUtil.getInstance().getService(BookService.class);
        bookService.searchByCategory(s).subscribeOn(Schedulers.io())
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

                if (listBaseResponse.getResCode() == 0) {
                    adapter.clear();
                    adapter.addAll(listBaseResponse.getItem());
                } else {
                    ToastUtil.showShort(SearchByCategoryFragment.this.getActivity(), "没有更多了");
                }

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                bookListRefresh.setRefreshing(false);
                Log.i("BookListFragment", throwable.getMessage());
                ToastUtil.showShort(SearchByCategoryFragment.this.getActivity(), R.string.err_msg);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_category_list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        adapter.clear();
        if(!TextUtils.isEmpty(query)&&!TextUtils.isEmpty(query.trim())) {
            search(query);
        }else{
            bookListRefresh.setRefreshing(false);
        }
    }


}
