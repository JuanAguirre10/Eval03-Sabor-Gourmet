# 🍽️ Sistema de Gestión de Restaurante "Sabor Gourmet"

## 📋 Descripción del Proyecto

Sistema empresarial completo desarrollado con **Spring Boot 3**, **Spring Security**, **AOP** y **JPA** para la gestión integral de un restaurante. Implementa autenticación basada en roles, auditoría automática con programación orientada a aspectos y operaciones CRUD completas para la administración de clientes, mesas, pedidos, inventario y compras.

**Autor:** Juan Aguirre Saavedra  
**Curso:** Desarrollo de Aplicaciones Web - TECSUP  
**Código:** EC03-S12-2025-02  
**Fecha:** Noviembre 2025

---

## 🏗️ Arquitectura y Tecnologías

### Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje de programación |
| Spring Boot | 3.2.0 | Framework principal |
| Spring Security | 6 | Autenticación y autorización |
| Spring Data JPA | 3.2.0 | Persistencia de datos |
| Spring AOP | 3.2.0 | Programación orientada a aspectos |
| MySQL | 8.0 | Base de datos relacional |
| Thymeleaf | 3.1.2 | Motor de plantillas |
| Bootstrap | 5.3.0 | Framework CSS |
| Lombok | 1.18.30 | Reducción de código boilerplate |
| Maven | 3.8+ | Gestión de dependencias |

### Patrones de Diseño Implementados

- **MVC (Model-View-Controller)** - Separación de responsabilidades
- **Repository Pattern** - Abstracción del acceso a datos
- **Service Layer Pattern** - Lógica de negocio centralizada
- **AOP (Aspect-Oriented Programming)** - Funcionalidades transversales
- **Dependency Injection** - Inversión de control con Spring

---

## 📦 Módulos Implementados

### 1️⃣ Módulo de Clientes y Mesas

**Entidades:** `Cliente`, `Mesa`

**Funcionalidades:**
- ✅ CRUD completo de clientes (DNI, nombres, apellidos, teléfono, correo)
- ✅ Gestión de estados de mesas (disponible, ocupada, reservada, mantenimiento)
- ✅ Validaciones con Bean Validation (DNI 8 dígitos, teléfono 9 dígitos)
- ✅ Búsqueda de clientes por nombre o apellido
- ✅ Control de capacidad de mesas (1-20 personas)
- ✅ Auditoría automática con AOP

**Endpoints principales:**
```
GET  /clientes           - Listar clientes activos
GET  /clientes/nuevo     - Formulario nuevo cliente
POST /clientes           - Crear cliente
GET  /clientes/editar/{id} - Editar cliente
GET  /clientes/eliminar/{id} - Eliminar cliente (lógico)

GET  /mesas              - Listar todas las mesas
GET  /mesas/nuevo        - Formulario nueva mesa
POST /mesas              - Crear mesa
GET  /mesas/ocupar/{id}  - Ocupar mesa
GET  /mesas/liberar/{id} - Liberar mesa
```

---

### 2️⃣ Módulo de Pedidos y Platos

**Entidades:** `Pedido`, `DetallePedido`, `Plato`

**Funcionalidades:**
- ✅ Creación de pedidos asociados a mesas
- ✅ Gestión de estados (pendiente, en_preparacion, servido, cerrado)
- ✅ Agregar platos a pedidos con cálculo automático de totales
- ✅ Catálogo de platos por tipo (entrada, fondo, postre, bebida)
- ✅ Ocupación automática de mesa al crear pedido
- ✅ Liberación automática de mesa al cerrar pedido
- ✅ Visualización de pedidos activos en cocina
- ✅ Auditoría completa de todas las operaciones

**Endpoints principales:**
```
GET  /pedidos                    - Listar pedidos activos
GET  /pedidos/nuevo              - Formulario nuevo pedido
POST /pedidos                    - Crear pedido
GET  /pedidos/ver/{id}           - Ver detalle del pedido
GET  /pedidos/agregar-items/{id} - Agregar platos al pedido
POST /pedidos/agregar-items/{id} - Guardar item agregado
GET  /pedidos/cerrar/{id}        - Cerrar pedido
GET  /pedidos/cambiar-estado/{id}?estado=X - Cambiar estado

GET  /platos                     - Listar platos
GET  /platos/nuevo               - Formulario nuevo plato
POST /platos                     - Crear plato
GET  /platos/editar/{id}         - Editar plato
GET  /platos/eliminar/{id}       - Eliminar plato
```

