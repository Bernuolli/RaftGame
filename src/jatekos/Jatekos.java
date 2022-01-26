package jatekos;


import kezelok.NyersanyagKezelo;
import kezelok.PalyaKezelo;
import kiegeszitok.*;
import nyersanyag.*;
import kivetelek.ErreNemMehetsz;
import kivetelek.HibasValasz;

import kivetelek.MozgasVege;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

/**
 * Játékos osztály tulajdonságainak beállítása, eljárásainak létrehozása
 * Pl. koordináták beállítása, lépés meghatározása
 */
public class Jatekos {
    private int x;
    private int y;
    private int ehseg;
    private int szomjusag;
    public ArrayList<Nyersanyag> taska = new ArrayList<>();


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getEhseg() {
        return ehseg;
    }

    public int getSzomjusag() {
        return szomjusag;
    }

    public void setEhseg(int ehseg) {
        if (ehseg > 100) {
            this.ehseg = 100;
        } else {
            this.ehseg = ehseg;
        }
    }

    public void setSzomjusag(int szomjusag) {
        if (szomjusag > 100) {
            this.szomjusag = 100;
        } else {
            this.szomjusag = szomjusag;

        }
    }

    /**
     * Játékos mozgásának irányának meghatározása a paraméterben kapott input segítségével:
     * 8 irány közül megadja melyik irányba kell elmozdulnia a játékosnak, és megnézi, helyes-e a megadott irány.
     * @param valasz
     * @param palya
     * @throws HibasValasz Nem létező input esetén ezt a kivételt dobja
     * @throws MozgasVege Mozgás végén ezt a kivételt dobja
     * @throws ErreNemMehetsz Pálya elhagyása esetén ezt a kivételt dobja
     */
    public void lepes(String valasz, int[][] palya) throws HibasValasz, MozgasVege, ErreNemMehetsz {
        switch (valasz) {
            case "fel":
                if (x - 1 < 0) throw new ErreNemMehetsz();

                x--;
                break;
            case "le":
                if (x + 1 >= palya.length) throw new ErreNemMehetsz();

                x++;
                break;
            case "jobbra":
                if (y + 1 >= palya[0].length) throw new ErreNemMehetsz();

                y++;
                break;
            case "balra":
                if (y - 1 < 0) throw new ErreNemMehetsz();
                y--;
                break;
            case "jobbraLe":
                if (x + 1 >= palya.length && y + 1 >= palya[0].length) throw new ErreNemMehetsz();
                x++;
                y++;
                break;
            case "jobbraFel":
                if (x - 1 < 0 && y + 1 >= palya[0].length) throw new ErreNemMehetsz();
                x--;
                y++;
                break;
            case "balraFel":
                if (x - 1 < 0 && y - 1 < 0) throw new ErreNemMehetsz();
                x--;
                y--;
                break;
            case "balraLe":
                if (x + 1 >= palya.length && y - 1 < 0) throw new ErreNemMehetsz();
                x++;
                y--;
                break;
            case "vége":
                throw new MozgasVege();
            default:
                throw new HibasValasz();
        }
    }

    /**
     * A játékos horgászásának megvalósítása:
     * Megvizsgálja, hogy megfelelő mezőn állunk-e, ha igen, a megadott esély alapján megadja, fogtunk-e halat vagy sem, ha rossz mezőn állunk hibát ír
     * @param palya
     */
    public void horgaszas(int[][] palya) {
        if (palya[getX()][getY()] == 0) {
            System.out.println("Horgászás következik!");
            System.out.println("Nézzük sikerül-e halat fognod...");
            Random randomSzam = new Random();
            int halfogasEsely = randomSzam.nextInt(100);
            if (halfogasEsely <= 24) {
                System.out.println("Halat fogtál!");
                System.out.println("A hal a táskádba került.");
                taska.add(new Hal());
            }else{
                System.out.println("Sajnos nem fogtál halat! :( ");
            }
        } else {
            System.out.println("Itt nem horgászhatsz! (Lépj víz mezőre)");
        }
    }

