-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 05, 2017 at 09:51 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `universidad`
--

-- --------------------------------------------------------

--
-- Table structure for table `concepto_plazo`
--

CREATE TABLE `concepto_plazo` (
  `cplazo_id` int(11) NOT NULL COMMENT 'Representa el id del concepto del plazo',
  `cplazo_descripcion` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Representa la descripcion del plazo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena los conceptos de los plazos de las actividades';

--
-- Dumping data for table `concepto_plazo`
--

INSERT INTO `concepto_plazo` (`cplazo_id`, `cplazo_descripcion`) VALUES
(1, 'ENTREGA TRABAJO DE GRADO'),
(2, 'ENTREGA CORRECCION PROPUESTA'),
(3, 'ENTREGA CORRECCION TRABAJO');

-- --------------------------------------------------------

--
-- Table structure for table `estado_propuesta`
--

CREATE TABLE `estado_propuesta` (
  `ep_id` int(11) NOT NULL COMMENT 'Define el id de el concepto',
  `ep_descripcion` varchar(30) COLLATE utf8_bin NOT NULL COMMENT 'Es la descripcion relativa al concepto'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;

--
-- Dumping data for table `estado_propuesta`
--

INSERT INTO `estado_propuesta` (`ep_id`, `ep_descripcion`) VALUES
(1, 'PENDIENTE'),
(2, 'DEVUELTA PARA CORRECCIONES'),
(3, 'APROBADA'),
(4, 'NO ACEPTADA');

-- --------------------------------------------------------

--
-- Table structure for table `estado_trabajo_grado`
--

CREATE TABLE `estado_trabajo_grado` (
  `epg_id` int(11) NOT NULL COMMENT 'Representa el id de concepto de propuesta',
  `epg_descripcion` varchar(30) COLLATE utf8_bin NOT NULL COMMENT 'Representa la descripcion del concepto'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de los conceptos asignados a proyect' ROW_FORMAT=COMPACT;

--
-- Dumping data for table `estado_trabajo_grado`
--

INSERT INTO `estado_trabajo_grado` (`epg_id`, `epg_descripcion`) VALUES
(1, 'EN PROPUESTA'),
(2, 'PENDIENTE'),
(4, 'LAUREADA'),
(6, 'APROBADA'),
(7, 'DEVUELTA PARA CORRECCIONES');

-- --------------------------------------------------------

--
-- Table structure for table `estudiante`
--

CREATE TABLE `estudiante` (
  `estudiante_codigo` bigint(20) NOT NULL COMMENT 'Representa el codigo de un estudiante',
  `estudiante_persona` bigint(20) NOT NULL COMMENT 'Representa la identificacion de el estudiante',
  `estudiante_programa` varchar(2) COLLATE utf8_bin NOT NULL COMMENT 'Represneta el id del programa al que pertenece'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de un estudiante';

--
-- Dumping data for table `estudiante`
--

INSERT INTO `estudiante` (`estudiante_codigo`, `estudiante_persona`, `estudiante_programa`) VALUES
(45, 45, '35'),
(123, 132, '22'),
(222, 333, '22'),
(2312, 2312, '22'),
(3332, 4442, '22'),
(4444, 4444, '22'),
(44444, 22222, '22'),
(22222222, 111111111111, '23'),
(2220141003, 1110577732, '22'),
(2220141020, 1110563494, '22'),
(3520151002, 19456629, '35');

-- --------------------------------------------------------

--
-- Table structure for table `estudiante_propuesta`
--

CREATE TABLE `estudiante_propuesta` (
  `ep_estudiante` bigint(20) NOT NULL COMMENT 'Representa el estudiante que hace una propuesta',
  `ep_trabajo_propuesto` int(11) NOT NULL COMMENT 'Representa la propuesta que presenta'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de los estudiantes y sus propuestas';

--
-- Dumping data for table `estudiante_propuesta`
--

INSERT INTO `estudiante_propuesta` (`ep_estudiante`, `ep_trabajo_propuesto`) VALUES
(2220141003, 5),
(2220141003, 6),
(2220141020, 5);

-- --------------------------------------------------------

--
-- Table structure for table `jurado_trabajo_grado`
--

CREATE TABLE `jurado_trabajo_grado` (
  `jtg_trabajo_grado` int(11) NOT NULL COMMENT 'Representa el trabajo de grado al que es asignado el jurado',
  `jtg_persona` bigint(20) NOT NULL COMMENT 'Representa un jurado asignado'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Informacion de los jurados de los trabajos de grados';

-- --------------------------------------------------------

--
-- Table structure for table `modalidad_trabajo`
--

CREATE TABLE `modalidad_trabajo` (
  `modalidad_id` int(11) NOT NULL COMMENT 'Representa el id de una modalidad',
  `modalidad_descripcion` varchar(30) COLLATE utf8_bin NOT NULL COMMENT 'Representa la descripcion de una modalidad'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la  informacion de las modalidades' ROW_FORMAT=COMPACT;

--
-- Dumping data for table `modalidad_trabajo`
--

INSERT INTO `modalidad_trabajo` (`modalidad_id`, `modalidad_descripcion`) VALUES
(1, 'MONOGRAFÍA'),
(2, 'MONOGRAFÍA PAZ Y REGIÓN'),
(3, 'ASISTENCIA DE INVESTIGACIÓN'),
(4, 'OPCIÓN EMPRENDIMIENTO'),
(5, 'TRABAJO DE INVESTIGACIÓN');

-- --------------------------------------------------------

--
-- Table structure for table `multa`
--

CREATE TABLE `multa` (
  `id_multa` int(11) NOT NULL COMMENT 'Representa el id de la multa',
  `valor_multa` double NOT NULL COMMENT 'Representa el valor de la multa en cop'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de las multas';

--
-- Dumping data for table `multa`
--

INSERT INTO `multa` (`id_multa`, `valor_multa`) VALUES
(1, 5000);

-- --------------------------------------------------------

--
-- Table structure for table `observacion`
--

CREATE TABLE `observacion` (
  `opg_id` int(11) NOT NULL COMMENT 'Representa el id de la observacion',
  `opg_observacion` text COLLATE utf8_bin NOT NULL COMMENT 'Representa la observacion',
  `opg_trabajo` int(11) DEFAULT NULL COMMENT 'Representa el trabajo al que se le hace laobservacion',
  `opg_propuesta` int(11) DEFAULT NULL COMMENT 'Representa la propuesta a la que se le hace la observacion'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena las observaciones hechas a los proyectos de grado' ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table `persona`
--

CREATE TABLE `persona` (
  `persona_identificacion` bigint(20) NOT NULL COMMENT 'Representa el numero de identificacion de una persona',
  `persona_p_nombre` varchar(15) COLLATE utf8_bin NOT NULL COMMENT 'Representa el primer nombre de una persona',
  `persona_s_nombre` varchar(15) COLLATE utf8_bin DEFAULT NULL COMMENT 'Representa el segundo nombre de una persona',
  `persona_p_apellido` varchar(15) COLLATE utf8_bin NOT NULL COMMENT 'Representa el primer apellido de una persona',
  `persona_s_apellido` varchar(15) COLLATE utf8_bin NOT NULL COMMENT 'Representa el segundo apellido de una persona',
  `persona_email` varchar(40) COLLATE utf8_bin DEFAULT NULL COMMENT 'Representa el email de una persona',
  `persona_es_jurado` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'Define si la persona es jurado'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de una persona' ROW_FORMAT=COMPACT;

--
-- Dumping data for table `persona`
--

INSERT INTO `persona` (`persona_identificacion`, `persona_p_nombre`, `persona_s_nombre`, `persona_p_apellido`, `persona_s_apellido`, `persona_email`, `persona_es_jurado`) VALUES
(132, 'dd', '(NULL)', 'aaa', 'fff', 'alejo_ospina96@hotmail.com', 0),
(333, 'nombre', '(NULL)', 'ape1', 'ape2', 'alejo_ospina96@hotmail.com', 0),
(2312, 'aaa', '(NULL)', 'dddd', 'sss', 'alejo_ospina96@hotmail.com', 0),
(4442, 'we', 'asda', 'ada', 'sda', 'fw@hotmail.com', 0),
(4444, 'aa', 'sds', 'ewew', 'wewe', 'alejo_ospina96@hotmail.com', 0),
(22222, 'sss', '(NULL)', 'sfsf', 'sada', 'alejo_ospina96@hotmail.com', 0),
(19456629, 'HONORIO', 'ANTONIO', 'FLOREZ', 'HOYOS', 'HAFLOREZ23@HOTMAIL.COM', 1),
(36182958, 'NIYIME', NULL, 'OSPINA', 'CARTAGENA', 'NOC661@HOTMAIL.COM', 1),
(1110563494, 'CRISTIAN', 'HERLEY', 'GARZON', 'MORENO', 'CRISHEGAR1495@HOTMAIL.COM', 0),
(1110577732, 'DANIEL', 'ALEJANDRO', 'FLOREZ', 'OSPINA', 'ALEJO_OSPINA96@HOTMAIL.COM', 0);

-- --------------------------------------------------------

--
-- Table structure for table `plazo`
--

CREATE TABLE `plazo` (
  `plazo_id` int(11) NOT NULL COMMENT 'Representa el id del plazo',
  `plazo_fecha_inicio` date NOT NULL COMMENT 'Representa la fecha de inicio del plazo',
  `plazo_fecha_fin` date NOT NULL COMMENT 'Representa la fecha final del plzao',
  `plazo_concepto` int(11) NOT NULL COMMENT 'Representa el concepto del plazo',
  `plazo_multa` int(11) NOT NULL COMMENT 'Representa la multa acarreada al no cumplir el plazo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena los plazos dados para las diferentes actividades';

--
-- Dumping data for table `plazo`
--

INSERT INTO `plazo` (`plazo_id`, `plazo_fecha_inicio`, `plazo_fecha_fin`, `plazo_concepto`, `plazo_multa`) VALUES
(2, '2017-03-05', '2017-03-23', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `programa`
--

CREATE TABLE `programa` (
  `programa_codigo` varchar(2) COLLATE utf8_bin NOT NULL COMMENT 'Representa el codigo de un programa academico',
  `programa_abreviatura` varchar(5) COLLATE utf8_bin NOT NULL COMMENT 'Representa la abreviatura del programa academico',
  `programa_nombre` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Representa el nombre dado a un programa academico'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de un programa academico';

--
-- Dumping data for table `programa`
--

INSERT INTO `programa` (`programa_codigo`, `programa_abreviatura`, `programa_nombre`) VALUES
('22', 'ISIS', 'INGENIERÍA DE SISTEMAS'),
('23', 'ININ', 'INGENIERÍA INDUSTRIAL'),
('35', 'DISE', 'DISEÑO');

-- --------------------------------------------------------

--
-- Table structure for table `propuesta`
--

CREATE TABLE `propuesta` (
  `propuesta_trabajo` int(11) NOT NULL COMMENT 'Representa el trabajo de grado propuesto',
  `propuesta_fecha` date NOT NULL COMMENT 'Representa la fecha en que se realizo la propuesta',
  `propuesta_archivo` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT 'Representa la ruta del archivo que se carga',
  `propuesta_concepto_estado` int(11) NOT NULL DEFAULT '1' COMMENT 'Define si la propuesta se aprueba o no',
  `propuesta_plazo_correcciones` int(11) DEFAULT NULL COMMENT 'Representa el plazo para presentar correcciones'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Propuesta de trabajo de grado de un estudiante';

--
-- Dumping data for table `propuesta`
--

INSERT INTO `propuesta` (`propuesta_trabajo`, `propuesta_fecha`, `propuesta_archivo`, `propuesta_concepto_estado`, `propuesta_plazo_correcciones`) VALUES
(5, '2017-03-05', 'Administracion_14_ed_-_Harold_Koontz_Wei.pdf', 1, NULL),
(6, '2017-03-05', 'Caso de Estudio - Proyecto de Asignatura.pdf', 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `prorroga`
--

CREATE TABLE `prorroga` (
  `prorroga_id` int(11) NOT NULL COMMENT 'Representa el id de una prorroga',
  `prorroga_trabajo` int(11) NOT NULL COMMENT 'Representa el trabajo que se prorroga',
  `prorroga_solicitante` bigint(20) NOT NULL COMMENT 'Representa al estudiante solicitante de la prorroga',
  `prorroga_archivo` varchar(30) COLLATE utf8_bin NOT NULL COMMENT 'Representa el archivo por escrito de solicitud de prorroga',
  `prorroga_plazo_entrega` int(11) DEFAULT NULL COMMENT 'Representa el plazo de la prorroga en caso de ser aceptada'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de una prorroga';

-- --------------------------------------------------------

--
-- Table structure for table `trabajo_grado`
--

CREATE TABLE `trabajo_grado` (
  `tg_id` int(11) NOT NULL COMMENT 'Representa el id de un trabajo de grado',
  `tg_modalidad` int(11) NOT NULL COMMENT 'Representa la modalidad del trabajo de grado',
  `tg_tematica` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'Representa la tematica del trabajo de grado',
  `tg_concepto_estado` int(11) DEFAULT '1' COMMENT 'Representa el concepto del estado del trabajo de grado',
  `tg_archivo` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT 'Representa el archivo final de proyecto de grado',
  `fecha_defensa` date DEFAULT NULL COMMENT 'Representa la fecha de defensa del trabajo de grado'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Almacena la informacion de un trabajo de grado';

--
-- Dumping data for table `trabajo_grado`
--

INSERT INTO `trabajo_grado` (`tg_id`, `tg_modalidad`, `tg_tematica`, `tg_concepto_estado`, `tg_archivo`, `fecha_defensa`) VALUES
(5, 1, 'TEMATICA COOL', 2, 'bootstrap.min.css', NULL),
(6, 3, 'ASIST', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_propuestas`
-- (See below for the actual view)
--
CREATE TABLE `v_propuestas` (
`trabajo` int(11)
,`fecha` date
,`descripcion` varchar(30)
,`tematica` varchar(50)
,`estudiante` bigint(20)
,`estado` varchar(30)
,`plazo_correcciones` date
);

-- --------------------------------------------------------

--
-- Structure for view `v_propuestas`
--
DROP TABLE IF EXISTS `v_propuestas`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_propuestas`  AS  select `trabajo_grado`.`tg_id` AS `trabajo`,`propuesta`.`propuesta_fecha` AS `fecha`,`modalidad_trabajo`.`modalidad_descripcion` AS `descripcion`,`trabajo_grado`.`tg_tematica` AS `tematica`,`estudiante_propuesta`.`ep_estudiante` AS `estudiante`,`estado_propuesta`.`ep_descripcion` AS `estado`,`plazo`.`plazo_fecha_fin` AS `plazo_correcciones` from (((((`propuesta` join `trabajo_grado` on((`trabajo_grado`.`tg_id` = `propuesta`.`propuesta_trabajo`))) join `modalidad_trabajo` on((`trabajo_grado`.`tg_modalidad` = `modalidad_trabajo`.`modalidad_id`))) join `estudiante_propuesta` on((`estudiante_propuesta`.`ep_trabajo_propuesto` = `trabajo_grado`.`tg_id`))) join `estado_propuesta` on((`estado_propuesta`.`ep_id` = `propuesta`.`propuesta_concepto_estado`))) left join `plazo` on((`plazo`.`plazo_id` = `propuesta`.`propuesta_plazo_correcciones`))) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `concepto_plazo`
--
ALTER TABLE `concepto_plazo`
  ADD PRIMARY KEY (`cplazo_id`);

--
-- Indexes for table `estado_propuesta`
--
ALTER TABLE `estado_propuesta`
  ADD PRIMARY KEY (`ep_id`);

--
-- Indexes for table `estado_trabajo_grado`
--
ALTER TABLE `estado_trabajo_grado`
  ADD PRIMARY KEY (`epg_id`);

--
-- Indexes for table `estudiante`
--
ALTER TABLE `estudiante`
  ADD PRIMARY KEY (`estudiante_codigo`),
  ADD KEY `fk_persona` (`estudiante_persona`),
  ADD KEY `fk_programa` (`estudiante_programa`);

--
-- Indexes for table `estudiante_propuesta`
--
ALTER TABLE `estudiante_propuesta`
  ADD PRIMARY KEY (`ep_estudiante`,`ep_trabajo_propuesto`),
  ADD KEY `fk_ep_propuesta` (`ep_trabajo_propuesto`);

--
-- Indexes for table `jurado_trabajo_grado`
--
ALTER TABLE `jurado_trabajo_grado`
  ADD PRIMARY KEY (`jtg_trabajo_grado`,`jtg_persona`),
  ADD KEY `fk_jurado_persona` (`jtg_persona`);

--
-- Indexes for table `modalidad_trabajo`
--
ALTER TABLE `modalidad_trabajo`
  ADD PRIMARY KEY (`modalidad_id`);

--
-- Indexes for table `multa`
--
ALTER TABLE `multa`
  ADD PRIMARY KEY (`id_multa`);

--
-- Indexes for table `observacion`
--
ALTER TABLE `observacion`
  ADD PRIMARY KEY (`opg_id`),
  ADD KEY `fk_obs_trabajo_grado` (`opg_trabajo`),
  ADD KEY `fk_obs_propuesta` (`opg_propuesta`);

--
-- Indexes for table `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`persona_identificacion`);

--
-- Indexes for table `plazo`
--
ALTER TABLE `plazo`
  ADD PRIMARY KEY (`plazo_id`),
  ADD KEY `fk_concepto_plazo` (`plazo_concepto`),
  ADD KEY `fk_plazo_multa` (`plazo_multa`);

--
-- Indexes for table `programa`
--
ALTER TABLE `programa`
  ADD PRIMARY KEY (`programa_codigo`);

--
-- Indexes for table `propuesta`
--
ALTER TABLE `propuesta`
  ADD PRIMARY KEY (`propuesta_trabajo`),
  ADD UNIQUE KEY `propuesta_archivo` (`propuesta_archivo`),
  ADD KEY `fk_trabajo_grado` (`propuesta_trabajo`),
  ADD KEY `fk_concepto_propuesta` (`propuesta_concepto_estado`),
  ADD KEY `fk_plazo_correcciones` (`propuesta_plazo_correcciones`);

--
-- Indexes for table `prorroga`
--
ALTER TABLE `prorroga`
  ADD PRIMARY KEY (`prorroga_id`),
  ADD KEY `fk_prorroga_trabajo` (`prorroga_trabajo`),
  ADD KEY `fk_prorroga_solicitante` (`prorroga_solicitante`),
  ADD KEY `fk_prorroga_plazo` (`prorroga_plazo_entrega`);

--
-- Indexes for table `trabajo_grado`
--
ALTER TABLE `trabajo_grado`
  ADD PRIMARY KEY (`tg_id`),
  ADD KEY `fk_modalidad` (`tg_modalidad`),
  ADD KEY `fk_concepto_trabajo` (`tg_concepto_estado`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `concepto_plazo`
--
ALTER TABLE `concepto_plazo`
  MODIFY `cplazo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id del concepto del plazo', AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `estado_propuesta`
--
ALTER TABLE `estado_propuesta`
  MODIFY `ep_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Define el id de el concepto', AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `estado_trabajo_grado`
--
ALTER TABLE `estado_trabajo_grado`
  MODIFY `epg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id de concepto de propuesta', AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `modalidad_trabajo`
--
ALTER TABLE `modalidad_trabajo`
  MODIFY `modalidad_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id de una modalidad', AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `multa`
--
ALTER TABLE `multa`
  MODIFY `id_multa` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id de la multa', AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `observacion`
--
ALTER TABLE `observacion`
  MODIFY `opg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id de la observacion';
--
-- AUTO_INCREMENT for table `plazo`
--
ALTER TABLE `plazo`
  MODIFY `plazo_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id del plazo', AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `prorroga`
--
ALTER TABLE `prorroga`
  MODIFY `prorroga_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id de una prorroga';
--
-- AUTO_INCREMENT for table `trabajo_grado`
--
ALTER TABLE `trabajo_grado`
  MODIFY `tg_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Representa el id de un trabajo de grado', AUTO_INCREMENT=7;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `estudiante_propuesta`
--
ALTER TABLE `estudiante_propuesta`
  ADD CONSTRAINT `fk_ep_estudiante` FOREIGN KEY (`ep_estudiante`) REFERENCES `estudiante` (`estudiante_codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_ep_propuesta` FOREIGN KEY (`ep_trabajo_propuesto`) REFERENCES `trabajo_grado` (`tg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `jurado_trabajo_grado`
--
ALTER TABLE `jurado_trabajo_grado`
  ADD CONSTRAINT `fk_jfg_trabajo` FOREIGN KEY (`jtg_trabajo_grado`) REFERENCES `trabajo_grado` (`tg_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_jurado_persona` FOREIGN KEY (`jtg_persona`) REFERENCES `persona` (`persona_identificacion`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `observacion`
--
ALTER TABLE `observacion`
  ADD CONSTRAINT `fk_obs_propuesta` FOREIGN KEY (`opg_propuesta`) REFERENCES `propuesta` (`propuesta_trabajo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_obs_trabajo_grado` FOREIGN KEY (`opg_trabajo`) REFERENCES `trabajo_grado` (`tg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `plazo`
--
ALTER TABLE `plazo`
  ADD CONSTRAINT `fk_plazo_concepto` FOREIGN KEY (`plazo_concepto`) REFERENCES `concepto_plazo` (`cplazo_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_plazo_multa` FOREIGN KEY (`plazo_multa`) REFERENCES `multa` (`id_multa`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `propuesta`
--
ALTER TABLE `propuesta`
  ADD CONSTRAINT `fk_plazo_correcciones` FOREIGN KEY (`propuesta_plazo_correcciones`) REFERENCES `plazo` (`plazo_id`),
  ADD CONSTRAINT `fk_propuesta_concepto` FOREIGN KEY (`propuesta_concepto_estado`) REFERENCES `estado_propuesta` (`ep_id`),
  ADD CONSTRAINT `fk_propuesta_trabajo` FOREIGN KEY (`propuesta_trabajo`) REFERENCES `trabajo_grado` (`tg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `prorroga`
--
ALTER TABLE `prorroga`
  ADD CONSTRAINT `fk_prorroga_plazo` FOREIGN KEY (`prorroga_plazo_entrega`) REFERENCES `plazo` (`plazo_id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_prorroga_solicitante` FOREIGN KEY (`prorroga_solicitante`) REFERENCES `estudiante` (`estudiante_codigo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_prorroga_trabajo` FOREIGN KEY (`prorroga_trabajo`) REFERENCES `trabajo_grado` (`tg_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `trabajo_grado`
--
ALTER TABLE `trabajo_grado`
  ADD CONSTRAINT `fk_concepto_trabajo` FOREIGN KEY (`tg_concepto_estado`) REFERENCES `estado_trabajo_grado` (`epg_id`),
  ADD CONSTRAINT `fk_modalidad` FOREIGN KEY (`tg_modalidad`) REFERENCES `modalidad_trabajo` (`modalidad_id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
