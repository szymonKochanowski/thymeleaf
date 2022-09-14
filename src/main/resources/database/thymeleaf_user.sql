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
-- Table structure for table `user`
--
use heroku_82b970403bd5b80;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `password` varchar(64) NOT NULL,
  `role` varchar(255) NOT NULL,
  `updated_on` datetime DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `profile_picture_url` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (41,'2022-07-19 19:50:03',_binary '','$2a$12$Gx.pSRPY66VbeWi9CJtEOuXumQpPEAaLZ6RqKf1OKsA9Pupq1PYMi','ROLE_USER',NULL,'test12','https://cdn-icons-png.flaticon.com/512/149/149071.png'),(42,'2022-07-21 17:08:17',_binary '','$2a$12$r3HD8f1bogt9mUGlMIB0Q.8qUbGKvYmZgiAFfXjzCHzX/.usM0XEe','ROLE_USER','2022-07-21 17:15:51','test123','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTv1vgdYWHDUkyYYYxV4RV78Q4AHDtagK2GRQ&usqp=CAU'),(44,'2022-08-06 10:16:30',_binary '','$2a$12$jgs4yHs00LWmkvRua9GYpeKx7PC3nhN3gtwfQwxizS37h82muBc2a','ROLE_USER',NULL,'test1234',''),(45,'2022-08-06 10:24:29',_binary '','$2a$12$XBUbJRNf1QOVqWphGz6IS.Ew7z37JcOfEvY5Fn5CAytOaSUx88Hmy','ROLE_USER','2022-08-06 12:22:16','test123456',''),(46,'2022-08-06 10:37:39',_binary '','$2a$12$8FeMdmzgl.qQl4MXmgc8SeAabF5IofqACoyyrVn4I8VqbkBx.4wHe','ROLE_USER',NULL,'test1234567',''),(47,'2022-08-06 11:38:34',_binary '','$2a$12$0hS1dW1O0zLNmycGhhKsCOSgP7mDep9dh.ZFnptyi746HVZ9.aYny','ROLE_USER',NULL,'test12345',''),(48,'2022-08-08 18:26:13',_binary '','$2a$12$teP4WydQkHuCM6EUcC1G2OJ51oIZFUh668jBE0TE8PSwKxe4SmwEG','ROLE_USER',NULL,'test',''),(49,'2022-08-08 18:26:47',_binary '','$2a$12$wNB0LbjDhcszt1UOnq.ecuos.pjx.6CfAEKKUlD/9.808SjNM0dGa','ROLE_USER',NULL,'test11',''),(50,'2022-08-11 13:10:23',_binary '','$2a$12$mtE8YD0WbIGbutPxgNpzZu1I5i3PMILpVyPgAEZ2eDgCmVetrgJ9u','ROLE_USER',NULL,'user123',''),(54,'2022-08-11 13:46:08',_binary '','$2a$12$/db0q0nyrMSqUKfeKggSpeY5mDerBH1exwq6sfkGV0C8eWQs3e9VS','ROLE_USER',NULL,'user1234',''),(69,'2022-08-19 08:36:02',_binary '','$2a$12$nK/vfSkYpIDq8VSzNMg6S.abz.eK27xjuyP4IJU6PFV6eVgT/I1/K','ROLE_USER',NULL,'user21','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTv1vgdYWHDUkyYYYxV4RV78Q4AHDtagK2GRQ&usqp=CAU'),(70,'2022-09-12 07:15:45',_binary '','$2a$12$fsF7dhYLCZhuQnCsUSpWOe/rpQgN9nRnLZhQhAJFwxJAiKDBuG/jS','ROLE_ADMIN','2022-09-14 11:07:14','admin','https://thumbs.dreamstime.com/b/ikona-logowania-administratora-na-notebooku-%E2%80%94-wektor-gie%C5%82dowy-166205404.jpg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-09-14 16:15:19
