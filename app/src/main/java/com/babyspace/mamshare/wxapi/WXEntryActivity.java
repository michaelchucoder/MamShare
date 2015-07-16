package com.babyspace.mamshare.wxapi;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.babyspace.mamshare.commons.AppConstants;
import com.babyspace.mamshare.commons.IntentParams;
import com.babyspace.mamshare.commons.UrlConstants;
import com.babyspace.mamshare.framework.utils.NetWorkUtil;
import com.babyspace.mamshare.framework.utils.UiHelper;
import com.google.gson.JsonObject;
import com.michael.core.okhttp.OkHttpCall;
import com.michael.core.tools.PreferencesUtil;
import com.michael.library.debug.L;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Calendar;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    public static final String AUTO_LOGIN = "auto_login";
    public static final String FROM = "from";
    // --------------进度条-------------//

    private static final String TAG = "WXEntryActivity";
    private static final int authType = 1;
    private IWXAPI api;
    private String interfaceToken;
    private String from;
    private Calendar calendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private String cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, AppConstants.WX_APP_ID, true);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq arg0) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onResp(BaseResp resp) {
        Bundle bundle = new Bundle();
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                PreferencesUtil.getPreferences("Wxin", "");
                interfaceToken = PreferencesUtil.getPreferences(AppConstants.INTERFACETOKEN, "");
                from = getIntent().getStringExtra(WXEntryActivity.FROM);
                resp.toBundle(bundle);
                Resp sp = new Resp(bundle);
                String code = sp.code;
                L.i(TAG, "返回的code:" + code);
                cancel = PreferencesUtil.getPreferences("Wxin", "");
                if ("wx_share".equals(cancel)) {
                    Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
                } else {
                    String getTokenPath = UrlConstants.WECHAT_GET_TOKEN + AppConstants.WX_APP_ID + "&secret=" + AppConstants.WX_APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
                    getAccess_token(getTokenPath);
                    this.finish();
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                cancel = PreferencesUtil.getPreferences("Wxin", "");
                if ("wx_login".equals(cancel)) {
                    Toast.makeText(this, "取消登录", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                cancel = PreferencesUtil.getPreferences("Wxin", "");
                if ("wx_login".equals(cancel)) {
                    Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "分享失败", Toast.LENGTH_LONG).show();
                }
                break;
        }
        finish();
    }
    /**
     * 微信返回的getAccess_token
     *
     * @param getTokenPath
     */
    private void getAccess_token(String getTokenPath) {
        Intent intent = new Intent();
        intent.putExtra("type", "show");
        intent.setAction(IntentParams.WEIXIN_LOGIN_DIALOG_BROADCAST);
        this.sendBroadcast(intent);

        if (!NetWorkUtil.networkCanUse(getApplicationContext())) {
            String msg = "请检查网络设置！";
            UiHelper.showSystemDialog(getApplicationContext(), msg);
            return;
        }
        JsonObject jo = new JsonObject();

        OkHttpCall.query(getTokenPath, jo, new OkHttpCall.HttpCallback() {
            @Override
            public void onFailure(Request request, Throwable e) {

            }

            @Override
            public void onSuccess(JsonObject result) {
                String access_token = result.get("access_token").getAsString();
                String expires_in = result.get("expires_in").getAsString();
                String refresh_token = result.get("refresh_token").getAsString();
                String openid = result.get("openid").getAsString();
                String scope = result.get("scope").getAsString();

                L.i(TAG, "access_token:" + access_token);
                L.i(TAG, "expires_in:" + expires_in);
                L.i(TAG, "refresh_token:" + refresh_token);
                L.i(TAG, "openid:" + openid);
                L.i(TAG, "scope:" + scope);
                String getInfoPath = UrlConstants.WECHAT_GET_INFO + access_token + "&openid=" + openid;
                /** 返回用户信息 */
                getUserInfo(getInfoPath);
            }
        });
    }

    /**
     * 返回用户信息
     *
     * @param getInfoPath
     */
    private void getUserInfo(String getInfoPath) {
        if (!NetWorkUtil.networkCanUse(getApplicationContext())) {
            String msg = "请检查网络设置！";
            UiHelper.showSystemDialog(getApplicationContext(), msg);
            return;
        }
        JsonObject jo = new JsonObject();
        OkHttpCall.query(getInfoPath, jo, new OkHttpCall.HttpCallback() {
            @Override
            public void onFailure(Request request, Throwable e) {

            }

            @Override
            public void onSuccess(JsonObject result) {
                if (result == null || result.isJsonNull()) {
                    return;
                }
                String openid = result.get("openid").getAsString();
                String nickname = result.get("nickname").getAsString();

                L.i(TAG, "openid:" + openid);
                L.i(TAG, "nickname:" + nickname);

                getThirdUserAuth(openid, nickname);
            }
        });
    }
    private void getThirdUserAuth(String openId, String nickName) {
        if (!NetWorkUtil.networkCanUse(getApplicationContext())) {
            String msg = "请检查网络设置！";
            UiHelper.showSystemDialog(getApplicationContext(), msg);
            return;
        }

        JsonObject jo = new JsonObject();
        jo.addProperty("authUserId", openId);
        jo.addProperty("nickName", nickName);
        jo.addProperty("authType", authType);


        OkHttpCall.query(UrlConstants.ThirdUserAuth, true, jo, new OkHttpCall.HttpCallback() {
            @Override
            public void onFailure(Request request, Throwable e) {

            }
            @Override
            public void onSuccess(JsonObject result) {
                String code = result.get("code").getAsString();
                if (code.equals("200")) {
                    JsonObject json_msg = result.get("msg").getAsJsonObject();
                    String auth = json_msg.get("auth").getAsString();
                    String userId = json_msg.get("userId").getAsString();
                    PreferencesUtil.putPreferences(PreferencesUtil.AUTH, auth);
                    PreferencesUtil.putPreferences(PreferencesUtil.USERID, userId);
                    PreferencesUtil.putPreferences(AUTO_LOGIN, false);// 如果是第三方登录，取消自动登录功能
                    PreferencesUtil.putPreferences("login_refresh", true);

                }
                if (code.equals("500")) {
                    String msg = "服务器内部错误，登录失败！";
                    UiHelper.showSystemDialog(getApplicationContext(), msg);
                } else {
                    String msg = "登录失败！";
                    UiHelper.showSystemDialog(getApplicationContext(), msg);
                }
            }
        });
    }

}