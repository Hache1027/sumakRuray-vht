package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equipo_mantenimiento database table.
 * 
 */
@Entity
@Table(name="equipo_mantenimiento")
@NamedQuery(name="EquipoMantenimiento.findAll", query="SELECT e FROM EquipoMantenimiento e")
public class EquipoMantenimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="equi_man_id", unique=true, nullable=false)
	private Integer equiManId;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	//bi-directional many-to-one association to Mantenimiento
	@ManyToOne
	@JoinColumn(name="man_id")
	private Mantenimiento mantenimiento;

	public EquipoMantenimiento() {
	}

	public Integer getEquiManId() {
		return this.equiManId;
	}

	public void setEquiManId(Integer equiManId) {
		this.equiManId = equiManId;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Mantenimiento getMantenimiento() {
		return this.mantenimiento;
	}

	public void setMantenimiento(Mantenimiento mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

}