package cn.seven.dream.service;

import cn.seven.dream.pojo.Sales;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:03
 */
public interface SalesService {
    Sales findById(int id);

    int updateSales(Sales salesReq);
}
