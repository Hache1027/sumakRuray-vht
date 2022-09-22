package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@Table(name="persona")
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="per_id", unique=true, nullable=false)
	private Integer perId;

	@Column(name="per_apellidos", nullable=false, length=50)
	private String perApellidos;

	@Column(name="per_cedula", nullable=false, length=15)
	private String perCedula;

	@Column(name="per_correo", length=100)
	private String perCorreo;

	@Column(name="per_direccion", nullable=false, length=100)
	private String perDireccion;

	@Column(name="per_estado", nullable=false)
	private Boolean perEstado;

	@Column(name="per_nombres", nullable=false, length=50)
	private String perNombres;

	@Column(name="per_telefono", length=20)
	private String perTelefono;

	//bi-directional many-to-one association to SegUsuario
	@OneToMany(mappedBy="persona")
	private List<SegUsuario> segUsuarios;

	public Persona() {
	}

	public Integer getPerId() {
		return this.perId;
	}

	public void setPerId(Integer perId) {
		this.perId = perId;
	}

	public String getPerApellidos() {
		return this.perApellidos;
	}

	public void setPerApellidos(String perApellidos) {
		this.perApellidos = perApellidos;
	}

	public String getPerCedula() {
		return this.perCedula;
	}

	public void setPerCedula(String perCedula) {
		this.perCedula = perCedula;
	}

	public String getPerCorreo() {
		return this.perCorreo;
	}

	public void setPerCorreo(String perCorreo) {
		this.perCorreo = perCorreo;
	}

	public String getPerDireccion() {
		return this.perDireccion;
	}

	public void setPerDireccion(String perDireccion) {
		this.perDireccion = perDireccion;
	}

	public Boolean getPerEstado() {
		return this.perEstado;
	}

	public void setPerEstado(Boolean perEstado) {
		this.perEstado = perEstado;
	}

	public String getPerNombres() {
		return this.perNombres;
	}

	public void setPerNombres(String perNombres) {
		this.perNombres = perNombres;
	}

	public String getPerTelefono() {
		return this.perTelefono;
	}

	public void setPerTelefono(String perTelefono) {
		this.perTelefono = perTelefono;
	}

	public List<SegUsuario> getSegUsuarios() {
		return this.segUsuarios;
	}

	public void setSegUsuarios(List<SegUsuario> segUsuarios) {
		this.segUsuarios = segUsuarios;
	}

	public SegUsuario addSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().add(segUsuario);
		segUsuario.setPersona(this);

		return segUsuario;
	}

	public SegUsuario removeSegUsuario(SegUsuario segUsuario) {
		getSegUsuarios().remove(segUsuario);
		segUsuario.setPersona(null);

		return segUsuario;
	}

}