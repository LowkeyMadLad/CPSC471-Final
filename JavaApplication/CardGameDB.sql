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
CREATE TABLE Card (
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
    -- IDs will be UUIDs
    gameID VARCHAR(255),
    `datetime` DATETIME,

    player1 VARCHAR(255),
    player2 VARCHAR(255),

    p1body INT UNSIGNED NOT NULL,
    p1hand INT UNSIGNED NOT NULL,
    p2body INT UNSIGNED NOT NULL,
    p2hand INT UNSIGNED NOT NULL,

    PRIMARY KEY (gameID),
    FOREIGN KEY (player1) REFERENCES Player(username),
    FOREIGN KEY (player2) REFERENCES Player(username),
    FOREIGN KEY (p1hand) REFERENCES Card(cardID),
    FOREIGN KEY (p1body) REFERENCES Card(cardID),
    FOREIGN KEY (p2body) REFERENCES Card(cardID),
    FOREIGN KEY (p2hand) REFERENCES Card(cardID)
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
    gameID VARCHAR(255),

    FOREIGN KEY (gameID) REFERENCES Game(gameID)
);

-- relation tables

DROP TABLE IF EXISTS `Deck`;
CREATE TABLE Deck (
    player VARCHAR(255),
    cardID INT UNSIGNED NOT NULL,

    FOREIGN KEY (player) REFERENCES Player(username),
    FOREIGN KEY (cardID) REFERENCES Card(cardID)
);

DROP TABLE IF EXISTS `Bans`;
CREATE TABLE Bans (
    player VARCHAR(255),
    `admin` VARCHAR(255),
    `datetime` DATETIME,

    FOREIGN KEY (player) REFERENCES Player(username),
    FOREIGN KEY (admin) REFERENCES Admin(username)
);


-- BASE GAME CARDS
-- Type binary types: 0=body, 1=hand
INSERT INTO `Card` (`cardID`, `name`, `type`, `melee`, `range`, `guard`) VALUES 
    -- body cards
    (001, 'Knight', 0, 70, 0, 30),      -- 
    (002, 'Barbarian', 0, 100, 0, 0),
    (003, 'Archer', 0, 0, 100, 0),
    (004, 'Paladin', 0, 0, 0, 100),
    (005, 'Assassin', 0, 50, 50, 0),
    (006, 'Tower', 0, 0, 20, 80),
    (007, 'Ace', 0, 40, 30, 30),
    (008, 'Steve', 0, 20, 60, 20),

    -- hand cards
    (101, 'Sword', 1, 100, 0, 0),       -- done
    (102, 'Bow', 1, 0, 100, 0),         -- done
    (103, 'Shield', 1, 0, 0, 100),      -- done
    (104, 'Scythe', 1, 50, 0, 50),      -- done
    (105, 'Kunai', 1, 30, 70, 0),       -- done
    (106, 'Ballista', 1, 0, 60, 40),    --  
    (107, 'Halberd', 1, 40, 40, 20),    -- done
    (108, 'Bomb', 1, 60, 30, 10)        -- done
;

-- test data
INSERT INTO `Player` (`username`, `password`, `wins`, `losses`, `mmr`, `displayname`) VALUES 
    ('dannyp', 'swag', 20, 5, 1000, 'pistachio'),
    ('bob', 'bbb222', 3, 42, 150, 'bobba1'),
    ('a1b2c3', '000', 10, 10, 500, 'testguy')
;
INSERT INTO `Admin` (`username`, `password`) VALUES 
    ('admin', 'pass'),
    ('prof', 'cpsc')
;
