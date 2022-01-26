package nyersanyag;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hullónyersanyag Hordó gyerekosztályának létrehozása és tulajdonságainak beállítása
 */
public class Hordo extends HulloNyersanyag {
    public String megjelenites = "\uD83D\uDEE2";

    public int getKezdoY(){
        return kezdoY;
    }

    public void setKezdoY(int y){
        this.kezdoY = y;
    }

    public Hordo(int randomY){
        super(randomY);
        super.megjelenites = megjelenites;
    }

    /**
     * Hordó tartalmának beállítása:
     * 5 véletlenszerűen kiválasztott nyersanyagot tartalmazhat(1-ből akár többet is)
     * @return
     */
    public static ArrayList<Nyersanyag> getTartalom() {
        ArrayList<Nyersanyag> nyersanyagok = new ArrayList<>();

        Random veletlen = new Random();

        for (int i = 0; i < 5; i++) {
            int mennyiseg = veletlen.nextInt(4);
            if(mennyiseg == 0){
                nyersanyagok.add(new Deszka(0));
            }else if(mennyiseg == 1){
                nyersanyagok.add(new Level(0));
            }else if(mennyiseg == 2){
                nyersanyagok.add(new Hulladek(0));
            }else if(mennyiseg == 3){
                nyersanyagok.add(new Krumpli());
            }
        }
        return nyersanyagok;
    }

    /**
     * Hordó paraméteres konstruktora.
     * @param kezdoX
     * @param kezdoY
     */
    public Hordo(int kezdoX, int kezdoY) {
        super(kezdoX, kezdoY);
        super.megjelenites = megjelenites;
    }
}
