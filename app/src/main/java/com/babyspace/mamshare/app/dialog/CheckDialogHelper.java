package com.babyspace.mamshare.app.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.framework.utils.UIUtils;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.dialog
 * Author: MichaelChuCoder
 * Date: 2015/7/1
 * Time: 17:10
 * To change this template use File | Settings | File and Code Templates.
 */
public class CheckDialogHelper {
    public static Dialog getDialog(Activity ctx, String content, final CallBack callback) {
        LayoutInflater mInflater = LayoutInflater.from(ctx);
        return new AlertDialog.Builder(ctx)
                .setTitle(content)
                .setIcon(R.drawable.push)
                        //.setView(v)
                .setPositiveButton(callback.rightBtnText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callback.rightBtnClick(dialog);
                            }
                        }
                )
                .setNegativeButton(callback.leftBtnText,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                callback.leftBtnClick(dialog);
                            }
                        }
                )
                .create();
    }

    public static abstract class CallBack {
        public String leftBtnText;
        public String rightBtnText;

        public CallBack(String left, String right) {
            leftBtnText = left;
            rightBtnText = right;
        }

        public abstract void leftBtnClick(DialogInterface dialog);

        public abstract void rightBtnClick(DialogInterface dialog);
    }

}
