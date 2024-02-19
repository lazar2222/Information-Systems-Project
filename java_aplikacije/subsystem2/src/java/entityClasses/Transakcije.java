/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityClasses;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "transakcije")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcije.findAll", query = "SELECT t FROM Transakcije t"),
    @NamedQuery(name = "Transakcije.findByIDTransakcije", query = "SELECT t FROM Transakcije t WHERE t.iDTransakcije = :iDTransakcije"),
    @NamedQuery(name = "Transakcije.findByIznos", query = "SELECT t FROM Transakcije t WHERE t.iznos = :iznos"),
    @NamedQuery(name = "Transakcije.findByDatumVremeRealizacije", query = "SELECT t FROM Transakcije t WHERE t.datumVremeRealizacije = :datumVremeRealizacije"),
    @NamedQuery(name = "Transakcije.findByRedniBrojSrc", query = "SELECT t FROM Transakcije t WHERE t.redniBrojSrc = :redniBrojSrc"),
    @NamedQuery(name = "Transakcije.findByRedniBrojDst", query = "SELECT t FROM Transakcije t WHERE t.redniBrojDst = :redniBrojDst"),
    @NamedQuery(name = "Transakcije.findBySvrha", query = "SELECT t FROM Transakcije t WHERE t.svrha = :svrha"),
    @NamedQuery(name = "Transakcije.findByIDFilijale", query = "SELECT t FROM Transakcije t WHERE t.iDFilijale = :iDFilijale")})
public class Transakcije implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDTransakcije")
    private Integer iDTransakcije;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Iznos")
    private long iznos;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremeRealizacije")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeRealizacije;
    @Column(name = "RedniBrojSrc")
    private Integer redniBrojSrc;
    @Column(name = "RedniBrojDst")
    private Integer redniBrojDst;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Svrha")
    private String svrha;
    @Column(name = "IDFilijale")
    private Integer iDFilijale;
    @JoinColumn(name = "IDDstRacuna", referencedColumnName = "IDRacuna")
    @ManyToOne
    private Racuni iDDstRacuna;
    @JoinColumn(name = "IDSrcRacuna", referencedColumnName = "IDRacuna")
    @ManyToOne
    private Racuni iDSrcRacuna;

    public Transakcije() {
    }

    public Transakcije(Integer iDTransakcije) {
        this.iDTransakcije = iDTransakcije;
    }

    public Transakcije(Integer iDTransakcije, long iznos, Date datumVremeRealizacije, String svrha) {
        this.iDTransakcije = iDTransakcije;
        this.iznos = iznos;
        this.datumVremeRealizacije = datumVremeRealizacije;
        this.svrha = svrha;
    }

    public Integer getIDTransakcije() {
        return iDTransakcije;
    }

    public void setIDTransakcije(Integer iDTransakcije) {
        this.iDTransakcije = iDTransakcije;
    }

    public long getIznos() {
        return iznos;
    }

    public void setIznos(long iznos) {
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

    public Integer getIDFilijale() {
        return iDFilijale;
    }

    public void setIDFilijale(Integer iDFilijale) {
        this.iDFilijale = iDFilijale;
    }

    public Racuni getIDDstRacuna() {
        return iDDstRacuna;
    }

    public void setIDDstRacuna(Racuni iDDstRacuna) {
        this.iDDstRacuna = iDDstRacuna;
    }

    public Racuni getIDSrcRacuna() {
        return iDSrcRacuna;
    }

    public void setIDSrcRacuna(Racuni iDSrcRacuna) {
        this.iDSrcRacuna = iDSrcRacuna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTransakcije != null ? iDTransakcije.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcije)) {
            return false;
        }
        Transakcije other = (Transakcije) object;
        if ((this.iDTransakcije == null && other.iDTransakcije != null) || (this.iDTransakcije != null && !this.iDTransakcije.equals(other.iDTransakcije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityClasses.Transakcije[ iDTransakcije=" + iDTransakcije + " ]";
    }
    
}
