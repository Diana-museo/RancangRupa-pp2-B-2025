package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.view.PengajarView;
import id.rancangrupa.kelasync.util.DBConnection;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 * PengajarController berfungsi sebagai otak yang mengatur 
 * alur data antara Database dan Tampilan (View).
 */
public class PengajarController {
    private PengajarView view;

    public PengajarController(PengajarView view) {
        this.view = view;
        
        // Memanggil data dari DB saat aplikasi pertama kali dibuka
        loadData();

        // --- MENGATUR ACTION LISTENER (Logika Klik Tombol) ---
        
        // Saat tombol Simpan diklik
        this.view.btnSimpan.addActionListener(e -> simpanData());
        
        // Saat tombol Update diklik
        this.view.btnUpdate.addActionListener(e -> updateData());
        
        // Saat tombol Hapus diklik
        this.view.btnHapus.addActionListener(e -> hapusData());
        
        // Saat tombol Bersih diklik
        this.view.btnBersih.addActionListener(e -> resetForm());

        // --- LOGIKA KLIK TABEL (Selection Listener) ---
        // Jika baris di tabel diklik, data akan muncul kembali di kolom input (TextField)
        this.view.tabelPengajar.getSelectionModel().addListSelectionListener(e -> {
            int row = view.tabelPengajar.getSelectedRow();
            if (row != -1) { // Jika ada baris yang terpilih
                // Mengambil data dari kolom tabel (index 1-4) dan set ke TextField
                view.txtNama.setText(view.tableModel.getValueAt(row, 1).toString());
                view.txtKeahlian.setText(view.tableModel.getValueAt(row, 2).toString());
                view.txtNoHp.setText(view.tableModel.getValueAt(row, 3).toString());
                view.txtAlamat.setText(view.tableModel.getValueAt(row, 4).toString());
            }
        });
    }

    /**
     * READ: Mengambil data dari MySQL dan menampilkannya ke JTable
     */
    private void loadData() {
        // Hapus semua baris di tabel UI agar tidak menumpuk saat refresh
        view.tableModel.setRowCount(0);
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM pengajar")) {
            
            while (rs.next()) {
                // Menambahkan baris demi baris ke model tabel UI
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

    /**
     * CREATE: Menambah data pengajar baru ke database
     */
    private void simpanData() {
        // Query SQL dengan Parameter (?) untuk keamanan (Anti SQL Injection)
        String sql = "INSERT INTO pengajar (nama_pengajar, keahlian_pengajar, no_hp_pengajar, alamat_pengajar) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Mengisi parameter (?) dengan data dari TextField di View
            pstmt.setString(1, view.txtNama.getText());
            pstmt.setString(2, view.txtKeahlian.getText());
            pstmt.setString(3, view.txtNoHp.getText());
            pstmt.setString(4, view.txtAlamat.getText());
            
            pstmt.executeUpdate(); // Eksekusi query
            JOptionPane.showMessageDialog(view, "Data Berhasil Ditambah!");
            
            loadData();  // Refresh tabel
            resetForm(); // Kosongkan form
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(view, "Gagal Simpan: " + e.getMessage());
        }
    }

    /**
     * UPDATE: Mengubah data pengajar yang sudah ada
     */
    private void updateData() {
        int row = view.tabelPengajar.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(view, "Pilih data di tabel untuk diubah!");
            return;
        }
        
        // Ambil ID dari kolom pertama (index 0) pada baris yang dipilih
        int id = (int) view.tableModel.getValueAt(row, 0);
        
        String sql = "UPDATE pengajar SET nama_pengajar=?, keahlian_pengajar=?, no_hp_pengajar=?, alamat_pengajar=? WHERE id_pengajar=?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, view.txtNama.getText());
            pstmt.setString(2, view.txtKeahlian.getText());
            pstmt.setString(3, view.txtNoHp.getText());
            pstmt.setString(4, view.txtAlamat.getText());
            pstmt.setInt(5, id); // Penentu baris mana yang diupdate
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(view, "Data Berhasil Diperbarui!");
            
            loadData();
            resetForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * DELETE: Menghapus data berdasarkan ID
     */
    private void hapusData() {
        int row = view.tabelPengajar.getSelectedRow();
        if (row != -1) {
            int id = (int) view.tableModel.getValueAt(row, 0);
            
            // Dialog konfirmasi agar user tidak tidak sengaja menghapus
            int confirm = JOptionPane.showConfirmDialog(view, "Yakin hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement("DELETE FROM pengajar WHERE id_pengajar=?")) {
                    
                    pstmt.setInt(1, id);
                    pstmt.executeUpdate();
                    
                    loadData();
                    resetForm();
                } catch (SQLException e) {
                    // Pesan ini muncul jika ID pengajar masih terhubung ke tabel lain (Foreign Key)
                    JOptionPane.showMessageDialog(view, "Gagal Hapus: Data mungkin sedang digunakan di tabel lain");
                }
            }
        }
    }

    /**
     * Helper: Membersihkan semua TextField dan seleksi tabel
     */
    private void resetForm() {
        view.txtNama.setText("");
        view.txtKeahlian.setText("");
        view.txtNoHp.setText("");
        view.txtAlamat.setText("");
        view.tabelPengajar.clearSelection();
    }
}