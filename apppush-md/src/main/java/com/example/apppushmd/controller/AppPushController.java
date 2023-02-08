package com.example.apppushmd.controller;

import com.example.apppushmd.messagepush.AppPush;
import com.example.apppushmd.messagepush.PushMessageDto;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author PeiDong Yan
 * @date 2023/02/07 14:46
 */
@RestController
@RequestMapping("push")
public class AppPushController {

    @Resource
    private AppPush appPush;

    @PostMapping("test")
    public void push(@RequestBody PushMessageDto message) {
        appPush.toBatchUser(message.getId(), message.getCids(), message.getTitle(), message.getContent(),
                message.getUrl(), null);
        System.out.println("push");
    }
}
