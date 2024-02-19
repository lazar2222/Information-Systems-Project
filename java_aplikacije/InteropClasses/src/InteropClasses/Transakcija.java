package InteropClasses;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.bind.annotation.JsonbDateFormat;

public class Transakcija extends InteropClass {

    public Transakcija() {
    }

    public Transakcija(Integer iDTransakcije, Long iznos, Date datumVremeRealizacije, Integer redniBrojSrc, Integer redniBrojDst, String svrha, Integer iDFilijale, Integer iDDstRacuna, Integer iDSrcRacuna, Boolean checked) {
        this.iDTransakcije = iDTransakcije;
        this.iznos = iznos;
        this.datumVremeRealizacije = datumVremeRealizacije;
        this.redniBrojSrc = redniBrojSrc;
        this.redniBrojDst = redniBrojDst;
        this.svrha = svrha;
        this.iDFilijale = iDFilijale;
        this.iDDstRacuna = iDDstRacuna;
        this.iDSrcRacuna = iDSrcRacuna;
        this.checked=checked;
    }
    
    private Integer iDTransakcije;
    private Long iznos;
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date datumVremeRealizacije;
    private Integer redniBrojSrc;
    private Integer redniBrojDst;
    private String svrha;
    private Integer iDFilijale;
    private Integer iDDstRacuna;
    private Integer iDSrcRacuna;
    private Boolean checked;

    public Integer getiDTransakcije() {
        return iDTransakcije;
    }

    public void setiDTransakcije(Integer iDTransakcije) {
        this.iDTransakcije = iDTransakcije;
    }

    public Long getIznos() {
        return iznos;
    }

    public void setIznos(Long iznos) {
        this.iznos = iznos;
    }

    public Date getDatumVremeRealizacije() {
        return datumVremeRealizacije;
    }

    public void setDatumVremeRealizacije(Date datumVremeRealizacije) {
        this.datumVremeRealizacije = datumVremeRealizacije;
    }

    public Integer getRedniBrojSrc() {
        return redniBrojSrc;
    }

    public void setRedniBrojSrc(Integer redniBrojSrc) {
        this.redniBrojSrc = redniBrojSrc;
    }

    public Integer getRedniBrojDst() {
        return redniBrojDst;
    }

    public void setRedniBrojDst(Integer redniBrojDst) {
        this.redniBrojDst = redniBrojDst;
    }

    public String getSvrha() {
        return svrha;
    }

    public void setSvrha(String svrha) {
        this.svrha = svrha;
    }

    public Integer getiDFilijale() {
        return iDFilijale;
    }

    public void setiDFilijale(Integer iDFilijale) {
        this.iDFilijale = iDFilijale;
    }

    public Integer getiDDstRacuna() {
        return iDDstRacuna;
    }

    public void setiDDstRacuna(Integer iDDstRacuna) {
        this.iDDstRacuna = iDDstRacuna;
    }

    public Integer getiDSrcRacuna() {
        return iDSrcRacuna;
    }

    public void setiDSrcRacuna(Integer iDSrcRacuna) {
        this.iDSrcRacuna = iDSrcRacuna;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==this){return true;}
        if(!(obj instanceof Transakcija)){return false;}
        return memberWiseCompare(obj);
    }

    @Override
    public int numcol() {
        return 9;
    }

    @Override
    public int[] colsize() {
        return new int[]{iDTransakcije.toString().length(),(iDSrcRacuna==null?0:iDSrcRacuna.toString().length()),(iDDstRacuna==null?0:iDDstRacuna.toString().length()),iznos.toString().length(),new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(datumVremeRealizacije).length(),(redniBrojSrc==null?0:redniBrojSrc.toString().length()),(redniBrojDst==null?0:redniBrojDst.toString().length()),svrha.length(),(iDFilijale==null?0:iDFilijale.toString().length())};
    }

    @Override
    public String[] colnames() {
        return new String[]{"IdTransakcije","IdRacunaSa","IdRacunaNa","Iznos","DatumRealizacije","RedBrTransakcijeRacunaSa","RedBrTransakcijeRacunaNa","Svrha","IdFilijale"};
    }

    @Override
    public String[] colvals() {
        return new String[]{iDTransakcije.toString(),(iDSrcRacuna==null?"":iDSrcRacuna.toString()),(iDDstRacuna==null?"":iDDstRacuna.toString()),iznos.toString(),new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(datumVremeRealizacije),(redniBrojSrc==null?"":redniBrojSrc.toString()),(redniBrojDst==null?"":redniBrojDst.toString()),svrha,(iDFilijale==null?"":iDFilijale.toString())};
    }


    
    
}
