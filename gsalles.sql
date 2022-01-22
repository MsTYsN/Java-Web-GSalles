-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 22, 2022 at 04:28 PM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gsalles`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `dateNaissance` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `email`, `password`, `nom`, `prenom`, `dateNaissance`) VALUES
(1, 'admin@gsalles.com', 'd8578edf8458ce06fbc5bb76a58c5ca4', 'MESTAOUI', 'Yassine', '1998-03-07 19:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `nom` varchar(50) NOT NULL,
  `prenom` varchar(50) NOT NULL,
  `cin` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `nom`, `prenom`, `cin`, `password`) VALUES
(1, 'Mestaoui', 'Yassine', 'EE815925', 'qwerty'),
(2, 'Ghoudan', 'Ayoub', 'EE777888', 'qwerty'),
(3, 'Asmih', 'Marouane', 'EE999555', 'qwerty'),
(5, 'Karkaih', 'Achraf', 'EE444555', 'tsattsat'),
(6, 'Idrissi', 'Ahmed', 'EE111222', 'qwerty');

-- --------------------------------------------------------

--
-- Table structure for table `creneau`
--

CREATE TABLE `creneau` (
  `id` int(11) NOT NULL,
  `heureDebut` varchar(50) NOT NULL,
  `heureFin` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `creneau`
--

INSERT INTO `creneau` (`id`, `heureDebut`, `heureFin`) VALUES
(1, '08:30', '10:00'),
(2, '10:30', '12:00'),
(3, '14:00', '15:30'),
(4, '15:30', '17:00'),
(5, '17:00', '18:30');

-- --------------------------------------------------------

--
-- Table structure for table `occupation`
--

CREATE TABLE `occupation` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `idClient` int(11) NOT NULL,
  `idSalle` int(11) NOT NULL,
  `idCreneau` int(11) NOT NULL,
  `etat` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `occupation`
--

INSERT INTO `occupation` (`id`, `date`, `idClient`, `idSalle`, `idCreneau`, `etat`) VALUES
(1, '2022-01-21 00:00:00', 1, 1, 1, 'En attente de validation'),
(2, '2022-01-21 00:00:00', 2, 1, 2, 'En attente de validation'),
(3, '2022-01-22 00:00:00', 6, 1, 1, 'En attente de validation');

-- --------------------------------------------------------

--
-- Table structure for table `salle`
--

CREATE TABLE `salle` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `capacite` int(11) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `salle`
--

INSERT INTO `salle` (`id`, `code`, `capacite`, `type`) VALUES
(1, 'CC1', 32, 'Pratique'),
(2, 'B4', 70, 'Théorique'),
(3, 'CC3', 30, 'Pratique'),
(5, 'B5', 30, 'Théorique'),
(6, 'CC4', 35, 'Théorique');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `creneau`
--
ALTER TABLE `creneau`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `occupation`
--
ALTER TABLE `occupation`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salle`
--
ALTER TABLE `salle`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `creneau`
--
ALTER TABLE `creneau`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `occupation`
--
ALTER TABLE `occupation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `salle`
--
ALTER TABLE `salle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
