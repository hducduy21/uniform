package nashtech.rookie.uniform.shared.aspects;

import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.exceptions.ApplicationException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    private static final String LOG_INF_TEMPLATE = "[{}][RequestID={}] [UserID={}] [Method={}] - {}";
    private static final String LOG_RETURN_TEMPLATE = "[{}][RequestID={}] [Method={}] - {}";
    private static final String LOG_THROW_TEMPLATE = "[{}][RequestID={}] [Method={}] \n {}";


    @Pointcut("execution(* nashtech.rookie.uniform.controllers.*.*(..))")
    public void controllersMethods(){}

    @Pointcut("execution(* nashtech.rookie.uniform.services.*.*(..))")
    public void servicesMethods(){}

    @Pointcut("execution(* nashtech.rookie.uniform.repositories.*.*(..))")
    public void repositoriesMethods(){}

    @Before("controllersMethods()")
    public void logBefore(JoinPoint joinPoint) {
        logMethodInvocation("CONTROLLER", joinPoint, "User is calling method");
    }

    @AfterReturning(pointcut = "controllersMethods()", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        if(result instanceof ApiResponse) {
            String message = ((ApiResponse) result).getMessage();
            logMethodReturn("CONTROLLER", joinPoint, message);
        }
        else {
            logMethodReturn("CONTROLLER", joinPoint, "Controller is returning");
        }
    }

    @AfterThrowing(pointcut = "controllersMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        logMethodException("CONTROLLER", joinPoint, error);
    }

    @AfterReturning(pointcut = "servicesMethods()", returning = "result")
    public void logAfterService(JoinPoint joinPoint, Object result) {
        logMethodReturn("SERVICE", joinPoint, "Service is returning");
    }

    @AfterThrowing(pointcut = "servicesMethods()", throwing = "error")
    public void logAfterThrowingService(JoinPoint joinPoint, Throwable error) {
        logMethodException("SERVICE", joinPoint, error);
    }

    @AfterThrowing(pointcut = "repositoriesMethods()", throwing = "error")
    public void logAfterThrowingRepository(JoinPoint joinPoint, Throwable error) {
        logMethodException("REPOSITORY", joinPoint, error);
    }

    private void logMethodInvocation(String type, JoinPoint joinPoint, String message) {
        String methodName = joinPoint.getSignature().getName();
        String requestId = MDC.get("requestID");
        String userId = MDC.get("userId");
        log.info(LOG_INF_TEMPLATE, type, requestId, userId, methodName, message);
    }

    private void logMethodReturn(String type, JoinPoint joinPoint, String message) {
        String methodName = joinPoint.getSignature().getName();
        String requestId = MDC.get("requestID");
        log.info(LOG_RETURN_TEMPLATE, type, requestId, methodName, message);
    }

    private void logMethodException(String type, JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        String requestId = MDC.get("requestID");

        if (error instanceof ApplicationException) {
            log.info(LOG_RETURN_TEMPLATE, type, requestId, methodName, error.getMessage());
        } else {
            log.error(LOG_THROW_TEMPLATE, type, requestId, methodName, error.getMessage());
        }
    }
}
