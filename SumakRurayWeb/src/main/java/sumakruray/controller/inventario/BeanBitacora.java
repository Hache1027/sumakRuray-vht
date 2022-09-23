package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import sumakruray.controller.JSFUtil;
import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.AudBitacora;
import sumakruray.model.core.entities.BitacoraAccesorio;
import sumakruray.model.core.entities.BitacoraEquipo;
import sumakruray.model.core.utils.ModelUtil;
import sumakruray.model.inventario.managers.ManagerBitacora;

@Named
@SessionScoped
public class BeanBitacora implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerBitacora managerBitacora;
	@Inject
	private BeanEquipo beanEquipo;

	private List<BitacoraAccesorio> listaBitacoraAccesorio;
	private List<BitacoraEquipo> listaBitacoraEquipo;
	private int acceIdSeleccionado;
	private int equiIdSeleccionado;
	// Tiempo
	private Timestamp tiempo;

	//
	public BeanBitacora() {
		// TODO Auto-generated constructor stub
	}

	private Date fechaInicio;
	private Date fechaFin;

	@PostConstruct
	public void inicializar() throws Exception {
		tiempo = new Timestamp(System.currentTimeMillis());
	}

	public String actionCargarMenuBitacoraAccesorio() {
		// obtener la fecha de ayer:
		fechaInicio = ModelUtil.addDays(new Date(), -1);
		// obtener la fecha de hoy:
		fechaFin = new Date();

		listaBitacoraAccesorio = managerBitacora.findBitacoraAccesorioByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraAccesorio.size());
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

	public String[] actionCargarFechaTranscurridos(Timestamp fechaAdquisicion, double precio) throws Exception {
		return managerBitacora.CalcularFechaEntreFechas(fechaAdquisicion, tiempo, precio);
	}

	public void actionListenerConsultarBitacoraAccesorio() {
		listaBitacoraAccesorio = managerBitacora.findBitacoraAccesorioByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraAccesorio.size());
	}

	public void actionListenerConsultarBitacoraEquipo() throws Exception {
		listaBitacoraEquipo = managerBitacora.findBitacoraEquipoByFecha(fechaInicio, fechaFin);
		JSFUtil.crearMensajeINFO("Registros encontrados: " + listaBitacoraEquipo.size());
	}

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

}
