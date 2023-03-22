package com.demo.dubbo.product.service.impl;

import com.demo.dubbo.product.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author PeiDong Yan
 * @date 2023/03/22 14:11
 */
@DubboService()
public class DemoServiceImpl implements DemoService {

    @Override
    public String message(String msg) {
        return null;
    }
}
