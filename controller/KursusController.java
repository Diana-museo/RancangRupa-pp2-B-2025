package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.model.Kursus;
import id.rancangrupa.kelasync.util.DBConnection;
import id.rancangrupa.kelasync.view.KursusView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class KursusController {

    private final KursusView view;
    private final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

    // ---- Konstruktor -----
    public KursusController(KursusView view) {
        this.view = view;
        loadPengajarList(); 
        refreshTable();     
        attachListeners();  
    }

    // ---- Pasang Listener -----
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
            if (!e.getValueIsAdjusting()) {
                populateSelectedRowToForm();
            }
        });
    }

    // ----- Validasi Form -----
    private boolean validateForm(Kursus k) {
        try {
            String nama = view.tfNama.getText().trim();
            String materi = view.taMateri.getText().replace("\n", " ").replace("\r", "").trim();
            String hari = (String) view.cbHari.getSelectedItem();
            String jmStr = view.tfJamMulai.getText().trim();
            String jsStr = view.tfJamSelesai.getText().trim();
            String pengSel = (String) view.cbPengajar.getSelectedItem();

            // 1. Validasi: Tidak boleh kosong
            if (nama.isEmpty() || materi.isEmpty() || jmStr.isEmpty() || jsStr.isEmpty()) {
                throw new Exception("Mata Pelajaran, Materi, dan Jam wajib diisi!");
            }

            // 2. Validasi: Panjang Char
            if (nama.length() > 100) {
                throw new Exception("Nama Mata Pelajaran terlalu panjang!");
            }
            if (materi.length() > 500) {
                throw new Exception("Deskripsi Materi terlalu panjang!");
            }

            // 3. Validasi: Pengajar yang dipilih
            if (pengSel == null || pengSel.isEmpty()) {
                throw new Exception("Pilih pengajar terlebih dahulu!");
            }

            // 4. Validasi: Format Jam
            LocalTime jm = LocalTime.parse(jmStr, TIME_FMT);
            LocalTime js = LocalTime.parse(jsStr, TIME_FMT);

            // 5. Validasi: Urutan Waktu
            if (!jm.isBefore(js)) {
                throw new Exception("Jam Mulai harus sebelum Jam Selesai!");
            }

            int idPeng = Integer.parseInt(pengSel.split(" - ")[0]);

            // Set data ke model
            k.setNamaKursus(nama);
            k.setMateri(materi);
            k.setHari(hari);
            k.setJamMulai(jm);
            k.setJamSelesai(js);
            k.setIdPengajar(idPeng);
            return true;

        } catch (DateTimeParseException e) {
            // Format jam Hour:Minute
            JOptionPane.showMessageDialog(view, "Gunakan format jam Hour:Minute");
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
            return false;
        }
    }

    // ---- Tambah Kursus -----
    public void addKursus() {
        Kursus k = new Kursus();
        if (!validateForm(k)) return;

        String sql = "INSERT INTO kursus (nama_kursus, materi, hari, jam_mulai, jam_selesai, id_pengajar) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getNamaKursus());
            ps.setString(2, k.getMateri());
            ps.setString(3, k.getHari());
            ps.setTime(4, Time.valueOf(k.getJamMulai()));
            ps.setTime(5, Time.valueOf(k.getJamSelesai()));
            ps.setInt(6, k.getIdPengajar());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data Berhasil Ditambah!");
            clearForm();
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Database: " + e.getMessage());
        }
    }

    // ---- Update Kursus -----
    public void updateKursus() {
        String idText = view.tfId.getText();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Pilih data di tabel untuk diubah!");
            return;
        }

        // Konfirmasi Update
        int confirm = JOptionPane.showConfirmDialog(view, "Simpan perubahan data ini?", "Konfirmasi Update", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        Kursus k = new Kursus();
        if (!validateForm(k)) return;

        String sql = "UPDATE kursus SET nama_kursus=?, materi=?, hari=?, jam_mulai=?, jam_selesai=?, id_pengajar=? WHERE id_kursus=?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, k.getNamaKursus());
            ps.setString(2, k.getMateri());
            ps.setString(3, k.getHari());
            ps.setTime(4, Time.valueOf(k.getJamMulai()));
            ps.setTime(5, Time.valueOf(k.getJamSelesai()));
            ps.setInt(6, k.getIdPengajar());
            ps.setInt(7, Integer.parseInt(idText));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data Berhasil Diupdate!");
            clearForm();
            refreshTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Error Update: " + e.getMessage());
        }
    }

    // ---- Hapus Kursus -----
    public void deleteKursus() {
        String idText = view.tfId.getText();
        if (idText.isEmpty()) return;
        
        int confirm = JOptionPane.showConfirmDialog(view, "Hapus data ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement("DELETE FROM kursus WHERE id_kursus=?")) {
                ps.setInt(1, Integer.parseInt(idText));
                ps.executeUpdate();
                clearForm();
                refreshTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(view, "Error Delete: " + e.getMessage());
            }
        }
    }

    // ---- Refresh Tabel -----
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) view.table.getModel();
        model.setRowCount(0);
        String sql = "SELECT * FROM kursus";
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery(sql)) {
            while (rs.next()) {
                Time jamMulai = rs.getTime("jam_mulai");
                Time jamSelesai = rs.getTime("jam_selesai");
                String jmFormatted = (jamMulai != null) ? jamMulai.toLocalTime().format(TIME_FMT) : "";
                String jsFormatted = (jamSelesai != null) ? jamSelesai.toLocalTime().format(TIME_FMT) : "";
                model.addRow(new Object[]{
                        rs.getInt("id_kursus"),
                        rs.getString("nama_kursus"),
                        rs.getString("materi"),
                        rs.getString("hari"),
                        jmFormatted,
                        jsFormatted,
                        rs.getInt("id_pengajar")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----- Load Pengajar -----
    private void loadPengajarList() {
        view.cbPengajar.removeAllItems();
        try (Connection conn = DBConnection.getConnection();
             Statement s = conn.createStatement();
             ResultSet rs = s.executeQuery("SELECT id_pengajar, nama_pengajar FROM pengajar")) {
            while (rs.next()) {
                view.cbPengajar.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ----- Get Data dari Baris Tabel ke Form -----
    private void populateSelectedRowToForm() {
        int row = view.table.getSelectedRow();
        if (row != -1) {
            view.tfId.setText(view.table.getValueAt(row, 0).toString());
            view.tfNama.setText(view.table.getValueAt(row, 1).toString());
            view.taMateri.setText(view.table.getValueAt(row, 2).toString());
            view.cbHari.setSelectedItem(view.table.getValueAt(row, 3).toString());
            view.tfJamMulai.setText(view.table.getValueAt(row, 4).toString());
            view.tfJamSelesai.setText(view.table.getValueAt(row, 5).toString());

            String idPeng = view.table.getValueAt(row, 6).toString();
            for (int i = 0; i < view.cbPengajar.getItemCount(); i++) {
                if (view.cbPengajar.getItemAt(i).startsWith(idPeng)) {
                    view.cbPengajar.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    // ----- Clear Form -----
    private void clearForm() {
        view.tfId.setText("");
        view.tfNama.setText("");
        view.taMateri.setText("");
        view.tfJamMulai.setText("");
        view.tfJamSelesai.setText("");
        view.cbHari.setSelectedIndex(0);
        view.table.clearSelection(); // Tidak ada tabel yang terpilih
    }
}