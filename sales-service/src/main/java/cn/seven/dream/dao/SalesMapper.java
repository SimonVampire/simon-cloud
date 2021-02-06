package cn.seven.dream.dao;

import cn.seven.dream.pojo.Sales;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:03
 */
@Repository
public interface SalesMapper {
    @Select("select * from sales where id=#{id}")
    Sales findById(@Param("id") int id);

    @Update("update sales set update_time =#{sales.updateTime} where id =#{sales.id}")
    int updateSales(@Param("sales") Sales sales);
}
