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
@Table(name = "ven_cate")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VenCate.findAll", query = "SELECT v FROM VenCate v")
    , @NamedQuery(name = "VenCate.findByIdVenCate", query = "SELECT v FROM VenCate v WHERE v.idVenCate = :idVenCate")
    , @NamedQuery(name = "VenCate.findByDescripcion", query = "SELECT v FROM VenCate v WHERE v.descripcion = :descripcion")
    , @NamedQuery(name = "VenCate.findByTipoCategoria", query = "SELECT v FROM VenCate v WHERE v.tipoCategoria = :tipoCategoria")
    , @NamedQuery(name = "VenCate.findByFecha", query = "SELECT v FROM VenCate v WHERE v.fecha = :fecha")})
public class VenCate implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Ven_Cate")
    private String idVenCate;
    @Size(max = 50)
    @Column(name = "Descripcion")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "Tipo_Categoria")
    private String tipoCategoria;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @OneToMany(mappedBy = "idVenCate")
    private List<Venta> ventaList;

    public VenCate() {
    }

    public VenCate(String idVenCate) {
        this.idVenCate = idVenCate;
    }

    public String getIdVenCate() {
        return idVenCate;
    }

    public void setIdVenCate(String idVenCate) {
        this.idVenCate = idVenCate;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(String tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVenCate != null ? idVenCate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VenCate)) {
            return false;
        }
        VenCate other = (VenCate) object;
        if ((this.idVenCate == null && other.idVenCate != null) || (this.idVenCate != null && !this.idVenCate.equals(other.idVenCate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.VenCate[ idVenCate=" + idVenCate + " ]";
    }
    
}
