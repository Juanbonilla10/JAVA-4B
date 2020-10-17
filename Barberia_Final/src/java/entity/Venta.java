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
@Table(name = "venta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Venta.findAll", query = "SELECT v FROM Venta v")
    , @NamedQuery(name = "Venta.findByIdventa", query = "SELECT v FROM Venta v WHERE v.idventa = :idventa")
    , @NamedQuery(name = "Venta.findByReferencia", query = "SELECT v FROM Venta v WHERE v.referencia = :referencia")
    , @NamedQuery(name = "Venta.findByFecha", query = "SELECT v FROM Venta v WHERE v.fecha = :fecha")
    , @NamedQuery(name = "Venta.findByHora", query = "SELECT v FROM Venta v WHERE v.hora = :hora")
    , @NamedQuery(name = "Venta.findByDescripcion", query = "SELECT v FROM Venta v WHERE v.descripcion = :descripcion")
    , @NamedQuery(name = "Venta.findByCantidad", query = "SELECT v FROM Venta v WHERE v.cantidad = :cantidad")
    , @NamedQuery(name = "Venta.findByPrecio", query = "SELECT v FROM Venta v WHERE v.precio = :precio")
    , @NamedQuery(name = "Venta.findByIvaTotal", query = "SELECT v FROM Venta v WHERE v.ivaTotal = :ivaTotal")
    , @NamedQuery(name = "Venta.findByPreciototal", query = "SELECT v FROM Venta v WHERE v.preciototal = :preciototal")})
public class Venta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_venta")
    private String idventa;
    @Size(max = 11)
    @Column(name = "Referencia")
    private String referencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Size(max = 50)
    @Column(name = "Descripcion")
    private String descripcion;
    @Column(name = "Cantidad")
    private Integer cantidad;
    @Column(name = "Precio")
    private Integer precio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Iva_Total")
    private int ivaTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Precio_total")
    private int preciototal;
    @JoinColumn(name = "Id_Inventario", referencedColumnName = "Id_Inventario")
    @ManyToOne
    private Inventarios idInventario;
    @JoinColumn(name = "Id_Ven_Cate", referencedColumnName = "Id_Ven_Cate")
    @ManyToOne
    private VenCate idVenCate;

    public Venta() {
    }

    public Venta(String idventa) {
        this.idventa = idventa;
    }

    public Venta(String idventa, Date fecha, Date hora, int ivaTotal, int preciototal) {
        this.idventa = idventa;
        this.fecha = fecha;
        this.hora = hora;
        this.ivaTotal = ivaTotal;
        this.preciototal = preciototal;
    }

    public String getIdventa() {
        return idventa;
    }

    public void setIdventa(String idventa) {
        this.idventa = idventa;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
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

    public int getIvaTotal() {
        return ivaTotal;
    }

    public void setIvaTotal(int ivaTotal) {
        this.ivaTotal = ivaTotal;
    }

    public int getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(int preciototal) {
        this.preciototal = preciototal;
    }

    public Inventarios getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(Inventarios idInventario) {
        this.idInventario = idInventario;
    }

    public VenCate getIdVenCate() {
        return idVenCate;
    }

    public void setIdVenCate(VenCate idVenCate) {
        this.idVenCate = idVenCate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idventa != null ? idventa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venta)) {
            return false;
        }
        Venta other = (Venta) object;
        if ((this.idventa == null && other.idventa != null) || (this.idventa != null && !this.idventa.equals(other.idventa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Venta[ idventa=" + idventa + " ]";
    }
    
}
