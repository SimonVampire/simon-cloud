package cn.seven.dream.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:05
 */
@Data
public class Order {
    private Integer id;
    private String outTradeNo;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private  Integer totalFee;
    private Integer salesId;
    private String salesTitle;
    private String salesImg;
    private Integer userId;
    //测试使用
    private String serverPort;
}
