package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.view.PengajarView;
import id.rancangrupa.kelasync.util.DBConnection;
import javax.swing.*;
import java.sql.*;

public class PengajarController {

    private PengajarView view;

    public PengajarController(PengajarView view) {
        this.view = view;

        loadData();

        view.btnTambah.addActionListener(e -> simpanData());
        view.btnUbah.addActionListener(e -> updateData());
        view.btnHapus.addActionListener(e -> hapusData());
        view.btnClear.addActionListener(e -> resetForm());
        view.btnRefresh.addActionListener(e -> loadData());

        view.tabelPengajar.getSelectionModel().addListSelectionListener(e -> {
            int row = view.tabelPengajar.getSelectedRow();
            if (row != -1) {
                view.txtNama.setText(view.tableModel.getValueAt(row, 1).toString());
                view.txtKeahlian.setText(view.tableModel.getValueAt(row, 2).toString());
                view.txtNoHp.setText(view.tableModel.getValueAt(row, 3).toString());
                view.txtAlamat.setText(view.tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    private boolean validasiInput() {
        if (
            view.txtNama.getText().trim().isEmpty() ||
            view.txtKeahlian.getText().trim().isEmpty() ||
            view.txtNoHp.getText().trim().isEmpty() ||
            view.txtAlamat.getText().trim().isEmpty()
        ) {
            JOptionPane.showMessageDialog(view,
                    "Semua kolom wajib diisi!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!view.txtNoHp.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(view,
                    "Nomor HP hanya boleh berisi angka!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
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
        if (!validasiInput()) return;

        String sql = "INSERT INTO pengajar (nama_pengajar, keahlian_pengajar, no_hp_pengajar, alamat_pengajar) VALUES (?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, view.txtNama.getText());
            ps.setString(2, view.txtKeahlian.getText());
            ps.setString(3, view.txtNoHp.getText());
            ps.setString(4, view.txtAlamat.getText());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data berhasil ditambahkan!");

            loadData();
            resetForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal menambah data!");
        }
    }

    private void updateData() {
        int row = view.tabelPengajar.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view,
                    "Pilih data yang akan diubah!",
                    "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validasiInput()) return;

        int id = (int) view.tableModel.getValueAt(row, 0);

        String sql = "UPDATE pengajar SET nama_pengajar=?, keahlian_pengajar=?, no_hp_pengajar=?, alamat_pengajar=? WHERE id_pengajar=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, view.txtNama.getText());
            ps.setString(2, view.txtKeahlian.getText());
            ps.setString(3, view.txtNoHp.getText());
            ps.setString(4, view.txtAlamat.getText());
            ps.setInt(5, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data berhasil diubah!");

            loadData();
            resetForm();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal mengubah data!");
        }
    }

    private void hapusData() {
        int row = view.tabelPengajar.getSelectedRow();
        if (row == -1) return;

        int id = (int) view.tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(view,
                "Yakin hapus data ini?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "DELETE FROM pengajar WHERE id_pengajar=?")) {

                ps.setInt(1, id);
                ps.executeUpdate();

                loadData();
                resetForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view,
                        "Gagal hapus data (terkait tabel lain)");
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
