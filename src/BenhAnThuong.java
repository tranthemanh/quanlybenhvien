import java.time.LocalDate;

public class BenhAnThuong extends BenhAn{
    private double phiNamVien;

    public BenhAnThuong(int soThuTu, String maBenhAn, String maBenhNhan, String tenBenhNhan, LocalDate ngayNhapVien, LocalDate ngayRaVien, String lyDoNhapVien, double phiNamVien){
        super(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien);
        this.phiNamVien = phiNamVien;
    }

    public double getPhiNamVien(){
        return phiNamVien;
    }

    public String getThongTinBenhAn() {
        return toString() + "," + phiNamVien;
    }
}
