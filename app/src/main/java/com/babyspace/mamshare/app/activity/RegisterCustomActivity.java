package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;
import com.michael.library.camera.ImageUtility;
import com.michael.library.widget.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhuo on 2015-7-24.
 *
 * 编辑妈妈个性名片
 */
public class RegisterCustomActivity extends BaseActivity {

    Point mSize;

    @InjectView(R.id.register_custom_avatar)
    ImageView registerCustomAvatar;

    @InjectView(R.id.register_custom_my_role)
    MaterialEditText registerCustomMyRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_register_custom);
        ButterKnife.inject(this);


        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);

        Intent intent  = getIntent();

        Uri uri = intent.getData();

        Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(uri.getPath(), mSize.x, mSize.x);

        registerCustomAvatar.setImageBitmap(bitmap);
    }


    @OnClick({R.id.register_custom_submit, R.id.common_title_left})
    public void doOnClick(View view) {

        switch (view.getId()){

            case R.id.register_custom_submit:


                break;
            case R.id.common_title_left:


                break;

        }

    }


}
