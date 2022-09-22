package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import sumakruray.controller.JSFUtil;
import sumakruray.controller.seguridades.BeanSegLogin;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioMantenimiento;
import sumakruray.model.core.entities.BodegaAccesorio;
import sumakruray.model.core.entities.BodegaEquipo;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoAccesorio;
import sumakruray.model.core.entities.EquipoMantenimiento;
import sumakruray.model.core.entities.Mantenimiento;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.core.entities.Proveedor;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.inventario.managers.ManagerAccesorio;
import sumakruray.model.inventario.managers.ManagerBodega;
import sumakruray.model.inventario.managers.ManagerEquipo;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanBodega implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerSeguridades managerSeguridades;
	@EJB
	private ManagerAccesorio managerAccesorio;
	@EJB
	private ManagerEquipo managerEquipo;
	@EJB
	private ManagerBodega managerBodega;
	@Inject
	private BeanSegLogin beanSegLogin;
	// Bodega
	private BodegaEquipo nuevoBodegaEquipo;
	private BodegaAccesorio nuevoBodegaAccesorio;
	private BodegaAccesorio edicionBodegaAccesorio;

	// Equipo
	private List<Equipo> listaEquipoBodegas;
	private BodegaEquipo edicionEquipoBodega;
	private Equipo equipo;
	//

	// Accesorios
	private List<Accesorio> listaAccesorioBodegas;
	private List<Accesorio> listaAccesorioBodegasBuscado;
	private List<Accesorio> listaAccesorioCEBodegas;
	private BodegaAccesorio edicionAccesorioBodega;
	private Accesorio accesorio;
	//
	// Tiempo
	private Timestamp tiempo;
	//

	public BeanBodega() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() throws Exception {
		listaEquipoBodegas = managerEquipo.findWhereByEquiEstado("Inactivo");
		listaAccesorioBodegas = managerAccesorio.findWhereByAcceEstado("Inactivo");
		tiempo = new Timestamp(System.currentTimeMillis());

	}


	/**
	 * prepara variable para ventanas emergentes para un nuevo BodegaAccesorio
	 */
	public void actionNuevoBodegaAccesorio() {
		nuevoBodegaAccesorio = new BodegaAccesorio();
	}
	

	/**
	 * prepara variable para ventanas emergentes para un nuevo BodegaEquipo
	 */
	public void actionNuevoBodegaEquipo() {
		nuevoBodegaEquipo = new BodegaEquipo();
	}

	/**
	 * redireccionamiento a la pagina de menu_bod_equipo
	 * 
	 * @return
	 * @throws Exception
	 */
	public String actionSelectionEquiposInactivos() throws Exception {
		listaEquipoBodegas = managerEquipo.findWhereByEquiEstado("Inactivo");
		return "menu_bod_equipo";
	}

	/**
	 * redireccionamiento a la pagina de menu_bod_accesorio
	 * 
	 * @return
	 * @throws Exception
	 */
	public String actionSelectionAccesoriosInactivos() throws Exception {
		listaAccesorioBodegas = managerAccesorio.findWhereByAcceEstado("Inactivo");
		return "menu_bod_accesorio";
	}

	/**
	 * redireccionamiento a la pagina de menu_bod_accesorio_CE
	 * 
	 * @return
	 * @throws Exception
	 */
	public String actionSelectionAccesoriosInactivosCE() throws Exception {
		listaAccesorioCEBodegas = managerAccesorio.findWhereByAcceEstado("Inactivo_Equipo");
		return "menu_bod_accesorio_CE";
	}
	/*
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	// ****************************---__Bodega e Equipo___---***********

	// Llenar los datos para el nuevo registro de EquipoBodega
	public void actionListenerInsertarNuevoEquipoABodega(Equipo equipo) {

		try {
			nuevoBodegaEquipo = new BodegaEquipo();
			nuevoBodegaEquipo.setBodFechaCreacion(tiempo);
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoBodegaEquipo.setBodUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			nuevoBodegaEquipo.setEquipo(equipo);

			managerBodega.insertarEquipoBodega(nuevoBodegaEquipo);
			listaEquipoBodegas = managerEquipo.findWhereByEquiEstado("Inactivo");
			nuevoBodegaEquipo = new BodegaEquipo();

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	// ****************************---__Bodega e Accesorio___---***********

	// /Llenar los datos para el nuevo registro de BodegaAccesorio
	public void actionListenerInsertarNuevoAccesorioABodega(Accesorio accesorio) {
		try {

			System.out.println("uuuuuuuuuuuu");
			nuevoBodegaAccesorio = new BodegaAccesorio();
			nuevoBodegaAccesorio.setBodFechaCreacion(tiempo);
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoBodegaAccesorio.setBodUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			nuevoBodegaAccesorio.setAccesorio(accesorio);
			System.out.println("pppppppppppppppppp");
			System.out.println("mmmmmmmmmmmmmmmmmmmmmm");
			managerBodega.insertarAccesorioBodega(nuevoBodegaAccesorio);
			listaAccesorioBodegas = managerAccesorio.findWhereByAcceEstado("Inactivo");
			nuevoBodegaAccesorio = new BodegaAccesorio();
			System.out.println("ooooooooooooooo");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * // Actualizar el estado de un registro de AccesorioBodega public void
	 * actionListenerActualizarBodegaPorAccesorio(BodegaAccesorio bodegaAccesorio,
	 * String estado) throws Exception {
	 * System.out.println(bodegaAccesorio.getBodEstado() + "uuu-------------2");
	 * managerBodega.cambiarEstadoBodegaAccesorio(bodegaAccesorio.getBodId(),
	 * estado);
	 * 
	 * }
	 */
	/*
	 * // Actualizar el estado de un registro de EquipoBodega public void
	 * actionListenerActualizarBodegaPorEquipo(BodegaEquipo bodegaEquipo, String
	 * estado) throws Exception { System.out.println(bodegaEquipo.getBodEstado() +
	 * "uuu-------------2");
	 * managerBodega.cambiarEstadoBodegaEquipo(bodegaEquipo.getBodId(), estado);
	 * 
	 * }
	 */
	/*
	 * // Buscar el Accesorio con un estado para luego activar public void
	 * actionListenerBuscarBodegaPorAccesorio(Accesorio accesorio, String estado,
	 * String cambio) throws Exception { listaAccesorioBodegasBuscado =
	 * managerBodega.findWhereByAcceIdBodega(accesorio.getAcceId(), estado);
	 * System.out.println(listaAccesorioBodegasBuscado.get(0).getBodEstado() +
	 * "+++++++++++");
	 * 
	 * System.out.println(listaAccesorioBodegasBuscado.size() + "qqqqqqqqqqqqqqqq");
	 * actionListenerActualizarBodegaPorAccesorio(listaAccesorioBodegasBuscado.get(0
	 * ), cambio); System.out.println("¿¿¿¿¿¿¿¿¿¿¿");
	 * 
	 * }
	 */

	// GETERS AND SETTERS

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public BodegaEquipo getNuevoBodegaEquipo() {
		return nuevoBodegaEquipo;
	}

	public void setNuevoBodegaEquipo(BodegaEquipo nuevoBodegaEquipo) {
		this.nuevoBodegaEquipo = nuevoBodegaEquipo;
	}

	public Accesorio getAccesorio() {
		return accesorio;
	}

	public void setAccesorio(Accesorio accesorio) {
		this.accesorio = accesorio;
	}

	public BodegaAccesorio getNuevoBodegaAccesorio() {
		return nuevoBodegaAccesorio;
	}

	public void setNuevoBodegaAccesorio(BodegaAccesorio nuevoBodegaAccesorio) {
		this.nuevoBodegaAccesorio = nuevoBodegaAccesorio;
	}

	public List<Equipo> getListaEquipoBodegas() {
		return listaEquipoBodegas;
	}

	public void setListaEquipoBodegas(List<Equipo> listaEquipoBodegas) {
		this.listaEquipoBodegas = listaEquipoBodegas;
	}

	public List<Accesorio> getListaAccesorioBodegas() {
		return listaAccesorioBodegas;
	}

	public void setListaAccesorioBodegas(List<Accesorio> listaAccesorioBodegas) {
		this.listaAccesorioBodegas = listaAccesorioBodegas;
	}

	public List<Accesorio> getListaAccesorioCEBodegas() {
		return listaAccesorioCEBodegas;
	}

	public void setListaAccesorioCEBodegas(List<Accesorio> listaAccesorioCEBodegas) {
		this.listaAccesorioCEBodegas = listaAccesorioCEBodegas;
	}

}
