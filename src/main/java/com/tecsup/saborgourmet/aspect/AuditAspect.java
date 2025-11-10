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

/**
 * Aspecto de Auditoría que registra todas las operaciones CRUD
 * en la bitácora del sistema
 * @author Juan Aguirre Saavedra
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class AuditAspect {

    private final BitacoraRepository bitacoraRepository;

    /**
     * Pointcut para operaciones de creación
     */
    @Pointcut("execution(* com.tecsup.saborgourmet.service.*.create*(..)) || " +
            "execution(* com.tecsup.saborgourmet.service.*.save*(..))")
    public void createOperations() {}

    /**
     * Pointcut para operaciones de actualización
     */
    @Pointcut("execution(* com.tecsup.saborgourmet.service.*.update*(..)) || " +
            "execution(* com.tecsup.saborgourmet.service.*.edit*(..))")
    public void updateOperations() {}

    /**
     * Pointcut para operaciones de eliminación
     */
    @Pointcut("execution(* com.tecsup.saborgourmet.service.*.delete*(..)) || " +
            "execution(* com.tecsup.saborgourmet.service.*.remove*(..))")
    public void deleteOperations() {}

    /**
     * Audita operaciones de creación
     */
    @AfterReturning(pointcut = "createOperations()", returning = "result")
    public void auditCreate(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "CREATE", result);
    }

    /**
     * Audita operaciones de actualización
     */
    @AfterReturning(pointcut = "updateOperations()", returning = "result")
    public void auditUpdate(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "UPDATE", result);
    }

    /**
     * Audita operaciones de eliminación
     */
    @AfterReturning(pointcut = "deleteOperations()", returning = "result")
    public void auditDelete(JoinPoint joinPoint, Object result) {
        registrarAuditoria(joinPoint, "DELETE", result);
    }

    /**
     * Registra en la bitácora la acción realizada
     */
    private void registrarAuditoria(JoinPoint joinPoint, String accion, Object result) {
        try {
            // Obtener usuario autenticado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = null;

            if (auth != null && auth.getPrincipal() instanceof Usuario) {
                usuario = (Usuario) auth.getPrincipal();
            }

            // Obtener módulo desde el nombre de la clase del servicio
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String modulo = className.replace("Service", "").toUpperCase();

            // Obtener método y parámetros
            String metodo = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();

            // Construir detalle
            StringBuilder detalle = new StringBuilder();
            detalle.append("Método: ").append(metodo).append(" | ");

            if (args != null && args.length > 0) {
                detalle.append("Parámetros: ");
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        detalle.append(args[i].getClass().getSimpleName())
                                .append("=").append(args[i].toString());
                        if (i < args.length - 1) detalle.append(", ");
                    }
                }
            }

            // Obtener IP del cliente
            String ipAddress = obtenerIpCliente();

            // Crear y guardar registro de bitácora
            Bitacora bitacora = Bitacora.builder()
                    .usuario(usuario)
                    .modulo(modulo)
                    .accion(accion)
                    .detalle(detalle.toString())
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

    /**
     * Obtiene la IP del cliente desde el request HTTP
     */
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

    /**
     * Manejo de excepciones en operaciones auditadas
     */
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

            Bitacora bitacora = Bitacora.builder()
                    .usuario(usuario)
                    .modulo(modulo)
                    .accion("ERROR")
                    .detalle("Método: " + metodo + " | Error: " + exception.getMessage())
                    .ipAddress(obtenerIpCliente())
                    .build();

            bitacoraRepository.save(bitacora);

            log.error("❌ [ERROR AUDITADO] {} - {} - {}", modulo, metodo, exception.getMessage());

        } catch (Exception e) {
            log.error("Error al registrar excepción en auditoría: {}", e.getMessage());
        }
    }
}