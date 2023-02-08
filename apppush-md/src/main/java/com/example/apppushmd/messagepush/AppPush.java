package com.example.apppushmd.messagepush;

import com.example.apppushmd.strategy.PushStrategy;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/***
 * @author PeiDong Yan
 * created at 2021/7/2 13:53
 *
 */
@Slf4j
public class AppPush {

    private final ConcurrentHashMap<Integer, PushStrategy> pushAppMap = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, AppDto> pushConfigMap = new ConcurrentHashMap<>();

    public AppPush(Map<Integer, PushStrategy> pushaAppMap) {
        this.pushAppMap.clear();
        this.pushAppMap.putAll(pushaAppMap);
    }

    public void add(Integer appId, PushStrategy pushStrategy) {
        this.pushAppMap.put(appId, pushStrategy);
    }

    public void configAddAll(Map<Integer,AppDto> map) {
        this.pushConfigMap.clear();
        this.pushConfigMap.putAll(map);
    }
    public void configAdd(AppDto dto) {

        this.pushConfigMap.put(dto.getId(),dto);
    }

    public String getAppName(Integer appId) {
        if (null == appId) {
            return "";
        }
        return Optional.ofNullable(pushConfigMap.get(appId)).map(AppDto::getName).orElse("");
    }

    /**
     * 同内容批量推
     *
     * @param userIds
     * @param title
     * @param content
     */
    public String toBatchUser(Integer appId, Collection<String> userIds, String title, String content,
                              String url, Byte type) {
        PushStrategy pushStrategy = this.pushAppMap.get(appId);
        if (null != pushStrategy) {
            return pushStrategy.toBatchUser(userIds, title, content, url, null);
        } else {
//            throw new PushFlowException(EMessagePush.APP_CONFIG_ERROR.getCode(), String.format("app配置不存在" +
//                    "，appId:{%s}", appId));
        }
        return null;
    }

    /**
     * 同内容批量推(定时发送)
     *
     * @param userIds
     * @param title
     * @param content
     */
    public Object toBatchUserSchedule(Integer appId, Collection<String> userIds, String title, String content,
                                      Byte type,
                                      LocalDateTime localDateTime) {
        return this.pushAppMap.get(appId).toBatchUserSchedule(userIds, title, content, getIntent(type), localDateTime);
    }

    /**
     * 别名批量推
     *
     * @param userIds
     * @param title
     * @param content
     */
    public Object toBatchUserByAlias(Integer appId, Collection<String> userIds, String title, String content,
                                     Byte type) {
        return this.pushAppMap.get(appId).toBatchUserByAlias(userIds, title, content, getIntent(type));
    }

    public Object getTaskInfo(Integer appId, String taskId) {
        Object taskInfo = this.pushAppMap.get(appId).getTaskInfo(new HashSet<>(Arrays.asList(taskId)));
        return taskInfo;
    }

    public Object getTaskInfoWithAction(Integer appId, Set<String> set) {
        return this.pushAppMap.get(appId).getTaskInfoWithAction(set);
    }

    public Object getToken(Integer appId) {
        return this.pushAppMap.get(appId).getToken();
    }

    public Object deleteToken(Integer appId, String token) {
        return this.pushAppMap.get(appId).deleteToken(token);
    }

    public Object bindAlias(Integer appId, Map<String, String> map) {
        return this.pushAppMap.get(appId).bindAlias(map);
    }

    public Object getAliasByCid(Integer appId, Set<String> cids) {
        return this.pushAppMap.get(appId).getAliasByCid(cids);
    }

    public Object getCidByAlias(Integer appId, String alias) {
        return this.pushAppMap.get(appId).getCidByAlias(alias);
    }

    public Object batchUnbindAlias(Integer appId, Map<String, String> map) {
        return this.pushAppMap.get(appId).batchUnbindAlias(map);
    }

    public Object addUserBlack(Integer appId, Set<String> cids) {
        return this.pushAppMap.get(appId).addUserBlack(cids);
    }

    public Object removeUserBlack(Integer appId, Set<String> cids) {
        return this.pushAppMap.get(appId).removeUserBlack(cids);
    }

    public Object getUserStatus(Integer appId, Set<String> cids) {
        return this.pushAppMap.get(appId).getUserStatus(cids);
    }

    public Object searchUsers(Integer appId) {
        return this.pushAppMap.get(appId).searchUsers();
    }

    private String getIntent(Byte type) {
        String intent = null;
        if (MessageConstants.MessageType.SCHEDULE == type) {
            intent = MessageConstants.MessageIntent.SCHEDULE_INTENT;
        } else if ((MessageConstants.MessageType.FLOW == type)) {
            intent = MessageConstants.MessageIntent.FLOW_INTENT;
        }

        return intent;
    }
}