---

### 3️⃣ Módulo de Inventario y Compras

**Entidades:** `Insumo`, `Proveedor`, `Compra`, `DetalleCompra`

**Funcionalidades:**
- ✅ Gestión de insumos con control de stock
- ✅ Alertas automáticas de stock mínimo
- ✅ Registro de proveedores con RUC único
- ✅ Compras con actualización automática de inventario
- ✅ Cálculo de totales de compras
- ✅ Historial de compras por proveedor
- ✅ Ajuste de stock al eliminar compras
- ✅ Auditoría completa del inventario

**Endpoints principales:**
```
GET  /inventario/insumos         - Listar insumos
GET  /inventario/insumos/nuevo   - Formulario nuevo insumo
POST /inventario/insumos         - Crear insumo
GET  /inventario/insumos/alertas - Ver alertas de stock bajo
GET  /inventario/insumos/editar/{id} - Editar insumo

GET  /compras                    - Listar compras
GET  /compras/nuevo              - Formulario nueva compra
POST /compras                    - Crear compra (actualiza stock)
GET  /compras/ver/{id}           - Ver detalle de compra
GET  /compras/eliminar/{id}      - Eliminar compra (ajusta stock)
```

---

## 🔒 Spring Security - Autenticación y Autorización

### Usuarios Predefinidos

| Usuario | Contraseña | Rol | Permisos |
|---------|------------|-----|----------|
| **juan** | **1234** | **ADMIN** | **Acceso total (protegido)** |
| admin | admin123 | ADMIN | Acceso total al sistema |
| mozo | mozo123 | MOZO | Clientes, Mesas, Pedidos |
| cocinero | cocinero123 | COCINERO | Pedidos (cocina), Platos |
| cajero | cajero123 | CAJERO | Ventas, Facturas |

### Configuración de Roles y Permisos

```java
/admin/**        → ROLE_ADMIN
/pedidos/**      → ROLE_MOZO, ROLE_COCINERO, ROLE_ADMIN
/inventario/**   → ROLE_ADMIN
/compras/**      → ROLE_ADMIN
/clientes/**     → ROLE_ADMIN, ROLE_MOZO
/mesas/**        → ROLE_ADMIN, ROLE_MOZO
/platos/**       → ROLE_ADMIN, ROLE_COCINERO
/dashboard       → Cualquier usuario autenticado
```

### Características de Seguridad

- ✅ **Cifrado BCrypt** de contraseñas
- ✅ **Sesiones únicas** por usuario
- ✅ **Protección CSRF** habilitada
- ✅ **Usuario "juan" protegido** (no se puede eliminar)
- ✅ **Logout seguro** con invalidación de sesión
- ✅ **Página de acceso denegado** (403)
- ✅ **Redirección automática** a login si no autenticado

---

## 🎯 Programación Orientada a Aspectos (AOP)

### Aspecto de Auditoría Implementado

**Clase:** `AuditAspect.java`

**Funcionalidad:** Registra automáticamente todas las operaciones CRUD en la tabla `bitacora`.

### Pointcuts Definidos

```java
@Pointcut("execution(* com.tecsup.saborgourmet.service.*.create*(..))")
public void createOperations() {}

@Pointcut("execution(* com.tecsup.saborgourmet.service.*.update*(..))")
public void updateOperations() {}

@Pointcut("execution(* com.tecsup.saborgourmet.service.*.delete*(..))")
public void deleteOperations() {}
```

### Información Registrada en Bitácora

| Campo | Descripción |
|-------|-------------|
| **usuario** | Usuario que realizó la acción |
| **modulo** | Módulo afectado (CLIENTE, MESA, PEDIDO, etc.) |
| **accion** | Tipo de operación (CREATE, UPDATE, DELETE, ERROR) |
| **detalle** | Descripción completa de la operación |
| **fechaHora** | Timestamp de la acción |
| **ipAddress** | Dirección IP del cliente |

### Ejemplo de Registro de Auditoría

```
Módulo: CLIENTE
Acción: CREATE
Detalle: Método: createCliente | Parámetros: Cliente=Juan Pérez García
Usuario: juan
IP: 127.0.0.1
Fecha: 2025-11-10 22:30:15
```

---

## 🗄️ Modelo de Base de Datos

### Diagrama de Entidades (Simplificado)

