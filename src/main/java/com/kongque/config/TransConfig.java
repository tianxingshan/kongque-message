package com.kongque.config;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
public class TransConfig {

    /*
    切面编程
     */
    private static final String AOP_POINTCUT_Expression = "execution (* com.kongque.service..*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;


    /*
        增强事物的属性配置
     */
    @Bean
    public TransactionInterceptor txAdvice(){
        NameMatchTransactionAttributeSource txAttributeS = new NameMatchTransactionAttributeSource();
        /*propagation="REQUIRED" , timeout=5 ;rollback-for=".. , .."配置*/
        RuleBasedTransactionAttribute requiredAttr = new RuleBasedTransactionAttribute();
        requiredAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredAttr.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        /*propagation="SUPPORTS" , readOnly="true"配置*/
        RuleBasedTransactionAttribute supportsAttr = new RuleBasedTransactionAttribute();
        supportsAttr.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        supportsAttr.setReadOnly(true);
           /*
            注意：方法名称来自类匹配的到方法【save*, “*”表示匹配任意個字符】
             <tx:method .../>
            */
        Map<String , TransactionAttribute> txMethod = new HashMap<String , TransactionAttribute>();
        txMethod.put("find*" , supportsAttr);
        txMethod.put("select*" , supportsAttr);
        txMethod.put("get*" , supportsAttr);
        txMethod.put("*" , requiredAttr);

        txAttributeS.setNameMap(txMethod);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager , txAttributeS);
        return txAdvice;
    }

    /*
    aop切面编程配置
     */
    @Bean
    public Advisor txAdviceAdvisor(){
        AspectJExpressionPointcut pointcut= new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_Expression);
        return new DefaultPointcutAdvisor(pointcut , txAdvice());
    }
}
