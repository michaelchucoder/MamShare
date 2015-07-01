package com.babyspace.mamshare.app.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babyspace.mamshare.R;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.dialog
 * Author: MichaelChuCoder
 * Date: 2015/7/1
 * Time: 16:26
 * To change this template use File | Settings | File and Code Templates.
 */
public class BaseDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //可以用下面的方法得到参数
//      mNum = getArguments().getInt("num");
        LayoutInflater mInflater = LayoutInflater.from(getActivity());
        View v = mInflater.inflate(R.layout.mc_fragment_dialog, null);
        return new AlertDialog.Builder(getActivity())
                .setTitle("你喜欢啥")
                .setView(v)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //确定按钮do something
                            }
                        }
                )
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //取消按钮do something
                            }
                        }
                )
                .create();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
