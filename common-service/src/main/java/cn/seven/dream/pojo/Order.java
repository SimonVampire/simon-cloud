package cn.seven.dream.pojo;

import lombok.Data;
import lombok.Generated;

import javax.persistence.*;
import java.util.Date;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:05
 */
@Data
@Table(name = "order")
public class Order {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;
    private String outTradeNo;
    private Integer state;
    private Date createTime;
    private Date updateTime;
    private  Integer totalFee;
    private Integer salesId;
    private Integer userId;
    //测试使用
    @Transient
    private String serverPort;
}
