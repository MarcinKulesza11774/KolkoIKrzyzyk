import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    ImageIcon[] ikony = {
            wczytajObrazek("src\\images\\puste_pole.png", 120, 120),
            wczytajObrazek("src\\images\\kolko.png", 120, 120),
            wczytajObrazek("src\\images\\krzyzyk.png", 120, 120)
    };
    int[][] wygrane = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };
    JButton[] pola = new JButton[9];
    int[] plansza = new int[9];
    int gracz = 1;
    boolean koniec = false;
    JLabel poleKomunikatu = new JLabel();

//    Tytuł: MainFrame
//    Opis: konstruktor głównego okna
//    Przyjmowane argumenty: brak
//    Zwracana wartość: brak
//    Autor: Marcin Kulesza
    MainFrame(){
        setTitle("Kółko i krzyżyk");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);

        JLabel tytul = new JLabel("Kółko i krzyżyk");
        tytul.setFont(new Font("Arial", Font.BOLD, 36));
        tytul.setHorizontalAlignment(SwingConstants.CENTER);

        poleKomunikatu.setFont(new Font("Arial", Font.BOLD, 22));
        poleKomunikatu.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelGorny = new JPanel(new GridLayout(2, 1));
        panelGorny.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panelGorny.add(tytul);
        panelGorny.add(poleKomunikatu);
        add(panelGorny, BorderLayout.NORTH);

        JPanel panelPlanszy = new JPanel(new GridLayout(3, 3, 5, 5));
        panelPlanszy.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));
        for (int i = 0; i < 9; i++) {
            int numer = i;
            pola[i] = new JButton(ikony[0]);
            pola[i].setFocusPainted(false);
            pola[i].addActionListener(e -> nacisnieto(numer));
            panelPlanszy.add(pola[i]);
        }
        add(panelPlanszy, BorderLayout.CENTER);

        JButton przyciskNowaGra = new JButton("Nowa gra");
        przyciskNowaGra.addActionListener(e -> nowaGra());
        JPanel panelDolny = new JPanel(new BorderLayout());
        panelDolny.setBorder(BorderFactory.createEmptyBorder(10, 100, 30, 100));
        panelDolny.add(przyciskNowaGra, BorderLayout.CENTER);
        add(panelDolny, BorderLayout.SOUTH);

        ustawTure();
        setVisible(true);
    }

    //    Tytuł: wczytajObrazek
//    Opis: Wczytuje obrazek, a następnie go skaluje
//    Przyjmowane argumenty: ścieżka do obrazka, jego szerokość i wysokośc
//    Zwracana wartość: ImageIcon z obrazkiem
//    Autor: Marcin Kulesza
    public ImageIcon wczytajObrazek(String sciezka, int szerokosc, int wysokosc){
        Image obraz = new ImageIcon(sciezka).getImage();
        return new ImageIcon(obraz.getScaledInstance(szerokosc, wysokosc, Image.SCALE_SMOOTH));
    }

    //    Tytuł: nacisnieto
//    Opis: Obsługuje wybieranie pól przez graczy
//    Przyjmowane argumenty: numer nacisniętego pola
//    Zwracana wartość: brak
//    Autor: Marcin Kulesza
    public void nacisnieto(int numer){
        if (koniec || plansza[numer] != 0) {
            return;
        }
        plansza[numer] = gracz;
        pola[numer].setIcon(ikony[gracz]);

        if (sprawdzWygrana()) {
            poleKomunikatu.setText("Wygrywa Gracz " + (gracz == 1 ? "O" : "X") + "!");
            koniec = true;
        } else if (planszaPelna()) {
            poleKomunikatu.setText("Remis!");
            koniec = true;
        } else {
            gracz = 3 - gracz;
            ustawTure();
        }
    }

    //    Tytuł: sprawdzWygrana
//    Opis: Sprawdza czy gracz wygrał
//    Przyjmowane argumenty: brak
//    Zwracana wartość: boolean czy wygrał czy nie
//    Autor: Marcin Kulesza
    public boolean sprawdzWygrana(){
        for (int[] linia : wygrane) {
            if (plansza[linia[0]] == gracz && plansza[linia[1]] == gracz && plansza[linia[2]] == gracz) {
                return true;
            }
        }
        return false;
    }


    //    Tytuł: planszaPelna
//    Opis: Sprawdza czy plansza jest zapełniona
//    Przyjmowane argumenty: brak
//    Zwracana wartość: boolean czy jest pełna czy nie
//    Autor: Marcin Kulesza
    public boolean planszaPelna(){
        for (int pole : plansza) {
            if (pole == 0) {
                return false;
            }
        }
        return true;
    }


    //    Tytuł: ustawTure
//    Opis: Zmienia komunikat mówiący czyja jest tura
//    Przyjmowane argumenty: brak
//    Zwracana wartość: brak
//    Autor: Marcin Kulesza
    public void ustawTure(){
        if (gracz == 1) {
            poleKomunikatu.setText("<html>Tura: <font color='#0055DD'>Gracz O</font></html>");
        } else {
            poleKomunikatu.setText("<html>Tura: <font color='#EE1111'>Gracz X</font></html>");
        }
    }


    //    Tytuł: nowaGra
//    Opis: czyści planszę i zaczyna nową grę
//    Przyjmowane argumenty: brak
//    Zwracana wartość: brak
//    Autor: Marcin Kulesza
    public void nowaGra(){
        for (int i = 0; i < 9; i++) {
            plansza[i] = 0;
            pola[i].setIcon(ikony[0]);
        }
        gracz = 1;
        koniec = false;
        ustawTure();
    }
}