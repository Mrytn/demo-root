package com.example.dubboproductmd.impl;

import com.example.dubboproductmd.ProductService;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author PeiDong Yan
 * @date 2023/03/15 16:58
 */
@DubboService(interfaceClass = ProductService.class)
public class ProductServiceImpl implements ProductService {


}
