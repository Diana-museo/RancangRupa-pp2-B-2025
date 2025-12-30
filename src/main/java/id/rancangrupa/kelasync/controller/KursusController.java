/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.model.Kursus;
import id.rancangrupa.kelasync.util.DBConnection;
import id.rancangrupa.kelasync.view.KursusView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

public class KursusController {
    private final KursusView view;
    private final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");
    private final Map<Integer,String> pengajarMap = new LinkedHashMap<>();

    public KursusController(KursusView view) {
        this.view = view;
        attachListeners();
        loadPengajarList(); 
        refreshTable();     
    }

    private void attachListeners() {
        view.btnAdd.addActionListener(e -> addKursus());
        view.btnUpdate.addActionListener(e -> updateKursus());
        view.btnDelete.addActionListener(e -> deleteKursus());
        view.btnClear.addActionListener(e -> clearForm());
        view.btnRefresh.addActionListener(e -> {
            loadPengajarList();
            refreshTable();
        });

        view.table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) populateSelectedRowToForm();
        });
    }

    private void loadPengajarList() {
        pengajarMap.clear();
        view.cbPengajar.removeAllItems();
        String sql = "SELECT id_pengajar, nama_pengajar FROM pengajar ORDER BY nama_pengajar";
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id_pengajar");
                String nama = rs.getString("nama_pengajar");
                pengajarMap.put(id, nama);
                view.cbPengajar.addItem(id + " - " + nama);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal menampilkan daftar pengajar:\n" + ex.getMessage());
        }
    }

    private void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        String sql = "SELECT id_kursus, nama_kursus, materi, hari, jam_mulai, jam_selesai, id_pengajar FROM kursus ORDER BY id_kursus DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id_kursus");
                String nama = rs.getString("nama_kursus");
                String materi = rs.getString("materi");
                String hari = rs.getString("hari");
                Time tm = rs.getTime("jam_mulai");
                Time ts = rs.getTime("jam_selesai");
                int idPeng = rs.getInt("id_pengajar");
                String jm = tm != null ? tm.toLocalTime().toString() : "";
                String js = ts != null ? ts.toLocalTime().toString() : "";
                model.addRow(new Object[]{id, nama, materi, hari, jm, js, idPeng});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal menampilkan data kursus:\n" + ex.getMessage());
        }
    }

    private void populateSelectedRowToForm() {
        int sel = view.table.getSelectedRow();
        if (sel == -1) return;
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        view.tfId.setText(String.valueOf(model.getValueAt(sel, 0)));
        view.tfNama.setText(String.valueOf(model.getValueAt(sel, 1)));
        view.taMateri.setText(String.valueOf(model.getValueAt(sel, 2)));
        view.cbHari.setSelectedItem(String.valueOf(model.getValueAt(sel, 3)));
        view.tfJamMulai.setText(String.valueOf(model.getValueAt(sel, 4)));
        view.tfJamSelesai.setText(String.valueOf(model.getValueAt(sel, 5)));
        String idPengStr = String.valueOf(model.getValueAt(sel, 6));
        if (idPengStr != null && !idPengStr.isEmpty()) {
            for (int i = 0; i < view.cbPengajar.getItemCount(); i++) {
                String it = view.cbPengajar.getItemAt(i);
                if (it.startsWith(idPengStr + " - ")) {
                    view.cbPengajar.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private boolean validateFormAndParse(Kursus kursusOut) {
        String nama = view.tfNama.getText().trim();
        String materi = view.taMateri.getText().trim();
        String hari = (String) view.cbHari.getSelectedItem();
        String jmText = view.tfJamMulai.getText().trim();
        String jsText = view.tfJamSelesai.getText().trim();
        String pengSel = (String) view.cbPengajar.getSelectedItem();

        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nama mata pelajaran harus diisi");
            view.tfNama.requestFocus();
            return false;
        }
        if (hari == null || hari.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih hari");
            return false;
        }
        LocalTime jm, js;
        try {
            jm = LocalTime.parse(jmText, TIME_FMT);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Format jam mulai harus HH:mm (contoh 08:30)");
            view.tfJamMulai.requestFocus();
            return false;
        }
        try {
            js = LocalTime.parse(jsText, TIME_FMT);
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(view, "Format jam selesai harus HH:mm (contoh 10:00)");
            view.tfJamSelesai.requestFocus();
            return false;
        }
        if (!jm.isBefore(js)) {
            JOptionPane.showMessageDialog(view, "Jam mulai harus lebih awal dari jam selesai");
            return false;
        }
        if (pengSel == null || pengSel.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih pengajar");
            return false;
        }
        int idPeng;
        try {
            idPeng = Integer.parseInt(pengSel.split(" - ")[0]);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Format pengajar tidak tepat");
            return false;
        }

        kursusOut.setNamaKursus(nama);
        kursusOut.setMateri(materi);
        kursusOut.setHari(hari);
        kursusOut.setJamMulai(jm);
        kursusOut.setJamSelesai(js);
        kursusOut.setIdPengajar(idPeng);
        return true;
    }

    private void addKursus() {
        Kursus k = new Kursus();
        if (!validateFormAndParse(k)) return;

        String sql = "INSERT INTO kursus (nama_kursus, materi, hari, jam_mulai, jam_selesai, id_pengajar) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNamaKursus());
            ps.setString(2, k.getMateri());
            ps.setString(3, k.getHari());
            ps.setTime(4, Time.valueOf(k.getJamMulai()));
            ps.setTime(5, Time.valueOf(k.getJamSelesai()));
            ps.setInt(6, k.getIdPengajar());

            int inserted = ps.executeUpdate();
            if (inserted > 0) {
                JOptionPane.showMessageDialog(view, "Kursus berhasil ditambahkan");
                clearForm();
                loadPengajarList();
                refreshTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal menambahkan kursus:\n" + ex.getMessage());
        }
    }

    private void updateKursus() {
        String idText = view.tfId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih kursus dulu dari tabel untuk diupdate");
            return;
        }
        Kursus k = new Kursus();
        if (!validateFormAndParse(k)) return;
        int id = Integer.parseInt(idText);

        String sql = "UPDATE kursus SET nama_kursus=?, materi=?, hari=?, jam_mulai=?, jam_selesai=?, id_pengajar=? WHERE id_kursus=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, k.getNamaKursus());
            ps.setString(2, k.getMateri());
            ps.setString(3, k.getHari());
            ps.setTime(4, Time.valueOf(k.getJamMulai()));
            ps.setTime(5, Time.valueOf(k.getJamSelesai()));
            ps.setInt(6, k.getIdPengajar());
            ps.setInt(7, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(view, "Kursus berhasil diupdate");
                clearForm();
                refreshTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal melakukan update kursus:\n" + ex.getMessage());
        }
    }

    private void deleteKursus() {
        String idText = view.tfId.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih kursus untuk dihapus");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view, "Yakin ingin menghapus kursus?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        int id = Integer.parseInt(idText);

        String sql = "DELETE FROM kursus WHERE id_kursus=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int deleted = ps.executeUpdate();
            if (deleted > 0) {
                JOptionPane.showMessageDialog(view, "Kursus berhasil dihapus");
                clearForm();
                refreshTable();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Gagal menghapus kursus:\n" + ex.getMessage());
        }
    }

    private void clearForm() {
        view.tfId.setText("");
        view.tfNama.setText("");
        view.taMateri.setText("");
        view.tfJamMulai.setText("");
        view.tfJamSelesai.setText("");
        loadPengajarList();
        view.cbHari.setSelectedIndex(0);
    }
}
