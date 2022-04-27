package cn.zpeace.bootstrap;

import cn.hutool.core.collection.ListUtil;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
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
    public void rxJava() {
        Disposable disposable = Flowable.just("Hello world").subscribe(System.out::println);
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
    
}
