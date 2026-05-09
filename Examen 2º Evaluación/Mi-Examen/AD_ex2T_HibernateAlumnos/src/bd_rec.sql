--
-- Base de datos: `ad_ex_REC`
--


DROP DATABASE IF EXISTS `ad_ex_REC`;
CREATE DATABASE IF NOT EXISTS `ad_ex_REC`;
USE `ad_ex_REC`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subject`
--

CREATE TABLE `subject` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(30) NOT NULL,
  `degree` varchar(30) NOT NULL,
  `teacherId` int(30) default NULL,
  PRIMARY KEY  (`ID`),
  KEY `teacherId` (`teacherId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Volcar la base de datos para la tabla `subject`
--

INSERT INTO `subject` ( `id`, `name`, `degree`, `teacherId`) VALUES
(1, 'Acceso a Datos', 'DAM', 1),
(2, 'Desarrollo Entorno Cliente', 'DAW', 1),
(3, 'Desarrollo Entorno Servidor', 'DAM', 2),
(4, 'Programacion', 'DAM', 3);
 
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `id` int(11) NOT NULL auto_increment,
  `nombre` varchar(30) NOT NULL,
  `experiencia` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Volcar la base de datos para la tabla `profesores`
--

INSERT INTO `profesor` (`id`, `nombre`, `experiencia`) VALUES
(1, 'PACO', 4),
(2, 'MARTA', 14),
(3, 'MARIA', 7);

--
-- Filtros para la tabla `subject`
--
ALTER TABLE `subject`
  ADD CONSTRAINT `subject_ibfk_1` FOREIGN KEY (`teacherId`) REFERENCES `profesor` (`id`) ON DELETE CASCADE;