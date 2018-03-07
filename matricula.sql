# Host: localhost  (Version 5.5.58)
# Date: 2017-10-20 10:56:44
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "aluno"
#

DROP TABLE IF EXISTS `aluno`;
CREATE TABLE `aluno` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `sexo` char(1) NOT NULL,
  `idade` int(11) NOT NULL,
  `ativo` char(1) NOT NULL,
  `cidade` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "aluno"
#


#
# Structure for table "cidade"
#

DROP TABLE IF EXISTS `cidade`;
CREATE TABLE `cidade` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `uf` char(2) NOT NULL,
  `ativo` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "cidade"
#


#
# Structure for table "curso"
#

DROP TABLE IF EXISTS `curso`;
CREATE TABLE `curso` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `ativo` char(1) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "curso"
#


#
# Structure for table "matricula"
#

DROP TABLE IF EXISTS `matricula`;
CREATE TABLE `matricula` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `id_aluno` int(11) DEFAULT NULL,
  `id_curso` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

#
# Data for table "matricula"
#

