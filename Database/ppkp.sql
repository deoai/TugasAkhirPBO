-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 29 Jun 2022 pada 05.55
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 8.0.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ppkp`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `administrasi`
--

CREATE TABLE `administrasi` (
  `kode` varchar(5) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `umur` int(2) NOT NULL,
  `jurusan` varchar(255) NOT NULL,
  `ipk` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `administrasi`
--

INSERT INTO `administrasi` (`kode`, `nama`, `umur`, `jurusan`, `ipk`) VALUES
('P01', 'Hafizh', 30, 'Teknik Informatika', 3),
('P01', 'Hafizh', 20, 'Teknik Informatika', 3.82);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kabupaten`
--

CREATE TABLE `kabupaten` (
  `kode` varchar(5) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kuota_kec` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kabupaten`
--

INSERT INTO `kabupaten` (`kode`, `nama`, `kuota_kec`) VALUES
('KAB01', 'Pekalongan', 5),
('KAB02', 'Batang', 3),
('KAB03', 'Kendal', 4),
('KAB04', 'Semarang', 7);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kecamatan`
--

CREATE TABLE `kecamatan` (
  `kode` varchar(5) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kabupaten` varchar(100) NOT NULL,
  `kuota_kel` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kecamatan`
--

INSERT INTO `kecamatan` (`kode`, `nama`, `kabupaten`, `kuota_kel`) VALUES
('KC01', 'Limpung', 'Batang', 3),
('KC02', 'Semarang Barat', 'Semarang', 5),
('KC03', 'Ungaran', 'Semarang', 6);

-- --------------------------------------------------------

--
-- Struktur dari tabel `kelurahan`
--

CREATE TABLE `kelurahan` (
  `kode` varchar(5) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `kecamatan` varchar(100) NOT NULL,
  `kuota_ang` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `kelurahan`
--

INSERT INTO `kelurahan` (`kode`, `nama`, `kecamatan`, `kuota_ang`) VALUES
('KL01', 'Babadan', 'Limpung', 11);

-- --------------------------------------------------------

--
-- Struktur dari tabel `penilaian`
--

CREATE TABLE `penilaian` (
  `kode` varchar(5) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `umur` int(2) NOT NULL,
  `jurusan` varchar(255) NOT NULL,
  `ipk` float NOT NULL,
  `tertulis` float NOT NULL,
  `wawancara` float NOT NULL,
  `nilai_akhir` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `penilaian`
--

INSERT INTO `penilaian` (`kode`, `nama`, `umur`, `jurusan`, `ipk`, `tertulis`, `wawancara`, `nilai_akhir`) VALUES
('P01', 'Hafizh', 20, 'Teknik Informatika', 3.82, 90, 54, 72);

-- --------------------------------------------------------

--
-- Struktur dari tabel `peserta`
--

CREATE TABLE `peserta` (
  `kode` varchar(5) NOT NULL,
  `nik` varchar(16) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `umur` int(2) NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `jurusan` varchar(255) NOT NULL,
  `ipk` float NOT NULL,
  `no_hp` varchar(15) NOT NULL,
  `email` varchar(255) NOT NULL,
  `alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `peserta`
--

INSERT INTO `peserta` (`kode`, `nik`, `nama`, `tempat_lahir`, `tanggal_lahir`, `umur`, `jenis_kelamin`, `jurusan`, `ipk`, `no_hp`, `email`, `alamat`) VALUES
('P01', '15542135421', 'Hafizh', 'Ungaran', '2001-09-09', 20, 'Laki-laki', 'Teknik Informatika', 3.82, '08452312451235', 'hafisxh@gmail.com', 'Jl. Ahmad Dani No.19 Perum Dewa Ungaran');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `user_id` varchar(10) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(254) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`user_id`, `name`, `password`) VALUES
('aa', 'aaaa', 'aa');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_dump`
--

CREATE TABLE `user_dump` (
  `id` int(4) NOT NULL,
  `user_id` varchar(10) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `password` varchar(254) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Dumping data untuk tabel `user_dump`
--

INSERT INTO `user_dump` (`id`, `user_id`, `name`, `password`) VALUES
(1, 'admin', 'admin', '827ccb0eea8a706c4c34a16891f84e7b'),
(2, 'yayas', 'yayas', '61c965b0758b6d9316e0bb041570ba7f'),
(3, 'aku', 'aku', '26c42cfa0f21a1a2c069fdd645e78979'),
(4, 'Nanta', 'nt', 'dbf0ecbe1a3d12d50699158bf6370431');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `peserta`
--
ALTER TABLE `peserta`
  ADD PRIMARY KEY (`kode`);

--
-- Indeks untuk tabel `user_dump`
--
ALTER TABLE `user_dump`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user_dump`
--
ALTER TABLE `user_dump`
  MODIFY `id` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
