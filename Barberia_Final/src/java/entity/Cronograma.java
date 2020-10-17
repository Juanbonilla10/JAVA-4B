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
@Table(name = "cronograma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cronograma.findAll", query = "SELECT c FROM Cronograma c")
    , @NamedQuery(name = "Cronograma.findByIdCronograma", query = "SELECT c FROM Cronograma c WHERE c.idCronograma = :idCronograma")
    , @NamedQuery(name = "Cronograma.findByFecha", query = "SELECT c FROM Cronograma c WHERE c.fecha = :fecha")
    , @NamedQuery(name = "Cronograma.findByHora", query = "SELECT c FROM Cronograma c WHERE c.hora = :hora")
    , @NamedQuery(name = "Cronograma.findByEstilista", query = "SELECT c FROM Cronograma c WHERE c.estilista = :estilista")
    , @NamedQuery(name = "Cronograma.findByCategoria", query = "SELECT c FROM Cronograma c WHERE c.categoria = :categoria")})
public class Cronograma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "Id_Cronograma")
    private String idCronograma;
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "Hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Size(max = 20)
    @Column(name = "Estilista")
    private String estilista;
    @Size(max = 45)
    @Column(name = "Categoria")
    private String categoria;
    @OneToMany(mappedBy = "idcronograma")
    private List<Citas> citasList;
    @JoinColumn(name = "Id_Empleados", referencedColumnName = "Id_Empleados")
    @ManyToOne
    private Empleados idEmpleados;
    @JoinColumn(name = "Id_Cliente", referencedColumnName = "Id_cliente")
    @ManyToOne
    private Cliente idCliente;
    @JoinColumn(name = "Id_cate_cro", referencedColumnName = "Id_cate_cro")
    @ManyToOne
    private CategoriaCronograma idcatecro;

    public Cronograma() {
    }

    public Cronograma(String idCronograma) {
        this.idCronograma = idCronograma;
    }

    public String getIdCronograma() {
        return idCronograma;
    }

    public void setIdCronograma(String idCronograma) {
        this.idCronograma = idCronograma;
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

    public String getEstilista() {
        return estilista;
    }

    public void setEstilista(String estilista) {
        this.estilista = estilista;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @XmlTransient
    public List<Citas> getCitasList() {
        return citasList;
    }

    public void setCitasList(List<Citas> citasList) {
        this.citasList = citasList;
    }

    public Empleados getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Empleados idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public CategoriaCronograma getIdcatecro() {
        return idcatecro;
    }

    public void setIdcatecro(CategoriaCronograma idcatecro) {
        this.idcatecro = idcatecro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCronograma != null ? idCronograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cronograma)) {
            return false;
        }
        Cronograma other = (Cronograma) object;
        if ((this.idCronograma == null && other.idCronograma != null) || (this.idCronograma != null && !this.idCronograma.equals(other.idCronograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Cronograma[ idCronograma=" + idCronograma + " ]";
    }
    
}
