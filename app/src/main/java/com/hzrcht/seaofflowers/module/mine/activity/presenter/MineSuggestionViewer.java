package com.hzrcht.seaofflowers.module.mine.activity.presenter;

import com.yu.common.mvp.Viewer;


public interface MineSuggestionViewer extends Viewer {
    void userFeedbackSuccess();

    void userFeedbackFail(String msg);
}
