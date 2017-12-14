var app = angular.module('trangchinh', ['ui.router']);
app.config(function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/sanpham'); // Mọi đường dẫn không hợp lệ đều được chuyển đến state home
    $stateProvider
            .state('sanpham', {// Định ngĩa 1 state home
                url: '/sanpham', // khai báo Url hiển thị
                redirectTo: 'sanpham.tatcasanpham',
            })
            .state('sanpham.tatcasanpham', {
                url: '/tatcasanpham',
                templateUrl: 'module/tatcasanpham.html',
                controller: 'tatcasanpham'
            })
            .state('sanpham.themsanpham', {
                url: '/themsanpham',
                templateUrl: 'module/themsanpham.html',
                controller: 'themsanpham'
            })
            .state('donhang', {
                url: '/donhang',
                templateUrl: 'module/donhang.html',
                controller: 'donhang'
            });
});

//Khi trang vừa được load lên
app.run(function ($http, $rootScope) {
    //Kiểm tra xem có đăng nhập hay không
    $http.post('services/request.php?id=10').success(function (data) {
        if (data == false) { //không có tài khoản đăng nhập
            window.location.href = 'dangnhap.html';
        } else {
            $rootScope.tentaikhoan = data;
        }
    });
});

//Directive điều khiển phần chọn ảnh
app.directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            require: '?ngModel',
            link: function (scope, element, attrs, ngModel) {
                element.bind('change', function () {
                    var file = element[0].files[0];
                    if (!angular.isUndefined(file)) {
                        if (file.type != 'image/jpeg') {
                            alert('Định dạng ảnh không được hỗ trợ');
                        } else {
                            var reader = new FileReader();
                            reader.readAsDataURL(file);
                            reader.onload = function (event) {
                                scope.$apply(function () {
                                    ngModel.$setViewValue(element.val());
                                    scope.urlImage = event.target.result;
                                    $parse('myFile').assign(scope, file);
                                });
                            }
                        }
                    }
                });
            }
        };
    }]);

//Filter random source
app.filter("randomSrc", function () {
    return function (input) {
        if (input) {
            var sep = input.indexOf("?") != -1 ? "&" : "?";
            return input + ".jpg" + sep + "r=" + Math.round(Math.random() * 999999);
        }
    }
});

//Filter định dạng date time
app.filter('dateToISO', function () {
    return function (input) {
        return new Date(input).toISOString();
    };
});

//Tạo filter tính tổng tiền đơn hàng
app.filter('tongcong', function () {
    return function (donhang) {
        if (donhang != null) {
            var tongtien = 0;
            for (i = 0; i < donhang.length; i++) {
                tongtien += donhang[i].DonGia * donhang[i].SoLuong1;
                tongtien += donhang[i].DonGia * donhang[i].SoLuong;
            }
            return tongtien;
        }
    }
});

//controller điều khiển tài khoản đăng nhập
app.controller('taikhoan', function ($scope, $state, $http) {
    //Khi nhấn đăng xuất
    $scope.DangXuat = function () {
        $http.post('services/request.php?id=11').success(function (data) {
            if (data == true) { //Đăng xuất thành công
                window.location.href = 'dangnhap.html';
            }
        });
    };
});

//controller điều khiển view thêm sản phẩm
app.controller('themsanpham', function ($scope, $state, $http) {
    //Lấy tất cả loại sản phẩm từ csdl để gán dữ liệu cho dropdownlist
    $http.post('services/request.php?id=2').success(function (data) {
        $scope.danhsachloaisanpham = data;
    });
    //Khi nhấn thêm
    $scope.ThemSanPham = function () {
        masanpham = new Date().getTime().toString().substr(8, 12);
        DuLieu = {
            masanpham: masanpham,
            loaisanpham: $scope.loaisanpham.MaLoai,
            tensanpham: $scope.tensanpham,
            dongia: $scope.dongia,
            soluong: $scope.soluong,
            mota: $scope.mota,
            anhsanpham: 'ASP' + masanpham
        };
        //Chuẩn bị ảnh
        var file = $scope.myFile;
        var fd = new FormData();
        fd.append('file', file);
        fd.append('dulieu', JSON.stringify(DuLieu));
        //Đưa dữ liệu tới server để thêm sản phẩm mới
        $http.post('services/request.php?id=3', fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined, 'Process-Data': false}
        }).success(function (data) {
            if (data == true) {
                alert('Thêm sản phẩm mới thành công');
                $state.reload();
            } else {
                alert('Thêm sản phẩm mới thất bại');
            }
        });
    };
});

