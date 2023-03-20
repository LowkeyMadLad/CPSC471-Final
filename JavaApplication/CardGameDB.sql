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
    -- 0 = Body, 1 = Hand
    `type` BINARY,

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
    gameID INT UNSIGNED NOT NULL,
    `datetime` DATETIME,
    winner VARCHAR(255),
    loser VARCHAR(255),

    PRIMARY KEY (gameID),
    FOREIGN KEY (winner) REFERENCES Player(username),
    FOREIGN KEY (loser) REFERENCES Player(username)
);

-- weak entities

DROP TABLE IF EXISTS `Season_Peak`;
CREATE TABLE Season_Peak (
    season INT NOT NULL,
    player VARCHAR(255),

    gamesplayed INT,
    peakMMR INT,

    FOREIGN KEY (player) REFERENCES Player(username)
);

DROP TABLE IF EXISTS `Move`;
CREATE TABLE Move (
    seed VARCHAR(255),
    gameID INT UNSIGNED NOT NULL,

    FOREIGN KEY (gameID) REFERENCES Game(gameID)
);

-- relation tables

DROP TABLE IF EXISTS `HasActive`;
CREATE TABLE HasActive (
    player VARCHAR(255),
    cardID INT UNSIGNED NOT NULL,

    FOREIGN KEY (player) REFERENCES Player(username),
    FOREIGN KEY (cardID) REFERENCES Card(cardID)
);

DROP TABLE IF EXISTS `Bans`;
CREATE TABLE Bans (
    player VARCHAR(255),
    `admin` VARCHAR(255),

    FOREIGN KEY (player) REFERENCES Player(username),
    FOREIGN KEY (admin) REFERENCES Admin(username)
);


-- BASE GAME CARDS
-- Type binary types: 0=body, 1=hand
INSERT INTO `Card` (`name`, `type`, `melee`, `range`, `guard`) VALUES (
    -- body cards
    ('Knight', 0, 70, 0, 30),
    ('Barbarian', 0, 100, 0, 0),
    ('Archer', 0, 0, 100, 0),
    ('Shield guy idfk lol', 0, 0, 0, 100),
    ('Assassin', 0, 50, 50, 0),

    -- hand cards
    ('Sword', 1, 100, 0, 0),
    ('Bow', 1, 0, 100, 0),
    ('Shield', 1, 0, 0, 100),
    ('Scythe', 1, 50, 0, 50),
    ('Kunai', 1, 30, 70, 0),
    ('Turret', 1, 0, 60, 40)
);