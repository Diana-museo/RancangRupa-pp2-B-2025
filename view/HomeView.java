package id.rancangrupa.kelasync.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

// VIEW: Home
// Halaman utama aplikasi Kelasync
// Berisi menu navigasi ke halaman Peserta, Pengajar, Kursus, dan Pendaftaran
public class HomeView extends JFrame {

    // Konstruktor HomeView
    // Akan dijalankan saat aplikasi pertama kali dibuka
    public HomeView() {
        setTitle("Kelasync App");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Label sambutan di bagian atas
        JLabel lblWelcome = new JLabel("Halo, selamat datang di", SwingConstants.CENTER);
        JLabel lblTitle = new JLabel("Kelasync App", SwingConstants.CENTER);

        // Pengaturan font judul
        lblWelcome.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 28));

        // Panel header untuk judul aplikasi
        JPanel panelHeader = new JPanel(new GridLayout(2, 1));
        panelHeader.add(lblWelcome);
        panelHeader.add(lblTitle);

        // Tombol menu utama
        JButton btnPengajar = new JButton("Pengajar");
        JButton btnKursus = new JButton("Kursus");
        JButton btnPeserta = new JButton("Peserta");
        JButton btnPendaftaran = new JButton("Pendaftaran");

        // Font untuk tombol menu
        Font menuFont = new Font("Times New Roman", Font.PLAIN, 14);
        btnPengajar.setFont(menuFont);
        btnKursus.setFont(menuFont);
        btnPeserta.setFont(menuFont);
        btnPendaftaran.setFont(menuFont);

        // Panel menu di bagian tengah
        JPanel panelMenu = new JPanel(new GridLayout(4, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panelMenu.add(btnPengajar);
        panelMenu.add(btnKursus);
        panelMenu.add(btnPeserta);
        panelMenu.add(btnPendaftaran);

        // Menambahkan panel ke frame
        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);

        // Aksi tombol untuk membuka halaman terkait
        btnPengajar.addActionListener(e -> new PengajarView().setVisible(true));
        btnKursus.addActionListener(e -> new KursusView().setVisible(true));
        btnPeserta.addActionListener(e -> new PesertaView().setVisible(true));
        btnPendaftaran.addActionListener(e -> new PendaftaranView().setVisible(true));
    }
}