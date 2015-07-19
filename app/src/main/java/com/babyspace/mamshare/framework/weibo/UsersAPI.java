/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.babyspace.mamshare.framework.weibo;

import android.content.Context;
import android.util.SparseArray;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.WeiboParameters;

/**
 * �����װ���û��ӿڡ�
 * ������ο�<a href="http://t.cn/8F1n1eF">�û��ӿ�</a>
 *
 * @author SINA
 * @since 2014-03-03
 */
public class UsersAPI extends AbsOpenAPI {

    private static final int READ_USER = 0;
    private static final int READ_USER_BY_DOMAIN = 1;
    private static final int READ_USER_COUNT = 2;

    private static final String API_BASE_URL = API_SERVER + "/users";

    private static final SparseArray<String> sAPIList = new SparseArray<String>();

    static {
        sAPIList.put(READ_USER, API_BASE_URL + "/show.json");
        sAPIList.put(READ_USER_BY_DOMAIN, API_BASE_URL + "/domain_show.json");
        sAPIList.put(READ_USER_COUNT, API_BASE_URL + "/counts.json");
    }

    public UsersAPI(Context context, String appKey, Oauth2AccessToken accessToken) {
        super(context, appKey, accessToken);
    }

    /**
     * �����û�ID��ȡ�û���Ϣ��
     *
     * @param uid      ��Ҫ��ѯ���û�ID
     * @param listener �첽����ص��ӿ�
     */
    public void show(long uid, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }

    /**
     * �����û��ǳƻ�ȡ�û���Ϣ��
     *
     * @param screen_name ��Ҫ��ѯ���û��ǳ�
     * @param listener    �첽����ص��ӿ�
     */
    public void show(String screen_name, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("screen_name", screen_name);
        requestAsync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET, listener);
    }

    /**
     * ͨ�����Ի�������ȡ�û������Լ��û����µ�һ��΢����
     *
     * @param domain   ��Ҫ��ѯ�ĸ��Ի���������ע�⣺��http://weibo.com/xxx�����xxx���֣�
     * @param listener �첽����ص��ӿ�
     */
    public void domainShow(String domain, RequestListener listener) {
        WeiboParameters params = new WeiboParameters();
        params.put("domain", domain);
        requestAsync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET, listener);
    }

    /**
     * ������ȡ�û��ķ�˿������ע����΢������
     *
     * @param uids     ��Ҫ��ȡ���ݵ��û�UID�����֮���ö��ŷָ�����಻����100��
     * @param listener �첽����ص��ӿ�
     */
    public void counts(long[] uids, RequestListener listener) {
        WeiboParameters params = buildCountsParams(uids);
        requestAsync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET, listener);
    }

    /**
     * -----------------------------------------------------------------------
     * ��ע�⣺���·����Ⱦ�ͬ��������������������Լ����첽������ƣ���ʹ�øú�����
     * -----------------------------------------------------------------------
     */

    /**
     * @see #show(long, RequestListener)
     */
    public String showSync(long uid) {
        WeiboParameters params = new WeiboParameters();
        params.put("uid", uid);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
     * @see #show(String, RequestListener)
     */
    public String showSync(String screen_name) {
        WeiboParameters params = new WeiboParameters();
        params.put("screen_name", screen_name);
        return requestSync(sAPIList.get(READ_USER), params, HTTPMETHOD_GET);
    }

    /**
     * @see #domainShow(String, RequestListener)
     */
    public String domainShowSync(String domain) {
        WeiboParameters params = new WeiboParameters();
        params.put("domain", domain);
        return requestSync(sAPIList.get(READ_USER_BY_DOMAIN), params, HTTPMETHOD_GET);
    }

    /**
     * @see #counts(long[], RequestListener)
     */
    public String countsSync(long[] uids) {
        WeiboParameters params = buildCountsParams(uids);
        return requestSync(sAPIList.get(READ_USER_COUNT), params, HTTPMETHOD_GET);
    }

    private WeiboParameters buildCountsParams(long[] uids) {
        WeiboParameters params = new WeiboParameters();
        StringBuilder strb = new StringBuilder();
        for (long cid : uids) {
            strb.append(cid).append(",");
        }
        strb.deleteCharAt(strb.length() - 1);
        params.put("uids", strb.toString());
        return params;
    }
}
