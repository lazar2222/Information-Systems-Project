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
@Table(name = "mesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesta.findAll", query = "SELECT m FROM Mesta m"),
    @NamedQuery(name = "Mesta.findByIDMesta", query = "SELECT m FROM Mesta m WHERE m.iDMesta = :iDMesta"),
    @NamedQuery(name = "Mesta.findByNaziv", query = "SELECT m FROM Mesta m WHERE m.naziv = :naziv"),
    @NamedQuery(name = "Mesta.findByPostanskiBroj", query = "SELECT m FROM Mesta m WHERE m.postanskiBroj = :postanskiBroj")})
public class Mesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "IDMesta")
    private Integer iDMesta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PostanskiBroj")
    private int postanskiBroj;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMestaSedista")
    private List<Komitenti> komitentiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMestaOtvaranja")
    private List<Racuni> racuniList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMesta")
    private List<Filijale> filijaleList;

    public Mesta() {
    }

    public Mesta(Integer iDMesta) {
        this.iDMesta = iDMesta;
    }

    public Mesta(Integer iDMesta, String naziv, int postanskiBroj) {
        this.iDMesta = iDMesta;
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }

    public Integer getIDMesta() {
        return iDMesta;
    }

    public void setIDMesta(Integer iDMesta) {
        this.iDMesta = iDMesta;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setPostanskiBroj(int postanskiBroj) {
        this.postanskiBroj = postanskiBroj;
    }

    @XmlTransient
    public List<Komitenti> getKomitentiList() {
        return komitentiList;
    }

    public void setKomitentiList(List<Komitenti> komitentiList) {
        this.komitentiList = komitentiList;
    }

    @XmlTransient
    public List<Racuni> getRacuniList() {
        return racuniList;
    }

    public void setRacuniList(List<Racuni> racuniList) {
        this.racuniList = racuniList;
    }

    @XmlTransient
    public List<Filijale> getFilijaleList() {
        return filijaleList;
    }

    public void setFilijaleList(List<Filijale> filijaleList) {
        this.filijaleList = filijaleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDMesta != null ? iDMesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesta)) {
            return false;
        }
        Mesta other = (Mesta) object;
        if ((this.iDMesta == null && other.iDMesta != null) || (this.iDMesta != null && !this.iDMesta.equals(other.iDMesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityClasses.Mesta[ iDMesta=" + iDMesta + " ]";
    }
    
}
