var app = angular.module('admin', []);

//Khi đến trang quản trị
app.run(function ($http) {
    $http.post('services/request.php?id=10').success(function (data) {
        if (data != false) { //có tài khoản đăng nhập
            window.location.href = 'trangchinh.html';
        } else {
            window.location.href = 'dangnhap.html';
        }
    });
});