package com.aaron.spring.transaction.demo2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Method;

@Component
@Aspect
public class AopExtTransaction {
	@Autowired
	private TransactionUtils transactionUtils;

	@AfterThrowing("execution(* com.itmayiedu.service.*.*.*(..))")
	public void afterThrowing() throws NoSuchMethodException, SecurityException {
		// isRollback(proceedingJoinPoint);
		System.out.println("程序发生异常");
		// TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		// TransactionStatus currentTransactionStatus =
		// TransactionAspectSupport.currentTransactionStatus();
		// System.out.println("currentTransactionStatus:" +
		// currentTransactionStatus);
		transactionUtils.rollback();
	}

	// // 环绕通知 在方法之前和之后处理事情
	@Around("execution(* com.itmayiedu.service.*.*.*(..))")
	public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		// 调用方法之前执行
		TransactionStatus transactionStatus = begin(proceedingJoinPoint);
		proceedingJoinPoint.proceed();// 代理调用方法 注意点： 如果调用方法抛出异常不会执行后面代码
		// 调用方法之后执行
		commit(transactionStatus);
	}

	public TransactionStatus begin(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {

		// // 判断是否有自定义事务注解
		ExtTransaction declaredAnnotation = getExtTransaction(pjp);
		if (declaredAnnotation == null) {
			return null;
		}
		// 如果有自定义事务注解，开启事务
		System.out.println("开启事务");
		TransactionStatus transactionStatu = transactionUtils.begin();
		return transactionStatu;
	}

	public void commit(TransactionStatus transactionStatu) {
		if (transactionStatu != null) {
			// 提交事务
			System.out.println("提交事务");
			transactionUtils.commit(transactionStatu);
		}
	}

	public ExtTransaction getExtTransaction(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
		// 获取方法名称
		String methodName = pjp.getSignature().getName();
		// 获取目标对象
		Class<?> classTarget = pjp.getTarget().getClass();
		// 获取目标对象类型
		Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
		// 获取目标对象方法
		Method objMethod = classTarget.getMethod(methodName, par);
		// // 判断是否有自定义事务注解
		ExtTransaction declaredAnnotation = objMethod.getDeclaredAnnotation(ExtTransaction.class);
		if (declaredAnnotation == null) {
			System.out.println("您的方法上,没有加入注解!");
			return null;
		}
		return declaredAnnotation;

	}

	// 回滚事务
	public void isRollback(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
		// // 判断是否有自定义事务注解
		ExtTransaction declaredAnnotation = getExtTransaction(pjp);
		if (declaredAnnotation != null) {
			System.out.println("已经开始回滚事务");
			// 获取当前事务 直接回滚
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return;
		}
	}

}
