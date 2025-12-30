package id.rancangrupa.kelasync.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PesertaView extends JFrame {

    public JTextField tfId = new JTextField();
    public JTextField tfNama = new JTextField();
    public JTextField tfNoHp = new JTextField();
    public JTextArea taAlamat = new JTextArea(3, 20);

    public JButton btnAdd = new JButton("Tambah");
    public JButton btnUpdate = new JButton("Update");
    public JButton btnDelete = new JButton("Hapus");
    public JButton btnClear = new JButton("Clear");
    public JButton btnRefresh = new JButton("Refresh");

    public JTable table;
    public DefaultTableModel tableModel;

    // Konstruktor kelas peserta view
    public PesertaView() {
        super("Manajemen Peserta Kursus");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 550);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        tfId.setVisible(false);

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Nama Peserta:"), c);
        c.gridx = 1;
        form.add(tfNama, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("No HP:"), c);
        c.gridx = 1;
        form.add(tfNoHp, c);

        c.gridx = 0; c.gridy = 2;
        form.add(new JLabel("Alamat:"), c);
        c.gridx = 1;
        form.add(new JScrollPane(taAlamat), c);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttons.add(btnAdd);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnClear);
        buttons.add(btnRefresh);

        String[] cols = {
                "ID", "Nama Peserta", "No HP", "Alamat"
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
        spTable.setPreferredSize(new Dimension(820, 280));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.NORTH);
        getContentPane().add(buttons, BorderLayout.CENTER);
        getContentPane().add(spTable, BorderLayout.SOUTH);

        applyTimesNewRoman();
    }

    private void applyTimesNewRoman() {
        Font plain = new Font("Times New Roman", Font.PLAIN, 12);
        Font bold  = new Font("Times New Roman", Font.BOLD, 12);

        tfNama.setFont(plain);
        tfNoHp.setFont(plain);
        taAlamat.setFont(plain);

        btnAdd.setFont(plain);
        btnUpdate.setFont(plain);
        btnDelete.setFont(plain);
        btnClear.setFont(plain);
        btnRefresh.setFont(plain);

        table.setFont(plain);
        table.setRowHeight(22);
        table.getTableHeader().setFont(bold);

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
        tfNoHp.setText("");
        taAlamat.setText("");
        table.clearSelection();
    }
}
