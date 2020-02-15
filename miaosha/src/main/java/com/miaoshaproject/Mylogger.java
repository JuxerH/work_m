package com.miaoshaproject;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class Mylogger {

    @Pointcut("execution(* com.miaoshaproject.controller..*.*(..))")
    public void logPointCut()
    {
    }
    //最终增强 在连接点执行之后执行的通知（返回通知和异常通知的异常）
    @After("logPointCut()")
    public void after(){
        log.info("业务方法调用完成");
    }

    /**
     * 后置增强 在连接点执行之后执行的通知（返回通知）
     */
    @AfterReturning(value="logPointCut()",returning="result")
    public void doAfterReturning(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();
        log.info("调用"+joinPoint.getTarget()+"的"+methodName+"方法。参数："+Arrays.toString(joinPoint.getArgs())
                +"。方法返回值："+result);
    }

    /**
     * 异常增强 在连接点执行之后执行的通知（异常通知）
     */
    @AfterThrowing("logPointCut()")
    public void doAfterThrowing(){
        log.info("异常处理完成");
    }

    /**
     * 环绕增强
     */
//    @Around("logPointCut()")
//    public void doAround(ProceedingJoinPoint jp){
//        log.info("调用"+jp.getTarget()+"的"+jp.getSignature().getName()+"方法。参数："+ Arrays.toString(jp.getArgs()));
//        try {
//            Object result=jp.proceed();//执行目标方法
//            log.info("方法返回值："+result);
//        }catch (Throwable e){
//            log.error(jp.getSignature().getName()+"方法发生异常");
//            e.printStackTrace();
//        }
//    }

}



