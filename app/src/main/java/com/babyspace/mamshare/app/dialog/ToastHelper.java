package com.babyspace.mamshare.app.dialog;

import android.content.Context;
import android.text.Html;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.framework.utils.UIUtils;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.dialog
 * Author: MichaelChuCoder
 * Date: 2015/7/1
 * Time: 17:09
 * To change this template use File | Settings | File and Code Templates.
 */
public class ToastHelper {

    public static Toast getDialog(String content) {
        return getDialog(content, null);
    }

    public static Toast getDialog(String content, Context context) {
        if (context == null)
            context = UIUtils.getForegroundActivity();

        Toast makeText = Toast.makeText(context, content, Toast.LENGTH_LONG);

        return makeText;
    }

    public static void showToast(Context ctx, String toastTxt) {
        String fontBase = "<font color=\"#0F1099\">contentPosition</font>";

        Toast toast = Toast.makeText(ctx, null, Toast.LENGTH_SHORT);

        LinearLayout toastView = (LinearLayout) toast.getView();

        ImageView imageCodeProject = new ImageView(ctx);
        imageCodeProject.setImageResource(R.drawable.push);
        toastView.addView(imageCodeProject, 0);
        toastView.setBackgroundColor(0xF5FF69B4);

        toast.setText(Html.fromHtml(fontBase.replace("contentPosition", toastTxt)));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();

    }
}