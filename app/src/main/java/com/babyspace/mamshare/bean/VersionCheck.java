package com.babyspace.mamshare.bean;

import java.io.Serializable;

public class VersionCheck implements Serializable {
    public int versionState; // ��ǰ�汾״̬ 0Ϊ���°汾 1��ǰ�����°汾��UI����ȥ�����¡���ť�Լ����ܾ����°�ť�� 2 ��ǰ�����°汾����Ϊͣ�ð汾��UIֻ����һ��ȥ�����¡���ť
    public String updateTitle; // �汾������ʾ����
    public String updateContent; // �汾������ʾ����
    public String updateTip; // �汾����ǿ��������ʾ�� ��versionState=2�������ֵ
    public String filePath; // ��׿�����ص�ַ
    public int isHaveAdvertisingImg; // �Ƿ��й��ͼ 1�� 0��
    public String advertisingImgUrl; // ���ͼƬ��ַ
    public int advertisingInterval; // ��ʾ��������
    public String backgroundColorValue; // ������ť������ɫ #ffdfc2
    public float transparency; // ������ť͸���� EG 0.6
    public String fontColorValue; // ������ť������ɫ #cc1d00
    public int isShowBotton; // �Ƿ���ʾ������ť 1��ʾ 0 ����ʾ


    @Override
    public String toString() {
        return "VersionCheck{" +
                "versionState=" + versionState +
                ", updateTitle='" + updateTitle + '\'' +
                ", updateContent='" + updateContent + '\'' +
                ", updateTip='" + updateTip + '\'' +
                ", filePath='" + filePath + '\'' +
                ", isHaveAdvertisingImg=" + isHaveAdvertisingImg +
                ", advertisingImgUrl='" + advertisingImgUrl + '\'' +
                ", advertisingInterval=" + advertisingInterval +
                ", backgroundColorValue='" + backgroundColorValue + '\'' +
                ", transparency=" + transparency +
                ", fontColorValue='" + fontColorValue + '\'' +
                ", isShowBotton=" + isShowBotton +
                '}';
    }
}
