package cn.seven.dream.controller;

import cn.seven.dream.feign.SalesFeign;
import cn.seven.dream.pojo.Sales;
import cn.seven.dream.pojo.Order;
import cn.seven.dream.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.EmptyStackException;
import java.util.UUID;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 16:01
 */
@RestController
@RequestMapping("/api/order")
@RefreshScope /**用于实时刷新配置*/
public class OrderController {

    @Autowired
    private SalesFeign salesFeign;

    @Value("${order.test-auto-config}")
    private String testAutoConfig;

    @Autowired
    private OrderService orderService;

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
    public Order findBySalesId(int salesId) {
        Order order = new Order();
        order.setSalesId(salesId);
        Sales sales = salesFeign.findById(salesId);
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

    /**
     * 有分布式事务控制，无异常
     * @param sales
     * @return java.lang.Object
     * @author zhangxue9
     * @date 2021/2/22 16:16
     */
    @GlobalTransactional
    @PostMapping("create_order")
    public Object createOrder(@RequestBody Sales sales){
        return "新增数量："+orderService.saveOrderBySales(sales);
    }

    /**
     * 有分布式事务控制，发生异常
     * @param sales
     * @return java.lang.Object
     * @author zhangxue9
     * @date 2021/2/22 16:16
     */
    @PostMapping("create_tx_error_order")
    @GlobalTransactional
    public Object createTxErrorOrder(@RequestBody Sales sales){
        orderService.saveOrderBySales(sales);
        throw new EmptyStackException();
    }

    /**
     * 无分布式事务控制，发生异常
     * @param sales
     * @return java.lang.Object
     * @author zhangxue9
     * @date 2021/2/22 16:16
     */
    @PostMapping("create_error_order")
    public Object createErrorOrder(@RequestBody Sales sales){
        orderService.saveOrderBySales(sales);
        throw new EmptyStackException();
    }
}
