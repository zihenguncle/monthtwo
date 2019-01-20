package com.example.month.url;

import retrofit2.http.PUT;

public class Apis {

    public static final String LOGIN_URL = "user/login?mobile=%s&password=%s";
    public static final String RES_URL = "user/reg?mobile=%s&password=%s";

    public static final String LIST_URL = "product/getProducts";

    public static final String DETAIL_URL = "product/getProductDetail?pid=%s";

    public static final String HEAD_IAMGE = "file/upload";


    public static final String ONR_URL = "product/getCatagory";

    public static final String TWO_URL = "product/getProductCatagory?cid=%d";

    //商品列表
    public static final String LIST_DATA_URL = "product/getProducts?pscid=%d";


    //添加购物车
    public static final String ADDCAR_URL = "product/addCart?uid=%d&pid=%d";

    //查询购物车
    public static final String SELECT_URL = "product/getCarts?uid=%d";

}
