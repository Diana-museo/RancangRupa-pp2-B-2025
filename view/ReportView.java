package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.ReportController;
import javax.swing.*;
import java.awt.*;

// VIEW: Report
// Halaman untuk menampilkan dan mencetak laporan pendaftaran kursus
// Terdiri dari laporan per peserta dan laporan berdasarkan periode tanggal

public class ReportView extends JFrame {

    // ===== FONT =====
    // Menggunakan Times New Roman agar konsisten dengan halaman lain
    private Font fontPlain = new Font("Times New Roman", Font.PLAIN, 14);
    private Font fontBold = new Font("Times New Roman", Font.BOLD, 14);

    // ===== PART 1 : REPORT PER PESERTA =====
    public JComboBox<String> cbPeserta = new JComboBox<>(); // Pilih peserta
    public JButton btnExportPeserta = new JButton("Export PDF Peserta"); // Export PDF per peserta

    // ===== PART 2 : REPORT PER PERIODE =====
    public JTextField tfFromDate = new JTextField(10); // Tanggal awal
    public JTextField tfToDate = new JTextField(10); // Tanggal akhir
    public JButton btnExportPeriode = new JButton("Export PDF Periode"); // Export PDF periode

    // ===== NAVIGASI =====
    public JButton btnBack = new JButton("Back"); // Kembali ke halaman pendaftaran

    // Konstruktor kelas ReportView
    public ReportView() {
        setTitle("Kelasync App - Report Pendaftaran");
        setSize(800, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL UTAMA =====
        // Panel utama berisi seluruh konten report
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PART 1 =====
        // Panel laporan pendaftaran per peserta
        JPanel p1 = new JPanel(new GridLayout(2, 2, 10, 10));
        p1.setBorder(BorderFactory.createTitledBorder("Report Per Peserta"));
        p1.add(createLabel("Peserta:"));
        p1.add(cbPeserta);
        p1.add(new JLabel(""));
        p1.add(btnExportPeserta);

        // ===== PART 2 =====
        // Panel laporan pendaftaran berdasarkan periode tanggal
        JPanel p2 = new JPanel(new GridLayout(3, 2, 10, 10));
        p2.setBorder(BorderFactory.createTitledBorder("Report Semua Pendaftaran (Periode)"));
        p2.add(createLabel("Dari Tanggal (yyyy-mm-dd):"));
        p2.add(tfFromDate);
        p2.add(createLabel("Sampai Tanggal (yyyy-mm-dd):"));
        p2.add(tfToDate);
        p2.add(new JLabel(""));
        p2.add(btnExportPeriode);

        // ===== FOOTER =====
        // Panel bawah untuk tombol navigasi
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.add(btnBack);

        // Terapkan font ke seluruh komponen
        applyFont(p1);
        applyFont(p2);
        applyFont(footer);

        panel.add(p1);
        panel.add(Box.createVerticalStrut(15));
        panel.add(p2);

        add(panel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        // Menghubungkan View dengan Controller
        // agar event tombol langsung aktif
        new ReportController(this);
    }

    // Membuat label dengan font standar
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(fontPlain);
        return l;
    }

    // Mengatur font seluruh komponen dalam container
    private void applyFont(Container c) {
        for (Component comp : c.getComponents()) {
            comp.setFont(fontPlain);
            if (comp instanceof Container) {
                applyFont((Container) comp);
            }
        }
    }
}