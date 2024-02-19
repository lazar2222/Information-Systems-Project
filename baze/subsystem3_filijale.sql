-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: subsystem3
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
-- Table structure for table `filijale`
--

DROP TABLE IF EXISTS `filijale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `filijale` (
  `IDFilijale` int unsigned NOT NULL,
  `Naziv` varchar(45) NOT NULL,
  `Adresa` varchar(45) NOT NULL,
  `IDMesta` int unsigned NOT NULL,
  PRIMARY KEY (`IDFilijale`),
  UNIQUE KEY `IDFilijale_UNIQUE` (`IDFilijale`),
  KEY `IDMesta_idx` (`IDMesta`),
  CONSTRAINT `IDMesta` FOREIGN KEY (`IDMesta`) REFERENCES `mesta` (`IDMesta`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `filijale`
--

LOCK TABLES `filijale` WRITE;
/*!40000 ALTER TABLE `filijale` DISABLE KEYS */;
INSERT INTO `filijale` VALUES (1,'Leka Trading','471 Serangoon Loop',10),(2,'Lyngbysild','Lyngbysild Fiskebakken 10',8),(3,'Zaanse Snoepfabriek','Verkoop Rijnweg 22',3),(4,'Karkki Oy','Valtakatu 12',3),(5,'Gday Mate','170 Prince Edward Parade Hunters Hill',9),(6,'Ma Maison','2960 Rue St. Laurent',4),(7,'Pasta Buttini s.r.l.','Via dei Gelsomini, 153',8),(8,'Escargots Nouveaux','22, rue H. Voiron',1),(9,'Gai paturage','Bat. B 3, rue des Alpes',8),(10,'Forets derables','148 rue Chasseur',4);
/*!40000 ALTER TABLE `filijale` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-30 23:51:49
