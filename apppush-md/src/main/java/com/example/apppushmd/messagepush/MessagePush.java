package com.example.apppushmd.messagepush;

import cn.hutool.core.collection.CollUtil;
import com.example.apppushmd.strategy.PushStrategy;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/***
 * @author PeiDong Yan
 * created at 2021/7/2 13:53
 *
 */
@Slf4j
public class MessagePush {

    private PushStrategy pushStrategy;

    public MessagePush(PushStrategy pushStrategy) {
        this.pushStrategy = pushStrategy;
    }

    public void initMapping(Map<String, String> datas) {
        if (CollUtil.isEmpty(datas)) {
            return;
        }
        log.info("ClientMapping 共加载 {}  条记录", datas.size());
        this.pushStrategy.addMapping(datas);
    }

    /**
     * 注册对应关系
     *
     * @param userId
     * @param clientId
     */
    public void registryClientMapping(String userId, String clientId) {
        log.info("registryClientMapping-------------userId:{},clientId:{}", userId, clientId);
        this.pushStrategy.addMapping(userId, clientId);
    }

    public void removeClientMapping(String userId) {
        this.pushStrategy.removeMapping(userId);
    }

    /**
     * 同内容单推
     *
     * @param userId
     * @param title
     * @param content
     */
    public void toSingleUser(String userId, String title, String content, byte type) {
        this.pushStrategy.toSingleUser(userId, title, content, getIntent(type));
    }

    /**
     * 同内容批量单推
     *
     * @param userIds
     * @param title
     * @param content
     */
    public void toBatchSingleUser(Collection<String> userIds, String title, String content, byte type) {
        this.pushStrategy.toSingleUserList(userIds, title, content, getIntent(type));
    }

    /**
     * 同内容批量推
     *
     * @param userIds
     * @param title
     * @param content
     */
    public String toBatchUser(Collection<String> userIds, String title, String content, String url, byte type) {
        return this.pushStrategy.toBatchUser(userIds, title, content, url, getIntent(type));
    }

    /**
     * 同内容批量推(定时发送)
     *
     * @param userIds
     * @param title
     * @param content
     */
    public Object toBatchUserSchedule(Collection<String> userIds, String title, String content, byte type,
                                      LocalDateTime localDateTime) {
        return this.pushStrategy.toBatchUserSchedule(userIds, title, content, getIntent(type), localDateTime);
    }

    /**
     * 别名批量推
     *
     * @param userIds
     * @param title
     * @param content
     */
    public Object toBatchUserByAlias(Collection<String> userIds, String title, String content, byte type) {
        return this.pushStrategy.toBatchUserByAlias(userIds, title, content, getIntent(type));
    }

    public Object getTaskInfo(String taskId) {
        Object taskInfo = this.pushStrategy.getTaskInfo(new HashSet<>(Arrays.asList(taskId)));
        return taskInfo;
    }

    public void pushToAll(Collection<String> userIds, String title, String content, byte intent) {
        this.pushStrategy.pushToAll(userIds, title, content, getIntent(intent));
    }

    public void searchPushToAll(Collection<String> userIds, String title, String content, byte intent) {
        this.pushStrategy.searchPushToAll(userIds, title, content, getIntent(intent));
    }

    public void pushByTag(Collection<String> userIds, String title, String content, byte intent) {
        this.pushStrategy.pushByTag(userIds, title, content, getIntent(intent));
    }

    public Object stopTask(String taskId) {
        return this.pushStrategy.stopTask(taskId);
    }

    public Object getScheduleTask(String taskId) {
        return this.pushStrategy.getScheduleTask(taskId);
    }

    public Object deleteScheduleTask(String taskId) {
        return this.pushStrategy.deleteScheduleTask(taskId);
    }

    public Object getTaskInfoWithAction(Set<String> set) {
        return this.pushStrategy.getTaskInfoWithAction(set);
    }

    public Object getResultByGroupName(String groupName) {
        return this.pushStrategy.getResultByGroupName(groupName);
    }

    public Object getResultByGroupName(LocalDate date) {
        return this.pushStrategy.getResultByGroupName(date);
    }

    public Object getOnlineUser() {
        return this.pushStrategy.getOnlineUser();
    }

    public Object getToken() {
        return this.pushStrategy.getToken();
    }

    public Object deleteToken(String token) {
        return this.pushStrategy.deleteToken(token);
    }

    public Object bindAlias(Map<String, String> map) {
        return this.pushStrategy.bindAlias(map);
    }

    public Object getAliasByCid(Set<String> cids) {
        return this.pushStrategy.getAliasByCid(cids);
    }

    public Object getCidByAlias(String alias) {
        return this.pushStrategy.getCidByAlias(alias);
    }

    public Object batchUnbindAlias(Map<String, String> map) {
        return this.pushStrategy.batchUnbindAlias(map);
    }

    public Object userBindTags(String cid, Set<String> tags) {
        return this.pushStrategy.userBindTags(cid, tags);
    }

    public Object userBindTags(Set<String> cids, String tag) {
        return this.pushStrategy.userBindTags(cids, tag);
    }

    public Object userUnBindTags(Set<String> cids, String tag) {
        return this.pushStrategy.userUnBindTags(cids, tag);
    }

    public Object getUserTags(String cid) {
        return this.pushStrategy.getUserTags(cid);
    }

    public Object addUserBlack(Set<String> cids) {
        return this.pushStrategy.addUserBlack(cids);
    }

    public Object removeUserBlack(Set<String> cids) {
        return this.pushStrategy.removeUserBlack(cids);
    }

    public Object getUserStatus(Set<String> cids) {
        return this.pushStrategy.getUserStatus(cids);
    }

    public Object searchUsers() {
        return this.pushStrategy.searchUsers();
    }

    private String getIntent(byte type) {
        String intent = null;
        if (MessageConstants.MessageType.SCHEDULE == type) {
            intent = MessageConstants.MessageIntent.SCHEDULE_INTENT;
        } else if ((MessageConstants.MessageType.FLOW == type)) {
            intent = MessageConstants.MessageIntent.FLOW_INTENT;
        }

        return intent;
    }
}
