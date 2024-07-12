import java.time.LocalDate;

public class BenhAnVIP extends BenhAn{
    private String loaiVIP;
    private LocalDate thoiHanVIP;

    public BenhAnVIP(int soThuTu, String maBenhAn, String maBenhNhan, String tenBenhNhan, LocalDate ngayNhapVien, LocalDate ngayRaVien, String lyDoNhapVien, String loaiVIP, LocalDate thoiHanVIP) {
        super(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
        this.loaiVIP = loaiVIP;
        this.thoiHanVIP = thoiHanVIP;
    }

    public String getLoaiVIP() {
        return loaiVIP;
    }

    public LocalDate getThoiHanVIP() {
        return thoiHanVIP;
    }

    public String getThongTinBenhAn() {
        return toString() + "," + loaiVIP + "," + thoiHanVIP;
    }
}
