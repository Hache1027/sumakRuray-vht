package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import sumakruray.controller.JSFUtil;
import sumakruray.controller.seguridades.BeanSegLogin;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioAtributo;
import sumakruray.model.core.entities.AccesorioMantenimiento;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoAccesorio;
import sumakruray.model.core.entities.EquipoAtributo;
import sumakruray.model.core.entities.EquipoMantenimiento;
import sumakruray.model.core.entities.Mantenimiento;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.inventario.managers.ManagerAccesorio;
import sumakruray.model.inventario.managers.ManagerEquipo;
import sumakruray.model.inventario.managers.ManagerMantenimiento;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanMantenimiento implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerSeguridades managerSeguridades;
	@EJB
	private ManagerMantenimiento managerMantenimiento;
	@Inject
	private BeanSegLogin beanSegLogin;
	@Inject
	private BeanEquipo beanEquipo;
	@Inject
	private BeanAccesorio beanAccesorio;
	@Inject
	private BeanAtributo beanAtributo;
	@EJB
	private ManagerAccesorio managerAccesorio;
	@EJB
	private ManagerEquipo managerEquipo;
	// EquipoMantenimiento
	private List<EquipoMantenimiento> listaEquipoMantenimientos;
	private List<EquipoMantenimiento> listaEquiManEstado;
	private EquipoMantenimiento edicionEquipoMantenimiento;

	private int equiIdSeleccionado;
	private String externo_interno;
	private String preventivo_correctivo;
	// Accesorios
	private List<Accesorio> listaAccesoriosInactivos;
	// Equipo
	private Equipo equipoDevuelto;
	// Equipos
	private List<Equipo> listaEquiposInactivos;
	private List<EquipoAccesorio> listaAccesoriosEquipo;
	private List<EquipoAtributo> listaAtributosEquipo;
	private EquipoMantenimiento equipoMantenimientoSeleccionado;
	// Mantenimiento
	private Mantenimiento nuevoMantenimiento;
	private List<Mantenimiento> listaMantenimiento;

	// Tiempo
	private Timestamp tiempo;
	//
	// Accesorio
	private AccesorioMantenimiento accesorioMantenimientoSeleccionado;
	private List<AccesorioAtributo> listaAccesorioAtributo;
	private Accesorio accesorioDevuelto;
	private AccesorioMantenimiento edicionAccesorioMantenimiento;
	private int acceIdSeleccionado;
	private List<AccesorioMantenimiento> listaAccesorioMantenimientos;
	private List<AccesorioMantenimiento> listaAcceManEstado;

	public BeanMantenimiento() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___ARIBUTOS__--******************************************

	@PostConstruct
	public void inicializar() {
		listaEquipoMantenimientos = managerMantenimiento.findAllEquipoMantenimientos();
		listaAccesorioMantenimientos = managerMantenimiento.findAllAccesorioMantenimientos();
		tiempo = new Timestamp(System.currentTimeMillis());

	}

//**************************--_______MANTENIMIENTO DE EQUIPOS______--*********************************
	// Inicializar Variables
	public void actionRecargarListaAccesoriosActivos() throws Exception {
		listaAccesoriosInactivos = managerAccesorio.findWhereByAcceEstado("Inactivo");
	}

	// Cargar la lista de Equipos Activos
	public void actionRecargarListaEquiposActivos() throws Exception {
		listaEquiposInactivos = managerEquipo.findWhereByEquiEstado("Inactivo");
	}

	// Listar todos los AccesoriosMantenimiento por un estado
	public void actionRecargarListaAccesoriosMantenimiento(String estado) throws Exception {
		listaAcceManEstado = managerMantenimiento.findManWhereByAcceManEstados(estado);
	}

	// Listar todos los EquiposMantenimiento por un estado
	public void actionRecargarListaEquiposMantenimiento(String estado) throws Exception {
		listaEquiManEstado = managerMantenimiento.findManWhereByEquiManEstados(estado);

	}

	// Redireccionamiento a la pagina man_nuevo_equipo
	public String actionMenuNuevoMantenimientoEquipo() throws Exception {
		nuevoMantenimiento = new Mantenimiento();
		actionRecargarListaEquiposActivos();
		return "man_nuevo_equipo";
	}

	// Redireccionamiento a la pagina man_menu_equipo
	public String actionMenuMantenimientoEquipo() throws Exception {
		actionRecargarListaEquiposMantenimiento("En_Mantenimiento");
		return "menu_man_equipo";
	}

	// Redireccionamiento a la pagina man_menu_equipo
	public String actionMenuMantenimientoFinalizadoEquipo() throws Exception {
		actionRecargarListaEquiposMantenimiento("Finalizado");
		return "man_equipo_realizados";
	}

	// Redireccionamiento a la pagina man_menu_accesorio
	public String actionMenuMantenimientoAccesorio() throws Exception {
		actionRecargarListaAccesoriosMantenimiento("En_Mantenimiento");
		return "menu_man_accesorio";
	}

	// Redireccionamiento a la pagina man_menu_accesorio
	public String actionMenuMantenimientoFinalizadoAccesorio() throws Exception {
		actionRecargarListaAccesoriosMantenimiento("Finalizado");
		return "man_accesorio_realizados";
	}

