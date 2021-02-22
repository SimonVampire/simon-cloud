#SpringCloud alibaba 全网最新最火的Spring全家桶   

`此demo由个人学习和分享使用`
## cloud组件列表

    组件                  功能                      解决问题		
    nacos               注册中心\配置中心         替代闭源的Eureka2.X  
    gateway             官方最新网关              替代淘汰的zuul  
    Sleuth+zipkin       链路追踪系统              解决接口诊断困难、应用性能诊断复杂、架构分析复杂等难题  
    sentinel            限流 降级 熔断            替代停更的hystrix  
    seata               分布式事务                分布式事务管理                 
    docker              容器化部署                实现快速部署，可移植性强，环境隔离 
## 各组件详细介绍 
##### nacos
    https://nacos.io/zh-cn/docs/quick-start.html
##### gateway
    https://spring.io/projects/spring-cloud-gateway
##### Sleuth+zipkin
    https://spring.io/projects/spring-cloud-sleuth
    https://zipkin.io/pages/quickstart.html
##### sentinel
    https://github.com/alibaba/Sentinel/wiki/%E6%96%B0%E6%89%8B%E6%8C%87%E5%8D%97
##### seata
    官网 https://seata.io/zh-cn/   
    脚本和配置 https://github.com/seata/seata/tree/develop/script     
##### docker
    https://docs.docker.com/
    https://docs.docker.com/docker-hub/

## 项目结构介绍

##### 本项目为聚合项目,微服务支持分库分表。旨在轻业务，重技术，简单分为:
    用户服务：user-service
    订单服务：order-service
    视频/产品服务：sales-service
    网关服务：gateway
    公共模块：common-service
    
##### 涉及业务数据表 
###### 视频服务数据库 sales表
      -- auto-generated definition
      create table sales
      (
        id          int auto_increment
          primary key,
        name        varchar(524)  null comment '商品名称',
        price       int           null comment '价格,分',
        summary     varchar(1026) null comment '概述',
        create_time datetime      null comment '创建时间',
        update_time datetime      null comment '修改时间'
      ) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;           
    
      INSERT INTO sales_db.sales (id, name, price, summary, create_time, update_time) VALUES (1, 'iphone12 pro', 698000, '真贵', '2021-01-27 13:14:00', '2021-01-27 13:14:00');
      INSERT INTO sales_db.sales (id, name, price, summary, create_time, update_time) VALUES (2, 'iphone12 pro max', 1100000, '更贵 但真香！', '2021-01-27 13:14:00', '2021-01-27 13:14:00');
      INSERT INTO sales_db.sales (id, name, price, summary, create_time, update_time) VALUES (3, '小米', 400000, '发烧机', '2021-01-27 13:14:00', '2021-01-27 13:14:00');
      INSERT INTO sales_db.sales (id, name, price, summary, create_time, update_time) VALUES (4, '华为', 500000, '加油吧！支持国产机！', '2021-01-27 13:14:00', '2021-01-27 13:14:00');
      INSERT INTO sales_db.sales (id, name, price, summary, create_time, update_time) VALUES (5, 'vivo', 450000, '美颜神机', '2021-01-27 13:14:00', '2021-01-27 13:14:00');
         
