-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-01-2017 a las 19:43:34
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 7.0.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ADAT_baskethiber`
--

DROP DATABASE IF EXISTS ADAT_baskethiber;
CREATE DATABASE IF NOT EXISTS ADAT_baskethiber;
USE ADAT_baskethiber;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `player`
--

CREATE TABLE `player` (
  `playerId` int(11) NOT NULL,
  `playerName` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `playerNumber` int(11) DEFAULT NULL,
  `idTeamFK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `playerposition`
--

CREATE TABLE `playerposition` (
  `ID` int(11) NOT NULL,
  `player` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `NUM` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `position`
--

CREATE TABLE `position` (
  `ID` int(11) NOT NULL,
  `text` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `DESCRIPTION` varchar(60) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `team`
--

CREATE TABLE `team` (
  `idTeam` int(11) NOT NULL,
  `Name` varchar(30) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Ã�ndices para tablas volcadas
--

--
-- Indices de la tabla `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`playerId`),
  ADD KEY `idTeamFK` (`idTeamFK`);

--
-- Indices de la tabla `playerposition`
--
ALTER TABLE `playerposition`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `player` (`player`),
  ADD KEY `position` (`position`);

--
-- Indices de la tabla `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`ID`);

--
-- Indices de la tabla `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`idTeam`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `player`
--
ALTER TABLE `player`
  MODIFY `playerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `playerposition`
--
ALTER TABLE `playerposition`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de la tabla `position`
--
ALTER TABLE `position`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `team`
--
ALTER TABLE `team`
  MODIFY `idTeam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `player_ibfk_1` FOREIGN KEY (`idTeamFK`) REFERENCES `team` (`idTeam`);

--
-- Filtros para la tabla `playerposition`
--
ALTER TABLE `playerposition`
  ADD CONSTRAINT `playerposition_ibfk_1` FOREIGN KEY (`player`) REFERENCES `player` (`playerId`),
  ADD CONSTRAINT `playerposition_ibfk_2` FOREIGN KEY (`position`) REFERENCES `position` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
