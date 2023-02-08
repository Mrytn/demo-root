package com.example.apppushmd.strategy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.AuthApi;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.api.StatisticApi;
import com.getui.push.v2.sdk.api.UserApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.*;
import com.getui.push.v2.sdk.dto.req.message.PushBatchDTO;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.GTNotification;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import com.getui.push.v2.sdk.dto.res.*;
import com.getui.push.v2.sdk.dto.res.statistic.StatisticDTO;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.hutool.crypto.SecureUtil.sha256;

/***
 * @author PeiDong Yan
 * created at 2021/7/2 14:00
 *
 */
@Slf4j
public class GetuiStrategy extends PushStrategy {

    @Getter
    @Setter
    private static String APP_ID;

    @Getter
    @Setter
    private static String APP_KEY;

    @Getter
    @Setter
    private static String MASTER_SECRET;

    private static final String DEFAULT_TITLE = "您有一条新消息！";

    private AuthApi authApi;

    private UserApi userApi;

    private final PushApi pushApi;

    private final StatisticApi statisticApi;

    public GetuiStrategy(UserApi userApi, AuthApi authApi, PushApi pushApi, StatisticApi statisticApi) {
        this.userApi = userApi;
        this.authApi = authApi;
        this.pushApi = pushApi;
        this.statisticApi = statisticApi;
    }

    public static GetuiStrategy build(String appId, String appKey, String masterSecret) {
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        //填写应用配置
        //oa
//        apiConfiguration.setAppId("ieSHzkwSqCASecNbVW88fA");
//        apiConfiguration.setAppKey("4Kz8x6hu7F9FqVBffpgHu1");
//        apiConfiguration.setMasterSecret("anYWrbZxIi9UNR6KMlbqk4");

        //个推demo
//        apiConfiguration.setAppId("6KtVqdt2xuAflm4vOVK957");
//        apiConfiguration.setAppKey("3edK0ePuaY9tW08NDmVXR9");
//        apiConfiguration.setMasterSecret("fTnAEmbNzk8UNuXGDoTGbA");
        APP_ID = appId;
        APP_KEY = appKey;
        MASTER_SECRET = masterSecret;
        //融合平台
        apiConfiguration.setAppId(appId);
        apiConfiguration.setAppKey(appKey);
        apiConfiguration.setMasterSecret(masterSecret);

        apiConfiguration.setDomain("https://restapi.getui.com/v2/");
        try {
            // 实例化ApiHelper对象，用于创建接口对象
            ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
            AuthApi authApi = apiHelper.creatApi(AuthApi.class);
            UserApi userApi = apiHelper.creatApi(UserApi.class);
            PushApi pushApi = apiHelper.creatApi(PushApi.class);
            StatisticApi statisticApi = apiHelper.creatApi(StatisticApi.class);
            return new GetuiStrategy(userApi, authApi, pushApi, statisticApi);
        } catch (Exception e) {
            log.info("app配置初始化失败，appId:{},appKey:{},masterSecret:{}", appId, appKey, apiConfiguration);
            return null;
        }
    }

