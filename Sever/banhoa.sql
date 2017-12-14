-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 14, 2017 lúc 01:24 CH
-- Phiên bản máy phục vụ: 5.7.14
-- Phiên bản PHP: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `banhoa`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `banner`
--

CREATE TABLE `banner` (
  `MaBanner` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `TenBanner` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `banner`
--

INSERT INTO `banner` (`MaBanner`, `TenBanner`) VALUES
('1', 'banner1'),
('2', 'banner2'),
('3', 'banner3');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `MaDonHang` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `MaSanPham` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `SoLuong` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`MaDonHang`, `MaSanPham`, `SoLuong`) VALUES
('5a327b0d2d89f', '02738', 1),
('5a327b0d2d89f', '27069', 4),
('5a327b0d2d89f', '27972', 2),
('5a327b0d2d89f', '44842', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `donhang`
--

CREATE TABLE `donhang` (
  `MaDonHang` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `NgayDatHang` datetime NOT NULL,
  `MaKhachHang` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `GhiChu` varchar(200) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `donhang`
--

INSERT INTO `donhang` (`MaDonHang`, `NgayDatHang`, `MaKhachHang`, `GhiChu`) VALUES
('5a327b0d2d89f', '2017-12-14 20:22:21', 'khuong', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaisanpham`
--

CREATE TABLE `loaisanpham` (
  `MaLoai` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `TenLoai` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `GhiChu` varchar(200) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `loaisanpham`
--

INSERT INTO `loaisanpham` (`MaLoai`, `TenLoai`, `GhiChu`) VALUES
('0', 'Tất cả', ''),
('1', 'Hoa Lan', ''),
('2', 'Hoa Mai', ''),
('3', 'Hoa Cúc', '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhanxet`
--

CREATE TABLE `nhanxet` (
  `MaNhanXet` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `MaSanPham` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `MaKhachHang` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `NoiDung` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `ThoiGian` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `quantri`
--

CREATE TABLE `quantri` (
  `TenTaiKhoan` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `MatKhau` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `HoTen` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `quantri`
--

INSERT INTO `quantri` (`TenTaiKhoan`, `MatKhau`, `HoTen`) VALUES
('admin', 'admin', 'Nguyễn Thành Khương');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `sanpham`
--

CREATE TABLE `sanpham` (
  `MaSanPham` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `TenSanPham` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `MaLoai` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `MoTa` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `DonGia` int(11) NOT NULL,
  `SoLuong` int(11) NOT NULL,
  `HinhAnh` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `sanpham`
--

INSERT INTO `sanpham` (`MaSanPham`, `TenSanPham`, `MaLoai`, `MoTa`, `DonGia`, `SoLuong`, `HinhAnh`) VALUES
('02738', 'Lan hồng', '1', 'Lan màu hồng', 230000, 100, 'ASP02738'),
('27069', 'Hoa mai vàng', '2', 'Hoa mai màu vàng', 120000000, 100, 'ASP27069'),
('27972', 'Hoa mai vàng', '2', 'Hoa mai vàng', 1200000, 100, 'ASP27972'),
('32362', 'Ha Lan Tím', '1', 'Hoa lan màu tím', 150000, 100, 'ASP32362'),
('38597', 'Hoa cúc tráng', '3', 'Hoa cúc trắng', 60000, 100, 'ASP38597'),
('44842', 'Lan trắng', '1', 'Lan màu trắng', 120000, 100, 'ASP44842'),
('56261', 'Hoa mai vàng', '2', 'Hoa mai vàng', 1320000, 100, 'ASP56261'),
('58184', 'Lan màu vàng', '1', 'Lan màu vàng', 140000, 100, 'ASP58184'),
('76175', 'Hoa mai vàng', '2', 'Hoa mai vàng', 1300000, 100, 'ASP76175'),
('81402', 'Hoa cúc tím', '3', 'Hoa cúc tím', 70000, 100, 'ASP81402'),
('88854', 'Hoa cúc vàng', '3', 'Hoa cúc màu vàng', 50000, 100, 'ASP88854');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `TenTaiKhoan` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `MatKhau` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `HoTen` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `SoDienThoai` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `DiaChi` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `AnhCaNhan` varchar(100) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`TenTaiKhoan`, `MatKhau`, `HoTen`, `Email`, `SoDienThoai`, `DiaChi`, `AnhCaNhan`) VALUES
('khuong', 'khuong', 'Nguyen Thanh Khuong', 'khuong@gmail.com', '01234567898', 'sdjgs dfsdfsdf', 'http://192.168.1.214/bandodien/admin/images/anhcanhan/avatar_khuong.jpg');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `banner`
--
ALTER TABLE `banner`
  ADD PRIMARY KEY (`MaBanner`);

--
-- Chỉ mục cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`MaDonHang`,`MaSanPham`),
  ADD KEY `MaDonHang` (`MaDonHang`),
  ADD KEY `MaSanPham` (`MaSanPham`);

--
-- Chỉ mục cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`MaDonHang`),
  ADD KEY `FK_donhang_taikhoan` (`MaKhachHang`);

--
-- Chỉ mục cho bảng `loaisanpham`
--
ALTER TABLE `loaisanpham`
  ADD PRIMARY KEY (`MaLoai`);

--
-- Chỉ mục cho bảng `nhanxet`
--
ALTER TABLE `nhanxet`
  ADD PRIMARY KEY (`MaNhanXet`);

--
-- Chỉ mục cho bảng `quantri`
--
ALTER TABLE `quantri`
  ADD PRIMARY KEY (`TenTaiKhoan`);

--
-- Chỉ mục cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD PRIMARY KEY (`MaSanPham`),
  ADD KEY `MaLoai` (`MaLoai`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`TenTaiKhoan`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD CONSTRAINT `FK_chitietdonhang_donhang` FOREIGN KEY (`MaDonHang`) REFERENCES `donhang` (`MaDonHang`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_chitietdonhang_sanpham` FOREIGN KEY (`MaSanPham`) REFERENCES `sanpham` (`MaSanPham`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `donhang`
--
ALTER TABLE `donhang`
  ADD CONSTRAINT `FK_donhang_taikhoan` FOREIGN KEY (`MaKhachHang`) REFERENCES `taikhoan` (`TenTaiKhoan`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `sanpham`
--
ALTER TABLE `sanpham`
  ADD CONSTRAINT `FK_sanpham_loaisanpham` FOREIGN KEY (`MaLoai`) REFERENCES `loaisanpham` (`MaLoai`) ON DELETE CASCADE ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
