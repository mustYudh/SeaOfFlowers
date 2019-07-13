package com.hzrcht.seaofflowers.module.login.activity.presenter;

import com.hzrcht.seaofflowers.module.login.bean.UserSigBean;
import com.yu.common.mvp.Viewer;

/**
 * @author yudneghao
 * @date 2019-06-03
 */
public interface SelectGenderViewer extends Viewer {
    void selectSexSuccess();

    void selectSexFail();

    void getUserSigSuccess(UserSigBean userSigBean);

    void getUserSigFail();
}
