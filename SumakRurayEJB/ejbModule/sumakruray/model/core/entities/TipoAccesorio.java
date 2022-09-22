package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tipo_accesorio database table.
 * 
 */
@Entity
@Table(name="tipo_accesorio")
@NamedQuery(name="TipoAccesorio.findAll", query="SELECT t FROM TipoAccesorio t")
public class TipoAccesorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tip_acc_id", unique=true, nullable=false)
	private Integer tipAccId;

	@Column(name="tip_acc_cantidad")
	private Integer tipAccCantidad;

	@Column(name="tip_acc_nombre", length=100)
	private String tipAccNombre;

	//bi-directional many-to-one association to Accesorio
	@OneToMany(mappedBy="tipoAccesorio")
	private List<Accesorio> accesorios;

	public TipoAccesorio() {
	}

	public Integer getTipAccId() {
		return this.tipAccId;
	}

	public void setTipAccId(Integer tipAccId) {
		this.tipAccId = tipAccId;
	}

	public Integer getTipAccCantidad() {
		return this.tipAccCantidad;
	}

	public void setTipAccCantidad(Integer tipAccCantidad) {
		this.tipAccCantidad = tipAccCantidad;
	}

	public String getTipAccNombre() {
		return this.tipAccNombre;
	}

	public void setTipAccNombre(String tipAccNombre) {
		this.tipAccNombre = tipAccNombre;
	}

	public List<Accesorio> getAccesorios() {
		return this.accesorios;
	}

	public void setAccesorios(List<Accesorio> accesorios) {
		this.accesorios = accesorios;
	}

	public Accesorio addAccesorio(Accesorio accesorio) {
		getAccesorios().add(accesorio);
		accesorio.setTipoAccesorio(this);

		return accesorio;
	}

	public Accesorio removeAccesorio(Accesorio accesorio) {
		getAccesorios().remove(accesorio);
		accesorio.setTipoAccesorio(null);

		return accesorio;
	}

}