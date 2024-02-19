/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityClasses;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "komitenti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Komitenti.findAll", query = "SELECT k FROM Komitenti k"),
    @NamedQuery(name = "Komitenti.findByIDKomitenta", query = "SELECT k FROM Komitenti k WHERE k.iDKomitenta = :iDKomitenta"),
    @NamedQuery(name = "Komitenti.findByNaziv", query = "SELECT k FROM Komitenti k WHERE k.naziv = :naziv"),
    @NamedQuery(name = "Komitenti.findByAdresa", query = "SELECT k FROM Komitenti k WHERE k.adresa = :adresa")})
public class Komitenti implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDKomitenta")
    private Integer iDKomitenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Adresa")
    private String adresa;
    @JoinColumn(name = "IDMestaSedista", referencedColumnName = "IDMesta")
    @ManyToOne(optional = false)
    private Mesta iDMestaSedista;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDKomitenta")
    private List<Racuni> racuniList;

    public Komitenti() {
    }

    public Komitenti(Integer iDKomitenta) {
        this.iDKomitenta = iDKomitenta;
    }

    public Komitenti(Integer iDKomitenta, String naziv, String adresa) {
        this.iDKomitenta = iDKomitenta;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    public Integer getIDKomitenta() {
        return iDKomitenta;
    }

    public void setIDKomitenta(Integer iDKomitenta) {
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

    public Mesta getIDMestaSedista() {
        return iDMestaSedista;
    }

    public void setIDMestaSedista(Mesta iDMestaSedista) {
        this.iDMestaSedista = iDMestaSedista;
    }

    @XmlTransient
    public List<Racuni> getRacuniList() {
        return racuniList;
    }

    public void setRacuniList(List<Racuni> racuniList) {
        this.racuniList = racuniList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDKomitenta != null ? iDKomitenta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Komitenti)) {
            return false;
        }
        Komitenti other = (Komitenti) object;
        if ((this.iDKomitenta == null && other.iDKomitenta != null) || (this.iDKomitenta != null && !this.iDKomitenta.equals(other.iDKomitenta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityClasses.Komitenti[ iDKomitenta=" + iDKomitenta + " ]";
    }
    
}
