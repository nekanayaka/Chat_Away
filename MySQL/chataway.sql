CREATE DATABASE IF NOT EXISTS chataway;
 
USE chataway;

CREATE TABLE user(
        userID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(20) NOT NULL,
    	password VARCHAR(100) NOT NULL,
    	email VARCHAR(30) NOT NULL,
    	banStatus decimal,
    	requestingChat boolean,
    	accountLevel VARCHAR(10) NOT NULL,
    	longitude DOUBLE NOT NULL,
		latitude DOUBLE NOT NULL
     )
     ENGINE=INNODB;



 CREATE TABLE message(
     	messageID INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
        senderID INT NOT NULL,
        receiverID INT NOT NULL,
        createdAt DATETIME NOT NULL,
     	message VARCHAR(500) NOT NULL,
        FOREIGN KEY (senderID) REFERENCES user (userID) ON DELETE CASCADE,
        FOREIGN KEY (receiverID) REFERENCES user (userID) ON DELETE CASCADE
     )
     ENGINE=INNODB;


CREATE TABLE groupchat(
        groupID INT NOT NULL AUTO_INCREMENT,
        userID INT NOT NULL,
    	baseCoordinates VARCHAR(50) NOT NULL,
        PRIMARY KEY (groupID, userID),
        FOREIGN KEY (userID) REFERENCES user (userID)
        
     )
     ENGINE=INNODB;