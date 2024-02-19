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
-- Table structure for table `transakcije`
--

DROP TABLE IF EXISTS `transakcije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transakcije` (
  `IDTransakcije` int unsigned NOT NULL,
  `IDSrcRacuna` int unsigned DEFAULT NULL,
  `IDDstRacuna` int unsigned DEFAULT NULL,
  `Iznos` decimal(10,0) NOT NULL,
  `DatumVremeRealizacije` datetime NOT NULL,
  `RedniBrojSrc` int unsigned DEFAULT NULL,
  `RedniBrojDst` int unsigned DEFAULT NULL,
  `Svrha` varchar(45) NOT NULL,
  `IDFilijale` int unsigned DEFAULT NULL,
  PRIMARY KEY (`IDTransakcije`),
  UNIQUE KEY `IDTransakcije_UNIQUE` (`IDTransakcije`),
  KEY `IDSrcRacuna_idx` (`IDSrcRacuna`),
  KEY `IDDstRacuna_idx` (`IDDstRacuna`),
  KEY `IDFilijale_idx` (`IDFilijale`),
  CONSTRAINT `IDDstRacuna` FOREIGN KEY (`IDDstRacuna`) REFERENCES `racuni` (`IDRacuna`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `IDFilijale` FOREIGN KEY (`IDFilijale`) REFERENCES `filijale` (`IDFilijale`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `IDSrcRacuna` FOREIGN KEY (`IDSrcRacuna`) REFERENCES `racuni` (`IDRacuna`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transakcije`
--

LOCK TABLES `transakcije` WRITE;
/*!40000 ALTER TABLE `transakcije` DISABLE KEYS */;
INSERT INTO `transakcije` VALUES (1,NULL,8,4500,'2022-01-01 00:02:00',NULL,1,'Bank Deposit',4),(2,10,10,18500,'2022-01-25 10:29:00',1,2,'Exotic Liquids',NULL),(3,9,NULL,41500,'2022-01-21 09:48:00',1,NULL,'Bank Withdrawal',7),(4,NULL,5,23000,'2022-01-11 00:05:00',NULL,1,'Bank Deposit',2),(5,9,8,4500,'2022-01-28 10:27:00',2,2,'Tokyo Traders',NULL),(6,NULL,3,42000,'2022-01-05 22:00:00',NULL,1,'Bank Deposit',8),(7,5,4,21500,'2022-01-09 23:36:00',2,1,'Exotic Liquids',NULL),(8,NULL,2,36000,'2022-01-22 18:52:00',NULL,1,'Bank Deposit',1),(9,4,NULL,4000,'2022-01-26 09:47:00',2,NULL,'Bank Withdrawal',7),(10,2,NULL,15000,'2022-01-15 19:55:00',2,NULL,'Bank Withdrawal',4),(11,NULL,1,31000,'2022-01-28 23:13:00',NULL,1,'Bank Deposit',4),(12,NULL,5,43500,'2022-01-08 00:14:00',NULL,3,'Bank Deposit',7),(13,7,NULL,15500,'2022-01-09 18:32:00',1,NULL,'Bank Withdrawal',4),(14,3,NULL,20000,'2022-01-18 23:42:00',2,NULL,'Bank Withdrawal',7),(15,7,5,10500,'2022-01-13 17:58:00',2,4,'Cooperativa de Quesos Las Cabras',NULL),(16,1,7,12500,'2022-01-03 18:07:00',2,3,'Mayumis',NULL),(17,NULL,4,28000,'2022-01-12 06:31:00',NULL,3,'Bank Deposit',8),(18,10,NULL,9000,'2022-01-13 22:42:00',3,NULL,'Bank Withdrawal',3),(19,1,NULL,28000,'2022-01-22 17:35:00',3,NULL,'Bank Withdrawal',1),(20,NULL,5,9500,'2022-01-08 00:50:00',NULL,5,'Bank Deposit',8),(21,NULL,9,3500,'2022-01-02 22:47:00',NULL,3,'Bank Deposit',6),(22,6,NULL,45500,'2022-01-28 08:58:00',1,NULL,'Bank Withdrawal',4),(23,10,6,49500,'2022-01-30 10:24:00',4,2,'Refrescos Americanas LTDA',NULL),(24,NULL,1,38000,'2022-01-23 22:43:00',NULL,4,'Bank Deposit',5),(25,2,NULL,2500,'2022-01-05 07:00:00',3,NULL,'Bank Withdrawal',5),(26,8,NULL,17500,'2022-01-07 17:26:00',3,NULL,'Bank Withdrawal',7),(27,1,NULL,16000,'2022-01-26 06:33:00',5,NULL,'Bank Withdrawal',8),(28,5,8,16500,'2022-01-30 12:06:00',6,4,'Exotic Liquids',NULL),(29,1,1,24500,'2022-01-14 12:16:00',6,7,'Mayumis',NULL),(30,NULL,2,2500,'2022-01-14 18:22:00',NULL,4,'Bank Deposit',5);
/*!40000 ALTER TABLE `transakcije` ENABLE KEYS */;
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