```
┌──────────┐       ┌──────────┐       ┌──────────┐
│ CLIENTES │ 1   N │ PEDIDOS  │ 1   N │ DETALLE  │
│          ├───────┤          ├───────┤ PEDIDO   │
└──────────┘       └────┬─────┘       └────┬─────┘
                        │                   │
                        │ N              1  │
                   ┌────┴─────┐       ┌────┴─────┐
                   │  MESAS   │       │  PLATOS  │
                   └──────────┘       └──────────┘

┌──────────┐       ┌──────────┐       ┌──────────┐
│PROVEEDOR │ 1   N │ COMPRAS  │ 1   N │ DETALLE  │
│          ├───────┤          ├───────┤ COMPRA   │
└──────────┘       └──────────┘       └────┬─────┘
                                            │ N
                                       ┌────┴─────┐
                                       │ INSUMOS  │
                                       └──────────┘

┌──────────┐       ┌──────────┐
│ USUARIOS │ 1   N │BITACORA  │
│          ├───────┤          │
└──────────┘       └──────────┘
```

### Tablas Principales (11 Entidades)

1. **clientes** - Información de clientes
2. **mesas** - Mesas del restaurante
3. **pedidos** - Pedidos realizados
4. **detalle_pedido** - Items de cada pedido
5. **platos** - Menú del restaurante
6. **insumos** - Inventario de insumos
7. **proveedores** - Proveedores de insumos
8. **compras** - Compras realizadas
9. **detalle_compra** - Items de cada compra
10. **usuarios** - Usuarios del sistema
11. **bitacora** - Registro de auditoría (AOP)

---

## 🚀 Instalación y Configuración

### Prerrequisitos

```bash
Java JDK 17 o superior
Maven 3.8 o superior
MySQL 8.0 o superior
IntelliJ IDEA (recomendado) o cualquier IDE Java
```

### Paso 1: Clonar el Repositorio

```bash
git clone <url-repositorio>
cd sabor-gourmet
```

### Paso 2: Configurar Base de Datos

**Crear base de datos en MySQL:**

```sql
CREATE DATABASE sabor_gourmet 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

**Configurar credenciales en `application.properties`:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3307/sabor_gourmet?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
```

> **Nota:** El puerto por defecto es 3307. Si usas 3306, cámbialo en la URL.

### Paso 3: Compilar el Proyecto

```bash
mvn clean install
```

### Paso 4: Ejecutar la Aplicación

**Opción 1 - Con Maven:**
```bash
mvn spring-boot:run
```

**Opción 2 - Desde IDE:**
- Ejecutar la clase `SaborGourmetApplication.java`

**Opción 3 - JAR ejecutable:**
```bash
cd target
java -jar sabor-gourmet-1.0.0.jar
```

### Paso 5: Acceder al Sistema

```
URL: http://localhost:8080/saborgourmet
Usuario: juan
Contraseña: 1234
```

---

## 📁 Estructura del Proyecto

```
sabor-gourmet/
├── src/
│   ├── main/
│   │   ├── java/com/tecsup/saborgourmet/
│   │   │   ├── SaborGourmetApplication.java
│   │   │   ├── model/                    (11 entidades JPA)
│   │   │   │   ├── Cliente.java
│   │   │   │   ├── Mesa.java
│   │   │   │   ├── Pedido.java
│   │   │   │   ├── DetallePedido.java
│   │   │   │   ├── Plato.java
│   │   │   │   ├── Insumo.java
│   │   │   │   ├── Proveedor.java
│   │   │   │   ├── Compra.java
│   │   │   │   ├── DetalleCompra.java
│   │   │   │   ├── Usuario.java
│   │   │   │   └── Bitacora.java
│   │   │   ├── repository/               (11 repositorios)
│   │   │   │   └── [Todos los Repository]
│   │   │   ├── service/                  (8 servicios)
│   │   │   │   ├── UsuarioService.java
│   │   │   │   ├── ClienteService.java
│   │   │   │   ├── MesaService.java
│   │   │   │   ├── PedidoService.java
│   │   │   │   ├── PlatoService.java
│   │   │   │   ├── InsumoService.java
│   │   │   │   ├── ProveedorService.java
│   │   │   │   └── CompraService.java
│   │   │   ├── controller/               (7 controladores)
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── ClienteController.java
│   │   │   │   ├── MesaController.java
│   │   │   │   ├── PedidoController.java
│   │   │   │   ├── PlatoController.java
│   │   │   │   ├── InsumoController.java
│   │   │   │   └── CompraController.java
│   │   │   ├── aspect/
│   │   │   │   └── AuditAspect.java
│   │   │   └── config/
│   │   │       ├── SecurityConfig.java
│   │   │       └── DataInitializer.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── templates/                (21 vistas HTML)
│   │       │   ├── login.html
│   │       │   ├── dashboard.html
│   │       │   ├── clientes/
│   │       │   │   ├── lista.html
│   │       │   │   └── formulario.html
│   │       │   ├── mesas/
│   │       │   │   ├── lista.html
│   │       │   │   └── formulario.html
│   │       │   ├── pedidos/
│   │       │   │   ├── lista.html
│   │       │   │   ├── formulario.html
│   │       │   │   ├── detalle.html
│   │       │   │   └── agregar-items.html
│   │       │   ├── platos/
│   │       │   │   ├── lista.html
│   │       │   │   └── formulario.html
│   │       │   ├── inventario/insumos/
│   │       │   │   ├── lista.html
│   │       │   │   ├── formulario.html
│   │       │   │   └── alertas.html
│   │       │   ├── compras/
│   │       │   │   ├── lista.html
│   │       │   │   ├── formulario.html
│   │       │   │   └── detalle.html
│   │       │   └── error/
│   │       │       ├── 403.html
│   │       │       └── error.html
│   │       └── static/
│   │           ├── css/
│   │           ├── js/
│   │           └── images/
└── pom.xml
```

