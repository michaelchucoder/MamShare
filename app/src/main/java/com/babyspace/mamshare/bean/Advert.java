package com.babyspace.mamshare.bean;

public class Advert {
    public static final int TO_GOODS_DETAILS = 1;
    public static final int TO_GOODS_LIST = 2;
    public static final int TO_GOODS_HTML = 3;
    public static final int TO_GOODS_LAST_MINUTE = 4;
    public static final int TO_GOODS_SALE = 5;
    public String DataValue; // �� DataType =1 ����Ʒҳ������Ʒ��goodsID ��ת����Ʒ����ҳ��ʱ goodsStockDetailId=0
    public int DataType;// �ֲ�ͼ�������� 1����Ʒҳ�� 2 ��Ʒ�б�ҳ 3 HTML��ַҳ�� 4 ��ת����ʱ����ҳ��(V1.4.1��Ӹ����ԣ� 5 ������(V2.1.1��Ӹ����ԣ�DataType =3 ���ΪHTML��ַ
    public String ImgesUrl;// �ֲ�ͼͼƬ��ַ
    public int AdvertisingFigureID;// �ֲ�ͼID��ʾ

    @Override
    public String toString() {
        return "Advert [DataValue=" + DataValue + ", DataType=" + DataType + ", ImgesUrl=" + ImgesUrl + ", AdvertisingFigureID=" + AdvertisingFigureID + "]";
    }

}
