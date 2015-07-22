/**
 *
 */
package com.babyspace.mamshare.framework.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.michael.core.tools.ViewRelayoutUtil;

public class UiHelper {

    public static void showAlertDialog(Context mContext, String title, String message, String pButton, String nButton, DialogInterface.OnClickListener pListener,
                                       DialogInterface.OnClickListener nListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        pButton = pButton == null ? "确定" : pButton;
        if (pListener != null) {
            builder.setPositiveButton(pButton, pListener);
        }
        nButton = nButton == null ? "取消" : nButton;
        if (nListener != null) {
            builder.setNegativeButton(nButton, nListener);
        }
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static void showSystemDialog(Context mContext, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("系统提示");
        builder.setMessage(message);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public static Dialog buildDialogStyle1(Context context, String title, String left, String right, android.view.View.OnClickListener listener_left, android.view.View.OnClickListener listener_right) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        View view = View.inflate(context, R.layout.dialog_style_layout, null);
        ViewRelayoutUtil.relayoutViewWithScale(view, MamShare.screenWidthScale);

        TextView txt_title = (TextView) view.findViewById(R.id.txt_title_dl);
        txt_title.setText(title);
        Button btn_left = (Button) view.findViewById(R.id.btn_left_dl);
        btn_left.setText(left);
        btn_left.setOnClickListener(listener_left);
        Button btn_right = (Button) view.findViewById(R.id.btn_right_dl);
        btn_right.setText(right);
        btn_right.setOnClickListener(listener_right);
        window.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static ProgressDialog showProgressDialog(Context context, String message, boolean cancelable) {
        return ProgressDialog.show(context, null, message, true, cancelable);
    }

    public static void toast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static Dialog buildDialog(Context mContext, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
