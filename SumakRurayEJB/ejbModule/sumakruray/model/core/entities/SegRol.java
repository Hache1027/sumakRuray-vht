package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the seg_rol database table.
 * 
 */
@Entity
@Table(name="seg_rol")
@NamedQuery(name="SegRol.findAll", query="SELECT s FROM SegRol s")
public class SegRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_seg_rol", unique=true, nullable=false)
	private Integer idSegRol;

	@Column(nullable=false)
	private Boolean estado;

	@Column(name="rol_descripcion", nullable=false, length=50)
	private String rolDescripcion;

	//bi-directional many-to-one association to SegUsuario
	@OneToMany(mappedBy="segRol")
	private List<SegUsuario> segUsuarios;

	public SegRol() {
	}

	public Integer getIdSegRol() {
		return this.idSegRol;
	}

	public void setIdSegRol(Integer idSegRol) {
		this.idSegRol = idSegRol;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getRolDescripcion() {
		return this.rolDescripcion;
	}

	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}

	public List<SegUsuario> getSegUsuarios() {
		return this.segUsuarios;
	}

	public void setSegUsuarios(List<SegUsuario> segUsuarios) {
		this.segUsuarios = segUsuarios;
	}

	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().add(segUsuario);
		segUsuario.setSegRol(this);

		return segUsuario;
	}

	public SegUsuario removeSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().remove(segUsuario);
		segUsuario.setSegRol(null);

		return segUsuario;
	}

}