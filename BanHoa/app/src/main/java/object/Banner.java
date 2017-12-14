package object;

public class Banner {
    String MaBanner;
    String TenBanner;

    public Banner(String MaBanner, String tenBanner) {
        this.MaBanner = MaBanner;
        TenBanner = tenBanner;
    }

    public String getMaBanner() {
        return MaBanner;
    }

    public void setMaBanner(String MaBanner) {
        this.MaBanner = MaBanner;
    }

    public String getTenBanner() {
        return TenBanner;
    }

    public void setTenBanner(String tenBanner) {
        TenBanner = tenBanner;
    }
}
