package kezelok;

import jatekos.Jatekos;
import ellenseg.Capa;
import kiegeszitok.Halo;
import kiegeszitok.Tuzhely;
import kiegeszitok.Viztisztito;
import nyersanyag.HulloNyersanyag;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * A pálya beállításáért, kiíratásáért és az objektumok lehelyezéséért felelős osztály
 */
public class PalyaKezelo {
    private Jatekos jatekos;
    private Capa capa;
    private Tuzhely tuzhely;
    private NyersanyagKezelo nyersanyagKezelo;
    private Viztisztito viztisztito;
    private Halo halo;
    public static int sorSzam;
    public static int oszlopSzam;

    /**
     * Pályakezelő paraméteres konstruktora
     * @param jatekos
     * @param nyersanyagKezelo
     * @param capa
     * @param tuzhely
     * @param viztisztito
     * @param halo
     */
    public PalyaKezelo(Jatekos jatekos, NyersanyagKezelo nyersanyagKezelo, Capa capa, Tuzhely tuzhely, Viztisztito viztisztito, Halo halo) {
        this.jatekos = jatekos;
        this.nyersanyagKezelo = nyersanyagKezelo;
        this.capa = capa;
        this.tuzhely = tuzhely;
        this.viztisztito = viztisztito;
        this.halo = halo;
    }

    /**
     * Az alap pálya beállítása a megadott fájból
     * @return
     * @throws FileNotFoundException Ha pálya betöltése során nem található a megadott fájl ezt a kivételt dobja
     */
    public int[][] palyatBeallit() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("src/palyak/palya.txt"));
        
        sorSzam = sc.nextInt();
        oszlopSzam = sc.nextInt();
        sc.nextLine();
        int [][] palya = new int[sorSzam][oszlopSzam];

        for (int i = 0; i < sorSzam; i++) {
            for (int j = 0; j < oszlopSzam; j++) {
                palya[i][j] = sc.nextInt();
            }
        }
        return palya;
    }

    /**
     * Pálya kiíratása a kimenetre:
     * Megnézi az adott koordinátán milyen objektum szerepel, és kiírja
     * @param palya
     */
    public void palyatKiir(int[][] palya) {
        for (int i = 0; i < palya.length; i++) {
            for (int j = 0; j < palya[i].length; j++) {
                boolean ok = false;
                if (jatekos.getX() == i && jatekos.getY() == j) {
                    System.out.print("☻" + "\t");
                }else if(capa.getX() == i && capa.getY() == j){
                    System.out.print("\uD83E\uDD88" + "\t");
                }else{
                    if(tuzhely.getTuzhelyDb() >= 1) {
                        if(tuzhely.getX() == i && tuzhely.getY() == j){
                            System.out.print("\uD83D\uDD25" + "\t");
                            palya[i][j] = 3;
                        }
                    }
                    if(viztisztito.getViztisztitoDb() > 0) {
                        if(viztisztito.getX() == i && viztisztito.getY() == j){
                            System.out.print("\uD83D\uDCA7" + "\t");
                            palya[i][j] = 4;
                        }
                    }
                    if(halo.getHaloDb() > 0) {
                        if(halo.getX() == i && halo.getY() == j){
                            System.out.print("\uD83C\uDFA3" + "\t");
                            palya[i][j] = 4;
                        }
                    }
                    for(HulloNyersanyag elem : NyersanyagKezelo.nyersanyagok){
                        if(elem.getKezdoX() == i && elem.getKezdoY() == j ){
                            System.out.print(elem.megjelenites + "\t");
                            ok = true;
                            break;
                        }
                    }
                    if(!ok){
                        if(palya[i][j] == 0){
                            System.out.print("\uD83C\uDF0A" + "\t");
                        }
                        if(palya[i][j] == 1){
                            System.out.print("\uD83D\uDFEB" + "\t");
                        }
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * Beállítja a játékos koordinátáit
     * @param palya
     */
    public void jatekostLehelyez(int[][] palya) {
        for (int i = 0; i < palya.length; i++) {
            for (int j = 0; j < palya[i].length; j++) {
                if (palya[i][j] == 1) {
                    jatekos.setX(i);
                    jatekos.setY(j);
                    return;
                }
            }
        }
    }

    /**
     * Beállítja a cápa koordinátáit
     * @param palya
     */
    public void capaLehelyez(int[][] palya){
        for (int i = 0; i < palya.length; i++) {
            for (int j = 0; j < palya[i].length; j++) {
                if (palya[i][j] == 1 && i > 0) {
                    capa.setX(i-1);
                    capa.setY(j);
                    return;
                }
            }
        }

    }

    /**
     * Beállítja a tűzhely koordinátáit
     * @param palya
     * @param x
     * @param y
     */
    public void tuzhelyLehelyez(int[][] palya, int x, int y){
        if (palya[x][y] == 1 && x > 0) {
            tuzhely.setX(x);
            tuzhely.setY(y);

            return;
        }

    }

    /**
     * Beállítja a víztisztító koordinátáit
     * @param palya
     * @param x
     * @param y
     */
    public void viztisztitoLehelyez(int[][] palya, int x, int y){
        if (palya[x][y] == 1 && x > 0) {
            viztisztito.setX(x);
            viztisztito.setY(y);

            return;
        }
    }

    /**
     * Beállítja a háló koordinátáit
     * @param palya
     * @param x
     * @param y
     */
    public void haloLehelyez(int[][] palya, int x, int y){
        if (palya[x][y] == 0) {
            if(palya[x-1][y] == 1||
                    palya[x][y+1] == 1||
                    palya[x+1][y] == 1||
                    palya[x][y-1] == 1){
                halo.setX(x);
                halo.setY(y);
            }
            return;
        }
    }
}
