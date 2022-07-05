package com.wen.servcie.impl;

import com.wen.mapper.BaseMapper;
import com.wen.mapper.UserMapper;
import com.wen.mapper.impl.BaseMapperImpl;
import com.wen.pojo.User;
import com.wen.servcie.*;
import com.wen.utils.ConfigUtil;
import com.wen.utils.FileUtil;
import com.wen.wrapper.WhereWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author Mr.文
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    TokenService tokenService;
    @Autowired
    FileStoreService storeService;
    @Resource
    MailService mailService;
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    FileService fileService;
    @Resource
    BaseMapper baseMapper;


    /**
     * 查询全部用户
     *
     * @return 全部用户
     */
    @Override
    public List<User> queryUsers() {
        ArrayList<User> users = baseMapper.selectTargets(User.class);
        return users;
    }

    @Override
    public ArrayList<User> queryUsersTerm(String term, String value) {
        WhereWrapper wrapper = new WhereWrapper();
        wrapper.add(term, value);
        ArrayList<User> users = baseMapper.selectTargets(User.class, wrapper);
        return users;
    }

    @Override
    public List<User> queryUsersLike(String term, String key) {
        WhereWrapper wrapper = new WhereWrapper();
        wrapper.like(term, key);
        return baseMapper.selectTargets(User.class, wrapper);
    }

    /**
     * 增加全部用户
     *
     * @param user
     * @return 修改状态
     */
    @Override
    public int addUser(User user) {
        if (user == null) {
            return 0;
        }
        String superAdminLoginName = ConfigUtil.getSuperAdminLoginName();
        //禁止添加与超级管理员同登录名
        if (superAdminLoginName != null && superAdminLoginName.equals(user.getLoginName())) {
            return 0;
        }
        return baseMapper.insertTarget(user);
    }

    @Override
    public int initAdmin(String superAdminName, String superAdminLoginName, String superAdminPassword) {
        User superAdmin = new User(-10, superAdminName, superAdminLoginName, superAdminPassword, 0, null, null, null, new Date());
        return baseMapper.replaceTarget(superAdmin);
    }

    @Override
    public int verifyAdmin(int userId) {
        WhereWrapper wrapper = new WhereWrapper();
        wrapper.add("id", userId);
        User user = baseMapper.selectTarget(User.class);
        if (user != null) {
            return user.getUserType();
        }
        return 2;
    }

    /**
     * 删除用户，超级管理员禁止删除
     *
     * @param userId
     * @return
     */
    @Override
    public int deleteUser(int userId) {
        WhereWrapper wrapper = new WhereWrapper();
        wrapper.add("id", userId);
        User user = baseMapper.selectTarget(User.class, wrapper);
        if (user != null && user.getUserType() == 0) {
            return 0;
        }
        return baseMapper.deleteTarget(User.class, wrapper);
    }

    /**
     * 修改全部用户
     *
     * @param user
     * @return 修改状态
     */
    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    /**
     * 登录
     *
     * @param loginName
     * @param pwd
     * @return
     */
    @Override
    public User login(String loginName, String pwd) {
        return userMapper.login(loginName, pwd);
    }

    @Override
    public Map<String, Object> register(User user) {
        HashMap<String, Object> rs = new HashMap<>(2);
        try {
            userMapper.addUser(user);
        } catch (Exception e) {
            rs.put("error", "注册失败，账号已存在");
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return rs;
        }
        if (!storeService.initStore(user.getId())) {
            //回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            rs.put("error", "初始化用户仓库失败");
        }
        rs.put("user", user);
        return rs;
    }


    @Override
    public User getUserById(int userID) {
        return userMapper.getUserById(userID);
    }

    @Override
    public boolean sendCode(String loginName, String email) {
        User user = userMapper.getUserByLName(loginName);
        if (user == null) {
            return false;
        }
        if (!email.equals(user.getEmail())) {
            return false;
        }
        try {
            String code = this.createCode();
            String subject, content;
            subject = "重置密码";
            content = "账号: " + loginName + "，您好。\n" + "您当前正在重置密码，您的验证码为：" + code;
            mailService.sendSimpleMail(email, subject, content);
            String key = "code:lname:" + loginName;
            redisTemplate.opsForValue().set(key, code, 3, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean verifyCode(String loginName, String code) {
        String key = "code:lname:" + loginName;
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return false;
        }
        String realCode = String.valueOf(value);
        return realCode.equals(code);
    }

    @Override
    public boolean repwd(String loginName, String password) {
        User user = userMapper.getUserByLName(loginName);
        if (user == null) {
            return false;
        }
        user.setPassWord(password);
        userMapper.updatepwd(user);
        return true;
    }

    @Override
    public boolean uploadHead(MultipartFile file, String userId) {
        String headRoot = FileUtil.getRootPath() + "head/";
        String path = headRoot + file.getOriginalFilename();
        System.out.println(path);
        if (fileService.uploadFileComm(file, path)) {
            User user = userMapper.getUserById(Integer.parseInt(userId));
            user.setAvatar(path);
            return userMapper.updateUser(user) > 0;
        }
        return false;
    }


    private String createCode() {
        StringBuilder code = new StringBuilder();
        //文件生成码
        for (int i = 0; i < 5; i++) {
            code.append(new Random().nextInt(9));
        }
        return String.valueOf(code);
    }
}
