package com.babyspace.mamshare.app.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.dialog
 * Author: MichaelChuCoder
 * Date: 2015/7/1
 * Time: 15:57
 * To change this template use File | Settings | File and Code Templates.
 */
public class PickerDialog extends DialogFragment {
    DatePickerDialog.OnDateSetListener callback;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int day = calendar.get(calendar.DAY_OF_MONTH);
        DatePickerDialog dialog;
        dialog = new DatePickerDialog(getActivity(), callback, year, month, day);
        return dialog;
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        callback = onDateSetListener;
    }
}
