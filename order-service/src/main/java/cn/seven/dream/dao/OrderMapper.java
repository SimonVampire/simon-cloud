package cn.seven.dream.dao;

import cn.seven.dream.pojo.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/2/22 15:53
 */
@Mapper
public interface OrderMapper{
    @Insert("insert into `order` (sales_id) value (#{order.salesId});")
    int insertOrder(@Param("order") Order order);
}
