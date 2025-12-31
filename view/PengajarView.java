package id.rancangrupa.kelasync.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PengajarView extends JFrame {
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
        pnlInput.add(new JLabel("Nama Pengajar:")); pnlInput.add(txtNama);
        pnlInput.add(new JLabel("Keahlian:")); pnlInput.add(txtKeahlian);
        pnlInput.add(new JLabel("No HP:")); pnlInput.add(txtNoHp);
        pnlInput.add(new JLabel("Alamat:")); pnlInput.add(txtAlamat);
        
        // Panel Tombol
        JPanel pnlTombol = new JPanel();
        pnlTombol.add(btnSimpan);
        pnlTombol.add(btnUpdate);
        pnlTombol.add(btnHapus);
        pnlTombol.add(btnBersih);
        pnlInput.add(new JLabel("")); pnlInput.add(pnlTombol);

        // Tabel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nama", "Keahlian", "No HP", "Alamat"}, 0);
        tabelPengajar.setModel(tableModel);

        add(pnlInput, BorderLayout.NORTH);
        add(new JScrollPane(tabelPengajar), BorderLayout.CENTER);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}