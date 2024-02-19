package InteropClasses;

public class Mesto extends InteropClass {

    public Mesto() {
    }

    public Mesto(Integer iDMesta, String naziv, Integer postanskiBroj) {
        this.iDMesta = iDMesta;
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }
    
    private Integer iDMesta;
    private String naziv;
    private Integer postanskiBroj;

    public Integer getiDMesta() {
        return iDMesta;
    }

    public void setiDMesta(Integer iDMesta) {
        this.iDMesta = iDMesta;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(Integer postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj==this){return true;}
        if(!(obj instanceof Mesto)){return false;}
        return memberWiseCompare(obj);
    }

    @Override
    public int numcol() {
        return 3;
    }

    @Override
    public int[] colsize() {
        return new int[]{iDMesta.toString().length(),naziv.length(),postanskiBroj.toString().length()};
    }

    @Override
    public String[] colnames() {
        return new String[]{"IdMesta","Naziv","PostanskiBroj"};
    }

    @Override
    public String[] colvals() {
        return new String[]{iDMesta.toString(),naziv,postanskiBroj.toString()};
    }


}
