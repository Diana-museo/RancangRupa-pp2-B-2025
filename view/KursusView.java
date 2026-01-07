package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.KursusController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KursusView extends JFrame {

    // ---- Deklarasi Controller -----
    private KursusController controller;

    // ---- Deklarasi Komponen Form -----
    public JTextField tfId = new JTextField();
    public JTextField tfNama = new JTextField();
    public JTextArea taMateri = new JTextArea(3, 20);
    public JComboBox<String> cbHari = new JComboBox<>();
    public JTextField tfJamMulai = new JTextField();
    public JTextField tfJamSelesai = new JTextField();
    public JComboBox<String> cbPengajar = new JComboBox<>();

    // ---- Deklarasi Tombol -----
    public JButton btnAdd = new JButton("Simpan"); 
    public JButton btnUpdate = new JButton("Update");
    public JButton btnDelete = new JButton("Hapus");
    public JButton btnClear = new JButton("Bersih"); 
    public JButton btnRefresh = new JButton("Refresh");

    // ---- Deklarasi Tabel -----
    public JTable table;
    public DefaultTableModel tableModel;

    // ---- Konstruktor View -----
    public KursusView() {
        super("Kelasync App - Manajemen Kursus");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Tidak tertutup semua
        setSize(900, 600);
        setLocationRelativeTo(null);

        // ---- Panel Form Input -----
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 10, 6, 10);
        c.anchor = GridBagConstraints.WEST;

        tfId.setVisible(false);
        
        // ---- Tambah Label dan Field -----
        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Mata Pelajaran:"), c);
        c.gridx = 1; c.weightx = 1.0; c.fill = GridBagConstraints.HORIZONTAL;
        form.add(tfNama, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0;
        form.add(new JLabel("Materi:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        form.add(new JScrollPane(taMateri), c);

        c.gridx = 0; c.gridy = 2;
        form.add(new JLabel("Hari:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.NONE;
        form.add(cbHari, c);

        c.gridx = 0; c.gridy = 3;
        form.add(new JLabel("Jam Mulai:"), c);
        c.gridx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        form.add(tfJamMulai, c);

        c.gridx = 0; c.gridy = 4;
        form.add(new JLabel("Jam Selesai:"), c);
        c.gridx = 1; form.add(tfJamSelesai, c);

        c.gridx = 0; c.gridy = 5;
        form.add(new JLabel("Pengajar:"), c);
        c.gridx = 1; form.add(cbPengajar, c);

        // ---- Panel Tombol -----
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnAdd); 
        buttons.add(btnUpdate); 
        buttons.add(btnDelete); 
        buttons.add(btnClear); 
        buttons.add(btnRefresh);

        // Tombol rapi di bagian atas
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(form, BorderLayout.CENTER);
        northPanel.add(buttons, BorderLayout.SOUTH);

        // ---- Inisialisasi Tabel -----
        String[] cols = {"ID", "Mata Pelajaran", "Materi", "Hari", "Jam Mulai", "Jam Selesai", "Pengajar ID"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override 
            public boolean isCellEditable(int r, int c) { return false; } // Tabel tidak bisa diedit langsung
        };
        table = new JTable(tableModel);
        JScrollPane spTable = new JScrollPane(table);

        // ---- Isi Combobox Hari -----
        String[] hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        for (String h : hari) { 
            cbHari.addItem(h); 
        }

        // ---- Layout Utama -----
        getContentPane().setLayout(new BorderLayout(10, 10));
        getContentPane().add(northPanel, BorderLayout.NORTH);
        getContentPane().add(spTable, BorderLayout.CENTER);

        // ---- Apply Font -----
        applyTimesNewRoman();

        // ---- Inisialisasi Controller -----
        this.controller = new KursusController(this);
    }

    // ---- Apply Font Times New Roman -----
    private void applyTimesNewRoman() {
        Font plain = new Font("Times New Roman", Font.PLAIN, 12);
        Font bold  = new Font("Times New Roman", Font.BOLD, 12);
        if (table.getTableHeader() != null) {
            table.getTableHeader().setFont(bold); // header tabel bold
        }
        setFontRecursively(getContentPane(), plain);
    }

    // ---- Set Font Rekursif -----
    private void setFontRecursively(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFontRecursively(child, font);
            }
        }
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