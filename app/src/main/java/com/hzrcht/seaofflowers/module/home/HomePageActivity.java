package com.hzrcht.seaofflowers.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.denghao.control.TabItem;
import com.denghao.control.TabView;
import com.denghao.control.view.BottomNavigationView;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseActivity;
import com.hzrcht.seaofflowers.module.home.presenter.HomePagePresenter;
import com.hzrcht.seaofflowers.module.home.presenter.HomePageViewer;
import com.hzrcht.seaofflowers.module.testFragment.TestFragment;
import com.hzrcht.seaofflowers.module.testFragment.TestFragment1;
import com.hzrcht.seaofflowers.module.testFragment.TestFragment2;
import com.hzrcht.seaofflowers.module.testFragment.TestFragment4;
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
    mBottomNavigationView = bindView(R.id.bottom_navigation_view);
    List<TabItem> items = new ArrayList<>();
    items.add(new TabView(createTabView("假面舞会", R.mipmap.ic_launcher), new TestFragment()));
    items.add(new TabView(createTabView("约会电台", R.mipmap.ic_launcher), new TestFragment1()));
    items.add(new TabView(createTabView("消息中心", R.mipmap.ic_launcher), new TestFragment2()));
    items.add(new TabView(createTabView("个人中心", R.mipmap.ic_launcher), new TestFragment4()));
    mBottomNavigationView.initControl(this).setPagerView(items, 0);
    mBottomNavigationView.getControl().setOnTabClickListener((position, view) -> {

    });
  }

  public View createTabView(String name, int drawable) {
    View view =
        LayoutInflater.from(this).inflate(R.layout.home_table_layout, mBottomNavigationView, false);
    ImageView imageView = view.findViewById(R.id.tab_icon);
    TextView tabName = view.findViewById(R.id.tab_name);
    imageView.setImageResource(drawable);
    tabName.setText(name);
    return view;
  }

  @Override public void onBackPressed() {
    if (!pressHandle.handlePress(KeyEvent.KEYCODE_BACK)) {
      super.onBackPressed();
    }
  }
}