//Cragra el listado de todos los registros de mantenimientos
	public void findAllMantenimientos() {
		listaMantenimiento = managerMantenimiento.findAllMantenimientos();
	}

	// redirecciinar a la pagina de bista_man_equipo
	public String actionVistaEquipoMantenimiento(EquipoMantenimiento equipoMantenimientoSeleccion) throws Exception {

		equipoMantenimientoSeleccionado = equipoMantenimientoSeleccion;
		int equiId = equipoMantenimientoSeleccion.getEquipo().getEquiId();
		listaAtributosEquipo = managerEquipo.findByEquiIdSeleccionadoAtributo(equiId);
		return "vista_man_equipo";
	}

	// Cambiar el estado de un mantenimietno finalizado
	public void ActualizarEstadoEquipoMantenimiento(EquipoMantenimiento equipoMantenimientoSeleccion) throws Exception {
		String estado = "Finalizado";
		managerMantenimiento.cambiarEstadoMantenimiento(equipoMantenimientoSeleccion.getMantenimiento().getManId(),
				estado);
	}

	/**
	 * edici�n de Equipo Mantenimiento
	 * 
	 * @param equipoMantenimientoSeleccion
	 * @return
	 * @throws Exception
	 */
	public String actionEdicionEquipoMantenimiento(EquipoMantenimiento equipoMantenimientoSeleccion) throws Exception {

		edicionEquipoMantenimiento = equipoMantenimientoSeleccion;
		beanAtributo.actionConsultarAllAtributo();
		equipoDevuelto = beanEquipo.ActionBuscarAcceAtriOfEquipo(edicionEquipoMantenimiento.getEquipo());
		beanEquipo.setCabecera(equipoDevuelto);
		int equiId = equipoMantenimientoSeleccion.getEquipo().getEquiId();
		equiIdSeleccionado = equiId;
		return "edicion_man_equipo";
	}

	/**
	 * edici�n de Accesorio Mantenimiento
	 * 
	 * @param AccesorioMantenimientoSeleccion
	 * @return
	 * @throws Exception
	 */
	public String actionEdicionAccesorioMantenimiento(AccesorioMantenimiento AccesorioMantenimientoSeleccion)
			throws Exception {

		edicionAccesorioMantenimiento = AccesorioMantenimientoSeleccion;
		beanAtributo.actionConsultarAllAtributo();
		accesorioDevuelto = beanAccesorio
				.ConsultarAccesorioAtributoEquipo(AccesorioMantenimientoSeleccion.getAccesorio());
		beanAccesorio.setCabecera(accesorioDevuelto);
		int acceId = AccesorioMantenimientoSeleccion.getAccesorio().getAcceId();
		acceIdSeleccionado = acceId;
		listaAccesorioAtributo = managerAccesorio.findByAcceIdSeleccionadoAtributo(acceId);

		return "edicion_man_accesorio";
	}

	/**
	 * Crear EquipoMantenimientos
	 */
	public void actionListenerInsertarNuevoEquipoMantenimiento() {
		try {
			String enlace = "";
			nuevoMantenimiento.setManFechaCreacion(tiempo);
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoMantenimiento.setManUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			nuevoMantenimiento.setManFuncionario(equipoDevuelto.getResponsable().getRespNombres() + " "
					+ equipoDevuelto.getResponsable().getRespApellidos());
			nuevoMantenimiento.setManEstado("En_Mantenimiento");

			// Cambiar el estado de Equipo
			equipoDevuelto.setEquiEstado("Mantenimiento");
			enlace += nuevoMantenimiento.getManDescripcion();
			enlace += " de tipo : " + nuevoMantenimiento.getManTipoIntExt() + " y "
					+ nuevoMantenimiento.getManTipoPreCorr();

			managerEquipo.cambiarEstadoEquipo(beanSegLogin.getLoginDTO(), equipoDevuelto, "Mantenimiento", enlace);

			managerMantenimiento.insertarEquipoMantenimiento(nuevoMantenimiento, equipoDevuelto);
			listaEquipoMantenimientos = managerMantenimiento.findAllEquipoMantenimientos();
			nuevoMantenimiento = new Mantenimiento();
			equipoDevuelto = null;
			actionRecargarListaEquiposMantenimiento("En_Mantenimiento");
			listaEquiposInactivos = managerEquipo.findWhereByEquiEstado("Inactivo");
			JSFUtil.crearMensajeINFO("EquipoMantenimiento insertado.");

		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Crear AccesorioMantenimientos
	 */
	public void actionListenerInsertarNuevoAccesorioMantenimiento() {
		try {
			String enlace = "";
			nuevoMantenimiento.setManFechaCreacion(tiempo);
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoMantenimiento.setManUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			nuevoMantenimiento.setManFuncionario(accesorioDevuelto.getResponsable().getRespNombres() + " "
					+ accesorioDevuelto.getResponsable().getRespApellidos());
			nuevoMantenimiento.setManEstado("En_Mantenimiento");

			// Cambiar el estado de Accesorio
			accesorioDevuelto.setAcceEstado("Mantenimiento");
			enlace += nuevoMantenimiento.getManDescripcion();
			enlace += " de tipo : " + nuevoMantenimiento.getManTipoIntExt() + " y "
					+ nuevoMantenimiento.getManTipoPreCorr();
			managerAccesorio.actualizarEstadoAccesorio(beanSegLogin.getLoginDTO(), accesorioDevuelto, "Mantenimiento",
					enlace);

			managerMantenimiento.insertarAccesorioMantenimiento(nuevoMantenimiento, accesorioDevuelto);

			listaAccesorioMantenimientos = managerMantenimiento.findAllAccesorioMantenimientos();
			nuevoMantenimiento = new Mantenimiento();
			accesorioDevuelto = null;
			actionRecargarListaAccesoriosActivos();
			actionRecargarListaAccesoriosMantenimiento("En_Mantenimiento");
			JSFUtil.crearMensajeINFO("AccesorioMantenimiento insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Accion actualizar EquipoMantenimiento
	 */

	public void actionListenerActualizarMantenimiento() {
		try {
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			edicionEquipoMantenimiento.getMantenimiento().setManFechaModificacion(tiempo);
			edicionEquipoMantenimiento.getMantenimiento()
					.setManUsuarioModifica(persona.getPerNombres() + " " + persona.getPerApellidos());

			System.out.println(".....................����");
			managerMantenimiento.actualizarEquipoMantenimiento(beanSegLogin.getLoginDTO(), edicionEquipoMantenimiento);
			listaEquipoMantenimientos = managerMantenimiento.findAllEquipoMantenimientos();
			actionRecargarListaEquiposMantenimiento("Enviado");
			JSFUtil.crearMensajeINFO("EquipoMantenimiento actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Accion actualizar Accesorio Mantenimiento
	 */
	public void actionActualizarMantenimientoAccesorio() {
		try {

			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			edicionAccesorioMantenimiento.getMantenimiento().setManFechaModificacion(tiempo);
			edicionAccesorioMantenimiento.getMantenimiento()
					.setManUsuarioModifica(persona.getPerNombres() + " " + persona.getPerApellidos());

			System.out.println(".....................����");

			managerMantenimiento.actualizarAccesorioMantenimiento(beanSegLogin.getLoginDTO(),
					edicionAccesorioMantenimiento);

			listaAccesorioMantenimientos = managerMantenimiento.findAllAccesorioMantenimientos();
			JSFUtil.crearMensajeINFO("AccesorioMantenimiento actualizado.");
			actionRecargarListaAccesoriosMantenimiento("Enviado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// **************************--_______MANTENIMIENTO DE
	// ACCESORIOS______--*********************************
	// Crear Mantenimiento

	// Cargar la lista de AccesoriosMantenimientos

	public void actionRecargarListaAccesoriosMantenimiento() throws Exception {
		listaEquipoMantenimientos = managerMantenimiento.findAllEquipoMantenimientos();
	}

	public String actionMenuNuevoMantenimientoAccesorio() throws Exception {
		nuevoMantenimiento = new Mantenimiento();
		actionRecargarListaAccesoriosActivos();
		return "man_nuevo_accesorio";
	}

	// Cambiar a la p�gina vistaV AccesorioMantenimiento
	public String actionVistaAccesorioMantenimiento(AccesorioMantenimiento AccesorioMantenimientoSeleccion)
			throws Exception {

		accesorioMantenimientoSeleccionado = AccesorioMantenimientoSeleccion;
		int acceId = AccesorioMantenimientoSeleccion.getAccesorio().getAcceId();
		listaAccesorioAtributo = managerAccesorio.findByAcceIdSeleccionadoAtributo(acceId);
		return "vista_man_accesorio";
	}

	// Buscar Accesorio, Accesorio de Accesorio y Atributos de Accesorio
	public void ActionfindAccesorio() throws Exception {
		accesorioDevuelto = (Accesorio) managerAccesorio.findByIdAccesorio(acceIdSeleccionado);
		listaAccesorioAtributo = managerAccesorio.findByAcceIdSeleccionadoAtributo(acceIdSeleccionado);
		accesorioDevuelto.setAccesorioAtributos(listaAccesorioAtributo);
	}

	// Buscar Equipo, Accesorio de Equipo y Atributos de Equipo
	public void ActionfindEquipo() throws Exception {
		equipoDevuelto = (Equipo) managerEquipo.findByIdEquipo(equiIdSeleccionado);
		equipoDevuelto = beanEquipo.ActionBuscarAcceAtriOfEquipo(equipoDevuelto);

	}
	
	// CAMBIOS REALIZADOS POR HACHE 
	
	// Reporte en Jasper de Accesorio Mantenimiento
	
	public String actionReporteAccesorio(int manID) {
		System.out.println("ZZZZZZZZZZZZZZ--------------------------MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM");
		Map<String, Object> parametros = new HashMap<String, Object>();
		/*
		 * parametros.put("p_titulo_principal",p_titulo_principal);
		 * parametros.put("p_titulo",p_titulo);
		 */ FacesContext context = FacesContext.getCurrentInstance();
		ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
		String ruta = servletContext.getRealPath("resources/jasper/reporteAccesorio/ReporteAccesorioFinal.jasper");
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporteAccesorio.pdf");
		response.setContentType("application/pdf");
		parametros.put("man_ID", manID);
		System.out.println("HO0LAAAAAAAAAAAAAAAAAAA ---------------------------------");
		System.out.println(manID);
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver");
			Connection connection = null;
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sumakruray2", "postgres",
					"vicoc123");
			JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
			JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
			context.getApplication().getStateManager().saveView(context);
			System.out.println("Reporte de Accesorio Generado Correctamente.");
			context.responseComplete();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
		return "";
	}
	
	// Reporte en Jasper de Equipo Mantenimiento
			public String actionReporteEquipo(int manID) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				/*
				 * parametros.put("p_titulo_principal",p_titulo_principal);
				 * parametros.put("p_titulo",p_titulo);
				 */ FacesContext context = FacesContext.getCurrentInstance();
				ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
				String ruta = servletContext.getRealPath("resources/jasper/reporteEquipo/ReporteEquipoFinal.jasper");
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				response.addHeader("Content-disposition", "attachment;filename=reporteEquipo.pdf");
				response.setContentType("application/pdf");
				parametros.put("man_ID", manID);
				try {
					Class.forName("org.postgresql.Driver");
					System.out.println("Driver");
					Connection connection = null;
					connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sumakruray2", "postgres",
							"vicoc123");
					JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
					JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
					context.getApplication().getStateManager().saveView(context);
					System.out.println("Reporte de Equipo Generado Correctamente.");
					context.responseComplete();
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR(e.getMessage());
					e.printStackTrace();
				}
				return "";
			}
	
	
	
	// ----------------------------------------------------------- HACHE ----------------------------------------

	// Accion actualizar AccesorioMantenimiento

	// *****************--__Getter and //
	// Setter__--************************************+

	public List<EquipoMantenimiento> getListaEquipoMantenimientos() {
		return listaEquipoMantenimientos;
	}

	public void setListaEquipoMantenimientos(List<EquipoMantenimiento> listaEquipoMantenimientos) {
		this.listaEquipoMantenimientos = listaEquipoMantenimientos;
	}

	public EquipoMantenimiento getEdicionEquipoMantenimiento() {
		return edicionEquipoMantenimiento;
	}

	public void setEdicionEquipoMantenimiento(EquipoMantenimiento edicionEquipoMantenimiento) {
		this.edicionEquipoMantenimiento = edicionEquipoMantenimiento;
	}

	public int getEquiIdSeleccionado() {
		return equiIdSeleccionado;
	}

	public void setEquiIdSeleccionado(int equiIdSeleccionado) {
		this.equiIdSeleccionado = equiIdSeleccionado;
	}

	public String getExterno_interno() {
		return externo_interno;
	}

	public void setExterno_interno(String externo_interno) {
		this.externo_interno = externo_interno;
	}

	public String getPreventivo_correctivo() {
		return preventivo_correctivo;
	}

	public void setPreventivo_correctivo(String preventivo_correctivo) {
		this.preventivo_correctivo = preventivo_correctivo;
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

	public EquipoMantenimiento getEquipoMantenimientoSeleccionado() {
		return equipoMantenimientoSeleccionado;
	}

	public void setEquipoMantenimientoSeleccionado(EquipoMantenimiento equipoMantenimientoSeleccionado) {
		this.equipoMantenimientoSeleccionado = equipoMantenimientoSeleccionado;
	}

	public Mantenimiento getNuevoMantenimiento() {
		return nuevoMantenimiento;
	}

	public void setNuevoMantenimiento(Mantenimiento nuevoMantenimiento) {
		this.nuevoMantenimiento = nuevoMantenimiento;
	}

	public AccesorioMantenimiento getAccesorioMantenimientoSeleccionado() {
		return accesorioMantenimientoSeleccionado;
	}

	public void setAccesorioMantenimientoSeleccionado(AccesorioMantenimiento accesorioMantenimientoSeleccionado) {
		this.accesorioMantenimientoSeleccionado = accesorioMantenimientoSeleccionado;
	}

	public Accesorio getAccesorioDevuelto() {
		return accesorioDevuelto;
	}

	public void setAccesorioDevuelto(Accesorio accesorioDevuelto) {
		this.accesorioDevuelto = accesorioDevuelto;
	}

	public AccesorioMantenimiento getEdicionAccesorioMantenimiento() {
		return edicionAccesorioMantenimiento;
	}

	public void setEdicionAccesorioMantenimiento(AccesorioMantenimiento edicionAccesorioMantenimiento) {
		this.edicionAccesorioMantenimiento = edicionAccesorioMantenimiento;
	}

	public int getAcceIdSeleccionado() {
		return acceIdSeleccionado;
	}

	public void setAcceIdSeleccionado(int acceIdSeleccionado) {
		this.acceIdSeleccionado = acceIdSeleccionado;
	}

	public List<AccesorioAtributo> getListaAccesorioAtributo() {
		return listaAccesorioAtributo;
	}

	public void setListaAccesorioAtributo(List<AccesorioAtributo> listaAccesorioAtributo) {
		this.listaAccesorioAtributo = listaAccesorioAtributo;
	}

	public List<AccesorioMantenimiento> getListaAccesorioMantenimientos() {
		return listaAccesorioMantenimientos;
	}

	public void setListaAccesorioMantenimientos(List<AccesorioMantenimiento> listaAccesorioMantenimientos) {
		this.listaAccesorioMantenimientos = listaAccesorioMantenimientos;
	}

	public List<Mantenimiento> getListaMantenimiento() {
		return listaMantenimiento;
	}

	public void setListaMantenimiento(List<Mantenimiento> listaMantenimiento) {
		this.listaMantenimiento = listaMantenimiento;
	}

	public List<Accesorio> getListaAccesoriosInactivos() {
		return listaAccesoriosInactivos;
	}

	public void setListaAccesoriosInactivos(List<Accesorio> listaAccesoriosInactivos) {
		this.listaAccesoriosInactivos = listaAccesoriosInactivos;
	}

	public List<Equipo> getListaEquiposInactivos() {
		return listaEquiposInactivos;
	}

	public void setListaEquiposInactivos(List<Equipo> listaEquiposInactivos) {
		this.listaEquiposInactivos = listaEquiposInactivos;
	}

	public List<EquipoMantenimiento> getListaEquiManEstado() {
		return listaEquiManEstado;
	}

	public void setListaEquiManEstado(List<EquipoMantenimiento> listaEquiManEstado) {
		this.listaEquiManEstado = listaEquiManEstado;
	}

	public List<AccesorioMantenimiento> getListaAcceManEstado() {
		return listaAcceManEstado;
	}

	public void setListaAcceManEstado(List<AccesorioMantenimiento> listaAcceManEstado) {
		this.listaAcceManEstado = listaAcceManEstado;
	}

}
