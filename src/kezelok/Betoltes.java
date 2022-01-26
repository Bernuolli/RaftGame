package kezelok;

import ellenseg.Capa;
import jatekos.Jatekos;
import kiegeszitok.Tuzhely;
import kiegeszitok.Viztisztito;
import nyersanyag.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Elmentett játék betöltéséért felelős osztály
 */
public class Betoltes {
    /**
     * Pálya betöltése a megadott file alapján
     * @param palyaKezelo
     * @param jatekos
     * @param korFigyelo
     * @param palya
     * @param tuzhely
     * @param viztisztito
     * @param capa
     * @param file
     */
    private static void palyaBetoltese(PalyaKezelo palyaKezelo, Jatekos jatekos, KorFigyelo korFigyelo, int[][] palya, Tuzhely tuzhely, Viztisztito viztisztito, Capa capa, Scanner file) {
        String[] elsoSor = file.nextLine().split(" ");
        int magassag = Integer.parseInt(elsoSor[0]);
        int szelesseg = Integer.parseInt(elsoSor[1]);
        palya = new int[magassag][szelesseg];
        NyersanyagKezelo.nyersanyagok.clear();
        for (int i = 0; i < magassag; i++) {
            String[] sor = file.nextLine().split(" ");
            for (int j = 0; j < szelesseg; j++) {
                String karakter = sor[j];
                if (karakter.equals("☻")) {
                    jatekos.setX(i);
                    jatekos.setY(j);
                } else if (karakter.equals("C")) {
                    capa.setX(i);
                    capa.setY(j);
                } else {
                    if(tuzhely.getTuzhelyDb() >= 1){
                        if (karakter.equals("TH")) {
                            tuzhely.setX(i);
                            tuzhely.setY(j);
                        }
                    }
                    if(viztisztito.getViztisztitoDb() >= 1){
                        if (karakter.equals("VT")) {
                            viztisztito.setX(i);
                            viztisztito.setY(j);
                        }
                    }

                    if (karakter.equals("\uD83C\uDF32")) {
                        NyersanyagKezelo.nyersanyagok.add(new Deszka(i,j));
                    }else if(karakter.equals("\uD83D\uDEE2")){
                        NyersanyagKezelo.nyersanyagok.add(new Hordo(i,j));
                    }else if(karakter.equals("\uD83D\uDDD1")){
                        NyersanyagKezelo.nyersanyagok.add(new Hulladek(i,j));
                    }else if(karakter.equals("\uD83C\uDF43")){
                        NyersanyagKezelo.nyersanyagok.add(new Level(i,j));
                    }
                    if (karakter.equals("V")) {
                        palya[i][j] = 0;
                    }
                    if (karakter.equals("T")) {
                        palya[i][j] = 1;
                    }
                }
            }
        }
    }

    /**
     * Különböző objektumok adatainak betöltése a megadott file alapján
     * @param palyaKezelo
     * @param jatekos
     * @param korFigyelo
     * @param palya
     * @param tuzhely
     * @param viztisztito
     * @param capa
     * @param file
     */
    private static void adatokBetoltese(PalyaKezelo palyaKezelo, Jatekos jatekos, KorFigyelo korFigyelo, int[][] palya, Tuzhely tuzhely, Viztisztito viztisztito, Capa capa, Scanner file){
        if(file.hasNextInt()){ jatekos.setEhseg(file.nextInt()); }else file.next();
        if(file.hasNextInt()){ jatekos.setSzomjusag(file.nextInt()); }else file.next();

        if(file.hasNextInt()){ korFigyelo.cselekvesekSzama= file.nextInt(); }else file.next();

        if(file.hasNextInt()){ tuzhely.setTuzhelyDb(file.nextInt()); }else file.next();
        if(file.hasNextBoolean()){ tuzhely.setSutValamit(file.nextBoolean()); }else file.next();

        if(file.hasNextInt()){ viztisztito.setViztisztitoDb(file.nextInt()); }else file.next();
        if(file.hasNextBoolean()){ viztisztito.setPoharDb(file.nextBoolean()); }else file.next();

    }

    /**
     * Játék teljes betöltése
     * @param palyaKezelo
     * @param jatekos
     * @param korFigyelo
     * @param palya
     * @param tuzhely
     * @param viztisztito
     * @param capa
     */
    public static void jatekBetoltese(PalyaKezelo palyaKezelo, Jatekos jatekos, KorFigyelo korFigyelo, int[][] palya, Tuzhely tuzhely, Viztisztito viztisztito, Capa capa){
        Scanner file;
        try {
            file = new Scanner(new File("src/palyak/mentes.txt"));
            palyaBetoltese(palyaKezelo, jatekos, korFigyelo, palya, tuzhely, viztisztito, capa, file);
            adatokBetoltese(palyaKezelo, jatekos, korFigyelo, palya, tuzhely, viztisztito, capa, file);

        } catch (FileNotFoundException e) {
            System.out.println("A file nem talalhato!");
            return;
        }


    }
}
