-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 12, 2020 at 06:36 PM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dundalk_library`
--
CREATE DATABASE IF NOT EXISTS `dundalk_library_test` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dundalk_library_test`;

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_id` int(11) NOT NULL,
  `book_name` varchar(35) NOT NULL,
  `book_isbn` varchar(25) NOT NULL,
  `book_edition` varchar(35) NOT NULL,
  `book_description` varchar(200) NOT NULL,
  `author` varchar(35) NOT NULL,
  `publisher` varchar(35) NOT NULL,
  `quantityInStock` int(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `book`
--

INSERT INTO `book` (`book_id`, `book_name`, `book_isbn`, `book_edition`, `book_description`, `author`, `publisher`, `quantityInStock`) VALUES
(1, 'Computing Intro', '978-1-60309-025-4', '1.1', 'Introduction to Computing Book ', 'William Shakespeare', 'Penguin Random House', 60),
(2, 'Computing Intro 2', '978-1-60309-025-5', '2.1', 'Introduction to Computing Book 2', 'William Shakespeare', 'Penguin Random House', 50),
(3, 'Biology Intro', '678-1-60309-025-4', '3.1', 'Biology Intro', 'Emily Dickinson', 'HarperCollins', 57),
(4, 'Biology Intro 2', '778-1-60309-025-4', '4.1', 'Biology Intro 2', 'Emily Dickinson', 'HarperCollins', 47),
(5, 'Harry Potter', '978-1-60309-029-4', '7.1', 'Fantasy Wizard Book', 'H. P. Lovecraft', 'Macmillan Publishers', 12),
(6, 'Harry Potter 2', '978-1-60309-089-4', '5.8', 'Fantasy Wizard Book Epic', 'H. P. Lovecraft', 'Macmillan Publishers', 0);

-- --------------------------------------------------------

--
-- Table structure for table `loan`
--

CREATE TABLE `loan` (
  `loan_id` int(11) NOT NULL,
  `loan_user_id` int(11) NOT NULL,
  `loan_book_id` int(11) NOT NULL,
  `loan_started` datetime NOT NULL,
  `loan_ends` datetime NOT NULL,
  `loan_is_active` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan`
--

INSERT INTO `loan` (`loan_id`, `loan_user_id`, `loan_book_id`, `loan_started`, `loan_ends`, `loan_is_active`) VALUES
(1, 1, 3, '2020-10-06 09:19:10', '2020-10-09 07:00:00', 0),
(2, 1, 3, '2020-10-12 14:25:19', '2020-10-16 17:00:00', 1),
(3, 1, 1, '2020-10-12 13:23:00', '2020-10-14 09:19:00', 1),
(4, 4, 5, '2020-10-14 11:00:00', '2020-10-19 00:00:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `type` enum('Admin','Member') NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(30) NOT NULL,
  `email` varchar(40) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `phoneNumber` varchar(12) NOT NULL,
  `dateRegistered` timestamp NOT NULL DEFAULT current_timestamp(),
  `activeAccount` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `type`, `username`, `password`, `email`, `dateOfBirth`, `phoneNumber`, `dateRegistered`, `activeAccount`) VALUES
(1, 'Member', 'Sam', '1', 'sam@gmail.com', '2017-08-26', '0838568457', '2020-10-12 16:13:03', 1),
(2, 'Member', 'Arnas', '1', 'arnas@gmail.com', '2020-10-07', '0869542586', '2020-10-12 16:13:03', 1),
(3, 'Admin', 'AdminBob', '1', 'bob@gmail.com', '2020-10-08', '0896542568', '2020-10-12 16:16:04', 1),
(4, 'Member', 'Malo', '1', 'malo@gmail.com', '2020-10-16', '2589652365', '2020-10-12 16:20:05', 1),
(5, 'Admin', 'AdminJohn', '1', 'john@gmail.com', '2020-10-24', '0256985478', '2020-10-12 16:20:05', 1),
(6, 'Member', 'Sam1', '1', 'sam1@gmail.com', '2020-10-02', '0589653258', '2020-10-12 16:20:54', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`),
  ADD UNIQUE KEY `book_isbn` (`book_isbn`);

--
-- Indexes for table `loan`
--
ALTER TABLE `loan`
  ADD PRIMARY KEY (`loan_id`),
  ADD KEY `userLoanConstraint` (`loan_user_id`),
  ADD KEY `bookLoanConstraint` (`loan_book_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phoneNumber` (`phoneNumber`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `book_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `loan`
--
ALTER TABLE `loan`
  MODIFY `loan_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `loan`
--
ALTER TABLE `loan`
  ADD CONSTRAINT `bookLoanConstraint` FOREIGN KEY (`loan_book_id`) REFERENCES `book` (`book_id`),
  ADD CONSTRAINT `userLoanConstraint` FOREIGN KEY (`loan_user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
