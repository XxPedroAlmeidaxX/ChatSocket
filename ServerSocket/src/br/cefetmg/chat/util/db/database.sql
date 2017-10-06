/*
Created: 28/09/2017
Modified: 28/09/2017
Model: PostgreSQL 9.5
Database: PostgreSQL 9.5
*/


-- Create tables section -------------------------------------------------

-- Table Message

CREATE TABLE Message(
 idMessage Serial NOT NULL,
 textMessage Text NOT NULL,
 stateMessage Boolean NOT NULL,
 targetMessage Integer,
 senderUser Integer NOT NULL,
 room Integer NOT NULL,
 nameUser Text,
 nameTarget Text
)
;

-- Create indexes for table Message

CREATE INDEX IX_Relationship3 ON Message (targetMessage,nameUser)
;

CREATE INDEX IX_Relationship4 ON Message (senderUser,nameTarget)
;

CREATE INDEX IX_Relationship5 ON Message (room)
;

-- Add keys for table Message

ALTER TABLE Message ADD CONSTRAINT Key1 PRIMARY KEY (idMessage)
;

-- Table User

CREATE TABLE Users(
 ipUser Integer NOT NULL,
 nameUser Text NOT NULL,
 idUser Serial NOT NULL
)
;

-- Add keys for table User

ALTER TABLE Users ADD CONSTRAINT Key2 PRIMARY KEY (idUser)
;

-- Table Room

CREATE TABLE Room(
 idRoom Serial NOT NULL,
 nameRoom Character(25) NOT NULL,
 stateRoom Boolean NOT NULL,
 password Character(32) NOT NULL
)
;

-- Add keys for table Room

ALTER TABLE Room ADD CONSTRAINT Key3 PRIMARY KEY (idRoom)
;

-- Table User-Room

CREATE TABLE UsersRoom(
 idRoom Integer NOT NULL,
 idUser Integer NOT NULL
)
;

-- Add keys for table User-Room

ALTER TABLE UsersRoom ADD CONSTRAINT Key4 PRIMARY KEY (idRoom,idUser)
;
-- Create relationships section ------------------------------------------------- 

ALTER TABLE Message ADD FOREIGN KEY (targetMessage) REFERENCES Users (idUser) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE Message ADD FOREIGN KEY (senderUser) REFERENCES Users (idUser) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE Message ADD FOREIGN KEY (room) REFERENCES Room (idRoom) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE UsersRoom ADD FOREIGN KEY (idRoom) REFERENCES Room (idRoom) ON DELETE NO ACTION ON UPDATE NO ACTION
;

ALTER TABLE UsersRoom ADD FOREIGN KEY (idUser) REFERENCES Users (idUser) ON DELETE NO ACTION ON UPDATE NO ACTION
;
