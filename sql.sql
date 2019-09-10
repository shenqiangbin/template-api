SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserID` int(11) NOT NULL AUTO_INCREMENT,
  `UserCode` varchar(64) DEFAULT NULL,
  `UserName` varchar(64) DEFAULT NULL,
  `Password` varchar(64) DEFAULT NULL,
  `Status` int(11) DEFAULT NULL,
  `CreateUser` varchar(64) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `ModifyUser` varchar(64) DEFAULT NULL,
  `ModifyTime` datetime DEFAULT NULL,
  PRIMARY KEY (`UserID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;