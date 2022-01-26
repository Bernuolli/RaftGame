package nyersanyag;

/**
 * Hullónyersanyag Deszka gyerekosztályának létrehozása és tulajdonságainak beállítása
 */
public class Deszka extends HulloNyersanyag{
    public String megjelenites = "\uD83C\uDF32";

    public Deszka(int y) {
        super(y);
        super.megjelenites = megjelenites;
    }

    public int getKezdoY(){
        return kezdoY;
    }

    public void setKezdoY(int y){
        this.kezdoY = y;
    }

    /**
     * Deszka paraméteres konstruktora.
     * @param kezdoX
     * @param kezdoY
     */
    public Deszka(int kezdoX, int kezdoY) {
        super(kezdoX, kezdoY);
        super.megjelenites = megjelenites;
    }
}
