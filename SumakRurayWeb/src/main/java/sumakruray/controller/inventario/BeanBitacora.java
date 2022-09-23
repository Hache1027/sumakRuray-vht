package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.AccesorioAtributo;
import sumakruray.model.core.entities.Atributo;
import sumakruray.model.core.entities.AudBitacora;
import sumakruray.model.core.entities.BitacoraAccesorio;
import sumakruray.model.core.entities.BitacoraEquipo;
import sumakruray.model.core.entities.EquipoAtributo;
import sumakruray.model.core.utils.ModelUtil;
import sumakruray.model.inventario.managers.ManagerAccesorio;
import sumakruray.model.inventario.managers.ManagerAtributo;
import sumakruray.model.inventario.managers.ManagerBitacora;
import sumakruray.model.inventario.managers.ManagerEquipo;

@Named
@SessionScoped
public class BeanBitacora implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerBitacora managerBitacora;
	@Inject
	private BeanEquipo beanEquipo;
	@Inject
	private BeanAccesorio beanAccesorio;
	@EJB
	private ManagerAccesorio managerAccesorio;
	@EJB
	private ManagerEquipo managerEquipo;
	@EJB
	private ManagerAtributo managerAtributo;
	
	//
	@Inject
	private BeanAtributo beanAtributo;

	private List<BitacoraAccesorio> listaBitacoraAccesorio;
	private List<BitacoraEquipo> listaBitacoraEquipo;
	private int acceIdSeleccionado;
	private int equiIdSeleccionado;
	
	// ------------------- HACHE --------------
	
	private int atriAcceIdSeleccionado;
	private int atriEquiIdSeleccionado;
	private String valorAtriAccesorioSelecionado;
	private String valorAtriEquipoSelecionado;
	
	// ---------------------- HACHE ------------------------
	
	// AccesorioAtributo
		private List<AccesorioAtributo> listAccesorioAtributo;
		private List<AccesorioAtributo> listAccesorioAtributoValor;
		private List<EquipoAtributo> listEquipoAtributo;
		private List<EquipoAtributo> listEquipoAtributoValor;

	public BeanBitacora() {
		// TODO Auto-generated constructor stub
	}

	private Date fechaInicio;
	private Date fechaFin;

	public String actionCargarMenuBitacoraAccesorio() throws Exception {
		// obtener la fecha de ayer:
		fechaInicio = ModelUtil.addDays(new Date(), -1);
		// obtener la fecha de hoy:
		fechaFin = new Date();

		listaBitacoraAccesorio = managerBitacora.findBitacoraAccesorioByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraAccesorio.size());
		beanAccesorio.actionRecargarListaAccesoriosAll();
		return "bitacoraAccesorio";
	
	}

	public String actionCargarMenuBitacoraEquipo() throws Exception {
		// obtener la fecha de ayer:
		fechaInicio = ModelUtil.addDays(new Date(), -1);
		// obtener la fecha de hoy:
		fechaFin = new Date();

		listaBitacoraEquipo = managerBitacora.findBitacoraEquipoByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraEquipo.size());
		beanEquipo.actionRecargarListaEquiposAll();
		return "bitacoraEquipo";
	}

	public void actionListenerConsultarBitacoraAccesorio() {
		listaBitacoraAccesorio = managerBitacora.findBitacoraAccesorioByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraAccesorio.size());
	}

	public void actionListenerConsultarBitacoraEquipo() throws Exception {
		listaBitacoraEquipo = managerBitacora.findBitacoraEquipoByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraEquipo.size());
	}

	// CAMBIOS REALIZADOS HACHE --------------------------
	
	// Redireccion al menu de Caracteristica Accesorio

		public String actionCargarMenuBitacoraCaracteristicaAccesorio() {
			beanAtributo.actionSeleccionarAccesorioAtributo();
			return "bitacoraCaracteristicaAccesorio";
		}
		
		// Redireccion al menu de Caracteristica Equipo

		public String actionCargarMenuBitacoraCaracteristicaEquipo() {
			beanAtributo.actionSeleccionarAccesorioAtributo();
			return "bitacoraCaracteristicaEquipo";
		}

	public void actionListenerConsultarBitacoraAccesorioCaracteristicaValor(int atriId) throws Exception {
		Atributo atributo = managerAtributo.findByIdAtributo(atriId);
		listAccesorioAtributoValor = managerAccesorio.findAtributosValorAccesorio(atributo.getAtriNombre());
		JSFUtil.crearMensajeINFO("¡Busqueda exitosa!");
	}
	
	public void actionListenerConsultarBitacoraAccesorioCaracteristica(int atriAcceIdSeleccionados,
			String valorAtriSeleccionados) throws Exception {
		System.out.println(atriAcceIdSeleccionados + "............." + valorAtriSeleccionados);
		listAccesorioAtributo = managerAccesorio.findAccesorioByAtriIdandValor(atriAcceIdSeleccionados,
				valorAtriSeleccionados);

		listAccesorioAtributoValor = managerAccesorio.findAtributosValorAccesorio(valorAtriSeleccionados);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listAccesorioAtributo.size());
	}
	
	 // EQUIPOS
	
	public void actionListenerConsultarBitacoraEquipoCaracteristica(int atriEquiIdSeleccionados,
			String valorAtriSeleccionados) throws Exception {
		System.out.println(atriEquiIdSeleccionados + "............." + valorAtriSeleccionados);
		listEquipoAtributo = managerEquipo.findEquipoByAtriIdandValor(atriEquiIdSeleccionados, valorAtriSeleccionados);

		listEquipoAtributoValor = managerEquipo.findAtributosValorEquipo(valorAtriSeleccionados);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listEquipoAtributo.size());
	}

	public void actionListenerConsultarBitacoraEquipoCaracteristicaValor(int atriId) throws Exception {
		System.out.println(atriId + "nnnnnnnnnnnn");
		Atributo atributo = managerAtributo.findByIdAtributo(atriId);
		System.out.println(atributo.getAtriNombre());
		listEquipoAtributoValor = managerEquipo.findAtributosValorEquipo(atributo.getAtriNombre());
		JSFUtil.crearMensajeINFO("Â¡Busqueda exitosa!");
	}
	
	 // REPORTES
	
	// Metodo del Reporte de Bitacora Accesorio

		public String actionReporteBitacoraAccesorio(int acceID) {
			System.out.println("ADIOSSSSSSSSSSSSSSSSSSSSSSSSSSSS -----------------------------------");
			Map<String, Object> parametros = new HashMap<String, Object>();
			/*
			 * parametros.put("p_titulo_principal",p_titulo_principal);
			 * parametros.put("p_titulo",p_titulo);
			 */ FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
			String ruta = servletContext
					.getRealPath("resources/jasper/reporteBitacora/ReporteAccesorioBitacoraFinal.jasper");
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			response.addHeader("Content-disposition", "attachment;filename=reporteBitacoraAccesorio.pdf");
			response.setContentType("application/pdf");
			parametros.put("acce_ID", acceID);
			System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZ ---------" + acceID);
			try {
				Class.forName("org.postgresql.Driver");
				System.out.println("Driver");
				Connection connection = null;
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sumakruray2", "postgres",
						"vicoc123");
				JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
				JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
				context.getApplication().getStateManager().saveView(context);
				System.out.println("Reporte de Bitacora Accesorio Generado Correctamente.");
				context.responseComplete();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
			return "";
		}

		// Metodo del Reporte de Bitacora Equipo

		public String actionReporteBitacoraEquipo(int equiID) {
			System.out.println("ADIOSSSSSSSSSSSSSSSSSSSSSSSSSSSS -----------------------------------");
			Map<String, Object> parametros = new HashMap<String, Object>();
			/*
			 * parametros.put("p_titulo_principal",p_titulo_principal);
			 * parametros.put("p_titulo",p_titulo);
			 */ FacesContext context = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
			String ruta = servletContext.getRealPath("resources/jasper/reporteBitacora/ReporteEquipoBitacoraFinal.jasper");
			HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
			response.addHeader("Content-disposition", "attachment;filename=reporteBitacoraEquipo.pdf");
			response.setContentType("application/pdf");
			parametros.put("equi_ID", equiID);
			System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZ ---------" + equiID);
			try {
				Class.forName("org.postgresql.Driver");
				System.out.println("Driver");
				Connection connection = null;
				connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sumakruray2", "postgres",
						"vicoc123");
				JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
				JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
				context.getApplication().getStateManager().saveView(context);
				System.out.println("Reporte de Bitacora Equipo Generado Correctamente.");
				context.responseComplete();
			} catch (Exception e) {
				JSFUtil.crearMensajeERROR(e.getMessage());
				e.printStackTrace();
			}
			return "";
		}
	
	// Metodo del Reporte de Atributos de un Accesorio 

			public String actionReporteAtributoAccesorio(int atriID) {
				System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA -----------------------------------");
				Map<String, Object> parametros = new HashMap<String, Object>();
				/*
				 * parametros.put("p_titulo_principal",p_titulo_principal);
				 * parametros.put("p_titulo",p_titulo);
				 */ FacesContext context = FacesContext.getCurrentInstance();
				ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
				String ruta = servletContext.getRealPath("resources/jasper/reporteAtributo/ReporteAtributosAccesorio.jasper");
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				response.addHeader("Content-disposition", "attachment;filename=reporteCaracterisitcaAccesorio.pdf");
				response.setContentType("application/pdf");
				parametros.put("atriID", atriID);
				System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm ---------" + atriID);
				try {
					Class.forName("org.postgresql.Driver");
					System.out.println("Driver");
					Connection connection = null;
					connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sumakruray2", "postgres",
							"vicoc123");
					JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
					JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
					context.getApplication().getStateManager().saveView(context);
					System.out.println("Reporte de Caracteristicas de un Accesorio Generado Correctamente.");
					context.responseComplete();
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR(e.getMessage());
					e.printStackTrace();
				}
				return "";
			}
			
			// Metodo del Reporte de Atributos de un Accesorio 

			public String actionReporteAtributoEquipo(int atriID) {
				System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA -----------------------------------");
				Map<String, Object> parametros = new HashMap<String, Object>();
				/*
				 * parametros.put("p_titulo_principal",p_titulo_principal);
				 * parametros.put("p_titulo",p_titulo);
				 */ FacesContext context = FacesContext.getCurrentInstance();
				ServletContext servletContext = (ServletContext) context.getExternalContext().getContext();
				String ruta = servletContext.getRealPath("resources/jasper/reporteAtributo/ReporteAtributosEquipo.jasper");
				HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
				response.addHeader("Content-disposition", "attachment;filename=reporteCaracterisitcaAccesorio.pdf");
				response.setContentType("application/pdf");
				parametros.put("atriID", atriID);
				System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm ---------" + atriID);
				try {
					Class.forName("org.postgresql.Driver");
					System.out.println("Driver");
					Connection connection = null;
					connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sumakruray2", "postgres",
							"vicoc123");
					JasperPrint impresion = JasperFillManager.fillReport(ruta, parametros, connection);
					JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
					context.getApplication().getStateManager().saveView(context);
					System.out.println("Reporte de Caracteristicas de un Equipo Generado Correctamente.");
					context.responseComplete();
				} catch (Exception e) {
					JSFUtil.crearMensajeERROR(e.getMessage());
					e.printStackTrace();
				}
				return "";
			}

	// ---------------------------------- HACHE ---------------------------

	/**
	 * GETTERS AND SETTERS
	 *
	 */
	public List<BitacoraAccesorio> getListaBitacoraAccesorio() {
		return listaBitacoraAccesorio;
	}

	public void setListaBitacoraAccesorio(List<BitacoraAccesorio> listaBitacoraAccesorio) {
		this.listaBitacoraAccesorio = listaBitacoraAccesorio;
	}

	public List<BitacoraEquipo> getListaBitacoraEquipo() {
		return listaBitacoraEquipo;
	}

	public void setListaBitacoraEquipo(List<BitacoraEquipo> listaBitacoraEquipo) {
		this.listaBitacoraEquipo = listaBitacoraEquipo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getAcceIdSeleccionado() {
		return acceIdSeleccionado;
	}

	public void setAcceIdSeleccionado(int acceIdSeleccionado) {
		this.acceIdSeleccionado = acceIdSeleccionado;
	}

	public int getEquiIdSeleccionado() {
		return equiIdSeleccionado;
	}

	public void setEquiIdSeleccionado(int equiIdSeleccionado) {
		this.equiIdSeleccionado = equiIdSeleccionado;
	}

	
	
	// GETTERS AND SETTERS CREADOS POR HACHE ----------------------
	
	public List<AccesorioAtributo> getListAccesorioAtributo() {
		return listAccesorioAtributo;
	}

	public void setListAccesorioAtributo(List<AccesorioAtributo> listAccesorioAtributo) {
		this.listAccesorioAtributo = listAccesorioAtributo;
	}

	public List<AccesorioAtributo> getListAccesorioAtributoValor() {
		return listAccesorioAtributoValor;
	}

	public void setListAccesorioAtributoValor(List<AccesorioAtributo> listAccesorioAtributoValor) {
		this.listAccesorioAtributoValor = listAccesorioAtributoValor;
	}

	public List<EquipoAtributo> getListEquipoAtributo() {
		return listEquipoAtributo;
	}

	public void setListEquipoAtributo(List<EquipoAtributo> listEquipoAtributo) {
		this.listEquipoAtributo = listEquipoAtributo;
	}

	public List<EquipoAtributo> getListEquipoAtributoValor() {
		return listEquipoAtributoValor;
	}

	public void setListEquipoAtributoValor(List<EquipoAtributo> listEquipoAtributoValor) {
		this.listEquipoAtributoValor = listEquipoAtributoValor;
	}

	public int getAtriAcceIdSeleccionado() {
		return atriAcceIdSeleccionado;
	}

	public void setAtriAcceIdSeleccionado(int atriAcceIdSeleccionado) {
		this.atriAcceIdSeleccionado = atriAcceIdSeleccionado;
	}

	public int getAtriEquiIdSeleccionado() {
		return atriEquiIdSeleccionado;
	}

	public void setAtriEquiIdSeleccionado(int atriEquiIdSeleccionado) {
		this.atriEquiIdSeleccionado = atriEquiIdSeleccionado;
	}

	public String getValorAtriAccesorioSelecionado() {
		return valorAtriAccesorioSelecionado;
	}

	public void setValorAtriAccesorioSelecionado(String valorAtriAccesorioSelecionado) {
		this.valorAtriAccesorioSelecionado = valorAtriAccesorioSelecionado;
	}

	public String getValorAtriEquipoSelecionado() {
		return valorAtriEquipoSelecionado;
	}

	public void setValorAtriEquipoSelecionado(String valorAtriEquipoSelecionado) {
		this.valorAtriEquipoSelecionado = valorAtriEquipoSelecionado;
	}
	
	
	// --------------------------------------------------- HACHE -----------------------------------
	
	

}
