CREATE DATABASE  IF NOT EXISTS `scheme1` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `scheme1`;
-- MySQL dump 10.13  Distrib 5.7.21, for Linux (x86_64)
--
-- Host: localhost    Database: scheme1
-- ------------------------------------------------------
-- Server version	5.7.21-0ubuntu0.16.04.1

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

--
-- Table structure for table `consultation`
--

DROP TABLE IF EXISTS `consultation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `consultation` (
  `consultation_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dateTime` datetime DEFAULT NULL,
  `description` longtext,
  `health_record_Id_FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`consultation_id`),
  KEY `FK88runj7tbbl3or2d50pflyplv` (`health_record_Id_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=82 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultation`
--

LOCK TABLES `consultation` WRITE;
/*!40000 ALTER TABLE `consultation` DISABLE KEYS */;
INSERT INTO `consultation` VALUES (64,'2018-04-20 06:02:19','description1',1),(65,'2018-04-20 06:02:49','description2',1),(66,'2018-04-20 06:03:00','description3',1),(67,'2018-04-20 06:03:11','description2',1),(68,'2018-04-20 06:03:17','description2',1),(69,'2018-04-20 06:03:21','description1',1),(70,'2018-04-20 06:16:44','description10',1),(71,'2018-04-20 06:17:25','description4',1),(72,'2018-04-20 06:17:33','description5',1),(73,'2018-04-20 06:17:40','description9',1),(74,'2018-04-20 06:17:46','description8',1),(75,'2018-04-20 06:17:51','description7',1),(76,'2018-04-20 06:18:58','description6',1),(77,'2018-04-20 06:19:01','description6',1),(78,'2018-04-20 06:19:06','description10',1),(79,'2018-04-20 06:19:10','description9',1),(80,'2018-04-20 06:19:15','description9',1),(81,'2018-04-20 06:19:22','description7',1);
/*!40000 ALTER TABLE `consultation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `health_record`
--

DROP TABLE IF EXISTS `health_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `health_record` (
  `health_record_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `patient_id_FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`health_record_id`),
  KEY `FKdjll2pg2vhf4migy493yd8y7n` (`patient_id_FK`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `health_record`
--

LOCK TABLES `health_record` WRITE;
/*!40000 ALTER TABLE `health_record` DISABLE KEYS */;
INSERT INTO `health_record` VALUES (1,1);
/*!40000 ALTER TABLE `health_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient` (
  `patient_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `streetNumber` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (1,'city1','country1','patient1@patient.com','state1','street1',1001,'patient1');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient_contact_list`
--

DROP TABLE IF EXISTS `patient_contact_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `patient_contact_list` (
  `patient_id_FK` bigint(20) NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  KEY `FKl0gcpjevflnq28tekwhfaawuu` (`patient_id_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_contact_list`
--

LOCK TABLES `patient_contact_list` WRITE;
/*!40000 ALTER TABLE `patient_contact_list` DISABLE KEYS */;
INSERT INTO `patient_contact_list` VALUES (1,'phone1');
/*!40000 ALTER TABLE `patient_contact_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacist`
--

DROP TABLE IF EXISTS `pharmacist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmacist` (
  `pharmacist_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `streetNumber` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `specialty` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pharmacist_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacist`
--

LOCK TABLES `pharmacist` WRITE;
/*!40000 ALTER TABLE `pharmacist` DISABLE KEYS */;
INSERT INTO `pharmacist` VALUES (1,'city1','country1','pharm1@pharm.com','state1','street1',1001,'pharmacist1','GeneralPractice'),(2,'city2','country2','pharm2@pharm.com','state2','street2',1002,'pharmacist2','IntensiveCare'),(3,'city3','country3','pharm3@pharm.com','state3','street3',1003,'pharmacist3','Dermatology'),(4,'city4','country4','pharmacist4@pharmacist.com','state4','street4',1004,'pharmacist4','Neonatology'),(5,'city5','country5','pharmacist5@pharmacist.com','state5','street5',1005,'pharmacist5','ClinicalPathology'),(6,'city6','country6','pharmacist6@pharmacist.com','state6','street6',1006,'pharmacist6','Cardiology'),(7,'city7','country7','pharmacist7@pharmacist.com','state7','street7',1007,'pharmacist7','Infectology'),(8,'city8','country8','pharmacist8@pharmacist.com','state8','street8',1008,'pharmacist8','Neurology'),(9,'city9','country9','pharmacist9@pharmacist.com','state9','street9',1009,'pharmacist9','Endocrinology'),(10,'city10','country10','pharmacist10@pharmacist.com','state10','street10',1010,'pharmacist10','Oncology'),(11,'city11','country11','pharmacist11@pharmacist.com','state11','street11',1011,'pharmacist11','Urology'),(12,'city12','country12','pharmacist12@pharmacist.com','state12','street12',1012,'pharmacist12','EarNoseThroatENT');
/*!40000 ALTER TABLE `pharmacist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacist_consultation`
--

DROP TABLE IF EXISTS `pharmacist_consultation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmacist_consultation` (
  `pharmacist_id_FK` bigint(20) NOT NULL,
  `consultation_id_FK` bigint(20) NOT NULL,
  KEY `FKcvdx1wsj9xgtmadxwyvwq5sbd` (`consultation_id_FK`),
  KEY `FKtrr1bfkeqnyr0sot17a5090c7` (`pharmacist_id_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacist_consultation`
--

LOCK TABLES `pharmacist_consultation` WRITE;
/*!40000 ALTER TABLE `pharmacist_consultation` DISABLE KEYS */;
INSERT INTO `pharmacist_consultation` VALUES (1,64),(2,65),(3,66),(2,67),(2,68),(1,69),(10,70),(4,71),(5,72),(9,73),(8,74),(7,75),(6,76),(6,77),(10,78),(9,79),(9,80),(7,81);
/*!40000 ALTER TABLE `pharmacist_consultation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacist_contact_list`
--

DROP TABLE IF EXISTS `pharmacist_contact_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pharmacist_contact_list` (
  `pharmacist_id_FK` bigint(20) NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  KEY `FKcd0xve1l4byseleytt13acnb3` (`pharmacist_id_FK`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacist_contact_list`
--

LOCK TABLES `pharmacist_contact_list` WRITE;
/*!40000 ALTER TABLE `pharmacist_contact_list` DISABLE KEYS */;
INSERT INTO `pharmacist_contact_list` VALUES (1,'phone1'),(2,'phone1'),(2,'phone2'),(2,'phone3'),(3,'phone1'),(3,'phone2'),(4,'phone1'),(5,'phone1'),(5,'phone2'),(6,'phone1'),(6,'phone2'),(6,'phone3'),(7,'phone1'),(7,'phone2'),(7,'phone3'),(8,'phone1'),(8,'phone2'),(9,'phone1'),(10,'phone1'),(10,'phone2'),(11,'phone1'),(12,'phone1'),(12,'phone2');
/*!40000 ALTER TABLE `pharmacist_contact_list` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-20  7:30:19
