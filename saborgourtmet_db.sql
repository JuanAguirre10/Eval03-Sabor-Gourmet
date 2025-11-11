CREATE DATABASE  IF NOT EXISTS `sabor_gourmet` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `sabor_gourmet`;
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sabor_gourmet
-- ------------------------------------------------------
-- Server version	5.5.5-10.11.14-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bitacora`
--

DROP TABLE IF EXISTS `bitacora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bitacora` (
  `id_bitacora` bigint(20) NOT NULL AUTO_INCREMENT,
  `accion` varchar(20) NOT NULL,
  `detalle` text DEFAULT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `ip_address` varchar(50) DEFAULT NULL,
  `modulo` varchar(50) NOT NULL,
  `id_usuario` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_bitacora`),
  KEY `FK9c577adx555dix8kgjnw1rbwp` (`id_usuario`),
  CONSTRAINT `FK9c577adx555dix8kgjnw1rbwp` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bitacora`
--

LOCK TABLES `bitacora` WRITE;
/*!40000 ALTER TABLE `bitacora` DISABLE KEYS */;
INSERT INTO `bitacora` VALUES (1,'CREATE','Método: createPedido | Parámetros: Pedido=Pedido(idPedido=1, cliente=Cliente(idCliente=1, dni=null, nombres=null, apellidos=null, telefono=null, correo=null, estado=true, fechaRegistro=null), mesa=Mesa(idMesa=1, numero=null, capacidad=null, estado=disponible), fechaHora=2025-11-10T16:53:00.547230400, estado=en_preparacion, detalles=[], total=0.0)','2025-11-10 16:53:00.000000','0:0:0:0:0:0:0:1','PEDIDO',5),(2,'CREATE','Método: createPedido | Parámetros: Pedido=Pedido(idPedido=2, cliente=Cliente(idCliente=2, dni=null, nombres=null, apellidos=null, telefono=null, correo=null, estado=true, fechaRegistro=null), mesa=Mesa(idMesa=1, numero=null, capacidad=null, estado=disponible), fechaHora=2025-11-10T16:53:25.373234800, estado=servido, detalles=[], total=0.0)','2025-11-10 16:53:25.000000','0:0:0:0:0:0:0:1','PEDIDO',5),(3,'CREATE','Método: createPedido | Parámetros: Pedido=Pedido(idPedido=3, cliente=Cliente(idCliente=2, dni=null, nombres=null, apellidos=null, telefono=null, correo=null, estado=true, fechaRegistro=null), mesa=Mesa(idMesa=5, numero=null, capacidad=null, estado=disponible), fechaHora=2025-11-10T16:53:36.309924400, estado=pendiente, detalles=[], total=0.0)','2025-11-10 16:53:36.000000','0:0:0:0:0:0:0:1','PEDIDO',5),(4,'CREATE','Método: createCompra | Parámetros: Compra=Compra(idCompra=1, proveedor=Proveedor(idProveedor=1, ruc=null, nombre=null, telefono=null, correo=null, direccion=null, estado=true), fechaCompra=2025-11-10T16:54:18.054339400, detalles=[], total=0.0)','2025-11-10 16:54:18.000000','0:0:0:0:0:0:0:1','COMPRA',5),(5,'DELETE','Método: deleteCompra | Parámetros: Long=1','2025-11-10 16:54:29.000000','0:0:0:0:0:0:0:1','COMPRA',5),(6,'UPDATE','Método: updateInsumo | Parámetros: Long=14, Insumo=Insumo(idInsumo=14, nombre=Leche, unidadMedida=kg, stock=6.0, stockMinimo=10.0, precioCompra=0.9, estado=true)','2025-11-10 16:57:47.000000','0:0:0:0:0:0:0:1','INSUMO',5),(7,'UPDATE','Método: updateInsumo | Parámetros: Long=4, Insumo=Insumo(idInsumo=4, nombre=Arroz, unidadMedida=kg, stock=0.0, stockMinimo=20.0, precioCompra=3.5, estado=true)','2025-11-10 16:57:57.000000','0:0:0:0:0:0:0:1','INSUMO',5);
/*!40000 ALTER TABLE `bitacora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `id_cliente` bigint(20) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(100) NOT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `dni` varchar(8) NOT NULL,
  `estado` bit(1) NOT NULL,
  `fecha_registro` datetime(6) DEFAULT NULL,
  `nombres` varchar(100) NOT NULL,
  `telefono` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`),
  UNIQUE KEY `UK_m6ysdwsqke00e5piajbvgn6lg` (`dni`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'Pérez García','juan.perez@email.com','12345678',_binary '','2025-11-10 16:42:00.000000','Juan Carlos','987654321'),(2,'López Torres','maria.lopez@email.com','87654321',_binary '','2025-11-10 16:42:00.000000','María','912345678'),(3,'Pérez Rojas','maria.perez@gmail.com','74563210',_binary '','2024-10-05 12:30:00.000000','María Fernanda','999555444'),(4,'Ramírez Díaz','luis.ramirez@hotmail.com','70254891',_binary '','2024-10-06 10:00:00.000000','Luis Alberto','988777666'),(5,'Gómez Salas','carla.gomez@yahoo.com','73698542',_binary '','2024-10-06 14:10:00.000000','Carla Vanessa','977444333'),(6,'Quispe Huamán','jose.quispe@outlook.com','71452369',_binary '','2024-10-07 11:45:00.000000','José Manuel','955111222'),(7,'Chávez Paredes','ana.chavez@gmail.com','70985471',_binary '','2024-10-07 13:00:00.000000','Ana Lucía','966999888');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `compras`
--

DROP TABLE IF EXISTS `compras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `compras` (
  `id_compra` bigint(20) NOT NULL AUTO_INCREMENT,
  `fecha_compra` datetime(6) NOT NULL,
  `total` double NOT NULL,
  `id_proveedor` bigint(20) NOT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `FKkypgd762ocsq30thp7sxxhd20` (`id_proveedor`),
  CONSTRAINT `FKkypgd762ocsq30thp7sxxhd20` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `compras`
--

LOCK TABLES `compras` WRITE;
/*!40000 ALTER TABLE `compras` DISABLE KEYS */;
INSERT INTO `compras` VALUES (15,'2024-10-05 09:00:00.000000',120,1),(16,'2024-10-06 11:30:00.000000',260.5,2),(17,'2024-10-07 08:45:00.000000',180.75,6),(18,'2024-10-08 10:15:00.000000',340,8),(19,'2024-10-09 14:40:00.000000',220,9),(20,'2024-10-12 09:20:00.000000',145.6,11),(21,'2024-10-13 16:00:00.000000',195,12),(22,'2024-10-14 12:00:00.000000',305.25,18),(23,'2024-10-15 15:50:00.000000',278.9,19),(24,'2024-10-16 11:10:00.000000',360.4,20);
/*!40000 ALTER TABLE `compras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_compra`
--

DROP TABLE IF EXISTS `detalle_compra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_compra` (
  `id_detalle_compra` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` double NOT NULL,
  `precio_unitario` double NOT NULL,
  `subtotal` double NOT NULL,
  `id_compra` bigint(20) NOT NULL,
  `id_insumo` bigint(20) NOT NULL,
  PRIMARY KEY (`id_detalle_compra`),
  KEY `FK24e1stplndaucn3dao9chwo8p` (`id_compra`),
  KEY `FKrmyf21mhpktv8dj2puups9oeo` (`id_insumo`),
  CONSTRAINT `FK24e1stplndaucn3dao9chwo8p` FOREIGN KEY (`id_compra`) REFERENCES `compras` (`id_compra`),
  CONSTRAINT `FKrmyf21mhpktv8dj2puups9oeo` FOREIGN KEY (`id_insumo`) REFERENCES `insumos` (`id_insumo`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_compra`
--

LOCK TABLES `detalle_compra` WRITE;
/*!40000 ALTER TABLE `detalle_compra` DISABLE KEYS */;
INSERT INTO `detalle_compra` VALUES (21,50,1,50,15,1),(22,30,0.75,22.5,15,2),(23,40,3,120,16,4),(24,100,0.9,90,16,5),(25,20,4.5,90,17,6),(26,25,0.5,12.5,17,7),(27,15,5,75,18,8),(28,30,0.9,27,18,9),(29,40,0.6,24,19,10),(30,30,1,30,19,1),(31,20,2.5,50,20,3),(32,50,0.9,45,20,5),(33,40,0.75,30,21,2),(34,30,3,90,21,4),(35,25,4.5,112.5,22,6),(36,40,0.5,20,22,7),(37,18,5,90,23,8),(38,35,0.9,31.5,23,9),(39,60,0.6,36,24,10),(40,40,1,40,24,1);
/*!40000 ALTER TABLE `detalle_compra` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detalle_pedido`
--

DROP TABLE IF EXISTS `detalle_pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detalle_pedido` (
  `id_detalle_pedido` bigint(20) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) NOT NULL,
  `subtotal` double NOT NULL,
  `id_pedido` bigint(20) NOT NULL,
  `id_plato` bigint(20) NOT NULL,
  PRIMARY KEY (`id_detalle_pedido`),
  KEY `FKh10qteor08f4cbxhsf97qtgyk` (`id_pedido`),
  KEY `FKdblhnstsowbr758vf3e6y0f83` (`id_plato`),
  CONSTRAINT `FKdblhnstsowbr758vf3e6y0f83` FOREIGN KEY (`id_plato`) REFERENCES `platos` (`id_plato`),
  CONSTRAINT `FKh10qteor08f4cbxhsf97qtgyk` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detalle_pedido`
--

LOCK TABLES `detalle_pedido` WRITE;
/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` VALUES (1,2,50,1,1),(2,1,35.5,1,2),(3,3,120,2,3),(4,1,45,3,4),(5,2,54,3,5),(6,2,150,4,6),(7,1,30,5,2),(8,2,45,5,7),(9,3,132,6,8),(10,2,56,7,1),(11,1,43.9,7,4),(12,2,180,8,3),(13,1,35,9,5),(14,1,30,9,6),(15,2,110,10,2);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `insumos`
--

DROP TABLE IF EXISTS `insumos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `insumos` (
  `id_insumo` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` bit(1) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio_compra` double NOT NULL,
  `stock` double NOT NULL,
  `stock_minimo` double NOT NULL,
  `unidad_medida` varchar(20) NOT NULL,
  PRIMARY KEY (`id_insumo`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `insumos`
--

LOCK TABLES `insumos` WRITE;
/*!40000 ALTER TABLE `insumos` DISABLE KEYS */;
INSERT INTO `insumos` VALUES (1,_binary '','Papa Blanca',2.5,50,10,'kg'),(2,_binary '','Carne de Res',25,30,5,'kg'),(3,_binary '','Pollo',12,40,10,'kg'),(4,_binary '','Arroz',3.5,0,20,'kg'),(5,_binary '','Tomate',4,20,5,'kg'),(6,_binary '','Papa',1,100,10,'kg'),(7,_binary '','Cebolla',0.75,80,8,'kg'),(8,_binary '','Ají Amarillo',2.5,40,5,'kg'),(9,_binary '','Pollo',3,60,10,'kg'),(10,_binary '','Arroz',0.9,200,20,'kg'),(11,_binary '','Carne de Res',4.5,50,8,'kg'),(12,_binary '','Limón',0.5,120,15,'kg'),(13,_binary '','Pescado (Filete)',5,30,5,'kg'),(14,_binary '','Leche',0.9,6,10,'kg'),(15,_binary '','Azúcar',0.6,150,15,'kg'),(16,_binary '','Papa',1.2,100,10,'kg'),(17,_binary '','Cebolla',0.9,80,8,'kg'),(18,_binary '','Ají Amarillo',2.5,40,5,'kg'),(19,_binary '','Pollo',6,60,10,'kg'),(20,_binary '','Arroz',3.5,200,20,'kg'),(21,_binary '','Carne de Res',12,50,8,'kg'),(22,_binary '','Limón',2,120,15,'kg'),(23,_binary '','Pescado (Filete)',14,30,5,'kg'),(24,_binary '','Leche',3,90,10,'litro'),(25,_binary '','Azúcar',2.2,150,15,'kg');
/*!40000 ALTER TABLE `insumos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesas`
--

DROP TABLE IF EXISTS `mesas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesas` (
  `id_mesa` bigint(20) NOT NULL AUTO_INCREMENT,
  `capacidad` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL,
  `numero` int(11) NOT NULL,
  PRIMARY KEY (`id_mesa`),
  UNIQUE KEY `UK_j0e0hutvqcecq56ogqll8feqw` (`numero`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesas`
--

LOCK TABLES `mesas` WRITE;
/*!40000 ALTER TABLE `mesas` DISABLE KEYS */;
INSERT INTO `mesas` VALUES (1,4,'ocupada',1),(2,4,'disponible',2),(3,4,'disponible',3),(4,4,'disponible',4),(5,4,'disponible',5),(6,4,'disponible',6),(7,4,'disponible',7),(8,4,'disponible',8),(9,4,'disponible',9),(10,4,'disponible',10),(11,6,'disponible',11),(12,6,'disponible',12),(13,6,'disponible',13),(14,6,'disponible',14),(15,6,'disponible',15);
/*!40000 ALTER TABLE `mesas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedidos`
--

DROP TABLE IF EXISTS `pedidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedidos` (
  `id_pedido` bigint(20) NOT NULL AUTO_INCREMENT,
  `estado` varchar(20) NOT NULL,
  `fecha_hora` datetime(6) NOT NULL,
  `total` double NOT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `id_mesa` bigint(20) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  KEY `FKdnomiluem4t3x66t6b9aher47` (`id_cliente`),
  KEY `FK6lnywvattil83fkuh1f9v1cak` (`id_mesa`),
  CONSTRAINT `FK6lnywvattil83fkuh1f9v1cak` FOREIGN KEY (`id_mesa`) REFERENCES `mesas` (`id_mesa`),
  CONSTRAINT `FKdnomiluem4t3x66t6b9aher47` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedidos`
--

LOCK TABLES `pedidos` WRITE;
/*!40000 ALTER TABLE `pedidos` DISABLE KEYS */;
INSERT INTO `pedidos` VALUES (1,'cerrado','2025-11-10 16:53:00.000000',85.5,1,1),(2,'servido','2025-11-10 16:53:25.000000',120,2,1),(3,'pendiente','2025-11-10 16:53:36.000000',99,2,5),(4,'pendiente','2024-10-01 12:30:00.000000',150,1,3),(5,'cerrado','2024-10-01 13:15:00.000000',75,2,5),(6,'servido','2024-10-02 14:00:00.000000',132,3,7),(7,'cerrado','2024-10-03 19:20:00.000000',99.9,4,2),(8,'pendiente','2024-10-04 11:45:00.000000',180,5,10),(9,'en_preparacion','2024-10-04 20:10:00.000000',65,6,1),(10,'servido','2024-10-05 12:10:00.000000',110,7,8),(11,'cerrado','2024-10-05 13:30:00.000000',0,3,9),(12,'pendiente','2024-10-06 15:00:00.000000',0,2,4),(13,'servido','2024-10-07 18:25:00.000000',0,1,6);
/*!40000 ALTER TABLE `pedidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `platos`
--

DROP TABLE IF EXISTS `platos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `platos` (
  `id_plato` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` text DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` double NOT NULL,
  `tipo` varchar(20) NOT NULL,
  PRIMARY KEY (`id_plato`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `platos`
--

LOCK TABLES `platos` WRITE;
/*!40000 ALTER TABLE `platos` DISABLE KEYS */;
INSERT INTO `platos` VALUES (1,'Causa tradicional con pollo y palta',_binary '','Causa Limeña',18,'entrada'),(2,'Tequeños de queso con salsas',_binary '','Tequeños',15,'entrada'),(3,'Lomo saltado con papas fritas',_binary '','Lomo Saltado',35,'fondo'),(4,'Arroz con mariscos frescos',_binary '','Arroz con Mariscos',42,'fondo'),(5,'Ají de gallina con arroz',_binary '','Ají de Gallina',28,'fondo'),(6,'Postre tradicional peruano',_binary '','Suspiro Limeño',12,'postre'),(7,'Torta tres leches',_binary '','Tres Leches',14,'postre'),(8,'Chicha morada natural',_binary '','Chicha Morada',8,'bebida'),(9,'Gaseosa Inca Kola 500ml',_binary '','Inca Kola',5,'bebida'),(10,'Pescado fresco con limón, cebolla y ají limo.',_binary '','Ceviche Clásico',25,'entrada'),(11,'Clásico plato peruano con carne de res, papas fritas y arroz.',_binary '','Lomo Saltado',30,'fondo'),(12,'Guiso cremoso de pollo deshilachado con pan y ají amarillo.',_binary '','Ají de Gallina',22,'fondo'),(13,'Refresco tradicional de maíz morado con piña y canela.',_binary '','Chicha Morada',6,'bebida'),(14,'Postre tradicional a base de manjar blanco y merengue.',_binary '','Suspiro Limeño',10,'postre'),(15,'Arroz salteado con mariscos y especias.',_binary '','Arroz con Mariscos',28,'fondo'),(16,'Rodajas de papa bañadas en salsa huancaína.',_binary '','Papa a la Huancaína',12,'entrada'),(17,'Jugo natural de papaya fresca.',_binary '','Jugo de Papaya',7,'bebida'),(18,'Pollo al carbón acompañado de papas fritas y ensalada.',_binary '','Pollo a la Brasa',35,'fondo'),(19,'Postre de maíz morado con frutas secas.',_binary '','Mazamorra Morada',8,'postre');
/*!40000 ALTER TABLE `platos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proveedores` (
  `id_proveedor` bigint(20) NOT NULL AUTO_INCREMENT,
  `correo` varchar(100) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `estado` bit(1) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `ruc` varchar(11) NOT NULL,
  `telefono` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`id_proveedor`),
  UNIQUE KEY `UK_l2qkpg6gem3abown28yrhqjph` (`ruc`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
INSERT INTO `proveedores` VALUES (1,'ventas@lagranja.com','Av. Argentina 123, Lima',_binary '','Distribuidora La Granja SAC','20123456789','987654321'),(2,'contacto@mercadocentral.com','Jr. Huallaga 456, Lima',_binary '','Mercado Central EIRL','20987654321','912345678'),(6,'ventas@insumosperu.com','Av. La Marina 10, Lima',_binary '\0','Insumos Peru S.A.','20422233344','999222333'),(8,'ventas@norte.com','Jr. Pescadores 12, Piura',_binary '\0','Proveedor Norteño','20444455566','995444555'),(9,'ventas@insumosur.pe','Av. Óvalo 3, Arequipa',_binary '\0','Insumos del Sur','20455566677','988555666'),(11,'contacto@importperu.pe','Av. Tacna 23, Lima',_binary '\0','Importadora Perú','20477788899','965777888'),(12,'ventas@suministros.pe','Jr. Oropesa 45, Trujillo',_binary '\0','Suministros Andinos','20488899900','954888999'),(18,'contacto@psj.com','Av. Argentina 123, Lima',_binary '','Proveedor San Jorge','20100011121','999111222'),(19,'ventas@mdn.com','Jr. Comercio 45, Lima',_binary '','Mercados del Norte S.A.','20456789012','987654321'),(20,'info@andinas.com','Av. Bolivia 567, Arequipa',_binary '','Distribuciones Andinas','20411122233','964000111'),(21,'contacto@alimentoscusco.pe','Calle Principal 88, Cusco',_binary '','Alimentos Cusco SAC','20433344455','984333444'),(22,'ventas@dlc.com','Jr. Comercio 90, Lima',_binary '','Distribuidores Lima Centro','20466677788','976666777');
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` bigint(20) NOT NULL AUTO_INCREMENT,
  `contrasena` varchar(255) NOT NULL,
  `estado` bit(1) NOT NULL,
  `nombre_usuario` varchar(50) NOT NULL,
  `rol` varchar(20) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `UK_of5vabgukahdwmgxk4kjrbu98` (`nombre_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'$2a$10$vc4AnWvvhmH2WnDN7rEe4ehqNhfWQWycs7QwWeiWA0wUCHwh/MTrS',_binary '','admin','ADMIN'),(2,'$2a$10$NpmqBSrb8I1a3rbRUpKp5.cXz04Ou5b4v4krymy/4F/bCZfUwF3rS',_binary '','mozo','MOZO'),(3,'$2a$10$ov2sjbAwD6d8W04y4j2IE.7F2GgQu2DbyKLq08YLJyP7mtNHO701.',_binary '','cocinero','COCINERO'),(4,'$2a$10$b6pk/.FELGeRp.mg17GtYO5PZUlq6NsE4yTx6.i7lSdeCn8ca/cfC',_binary '','cajero','CAJERO'),(5,'$2a$10$MIBLn/VNep94bbBOJus7n.mcj9OPVZMT7euwKW/mUjnuu4b8RyCTO',_binary '','juan','ADMIN');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-10 22:27:48
