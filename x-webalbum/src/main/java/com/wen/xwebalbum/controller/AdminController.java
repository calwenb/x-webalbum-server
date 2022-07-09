package com.wen.xwebalbum.controller;

import com.wen.xwebalbum.annotation.PassToken;
import com.wen.xwebalbum.pojo.User;
import com.wen.xwebalbum.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @GetMapping("/all_user")
    public String queryAllUser() {
        List<User> users = userService.queryUsers();
        return ResponseUtil.success(users);
    }

    @GetMapping("/search_user")
    public String searchUser(@RequestParam("term") String term,
                             @RequestParam("value") String value,
                             @RequestParam("accurate") boolean accurate) {
        List<User> users;
        if (accurate) {
            users = userService.queryUsersTerm(term, value);
        } else {
            users = userService.queryUsersLike(term, value);
        }
        return ResponseUtil.success(users);
    }

    @GetMapping("/user/{id}")
    public String queryUser(@PathVariable String id) {
        User user = userService.getUserById(Integer.parseInt(id));
        return ResponseUtil.success(user);
    }


    @PostMapping("/add_admin")
    public String addAdmin(@RequestParam("userName") String userName,
                           @RequestParam("loginName") String loginName,
                           @RequestParam("password") String passWord,
                           @RequestParam("email") String email) {
        User exeUser = tokenService.getTokenUser();
        if (exeUser.getUserType() != 0) {
            return ResponseUtil.error("你无权操作其他管理员，请联系超级管理员");
        }
        User admin = new User(-1, userName, loginName, passWord, 1, "", email, "", new Date());
        int i = userService.addUser(admin);
        if (i > 0) {
            return ResponseUtil.success("操作成功");
        }
        return ResponseUtil.error("操作失败");
    }

    @DeleteMapping("/user/{id}")
    public String delUser(@PathVariable String id) {
        User exeUser = tokenService.getTokenUser();
        if (exeUser.getUserType() != 0) {
            return ResponseUtil.error("你无权操作其他管理员，请联系超级管理员");
        }
        int i = userService.deleteUser(Integer.parseInt(id));
        if (i > 0) {
            return ResponseUtil.success("操作成功");
        }
        return ResponseUtil.error("操作失败");
    }

    @PutMapping("/user/{id}")
    public String updateUser(@PathVariable int id,
                             @RequestParam(value = "userName", required = false) String userName,
                             @RequestParam(value = "loginName", required = false) String loginName,
                             @RequestParam(value = "password", required = false) String passWord,
                             @RequestParam(value = "userType", required = false) Integer userType,
                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                             @RequestParam(value = "email", required = false) String email) {
        User exeUser = tokenService.getTokenUser();
        if (userType != null && userType == 0) {
            return ResponseUtil.error("禁止操作超级管理员信息");
        } else if (userType != null && userType == 1) {
            if (exeUser.getUserType() != 0) {
                return ResponseUtil.error("你无权操作其他管理员，请联系超级管理员");
            }
        } else {
            userType = 2;
        }
        User user = new User(id, userName, loginName, passWord, userType, phoneNumber, email, null, null);
        if (userService.updateUser(user) > 0) {
            return ResponseUtil.successDo();
        }
        return ResponseUtil.errorDo();
    }


}
