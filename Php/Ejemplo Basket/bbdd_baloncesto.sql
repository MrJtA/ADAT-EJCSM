-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 26, 2017 at 06:56 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `JSON_Baloncesto`
--


DROP DATABASE IF EXISTS JSON_Baloncesto;
CREATE DATABASE IF NOT EXISTS JSON_Baloncesto;
USE JSON_Baloncesto;


-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `playerId` int(11) NOT NULL,
  `playerName` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `playerNumber` int(11) DEFAULT NULL,
  `idTeamFK` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`playerId`, `playerName`, `playerNumber`, `idTeamFK`) VALUES
(1, 'Paco', 5, 1),
(2, 'Juan', 6, 1),
(3, 'Ana', 7, 1),
(4, 'Luis', 8, 1),
(5, 'Lola', 9, 1);

-- --------------------------------------------------------

--
-- Table structure for table `playerposition`
--

CREATE TABLE `playerposition` (
  `ID` int(11) NOT NULL,
  `player` int(11) NOT NULL,
  `position` int(11) NOT NULL,
  `NUM` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `playerposition`
--

INSERT INTO `playerposition` (`ID`, `player`, `position`, `NUM`) VALUES
(1, 1, 1, 2),
(2, 1, 2, 3),
(3, 2, 2, 1),
(4, 4, 4, 4),
(5, 5, 5, 4),
(6, 3, 3, 2),
(7, 3, 4, 2);

-- --------------------------------------------------------

--
-- Table structure for table `position`
--

CREATE TABLE `position` (
  `ID` int(11) NOT NULL,
  `text` varchar(30) COLLATE utf8_spanish_ci NOT NULL,
  `DESCRIPTION` varchar(60) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `position`
--

INSERT INTO `position` (`ID`, `text`, `DESCRIPTION`) VALUES
(1, 'Pivot', 'Taponar'),
(2, 'Ala-Pivot', 'Rebotes y puntos'),
(3, 'Alero', 'De todo un poco'),
(4, 'Escolta', 'Tirador'),
(5, 'Base', 'Dirigir');

-- --------------------------------------------------------

--
-- Table structure for table `team`
--

CREATE TABLE `team` (
  `idTeam` int(11) NOT NULL,
  `Name` varchar(30) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Dumping data for table `team`
--

INSERT INTO `team` (`idTeam`, `Name`) VALUES
(1, 'ORANGE TEAM'),
(2, 'DREAM TEAM');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`playerId`),
  ADD KEY `idTeamFK` (`idTeamFK`);

--
-- Indexes for table `playerposition`
--
ALTER TABLE `playerposition`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `player` (`player`),
  ADD KEY `position` (`position`);

--
-- Indexes for table `position`
--
ALTER TABLE `position`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`idTeam`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `playerId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `playerposition`
--
ALTER TABLE `playerposition`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `position`
--
ALTER TABLE `position`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `team`
--
ALTER TABLE `team`
  MODIFY `idTeam` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `player_ibfk_1` FOREIGN KEY (`idTeamFK`) REFERENCES `team` (`idTeam`);

--
-- Constraints for table `playerposition`
--
ALTER TABLE `playerposition`
  ADD CONSTRAINT `playerposition_ibfk_1` FOREIGN KEY (`player`) REFERENCES `player` (`playerId`),
  ADD CONSTRAINT `playerposition_ibfk_2` FOREIGN KEY (`position`) REFERENCES `position` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
