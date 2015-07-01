package com.babyspace.mamshare.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.commons.AppRuntime;


public class ViewUtils {

    /**
     * 改变View的大小
     *
     * @param view
     * @param w
     * @param h
     */
    public static void updateViewSize(View view, int w, int h) {
        LayoutParams params = view.getLayoutParams();
        params.width = w;
        params.height = h;
        view.setLayoutParams(params);
    }

    /**
     * 图片等比例缩放,按宽960（大于960）
     *
     * @param oldbmp 需要缩放的bitmap
     */
    public static Bitmap zoomDrawable(Bitmap oldbmp) {
        Bitmap newbmp = null;
        if (oldbmp != null) {
            int width = oldbmp.getWidth();
            int height = oldbmp.getHeight();
            if (width > 960) {
                Matrix matrix = new Matrix();
                float scale = ((float) 960 / width);
                matrix.postScale(scale, scale);
                newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
                        matrix, true);
                return newbmp;
            } else {
                return oldbmp;
            }
        }
        return oldbmp;
    }

    /**
     * 图片按一定长度缩放
     *
     * @param oldbmp 需要缩放的bitmap
     * @param w      缩放后宽
     * @param h      缩放后长
     */
    public static Bitmap zoomDrawable(Bitmap oldbmp, int w, int h) {
        Bitmap newbmp = null;
        if (oldbmp != null) {
            int width = oldbmp.getWidth();
            int height = oldbmp.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidth = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidth, scaleHeight);
            newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix,
                    true);
            return newbmp;
        }
        return oldbmp;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getCurrentScreenWidth(Context context) {
        int width = 0;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();// 屏幕宽度
        return width;
    }

    /**
     * 获取屏幕长度
     */
    public static int getCurrentScreenHeight(Context context) {
        int height = 0;
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 平铺背景图片
     *
     * @param resource 图片资源
     * @return Drawable
     */
    public static BitmapDrawable tiledDrawable(Context context, int resource) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                resource);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
        drawable.setDither(true);
        return drawable;
    }

    public static String TAG = "ViewUtils";
    /**
     * 快捷方式目标activity
     */
    public static Class<Activity> deskShortCutTarget;
    public static int shortcutLogoResId;
    public static int shortcutAppNameResId;

    /**
     * 创建快捷方式
     */
    public static void createDeskShortCut(Context context) {
        if (deskShortCutTarget == null) {
            Log.e(TAG, " ViewUtils 没有制定快捷方式目标activity！ ");
            return;
        }

        if (shortcutLogoResId == 0) {
            Log.e(TAG, " ViewUtils 没有制定快捷方式目标logo！ ");
            return;
        }

        if (shortcutAppNameResId == 0) {
            Log.e(TAG, " ViewUtils 没有制定快捷方式目标appname！ ");
            return;
        }

        // 创建快捷方式的Intent
        Intent shortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        shortcutIntent.putExtra("duplicate", false);
        // 需要显示的名称 R.string.app_name
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(shortcutLogoResId));
        // 快捷图片 R.drawable.app_logo
        Parcelable icon = Intent.ShortcutIconResource.fromContext(context.getApplicationContext(), shortcutAppNameResId);
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        Intent intent = new Intent(context.getApplicationContext(), deskShortCutTarget);
        // 下面两个属性是为了当应用程序卸载时桌面 上的快捷方式会删除
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        // 点击快捷图片，运行的程序主入口
        shortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
        // 发送广播。OK
        context.sendBroadcast(shortcutIntent);
        // 在配置文件中声明已经创建了快捷方式
        context.getSharedPreferences(AppRuntime.APP_ENTER_COUNT, Context.MODE_PRIVATE).edit().putBoolean(AppRuntime.IS_FIRST_ENTER, true).commit();

    }

    /**
     * 重新测量listview高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        // ((MarginLayoutParams) params).setMargins(10, 10, 10, 10); // 可删除

        listView.setLayoutParams(params);
    }
    public static void showToast(Context ctx, String toastTxt) {
        String fontBase = "<font color=\"#0F1099\">contentPosition</font>";

        Toast toast = Toast.makeText(ctx, null, Toast.LENGTH_SHORT);

        LinearLayout toastView = (LinearLayout) toast.getView();

        ImageView imageCodeProject = new ImageView(ctx);
        imageCodeProject.setImageResource(R.drawable.push);
        toastView.addView(imageCodeProject, 1);
        toastView.setBackgroundColor(0xF5FF69B4);

        toast.setText(Html.fromHtml(fontBase.replace("contentPosition", toastTxt)));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }
}
