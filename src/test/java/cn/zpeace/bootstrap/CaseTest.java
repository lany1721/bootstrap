package cn.zpeace.bootstrap;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlInjectionUtils;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created on 2021-12-28.
 *
 * @author skiya
 */
public class CaseTest {

    @Test
    public void testLocalDateTime() {
        LocalDateTime utc = LocalDateTime.now(ZoneId.of("UTC"));
        System.out.println(utc);
        LocalDateTime cn = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(cn);
    }

    @Test
    public void testPeek() {
        List<String> list = new ArrayList<>();
        List<String> strings = ListUtil.of("1", "5", "3", "2", "4")
                .stream()
                .peek(list::add)
                .collect(Collectors.toList());
        System.out.println("strings: " + strings);
        System.out.println("list: " + list);
    }

    @Test
    public void d0() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        //1、使用runAsync或supplyAsync发起异步调用
        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(() -> "result1", executor);
        //2、CompletableFuture.completedFuture()直接创建一个已完成状态的CompletableFuture
        CompletableFuture<String> cf2 = CompletableFuture.completedFuture("result2");
        //3、先初始化一个未完成的CompletableFuture，然后通过complete()、completeExceptionally()，完成该CompletableFuture
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("success");
        System.out.println(cf1.get());
    }

    @Test
    public void t8() {
        // System.out.println(System.currentTimeMillis());
        // System.out.println(Instant.now().toEpochMilli());
        int anInt = Integer.parseInt("1111", 2);
        System.out.println(anInt);
        System.out.println(Thread.currentThread().getId());
        HashSet<Integer> set = new HashSet<>();
        set.add(1);
        set.add(3);
        set.add(2);
        Integer[] array = set.toArray(new Integer[0]);
        Arrays.sort(array);;
        for (Integer integer : array) {
            System.out.println(integer);
        }
    }
    @Test
    public void t9() {
        Set<String> set = Sets.newConcurrentHashSet();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                set.add("s" + i);
            }
            System.out.println("t1a:" + set);
        }).start();
        new Thread(() -> {
            ThreadUtil.sleep(9000);
            System.out.println("t2b:" + set);
            set.clear();
            System.out.println("t2a:" + set);
        }).start();

        new Thread(() -> {
            ThreadUtil.sleep(6000);
            System.out.println("t3b:" + set);
            set.remove("s31");
            System.out.println("t3a:" + set);
        }).start();


        while (true) {
            ThreadUtil.sleep(3000);
            System.out.println(set);
        }
    }

    public void sqlCheck() {
        SqlInjectionUtils.check("");
    }
}
