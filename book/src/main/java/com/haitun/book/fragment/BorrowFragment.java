package com.haitun.book.fragment;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.adapter.MyBorrowAdapter;
import com.haitun.book.api.BorrowService;
import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.Borrow;
import com.haitun.book.utils.HTTPUtil;
import com.haitun.book.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class BorrowFragment extends BaseFragment {

    private static final String KEY = "id";
    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar pbLoading;
    @Bind(R.id.list)
    RecyclerView listView;
    @Bind(R.id.tv_load_empty)
    TextView tvLoadEmpty;

    private List<Borrow> lists;
    private MyBorrowAdapter adapter;
    private String id;

    public BorrowFragment() {
    }

    public static BorrowFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY, id);
        BorrowFragment fragment = new BorrowFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        id = getArguments().getString(KEY);
        lists = new ArrayList<>();
        adapter = new MyBorrowAdapter(lists);
        listView.setAdapter(adapter);
        loadData();
    }

    private void loadData() {

        BorrowService borrowService = HTTPUtil.getInstance().getService(BorrowService.class);
        borrowService.getBorrowList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Action1<BaseResponse<List<Borrow>>>() {
                    @Override
                    public void call(BaseResponse<List<Borrow>> listBaseResponse) {
                        pbLoading.setVisibility(View.GONE);
                        if (listBaseResponse.getResCode() == 0) {
                            showData(listBaseResponse.getItem());
                        } else {
                            ToastUtil.showShort(BorrowFragment.this.getContext(), R.string.err_msg);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        pbLoading.setVisibility(View.GONE);
                        Log.i(TAG, throwable.getMessage());
                        ToastUtil.showShort(BorrowFragment.this.getContext(), R.string.err_msg);
                    }
                });
    }

    private void showData(List<Borrow> item) {
        if(item==null||item.size()==0){
            tvLoadEmpty.setVisibility(View.VISIBLE);
            return ;
        }
        tvLoadEmpty.setVisibility(View.GONE);
        lists.addAll(item);
        adapter.notifyDataSetChanged();
       /* Snackbar.make(listView, String.format(getResources().getString(R.string.borrow_desc),item.size()), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
    }


    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_borrow_list;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