    /**
     * Játékos körül lévő nyersanyagok felszedése:
     * Ha a játékos körül lévő 8 mezőn, vagy azon a mezőn ahol a játékos áll található nyersanyag, akkor automatikusan a játékos táskájába rakja, a pályáról pedig elveszi.
     */
    public void nyersanyagFelszedes() {
        Iterator<HulloNyersanyag> it = NyersanyagKezelo.nyersanyagok.iterator();
        while(it.hasNext()) {
            HulloNyersanyag elem = it.next();
            if (
                    elem.getKezdoX() == x && elem.getKezdoY() == y          ||
                    elem.getKezdoX() == x - 1 && elem.getKezdoY() == y      ||
                    elem.getKezdoX() == x - 1 && elem.getKezdoY() == y + 1  ||
                    elem.getKezdoX() == x && elem.getKezdoY() == y + 1      ||
                    elem.getKezdoX() == x + 1 && elem.getKezdoY() == y + 1  ||
                    elem.getKezdoX() == x + 1 && elem.getKezdoY() == y      ||
                    elem.getKezdoX() == x + 1 && elem.getKezdoY() == y - 1  ||
                    elem.getKezdoX() == x && elem.getKezdoY() == y - 1      ||
                    elem.getKezdoX() == x - 1 && elem.getKezdoY() == y - 1) {
                if (elem.getClass().getSimpleName().equals("Hordo")) {
                    ArrayList<Nyersanyag> hordoiElemei = Hordo.getTartalom();

                    for (Nyersanyag nyersanyag : hordoiElemei) {
                        taska.add(nyersanyag);
                        System.out.println("Felvetted: " + nyersanyag.getClass().getSimpleName());
                    }
                } else {
                    taska.add(elem);
                    System.out.println("Felvetted: " + elem.getClass().getSimpleName());
                }
                it.remove();
                break;
            }
        }
    }

    /**
     * Tűzhely építése (játékos által):
     * Megvizsgálja: van-e elég nyersanyaga a játékosnak, ha igen, az inputból bekért koordináta helyes-e(Lehet-e oda tűzhelyet rakni), ha mindkettő megfelel megépíti a tűzhelyet(A nyersanyagokat levonja)
     * Ha valamelyik feltétel nem teljesül, kiírja, és nem fut tovább.
     * @param palya
     * @param palyaKezelo
     * @param tuzhely
     */
    public void tuzhelyKeszites(int[][] palya, PalyaKezelo palyaKezelo, Tuzhely tuzhely){
        System.out.println("Tűzhelyet akarsz készíteni!");
        System.out.println("Tűzhelykészítés ára: 2 deszka, 4 levél, 3 hulladék");
        int deszkaDb = 0;
        int levelDb = 0;
        int hulladekDb = 0;

        for(Nyersanyag nyersanyag : taska){
            if(nyersanyag.getClass().getSimpleName().equals("Deszka")){
                deszkaDb++;
            }else if(nyersanyag.getClass().getSimpleName().equals("Level")){
                levelDb++;
            }else if(nyersanyag.getClass().getSimpleName().equals("Hulladek")){
                hulladekDb++;
            }
        }
        if(deszkaDb >= 2 && levelDb >= 4 && hulladekDb >= 3){

            System.out.println("Van elég alapanyagod!");
            System.out.print("Hova szeretnél építeni? ");
            System.out.println("Add meg melyik sorba szeretnéd lehelyezni: ");
            Scanner sc1 = new Scanner(System.in);
            int valaszX = sc1.nextInt()-1;
            System.out.println("Add meg melyik oszlopba szeretnéd lehelyezni: ");
            Scanner sc2 = new Scanner(System.in);
            int valaszY = sc2.nextInt()-1;
            if (palya[valaszX][valaszY] == 1){
                palyaKezelo.tuzhelyLehelyez(palya,valaszX,valaszY);
                tuzhely.setTuzhelyDb(tuzhely.getTuzhelyDb()+1);
                System.out.println("Tűzhelyed megépült!");
                palyaKezelo.palyatKiir(palya);

            }else{
                System.out.println("Csak a tutajra építhetsz.");
            }
            for(Nyersanyag nyersanyag : taska){
                if(nyersanyag.getClass().getSimpleName().equals("deszka") && deszkaDb != 0){
                    taska.remove(nyersanyag);
                    deszkaDb--;
                }else if(nyersanyag.getClass().getSimpleName().equals("level") && levelDb != 0){
                    taska.remove(nyersanyag);
                    levelDb--;
                }else if(nyersanyag.getClass().getSimpleName().equals("hulladek") && hulladekDb != 0){
                    taska.remove(nyersanyag);
                    hulladekDb--;
                }
            }
        }else{
            System.out.println("Nincs elég alapanyagod!");
        }

    }

