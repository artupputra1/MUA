-- phpMyAdmin SQL Dump
-- version 4.9.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Nov 04, 2020 at 06:32 PM
-- Server version: 10.2.33-MariaDB-cll-lve
-- PHP Version: 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `belf5588_mua`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `name` varchar(35) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `name`, `username`, `password`) VALUES
(1, 'Administrator', 'admin', '21232f297a57a5a743894a0e4a801fc3');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(5) NOT NULL,
  `name` varchar(35) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Make Up'),
(2, 'Hair Do'),
(3, 'Nail Art'),
(4, 'Hena Art');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `user_id` int(11) NOT NULL,
  `title` varchar(25) NOT NULL,
  `content` varchar(99) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`id`, `date`, `user_id`, `title`, `content`) VALUES
(28, '2020-11-02 16:15:44', 13, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(27, '2020-11-02 16:14:51', 13, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(26, '2020-11-02 00:00:00', 14, 'Notifikasi Booking', 'Pembayaran Berhasil, Menunggu Konfirmasi dari Admin'),
(25, '2020-11-02 12:57:17', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(24, '2020-11-02 12:54:29', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(23, '2020-10-31 20:58:33', 2, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(22, '2020-10-31 20:54:52', 2, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(21, '2020-10-31 20:51:40', 2, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(20, '2020-10-31 00:00:00', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(19, '2020-10-31 09:56:28', 2, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(29, '2020-11-02 17:14:37', 13, 'Notifikasi Booking', 'Pembayaran Telah Dikonfirmasi, Silahkan Menunggu Jadwal Makeup yang Ditentukan'),
(30, '2020-11-02 22:52:54', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(31, '2020-11-02 22:53:10', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(32, '2020-11-02 00:00:00', 14, 'Notifikasi Booking', 'Pembayaran Berhasil, Menunggu Konfirmasi dari Admin'),
(33, '2020-11-02 22:55:20', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(34, '2020-11-02 22:55:41', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(35, '2020-11-02 23:01:09', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(36, '2020-11-02 23:01:34', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(37, '2020-11-02 23:10:13', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(38, '2020-11-02 23:10:13', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(39, '2020-11-02 23:10:33', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(40, '2020-11-02 23:20:36', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(41, '2020-11-02 00:00:00', 14, 'Notifikasi Booking', 'Pesanan Ditolak Oleh MUA'),
(42, '2020-11-02 23:26:03', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(43, '2020-11-02 00:00:00', 14, 'Notifikasi Booking', 'Pembayaran Berhasil, Menunggu Konfirmasi dari Admin'),
(44, '2020-11-02 23:27:48', 14, 'Notifikasi Booking', 'Pembayaran Telah Dikonfirmasi, Silahkan Menunggu Jadwal Makeup yang Ditentukan'),
(45, '2020-11-03 06:33:54', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(46, '2020-11-03 06:34:03', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(47, '2020-11-03 00:00:00', 14, 'Notifikasi Booking', 'Pembayaran Berhasil, Menunggu Konfirmasi dari Admin'),
(48, '2020-11-03 06:35:13', 14, 'Notifikasi Booking', 'Pembayaran Telah Dikonfirmasi, Silahkan Menunggu Jadwal Makeup yang Ditentukan'),
(49, '2020-11-03 11:26:18', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(50, '2020-11-03 11:26:47', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(51, '2020-11-03 00:00:00', 14, 'Notifikasi Booking', 'Pembayaran Berhasil, Menunggu Konfirmasi dari Admin'),
(52, '2020-11-03 11:27:50', 14, 'Notifikasi Booking', 'Pembayaran Telah Dikonfirmasi, Silahkan Menunggu Jadwal Makeup yang Ditentukan'),
(53, '2020-11-04 16:25:23', 14, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(54, '2020-11-04 16:30:34', 14, 'Notifikasi Booking', 'Pesanan Telah Dikonfirmasi, Silahkan Melanjutkan Pembayaran'),
(55, '2020-11-04 00:00:00', 14, 'Notifikasi Booking', 'Pembayaran Berhasil, Menunggu Konfirmasi dari Admin'),
(56, '2020-11-04 16:32:18', 14, 'Notifikasi Booking', 'Pembayaran Telah Dikonfirmasi, Silahkan Menunggu Jadwal Makeup yang Ditentukan');

-- --------------------------------------------------------

--
-- Table structure for table `notif_mua`
--

CREATE TABLE `notif_mua` (
  `id` int(11) NOT NULL,
  `date` datetime NOT NULL,
  `provider_id` int(11) NOT NULL,
  `title` varchar(35) NOT NULL,
  `content` varchar(99) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notif_mua`
--

INSERT INTO `notif_mua` (`id`, `date`, `provider_id`, `title`, `content`) VALUES
(1, '0000-00-00 00:00:00', 1, 'asdasd', 'asdasd'),
(2, '2020-10-31 20:51:40', 1, 'Notifikasi Booking', 'Pesanan Anda Telah Masuk, Menunggu Konfirmasi Dari MUA'),
(3, '2020-10-31 20:54:52', 15, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama sdfsd'),
(4, '2020-11-02 12:54:29', 15, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama fis'),
(5, '2020-11-02 16:14:51', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama fis'),
(6, '2020-11-02 22:52:54', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama fis'),
(7, '2020-11-02 22:55:20', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama oki'),
(8, '2020-11-02 23:01:09', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama lia'),
(9, '2020-11-02 23:10:13', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama odet'),
(10, '2020-11-02 23:10:13', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama odet'),
(11, '2020-11-02 23:20:36', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama lolaa'),
(12, '2020-11-03 06:33:54', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama hoi'),
(13, '2020-11-03 11:26:18', 1, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama sda'),
(14, '2020-11-04 16:25:23', 16, 'Notifikasi Booking', 'Ada Pesanan Baru Atas Nama oli');

-- --------------------------------------------------------

--
-- Table structure for table `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `provider_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL,
  `customer_name` varchar(25) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `amount_person` int(3) NOT NULL,
  `address` varchar(55) NOT NULL,
  `total_price` int(13) NOT NULL,
  `payment_proof` text NOT NULL,
  `status` varchar(25) NOT NULL,
  `date_limit` date NOT NULL,
  `time_limit` time NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order`
--

INSERT INTO `order` (`id`, `service_id`, `user_id`, `provider_id`, `date`, `time`, `customer_name`, `phone`, `amount_person`, `address`, `total_price`, `payment_proof`, `status`, `date_limit`, `time_limit`) VALUES
(31, 20, 14, 16, '2020-11-07', '06:00:00', 'oli', '67', 3, 'malang', 30000, '31.jpg', 'Dibatalkan', '2020-11-04', '17:30:34'),
(30, 11, 14, 1, '2020-11-12', '14:00:00', 'sda', '23', 2, 'sdfsdf', 60000, '30.jpg', 'Dibatalkan', '2020-11-03', '12:26:47'),
(29, 23, 14, 16, '2020-11-07', '18:00:00', 'hoi', '63', 2, 'malang', 100000, '29.jpg', 'Dibatalkan', '2020-11-03', '07:34:03'),
(28, 20, 14, 16, '2020-11-07', '14:00:00', 'lolaa', '56', 2, 'malang', 20000, '', 'Dibatalkan', '2020-11-03', '00:26:03'),
(27, 22, 14, 16, '2020-11-07', '11:00:00', 'odet', '78', 2, 'malang', 60000, '', 'Dibatalkan', '2020-11-03', '00:10:33'),
(23, 21, 14, 16, '2020-11-04', '12:00:00', 'fis', '01', 1, 'malang', 20000, '23.jpg', 'Dibatalkan', '2020-11-02', '23:53:10'),
(25, 22, 14, 16, '2020-11-03', '09:00:00', 'lia', '02', 2, 'malang', 60000, '', 'Dibatalkan', '2020-11-03', '00:01:34'),
(24, 23, 14, 16, '2020-11-03', '01:00:00', 'oki', '30', 1, 'malang', 50000, '24.jpg', 'Dibatalkan', '2020-11-02', '23:55:41'),
(26, 22, 14, 16, '2020-11-07', '11:00:00', 'odet', '78', 2, 'malang', 60000, '', 'Pesanan Ditolak', '0000-00-00', '00:00:00'),
(22, 20, 13, 16, '2020-11-03', '12:00:00', 'fis', '07', 1, 'malang', 5000, '', 'Dibatalkan', '2020-11-02', '17:15:44'),
(21, 16, 14, 15, '2020-11-03', '12:00:00', 'fis', '09', 2, 'malang', 150000, '21.jpg', 'Dibatalkan', '2020-11-02', '13:57:17');

-- --------------------------------------------------------

--
-- Table structure for table `portofolio_provider`
--

CREATE TABLE `portofolio_provider` (
  `id` int(5) NOT NULL,
  `provider_id` int(5) NOT NULL,
  `link` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `portofolio_provider`
--

INSERT INTO `portofolio_provider` (`id`, `provider_id`, `link`) VALUES
(19, 16, 'http://belajarkoding.xyz/mua/upload/portofolio/57.jpg'),
(18, 16, 'http://belajarkoding.xyz/mua/upload/portofolio/86.jpg'),
(17, 16, 'http://belajarkoding.xyz/mua/upload/portofolio/70.jpg'),
(16, 15, 'http://belajarkoding.xyz/mua/upload/portofolio/79.jpg'),
(15, 15, 'http://belajarkoding.xyz/mua/upload/portofolio/34.jpg'),
(14, 1, 'http://belajarkoding.xyz/mua/upload/portofolio/37.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `providers`
--

CREATE TABLE `providers` (
  `id_mua` int(5) NOT NULL,
  `owner` varchar(35) NOT NULL,
  `name_business` varchar(35) NOT NULL,
  `address` text NOT NULL,
  `phone` varchar(15) NOT NULL,
  `mua_since` varchar(15) NOT NULL,
  `member_since` date NOT NULL,
  `Job_done` int(5) NOT NULL,
  `user_id` int(5) NOT NULL,
  `image` text NOT NULL,
  `info` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `providers`
--

INSERT INTO `providers` (`id_mua`, `owner`, `name_business`, `address`, `phone`, `mua_since`, `member_since`, `Job_done`, `user_id`, `image`, `info`) VALUES
(1, 'Muna1', 'MUA 11', 'Malang1', '1234', '2020-11-04', '2020-10-03', 2, 2, '2020-11-04 18:16:17._.MUA 11.jpg', 'dsfsdf'),
(14, 'mua 4', 'hzhbfb', 'vjjdh', '', '', '2020-10-27', 0, 10, '', ''),
(13, 'asd', 'sdf', 'sdfsdf', '', '', '2020-10-27', 0, 4, '', ''),
(15, 'jeni', 'jeni_makeup', 'malang', '', '', '2020-10-27', 0, 13, 'jeni_makeup.jpg', ''),
(16, 'fis', 'fis_make up', 'malang', '00000', '', '2020-11-02', 2, 14, '2020-11-04 17:06:09._.fis_make up.jpg', 'fix order = dp 50% ');

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `id` int(11) NOT NULL,
  `service_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `review` text NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`id`, `service_id`, `user_id`, `review`) VALUES
(1, 1, 2, 'tes'),
(2, 0, 0, ''),
(3, 1, 2, 'asdasdsad'),
(4, 4, 2, 'sdfdsfsdf'),
(5, 16, 14, 'mantap'),
(6, 23, 14, 'oke'),
(7, 23, 14, 'bagus');

-- --------------------------------------------------------

--
-- Table structure for table `service_provider`
--

CREATE TABLE `service_provider` (
  `id` int(5) NOT NULL,
  `provider_id` int(5) NOT NULL,
  `service` varchar(35) NOT NULL,
  `price` int(15) NOT NULL,
  `information` text NOT NULL,
  `duration` varchar(99) NOT NULL,
  `category_id` int(5) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service_provider`
--

INSERT INTO `service_provider` (`id`, `provider_id`, `service`, `price`, `information`, `duration`, `category_id`) VALUES
(17, 15, 'hena art', 100000, '2-3 jam', '', 4),
(16, 15, 'nail art', 150000, '1-2jam', '', 3),
(14, 15, 'party', 200000, 'include hair do', '1 jam', 1),
(13, 14, 'bxgv', 3888, ' ncnc', '', 1),
(12, 1, 'Wisuda', 30000, '3 - 4 Jam', '', 1),
(11, 1, 'Wisuda', 30000, '3 - 4 Jam', '16', 1),
(18, 15, 'prewedding', 550000, 'touch up', 'touch up', 1),
(19, 15, 'hair do', 150000, 'hair styling', 'hair styling', 2),
(20, 16, 'graduation', 10000, 'include hair do/hijab do', '1-2 jam', 1),
(21, 16, 'hairstyling', 20000, '', '', 2),
(22, 16, 'nail art', 30000, 'unik dan lucu', 'unik dan lucu', 3),
(23, 16, 'hena art', 50000, 'red color', 'red color', 4);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(5) NOT NULL,
  `username` varchar(15) NOT NULL,
  `password` text NOT NULL,
  `name` varchar(35) NOT NULL,
  `email` varchar(35) NOT NULL,
  `phone` varchar(13) NOT NULL,
  `fcm_id` text NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `name`, `email`, `phone`, `fcm_id`, `image`) VALUES
(2, 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'User MUA2', 'user@gmail.com', 'as', 'dqFXNPFfW10:APA91bFX2sBV016YwEOtsQT2S8CMdJCpJ4Sw1ObCz4eHF2hxNpQwX3d7HE34WDgD_sKSRp2VPy4mNzr5oq2cZWQR3QJVAp2YFl9s6rzQLY-NP7vKKyqEEOFWSGahChVIYITeIXuyIKJB', '2020-11-02 16:58:03._.User MUA2.jpg'),
(3, 'tuptup', '66437b52eb3630631dac9e6a1ab89ba8', 'tup', 'tup@gmail.com', '12313', '', ''),
(4, 'tea', '28b662d883b6d76fd96e4ddc5e9ba780', 'tea', 'tes', 'tea', 'dqFXNPFfW10:APA91bFX2sBV016YwEOtsQT2S8CMdJCpJ4Sw1ObCz4eHF2hxNpQwX3d7HE34WDgD_sKSRp2VPy4mNzr5oq2cZWQR3QJVAp2YFl9s6rzQLY-NP7vKKyqEEOFWSGahChVIYITeIXuyIKJB', ''),
(5, 'qqq', 'b2ca678b4c936f905fb82f2733f5297f', 'qqq', 'qqq', 'qqq', '', ''),
(6, 'etty', '5b2092be6e6f82bcac816ff988013458', 'etty', 'etty', 'etty', 'dfNhGpGQDkA:APA91bE7TNdJYK2_GDouQjrlzARCdI62FMJ6TQNUbgtb8BL5NSEprCObqNy5IGielywox1gpJQKbycvy0jscvGitVv_fEgG6WfYIdYzRf1NQywQyS8Euk8HUdG6bVL_-6Oni8blwlCzH', ''),
(7, 'konco', 'c3334b8160bd0dd4533357a2d10c7d5e', 'konco', 'konco', 'konco', 'cZo9ooNNdnE:APA91bG4s0u9gAhG0WY4YUvndl0umU0REY8B9HtTn2jbsdUah37SHMUtnJA0lAXSHLe5RHKS4HsiefXftakyLhT_6aVYvuR3YiO5eduBEBx536KATj8oM5q9vVDSDCXsd_OSXkC3zhgA', ''),
(8, 'asda', '0aa1ea9a5a04b78d4581dd6d17742627', 'asda', 'asdas', 'asda', '', ''),
(9, 'renamua', '6fd469edf7f076cf0258942b23b62f1c', 'renamua', 'renamua', 'renamua', 'cZo9ooNNdnE:APA91bG4s0u9gAhG0WY4YUvndl0umU0REY8B9HtTn2jbsdUah37SHMUtnJA0lAXSHLe5RHKS4HsiefXftakyLhT_6aVYvuR3YiO5eduBEBx536KATj8oM5q9vVDSDCXsd_OSXkC3zhgA', ''),
(10, 'qwe', '76d80224611fc919a5d54f0ff9fba446', 'qwe', 'qwe', 'qwe', 'cHiMqrXVeIM:APA91bF2YEl5AGE-Ro3odCvwwYDAs6Lwi87s16WoekHbRPN8YGuE4RdGblov3f1GISIukfijFm38z2cyyhhe3Qb7mJg2ClyhbzBuagke9AH2291YtpUtS-gzXHh_Jn0hSkaQMrtjCkFq', ''),
(11, 'titin12', '9c73f90b46c03eb06cd43394339e82ca', 'titin12', 'titin12', 'titin12', 'f5oG1R_L5w0:APA91bE4SzsTmTc5z4eQgqe2P0DQRR1hl-4vQd3YjIDt45h9gEZscWFh30o_iteOjm08yMGZGEW3m2qJkFeyWKT4DOPZkGrgL_6e-u-1NzHYMElTo3RLaPTt1GLG-Xpzgx3hsG3YLpXt', ''),
(12, 'coba', 'c3ec0f7b054e729c5a716c8125839829', 'coba', 'coba', 'coba', 'cHiMqrXVeIM:APA91bF2YEl5AGE-Ro3odCvwwYDAs6Lwi87s16WoekHbRPN8YGuE4RdGblov3f1GISIukfijFm38z2cyyhhe3Qb7mJg2ClyhbzBuagke9AH2291YtpUtS-gzXHh_Jn0hSkaQMrtjCkFq', ''),
(13, 'jeni12', 'f5686e186fd3f122ea116fef820956db', 'jeni', 'jeni@gmail.com', '098745632108', 'dbRu909XLYw:APA91bHfXnGFFXm7JD0FGG0QzEvsYM4Ect2uHirqW_BvPOh_X3gjnfDh6t_H0rhNHlO5tbCxHjXpH1ixh-vqg78UQv7vY5q-3GR5yqTnvhJDhpzIWg2g3t8YFSZcc6awEz_MXixRJ5lZ', 'jeni.jpg'),
(14, 'fis', '37ab815c056b5c5f600f6ac93e486a78', 'fis', 'fis@gmail.com', '098765432112', 'e6L5ygRY9QY:APA91bFjp4tyOytyVxe0Ta-p5qHgespC-X6KxMgwQDSe-qx0SVVR7AgX5auWy9iUNnVNzGm_mKIk-COkUeuIilCwOSNpyHbHL6XgPg-A2rUJpuj-jvm-ORPSEiyqW4aWDD8PkdS43066', '2020-11-02 19:34:00._.fis.jpg');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `notif_mua`
--
ALTER TABLE `notif_mua`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `portofolio_provider`
--
ALTER TABLE `portofolio_provider`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `providers`
--
ALTER TABLE `providers`
  ADD PRIMARY KEY (`id_mua`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `service_provider`
--
ALTER TABLE `service_provider`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `notif_mua`
--
ALTER TABLE `notif_mua`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `portofolio_provider`
--
ALTER TABLE `portofolio_provider`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `providers`
--
ALTER TABLE `providers`
  MODIFY `id_mua` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `service_provider`
--
ALTER TABLE `service_provider`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
