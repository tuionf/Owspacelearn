package com.example.tuionf.owspacelearn.view.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.tuionf.owspacelearn.MainActivity;
import com.example.tuionf.owspacelearn.MyApplication;
import com.example.tuionf.owspacelearn.R;
import com.example.tuionf.owspacelearn.di.component.DaggerSplashComponent;
import com.example.tuionf.owspacelearn.di.module.SplashModule;
import com.example.tuionf.owspacelearn.presenter.SplashContract;
import com.example.tuionf.owspacelearn.presenter.SplashPresenter;
import com.example.tuionf.owspacelearn.util.AppUtil;
import com.example.tuionf.owspacelearn.util.FileUtils;
import com.example.tuionf.owspacelearn.view.widget.FixedImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SplashActivity extends BaseActivity implements SplashContract.View,EasyPermissions.PermissionCallbacks{

    @BindView(R.id.splash_img) FixedImageView splashImg;

    @Inject
    SplashPresenter presenter;

    private static final int PERMISSON_REQUESTCODE = 1;
    /**
     * 需要进行检测的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSplashComponent.builder()
                .netComponent(MyApplication.getInstance().getNetComponent())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);

        initStatus();
    }

    private void initStatus() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestCodePermissions();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void delaySplash() {
        List<String> picList = FileUtils.getAllAD();

        if (picList.size() > 0) {
            int index = new Random().nextInt(picList.size());
            Logger.d("tuionf","----index---"+index);
            File file = new File(picList.get(index));
            InputStream fis = null;
            try {
                fis = new FileInputStream(file);
                splashImg.setImageDrawable(InputStream2Drawable(fis));
                animWelcomeImage();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                AssetManager am = this.getAssets();
                InputStream is = am.open("welcome_default.jpg");
                splashImg.setImageDrawable(InputStream2Drawable(is));
                animWelcomeImage();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

	private Drawable InputStream2Drawable(InputStream is) {
        Drawable drawable = BitmapDrawable.createFromStream(is,"splashImg");
        return drawable;
    }
    private void animWelcomeImage() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(splashImg,"translationX",-100f);
        objectAnimator.setDuration(1500);
        objectAnimator.start();
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //该注解在此处的意识是 只有所有申请的权限全部被授予之后才会走
	@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
    @AfterPermissionGranted(PERMISSON_REQUESTCODE)
    private void requestCodePermissions() {
        if (!EasyPermissions.hasPermissions(this,needPermissions)){
            EasyPermissions.requestPermissions(this,"应用需要这些权限",PERMISSON_REQUESTCODE,needPermissions);
        }else {
            setContentView(R.layout.activity_splash);
            ButterKnife.bind(this);
            delaySplash();
            String deviceId = AppUtil.getDeviceId(this);
            presenter.getSplash(deviceId);
//            splashImg.setImageResource(R.mipmap.welcome_default);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        recreate();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showMissingPermissionDialog();
    }

    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

        builder.setPositiveButton("设置",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);

        builder.show();
    }

    /**
     * 启动应用的设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
