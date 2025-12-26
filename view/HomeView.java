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

public class HomeView extends JFrame {

    public HomeView() {
        setTitle("Kelasync App");
        setSize(450, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ---- JUDUL -----
        JLabel lblWelcome = new JLabel("Halo, selamat datang di", SwingConstants.CENTER);
        JLabel lblTitle = new JLabel("Kelasync App", SwingConstants.CENTER);

        lblWelcome.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 28));

        JPanel panelHeader = new JPanel(new GridLayout(2, 1));
        panelHeader.add(lblWelcome);
        panelHeader.add(lblTitle);

        // ---- MENU ----
        JButton btnPeserta = new JButton("Peserta");
        JButton btnPengajar = new JButton("Pengajar");
        JButton btnKursus = new JButton("Kursus");
        JButton btnPendaftaran = new JButton("Pendaftaran");

        Font menuFont = new Font("Times New Roman", Font.PLAIN, 14);
        btnPeserta.setFont(menuFont);
        btnPengajar.setFont(menuFont);
        btnKursus.setFont(menuFont);
        btnPendaftaran.setFont(menuFont);

        JPanel panelMenu = new JPanel(new GridLayout(4, 1, 10, 10));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        panelMenu.add(btnPeserta);
        panelMenu.add(btnPengajar);
        panelMenu.add(btnKursus);
        panelMenu.add(btnPendaftaran);

        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);

        // ---- ACTION ----
        btnPeserta.addActionListener(e -> new PesertaView().setVisible(true));
        btnPengajar.addActionListener(e -> new PengajarView().setVisible(true));
        btnKursus.addActionListener(e -> new KursusView().setVisible(true));
        btnPendaftaran.addActionListener(e -> new PendaftaranView().setVisible(true));
    }
}