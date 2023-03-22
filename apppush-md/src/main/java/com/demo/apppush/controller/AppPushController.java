package com.demo.apppush.controller;

import com.demo.apppush.messagepush.*;
import com.demo.apppush.messagepush.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * @author PeiDong Yan
 * @date 2023/02/07 14:46
 */
@RestController
@RequestMapping("push")
public class AppPushController {

    @Resource
    private AppPush appPush;

    @Resource
    @Lazy
    private MessagePush messagePush;

    @PostMapping("test")
    public void push(@RequestBody PushMessageDto message) {
        appPush.toBatchUser(message.getId(), message.getCids(), message.getTitle(), message.getContent(),
                message.getUrl(), null);
        System.out.println("push");
    }

    //单推
    @PostMapping("/t1")
    public void singlePush(@RequestBody PushBo bo) {
        messagePush.toSingleUser(bo.getPhone(), bo.getTitle(), bo.getContent(), MessageConstants.MessageType.SCHEDULE);
    }

    //批量单推
    @PostMapping("/tl1")
    public void batchSinglePush(@RequestBody PushBo bo) {
        messagePush.toBatchUser(bo.getPhoneList(), bo.getTitle(), bo.getContent(), bo.getUrl(),
                MessageConstants.MessageType.SCHEDULE);
    }

    //批量推
    @PostMapping("/bt1")
    public Object batchPush(@RequestBody PushBo bo) {
        return messagePush.toBatchUser(bo.getPhoneList(), bo.getTitle(), bo.getContent(), bo.getUrl(),
                MessageConstants.MessageType.SCHEDULE);
    }

//    //批量推
//    @PostMapping("/btt1")
//    public void batchPusht(@RequestBody AppPushBo bo) {
//        appPushApi.push(bo);
//    }

    //批量推定时发送
    @PostMapping("/bts1")
    public Object batchPushSchedule(@RequestBody PushBo bo) {
        return messagePush.toBatchUserSchedule(bo.getPhoneList(), bo.getTitle(), bo.getContent(),
                MessageConstants.MessageType.SCHEDULE, bo.getTime());
    }

    //别名批量推
    @PostMapping("/bt11")
    public Object batchPushByAlias(@RequestBody PushBo bo) {
        return messagePush.toBatchUserByAlias(bo.getPhoneList(), bo.getTitle(), bo.getContent(),
                MessageConstants.MessageType.SCHEDULE);
    }

    //获取推送结果
    @GetMapping("/s1")
    public Object getTask(@RequestParam("task") String task) {
        return messagePush.getTaskInfo(task);
    }

    //群推(全部用户)
    @PostMapping("/s2")
    public void pushToAll(@RequestBody PushBo bo) {
        messagePush.pushToAll(bo.getPhoneList(), bo.getTitle(), bo.getContent(), MessageConstants.MessageType.SCHEDULE);
    }

    //群推(筛选用户)
    @PostMapping("/tl2")
    public void searchPushToAll(@RequestBody PushBo bo) {
        messagePush.searchPushToAll(bo.getPhoneList(), bo.getTitle(), bo.getContent(),
                MessageConstants.MessageType.SCHEDULE);
    }

    //群推(使用标签快速推送）
    @PostMapping("/tl3")
    public void pushByTag(@RequestBody PushBo bo) {
        messagePush.pushByTag(bo.getPhoneList(), bo.getTitle(), bo.getContent(), MessageConstants.MessageType.SCHEDULE);
    }

    //停止任务
    @GetMapping("/s3")
    public Object stopTask(@RequestParam("task") String task) {
        return messagePush.stopTask(task);
    }

    //查询定时任务
    @GetMapping("/s4")
    public Object getScheduleTask(@RequestParam("task") String task) {
        return messagePush.getScheduleTask(task);
    }

    //删除定时任务
    @GetMapping("/s5")
    public Object deleteScheduleTask(@RequestParam("task") String task) {
        return messagePush.deleteScheduleTask(task);
    }

    //获取推送结果（含自定义事件）
    @GetMapping("/s6")
    public Object getTaskInfoWithAction(@RequestParam("task") Set<String> set) {
        return messagePush.getTaskInfoWithAction(set);
    }

    //任务组名查报表
    @GetMapping("/s7")
    public Object getResultByGroupName(@RequestParam("groupName") String groupName) {
        return messagePush.getResultByGroupName(groupName);
    }

    //获取单日推送数据(不支持当天)
    @GetMapping("/s8")
    public Object getResultByGroupName(@RequestParam("date") LocalDate date) {
        return messagePush.getResultByGroupName(date);
    }

    //获取24个小时在线用户数
    @GetMapping("/s9")
    public Object getOnlineUser() {
        return messagePush.getOnlineUser();
    }

    //获取鉴权token
    @GetMapping("/s10")
    public Object getToken() {
        return messagePush.getToken();
    }

    //删除token
    @GetMapping("/s11")
    public Object deleteToken(@RequestParam("token") String token) {
        return messagePush.deleteToken(token);
    }

    //绑定别名
    @PostMapping("/s12")
    public Object bindAlias(@RequestBody Map<String, String> map) {

        return messagePush.bindAlias(map);
    }

    //根据cid查询别名
    @GetMapping("/s13")
    public Object getAliasByCid(@RequestParam("task") Set<String> set) {
        return messagePush.getAliasByCid(set);
    }

    //根据别名查询cid
    @GetMapping("/s14")
    public Object getCidByAlias(@RequestParam("alias") String alias) {
        return messagePush.getCidByAlias(alias);
    }

    //批量解绑别名
    @GetMapping("/s15")
    public Object batchUnbindAlias(@RequestParam("map") Map<String, String> map) {
        return messagePush.batchUnbindAlias(map);
    }

    //一个用户绑定一批标签（此接口对单个cid有频控限制，每天只能修改一次，最多设置100个标签
    @GetMapping("/s16")
    public Object userBindTags(@RequestParam("cid") String cid, @RequestParam("task") Set<String> set) {
        return messagePush.userBindTags(cid, set);
    }

    //一批用户绑定一个标签
    @GetMapping("/s17")
    public Object userBindTag(@RequestParam("tag") String tag, @RequestParam("cids") Set<String> cids) {
        return messagePush.userBindTags(cids, tag);
    }

    //一批用户解绑一个标签
    @GetMapping("/s18")
    public Object userUnBindTags(@RequestParam("tag") String tag, @RequestParam("cids") Set<String> cids) {
        return messagePush.userUnBindTags(cids, tag);
    }

    //查询用户标签
    @GetMapping("/s19")
    public Object getUserTags(@RequestParam("cid") String cid) {
        return messagePush.getUserTags(cid);
    }

    //添加黑名单用户
    @GetMapping("/s20")
    public Object addUserBlack(@RequestParam("set") Set<String> set) {

        return messagePush.addUserBlack(set);
    }

    //移除黑名单用户
    @GetMapping("/s21")
    public Object removeUserBlack(@RequestParam("cids") Set<String> cids) {
        return appPush.removeUserBlack(1,cids);
    }

    //查询用户状态
    @GetMapping("/s22")
    public Object getUserStatus(@RequestParam("cids") Set<String> cids) {
        return messagePush.getUserStatus(cids);
    }

    //查询用户总量
    @GetMapping("/s23")
    public Object searchUsers() {
        return messagePush.searchUsers();
    }
}
