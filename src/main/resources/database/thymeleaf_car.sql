-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: thymeleaf
-- ------------------------------------------------------
-- Server version	8.0.27
use heroku_a162b5d731da075;

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `car` (
  `id` int NOT NULL AUTO_INCREMENT,
  `color` varchar(45) NOT NULL,
  `mark` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `price` double NOT NULL,
  `year` int NOT NULL,
  `picture_url` text,
  `car_detail_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7af62btjkjfarbrwps3yeao6l` (`car_detail_id`),
  CONSTRAINT `FK7af62btjkjfarbrwps3yeao6l` FOREIGN KEY (`car_detail_id`) REFERENCES `car_detail` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES (44,'grey','Porshe','Carrera',150000,2020,'https://www.autocentrum.pl/ac-file/article/5d8bbcea57502add3b40a06e/porsche-911-carrera-4s-poprawic-doskonalosc.jpg',7),(46,'black','Volks Wagen','Passat B5',4000,1992,'https://motohigh.pl/wp-content/uploads/2020/04/Volkswagen-Passat_W8-2001-1024-08.jpg',9),(47,'white','Fiat','126p',3290,1988,'https://www.wyborkierowcow.pl/wp-content/uploads/2021/10/fiat-126p-autostrada.jpg',10),(48,'black','BMW','X5 PRO',45000,2010,'https://bi.im-g.pl/im/25/f6/17/z25125925Q,Opancerzone-BMW-X5-Protection-VR6.jpg',11),(49,'red','Porshe','Carrera',300000,2012,'	https://www.autocentrum.pl/ac-file/car-version/5d5ada42c74b352e3611350a.jpg',12),(50,'red','Opel','Siena',3000,1995,'https://i.ytimg.com/vi/Nk4ylfY_qTk/maxresdefault.jpg',13),(51,'white','Fiat','Panda',7000,2000,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWdOgfvUxHwsB_1lMxna2lhfG8XmqyxqKDkQ&usqp=CAU',14),(52,'green','Polonez','Caro',14000,1990,'https://www.autocentrum.pl/ac-file/article/5e41772bc74b353e4742d22e/11-02-1983-stutysieczny-polonez.jpg',15),(53,'grey','Paggani','Zonda',500000,2020,'https://forzaitalia.pl/wp-content/uploads/2019/12/Zonda-Aether-7.jpg',16);
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
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
