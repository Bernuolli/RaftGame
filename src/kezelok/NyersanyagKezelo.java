package kezelok;

import nyersanyag.*;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Nyersanyagok kezeléséért felelős osztály:
 * létrehozza, mozgatja és törli
 */
public class NyersanyagKezelo {
    public static List<HulloNyersanyag> nyersanyagok = new ArrayList<>();


    /**
     * Új nyersanyagok létrehozása:
     * Elsőnek véletlenszerűen kiválasztja hány nyersanyag van a sorban, majd szintén véletlenszerűen kiválasztja melyek ezek a nyersanyagok, és egy listához adja
     * Majd ezt a listát hozzáadja a már meglévő nyersanyagokhoz
     */
    public static void ujSorLetrehozasa(){
        List<HulloNyersanyag> elsosor= new ArrayList<>();

        Random veletlen = new Random();
        int osszDb = veletlen.nextInt(4); //0-3

        ArrayList<Integer> randomYsorszamok = new ArrayList<>();

        while (randomYsorszamok.size() != osszDb) {
            int randomY = veletlen.nextInt(PalyaKezelo.oszlopSzam); //0-20

            if (!randomYsorszamok.contains(randomY)) {
                randomYsorszamok.add(randomY);
            }
        }

        for (Integer y : randomYsorszamok) { //y: 0, 4
            int esely = veletlen.nextInt(100);

            if(esely <= 31){
                elsosor.add(new Deszka(y));
            }else if(esely <= 63){
                elsosor.add(new Level(y));
            }else if(esely <= 95){
                elsosor.add(new Hulladek(y));
            }else {
                elsosor.add(new Hordo(y));
            }
        }
        nyersanyagok.addAll(elsosor);
    }

    /**
     * Nyersanyagok mozgás:
     * a pályán egyre haladnak lefelé.
     * @param palya
     */
    public static void nyersanyagHullas(int[][] palya){
        for (int i = 0; i < nyersanyagok.size(); i++) {
            nyersanyagok.get(i).hullas(palya);
        }
    }

    /**
     * Nyersanyagok törlése:
     * Törli a nyersanyagot a listából.
     * @param hulloNyersanyag
     */
    public static void kivesz(HulloNyersanyag hulloNyersanyag) {
        Iterator<HulloNyersanyag> it = nyersanyagok.iterator();
        while(it.hasNext()) {
            HulloNyersanyag elem = it.next();
            if(elem == hulloNyersanyag){
                it.remove();
            }
        }
    }
}
