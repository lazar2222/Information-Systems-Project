package InteropClasses;

public class Komitent extends InteropClass {

    public Komitent() {
    }

    public Komitent(Integer iDKomitenta, String naziv, String adresa, Integer iDMestaSedista) {
        this.iDKomitenta = iDKomitenta;
        this.naziv = naziv;
        this.adresa = adresa;
        this.iDMestaSedista = iDMestaSedista;
    }
    
    private Integer iDKomitenta;
    private String naziv;
    private String adresa;
    private Integer iDMestaSedista;

    public Integer getiDKomitenta() {
        return iDKomitenta;
    }

    public void setiDKomitenta(Integer iDKomitenta) {
        this.iDKomitenta = iDKomitenta;
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

    public Integer getiDMestaSedista() {
        return iDMestaSedista;
    }

    public void setiDMestaSedista(Integer iDMestaSedista) {
        this.iDMestaSedista = iDMestaSedista;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj==this){return true;}
        if(!(obj instanceof Komitent)){return false;}
        return memberWiseCompare(obj);
    }

    @Override
    public int numcol() {
        return 4;
    }

    @Override
    public int[] colsize() {
        return new int[]{iDKomitenta.toString().length(),naziv.length(),adresa.length(),iDMestaSedista.toString().length()};
    }

    @Override
    public String[] colnames() {
        return new String[]{"IdKomitenta","Naziv","Adresa","IdMestaSedista"};
    }

    @Override
    public String[] colvals() {
        return new String[]{iDKomitenta.toString(),naziv,adresa,iDMestaSedista.toString()};
    }
}
