-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: thymeleaf
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `car_detail`
--
use heroku_85fb6b6f2cfb340;

DROP TABLE IF EXISTS `car_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `car_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `body_type` varchar(255) NOT NULL,
  `country_of_origin` varchar(255) NOT NULL,
  `drive` varchar(255) NOT NULL,
  `mileage` int NOT NULL,
  `never_crashed` bit(1) NOT NULL,
  `number_of_doors` int NOT NULL,
  `number_of_seats` int NOT NULL,
  `power` int NOT NULL,
  `transmission` varchar(255) NOT NULL,
  `type_of_fuel` varchar(255) NOT NULL,
  `vehicle_condition` varchar(255) NOT NULL,
  `air_condition` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_detail`
--

LOCK TABLES `car_detail` WRITE;
/*!40000 ALTER TABLE `car_detail` DISABLE KEYS */;
INSERT INTO `car_detail` VALUES (1,'sedan','Poland','front wheels',1,_binary '',1,4,1,'automat','gas','new',_binary '\0'),(2,'bus','Poland','back wheels',1,_binary '',1,4,1,'automat','gas','new',_binary '\0'),(3,'sedan','Germany','quatro',12000,_binary '',2,2,320,'automat','petrol','used',_binary '\0'),(4,'sedan','Poland','back wheels',1,_binary '\0',5,4,60,'automat','petrol','used',_binary '\0'),(7,'sedan','Italy','quatro',0,_binary '',2,2,320,'automat','petrol','new',_binary '\0'),(9,'station wagon','Germany','rear wheel drive',230000,_binary '',5,4,90,'manual','petrol','used',_binary '\0'),(10,'station wagon','Poland','rear wheel drive',320000,_binary '',2,4,23,'manual','petrol','used',_binary '\0'),(11,'SUV','Germany','all wheel drive system',0,_binary '',5,4,120,'automate','diesel','new',_binary ''),(12,'Coupe','Germany','rear wheel drive',0,_binary '',2,2,320,'automate','petrol','new',_binary ''),(13,'sedan','Germany','rear wheel drive',280000,_binary '',5,4,65,'manual','petrol','used',_binary '\0'),(14,'Hatchback','Italy','font wheel drive',180000,_binary '',5,4,65,'manual','gas','used',_binary '\0'),(15,'sedan','Poland','font wheel drive',290000,_binary '\0',5,4,55,'manual','petrol','used',_binary '\0'),(16,'sedan','Italy','all wheel drive system',0,_binary '',2,2,340,'manual','petrol','new',_binary '');
/*!40000 ALTER TABLE `car_detail` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-14 16:15:18
