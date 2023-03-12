-- The database for our beautiful card game :D

DROP DATABASE IF EXISTS CARD_DATABASE;
CREATE DATABASE CARD_DATABASE; 
USE CARD_DATABASE;

-- DROP TABLE IF EXISTS `User`;
-- CREATE TABLE User (
--     username VARCHAR(255),
--     `password` VARCHAR(255),
--     PRIMARY KEY (username)
-- );

DROP TABLE IF EXISTS `Player`;
CREATE TABLE Player (
    username VARCHAR(255),
    `password` VARCHAR(255),

    wins INT NOT NULL,
    losses INT NOT NULL,
    mmr INT NOT NULL,
    displayname VARCHAR(255),

    PRIMARY KEY (username)
);

DROP TABLE IF EXISTS `Admin`;
CREATE TABLE Admin (
    username VARCHAR(255),
    `password` VARCHAR(255),

    PRIMARY KEY (username)
);

DROP TABLE IF EXISTS `Card`;
CREATE TABLE Player (
    cardID INT UNSIGNED NOT NULL,
    
    `name` VARCHAR(255),
    `type` VARCHAR(255),

    melee INT NOT NULL,
    `range` INT NOT NULL,
    guard INT NOT NULL,

    PRIMARY KEY (cardID)
);

DROP TABLE IF EXISTS `Unique_Card`;
CREATE TABLE Unique_Card (
    cardID INT UNSIGNED NOT NULL,
    -- admin who verified the card
    verifiedBy VARCHAR(255),
    `owner` VARCHAR(255),

    FOREIGN KEY (cardID) REFERENCES Card(cardID),
    FOREIGN KEY (verifiedBy) REFERENCES Admin(username),
    FOREIGN KEY (owner) REFERENCES Player(username)
);

DROP TABLE IF EXISTS `Game`;
CREATE TABLE Game (

);

DROP TABLE IF EXISTS `Season_Peak`;
CREATE TABLE Season_Peak (

);

DROP TABLE IF EXISTS `Move`;
CREATE TABLE Move (

);