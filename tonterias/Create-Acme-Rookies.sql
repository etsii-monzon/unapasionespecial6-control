start transaction;
-- MySQL dump 10.13  Distrib 5.5.29, for Win64 (x86)
--
-- Host: localhost    Database: Acme-Rookies
-- ------------------------------------------------------
-- Server version	5.5.29

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

drop database if exists `Acme-Rookies`;
create database `Acme-Rookies`;

drop user 'acme-user'@'%';

drop user 'acme-manager'@'%';

create user 'acme-user'@'%' 
	identified by password '*4F10007AADA9EE3DBB2CC36575DFC6F4FDE27577';

create user 'acme-manager'@'%' 
	identified by password '*FDB8CD304EB2317D10C95D797A4BD7492560F55F';

grant select, insert, update, delete 
	on `Acme-Rookies`.* to 'acme-user'@'%';

grant select, insert, update, delete, create, drop, references, index, alter, 
        create temporary tables, lock tables, create view, create routine, 
        alter routine, execute, trigger, show view
    on `Acme-Rookies`.* to 'acme-manager'@'%';
--
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `optional_address` varchar(255) DEFAULT NULL,
  `optional_photo` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_b2e562x98pje1n9vu0deulcev` (`credit_card`),
  KEY `FK_7ohwsa2usmvu0yxb44je2lge` (`user_account`),
  CONSTRAINT `FK_7ohwsa2usmvu0yxb44je2lge` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_b2e562x98pje1n9vu0deulcev` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES (247,0,'super@hotmail.com','Juan','','','+34 675359045','Calcuta',21,248,235);
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `application`
--

DROP TABLE IF EXISTS `application`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `application` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `explanations` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `moment_sumbit` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  `problem` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cqpb54jon3yjef814oj6g6o4n` (`position`),
  KEY `FK_7gn6fojv7rim6rxyglc6n9mt2` (`problem`),
  CONSTRAINT `FK_7gn6fojv7rim6rxyglc6n9mt2` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_cqpb54jon3yjef814oj6g6o4n` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `application`
--

