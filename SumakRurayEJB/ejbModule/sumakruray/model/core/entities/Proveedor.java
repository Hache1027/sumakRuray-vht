package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the proveedor database table.
 * 
 */
@Entity
@Table(name="proveedor")
@NamedQuery(name="Proveedor.findAll", query="SELECT p FROM Proveedor p")
public class Proveedor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pro_id", unique=true, nullable=false)
	private Integer proId;

	@Column(name="pro_correo", length=100)
	private String proCorreo;

	@Column(name="pro_direccion", length=250)
	private String proDireccion;

	@Column(name="pro_empresa", nullable=false, length=100)
	private String proEmpresa;

	@Column(name="pro_estado")
	private Boolean proEstado;

	@Column(name="pro_fecha_creacion")
	private Timestamp proFechaCreacion;

	@Column(name="pro_fecha_modificacion")
	private Timestamp proFechaModificacion;

	@Column(name="pro_telefono", length=15)
	private String proTelefono;

	@Column(name="pro_usuario_crea", length=100)
	private String proUsuarioCrea;

	@Column(name="pro_usuario_modifica", length=100)
	private String proUsuarioModifica;

	//bi-directional many-to-one association to Accesorio
	@OneToMany(mappedBy="proveedor")
	private List<Accesorio> accesorios;

	//bi-directional many-to-one association to Equipo
	@OneToMany(mappedBy="proveedor")
	private List<Equipo> equipos;

	public Proveedor() {
	}

	public Integer getProId() {
		return this.proId;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public String getProCorreo() {
		return this.proCorreo;
	}

	public void setProCorreo(String proCorreo) {
		this.proCorreo = proCorreo;
	}

	public String getProDireccion() {
		return this.proDireccion;
	}

	public void setProDireccion(String proDireccion) {
		this.proDireccion = proDireccion;
	}

	public String getProEmpresa() {
		return this.proEmpresa;
	}

	public void setProEmpresa(String proEmpresa) {
		this.proEmpresa = proEmpresa;
	}

	public Boolean getProEstado() {
		return this.proEstado;
	}

	public void setProEstado(Boolean proEstado) {
		this.proEstado = proEstado;
	}

	public Timestamp getProFechaCreacion() {
		return this.proFechaCreacion;
	}

	public void setProFechaCreacion(Timestamp proFechaCreacion) {
		this.proFechaCreacion = proFechaCreacion;
	}

	public Timestamp getProFechaModificacion() {
		return this.proFechaModificacion;
	}

	public void setProFechaModificacion(Timestamp proFechaModificacion) {
		this.proFechaModificacion = proFechaModificacion;
	}

	public String getProTelefono() {
		return this.proTelefono;
	}

	public void setProTelefono(String proTelefono) {
		this.proTelefono = proTelefono;
	}

	public String getProUsuarioCrea() {
		return this.proUsuarioCrea;
	}

	public void setProUsuarioCrea(String proUsuarioCrea) {
		this.proUsuarioCrea = proUsuarioCrea;
	}

	public String getProUsuarioModifica() {
		return this.proUsuarioModifica;
	}

	public void setProUsuarioModifica(String proUsuarioModifica) {
		this.proUsuarioModifica = proUsuarioModifica;
	}

	public List<Accesorio> getAccesorios() {
		return this.accesorios;
	}

	public void setAccesorios(List<Accesorio> accesorios) {
		this.accesorios = accesorios;
	}

	public Accesorio addAccesorio(Accesorio accesorio) {
		getAccesorios().add(accesorio);
		accesorio.setProveedor(this);

		return accesorio;
	}

	public Accesorio removeAccesorio(Accesorio accesorio) {
		getAccesorios().remove(accesorio);
		accesorio.setProveedor(null);

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
		equipo.setProveedor(this);

		return equipo;
	}

	public Equipo removeEquipo(Equipo equipo) {
		getEquipos().remove(equipo);
		equipo.setProveedor(null);

		return equipo;
	}

}