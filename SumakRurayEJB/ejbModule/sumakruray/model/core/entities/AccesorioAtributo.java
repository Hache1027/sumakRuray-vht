package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the accesorio_atributo database table.
 * 
 */
@Entity
@Table(name="accesorio_atributo")
@NamedQuery(name="AccesorioAtributo.findAll", query="SELECT a FROM AccesorioAtributo a")
public class AccesorioAtributo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="acce_atri_id", unique=true, nullable=false)
	private Integer acceAtriId;

	@Column(name="atri_descripcion", length=200)
	private String atriDescripcion;

	//bi-directional many-to-one association to Accesorio
	@ManyToOne
	@JoinColumn(name="acce_id")
	private Accesorio accesorio;

	//bi-directional many-to-one association to Atributo
	@ManyToOne
	@JoinColumn(name="atri_id")
	private Atributo atributo;

	public AccesorioAtributo() {
	}

	public Integer getAcceAtriId() {
		return this.acceAtriId;
	}

	public void setAcceAtriId(Integer acceAtriId) {
		this.acceAtriId = acceAtriId;
	}

	public String getAtriDescripcion() {
		return this.atriDescripcion;
	}

	public void setAtriDescripcion(String atriDescripcion) {
		this.atriDescripcion = atriDescripcion;
	}

	public Accesorio getAccesorio() {
		return this.accesorio;
	}

	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}

	public Atributo getAtributo() {
		return this.atributo;
	}

	public void setAtributo(Atributo atributo) {
		this.atributo = atributo;
	}

}