--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
CREATE TABLE `app_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role_id` bigint NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`),
  UNIQUE KEY `app_user_login_uindex` (`login`)
);

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  PRIMARY KEY (`id`)
);

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
CREATE TABLE `messages` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `topic` varchar(255) NOT NULL,
  `message` varchar(1000) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `isAnswered` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON UPDATE CASCADE
);

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
CREATE TABLE `section` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(500) NOT NULL,
  `description` varchar(1000) NOT NULL,
  `conference_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `section_ibfk_1` (`conference_id`),
  CONSTRAINT `section_ibfk_1` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

--
-- Table structure for table `users_sections`
--

DROP TABLE IF EXISTS `users_sections`;
CREATE TABLE `users_sections` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `section_id` bigint NOT NULL,
  `state_id` bigint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `users_sections_ibfk_1` (`user_id`),
  KEY `users_sections_ibfk_2` (`section_id`),
  CONSTRAINT `users_sections_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `users_sections_ibfk_2` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
);
