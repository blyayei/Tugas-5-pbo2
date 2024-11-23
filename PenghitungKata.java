import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PenghitungKata extends JFrame {
    private JTextArea textArea;
    private JLabel labelKata, labelKarakter, labelKalimat, labelParagraf;
    private JTextField fieldCari;
    private JButton tombolHitung, tombolCari, tombolSimpan;

    public PenghitungKata() {
        setTitle("Aplikasi Penghitung Kata");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel input teks
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // Panel hasil
        JPanel panelHasil = new JPanel(new GridLayout(4, 1));
        labelKata = new JLabel("Jumlah Kata: 0");
        labelKarakter = new JLabel("Jumlah Karakter (tanpa spasi): 0");
        labelKalimat = new JLabel("Jumlah Kalimat: 0");
        labelParagraf = new JLabel("Jumlah Paragraf: 0");
        panelHasil.add(labelKata);
        panelHasil.add(labelKarakter);
        panelHasil.add(labelKalimat);
        panelHasil.add(labelParagraf);
        add(panelHasil, BorderLayout.EAST);

        // Panel bawah untuk tombol
        JPanel panelTombol = new JPanel(new FlowLayout());
        tombolHitung = new JButton("Hitung Manual");
        tombolCari = new JButton("Cari Kata");
        tombolSimpan = new JButton("Simpan Teks");
        fieldCari = new JTextField(10);
        panelTombol.add(tombolHitung);
        panelTombol.add(new JLabel("Kata: "));
        panelTombol.add(fieldCari);
        panelTombol.add(tombolCari);
        panelTombol.add(tombolSimpan);
        add(panelTombol, BorderLayout.SOUTH);

        // Event Listeners
        tombolHitung.addActionListener(e -> hitungStatistik());
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                hitungStatistik();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                hitungStatistik();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                hitungStatistik();
            }
        });

        tombolCari.addActionListener(e -> cariKata());
        tombolSimpan.addActionListener(e -> simpanTeks());

        setVisible(true);
    }

    private void hitungStatistik() {
        String teks = textArea.getText();
        String[] kata = teks.trim().split("\\s+");
        String[] kalimat = teks.split("[.!?]");
        String[] paragraf = teks.split("\\n+");

        labelKata.setText("Jumlah Kata: " + (teks.isEmpty() ? 0 : kata.length));
        labelKarakter.setText("Jumlah Karakter (tanpa spasi): " + teks.replaceAll("\\s", "").length());
        labelKalimat.setText("Jumlah Kalimat: " + (teks.isEmpty() ? 0 : kalimat.length));
        labelParagraf.setText("Jumlah Paragraf: " + (teks.isEmpty() ? 0 : paragraf.length));
    }

    private void cariKata() {
        String cari = fieldCari.getText();
        String teks = textArea.getText();
        int jumlah = teks.split("\\b" + cari + "\\b").length - 1;

        JOptionPane.showMessageDialog(this, "Kata \"" + cari + "\" ditemukan sebanyak " + jumlah + " kali.");
    }

    private void simpanTeks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("hasil_teks.txt"))) {
            writer.write("Teks: \n" + textArea.getText() + "\n\n");
            writer.write("Statistik:\n");
            writer.write(labelKata.getText() + "\n");
            writer.write(labelKarakter.getText() + "\n");
            writer.write(labelKalimat.getText() + "\n");
            writer.write(labelParagraf.getText() + "\n");
            JOptionPane.showMessageDialog(this, "Teks berhasil disimpan!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PenghitungKata::new);
    }
}
