-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: localhost    Database: schedule
-- ------------------------------------------------------
-- Server version	5.6.21-log

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
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `class` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `room` varchar(45) DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `schedule_id` int(11) NOT NULL,
  `day` varchar(45) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`schedule_id`),
  KEY `fk_class_schedule1_idx` (`schedule_id`),
  KEY `fk_class_day1_idx` (`day`),
  CONSTRAINT `fk_class_schedule1` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'ДГС','505/255','13:00:00','14:20:00',13,'Monday','practice'),(2,'СУБД','506','14:30:00','15:50:00',13,'Monday','practice'),(3,'СУБД','521','16:00:00','17:20:00',13,'Monday','lecture'),(4,'ЭСУПК','607','13:00:00','14:20:00',13,'Tuesday','lecture'),(5,'ИИС','607','14:30:00','15:50:00',13,'Tuesday','lecture'),(6,'с/к','607','16:00:00','17:20:00',13,'Tuesday','practice'),(7,'с/л','607','17:30:00','18:50:00',13,'Tuesday','lecture'),(8,'ДГС','607','14:30:00','15:50:00',13,'Wednesday','lecture'),(9,'с/к','607','16:00:00','17:20:00',13,'Wednesday','practice'),(10,'с/л','607','17:30:00','18:50:00',13,'Wednesday','seminar'),(11,'ЭСУПК',NULL,'16:00:00','17:20:00',13,'Thursday','practice'),(12,'Экология','607','17:30:00','18:50:00',13,'Thursday','lecture'),(13,'ИСМ','521','14:30:00','15:50:00',13,'Friday','lecture'),(14,'ИСМ','507/608','16:00:00','17:20:00',13,'Friday','practice'),(15,'ИИС','604','17:30:00','18:50:00',13,'Friday','practice'),(16,'ДГС','505/255','14:30:00','15:50:00',14,'Monday','practice'),(17,'СУБД','521','16:00:00','17:20:00',14,'Monday','lecture'),(18,'СУБД','506','17:30:00','18:50:00',14,'Monday','practice'),(19,'ЭСУПК','607','13:00:00','14:20:00',14,'Tuesday','lecture'),(20,'ИИС','607','14:30:00','15:50:00',14,'Tuesday','lecture'),(21,'с/к','522','16:00:00','17:20:00',14,'Tuesday','practice'),(23,'с/с','522','17:30:00','18:50:00',14,'Tuesday','seminar'),(24,'ДГС','607','14:30:00','15:50:00',14,'Wednesday','lecture'),(25,'с/к','518','16:00:00','17:20:00',14,'Wednesday','practice'),(26,'с/л','506','17:30:00','18:50:00',14,'Wednesday','lecture'),(27,'Экология','607','17:30:00','18:50:00',14,'Thursday','lecture'),(28,'ЭСУПК',NULL,'19:00:00','20:20:00',14,'Thursday','practice'),(29,'ИиСМ','521','14:30:00','15:50:00',14,'Friday','lecture'),(30,'ИИС','604','16:00:00','17:20:00',14,'Friday','practice'),(31,'ИСМ','608/507','17:30:00','18:50:00',14,'Friday','practice'),(32,'ДГС','505/255','14:30:00','15:50:00',15,'Monday','practice'),(33,'СУБД','521','16:00:00','17:20:00',15,'Monday','lecture'),(34,'ЭСУПК','607','13:00:00','14:20:00',15,'Tuesday','lecture'),(35,'ИИС','607','14:30:00','15:50:00',15,'Tuesday','lecture'),(36,'ИИС','604','16:00:00','17:20:00',15,'Tuesday','practice'),(37,'СУБД','506','17:30:00','18:50:00',15,'Tuesday','practice'),(38,'ДГС','607','14:30:00','15:50:00',15,'Wednesday','lecture'),(39,'ИСМ','508','16:00:00','17:20:00',15,'Wednesday','practice'),(40,'с/к','522','17:30:00','18:50:00',15,'Wednesday','practice'),(42,'с/л','522','19:00:00','20:20:00',15,'Wednesday','lecture'),(43,'Экология','607','17:30:00','18:50:00',15,'Thursday','lecture'),(44,'ИиСМ','521','14:30:00','15:50:00',15,'Friday','lecture'),(45,'ДГС','256/505','16:00:00','17:20:00',15,'Friday','practice'),(46,'с/к','517','11:15:00','12:35:00',15,'Saturday','practice'),(47,'с/к','517','13:00:00','14:20:00',15,'Saturday','practice'),(48,'СУБД','521','16:00:00','17:20:00',16,'Monday','lecture'),(49,'с/к','518','17:30:00','18:50:00',16,'Monday','practice'),(50,'с/л','506','19:00:00','20:20:00',16,'Monday','practice'),(51,'ЭСУПК','607','13:00:00','14:20:00',16,'Tuesday','lecture'),(52,'ИИС','607','14:30:00','15:50:00',16,'Tuesday','lecture'),(53,'СУБД','314','16:00:00','17:20:00',16,'Tuesday','practice'),(54,'ДГС','607','14:30:00','15:50:00',16,'Wednesday','lecture'),(55,'с/к','517','16:00:00','17:20:00',16,'Wednesday','practice'),(56,'с/л','517','17:30:00','18:50:00',16,'Wednesday','practice'),(57,'ЭСУПК',NULL,'16:00:00','17:20:00',16,'Thursday','practice'),(58,'Экология','607','17:30:00','18:50:00',16,'Thursday','lecture'),(59,'ИиСМ','521','14:30:00','15:50:00',16,'Friday','lecture'),(60,'ИСМ','506','16:00:00','17:20:00',16,'Friday','practice'),(61,'ДГС','256/505','17:30:00','18:50:00',16,'Friday','practice'),(62,'ИИС','604','19:00:00','20:20:00',16,'Friday','practice');
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `isActual` tinyint(1) NOT NULL DEFAULT '1',
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`group_id`),
  KEY `fk_schedule_group1_idx` (`group_id`),
  CONSTRAINT `fk_schedule_group1` FOREIGN KEY (`group_id`) REFERENCES `student_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (1,NULL,1,1),(2,NULL,1,2),(3,NULL,1,3),(4,NULL,1,4),(5,NULL,1,5),(6,NULL,1,6),(7,NULL,1,7),(8,NULL,1,8),(9,NULL,1,9),(10,NULL,1,10),(11,NULL,1,11),(12,NULL,1,12),(13,NULL,1,13),(14,NULL,1,14),(15,NULL,1,15),(16,NULL,1,16),(17,NULL,1,17),(18,NULL,1,18),(19,NULL,1,19),(20,NULL,1,20);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_group`
--

DROP TABLE IF EXISTS `student_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_group` (
  `id` int(11) NOT NULL,
  `name_group` varchar(45) NOT NULL,
  `course` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_group`
--

LOCK TABLES `student_group` WRITE;
/*!40000 ALTER TABLE `student_group` DISABLE KEYS */;
INSERT INTO `student_group` VALUES (1,'1',1),(2,'2',1),(3,'3',1),(4,'4',1),(5,'1',2),(6,'2',2),(7,'3',2),(8,'4',2),(9,'1',3),(10,'2',3),(11,'3',3),(12,'4',3),(13,'1',4),(14,'2',4),(15,'3',4),(16,'4',4),(17,'1',5),(18,'2',5),(19,'3',5),(20,'4',5);
/*!40000 ALTER TABLE `student_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`group_id`),
  KEY `fk_user_group_idx` (`group_id`),
  CONSTRAINT `fk_user_group` FOREIGN KEY (`group_id`) REFERENCES `student_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Natalie','Pakki','fecit@inbox.ru','wXwgAWh6YVeq8d8reFtWFQ==\n',1);
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

-- Dump completed on 2016-02-05 14:54:17
