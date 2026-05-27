--
-- Base de datos: `ad_ex_AG`
--


DROP DATABASE IF EXISTS `ad_ex_AG`;
CREATE DATABASE IF NOT EXISTS `ad_ex_AG`;
USE `ad_ex_AG`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `albumes`
--

CREATE TABLE `albumes` (
  `ID` int(11) NOT NULL auto_increment,
  `TITULO` varchar(30) NOT NULL,
  `AUTOR` int(30) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `AUTOR` (`AUTOR`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcar la base de datos para la tabla `albumes`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `grupos`
--

CREATE TABLE `grupos` (
  `id` int(11) NOT NULL auto_increment,
  `nombre` varchar(30) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcar la base de datos para la tabla `grupos`
--

INSERT INTO `grupos` (`id`, `nombre`) VALUES
(1, 'Mecano');

--
-- Filtros para la tabla `albumes`
--
ALTER TABLE `albumes`
  ADD CONSTRAINT `albumes_ibfk_1` FOREIGN KEY (`AUTOR`) REFERENCES `grupos` (`id`) ON DELETE CASCADE;