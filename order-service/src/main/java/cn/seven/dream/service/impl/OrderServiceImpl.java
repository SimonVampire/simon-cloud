package cn.seven.dream.service.impl;

import cn.seven.dream.dao.OrderMapper;
import cn.seven.dream.feign.SalesFeign;
import cn.seven.dream.pojo.Order;
import cn.seven.dream.pojo.Sales;
import cn.seven.dream.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.EmptyStackException;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 17:36
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SalesFeign salesFeign;
    @Override
    @GlobalTransactional
    public int saveOrderBySales(Sales sales) {
        Order order = new Order();
        if (null == sales.getId()) {
            return 0;
        }
        sales.setUpdateTime(new Date());
        //模拟商品写操作
        salesFeign.updateSales(sales);
        Date now = new Date();
        //模拟订单生成操作
        order.setSalesId(sales.getId());
        order.setUserId(111);
        order.setState(0);
        order.setCreateTime(now);
        order.setUpdateTime(now);
        return orderMapper.insertOrder(order);
    }
}
