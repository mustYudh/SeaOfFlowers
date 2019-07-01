package com.hzrcht.seaofflowers.module.home.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeSearchPresenter;
import com.hzrcht.seaofflowers.module.home.activity.presenter.HomeSearchViewer;
import com.hzrcht.seaofflowers.module.home.adapter.HomeSearchRvAdapter;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.yu.common.mvp.PresenterLifeCycle;

import java.util.ArrayList;
import java.util.List;


public class HomeSearchActivity extends BaseBarActivity implements HomeSearchViewer {
    private List<String> list = new ArrayList<>();
    @PresenterLifeCycle
    private HomeSearchPresenter presenter = new HomeSearchPresenter(this);
    private RecyclerView mSearch;

    @Override
    protected int getActionBarLayoutId() {
        return R.layout.activity_home_search_view_bar;
    }

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_home_search_view);
    }

    @Override
    protected void loadData() {
        ClearEditText ed_search = bindView(R.id.ed_search);

        for (int i = 0; i < 10; i++) {
            list.add("");
        }
        mSearch = bindView(R.id.rv_search);
        LinearLayout ll_add = bindView(R.id.ll_add);
        ll_add.setOnClickListener(view -> {
            finish();
        });
        mSearch.setLayoutManager(new LinearLayoutManager(getActivity()));

        ed_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputMgr = (InputMethodManager) getActivity()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMgr.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
                    if (TextUtils.isEmpty(ed_search.getText().toString().trim())) {
//                        ToastUtil.showCommonToast("请输入搜索内容");
                    } else {
                        HomeSearchRvAdapter adapter = new HomeSearchRvAdapter(R.layout.item_home_search, list, getActivity());
                        mSearch.setAdapter(adapter);
                    }
                    return true;
                }
                return false;
            }
        });


    }
}
