package id.rancangrupa.kelasync.view;

import id.rancangrupa.kelasync.controller.PesertaController;
import javax.swing.*;
import id.rancangrupa.kelasync.util.DBConnection;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PesertaView extends JFrame {

    // ---- Controller ----
    private PesertaController controller;

    // ---- Komponen Form ----
    public JTextField tfId = new JTextField();
    public JTextField tfNama = new JTextField();
    public JTextField tfNoHp = new JTextField();
    public JTextArea taAlamat = new JTextArea(3, 20);

    // ---- Tombol ----
    public JButton btnAdd = new JButton("Tambah");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnDelete = new JButton("Hapus");
    public JButton btnClear = new JButton("Clear");
    public JButton btnRefresh = new JButton("Refresh");

    // ---- Tabel ----
    public JTable table;
    public DefaultTableModel tableModel;

    // ---- Konstruktor ----
    public PesertaView() {
        super("Manajemen Peserta");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);

        // ---- Panel Form ----
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfId.setVisible(false);

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Nama Peserta:"), c);
        c.gridx = 1; form.add(tfNama, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("No HP:"), c);
        c.gridx = 1; form.add(tfNoHp, c);

        c.gridx = 0; c.gridy = 2;
        form.add(new JLabel("Alamat:"), c);
        c.gridx = 1; form.add(new JScrollPane(taAlamat), c);

        // ---- Panel Tombol ----
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);
        buttons.add(btnRefresh);

        // ---- Tabel ----
        String[] cols = {"ID", "Nama", "No HP", "Alamat"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane spTable = new JScrollPane(table);
        spTable.setPreferredSize(new Dimension(780, 280));

        // ---- Layout Utama ----
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.NORTH);
        getContentPane().add(buttons, BorderLayout.CENTER);
        getContentPane().add(spTable, BorderLayout.SOUTH);

        // ---- Apply Font ----
        applyTimesNewRoman();

        // ---- Init Controller ----
        this.controller = new PesertaController(this);

        setVisible(true);
    }

    // ---- Font ----
    private void applyTimesNewRoman() {
        Font plain = new Font("Times New Roman", Font.PLAIN, 12);
        Font bold  = new Font("Times New Roman", Font.BOLD, 12);

        if (table.getTableHeader() != null) {
            table.getTableHeader().setFont(bold);
        }
        setFontRecursively(getContentPane(), plain);
    }

    private void setFontRecursively(Component c, Font f) {
        c.setFont(f);
        if (c instanceof Container) {
            for (Component child : ((Container) c).getComponents()) {
                setFontRecursively(child, f);
            }
        }
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