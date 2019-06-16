-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 16, 2019 at 02:15 PM
-- Server version: 5.7.24
-- PHP Version: 7.2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rapizz`
--

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int(4) NOT NULL AUTO_INCREMENT,
  `isAdmin` int(1) NOT NULL DEFAULT '0',
  `nom` varchar(250) NOT NULL,
  `login` varchar(250) NOT NULL,
  `mdp` varchar(250) NOT NULL,
  `abonne` int(1) NOT NULL DEFAULT '0',
  `solde` float NOT NULL DEFAULT '0',
  `nb_pizza_commande` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`idClient`, `isAdmin`, `nom`, `login`, `mdp`, `abonne`, `solde`, `nb_pizza_commande`) VALUES
(1, 1, 'admin', 'admin', 'admin', 1, 916, 10),
(2, 0, 'Billy', 'Billy', 'billy78', 1, 80.36, 2),
(16, 0, 'testDemo', 'testDemo', 'test', 0, 82, 2);

-- --------------------------------------------------------

--
-- Table structure for table `commande`
--

DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `idCommande` int(11) NOT NULL AUTO_INCREMENT,
  `livreur` int(11) NOT NULL,
  `client` int(11) NOT NULL,
  `prix` float NOT NULL,
  PRIMARY KEY (`idCommande`),
  KEY `livreur` (`livreur`),
  KEY `client` (`client`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `commande`
--

INSERT INTO `commande` (`idCommande`, `livreur`, `client`, `prix`) VALUES
(15, 2, 16, 18);

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE IF NOT EXISTS `ingredient` (
  `idIngredient` int(4) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  PRIMARY KEY (`idIngredient`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`idIngredient`, `nom`) VALUES
(1, 'sauce tomate'),
(2, 'mozzarella'),
(3, 'jambon blanc '),
(4, 'fromage de chèvre'),
(5, 'champignon frais'),
(6, 'merguez'),
(7, 'émincé de poulet rôti'),
(8, 'oignons frais '),
(9, 'sauce Samouraï'),
(10, 'ananas'),
(11, 'jambon'),
(12, 'poivrons verts'),
(13, 'haché au boeuf'),
(14, 'piments Mexicain'),
(15, 'saucisse pepperoni'),
(16, 'lardons'),
(17, 'sauce BBQ'),
(18, 'pommes de terre'),
(19, 'fromage à raclette'),
(20, 'crème fraîche légère '),
(21, 'miel'),
(22, 'thon'),
(23, 'olives noires'),
(24, 'sauce kebab'),
(25, 'fourme d\'Ambert AOP'),
(26, 'saumon de Norvège'),
(27, 'persillade'),
(28, 'tomates fraîches');

-- --------------------------------------------------------

--
-- Table structure for table `ingredientsparpizza`
--

DROP TABLE IF EXISTS `ingredientsparpizza`;
CREATE TABLE IF NOT EXISTS `ingredientsparpizza` (
  `idIngredient` int(11) NOT NULL,
  `idPizza` int(11) NOT NULL,
  KEY `idIngredient` (`idIngredient`),
  KEY `idPizza` (`idPizza`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ingredientsparpizza`
--

INSERT INTO `ingredientsparpizza` (`idIngredient`, `idPizza`) VALUES
(2, 1),
(2, 5),
(9, 5),
(8, 5),
(7, 5),
(6, 5),
(2, 6),
(11, 6),
(5, 6),
(5, 6),
(2, 7),
(11, 7),
(10, 7),
(10, 7),
(2, 8),
(6, 8),
(6, 8),
(2, 9),
(5, 9),
(8, 9),
(12, 9),
(28, 9),
(1, 10),
(2, 10),
(13, 10),
(8, 10),
(14, 10),
(28, 10),
(1, 11),
(2, 11),
(2, 11),
(15, 11),
(15, 11),
(17, 12),
(2, 12),
(11, 12),
(13, 12),
(16, 12),
(5, 12),
(8, 12);

-- --------------------------------------------------------

--
-- Table structure for table `livraison`
--

