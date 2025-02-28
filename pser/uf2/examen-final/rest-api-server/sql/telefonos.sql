-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-02-2025 a las 14:27:15
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
-- Base de datos: `telefonos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operadores`
--

CREATE TABLE `operadores` (
  `codOperador` int(11) NOT NULL,
  `nombre` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `operadores`
--

INSERT INTO `operadores` (`codOperador`, `nombre`) VALUES
(1, 'Movistar'),
(2, 'Vodafone'),
(3, 'R'),
(4, 'Yoigo'),
(5, 'Jazztel'),
(6, 'Orange'),
(7, 'Simyo'),
(8, 'CDM');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telefonos`
--

CREATE TABLE `telefonos` (
  `telefono` varchar(9) NOT NULL,
  `codOperador` int(11) NOT NULL,
  `titular` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `telefonos`
--

INSERT INTO `telefonos` (`telefono`, `codOperador`, `titular`) VALUES
('123', 1, 'Borja'),
('123123', 2, 'prueba'),
('600000000', 7, 'pepe'),
('600123456', 1, 'luisa'),
('608456087', 1, 'pepita'),
('610203040', 1, 'luisa'),
('611111111', 7, 'martin'),
('612345630', 1, 'jaime'),
('619847563', 1, 'pepe'),
('632423432', 2, 'rosa'),
('634233234', 3, 'maria'),
('634234323', 3, 'mario'),
('634325465', 4, 'david'),
('638947323', 2, 'ana'),
('642344323', 6, 'ignacio'),
('643234324', 3, 'teresa'),
('643334554', 6, 'carlos'),
('643345345', 5, 'abdul'),
('643345432', 5, 'amelia'),
('643425345', 6, 'pepe'),
('643435345', 5, 'magda'),
('643456455', 4, 'isa'),
('643534543', 2, 'salvador'),
('643543435', 2, 'ignacio'),
('643543544', 4, 'marga'),
('645656424', 4, 'david'),
('649857621', 1, 'jacinto');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `traspasos`
--

CREATE TABLE `traspasos` (
  `id` int(11) NOT NULL,
  `telefono` int(11) NOT NULL,
  `viejaOperadora` int(11) NOT NULL,
  `nuevaOperadora` int(11) NOT NULL,
  `motivo` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Volcado de datos para la tabla `traspasos`
--

INSERT INTO `traspasos` (`id`, `telefono`, `viejaOperadora`, `nuevaOperadora`, `motivo`) VALUES
(2, 600000000, 7, 1, ' p r u e b a '),
(3, 600000000, 1, 7, ' p r u e b a - - 2 '),
(4, 123123, 1, 2, 'segundo');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `operadores`
--
ALTER TABLE `operadores`
  ADD PRIMARY KEY (`codOperador`);

--
-- Indices de la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD PRIMARY KEY (`telefono`),
  ADD KEY `codOperador` (`codOperador`);

--
-- Indices de la tabla `traspasos`
--
ALTER TABLE `traspasos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `operadores`
--
ALTER TABLE `operadores`
  MODIFY `codOperador` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `traspasos`
--
ALTER TABLE `traspasos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `telefonos`
--
ALTER TABLE `telefonos`
  ADD CONSTRAINT `telefonos_ibfk_1` FOREIGN KEY (`codOperador`) REFERENCES `operadores` (`codOperador`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
