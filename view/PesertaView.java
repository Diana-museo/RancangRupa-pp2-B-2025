package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.PesertaController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PesertaView extends JFrame {

    // ---- Controller ----
    private PesertaController controller;

    // ---- Komponen Form ----
    public JTextField tfId = new JTextField();
    public JTextField tfNama = new JTextField(20);
    public JTextField tfNoHp = new JTextField(20);
    public JTextArea taAlamat = new JTextArea(3, 20);

    // ---- Tombol ----
    public JButton btnAdd = new JButton("Tambah");
    public JButton btnUpdate = new JButton("Ubah");
    public JButton btnDelete = new JButton("Hapus");
    public JButton btnClear = new JButton("Clear");
    public JButton btnRefresh = new JButton("Refresh");

    // ---- Tabel ----
    public JTable table;
    public DefaultTableModel tableModel;

    // ---- Font ----
    private Font customFont = new Font("Times New Roman", Font.PLAIN, 14);
    private Font headerFont = new Font("Times New Roman", Font.BOLD, 14);

    // ---- Konstruktor ----
    public PesertaView() {
        super("Kelasync App - Manajemen Peserta");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---- Event saat window ditutup ----
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // tampilkan kembali Kelasync App (frame lain yang masih ada)
                for (Frame f : Frame.getFrames()) {
                    if (f != PesertaView.this && f.isDisplayable()) {
                        f.setVisible(true);
                    }
                }
            }
        });

        // ---- Panel Input ----
        JPanel pnlInput = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tfId.setVisible(false);

        // Menambahkan Label dan Field
        pnlInput.add(createStyledLabel("Nama Peserta:"));
        pnlInput.add(tfNama);

        pnlInput.add(createStyledLabel("No HP:"));
        pnlInput.add(tfNoHp);

        pnlInput.add(createStyledLabel("Alamat:"));
        pnlInput.add(new JScrollPane(taAlamat));

        // ---- Panel Tombol ----
        JPanel pnlTombol = new JPanel();
        applyButtonFont(btnAdd);
        applyButtonFont(btnUpdate);
        applyButtonFont(btnDelete);
        applyButtonFont(btnClear);
        applyButtonFont(btnRefresh);

        pnlTombol.add(btnAdd);
        pnlTombol.add(btnUpdate);
        pnlTombol.add(btnDelete);
        pnlTombol.add(btnClear);
        pnlTombol.add(btnRefresh);

        pnlInput.add(new JLabel(""));
        pnlInput.add(pnlTombol);

        // ---- Terapkan Font ke Komponen ----
        tfNama.setFont(customFont);
        tfNoHp.setFont(customFont);
        taAlamat.setFont(customFont);

        // ---- Tabel ----
        String[] cols = { "ID", "Nama", "No HP", "Alamat" };
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setFont(customFont); // Font isi tabel
        table.getTableHeader().setFont(headerFont); // Font judul kolom
        table.setRowHeight(25); // Menyesuaikan tinggi baris agar font terlihat bagus
        JScrollPane spTable = new JScrollPane(table);

        // ---- Layout Utama ----
        add(pnlInput, BorderLayout.NORTH);
        add(spTable, BorderLayout.CENTER);

        // ---- Init Controller ----
        controller = new PesertaController(this);

        setVisible(true);
    }

    // Helper method untuk membuat Label dengan font custom
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(customFont);
        return label;
    }

    // Helper method untuk menerapkan font ke tombol
    private void applyButtonFont(JButton button) {
        button.setFont(headerFont);
    }

    // ---- Clear Form ----
    public void clearForm() {
        tfId.setText("");
        tfNama.setText("");
        tfNoHp.setText("");
        taAlamat.setText("");
        table.clearSelection();
    }
}