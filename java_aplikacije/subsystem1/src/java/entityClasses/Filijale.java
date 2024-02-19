/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityClasses;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "filijale")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filijale.findAll", query = "SELECT f FROM Filijale f"),
    @NamedQuery(name = "Filijale.findByIDFilijale", query = "SELECT f FROM Filijale f WHERE f.iDFilijale = :iDFilijale"),
    @NamedQuery(name = "Filijale.findByNaziv", query = "SELECT f FROM Filijale f WHERE f.naziv = :naziv"),
    @NamedQuery(name = "Filijale.findByAdresa", query = "SELECT f FROM Filijale f WHERE f.adresa = :adresa")})
public class Filijale implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDFilijale")
    private Integer iDFilijale;
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
    @JoinColumn(name = "IDMesta", referencedColumnName = "IDMesta")
    @ManyToOne(optional = false)
    private Mesta iDMesta;

    public Filijale() {
    }

    public Filijale(Integer iDFilijale) {
        this.iDFilijale = iDFilijale;
    }

    public Filijale(Integer iDFilijale, String naziv, String adresa) {
        this.iDFilijale = iDFilijale;
        this.naziv = naziv;
        this.adresa = adresa;
    }

    public Integer getIDFilijale() {
        return iDFilijale;
    }

    public void setIDFilijale(Integer iDFilijale) {
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

    public Mesta getIDMesta() {
        return iDMesta;
    }

    public void setIDMesta(Mesta iDMesta) {
        this.iDMesta = iDMesta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDFilijale != null ? iDFilijale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filijale)) {
            return false;
        }
        Filijale other = (Filijale) object;
        if ((this.iDFilijale == null && other.iDFilijale != null) || (this.iDFilijale != null && !this.iDFilijale.equals(other.iDFilijale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityClasses.Filijale[ iDFilijale=" + iDFilijale + " ]";
    }
    
}
