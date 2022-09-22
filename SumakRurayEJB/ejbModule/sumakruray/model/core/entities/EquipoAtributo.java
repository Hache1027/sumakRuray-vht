package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the equipo_atributo database table.
 * 
 */
@Entity
@Table(name="equipo_atributo")
@NamedQuery(name="EquipoAtributo.findAll", query="SELECT e FROM EquipoAtributo e")
public class EquipoAtributo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="equi_atri_id", unique=true, nullable=false)
	private Integer equiAtriId;

	@Column(name="atri_descripcion", length=200)
	private String atriDescripcion;

	//bi-directional many-to-one association to Atributo
	@ManyToOne
	@JoinColumn(name="atri_id")
	private Atributo atributo;

	//bi-directional many-to-one association to Equipo
	@ManyToOne
	@JoinColumn(name="equi_id")
	private Equipo equipo;

	public EquipoAtributo() {
	}

	public Integer getEquiAtriId() {
		return this.equiAtriId;
	}

	public void setEquiAtriId(Integer equiAtriId) {
		this.equiAtriId = equiAtriId;
	}

	public String getAtriDescripcion() {
		return this.atriDescripcion;
	}

	public void setAtriDescripcion(String atriDescripcion) {
		this.atriDescripcion = atriDescripcion;
	}

	public Atributo getAtributo() {
		return this.atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}

	public Equipo getEquipo() {
		return this.equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

}