package object;


public class TaiKhoan {

    String TenTaiKhoan, MatKhau, HoTen, Email, SoDienThoai, DiaChi, AnhCaNhan;

    public TaiKhoan(String tenTaiKhoan, String matKhau, String hoTen, String email, String soDienThoai, String diaChi, String anhCaNhan) {
        TenTaiKhoan = tenTaiKhoan;
        MatKhau = matKhau;
        HoTen = hoTen;
        Email = email;
        SoDienThoai = soDienThoai;
        DiaChi = diaChi;
        AnhCaNhan = anhCaNhan;
    }

    public String getTenTaiKhoan() {
        return TenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        TenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getAnhCaNhan() {
        return AnhCaNhan;
    }

    public void setAnhCaNhan(String anhCaNhan) {
        AnhCaNhan = anhCaNhan;
    }
}
