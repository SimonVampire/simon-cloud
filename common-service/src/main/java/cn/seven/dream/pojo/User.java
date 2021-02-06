package cn.seven.dream.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 15:54
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String pwd;
    private String headImg;
    private String phone;
    private Date createTime;
    private String wechat;
}
