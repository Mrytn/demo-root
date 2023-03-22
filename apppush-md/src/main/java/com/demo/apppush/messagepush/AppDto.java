package com.demo.apppush.messagepush;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author PeiDong Yan
 * @date 2023/02/07 14:40
 */
@Data
@Accessors(chain = true)
public class AppDto {

    private Integer id;

    private String appId;

    private String appKey;

    private String masterSecret;

    private String name;
}
