package ellenseg;

import kivetelek.ErreNemMehetsz;
import kivetelek.HibasValasz;
import kivetelek.MozgasVege;

import java.util.Random;

/**
 * Cápa osztály tulajdonságainak beállítása:
 * koordináták beállítása, lekérése, mozgás beállítása
 */
public class Capa {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Cápa mozgatása:
     * Véletlenszerűen számot sorsolva, annak irányt megfeleltetve halad.
     * @param palya
     */
    public void uszas(int[][] palya) {
        Random veletlen = new Random();

        while (true) {
            int irany = veletlen.nextInt(4); // 0-3

            if (irany == 0) { // FEL
                if (x - 1 >= 0 && palya[x - 1][y] != 1) {
                    x--;
                    return;
                }
            } else if (irany == 1) { //JOBBRA
                if (y + 1 < palya[0].length && palya[x][y + 1] != 1) {
                    y++;

                    return;
                }
            } else if (irany == 2) { //LE
                if (x + 1 < palya.length && palya[x + 1][y] != 1) {
                    x++;

                    return;
                }
            } else if (irany == 3) { //BALRA
                if (y - 1 >= 0 && palya[x][y] != 1) {
                    y--;

                    return;
                }
            }
        }
    }
}
