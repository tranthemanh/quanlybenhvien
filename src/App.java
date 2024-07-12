import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static BenhAnManager manager = new BenhAnManager();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("CHƯƠNG TRÌNH QUẢN LÝ BỆNH ÁN");
            System.out.println("Chọn chức năng theo số (để tiếp tục)");
            System.out.println("1. Thêm mới");
            System.out.println("2. Xóa");
            System.out.println("3. Xem danh sách các bệnh án");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    themMoiBenhAn(scanner);
                    break;
                case 2:
                    xoaBenhAn(scanner);
                    break;
                case 3:
                    manager.hienThiDanhSach();
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    private static void themMoiBenhAn(Scanner scanner) {
        try {
            System.out.print("Nhập mã bệnh án (BA-XXX): ");
            String maBenhAn = scanner.nextLine();
            if (!maBenhAn.matches("BA-\\d{3}")) {
                System.out.println("Mã bệnh án phải đúng định dạng BA-XXX, với XXX là các kí tự số.");
                return;
            }

            System.out.print("Nhập mã bệnh nhân (BN-XXX): ");
            String maBenhNhan = scanner.nextLine();
            if (!maBenhNhan.matches("BN-\\d{3}")) {
                System.out.println("Mã bệnh nhân phải đúng định dạng BN-XXX, với XXX là các kí tự số.");
                return;
            }

            System.out.print("Nhập tên bệnh nhân: ");
            String tenBenhNhan = scanner.nextLine();

            System.out.print("Nhập ngày nhập viện (dd/MM/yyyy): ");
            LocalDate ngayNhapVien = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);

            System.out.print("Nhập ngày ra viện (dd/MM/yyyy): ");
            LocalDate ngayRaVien = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);
            if (ngayNhapVien.isAfter(ngayRaVien)) {
                System.out.println("Ngày nhập viện phải nhỏ hơn hoặc bằng ngày ra viện.");
                return;
            }

            System.out.print("Nhập lý do nhập viện: ");
            String lyDoNhapVien = scanner.nextLine();

            System.out.print("Loại bệnh án (1 - Thường, 2 - VIP): ");
            int loaiBenhAn = Integer.parseInt(scanner.nextLine());

            int soThuTu = manager.getSoThuTuTiepTheo();
            BenhAn benhAn = null;

            if (loaiBenhAn == 1) {
                System.out.print("Nhập phí nằm viện (VNĐ): ");
                double phiNamVien = Double.parseDouble(scanner.nextLine());
                benhAn = new BenhAnThuong(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, phiNamVien);
            } else if (loaiBenhAn == 2) {
                System.out.print("Nhập gói VIP (VIP I, VIP II, VIP III): ");
                String loaiVIP = scanner.nextLine();
                if (!loaiVIP.equals("VIP I") && !loaiVIP.equals("VIP II") && !loaiVIP.equals("VIP III")) {
                    System.out.println("Gói VIP chỉ được chọn 1 trong 3 gói: VIP I, VIP II, VIP III.");
                    return;
                }
                System.out.print("Nhập thời hạn VIP (dd/MM/yyyy): ");
                LocalDate thoiHanVIP = LocalDate.parse(scanner.nextLine(), DATE_FORMAT);
                benhAn = new BenhAnVIP(soThuTu, maBenhAn, maBenhNhan, tenBenhNhan, ngayNhapVien, ngayRaVien, lyDoNhapVien, loaiVIP, thoiHanVIP);
            } else {
                System.out.println("Lựa chọn loại bệnh án không hợp lệ.");
                return;
            }

            manager.themBenhAn(benhAn);
            System.out.println("Thêm bệnh án thành công.");
        } catch (DuplicateMedicalRecordException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private static void xoaBenhAn(Scanner scanner) {
        try {
            System.out.print("Nhập mã bệnh án cần xóa: ");
            String maBenhAn = scanner.nextLine();
            System.out.print("Bạn có chắc chắn muốn xóa bệnh án này không? (Yes/No): ");
            String confirm = scanner.nextLine();
            if (confirm.equalsIgnoreCase("Yes")) {
                manager.xoaBenhAn(maBenhAn);
                System.out.println("Xóa bệnh án thành công.");
            } else {
                System.out.println("Hủy bỏ thao tác xóa.");
            }
        } catch (Exception e) {
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }
}
