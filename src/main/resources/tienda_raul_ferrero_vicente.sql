-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 03-05-2024 a las 01:07:52
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

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
(20, 'factura', '15', ''),
(21, 'nombreTienda', 'Mesa Para 2', NULL),
(22, 'cifTienda', '11971683E', NULL),
(23, 'direccionTienda', 'Avenida Valladolid 3, B2, P1, 2ºD', NULL),
(24, 'cpTienda', '49029', NULL),
(25, 'poblacionTienda', 'Zamora', NULL),
(26, 'tlfTienda', '607307943', NULL);

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

--
-- Volcado de datos para la tabla `descuentos`
--

INSERT INTO `descuentos` (`id`, `codigo`, `descuento`, `fecha_inicio`, `fecha_fin`) VALUES
(2, 'Bienvenida', 10.5, '2024-04-26 19:58:50', '2024-06-29 19:58:55'),
(3, 'ABRIL', 5, '2024-03-31 22:04:57', '2024-04-29 22:05:07');

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
(24, 24, 22, 33.35, 1, 21, 33.35),
(25, 25, 20, 28.67, 3, 21, 86.01),
(26, 25, 24, 11.95, 6, 21, 71.69999999999999),
(27, 25, 14, 34.99, 1, 21, 34.99),
(28, 26, 20, 28.67, 1, 21, 28.67),
(29, 26, 24, 11.95, 2, 21, 23.9),
(30, 27, 24, 11.95, 1, 21, 11.95),
(31, 28, 23, 9.99, 1, 21, 9.99),
(32, 29, 25, 94.95, 1, 21, 94.95),
(33, 30, 17, 20.99, 1, 21, 20.99),
(34, 30, 8, 16.69, 1, 21, 16.69),
(35, 30, 26, 33.75, 1, 21, 33.75),
(36, 31, 25, 94.95, 1, 21, 94.95),
(37, 32, 20, 28.67, 1, 21, 28.67),
(38, 33, 8, 16.69, 3, 21, 50.07000000000001),
(39, 33, 11, 9.99, 1, 21, 9.99),
(40, 34, 25, 94.95, 1, 21, 94.95),
(41, 35, 25, 94.95, 1, 21, 94.95),
(42, 36, 25, 94.95, 1, 21, 94.95),
(43, 37, 24, 11.95, 1, 21, 11.95),
(44, 37, 25, 94.95, 3, 21, 284.85),
(45, 37, 26, 33.75, 1, 21, 33.75),
(48, 39, 22, 33.35, 1, 21, 33.35),
(49, 39, 26, 33.75, 1, 21, 33.75),
(51, 40, 20, 28.67, 1, 21, 28.67),
(52, 40, 8, 16.69, 2, 21, 33.38),
(53, 40, 11, 9.99, 1, 21, 9.99),
(54, 41, 23, 9.99, 1, 21, 9.99),
(55, 41, 8, 16.69, 1, 21, 16.69),
(56, 43, 26, 33.75, 1, 21, 33.75);

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
(16, 3, 'Categorías', '/categorias'),
(17, 1, 'Cupones', '/cupones'),
(18, 4, 'Cupones', '/cupones'),
(19, 1, 'Proveedores', '/proveedores'),
(20, 4, 'Proveedores', '/proveedores'),
(21, 3, 'Proveedores', '/proveedores'),
(22, 1, 'Configuracion', '/configuracion'),
(23, 4, 'Configuracion', '/configuracion');

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
(24, 6, '2023-12-27 15:25:00', 'Tarjeta', 'E', 'FAC10', 33.35),
(25, 6, '2023-12-30 15:25:31', 'Paypal', 'PC', 'En trámite', 192.7),
(26, 9, '2024-01-26 19:37:58', 'Tarjeta', 'E', 'FAC7', 52.57),
(27, 9, '2024-01-26 19:40:04', 'Paypal', 'E', 'FAC8', 11.95),
(28, 9, '2024-02-26 19:47:10', 'Tarjeta', 'PC', 'FAC9', 9.99),
(29, 8, '2024-02-28 00:52:58', 'Paypal', 'PE', 'En trámite', 94.95),
(30, 8, '2024-03-28 00:54:52', 'Tarjeta', 'PE', 'En trámite', 71.43),
(31, 9, '2024-03-28 10:38:58', 'Tarjeta', 'E', 'FAC11', 94.95),
(32, 6, '2024-03-28 14:01:55', 'Paypal', 'E', 'FAC13', 28.67),
(33, 8, '2024-04-28 15:26:58', 'Tarjeta', 'C', 'En trámite', 60.06000000000001),
(34, 6, '2024-04-28 21:45:40', 'Tarjeta', 'PC', 'En trámite', 94.95),
(35, 6, '2024-04-28 21:46:54', 'Paypal', 'E', 'FAC14', 94.95),
(36, 6, '2024-04-28 21:47:41', 'Tarjeta', 'PE', 'En trámite', 84.45),
(37, 8, '2024-04-29 10:21:25', 'Tarjeta', 'E', 'FAC12', 320.05),
(39, 10, '2024-04-30 22:00:00', 'Tarjeta', 'PC', 'En trámite', 67.1),
(40, 6, '2024-05-01 17:21:40', 'Paypal', 'PE', 'En trámite', 61.540000000000006),
(41, 8, '2024-05-01 21:05:23', 'Tarjeta', 'PE', 'En trámite', 26.68),
(43, 8, '2024-05-01 21:10:15', 'Tarjeta', 'PE', 'En trámite', 33.75);

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
(8, 3, 'Jenga', '¡El juego clásico que consiste en crear una pila de bloques y derrumbarla! ¿Cómo apilar contra la ley de la gravedad? Apilad los bloques de madera en una torre firme, después retirad los bloques uno a uno por turnos hasta que la pila se venga abajo. ', 16.69, 196, '2024-04-16 14:14:28', NULL, 21, 'Jenga8.jpg'),
(9, 5, 'Monopoly', 'Es un básico para las noches familiares de juegos Los jugadores compran, venden, sueñan y preparan su camino hacia las riquezas con el juego Monopoly', 27.24, 0, '2024-04-16 14:14:28', NULL, 21, 'Monopoly9.jpg'),
(11, 8, 'Sushi Go', '¿Te gusta el sushi? Sin duda es un elemento a tener en cuenta, pues si es así, disfrutarás de cada una de las opciones de este menú. Sushi Go! ', 9.99, 2, '2024-04-16 14:14:28', NULL, 21, 'Sushi Go11.jpg'),
(12, 5, 'Tragabolas', '¿Qué hipopótamo será el más glotón en Tragabolas? Trata de moverte rápido cuando las bolas se suelten en el tablero de juego, ¡si tu hipopótamo es el que traga el mayor número de bolitas ganarás! Los hipopótamos están preparados para devorar bolitas.', 22.71, 5, '2024-04-16 14:14:28', NULL, 21, 'Tragabolas12.jpg'),
(14, 6, 'Chuchelandia', 'Crea y saborea las golosinas más deliciosas. Ahora con más contenido y atractivas propuestas de juego. Incluye set de trabajo y todos los elementos necesarios para crear tus chuches preferidas', 34.99, 0, '2024-04-16 14:14:28', NULL, 21, 'Chuchelandia14.jpg'),
(15, 8, 'Uno', 'Durante más de 50 años, UNO ha conectado a personas de todo el mundo a través de juegos icónicos que trascienden la edad, el género y el idioma. Es fácil de aprender, fácil de jugar y fácil de disfrutar.', 9.69, 1, '2024-04-16 14:14:28', NULL, 21, 'Uno15.jpg'),
(16, 2, 'Catán', 'Sois los primeros colonos en llegar a la isla de Catan. Muy pronto empiezan a aparecer los primeros poblados y las primeras carreteras', 9.69, 0, '2024-04-16 14:14:28', NULL, 21, 'Catán16.jpg'),
(17, 5, 'Gestos', '¡Hacer payasadas nunca había sido tan divertido! Descubre Gestos, el divertido y rápido juego de mímica.Los jugadores de cada equipo tendrán que adivinar el máximo número de palabras cuando el reloj se ponga en marcha.', 20.99, 79, '2024-04-16 14:14:28', NULL, 21, 'Gestos17.jpg'),
(18, 7, 'Cluedo', 'El solitario millonario Samuel Black ha sido asesinado en su mansión. Ahora, depende de ti resolver el caso. Haz preguntas sobre todo para aclarar el misterio y ser el ganador del CLUEDO.', 27.95, 80, '2024-04-16 14:14:28', NULL, 21, 'Cluedo18.jpg'),
(19, 4, 'Conecta 4', '¡Desafía a un amigo a divertiros dejando caer las fichas en este juego clásico de Conecta 4! Deja caer tus fichas rojas o amarillas en la parrilla y sé el primero en conseguir 4 fichas en línea para ganar.', 10.99, 69, '2024-04-16 14:14:28', NULL, 21, 'Conecta 419.jpg'),
(20, 4, 'Dixit', 'Dixit es uno de esos títulos que no puede faltar en ninguna colección de juegos de mesa que se precie por su originalidad, la sencillez de sus reglas y la ingente cantidad de horas de diversión.', 28.67, 7, '2024-04-16 14:14:28', NULL, 21, 'Dixit20.jpg'),
(21, 7, 'La tripulación', '¡Se buscan astronautas! Los científicos afirman la existencia de un misterioso noveno planeta en los confines de nuestro sistema solar.', 14.95, 0, '2024-04-16 14:14:28', NULL, 21, 'La tripulación21.jpg'),
(22, 7, 'DOD', 'Un cooperativo para jugadores/as de a partir de 6 años con partidas de 10 minutos', 33.35, 53, '2024-04-16 14:14:28', NULL, 21, 'DOD22.jpg'),
(23, 5, 'Parchis', '¡Los niños merecen lo mejor, por eso te presentamos', 9.99, 0, '2024-04-16 14:14:28', NULL, 21, 'Parchis23.avif'),
(24, 8, 'Baraja Española', 'Baraja de cartas española de 50 cartas empaquetada en caja de cartón de alta calidad', 11.95, 82, '2024-04-18 06:50:55', NULL, 21, 'Baraja Española24.jpg'),
(25, 2, 'HeroQuest', 'El juego de mesa HeroQuest de Avalon Hill es un juego de fantasía y rol en el que los jugadores deben cooperar en la lucha contra Zargon y sus monstruos, completar misiones y saquear mazmorras.', 94.95, 41, '2024-04-27 23:44:00', NULL, 21, 'HeroQuest25.jpg'),
(26, 1, 'Stratego', 'Juego de mesa clásico de ataque y defensa cuyo objetivo consiste en apoderarse y conquistar la bandera enemiga con un ejército de 40 piezas de diferentes rangos', 33.75, 16, '2024-04-27 23:49:39', NULL, 21, 'Stratego26.jpg');

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

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`id`, `nombre`, `direccion`, `localidad`, `provincia`, `telefono`, `cif`, `email`) VALUES
(2, 'ROLDAN JUGUETES, S.L.', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', '', 'roldanjh@hotmail.com');

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
(1, 1, 'admin@admin.com', 'gpn3LFcXVudSyUK/YyWCgLDEp02pxNpCRD1rKoetMnUu+lTJaQGwqtjCliWTUftO', 'Raul Administrador', 'Ferrero', 'Vicente', 'calle larga', 'Salamanca', 'asdf', '123123123', '12312312A', NULL),
(3, 3, 'empleado@empleado.com', 'j2+WWOYTj1PflVOrMlffyChPmXsiNYbziwb8tGH/CTs98VYnX3SUPZ7whQxJiuqi', 'Raul Empleado', 'Empleado', 'Ap', 'Empleado', 'Valladolid', 'Tordesillas', '607307943', '21234765P', NULL),
(6, 2, 'cliente@cliente.com', 'YsJqMnSKXshDjYlkFPRgJzr+JgmJCQr1kImaRHNAlMUSoxrl0qy+w48E2PN8zNBd', 'Raul Cliente', 'Ferrero', 'Vicente', 'Avd. Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Tábara', '607307943', '11878787y', NULL),
(7, 4, 'superAdmin@superAdmin.com', 'HKwS96rY5P/YKXqRvybj4zrToOvpP1wlNbG2took0bqVjn0uNRnx8xus6Fqcu/Ka', 'Raul', 'Ferrero', 'Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Alcañices', '607307943', '76576576o', NULL),
(8, 2, 'cliente2@cliente2.com', 'tQ9+nOa58ETJ+9JAGE5+JqDJJ2UtlhUzpVH9IJm8L1x54nVZTxEQI60INwewblR4', 'Cliente2', 'Ape1', 'Ape2', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', '11972683e', NULL),
(9, 2, 'raul_fv@hotmail.com', '8xv2UY7ZLzj7OuD0fMZwMbPk7IA/3FdytpqVDPQQQuprGfwtfkqiHqXlOIZw58U8', 'Raul', 'Ferrero', 'Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Zamora', '607307943', '11971683e', NULL),
(10, 2, 'cliente3@cliente3.com', 'Ghlxoq5RFaIORQWETP50f6KnMc8gC40oYXDuh+nUNoK3rW3B/c+CQ6l/3RORGjY+', 'Cliente3', 'Ape1', 'Ferrero Vicente', 'Avd/ Valladolid Nº3, Bloque 2, Portal 1, 2º-D', 'Zamora', 'Tabara', '607307943', '12345678O', NULL);

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
-- Volcado de datos para la tabla `valoraciones`
--

INSERT INTO `valoraciones` (`id`, `id_producto`, `id_usuario`, `valoracion`, `comentario`) VALUES
(1, 24, 6, 5, 'Me gusta'),
(2, 24, 8, 4, 'Muy buena'),
(3, 11, 6, 2, 'No es tan bueno.'),
(4, 20, 6, 3, 'No esta mal'),
(5, 25, 8, 5, 'Juego indispensable para tener en tu colección!');

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `configuracion`
--
ALTER TABLE `configuracion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `descuentos`
--
ALTER TABLE `descuentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `detalles_pedido`
--
ALTER TABLE `detalles_pedido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `valoraciones`
--
ALTER TABLE `valoraciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

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
