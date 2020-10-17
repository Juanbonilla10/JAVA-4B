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
@Table(name = "inventarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Inventarios.findAll", query = "SELECT i FROM Inventarios i")
    , @NamedQuery(name = "Inventarios.findByIdInventario", query = "SELECT i FROM Inventarios i WHERE i.idInventario = :idInventario")
    , @NamedQuery(name = "Inventarios.findByDescripcion", query = "SELECT i FROM Inventarios i WHERE i.descripcion = :descripcion")
    , @NamedQuery(name = "Inventarios.findByCantidad", query = "SELECT i FROM Inventarios i WHERE i.cantidad = :cantidad")
    , @NamedQuery(name = "Inventarios.findByPrecio", query = "SELECT i FROM Inventarios i WHERE i.precio = :precio")
    , @NamedQuery(name = "Inventarios.findByEstado", query = "SELECT i FROM Inventarios i WHERE i.estado = :estado")})
public class Inventarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Inventario")
    private String idInventario;
    @Size(max = 20)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Cantidad")
    private Integer cantidad;
    @Column(name = "Precio")
    private Integer precio;
    @Size(max = 20)
    @Column(name = "Estado")
    private String estado;
    @OneToMany(mappedBy = "idInventario")
    private List<Venta> ventaList;
    @JoinColumn(name = "Nit", referencedColumnName = "Nit")
    @ManyToOne
    private Proveedor nit;
    @JoinColumn(name = "Referencia_Producto", referencedColumnName = "Referencia_Producto")
    @ManyToOne
    private CrearPro referenciaProducto;
    @JoinColumn(name = "Id_ingreso", referencedColumnName = "Id_ingreso")
    @ManyToOne
    private IngresoProd idingreso;

    public Inventarios() {
    }

    public Inventarios(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(String idInventario) {
        this.idInventario = idInventario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Venta> getVentaList() {
        return ventaList;
    }

    public void setVentaList(List<Venta> ventaList) {
        this.ventaList = ventaList;
    }

    public Proveedor getNit() {
        return nit;
    }

    public void setNit(Proveedor nit) {
        this.nit = nit;
    }

    public CrearPro getReferenciaProducto() {
        return referenciaProducto;
    }

    public void setReferenciaProducto(CrearPro referenciaProducto) {
        this.referenciaProducto = referenciaProducto;
    }

    public IngresoProd getIdingreso() {
        return idingreso;
    }

    public void setIdingreso(IngresoProd idingreso) {
        this.idingreso = idingreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInventario != null ? idInventario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventarios)) {
            return false;
        }
        Inventarios other = (Inventarios) object;
        if ((this.idInventario == null && other.idInventario != null) || (this.idInventario != null && !this.idInventario.equals(other.idInventario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Inventarios[ idInventario=" + idInventario + " ]";
    }
    
}
