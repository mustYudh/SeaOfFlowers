package com.hzrcht.seaofflowers.module.mine.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.hzrcht.seaofflowers.R;
import com.hzrcht.seaofflowers.base.BaseBarActivity;
import com.hzrcht.seaofflowers.module.mine.activity.bean.UserConfigBean;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.SystemSettingsPresenter;
import com.hzrcht.seaofflowers.module.mine.activity.presenter.SystemSettingsViewer;
import com.hzrcht.seaofflowers.module.view.MyOneLineView;
import com.hzrcht.seaofflowers.utils.ActivityManager;
import com.hzrcht.seaofflowers.utils.CheckVersionCodeUtils;
import com.hzrcht.seaofflowers.utils.MyHandler;
import com.yu.common.mvp.PresenterLifeCycle;
import com.yu.common.toast.ToastUtils;

import java.io.File;
import java.text.DecimalFormat;


public class SystemSettingsActivity extends BaseBarActivity implements SystemSettingsViewer {

    @PresenterLifeCycle
    private SystemSettingsPresenter mPresenter = new SystemSettingsPresenter(this);
    private MyOneLineView msg_voice;
    private MyOneLineView msg_shake;
    private MyOneLineView cache;

    @Override
    protected void setView(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_system_settings_view);
    }

    @Override
    protected void loadData() {
        setTitle("系统设置");
        msg_voice = bindView(R.id.msg_voice);
        msg_shake = bindView(R.id.msg_shake);
        MyOneLineView version_code = bindView(R.id.version_code);
        cache = bindView(R.id.cache);
        MyOneLineView opinion = bindView(R.id.opinion);


        msg_voice.init("消息提示音");
        msg_voice.showSwitchView(true);
        msg_shake.init("消息提示震动");
        msg_shake.showSwitchView(true);
        version_code.init("版本号");
        version_code.showRightText(true);
        version_code.setTextRight(CheckVersionCodeUtils.getVerName(getActivity()));
        cache.init("清除缓存");
        cache.showRightText(true);
        opinion.init("意见反馈");
        getcache();
        opinion.setOnClickListener(view -> {
            getLaunchHelper().startActivity(MineSuggestionActivity.class);
        });

        cache.setOnClickListener(view -> {
            clearCache();
        });

        mPresenter.getUserConfig();
        bindView(R.id.tv_exit, view -> {
            ActivityManager.getInstance().reLogin();
        });
    }

    @Override
    public void getUserConfigSuccess(UserConfigBean userConfigBean) {
        if (userConfigBean != null) {
            msg_voice.setSwichButton(userConfigBean.voice);
            msg_shake.setSwichButton(userConfigBean.shake);

            msg_voice.setSwichlistener(switchStatus -> {
                mPresenter.userEditConfig(userConfigBean, "2");
            });

            msg_shake.setSwichlistener(switchStatus -> {
                mPresenter.userEditConfig(userConfigBean, "3");
            });
        }
    }

    @Override
    public void userEditConfigSuccess(UserConfigBean userConfigBean, String type) {
        switch (type) {
            case "2":
                msg_voice.setSwichButton(!userConfigBean.voice);
                userConfigBean.voice = !userConfigBean.voice;
                break;
            case "3":
                msg_shake.setSwichButton(!userConfigBean.shake);
                userConfigBean.shake = !userConfigBean.shake;
                break;
        }
    }

    private void getcache() {
        long filesize = 0;
        String cacheSize = "0kB";
        File fileDir = getFilesDir();
        File fileCache = getCacheDir();
        filesize += getDirSize(fileDir);
        filesize += getDirSize(fileCache);
        //2.2版本才有将应用缓存转移到内存卡
        if (isMethodsCompat(Build.VERSION_CODES.FROYO)) {
            File externalCacheDir = getExternalCacheDir(getActivity());
            filesize += getDirSize(externalCacheDir);
        }
        if (filesize > 0) {
            cacheSize = formatFileSize(filesize);
        }
        cache.setTextRight(cacheSize + "");
    }


    /**
     * 清除app缓存
     */
    public void clearCache() {

        @SuppressLint("HandlerLeak") final Handler handler = new MyHandler(getActivity()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastUtils.show("缓存清除成功");
                    getcache();
                } else {
                    ToastUtils.show("缓存清除失败");
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }


    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        deleteDatabase("webview.db");
        deleteDatabase("webview.db-shm");
        deleteDatabase("webview.db-wal");
        deleteDatabase("webviewCache.db");
        deleteDatabase("webviewCache.db-shm");
        deleteDatabase("webviewCache.db-wal");
        //清除数据缓存
        clearCacheFolder(getFilesDir(), System.currentTimeMillis());
        clearCacheFolder(getCacheDir(), System.currentTimeMillis());
        //2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(Build.VERSION_CODES.FROYO)) {
            clearCacheFolder(getExternalCacheDir(this), System.currentTimeMillis());
        }

    }

    /**
     * 获取目录文件大小
     *
     * @param dir
     * @return
     */
    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    @TargetApi(8)
    public static File getExternalCacheDir(Context context) {

        return context.getExternalCacheDir();
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return B/KB/MB/GB
     */
    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }


    private int clearCacheFolder(File dir, long time) {
        int deletedFiles = 0;
        try {

            if (dir != null && dir.isDirectory()) {

                for (File child : dir.listFiles()) {
                    if (child.isDirectory()) {
                        deletedFiles += clearCacheFolder(child, time);
                    }
                    if (child.lastModified() < time) {
                        if (child.delete()) {
                            deletedFiles++;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        return deletedFiles;
    }
}
