package com.wen;

import com.wen.servcie.FileService;
import com.wen.utils.ConfigUtil;
import com.wen.utils.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class WebalbumApplicationTests {

    @Test
    void contextLoads() {

    }

    @Resource
    FileService fileService;

    @Test
    void thumbnailPower() throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            long m1 = System.currentTimeMillis();

            List<Map<String, String>> list = fileService.queryFilesByUid(1, i + 1, true);
            String data = list.get(0).get("data");
            System.out.println(data);

            System.out.println("处理==>" + (i + 1) + "耗时：" + (System.currentTimeMillis() - m1));

        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    void thumbnailPower2() throws IOException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 3; i++) {
            long m1 = System.currentTimeMillis();

            List<Map<String, String>> list = fileService.queryFilesByUid(1, i + 1, true);
            String data = list.get(0).get("data");
            System.out.println(data);

            System.out.println("处理==>" + (i + 1) + "耗时：" + (System.currentTimeMillis() - m1));

        }
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
    }

    @Test
    public void myPropertiesTest() {
        System.out.println(ConfigUtil.getSuperAdminName());
    }

}
