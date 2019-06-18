package com.hzrcht.seaofflowers.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.test.presenter.NewTestPresenter;
import com.hzrcht.seaofflowers.test.presenter.NewTestViewer;
import com.yu.common.mvp.PresenterLifeCycle;

public class NewTestActivity extends BaseBarActivity implements NewTestViewer {

  @PresenterLifeCycle private NewTestPresenter presenter = new NewTestPresenter(this);

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_new_test_view);
  }

  @Override protected void loadData() {

  }
}