    private PushDTO<Audience> buildPushDTO(String userId, String title, String content, String url, String intent) {
        //根据cid进行单推
        PushDTO<Audience> pushDTO = new PushDTO<>();
        //设置过期时间
        Settings settings = new Settings();
        settings.setTtl(360000);
        pushDTO.setSettings(settings);
        // 设置推送参数
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        PushMessage pushMessage = new PushMessage();
        pushDTO.setPushMessage(pushMessage);
        GTNotification notification = new GTNotification();
        pushMessage.setNotification(notification);
        notification.setTitle(Optional.ofNullable(title).orElse(DEFAULT_TITLE));
        notification.setBody(content);
        if (StrUtil.isEmpty(url)) {
            //回到应用首页
            notification.setClickType("startapp");
        } else {
            //跳转指定网址
            notification.setClickType("url");
            notification.setUrl(url);
        }
        //跳转应用内页面
//        notification.setClickType("intent");
//        notification.setUrl("intent://com.o2o.erp.oa/detail?#Intent;scheme=gtpushscheme;launchFlags=0x4000000;\n" +
//                "package=com.o2o.erp.oa;component=com.o2o.erp.oa/\n" +
//                "com.o2o.erp.oa.DemoActivity;S.payload=payloadStr;end");
        // 设置接收人信息
        Audience audience = new Audience();
        pushDTO.setAudience(audience);
        String clientId = getClientId(userId);
        if (StrUtil.isEmpty(clientId)) {
//            throw new ServiceException("clientId is null");
        }
        audience.addCid(clientId);
        // 设置厂商通道消息
        PushChannel pushChannel = new PushChannel();
        pushDTO.setPushChannel(pushChannel);
        // android
        AndroidDTO androidDTO = new AndroidDTO();
        pushChannel.setAndroid(androidDTO);
        Ups ups = new Ups();
        androidDTO.setUps(ups);
        ThirdNotification thirdNotification = new ThirdNotification();
        ups.setNotification(thirdNotification);
        thirdNotification.setTitle(title);
        thirdNotification.setBody(content);
        thirdNotification.setClickType("startapp");
//        ups.addOption("XM", "channel", "Default");
        ups.addOption("XM", "channel", "high_system");
        ups.addOption("HW", "/message/android/notification/importance", "NORMAL");
        // IOS
        IosDTO iosDTO = new IosDTO();
        pushChannel.setIos(iosDTO);
        Aps aps = new Aps();
        iosDTO.setAps(aps);
        Alert alert = new Alert();
        aps.setAlert(alert);
        alert.setTitle(title);
        alert.setBody(content);

        System.out.println(pushDTO);
        return pushDTO;
    }

    private PushDTO<Audience> buildPushDTOSchedule(String userId, String title, String content, String intent,
                                                   LocalDateTime localDateTime) {
        //根据cid进行单推
        PushDTO<Audience> pushDTO = new PushDTO<>();
        //设置过期时间
        Settings settings = new Settings();
        settings.setScheduleTime(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        settings.setTtl(36000000);
        pushDTO.setSettings(settings);
        // 设置推送参数
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        PushMessage pushMessage = new PushMessage();
        pushDTO.setPushMessage(pushMessage);
        GTNotification notification = new GTNotification();
        pushMessage.setNotification(notification);
        notification.setTitle(Optional.ofNullable(title).orElse(DEFAULT_TITLE));
        notification.setBody(content);
        //回到应用首页
        notification.setClickType("startapp");
        //跳转指定网址
//        notification.setClickType("url");
//        notification.setUrl("http://www.baidu.com");
        //跳转应用内页面
//        notification.setClickType("intent");
//        notification.setUrl("intent://com.o2o.erp.oa/detail?#Intent;scheme=gtpushscheme;launchFlags=0x4000000;\n" +
//                "package=com.o2o.erp.oa;component=com.o2o.erp.oa/\n" +
//                "com.o2o.erp.oa.DemoActivity;S.payload=payloadStr;end");
        // 设置接收人信息
        Audience audience = new Audience();
        pushDTO.setAudience(audience);
        String clientId = getClientId(userId);
        if (StrUtil.isEmpty(clientId)) {
//            throw new ServiceException("clientId is null");
        }
        audience.addCid(clientId);
        // 设置厂商通道消息
        PushChannel pushChannel = new PushChannel();
        pushDTO.setPushChannel(pushChannel);
        // android
        AndroidDTO androidDTO = new AndroidDTO();
        pushChannel.setAndroid(androidDTO);
        Ups ups = new Ups();
        androidDTO.setUps(ups);
        ThirdNotification thirdNotification = new ThirdNotification();
        ups.setNotification(thirdNotification);
        thirdNotification.setTitle(title);
        thirdNotification.setBody(content);
        thirdNotification.setClickType("startapp");
//        ups.addOption("XM", "channel", "Default");
        ups.addOption("XM", "channel", "high_system");
        ups.addOption("HW", "/message/android/notification/importance", "NORMAL");
        // IOS
        IosDTO iosDTO = new IosDTO();
        pushChannel.setIos(iosDTO);
        Aps aps = new Aps();
        iosDTO.setAps(aps);
        Alert alert = new Alert();
        aps.setAlert(alert);
        alert.setTitle(title);
        alert.setBody(content);

        System.out.println(pushDTO);
        return pushDTO;
    }

    //单推
    @Override
    public void toSingleUser(String userId, String title, String content, String intent) {
        try {
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByCid(buildPushDTO(userId
                    , title, content, null, intent));
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
//                log.info("push success to userId-{},time-{},getData-{}", userId, DateUtil.format(new Date(),
//                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push error to userId-{},time-{},getCode-{},getMsg-{}", userId,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(),
                        apiResult.getMsg());
            }
        } catch (Exception e) {
            log.error("push exception to userId-{},time-{}", userId, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
    }

    //批量单推
    @Override
    public void toSingleUserList(Collection<String> userIds, String title, String content, String intent) {
        userIds = getHasDataUserIds((List<String>) userIds);
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        userIds = getHasDataUserIds((List<String>) userIds);
        PushBatchDTO pushBatchDTO = new PushBatchDTO();
        userIds.forEach(userId -> pushBatchDTO.addPushDTO(buildPushDTO(userId, title, content, null, intent)));
        try {
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushBatchByCid(pushBatchDTO);
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
//                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
//                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push batch error to userIds-{},time-{},getCode-{},getMsg-{}", userIds,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(), apiResult.getMsg());
            }
        } catch (Exception e) {
            log.error("push batch exception to userIds-{},time-{}", userIds, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
    }

    //批量推
    @Override
    public String toBatchUser(Collection<String> userIds, String title, String content, String url, String intent) {
        try {
            String taskId = createMessage(title, content, url, intent);
            System.out.println("taskId="+taskId);
            Audience audience = new Audience();
            audience.setCid((List<String>) userIds);
            AudienceDTO audienceDTO = new AudienceDTO();
            audienceDTO.setTaskid(taskId);
            audienceDTO.setAudience(audience);
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushListByCid(audienceDTO);
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
//                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
//                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
//                throw new GeiTuiPushException(EMessagePush.CID_ERROR.getCode(), String.format("app推送，cid有误" +
//                        "cid:{%s},getCode-{%s},getMsg-{%s}", userIds, apiResult.getCode(), apiResult.getMsg()));
            }
            return taskId;

        } catch (Exception e) {
            log.error("push batch exception to appid-{},userIds-{},time-{}", userIds,
                    DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), e);
            throw e;
        }
    }

