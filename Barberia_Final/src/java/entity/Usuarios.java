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
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u")
    , @NamedQuery(name = "Usuarios.findByNumIdentificacion", query = "SELECT u FROM Usuarios u WHERE u.numIdentificacion = :numIdentificacion")
    , @NamedQuery(name = "Usuarios.findByNombresyapellido", query = "SELECT u FROM Usuarios u WHERE u.nombresyapellido = :nombresyapellido")
    , @NamedQuery(name = "Usuarios.findByTelefono", query = "SELECT u FROM Usuarios u WHERE u.telefono = :telefono")
    , @NamedQuery(name = "Usuarios.findByFechadenacimiento", query = "SELECT u FROM Usuarios u WHERE u.fechadenacimiento = :fechadenacimiento")
    , @NamedQuery(name = "Usuarios.findByDireccion", query = "SELECT u FROM Usuarios u WHERE u.direccion = :direccion")
    , @NamedQuery(name = "Usuarios.findByGenero", query = "SELECT u FROM Usuarios u WHERE u.genero = :genero")
    , @NamedQuery(name = "Usuarios.findByEmail", query = "SELECT u FROM Usuarios u WHERE u.email = :email")
    , @NamedQuery(name = "Usuarios.findByContrase\u00f1a", query = "SELECT u FROM Usuarios u WHERE u.contrase\u00f1a = :contrase\u00f1a")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Num_Identificacion")
    private Integer numIdentificacion;
    @Size(max = 50)
    @Column(name = "Nombres_y_apellido")
    private String nombresyapellido;
    @Column(name = "Telefono")
    private Integer telefono;
    @Column(name = "Fecha_de_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechadenacimiento;
    @Size(max = 30)
    @Column(name = "Direccion")
    private String direccion;
    @Size(max = 20)
    @Column(name = "Genero")
    private String genero;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 20)
    @Column(name = "Email")
    private String email;
    @Column(name = "Contrase\u00f1a")
    private Integer contraseña;
    @OneToMany(mappedBy = "numIdentificacion")
    private List<Empleados> empleadosList;
    @JoinColumn(name = "Cod_rol", referencedColumnName = "Cod_rol")
    @ManyToOne
    private Roles codrol;
    @OneToMany(mappedBy = "numIdentificacion")
    private List<Cliente> clienteList;

    public Usuarios() {
    }

    public Usuarios(Integer numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    public Integer getNumIdentificacion() {
        return numIdentificacion;
    }

    public void setNumIdentificacion(Integer numIdentificacion) {
        this.numIdentificacion = numIdentificacion;
    }

    public String getNombresyapellido() {
        return nombresyapellido;
    }

    public void setNombresyapellido(String nombresyapellido) {
        this.nombresyapellido = nombresyapellido;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public Date getFechadenacimiento() {
        return fechadenacimiento;
    }

    public void setFechadenacimiento(Date fechadenacimiento) {
        this.fechadenacimiento = fechadenacimiento;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getContraseña() {
        return contraseña;
    }

    public void setContraseña(Integer contraseña) {
        this.contraseña = contraseña;
    }

    @XmlTransient
    public List<Empleados> getEmpleadosList() {
        return empleadosList;
    }

    public void setEmpleadosList(List<Empleados> empleadosList) {
        this.empleadosList = empleadosList;
    }

    public Roles getCodrol() {
        return codrol;
    }

    public void setCodrol(Roles codrol) {
        this.codrol = codrol;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numIdentificacion != null ? numIdentificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.numIdentificacion == null && other.numIdentificacion != null) || (this.numIdentificacion != null && !this.numIdentificacion.equals(other.numIdentificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Usuarios[ numIdentificacion=" + numIdentificacion + " ]";
    }
    
}
