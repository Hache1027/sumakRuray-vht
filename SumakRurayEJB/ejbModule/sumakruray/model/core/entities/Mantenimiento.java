package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the mantenimiento database table.
 * 
 */
@Entity
@Table(name = "mantenimiento")
@NamedQuery(name = "Mantenimiento.findAll", query = "SELECT m FROM Mantenimiento m")
public class Mantenimiento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "man_id", unique = true, nullable = false)
	private Integer manId;

	@Column(name = "man_descripcion", length = 500)
	private String manDescripcion;

	@Column(name = "man_diagnostico", length = 100)
	private String manDiagnostico;

	@Column(name = "man_estado", length = 50)
	private String manEstado;

	@Column(name = "man_fecha_creacion")
	private Timestamp manFechaCreacion;

	@Column(name = "man_fecha_modificacion")
	private Timestamp manFechaModificacion;

	@Column(name = "man_funcionario", length = 100)
	private String manFuncionario;

	@Column(name = "man_funcionario_entrega", length = 100)
	private String manFuncionarioEntrega;

	@Column(name = "man_funcionario_recibe", length = 100)
	private String manFuncionarioRecibe;

	@Column(name = "man_tipo_int_ext", length = 100)
	private String manTipoIntExt;

	@Column(name = "man_tipo_pre_corr", length = 100)
	private String manTipoPreCorr;

	@Column(name = "man_usuario_crea", length = 100)
	private String manUsuarioCrea;

	@Column(name = "man_usuario_modifica", length = 100)
	private String manUsuarioModifica;

	@Column(name = "man_usuario_recibe", length = 100)
	private String manUsuarioRecibe;

	// bi-directional many-to-one association to AccesorioMantenimiento
	@OneToMany(mappedBy = "mantenimiento", cascade = CascadeType.ALL)
	private List<AccesorioMantenimiento> accesorioMantenimientos;

	// bi-directional many-to-one association to EquipoMantenimiento
	@OneToMany(mappedBy = "mantenimiento", cascade = CascadeType.ALL)
	private List<EquipoMantenimiento> equipoMantenimientos;

	public Mantenimiento() {
	}

	public Integer getManId() {
		return this.manId;
	}

	public void setManId(Integer manId) {
		this.manId = manId;
	}

	public String getManDescripcion() {
		return this.manDescripcion;
	}

	public void setManDescripcion(String manDescripcion) {
		this.manDescripcion = manDescripcion;
	}

	public String getManDiagnostico() {
		return this.manDiagnostico;
	}

	public void setManDiagnostico(String manDiagnostico) {
		this.manDiagnostico = manDiagnostico;
	}

	public String getManEstado() {
		return this.manEstado;
	}

	public void setManEstado(String manEstado) {
		this.manEstado = manEstado;
	}

	public Timestamp getManFechaCreacion() {
		return this.manFechaCreacion;
	}

	public void setManFechaCreacion(Timestamp manFechaCreacion) {
		this.manFechaCreacion = manFechaCreacion;
	}

	public Timestamp getManFechaModificacion() {
		return this.manFechaModificacion;
	}

	public void setManFechaModificacion(Timestamp manFechaModificacion) {
		this.manFechaModificacion = manFechaModificacion;
	}

	public String getManFuncionario() {
		return this.manFuncionario;
	}

	public void setManFuncionario(String manFuncionario) {
		this.manFuncionario = manFuncionario;
	}

	public String getManFuncionarioEntrega() {
		return this.manFuncionarioEntrega;
	}

	public void setManFuncionarioEntrega(String manFuncionarioEntrega) {
		this.manFuncionarioEntrega = manFuncionarioEntrega;
	}

	public String getManFuncionarioRecibe() {
		return this.manFuncionarioRecibe;
	}

	public void setManFuncionarioRecibe(String manFuncionarioRecibe) {
		this.manFuncionarioRecibe = manFuncionarioRecibe;
	}

	public String getManTipoIntExt() {
		return this.manTipoIntExt;
	}

	public void setManTipoIntExt(String manTipoIntExt) {
		this.manTipoIntExt = manTipoIntExt;
	}

	public String getManTipoPreCorr() {
		return this.manTipoPreCorr;
	}

	public void setManTipoPreCorr(String manTipoPreCorr) {
		this.manTipoPreCorr = manTipoPreCorr;
	}

	public String getManUsuarioCrea() {
		return this.manUsuarioCrea;
	}

	public void setManUsuarioCrea(String manUsuarioCrea) {
		this.manUsuarioCrea = manUsuarioCrea;
	}

	public String getManUsuarioModifica() {
		return this.manUsuarioModifica;
	}

	public void setManUsuarioModifica(String manUsuarioModifica) {
		this.manUsuarioModifica = manUsuarioModifica;
	}

	public String getManUsuarioRecibe() {
		return this.manUsuarioRecibe;
	}

	public void setManUsuarioRecibe(String manUsuarioRecibe) {
		this.manUsuarioRecibe = manUsuarioRecibe;
	}

	public List<AccesorioMantenimiento> getAccesorioMantenimientos() {
		return this.accesorioMantenimientos;
	}

	public void setAccesorioMantenimientos(List<AccesorioMantenimiento> accesorioMantenimientos) {
		this.accesorioMantenimientos = accesorioMantenimientos;
	}

	public AccesorioMantenimiento addAccesorioMantenimiento(AccesorioMantenimiento accesorioMantenimiento) {
		getAccesorioMantenimientos().add(accesorioMantenimiento);
		accesorioMantenimiento.setMantenimiento(this);

		return accesorioMantenimiento;
	}

	public AccesorioMantenimiento removeAccesorioMantenimiento(AccesorioMantenimiento accesorioMantenimiento) {
		getAccesorioMantenimientos().remove(accesorioMantenimiento);
		accesorioMantenimiento.setMantenimiento(null);

		return accesorioMantenimiento;
	}

	public List<EquipoMantenimiento> getEquipoMantenimientos() {
		return this.equipoMantenimientos;
	}

	public void setEquipoMantenimientos(List<EquipoMantenimiento> equipoMantenimientos) {
		this.equipoMantenimientos = equipoMantenimientos;
	}

	public EquipoMantenimiento addEquipoMantenimiento(EquipoMantenimiento equipoMantenimiento) {
		getEquipoMantenimientos().add(equipoMantenimiento);
		equipoMantenimiento.setMantenimiento(this);

		return equipoMantenimiento;
	}

	public EquipoMantenimiento removeEquipoMantenimiento(EquipoMantenimiento equipoMantenimiento) {
		getEquipoMantenimientos().remove(equipoMantenimiento);
		equipoMantenimiento.setMantenimiento(null);

		return equipoMantenimiento;
	}

}