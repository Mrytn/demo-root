package com.example.apppushmd.messagepush;

import com.example.apppushmd.strategy.GetuiStrategy;
import com.example.apppushmd.strategy.PushStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/***
 * @author PeiDong Yan
 * created at 2021/7/28 15:23
 *
 */
@Configuration
public class MessagePushConfig {

    @Bean
    @Lazy
    public AppPush appPush() {
        Map<Integer, PushStrategy> map = Maps.newHashMap();
        List<AppDto> appList = Lists.newArrayList();
        appList.add(new AppDto().setId(1)
                .setAppId("brNLY8MRLK7C22ZRArHAC")
                .setAppKey("LuE72Dt4Iv5ovqQLGsHsUA")
                .setMasterSecret("LsHeccgFn58koT0h30MGzA"));
        appList.stream().forEach(m -> {
            GetuiStrategy getuiStrategy = GetuiStrategy.build(m.getAppId(), m.getAppKey(), m.getMasterSecret());
            if (null != getuiStrategy) {
                map.put(m.getId(), getuiStrategy);
            }
        });
        AppPush appPush = new AppPush(map);
        //添加appconfig缓存
        appPush.configAddAll(appList.stream().collect(Collectors.toMap(AppDto::getId, Function.identity())));
        return appPush;
    }

}
