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
@Table(name = "categoria_cronograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoriaCronograma.findAll", query = "SELECT c FROM CategoriaCronograma c")
    , @NamedQuery(name = "CategoriaCronograma.findByIdcatecro", query = "SELECT c FROM CategoriaCronograma c WHERE c.idcatecro = :idcatecro")
    , @NamedQuery(name = "CategoriaCronograma.findByNombre", query = "SELECT c FROM CategoriaCronograma c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "CategoriaCronograma.findByDescripcion", query = "SELECT c FROM CategoriaCronograma c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CategoriaCronograma.findByFechaCreacion", query = "SELECT c FROM CategoriaCronograma c WHERE c.fechaCreacion = :fechaCreacion")})
public class CategoriaCronograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_cate_cro")
    private String idcatecro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha_Creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @OneToMany(mappedBy = "idcatecro")
    private List<Cronograma> cronogramaList;

    public CategoriaCronograma() {
    }

    public CategoriaCronograma(String idcatecro) {
        this.idcatecro = idcatecro;
    }

    public CategoriaCronograma(String idcatecro, String nombre, String descripcion, Date fechaCreacion) {
        this.idcatecro = idcatecro;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
    }

    public String getIdcatecro() {
        return idcatecro;
    }

    public void setIdcatecro(String idcatecro) {
        this.idcatecro = idcatecro;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @XmlTransient
    public List<Cronograma> getCronogramaList() {
        return cronogramaList;
    }

    public void setCronogramaList(List<Cronograma> cronogramaList) {
        this.cronogramaList = cronogramaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcatecro != null ? idcatecro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoriaCronograma)) {
            return false;
        }
        CategoriaCronograma other = (CategoriaCronograma) object;
        if ((this.idcatecro == null && other.idcatecro != null) || (this.idcatecro != null && !this.idcatecro.equals(other.idcatecro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CategoriaCronograma[ idcatecro=" + idcatecro + " ]";
    }
    
}
