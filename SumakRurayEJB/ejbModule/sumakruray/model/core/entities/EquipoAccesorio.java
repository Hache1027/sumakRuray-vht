package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equipo_accesorio database table.
 * 
 */
@Entity
@Table(name="equipo_accesorio")
@NamedQuery(name="EquipoAccesorio.findAll", query="SELECT e FROM EquipoAccesorio e")
public class EquipoAccesorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="equi_acc_id", unique=true, nullable=false)
	private Integer equiAccId;

	//bi-directional many-to-one association to Accesorio
	@ManyToOne
	@JoinColumn(name="acce_id")
	private Accesorio accesorio;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	public EquipoAccesorio() {
	}

	public Integer getEquiAccId() {
		return this.equiAccId;
	}

	public void setEquiAccId(Integer equiAccId) {
		this.equiAccId = equiAccId;
	}

	public Accesorio getAccesorio() {
		return this.accesorio;
	}

	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}