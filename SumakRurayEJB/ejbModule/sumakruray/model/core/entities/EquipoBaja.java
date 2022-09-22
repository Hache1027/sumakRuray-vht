package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the equipo_baja database table.
 * 
 */
@Entity
@Table(name="equipo_baja")
@NamedQuery(name="EquipoBaja.findAll", query="SELECT e FROM EquipoBaja e")
public class EquipoBaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="equi_baja_id", unique=true, nullable=false)
	private Integer equiBajaId;

	@Column(name="baja_descripcion", length=300)
	private String bajaDescripcion;

	@Column(name="baja_fecha_creacion")
	private Timestamp bajaFechaCreacion;

	@Column(name="baja_tipo_obsol_dania", length=30)
	private String bajaTipoObsolDania;

	@Column(name="baja_usuario_crea", length=100)
	private String bajaUsuarioCrea;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	public EquipoBaja() {
	}

	public Integer getEquiBajaId() {
		return this.equiBajaId;
	}

	public void setEquiBajaId(Integer equiBajaId) {
		this.equiBajaId = equiBajaId;
	}

	public String getBajaDescripcion() {
		return this.bajaDescripcion;
	}

	public void setBajaDescripcion(String bajaDescripcion) {
		this.bajaDescripcion = bajaDescripcion;
	}

	public Timestamp getBajaFechaCreacion() {
		return this.bajaFechaCreacion;
	}

	public void setBajaFechaCreacion(Timestamp bajaFechaCreacion) {
		this.bajaFechaCreacion = bajaFechaCreacion;
	}

	public String getBajaTipoObsolDania() {
		return this.bajaTipoObsolDania;
	}

	public void setBajaTipoObsolDania(String bajaTipoObsolDania) {
		this.bajaTipoObsolDania = bajaTipoObsolDania;
	}

	public String getBajaUsuarioCrea() {
		return this.bajaUsuarioCrea;
	}

	public void setBajaUsuarioCrea(String bajaUsuarioCrea) {
		this.bajaUsuarioCrea = bajaUsuarioCrea;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}