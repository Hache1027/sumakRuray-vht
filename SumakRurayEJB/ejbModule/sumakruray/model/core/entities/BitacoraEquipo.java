package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the bitacora_equipo database table.
 * 
 */
@Entity
@Table(name="bitacora_equipo")
@NamedQuery(name="BitacoraEquipo.findAll", query="SELECT b FROM BitacoraEquipo b")
public class BitacoraEquipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bit_equi_id", unique=true, nullable=false)
	private Integer bitEquiId;

	@Column(name="bit_equi_evento", length=100)
	private String bitEquiEvento;

	@Column(name="bit_equi_fecha_crea")
	private Timestamp bitEquiFechaCrea;

	@Column(name="bit_equi_observacion", length=1000)
	private String bitEquiObservacion;

	@Column(name="bit_equi_usuario_crea", length=100)
	private String bitEquiUsuarioCrea;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	public BitacoraEquipo() {
	}

	public Integer getBitEquiId() {
		return this.bitEquiId;
	}

	public void setBitEquiId(Integer bitEquiId) {
		this.bitEquiId = bitEquiId;
	}

	public String getBitEquiEvento() {
		return this.bitEquiEvento;
	}

	public void setBitEquiEvento(String bitEquiEvento) {
		this.bitEquiEvento = bitEquiEvento;
	}

	public Timestamp getBitEquiFechaCrea() {
		return this.bitEquiFechaCrea;
	}

	public void setBitEquiFechaCrea(Timestamp bitEquiFechaCrea) {
		this.bitEquiFechaCrea = bitEquiFechaCrea;
	}

	public String getBitEquiObservacion() {
		return this.bitEquiObservacion;
	}

	public void setBitEquiObservacion(String bitEquiObservacion) {
		this.bitEquiObservacion = bitEquiObservacion;
	}

	public String getBitEquiUsuarioCrea() {
		return this.bitEquiUsuarioCrea;
	}

	public void setBitEquiUsuarioCrea(String bitEquiUsuarioCrea) {
		this.bitEquiUsuarioCrea = bitEquiUsuarioCrea;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}