//controller điều khiển view tất cả sản phẩm
app.controller('tatcasanpham', function ($scope, $state, $http) {
    //Lấy tất cả sản phẩm từ CSDL
    $http.post('services/request.php?id=4').success(function (data) {
        $scope.danhsachsanpham = data;
    });
    //Lấy tất cả loại sản phẩm để hiện lên dropdownlist
    $http.post('services/request.php?id=2').success(function (data) {
        $scope.danhsachloaisanpham = data;
    });

    //Khi nhấn sửa
    $scope.SuaSanPham = function (sp) {
        //Gán thông tin hiện lên
        angular.forEach($scope.danhsachloaisanpham, function (lsp) {
            if (lsp.MaLoai == sp.MaLoai) {
                $scope.loaisanpham = lsp;
                return;
            }
        });
        $scope.tensanpham = sp.TenSanPham;
        $scope.dongia = parseInt(sp.DonGia);
        $scope.soluong = parseInt(sp.SoLuong);
        $scope.mota = sp.MoTa;
        $scope.urlImage = 'images/anhsanpham/' + sp.HinhAnh + '.jpg?decache=' + Math.random();
        $scope.image = 'curr';
        $('#popupsuasanpham').modal('show');
        //Khi nhấn nút Lưu
        $scope.Luu = function () {
            DuLieu = {
                masanpham: sp.MaSanPham,
                loaisanpham: $scope.loaisanpham.MaLoai,
                tensanpham: $scope.tensanpham,
                dongia: $scope.dongia,
                soluong: $scope.soluong,
                mota: $scope.mota,
                anhsanpham: sp.HinhAnh
            };
            //Chuẩn bị ảnh
            var file = $scope.myFile;
            var fd = new FormData();
            fd.append('file', file);
            fd.append('dulieu', JSON.stringify(DuLieu));
            //Đưa dữ liệu tới server để sửa sản phẩm
            $http.post('services/request.php?id=5', fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined, 'Process-Data': false}
            }).success(function (data) {
                if (data == true) {
                    alert('Sửa sản phẩm thành công');
                    window.location.reload();
                } else {
                    alert('Sửa sản phẩm thất bại');
                }
                $('#popupsuasanpham').modal('hide');
            });
        };
    };
    //Khi nhấn xóa
    $scope.XoaSanPham = function (sp) {
        var r = confirm("Bạn có muốn xóa sản phẩm này không ?");
        if (r == true) {
            DuLieu = {
                masanpham: sp.MaSanPham,
                anhsanpham: sp.HinhAnh
            };
            $http.post('services/request.php?id=6', DuLieu).success(function (data) {
                if (data == true) {
                    alert('Xoá sản phẩm thành công');
                    window.location.reload();
                } else {
                    alert('Xoá sản phẩm thất bại');
                }
            });
        }
    };
});

//controller điều khiển view đơn hàng
app.controller('donhang', function ($scope, $state, $http) {
    $scope.danhsachchitietdonhang = null;
    //Lấy tất cả đơn hàng
    $http.post('services/request.php?id=7').success(function (data) {
        $scope.danhsachdonhang = data;
    });
    //Xem chi tiết đơn hàng
    $scope.XemChiTietDonHang = function (dh) {
        //Lấy chi tiết đơn hàng
        DuLieu = {
            madonhang: dh.MaDonHang
        }
        $http.post('services/request.php?id=8', DuLieu).success(function (data) {
            $scope.danhsachchitietdonhang = data;
            console.log($scope.danhsachchitietdonhang);
            $('#popupchitietdonhang').modal('show');
        });
    };
    //Xóa đơn hàng
    $scope.XoaDonHang = function (dh) {
        var t = confirm('Bạn có muốn xóa đơn hàng này không ?');
        if (t == true) {
            DuLieu = {
                madonhang: dh.MaDonHang
            }
            $http.post('services/request.php?id=9', DuLieu).success(function (data) {
                if (data == true) {
                    alert('Xóa đơn hàng thành công');
                    $('#popupchitietdonhang').modal('hide');
                    window.location.reload();
                } else {
                    alert('Xóa đơn hàng thất bại');
                }
            });
        }
    };
});