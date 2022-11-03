package top.yumoyumo.yumobot;


import top.yumoyumo.yumobot.annotation.VirtualThread;
import top.yumoyumo.yumobot.service.RedisService;
import top.yumoyumo.yumobot.util.MinioUtil;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class QqBotApplicationTests {

    @Resource
    private ExecutorService executorService;


    @Test
    public void threadTest1() {
        val start = System.currentTimeMillis();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            IntStream.range(0, 1000000).forEach(i -> {
                executorService.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        System.out.println("1000000个执行1s的平台线程耗时：" + (System.currentTimeMillis() - start) / 1000.0 + "s");
    }

    @Test
    public void virtualThreadTest1() {
        val start = System.currentTimeMillis();
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 5000000).forEach(i -> {
                executorService.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    return i;
                });
            });
        }
        System.out.println("5000000个执行1s的虚拟线程耗时：" + (System.currentTimeMillis() - start) / 1000.0 + "s");
    }


    @Test
    public void virtualThreadTest2() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
        log.info("test方法当前线程: " + Thread.currentThread() + "当前时间：" + (LocalDateTime.now()));
    }


    @VirtualThread
    public void test(int i) {
        executorService.submit(() -> {
            Thread.sleep(Duration.ofSeconds(1));
            log.info(Thread.currentThread() + "1000000个执行1s的平台线程耗时{}s", (System.currentTimeMillis()) / 1000.0);
            return i;
        });
    }

    @Test
    public void ThreadTest() {
        final val start = System.currentTimeMillis();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 1000000).forEach(i -> {
                executor.submit(() -> {
                    Thread.sleep(Duration.ofSeconds(1));
                    log.info("1000000个执行1s的平台线程耗时{}s", (System.currentTimeMillis() - start) / 1000.0);
                    return i;
                });
            });
        }  // executor.close() is called implicitly, and waits
        log.info("1000000个执行1s的平台线程耗时{}s", (System.currentTimeMillis() - start) / 1000.0);
    }


    @Autowired
    private RedisService redisService;

    @Resource
    private MinioUtil minioUtil;


    @Test
    public void MinioVirtualUploadTest() {


        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            URL url = new URL("https://i.pixiv.re/img-original/img/2022/08/24/00/00/17/100715788_p0.png");
            var future1 = executor.submit(() -> fetchURL(url));
            ;

        } catch (Exception e) {
        }

    }

    private InputStream fetchURL(URL url) throws IOException {
        return url.openStream();
    }


    @Test
    public void existBucket() {

        minioUtil.existBucket("test");
    }


//    @Test
//    public void sendAsyncData(){
//        long l = System.currentTimeMillis();
//        for (int i=0;i<10;i++){
//            AsyncHandlerData asyncHandlerData = new AsyncHandlerData();
//            asyncHandlerData.setDataInfo("data info"+i);
//            boolean status = asyncMultiConsumerHandlerService.putTask(asyncHandlerData);
//            if (!status) {
//                throw new RuntimeException("insert fail");
//            }
//        }
//        System.out.println("执行时间: "+(System.currentTimeMillis()-l));
//    }


    @Test
    void importRedis() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("src/main/resources/word.txt"));
        while (scanner.hasNextLine()) {
            String k = scanner.next();
            String v = scanner.nextLine();
            redisService.set("word_" + k, v);
            redisService.expire("word_" + k, 30);
        }


        scanner = new Scanner(new FileInputStream("src/main/resources/replace.txt"));
        while (scanner.hasNextLine()) {
            String k = scanner.next();
            String v = scanner.nextLine();
            redisService.set("replace_" + k, v);
            redisService.expire("replace_" + k, 30);
        }
    }

    @Test
    void keys() {
        Set<String> keys = redisService.keys("*");
        for (String key : keys) {
            System.out.println(key + ": " + redisService.get(key));
        }
    }

    @Test
    void scan() {
        Cursor<String> scan = redisService.scan("word_*");
        while (scan.hasNext()) {
            String x = scan.next();
            x = x.substring(x.lastIndexOf("_") + 1);
            if (x.equals("傻逼")) {
                System.out.println(redisService.get(x));
            }
        }
    }

}
