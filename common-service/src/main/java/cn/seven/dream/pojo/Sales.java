package cn.seven.dream.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:05
 */
@Data
public class Sales {
    private Integer id;
    private String name;
    private String summary;
    private Integer  price;
    private Date createTime;
    private Date updateTime;

    //测试使用
    private String serverPort;

}
