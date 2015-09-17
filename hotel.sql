-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: hotel
-- ------------------------------------------------------
-- Server version	5.6.24-log

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
-- Table structure for table `daily_order`
--

DROP TABLE IF EXISTS `daily_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `roomNumber` varchar(45) DEFAULT NULL,
  `roomType` varchar(45) DEFAULT NULL,
  `gym` enum('true','false') DEFAULT NULL,
  `restaurant` enum('true','false') DEFAULT NULL,
  `saun` enum('true','false') DEFAULT NULL,
  `pool` enum('true','false') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_order`
--

LOCK TABLES `daily_order` WRITE;
/*!40000 ALTER TABLE `daily_order` DISABLE KEYS */;
INSERT INTO `daily_order` VALUES (36,'ognjetina','21','two beds','false','true','true','false'),(37,'djomla','3','one bed','true','true','true','true'),(38,'ognjetina2','21','two beds','true','true','false','false');
/*!40000 ALTER TABLE `daily_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reciept`
--

DROP TABLE IF EXISTS `reciept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reciept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `roomNumber` varchar(45) DEFAULT NULL,
  `roomType` varchar(45) DEFAULT NULL,
  `dateFrom` date DEFAULT NULL,
  `dateTo` date DEFAULT NULL,
  `gym` enum('true','false') DEFAULT NULL,
  `restaurant` enum('true','false') DEFAULT NULL,
  `saun` enum('true','false') DEFAULT NULL,
  `pool` enum('true','false') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reciept`
--

LOCK TABLES `reciept` WRITE;
/*!40000 ALTER TABLE `reciept` DISABLE KEYS */;
INSERT INTO `reciept` VALUES (4,'ognjetina','31','apartment','2015-09-11','2015-09-17','true','true','true','false'),(5,'ognjetina','4','one bed','2015-09-11','2015-09-17','true','true','true','false');
/*!40000 ALTER TABLE `reciept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms` (
  `roomNumber` int(11) NOT NULL AUTO_INCREMENT,
  `isOccupied` enum('true','false') DEFAULT 'false',
  `roomType` enum('one bed','two beds','apartment') DEFAULT NULL,
  PRIMARY KEY (`roomNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES (2,'true','one bed'),(3,'true','one bed'),(4,'false','one bed'),(5,'false','one bed'),(6,'false','one bed'),(7,'false','one bed'),(8,'false','one bed'),(9,'false','one bed'),(10,'false','one bed'),(11,'false','one bed'),(12,'false','one bed'),(13,'false','one bed'),(14,'false','one bed'),(15,'false','one bed'),(16,'false','one bed'),(17,'false','one bed'),(18,'false','one bed'),(19,'false','one bed'),(20,'false','one bed'),(21,'true','two beds'),(22,'false','two beds'),(23,'false','two beds'),(24,'false','two beds'),(25,'false','two beds'),(26,'false','two beds'),(27,'false','two beds'),(28,'false','two beds'),(29,'false','two beds'),(30,'false','two beds'),(31,'false','apartment'),(32,'false','apartment'),(33,'false','apartment'),(34,'false','apartment'),(35,'false','apartment');
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `iD` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `isAdmin` enum('true','false') DEFAULT NULL,
  `gender` enum('male','female') DEFAULT NULL,
  `idCard` varchar(45) DEFAULT NULL,
  `isOnline` enum('true','false') DEFAULT NULL,
  `isCheckdIn` enum('true','false') DEFAULT NULL,
  `dateOfCheckingIn` date DEFAULT NULL,
  `userCheckdOutDate` date DEFAULT NULL,
  PRIMARY KEY (`iD`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='	';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin','admin','admin','true',NULL,NULL,'false','false',NULL,NULL),(9,'ognjetina','1234qwer','Ognjen','Lazic','false','male','asdfqwerasdf','true','true','2015-09-17',NULL),(10,'djomla','1234qwer','Mladen','Stevanovic','false','male','asdqwe123','false','true','2015-09-10',NULL),(11,'ognjetina2','1234qwer','Ognjen','Asdfggkkg','false','male','asdfqeasd','false','true','2015-09-17',NULL),(12,'apartmanUser','asdfqwer','Kralj','Carina','false','male','asdfqwerasdf','false','false',NULL,NULL),(13,'djomlaOpet','1234qwer','1234qwer','1234qwer','false','male','1234qwer','false','false',NULL,NULL),(14,'test','testtttt','1234qwer','1234qwer','false','male','asdfqwefasd','false','false',NULL,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-09-17 13:09:57
