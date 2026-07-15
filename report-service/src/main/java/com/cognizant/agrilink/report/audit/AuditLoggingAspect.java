package com.cognizant.agrilink.report.audit;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditLoggingAspect {

    private final AuditLogRepository auditLogRepository;
    private static final String MODULE = "REPORT";

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerMethods() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) || " +
              "@annotation(org.springframework.web.bind.annotation.PutMapping) || " +
              "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void writeOperations() {}

    @AfterReturning(pointcut = "controllerMethods() && writeOperations()", returning = "result")
    public void auditLog(JoinPoint joinPoint, Object result) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes == null) return;
            HttpServletRequest request = attributes.getRequest();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Integer userId = null;
            if (auth != null && auth.getPrincipal() instanceof Integer) {
                userId = (Integer) auth.getPrincipal();
            }

            String httpMethod = request.getMethod();
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String entityName = className.replace("Controller", "");
            
            String action = getActionName(httpMethod, entityName);
            String ipAddress = request.getRemoteAddr();

            AuditLog auditLog = AuditLog.builder()
                    .userId(userId)
                    .action(action)
                    .module(MODULE)
                    .ipAddress(ipAddress)
                    .timestamp(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            log.warn("Failed to write audit log in AOP aspect: {}", e.getMessage());
        }
    }

    private String getActionName(String httpMethod, String entityName) {
        switch (httpMethod.toUpperCase()) {
            case "POST": return "CREATE_" + entityName;
            case "PUT": return "UPDATE_" + entityName;
            case "DELETE": return "DELETE_" + entityName;
            default: return httpMethod + "_" + entityName;
        }
    }
}
