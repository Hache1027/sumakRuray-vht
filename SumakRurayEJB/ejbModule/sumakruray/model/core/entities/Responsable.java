package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the responsable database table.
 * 
 */
@Entity
@Table(name="responsable")
@NamedQuery(name="Responsable.findAll", query="SELECT r FROM Responsable r")
public class Responsable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="resp_id", unique=true, nullable=false)
	private Integer respId;

	@Column(name="resp_apellidos", length=100)
	private String respApellidos;

	@Column(name="resp_cargo", length=100)
	private String respCargo;

	@Column(name="resp_correo", length=100)
	private String respCorreo;

	@Column(name="resp_direccion_domicilio", length=250)
	private String respDireccionDomicilio;

	@Column(name="resp_estado")
	private Boolean respEstado;

	@Column(name="resp_fecha_creacion")
	private Timestamp respFechaCreacion;

	@Column(name="resp_fecha_modificacion")
	private Timestamp respFechaModificacion;

	@Column(name="resp_identificacion", length=13)
	private String respIdentificacion;

	@Column(name="resp_nombres", length=100)
	private String respNombres;

	@Column(name="resp_telefono", length=15)
	private String respTelefono;

	@Column(name="resp_usuario_crea", length=100)
	private String respUsuarioCrea;

	@Column(name="resp_usuario_modifica", length=100)
	private String respUsuarioModifica;

	//bi-directional many-to-one association to Accesorio
	@OneToMany(mappedBy="responsable")
	private List<Accesorio> accesorios;

	//bi-directional many-to-one association to Equipo
	@OneToMany(mappedBy="responsable")
	private List<Equipo> equipos;

	public Responsable() {
	}

	public Integer getRespId() {
		return this.respId;
	}

	public void setRespId(Integer respId) {
		this.respId = respId;
	}

	public String getRespApellidos() {
		return this.respApellidos;
	}

	public void setRespApellidos(String respApellidos) {
		this.respApellidos = respApellidos;
	}

	public String getRespCargo() {
		return this.respCargo;
	}

	public void setRespCargo(String respCargo) {
		this.respCargo = respCargo;
	}

	public String getRespCorreo() {
		return this.respCorreo;
	}

	public void setRespCorreo(String respCorreo) {
		this.respCorreo = respCorreo;
	}

	public String getRespDireccionDomicilio() {
		return this.respDireccionDomicilio;
	}

	public void setRespDireccionDomicilio(String respDireccionDomicilio) {
		this.respDireccionDomicilio = respDireccionDomicilio;
	}

	public Boolean getRespEstado() {
		return this.respEstado;
	}

	public void setRespEstado(Boolean respEstado) {
		this.respEstado = respEstado;
	}

	public Timestamp getRespFechaCreacion() {
		return this.respFechaCreacion;
	}

	public void setRespFechaCreacion(Timestamp respFechaCreacion) {
		this.respFechaCreacion = respFechaCreacion;
	}

	public Timestamp getRespFechaModificacion() {
		return this.respFechaModificacion;
	}

	public void setRespFechaModificacion(Timestamp respFechaModificacion) {
		this.respFechaModificacion = respFechaModificacion;
	}

	public String getRespIdentificacion() {
		return this.respIdentificacion;
	}

	public void setRespIdentificacion(String respIdentificacion) {
		this.respIdentificacion = respIdentificacion;
	}

	public String getRespNombres() {
		return this.respNombres;
	}

	public void setRespNombres(String respNombres) {
		this.respNombres = respNombres;
	}

	public String getRespTelefono() {
		return this.respTelefono;
	}

	public void setRespTelefono(String respTelefono) {
		this.respTelefono = respTelefono;
	}

	public String getRespUsuarioCrea() {
		return this.respUsuarioCrea;
	}

	public void setRespUsuarioCrea(String respUsuarioCrea) {
		this.respUsuarioCrea = respUsuarioCrea;
	}

	public String getRespUsuarioModifica() {
		return this.respUsuarioModifica;
	}

	public void setRespUsuarioModifica(String respUsuarioModifica) {
		this.respUsuarioModifica = respUsuarioModifica;
	}

	public List<Accesorio> getAccesorios() {
		return this.accesorios;
	}

	public void setAccesorios(List<Accesorio> accesorios) {
		this.accesorios = accesorios;
	}

	public Accesorio addAccesorio(Accesorio accesorio) {
		getAccesorios().add(accesorio);
		accesorio.setResponsable(this);

		return accesorio;
	}

	public Accesorio removeAccesorio(Accesorio accesorio) {
		getAccesorios().remove(accesorio);
		accesorio.setResponsable(null);

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
		equipo.setResponsable(this);

		return equipo;
	}

	public Equipo removeEquipo(Equipo equipo) {
		getEquipos().remove(equipo);
		equipo.setResponsable(null);

		return equipo;
	}

}