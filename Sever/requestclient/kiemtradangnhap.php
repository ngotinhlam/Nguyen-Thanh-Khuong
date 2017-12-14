<?php

require './database.php';

$username = $_POST['username'];
$password = $_POST['password'];

$response = array('result' => '', 'data' => '');

try {
    $db = new database();
    $sql = "SELECT * FROM `taikhoan` WHERE TenTaiKhoan = '$username' AND MatKhau = '$password'";
    $db->query($sql);

    if ($db->count_num_rows() > 0) {
        $response['result'] = 'login success';
        $response['data'] = $db->fetch();
    } else {
        $response['result'] = 'login fail';
    }
} catch (Exception $ex) {
    $response['result'] = 'login fail';
}

echo json_encode($response);
?>
