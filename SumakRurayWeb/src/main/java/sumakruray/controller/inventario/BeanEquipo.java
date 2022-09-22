package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import sumakruray.controller.JSFUtil;
import sumakruray.controller.seguridades.BeanSegLogin;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioAtributo;
import sumakruray.model.core.entities.AccesorioMantenimiento;
import sumakruray.model.core.entities.Atributo;
import sumakruray.model.core.entities.BodegaAccesorio;
import sumakruray.model.core.entities.BodegaEquipo;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoAccesorio;
import sumakruray.model.core.entities.EquipoAtributo;
import sumakruray.model.core.entities.EquipoMantenimiento;
import sumakruray.model.core.entities.ListaIp;
import sumakruray.model.core.entities.Marca;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.core.entities.Proveedor;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.core.entities.SegDependencia;
import sumakruray.model.inventario.managers.ManagerAccesorio;
import sumakruray.model.inventario.managers.ManagerBodega;
import sumakruray.model.inventario.managers.ManagerDependencia;
import sumakruray.model.inventario.managers.ManagerEquipo;
import sumakruray.model.inventario.managers.ManagerListaIp;
import sumakruray.model.inventario.managers.ManagerMantenimiento;
import sumakruray.model.inventario.managers.ManagerMarca;
import sumakruray.model.inventario.managers.ManagerProveedor;
import sumakruray.model.inventario.managers.ManagerResponsable;
import sumakruray.model.inventario.managers.ManagerTipoAE;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanEquipo implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerMantenimiento managerMantenimiento;
	@EJB
	private ManagerListaIp managerListaIp;
	@EJB
	private ManagerTipoAE managerTipoAE;
	@EJB
	private ManagerResponsable managerResponsable;
	@EJB
	private ManagerProveedor managerProveedor;
	@EJB
	private ManagerBodega managerBodega;
	@EJB
	private ManagerAccesorio managerAccesorio;
	@EJB
	private ManagerDependencia managerDependencia;
	@EJB
	private ManagerMarca managerMarca;
	@EJB
	private ManagerSeguridades managerSeguridades;
	@EJB
	private ManagerEquipo managerEquipo;
	@Inject
	private BeanBodega beanBodega;
	@Inject
	private BeanListaIp beanListaIp;
	@Inject
	private BeanSegLogin beanSegLogin;
	@Inject
	private BeanAccesorio beanAccesorio;
	@Inject
	private BeanMantenimiento beanMantenimiento;
	@Inject
	private BeanDependencia beanDependencia;
	@Inject
	private BeanProveedor beanProveedor;
	@Inject
	private BeanResponsable beanResponsable;
	@Inject
	private BeanMarca beanMarca;
	@Inject
	private BeanAtributo beanAtributo;
	// Equipos
	private List<Equipo> listaEquiposActivos;
	private List<Equipo> listaEquiposInactivos;
	private List<Equipo> listaEquiposMantenimientos;
	private List<Equipo> listaEquiposBajas;
	private List<Equipo> listaEquiposAll;
	// BodegaEquipo
	private List<Equipo> bodegaEquipoBuscado;
	private Equipo nuevoEquipo;
	private Equipo edicionEquipo;
	private Equipo vistaEquipo;
	private Equipo Equipo;
	private Equipo equipoBodega;
	private Equipo equipoDevuelto;

	// Cabecera
	private Equipo cabecera;

	// Equipo-Accesorio
	private List<EquipoAccesorio> listaAccesoriosEquipo;

	// Equipo-Atributo
	private List<EquipoAtributo> listaAtributosEquipo;

	// Dependencias
	private int idSegDependenciaSeleccionado;
	// Responsable
	private int respIdSeleccionado;
	// Proveedor;
	private int proIdSeleccionado;
	// Atributos
	private int atriIdSeleccionado;
	// Marcas
	private int marIdSeleccionado;
	// Bodega

	// Accesorios
	private int acceIdSeleccionado;

	// EXTRAS
	private int tipEquiIdSeleccionado;
	private int tipAccIdSeleccionado;
	private String tipoAccion;
	private int equiIdSeleccionado;

	// Tiempo
	private Timestamp tiempo;
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
	 */

	private List<BodegaAccesorio> listaAccesoriosBodega;

	private BodegaEquipo bodegaEquipo;

	// BOdegaAccesorio
	private List<BodegaAccesorio> bodegaAccesorioBuscado;
	private BodegaAccesorio bodegaAccesorioSeleccionado;
