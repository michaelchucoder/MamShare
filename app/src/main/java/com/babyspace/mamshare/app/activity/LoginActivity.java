package com.babyspace.mamshare.app.activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.babyspace.mamshare.R;
import com.babyspace.mamshare.app.dialog.ToastHelper;
import com.babyspace.mamshare.basement.BaseActivity;
import com.babyspace.mamshare.bean.LoginEvent;
import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.IntentParams;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.framework.utils.AccessTokenKeeper;
import com.babyspace.mamshare.framework.utils.NetWorkUtil;
import com.babyspace.mamshare.framework.utils.UiHelper;
import com.babyspace.mamshare.framework.weibo.UsersAPI;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpExecutor;
import com.michael.core.tools.MD5Util;
import com.michael.core.tools.SPrefUtil;
import com.michael.library.debug.L;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    public static final int QQ = 2;
    public static final int SINA = 3;
    protected static final String TAG = "LoginActivity";
    public static QQAuth mQQAuth;
    protected Tencent tencent;

    @InjectView(R.id.login_account_edit)
    EditText etUsername;
    @InjectView(R.id.login_password_edit)
    EditText etPwd;


    @InjectView(R.id.common_title_left)
    ImageButton common_title_left;
    @InjectView(R.id.common_title_text)
    TextView common_title_text;
    @InjectView(R.id.common_title_right)
    ImageButton common_title_right;

    private String mobile;// 帐号
    private String password;// 密码
    private String auth;
    private String userId;
    private int authType = 0;
    private String from;
    private UserInfo mInfo; // QQ用户信息
    private String openId; // 用户openId
    private String nickName; // 用户的昵称
    private WeiboAuth weiboAuth;
    /**
     * 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能
     */
    private Oauth2AccessToken mAccessToken;

    /**
     * 注意：SsoHandler 仅当 SDK 支持 SSO 时有效
     */
    private SsoHandler mSsoHandler;


    private IWXAPI iwxapi;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @SuppressWarnings("null")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            ProgressDialog dialog = null;
            if (action.equals(IntentParams.WEIXIN_LOGIN_DIALOG_BROADCAST)) {
                String string = intent.getExtras().getString("type");
                if (string.endsWith("show")) {
                    dialog = ProgressDialog.show(LoginActivity.this, "", "正在登陆中");
                } else {
                    dialog.dismiss();
                }
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.AppBaseTheme);
        initView();
        registerToThird();
        registerReceiver();

    }

    private void initView() {
        common_title_left.setImageResource(R.drawable.ic_back);
        common_title_text.setText("登录");

    }

    private void registerReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(IntentParams.WEIXIN_LOGIN_DIALOG_BROADCAST);
        //注册广播
        registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void registerToThird() {
        // 通过WXAPIFactory工厂，获取IWXPI的实例
        iwxapi = WXAPIFactory.createWXAPI(LoginActivity.this, AppConstants.WX_APP_ID, true);
        // 将应用的appid注册到微信
        iwxapi.registerApp(AppConstants.WX_APP_ID);

        tencent = Tencent.createInstance(AppConstants.QQ_APP_ID, this);
        mQQAuth = QQAuth.createInstance(AppConstants.QQ_APP_ID, this);

        weiboAuth = new WeiboAuth(this, AppConstants.SINA_APP_ID, AppConstants.SINA_REDIRECT_URL, "");
    }

    /**
     * 判断用户是否已安装微信
     *
     * @param api
     * @return
     */
    private boolean isWXAppInstalledAndSupported(IWXAPI api) {
        boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled() && api.isWXAppSupportAPI();
        return sIsWXAppInstalledAndSupported;
    }

    // 微信登录
    private void doIvWeixin() {
        SPrefUtil.putSPref("Wxin", "wx_login");
        L.i(TAG, "微信联合登录");
        if (!NetWorkUtil.networkCanUse(this)) {
            String msg = "请检查网络连接！";
            UiHelper.showSystemDialog(LoginActivity.this, msg);
            return;
        }
        if (!isWXAppInstalledAndSupported(iwxapi)) {
            Toast.makeText(this, "对不起，请先安装微信！", Toast.LENGTH_SHORT).show();
            return;
        } else {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            iwxapi.sendReq(req);
        }
    }

    // QQ登录
    private void doIvQQLogin() {
        if (!NetWorkUtil.networkCanUse(this)) {
            String msg = "请检查网络连接！";
            UiHelper.showSystemDialog(LoginActivity.this, msg);
            return;
        }
        // QQ联合登录
        authType = QQ;
        BaseUiListener listener = new BaseUiListener();
        if (!tencent.isSessionValid()) {
            tencent.login(LoginActivity.this, "all", listener);
        } else {
            ToastHelper.showToast(this, "QQ账号已经登录！");
        }
    }

    // QQ第三方联合登录接口
    private void getThirdUserAuth() {
        if (!NetWorkUtil.networkCanUse(LoginActivity.this)) {
            String msg = "请检查网络设置！";
            UiHelper.showSystemDialog(LoginActivity.this, msg);
            return;
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("authUserId", openId);
        jo.addProperty("nickName", nickName);
        jo.addProperty("authType", authType);
        OkHttpExecutor.query(UrlConstants.ThirdUserAuth, jo, LoginEvent.class, false, this);

    }

    private void doToRegister() {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 登录qq成功后，接收回调
        if (tencent != null) {
            tencent.onActivityResult(requestCode, resultCode, data);
        }

        // 新浪 SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private boolean doCheckInput() {

        mobile = etUsername.getText().toString().trim();
        password = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(mobile)) {
            ToastHelper.showToast(this, "请输入账号");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            ToastHelper.showToast(this, "请输入密码");
            return false;
        }
        return true;
    }

    private void doLogin() {
        if (!NetWorkUtil.networkCanUse(this)) {
            // Toast.makeText(M6go.context, "请检查网络设置！", Toast.LENGTH_SHORT).show();
            String msg = "请检查网络设置！";
            UiHelper.showSystemDialog(LoginActivity.this, msg);
            return;
        }

        if (!doCheckInput()) {
            return;
        }
        JsonObject jo = new JsonObject();
        jo.addProperty("logonName", mobile);
        jo.addProperty("password", MD5Util.getMD5String(password));
        OkHttpExecutor.query(UrlConstants.Login, jo, LoginEvent.class, false, this);

    }

    private void doForgetPassword() {
        Intent intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            from = getIntent().getStringExtra("from");
            if ("PersonalCenterActivity".equals(from)) {
                Intent intent = new Intent(this, HomePrefaceActivity.class);
                startActivity(intent);
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 返回用户的openid
     *
     * @param str
     */
    private void getOpenId(String str) {
        try {
            JSONObject json = new JSONObject(str);
            openId = json.getString("openid");
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "解析Json数据失败！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 返回用户的nickname昵称
     *
     * @param str
     */
    private void getNickName(String str) {
        try {
            JSONObject json = new JSONObject(str);
            nickName = json.getString("nickname");
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), "解析Json数据失败！", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void getUserInfo() {
        if (mQQAuth.isSessionValid()) {
            //startProgressBar("正在登录，请稍后...", new Thread(), true); // 开始加载数据
            mInfo = new UserInfo(this, mQQAuth.getQQToken());
            mInfo.getUserInfo(new getUserInfoUiListener());
        } else {
            Toast.makeText(getApplicationContext(), "对不起，你还没有登录QQ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 返回登录的结果
     */
    private class BaseUiListener implements IUiListener {

        @Override
        public void onError(UiError arg0) {
            // 登录失败
        }

        @Override
        public void onCancel() {
            // 取消登录
        }

        @Override
        public void onComplete(Object arg0) {
            getOpenId(arg0.toString());
            L.i(TAG, "openId:" + openId);
            getUserInfo();
        }
    }

    private class getUserInfoUiListener implements IUiListener {

        @Override
        public void onCancel() {
        }

        @Override
        public void onError(UiError arg0) {
        }

        @Override
        public void onComplete(Object arg0) {
            getNickName(arg0.toString());
            getThirdUserAuth();
        }

    }

    /**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     * 该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {

        @Override
        public void onComplete(Bundle values) {
            L.i(TAG, values.toString());
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                // 显示 Token

                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(LoginActivity.this, mAccessToken);
                Toast.makeText(LoginActivity.this,
                        R.string.weibosdk_demo_toast_auth_success, Toast.LENGTH_SHORT).show();
                final long uid = Long.parseLong(values.getString("uid"));
                UsersAPI mUsersApi = new UsersAPI(LoginActivity.this, AppConstants.SINA_APP_ID, mAccessToken);
                mUsersApi.show(uid, new RequestListener() {
                    @Override
                    public void onComplete(String s) {
                        L.i(TAG, s);
                        openId = String.valueOf(uid);
                        try {
                            JSONObject user = new JSONObject(s);
                            nickName = user.getString("screen_name");

                            getThirdUserAuth();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onWeiboException(WeiboException e) {

                    }
                });
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = getString(R.string.weibosdk_demo_toast_auth_failed);
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this,
                    R.string.weibosdk_demo_toast_auth_canceled, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this,
                    R.string.weibosdk_demo_toast_auth_failed, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick({R.id.btn_login_submit
            , R.id.btn_register_submit
            , R.id.tv_login_forgot_pwd
            , R.id.btn_login_tencent
            , R.id.btn_login_wechat
            , R.id.btn_login_sina})
    public void doOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_submit:
                ToastHelper.showToast(this,"哈哈登录");
                doLogin();
                break;
            case R.id.btn_register_submit:
                doToRegister();
                break;
            case R.id.tv_login_forgot_pwd:
                doForgetPassword();
                break;
            case R.id.btn_login_tencent:
                doIvQQLogin();
                break;
            case R.id.btn_login_wechat:
                doIvWeixin();
                break;
            case R.id.btn_login_sina:
                authType = SINA;
                mSsoHandler = new SsoHandler(this, weiboAuth);
                mSsoHandler.authorize(new AuthListener());
                break;
        }
    }

    public void onEventMainThread(LoginEvent event) {
        hideLoadingProgress();
        L.d(OkHttpExecutor.TAG, "onEventMainThread->" + event.getResultStr());

    }

}
