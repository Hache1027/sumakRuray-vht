package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import sumakruray.controller.JSFUtil;
import sumakruray.controller.seguridades.BeanSegLogin;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.ListaIp;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.inventario.managers.ManagerEquipo;
import sumakruray.model.inventario.managers.ManagerListaIp;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanListaIp implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerListaIp managerListaIp;
	@EJB
	private ManagerEquipo managerEquipo;
	@EJB
	private ManagerSeguridades managerSeguridades;
	@Inject
	private BeanSegLogin beanSegLogin;
	@Inject
	private BeanEquipo beanEquipo;
	@Inject
	private BeanAccesorio beanAccesorio;
	// ListaIp
	private List<ListaIp> listaListaIps;
	private List<ListaIp> listaIpsSnEquipo;
	private ListaIp nuevoListaIp;
	private ListaIp escodigoListaIp;
	private ListaIp ListaIpRespaldo;
	private ListaIp edicionListaIp;
	private ListaIp ultimoListaIp;
	private ListaIp edicionipEquipoRespaldo;
	// Equipo
	private List<Equipo> EquiposSnIp;

	//
	// Tiempo
	private Timestamp tiempo;

	public BeanListaIp() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___ARIBUTOS__--******************************************
	// Redireccionamiento a la pagina listaIp

	@PostConstruct
	public void inicializar() throws Exception {
		tiempo = new Timestamp(System.currentTimeMillis());
	}

	public String actionMenuListaIps() {
		listaListaIps = managerListaIp.findAllListaIps();
		return "listaIp";
	}

	// Redireccionamiento a la pagina ListaIp_nuevo
	public String actionMenuNuevoListaIp() {
		nuevoListaIp = new ListaIp();
		nuevoListaIp.setIpsEstado("Activo");
		return "ListaIp_nuevo";
	}

	// Redireccionamiento a la pagina ListaIp_nuevo
	public String actionListaIp() {
		nuevoListaIp = new ListaIp();
		nuevoListaIp.setIpsEstado("Activo");
		return "ListaIp_nuevo";
	}

	// Redireccionamiento a la pagina ListaIp_nuevo
	public void actionGuardarIpEquipo(ListaIp listaIp) {
		edicionipEquipoRespaldo = listaIp;
	}

	// Insertar un nuevo registro de ListaIp
	public void actionListenerInsertarNuevoListaIp() {
		try {

			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);

			nuevoListaIp.setIpsFechaCreacion(tiempo);
			System.out.println(nuevoListaIp.getIpsFechaCreacion() + "fecha");
			nuevoListaIp.setIpsUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());

			managerListaIp.insertarListaIp(nuevoListaIp);
			escodigoListaIp = nuevoListaIp;
			listaListaIps = managerListaIp.findAllListaIps();

			if (beanEquipo.getCabecera() == null) {
				nuevoListaIp = new ListaIp();
				nuevoListaIp.setIpsEstado("Inactivo");
			}

			JSFUtil.crearMensajeINFO("ListaIp insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Metodo para consultar el ultimo registro insertado de Ips
	public void actionUltimoRegistroListaIp() throws Exception {
		ultimoListaIp = managerListaIp.ConsultarUltimaListaIp();
		ultimoListaIp = SeleccionarEquipoCnIp(ultimoListaIp);

	}

	public ListaIp SeleccionarEquipoCnIp(ListaIp listaIp) throws Exception {
		System.out.println(listaIp.getIpsIp() + "ip_llegado");
		int numUltimoIp = 0;
		if (listaIp.getEquipo() == null) {
			numUltimoIp = listaIp.getIpsId() - 1;
			System.out.println(numUltimoIp + "--> id de la Ip");
			listaIp = managerListaIp.findByIdListaIp(numUltimoIp);
			return SeleccionarEquipoCnIp(listaIp);

		}
		System.out.println(listaIp.getIpsIp() + "ip_saliendo" + numUltimoIp);
		return listaIp;

	}

	// Metodo para consultar el ultimo registro insertado de Ips
	public void findAllEquiposSnIp() throws Exception {
		EquiposSnIp = managerEquipo.findEquiposSnId();
	}

	// Metodo para consultar el ultimo registro insertado de Ips
	public void findIdSnEquipo() throws Exception {
		listaIpsSnEquipo = managerListaIp.findIdSnEquipo();
	}

	public void actionPrepararNuevoListaIp() throws Exception {
		nuevoListaIp = new ListaIp();
		nuevoListaIp.setIpsEstado("Activo");
		actionUltimoRegistroListaIp();
		findAllEquiposSnIp();

	}

	// Insertar un nuevo registro de ListaIp
	public void actionSeleccionarNuevoListaIp(ListaIp nuevoListaIps) {
		try {
			// esta parte es cuando selecciono una ip de las ip que estaban sin asignarse
			// aun equipo
			if (ListaIpRespaldo != null) {

				if (!ListaIpRespaldo.equals(nuevoListaIps)) {
					nuevoListaIps.setIpsId(null);
					nuevoListaIps.setIpsEstado("Inactivo");
					nuevoListaIps.setEquipo(null);
					beanEquipo.setEdicionipEquipo(nuevoListaIps);
					nuevoListaIp = nuevoListaIps;
					actionListenerInsertarNuevoListaIp();
				} else if (ListaIpRespaldo.equals(nuevoListaIps)) {
					escodigoListaIp = nuevoListaIps;
					beanEquipo.setEdicionipEquipo(nuevoListaIps);
				}
			} else {
				nuevoListaIp = nuevoListaIps;
				nuevoListaIp.setIpsId(null);
				nuevoListaIp.setEquipo(null);
				actionListenerInsertarNuevoListaIp();
			}

			JSFUtil.crearMensajeINFO("Ip asignado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger ListaIp para editar
	public void actionSeleccionarEdicionListaIp(ListaIp ListaIp) {
		edicionListaIp = ListaIp;
	}

	// actualizar un registro de ListaIp
	public void actionListenerActualizarEdicionListaIp() {
		try {
			managerListaIp.actualizarListaIp(beanSegLogin.getLoginDTO(), edicionListaIp);
			listaListaIps = managerListaIp.findAllListaIps();
			JSFUtil.crearMensajeINFO("ListaIp actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Activar Desactivar ListaIp
	public void actionListenerCambiarEstadoListaIp(ListaIp listaIp, String estado) {
		try {
			managerListaIp.cambiarEstadoListaIp(beanSegLogin.getLoginDTO(), listaIp, estado);
			escodigoListaIp = null;
			listaListaIps = managerListaIp.findAllListaIps();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Seleccionar un nuevo Ip a un Equipo
	public void actionNuevoIpAEquipo() throws Exception {
		PrimeFaces current = PrimeFaces.current();
		System.out.println("-------------------------------entrada de ActionNuevoAccesorio");
		nuevoListaIp = new ListaIp();
		nuevoListaIp.setIpsEstado("Inactivo");

		if (beanEquipo.getCabecera() != null) {
			System.out.println(beanEquipo.getCabecera().getEquiNombre() + "-");
			current.executeScript("PF('dialogoCrearIp').show()");
			findIdSnEquipo();
			actionUltimoRegistroListaIp();
		}

		System.out.println("-------------------------------Salida de ActionnuevoListaIp");
	}

	// Seleccionar una Ip a un Equipo de un IP que estaba creado
	public void actionSeleecionarIpAEquipo(ListaIp listaIp) throws Exception {
		ListaIpRespaldo = listaIp;
		nuevoListaIp = listaIp;
		escodigoListaIp = listaIp;
		beanEquipo.setEdicionipEquipo(listaIp);
		// beanEquipo.setEdicionipEquipo(listaIp);

	}

	// *****************--__Getter and //
	// Setter__--************************************+

	public List<ListaIp> getListaListaIps() {
		return listaListaIps;
	}

	public void setListaListaIps(List<ListaIp> listaListaIps) {
		this.listaListaIps = listaListaIps;
	}

	public ListaIp getNuevoListaIp() {
		return nuevoListaIp;
	}

	public void setNuevoListaIp(ListaIp nuevoListaIp) {
		this.nuevoListaIp = nuevoListaIp;
	}

	public ListaIp getEdicionListaIp() {
		return edicionListaIp;
	}

	public void setEdicionListaIp(ListaIp edicionListaIp) {
		this.edicionListaIp = edicionListaIp;
	}

	public ListaIp getUltimoListaIp() {
		return ultimoListaIp;
	}

	public void setUltimoListaIp(ListaIp ultimoListaIp) {
		this.ultimoListaIp = ultimoListaIp;
	}

	public List<Equipo> getEquiposSnIp() {
		return EquiposSnIp;
	}

	public void setEquiposSnIp(List<Equipo> equiposSnIp) {
		EquiposSnIp = equiposSnIp;
	}

	public List<ListaIp> getListaIpsSnEquipo() {
		return listaIpsSnEquipo;
	}

	public void setListaIpsSnEquipo(List<ListaIp> listaIpsSnEquipo) {
		this.listaIpsSnEquipo = listaIpsSnEquipo;
	}

	public ListaIp getEdicionipEquipoRespaldo() {
		return edicionipEquipoRespaldo;
	}

	public void setEdicionipEquipoRespaldo(ListaIp edicionipEquipoRespaldo) {
		this.edicionipEquipoRespaldo = edicionipEquipoRespaldo;
	}

	public ListaIp getEscodigoListaIp() {
		return escodigoListaIp;
	}

	public void setEscodigoListaIp(ListaIp escodigoListaIp) {
		this.escodigoListaIp = escodigoListaIp;
	}

}
