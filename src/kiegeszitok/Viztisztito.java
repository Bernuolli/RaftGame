package kiegeszitok;

/**
 * Építmények Víztisztító gyerekosztályának létrehozása és tulajdonsságainak beállítása
 */
public class Viztisztito extends Epitmenyek{

    private int viztisztitoDb = 0;
    private boolean poharDb = false;



    public int getViztisztitoDb() {
        return viztisztitoDb;
    }

    public void setViztisztitoDb(int viztisztitoDb) {
        this.viztisztitoDb = viztisztitoDb;
    }

    public boolean isPoharDb() {
        return poharDb;
    }

    public void setPoharDb(boolean poharDb) {
        this.poharDb = poharDb;
    }
}
