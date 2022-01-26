package nyersanyag;

/**
 * Hullónyersanyag Levél gyerekosztályának létrehozása és tulajdonságainak beállítása
 */
public class Level extends HulloNyersanyag{
    public String megjelenites = "\uD83C\uDF43";
    public Level(int y) {
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
     * Levél paraméteres konstruktora.
     * @param kezdoX
     * @param kezdoY
     */
    public Level(int kezdoX, int kezdoY) {
        super(kezdoX, kezdoY);
        super.megjelenites = megjelenites;
    }
}
