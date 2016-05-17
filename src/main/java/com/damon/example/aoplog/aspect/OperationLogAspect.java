package com.damon.example.aoplog.aspect;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.Stream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.damon.example.aoplog.annotation.SelectOne;
import com.damon.example.aoplog.util.JsonUtil;
import com.damon.example.aoplog.util.LoggerUtil;

@Aspect
@Component
public class OperationLogAspect {

	private static final Logger LOG = LoggerFactory.getLogger(OperationLogAspect.class);
	
	@Pointcut("@annotation(com.damon.example.aoplog.annotation.ModuleOperation)")
	public void actionMethod(){
		// AOP切点方法，无需任何内容
	}
	
	@Before(value = "actionMethod() ")
	public void beforeInvokeActionMethod(JoinPoint joinPoint){
		LoggerUtil.createLoggerAction(joinPoint);
	}
	
	@After(value = "actionMethod() ")
	public void afterInvokeActionMethod(JoinPoint joinPoint){
		LoggerUtil.checkLoggerAction();
	}
	
	@Pointcut(value = "!execution( * com.damon.example.aoplog.service..LogService*.*(..) ) && "
					+ "(execution( * com.damon.example.aoplog.service..*Service.create*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.save*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.insert*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.add*(..) ) )")
    public void createMethod() {
		// AOP切点方法，无需任何内容
    }
	
	@AfterReturning(value = "createMethod() ", returning = "retValue")
	public void createOperation(JoinPoint joinPoint, Object retValue){
//		if(retValue == null){
//			return;
//		}
		//只有一个参数的create方法
		Object[] arguments = joinPoint.getArgs();
		LoggerUtil.createLoggerForAdding(arguments[0], "添加");
	}
	
	@Pointcut(value = "!execution( * com.damon.example.aoplog.service..LoggerService*.*(..) ) && "
					+ "(execution( * com.damon.example.aoplog.service..*Service.update*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.change*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.do*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.modify*(..) ) )")
    public void updateMethod() {
		// AOP切点方法，无需任何内容
    }
	
	/**
	 * 如理更新操作
	 * @exception Throwable 抛出的异常信息，该异常不能在这里被catch，否则会影响ValidationUtil的校验
	 * */
	@Around(value = "updateMethod() ")
	public Object updateOperation(ProceedingJoinPoint joinPoint) throws Throwable{
		Object result = null;
		Object instance = joinPoint.getTarget();
		Object[] arguments = joinPoint.getArgs();
		//只有一个参数的update方法
		if(arguments.length == 1){
			Object before = getObject(instance, arguments[0]);
			result = joinPoint.proceed(arguments);
			Object after = getObject(instance, arguments[0]);
			LoggerUtil.createLoggerForUpdating(before, after, "修改");
		}else{
			//参数数量大于1的,单独处理日志信息
			result = joinPoint.proceed(arguments);
		}
		return result;
	}
	
	@Pointcut(value = "!execution( * com.damon.example.aoplog.service..LoggerService*.*(..) ) && "
					+ "(execution( * com.damon.example.aoplog.service..*Service.remove*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.del*(..) ) || "
					+ "execution( * com.damon.example.aoplog.service..*Service.delete*(..) ) )")
    public void deleteMethod() {
		// AOP切点方法，无需任何内容
    }
	
	/**
	 * 处理删除操作
	 * @exception Throwable 抛出的异常信息，该异常不能在这里被catch，否则会影响ValidationUtil的校验
	 * */
	@Around(value = "deleteMethod()")
	public Object deleteOperation(ProceedingJoinPoint joinPoint) throws Throwable{
		Object result = null;
		Object[] arguments = joinPoint.getArgs();
		if(arguments.length == 1){
			Object before = getObject(joinPoint.getTarget(), arguments[0]);
			result = joinPoint.proceed(arguments);
			if(before != null){
				LoggerUtil.createLoggerForDeleting(before, "删除");
			}
		}else{
			//参数数量大于1的,单独处理日志信息
			result = joinPoint.proceed(arguments);
		}
		return result;
	}
	
	private Object getObject(Object instance, Object argument){
		if(instance == null || argument == null){
			return null;
		}
		Method selectOneMethod = getSelectObjectMethod(instance);
		if(selectOneMethod == null){
			return null;
		}
		
		if(argument.getClass().isArray()){
			Object[] array = (Object[])argument;
			return Stream.of(array)
						.map(arg -> {
							return selectObject(instance, selectOneMethod, getId(arg));
						})
						.toArray();
		}
		Long id = getId(argument);
		return selectObject(instance, selectOneMethod, id);
	}
	
	private Object selectObject(Object instance, Method selectOneMethod, Long id){
		try {
			return selectOneMethod.invoke(instance, id);
		} catch (Exception e) {
			LOG.info("selectObject exception: -> {} ", e);
		} 
		return null;
	}
	
	private Method getSelectObjectMethod(Object instance){
		Optional<Method> selectOneMethod = Stream
			.of(instance.getClass().getMethods())
			.filter(method -> {
				return method.isAnnotationPresent(SelectOne.class);
			})
			.findFirst();
		if(selectOneMethod.isPresent()){
			return selectOneMethod.get();
		}
		return null;
	}
	
	private Long getId(Object argument){
		if(argument instanceof Long){
			return (Long)argument;
		}else{
			return Long.parseLong(String.valueOf(JsonUtil.object2Map(argument).get("id")));
		}
	}

}
