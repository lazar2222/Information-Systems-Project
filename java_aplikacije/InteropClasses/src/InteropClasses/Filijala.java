package InteropClasses;

public class Filijala extends InteropClass {

    public Filijala() {
    }
    
    public Filijala(Integer iDFilijale, String naziv, String adresa, Integer iDMesta) {
        this.iDFilijale = iDFilijale;
        this.naziv = naziv;
        this.adresa = adresa;
        this.iDMesta = iDMesta;
    }
    
    private Integer iDFilijale;
    private String naziv;
    private String adresa;
    private Integer iDMesta;

    public Integer getiDFilijale() {
        return iDFilijale;
    }

    public void setiDFilijale(Integer iDFilijale) {
        this.iDFilijale = iDFilijale;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Integer getiDMesta() {
        return iDMesta;
    }

    public void setiDMesta(Integer iDMesta) {
        this.iDMesta = iDMesta;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj==this){return true;}
        if(!(obj instanceof Filijala)){return false;}
        return memberWiseCompare(obj);
    }

    @Override
    public int numcol() {
        return 4;
    }

    @Override
    public int[] colsize() {
        return new int[]{iDFilijale.toString().length(),naziv.length(),adresa.length(),iDMesta.toString().length()};
    }

    @Override
    public String[] colnames() {
        return new String[]{"IdFilijale","Naziv","Adresa","IdMesta"};
    }

    @Override
    public String[] colvals() {
        return new String[]{iDFilijale.toString(),naziv,adresa,iDMesta.toString()};
    }
}
