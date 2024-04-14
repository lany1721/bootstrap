package cn.zpeace.bootstrap.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.BaseStatusListener;
import org.redisson.api.listener.MessageListener;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author zpeace
 * @date 2024/1/3
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheInvalidListenner implements ApplicationRunner {

    private final RedissonClient redissonClient;

    @Override
    public void run(ApplicationArguments args) {
        try {
            RTopic topic = redissonClient.getTopic("douc_rabc_local_cache_invalid_topic");
            topic.addListener(String.class, (channel, message) -> {
                log.info("channel: {}, message: {},", channel, message);
            });
            topic.addListener(String.class, (channel, message) -> {
                log.info("channel: {}, message: {},", channel, message);
            });
        } catch (Exception e) {
            log.error("error: {}", e.getMessage(), e);
        }
    }
}
