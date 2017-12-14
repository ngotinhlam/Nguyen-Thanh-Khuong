<?php

require './database.php';

$data = json_decode($_POST['data']);
$makh = $_POST['makh'];

$madonhang = uniqid();

try {
    $db = new database();
    $sql = "INSERT INTO `donhang` (`MaDonHang`, `NgayDatHang`, `MaKhachHang`, `GhiChu`) VALUES ('$madonhang', CURRENT_TIMESTAMP, '$makh', '')";
    $db->excute($sql);

    for ($i = 0; $i < count($data); $i++) {
        $sanpham = $data[$i]->SanPham;
        $masanpham = $sanpham->MaSanPham;
        $soluong = $data[$i]->SoLuong;
        $sql = "INSERT INTO `chitietdonhang` (`MaDonHang`, `MaSanPham`, `SoLuong`) VALUES ('$madonhang', '$masanpham', $soluong)";
        $db->excute($sql);
    }

    echo TRUE;
} catch (Exception $ex) {
    echo FALSE;
}

?>
