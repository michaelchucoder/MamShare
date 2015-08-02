package com.babyspace.mamshare.app.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.DefaultResponseEvent;
import com.babyspace.mamshare.commons.RegisterConstant;
import com.babyspace.mamshare.commons.UrlConstants;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.SelectPicTools;
import com.michael.library.camera.ImageUtility;
import com.michael.library.widget.materialedittext.MaterialEditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by zhuo on 2015-7-24.
 * <p/>
 * 编辑妈妈个性名片
 */
public class RegisterCustomActivity extends BaseActivity {

    Point mSize;

    @InjectView(R.id.register_custom_avatar)
    ImageView registerCustomAvatar;

    @InjectView(R.id.register_custom_my_role)
    MaterialEditText registerCustomMyRole;

    String imageLocalPath = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_register_custom);
        ButterKnife.inject(this);


        setImage();


    }

    /**
     * 设置
     */
    private void setImage() {
        Display display = getWindowManager().getDefaultDisplay();
        mSize = new Point();
        display.getSize(mSize);

        Intent intent = getIntent();
        Uri uri = intent.getData();
        int from = intent.getIntExtra("from", -1);

        if (from == 1234) {


            imageLocalPath = SelectPicTools.getPath(RegisterCustomActivity.this, uri);


            registerCustomAvatar.setImageBitmap(BitmapFactory.decodeFile(imageLocalPath));


        } else {

//            L.d("asker","拍照获取的路径-------》"+uri.getPath());

            imageLocalPath = uri.getPath();
            Bitmap bitmap = ImageUtility.decodeSampledBitmapFromPath(uri.getPath(), mSize.x, mSize.x);


            registerCustomAvatar.setImageBitmap(bitmap);


        }
    }


    @OnClick({R.id.register_custom_submit, R.id.common_title_left})
    public void doOnClick(View view) {

        switch (view.getId()) {

            case R.id.register_custom_submit:

                String mamaRole = registerCustomMyRole.getText().toString().trim();

                if (mamaRole.length() > 0) {

//                    submitData(mamaRole);

                    Intent intent = new Intent(RegisterCustomActivity.this, RegisterActivity.class);

                    intent.putExtra(RegisterConstant.FLAG, RegisterConstant.FLAG_TO_REGTISTERNAME);

                    Bundle bundle = new Bundle();
                    bundle.putString(RegisterConstant.MAMA_ROLE_ICON_LOCAL_PATH, imageLocalPath);

                    bundle.putString(RegisterConstant.MAMA_ROLE, mamaRole);

                    bundle.putInt(RegisterConstant.FLAG, RegisterConstant.FLAG_TO_REGTISTERNAME);

                    intent.putExtras(bundle);

                    startActivity(intent);
                    finish();


                }


                break;
            case R.id.common_title_left:


                break;

        }

    }

    /*public String upLoadImage(){
        JsonObject parameter = new JsonObject();
        parameter.addProperty("uploadTpe",15);
//            parameter.addProperty("file",new );


        OkHttpCall.query(UrlConstants.imageUpload,parameter
                , uri.getPath(), new OkHttpCall.HttpCallback() {
            @Override
            public void onFailure(Request request, Throwable e) {

                L.d("asker","上传失败"+e.getMessage()+"------");

            }

            @Override
            public void onSuccess(JsonObject result) {
                L.d("asker","上传成功"+result.toString()+"------");

            }
        });





    }*/

    private void submitData(String mamaRole) {

        JsonObject jsonParameter = new JsonObject();

        jsonParameter.addProperty("mobile", "");
        jsonParameter.addProperty("password", "1");
        jsonParameter.addProperty("nickname", "");
        jsonParameter.addProperty("headIcon", "1");
        jsonParameter.addProperty("mRoleID", "");
        jsonParameter.addProperty("mamRoleName", "1");
        jsonParameter.addProperty("RegFrom", "");
        jsonParameter.addProperty("validCode", "1");


        OkHttpExecutor.query(UrlConstants.Register, jsonParameter, DefaultResponseEvent.class, false, this);

    }


}
