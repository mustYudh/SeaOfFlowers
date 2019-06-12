package com.hzrcht.seaofflowers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.home.HomePageActivity;

public class SplashActivity extends BaseBarActivity {

  @Override protected void setView(@Nullable Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
  }

  @Override protected void loadData() {
    getLaunchHelper().startActivity(HomePageActivity.class);
  }
}