DROP TABLE IF EXISTS `livraison`;
CREATE TABLE IF NOT EXISTS `livraison` (
  `commande` int(11) NOT NULL,
  `ts_commande` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ts_livraison` timestamp NULL DEFAULT NULL,
  KEY `commande` (`commande`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livraison`
--

INSERT INTO `livraison` (`commande`, `ts_commande`, `ts_livraison`) VALUES
(15, '2019-06-05 20:55:13', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `livreur`
--

DROP TABLE IF EXISTS `livreur`;
CREATE TABLE IF NOT EXISTS `livreur` (
  `idLivreur` int(4) NOT NULL AUTO_INCREMENT,
  `nom` varchar(250) NOT NULL,
  `vehicule` int(4) DEFAULT NULL,
  `statut` int(3) NOT NULL,
  PRIMARY KEY (`idLivreur`),
  KEY `vehicule` (`vehicule`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `livreur`
--

INSERT INTO `livreur` (`idLivreur`, `nom`, `vehicule`, `statut`) VALUES
(1, 'Billy', 1, 1),
(2, 'Bruce Wayne', 2, 1),
(3, 'Peter Quill', 3, 0),
(4, 'Steve Rogers', 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pizza`
--

DROP TABLE IF EXISTS `pizza`;
CREATE TABLE IF NOT EXISTS `pizza` (
  `idPizza` int(4) NOT NULL AUTO_INCREMENT,
  `nom` varchar(50) NOT NULL,
  `prix_normal` float NOT NULL,
  PRIMARY KEY (`idPizza`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pizza`
--

INSERT INTO `pizza` (`idPizza`, `nom`, `prix_normal`) VALUES
(1, 'Margherita', 9),
(5, 'Samouraï', 8),
(6, 'Queen', 8),
(7, 'Hawaïenne jambon', 8),
(8, 'Orientale', 8),
(9, 'Végétarienne', 8),
(10, 'Spicy Hot One', 8),
(11, 'Pepperoni Lovers', 8),
(12, 'Chicken BBQ', 9),
(14, 'Montagnarde', 9),
(15, 'Chèvre Miel', 9),
(17, 'Suprême', 9),
(18, 'Raclette', 9),
(19, 'Campagnarde', 9),
(20, 'Provençale', 9),
(21, 'BPM', 9),
(22, 'Kasbah', 9),
(23, '4 Fromages', 9),
(24, 'Nordique', 9);

-- --------------------------------------------------------

--
-- Table structure for table `pizzaparcommande`
--

DROP TABLE IF EXISTS `pizzaparcommande`;
CREATE TABLE IF NOT EXISTS `pizzaparcommande` (
  `idCommande` int(11) NOT NULL,
  `idPizza` int(11) NOT NULL,
  KEY `ix_idCommande` (`idCommande`),
  KEY `ix_idPizza` (`idPizza`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pizzaparcommande`
--

INSERT INTO `pizzaparcommande` (`idCommande`, `idPizza`) VALUES
(15, 1),
(15, 1);

-- --------------------------------------------------------

--
-- Table structure for table `typevehicule`
--

DROP TABLE IF EXISTS `typevehicule`;
CREATE TABLE IF NOT EXISTS `typevehicule` (
  `idTypeVehicule` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(250) NOT NULL,
  PRIMARY KEY (`idTypeVehicule`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `typevehicule`
--

INSERT INTO `typevehicule` (`idTypeVehicule`, `nom`) VALUES
(1, 'moto'),
(2, 'voiture'),
(3, 'trotinette');

-- --------------------------------------------------------

--
-- Table structure for table `vehicule`
--

DROP TABLE IF EXISTS `vehicule`;
CREATE TABLE IF NOT EXISTS `vehicule` (
  `idVehicule` int(4) NOT NULL AUTO_INCREMENT,
  `nom` varchar(250) NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`idVehicule`),
  KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vehicule`
--

INSERT INTO `vehicule` (`idVehicule`, `nom`, `type`) VALUES
(1, 'moto 1', 1),
(2, 'BatMoto', 1),
(3, 'voiture 1', 2),
(4, 'trotinette 1', 3),
(5, 'BatTrotinette', 3);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `commande`
--
ALTER TABLE `commande`
  ADD CONSTRAINT `fk_client` FOREIGN KEY (`client`) REFERENCES `client` (`idClient`),
  ADD CONSTRAINT `fk_livreur` FOREIGN KEY (`livreur`) REFERENCES `livreur` (`idLivreur`);

--
-- Constraints for table `ingredientsparpizza`
--
ALTER TABLE `ingredientsparpizza`
  ADD CONSTRAINT `fk_idIngredient` FOREIGN KEY (`idIngredient`) REFERENCES `ingredient` (`idIngredient`),
  ADD CONSTRAINT `fk_idPizza` FOREIGN KEY (`idPizza`) REFERENCES `pizza` (`idPizza`);

--
-- Constraints for table `livraison`
--
ALTER TABLE `livraison`
  ADD CONSTRAINT `fk_commande` FOREIGN KEY (`commande`) REFERENCES `commande` (`idCommande`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `livreur`
--
ALTER TABLE `livreur`
  ADD CONSTRAINT `fk_vehicule` FOREIGN KEY (`vehicule`) REFERENCES `vehicule` (`idVehicule`);

--
-- Constraints for table `pizzaparcommande`
--
ALTER TABLE `pizzaparcommande`
  ADD CONSTRAINT `fk_idCommandeidCommande` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_idPizzaidPizza` FOREIGN KEY (`idPizza`) REFERENCES `pizza` (`idPizza`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `vehicule`
--
ALTER TABLE `vehicule`
  ADD CONSTRAINT `fk_type` FOREIGN KEY (`type`) REFERENCES `typevehicule` (`idTypeVehicule`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
