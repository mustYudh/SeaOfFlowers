package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineSuggestionPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.MineSuggestionViewer;
import com.hzrcht.seaofflowers.module.view.ClearEditText;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;


public class MineSuggestionActivity extends BaseBarActivity implements MineSuggestionViewer {

    @PresenterLifeCycle
    private MineSuggestionPresenter presenter = new MineSuggestionPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_mine_suggestion_view);
    }

    @Override
    protected void loadData() {
        setTitle("意见反馈");
        ClearEditText et_suggestion = bindView(R.id.et_suggestion);
        bindView(R.id.tv_commit, view -> {
            if (TextUtils.isEmpty(et_suggestion.getText().toString())) {
                ToastUtils.show("请留下您的宝贵意见和建议，我们将努力改进！");
                return;
            }
            
        });
    }
}