###### 用户服务数据库 user表
    CREATE TABLE `user` (
      `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
      `phone` varchar(32) DEFAULT NULL,
      `pwd` varchar(128) DEFAULT NULL,
      `sex` int(2) DEFAULT NULL,
      `img` varchar(128) DEFAULT NULL,
      `create_time` datetime DEFAULT NULL,
      `role` int(11) DEFAULT NULL COMMENT '1是普通用户，2是管理员',
      `username` varchar(128) DEFAULT NULL,
      `wechat` varchar(128) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
    

###### 订单服务数据库order表   
    create table `order`
    (
      id           int(11) unsigned auto_increment
        primary key,
      out_trade_no varchar(64)  null comment '订单唯一标识',
      state        int          null comment '0表示未支付，1表示已支付',
      total_fee    int          null comment '支付金额，单位分',
      sales_id     int          null comment '商品主键',
      sales_name   varchar(256) null comment '商品名称',
      user_id      int(12)      null comment '用户id',
      create_time  datetime     null comment '订单生成时间',
      update_time  datetime     null comment '更新时间'
    )
      charset = utf8;
     
#### zipkin持久化使用数据表
    CREATE TABLE IF NOT EXISTS zipkin_spans (
      `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
      `trace_id` BIGINT NOT NULL,
      `id` BIGINT NOT NULL,
      `name` VARCHAR(255) NOT NULL,
      `remote_service_name` VARCHAR(255),
      `parent_id` BIGINT,
      `debug` BIT(1),
      `start_ts` BIGINT COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
      `duration` BIGINT COMMENT 'Span.duration(): micros used for minDuration and maxDuration query',
      PRIMARY KEY (`trace_id_high`, `trace_id`, `id`)
    ) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;
     
    ALTER TABLE zipkin_spans ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTracesByIds';
    ALTER TABLE zipkin_spans ADD INDEX(`name`) COMMENT 'for getTraces and getSpanNames';
    ALTER TABLE zipkin_spans ADD INDEX(`remote_service_name`) COMMENT 'for getTraces and getRemoteServiceNames';
    ALTER TABLE zipkin_spans ADD INDEX(`start_ts`) COMMENT 'for getTraces ordering and range';
     
    CREATE TABLE IF NOT EXISTS zipkin_annotations (
      `trace_id_high` BIGINT NOT NULL DEFAULT 0 COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
      `trace_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
      `span_id` BIGINT NOT NULL COMMENT 'coincides with zipkin_spans.id',
      `a_key` VARCHAR(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
      `a_value` BLOB COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
      `a_type` INT NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
      `a_timestamp` BIGINT COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
      `endpoint_ipv4` INT COMMENT 'Null when Binary/Annotation.endpoint is null',
      `endpoint_ipv6` BINARY(16) COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
      `endpoint_port` SMALLINT COMMENT 'Null when Binary/Annotation.endpoint is null',
      `endpoint_service_name` VARCHAR(255) COMMENT 'Null when Binary/Annotation.endpoint is null'
    ) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;
     
    ALTER TABLE zipkin_annotations ADD UNIQUE KEY(`trace_id_high`, `trace_id`, `span_id`, `a_key`, `a_timestamp`) COMMENT 'Ignore insert on duplicate';
    ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`, `span_id`) COMMENT 'for joining with zipkin_spans';
    ALTER TABLE zipkin_annotations ADD INDEX(`trace_id_high`, `trace_id`) COMMENT 'for getTraces/ByIds';
    ALTER TABLE zipkin_annotations ADD INDEX(`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames';
    ALTER TABLE zipkin_annotations ADD INDEX(`a_type`) COMMENT 'for getTraces and autocomplete values';
    ALTER TABLE zipkin_annotations ADD INDEX(`a_key`) COMMENT 'for getTraces and autocomplete values';
    ALTER TABLE zipkin_annotations ADD INDEX(`trace_id`, `span_id`, `a_key`) COMMENT 'for dependencies job';
     
    CREATE TABLE IF NOT EXISTS zipkin_dependencies (
      `day` DATE NOT NULL,
      `parent` VARCHAR(255) NOT NULL,
      `child` VARCHAR(255) NOT NULL,
      `call_count` BIGINT,
      `error_count` BIGINT,
      PRIMARY KEY (`day`, `parent`, `child`)
    ) ENGINE=InnoDB ROW_FORMAT=COMPRESSED CHARACTER SET=utf8 COLLATE utf8_general_ci;

     

## docker常用命令汇总

    常用命令（安装部署好Docker后，执行的命令是docker开头）,xxx是镜像名称
    
    编译生成镜像：mvn install -Dmaven.test.skip=true dockerfile:build

    搜索镜像：docker search xxx 
    
    列出当前系统存在的镜像：docker images  
    
    拉取镜像：docker pull xxx   xxx是具体某个镜像名称(格式 REPOSITORY:TAG)
    REPOSITORY：表示镜像的仓库源,TAG：镜像的标签
    
    运行一个容器：
    docker run --name nginx-xd -p 8080:80 -d nginx​
    docker run - 运行一个容器
       -d 后台运行
       -p 端口映射
       --name "xxx"  容器名称
    最后的nginx 则是容器ID
    查看容器启动日志: docker logs -f containerid
           
    列举当前运行的容器：docker ps  
    
    检查容器内部信息：docker inspect 容器名称
    
    删除镜像：docker rmi IMAGE_NAME
    强制移除镜像不管是否有容器使用该镜像 增加 -f 参数
    
    停止某个容器：docker stop 容器名称
    
    启动某个容器：docker start 容器名称
    
    移除某个容器： docker rm 容器名称 （容器必须是停止状态）
    
    列举全部 容器 ： docker ps -a   
    
