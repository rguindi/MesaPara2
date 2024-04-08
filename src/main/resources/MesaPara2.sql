-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 08-04-2024 a las 22:04:19
-- Versión del servidor: 10.4.10-MariaDB
-- Versión de PHP: 7.1.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `MesaPara2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

CREATE TABLE `categorias` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `impuestos`
--

CREATE TABLE `impuestos` (
  `id` int(11) NOT NULL,
  `impuesto` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `metodos_pago`
--

CREATE TABLE `metodos_pago` (
  `id` int(11) NOT NULL,
  `metodo_pago` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `opciones_menu`
--

CREATE TABLE `opciones_menu` (
  `id` int(11) NOT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `nombre_opcion` varchar(255) DEFAULT NULL,
  `url_opcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `metodo_pago` varchar(255) DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `num_factura` varchar(255) DEFAULT NULL,
  `total` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  `fecha_alta` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `fecha_baja` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `impuesto` float DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `id_categoria`, `nombre`, `descripcion`, `precio`, `stock`, `fecha_alta`, `fecha_baja`, `impuesto`, `imagen`) VALUES
(8, 3, 'Jenga', '¡El juego clásico que consiste en crear una pila de bloques y derrumbarla! ¿Cómo apilar contra la ley de la gravedad? Apilad los bloques de madera en una torre firme, después retirad los bloques uno a uno por turnos hasta que la pila se venga abajo. ', 16.69, 5, '2024-04-05 21:49:44', '2024-04-05 21:49:44', 21, 'http://localhost:8080/files/8.jpg'),
(9, 5, 'Monopoly', 'Es un básico para las noches familiares de juegos Los jugadores compran, venden, sueñan y preparan su camino hacia las riquezas con el juego Monopoly', 27.24, 5, '2024-04-05 21:51:20', '2024-04-05 21:51:20', 21, 'http://localhost:8080/files/9.jpg'),
(11, 8, 'Sushi Go', '¿Te gusta el sushi? Sin duda es un elemento a tener en cuenta, pues si es así, disfrutarás de cada una de las opciones de este menú. Sushi Go! ', 9.99, 5, '2024-04-05 21:53:34', '2024-04-05 21:53:34', 21, 'http://localhost:8080/files/11.jpg'),
(12, 5, 'Tragabolas', '¿Qué hipopótamo será el más glotón en Tragabolas? Trata de moverte rápido cuando las bolas se suelten en el tablero de juego, ¡si tu hipopótamo es el que traga el mayor número de bolitas ganarás! Los hipopótamos están preparados para devorar bolitas.', 22.71, 5, '2024-04-05 21:54:51', '2024-04-05 21:54:51', 21, 'http://localhost:8080/files/12.jpg'),
(14, 6, 'Chuchelandia', 'Crea y saborea las golosinas más deliciosas. Ahora con más contenido y atractivas propuestas de juego. Incluye set de trabajo y todos los elementos necesarios para crear tus chuches preferidas', 34.99, 2, '2024-04-06 09:01:35', '2024-04-06 09:01:35', 21, 'http://localhost:8080/files/14.jpg'),
(15, 8, 'Uno', 'Durante más de 50 años, UNO ha conectado a personas de todo el mundo a través de juegos icónicos que trascienden la edad, el género y el idioma. Es fácil de aprender, fácil de jugar y fácil de disfrutar.', 9.69, 2, '2024-04-06 09:02:45', '2024-04-06 09:02:45', 21, 'http://localhost:8080/files/15.jpg'),
(16, 2, 'Catán', 'Sois los primeros colonos en llegar a la isla de Catan. Muy pronto empiezan a aparecer los primeros poblados y las primeras carreteras', 9.69, 2, '2024-04-06 09:04:20', '2024-04-06 09:03:30', 21, 'http://localhost:8080/files/16.jpg'),
(17, 5, 'Gestos', '¡Hacer payasadas nunca había sido tan divertido! Descubre Gestos, el divertido y rápido juego de mímica.Los jugadores de cada equipo tendrán que adivinar el máximo número de palabras cuando el reloj se ponga en marcha.', 20.99, 80, '2024-04-06 09:04:58', '2024-04-06 09:04:58', 21, 'http://localhost:8080/files/17.jpg'),
(18, 7, 'Cluedo', 'El solitario millonario Samuel Black ha sido asesinado en su mansión. Ahora, depende de ti resolver el caso. Haz preguntas sobre todo para aclarar el misterio y ser el ganador del CLUEDO.', 27.95, 80, '2024-04-06 09:05:39', '2024-04-06 09:05:39', 21, 'http://localhost:8080/files/18.jpg'),
(19, 4, 'Conecta 4', '¡Desafía a un amigo a divertiros dejando caer las fichas en este juego clásico de Conecta 4! Deja caer tus fichas rojas o amarillas en la parrilla y sé el primero en conseguir 4 fichas en línea para ganar.', 10.99, 80, '2024-04-06 09:07:15', '2024-04-06 09:07:15', 21, 'http://localhost:8080/files/19.jpg'),
(20, 4, 'Dixit', 'Dixit es uno de esos títulos que no puede faltar en ninguna colección de juegos de mesa que se precie por su originalidad, la sencillez de sus reglas y la ingente cantidad de horas de diversión.', 28.67, 6, '2024-04-08 18:06:55', '2024-04-08 18:05:49', 21, 'http://localhost:8080/files/20.jpg'),
(21, 7, 'La tripulación', '¡Se buscan astronautas! Los científicos afirman la existencia de un misterioso noveno planeta en los confines de nuestro sistema solar.', 14.95, 65, '2024-04-08 18:08:52', '2024-04-08 18:08:52', 21, 'http://localhost:8080/files/21.jpg'),
(22, 7, 'DOD', 'Un cooperativo para jugadores/as de a partir de 6 años con partidas de 10 minutos', 33.35, 65, '2024-04-08 18:10:31', '2024-04-08 18:10:31', 21, 'http://localhost:8080/files/22.jpg');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `rol` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `rol`) VALUES
(1, 'user');

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
  `dni` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `id_rol`, `email`, `clave`, `nombre`, `apellido1`, `apellido2`, `direccion`, `provincia`, `localidad`, `telefono`, `dni`) VALUES
(15, 1, 'raul_fv@hotmail.com', 'i', 'Raul', 'oiu', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', ''),
(17, 1, 'p', 'uiop', 'Raul', 'iop', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', ''),
(18, 1, 'fghj', 'fghj', '', 'fghj', 'fghj', 'fghj', 'fghj', '', '', ''),
(19, 1, 'sd@e34', 'sdc', '', 'sdf', 'sdf', 'sdf', 'sdf', '', '', '2345'),
(20, 1, 'raul_fv@hotmail.comy', '123', 'Raul', '123', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', '123'),
(21, 1, 'raul_fv@hotmail.lcom', 'l', 'Raul', 'g', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', 'h'),
(22, 1, 'raul_fvv@hotmail.com', 'mb', 'Raul', 'b', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', 'b');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `descuentos`
--
ALTER TABLE `descuentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

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
