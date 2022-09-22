package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the accesorio database table.
 * 
 */
@Entity
@Table(name="accesorio")
@NamedQuery(name="Accesorio.findAll", query="SELECT a FROM Accesorio a")
public class Accesorio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="acce_id", unique=true, nullable=false)
	private Integer acceId;

	@Column(name="acc_precio")
	private double accPrecio;

	@Column(name="acce_ano_vida_util")
	private Integer acceAnoVidaUtil;

	@Column(name="acce_cod_bodega", length=50)
	private String acceCodBodega;

	@Column(name="acce_estado", length=50)
	private String acceEstado;

	@Column(name="acce_fecha_creacion")
	private Timestamp acceFechaCreacion;

	@Column(name="acce_fecha_modificacion")
	private Timestamp acceFechaModificacion;

	@Column(name="acce_garantia")
	private Integer acceGarantia;

	@Column(name="acce_nombre", length=100)
	private String acceNombre;

	@Column(name="acce_nro_serie", length=50)
	private String acceNroSerie;

	@Column(name="acce_usuario_crea", length=100)
	private String acceUsuarioCrea;

	@Column(name="acce_usuario_modifica", length=100)
	private String acceUsuarioModifica;

	//bi-directional many-to-one association to Marca
	@ManyToOne
	@JoinColumn(name="mar_id")
	private Marca marca;

	//bi-directional many-to-one association to Proveedor
	@ManyToOne
	@JoinColumn(name="pro_id")
	private Proveedor proveedor;

	//bi-directional many-to-one association to Responsable
	@ManyToOne
	@JoinColumn(name="resp_id")
	private Responsable responsable;

	//bi-directional many-to-one association to SegDependencia
	@ManyToOne
	@JoinColumn(name="id_seg_dependencia")
	private SegDependencia segDependencia;

	//bi-directional many-to-one association to TipoAccesorio
	@ManyToOne
	@JoinColumn(name="tip_acc_id")
	private TipoAccesorio tipoAccesorio;

	//bi-directional many-to-one association to AccesorioAtributo
	@OneToMany(mappedBy="accesorio", cascade = CascadeType.ALL)
	private List<AccesorioAtributo> accesorioAtributos;

	//bi-directional many-to-one association to AccesorioBaja
	@OneToMany(mappedBy="accesorio")
	private List<AccesorioBaja> accesorioBajas;

	//bi-directional many-to-one association to AccesorioMantenimiento
	@OneToMany(mappedBy="accesorio")
	private List<AccesorioMantenimiento> accesorioMantenimientos;

	//bi-directional many-to-one association to BitacoraAccesorio
	@OneToMany(mappedBy="accesorio")
	private List<BitacoraAccesorio> bitacoraAccesorios;

	//bi-directional many-to-one association to BodegaAccesorio
	@OneToMany(mappedBy="accesorio")
	private List<BodegaAccesorio> bodegaAccesorios;

	//bi-directional many-to-one association to EquipoAccesorio
	@OneToMany(mappedBy="accesorio")
	private List<EquipoAccesorio> equipoAccesorios;

	public Accesorio() {
	}

	public Integer getAcceId() {
		return this.acceId;
	}

	public void setAcceId(Integer acceId) {
		this.acceId = acceId;
	}

	public double getAccPrecio() {
		return this.accPrecio;
	}

	public void setAccPrecio(double accPrecio) {
		this.accPrecio = accPrecio;
	}

	public Integer getAcceAnoVidaUtil() {
		return this.acceAnoVidaUtil;
	}

	public void setAcceAnoVidaUtil(Integer acceAnoVidaUtil) {
		this.acceAnoVidaUtil = acceAnoVidaUtil;
	}

	public String getAcceCodBodega() {
		return this.acceCodBodega;
	}

	public void setAcceCodBodega(String acceCodBodega) {
		this.acceCodBodega = acceCodBodega;
	}

	public String getAcceEstado() {
		return this.acceEstado;
	}

	public void setAcceEstado(String acceEstado) {
		this.acceEstado = acceEstado;
	}

	public Timestamp getAcceFechaCreacion() {
		return this.acceFechaCreacion;
	}

	public void setAcceFechaCreacion(Timestamp acceFechaCreacion) {
		this.acceFechaCreacion = acceFechaCreacion;
	}

	public Timestamp getAcceFechaModificacion() {
		return this.acceFechaModificacion;
	}

	public void setAcceFechaModificacion(Timestamp acceFechaModificacion) {
		this.acceFechaModificacion = acceFechaModificacion;
	}

	public Integer getAcceGarantia() {
		return this.acceGarantia;
	}

	public void setAcceGarantia(Integer acceGarantia) {
		this.acceGarantia = acceGarantia;
	}

	public String getAcceNombre() {
		return this.acceNombre;
	}

	public void setAcceNombre(String acceNombre) {
		this.acceNombre = acceNombre;
	}

	public String getAcceNroSerie() {
		return this.acceNroSerie;
	}

	public void setAcceNroSerie(String acceNroSerie) {
		this.acceNroSerie = acceNroSerie;
	}

	public String getAcceUsuarioCrea() {
		return this.acceUsuarioCrea;
	}

	public void setAcceUsuarioCrea(String acceUsuarioCrea) {
		this.acceUsuarioCrea = acceUsuarioCrea;
	}

	public String getAcceUsuarioModifica() {
		return this.acceUsuarioModifica;
	}

	public void setAcceUsuarioModifica(String acceUsuarioModifica) {
		this.acceUsuarioModifica = acceUsuarioModifica;
	}

	public Marca getMarca() {
		return this.marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Responsable getResponsable() {
		return this.responsable;
	}

	public void setResponsable(Responsable responsable) {
		this.responsable = responsable;
	}

	public SegDependencia getSegDependencia() {
		return this.segDependencia;
	}

	public void setSegDependencia(SegDependencia segDependencia) {
		this.segDependencia = segDependencia;
	}

	public TipoAccesorio getTipoAccesorio() {
		return this.tipoAccesorio;
	}

	public void setTipoAccesorio(TipoAccesorio tipoAccesorio) {
		this.tipoAccesorio = tipoAccesorio;
	}

	public List<AccesorioAtributo> getAccesorioAtributos() {
		return this.accesorioAtributos;
	}

	public void setAccesorioAtributos(List<AccesorioAtributo> accesorioAtributos) {
		this.accesorioAtributos = accesorioAtributos;
	}

	public AccesorioAtributo addAccesorioAtributo(AccesorioAtributo accesorioAtributo) {
		getAccesorioAtributos().add(accesorioAtributo);
		accesorioAtributo.setAccesorio(this);

		return accesorioAtributo;
	}

	public AccesorioAtributo removeAccesorioAtributo(AccesorioAtributo accesorioAtributo) {
		getAccesorioAtributos().remove(accesorioAtributo);
		accesorioAtributo.setAccesorio(null);

		return accesorioAtributo;
	}

	public List<AccesorioBaja> getAccesorioBajas() {
		return this.accesorioBajas;
	}

	public void setAccesorioBajas(List<AccesorioBaja> accesorioBajas) {
		this.accesorioBajas = accesorioBajas;
	}

	public AccesorioBaja addAccesorioBaja(AccesorioBaja accesorioBaja) {
		getAccesorioBajas().add(accesorioBaja);
		accesorioBaja.setAccesorio(this);

		return accesorioBaja;
	}

	public AccesorioBaja removeAccesorioBaja(AccesorioBaja accesorioBaja) {
		getAccesorioBajas().remove(accesorioBaja);
		accesorioBaja.setAccesorio(null);

		return accesorioBaja;
	}

	public List<AccesorioMantenimiento> getAccesorioMantenimientos() {
		return this.accesorioMantenimientos;
	}

	public void setAccesorioMantenimientos(List<AccesorioMantenimiento> accesorioMantenimientos) {
		this.accesorioMantenimientos = accesorioMantenimientos;
	}

	public AccesorioMantenimiento addAccesorioMantenimiento(AccesorioMantenimiento accesorioMantenimiento) {
		getAccesorioMantenimientos().add(accesorioMantenimiento);
		accesorioMantenimiento.setAccesorio(this);

		return accesorioMantenimiento;
	}

	public AccesorioMantenimiento removeAccesorioMantenimiento(AccesorioMantenimiento accesorioMantenimiento) {
		getAccesorioMantenimientos().remove(accesorioMantenimiento);
		accesorioMantenimiento.setAccesorio(null);

		return accesorioMantenimiento;
	}

	public List<BitacoraAccesorio> getBitacoraAccesorios() {
		return this.bitacoraAccesorios;
	}

	public void setBitacoraAccesorios(List<BitacoraAccesorio> bitacoraAccesorios) {
		this.bitacoraAccesorios = bitacoraAccesorios;
	}

	public BitacoraAccesorio addBitacoraAccesorio(BitacoraAccesorio bitacoraAccesorio) {
		getBitacoraAccesorios().add(bitacoraAccesorio);
		bitacoraAccesorio.setAccesorio(this);

		return bitacoraAccesorio;
	}

	public BitacoraAccesorio removeBitacoraAccesorio(BitacoraAccesorio bitacoraAccesorio) {
		getBitacoraAccesorios().remove(bitacoraAccesorio);
		bitacoraAccesorio.setAccesorio(null);

		return bitacoraAccesorio;
	}

	public List<BodegaAccesorio> getBodegaAccesorios() {
		return this.bodegaAccesorios;
	}

	public void setBodegaAccesorios(List<BodegaAccesorio> bodegaAccesorios) {
		this.bodegaAccesorios = bodegaAccesorios;
	}

	public BodegaAccesorio addBodegaAccesorio(BodegaAccesorio bodegaAccesorio) {
		getBodegaAccesorios().add(bodegaAccesorio);
		bodegaAccesorio.setAccesorio(this);

		return bodegaAccesorio;
	}

	public BodegaAccesorio removeBodegaAccesorio(BodegaAccesorio bodegaAccesorio) {
		getBodegaAccesorios().remove(bodegaAccesorio);
		bodegaAccesorio.setAccesorio(null);

		return bodegaAccesorio;
	}

	public List<EquipoAccesorio> getEquipoAccesorios() {
		return this.equipoAccesorios;
	}

	public void setEquipoAccesorios(List<EquipoAccesorio> equipoAccesorios) {
		this.equipoAccesorios = equipoAccesorios;
	}

	public EquipoAccesorio addEquipoAccesorio(EquipoAccesorio equipoAccesorio) {
		getEquipoAccesorios().add(equipoAccesorio);
		equipoAccesorio.setAccesorio(this);

		return equipoAccesorio;
	}

	public EquipoAccesorio removeEquipoAccesorio(EquipoAccesorio equipoAccesorio) {
		getEquipoAccesorios().remove(equipoAccesorio);
		equipoAccesorio.setAccesorio(null);

		return equipoAccesorio;
	}

}