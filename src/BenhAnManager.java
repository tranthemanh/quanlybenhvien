import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BenhAnManager {
    private static final String FILE_PATH = "medical_records.csv";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private List<BenhAn> danhSachBenhAn = new ArrayList<>();

    public BenhAnManager() {
        loadRecords();
    }

    public void themBenhAn(BenhAn benhAn) throws IOException, DuplicateMedicalRecordException {
        for (BenhAn ba : danhSachBenhAn) {
            if (ba.getMaBenhAn().equals(benhAn.getMaBenhAn())) {
                throw new DuplicateMedicalRecordException("Bệnh án đã tồn tại.");
            }
        }
        danhSachBenhAn.add(benhAn);
        saveRecords();
    }

    public void xoaBenhAn(String maBenhAn) throws IOException {
        boolean removed = danhSachBenhAn.removeIf(ba -> ba.getMaBenhAn().equals(maBenhAn));
        if (removed) {
            saveRecords();
        }
    }

    public void hienThiDanhSach() {
        for (BenhAn benhAn : danhSachBenhAn) {
            System.out.println(benhAn.getThongTinBenhAn());
        }
    }

    private void loadRecords() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int soThuTu = Integer.parseInt(parts[0]);
                String maBenhAn = parts[1];
                String maBenhNhan = parts[2];
                String tenBenhNhan = parts[3];
                LocalDate ngayNhapVien = LocalDate.parse(parts[4], DATE_FORMAT);
                LocalDate ngayRaVien = LocalDate.parse(parts[5], DATE_FORMAT);
                String lyDoNhapVien = parts[6];

                if (parts.length == 8) {
                    double phiNamVien = Double.parseDouble(parts[7]);
                    danhSachBenhAn.add(new BenhAnThuong(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, phiNamVien));
                } else if (parts.length == 9) {
                    String loaiVIP = parts[7];
                    LocalDate thoiHanVIP = LocalDate.parse(parts[8], DATE_FORMAT);
                    danhSachBenhAn.add(new BenhAnVIP(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, loaiVIP, thoiHanVIP));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRecords() throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(FILE_PATH))) {
            for (BenhAn benhAn : danhSachBenhAn) {
                bw.write(benhAn.getThongTinBenhAn());
                bw.newLine();
            }
        }
    }

    public int getSoThuTuTiepTheo() {
        if (danhSachBenhAn.isEmpty()) {
            return 1;
        } else {
            return danhSachBenhAn.get(danhSachBenhAn.size() - 1).getSoThuTu() + 1;
        }
    }
}
