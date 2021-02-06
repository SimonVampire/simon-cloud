package cn.seven.dream.controller;

import cn.seven.dream.feign.SalesFeign;
import cn.seven.dream.pojo.Sales;
import cn.seven.dream.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:01
 */
@RestController
@RequestMapping("/api/order")
@RefreshScope /**用于实时刷新配置*/
public class OrderController {
 /*   @Autowired
    private DiscoveryClient discoveryClient;*/

/*    @Autowired
    private RestTemplate restTemplate;*/

    @Autowired
    private SalesFeign salesFeign;

    @Value("${order.test-auto-config}")
    private String testAutoConfig;

    @GetMapping("test_auto_config")
    public String testAutoConfig(){
        return testAutoConfig;
    }
    /**
     * 测试 远程调用查询video服务接口  GET请求
     *
     * @param salesId
     * @return java.lang.Object
     * @author zhangxue9
     * @date 2021/1/28 17:21
     */
    @GetMapping("find_by_sales_id")
    public Object findBySalesId(int salesId) {
        Order order = new Order();
        order.setSalesId(salesId);

       /* List<ServiceInstance> list = discoveryClient.getInstances("sales-service");

        ServiceInstance serviceInstance = list.get(0);
        Sales sales = restTemplate.getForObject("http://sales-service/api/sales/find_by_sales_id?id=" + salesId, Sales.class);*/

        Sales sales = salesFeign.findById(salesId);
        order.setSalesTitle(sales.getName());
        order.setSalesId(sales.getId());
        order.setServerPort(sales.getServerPort());
        return order;
    }

    @PostMapping("update_sales")
    public Object updateSales(@RequestBody Sales sales) {
        if (null == sales.getId()) {
            return null;
        }
        sales.setUpdateTime(new Date());
        salesFeign.updateSales(sales);
        return salesFeign.findById(sales.getId());
    }

}
