package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.PengajarController;
import id.rancangrupa.kelasync.util.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PengajarView extends JFrame {
    // Inisialisasi Font
    private Font customFont = new Font("Times New Roman", Font.PLAIN, 14);
    private Font headerFont = new Font("Times New Roman", Font.BOLD, 14);

    public JTextField txtNama = new JTextField(20);
    public JTextField txtKeahlian = new JTextField(20);
    public JTextField txtNoHp = new JTextField(20);
    public JTextField txtAlamat = new JTextField(20);
    
    public JButton btnSimpan = new JButton("Simpan");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnHapus = new JButton("Hapus");
    public JButton btnBersih = new JButton("Bersih");
    
    public JTable tabelPengajar = new JTable();
    public DefaultTableModel tableModel;

    public PengajarView() {
        setTitle("Kelasync App - Manajemen Pengajar");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Input
        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Menambahkan Label dengan Font Times New Roman
        pnlInput.add(createStyledLabel("Nama Pengajar:")); pnlInput.add(txtNama);
        pnlInput.add(createStyledLabel("Keahlian:")); pnlInput.add(txtKeahlian);
        pnlInput.add(createStyledLabel("No HP:")); pnlInput.add(txtNoHp);
        pnlInput.add(createStyledLabel("Alamat:")); pnlInput.add(txtAlamat);
        
        // Terapkan font ke TextField
        txtNama.setFont(customFont);
        txtKeahlian.setFont(customFont);
        txtNoHp.setFont(customFont);
        txtAlamat.setFont(customFont);

        // Panel Tombol
        JPanel pnlTombol = new JPanel();
        applyButtonFont(btnSimpan);
        applyButtonFont(btnUpdate);
        applyButtonFont(btnHapus);
        applyButtonFont(btnBersih);
        
        pnlTombol.add(btnSimpan);
        pnlTombol.add(btnUpdate);
        pnlTombol.add(btnHapus);
        pnlTombol.add(btnBersih);
        pnlInput.add(new JLabel("")); pnlInput.add(pnlTombol);

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Keahlian", "No HP", "Alamat"}, 0);
        tabelPengajar.setModel(tableModel);
        tabelPengajar.setFont(customFont); // Font isi tabel
        tabelPengajar.getTableHeader().setFont(headerFont); // Font judul kolom
        tabelPengajar.setRowHeight(25); // Menyesuaikan tinggi baris agar font terlihat bagus

        add(pnlInput, BorderLayout.NORTH);
        add(new JScrollPane(tabelPengajar), BorderLayout.CENTER);
        
        new PengajarController(this);
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

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}