//Mnatenimiento
	private List<EquipoMantenimiento> listaEquiMan;

	//

	// Valor o descripcion del atributo
	private String valorAtributo;

	// Lista IP
	private ListaIp nuevaIp;
	private ListaIp ipEquipo;
	private ListaIp edicionipEquipo;
	private ListaIp edicionIpEquipoRespaldo;
	// varios
	private boolean verificador;
	private String equipoEstado;
	private boolean enabled;
	private boolean verificador_mantenimiento;

	public BeanEquipo() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() throws Exception {
		actionRecargarListaEquiposActivos();
		tiempo = new Timestamp(System.currentTimeMillis());
		verificador = false;
		System.out.println(verificador + "----------------------------");
		cabecera = null;

	}

	// ****---___METODOS_PARA_UTILIZAR_EN_EL_MISMO_BEANEQUIPO___---***********
	/**
	 * Cargar la lista de Equipos segun su estado
	 * 
	 * @throws Exception
	 */
	public void actionRecargarListaEquiposActivos() throws Exception {
		listaEquiposActivos = managerEquipo.findWhereByEquiEstado("Activo");
	}

	public void actionRecargarListaEquiposAll() throws Exception {
		listaEquiposAll = managerEquipo.findAllEquipos();
	}

	public void actionRecargarListaEquiposInactivos() throws Exception {
		listaEquiposInactivos = managerEquipo.findWhereByEquiEstado("Inactivo");
	}

	public void actionRecargarListaEquiposMantenimientos() throws Exception {
		listaEquiposMantenimientos = managerEquipo.findWhereByEquiEstado("Mantenimiento");
	}

	public void actionRecargarListaEquiposBjas() throws Exception {
		listaEquiposBajas = managerEquipo.findWhereByEquiEstado("De Baja");
	}

	/**
	 * Cargar las Variables para crear un Accesorio
	 * 
	 * @throws Exception
	 */
	public void cargarVaribalesParaEquipo() throws Exception {
		beanDependencia.actionConsultarAllDependencias();
		beanResponsable.actionConsultarAllResponsable();
		beanProveedor.actionConsultarAllProveedor();
		beanMarca.actionConsultarAllMarca();
		beanAtributo.actionConsultarAllAtributo();
	}

	/**
	 * Inicializa o vacia las Variabes de Bean Equipo
	 */
	public void VaciarVariablesEquipo() {

		nuevoEquipo = new Equipo();
		nuevoEquipo.setEquiEstado("Activo");
		respIdSeleccionado = 0;
		proIdSeleccionado = 0;
		marIdSeleccionado = 0;
		idSegDependenciaSeleccionado = 0;
		acceIdSeleccionado = 0;
		atriIdSeleccionado = 0;
		valorAtributo = "";
		vistaEquipo = null;
		cabecera = null;

	}

	/**
	 * Buscar Datos de un Equipo
	 * 
	 * @param equipo
	 * @return
	 * @throws Exception
	 */
	public Equipo ActionBuscarAcceAtriOfEquipo(Equipo equipo) throws Exception {
		Equipo = new Equipo();
		Equipo = equipo;
		int equiId = Equipo.getEquiId();
		Equipo.setEquipoAccesorios(managerEquipo.findByEquiIdSeleccionado(equiId));
		Equipo.setEquipoAtributos(managerEquipo.findByEquiIdSeleccionadoAtributo(equiId));
		if (managerListaIp.findListaIpByEquiId(equiId) != null) {
			ipEquipo = managerListaIp.findListaIpByEquiId(equiId);
			edicionipEquipo = ipEquipo;
			edicionIpEquipoRespaldo = managerListaIp.findListaIpByEquiId(equiId);

		}

		return Equipo;
	}

	// METODO CRUD
	/**
	 * Metodo para preparar varibales para un nuevo equipo y redireecion a la pagina
	 * de equipo_nuevo
	 * 
	 * @param tipEquiIdSeleccionados
	 * @return
	 * @throws Exception
	 */

	public String actionMenuEquipos(int tipEquiIdSeleccionados) throws Exception {
		tipEquiIdSeleccionado = tipEquiIdSeleccionados;
		VaciarVariablesEquipo();
		cargarVaribalesParaEquipo();
		verificador = true;
		return "equipo_nuevo";
	}

	/**
	 * Metodo para asignar datos a un nuevo equipo
	 * 
	 * @param tipoObjeto
	 * @throws Exception
	 */
	public void AsignarValoresNuevoEquipos(String tipoObjeto) throws Exception {
		nuevoEquipo.setResponsable(managerResponsable.findByIdResponsable(respIdSeleccionado));
		nuevoEquipo.setProveedor(managerProveedor.findByIdProveedor(proIdSeleccionado));
		nuevoEquipo.setMarca(managerMarca.findByIdMarca(marIdSeleccionado));
		nuevoEquipo.setSegDependencia(managerDependencia.findByIdSegDependencia(idSegDependenciaSeleccionado));
		nuevoEquipo.setTipoEquipo(managerTipoAE.findByIdTipoEquipo(tipEquiIdSeleccionado));
		nuevoEquipo.setEquiFechaCreacion(tiempo);
		int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
		Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
		nuevoEquipo.setEquiUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
		System.out.println(nuevoEquipo.getEquiNombre() + "----------" + respIdSeleccionado);
		System.out.println(acceIdSeleccionado + "****************************************************");
		System.out.println(tipoObjeto + "0");
		cabecera = managerEquipo.adicionarAccesorioAtributo(cabecera, nuevoEquipo, acceIdSeleccionado,
				atriIdSeleccionado, valorAtributo, tipoObjeto);
	}

	/**
	 * Pepara los nuevos datos del Registro de Equipo
	 * 
	 * @param tipoObjeto
	 * @throws Exception
	 */
	public void actionListenerAdicionarAccesorioAtributo(String tipoObjeto) throws Exception {
		// método que actua cuando crea un nuevo Accesorio mientras esta registrando un
		// nuevo equipo

		if (beanAccesorio.getAccesorioCreado() != null) {
			acceIdSeleccionado = beanAccesorio.getAccesorioCreado().getAcceId();
		}

		// Metodo para verificar si el accesorio o atributo existe en la lista

		String add = "ADD";
		if (cabecera == null) {
			AsignarValoresNuevoEquipos(tipoObjeto);
		} else if (cabecera != null) {
			if (tipoObjeto.equals("Atributo")) {
				for (int i = 0; i < cabecera.getEquipoAtributos().size(); i++) {
					int id = cabecera.getEquipoAtributos().get(i).getAtributo().getAtriId();
					if (id == atriIdSeleccionado) {

						JSFUtil.crearMensajeWARN("Atributo ya seleccionado ");
						add = "no";
						break;
					}
				}
			} else if (tipoObjeto.equals("Accesorio")) {
				for (int i = 0; i < cabecera.getEquipoAccesorios().size(); i++) {
					int id = cabecera.getEquipoAccesorios().get(i).getAccesorio().getAcceId();
					if (id == acceIdSeleccionado) {

						JSFUtil.crearMensajeWARN("Accesorio ya seleccionado ");
						add = "no";
						break;
					}
				}
			}

			if (add.equals("ADD")) {
				System.out.println(tipoObjeto + "1");
				cabecera = managerEquipo.adicionarAccesorioAtributo(cabecera, nuevoEquipo, acceIdSeleccionado,
						atriIdSeleccionado, valorAtributo, tipoObjeto);
				// Insertar Atributos desde Mantenimiento
				if (beanMantenimiento.getEquipoDevuelto() != null) {
					beanMantenimiento.setEquipoDevuelto(cabecera);
				} else {
					if (cabecera.getEquiId() != null) {

						bodegaEquipoBuscado = managerBodega.findWhereByEquiIdBodega(cabecera.getEquiId(), "Inactivo");
						bodegaAccesorioBuscado = managerBodega.findWhereByAcceIdBodegaOne(acceIdSeleccionado);
						Accesorio accesorio = managerAccesorio.findByIdAccesorio(acceIdSeleccionado);
						if (bodegaAccesorioBuscado.size() > 0 && bodegaEquipoBuscado.size() > 0) {

							accesorio.setAcceEstado("Inactivo_Equipo");
							managerAccesorio.cambiarEstadoAccesorioEnEquipo(beanSegLogin.getLoginDTO(), accesorio,
									cabecera, "Inactivo_Equipo");
							// beanBodega.actionListenerActualizarBodegaPorAccesorio(bodegaAccesorioBuscado.get(0),
							// "Inactivo_Equipo");
						} else {
							accesorio.setAcceEstado("Activo");
							System.out.println(accesorio.getAcceNombre() + "--------fff----");
							managerAccesorio.cambiarEstadoAccesorioEnEquipo(beanSegLogin.getLoginDTO(), accesorio,
									cabecera, "Activo");
						}

					}
					beanBodega.actionSelectionAccesoriosInactivos();
				}

				if (nuevoEquipo != null) {
					if (nuevoEquipo.getSegDependencia() != null) {
						if (idSegDependenciaSeleccionado != nuevoEquipo.getSegDependencia().getIdSegDependencia()) {
							cabecera.setSegDependencia(
									managerDependencia.findByIdSegDependencia(idSegDependenciaSeleccionado));
							System.out.println("1...");
						}
					}
					if (nuevoEquipo.getResponsable() != null) {
						if (respIdSeleccionado != nuevoEquipo.getResponsable().getRespId()) {
							cabecera.setResponsable(managerResponsable.findByIdResponsable(respIdSeleccionado));
							System.out.println("2...");
						}
					}
					if (proIdSeleccionado != nuevoEquipo.getProveedor().getProId()) {
						cabecera.setProveedor(managerProveedor.findByIdProveedor(proIdSeleccionado));
						System.out.println("3...");
					}
					if (marIdSeleccionado != nuevoEquipo.getMarca().getMarId()) {
						cabecera.setMarca(managerMarca.findByIdMarca(marIdSeleccionado));
						System.out.println("4...");
					}
				}
			}
			verificador = false;
		}

		beanAccesorio.setAccesorioCreado(null);
		actionRecargarListaEquiposActivos();

	}

	/**
	 * Quitar accesorios de la lista Accesorios de un Equipo
	 * 
	 * @param equiId
	 * @param acceId
	 * @throws Exception
	 */
	public void actionListenerQuitarAccesorio(int equiId, int acceId) throws Exception {
		if (cabecera == null) {
			JSFUtil.crearMensajeWARN("No hay Accesorios que eliminar");
		} else if (cabecera != null) {
			for (int i = 0; i < cabecera.getEquipoAccesorios().size(); i++) {
				if ((int) cabecera.getEquipoAccesorios().get(i).getAccesorio().getAcceId() == acceId) {
					beanAccesorio.actionListenerInsertarNuevoAccesorioABodega(
							cabecera.getEquipoAccesorios().get(i).getAccesorio(), cabecera);
					cabecera = managerEquipo.QuitarAccesorio(cabecera, i);
					System.out.println("''''''''''''''''''''''''''''");
					JSFUtil.crearMensajeWARN("Accesorio quitado de la lista");
					break;
				}
			}
			beanBodega.actionSelectionAccesoriosInactivos();
		}
	}

	/**
	 * Quitar atributos de lista de Atributos de un Equipo
	 * 
	 * @param equiId
	 * @param atriId
	 * @throws Exception
	 */

	public void actionListenerQuitarAtributos(int equiId, int atriId) throws Exception {
		if (cabecera == null) {
			JSFUtil.crearMensajeWARN("No hay Atributos que eliminar");
		} else if (cabecera != null) {
			for (int i = 0; i < cabecera.getEquipoAtributos().size(); i++) {
				if ((int) cabecera.getEquipoAtributos().get(i).getAtributo().getAtriId() == atriId) {
					List<EquipoAtributo> equipoAtributo = managerEquipo.findWhereByEquiAtriId(equiId, atriId);
					if (equipoAtributo.size() > 0) {
						managerEquipo.eliminarAtributoEquipo(cabecera.getEquipoAtributos().get(i).getEquiAtriId());
					}
					cabecera = managerEquipo.QuitarAtributo(cabecera, i);
					JSFUtil.crearMensajeWARN("Atributo quitado de la lista");
					break;
				}
			}
		}
	}

	/**
	 * Insertar un nuevo Registro de Equipo
	 */
	public void actionListenerInsertarNuevoEquipo() {
		try {
			if (cabecera == null) {
				AsignarValoresNuevoEquipos("");
			}
			managerEquipo.registrarEquipo(beanSegLogin.getLoginDTO(), cabecera);

			JSFUtil.crearMensajeINFO("Equipo creado ¡Creado exitosamente!");
			if (beanListaIp.getEscodigoListaIp() != null) {
				beanListaIp.getEscodigoListaIp().setEquipo(cabecera);
				beanListaIp.getEscodigoListaIp().setIpsEstado("Activo");
				beanListaIp.actionListenerCambiarEstadoListaIp(beanListaIp.getEscodigoListaIp(), "Activo");
			}

			VaciarVariablesEquipo();
			actionRecargarListaEquiposActivos();

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Actualizar pagina para un nuevo Registrode Equipo
	 * 
	 * @return
	 */
	public String actionVolverAMenuEquipo() {
		VaciarVariablesEquipo();
		return ("menu_bod_equipo");
	}

	/**
	 * Buscar Datos de Equipo y Redireccionamiento a la pagina equipo_vista
	 * 
	 * @param vistaEquipos
	 * @return
	 * @throws Exception
	 */
	public String actionVistaEquipo(Equipo vistaEquipos) throws Exception {
		vistaEquipo = new Equipo();
		vistaEquipo = ActionBuscarAcceAtriOfEquipo(vistaEquipos);
		return "equipo_vista";
	}

	/**
	 * Buscar equipo eleccionado para colocar un accesorio
	 * 
	 * @param equipo
	 * @return
	 * @throws Exception
	 */
	public void actionfindEquipo(int equiIdSeleccionados) throws Exception {

		equipoDevuelto = ActionBuscarAcceAtriOfEquipo(managerEquipo.findByIdEquipo(equiIdSeleccionados));
	}

	/**
	 * Buscar Datos de Equipo y Redireccionamiento a la pagina equipo_edicion
	 * 
	 * @param edicionEquipos
	 * @return
	 * @throws Exception
	 */
	public String actionEdicionEquipo(Equipo edicionEquipos) throws Exception {
		edicionEquipo = ActionBuscarAcceAtriOfEquipo(edicionEquipos);
		cargarVaribalesParaEquipo();
		if (edicionEquipos.getResponsable() != null) {
			respIdSeleccionado = edicionEquipos.getResponsable().getRespId();
		}
		if (edicionEquipos.getProveedor() != null) {
			proIdSeleccionado = edicionEquipos.getProveedor().getProId();
		}
		if (edicionEquipos.getMarca() != null) {
			marIdSeleccionado = edicionEquipos.getMarca().getMarId();
		}
		if (edicionEquipos.getSegDependencia() != null) {
			idSegDependenciaSeleccionado = edicionEquipos.getSegDependencia().getIdSegDependencia();
		}

		nuevoEquipo = edicionEquipo;
		cabecera = edicionEquipo;
		return "equipo_edicion";
	}

	/**
	 * Actualizar un registro de un Equipo
	 */
	public void actionListenerActualizarEdicionEquipo() {
		try {
			cabecera.setEquiEstado(edicionEquipo.getEquiEstado());
			edicionEquipo = cabecera;
			edicionEquipo.setResponsable(managerResponsable.findByIdResponsable(respIdSeleccionado));
			edicionEquipo.setSegDependencia(managerDependencia.findByIdSegDependencia(idSegDependenciaSeleccionado));
			edicionEquipo.setProveedor(managerProveedor.findByIdProveedor(proIdSeleccionado));
			edicionEquipo.setMarca(managerMarca.findByIdMarca(marIdSeleccionado));
			edicionEquipo.setTipoEquipo(managerTipoAE.findByIdTipoEquipo(tipAccIdSeleccionado));

			// Esta condifición verifica que la actualización del equipo se hace desde un
			// equipo que esta en bodega

			if (equipoEstado != null) {
				if (edicionEquipo.getResponsable() == null || edicionEquipo.getSegDependencia() == null) {
					JSFUtil.crearMensajeINFO("Equipo se Queda en Bodega");
				} else {
					edicionEquipo.setEquiEstado(equipoEstado);
					beanBodega.actionSelectionEquiposInactivos();
					JSFUtil.crearMensajeINFO("Equipo Listo para enviar a su Dependecia");
				}
			}

			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			edicionEquipo.setEquiFechaModificacion(tiempo);
			edicionEquipo.setEquiUsuarioModifica(persona.getPerNombres() + " " + persona.getPerApellidos());
			managerEquipo.actualizarEquipo(beanSegLogin.getLoginDTO(), edicionEquipo);

			// Actualizacion de la ip del Equipo

			if (beanListaIp.getEscodigoListaIp() != null) {
				if (!beanListaIp.getEscodigoListaIp().getIpsIp().equals(edicionIpEquipoRespaldo.getIpsIp())) {
					beanListaIp.getEscodigoListaIp().setEquipo(edicionEquipo);
					beanListaIp.actionListenerCambiarEstadoListaIp(beanListaIp.getEscodigoListaIp(), "Activo");
				}
			}

			// Actualizar la Ip editado
			edicionipEquipo = new ListaIp();
			actionRecargarListaEquiposActivos();
			JSFUtil.crearMensajeINFO("Equipo actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Actualizar un registro de un Equipo
	 */
	public void actionListenerActualizarEdicionEquiposfromMantenimiento(Equipo equipo) {
		try {
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			equipo.setEquiFechaModificacion(tiempo);
			equipo.setEquiUsuarioModifica(persona.getPerNombres() + " " + persona.getPerApellidos());

			managerEquipo.actualizarEquipofromMantenimiento(beanSegLogin.getLoginDTO(), equipo);

			actionRecargarListaEquiposActivos();
			JSFUtil.crearMensajeINFO("Equipo actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para consultar la IP de un Equipo
	 * 
	 * @param equipos
	 * @throws Exception
	 */
	public void ConsultarIpEquipo(Equipo equipos) throws Exception {
		ipEquipo = new ListaIp();
		if (managerListaIp.findListaIpByEquiId(equipos.getEquiId()) != null) {
			ipEquipo = managerListaIp.findListaIpByEquiId(equipos.getEquiId());
		}
	}

	/**
	 * Metodo para mostra dialogo para crear Accesorios
	 */
	public void Mostrardialogos() {
		PrimeFaces current = PrimeFaces.current();
		System.out.println("1");
		if (nuevoEquipo.getEquiNombre() != null || nuevoEquipo.getEquiNroSerie() != null
				|| nuevoEquipo.getEquiCodBodega() != null) {

			current.executeScript("PF('dialogoCrearAccesorio').show()");
			System.out.println("2");
		} else if (edicionEquipo != null) {
			current.executeScript("PF('dialogoCrearAccesorio').show()");
		}

		else {
			JSFUtil.crearMensajeINFO("Ingresar los Datos correspondientes del Equipo");
			System.out.println("2");
		}
	}

	/**
	 * Colocar una Ip a un Accesorio
	 */

	public void ActionAsignarIpAEquipo(ListaIp listaIp) throws Exception {
		ListaIp ipEquipoAsignado = listaIp;
		ipEquipoAsignado.setIpsEstado("Activo");
		ipEquipoAsignado.setEquipo(equipoDevuelto);
		beanListaIp.setNuevoListaIp(listaIp);
		beanListaIp.actionListenerInsertarNuevoListaIp();
		ConsultarIpEquipo(equipoDevuelto);

	}
	// ________________________________________metodo de Mantenimiento

	/**
	 * Metodo para verificar que un equipo sale dle mantnimiento a bodega
	 */
	public void ValidarVerificadorMantenimiento() {
		verificador_mantenimiento = true;
	}

	/**
	 * Insertar un Registro de accesorio a la bodega
	 * 
	 * @param equipo
	 */
	public void actionListenerInsertarNuevoEquipoABodega(Equipo equipo) {
		try {
			String enlace = "";
			if (equipo == null) {

				JSFUtil.crearMensajeINFO("Guardar el Equipo");
			} else if (equipo != null) {

				// Cambio de estado de mantenimiento cuando se envie a bodega
				if (verificador_mantenimiento) {

					System.out.println("111");
					listaEquiMan = managerMantenimiento.findEquipoMantenimientoByEquiId(equipo.getEquiId());
					for (int i = 0; i < listaEquiMan.size(); i++) {
						System.out.println(listaEquiMan.get(i).getMantenimiento().getManEstado() + "22");
						System.out.println(listaEquiMan.get(i).getMantenimiento().getManId() + "...........");
						if (listaEquiMan.get(i).getMantenimiento().getManEstado().equals("En_Mantenimiento")) {
							System.out.println(listaEquiMan.get(i).getMantenimiento().getManEstado() + "33");

							listaEquiMan.get(i).getMantenimiento().setManEstado("Finalizado");
							System.out.println(listaEquiMan.get(i).getMantenimiento().getManEstado() + "33");

							managerMantenimiento.cambiarEstadoAccesorioDirecto(listaEquiMan.get(i).getMantenimiento());
							enlace += " Mantenimiento finalizado ";
						}
					}
					beanMantenimiento.actionRecargarListaEquiposMantenimiento("En_Mantenimiento");
				}
				// Cambio de estado de Activo cuando se envie a bodega

				if (beanBodega.getNuevoBodegaEquipo() != null) {
					enlace += beanBodega.getNuevoBodegaEquipo().getBodObservacion() + " con los accesorios :";
				}

				listaAccesoriosEquipo = managerEquipo.findByEquiIdSeleccionado(equipo.getEquiId());
				if (verificador == false) {
					if (listaAccesoriosEquipo.size() > 0) {
						equipo.setEquipoAccesorios(listaAccesoriosEquipo);
						for (int i = 0; i < equipo.getEquipoAccesorios().size(); i++) {
							Accesorio accesorio = equipo.getEquipoAccesorios().get(i).getAccesorio();
							listaAccesoriosBodega = managerBodega.findWhereByAcceIdBodegaOne(accesorio.getAcceId());
							accesorio.setAcceEstado("Inactivo_Equipo");
							enlace += " <> " + accesorio.getAcceNombre() + " - " + accesorio.getAcceNroSerie();
							managerAccesorio.cambiarEstadoAccesorioEnEquipoParaBodega(beanSegLogin.getLoginDTO(),
									accesorio, equipo, "Inactivo_Equipo");
							if (listaAccesoriosBodega.size() == 0) {
								beanBodega.actionListenerInsertarNuevoAccesorioABodega(accesorio);
							}
						}
					}
					equipo.setEquiEstado("Inactivo");
					// managerInventario.actualizarEquipo(beanSegLogin.getLoginDTO(), equipo);
					managerEquipo.cambiarEstadoEquipo(beanSegLogin.getLoginDTO(), equipo, "Inactivo", enlace);

					System.out.println("Equipo No Nuevo--------------");
				}
				verificador = false;
				beanBodega.actionListenerInsertarNuevoEquipoABodega(equipo);
				beanBodega.setEquipo(null);
				actionRecargarListaEquiposActivos();
				JSFUtil.crearMensajeINFO("Equipo enviado a bodega.");
			}

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Colocar un accesorio a un Equipo
	 * 
	 * @param accesorio
	 * @throws Exception
	 */

	public void ActionAccesorioColocarAEquipo(Accesorio accesorio) throws Exception {
		nuevoEquipo = null;
		cabecera = new Equipo();
		acceIdSeleccionado = accesorio.getAcceId();
		atriIdSeleccionado = 0;
		valorAtributo = "";
		cabecera = equipoDevuelto;
		actionListenerAdicionarAccesorioAtributo("Accesorio");
		managerEquipo.actualizarEquipo(beanSegLogin.getLoginDTO(), cabecera);
		listaAccesoriosEquipo = managerEquipo.findByEquiIdSeleccionado(cabecera.getEquiId());
		equipoDevuelto.setEquipoAccesorios(listaAccesoriosEquipo);
		// beanAccesorio.actionListenerActualizarAccesorioPorEquipo(equipoDevuelto,
		// accesorio);
		beanBodega.actionSelectionAccesoriosInactivos();
		beanAccesorio.ConsultarAccesorioAtributoEquipo(accesorio);
		beanAccesorio.actionConsultarListaAccesoriosActivos();

	}

	/**
	 * Buscar un Equipo con sus atributos y al equipo que pertenece y redirecciona a
	 * la pagina de vista
	 */

	public void actionSeleccionarEquipoBodega(Equipo Equipo) throws Exception {
		equipoBodega = ActionBuscarAcceAtriOfEquipo(Equipo);

	}

	/**
	 * Método para verificar que la la autualización del equipo se hace desde bodega
	 * 
	 * @param estado
	 */
	public void CambiarEstado(String estado) {
		equipoEstado = estado;
	}

	/**
	 * Metodo para Activar el Equipo con todos sus Accesorios
	 * 
	 * @param bodegaEquipo
	 * @throws Exception
	 */

	public void actionActivarEquipoWithAllAccesorio(Equipo bodegaEquipo) throws Exception {
		String enlace = "";
		Equipo = ActionBuscarAcceAtriOfEquipo(bodegaEquipo);

		for (int i = 0; i < Equipo.getEquipoAccesorios().size(); i++) {
			Accesorio accesorio = Equipo.getEquipoAccesorios().get(i).getAccesorio();
			accesorio.setAcceEstado("Activo");
			enlace += " <> " + accesorio.getAcceNombre() + " - " + accesorio.getAcceNroSerie();
			managerAccesorio.cambiarEstadoAccesorioDirecto(accesorio);
		}
		Equipo.setEquiEstado("Activo");
		managerEquipo.cambiarEstadoEquipo(beanSegLogin.getLoginDTO(), Equipo, "Activo", enlace);

		beanBodega.actionSelectionEquiposInactivos();
		beanBodega.actionSelectionAccesoriosInactivos();

		// beanBodega.actionSelectionAccesoriosInactivosCE();
		// beanAccesorio.actionConsultarListaAccesoriosActivos();
		// actionRecargarListaEquiposActivos();
		JSFUtil.crearMensajeWARN("Equipo y Accesorios Activados");
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
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	/*
	 * 
	 * OBSERVACIÓN
	 * 
	 * 
	 * 
	 */

	// Eliminado fisico de registro en la tabla EquipoAccesorio
	public void actionQuitarAccesoriodeEquipoParaBodega(int acceId) throws Exception {
		listaAccesoriosEquipo = managerEquipo.findByAcceIdSeleccionadforEquipo(acceId);
		if (listaAccesoriosEquipo.size() > 0) {
			Equipo = listaAccesoriosEquipo.get(0).getEquipo();
			listaAccesoriosEquipo = managerEquipo.findByEquiIdSeleccionado(Equipo.getEquiId());
			Equipo.setEquipoAccesorios(listaAccesoriosEquipo);
			for (int i = 0; i < Equipo.getEquipoAccesorios().size(); i++) {
				if ((int) Equipo.getEquipoAccesorios().get(i).getAccesorio().getAcceId() == acceId) {
					managerEquipo.eliminarAccesorioEquipo(Equipo.getEquipoAccesorios().get(i).getEquiAccId());
					System.out.println("accesorio quitado de Equipo");
					JSFUtil.crearMensajeWARN("Accesorio quitado de la lista");
					break;
				}
			}
		}
	}

	// Observacion

	/*
	 * 
	 * 
	 * 
	 * OBSERVACIÓN
	 * 
	 * 
	 * 
	 * 
	 */

	// Setters and Getters

	public Equipo getNuevoEquipo() {
		return nuevoEquipo;
	}

	public void setNuevoEquipo(Equipo nuevoEquipo) {
		this.nuevoEquipo = nuevoEquipo;
	}

	public Equipo getEdicionEquipo() {
		return edicionEquipo;
	}

	public void setEdicionEquipo(Equipo edicionEquipo) {
		this.edicionEquipo = edicionEquipo;
	}

	public int getRespIdSeleccionado() {
		return respIdSeleccionado;
	}

	public void setRespIdSeleccionado(int respIdSeleccionado) {
		this.respIdSeleccionado = respIdSeleccionado;
	}

	public int getProIdSeleccionado() {
		return proIdSeleccionado;
	}

	public void setProIdSeleccionado(int proIdSeleccionado) {
		this.proIdSeleccionado = proIdSeleccionado;
	}

	public int getIdSegDependenciaSeleccionado() {
		return idSegDependenciaSeleccionado;
	}

	public void setIdSegDependenciaSeleccionado(int idSegDependenciaSeleccionado) {
		this.idSegDependenciaSeleccionado = idSegDependenciaSeleccionado;
	}

	public int getAtriIdSeleccionado() {
		return atriIdSeleccionado;
	}

	public void setAtriIdSeleccionado(int atriIdSeleccionado) {
		this.atriIdSeleccionado = atriIdSeleccionado;
	}

	public int getMarIdSeleccionado() {
		return marIdSeleccionado;
	}

	public void setMarIdSeleccionado(int marIdSeleccionado) {
		this.marIdSeleccionado = marIdSeleccionado;
	}

	public int getTipAccIdSeleccionado() {
		return tipAccIdSeleccionado;
	}

	public void setTipAccIdSeleccionado(int tipAccIdSeleccionado) {
		this.tipAccIdSeleccionado = tipAccIdSeleccionado;
	}

	public String getValorAtributo() {
		return valorAtributo;
	}

	public void setValorAtributo(String valorAtributo) {
		this.valorAtributo = valorAtributo;
	}

	public Equipo getCabecera() {
		return cabecera;
	}

	public void setCabecera(Equipo cabecera) {
		this.cabecera = cabecera;
	}

	public int getAcceIdSeleccionado() {
		return acceIdSeleccionado;
	}

	public void setAcceIdSeleccionado(int acceIdSeleccionado) {
		this.acceIdSeleccionado = acceIdSeleccionado;
	}

	public int getTipEquiIdSeleccionado() {
		return tipEquiIdSeleccionado;
	}

	public void setTipEquiIdSeleccionado(int tipEquiIdSeleccionado) {
		this.tipEquiIdSeleccionado = tipEquiIdSeleccionado;
	}

	public Equipo getVistaEquipo() {
		return vistaEquipo;
	}

	public void setVistaEquipo(Equipo vistaEquipo) {
		this.vistaEquipo = vistaEquipo;
	}

	public List<EquipoAccesorio> getListaAccesoriosEquipo() {
		return listaAccesoriosEquipo;
	}

	public void setListaAccesoriosEquipo(List<EquipoAccesorio> listaAccesoriosEquipo) {
		this.listaAccesoriosEquipo = listaAccesoriosEquipo;
	}

	public List<EquipoAtributo> getListaAtributosEquipo() {
		return listaAtributosEquipo;
	}

	public void setListaAtributosEquipo(List<EquipoAtributo> listaAtributosEquipo) {
		this.listaAtributosEquipo = listaAtributosEquipo;
	}

	public Equipo getEquipoDevuelto() {
		return equipoDevuelto;
	}

	public void setEquipoDevuelto(Equipo equipoDevuelto) {
		this.equipoDevuelto = equipoDevuelto;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public ListaIp getIpEquipo() {
		return ipEquipo;
	}

	public void setIpEquipo(ListaIp ipEquipo) {
		this.ipEquipo = ipEquipo;
	}

	public ListaIp getEdicionipEquipo() {
		return edicionipEquipo;
	}

	public void setEdicionipEquipo(ListaIp edicionipEquipo) {
		this.edicionipEquipo = edicionipEquipo;
	}

	public ListaIp getEdicionIpEquipoRespaldo() {
		return edicionIpEquipoRespaldo;
	}

	public void setEdicionIpEquipoRespaldo(ListaIp edicionIpEquipoRespaldo) {
		this.edicionIpEquipoRespaldo = edicionIpEquipoRespaldo;
	}

	public List<Equipo> getListaEquiposActivos() {
		return listaEquiposActivos;
	}

	public void setListaEquiposActivos(List<Equipo> listaEquiposActivos) {
		this.listaEquiposActivos = listaEquiposActivos;
	}

	public List<Equipo> getListaEquiposInactivos() {
		return listaEquiposInactivos;
	}

	public void setListaEquiposInactivos(List<Equipo> listaEquiposInactivos) {
		this.listaEquiposInactivos = listaEquiposInactivos;
	}

	public List<Equipo> getListaEquiposMantenimientos() {
		return listaEquiposMantenimientos;
	}

	public void setListaEquiposMantenimientos(List<Equipo> listaEquiposMantenimientos) {
		this.listaEquiposMantenimientos = listaEquiposMantenimientos;
	}

	public List<Equipo> getListaEquiposBajas() {
		return listaEquiposBajas;
	}

	public void setListaEquiposBajas(List<Equipo> listaEquiposBajas) {
		this.listaEquiposBajas = listaEquiposBajas;
	}

	public List<Equipo> getListaEquiposAll() {
		return listaEquiposAll;
	}

	public void setListaEquiposAll(List<Equipo> listaEquiposAll) {
		this.listaEquiposAll = listaEquiposAll;
	}

	public int getEquiIdSeleccionado() {
		return equiIdSeleccionado;
	}

	public void setEquiIdSeleccionado(int equiIdSeleccionado) {
		this.equiIdSeleccionado = equiIdSeleccionado;
	}

	public Equipo getEquipoBodega() {
		return equipoBodega;
	}

	public void setEquipoBodega(Equipo equipoBodega) {
		this.equipoBodega = equipoBodega;
	}

}
