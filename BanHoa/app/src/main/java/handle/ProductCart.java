package handle;

import java.io.Serializable;
import object.SanPham;

public class ProductCart implements Serializable{
    SanPham SanPham;
    int SoLuong;

    public ProductCart(SanPham sanPham, int soLuong) {
        SanPham = sanPham;
        SoLuong = soLuong;
    }

    public SanPham getSanPham() {
        return SanPham;
    }

    public void setSanPham(SanPham sanPham) {
        SanPham = sanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }
}