    /**
     * Játékos elkészíti az ételt:
     * Csak akkor fut le ha már van megépítve tűzhelyünk
     * Inputból az étel típusát(hal vagy krumpli) bekérve megnézi, van-e a játékosnak, ha van törli a táskából és a tűzhelyre rakja.
     * (Egyéb esetben jelzi a hibát)
     * @param tuzhely
     * @throws HibasValasz
     */
    public void etelKeszites(Tuzhely tuzhely) throws HibasValasz {
        if(tuzhely.getTuzhelyDb() >= 1){
            System.out.println("Mit szeretnél sütni?");
            System.out.println("Lehetőségeid: hal, krumpli");
            Scanner sc = new Scanner(System.in);
            String valasz = sc.nextLine();
            int db = 0;
            switch (valasz) {

                case "hal":
                    for (Nyersanyag nyersanyag : taska) {
                        if(nyersanyag.getClass().getSimpleName().equals("Hal")){
                            System.out.println("Halat raktál a sütőbe!");
                            taska.remove(nyersanyag);
                            tuzhely.setSutValamit(true);
                            db++;
                            break;
                        }
                    }
                    if(db == 0){
                        System.out.println("Nincs hal a táskádban! Előbb szerezz egyet...");
                    }
                    break;
                case "krumpli":
                    for (Nyersanyag nyersanyag : taska) {
                        if(nyersanyag.getClass().getSimpleName().equals("Krumpli")){
                            System.out.println("Krumplit raktál a sütőbe!");
                            taska.remove(nyersanyag);
                            tuzhely.setSutValamit(true);
                            db++;
                        }

                    }
                    if(db == 0){
                        System.out.println("Nincs krumpli a táskádban! Előbb szerezz egyet...");
                    }
                    break;

                default:
                    throw new HibasValasz();
            }
        }else{
            System.out.println("Nincs tűzhelyed! Előbb csinálj egyet...");
        }
    }

