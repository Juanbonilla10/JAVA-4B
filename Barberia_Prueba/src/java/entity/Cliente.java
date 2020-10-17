/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author soporte
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findByIdcliente", query = "SELECT c FROM Cliente c WHERE c.idcliente = :idcliente")
    , @NamedQuery(name = "Cliente.findByTipodecliente", query = "SELECT c FROM Cliente c WHERE c.tipodecliente = :tipodecliente")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_cliente")
    private String idcliente;
    @Size(max = 20)
    @Column(name = "Tipo_de_cliente")
    private String tipodecliente;
    @JoinColumn(name = "Num_Identificacion", referencedColumnName = "Num_Identificacion")
    @ManyToOne
    private Usuarios numIdentificacion;
    @OneToMany(mappedBy = "idCliente")
    private List<Cronograma> cronogramaList;
    @OneToMany(mappedBy = "idcliente")
    private List<Pqrs> pqrsList;

    public Cliente() {
    }

    public Cliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getTipodecliente() {
        return tipodecliente;
    }

    public void setTipodecliente(String tipodecliente) {
        this.tipodecliente = tipodecliente;
    }

    public Usuarios getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(Usuarios numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    @XmlTransient
    public List<Cronograma> getCronogramaList() {
        return cronogramaList;
    }

    public void setCronogramaList(List<Cronograma> cronogramaList) {
        this.cronogramaList = cronogramaList;
    }

    @XmlTransient
    public List<Pqrs> getPqrsList() {
        return pqrsList;
    }

    public void setPqrsList(List<Pqrs> pqrsList) {
        this.pqrsList = pqrsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcliente != null ? idcliente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.idcliente == null && other.idcliente != null) || (this.idcliente != null && !this.idcliente.equals(other.idcliente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cliente[ idcliente=" + idcliente + " ]";
    }
    
}
