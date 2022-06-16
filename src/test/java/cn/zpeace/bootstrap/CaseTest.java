package cn.zpeace.bootstrap;

import cn.hutool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println(System.currentTimeMillis());
        System.out.println(Instant.now().toEpochMilli());
    }

    public void t9() {
    }
}
