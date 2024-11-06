-- phpMyAdmin SQL Dump
-- version 4.9.1deb2
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Xerado por ventin.cdm@gmail.com
-- Versión do servidor: 8.0.20-0ubuntu0.19.10.1
-- Versión do PHP: 7.3.11-0ubuntu0.19.10.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `clinica`
--

-- --------------------------------------------------------

--
-- Estrutura da táboa `citasperrucaria`
--

CREATE TABLE `citasperrucaria` (
  `codCita` int NOT NULL,
  `chip` varchar(20) NOT NULL,
  `data` date NOT NULL,
  `hora` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `citasperrucaria`
--
ALTER TABLE `citasperrucaria`
  ADD PRIMARY KEY (`codCita`),
  ADD KEY `EE` (`chip`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `citasperrucaria`
--
ALTER TABLE `citasperrucaria`
  MODIFY `codCita` int NOT NULL AUTO_INCREMENT;

--
-- Restricións para os envorcados das táboas
--

--
-- Restricións para a táboa `citasperrucaria`
--
ALTER TABLE `citasperrucaria`
  ADD CONSTRAINT `EE` FOREIGN KEY (`chip`) REFERENCES `cans` (`chip`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;