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
    private Font fontSmallItalic = new Font("Times New Roman", Font.ITALIC, 11); // Font untuk teks kecil italic

    // ===== FORM INPUT =====
    public JComboBox<String> cbPeserta = new JComboBox<>(); // Pilih peserta
    public JComboBox<String> cbKursus = new JComboBox<>(); // Pilih kursus
    public JTextField tfTanggal = new JTextField(15); // Input tanggal daftar

    // ===== BUTTON =====
    public JButton btnTambah = new JButton("Tambah"); // Tambah data pendaftaran
    public JButton btnHapus = new JButton("Hapus"); // Hapus data terpilih
    public JButton btnClear = new JButton("Clear"); // Bersihkan form
    public JButton btnRefresh = new JButton("Refresh"); // Refresh tabel
    public JButton btnReport = new JButton("Report"); // Masuk ke halaman laporan

    // ===== TABLE =====
    public JTable tabel; // Tabel data pendaftaran
    public DefaultTableModel tableModel; // Model tabel

    // Konstruktor kelas PendaftaranView
    public PendaftaranView() {
        setTitle("Kelasync App - Pendaftaran Kursus");
        setSize(900, 600); // Ditambah tinggi sedikit untuk teks tambahan
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL FORM =====
        // Panel atas untuk input data pendaftaran
        JPanel pnlForm = new JPanel(new GridLayout(4, 2, 10, 5)); // Row spacing dikurangi untuk teks kecil
        pnlForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlForm.add(createLabel("Peserta:"));
        pnlForm.add(cbPeserta);

        pnlForm.add(createLabel("Kursus:"));
        pnlForm.add(cbKursus);

        // Panel untuk label tanggal + informasi kecil
        JPanel pnlTanggalLabel = new JPanel(new BorderLayout(0, 2));

        // Label utama "Tanggal Daftar"
        JLabel lblTanggal = createLabel("Tanggal Daftar (yyyy-mm-dd):");
        pnlTanggalLabel.add(lblTanggal, BorderLayout.NORTH);

        // Teks informasi kecil di bawah label utama
        JLabel infoLabel = new JLabel("Kolom tanggal daftar bisa dikosongkan dan akan digenerate otomatis");
        infoLabel.setFont(fontSmallItalic);
        infoLabel.setForeground(Color.DARK_GRAY);
        pnlTanggalLabel.add(infoLabel, BorderLayout.SOUTH);

        pnlForm.add(pnlTanggalLabel);
        pnlForm.add(tfTanggal); // Field tanggal tetap ukuran normal

        // ===== PANEL BUTTON =====
        // Panel tombol aksi (CRUD & Report)
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.LEFT));
        applyButtonFont(btnTambah);
        applyButtonFont(btnHapus);
        applyButtonFont(btnClear);
        applyButtonFont(btnRefresh);
        applyButtonFont(btnReport);

        pnlButton.add(btnTambah);
        pnlButton.add(btnHapus);
        pnlButton.add(btnClear);
        pnlButton.add(btnRefresh);
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