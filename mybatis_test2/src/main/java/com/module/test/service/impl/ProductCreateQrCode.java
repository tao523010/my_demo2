package com.module.test.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.module.test.service.MidCreateQrCodeService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @Author: cjt
 * @Date: 2021/05/11/9:54
 * @Description:
 */
@Component
public class ProductCreateQrCode implements MidCreateQrCodeService, InitializingBean {
    @Override
    public String createQrCode(String id, String url, JSONObject extraInfo) {
        System.out.println("productQrCode");
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        MidCreateQrCodeFactory.register("productQrCode",this);
    }
}
