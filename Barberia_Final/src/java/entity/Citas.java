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
@Table(name = "citas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Citas.findAll", query = "SELECT c FROM Citas c")
    , @NamedQuery(name = "Citas.findByIdCitas", query = "SELECT c FROM Citas c WHERE c.idCitas = :idCitas")
    , @NamedQuery(name = "Citas.findByNombreclientes", query = "SELECT c FROM Citas c WHERE c.nombreclientes = :nombreclientes")
    , @NamedQuery(name = "Citas.findByDocumento", query = "SELECT c FROM Citas c WHERE c.documento = :documento")
    , @NamedQuery(name = "Citas.findByNumerodetelefono", query = "SELECT c FROM Citas c WHERE c.numerodetelefono = :numerodetelefono")
    , @NamedQuery(name = "Citas.findByFecha", query = "SELECT c FROM Citas c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Citas.findByHora", query = "SELECT c FROM Citas c WHERE c.hora = :hora")
    , @NamedQuery(name = "Citas.findByTipodepago", query = "SELECT c FROM Citas c WHERE c.tipodepago = :tipodepago")})
public class Citas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Citas")
    private String idCitas;
    @Size(max = 50)
    @Column(name = "Nombre_clientes")
    private String nombreclientes;
    @Column(name = "Documento")
    private Integer documento;
    @Column(name = "Numero_de_telefono")
    private Integer numerodetelefono;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "Hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Size(max = 20)
    @Column(name = "Tipo_de_pago")
    private String tipodepago;
    @JoinColumn(name = "Id_cronograma", referencedColumnName = "Id_Cronograma")
    @ManyToOne
    private Cronograma idcronograma;
    @OneToMany(mappedBy = "idcitas")
    private List<Servicios> serviciosList;

    public Citas() {
    }

    public Citas(String idCitas) {
        this.idCitas = idCitas;
    }

    public String getIdCitas() {
        return idCitas;
    }

    public void setIdCitas(String idCitas) {
        this.idCitas = idCitas;
    }

    public String getNombreclientes() {
        return nombreclientes;
    }

    public void setNombreclientes(String nombreclientes) {
        this.nombreclientes = nombreclientes;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getNumerodetelefono() {
        return numerodetelefono;
    }

    public void setNumerodetelefono(Integer numerodetelefono) {
        this.numerodetelefono = numerodetelefono;
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

    public String getTipodepago() {
        return tipodepago;
    }

    public void setTipodepago(String tipodepago) {
        this.tipodepago = tipodepago;
    }

    public Cronograma getIdcronograma() {
        return idcronograma;
    }

    public void setIdcronograma(Cronograma idcronograma) {
        this.idcronograma = idcronograma;
    }

    @XmlTransient
    public List<Servicios> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(List<Servicios> serviciosList) {
        this.serviciosList = serviciosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCitas != null ? idCitas.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Citas)) {
            return false;
        }
        Citas other = (Citas) object;
        if ((this.idCitas == null && other.idCitas != null) || (this.idCitas != null && !this.idCitas.equals(other.idCitas))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Citas[ idCitas=" + idCitas + " ]";
    }
    
}
