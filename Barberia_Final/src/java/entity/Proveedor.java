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
@Table(name = "proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p")
    , @NamedQuery(name = "Proveedor.findByNit", query = "SELECT p FROM Proveedor p WHERE p.nit = :nit")
    , @NamedQuery(name = "Proveedor.findByNombreProveedor", query = "SELECT p FROM Proveedor p WHERE p.nombreProveedor = :nombreProveedor")
    , @NamedQuery(name = "Proveedor.findByRazonsocial", query = "SELECT p FROM Proveedor p WHERE p.razonsocial = :razonsocial")
    , @NamedQuery(name = "Proveedor.findByFecha", query = "SELECT p FROM Proveedor p WHERE p.fecha = :fecha")
    , @NamedQuery(name = "Proveedor.findByNumcelular", query = "SELECT p FROM Proveedor p WHERE p.numcelular = :numcelular")
    , @NamedQuery(name = "Proveedor.findByNumerotelefono", query = "SELECT p FROM Proveedor p WHERE p.numerotelefono = :numerotelefono")
    , @NamedQuery(name = "Proveedor.findByDiaspagoCredito", query = "SELECT p FROM Proveedor p WHERE p.diaspagoCredito = :diaspagoCredito")
    , @NamedQuery(name = "Proveedor.findByCiudad", query = "SELECT p FROM Proveedor p WHERE p.ciudad = :ciudad")
    , @NamedQuery(name = "Proveedor.findByDireccion", query = "SELECT p FROM Proveedor p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Proveedor.findByEmail", query = "SELECT p FROM Proveedor p WHERE p.email = :email")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Nit")
    private Integer nit;
    @Size(max = 50)
    @Column(name = "Nombre_Proveedor")
    private String nombreProveedor;
    @Size(max = 50)
    @Column(name = "Razon_social")
    private String razonsocial;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "Num_celular")
    private Integer numcelular;
    @Column(name = "Numero_telefono")
    private Integer numerotelefono;
    @Column(name = "Dias_pago_Credito")
    private Integer diaspagoCredito;
    @Size(max = 50)
    @Column(name = "Ciudad")
    private String ciudad;
    @Size(max = 50)
    @Column(name = "Direccion")
    private String direccion;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "Email")
    private String email;
    @OneToMany(mappedBy = "nit")
    private List<Inventarios> inventariosList;
    @JoinColumn(name = "Id_Cate_prove", referencedColumnName = "Id_Cate_prove")
    @ManyToOne
    private CategoProve idCateprove;

    public Proveedor() {
    }

    public Proveedor(Integer nit) {
        this.nit = nit;
    }

    public Integer getNit() {
        return nit;
    }

    public void setNit(Integer nit) {
        this.nit = nit;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getNumcelular() {
        return numcelular;
    }

    public void setNumcelular(Integer numcelular) {
        this.numcelular = numcelular;
    }

    public Integer getNumerotelefono() {
        return numerotelefono;
    }

    public void setNumerotelefono(Integer numerotelefono) {
        this.numerotelefono = numerotelefono;
    }

    public Integer getDiaspagoCredito() {
        return diaspagoCredito;
    }

    public void setDiaspagoCredito(Integer diaspagoCredito) {
        this.diaspagoCredito = diaspagoCredito;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Inventarios> getInventariosList() {
        return inventariosList;
    }

    public void setInventariosList(List<Inventarios> inventariosList) {
        this.inventariosList = inventariosList;
    }

    public CategoProve getIdCateprove() {
        return idCateprove;
    }

    public void setIdCateprove(CategoProve idCateprove) {
        this.idCateprove = idCateprove;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Proveedor[ nit=" + nit + " ]";
    }
    
}
