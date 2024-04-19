-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-04-2024 a las 21:26:12
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda_raul_ferrero_vicente`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `nombre`, `descripcion`) VALUES
(1, 'Bélicos', 'Juegos basados en la guerra.'),
(2, 'Rol', 'Juegos de Rol'),
(3, 'Habilidad', 'Juegos de habilidad'),
(4, 'Inteligencia', 'Juegos de inteligencia'),
(5, 'Familiares', 'Juegos para toda la familia'),
(6, 'Solitarios', 'Juegos para jugar solo'),
(7, 'Cooperativos', 'Juegos Cooperativos'),
(8, 'Cartas', 'Juegos de Cartas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuracion`
--

CREATE TABLE `configuracion` (
  `id` int(11) NOT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `configuracion`
--

INSERT INTO `configuracion` (`id`, `clave`, `valor`, `tipo`) VALUES
(20, 'factura', '3', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `descuentos`
--

CREATE TABLE `descuentos` (
  `id` int(11) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `descuento` float DEFAULT NULL,
  `fecha_inicio` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_fin` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles_pedido`
--

CREATE TABLE `detalles_pedido` (
  `id` int(11) NOT NULL,
  `id_pedido` int(11) DEFAULT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `precio_unidad` float DEFAULT NULL,
  `unidades` int(11) DEFAULT NULL,
  `impuesto` float DEFAULT NULL,
  `total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `detalles_pedido`
--

INSERT INTO `detalles_pedido` (`id`, `id_pedido`, `id_producto`, `precio_unidad`, `unidades`, `impuesto`, `total`) VALUES
(1, 1, 19, 10.99, 2, 21, 21.98),
(2, 2, 20, 28.67, 1, 21, 28.67),
(3, 5, 19, 10.99, 1, 21, 10.99),
(4, 6, 11, 9.99, 1, 21, 9.99),
(5, 7, 21, 14.95, 65, 21, 971.75),
(6, 9, 19, 10.99, 2, 21, 21.98),
(7, 9, 22, 33.35, 1, 21, 33.35),
(8, 11, 8, 16.69, 5, 21, 83.45),
(9, 12, 22, 33.35, 9, 21, 300.15000000000003),
(10, 13, 9, 27.24, 2, 21, 54.48),
(11, 14, 14, 34.99, 1, 21, 34.99),
(12, 15, 20, 28.67, 1, 21, 28.67),
(13, 16, 19, 10.99, 3, 21, 32.97),
(14, 16, 20, 28.67, 1, 21, 28.67),
(15, 17, 24, 11.95, 1, 21, 11.95),
(16, 18, 19, 10.99, 3, 21, 32.97),
(17, 18, 15, 9.69, 2, 21, 19.38),
(18, 18, 16, 9.69, 2, 21, 19.38),
(19, 19, 9, 27.24, 3, 21, 81.72),
(20, 20, 24, 11.95, 1, 21, 11.95),
(21, 21, 24, 11.95, 3, 21, 35.849999999999994);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `impuestos`
--

CREATE TABLE `impuestos` (
  `id` int(11) NOT NULL,
  `impuesto` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodos_pago`
--

CREATE TABLE `metodos_pago` (
  `id` int(11) NOT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opciones_menu`
--

CREATE TABLE `opciones_menu` (
  `id` int(11) NOT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `nombre_opcion` varchar(255) DEFAULT NULL,
  `url_opcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `opciones_menu`
--

INSERT INTO `opciones_menu` (`id`, `id_rol`, `nombre_opcion`, `url_opcion`) VALUES
(2, 1, 'Productos', '/productos'),
(3, 1, 'Pedidos', '/pedidos'),
(4, 1, 'Categorías', '/categorias'),
(5, 1, 'Empleados', '/empleados'),
(6, 1, 'Clientes', '/clientes'),
(7, 4, 'Productos', '/productos'),
(8, 4, 'Categorías', '/categorias'),
(9, 4, 'Empleados', '/empleados'),
(10, 4, 'Pedidos', '/pedidos'),
(11, 4, 'Clientes', '/clientes'),
(12, 4, 'Administradores', '/administradores'),
(13, 3, 'Pedidos', '/pedidos'),
(14, 3, 'Productos', '/productos'),
(15, 3, 'Clientes', '/clientes'),
(16, 3, 'Categorías', '/categorias');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp(),
  `metodo_pago` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `num_factura` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id`, `id_usuario`, `fecha`, `metodo_pago`, `estado`, `num_factura`, `total`) VALUES
(1, 1, '2024-04-18 18:20:42', 'Paypal', 'PC', 'FAC1', 21.98),
(2, 1, '2024-04-17 13:53:54', 'Paypal', 'PC', 'FAC2', 28.67),
(3, 1, '2024-04-17 17:09:13', 'Paypal', 'PC', 'FAC3', 0),
(4, 1, '2024-04-15 14:25:26', 'Paypal', 'PE', 'FAC4', 0),
(5, 1, '2024-04-15 14:28:12', 'Tarjeta', 'PE', 'FAC5', 10.99),
(6, 1, '2024-04-15 15:21:37', 'Tarjeta', 'PE', 'FAC6', 9.99),
(7, 1, '2024-04-15 17:07:33', 'Tarjeta', 'PE', 'FAC7', 971.75),
(8, 1, '2024-04-15 17:08:17', 'Tarjeta', 'PE', 'FAC8', 0),
(9, 1, '2024-04-15 17:13:47', 'Tarjeta', 'PE', 'FAC9', 55.33),
(10, 1, '2024-04-15 17:19:56', 'Tarjeta', 'PE', 'FAC10', 0),
(11, 1, '2024-04-15 18:14:54', 'Paypal', 'PE', 'FAC11', 83.45),
(12, 1, '2024-04-15 19:12:39', 'Paypal', 'PE', 'FAC12', 300.15000000000003),
(13, 1, '2024-04-16 19:21:45', 'Tarjeta', 'PE', 'FAC13', 54.48),
(14, 1, '2024-04-17 13:51:07', 'Paypal', 'PE', 'FAC14', 34.99),
(15, 1, '2024-04-17 15:08:39', 'Paypal', 'PE', 'FAC15', 28.67),
(16, 6, '2023-02-08 15:28:15', 'Tarjeta', 'PE', 'FAC16', 61.64),
(17, 6, '2024-04-18 15:49:55', 'Tarjeta', 'PC', 'FAC17', 11.95),
(18, 6, '2024-04-08 14:28:50', 'Paypal', 'PE', 'FAC18', 71.72999999999999),
(19, 6, '2024-04-18 18:58:14', 'Tarjeta', 'PE', 'FAC19', 81.72),
(20, 6, '2024-04-19 17:53:22', 'Paypal', 'PE', 'FAC1', 11.95),
(21, 6, '2024-04-19 17:53:47', 'Paypal', 'PE', 'FAC2', 35.849999999999994);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` int(11) NOT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `fecha_alta` timestamp NOT NULL DEFAULT current_timestamp(),
  `fecha_baja` timestamp NULL DEFAULT NULL,
  `impuesto` float DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `id_categoria`, `nombre`, `descripcion`, `precio`, `stock`, `fecha_alta`, `fecha_baja`, `impuesto`, `imagen`) VALUES
(8, 3, 'Jenga', '¡El juego clásico que consiste en crear una pila de bloques y derrumbarla! ¿Cómo apilar contra la ley de la gravedad? Apilad los bloques de madera en una torre firme, después retirad los bloques uno a uno por turnos hasta que la pila se venga abajo. ', 16.69, 0, '2024-04-16 14:14:28', NULL, 21, '8.jpg'),
(9, 5, 'Monopoly', 'Es un básico para las noches familiares de juegos Los jugadores compran, venden, sueñan y preparan su camino hacia las riquezas con el juego Monopoly', 27.24, 0, '2024-04-16 14:14:28', NULL, 21, '9.jpg'),
(11, 8, 'Sushi Go', '¿Te gusta el sushi? Sin duda es un elemento a tener en cuenta, pues si es así, disfrutarás de cada una de las opciones de este menú. Sushi Go! ', 9.99, 4, '2024-04-16 14:14:28', NULL, 21, '11.jpg'),
(12, 5, 'Tragabolas', '¿Qué hipopótamo será el más glotón en Tragabolas? Trata de moverte rápido cuando las bolas se suelten en el tablero de juego, ¡si tu hipopótamo es el que traga el mayor número de bolitas ganarás! Los hipopótamos están preparados para devorar bolitas.', 22.71, 5, '2024-04-16 14:14:28', NULL, 21, '12.jpg'),
(14, 6, 'Chuchelandia', 'Crea y saborea las golosinas más deliciosas. Ahora con más contenido y atractivas propuestas de juego. Incluye set de trabajo y todos los elementos necesarios para crear tus chuches preferidas', 34.99, 1, '2024-04-16 14:14:28', NULL, 21, '14.jpg'),
(15, 8, 'Uno', 'Durante más de 50 años, UNO ha conectado a personas de todo el mundo a través de juegos icónicos que trascienden la edad, el género y el idioma. Es fácil de aprender, fácil de jugar y fácil de disfrutar.', 9.69, 0, '2024-04-16 14:14:28', NULL, 21, '15.jpg'),
(16, 2, 'Catán', 'Sois los primeros colonos en llegar a la isla de Catan. Muy pronto empiezan a aparecer los primeros poblados y las primeras carreteras', 9.69, 0, '2024-04-16 14:14:28', NULL, 21, '16.jpg'),
(17, 5, 'Gestos', '¡Hacer payasadas nunca había sido tan divertido! Descubre Gestos, el divertido y rápido juego de mímica.Los jugadores de cada equipo tendrán que adivinar el máximo número de palabras cuando el reloj se ponga en marcha.', 20.99, 80, '2024-04-16 14:14:28', NULL, 21, '17.jpg'),
(18, 7, 'Cluedo', 'El solitario millonario Samuel Black ha sido asesinado en su mansión. Ahora, depende de ti resolver el caso. Haz preguntas sobre todo para aclarar el misterio y ser el ganador del CLUEDO.', 27.95, 80, '2024-04-16 14:14:28', NULL, 21, '18.jpg'),
(19, 4, 'Conecta 4', '¡Desafía a un amigo a divertiros dejando caer las fichas en este juego clásico de Conecta 4! Deja caer tus fichas rojas o amarillas en la parrilla y sé el primero en conseguir 4 fichas en línea para ganar.', 10.99, 69, '2024-04-16 14:14:28', NULL, 21, '19.jpg'),
(20, 4, 'Dixit', 'Dixit es uno de esos títulos que no puede faltar en ninguna colección de juegos de mesa que se precie por su originalidad, la sencillez de sus reglas y la ingente cantidad de horas de diversión.', 28.67, 3, '2024-04-16 14:14:28', NULL, 21, '20.jpg'),
(21, 7, 'La tripulación', '¡Se buscan astronautas! Los científicos afirman la existencia de un misterioso noveno planeta en los confines de nuestro sistema solar.', 14.95, 0, '2024-04-16 14:14:28', NULL, 21, '21.jpg'),
(22, 7, 'DOD', 'Un cooperativo para jugadores/as de a partir de 6 años con partidas de 10 minutos', 33.35, 55, '2024-04-16 14:14:28', NULL, 21, '22.jpg'),
(23, 5, 'Parchis', '¡Los niños merecen lo mejor, por eso te presentamos', 9.99, 2, '2024-04-16 14:14:28', NULL, 21, 'Parchis23.avif'),
(24, 8, 'Baraja Española', 'Baraja de cartas española de 50 cartas empaquetada en caja de cartón de alta calidad', 11.95, 94, '2024-04-18 06:50:55', NULL, 21, 'Baraja Española23.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `cif` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `rol` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `rol`) VALUES
(1, 'admin'),
(2, 'cliente'),
(3, 'empleado'),
(4, 'superAdmin');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `apellido1` varchar(255) DEFAULT NULL,
  `apellido2` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `provincia` varchar(255) DEFAULT NULL,
  `localidad` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `fecha_baja` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `id_rol`, `email`, `clave`, `nombre`, `apellido1`, `apellido2`, `direccion`, `provincia`, `localidad`, `telefono`, `dni`, `fecha_baja`) VALUES
(1, 1, 'admin@admin.com', 'tUdv/kRQMK5VBQp76IZ4Akkl+qSaeCERsMsETmlclWnUnFmB3uNEyAVEMyxkZYTV', 'Raul', 'Ferrero', 'Vicente', 'asfds', 'ry', 'asdf', '124234', '1234132432e', NULL),
(3, 3, 'empleado@empleado.com', 'tUdv/kRQMK5VBQp76IZ4Akkl+qSaeCERsMsETmlclWnUnFmB3uNEyAVEMyxkZYTV', 'Raul', 'Empleado', 'Empleado', 'Empleado', 'Empleado', 'Empleado', '607307943', '21234', NULL),
(6, 2, 'cliente@cliente.com', 'SLyJpFoEU8v0IpC7PKMqryCyANhzN8YdyoyYs/tY7bQj8VwYHT0ZRHdH+s4/NygW', 'Raul', 'fERRERO', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', '12345t', NULL),
(7, 4, 'superAdmin@superAdmin.com', 'HKwS96rY5P/YKXqRvybj4zrToOvpP1wlNbG2took0bqVjn0uNRnx8xus6Fqcu/Ka', 'Raul', 'Ferrero', 'Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', '12345D', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `valoraciones`
--

CREATE TABLE `valoraciones` (
  `id` int(11) NOT NULL,
  `id_producto` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `valoracion` int(11) DEFAULT NULL,
  `comentario` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categorias`
--
ALTER TABLE `categorias`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `descuentos`
--
ALTER TABLE `descuentos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_pedido` (`id_pedido`),
  ADD KEY `id_producto` (`id_producto`);

--
-- Indices de la tabla `impuestos`
--
ALTER TABLE `impuestos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `metodos_pago`
--
ALTER TABLE `metodos_pago`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `opciones_menu`
--
ALTER TABLE `opciones_menu`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_rol` (`id_rol`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_categoria` (`id_categoria`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_rol` (`id_rol`);

--
-- Indices de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_producto` (`id_producto`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categorias`
--
ALTER TABLE `categorias`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `descuentos`
--
ALTER TABLE `descuentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `impuestos`
--
ALTER TABLE `impuestos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `metodos_pago`
--
ALTER TABLE `metodos_pago`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `opciones_menu`
--
ALTER TABLE `opciones_menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  ADD CONSTRAINT `detalles_pedido_ibfk_1` FOREIGN KEY (`id_pedido`) REFERENCES `pedidos` (`id`),
  ADD CONSTRAINT `detalles_pedido_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`);

--
-- Filtros para la tabla `opciones_menu`
--
ALTER TABLE `opciones_menu`
  ADD CONSTRAINT `opciones_menu_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`);

--
-- Filtros para la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD CONSTRAINT `pedidos_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `categorias` (`id`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `roles` (`id`);

--
-- Filtros para la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  ADD CONSTRAINT `valoraciones_ibfk_1` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`),
  ADD CONSTRAINT `valoraciones_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
