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
@Table(name = "crear_pro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CrearPro.findAll", query = "SELECT c FROM CrearPro c")
    , @NamedQuery(name = "CrearPro.findByReferenciaProducto", query = "SELECT c FROM CrearPro c WHERE c.referenciaProducto = :referenciaProducto")
    , @NamedQuery(name = "CrearPro.findByDescripcion", query = "SELECT c FROM CrearPro c WHERE c.descripcion = :descripcion")
    , @NamedQuery(name = "CrearPro.findByCodigoDebarras", query = "SELECT c FROM CrearPro c WHERE c.codigoDebarras = :codigoDebarras")
    , @NamedQuery(name = "CrearPro.findByProveedor", query = "SELECT c FROM CrearPro c WHERE c.proveedor = :proveedor")
    , @NamedQuery(name = "CrearPro.findByPrecioproveedor", query = "SELECT c FROM CrearPro c WHERE c.precioproveedor = :precioproveedor")
    , @NamedQuery(name = "CrearPro.findByPreciopublico", query = "SELECT c FROM CrearPro c WHERE c.preciopublico = :preciopublico")
    , @NamedQuery(name = "CrearPro.findByFecha", query = "SELECT c FROM CrearPro c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "CrearPro.findByVentasinsumos", query = "SELECT c FROM CrearPro c WHERE c.ventasinsumos = :ventasinsumos")})
public class CrearPro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Referencia_Producto")
    private String referenciaProducto;
    @Size(max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Codigo_De_barras")
    private Integer codigoDebarras;
    @Size(max = 50)
    @Column(name = "Proveedor")
    private String proveedor;
    @Column(name = "Precio_proveedor")
    private Integer precioproveedor;
    @Column(name = "Precio_publico")
    private Integer preciopublico;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 10)
    @Column(name = "Ventas_insumos")
    private String ventasinsumos;
    @OneToMany(mappedBy = "referenciaProducto")
    private List<Inventarios> inventariosList;
    @JoinColumn(name = "Id_Empleados", referencedColumnName = "Id_Empleados")
    @ManyToOne
    private Empleados idEmpleados;

    public CrearPro() {
    }

    public CrearPro(String referenciaProducto) {
        this.referenciaProducto = referenciaProducto;
    }

    public String getReferenciaProducto() {
        return referenciaProducto;
    }

    public void setReferenciaProducto(String referenciaProducto) {
        this.referenciaProducto = referenciaProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigoDebarras() {
        return codigoDebarras;
    }

    public void setCodigoDebarras(Integer codigoDebarras) {
        this.codigoDebarras = codigoDebarras;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public Integer getPrecioproveedor() {
        return precioproveedor;
    }

    public void setPrecioproveedor(Integer precioproveedor) {
        this.precioproveedor = precioproveedor;
    }

    public Integer getPreciopublico() {
        return preciopublico;
    }

    public void setPreciopublico(Integer preciopublico) {
        this.preciopublico = preciopublico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getVentasinsumos() {
        return ventasinsumos;
    }

    public void setVentasinsumos(String ventasinsumos) {
        this.ventasinsumos = ventasinsumos;
    }

    @XmlTransient
    public List<Inventarios> getInventariosList() {
        return inventariosList;
    }

    public void setInventariosList(List<Inventarios> inventariosList) {
        this.inventariosList = inventariosList;
    }

    public Empleados getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Empleados idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (referenciaProducto != null ? referenciaProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CrearPro)) {
            return false;
        }
        CrearPro other = (CrearPro) object;
        if ((this.referenciaProducto == null && other.referenciaProducto != null) || (this.referenciaProducto != null && !this.referenciaProducto.equals(other.referenciaProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CrearPro[ referenciaProducto=" + referenciaProducto + " ]";
    }
    
}
