package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.PengajarController;
import id.rancangrupa.kelasync.util.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PengajarView extends JFrame {

    private Font font = new Font("Times New Roman", Font.PLAIN, 14);
    private Font fontBold = new Font("Times New Roman", Font.BOLD, 14);

    public JTextField txtNama = new JTextField(20);
    public JTextField txtKeahlian = new JTextField(20);
    public JTextField txtNoHp = new JTextField(20);
    public JTextField txtAlamat = new JTextField(20);

    public JButton btnTambah = new JButton("Tambah");
    public JButton btnUbah = new JButton("Ubah");
    public JButton btnHapus = new JButton("Hapus");
    public JButton btnClear = new JButton("Clear");
    public JButton btnRefresh = new JButton("Refresh");

    public JTable tabelPengajar = new JTable();
    public DefaultTableModel tableModel;

    public PengajarView() {
        setTitle("Kelasync App - Manajemen Pengajar");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlInput = new JPanel(new GridLayout(5, 2, 5, 5));
        pnlInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlInput.add(label("Nama Pengajar"));
        pnlInput.add(txtNama);
        pnlInput.add(label("Keahlian"));
        pnlInput.add(txtKeahlian);
        pnlInput.add(label("No HP"));
        pnlInput.add(txtNoHp);
        pnlInput.add(label("Alamat"));
        pnlInput.add(txtAlamat);

        txtNama.setFont(font);
        txtKeahlian.setFont(font);
        txtNoHp.setFont(font);
        txtAlamat.setFont(font);

        JPanel pnlButton = new JPanel();
        buttonFont(btnTambah);
        buttonFont(btnUbah);
        buttonFont(btnHapus);
        buttonFont(btnClear);
        buttonFont(btnRefresh);

        pnlButton.add(btnTambah);
        pnlButton.add(btnUbah);
        pnlButton.add(btnHapus);
        pnlButton.add(btnClear);
        pnlButton.add(btnRefresh);

        pnlInput.add(new JLabel(""));
        pnlInput.add(pnlButton);

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nama", "Keahlian", "No HP", "Alamat"}, 0
        );
        tabelPengajar.setModel(tableModel);
        tabelPengajar.setFont(font);
        tabelPengajar.getTableHeader().setFont(fontBold);
        tabelPengajar.setRowHeight(25);

        add(pnlInput, BorderLayout.NORTH);
        add(new JScrollPane(tabelPengajar), BorderLayout.CENTER);

        new PengajarController(this);
    }

    private JLabel label(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(font);
        return lbl;
    }

    private void buttonFont(JButton btn) {
        btn.setFont(fontBold);
    }
}