package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the accesorio_baja database table.
 * 
 */
@Entity
@Table(name="accesorio_baja")
@NamedQuery(name="AccesorioBaja.findAll", query="SELECT a FROM AccesorioBaja a")
public class AccesorioBaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="acce_baja_id", unique=true, nullable=false)
	private Integer acceBajaId;

	@Column(name="baja_descripcion", length=300)
	private String bajaDescripcion;

	@Column(name="baja_fecha_creacion")
	private Timestamp bajaFechaCreacion;

	@Column(name="baja_tipo_obsol_dania", length=30)
	private String bajaTipoObsolDania;

	@Column(name="baja_usuario_crea", length=100)
	private String bajaUsuarioCrea;

	//bi-directional many-to-one association to Accesorio
	@ManyToOne
	@JoinColumn(name="acce_id")
	private Accesorio accesorio;

	public AccesorioBaja() {
	}

	public Integer getAcceBajaId() {
		return this.acceBajaId;
	}

	public void setAcceBajaId(Integer acceBajaId) {
		this.acceBajaId = acceBajaId;
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

	public Accesorio getAccesorio() {
		return this.accesorio;
	}

	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}

}