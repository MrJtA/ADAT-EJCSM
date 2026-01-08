--
-- Base de datos: `ad_ex_PD`
--


DROP DATABASE IF EXISTS `ad_ex_PD`;
CREATE DATABASE IF NOT EXISTS `ad_ex_PD`;
USE `ad_ex_PD`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peliculas`
--

CREATE TABLE `peliculas` (
  `ID` int(11) NOT NULL auto_increment,
  `TITULO` varchar(30) NOT NULL,
  `DIRECTOR` int(30) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `DIRECTOR` (`DIRECTOR`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcar la base de datos para la tabla `peliculas`
--

INSERT INTO `peliculas` (`id`, `titulo`, `director`) VALUES
(1, 'El buen Patron', 1);
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `directores`
--

CREATE TABLE `directores` (
  `id` int(11) NOT NULL auto_increment,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `directores`
--

INSERT INTO `directores` (`id`, `nombre`) VALUES
(1, 'Fernando León'),
(2, 'Pedro Almodovar'),
(3, 'Steven Spielberg');

--
-- Filtros para la tabla `peliculas`
--
ALTER TABLE `peliculas`
  ADD CONSTRAINT `peliculas_ibfk_1` FOREIGN KEY (`DIRECTOR`) REFERENCES `directores` (`id`) ON DELETE CASCADE;