package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class OrderController {
    private static final String payment_url="http://localhost:8001/cloud-payment";
    @Resource
    private  RestTemplate restTemplate;
    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        CommonResult commonResult = restTemplate.postForObject(payment_url + "/payment/create", payment, CommonResult.class);
        return commonResult;
    }
    @GetMapping("/consumer/payment/getPayment/{id}")
    public CommonResult getPayment(@PathVariable Long id){
        CommonResult commonResult=restTemplate.getForObject(payment_url+"/payment/get/"+id,CommonResult.class);
        return commonResult;
    }
}
