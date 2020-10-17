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
@Table(name = "servicios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servicios.findAll", query = "SELECT s FROM Servicios s")
    , @NamedQuery(name = "Servicios.findByIdRegisSer", query = "SELECT s FROM Servicios s WHERE s.idRegisSer = :idRegisSer")
    , @NamedQuery(name = "Servicios.findByNombreServicio", query = "SELECT s FROM Servicios s WHERE s.nombreServicio = :nombreServicio")
    , @NamedQuery(name = "Servicios.findByValorServicio", query = "SELECT s FROM Servicios s WHERE s.valorServicio = :valorServicio")
    , @NamedQuery(name = "Servicios.findByCodigoServicio", query = "SELECT s FROM Servicios s WHERE s.codigoServicio = :codigoServicio")
    , @NamedQuery(name = "Servicios.findByAquienvadirigido", query = "SELECT s FROM Servicios s WHERE s.aquienvadirigido = :aquienvadirigido")
    , @NamedQuery(name = "Servicios.findByFechaCrea", query = "SELECT s FROM Servicios s WHERE s.fechaCrea = :fechaCrea")
    , @NamedQuery(name = "Servicios.findByHora", query = "SELECT s FROM Servicios s WHERE s.hora = :hora")})
public class Servicios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Regis_Ser")
    private String idRegisSer;
    @Size(max = 50)
    @Column(name = "Nombre_Servicio")
    private String nombreServicio;
    @Column(name = "Valor_Servicio")
    private Integer valorServicio;
    @Column(name = "Codigo_Servicio")
    private Integer codigoServicio;
    @Size(max = 20)
    @Column(name = "A_quien_va_dirigido")
    private String aquienvadirigido;
    @Column(name = "Fecha_Crea")
    @Temporal(TemporalType.DATE)
    private Date fechaCrea;
    @Column(name = "Hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @JoinColumn(name = "Id_tipo_Servicio", referencedColumnName = "Id_tipo_Servicio")
    @ManyToOne(optional = false)
    private TipoServicio idtipoServicio;
    @JoinColumn(name = "Id_citas", referencedColumnName = "Id_Citas")
    @ManyToOne
    private Citas idcitas;

    public Servicios() {
    }

    public Servicios(String idRegisSer) {
        this.idRegisSer = idRegisSer;
    }

    public String getIdRegisSer() {
        return idRegisSer;
    }

    public void setIdRegisSer(String idRegisSer) {
        this.idRegisSer = idRegisSer;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public Integer getValorServicio() {
        return valorServicio;
    }

    public void setValorServicio(Integer valorServicio) {
        this.valorServicio = valorServicio;
    }

    public Integer getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(Integer codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public String getAquienvadirigido() {
        return aquienvadirigido;
    }

    public void setAquienvadirigido(String aquienvadirigido) {
        this.aquienvadirigido = aquienvadirigido;
    }

    public Date getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(Date fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public TipoServicio getIdtipoServicio() {
        return idtipoServicio;
    }

    public void setIdtipoServicio(TipoServicio idtipoServicio) {
        this.idtipoServicio = idtipoServicio;
    }

    public Citas getIdcitas() {
        return idcitas;
    }

    public void setIdcitas(Citas idcitas) {
        this.idcitas = idcitas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRegisSer != null ? idRegisSer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Servicios)) {
            return false;
        }
        Servicios other = (Servicios) object;
        if ((this.idRegisSer == null && other.idRegisSer != null) || (this.idRegisSer != null && !this.idRegisSer.equals(other.idRegisSer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Servicios[ idRegisSer=" + idRegisSer + " ]";
    }
    
}
