package kezelok;

import ellenseg.Capa;
import jatekos.Jatekos;
import kiegeszitok.Halo;
import kiegeszitok.Tuzhely;
import kiegeszitok.Viztisztito;
import kivetelek.ErreNemMehetsz;
import kivetelek.Gyozelem;
import kivetelek.HibasValasz;
import kivetelek.MozgasVege;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class JatekKezelo {
    public int[][] palya;
    private PalyaKezelo palyaKezelo;
    private Jatekos jatekos;
    private Capa capa;
    private Tuzhely tuzhely;
    private Viztisztito viztisztito;
    private Halo halo;
    private NyersanyagKezelo nyersanyagKezelo;
    KorFigyelo korFigyelo;
    Mentes mentes;

    /**
     * Objektumok inicializálása
     */
    public JatekKezelo() {
        jatekos = new Jatekos();
        capa = new Capa();
        tuzhely = new Tuzhely();
        viztisztito = new Viztisztito();
        halo = new Halo();
        palyaKezelo = new PalyaKezelo(jatekos, nyersanyagKezelo, capa ,tuzhely, viztisztito, halo);
        korFigyelo = new KorFigyelo(jatekos, tuzhely, viztisztito, halo);
        mentes = new Mentes();
    }

    /**
     * A játék beállítása:
     * Kezdeti állapot beállításának meghívása, éhség és szomjúség figyelése
     * Utasítások inputból való beolvasása és végrehajtása, hibás válasz esetén annak jelzése.
     * Győzelem és vereség lekezelése
     * @throws IOException
     * @throws HibasValasz Nem létező input esetén ezt a kivételt dobja
     */
    public void jatek() throws IOException, HibasValasz {
        kezdetiAllapotBeallitas();
        palyaKezelo.palyatKiir(palya);
        while (korFigyelo.cselekvesekSzama != 0) {
            System.out.println("Mit szeretne csinálni?");
            System.out.println("Lehetőségek: mozgás, horgászás, tűzhelyKészítés, sütés, víztisztítóKészítés, ivás, tutajBővítés, hálóKészítés, mentés, játékBetöltése");
            System.out.print("Válasz: ");
            Scanner sc = new Scanner(System.in);
            String valasz = sc.nextLine();

            if(jatekos.getEhseg()-1 >= 0 && jatekos.getSzomjusag()-1 >= 0){
                jatekos.setEhseg(jatekos.getEhseg()-1);
                jatekos.setSzomjusag(jatekos.getSzomjusag()-1);
            }else{
                vereseg();
            }

            try {
                switch (valasz) {
                    case "mozgás":
                        mozgas();
                        palyaKezelo.palyatKiir(palya);
                        break;
                    case "horgászás":
                        jatekos.horgaszas(palya);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "tűzhelyKészítés":
                        jatekos.tuzhelyKeszites(palya, palyaKezelo, tuzhely);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "sütés":
                        jatekos.etelKeszites(tuzhely);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "víztisztítóKészítés":
                        jatekos.viztisztitoKeszites(palya, palyaKezelo, viztisztito);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "ivás":
                        jatekos.ivas(viztisztito);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "tutajBővítés":
                        jatekos.tutajBovites(palya, palyaKezelo);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "hálóKészítés":
                        jatekos.haloKeszites(palya, palyaKezelo, halo);
                        NyersanyagKezelo.nyersanyagHullas(palya);
                        korFigyelo.korFigyelese();
                        break;
                    case "mentés":
                        mentes.jatekMentese(jatekos, korFigyelo, palya, tuzhely, viztisztito, capa);
                        break;
                    case "játékBetöltése":
                        Betoltes.jatekBetoltese(palyaKezelo, jatekos, korFigyelo, palya, tuzhely, viztisztito, capa);
                        palyaKezelo.palyatKiir(palya);
                        break;
                    default:
                        System.out.println("Ilyen lehetőség nincs");
                        break;
                }
            } catch (Gyozelem gyozelem) {
                gyozelem();
                return;
            }
        }
    }

    /**
     * Játékos mozgatása az inputból bekért adat segítségével
     * Új nyersanyagokat hoz létre a pályán, a játékos közelében lévő nyersanyagok felszedése, mozgás következményeinek meghívása
     *
     * @throws HibasValasz Nem létező input esetén ezt a kivételt dobja
     * @throws ErreNemMehetsz  Pályáról történő lelépés esetén ezt a kivételt dobja
     */
    public void mozgas() throws Gyozelem {
        System.out.println("Mozgás következik");

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Add meg merre szeretnél menni: fel, le, jobbra, balra, jobbraFel, jobbraLe, balraFel, balraLe, vége");
            System.out.print("A válaszod: ");
            String valasz = sc.nextLine();
            try {
                NyersanyagKezelo.ujSorLetrehozasa();
                jatekos.nyersanyagFelszedes();
                jatekos.lepes(valasz, palya);
                jatekos.nyersanyagFelszedes();
                mozgasKovetkezmenye();
            } catch (HibasValasz e) {
                System.out.println("Ilyen válasz nem létezik!");
            } catch (MozgasVege mozgasVege) {
                break;
            } catch (ErreNemMehetsz e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Mozgás következtében történő változások beállítása:
     *Körfigyelelés metódus meghívása, cápa mozgatása, pálya kiírása, nyersanyagok mozgatása
     */
    private void mozgasKovetkezmenye() throws Gyozelem {
        korFigyelo.korFigyelese();
        capa.uszas(palya);
        palyaKezelo.palyatKiir(palya);
        NyersanyagKezelo.nyersanyagHullas(palya);
    }

    /**
     * Játék kezdetén a bevezető szöveg kiírása, játék rövid ismertetése
     */
    private void bevezeto(){
        System.out.println("Üdvözöl a Raft játék!");
        System.out.println("A játék célja, hogy túlélj 1000 cselekvést anélkül, hogy bármi rossz történne veled.");
        System.out.println("Karakteredet a ☻ jelzésnél találod, vele tudsz: mozogni, építeni, horgászni, sütni, inni, stb... de a cápával vigyázz!");
        System.out.println("Kezdjünk is bele!");
        System.out.println("A pálya kezdeti állapota:");
    }

    /**
     * Győzelem esetén konzolra írás
     */
    private void gyozelem() {
        System.out.println("Gratulálok megnyereted a játékot!");
    }

    /**
     * Vereség esetén konzolra írás
     */
    private void vereseg(){
        System.out.println("Sajnos vesztettél!");
    }

    /**
     * A játék kezdeti állásának beállítása:
     * Amely a pálya betöltése illetve a játékos és a cápa lehelyezése, a cselekvések számának beállítása 1000-re, az éhség és szomjúság értékének beállítása 100-100-ra
     *
     * @throws FileNotFoundException Ha a pálya betöltése során nem található a megadott fájl ezt a kivételt dobja
     */
    private void kezdetiAllapotBeallitas() throws FileNotFoundException {
        bevezeto();
        palya = palyaKezelo.palyatBeallit();
        palyaKezelo.jatekostLehelyez(palya);
        palyaKezelo.capaLehelyez(palya);
        korFigyelo.cselekvesekSzama = 1000;
        jatekos.setEhseg(100);
        jatekos.setSzomjusag(100);
    }

}
