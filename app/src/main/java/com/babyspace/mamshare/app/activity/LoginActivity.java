package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @InjectView(R.id.edit_login_account)
    EditText edit_login_account;

    @InjectView(R.id.edit_login_pwd)
    EditText edit_login_pwd;

    @InjectView(R.id.btn_login_submit)
    Button btn_login_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


    }

    @OnClick({R.id.btn_login_submit})
    public void doOnClick(View view) {
        Intent i = new Intent();

        switch (view.getId()) {
            case R.id.btn_login_submit:
                i.setClass(this, HomeGroupActivity.class);
                break;
        }
        startActivity(i);
    }

    private void makeShapes() {

        ShapeDrawable activeDrawable = new ShapeDrawable();
        ShapeDrawable inactiveDrawable = new ShapeDrawable();
        float mIndicatorSize=3f;
        activeDrawable.setBounds(0, 0, (int) mIndicatorSize,
                (int) mIndicatorSize);
        inactiveDrawable.setBounds(0, 0, (int) mIndicatorSize,
                (int) mIndicatorSize);

        int i[] = new int[2];
        i[0] = android.R.attr.textColorSecondary;
        i[1] = android.R.attr.textColorSecondaryInverse;
        TypedArray a = this.getTheme().obtainStyledAttributes(i);

        Shape s1 = new OvalShape();
        s1.resize(mIndicatorSize, mIndicatorSize);
        Shape s2 = new OvalShape();
        s2.resize(mIndicatorSize, mIndicatorSize);

        ((ShapeDrawable) activeDrawable).getPaint().setColor(
                a.getColor(0, Color.DKGRAY));
        ((ShapeDrawable) inactiveDrawable).getPaint().setColor(
                a.getColor(1, Color.LTGRAY));

        ((ShapeDrawable) activeDrawable).setShape(s1);
        ((ShapeDrawable) inactiveDrawable).setShape(s2);
    }
}
