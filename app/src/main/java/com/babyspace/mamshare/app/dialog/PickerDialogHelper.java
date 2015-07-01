package com.babyspace.mamshare.app.dialog;

import android.app.Dialog;
import android.support.v4.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.framework.utils.UIUtils;
import com.babyspace.mamshare.framework.utils.ViewUtils;

import java.lang.ref.WeakReference;

/**
 * Created with Android Studio
 * Package name: com.babyspace.mamshare.app.dialog
 * Author: MichaelChuCoder
 * Date: 2015/7/1
 * Time: 18:11
 * To change this template use File | Settings | File and Code Templates.
 */
public class PickerDialogHelper {

    public static Dialog getDialog(final ArrayMap<String, String> expressType, final CallBack<String> callback, String selectedkey) {

        final Dialog dialog = new Dialog(UIUtils.getForegroundActivity(), R.style.plane_dialog_style);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = 0.97f;
        lp.dimAmount = 0.7f;
        lp.gravity = Gravity.BOTTOM;

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setWindowAnimations(R.style.plane_picker_dialog_anim);
        window.setContentView(R.layout.view_picker_dialog);
        ListView lv = (ListView) window.findViewById(R.id.mListView);
        RelativeLayout root = (RelativeLayout) window.findViewById(R.id.mRoot);
        root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

        final PickerAdapter pickerAdapter = new PickerAdapter(expressType, selectedkey);
        lv.setAdapter(pickerAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.mRightIcon);
                mCheckBox.setChecked(!mCheckBox.isChecked());
                callback.pickedOut((String) parent.getAdapter().getItem(position));

                if (pickerAdapter.mSelectedView != null && pickerAdapter.mSelectedView.get() != null) {
                    pickerAdapter.mSelectedView.get().setChecked(false);
                    pickerAdapter.mSelectedView = new WeakReference<CheckBox>(mCheckBox);
                }
                dialog.dismiss();
            }

        });

        ViewUtils.setListViewHeightBasedOnChildren(lv);

        return dialog;
    }

    public static interface CallBack<T> {
        void pickedOut(T entry);
    }

    static class PickerAdapter extends BaseAdapter {
        ArrayMap<String, String> expressType;
        public String selectedkey;
        public WeakReference<CheckBox> mSelectedView;

        public PickerAdapter(ArrayMap<String, String> expressType, String selectedkey) {
            this.selectedkey = selectedkey;
            this.expressType = expressType;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = View.inflate(UIUtils.getContext(), R.layout.item_pick_dialog, null);

            TextView content = (TextView) convertView.findViewById(R.id.mContentLabel);
            content.setText(expressType.get(expressType.keyAt(position)));
            CheckBox mCheckBox = (CheckBox) convertView.findViewById(R.id.mRightIcon);
            System.out.println("selectKey=" + selectedkey);
            if (selectedkey != null && expressType.keyAt(position).equals(selectedkey)) {
                //System.out.println("第" + position + "位被 选中，key=" + expressType.keyAt(position));
                if (this.mSelectedView != null && mSelectedView.get() != null) {
                    //System.out.println("weak中有view，置否，清除");
                    mSelectedView.get().setChecked(false);
                    mSelectedView.clear();
                    mSelectedView = new WeakReference<CheckBox>(mCheckBox);
                }

                //System.out.println("向weak中添加view");

                mCheckBox.setChecked(true);
            } else {
                //System.out.println("第" + position + "位，key=" + expressType.keyAt(position));
                mCheckBox.setChecked(false);
            }

            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return expressType.keyAt(position);
        }

        @Override
        public int getCount() {
            return expressType.size();
        }
    }

    ;
}
