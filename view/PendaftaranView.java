package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.PendaftaranController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// VIEW: Pendaftaran
// Halaman untuk mengelola pendaftaran peserta ke kursus
// Menampilkan form input dan tabel data pendaftaran

public class PendaftaranView extends JFrame {

    // ===== FONT =====
    // Diatur memakai Times New Roman agar konsisten dengan tampilan lain
    private Font fontPlain = new Font("Times New Roman", Font.PLAIN, 14);
    private Font fontBold = new Font("Times New Roman", Font.BOLD, 14);

    // ===== FORM INPUT =====
    public JComboBox<String> cbPeserta = new JComboBox<>(); // Pilih peserta
    public JComboBox<String> cbKursus = new JComboBox<>(); // Pilih kursus
    public JTextField tfTanggal = new JTextField(15); // Input tanggal daftar

    // ===== BUTTON =====
    public JButton btnSimpan = new JButton("Simpan"); // Simpan data pendaftaran
    public JButton btnHapus = new JButton("Hapus"); // Hapus data terpilih
    public JButton btnBersih = new JButton("Bersih"); // Bersihkan form
    public JButton btnReport = new JButton("Report"); // Masuk ke halaman laporan

    // ===== TABLE =====
    public JTable tabel; // Tabel data pendaftaran
    public DefaultTableModel tableModel; // Model tabel

    // Konstruktor kelas PendaftaranView
    public PendaftaranView() {
        setTitle("Kelasync App - Pendaftaran Kursus");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL FORM =====
        // Panel atas untuk input data pendaftaran
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 10));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlForm.add(createLabel("Peserta:"));
        pnlForm.add(cbPeserta);

        pnlForm.add(createLabel("Kursus:"));
        pnlForm.add(cbKursus);

        pnlForm.add(createLabel("Tanggal Daftar:"));
        pnlForm.add(tfTanggal);

        // ===== PANEL BUTTON =====
        // Panel tombol aksi (CRUD & Report)
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        applyButtonFont(btnSimpan);
        applyButtonFont(btnHapus);
        applyButtonFont(btnBersih);
        applyButtonFont(btnReport);

        pnlButton.add(btnSimpan);
        pnlButton.add(btnHapus);
        pnlButton.add(btnBersih);
        pnlButton.add(btnReport);

        pnlForm.add(new JLabel(""));
        pnlForm.add(pnlButton);

        // ===== TABLE =====
        // Tabel untuk menampilkan data pendaftaran
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Peserta", "Kursus", "Tanggal" }, 0) {
            // Supaya tabel tidak bisa diedit langsung
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabel = new JTable(tableModel);
        tabel.setFont(fontPlain);
        tabel.getTableHeader().setFont(fontBold);
        tabel.setRowHeight(24);

        add(pnlForm, BorderLayout.NORTH);
        add(new JScrollPane(tabel), BorderLayout.CENTER);

        // Menghubungkan View dengan Controller
        // supaya saat View dibuat, seluruh event handler langsung aktif dan siap
        // digunakan
        new PendaftaranController(this);
    }

    // Membuat label dengan font standar
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(fontPlain);
        return l;
    }

    // Mengatur font tombol agar konsisten
    private void applyButtonFont(JButton b) {
        b.setFont(fontBold);
    }
}