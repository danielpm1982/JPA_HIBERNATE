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
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customer_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `streetNumber` int(11) NOT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'city1','country1','state1','street1',1001,'customer1'),(2,'city2','country2','state2','street2',1002,'customer2'),(3,'city3','country3','state3','street3',1003,'customer3');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `order_fk` bigint(20) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2jm1jtkd2db6f2971ek86695d` (`order_fk`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
INSERT INTO `item` VALUES (1,'description1A1','label1A1','item1A1',1,50.50),(2,'description1A2','label1A2','item1A2',1,120.30),(3,'description1A3','label1A3','item1A3',1,147.30),(4,'description1B1','label1B1','item1B1',2,189.50),(5,'description1B2','label1B2','item1B2',2,220.50),(6,'description1C1','label1C1','item1C1',3,520.50),(7,'description1C2','label1C2','item1C2',3,115.30),(8,'description2A1','label2A1','item2A1',4,128.50),(9,'description2A2','label2A2','item2A2',4,180.60),(10,'description2B1','label2B1','item2B1',5,119.50);
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_date` datetime DEFAULT NULL,
  `order_name` varchar(255) DEFAULT NULL,
  `customer_fk` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `UK_bomgbnsox185d1k1d6a29ik76` (`order_name`),
  KEY `FK6w07dtojxwqh4qc4jxxqfoh5p` (`customer_fk`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (1,'2018-04-13 04:21:01','order1A',1),(2,'2018-04-13 04:21:08','order1B',1),(3,'2018-04-13 04:21:11','order1C',1),(4,'2018-04-13 04:21:21','order2A',2),(5,'2018-04-13 04:21:25','order2B',2),(6,'2018-04-13 04:21:38','order3A',3);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-13  5:06:51
