package com.wen.xwebalbum.pojo;

import com.wen.baseorm.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * User实体类
 *
 * @author Mr.文
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    private Integer id;
    private String userName;
    private String loginName;
    private String passWord;
    private Integer userType;
    private String phoneNumber;
    private String email;
    private String avatar;
    private Date registerTime;

}
