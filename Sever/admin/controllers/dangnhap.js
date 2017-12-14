var app = angular.module('dangnhap', []);

app.controller('ctrl_dangnhap', function ($scope, $http) {
    //Khi nhấn nút đăng nhập
    $scope.DangNhap = function () {
        DuLieu = {
            tentaikhoan: $scope.tentaikhoan,
            matkhau: $scope.matkhau
        }
        $http.post('services/request.php?id=1', DuLieu).success(function (data) {
            if (data == true) {
                alert('Đăng nhập thành công');
                window.location.href = 'trangchinh.html';
            } else {
                alert('Sai tên tài khoản hoặc mật khẩu');
            }
        });
    };
});