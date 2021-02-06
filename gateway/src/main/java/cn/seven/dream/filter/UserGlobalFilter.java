package cn.seven.dream.filter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @description 自定义公共过滤器
 * @AUTHOR zhangxue9
 * @DATE 2021/2/3 9:50
 */
//@Component
public class UserGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = exchange.getRequest().getHeaders().getFirst("token");

        System.out.println("token="+token);
        //todo 这里简单判断  ，常用JWT
        if(StringUtils.isBlank(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        //继续往下执行
        return chain.filter(exchange);

    }

    //数字越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}