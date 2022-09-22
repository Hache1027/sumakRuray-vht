package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accesorio_mantenimiento database table.
 * 
 */
@Entity
@Table(name="accesorio_mantenimiento")
@NamedQuery(name="AccesorioMantenimiento.findAll", query="SELECT a FROM AccesorioMantenimiento a")
public class AccesorioMantenimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="acce_man_id", unique=true, nullable=false)
	private Integer acceManId;

	//bi-directional many-to-one association to Accesorio
	@ManyToOne
	@JoinColumn(name="acce_id")
	private Accesorio accesorio;

	//bi-directional many-to-one association to Mantenimiento
	@ManyToOne
	@JoinColumn(name="man_id")
	private Mantenimiento mantenimiento;

	public AccesorioMantenimiento() {
	}

	public Integer getAcceManId() {
		return this.acceManId;
	}

	public void setAcceManId(Integer acceManId) {
		this.acceManId = acceManId;
	}

	public Accesorio getAccesorio() {
		return this.accesorio;
	}

	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}

	public Mantenimiento getMantenimiento() {
		return this.mantenimiento;
	}

	public void setMantenimiento(Mantenimiento mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

}