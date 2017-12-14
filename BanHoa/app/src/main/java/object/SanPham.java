package object;


import java.io.Serializable;

public class SanPham implements Serializable {

    String MaSanPham;
    String TenSanPham;
    String MaLoai;
    String MoTa;
    int DonGia;
    int SoLuong;
    String HinhAnh;

    public SanPham(String maSanPham, String tenSanPham, String maLoai, String moTa, int donGia, int soLuong, String hinhAnh) {
        MaSanPham = maSanPham;
        TenSanPham = tenSanPham;
        MaLoai = maLoai;
        MoTa = moTa;
        DonGia = donGia;
        SoLuong = soLuong;
        HinhAnh = hinhAnh;
    }

    public String getMaSanPham() {
        return MaSanPham;
    }

    public void setMaSanPham(String maSanPham) {
        MaSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public String getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        HinhAnh = hinhAnh;
    }
}
