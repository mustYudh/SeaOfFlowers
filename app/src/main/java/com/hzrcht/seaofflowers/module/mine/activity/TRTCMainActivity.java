package com.hzrcht.seaofflowers.module.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.TRTCMainActivityPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.TRTCMainActivityViewer;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tencent.trtc.TRTCStatistics;
import com.yu.common.mvp.PresenterLifeCycle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;


public class TRTCMainActivity extends BaseBarActivity implements TRTCMainActivityViewer {

    @PresenterLifeCycle
    private TRTCMainActivityPresenter presenter = new TRTCMainActivityPresenter(this);

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_trtcmain_view);
    }

    @Override
    protected void loadData() {

    }


}