    /**
     * Víztisztító építése (játékos által):
     * Megvizsgálja: van-e elég nyersanyaga a játékosnak, ha igen, az inputból bekért koordináta helyes-e(Lehet-e oda víztisztítót rakni), ha mindkettő megfelel megépíti a víztisztítót(A nyersanyagokat levonja)
     * Ha valamelyik feltétel nem teljesül, kiírja, és nem fut tovább.
     * @param palya
     * @param palyaKezelo
     * @param viztisztito
     */
    public void viztisztitoKeszites(int[][] palya, PalyaKezelo palyaKezelo, Viztisztito viztisztito){
        System.out.println("Víztisztítót akarsz készíteni!");
        System.out.println("Víztisztító készítés ára: 2 levél, 4 hulladék");
        int levelDb = 0;
        int hulladekDb = 0;

        for(Nyersanyag nyersanyag : taska){
            if(nyersanyag.getClass().getSimpleName().equals("Level")){
                levelDb++;
            }else if(nyersanyag.getClass().getSimpleName().equals("Hulladek")){
                hulladekDb++;
            }
        }
        if(levelDb >= 2 && hulladekDb >= 4){

            System.out.println("Van elég alapanyagod!");
            System.out.print("Hova szeretnél építeni? ");
            System.out.println("Add meg melyik sorba szeretnéd lehelyezni:");
            Scanner sc1 = new Scanner(System.in);
            int valaszX = sc1.nextInt()-1;
            System.out.println("Add meg melyik oszlopba szeretnéd lehelyezni:");
            Scanner sc2 = new Scanner(System.in);
            int valaszY = sc2.nextInt()-1;
            if (palya[valaszX][valaszY] == 1){
                palyaKezelo.viztisztitoLehelyez(palya,valaszX,valaszY);
                viztisztito.setViztisztitoDb(viztisztito.getViztisztitoDb()+1);
                System.out.println("Víztisztítód megépült!");
                palyaKezelo.palyatKiir(palya);

            }else{
                System.out.println("Csak a tutajra építhetsz.");
            }
            for(Nyersanyag nyersanyag : taska){
                if(nyersanyag.getClass().getSimpleName().equals("level") && levelDb != 0){
                    taska.remove(nyersanyag);
                    levelDb--;
                }else if(nyersanyag.getClass().getSimpleName().equals("hulladek") && hulladekDb != 0){
                    taska.remove(nyersanyag);
                    hulladekDb--;
                }
            }
        }else{
            System.out.println("Nincs elég alapanyagod!");
        }
    }

    /**
     * Játékos iszik:
     * Ha a játékos a víztisztítóra lép, és van rajta pohár, megissza a vizet, ezáltal nő a szomjűság értéke +40-el.
     * Különben hibát ír.
     * @param viztisztito
     */
    public void ivas(Viztisztito viztisztito) {
        if(viztisztito.getX() == x && viztisztito.getY() == y){
            if(viztisztito.isPoharDb()){
                System.out.println("Megittál egy pohár vizet!");
                setSzomjusag(getSzomjusag()+40);
                viztisztito.setPoharDb(false);

            }else{
                System.out.println("Nincs még víz!");
            }
        }else{
            System.out.println("Csak a víztisztító mezőn ihatsz!");
        }
    }

    /**
     * Játékos bővíti a tutajt:
     * Ha van elég nyersanyag, az inputból kapott koordináták helyesek,a tutajon áll és 1 egysényire akarja magától bővíteni, akkor bővíti a tutajt 1 egységgel (A nyersanyagokat levonja)
     * Különben hibát ír.
     * @param palya
     * @param palyaKezelo
     */
    public void tutajBovites(int[][] palya, PalyaKezelo palyaKezelo){
        System.out.println("Tutajod bővíteni szeretnéd...");
        System.out.println("Tutaj bővítésének ára: 2 deszka és 2 levél");
        int deszkaDb = 0;
        int levelDb = 0;

        for(Nyersanyag nyersanyag : taska){
            if(nyersanyag.getClass().getSimpleName().equals("Level")){
                levelDb++;
            }else if(nyersanyag.getClass().getSimpleName().equals("Deszka")){
                deszkaDb++;
            }
        }
        if(deszkaDb >= 2 && levelDb >= 2){
            System.out.println("Van elég alapanyagod!");
            System.out.print("Hol szeretnéd kibővíteni tutajod?");
            System.out.println("Add meg melyik sorba szeretnéd bővíteni:");
            Scanner sc1 = new Scanner(System.in);
            int valaszX = sc1.nextInt()-1;
            System.out.println("Add meg melyik oszlopba szeretnéd bővíteni:");
            Scanner sc2 = new Scanner(System.in);
            int valaszY = sc2.nextInt()-1;
            if (palya[valaszX][valaszY] == 0 && palya[x][y] == 1){
                if(valaszX == x - 1 && valaszY == y      ||
                        valaszX == x - 1 && valaszY == y + 1  ||
                        valaszX == x && valaszY == y + 1      ||
                        valaszX == x + 1 && valaszY == y + 1  ||
                        valaszX == x + 1 && valaszY == y      ||
                        valaszX == x + 1 && valaszY == y - 1  ||
                        valaszX == x && valaszY == y - 1      ||
                        valaszX == x - 1 && valaszY == y - 1){
                    palya[valaszX][valaszY] = 1;
                    System.out.println("Kibővítetted a tutajod!");
                    palyaKezelo.palyatKiir(palya);
                }else{
                    System.out.println("Túl messze vagy! Csak 1 távolságról bővítheted tutajod!");
                }


            }else{
                System.out.println("Csak a tutajodról tudsz bővíteni!");
            }
            for(Nyersanyag nyersanyag : taska){
                if(nyersanyag.getClass().getSimpleName().equals("level") && levelDb != 0){
                    taska.remove(nyersanyag);
                    levelDb--;
                }else if(nyersanyag.getClass().getSimpleName().equals("deszka") && deszkaDb != 0){
                    taska.remove(nyersanyag);
                    deszkaDb--;
                }
            }
        }else{
            System.out.println("Nincs elég alapanyagod!");
        }

    }

