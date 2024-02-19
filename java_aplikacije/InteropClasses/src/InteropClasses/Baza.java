package InteropClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class Baza implements Serializable{

    public Baza() {
        mesta=new ArrayList<>();
        komitenti=new ArrayList<>();
        filijale=new ArrayList<>();
        racuni=new ArrayList<>();
        transakcije=new ArrayList<>();
    }
    
    private ArrayList<Mesto> mesta;
    private ArrayList<Komitent> komitenti;
    private ArrayList<Filijala> filijale;
    private ArrayList<Racun> racuni;
    private ArrayList<Transakcija> transakcije;

    public ArrayList<Mesto> getMesta() {
        return mesta;
    }

    public void setMesta(ArrayList<Mesto> mesta) {
        this.mesta = mesta;
    }

    public ArrayList<Komitent> getKomitenti() {
        return komitenti;
    }

    public void setKomitenti(ArrayList<Komitent> komitenti) {
        this.komitenti = komitenti;
    }

    public ArrayList<Filijala> getFilijale() {
        return filijale;
    }

    public void setFilijale(ArrayList<Filijala> filijale) {
        this.filijale = filijale;
    }

    public ArrayList<Racun> getRacuni() {
        return racuni;
    }

    public void setRacuni(ArrayList<Racun> racuni) {
        this.racuni = racuni;
    }

    public ArrayList<Transakcija> getTransakcije() {
        return transakcije;
    }

    public void setTransakcije(ArrayList<Transakcija> transakcije) {
        this.transakcije = transakcije;
    }
    
}
