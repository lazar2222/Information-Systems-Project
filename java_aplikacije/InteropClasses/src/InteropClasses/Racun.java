package InteropClasses;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.bind.annotation.JsonbDateFormat;

public class Racun extends InteropClass {

    public Racun() {
    }

    public Racun(Integer iDRacuna, Integer iDKomitenta, Integer iDMestaOtvaranja, Long stanje, Long dozvoljenMinus, Character status, Date datumVremeOtvaranja, Integer brojTransakcija) {
        this.iDRacuna = iDRacuna;
        this.iDKomitenta = iDKomitenta;
        this.iDMestaOtvaranja = iDMestaOtvaranja;
        this.stanje = stanje;
        this.dozvoljenMinus = dozvoljenMinus;
        this.status = status;
        this.datumVremeOtvaranja = datumVremeOtvaranja;
        this.brojTransakcija = brojTransakcija;
    }
    
    private Integer iDRacuna;
    private Integer iDKomitenta;
    private Integer iDMestaOtvaranja;
    private Long stanje;
    private Long dozvoljenMinus;
    private Character status;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date datumVremeOtvaranja;
    private Integer brojTransakcija;

    public Integer getiDRacuna() {
        return iDRacuna;
    }

    public void setiDRacuna(Integer iDRacuna) {
        this.iDRacuna = iDRacuna;
    }

    public Integer getiDKomitenta() {
        return iDKomitenta;
    }

    public void setiDKomitenta(Integer iDKomitenta) {
        this.iDKomitenta = iDKomitenta;
    }

    public Integer getiDMestaOtvaranja() {
        return iDMestaOtvaranja;
    }

    public void setiDMestaOtvaranja(Integer iDMestaOtvaranja) {
        this.iDMestaOtvaranja = iDMestaOtvaranja;
    }

    public Long getStanje() {
        return stanje;
    }

    public void setStanje(Long stanje) {
        this.stanje = stanje;
    }

    public Long getDozvoljenMinus() {
        return dozvoljenMinus;
    }

    public void setDozvoljenMinus(Long dozvoljenMinus) {
        this.dozvoljenMinus = dozvoljenMinus;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public Date getDatumVremeOtvaranja() {
        return datumVremeOtvaranja;
    }

    public void setDatumVremeOtvaranja(Date datumVremeOtvaranja) {
        this.datumVremeOtvaranja = datumVremeOtvaranja;
    }

    public Integer getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(Integer brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj==this){return true;}
        if(!(obj instanceof Racun)){return false;}
        return memberWiseCompare(obj);
    }

    @Override
    public int numcol() {
        return 8;
    }

    @Override
    public int[] colsize() {
        return new int[]{iDRacuna.toString().length(),iDKomitenta.toString().length(),iDMestaOtvaranja.toString().length(),stanje.toString().length(),dozvoljenMinus.toString().length(),1,new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(datumVremeOtvaranja).length(),brojTransakcija.toString().length()};
    }

    @Override
    public String[] colnames() {
        return new String[]{"IdRacuna","IdKomitenta","IdMestaOtvaranja","Stanje","DozvoljeniMinus","Status","DatumOtvaranja","BrojTransakcija"};
    }

    @Override
    public String[] colvals() {
        return new String[]{iDRacuna.toString(),iDKomitenta.toString(),iDMestaOtvaranja.toString(),stanje.toString(),dozvoljenMinus.toString(),status.toString(),new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(datumVremeOtvaranja),brojTransakcija.toString()};
    }


}
