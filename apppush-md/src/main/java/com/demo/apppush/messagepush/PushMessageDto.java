package com.demo.apppush.messagepush;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author PeiDong Yan
 * @date 2023/02/07 14:40
 */
@Data
@Accessors(chain = true)
public class PushMessageDto {

    private Integer id;

    private List<String> cids;

    private String title;

    private String content;

    private String url;
}
