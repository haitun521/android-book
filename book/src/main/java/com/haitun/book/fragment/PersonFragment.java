package com.haitun.book.fragment;

import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haitun.book.R;
import com.haitun.book.api.UserService;
import com.haitun.book.model.BaseResponse;
import com.haitun.book.model.User;
import com.haitun.book.utils.HTTPUtil;
import com.haitun.book.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by 笨笨 on 2016/4/28.
 */
public class PersonFragment extends BaseFragment {
    private static final String KEY = "id";
    @Bind(R.id.pb_loading)
    ContentLoadingProgressBar pbLoading;
    @Bind(R.id.person_id)
    TextView personId;
    @Bind(R.id.person_sex)
    TextView personSex;
    @Bind(R.id.person_major)
    TextView personMajor;
    @Bind(R.id.person_grade)
    TextView personGrade;
    @Bind(R.id.person_overdue)
    TextView personOverdue;
    private String id;

    public PersonFragment() {

    }

    public static PersonFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY, id);
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        id = getArguments().getString(KEY);
        loadData(id);
    }

    private void loadData(String id) {

        UserService userService= HTTPUtil.getInstance().getService(UserService.class);
        userService.getUser(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        pbLoading.setVisibility(View.VISIBLE);
                    }
                })
                .subscribe(new Action1<BaseResponse<User>>() {
                    @Override
                    public void call(BaseResponse<User> response) {
                        pbLoading.setVisibility(View.GONE);
                        if(response.getResCode()==0){
                            showData(response.getItem());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        pbLoading.setVisibility(View.GONE);
                        Log.i(TAG,throwable.getMessage());
                        ToastUtil.showShort(PersonFragment.this.getActivity(),R.string.err_msg);
                    }
                });
    }

    private void showData(User user) {
        personId.append(user.getId());
        personSex.append(user.getSex());
        personMajor.append(user.getMajor());
        personGrade.append(user.getGrade());
        personOverdue.append(String.valueOf(user.getOverdue())+"元");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}