**Total de archivos creados:** 58+ archivos

---

## 🧪 Pruebas del Sistema

### 1. Probar Autenticación

```
1. Acceder a http://localhost:8080/saborgourmet
2. Login con: juan / 1234
3. Verificar redirección a dashboard
4. Verificar que muestre: "Usuario: juan"
```

### 2. Probar Spring Security

```
1. Cerrar sesión
2. Intentar acceder directamente a /saborgourmet/clientes
3. Debe redirigir a /login ✅
4. Login como mozo
5. Intentar acceder a /saborgourmet/compras
6. Debe mostrar error 403 ✅
```

### 3. Probar Auditoría (AOP)

```sql
-- 1. Crear un cliente desde la interfaz
-- 2. Ejecutar en MySQL:

USE sabor_gourmet;
SELECT * FROM bitacora 
ORDER BY fecha_hora DESC 
LIMIT 5;

-- Debe mostrar el registro:
-- modulo: CLIENTE
-- accion: CREATE
-- usuario: juan
```

### 4. Probar Gestión de Pedidos

```
1. Login como mozo
2. Ir a Pedidos → Nuevo Pedido
3. Seleccionar una mesa disponible
4. Crear pedido
5. Click en "➕ Items"
6. Agregar platos al pedido
7. Verificar cálculo automático del total
8. Cambiar estado a "En Preparación"
9. Cerrar pedido
10. Verificar que la mesa se liberó automáticamente
```

### 5. Probar Gestión de Inventario

```
1. Login como admin
2. Ir a Inventario → Insumos
3. Verificar alertas de stock bajo (badge amarillo)
4. Ir a Compras → Nueva Compra
5. Seleccionar proveedor
6. Agregar insumos
7. Guardar compra
8. Verificar que el stock aumentó automáticamente ✅
```

### 6. Verificar Contraseñas Cifradas

```sql
SELECT nombre_usuario, contrasena 
FROM usuarios;

-- Las contraseñas deben empezar con $2a$ (BCrypt) ✅
```

### 7. Verificar Usuario Protegido

```
1. Intentar eliminar el usuario "juan"
2. Debe mostrar error: 
   "❌ El usuario 'juan' no puede ser eliminado" ✅
```

---

## 📊 Datos Iniciales Cargados Automáticamente

Al ejecutar la aplicación por primera vez, se crean automáticamente:

### Usuarios (5)
- admin, mozo, cocinero, cajero, juan

### Mesas (15)
- Mesas 1-10: Capacidad 4 personas
- Mesas 11-15: Capacidad 6 personas
- Todas en estado: disponible

### Platos (9)
**Entradas:** Causa Limeña, Tequeños  
**Fondos:** Lomo Saltado, Arroz con Mariscos, Ají de Gallina  
**Postres:** Suspiro Limeño, Tres Leches  
**Bebidas:** Chicha Morada, Inca Kola

### Insumos (5)
- Papa Blanca, Carne de Res, Pollo, Arroz, Tomate

