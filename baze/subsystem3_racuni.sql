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
-- Table structure for table `racuni`
--

DROP TABLE IF EXISTS `racuni`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `racuni` (
  `IDRacuna` int unsigned NOT NULL,
  `IDKomitenta` int unsigned NOT NULL,
  `IDMestaOtvaranja` int unsigned NOT NULL,
  `Stanje` decimal(10,0) NOT NULL DEFAULT '0',
  `DozvoljenMinus` decimal(10,0) NOT NULL,
  `Status` char(1) NOT NULL DEFAULT 'A',
  `DatumVremeOtvaranja` datetime NOT NULL,
  `BrojTransakcija` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`IDRacuna`),
  UNIQUE KEY `IDRacuna_UNIQUE` (`IDRacuna`),
  KEY `IDKomitenta_idx` (`IDKomitenta`),
  KEY `IDMestaOtvaranja_idx` (`IDMestaOtvaranja`),
  CONSTRAINT `IDKomitenta` FOREIGN KEY (`IDKomitenta`) REFERENCES `komitenti` (`IDKomitenta`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `IDMestaOtvaranja` FOREIGN KEY (`IDMestaOtvaranja`) REFERENCES `mesta` (`IDMesta`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `racuni`
--

LOCK TABLES `racuni` WRITE;
/*!40000 ALTER TABLE `racuni` DISABLE KEYS */;
INSERT INTO `racuni` VALUES (1,2,7,12500,0,'A','2021-01-11 09:35:00',7),(2,7,3,21000,87000,'A','2021-02-12 09:40:00',4),(3,2,8,22000,0,'A','2021-02-15 10:05:00',2),(4,8,2,45500,34000,'A','2021-05-07 10:40:00',3),(5,7,7,48500,90000,'A','2021-08-23 11:40:00',6),(6,3,8,4000,54000,'A','2021-08-27 12:15:00',2),(7,9,10,-13500,0,'B','2021-08-30 12:30:00',3),(8,5,7,8000,0,'A','2021-10-04 15:25:00',4),(9,6,10,-42500,0,'B','2021-10-20 16:20:00',3),(10,9,4,-58500,75000,'A','2021-10-22 17:00:00',4);
/*!40000 ALTER TABLE `racuni` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-30 23:51:50
