package nyersanyag;

import kezelok.NyersanyagKezelo;

/**
 * Nyersanyag HullóNyersanyag gyerekosztály létrehozása
 */
public class HulloNyersanyag extends Nyersanyag{
    protected int kezdoX = 0;
    protected int kezdoY;
    public String megjelenites = "";

    public int getKezdoX() {
        return kezdoX;
    }

    public int getKezdoY() {
        return kezdoY;
    }

    /**
     * Nyersanyagok x koordinátájának növelése:
     * Ha a nyersanyag x koordinátája túllépi a pálya hosszát, akkor törli ezt a nyersanyagot a listából.
     * @param palya
     */
    public void hullas(int[][] palya){
        kezdoX++;
        if (kezdoX >= palya.length) {
            NyersanyagKezelo.kivesz(this);
        }
    }

    public HulloNyersanyag(int y) {
        kezdoY = y;
    }

    /**
     * HullóNyersanyag paraméteres konstruktora
     * @param kezdoX
     * @param kezdoY
     */
    public HulloNyersanyag(int kezdoX, int kezdoY) {
        this.kezdoX = kezdoX;
        this.kezdoY = kezdoY;
    }
}
