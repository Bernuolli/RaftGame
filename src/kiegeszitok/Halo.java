package kiegeszitok;


import jatekos.Jatekos;
import kezelok.NyersanyagKezelo;
import nyersanyag.Hordo;
import nyersanyag.HulloNyersanyag;
import nyersanyag.Nyersanyag;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Építmények Háló gyerekosztályának létrehozása és tulajdonsságainak beállítása
 */
public class Halo extends Epitmenyek{
    private int haloDb;
    public int getHaloDb() {
        return haloDb;
    }

    public void setHaloDb(int haloDb) {
        this.haloDb = haloDb;
    }

    /**
     * Nyersanyagok begyűjtése:
     * Ha van háló építve, és rá nyersanyak kerül, automatikusan a játékos táskájához adja.
     * @param jatekos
     */
    public void nyersanyagBegyujtes(Jatekos jatekos){
        Iterator<HulloNyersanyag> it = NyersanyagKezelo.nyersanyagok.iterator();
        while(it.hasNext()) {
            HulloNyersanyag elem = it.next();
            if (
                    elem.getKezdoX() == getX() && elem.getKezdoY() == getY()) {
                if (elem.getClass().getSimpleName().equals("Hordo")) {
                    ArrayList<Nyersanyag> hordoiElemei = Hordo.getTartalom();

                    for (Nyersanyag nyersanyag : hordoiElemei) {
                        jatekos.taska.add(nyersanyag);
                        System.out.println("Felvetted: " + nyersanyag.getClass().getSimpleName());
                    }

                } else {
                    jatekos.taska.add(elem);
                    System.out.println("Felvetted: " + elem.getClass().getSimpleName());
                }
                it.remove();
                break;
            }
        }
    }
}
