package cn.seven.dream.service;

import cn.seven.dream.pojo.Order;
import cn.seven.dream.pojo.Sales;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 17:30
 */
public interface OrderService {
    int saveOrderBySales(Sales sales);
}
