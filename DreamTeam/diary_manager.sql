-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 2016-04-17 16:28:18
-- 服务器版本： 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `diary_manager`
--

-- --------------------------------------------------------

--
-- 表的结构 `diary`
--

CREATE TABLE IF NOT EXISTS `diary` (
  `id` int(11) NOT NULL COMMENT '日记编号',
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `title` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '标题',
  `contenturl` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '内容URL',
  `picurl` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '图片地址',
  `time` date NOT NULL COMMENT '日期',
  `weather` enum('晴','阴','雨','雪','风') COLLATE utf8_bin NOT NULL COMMENT '天气',
  `mood` enum('开心','悲伤','害怕','愤怒','郁闷') COLLATE utf8_bin NOT NULL COMMENT '心情'
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- 转存表中的数据 `diary`
--

INSERT INTO `diary` (`id`, `name`, `title`, `contenturl`, `picurl`, `time`, `weather`, `mood`) VALUES
(1, 'wxq', 'hello', 'DreamTeam/diary/u/u4/新建文本文档.txt', 'DreamTeam/diary/u/u4/1.jpg', '2015-12-12', '雨', '开心'),
(2, 'wxq', 'hello111', 'DreamTeam/diary/u/u4/新建文本文档.txt', 'DreamTeam/diary/u/u4/1.jpg', '2015-12-12', '雨', '开心'),
(3, 'wxq', 'hellqqo', 'DreamTeam/diary/u/u4/新建文本文档.txt', 'DreamTeam/diary/u/u4/1.jpg', '2015-12-12', '雨', '开心'),
(4, 'wxq', 'hi', 'uytt', 'xzz', '2015-12-12', '雨', '开心'),
(5, 'wxq', 'hi', 'uytt', 'xzz', '2015-12-12', '雨', '开心'),
(6, 'wxq', 'hi', 'uytt', 'xzz', '2015-12-12', '雨', '开心'),
(7, 'wxq', 'hi', 'uytt', 'xzz', '2015-12-12', '雨', '开心'),
(8, 'wxq', 'hi', 'uytt', 'xzz', '2015-12-12', '雨', '开心'),
(9, 'wxq', 'hi', 'uytt', 'xzz', '2015-12-12', '雨', '开心'),
(10, 'abc', '123', 'C:\\Users\\snow\\Documents\\1.txt', 'C:\\Users\\snow\\Documents\\1_1jpg,null', '2015-12-30', '晴', '开心'),
(11, 'abc', 'de', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\abc\\1.txt', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\abc\\1_1jpg,null', '2015-12-30', '晴', '开心'),
(12, 'abc', '123', 'C:\\Users\\snow\\Documents\\3.txt', 'C:\\Users\\snow\\Documents\\3_1jpg,null', '2015-12-12', '晴', '开心'),
(13, 'abc', 'rrrrrr', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\abc\\ere.txt', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\abc\\ere_1.jpg,null', '2015-12-30', '雪', '郁闷'),
(14, 'abc', '你好', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\abc\\ewwe.txt', 'null,null', '2015-12-30', '晴', '开心'),
(15, 'qwe', 'hello', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\qwe\\q.txt', 'E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\qwe\\q_1.jpg,E:\\大三上\\JAVA\\workspace\\DreamTeam\\diary\\qwe\\q_2.jpg', '2015-12-30', '晴', '开心');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `name` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '用户名',
  `password` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '口令'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`name`, `password`) VALUES
('123', '123'),
('aaa', 'aaa'),
('abc', '12311'),
('qwe', '123'),
('wxq', 'wxq'),
('yss', 'yss'),
('yyf', 'yyf'),
('yyj', 'yyj'),
('zzy', 'qwe');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `diary`
--
ALTER TABLE `diary`
  ADD PRIMARY KEY (`id`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `diary`
--
ALTER TABLE `diary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '日记编号',AUTO_INCREMENT=16;
--
-- 限制导出的表
--

--
-- 限制表 `diary`
--
ALTER TABLE `diary`
  ADD CONSTRAINT `diary_ibfk_1` FOREIGN KEY (`name`) REFERENCES `user` (`name`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
