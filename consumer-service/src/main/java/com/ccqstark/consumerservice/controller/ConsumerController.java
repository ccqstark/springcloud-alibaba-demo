package com.ccqstark.consumerservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class ConsumerController {
    private Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    //注入 Ribbon 负载均衡器对象
    //在引入 starter-netflix-ribbon 后在 SpringBoot 启动时会自动实例化 LoadBalancerClient 对象。
    //在 Controller 使用 @Resource 注解进行注入即可。
    @Resource
    private LoadBalancerClient loadBalancerClient;
    @Resource
    //将应用启动时创建的 RestTemplate 对象注入 ConsumerController
    private RestTemplate restTemplate;
    @GetMapping("/consumer/msg1")
    public String getProviderMessage1() {
        //loadBalancerClient.choose()方法会从 Nacos 获取 provider-service 所有可用实例，
        //并按负载均衡策略从中选择一个可用实例，封装为 ServiceInstance（服务实例）对象
        //结合现有环境既是从三个实例中选择一个包装为ServiceInstance
        ServiceInstance serviceInstance = loadBalancerClient.choose("provider-service");
        //获取服务实例的 IP 地址
        String host = serviceInstance.getHost();
        //获取服务实例的端口
        int port = serviceInstance.getPort();
        //在日志中打印服务实例信息
        logger.info("本次调用由provider-service的" + host + ":" + port + " 实例节点负责处理" );
        //通过 RestTemplate 对象的 getForObject() 方法向指定 URL 发送请求，并接收响应。
        //getForObject()方法有两个参数：
        //1. 具体发送的 URL，结合当前环境发送地址为：http://[ip]:[port]/provider/msg
        //2. String.class说明 URL 返回的是纯字符串，如果第二参数是实体类， RestTemplate 会自动进行反序列化，为实体属性赋值
        String result = restTemplate.getForObject("http://" + host + ":" + port + "/provider/msg", String.class);
        //输出响应内容
        logger.info("provider-service 响应数据:" + result);
        //向浏览器返回响应
        return "consumer-service 响应数据:" + result;
    }

    @GetMapping("/consumer/msg2")
    public String getProviderMessage2() {
        //关键点：将原有IP:端口替换为服务名，RestTemplate便会在通信前自动利用Ribbon查询可用provider-service实例列表
        //再根据负载均衡策略选择节点实例
        String result = restTemplate.getForObject("http://provider-service/provider/msg", String.class);
        logger.info("consumer-service获得数据:" + result);
        return   "consumer-service获得数据:" + result;
    }


}