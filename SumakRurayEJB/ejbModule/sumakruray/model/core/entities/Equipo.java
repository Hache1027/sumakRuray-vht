package sumakruray.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the equipo database table.
 * 
 */
@Entity
@Table(name="equipo")
@NamedQuery(name="Equipo.findAll", query="SELECT e FROM Equipo e")
public class Equipo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="equi_id", unique=true, nullable=false)
	private Integer equiId;

	@Column(name="equi_ano_vida_util")
	private Integer equiAnoVidaUtil;

	@Column(name="equi_cod_bodega", length=50)
	private String equiCodBodega;

	@Column(name="equi_estado", length=50)
	private String equiEstado;

	@Column(name="equi_fecha_creacion")
	private Timestamp equiFechaCreacion;

	@Column(name="equi_fecha_modificacion")
	private Timestamp equiFechaModificacion;

	@Column(name="equi_garantia")
	private Integer equiGarantia;

	@Column(name="equi_nombre", length=100)
	private String equiNombre;

	@Column(name="equi_nro_serie", length=50)
	private String equiNroSerie;

	@Column(name="equi_usuario_crea", length=100)
	private String equiUsuarioCrea;

	@Column(name="equi_usuario_modifica", length=100)
	private String equiUsuarioModifica;

	@Column(name="equi_valor")
	private double equiValor;

	//bi-directional many-to-one association to BitacoraEquipo
	@OneToMany(mappedBy="equipo")
	private List<BitacoraEquipo> bitacoraEquipos;

	//bi-directional many-to-one association to BodegaEquipo
	@OneToMany(mappedBy="equipo")
	private List<BodegaEquipo> bodegaEquipos;

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

	//bi-directional many-to-one association to TipoEquipo
	@ManyToOne
	@JoinColumn(name="tip_equi_id")
	private TipoEquipo tipoEquipo;

	//bi-directional many-to-one association to EquipoAccesorio
	@OneToMany(mappedBy="equipo", cascade = CascadeType.ALL)
	private List<EquipoAccesorio> equipoAccesorios;

	//bi-directional many-to-one association to EquipoAtributo
	@OneToMany(mappedBy="equipo", cascade = CascadeType.ALL)
	private List<EquipoAtributo> equipoAtributos;

	//bi-directional many-to-one association to EquipoBaja
	@OneToMany(mappedBy="equipo")
	private List<EquipoBaja> equipoBajas;

	//bi-directional many-to-one association to EquipoMantenimiento
	@OneToMany(mappedBy="equipo")
	private List<EquipoMantenimiento> equipoMantenimientos;

	//bi-directional many-to-one association to ListaIp
	@OneToMany(mappedBy="equipo")
	private List<ListaIp> listaIps;

	public Equipo() {
	}

	public Integer getEquiId() {
		return this.equiId;
	}

	public void setEquiId(Integer equiId) {
		this.equiId = equiId;
	}

	public Integer getEquiAnoVidaUtil() {
		return this.equiAnoVidaUtil;
	}

	public void setEquiAnoVidaUtil(Integer equiAnoVidaUtil) {
		this.equiAnoVidaUtil = equiAnoVidaUtil;
	}

	public String getEquiCodBodega() {
		return this.equiCodBodega;
	}

	public void setEquiCodBodega(String equiCodBodega) {
		this.equiCodBodega = equiCodBodega;
	}

	public String getEquiEstado() {
		return this.equiEstado;
	}

	public void setEquiEstado(String equiEstado) {
		this.equiEstado = equiEstado;
	}

	public Timestamp getEquiFechaCreacion() {
		return this.equiFechaCreacion;
	}

	public void setEquiFechaCreacion(Timestamp equiFechaCreacion) {
		this.equiFechaCreacion = equiFechaCreacion;
	}

	public Timestamp getEquiFechaModificacion() {
		return this.equiFechaModificacion;
	}

	public void setEquiFechaModificacion(Timestamp equiFechaModificacion) {
		this.equiFechaModificacion = equiFechaModificacion;
	}

	public Integer getEquiGarantia() {
		return this.equiGarantia;
	}

	public void setEquiGarantia(Integer equiGarantia) {
		this.equiGarantia = equiGarantia;
	}

	public String getEquiNombre() {
		return this.equiNombre;
	}

	public void setEquiNombre(String equiNombre) {
		this.equiNombre = equiNombre;
	}

	public String getEquiNroSerie() {
		return this.equiNroSerie;
	}

	public void setEquiNroSerie(String equiNroSerie) {
		this.equiNroSerie = equiNroSerie;
	}

	public String getEquiUsuarioCrea() {
		return this.equiUsuarioCrea;
	}

	public void setEquiUsuarioCrea(String equiUsuarioCrea) {
		this.equiUsuarioCrea = equiUsuarioCrea;
	}

	public String getEquiUsuarioModifica() {
		return this.equiUsuarioModifica;
	}

	public void setEquiUsuarioModifica(String equiUsuarioModifica) {
		this.equiUsuarioModifica = equiUsuarioModifica;
	}

	public double getEquiValor() {
		return this.equiValor;
	}

	public void setEquiValor(double equiValor) {
		this.equiValor = equiValor;
	}

	public List<BitacoraEquipo> getBitacoraEquipos() {
		return this.bitacoraEquipos;
	}

	public void setBitacoraEquipos(List<BitacoraEquipo> bitacoraEquipos) {
		this.bitacoraEquipos = bitacoraEquipos;
	}

	public BitacoraEquipo addBitacoraEquipo(BitacoraEquipo bitacoraEquipo) {
		getBitacoraEquipos().add(bitacoraEquipo);
		bitacoraEquipo.setEquipo(this);

		return bitacoraEquipo;
	}

	public BitacoraEquipo removeBitacoraEquipo(BitacoraEquipo bitacoraEquipo) {
		getBitacoraEquipos().remove(bitacoraEquipo);
		bitacoraEquipo.setEquipo(null);

		return bitacoraEquipo;
	}

	public List<BodegaEquipo> getBodegaEquipos() {
		return this.bodegaEquipos;
	}

	public void setBodegaEquipos(List<BodegaEquipo> bodegaEquipos) {
		this.bodegaEquipos = bodegaEquipos;
	}

	public BodegaEquipo addBodegaEquipo(BodegaEquipo bodegaEquipo) {
		getBodegaEquipos().add(bodegaEquipo);
		bodegaEquipo.setEquipo(this);

		return bodegaEquipo;
	}

	public BodegaEquipo removeBodegaEquipo(BodegaEquipo bodegaEquipo) {
		getBodegaEquipos().remove(bodegaEquipo);
		bodegaEquipo.setEquipo(null);

		return bodegaEquipo;
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

	public TipoEquipo getTipoEquipo() {
		return this.tipoEquipo;
	}

	public void setTipoEquipo(TipoEquipo tipoEquipo) {
		this.tipoEquipo = tipoEquipo;
	}

	public List<EquipoAccesorio> getEquipoAccesorios() {
		return this.equipoAccesorios;
	}

	public void setEquipoAccesorios(List<EquipoAccesorio> equipoAccesorios) {
		this.equipoAccesorios = equipoAccesorios;
	}

	public EquipoAccesorio addEquipoAccesorio(EquipoAccesorio equipoAccesorio) {
		getEquipoAccesorios().add(equipoAccesorio);
		equipoAccesorio.setEquipo(this);

		return equipoAccesorio;
	}

	public EquipoAccesorio removeEquipoAccesorio(EquipoAccesorio equipoAccesorio) {
		getEquipoAccesorios().remove(equipoAccesorio);
		equipoAccesorio.setEquipo(null);

		return equipoAccesorio;
	}

	public List<EquipoAtributo> getEquipoAtributos() {
		return this.equipoAtributos;
	}

	public void setEquipoAtributos(List<EquipoAtributo> equipoAtributos) {
		this.equipoAtributos = equipoAtributos;
	}

	public EquipoAtributo addEquipoAtributo(EquipoAtributo equipoAtributo) {
		getEquipoAtributos().add(equipoAtributo);
		equipoAtributo.setEquipo(this);

		return equipoAtributo;
	}

	public EquipoAtributo removeEquipoAtributo(EquipoAtributo equipoAtributo) {
		getEquipoAtributos().remove(equipoAtributo);
		equipoAtributo.setEquipo(null);

		return equipoAtributo;
	}

	public List<EquipoBaja> getEquipoBajas() {
		return this.equipoBajas;
	}

	public void setEquipoBajas(List<EquipoBaja> equipoBajas) {
		this.equipoBajas = equipoBajas;
	}

	public EquipoBaja addEquipoBaja(EquipoBaja equipoBaja) {
		getEquipoBajas().add(equipoBaja);
		equipoBaja.setEquipo(this);

		return equipoBaja;
	}

	public EquipoBaja removeEquipoBaja(EquipoBaja equipoBaja) {
		getEquipoBajas().remove(equipoBaja);
		equipoBaja.setEquipo(null);

		return equipoBaja;
	}

	public List<EquipoMantenimiento> getEquipoMantenimientos() {
		return this.equipoMantenimientos;
	}

	public void setEquipoMantenimientos(List<EquipoMantenimiento> equipoMantenimientos) {
		this.equipoMantenimientos = equipoMantenimientos;
	}

	public EquipoMantenimiento addEquipoMantenimiento(EquipoMantenimiento equipoMantenimiento) {
		getEquipoMantenimientos().add(equipoMantenimiento);
		equipoMantenimiento.setEquipo(this);

		return equipoMantenimiento;
	}

	public EquipoMantenimiento removeEquipoMantenimiento(EquipoMantenimiento equipoMantenimiento) {
		getEquipoMantenimientos().remove(equipoMantenimiento);
		equipoMantenimiento.setEquipo(null);

		return equipoMantenimiento;
	}

	public List<ListaIp> getListaIps() {
		return this.listaIps;
	}

	public void setListaIps(List<ListaIp> listaIps) {
		this.listaIps = listaIps;
	}

	public ListaIp addListaIp(ListaIp listaIp) {
		getListaIps().add(listaIp);
		listaIp.setEquipo(this);

		return listaIp;
	}

	public ListaIp removeListaIp(ListaIp listaIp) {
		getListaIps().remove(listaIp);
		listaIp.setEquipo(null);

		return listaIp;
	}

}