### Proveedores (2)
- Distribuidora La Granja SAC
- Mercado Central EIRL

### Clientes (2)
- Juan Carlos Pérez García
- María López Torres

---

## 📝 Requerimientos Cumplidos

### Requerimientos Funcionales ✅

- [x] RF1: Registrar y consultar clientes
- [x] RF2: Asignar y liberar mesas
- [x] RF3: Mostrar mesas disponibles en tiempo real
- [x] RF4: Registrar platos y bebidas con precios
- [x] RF7: Registrar pedidos asociados a mesas
- [x] RF8: Cambiar estado de pedidos
- [x] RF9: Mostrar pedidos pendientes en cocina
- [x] RF13: Registrar proveedores y compras
- [x] RF14: Actualizar stock automáticamente
- [x] RF15: Alertas de stock bajo
- [x] RF16: Crear usuarios y roles
- [x] RF17: Registrar acciones en bitácora
- [x] RF18: Restringir accesos según rol

### Requerimientos No Funcionales ✅

- [x] RNF1: Contraseñas cifradas con BCrypt
- [x] RNF2: Autenticación obligatoria
- [x] RNF3: Registro completo en bitácora
- [x] RNF8: Interfaz intuitiva con Bootstrap 5
- [x] RNF10: Arquitectura modular MVC

---

## 🐛 Solución de Problemas Comunes

### Error: "Port 8080 already in use"

**Solución:** Cambiar puerto en `application.properties`:
```properties
server.port=8081
```

### Error: "Access denied for user 'root'"

**Solución:** Verificar password en `application.properties`

### Error: "Could not create connection to database"

**Solución:**
1. Verificar que MySQL esté corriendo
2. Verificar que la base de datos exista
3. Verificar puerto (3306 o 3307)

### Error: "Bean 'passwordEncoder' could not be registered"

**Solución:** Eliminar archivo `PasswordEncoderConfig.java` si existe en la carpeta `config/`

### Error: Logout no funciona

**Solución:** El logout debe ser POST, no GET. Usar:
```html
<form method="post" th:action="@{/logout}">
    <button type="submit">Salir</button>
</form>
```

### Error: "Table doesn't exist"

**Solución:** Cambiar en `application.properties`:
```properties
spring.jpa.hibernate.ddl-auto=create
```
(Después volver a `update`)

---

## 🎓 Conceptos Aplicados

### Spring Boot
- Auto-configuración
- Inyección de dependencias
- Spring Boot Starter
- Embedded Tomcat

### JPA / Hibernate
- Entidades con `@Entity`
- Relaciones `@ManyToOne`, `@OneToMany`
- Validaciones Bean Validation
- `@PrePersist`, `@PreUpdate`
- JPQL Queries

### Spring Security
- `UserDetailsService`
- Autenticación y Autorización
- BCrypt Password Encoding
- Role-based Access Control
- CSRF Protection

### AOP
- `@Aspect`, `@Component`
- `@Pointcut`, `@AfterReturning`, `@AfterThrowing`
- Interceptación de métodos
- Cross-cutting concerns

### Thymeleaf
- Template Engine
- Expresiones `${...}`, `th:text`, `th:each`
- Formularios con `th:object`
- Integración con Spring Security

---

## 📚 Referencias y Documentación

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Spring Security Reference](https://docs.spring.io/spring-security/)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/)
- [Spring AOP](https://docs.spring.io/spring-framework/reference/core/aop.html)
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Bootstrap Documentation](https://getbootstrap.com/docs/)

---

## 👨‍💻 Información del Desarrollador

**Nombre:** Juan Aguirre Saavedra  
**Institución:** TECSUP  
**Curso:** Desarrollo de Aplicaciones Web  
**Proyecto:** EC03-S12-AGUIRRE-2025-02  
**Fecha:** Noviembre 2025


## 🎉 Características Destacadas

✅ **Sistema 100% funcional** con 3 módulos completos  
✅ **58+ archivos** de código fuente  
✅ **11 entidades JPA** con relaciones complejas  
✅ **Spring Security** con 5 roles diferenciados  
✅ **AOP Auditoría** registra todas las operaciones  
✅ **Contraseñas BCrypt** cifradas  
✅ **Bootstrap 5** diseño responsive  
✅ **Validaciones completas** en formularios  
✅ **Stock automático** en compras  
✅ **Usuario protegido** no se puede eliminar  
✅ **Datos iniciales** cargados automáticamente  

