package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class PaymentController {
    @Resource
    private PaymentService paymentService;
    @PostMapping(value="/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result= paymentService.create(payment);
        log.info("*******插入结果："+result);
        if(result>0){
            return  new CommonResult(200,"插入数据库成功！",result);
        }else{
            return new CommonResult(400,"插入数据库失败",result);
        }
    }
    @GetMapping(value="/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id, HttpServletRequest request){
      Payment payment= paymentService.getPaymentById(id);
        HttpSession httpSession=request.getSession();
        if(httpSession.isNew()){
            log.info("新建session，sessionId="+httpSession.getId());
        }else{
            log.info("已经存在的session，sessionId="+httpSession.getId());
        }
        log.info("*******插入结果："+payment);
        if(payment!=null){
            return  new CommonResult(200,"成功！",payment);
        }else{
            return new CommonResult(400,"没有对应的结果--》"+id,null);
        }
    }

}
