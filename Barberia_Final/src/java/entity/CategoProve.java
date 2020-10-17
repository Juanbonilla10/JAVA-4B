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
@Table(name = "catego_prove")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoProve.findAll", query = "SELECT c FROM CategoProve c")
    , @NamedQuery(name = "CategoProve.findByIdCateprove", query = "SELECT c FROM CategoProve c WHERE c.idCateprove = :idCateprove")
    , @NamedQuery(name = "CategoProve.findByTiposdeproductos", query = "SELECT c FROM CategoProve c WHERE c.tiposdeproductos = :tiposdeproductos")
    , @NamedQuery(name = "CategoProve.findByUnidaddeempaque", query = "SELECT c FROM CategoProve c WHERE c.unidaddeempaque = :unidaddeempaque")
    , @NamedQuery(name = "CategoProve.findByTipodeempaque", query = "SELECT c FROM CategoProve c WHERE c.tipodeempaque = :tipodeempaque")
    , @NamedQuery(name = "CategoProve.findByDiasparaentrega", query = "SELECT c FROM CategoProve c WHERE c.diasparaentrega = :diasparaentrega")})
public class CategoProve implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Cate_prove")
    private String idCateprove;
    @Size(max = 20)
    @Column(name = "Tipos_de_productos")
    private String tiposdeproductos;
    @Column(name = "Unidad_de_empaque")
    private Integer unidaddeempaque;
    @Size(max = 10)
    @Column(name = "Tipo_de_empaque")
    private String tipodeempaque;
    @Column(name = "Dias_para_entrega")
    private Integer diasparaentrega;
    @OneToMany(mappedBy = "idCateprove")
    private List<Proveedor> proveedorList;

    public CategoProve() {
    }

    public CategoProve(String idCateprove) {
        this.idCateprove = idCateprove;
    }

    public String getIdCateprove() {
        return idCateprove;
    }

    public void setIdCateprove(String idCateprove) {
        this.idCateprove = idCateprove;
    }

    public String getTiposdeproductos() {
        return tiposdeproductos;
    }

    public void setTiposdeproductos(String tiposdeproductos) {
        this.tiposdeproductos = tiposdeproductos;
    }

    public Integer getUnidaddeempaque() {
        return unidaddeempaque;
    }

    public void setUnidaddeempaque(Integer unidaddeempaque) {
        this.unidaddeempaque = unidaddeempaque;
    }

    public String getTipodeempaque() {
        return tipodeempaque;
    }

    public void setTipodeempaque(String tipodeempaque) {
        this.tipodeempaque = tipodeempaque;
    }

    public Integer getDiasparaentrega() {
        return diasparaentrega;
    }

    public void setDiasparaentrega(Integer diasparaentrega) {
        this.diasparaentrega = diasparaentrega;
    }

    @XmlTransient
    public List<Proveedor> getProveedorList() {
        return proveedorList;
    }

    public void setProveedorList(List<Proveedor> proveedorList) {
        this.proveedorList = proveedorList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCateprove != null ? idCateprove.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoProve)) {
            return false;
        }
        CategoProve other = (CategoProve) object;
        if ((this.idCateprove == null && other.idCateprove != null) || (this.idCateprove != null && !this.idCateprove.equals(other.idCateprove))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CategoProve[ idCateprove=" + idCateprove + " ]";
    }
    
}