LOCK TABLES `application` WRITE;
/*!40000 ALTER TABLE `application` DISABLE KEYS */;
INSERT INTO `application` VALUES (284,0,NULL,NULL,'2019-12-01 17:30:00',NULL,'PENDING',270,274),(285,0,'this works because I passed DP','https:/www.github.com/sabiore/appfort.git','2020-10-02 12:10:00','2020-12-02 10:00:00','SUBMITTED',271,277);
/*!40000 ALTER TABLE `application` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit`
--

DROP TABLE IF EXISTS `audit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `draft_mode` bit(1) DEFAULT NULL,
  `moment` datetime DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bumsxo4hc038y25pbfsinc38u` (`position`),
  CONSTRAINT `FK_bumsxo4hc038y25pbfsinc38u` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit`
--

LOCK TABLES `audit` WRITE;
/*!40000 ALTER TABLE `audit` DISABLE KEYS */;
INSERT INTO `audit` VALUES (287,4,'\0','2019-08-01 00:00:00',5,'Recuperación datos',270),(288,0,'\0','2019-08-01 00:00:00',5,'TEST',270),(291,0,'\0','2019-08-01 00:00:00',5,'TEST',271);
/*!40000 ALTER TABLE `audit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audit_quolets`
--

DROP TABLE IF EXISTS `audit_quolets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audit_quolets` (
  `audit` int(11) NOT NULL,
  `quolets` int(11) NOT NULL,
  UNIQUE KEY `UK_nfimx0hhlnlfjpah4yhbk50y4` (`quolets`),
  KEY `FK_r4rw380xsmcgcwt69v0h0qpov` (`audit`),
  CONSTRAINT `FK_nfimx0hhlnlfjpah4yhbk50y4` FOREIGN KEY (`quolets`) REFERENCES `quolet` (`id`),
  CONSTRAINT `FK_r4rw380xsmcgcwt69v0h0qpov` FOREIGN KEY (`audit`) REFERENCES `audit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audit_quolets`
--

LOCK TABLES `audit_quolets` WRITE;
/*!40000 ALTER TABLE `audit_quolets` DISABLE KEYS */;
INSERT INTO `audit_quolets` VALUES (287,32768),(287,65536),(288,289),(288,290),(291,292),(291,293);
/*!40000 ALTER TABLE `audit_quolets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor`
--

DROP TABLE IF EXISTS `auditor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `optional_address` varchar(255) DEFAULT NULL,
  `optional_photo` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_k5yihs4tvrnhe8rndei6ypc8w` (`credit_card`),
  KEY `FK_1hfceldjralkadedlv9lg1tl8` (`user_account`),
  CONSTRAINT `FK_1hfceldjralkadedlv9lg1tl8` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_k5yihs4tvrnhe8rndei6ypc8w` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor`
--

LOCK TABLES `auditor` WRITE;
/*!40000 ALTER TABLE `auditor` DISABLE KEYS */;
INSERT INTO `auditor` VALUES (255,0,'sergiodp@hotmail.com','Sergio','','','+34 675320557','del Pino',21,256,239),(257,0,'juan@hotmail.com','Juan','','','+34 746320557','Sastre',21,258,242),(259,0,'juan@hotmail.com','Juan Antonio','','','+34 746320557','Lopez',21,258,240);
/*!40000 ALTER TABLE `auditor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auditor_audits`
--

DROP TABLE IF EXISTS `auditor_audits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `auditor_audits` (
  `auditor` int(11) NOT NULL,
  `audits` int(11) NOT NULL,
  UNIQUE KEY `UK_ld7rrqg8j8q64tvpy15gbba6p` (`audits`),
  KEY `FK_bmqgrk24lut7s90cohn81e2qa` (`auditor`),
  CONSTRAINT `FK_bmqgrk24lut7s90cohn81e2qa` FOREIGN KEY (`auditor`) REFERENCES `auditor` (`id`),
  CONSTRAINT `FK_ld7rrqg8j8q64tvpy15gbba6p` FOREIGN KEY (`audits`) REFERENCES `audit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auditor_audits`
--

LOCK TABLES `auditor_audits` WRITE;
/*!40000 ALTER TABLE `auditor_audits` DISABLE KEYS */;
INSERT INTO `auditor_audits` VALUES (255,287),(257,288),(259,291);
/*!40000 ALTER TABLE `auditor_audits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `optional_address` varchar(255) DEFAULT NULL,
  `optional_photo` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `commercial_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_4askypslrvhwn9noojdiojojc` (`commercial_name`),
  KEY `FK_cp2qc9fdm9995xdhrrd06n86c` (`credit_card`),
  KEY `FK_pno7oguspp7fxv0y2twgplt0s` (`user_account`),
  CONSTRAINT `FK_cp2qc9fdm9995xdhrrd06n86c` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`),
  CONSTRAINT `FK_pno7oguspp7fxv0y2twgplt0s` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (278,0,'super@hotmail.com','Juan','','','+34 675359045','Calcuta',21,279,236,'jola'),(280,0,'company2@hotmail.com','Pedro','','','+34 660254788','Sanchez',21,281,238,'COMPA2');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_positions`
--

DROP TABLE IF EXISTS `company_positions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_positions` (
  `company` int(11) NOT NULL,
  `positions` int(11) NOT NULL,
  UNIQUE KEY `UK_4it3dja2ufiuy0g1g00egpp5l` (`positions`),
  KEY `FK_n1l7q0sqjt97laonivln5f4dc` (`company`),
  CONSTRAINT `FK_4it3dja2ufiuy0g1g00egpp5l` FOREIGN KEY (`positions`) REFERENCES `position` (`id`),
  CONSTRAINT `FK_n1l7q0sqjt97laonivln5f4dc` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_positions`
--

LOCK TABLES `company_positions` WRITE;
/*!40000 ALTER TABLE `company_positions` DISABLE KEYS */;
INSERT INTO `company_positions` VALUES (278,270),(280,271);
/*!40000 ALTER TABLE `company_positions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_problems`
--

DROP TABLE IF EXISTS `company_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_problems` (
  `company` int(11) NOT NULL,
  `problems` int(11) NOT NULL,
  UNIQUE KEY `UK_cx23ims7aocp0oslwgshkjjoy` (`problems`),
  KEY `FK_hv9w5vv3nbjmr3rvhrrobr5dj` (`company`),
  CONSTRAINT `FK_cx23ims7aocp0oslwgshkjjoy` FOREIGN KEY (`problems`) REFERENCES `problem` (`id`),
  CONSTRAINT `FK_hv9w5vv3nbjmr3rvhrrobr5dj` FOREIGN KEY (`company`) REFERENCES `company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_problems`
--

LOCK TABLES `company_problems` WRITE;
/*!40000 ALTER TABLE `company_problems` DISABLE KEYS */;
INSERT INTO `company_problems` VALUES (278,274),(278,275),(278,276),(280,277);
/*!40000 ALTER TABLE `company_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_quolets`
--

DROP TABLE IF EXISTS `company_quolets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_quolets` (
  `company` int(11) NOT NULL,
  `quolets` int(11) NOT NULL,
  UNIQUE KEY `UK_fyltmwijsv5vfsxp9hiw3c16f` (`quolets`),
  KEY `FK_fx1wcuqlgop0ejfmitu1nsof5` (`company`),
  CONSTRAINT `FK_fx1wcuqlgop0ejfmitu1nsof5` FOREIGN KEY (`company`) REFERENCES `company` (`id`),
  CONSTRAINT `FK_fyltmwijsv5vfsxp9hiw3c16f` FOREIGN KEY (`quolets`) REFERENCES `quolet` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_quolets`
--

LOCK TABLES `company_quolets` WRITE;
/*!40000 ALTER TABLE `company_quolets` DISABLE KEYS */;
INSERT INTO `company_quolets` VALUES (278,289),(278,290),(280,292),(280,293);
/*!40000 ALTER TABLE `company_quolets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `bannerurl` varchar(255) DEFAULT NULL,
  `country_code` varchar(255) DEFAULT NULL,
  `re_brand` bit(1) NOT NULL,
  `sistem_name` varchar(255) DEFAULT NULL,
  `welcomeen` varchar(255) DEFAULT NULL,
  `welcomesp` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configuration`
--

LOCK TABLES `configuration` WRITE;
/*!40000 ALTER TABLE `configuration` DISABLE KEYS */;
INSERT INTO `configuration` VALUES (286,0,'https://i.imgur.com/yTJ0Yoo.png','34','\0','Acme Rookie Rank','Welcome to Acme Rookie! We\'re IT hacker\'s favourite job marketplace!','¡Bienvenidos a Acme Rookie! ¡Somos el mercado de trabajo favorito de los profesionales de las TICs!');
/*!40000 ALTER TABLE `configuration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `credit_card`
--

DROP TABLE IF EXISTS `credit_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `credit_card` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `brand_name` varchar(255) DEFAULT NULL,
  `cvv` int(11) NOT NULL,
  `exp_month` int(11) NOT NULL,
  `exp_year` int(11) NOT NULL,
  `holder_name` varchar(255) DEFAULT NULL,
  `number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `credit_card`
--

LOCK TABLES `credit_card` WRITE;
/*!40000 ALTER TABLE `credit_card` DISABLE KEYS */;
INSERT INTO `credit_card` VALUES (248,0,'brand1',123,1,21,'Calcuta','4378363422128908'),(256,0,'brand4',457,1,22,'Sergio','5279341674209565'),(258,0,'brand4',231,11,24,'Juan','5343097500672309'),(261,0,'brand4',741,5,26,'Miguel','5577780643262156'),(263,0,'brand4',231,11,24,'Juan','5343097500672309'),(265,0,'brand4',231,11,24,'Juan','5343097500672309'),(267,0,'brand4',231,11,24,'Juan','5343097500672309'),(269,0,'brand4',231,11,24,'Juan','5343097500672309'),(279,0,'brand1',123,1,21,'Calcuta','4193667723509399'),(281,0,'brand4',580,8,23,'Pedro','5456824916609101'),(283,0,'brand1',123,1,21,'Calcuta','4704962467776789');
/*!40000 ALTER TABLE `credit_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hacker`
--

DROP TABLE IF EXISTS `hacker`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hacker` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `optional_address` varchar(255) DEFAULT NULL,
  `optional_photo` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tb3fnr0u8r6bad61eco2pldkq` (`credit_card`),
  KEY `FK_pechhr6iex4b7l2rymmx1xi38` (`user_account`),
  CONSTRAINT `FK_pechhr6iex4b7l2rymmx1xi38` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FK_tb3fnr0u8r6bad61eco2pldkq` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hacker`
--

LOCK TABLES `hacker` WRITE;
/*!40000 ALTER TABLE `hacker` DISABLE KEYS */;
INSERT INTO `hacker` VALUES (282,0,'super@hotmail.com','Juan','','','+34 675359045','Calcuta',21,283,237);
/*!40000 ALTER TABLE `hacker` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hacker_applications`
--

DROP TABLE IF EXISTS `hacker_applications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hacker_applications` (
  `hacker` int(11) NOT NULL,
  `applications` int(11) NOT NULL,
  UNIQUE KEY `UK_7kd9ixt1el7348djky832t91` (`applications`),
  KEY `FK_qkk2jfc62jss5hkyr86msq193` (`hacker`),
  CONSTRAINT `FK_7kd9ixt1el7348djky832t91` FOREIGN KEY (`applications`) REFERENCES `application` (`id`),
  CONSTRAINT `FK_qkk2jfc62jss5hkyr86msq193` FOREIGN KEY (`hacker`) REFERENCES `hacker` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hacker_applications`
--

LOCK TABLES `hacker_applications` WRITE;
/*!40000 ALTER TABLE `hacker_applications` DISABLE KEYS */;
INSERT INTO `hacker_applications` VALUES (282,284),(282,285);
/*!40000 ALTER TABLE `hacker_applications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequences`
--

DROP TABLE IF EXISTS `hibernate_sequences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequences`
--

LOCK TABLES `hibernate_sequences` WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO `hibernate_sequences` VALUES ('domain_entity',3);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (249,0,'descItem','https://item.com','itemName'),(250,0,'descItem','https://item2.com','itemaso'),(251,0,'descItem','https://3tem.com','meti'),(252,0,'descItem','https://3tem.com','item4'),(253,0,'descItem','https://3tem.com','example'),(254,0,'descItem','https://3tem.com','ejemplo');
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item_optional_pictures`
--

DROP TABLE IF EXISTS `item_optional_pictures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_optional_pictures` (
  `item` int(11) NOT NULL,
  `optional_pictures` varchar(255) DEFAULT NULL,
  KEY `FK_93j80o7fp0l5ikfsxxyw2ig0l` (`item`),
  CONSTRAINT `FK_93j80o7fp0l5ikfsxxyw2ig0l` FOREIGN KEY (`item`) REFERENCES `item` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item_optional_pictures`
--

LOCK TABLES `item_optional_pictures` WRITE;
/*!40000 ALTER TABLE `item_optional_pictures` DISABLE KEYS */;
INSERT INTO `item_optional_pictures` VALUES (249,'https://photo.com'),(250,'https://photo.com'),(251,'https://photo.com'),(252,'https://photo.com'),(253,'https://photo.com'),(254,'https://photo.com');
/*!40000 ALTER TABLE `item_optional_pictures` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position`
--

DROP TABLE IF EXISTS `position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `deadline` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `draft_mode` bit(1) NOT NULL,
  `profile` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `ticker` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `UK_grfnlov0nv1fop4dlrsv6ihhg` (`title`,`description`,`profile`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position`
--

LOCK TABLES `position` WRITE;
/*!40000 ALTER TABLE `position` DISABLE KEYS */;
INSERT INTO `position` VALUES (270,0,'2019-08-01 00:00:00','Entrar en sistema w10','\0','Graduado Ingeniería Informática',1250,'jola-2310','Vulnerabilidad Windows'),(271,0,'2019-08-01 00:00:00','Entrar en sistema Ubuntu','\0','Graduado Ingeniería Informática',1000,'COMPA-2310','Vulnerabilidad Linux'),(272,0,'2019-11-01 00:00:00','Abrir pc','','Graduado Ingeniería Informática',750,'jola-2111','Ssd error'),(273,0,'2019-12-01 00:00:00','Entrar en us.es','','Graduado Ingeniería Informática',1800,'jola-1210','SQL injection en us.es');
/*!40000 ALTER TABLE `position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_skills`
--

DROP TABLE IF EXISTS `position_skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_skills` (
  `position` int(11) NOT NULL,
  `skills` varchar(255) DEFAULT NULL,
  KEY `FK_iksb6ri4m9ktp19nm3n0iqq9k` (`position`),
  CONSTRAINT `FK_iksb6ri4m9ktp19nm3n0iqq9k` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_skills`
--

LOCK TABLES `position_skills` WRITE;
/*!40000 ALTER TABLE `position_skills` DISABLE KEYS */;
INSERT INTO `position_skills` VALUES (270,'Programación'),(271,'Programación'),(272,'Hardware'),(273,'Programación');
/*!40000 ALTER TABLE `position_skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `position_technologies`
--

DROP TABLE IF EXISTS `position_technologies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `position_technologies` (
  `position` int(11) NOT NULL,
  `technologies` varchar(255) DEFAULT NULL,
  KEY `FK_gap9mgajhx1f17j61fkxaagvy` (`position`),
  CONSTRAINT `FK_gap9mgajhx1f17j61fkxaagvy` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `position_technologies`
--

LOCK TABLES `position_technologies` WRITE;
/*!40000 ALTER TABLE `position_technologies` DISABLE KEYS */;
INSERT INTO `position_technologies` VALUES (270,'Java, Python'),(271,'Java, Python'),(272,'Ordenadores Asus'),(273,'SQL');
/*!40000 ALTER TABLE `position_technologies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem`
--

DROP TABLE IF EXISTS `problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `draft_mode` bit(1) DEFAULT NULL,
  `optional_hint` varchar(255) DEFAULT NULL,
  `statement` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `position` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ehy58i7iq25u9d829b76s1891` (`position`),
  CONSTRAINT `FK_ehy58i7iq25u9d829b76s1891` FOREIGN KEY (`position`) REFERENCES `position` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem`
--

LOCK TABLES `problem` WRITE;
/*!40000 ALTER TABLE `problem` DISABLE KEYS */;
INSERT INTO `problem` VALUES (274,0,'\0','hint1','statemen1','Error inicio',270),(275,0,'\0','','statement2','Error injection',270),(276,0,'\0','hint3','statment3','Error URL',270),(277,0,'\0','hint4','statement4','Devil error',271);
/*!40000 ALTER TABLE `problem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `problem_attachments`
--

DROP TABLE IF EXISTS `problem_attachments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_attachments` (
  `problem` int(11) NOT NULL,
  `attachments` varchar(255) DEFAULT NULL,
  KEY `FK_mhrgqo77annlexxubmtv4qvf7` (`problem`),
  CONSTRAINT `FK_mhrgqo77annlexxubmtv4qvf7` FOREIGN KEY (`problem`) REFERENCES `problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `problem_attachments`
--

LOCK TABLES `problem_attachments` WRITE;
/*!40000 ALTER TABLE `problem_attachments` DISABLE KEYS */;
INSERT INTO `problem_attachments` VALUES (274,'attachments1'),(275,'attachment2'),(276,'attachment3, attachment3.1'),(277,'attachment4');
/*!40000 ALTER TABLE `problem_attachments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider`
--

DROP TABLE IF EXISTS `provider`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `optional_address` varchar(255) DEFAULT NULL,
  `optional_photo` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `vat` double DEFAULT NULL,
  `credit_card` int(11) DEFAULT NULL,
  `user_account` int(11) DEFAULT NULL,
  `make` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_8tfs0v3dygkxkfyijig9gv9mj` (`credit_card`),
  KEY `FK_pj40m1p8m3lcs2fkdl1n3f3lq` (`user_account`),
  CONSTRAINT `FK_8tfs0v3dygkxkfyijig9gv9mj` FOREIGN KEY (`credit_card`) REFERENCES `credit_card` (`id`),
  CONSTRAINT `FK_pj40m1p8m3lcs2fkdl1n3f3lq` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider`
--

LOCK TABLES `provider` WRITE;
/*!40000 ALTER TABLE `provider` DISABLE KEYS */;
INSERT INTO `provider` VALUES (260,0,'miguefc@hotmail.com','Miguel','','','+34 612520557','Fuentes',21,261,241,'make1'),(262,0,'sergiofc@hotmail.com','Sergio','','','+34 622558557','Del Pino',21,263,243,'make1'),(264,0,'cmamo@hotmail.com','Manuel','','','+34 633420577','Fuentes',21,265,244,'make1'),(266,0,'miguerodri@hotmail.com','Miguel','','','+34 712520557','Rodriguez',21,267,245,'make1'),(268,0,'marirodri@hotmail.com','Maria','','','+34 712130557','Rodriguez',11,269,246,'make1');
/*!40000 ALTER TABLE `provider` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `provider_items`
--

DROP TABLE IF EXISTS `provider_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `provider_items` (
  `provider` int(11) NOT NULL,
  `items` int(11) NOT NULL,
  UNIQUE KEY `UK_2yq3kc6jhoc2plugiei2ox2br` (`items`),
  KEY `FK_pamty62h3b3k1j2583lurdd72` (`provider`),
  CONSTRAINT `FK_2yq3kc6jhoc2plugiei2ox2br` FOREIGN KEY (`items`) REFERENCES `item` (`id`),
  CONSTRAINT `FK_pamty62h3b3k1j2583lurdd72` FOREIGN KEY (`provider`) REFERENCES `provider` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `provider_items`
--

LOCK TABLES `provider_items` WRITE;
/*!40000 ALTER TABLE `provider_items` DISABLE KEYS */;
INSERT INTO `provider_items` VALUES (260,249),(262,250),(262,251),(264,252),(266,253),(268,254);
/*!40000 ALTER TABLE `provider_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quolet`
--

DROP TABLE IF EXISTS `quolet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quolet` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `body` varchar(255) DEFAULT NULL,
  `draft_mode` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `optional_picture` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `audit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_4e5mw905m6ogrd5cmtxnq423y` (`name`),
  KEY `FK_6rgw0elqmvimomjaxlqu5ncoj` (`audit`),
  CONSTRAINT `FK_6rgw0elqmvimomjaxlqu5ncoj` FOREIGN KEY (`audit`) REFERENCES `audit` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quolet`
--

LOCK TABLES `quolet` WRITE;
/*!40000 ALTER TABLE `quolet` DISABLE KEYS */;
INSERT INTO `quolet` VALUES (289,0,'TEST','\0','190615#ACW','http://www.photo.com','TROME',288),(290,0,'TEST','\0','190515#VXN','http://www.photo.com','GORK',288),(292,0,'TEST','\0','180515#ACR','http://www.photo.com','STIM',291),(293,0,'TEST','\0','190515-BTP','http://www.photo.com','ZURK',291),(32768,0,'klmklmkl','\0','190913#TX8','','TROME',287),(65536,0,'nlnlknlkn','\0','190913#ON3','','GORK',287);
/*!40000 ALTER TABLE `quolet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `notify` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_castjbvpeeus0r8lbpehiu0e4` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (235,0,'\0','21232f297a57a5a743894a0e4a801fc3','admin'),(236,0,'','df655f976f7c9d3263815bd981225cd9','company1'),(237,0,'\0','1b3231655cebb7a1f783eddf27d254ca','rookie'),(238,0,'\0','d196a28097115067fefd73d25b0c0be8','company2'),(239,0,'\0','1b3231655cebb7a1f783eddf27d254ca','auditor'),(240,0,'\0','04dd94ba3212ac52ad3a1f4cbfb52d4f','auditor2'),(241,0,'\0','1b3231655cebb7a1f783eddf27d254ca','provider'),(242,0,'\0','175d2e7a63f386554a4dd66e865c3e14','auditor1'),(243,0,'\0','1b3231655cebb7a1f783eddf27d254ca','provider1'),(244,0,'\0','1b3231655cebb7a1f783eddf27d254ca','provider2'),(245,0,'\0','1b3231655cebb7a1f783eddf27d254ca','provider3'),(246,0,'\0','1b3231655cebb7a1f783eddf27d254ca','provider4');
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account_authorities`
--

DROP TABLE IF EXISTS `user_account_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account_authorities` (
  `user_account` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_pao8cwh93fpccb0bx6ilq6gsl` (`user_account`),
  CONSTRAINT `FK_pao8cwh93fpccb0bx6ilq6gsl` FOREIGN KEY (`user_account`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account_authorities`
--

LOCK TABLES `user_account_authorities` WRITE;
/*!40000 ALTER TABLE `user_account_authorities` DISABLE KEYS */;
INSERT INTO `user_account_authorities` VALUES (235,'ADMIN'),(236,'COMPANY'),(237,'ROOKIE'),(238,'COMPANY'),(239,'AUDITOR'),(240,'AUDITOR'),(241,'PROVIDER'),(242,'AUDITOR'),(243,'PROVIDER'),(244,'PROVIDER'),(245,'PROVIDER'),(246,'PROVIDER');
/*!40000 ALTER TABLE `user_account_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-13 10:46:50
commit;