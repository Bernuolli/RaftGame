package kezelok;

import jatekos.Jatekos;
import kiegeszitok.*;
import kivetelek.Gyozelem;

/**
 * Körfigyelő osztály létrehozása:
 *
 */
public class KorFigyelo {
    public int cselekvesekSzama;
    public int korDbEtel = -1;
    public int korDbViz = -1;
    Tuzhely tuzhely;
    Viztisztito viztisztito;
    Jatekos jatekos;
    Halo halo;

    /**
     * Körfigyelő osztály paraméteres konstruktora
     * @param jatekos
     * @param tuzhely
     * @param viztisztito
     * @param halo
     */
    public KorFigyelo(Jatekos jatekos, Tuzhely tuzhely, Viztisztito viztisztito, Halo halo) {
        this.cselekvesekSzama = 0;
        this.jatekos = jatekos;
        this.tuzhely = tuzhely;
        this.viztisztito = viztisztito;
        this.halo = halo;
    }

    /**
     * Körszámtól függő változásokat figyeli:
     * A cselekvésszámot minden kör elején csökkenti.
     * Ha a tűzhelyen sül valami(true), 25 kör után megsül az étel, a játékos éhsége +60-nal javul automatikusan, a sütés leáll.(false)
     * Ha van víztisztító, 25 kör után termel 1 pohár vizet, amit akkor ihat meg a játékos ha a mezőjére lép.
     * Ha van háló, akkor körönként megnézi, van-e a mezőjén nyersanyag.
     */
    public void korFigyelese() throws Gyozelem {
        cselekvesekSzama--;
        if(cselekvesekSzama<=0){
            throw new Gyozelem();
        }

        if(tuzhely.isSutValamit()){
            korDbEtel++;
            System.out.print(korDbEtel + "\n");
            if(korDbEtel == 25){
                korDbEtel = 0;
                jatekos.setEhseg(jatekos.getEhseg()+60);
                System.out.println("Éhséged 60 ponttal javult.");
            }
        }

        if (viztisztito.getViztisztitoDb() >= 1){
            korDbViz++;
            if(korDbViz == 25){
                korDbViz = 0;
                viztisztito.setPoharDb(true);
                System.out.println("Van 1 pohár vized!");
                System.out.println("Menj a víztisztítóra hogy megihasd");
            }
        }

        if (halo.getHaloDb() >= 1){
            halo.nyersanyagBegyujtes(jatekos);
        }
    }
}
