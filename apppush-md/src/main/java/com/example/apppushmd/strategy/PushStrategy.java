package com.example.apppushmd.strategy;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/***
 * @author PeiDong Yan
 * created at 2021/7/2 13:54
 *
 */
@Slf4j
public abstract class PushStrategy {
    public static final byte ANDROID = 0;
    public static final byte IOS = 1;
    //key:userId,value:clientId/registryId
    private final Map<String, String> clientMapping = new ConcurrentHashMap<>(1024);

    public void addMapping(String key, String value) {
        checkMappingSize();
        clientMapping.put(key, value);
    }

    public void addMapping(Map<String, String> map) {
        clientMapping.clear();
        clientMapping.putAll(map);
        log.info("clientMapping.size=" + clientMapping.size());
    }

    public void removeMapping(String key) {
        clientMapping.remove(key);
    }

    public String getClientId(String key) {
        if (null == key) {
            return null;
        }
        return clientMapping.get(key);
    }

    public abstract void toSingleUser(String userId, String title, String content, String intent);

    public abstract void toSingleUserList(Collection<String> userIds, String title, String content, String intent);

    public abstract String toBatchUser(Collection<String> userIds, String title, String content,
                                       String utl, String intent);

    public abstract Object toBatchUserSchedule(Collection<String> userIds, String title, String content,
                                               String intent, LocalDateTime localDateTime);

    public abstract Object toBatchUserByAlias(Collection<String> userIds, String title, String content, String intent);

    public abstract Object getTaskInfo(Set<String> set);

    public abstract void toGroup(String group);

    public abstract void toPlatform(byte platform);

    public abstract void toAll();

    public abstract void pushToAll(Collection<String> userIds, String title, String content, String intent);

    public abstract void searchPushToAll(Collection<String> userIds, String title, String content, String intent);

    public abstract void pushByTag(Collection<String> userIds, String title, String content, String intent);

    public abstract Object stopTask(String taskId);

    public abstract Object getScheduleTask(String taskId);

    public abstract Object deleteScheduleTask(String taskId);

    public abstract Object getTaskInfoWithAction(Set<String> set);

    public abstract Object getResultByGroupName(String groupName);

    public abstract Object getResultByGroupName(LocalDate date);

    public abstract Object getOnlineUser();

    public abstract Object getToken();

    public abstract Object deleteToken(String token);

    public abstract Object bindAlias(Map<String, String> map);

    public abstract Object getAliasByCid(Set<String> cids);

    public abstract Object getCidByAlias(String alias);

    public abstract Object batchUnbindAlias(Map<String, String> map);

    public abstract Object userBindTags(String cid, Set<String> tags);

    public abstract Object userBindTags(Set<String> cids, String tag);

    public abstract Object userUnBindTags(Set<String> cids, String tag);

    public abstract Object getUserTags(String cid);

    public abstract Object addUserBlack(Set<String> cids);

    public abstract Object removeUserBlack(Set<String> cids);

    public abstract Object getUserStatus(Set<String> cids);

    public abstract Object searchUsers();

    private void checkMappingSize() {
        if (clientMapping.size() > 10_000) {
//            throw new Exception("PushStrategy_clientMapping is more than 10000");
        }
    }

    public boolean hasData(String userId) {
        return clientMapping.containsKey(userId);
    }

    public List<String> getHasDataUserIds(List<String> userIds) {
        return CollUtil.isEmpty(userIds) ? null :
                userIds.stream().filter(clientMapping::containsKey).collect(Collectors.toList());
    }
}
