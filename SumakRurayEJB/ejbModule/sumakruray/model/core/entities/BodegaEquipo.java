package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the bodega_equipo database table.
 * 
 */
@Entity
@Table(name="bodega_equipo")
@NamedQuery(name="BodegaEquipo.findAll", query="SELECT b FROM BodegaEquipo b")
public class BodegaEquipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bod_id", unique=true, nullable=false)
	private Integer bodId;

	@Column(name="bod_fecha_creacion")
	private Timestamp bodFechaCreacion;

	@Column(name="bod_fecha_modificacion")
	private Timestamp bodFechaModificacion;

	@Column(name="bod_observacion", length=300)
	private String bodObservacion;

	@Column(name="bod_usuario_crea", length=100)
	private String bodUsuarioCrea;

	@Column(name="bod_usuario_modifica", length=100)
	private String bodUsuarioModifica;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	public BodegaEquipo() {
	}

	public Integer getBodId() {
		return this.bodId;
	}

	public void setBodId(Integer bodId) {
		this.bodId = bodId;
	}

	public Timestamp getBodFechaCreacion() {
		return this.bodFechaCreacion;
	}

	public void setBodFechaCreacion(Timestamp bodFechaCreacion) {
		this.bodFechaCreacion = bodFechaCreacion;
	}

	public Timestamp getBodFechaModificacion() {
		return this.bodFechaModificacion;
	}

	public void setBodFechaModificacion(Timestamp bodFechaModificacion) {
		this.bodFechaModificacion = bodFechaModificacion;
	}

	public String getBodObservacion() {
		return this.bodObservacion;
	}

	public void setBodObservacion(String bodObservacion) {
		this.bodObservacion = bodObservacion;
	}

	public String getBodUsuarioCrea() {
		return this.bodUsuarioCrea;
	}

	public void setBodUsuarioCrea(String bodUsuarioCrea) {
		this.bodUsuarioCrea = bodUsuarioCrea;
	}

	public String getBodUsuarioModifica() {
		return this.bodUsuarioModifica;
	}

	public void setBodUsuarioModifica(String bodUsuarioModifica) {
		this.bodUsuarioModifica = bodUsuarioModifica;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}