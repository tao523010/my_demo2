package com.module.test.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author: cjt
 * @Date: 2021/05/11/9:51
 * @Description:
 */
public interface MidCreateQrCodeService {

    String createQrCode(String id, String url, JSONObject extraInfo);
}
