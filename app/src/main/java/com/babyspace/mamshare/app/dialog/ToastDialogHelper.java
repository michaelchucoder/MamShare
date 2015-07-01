package com.babyspace.mamshare.app.dialog;

import android.content.Context;
import android.widget.Toast;

import com.babyspace.mamshare.framework.utils.UIUtils;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.dialog
 * Author: MichaelChuCoder
 * Date: 2015/7/1
 * Time: 17:09
 * To change this template use File | Settings | File and Code Templates.
 */
public class ToastDialogHelper {

    public static Toast getDialog(String content) {
        return getDialog(content, null);
    }

    public static Toast getDialog(String content, Context context) {
        if (context == null)
            context = UIUtils.getForegroundActivity();

        Toast makeText = Toast.makeText(context, content, Toast.LENGTH_LONG);

        return makeText;
    }

}