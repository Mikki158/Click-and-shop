package org.exception.customException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Aspect
@Component
public class ErrorDefinitionAspect {

    @AfterThrowing(value = "@annotation(errorDefinition)", throwing = "ex")
    public void handleMethodException(
            JoinPoint joinPoint,
            ErrorDefinition errorDefinition,
            Throwable ex
    ) throws Throwable {

        System.out.println("Massiv DO = " + Arrays.toString(ex.getSuppressed()));

        ErrorDefinitionException errorDefinitionException = findErrorDefinitionException(ex);
        if(isNull(errorDefinitionException)){
            errorDefinitionException = buildException(joinPoint, errorDefinition);
            ex.addSuppressed(errorDefinitionException);
        }
        System.out.println("Massiv PO = " + Arrays.toString(ex.getSuppressed()));
        throw ex;
    }

    private ErrorDefinitionException findErrorDefinitionException(Throwable ex) {
        if(ex instanceof ErrorDefinitionException) {
            return (ErrorDefinitionException) ex;
        }



        for(Throwable suppressed: ex.getSuppressed()) {
            ErrorDefinitionException found = findErrorDefinitionException(suppressed);
            if(found != null)
                return found;
        }

        if(ex.getCause() != null && ex.getCause() != ex)
            return findErrorDefinitionException(ex.getCause());

        return null;
    }

    private ErrorDefinitionException buildException(JoinPoint joinPoint, ErrorDefinition errorDefinition) {
        String errorCode = errorDefinition.value();

        ErrorType errorType = errorDefinition.type() == ErrorType.DEFAULT
                ? detectErrorType(joinPoint)
                : errorDefinition.type();

        Map<String, String> details = Arrays.stream(errorDefinition.details()).collect(Collectors.toMap(Detail::key, Detail::value));

        details.putIfAbsent("className", joinPoint.getSignature().getDeclaringTypeName());
        details.putIfAbsent("methodName", joinPoint.getSignature().getName());

        return new ErrorDefinitionException("Error occured in " +
                errorCode,
                errorType,
                details);
    }

    private ErrorType detectErrorType(JoinPoint joinPoint) {
        Class<?> clazz = joinPoint.getSignature().getDeclaringType();

        if(clazz.isAnnotationPresent(Service.class))
            return ErrorType.SERVICE;
        else if(clazz.isAnnotationPresent(Repository.class))
            return ErrorType.REPOSITORY;
        else if(clazz.isAnnotationPresent(Component.class))
            return ErrorType.COMPONENT;
        else if(clazz.isAnnotationPresent(Controller.class))
            return ErrorType.VIEW;

        return ErrorType.DEFAULT;
    }
}
