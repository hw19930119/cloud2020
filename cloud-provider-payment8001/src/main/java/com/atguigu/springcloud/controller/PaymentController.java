package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @Resource
    private DiscoveryClient discoveryClient;
    @Value("${server.port}")
    private String serverPort;
    @PostMapping(value="/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result= paymentService.create(payment);
        log.info("*******插入结果："+result);
        if(result>0){
            return  new CommonResult(200,"插入数据库成功！端口："+serverPort,result);
        }else{
            return new CommonResult(400,"插入数据库失败!端口："+serverPort,result);
        }
    }
    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
      Payment payment= paymentService.getPaymentById(id);
        if(payment!=null){
            CommonResult commonResult= new CommonResult(200,"成功！端口："+serverPort,payment);
            return  commonResult;
        }else{
            return new CommonResult(400,"端口："+serverPort+" 没有对应的结果--》"+id,null);
        }
    }
    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for(String element:services){
            log.info("****element: "+element);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for(ServiceInstance serviceInstance:instances){
            log.info(serviceInstance.getServiceId()+"\t" + serviceInstance.getHost()+"\t"+serviceInstance.getPort()+"\t"+serviceInstance.getUri());
        }
        return this.discoveryClient;
    }
}
