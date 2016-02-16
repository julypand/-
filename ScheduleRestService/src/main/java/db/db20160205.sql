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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'ДГС'),(2,'СУБД'),(3,'ЭСУПК'),(4,'ИИС'),(5,'с/к'),(6,'с/л'),(7,'ИСМ'),(8,'Экология');
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lesson`
--

DROP TABLE IF EXISTS `lesson`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lesson` (
  `id` int(11) NOT NULL,
  `name_id` int(11) DEFAULT NULL,
  `room` varchar(45) DEFAULT '0',
  `time_start` time DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `schedule_id` int(11) NOT NULL,
  `day` varchar(45) NOT NULL,
  `type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`schedule_id`),
  KEY `fk_class_schedule1_idx` (`schedule_id`),
  KEY `fk_class_day1_idx` (`day`),
  KEY `fk_class_type1_idx` (`type_id`),
  KEY `fk_class_name1_idx` (`name_id`),
  CONSTRAINT `fk_class_name1` FOREIGN KEY (`name_id`) REFERENCES `class` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_schedule1` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_class_type1` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lesson`
--

LOCK TABLES `lesson` WRITE;
/*!40000 ALTER TABLE `lesson` DISABLE KEYS */;
INSERT INTO `lesson` VALUES (1,1,'505/255','13:00:00','14:20:00',13,'Monday',2),(2,2,'506','14:30:00','15:50:00',13,'Monday',2),(3,2,'521','16:00:00','17:20:00',13,'Monday',1),(4,3,'607','13:00:00','14:20:00',13,'Tuesday',1),(5,4,'607','14:30:00','15:50:00',13,'Tuesday',1),(6,5,'607','16:00:00','17:20:00',13,'Tuesday',2),(7,6,'607','17:30:00','18:50:00',13,'Tuesday',1),(8,1,'607','14:30:00','15:50:00',13,'Wednesday',1),(9,5,'607','16:00:00','17:20:00',13,'Wednesday',2),(10,6,'607','17:30:00','18:50:00',13,'Wednesday',3),(11,3,'  ','16:00:00','17:20:00',13,'Thursday',2),(12,8,'607','17:30:00','18:50:00',13,'Thursday',1),(13,7,'521','14:30:00','15:50:00',13,'Friday',1),(14,7,'507/608','16:00:00','17:20:00',13,'Friday',2),(15,4,'604','17:30:00','18:50:00',13,'Friday',2),(16,1,'505/255','14:30:00','15:50:00',14,'Monday',2),(17,2,'521','16:00:00','17:20:00',14,'Monday',1),(18,2,'506','17:30:00','18:50:00',14,'Monday',2),(19,3,'607','13:00:00','14:20:00',14,'Tuesday',1),(20,4,'607','14:30:00','15:50:00',14,'Tuesday',1),(21,5,'522','16:00:00','17:20:00',14,'Tuesday',2),(23,5,'522','17:30:00','18:50:00',14,'Tuesday',3),(24,1,'607','14:30:00','15:50:00',14,'Wednesday',1),(25,5,'518','16:00:00','17:20:00',14,'Wednesday',2),(26,6,'506','17:30:00','18:50:00',14,'Wednesday',1),(27,8,'607','17:30:00','18:50:00',14,'Thursday',1),(28,3,'  ','19:00:00','20:20:00',14,'Thursday',2),(29,7,'521','14:30:00','15:50:00',14,'Friday',1),(30,4,'604','16:00:00','17:20:00',14,'Friday',2),(31,7,'608/507','17:30:00','18:50:00',14,'Friday',2),(32,1,'505/255','14:30:00','15:50:00',15,'Monday',2),(33,2,'521','16:00:00','17:20:00',15,'Monday',1),(34,3,'607','13:00:00','14:20:00',15,'Tuesday',1),(35,4,'607','14:30:00','15:50:00',15,'Tuesday',1),(36,4,'604','16:00:00','17:20:00',15,'Tuesday',2),(37,2,'506','17:30:00','18:50:00',15,'Tuesday',2),(38,1,'607','14:30:00','15:50:00',15,'Wednesday',1),(39,7,'508','16:00:00','17:20:00',15,'Wednesday',2),(40,5,'522','17:30:00','18:50:00',15,'Wednesday',2),(42,6,'522','19:00:00','20:20:00',15,'Wednesday',1),(43,8,'607','17:30:00','18:50:00',15,'Thursday',1),(44,7,'521','14:30:00','15:50:00',15,'Friday',1),(45,1,'256/505','16:00:00','17:20:00',15,'Friday',2),(46,5,'517','11:15:00','12:35:00',15,'Saturday',2),(47,5,'517','13:00:00','14:20:00',15,'Saturday',2),(48,2,'521','16:00:00','17:20:00',16,'Monday',1),(49,5,'518','17:30:00','18:50:00',16,'Monday',2),(50,6,'506','19:00:00','20:20:00',16,'Monday',1),(51,3,'607','13:00:00','14:20:00',16,'Tuesday',1),(52,4,'607','14:30:00','15:50:00',16,'Tuesday',1),(53,2,'314','16:00:00','17:20:00',16,'Tuesday',2),(54,1,'607','14:30:00','15:50:00',16,'Wednesday',1),(55,5,'517','16:00:00','17:20:00',16,'Wednesday',2),(56,6,'517','17:30:00','18:50:00',16,'Wednesday',1),(57,3,'  ','16:00:00','17:20:00',16,'Thursday',2),(58,8,'607','17:30:00','18:50:00',16,'Thursday',1),(59,7,'521','14:30:00','15:50:00',16,'Friday',1),(60,7,'506','16:00:00','17:20:00',16,'Friday',2),(61,1,'256/505','17:30:00','18:50:00',16,'Friday',2),(62,4,'604','19:00:00','20:20:00',16,'Friday',2);
/*!40000 ALTER TABLE `lesson` ENABLE KEYS */;
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
INSERT INTO `schedule` VALUES (1,'1 course 1 group',1,1),(2,'1 course 2 group',1,2),(3,'1 course 3 group',1,3),(4,'1 course 4 group',1,4),(5,'2 course 1 group',1,5),(6,'2 course 2 group',1,6),(7,'2 course 3 group',1,7),(8,'2 course 4 group',1,8),(9,'3 course 1 group',1,9),(10,'3 course 2 group',1,10),(11,'3 course 3 group',1,11),(12,'3 course 4 group',1,12),(13,'4 course 1 group',1,13),(14,'4 course 2 group',1,14),(15,'4 course 3 group',1,15),(16,'4 course 4 group',1,16),(17,'5 course 1 group',1,17),(18,'5 course 2 group',1,18),(19,'5 course 3 group',1,19),(20,'5 course 4 group',1,20);
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
-- Table structure for table `type`
--

DROP TABLE IF EXISTS `type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type`
--

LOCK TABLES `type` WRITE;
/*!40000 ALTER TABLE `type` DISABLE KEYS */;
INSERT INTO `type` VALUES (1,'lecture'),(2,'practice'),(3,'seminar');
/*!40000 ALTER TABLE `type` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Natalie','Pakki','fecit@inbox.ru','wXwgAWh6YVeq8d8reFtWFQ==\n',15),(2,'Yulian','Haponenka','yulian@gmail.com','VKKc8nHi+6ZfpSvo5p9ucw==\n',13),(4,'Dima','Kachkov','kach@gmail.com','VKKc8nHi+6ZfpSvo5p9ucw==\n',13),(5,'Fuck','Fuck','kaka@gmail.com','VE95+NqmO2U/eWyFBXeB7A==\n',9),(6,'hui','huwi','fec@gmail.com','VKKc8nHi+6ZfpSvo5p9ucw==\n',10);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_has_schedule`
--

DROP TABLE IF EXISTS `user_has_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_has_schedule` (
  `user_id` int(11) NOT NULL,
  `schedule_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`schedule_id`),
  KEY `fk_user_has_schedule_schedule1_idx` (`schedule_id`),
  KEY `fk_user_has_schedule_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_schedule_schedule1` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_schedule_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_has_schedule`
--

LOCK TABLES `user_has_schedule` WRITE;
/*!40000 ALTER TABLE `user_has_schedule` DISABLE KEYS */;
INSERT INTO `user_has_schedule` VALUES (5,9),(6,10),(1,13),(4,13),(1,15);
/*!40000 ALTER TABLE `user_has_schedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-16 15:46:32
