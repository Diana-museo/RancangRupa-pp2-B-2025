package id.rancangrupa.kelasync.controller;

import id.rancangrupa.kelasync.util.DBConnection;
import id.rancangrupa.kelasync.view.ReportView;
import id.rancangrupa.kelasync.view.PendaftaranView;

import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

// Controller ini bertugas mengatur proses pembuatan laporan (report)
// Report bisa berupa:
// 1. Laporan jadwal kursus per peserta
// 2. Laporan pendaftaran berdasarkan periode tanggal
public class ReportController {

    // Menyimpan referensi ke tampilan ReportView
    // Digunakan untuk membaca input dan mengatur aksi tombol
    private final ReportView view;

    // Konstruktor controller
    // Akan dijalankan saat ReportView dibuat
    public ReportController(ReportView view) {
        this.view = view;

        // Mengisi combo box peserta dari database
        loadPeserta();

        // Menghubungkan tombol-tombol dengan aksi
        attachListener();
    }

    // Method ini berisi seluruh event handler tombol
    // Tujuannya supaya kode lebih rapi dan tidak menumpuk di konstruktor
    private void attachListener() {

        // Tombol export PDF untuk satu peserta
        view.btnExportPeserta.addActionListener(e -> {
            try {
                // Mengambil id peserta dari combo box
                // Format item: "id - nama"
                int idPeserta = Integer.parseInt(
                        view.cbPeserta.getSelectedItem().toString().split(" - ")[0]);

                // Memanggil proses export PDF
                exportPDFPerPeserta(idPeserta);

                JOptionPane.showMessageDialog(
                        view,
                        "PDF berhasil dibuat di folder reports");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage());
            }
        });

