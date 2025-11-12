package com.tecsup.saborgourmet.aspect;

import com.tecsup.saborgourmet.model.Bitacora;
import com.tecsup.saborgourmet.model.Usuario;
import com.tecsup.saborgourmet.repository.BitacoraRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final BitacoraRepository bitacoraRepository;

    @Pointcut("execution(* com.tecsup.saborgourmet.service.*.create*(..)) || " +
            "execution(* com.tecsup.saborgourmet.service.*.save*(..))")
    public void createOperations() {}

    @Pointcut("execution(* com.tecsup.saborgourmet.service.*.update*(..)) || " +
            "execution(* com.tecsup.saborgourmet.service.*.edit*(..))")
    public void updateOperations() {}

    @Pointcut("execution(* com.tecsup.saborgourmet.service.*.delete*(..)) || " +
            "execution(* com.tecsup.saborgourmet.service.*.remove*(..))")
    public void deleteOperations() {}

    @AfterReturning(pointcut = "createOperations()", returning = "result")
    public void auditCreate(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "CREATE", result);
    }

    @AfterReturning(pointcut = "updateOperations()", returning = "result")
    public void auditUpdate(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "UPDATE", result);
    }

    @AfterReturning(pointcut = "deleteOperations()", returning = "result")
    public void auditDelete(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "DELETE", result);
    }

    private void registrarAuditoria(JoinPoint joinPoint, String accion, Object result) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = null;

            if (auth != null && auth.getPrincipal() instanceof Usuario) {
                usuario = (Usuario) auth.getPrincipal();
            }

            String className = joinPoint.getTarget().getClass().getSimpleName();
            String modulo = className.replace("Service", "").toUpperCase();
            String metodo = joinPoint.getSignature().getName();

            String detalle = metodo + " - Operación exitosa";

            String ipAddress = obtenerIpCliente();
            if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:1".equals(ipAddress)) {
                ipAddress = "127.0.0.1";
            }

            Bitacora bitacora = Bitacora.builder()
                    .usuario(usuario)
                    .modulo(modulo)
                    .accion(accion)
                    .detalle(detalle)
                    .ipAddress(ipAddress)
                    .build();

            bitacoraRepository.save(bitacora);

            log.info("✅ [AUDITORÍA] {} - {} - {} - Usuario: {}",
                    modulo, accion, metodo,
                    usuario != null ? usuario.getUsername() : "Sistema");

        } catch (Exception e) {
            log.error("❌ Error al registrar auditoría: {}", e.getMessage());
        }
    }

    private String obtenerIpCliente() {
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String ip = request.getHeader("X-Forwarded-For");

                if (ip == null || ip.isEmpty()) {
                    ip = request.getRemoteAddr();
                }

                return ip;
            }
        } catch (Exception e) {
            log.warn("No se pudo obtener la IP del cliente");
        }
        return "unknown";
    }

    @AfterThrowing(pointcut = "createOperations() || updateOperations() || deleteOperations()",
            throwing = "exception")
    public void auditException(JoinPoint joinPoint, Exception exception) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = auth != null && auth.getPrincipal() instanceof Usuario
                    ? (Usuario) auth.getPrincipal() : null;

            String className = joinPoint.getTarget().getClass().getSimpleName();
            String modulo = className.replace("Service", "").toUpperCase();
            String metodo = joinPoint.getSignature().getName();

            String ipAddress = obtenerIpCliente();
            if ("0:0:0:0:0:0:0:1".equals(ipAddress) || "0:0:0:0:0:0:0:0:0:0:0:0:0:0:0:1".equals(ipAddress)) {
                ipAddress = "127.0.0.1";
            }

            Bitacora bitacora = Bitacora.builder()
                    .usuario(usuario)
                    .modulo(modulo)
                    .accion("ERROR")
                    .detalle(metodo + " - Error: " + exception.getMessage())
                    .ipAddress(ipAddress)
                    .build();

            bitacoraRepository.save(bitacora);

            log.error("❌ [ERROR AUDITADO] {} - {} - {}", modulo, metodo, exception.getMessage());

        } catch (Exception e) {
            log.error("Error al registrar excepción en auditoría: {}", e.getMessage());
        }
    }
}