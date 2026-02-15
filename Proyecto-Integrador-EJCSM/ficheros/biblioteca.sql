-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generacion: 09-03-2022 a las 12:19:44
-- Version del servidor: 10.4.11-MariaDB
-- Version de PHP: 7.4.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE DATABASE IF NOT EXISTS `biblioteca`;
USE `biblioteca`;

DROP TABLE IF EXISTS `libro`;
CREATE TABLE libro (
    isbn INT PRIMARY KEY,
    titulo VARCHAR(100),
    autor VARCHAR(100),
    editorial VARCHAR(100),
    genero VARCHAR(100)
);

INSERT INTO libro (isbn, titulo, autor, editorial, genero) VALUES
(16, 'Leviatán', 'Thomas Hobbes', 'Fondo de Cultura Económica', 'Filosofía Política'),
(17, 'La riqueza de las naciones', 'Adam Smith', 'Alianza', 'Economía'),
(18, 'La canción de Aquiles', 'Madeline Miller', 'AdN Alianza', 'Novela Histórica'),
(19, 'El origen de las especies', 'Charles Darwin', 'Austral', 'Biología/Ciencia'),
(20, 'La Santa Biblia', 'Anónimo', 'Sociedades Bíblicas', 'Religión'),
(21, 'Fenomenología del espíritu', 'Georg Wilhelm Friedrich Hegel', 'Fondo de Cultura Económica', 'Filosofía'),
(22, 'Crítica a la razón pura', 'Immanuel Kant', 'Taurus', 'Filosofía'),
(23, 'El extranjero', 'Albert Camus', 'Alianza', 'Novela'),
(24, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'RAE', 'Novela'),
(25, 'Comentarios reales', 'Inca Garcilaso de la Vega', 'Cátedra', 'Historia');