        // Tombol export PDF berdasarkan periode tanggal
        view.btnExportPeriode.addActionListener(e -> {
            try {
                // Tanggal diketik manual dengan format yyyy-MM-dd
                LocalDate from = LocalDate.parse(view.tfFromDate.getText());
                LocalDate to = LocalDate.parse(view.tfToDate.getText());

                // Membuat laporan berdasarkan rentang tanggal
                exportPDFPerPeriode(from, to);

                JOptionPane.showMessageDialog(
                        view,
                        "PDF berhasil dibuat di folder reports");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage());
            }
        });

        // Tombol back untuk kembali ke halaman pendaftaran
        // Halaman report ditutup supaya tidak menumpuk window
        view.btnBack.addActionListener(e -> {
            new PendaftaranView().setVisible(true);
            view.dispose();
        });
    }

    // Mengambil data peserta dari database
    // Data ini digunakan untuk pilihan report per peserta
    private void loadPeserta() {
        view.cbPeserta.removeAllItems();

        try (Connection c = DBConnection.getConnection();
                Statement s = c.createStatement();
                ResultSet r = s.executeQuery(
                        "SELECT id_peserta, nama_peserta FROM peserta")) {

            while (r.next()) {
                view.cbPeserta.addItem(
                        r.getInt("id_peserta") + " - " + r.getString("nama_peserta"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mengecek apakah folder reports sudah ada
    // Jika belum, folder akan dibuat otomatis
    private File prepareReportFolder() {
        File folder = new File("reports");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    // Font untuk judul PDF
    private Font fontTitle(int size) {
        return FontFactory.getFont(FontFactory.TIMES_ROMAN, size, Font.BOLD);
    }

    // Font untuk header tabel PDF
    private Font fontHeader() {
        return FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
    }

    // Font untuk isi tabel dan paragraf
    private Font fontBody() {
        return FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
    }

    // Membuat satu cell header tabel PDF
    // Digunakan agar header konsisten dan mudah diatur
    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, fontHeader()));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);
    }

    // Membuat laporan PDF untuk satu peserta tertentu
    // Digunakan saat user memilih report per peserta
    private void exportPDFPerPeserta(int idPeserta) throws Exception {

        // Query untuk mengambil jadwal kursus peserta
        String sql = "SELECT s.nama_peserta, k.nama_kursus, p.nama_pengajar, " +
                "k.hari, k.jam_mulai, k.jam_selesai, d.tanggal_daftar " +
                "FROM pendaftaran d " +
                "JOIN peserta s ON d.id_peserta = s.id_peserta " +
                "JOIN kursus k ON d.id_kursus = k.id_kursus " +
                "JOIN pengajar p ON k.id_pengajar = p.id_pengajar " +
                "WHERE s.id_peserta = ?";

        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, idPeserta);
        ResultSet rs = ps.executeQuery();

        File folder = prepareReportFolder();
        String fileName = "Jadwal_Peserta_" + idPeserta + ".pdf";

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(
                document,
                new FileOutputStream(new File(folder, fileName)));

        document.open();

        Font titleFont = fontTitle(16);
        Font bodyFont = fontBody();

        // Judul laporan
        Paragraph title = new Paragraph(
                "JADWAL KURSUS PESERTA - KELASYNC\n\n",
                titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        // Mengambil nama peserta untuk ditampilkan di laporan
        String sqlNama = "SELECT nama_peserta FROM peserta WHERE id_peserta = ?";
        PreparedStatement psNama = c.prepareStatement(sqlNama);
        psNama.setInt(1, idPeserta);
        ResultSet rsNama = psNama.executeQuery();

        String namaPeserta = "";
        if (rsNama.next()) {
            namaPeserta = rsNama.getString("nama_peserta");
        }

        document.add(new Paragraph(
                "Nama Peserta: " + namaPeserta + "\n\n",
                bodyFont));

        // Membuat tabel jadwal kursus
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);

        addHeader(table, "Kursus");
        addHeader(table, "Pengajar");
        addHeader(table, "Hari");
        addHeader(table, "Mulai");
        addHeader(table, "Selesai");
        addHeader(table, "Tanggal");

        while (rs.next()) {
            table.addCell(new Phrase(rs.getString("nama_kursus"), bodyFont));
            table.addCell(new Phrase(rs.getString("nama_pengajar"), bodyFont));
            table.addCell(new Phrase(rs.getString("hari"), bodyFont));
            table.addCell(new Phrase(rs.getString("jam_mulai"), bodyFont));
            table.addCell(new Phrase(rs.getString("jam_selesai"), bodyFont));
            table.addCell(new Phrase(rs.getString("tanggal_daftar"), bodyFont));
        }

        document.add(table);
        document.add(new Paragraph(
                "\nDicetak pada: " + LocalDate.now(),
                bodyFont));

        document.close();
        rs.close();
        ps.close();
        rsNama.close();
        psNama.close();
        c.close();
    }

    // Membuat laporan PDF berdasarkan rentang tanggal pendaftaran
    // Digunakan untuk laporan periode
    private void exportPDFPerPeriode(LocalDate from, LocalDate to) throws Exception {

        String sql = "SELECT s.nama_peserta, k.nama_kursus, p.nama_pengajar, " +
                "k.hari, k.jam_mulai, k.jam_selesai, d.tanggal_daftar " +
                "FROM pendaftaran d " +
                "JOIN peserta s ON d.id_peserta = s.id_peserta " +
                "JOIN kursus k ON d.id_kursus = k.id_kursus " +
                "JOIN pengajar p ON k.id_pengajar = p.id_pengajar " +
                "WHERE d.tanggal_daftar BETWEEN ? AND ? " +
                "ORDER BY d.tanggal_daftar";

        Connection c = DBConnection.getConnection();
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(from));
        ps.setDate(2, Date.valueOf(to));
        ResultSet rs = ps.executeQuery();

        File folder = prepareReportFolder();
        String fileName = "Laporan_Pendaftaran_Periode.pdf";

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(
                document,
                new FileOutputStream(new File(folder, fileName)));

        document.open();

        Font titleFont = fontTitle(18);
        Font bodyFont = fontBody();

        Paragraph title = new Paragraph(
                "LAPORAN PENDAFTARAN KURSUS - KELASYNC\n\n",
                titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(100);

        addHeader(table, "Peserta");
        addHeader(table, "Kursus");
        addHeader(table, "Pengajar");
        addHeader(table, "Hari");
        addHeader(table, "Mulai");
        addHeader(table, "Selesai");
        addHeader(table, "Tanggal");

        while (rs.next()) {
            table.addCell(new Phrase(rs.getString("nama_peserta"), bodyFont));
            table.addCell(new Phrase(rs.getString("nama_kursus"), bodyFont));
            table.addCell(new Phrase(rs.getString("nama_pengajar"), bodyFont));
            table.addCell(new Phrase(rs.getString("hari"), bodyFont));
            table.addCell(new Phrase(rs.getString("jam_mulai"), bodyFont));
            table.addCell(new Phrase(rs.getString("jam_selesai"), bodyFont));
            table.addCell(new Phrase(rs.getString("tanggal_daftar"), bodyFont));
        }

        document.add(table);
        document.add(new Paragraph(
                "\nDicetak pada: " + LocalDate.now(),
                bodyFont));

        document.close();
        rs.close();
        ps.close();
        c.close();
    }
}