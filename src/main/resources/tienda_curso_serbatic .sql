CREATE TABLE `productos` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_categoria` int,
  `nombre` varchar(255),
  `descripcion` varchar(255),
  `precio` double,
  `stock` int,
  `fecha_alta` timestamp,
  `fecha_baja` timestamp,
  `impuesto` float,
  `imagen` varchar(255)
);

CREATE TABLE `categorias` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(255),
  `descripcion` varchar(255)
);

CREATE TABLE `roles` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `rol` varchar(255)
);

CREATE TABLE `usuarios` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_rol` int,
  `email` varchar(255),
  `clave` varchar(255),
  `nombre` varchar(255),
  `apellido1` varchar(255),
  `apellido2` varchar(255),
  `direccion` varchar(255),
  `provincia` varchar(255),
  `localidad` varchar(255),
  `telefono` varchar(255),
  `dni` varchar(255)
);

CREATE TABLE `pedidos` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int,
  `fecha` timestamp,
  `metodo_pago` varchar(255),
  `estado` varchar(255),
  `num_factura` varchar(255),
  `total` double
);

CREATE TABLE `detalles_pedido` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_pedido` int,
  `id_producto` int,
  `precio_unidad` float,
  `unidades` int,
  `impuesto` float,
  `total` double
);

CREATE TABLE `opciones_menu` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_rol` int,
  `nombre_opcion` varchar(255),
  `url_opcion` varchar(255)
);

CREATE TABLE `configuracion` (
  `id` int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `clave` varchar(255),
  `valor` varchar(255),
  `tipo` varchar(255)
);

CREATE TABLE `valoraciones` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `id_producto` int,
  `id_usuario` int,
  `valoracion` int,
  `comentario` varchar(255)
);

CREATE TABLE `proveedores` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `nombre` varchar(255),
  `direccion` varchar(255),
  `localidad` varchar(255),
  `provincia` varchar(255),
  `telefono` varchar(255),
  `cif` varchar(255),
  `email` varchar(255)
);

CREATE TABLE `descuentos` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `codigo` varchar(255),
  `descuento` float,
  `fecha_inicio` timestamp,
  `fecha_fin` timestamp
);

CREATE TABLE `impuestos` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `impuesto` float
);

CREATE TABLE `metodos_pago` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `metodo_pago` varchar(255)
);

ALTER TABLE `productos` ADD FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`);

ALTER TABLE `usuarios` ADD FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`);

ALTER TABLE `opciones_menu` ADD FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`);

ALTER TABLE `pedidos` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

ALTER TABLE `detalles_pedido` ADD FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`);

ALTER TABLE `detalles_pedido` ADD FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`);

ALTER TABLE `valoraciones` ADD FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`);

ALTER TABLE `valoraciones` ADD FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);
