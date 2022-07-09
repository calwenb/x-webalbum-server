package com.wen;

import com.wen.baseorm.mapper.impl.BaseMapperImpl;
import com.wen.pojo.User;
import com.wen.baseorm.wrapper.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;

@SpringBootTest
class XWebalbumApplicationTests {
    @Resource
    BaseMapperImpl baseMapper;

    @Test
    void contextLoads() {

    }

    @Test
    void t1() {
        String sql = "select * from x_webalbum.user " + "where concat(user_name) like ? ";
        ArrayList<User> users = baseMapper.selectSQL(sql, new Object[]{"%n%"}, User.class);
        System.out.println(users);
    }

    @Test
    void t2() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.likeRight("user_name", "n");
        ArrayList<User> users1 = baseMapper.selectTargets(User.class, wrapper);
        System.out.println(users1);
    }

    //order
    @Test
    void t3() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderDesc("user_type");
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        System.out.println();
        users.forEach(System.out::println);
    }

    //分页
    @Test
    void t4() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderDesc("user_type").limit(1, 2);
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        System.out.println();
        users.forEach(System.out::println);
    }

    @Test
    void t5() {
        QueryWrapper wrapper = new QueryWrapper();
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void t6() {
/*        for (int i = 0; i < 30; i++) {
            User user = new User(-1, "wen " + i, "wen " + i, "123", 2, "666", "@qq", "/#", new Date());
            baseMapper.insertTarget(user);
        }*/
    }

    @Test
    void t9() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name", "admin").or().like("id", 2);

        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void t10() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.in("user_name", "admin", "wen 1").in("user_type", 2, 3);
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void t11() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.less("user_type", 1);
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void t12() {
        QueryWrapper wrapper = new QueryWrapper();
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void t13() {
        QueryWrapper wrapper = new QueryWrapper();
        User user = baseMapper.selectTarget(User.class, wrapper);
        System.out.println(user);
    }

    @Test
    void t14() {
        QueryWrapper wrapper = new QueryWrapper()
                .notEq("user_name", "admin")
                .or()
                .notEq("user_type", 2);
        Integer integer = baseMapper.selectCount(User.class, wrapper);
        System.out.println(integer);
    }

    @Test
    void t15() {
        Integer integer = baseMapper.selectCount(User.class);
        System.out.println(integer);
    }


}
