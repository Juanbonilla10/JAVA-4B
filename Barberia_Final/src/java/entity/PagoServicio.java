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
@Table(name = "pago_servicio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PagoServicio.findAll", query = "SELECT p FROM PagoServicio p")
    , @NamedQuery(name = "PagoServicio.findByCodigodelpago", query = "SELECT p FROM PagoServicio p WHERE p.codigodelpago = :codigodelpago")
    , @NamedQuery(name = "PagoServicio.findByCodigoCliente", query = "SELECT p FROM PagoServicio p WHERE p.codigoCliente = :codigoCliente")
    , @NamedQuery(name = "PagoServicio.findByNombreBarbero", query = "SELECT p FROM PagoServicio p WHERE p.nombreBarbero = :nombreBarbero")
    , @NamedQuery(name = "PagoServicio.findByFechaServicio", query = "SELECT p FROM PagoServicio p WHERE p.fechaServicio = :fechaServicio")
    , @NamedQuery(name = "PagoServicio.findByHoraservicio", query = "SELECT p FROM PagoServicio p WHERE p.horaservicio = :horaservicio")
    , @NamedQuery(name = "PagoServicio.findByNombreServicio", query = "SELECT p FROM PagoServicio p WHERE p.nombreServicio = :nombreServicio")
    , @NamedQuery(name = "PagoServicio.findByValorservicio", query = "SELECT p FROM PagoServicio p WHERE p.valorservicio = :valorservicio")
    , @NamedQuery(name = "PagoServicio.findByFechapagoservicio", query = "SELECT p FROM PagoServicio p WHERE p.fechapagoservicio = :fechapagoservicio")
    , @NamedQuery(name = "PagoServicio.findBySaldoTotal", query = "SELECT p FROM PagoServicio p WHERE p.saldoTotal = :saldoTotal")
    , @NamedQuery(name = "PagoServicio.findByAbono", query = "SELECT p FROM PagoServicio p WHERE p.abono = :abono")
    , @NamedQuery(name = "PagoServicio.findBySaldoPendiente", query = "SELECT p FROM PagoServicio p WHERE p.saldoPendiente = :saldoPendiente")})
public class PagoServicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Codigo_del_pago")
    private String codigodelpago;
    @Size(max = 11)
    @Column(name = "Codigo_Cliente")
    private String codigoCliente;
    @Size(max = 20)
    @Column(name = "Nombre_Barbero")
    private String nombreBarbero;
    @Column(name = "Fecha_Servicio")
    @Temporal(TemporalType.DATE)
    private Date fechaServicio;
    @Column(name = "Hora_servicio")
    @Temporal(TemporalType.TIME)
    private Date horaservicio;
    @Size(max = 30)
    @Column(name = "Nombre_Servicio")
    private String nombreServicio;
    @Column(name = "Valor_servicio")
    private Integer valorservicio;
    @Column(name = "Fecha_pago_servicio")
    @Temporal(TemporalType.DATE)
    private Date fechapagoservicio;
    @Column(name = "Saldo_Total")
    private Integer saldoTotal;
    @Column(name = "Abono")
    private Integer abono;
    @Column(name = "Saldo_Pendiente")
    private Integer saldoPendiente;
    @JoinColumn(name = "Id_tipo_Servicio", referencedColumnName = "Id_tipo_Servicio")
    @ManyToOne
    private TipoServicio idtipoServicio;

    public PagoServicio() {
    }

    public PagoServicio(String codigodelpago) {
        this.codigodelpago = codigodelpago;
    }

    public String getCodigodelpago() {
        return codigodelpago;
    }

    public void setCodigodelpago(String codigodelpago) {
        this.codigodelpago = codigodelpago;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getNombreBarbero() {
        return nombreBarbero;
    }

    public void setNombreBarbero(String nombreBarbero) {
        this.nombreBarbero = nombreBarbero;
    }

    public Date getFechaServicio() {
        return fechaServicio;
    }

    public void setFechaServicio(Date fechaServicio) {
        this.fechaServicio = fechaServicio;
    }

    public Date getHoraservicio() {
        return horaservicio;
    }

    public void setHoraservicio(Date horaservicio) {
        this.horaservicio = horaservicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public Integer getValorservicio() {
        return valorservicio;
    }

    public void setValorservicio(Integer valorservicio) {
        this.valorservicio = valorservicio;
    }

    public Date getFechapagoservicio() {
        return fechapagoservicio;
    }

    public void setFechapagoservicio(Date fechapagoservicio) {
        this.fechapagoservicio = fechapagoservicio;
    }

    public Integer getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(Integer saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public Integer getAbono() {
        return abono;
    }

    public void setAbono(Integer abono) {
        this.abono = abono;
    }

    public Integer getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(Integer saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public TipoServicio getIdtipoServicio() {
        return idtipoServicio;
    }

    public void setIdtipoServicio(TipoServicio idtipoServicio) {
        this.idtipoServicio = idtipoServicio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigodelpago != null ? codigodelpago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PagoServicio)) {
            return false;
        }
        PagoServicio other = (PagoServicio) object;
        if ((this.codigodelpago == null && other.codigodelpago != null) || (this.codigodelpago != null && !this.codigodelpago.equals(other.codigodelpago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PagoServicio[ codigodelpago=" + codigodelpago + " ]";
    }
    
}
