<?php

include_once './database.php';
session_start();

$id = $_GET['id'];

switch ($id) {
    case 1:
        KiemTraDangNhap();
        break;
    case 2:
        LayTatCaLoaiSanPham();
        break;
    case 3:
        ThemSanPham();
        break;
    case 4:
        LayTatCaSanPham();
        break;
    case 5:
        SuaSanPham();
        break;
    case 6:
        XoaSanPham();
        break;
    case 7:
        LayTatCaDonHang();
        break;
    case 8:
        LayChiTietDonHang();
        break;
    case 9:
        XoaDonHang();
        break;
    case 10:
        CoTaiKhoanDangNhap();
        break;
    case 11:
        DangXuat();
        break;
    case 12:
        LayThongTinSanPham();
        break;
}

function KiemTraDangNhap() {
    if (file_get_contents('php://input') != NULL) {
        $data = json_decode(file_get_contents('php://input'));
        $db = new database();
        $tentaikhoan = $data->tentaikhoan;
        $matkhau = $data->matkhau;
        $sql = "SELECT * FROM `quantri` WHERE TenTaiKhoan = '$tentaikhoan' AND MatKhau = '$matkhau'";
        $db->query($sql);
        if ($db->count_num_rows() > 0) {
            $_SESSION['taikhoan'] = $tentaikhoan;
            echo TRUE;
        } else
            echo FALSE;
    } else
        echo FALSE;
}

function CoTaiKhoanDangNhap() {
    if (isset($_SESSION['taikhoan'])) {
        echo $_SESSION['taikhoan'];
    } else
        echo FALSE;
}

function DangXuat() {
    unset($_SESSION['taikhoan']);
    echo TRUE;
}

function LayTatCaLoaiSanPham() {
    $db = new database();
    $sql = "SELECT * FROM `loaisanpham`";
    $db->query($sql);
    $data = [];
    while ($row = $db->fetch()) {
        $data[] = $row;
    }
    echo json_encode($data);
}

function ThemSanPham() {
    if ($_POST['dulieu'] != null && !empty($_FILES["file"]["name"])) {
        $data = json_decode($_POST['dulieu']);
        $thumucchua = "../images/anhsanpham/";
        $db = new database;
        $masanpham = $data->masanpham;
        $loaisanpham = $data->loaisanpham;
        $tensanpham = $data->tensanpham;
        $dongia = $data->dongia;
        $soluong = $data->soluong;
        $mota = $data->mota;
        $anhsanpham = $data->anhsanpham;
        $target_file = $thumucchua . $anhsanpham . '.jpg';
        $sql = "INSERT INTO `sanpham` (`MaSanPham`, `TenSanPham`, `MaLoai`, `MoTa`, `DonGia`, `SoLuong`, `HinhAnh`) VALUES ('$masanpham', '$tensanpham', '$loaisanpham', '$mota', '$dongia', '$soluong', '$anhsanpham')";
        try {
            if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_file)) {
                $db->excute($sql);
                echo TRUE;
            } else {
                echo FALSE;
            }
        } catch (Exception $exc) {
            echo FALSE;
        }
    } else
        echo FALSE;
}

function LayTatCaSanPham() {
    $db = new database();
    $sql = "SELECT * FROM sanpham a, loaisanpham b WHERE a.MaLoai = b.MaLoai";
    $db->query($sql);
    $data = [];
    while ($row = $db->fetch()) {
        $data[] = $row;
    }
    echo json_encode($data);
}

function SuaSanPham() {
    if ($_POST['dulieu'] != null) {
        $data = json_decode($_POST['dulieu']);
        $thumucchua = "../images/anhsanpham/";
        $db = new database;
        $masanpham = $data->masanpham;
        $loaisanpham = $data->loaisanpham;
        $tensanpham = $data->tensanpham;
        $dongia = $data->dongia;
        $soluong = $data->soluong;
        $mota = $data->mota;
        $anhsanpham = $data->anhsanpham;
        $target_file = $thumucchua . $anhsanpham . '.jpg';
        $sql = "UPDATE `sanpham` SET `TenSanPham` = '$tensanpham', `MaLoai` = '$loaisanpham', `MoTa` = '$mota', `DonGia` = '$dongia', `SoLuong` = '$soluong', `HinhAnh` = '$anhsanpham' WHERE `sanpham`.`MaSanPham` = '$masanpham'";
        try {
            if (!empty($_FILES["file"]["name"])) {
                move_uploaded_file($_FILES["file"]["tmp_name"], $target_file);
            }
            $db->excute($sql);
            echo TRUE;
        } catch (Exception $exc) {
            echo FALSE;
        }
    } else
        echo FALSE;
}

function XoaSanPham() {
    if (file_get_contents('php://input') != NULL) {
        $data = json_decode(file_get_contents('php://input'));
        $thumucchua = "../images/anhsanpham/";
        $db = new database();
        $masanpham = $data->masanpham;
        $anhsanpham = $data->anhsanpham;
        $target_file = $thumucchua . $anhsanpham . '.jpg';
        $sql = "DELETE FROM sanpham WHERE MaSanPham = '$masanpham'";
        try {
            unlink($target_file);
            $db->excute($sql);
            echo TRUE;
        } catch (Exception $exc) {
            echo FALSE;
        }
    } else
        echo FALSE;
}

function LayTatCaDonHang() {
    $db = new database();
    $sql = "SELECT * FROM `donhang`";
    $db->query($sql);
    $data = [];
    while ($row = $db->fetch()) {
        $data[] = $row;
    }
    echo json_encode($data);
}

function LayChiTietDonHang() {
    if (file_get_contents('php://input') != NULL) {
        $data = json_decode(file_get_contents('php://input'));
        $db = new database();
        $madonhang = $data->madonhang;
        $sql = "SELECT *, a.SoLuong AS SoLuong1 FROM chitietdonhang a, sanpham b WHERE a.MaSanPham = b.MaSanPham AND a.MaDonHang = '$madonhang'";
        $db->query($sql);
        $data = [];
        while ($row = $db->fetch()) {
            $data[] = $row;
        }
        echo json_encode($data);
    } else
        echo FALSE;
}

function XoaDonHang() {
    if (file_get_contents('php://input') != NULL) {
        $data = json_decode(file_get_contents('php://input'));
        $db = new database();
        $madonhang = $data->madonhang;
        $sql = "DELETE FROM `donhang` WHERE `donhang`.`MaDonHang` = '$madonhang'";
        try {
            $db->excute($sql);
            echo TRUE;
        } catch (Exception $exc) {
            echo FALSE;
        }
    } else
        echo FALSE;
}

function LayThongTinSanPham() {
    if (file_get_contents('php://input') != NULL) {
        $data = json_decode(file_get_contents('php://input'));
        $db = new database();
        $masanpham = $data->masanpham;
        $sql = "SELECT * FROM `sanpham` WHERE MaSanPham='$masanpham'";
        $db->query($sql);
        $data = [];
        while ($row = $db->fetch()) {
            $data[] = $row;
        }
        echo json_encode($data);
    } else
        echo FALSE;
}

?>
