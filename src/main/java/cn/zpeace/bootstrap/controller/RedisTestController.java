package cn.zpeace.bootstrap.controller;

import cn.zpeace.bootstrap.support.ApiResponse;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RBatch;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RQueue;
import org.redisson.api.RSetAsync;
import org.redisson.api.RTopic;
import org.redisson.api.RTopicAsync;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author zpeace
 * @date 2024/1/3
 */
@RestController
@RequiredArgsConstructor
public class RedisTestController {

    private final RedissonClient redissonClient;


    @GetMapping("/redis/bloom")
    public ApiResponse<Boolean> bloomFilter() {
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter("emp_existed");
        bloomFilter.tryInit(2000000, 0.01);
        bloomFilter.add("u181");
        bloomFilter.add("u182");
        boolean u181 = bloomFilter.contains("u181");
        return ApiResponse.ok(u181);
    }

    @GetMapping("/redis/add")
    public ApiResponse<Void> cacheAdd() {
        RBatch batch = redissonClient.createBatch();
        RTopicAsync topic = batch.getTopic("douc_rabc_local_cache_invalid_topic");
        topic.publishAsync("add:roleCache:23");
        RSetAsync<Object> set = batch.getSet("douc:role:originalIds");
        set.addAsync("23");
        batch.execute();
        return ApiResponse.ok();
    }

    @GetMapping("/redis/delayed/offer")
    public ApiResponse<Void> delayedQueue(String data, Long timeout) {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("night_shield_queue");
        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        delayedQueue.offer(data, timeout, TimeUnit.SECONDS);
        return ApiResponse.ok();
    }

    @GetMapping("/redis/lock")
    public ApiResponse<Void> lock() {
        return ApiResponse.ok();
    }

}
