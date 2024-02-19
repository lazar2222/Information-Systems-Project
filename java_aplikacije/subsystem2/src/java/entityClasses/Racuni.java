/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author lazar
 */
@Entity
@Table(name = "racuni")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Racuni.findAll", query = "SELECT r FROM Racuni r"),
    @NamedQuery(name = "Racuni.findByIDRacuna", query = "SELECT r FROM Racuni r WHERE r.iDRacuna = :iDRacuna"),
    @NamedQuery(name = "Racuni.findByIDMestaOtvaranja", query = "SELECT r FROM Racuni r WHERE r.iDMestaOtvaranja = :iDMestaOtvaranja"),
    @NamedQuery(name = "Racuni.findByStanje", query = "SELECT r FROM Racuni r WHERE r.stanje = :stanje"),
    @NamedQuery(name = "Racuni.findByDozvoljenMinus", query = "SELECT r FROM Racuni r WHERE r.dozvoljenMinus = :dozvoljenMinus"),
    @NamedQuery(name = "Racuni.findByStatus", query = "SELECT r FROM Racuni r WHERE r.status = :status"),
    @NamedQuery(name = "Racuni.findByDatumVremeOtvaranja", query = "SELECT r FROM Racuni r WHERE r.datumVremeOtvaranja = :datumVremeOtvaranja"),
    @NamedQuery(name = "Racuni.findByBrojTransakcija", query = "SELECT r FROM Racuni r WHERE r.brojTransakcija = :brojTransakcija")})
public class Racuni implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDRacuna")
    private Integer iDRacuna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDMestaOtvaranja")
    private int iDMestaOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Stanje")
    private long stanje;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DozvoljenMinus")
    private long dozvoljenMinus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Status")
    private Character status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremeOtvaranja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremeOtvaranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BrojTransakcija")
    private int brojTransakcija;
    @JoinColumn(name = "IDKomitenta", referencedColumnName = "IDKomitenta")
    @ManyToOne(optional = false)
    private Komitenti iDKomitenta;
    @OneToMany(mappedBy = "iDDstRacuna")
    private List<Transakcije> transakcijeList;
    @OneToMany(mappedBy = "iDSrcRacuna")
    private List<Transakcije> transakcijeList1;

    public Racuni() {
    }

    public Racuni(Integer iDRacuna) {
        this.iDRacuna = iDRacuna;
    }

    public Racuni(Integer iDRacuna, int iDMestaOtvaranja, long stanje, long dozvoljenMinus, Character status, Date datumVremeOtvaranja, int brojTransakcija) {
        this.iDRacuna = iDRacuna;
        this.iDMestaOtvaranja = iDMestaOtvaranja;
        this.stanje = stanje;
        this.dozvoljenMinus = dozvoljenMinus;
        this.status = status;
        this.datumVremeOtvaranja = datumVremeOtvaranja;
        this.brojTransakcija = brojTransakcija;
    }

    public Integer getIDRacuna() {
        return iDRacuna;
    }

    public void setIDRacuna(Integer iDRacuna) {
        this.iDRacuna = iDRacuna;
    }

    public int getIDMestaOtvaranja() {
        return iDMestaOtvaranja;
    }

    public void setIDMestaOtvaranja(int iDMestaOtvaranja) {
        this.iDMestaOtvaranja = iDMestaOtvaranja;
    }

    public long getStanje() {
        return stanje;
    }

    public void setStanje(long stanje) {
        this.stanje = stanje;
    }

    public long getDozvoljenMinus() {
        return dozvoljenMinus;
    }

    public void setDozvoljenMinus(long dozvoljenMinus) {
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

    public int getBrojTransakcija() {
        return brojTransakcija;
    }

    public void setBrojTransakcija(int brojTransakcija) {
        this.brojTransakcija = brojTransakcija;
    }

    public Komitenti getIDKomitenta() {
        return iDKomitenta;
    }

    public void setIDKomitenta(Komitenti iDKomitenta) {
        this.iDKomitenta = iDKomitenta;
    }

    @XmlTransient
    public List<Transakcije> getTransakcijeList() {
        return transakcijeList;
    }

    public void setTransakcijeList(List<Transakcije> transakcijeList) {
        this.transakcijeList = transakcijeList;
    }

    @XmlTransient
    public List<Transakcije> getTransakcijeList1() {
        return transakcijeList1;
    }

    public void setTransakcijeList1(List<Transakcije> transakcijeList1) {
        this.transakcijeList1 = transakcijeList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDRacuna != null ? iDRacuna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Racuni)) {
            return false;
        }
        Racuni other = (Racuni) object;
        if ((this.iDRacuna == null && other.iDRacuna != null) || (this.iDRacuna != null && !this.iDRacuna.equals(other.iDRacuna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entityClasses.Racuni[ iDRacuna=" + iDRacuna + " ]";
    }
    
}
