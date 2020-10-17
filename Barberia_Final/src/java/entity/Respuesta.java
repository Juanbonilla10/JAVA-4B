/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author soporte
 */
@Entity
@Table(name = "respuesta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Respuesta.findAll", query = "SELECT r FROM Respuesta r")
    , @NamedQuery(name = "Respuesta.findByIdrespuesta", query = "SELECT r FROM Respuesta r WHERE r.idrespuesta = :idrespuesta")
    , @NamedQuery(name = "Respuesta.findByFecha", query = "SELECT r FROM Respuesta r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Respuesta.findByEstado", query = "SELECT r FROM Respuesta r WHERE r.estado = :estado")
    , @NamedQuery(name = "Respuesta.findByEnvioderespues", query = "SELECT r FROM Respuesta r WHERE r.envioderespues = :envioderespues")})
public class Respuesta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_respuesta")
    private String idrespuesta;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 20)
    @Column(name = "Estado")
    private String estado;
    @Size(max = 2)
    @Column(name = "Envio_de_respues")
    private String envioderespues;
    @JoinColumn(name = "Id_Pqrs", referencedColumnName = "Id_Pqrs")
    @ManyToOne(optional = false)
    private Pqrs idPqrs;
    @JoinColumn(name = "Id_empleados", referencedColumnName = "Id_Empleados")
    @ManyToOne
    private Empleados idempleados;

    public Respuesta() {
    }

    public Respuesta(String idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public String getIdrespuesta() {
        return idrespuesta;
    }

    public void setIdrespuesta(String idrespuesta) {
        this.idrespuesta = idrespuesta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEnvioderespues() {
        return envioderespues;
    }

    public void setEnvioderespues(String envioderespues) {
        this.envioderespues = envioderespues;
    }

    public Pqrs getIdPqrs() {
        return idPqrs;
    }

    public void setIdPqrs(Pqrs idPqrs) {
        this.idPqrs = idPqrs;
    }

    public Empleados getIdempleados() {
        return idempleados;
    }

    public void setIdempleados(Empleados idempleados) {
        this.idempleados = idempleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrespuesta != null ? idrespuesta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Respuesta)) {
            return false;
        }
        Respuesta other = (Respuesta) object;
        if ((this.idrespuesta == null && other.idrespuesta != null) || (this.idrespuesta != null && !this.idrespuesta.equals(other.idrespuesta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Respuesta[ idrespuesta=" + idrespuesta + " ]";
    }
    
}