    //定时批量推
    @Override
    public Object toBatchUserSchedule(Collection<String> userIds, String title, String content, String intent,
                                      LocalDateTime localDateTime) {
        userIds = getHasDataUserIds((List<String>) userIds);
        try {
            String taskId = createMessageSchedule(title, content, intent, localDateTime);
            System.out.println("taskId:" + taskId);
            Audience audience = new Audience();
            List<String> cids = new ArrayList<>();
            userIds.forEach(userId -> cids.add(getClientId(userId)));
            audience.setCid(cids);
            AudienceDTO audienceDTO = new AudienceDTO();
            audienceDTO.setTaskid(taskId);
            audienceDTO.setAudience(audience);
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushListByCid(audienceDTO);
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push batch error to userIds-{},time-{},getCode-{},getMsg-{}", userIds,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(), apiResult.getMsg());
            }
            Thread t1 = new Thread() {
                @Override
                public void run() {
                    try {
                        int s = 0;
                        while (s < 60) {
                            getTaskInfo(Sets.newHashSet(taskId));
                            System.out.println("第:" + s + "秒");
//                            Thread.sleep(1000);
                            TimeUnit.SECONDS.sleep(1); // 休眠 1s
                            s += 1;
                        }
                        //TimeUnit.DAYS.sleep(1); // 休眠 1 天
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t1.start();

            return apiResult;
        } catch (Exception e) {
            log.error("push batch exception to userIds-{},time-{}", userIds, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
        return null;
    }

    //别名批量推
    @Override
    public Object toBatchUserByAlias(Collection<String> userIds, String title, String content, String intent) {
//        userIds = getHasDataUserIds((List<String>) userIds);
        try {
            String taskId = createMessage(title, content, null, intent);
            System.out.println("taskId:" + taskId);
            Audience audience = new Audience();
            List<String> alias = new ArrayList<>();
//            userIds.forEach(userId -> alias.add(getClientId(userId)));
            audience.setAlias((List<String>) userIds);
            AudienceDTO audienceDTO = new AudienceDTO();
            audienceDTO.setTaskid(taskId);
            audienceDTO.setAudience(audience);
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushListByAlias(audienceDTO);
            System.out.println(apiResult);
            // success
            if (apiResult.isSuccess()) {
                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push batch error to userIds-{},time-{},getCode-{},getMsg-{}", userIds,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(), apiResult.getMsg());
            }
            return apiResult;
        } catch (Exception e) {
            log.error("push batch exception to userIds-{},time-{}", userIds, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
        return null;
    }

    //群推(全部用户)
    @Override
    public void pushToAll(Collection<String> userIds, String title, String content, String intent) {
        userIds = getHasDataUserIds((List<String>) userIds);
        try {
            String taskId = createMessage(title, content, null, intent);
            System.out.println("taskId:" + taskId);
            Audience audience = new Audience();
            List<String> alias = new ArrayList<>();
            userIds.forEach(userId -> alias.add(getClientId(userId)));
            audience.setCid(alias);
            AudienceDTO audienceDTO = new AudienceDTO();
            audienceDTO.setTaskid(taskId);
            audienceDTO.setAudience(audience);
            PushDTO pushDTO = new PushDTO();
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushAll(pushDTO);
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push batch error to userIds-{},time-{},getCode-{},getMsg-{}", userIds,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(), apiResult.getMsg());
            }
        } catch (Exception e) {
            log.error("push batch exception to userIds-{},time-{}", userIds, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
    }

    //群推(筛选用户)
    @Override
    public void searchPushToAll(Collection<String> userIds, String title, String content, String intent) {
        userIds = getHasDataUserIds((List<String>) userIds);
        try {
            String taskId = createMessage(title, content, null, intent);
            System.out.println("taskId:" + taskId);
            Audience audience = new Audience();
            List<String> alias = new ArrayList<>();
            userIds.forEach(userId -> alias.add(getClientId(userId)));
            audience.setCid(alias);
            AudienceDTO audienceDTO = new AudienceDTO();
            audienceDTO.setTaskid(taskId);
            audienceDTO.setAudience(audience);
            PushDTO pushDTO = new PushDTO();
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushByTag(pushDTO);
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push batch error to userIds-{},time-{},getCode-{},getMsg-{}", userIds,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(), apiResult.getMsg());
            }
        } catch (Exception e) {
            log.error("push batch exception to userIds-{},time-{}", userIds, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
    }

    //群推(使用标签快速推送）
    @Override
    public void pushByTag(Collection<String> userIds, String title, String content, String intent) {
        userIds = getHasDataUserIds((List<String>) userIds);
        try {
            String taskId = createMessage(title, content, null, intent);
            System.out.println("taskId:" + taskId);
            Audience audience = new Audience();
            List<String> alias = new ArrayList<>();
            userIds.forEach(userId -> alias.add(getClientId(userId)));
            audience.setCid(alias);
            AudienceDTO audienceDTO = new AudienceDTO();
            audienceDTO.setTaskid(taskId);
            audienceDTO.setAudience(audience);
            PushDTO pushDTO = new PushDTO();
            ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushByFastCustomTag(pushDTO);
            System.out.println(apiResult);
            if (apiResult.isSuccess()) {
                // success
                log.info("push batch success to userIds-{},time-{},getData-{}", userIds, DateUtil.format(new Date(),
                        "yyyy-MM-dd HH:mm:ss"), apiResult.getData());
            } else {
                // failed
                log.error("push batch error to userIds-{},time-{},getCode-{},getMsg-{}", userIds,
                        DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"), apiResult.getCode(), apiResult.getMsg());
            }
        } catch (Exception e) {
            log.error("push batch exception to userIds-{},time-{}", userIds, DateUtil.format(new Date(), "yyyy-MM-dd " +
                    "HH:mm:ss"), e);
        }
    }

    //停止任务
    @Override
    public Object stopTask(String taskId) {
        ApiResult<Void> result = pushApi.stopPush(taskId);
        return result;
    }

    //查询定时任务
    @Override
    public Object getScheduleTask(String taskId) {
        ApiResult<Map<String, ScheduleTaskDTO>> result = pushApi.queryScheduleTask(taskId);
        return result;
    }

    //删除定时任务
    @Override
    public Object deleteScheduleTask(String taskId) {
        ApiResult<Void> result = pushApi.deleteScheduleTask(taskId);
        return result;
    }

    @Override
    public void toGroup(String group) {

    }

    @Override
    public void toPlatform(byte platform) {

    }

    @Override
    public void toAll() {

    }

    //创建消息体，为批量推前置条件
    private String createMessage(String title, String content, String url, String intent) {
        PushDTO<Audience> pushDTO = buildPushDTO(null, title, content, url, intent);
        ApiResult<TaskIdDTO> msg = pushApi.createMsg(pushDTO);
        return Optional.ofNullable(msg).map(ApiResult::getData).map(TaskIdDTO::getTaskId).orElse(null);
    }

    //创建定时发送消息体，为批量推前置条件
    private String createMessageSchedule(String title, String content, String intent, LocalDateTime time) {
        PushDTO<Audience> pushDTO = buildPushDTOSchedule(null, title, content, intent, time);
        ApiResult<TaskIdDTO> msg = pushApi.createMsg(pushDTO);
        return Optional.ofNullable(msg).map(ApiResult::getData).map(TaskIdDTO::getTaskId).orElse(null);
    }

    //获取推送结果（不含自定义事件）
    @Override
    public Object getTaskInfo(Set<String> set) {
        ApiResult<Map<String, Map<String, StatisticDTO>>> mapApiResult = statisticApi.queryPushResultByTaskIds(set);
        System.out.println(mapApiResult);
        return mapApiResult;
    }

    //获取推送结果（含自定义事件）
    @Override
    public Object getTaskInfoWithAction(Set<String> set) {
        ApiResult<Map<String, Map<String, StatisticDTO>>> result =
                statisticApi.queryPushResultByTaskIdsAndActionIds(set, Sets.newHashSet());
        System.out.println(result);
        return result;
    }

    //任务组名查报表
    @Override
    public Object getResultByGroupName(String groupName) {
        ApiResult<Map<String, Map<String, StatisticDTO>>> result =
                statisticApi.queryPushResultByGroupName(groupName);
        return result;
    }

    //获取单日推送数据(不支持当天)
    @Override
    public Object getResultByGroupName(LocalDate date) {
        ApiResult<Map<String, Map<String, StatisticDTO>>> result =
                statisticApi.queryPushResultByDate(date.toString());
        return result;
    }

    //获取24个小时在线用户数
    @Override
    public Object getOnlineUser() {
        ApiResult<Map<String, Map<String, Integer>>> result = statisticApi.queryOnlineUserData();
        return result;
    }

    //获取鉴权token
    @Override
    public Object getToken() {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setAppkey(APP_KEY);
        long time = System.currentTimeMillis();
        String sign = sha256(APP_KEY + time + MASTER_SECRET);
        authDTO.setTimestamp(time);
        authDTO.setSign(sign);
//        String sing= SignatureDSA.SHA256()
        ApiResult<TokenDTO> result = authApi.auth(authDTO);
        return result;
    }

    //删除token
    @Override
    public Object deleteToken(String token) {
        ApiResult<Void> result = authApi.close(token);
        return result;
    }

    //绑定别名
    @Override
    public Object bindAlias(Map<String, String> map) {
        CidAliasListDTO cidAliasListDTO = new CidAliasListDTO();
        List<CidAliasListDTO.CidAlias> cidAliasList =
                map.entrySet().stream().map(m -> new CidAliasListDTO.CidAlias(m.getKey(), m.getValue())).collect(Collectors.toList());
        cidAliasListDTO.setDataList(cidAliasList);
        ApiResult<Void> result = userApi.bindAlias(cidAliasListDTO);
        return result;
    }

    //根据cid查询别名
    @Override
    public Object getAliasByCid(Set<String> cids) {
        ApiResult<AliasResDTO> result = userApi.queryAliasByCid(cids);
        return result;
    }

    //根据别名查询cid
    @Override
    public Object getCidByAlias(String alias) {
        ApiResult<QueryCidResDTO> result = userApi.queryCidByAlias(alias);
        return result;
    }

    //批量解绑别名
    @Override
    public Object batchUnbindAlias(Map<String, String> map) {
        CidAliasListDTO cidAliasListDTO = new CidAliasListDTO();
        List<CidAliasListDTO.CidAlias> cidAliasList =
                map.entrySet().stream().map(m -> new CidAliasListDTO.CidAlias(m.getValue(), m.getKey())).collect(Collectors.toList());
        cidAliasListDTO.setDataList(cidAliasList);
        ApiResult<Void> result = userApi.batchUnbindAlias(cidAliasListDTO);
        return result;
    }

    //一个用户绑定一批标签（此接口对单个cid有频控限制，每天只能修改一次，最多设置100个标签；
    // 单个标签长度最大为32字符，标签总长度最大为512个字符，申请修改请点击右侧“技术咨询”了解详情 。）
    @Override
    public Object userBindTags(String cid, Set<String> tags) {
        TagDTO tagDTO = new TagDTO();
        tagDTO.setCustomTag(tags);
        ApiResult<Void> result = userApi.userBindTags(cid, tagDTO);
        return result;
    }

    //一批用户绑定一个标签
    //此接口为增量,此接口有频次控制(每分钟最多100次，每天最多10000次)，
    @Override
    public Object userBindTags(Set<String> cids, String tag) {
        UserDTO userDTO = new UserDTO();
        userDTO.setCid(cids);
        ApiResult<Map<String, String>> result = userApi.usersBindTag(tag, userDTO);
        return result;
    }

    //一批用户解绑一个标签
    //此接口为增量,此接口有频次控制(每分钟最多100次，每天最多10000次)，
    @Override
    public Object userUnBindTags(Set<String> cids, String tag) {
        UserDTO userDTO = new UserDTO();
        userDTO.setCid(cids);
        ApiResult<Map<String, String>> result = userApi.deleteUsersTag(tag, userDTO);
        return result;
    }

    //查询用户标签
    @Override
    public Object getUserTags(String cid) {
        ApiResult<Map<String, List<String>>> result = userApi.queryUserTags(cid);
        return result;
    }

    //添加黑名单用户
    //用户标识，多个以英文逗号隔开，一次最多传200个
    @Override
    public Object addUserBlack(Set<String> cids) {
        ApiResult<Void> result = userApi.addBlackUser(cids);
        return result;
    }

    //移除黑名单用户
    @Override
    public Object removeUserBlack(Set<String> cids) {
        ApiResult<Void> result = userApi.removeBlackUser(cids);
        return result;
    }

    //查询用户状态
    @Override
    public Object getUserStatus(Set<String> cids) {
        ApiResult<Map<String, CidStatusDTO>> result = userApi.queryUserStatus(cids);
        return result;
    }

    //查询设备状态
    //注意：
    //1.该接口返回设备在线时，仅表示存在集成了个推SDK的应用在线
    //2.该接口返回设备不在线时，仅表示不存在集成了个推SDK的应用在线
    //3.该接口需要开通权限，如需开通，请联系右侧技术咨询
    public Object getMachineStatus() {
//        userApi.
        return null;
    }

    //查询用户总量
    //通过指定查询条件来查询满足条件的用户数量
    @Override
    public Object searchUsers() {
        ConditionListDTO conditionListDTO = new ConditionListDTO();
        List<Condition> conditionList = new ArrayList<>();
        Condition condition = new Condition();
        conditionListDTO.setTag(conditionList);
        ApiResult<Map<String, Integer>> result = userApi.queryUser(conditionListDTO);
        return result;
    }

}
