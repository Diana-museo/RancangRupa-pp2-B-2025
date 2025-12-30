/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.rancangrupa.kelasync.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class KursusView extends JFrame {

    public JTextField tfId = new JTextField();
    public JTextField tfNama = new JTextField();
    public JTextArea taMateri = new JTextArea(3, 20);
    public JComboBox<String> cbHari = new JComboBox<>();
    public JTextField tfJamMulai = new JTextField();
    public JTextField tfJamSelesai = new JTextField();
    public JComboBox<String> cbPengajar = new JComboBox<>();

    public JButton btnAdd = new JButton("Tambah");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnDelete = new JButton("Hapus");
    public JButton btnClear = new JButton("Clear");
    public JButton btnRefresh = new JButton("Refresh");

    public JTable table;
    public DefaultTableModel tableModel;

    public KursusView() {
        super("Manajemen Kursus (Mata Pelajaran SMA)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfId.setVisible(false);

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Mata Pelajaran:"), c);
        c.gridx = 1;
        form.add(tfNama, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Materi:"), c);
        c.gridx = 1;
        form.add(new JScrollPane(taMateri), c);

        c.gridx = 0; c.gridy = 2;
        form.add(new JLabel("Hari:"), c);
        c.gridx = 1;
        form.add(cbHari, c);

        c.gridx = 0; c.gridy = 3;
        form.add(new JLabel("Jam Mulai (HH:mm):"), c);
        c.gridx = 1;
        form.add(tfJamMulai, c);

        c.gridx = 0; c.gridy = 4;
        form.add(new JLabel("Jam Selesai (HH:mm):"), c);
        c.gridx = 1;
        form.add(tfJamSelesai, c);

        c.gridx = 0; c.gridy = 5;
        form.add(new JLabel("Pengajar:"), c);
        c.gridx = 1;
        form.add(cbPengajar, c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);
        buttons.add(btnRefresh);

        String[] cols = {
            "ID", "Mata Pelajaran", "Materi",
            "Hari", "Jam Mulai", "Jam Selesai", "Pengajar ID"
        };

        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane spTable = new JScrollPane(table);
        spTable.setPreferredSize(new Dimension(880, 300));

        String[] hari = {
            "Senin", "Selasa", "Rabu",
            "Kamis", "Jumat", "Sabtu", "Minggu"
        };
        for (String h : hari) {
            cbHari.addItem(h);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.NORTH);
        getContentPane().add(buttons, BorderLayout.CENTER);
        getContentPane().add(spTable, BorderLayout.SOUTH);

        // 🔥 PASTIIN FONT TIMES NEW ROMAN
        applyTimesNewRoman();
    }

    private void applyTimesNewRoman() {
        Font plain = new Font("Times New Roman", Font.PLAIN, 12);
        Font bold  = new Font("Times New Roman", Font.BOLD, 12);

        tfNama.setFont(plain);
        taMateri.setFont(plain);
        cbHari.setFont(plain);
        tfJamMulai.setFont(plain);
        tfJamSelesai.setFont(plain);
        cbPengajar.setFont(plain);

        btnAdd.setFont(plain);
        btnUpdate.setFont(plain);
        btnDelete.setFont(plain);
        btnClear.setFont(plain);
        btnRefresh.setFont(plain);

        table.setFont(plain);
        table.setRowHeight(22);
        table.getTableHeader().setFont(bold);

        // Set semua JLabel & komponen lain
        setFontRecursively(getContentPane(), plain);
    }

    private void setFontRecursively(Component component, Font font) {
        component.setFont(font);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setFontRecursively(child, font);
            }
        }
    }

    public void clearForm() {
        tfId.setText("");
        tfNama.setText("");
        taMateri.setText("");
        cbHari.setSelectedIndex(0);
        tfJamMulai.setText("");
        tfJamSelesai.setText("");
        cbPengajar.removeAllItems();
    }
}
