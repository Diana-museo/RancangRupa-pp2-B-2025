package id.rancangrupa.kelasync2.controller;

import id.rancangrupa.kelasync2.util.DBConnection;
import id.rancangrupa.kelasync2.view.PendaftaranView;
import id.rancangrupa.kelasync2.view.ReportView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PendaftaranController {

    private final PendaftaranView view;

    // Konstruktor controller
    // Dipanggil saat PendaftaranView dibuat
    public PendaftaranController(PendaftaranView view) {
        this.view = view;
        loadPeserta();
        loadKursus();
        loadData();
        attachListener();
    }

    // Menghubungkan semua tombol dengan aksi
    private void attachListener() {

        view.btnTambah.addActionListener(e -> {
            try {
                int idPeserta = Integer.parseInt(
                        view.cbPeserta.getSelectedItem().toString().split(" - ")[0]);
                int idKursus = Integer.parseInt(
                        view.cbKursus.getSelectedItem().toString().split(" - ")[0]);

                insert(idPeserta, idKursus);
                loadData();
                bersihkanForm();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Input tidak valid");
            }
        });

        view.btnHapus.addActionListener(e -> {
            int row = view.tabel.getSelectedRow();
            if (row >= 0) {
                int id = Integer.parseInt(
                        view.tableModel.getValueAt(row, 0).toString());
                delete(id);
                loadData();
                bersihkanForm();
            } else {
                JOptionPane.showMessageDialog(view, "Pilih data yang akan dihapus terlebih dahulu");
            }
        });

        view.btnClear.addActionListener(e -> bersihkanForm());

        // Tombol Refresh - mengambil data terbaru dari database
        view.btnRefresh.addActionListener(e -> {
            refreshData();
        });

        view.btnReport.addActionListener(e -> {
            new ReportView().setVisible(true);
            view.dispose();
        });
    }

    // Mengambil data peserta untuk combobox
    private void loadPeserta() {
        view.cbPeserta.removeAllItems();
        try (Connection c = DBConnection.getConnection();
                Statement s = c.createStatement();
                ResultSet r = s.executeQuery(
                        "SELECT id_peserta, nama_peserta FROM peserta")) {

            while (r.next()) {
                view.cbPeserta.addItem(
                        r.getInt(1) + " - " + r.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading peserta: " + e.getMessage());
        }
    }

    // Mengambil data kursus untuk combobox
    private void loadKursus() {
        view.cbKursus.removeAllItems();
        try (Connection c = DBConnection.getConnection();
                Statement s = c.createStatement();
                ResultSet r = s.executeQuery(
                        "SELECT id_kursus, nama_kursus FROM kursus")) {

            while (r.next()) {
                view.cbKursus.addItem(
                        r.getInt(1) + " - " + r.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading kursus: " + e.getMessage());
        }
    }

    // Menampilkan data pendaftaran ke tabel
    private void loadData() {
        view.tableModel.setRowCount(0);

        String sql = """
                    SELECT d.id_pendaftaran, s.nama_peserta, k.nama_kursus, d.tanggal_daftar
                    FROM pendaftaran d
                    JOIN peserta s ON d.id_peserta = s.id_peserta
                    JOIN kursus k ON d.id_kursus = k.id_kursus
                """;

        try (Connection c = DBConnection.getConnection();
                Statement s = c.createStatement();
                ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                view.tableModel.addRow(new Object[] {
                        r.getInt("id_pendaftaran"),
                        r.getString("nama_peserta"),
                        r.getString("nama_kursus"),
                        r.getDate("tanggal_daftar")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error loading data: " + e.getMessage());
        }
    }

    // Fungsi untuk merefresh semua data (tabel dan combobox)
    private void refreshData() {
        try {
            // Refresh combobox peserta
            loadPeserta();

            // Refresh combobox kursus
            loadKursus();

            // Refresh tabel pendaftaran
            loadData();

            JOptionPane.showMessageDialog(view, "Data berhasil di-refresh");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error refreshing data: " + e.getMessage());
        }
    }

    // Mengecek apakah peserta sudah terdaftar di kursus yang sama
    private boolean isSudahTerdaftar(int idPeserta, int idKursus) {
        try {
            String sql = """
                        SELECT COUNT(*)
                        FROM pendaftaran
                        WHERE id_peserta = ? AND id_kursus = ?
                    """;

            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, idPeserta);
            ps.setInt(2, idKursus);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error checking duplicate: " + e.getMessage());
        }
        return false;
    }

    // Menyimpan data pendaftaran dengan validasi duplikasi
    private void insert(int idPeserta, int idKursus) {

        // Cegah peserta mendaftar kursus yang sama lebih dari sekali
        if (isSudahTerdaftar(idPeserta, idKursus)) {
            JOptionPane.showMessageDialog(
                    view,
                    "Peserta sudah terdaftar pada kursus tersebut");
            return;
        }

        try {
            String sql = "INSERT INTO pendaftaran (id_peserta, id_kursus) VALUES (?, ?)";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, idPeserta);
            ps.setInt(2, idKursus);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(view, "Pendaftaran berhasil ditambahkan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error inserting data: " + e.getMessage());
        }
    }

    // Menghapus data pendaftaran berdasarkan ID
    private void delete(int id) {
        try {
            String sql = "DELETE FROM pendaftaran WHERE id_pendaftaran = ?";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(view, "Data berhasil dihapus");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Error deleting data: " + e.getMessage());
        }
    }

    // Membersihkan input form
    private void bersihkanForm() {
        if (view.cbPeserta.getItemCount() > 0) {
            view.cbPeserta.setSelectedIndex(0);
        }
        if (view.cbKursus.getItemCount() > 0) {
            view.cbKursus.setSelectedIndex(0);
        }
        view.tfTanggal.setText("");
        view.tabel.clearSelection();
    }
}