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
@Table(name = "empleados")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")
    , @NamedQuery(name = "Empleados.findByIdEmpleados", query = "SELECT e FROM Empleados e WHERE e.idEmpleados = :idEmpleados")
    , @NamedQuery(name = "Empleados.findByCargo", query = "SELECT e FROM Empleados e WHERE e.cargo = :cargo")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Empleados")
    private String idEmpleados;
    @Size(max = 20)
    @Column(name = "Cargo")
    private String cargo;
    @JoinColumn(name = "Num_Identificacion", referencedColumnName = "Num_Identificacion")
    @ManyToOne
    private Usuarios numIdentificacion;
    @OneToMany(mappedBy = "idEmpleados")
    private List<Cronograma> cronogramaList;
    @OneToMany(mappedBy = "idempleados")
    private List<Respuesta> respuestaList;
    @OneToMany(mappedBy = "idEmpleados")
    private List<CrearPro> crearProList;

    public Empleados() {
    }

    public Empleados(String idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public String getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(String idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
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
    public List<Respuesta> getRespuestaList() {
        return respuestaList;
    }

    public void setRespuestaList(List<Respuesta> respuestaList) {
        this.respuestaList = respuestaList;
    }

    @XmlTransient
    public List<CrearPro> getCrearProList() {
        return crearProList;
    }

    public void setCrearProList(List<CrearPro> crearProList) {
        this.crearProList = crearProList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpleados != null ? idEmpleados.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.idEmpleados == null && other.idEmpleados != null) || (this.idEmpleados != null && !this.idEmpleados.equals(other.idEmpleados))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Empleados[ idEmpleados=" + idEmpleados + " ]";
    }
    
}
