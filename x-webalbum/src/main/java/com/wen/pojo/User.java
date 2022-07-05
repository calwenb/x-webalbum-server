package com.wen.pojo;

import com.wen.annotation.FieldName;
import com.wen.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * User实体类
 *
 * @author Mr.文
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
