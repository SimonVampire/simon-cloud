package cn.seven.dream.controller;

import cn.seven.dream.pojo.Sales;
import cn.seven.dream.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:01
 */
@RestController
@RequestMapping("/api/sales")
public class SalesController {
    @Autowired
    private SalesService videoService;

    @GetMapping("find_by_id")
    public Sales findById(int salesId, HttpServletRequest request){
        Sales sales = videoService.findById(salesId);
        //增加测试用数据
        if(null!=sales){
            sales.setServerPort(request.getServerPort()+"");
        }
        return sales;
    }

    @PostMapping("update_sales")
    public Sales updateSales(@RequestBody Sales sales){
        videoService.updateSales(sales);
        return videoService.findById(sales.getId());
    }
}
