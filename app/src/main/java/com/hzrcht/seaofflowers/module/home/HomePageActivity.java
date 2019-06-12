package com.hzrcht.seaofflowers.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import com.denghao.control.TabItem;
import com.denghao.control.view.BottomNavigationView;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.home.presenter.HomePagePresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomePageViewer;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.utils.PressHandle;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yudenghao
 */
public class HomePageActivity extends BaseActivity implements HomePageViewer {

  private PressHandle pressHandle = new PressHandle(this);

  @PresenterLifeCycle HomePagePresenter presenter = new HomePagePresenter(this);
  private BottomNavigationView mBottomNavigationView;

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_home_page_view);
  }

  @Override protected void loadData() {
    List<TabItem> items = new ArrayList<>();
    mBottomNavigationView.initControl(this).setPagerView(items, 0);
    mBottomNavigationView.getControl().setOnTabClickListener((position, view) -> {

    });
  }

  @Override public void onBackPressed() {
    if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
      super.onBackPressed();
    }
  }
}
