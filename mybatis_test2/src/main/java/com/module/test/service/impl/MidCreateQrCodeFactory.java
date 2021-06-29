package com.module.test.service.impl;

import com.module.test.service.MidCreateQrCodeService;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: cjt
 * @Date: 2021/05/11/9:56
 * @Description:
 */
public class MidCreateQrCodeFactory {

    private static Map<String, MidCreateQrCodeService> services = new ConcurrentHashMap<String, MidCreateQrCodeService>();

    public static MidCreateQrCodeService getByType(String type){
        return services.get(type);
    }

    public static void register(String type, MidCreateQrCodeService userPayService){
        Assert.notNull(type, "type can't be null");
        services.put(type, userPayService);
    }


}
