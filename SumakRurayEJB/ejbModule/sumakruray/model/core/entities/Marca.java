package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the marca database table.
 * 
 */
@Entity
@Table(name="marca")
@NamedQuery(name="Marca.findAll", query="SELECT m FROM Marca m")
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="mar_id", unique=true, nullable=false)
	private Integer marId;

	@Column(name="mar_descripcion", length=100)
	private String marDescripcion;

	//bi-directional many-to-one association to Accesorio
	@OneToMany(mappedBy="marca")
	private List<Accesorio> accesorios;

	//bi-directional many-to-one association to Equipo
	@OneToMany(mappedBy="marca")
	private List<Equipo> equipos;

	public Marca() {
	}

	public Integer getMarId() {
		return this.marId;
	}

	public void setMarId(Integer marId) {
		this.marId = marId;
	}

	public String getMarDescripcion() {
		return this.marDescripcion;
	}

	public void setMarDescripcion(String marDescripcion) {
		this.marDescripcion = marDescripcion;
	}

	public List<Accesorio> getAccesorios() {
		return this.accesorios;
	}

	public void setAccesorios(List<Accesorio> accesorios) {
		this.accesorios = accesorios;
	}

	public Accesorio addAccesorio(Accesorio accesorio) {
		getAccesorios().add(accesorio);
		accesorio.setMarca(this);

		return accesorio;
	}

	public Accesorio removeAccesorio(Accesorio accesorio) {
		getAccesorios().remove(accesorio);
		accesorio.setMarca(null);

		return accesorio;
	}

	public List<Equipo> getEquipos() {
		return this.equipos;
	}

	public void setEquipos(List<Equipo> equipos) {
		this.equipos = equipos;
	}

	public Equipo addEquipo(Equipo equipo) {
		getEquipos().add(equipo);
		equipo.setMarca(this);

		return equipo;
	}

	public Equipo removeEquipo(Equipo equipo) {
		getEquipos().remove(equipo);
		equipo.setMarca(null);

		return equipo;
	}

}