    /**
     * A játékos hálót készít:
     * Ha van elég nyersanyag, az inputból kapott koordináták helyesek,a tutajon áll és 1 egysényire akarja magától bővíteni, akkor bővíti a tutajt 1 egységgel (A nyersanyagokat levonja)
     * Különben hibát ír.
     * @param palya
     * @param palyaKezelo
     * @param halo
     */
    public void haloKeszites(int[][] palya, PalyaKezelo palyaKezelo, Halo halo){
        System.out.println("Hálót akarsz készíteni!");
        System.out.println("Háló készítésének ára: 2 deszka, 6 levél");
        int deszkaDb = 0;
        int levelDb = 0;;

        for(Nyersanyag nyersanyag : taska){
            if(nyersanyag.getClass().getSimpleName().equals("Deszka")){
                deszkaDb++;
            }else if(nyersanyag.getClass().getSimpleName().equals("Level")){
                levelDb++;
            }
        }
        if(deszkaDb >= 2 && levelDb >= 6){
            System.out.println("Van elég alapanyagod!");
            System.out.print("Hova szeretnél építeni? ");
            System.out.println("Add meg melyik sorba szeretnéd lehelyezni: ");
            Scanner sc1 = new Scanner(System.in);
            int valaszX = sc1.nextInt()-1;
            System.out.println("Add meg melyik oszlopba szeretnéd lehelyezni: ");
            Scanner sc2 = new Scanner(System.in);
            int valaszY = sc2.nextInt()-1;
            if (palya[valaszX][valaszY] == 0 && palya[x][y] == 1){
                if(valaszX == x - 1 && valaszY == y      ||
                        valaszX == x - 1 && valaszY == y + 1  ||
                        valaszX == x && valaszY == y + 1      ||
                        valaszX == x + 1 && valaszY == y + 1  ||
                        valaszX == x + 1 && valaszY == y      ||
                        valaszX == x + 1 && valaszY == y - 1  ||
                        valaszX == x && valaszY == y - 1      ||
                        valaszX == x - 1 && valaszY == y - 1) {
                    palyaKezelo.haloLehelyez(palya, valaszX, valaszY);
                    halo.setHaloDb(halo.getHaloDb() + 1);
                    System.out.println("Hálód megépült!");
                    palyaKezelo.palyatKiir(palya);
                }else{
                    System.out.println("Túl messze vagy!");
                }
            }else{
                System.out.println("Csak a vízbe rakhatsz hálót.");
            }
            for(Nyersanyag nyersanyag : taska){
                if(nyersanyag.getClass().getSimpleName().equals("deszka") && deszkaDb != 0){
                    taska.remove(nyersanyag);
                    deszkaDb--;
                }else if(nyersanyag.getClass().getSimpleName().equals("level") && levelDb != 0){
                    taska.remove(nyersanyag);
                    levelDb--;
                }
            }
        }else {
            System.out.println("Nincs elég alapanyagod!");
        }
    }
}
