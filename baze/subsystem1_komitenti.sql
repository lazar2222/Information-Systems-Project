-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: subsystem1
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
-- Table structure for table `komitenti`
--

DROP TABLE IF EXISTS `komitenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `komitenti` (
  `IDKomitenta` int unsigned NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Adresa` varchar(45) NOT NULL,
  `IDMestaSedista` int unsigned NOT NULL,
  PRIMARY KEY (`IDKomitenta`),
  UNIQUE KEY `IDKomitenta_UNIQUE` (`IDKomitenta`),
  KEY `IDMestaSedista_idx` (`IDMestaSedista`),
  CONSTRAINT `IDMestaSedista` FOREIGN KEY (`IDMestaSedista`) REFERENCES `mesta` (`IDMesta`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `komitenti`
--

LOCK TABLES `komitenti` WRITE;
/*!40000 ALTER TABLE `komitenti` DISABLE KEYS */;
INSERT INTO `komitenti` VALUES (1,'Maria Anders','Obere Str. 57',1),(2,'Ana Trujillo','Avda. de la Constitucion 2222',2),(3,'Antonio Moreno','Mataderos  2312',3),(4,'Thomas Hardy','120 Hanover Sq.',1),(5,'Christina Berglund','Berguvsvagen  8',5),(6,'Hanna Moos','Forsterstr. 57',6),(7,'Frederique Citeaux','24, place Kleber',7),(8,'Martin Sommer','C/ Araquil, 67',8),(9,'Laurence Lebihan','12, rue des Bouchers',9),(10,'Elizabeth Lincoln','23 Tsawassen Blvd.',3);
/*!40000 ALTER TABLE `komitenti` ENABLE KEYS */;
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
