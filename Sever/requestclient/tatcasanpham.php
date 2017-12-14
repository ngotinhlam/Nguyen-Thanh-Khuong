<?php
    require './database.php';
    
    $db = new database();
    $sql = "SELECT * FROM sanpham a, loaisanpham b WHERE a.MaLoai = b.MaLoai";
    $db->query($sql);
    $data = [];
    while ($row = $db->fetch()) {
        $data[] = $row;
    }
    echo json_encode($data);
?>

