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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author soporte
 */
@Entity
@Table(name = "pqrs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pqrs.findAll", query = "SELECT p FROM Pqrs p")
    , @NamedQuery(name = "Pqrs.findByIdPqrs", query = "SELECT p FROM Pqrs p WHERE p.idPqrs = :idPqrs")
    , @NamedQuery(name = "Pqrs.findByNombrecliente", query = "SELECT p FROM Pqrs p WHERE p.nombrecliente = :nombrecliente")
    , @NamedQuery(name = "Pqrs.findByFecha", query = "SELECT p FROM Pqrs p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Pqrs.findByDescripcion", query = "SELECT p FROM Pqrs p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Pqrs.findByEstado", query = "SELECT p FROM Pqrs p WHERE p.estado = :estado")
    , @NamedQuery(name = "Pqrs.findByNivelservicio", query = "SELECT p FROM Pqrs p WHERE p.nivelservicio = :nivelservicio")
    , @NamedQuery(name = "Pqrs.findByTipo", query = "SELECT p FROM Pqrs p WHERE p.tipo = :tipo")
    , @NamedQuery(name = "Pqrs.findByEstilista", query = "SELECT p FROM Pqrs p WHERE p.estilista = :estilista")})
public class Pqrs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Pqrs")
    private String idPqrs;
    @Size(max = 20)
    @Column(name = "Nombre_cliente")
    private String nombrecliente;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 200)
    @Column(name = "Descripcion")
    private String descripcion;
    @Size(max = 20)
    @Column(name = "Estado")
    private String estado;
    @Size(max = 20)
    @Column(name = "Nivel_servicio")
    private String nivelservicio;
    @Size(max = 20)
    @Column(name = "Tipo")
    private String tipo;
    @Size(max = 20)
    @Column(name = "Estilista")
    private String estilista;
    @JoinColumn(name = "Id_tipo_Servicio", referencedColumnName = "Id_tipo_Servicio")
    @ManyToOne
    private TipoServicio idtipoServicio;
    @JoinColumn(name = "Id_cliente", referencedColumnName = "Id_cliente")
    @ManyToOne
    private Cliente idcliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPqrs")
    private List<Respuesta> respuestaList;

    public Pqrs() {
    }

    public Pqrs(String idPqrs) {
        this.idPqrs = idPqrs;
    }

    public String getIdPqrs() {
        return idPqrs;
    }

    public void setIdPqrs(String idPqrs) {
        this.idPqrs = idPqrs;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNivelservicio() {
        return nivelservicio;
    }

    public void setNivelservicio(String nivelservicio) {
        this.nivelservicio = nivelservicio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstilista() {
        return estilista;
    }

    public void setEstilista(String estilista) {
        this.estilista = estilista;
    }

    public TipoServicio getIdtipoServicio() {
        return idtipoServicio;
    }

    public void setIdtipoServicio(TipoServicio idtipoServicio) {
        this.idtipoServicio = idtipoServicio;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    @XmlTransient
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPqrs != null ? idPqrs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pqrs)) {
            return false;
        }
        Pqrs other = (Pqrs) object;
        if ((this.idPqrs == null && other.idPqrs != null) || (this.idPqrs != null && !this.idPqrs.equals(other.idPqrs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Pqrs[ idPqrs=" + idPqrs + " ]";
    }
    
}
