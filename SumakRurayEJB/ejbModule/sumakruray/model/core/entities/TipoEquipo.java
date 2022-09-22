package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_equipo database table.
 * 
 */
@Entity
@Table(name="tipo_equipo")
@NamedQuery(name="TipoEquipo.findAll", query="SELECT t FROM TipoEquipo t")
public class TipoEquipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tip_equi_id", unique=true, nullable=false)
	private Integer tipEquiId;

	@Column(name="tip_equi_cantidad")
	private Integer tipEquiCantidad;

	@Column(name="tip_equi_nombre", length=100)
	private String tipEquiNombre;

	//bi-directional many-to-one association to Equipo
	@OneToMany(mappedBy="tipoEquipo")
	private List<Equipo> equipos;

	public TipoEquipo() {
	}

	public Integer getTipEquiId() {
		return this.tipEquiId;
	}

	public void setTipEquiId(Integer tipEquiId) {
		this.tipEquiId = tipEquiId;
	}

	public Integer getTipEquiCantidad() {
		return this.tipEquiCantidad;
	}

	public void setTipEquiCantidad(Integer tipEquiCantidad) {
		this.tipEquiCantidad = tipEquiCantidad;
	}

	public String getTipEquiNombre() {
		return this.tipEquiNombre;
	}

	public void setTipEquiNombre(String tipEquiNombre) {
		this.tipEquiNombre = tipEquiNombre;
	}

	public List<Equipo> getEquipos() {
		return this.equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}

	public Equipo addEquipo(Equipo equipo) {
		getEquipos().add(equipo);
		equipo.setTipoEquipo(this);

		return equipo;
	}

	public Equipo removeEquipo(Equipo equipo) {
		getEquipos().remove(equipo);
		equipo.setTipoEquipo(null);

		return equipo;
	}

}