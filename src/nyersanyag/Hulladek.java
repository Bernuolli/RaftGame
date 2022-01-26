package nyersanyag;

/**
 * Hullónyersanyag Hulladék gyerekosztályának létrehozása és tulajdonságainak beállítása
 */
public class Hulladek extends HulloNyersanyag{
    public String megjelenites = "\uD83D\uDDD1";
    public Hulladek(int y) {
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
     * Hulladék paraméteres konstruktora.
     * @param kezdoX
     * @param kezdoY
     */
    public Hulladek(int kezdoX, int kezdoY) {
        super(kezdoX, kezdoY);
        super.megjelenites = megjelenites;
    }
}
