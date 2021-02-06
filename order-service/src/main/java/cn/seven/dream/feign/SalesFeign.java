package cn.seven.dream.feign;

import cn.seven.dream.pojo.Sales;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/28 16:22
 */
//@FeignClient(name = "sales-service",path = "/api/sales" ,fallback = SalesFeign.SalesFeignFallBack.class)
@FeignClient(name = "sales-service",path = "/api/sales" )
public interface SalesFeign {
    @GetMapping(value = "find_by_id")
    Sales findById(@RequestParam("salesId") int salesId);

    @PostMapping("update_sales")
    Sales updateSales(Sales sales);

    /**
     * 这里使用内部类 降低暴露范围 但注意需要增加 @Service  ，否则找不到BEAN
     * @author zhangxue9
     * @date 2021/2/2 10:12
     */
    @Service
    class SalesFeignFallBack implements SalesFeign {

        @Override
        public Sales findById(int salesId) {
//            return null;
//            todo 测试使用
            Sales sales = new Sales();
            sales.setName("熔断降级数据测试");
            return sales;
        }

        @Override
        public Sales updateSales(Sales sales) {
            return null;
        }
    }
}
