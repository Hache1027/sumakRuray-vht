package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the atributo database table.
 * 
 */
@Entity
@Table(name="atributo")
@NamedQuery(name="Atributo.findAll", query="SELECT a FROM Atributo a")
public class Atributo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="atri_id", unique=true, nullable=false)
	private Integer atriId;

	@Column(name="atri_nombre", length=100)
	private String atriNombre;

	//bi-directional many-to-one association to AccesorioAtributo
	@OneToMany(mappedBy="atributo")
	private List<AccesorioAtributo> accesorioAtributos;

	//bi-directional many-to-one association to EquipoAtributo
	@OneToMany(mappedBy="atributo")
	private List<EquipoAtributo> equipoAtributos;

	public Atributo() {
	}

	public Integer getAtriId() {
		return this.atriId;
	}

	public void setAtriId(Integer atriId) {
		this.atriId = atriId;
	}

	public String getAtriNombre() {
		return this.atriNombre;
	}

	public void setAtriNombre(String atriNombre) {
		this.atriNombre = atriNombre;
	}

	public List<AccesorioAtributo> getAccesorioAtributos() {
		return this.accesorioAtributos;
	}

	public void setAccesorioAtributos(List<AccesorioAtributo> accesorioAtributos) {
		this.accesorioAtributos = accesorioAtributos;
	}

	public AccesorioAtributo addAccesorioAtributo(AccesorioAtributo accesorioAtributo) {
		getAccesorioAtributos().add(accesorioAtributo);
		accesorioAtributo.setAtributo(this);

		return accesorioAtributo;
	}

	public AccesorioAtributo removeAccesorioAtributo(AccesorioAtributo accesorioAtributo) {
		getAccesorioAtributos().remove(accesorioAtributo);
		accesorioAtributo.setAtributo(null);

		return accesorioAtributo;
	}

	public List<EquipoAtributo> getEquipoAtributos() {
		return this.equipoAtributos;
	}

	public void setEquipoAtributos(List<EquipoAtributo> equipoAtributos) {
		this.equipoAtributos = equipoAtributos;
	}

	public EquipoAtributo addEquipoAtributo(EquipoAtributo equipoAtributo) {
		getEquipoAtributos().add(equipoAtributo);
		equipoAtributo.setAtributo(this);

		return equipoAtributo;
	}

	public EquipoAtributo removeEquipoAtributo(EquipoAtributo equipoAtributo) {
		getEquipoAtributos().remove(equipoAtributo);
		equipoAtributo.setAtributo(null);

		return equipoAtributo;
	}

}