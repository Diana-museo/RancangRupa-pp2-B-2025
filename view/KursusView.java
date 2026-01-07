package id.rancangrupa.kelasync2.view;

import id.rancangrupa.kelasync2.controller.KursusController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KursusView extends JFrame {

    // ---- Deklarasi Controller -----
    private KursusController controller;

    // ---- Deklarasi Komponen Form -----
    public JTextField tfId = new JTextField();
    public JTextField tfNama = new JTextField(15);
    public JTextArea taMateri = new JTextArea(3, 20);
    public JComboBox<String> cbHari = new JComboBox<>();
    public JTextField tfJamMulai = new JTextField(15);
    public JTextField tfJamSelesai = new JTextField(15);
    public JComboBox<String> cbPengajar = new JComboBox<>();

    // ---- Deklarasi Tombol -----
    public JButton btnAdd = new JButton("Tambah");
    public JButton btnUpdate = new JButton("Ubah");
    public JButton btnDelete = new JButton("Hapus");
    public JButton btnClear = new JButton("Clear");
    public JButton btnRefresh = new JButton("Refresh");

    // ---- Deklarasi Tabel -----
    public JTable table;
    public DefaultTableModel tableModel;

    // ---- Font -----
    private Font fontPlain = new Font("Times New Roman", Font.PLAIN, 14);
    private Font fontBold = new Font("Times New Roman", Font.BOLD, 14);

    // ---- Konstruktor View -----
    public KursusView() {
        super("Kelasync App - Manajemen Kursus");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tidak tertutup semua
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---- Panel Utama untuk Form -----
        JPanel pnlMainForm = new JPanel(new BorderLayout(10, 10));
        pnlMainForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ---- Panel Input dengan 2 kolom -----
        JPanel pnlInput = new JPanel(new GridLayout(3, 4, 10, 10));

        tfId.setVisible(false);

        // ---- Baris 1 -----
        // Kolom 1: Mata Pelajaran
        pnlInput.add(createLabel("Mata Pelajaran:"));
        pnlInput.add(tfNama);

        // Kolom 3: Hari
        pnlInput.add(createLabel("Hari:"));
        pnlInput.add(cbHari);

        // ---- Baris 2 -----
        // Kolom 1: Materi
        pnlInput.add(createLabel("Materi:"));
        JScrollPane scrollMateri = new JScrollPane(taMateri);
        scrollMateri.setPreferredSize(new Dimension(200, 60));
        pnlInput.add(scrollMateri);

        // Kolom 3: Jam Mulai
        pnlInput.add(createLabel("Jam Mulai (jam:menit):"));
        pnlInput.add(tfJamMulai);

        // ---- Baris 3 -----
        // Kolom 1: Pengajar
        pnlInput.add(createLabel("Pengajar:"));
        pnlInput.add(cbPengajar);

        // Kolom 3: Jam Selesai
        pnlInput.add(createLabel("Jam Selesai (jam:menit):"));
        pnlInput.add(tfJamSelesai);

        // ---- Panel untuk Input + Tombol -----
        JPanel pnlInputWithButtons = new JPanel(new BorderLayout(10, 10));
        pnlInputWithButtons.add(pnlInput, BorderLayout.CENTER);

        // ---- Panel Tombol di pojok kanan bawah -----
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        applyButtonFont(btnAdd);
        applyButtonFont(btnUpdate);
        applyButtonFont(btnDelete);
        applyButtonFont(btnClear);
        applyButtonFont(btnRefresh);

        pnlButton.add(btnAdd);
        pnlButton.add(btnUpdate);
        pnlButton.add(btnDelete);
        pnlButton.add(btnClear);
        pnlButton.add(btnRefresh);

        pnlInputWithButtons.add(pnlButton, BorderLayout.SOUTH);

        // ---- Terapkan Font ke Komponen -----
        tfNama.setFont(fontPlain);
        taMateri.setFont(fontPlain);
        cbHari.setFont(fontPlain);
        tfJamMulai.setFont(fontPlain);
        tfJamSelesai.setFont(fontPlain);
        cbPengajar.setFont(fontPlain);

        // ---- Inisialisasi Tabel -----
        String[] cols = { "ID", "Mata Pelajaran", "Materi", "Hari", "Jam Mulai", "Jam Selesai", "Pengajar ID" };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            } // Tabel tidak bisa diedit langsung
        };
        table = new JTable(tableModel);
        table.setFont(fontPlain);
        table.getTableHeader().setFont(fontBold);
        table.setRowHeight(24);
        JScrollPane spTable = new JScrollPane(table);

        // ---- Isi Combobox Hari -----
        String[] hari = { "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu" };
        for (String h : hari) {
            cbHari.addItem(h);
        }

        // ---- Layout Utama -----
        pnlMainForm.add(pnlInputWithButtons, BorderLayout.NORTH);
        add(pnlMainForm, BorderLayout.NORTH);
        add(spTable, BorderLayout.CENTER);

        // ---- Inisialisasi Controller -----
        this.controller = new KursusController(this);
    }

    // ---- Helper untuk membuat label -----
    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(fontPlain);
        return l;
    }

    // ---- Helper untuk mengatur font tombol -----
    private void applyButtonFont(JButton b) {
        b.setFont(fontBold);
    }

    // ---- Clear Form -----
    public void clearForm() {
        tfId.setText("");
        tfNama.setText("");
        taMateri.setText("");
        tfJamMulai.setText("");
        tfJamSelesai.setText("");
        cbHari.setSelectedIndex(0);
    }
}