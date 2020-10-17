/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soporte
 */
@Entity
@Table(name = "ingreso_prod")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IngresoProd.findAll", query = "SELECT i FROM IngresoProd i")
    , @NamedQuery(name = "IngresoProd.findByIdingreso", query = "SELECT i FROM IngresoProd i WHERE i.idingreso = :idingreso")
    , @NamedQuery(name = "IngresoProd.findByProveedor", query = "SELECT i FROM IngresoProd i WHERE i.proveedor = :proveedor")
    , @NamedQuery(name = "IngresoProd.findByFecha", query = "SELECT i FROM IngresoProd i WHERE i.fecha = :fecha")
    , @NamedQuery(name = "IngresoProd.findByCantidad", query = "SELECT i FROM IngresoProd i WHERE i.cantidad = :cantidad")
    , @NamedQuery(name = "IngresoProd.findByCosto", query = "SELECT i FROM IngresoProd i WHERE i.costo = :costo")
    , @NamedQuery(name = "IngresoProd.findByCostototal", query = "SELECT i FROM IngresoProd i WHERE i.costototal = :costototal")})
public class IngresoProd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_ingreso")
    private String idingreso;
    @Size(max = 50)
    @Column(name = "Proveedor")
    private String proveedor;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "Costo")
    private Integer costo;
    @Column(name = "Costo_total")
    private Integer costototal;
    @OneToMany(mappedBy = "idingreso")
    private List<Inventarios> inventariosList;

    public IngresoProd() {
    }

    public IngresoProd(String idingreso) {
        this.idingreso = idingreso;
    }

    public String getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(String idingreso) {
        this.idingreso = idingreso;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public Integer getCostototal() {
        return costototal;
    }

    public void setCostototal(Integer costototal) {
        this.costototal = costototal;
    }

    @XmlTransient
    public List<Inventarios> getInventariosList() {
        return inventariosList;
    }

    public void setInventariosList(List<Inventarios> inventariosList) {
        this.inventariosList = inventariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idingreso != null ? idingreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IngresoProd)) {
            return false;
        }
        IngresoProd other = (IngresoProd) object;
        if ((this.idingreso == null && other.idingreso != null) || (this.idingreso != null && !this.idingreso.equals(other.idingreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.IngresoProd[ idingreso=" + idingreso + " ]";
    }
    
}
