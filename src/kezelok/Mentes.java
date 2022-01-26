package kezelok;

import ellenseg.Capa;
import jatekos.Jatekos;
import kiegeszitok.Tuzhely;
import kiegeszitok.Viztisztito;
import nyersanyag.HulloNyersanyag;


import java.io.FileWriter;
import java.io.IOException;

/**
 * Játék aktuális helyzetének elmentéséért felelős osztály
 */
public class Mentes {
    /**
     * Játék aktuális helyzetének mentése:
     * Pálya lementése, játékos, körfigyelő, cápa, és az építmények adatainak és aktuális helyzetének mentése
     * @param jatekos
     * @param korFigyelo
     * @param palya
     * @param tuzhely
     * @param viztisztito
     * @param capa
     * @throws IOException
     */
    public void jatekMentese(Jatekos jatekos, KorFigyelo korFigyelo, int[][] palya, Tuzhely tuzhely, Viztisztito viztisztito, Capa capa) throws IOException {
        FileWriter writer = new FileWriter("src/palyak/mentes.txt");
        writer.write(palya.length + " ");
        writer.write(palya[0].length +"\n");
        for (int i = 0; i < palya.length; i++) {
            for (int j = 0; j < palya[i].length; j++) {
                boolean ok = false;
                if (jatekos.getX() == i && jatekos.getY() == j) {
                    writer.write("☻ ");
                } else if (capa.getX() == i && capa.getY() == j) {
                    writer.write("C ");
                } else {
                    if (tuzhely.getTuzhelyDb() >= 1) {
                        if (tuzhely.getX() == i && tuzhely.getY() == j) {
                            writer.write("TH ");
                        }

                    }
                    if (viztisztito.getViztisztitoDb() >= 1) {
                        if (viztisztito.getX() == i && viztisztito.getY() == j) {
                            writer.write("VT ");
                        }

                    }
                    for (HulloNyersanyag elem : NyersanyagKezelo.nyersanyagok) {
                        if (elem.getKezdoX() == i && elem.getKezdoY() == j) {
                            writer.write(elem.megjelenites + " ");
                            ok = true;
                            break;
                        }
                    }

                    if (!ok) {
                        if (palya[i][j] == 0) {
                            writer.write("V ");
                        } else if (palya[i][j] == 1) {
                            writer.write("T ");
                        }
                    }
                }
            }
            writer.write("\n");

        }

        writer.write("===JatekosAdatai====\n");
        writer.write("ehseg: "+ jatekos.getEhseg()+ "\n");
        writer.write("szomjusag: "+ jatekos.getSzomjusag()+ "\n");


        writer.write("===Korfigyelo====\n");
        writer.write("cselekvesekSzama: "+ korFigyelo.cselekvesekSzama+ "\n");

        writer.write("===Tuzhely====\n");
        writer.write("db: "+ tuzhely.getTuzhelyDb()+ "\n");
        writer.write("sut-e: "+ tuzhely.isSutValamit()+ "\n");

        writer.write("===Viztisztito====\n");
        writer.write("db: "+ viztisztito.getViztisztitoDb()+ "\n");
        writer.write("pohar: "+ viztisztito.isPoharDb()+ "\n");


        System.out.println("Pálya mentése megtörént");
        writer.close();
    }
}
