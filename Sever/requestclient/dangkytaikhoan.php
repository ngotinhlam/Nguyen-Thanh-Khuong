<?php

require './database.php';

$fullname = $_POST["fullname"];
$username = $_POST["username"];
$password = $_POST["password"];
$email = $_POST["email"];
$phone = $_POST["phone"];
$address = $_POST["address"];

$name = $_POST['name'];
$image = $_POST['image'];
$urlhost = $_POST['host'];
$name_file = $name.'.jpg';
$path_save = "../admin/images/anhcanhan/$name_file";
$url = "$urlhost/bandodien/admin/images/anhcanhan/$name_file";

$respone = array('account' => '', 'avatar' => '');
if (!empty($fullname) && !empty($username) && !empty($password) && !empty($email) && !empty($phone) && !empty($address)) {
    $db = new database();
    $avatar = empty($image) ? NULL : $url;
    $sql = "SELECT * FROM `taikhoan` WHERE TenTaiKhoan='$username'";
    $db->query($sql);
    if ($db->count_num_rows() > 0) {
        $respone['account'] = "username exist";
        //echo "username exist";
    } else {
        $sql = "INSERT INTO `taikhoan` (`TenTaiKhoan`, `MatKhau`, `HoTen`, `Email`, `SoDienThoai`, `DiaChi`, `AnhCaNhan`) VALUES ('$username', '$password', '$fullname', '$email', '$phone', '$address', '$avatar')";
        try {
            $db->excute($sql);
            $respone['account'] = "register account success";
            if (!empty($image)) {
                if (file_put_contents($path_save, base64_decode($image))) {
                    $respone['avatar'] = "upload avatar success";
                    //echo "upload avatar success";
                } else {
                    $respone['avatar'] = "upload avatar fail";
                    //echo "upload avatar fail";
                }
            }
            //echo "register account success";
        } catch (Exception $ex) {
            $respone['account'] = "register account fail";
            //echo "register account fail";
        }
    }
} else {
    $respone['account'] = "register account fail";
}

echo json_encode($respone);
?>

