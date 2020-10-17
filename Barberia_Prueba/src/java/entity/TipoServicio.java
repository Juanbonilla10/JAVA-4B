/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "tipo_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoServicio.findAll", query = "SELECT t FROM TipoServicio t")
    , @NamedQuery(name = "TipoServicio.findByIdtipoServicio", query = "SELECT t FROM TipoServicio t WHERE t.idtipoServicio = :idtipoServicio")
    , @NamedQuery(name = "TipoServicio.findByNombre", query = "SELECT t FROM TipoServicio t WHERE t.nombre = :nombre")
    , @NamedQuery(name = "TipoServicio.findByDescripcion", query = "SELECT t FROM TipoServicio t WHERE t.descripcion = :descripcion")
    , @NamedQuery(name = "TipoServicio.findByValordeServicio", query = "SELECT t FROM TipoServicio t WHERE t.valordeServicio = :valordeServicio")})
public class TipoServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_tipo_Servicio")
    private String idtipoServicio;
    @Size(max = 50)
    @Column(name = "Nombre")
    private String nombre;
    @Size(max = 50)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Valor_de_Servicio")
    private Integer valordeServicio;
    @OneToMany(mappedBy = "idtipoServicio")
    private List<PagoServicio> pagoServicioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtipoServicio")
    private List<Servicios> serviciosList;
    @OneToMany(mappedBy = "idtipoServicio")
    private List<Pqrs> pqrsList;

    public TipoServicio() {
    }

    public TipoServicio(String idtipoServicio) {
        this.idtipoServicio = idtipoServicio;
    }

    public String getIdtipoServicio() {
        return idtipoServicio;
    }

    public void setIdtipoServicio(String idtipoServicio) {
        this.idtipoServicio = idtipoServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getValordeServicio() {
        return valordeServicio;
    }

    public void setValordeServicio(Integer valordeServicio) {
        this.valordeServicio = valordeServicio;
    }

    @XmlTransient
    public List<PagoServicio> getPagoServicioList() {
        return pagoServicioList;
    }

    public void setPagoServicioList(List<PagoServicio> pagoServicioList) {
        this.pagoServicioList = pagoServicioList;
    }

    @XmlTransient
    public List<Servicios> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(List<Servicios> serviciosList) {
        this.serviciosList = serviciosList;
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
        hash += (idtipoServicio != null ? idtipoServicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoServicio)) {
            return false;
        }
        TipoServicio other = (TipoServicio) object;
        if ((this.idtipoServicio == null && other.idtipoServicio != null) || (this.idtipoServicio != null && !this.idtipoServicio.equals(other.idtipoServicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TipoServicio[ idtipoServicio=" + idtipoServicio + " ]";
    }
    
}
