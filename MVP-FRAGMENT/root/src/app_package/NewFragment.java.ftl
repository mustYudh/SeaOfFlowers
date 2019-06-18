package ${packageName};

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseFragment;
import ${packageName}.presenter.${fragmentClass}Presenter;
import ${packageName}.presenter.${fragmentClass}Viewer;
import com.yu.common.mvp.PresenterLifeCycle;


public class ${fragmentClass}Fragment extends BaseFragment implements ${fragmentClass}Viewer {

  @PresenterLifeCycle private ${fragmentClass}Presenter mPresenter = new ${fragmentClass}Presenter(this);

  @Override protected int getContentViewId() {
    return R.layout.${layoutName}_view;
  }

  @Override protected void setView(@Nullable Bundle savedInstanceState) {

  }

  @Override protected void loadData() {

  }
}
