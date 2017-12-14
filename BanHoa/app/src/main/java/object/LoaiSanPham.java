package object;

public class LoaiSanPham {
    String MaLoai;
    String TenLoai;
    String GhiChu;

    public LoaiSanPham(String maLoai, String tenLoai, String ghiChu) {
        MaLoai = maLoai;
        TenLoai = tenLoai;
        GhiChu = ghiChu;
    }

    public String getMaLoai() {
        return MaLoai;
    }

    public void setMaLoai(String maLoai) {
        MaLoai = maLoai;
    }

    public String getTenLoai() {
        return TenLoai;
    }

    public void setTenLoai(String tenLoai) {
        TenLoai = tenLoai;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }
}
