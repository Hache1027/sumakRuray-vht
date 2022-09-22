package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the lista_ip database table.
 * 
 */
@Entity
@Table(name="lista_ip")
@NamedQuery(name="ListaIp.findAll", query="SELECT l FROM ListaIp l")
public class ListaIp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ips_id", unique=true, nullable=false)
	private Integer ipsId;

	@Column(name="ips_estado", length=50)
	private String ipsEstado;

	@Column(name="ips_fecha_creacion")
	private Timestamp ipsFechaCreacion;

	@Column(name="ips_fecha_modificacion")
	private Timestamp ipsFechaModificacion;

	@Column(name="ips_ip", length=20)
	private String ipsIp;

	@Column(name="ips_observacion", length=100)
	private String ipsObservacion;

	@Column(name="ips_puerta_enlace", length=20)
	private String ipsPuertaEnlace;

	@Column(name="ips_usuario_crea", length=100)
	private String ipsUsuarioCrea;

	@Column(name="ips_usuario_modifica", length=100)
	private String ipsUsuarioModifica;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	public ListaIp() {
	}

	public Integer getIpsId() {
		return this.ipsId;
	}

	public void setIpsId(Integer ipsId) {
		this.ipsId = ipsId;
	}

	public String getIpsEstado() {
		return this.ipsEstado;
	}

	public void setIpsEstado(String ipsEstado) {
		this.ipsEstado = ipsEstado;
	}

	public Timestamp getIpsFechaCreacion() {
		return this.ipsFechaCreacion;
	}

	public void setIpsFechaCreacion(Timestamp ipsFechaCreacion) {
		this.ipsFechaCreacion = ipsFechaCreacion;
	}

	public Timestamp getIpsFechaModificacion() {
		return this.ipsFechaModificacion;
	}

	public void setIpsFechaModificacion(Timestamp ipsFechaModificacion) {
		this.ipsFechaModificacion = ipsFechaModificacion;
	}

	public String getIpsIp() {
		return this.ipsIp;
	}

	public void setIpsIp(String ipsIp) {
		this.ipsIp = ipsIp;
	}

	public String getIpsObservacion() {
		return this.ipsObservacion;
	}

	public void setIpsObservacion(String ipsObservacion) {
		this.ipsObservacion = ipsObservacion;
	}

	public String getIpsPuertaEnlace() {
		return this.ipsPuertaEnlace;
	}

	public void setIpsPuertaEnlace(String ipsPuertaEnlace) {
		this.ipsPuertaEnlace = ipsPuertaEnlace;
	}

	public String getIpsUsuarioCrea() {
		return this.ipsUsuarioCrea;
	}

	public void setIpsUsuarioCrea(String ipsUsuarioCrea) {
		this.ipsUsuarioCrea = ipsUsuarioCrea;
	}

	public String getIpsUsuarioModifica() {
		return this.ipsUsuarioModifica;
	}

	public void setIpsUsuarioModifica(String ipsUsuarioModifica) {
		this.ipsUsuarioModifica = ipsUsuarioModifica;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}