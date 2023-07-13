import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GUI extends JFrame {
    private JButton[][] przyciski;
    private char aktualnyGracz;
    private boolean czyRuchBota;

    public GUI()
    {
        super("Kółko i krzyżyk");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(3, 3));

        przyciski = new JButton[3][3];
        aktualnyGracz = 'X';
        czyRuchBota = false;

        zainicjalizujPlansze();
        ustawPrzyciski();

        setVisible(true);
    }

    private void zainicjalizujPlansze()
    {
        for (int wiersz = 0; wiersz < 3; wiersz++)
        {
            for (int kolumna = 0; kolumna < 3; kolumna++)
            {
                przyciski[wiersz][kolumna] = new JButton("-");
                add(przyciski[wiersz][kolumna]);
            }
        }
    }

    private void ustawPrzyciski()
    {
        for (int wiersz = 0; wiersz < 3; wiersz++)
        {
            for (int kolumna = 0; kolumna < 3; kolumna++)
            {
                przyciski[wiersz][kolumna].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton przycisk = (JButton) e.getSource();
                        int przyciskWiersz = -1;
                        int przyciskKolumna = -1;

                        for (int i = 0; i < 3; i++) {
                            for (int j = 0; j < 3; j++) {
                                if (przycisk == przyciski[i][j]) {
                                    przyciskWiersz = i;
                                    przyciskKolumna = j;
                                    break;
                                }
                            }
                        }

                        wykonajRuch(przyciskWiersz, przyciskKolumna);
                    }
                });
            }
        }
    }

    private void wykonajRuch(int wiersz, int kolumna)
    {
        if (przyciski[wiersz][kolumna].getText().equals("-") && !czyRuchBota) {
            przyciski[wiersz][kolumna].setText(Character.toString(aktualnyGracz));

            if (czyWygrana())
            {
                JOptionPane.showMessageDialog(this, "Gracz " + aktualnyGracz + " wygrywa!");
                zresetujPlansze();
            } else if (czyRemis()) {
                JOptionPane.showMessageDialog(this, "Remis!");
                zresetujPlansze();
            } else{
                aktualnyGracz = (aktualnyGracz == 'X') ? 'O' : 'X';
                czyRuchBota = true;
                wykonajRuchBota();
            }
        }
    }

    private void wykonajRuchBota()
    {
        Random random = new Random();
        int wiersz, kolumna;

        do {
            wiersz = random.nextInt(3);
            kolumna = random.nextInt(3);
        } while (!przyciski[wiersz][kolumna].getText().equals("-"));

        przyciski[wiersz][kolumna].setText(Character.toString(aktualnyGracz));

        if (czyWygrana()) {
            JOptionPane.showMessageDialog(this, "Gracz " + aktualnyGracz + " wygrywa!");
            zresetujPlansze();
        } else if (czyRemis()) {
            JOptionPane.showMessageDialog(this, "Remis!");
            zresetujPlansze();
        } else {
            aktualnyGracz = (aktualnyGracz == 'X') ? 'O' : 'X';
            czyRuchBota = false;
        }
    }

    private boolean czyWygrana()
    {
        String[][] plansza = new String[3][3];

        for (int wiersz = 0; wiersz < 3; wiersz++)
        {
            for (int kolumna = 0; kolumna < 3; kolumna++)
            {
                plansza[wiersz][kolumna] = przyciski[wiersz][kolumna].getText();
            }
        }

        for (int wiersz = 0; wiersz < 3; wiersz++)
        {
            if (plansza[wiersz][0].equals(plansza[wiersz][1]) && plansza[wiersz][0].equals(plansza[wiersz][2]) && !plansza[wiersz][0].equals("-")) {
                return true;
            }
        }

        for (int kolumna = 0; kolumna < 3; kolumna++)
        {
            if (plansza[0][kolumna].equals(plansza[1][kolumna]) && plansza[0][kolumna].equals(plansza[2][kolumna]) && !plansza[0][kolumna].equals("-")) {
                return true;
            }
        }

        if (plansza[0][0].equals(plansza[1][1]) && plansza[0][0].equals(plansza[2][2]) && !plansza[0][0].equals("-")) {
            return true;
        }

        if (plansza[0][2].equals(plansza[1][1]) && plansza[0][2].equals(plansza[2][0]) && !plansza[0][2].equals("-")) {
            return true;
        }

        return false;
    }

    private boolean czyRemis()
    {
        for (int wiersz = 0; wiersz < 3; wiersz++)
        {
            for (int kolumna = 0; kolumna < 3; kolumna++)
            {
                if (przyciski[wiersz][kolumna].getText().equals("-"))
                {
                    return false;
                }
            }
        }
        return true;
    }

    private void zresetujPlansze()
    {
        for (int wiersz = 0; wiersz < 3; wiersz++)
        {
            for (int kolumna = 0; kolumna < 3; kolumna++)
            {
                przyciski[wiersz][kolumna].setText("-");
            }
        }
    }
}