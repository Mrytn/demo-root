package com.demo.apppush.messagepush;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * description:
 *
 * @author PeiDong Yan
 * @date 2022/08/09 13:17
 */
@Data
@Accessors(chain = true)
public class PushBo {

    private String phone;

    private List<String> phoneList;

    private String title;

    private String content;

    private String url;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
