package cn.seven.dream.service.impl;

import cn.seven.dream.dao.SalesMapper;
import cn.seven.dream.pojo.Sales;
import cn.seven.dream.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:03
 */
@Service
public class SalesServiceImpl implements SalesService {
    @Autowired
    private SalesMapper videoMapper;
    @Override
    public Sales findById(int id) {
        return videoMapper.findById(id);
    }

    @Override
    public int updateSales(Sales sales) {
        if(null!= sales.getId()){
            return videoMapper.updateSales(sales);
        }
        return 0;
    }
}
