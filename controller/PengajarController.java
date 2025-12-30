package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.view.PengajarView;
import id.rancangrupa.kelasync.util.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;

public class PengajarController {
    private PengajarView view;

    public PengajarController(PengajarView view) {
        this.view = view;
        loadData();

        // Action Listeners
        this.view.btnSimpan.addActionListener(e -> simpanData());
        this.view.btnUpdate.addActionListener(e -> updateData());
        this.view.btnHapus.addActionListener(e -> hapusData());
        this.view.btnBersih.addActionListener(e -> resetForm());

        // Listener klik tabel (untuk Update/Hapus)
        this.view.tabelPengajar.getSelectionModel().addListSelectionListener(e -> {
            int row = view.tabelPengajar.getSelectedRow();
            if (row != -1) {
                view.txtNama.setText(view.tableModel.getValueAt(row, 1).toString());
                view.txtKeahlian.setText(view.tableModel.getValueAt(row, 2).toString());
                view.txtNoHp.setText(view.tableModel.getValueAt(row, 3).toString());
                view.txtAlamat.setText(view.tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private void loadData() {
        view.tableModel.setRowCount(0);
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pengajar")) {
            while (rs.next()) {
                view.tableModel.addRow(new Object[]{
                    rs.getInt("id_pengajar"),
                    rs.getString("nama_pengajar"),
                    rs.getString("keahlian_pengajar"),
                    rs.getString("no_hp_pengajar"),
                    rs.getString("alamat_pengajar")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void simpanData() {
        String sql = "INSERT INTO pengajar (nama_pengajar, keahlian_pengajar, no_hp_pengajar, alamat_pengajar) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, view.txtNama.getText());
            pstmt.setString(2, view.txtKeahlian.getText());
            pstmt.setString(3, view.txtNoHp.getText());
            pstmt.setString(4, view.txtAlamat.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data Berhasil Ditambah!");
            loadData();
            resetForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Simpan: " + e.getMessage());
        }
    }

    private void updateData() {
        int row = view.tabelPengajar.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data di tabel untuk diubah!");
            return;
        }
        int id = (int) view.tableModel.getValueAt(row, 0);
        String sql = "UPDATE pengajar SET nama_pengajar=?, keahlian_pengajar=?, no_hp_pengajar=?, alamat_pengajar=? WHERE id_pengajar=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, view.txtNama.getText());
            pstmt.setString(2, view.txtKeahlian.getText());
            pstmt.setString(3, view.txtNoHp.getText());
            pstmt.setString(4, view.txtAlamat.getText());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data Berhasil Diperbarui!");
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void hapusData() {
        int row = view.tabelPengajar.getSelectedRow();
        if (row != -1) {
            int id = (int) view.tableModel.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pengajar WHERE id_pengajar=?")) {
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    loadData();
                    resetForm();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(view, "Gagal Hapus: Data mungkin sedang digunakan di tabel Kursus");
                }
            }
        }
    }

    private void resetForm() {
        view.txtNama.setText("");
        view.txtKeahlian.setText("");
        view.txtNoHp.setText("");
        view.txtAlamat.setText("");
        view.tabelPengajar.clearSelection();
    }
}