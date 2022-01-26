package kiegeszitok;

/**
 * Építmények Tűzhely gyerekosztályának létrehozása és tulajdonsságainak beállítása
 */
public class Tuzhely extends Epitmenyek{

    private int tuzhelyDb = 0;
    private boolean sutValamit = false;


    public int getTuzhelyDb() {
        return tuzhelyDb;
    }

    public void setTuzhelyDb(int tuzhelyDb) {
        this.tuzhelyDb = tuzhelyDb;
    }

    public boolean isSutValamit() {
        return sutValamit;
    }

    public void setSutValamit(boolean sutValamit) {
        this.sutValamit = sutValamit;
    }


}
