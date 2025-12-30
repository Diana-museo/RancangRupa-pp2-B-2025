package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.model.Peserta;
import id.rancangrupa.kelasync.util.DBConnection;
import id.rancangrupa.kelasync.view.PesertaView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class PesertaController {

    private final PesertaView view;

    public PesertaController(PesertaView view) {
        this.view = view;
        attachListeners();
        refreshTable();
    }

    private void attachListeners() {
        view.btnAdd.addActionListener(e -> addPeserta());
        view.btnUpdate.addActionListener(e -> updatePeserta());
        view.btnDelete.addActionListener(e -> deletePeserta());
        view.btnClear.addActionListener(e -> clearForm());
        view.btnRefresh.addActionListener(e -> refreshTable());

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) populateSelectedRowToForm();
        });
    }

    // ================= TABLE =================
    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);

        String sql = """
            SELECT id_peserta, nama_peserta, no_hp_peserta, alamat_peserta
            FROM peserta
            ORDER BY id_peserta DESC
        """;

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id_peserta"),
                        rs.getString("nama_peserta"),
                        rs.getString("no_hp_peserta"),
                        rs.getString("alamat_peserta")
                });
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Gagal memuat data peserta:\n" + ex.getMessage());
        }
    }

    private void populateSelectedRowToForm() {
        int row = view.table.getSelectedRow();
        if (row == -1) return;

        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        view.tfId.setText(model.getValueAt(row, 0).toString());
        view.tfNama.setText(model.getValueAt(row, 1).toString());
        view.tfNoHp.setText(model.getValueAt(row, 2).toString());
        view.taAlamat.setText(model.getValueAt(row, 3).toString());
    }

    // ================= VALIDATION =================
    private boolean validateForm(Peserta p) {
        String nama = view.tfNama.getText().trim();
        String noHp = view.tfNoHp.getText().trim();
        String alamat = view.taAlamat.getText().trim();

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama peserta wajib diisi");
            view.tfNama.requestFocus();
            return false;
        }

        if (noHp.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No HP wajib diisi");
            view.tfNoHp.requestFocus();
            return false;
        }

        if (!noHp.matches("[0-9+ ]{8,15}")) {
            JOptionPane.showMessageDialog(view,
                    "No HP hanya boleh angka (8–15 digit)");
            view.tfNoHp.requestFocus();
            return false;
        }

        if (alamat.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Alamat wajib diisi");
            view.taAlamat.requestFocus();
            return false;
        }

        // set ke model
        p.setNamaPeserta(nama);
        p.setNoHpPeserta(noHp);
        p.setAlamatPeserta(alamat);
        return true;
    }

    // ================= CRUD =================
    private void addPeserta() {
        Peserta p = new Peserta();
        if (!validateForm(p)) return;

        String sql = """
            INSERT INTO peserta (nama_peserta, no_hp_peserta, alamat_peserta)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaPeserta());
            ps.setString(2, p.getNoHpPeserta());
            ps.setString(3, p.getAlamatPeserta());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Peserta berhasil ditambahkan");
            clearForm();
            refreshTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Gagal menambahkan peserta:\n" + ex.getMessage());
        }
    }

    private void updatePeserta() {
        if (view.tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Pilih peserta dari tabel untuk diupdate");
            return;
        }

        Peserta p = new Peserta();
        if (!validateForm(p)) return;

        int id = Integer.parseInt(view.tfId.getText());

        String sql = """
            UPDATE peserta
            SET nama_peserta=?, no_hp_peserta=?, alamat_peserta=?
            WHERE id_peserta=?
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getNamaPeserta());
            ps.setString(2, p.getNoHpPeserta());
            ps.setString(3, p.getAlamatPeserta());
            ps.setInt(4, id);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Peserta berhasil diupdate");
            clearForm();
            refreshTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Gagal mengupdate peserta:\n" + ex.getMessage());
        }
    }

    private void deletePeserta() {
        if (view.tfId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Pilih peserta yang ingin dihapus");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Yakin ingin menghapus peserta?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) return;

        int id = Integer.parseInt(view.tfId.getText());

        String sql = "DELETE FROM peserta WHERE id_peserta=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Peserta berhasil dihapus");
            clearForm();
            refreshTable();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view,
                    "Gagal menghapus peserta:\n" + ex.getMessage());
        }
    }

    private void clearForm() {
        view.tfId.setText("");
        view.tfNama.setText("");
        view.tfNoHp.setText("");
        view.taAlamat.setText("");
        view.table.clearSelection();
    }
}
