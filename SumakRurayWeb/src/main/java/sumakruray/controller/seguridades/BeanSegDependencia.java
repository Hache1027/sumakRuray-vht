package sumakruray.controller.seguridades;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import sumakruray.controller.JSFUtil;
import sumakruray.model.core.entities.SegDependencia;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanSegDependencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerSeguridades managerSeguridades;

	private List<SegDependencia> listaDependencias;
	private SegDependencia nuevoDependencia;
	private SegDependencia edicionDependencia;
	// Tiempo
	private Timestamp tiempo;
	//
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanSegDependencia() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() {
		listaDependencias = managerSeguridades.findAllDependencias();
		tiempo = new Timestamp(System.currentTimeMillis());
		nuevoDependencia = new SegDependencia();
		nuevoDependencia.setEstado(true);
	}

	// devolver lista de Dependencias
	public String actionMenuDependencias() {
		listaDependencias = managerSeguridades.findAllDependencias();
		return "dependencias";
	}

	// devolver lista de Dependencias
	public void actionConsultarAllDependencias() {
		listaDependencias = managerSeguridades.findAllDependencias();
	}

	// Lista de Dependencias
	public List<SegDependencia> getListaDependencias() {
		return listaDependencias;
	}

	// Crear Dependencia
	public String actionMenuNuevoDependencia() {
		nuevoDependencia = new SegDependencia();
		nuevoDependencia.setEstado(true);
		return "dependencias_nuevo";
	}

	public SegDependencia getNuevoDependencia() {
		return nuevoDependencia;
	}

	// Crear Dependencia
	public void actionListenerInsertarNuevoDependencia() {
		try {
			managerSeguridades.insertarDependencia(nuevoDependencia);
			listaDependencias = managerSeguridades.findAllDependencias();
			nuevoDependencia = new SegDependencia();
			nuevoDependencia.setEstado(true);
			JSFUtil.crearMensajeINFO("Dependencia insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Activar Desactivar Dependencia
	public void actionListenerActivarDesactivarDependencia(int idSegDependencia) {
		try {
			managerSeguridades.activarDesactivarDependencia(idSegDependencia);
			listaDependencias = managerSeguridades.findAllDependencias();
			JSFUtil.crearMensajeINFO("Dependencia activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Actualizar Dependencia
	public void actionSeleccionarEdicionDependencia(SegDependencia dependencia) {
		edicionDependencia = dependencia;
	}

	public void actionListenerActualizarEdicionDependencia() {
		try {
			managerSeguridades.actualizarDependencia(beanSegLogin.getLoginDTO(), edicionDependencia);
			listaDependencias = managerSeguridades.findAllDependencias();
			JSFUtil.crearMensajeINFO("Dependencia actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	// Set and Get

	public SegDependencia getEdicionDependencia() {
		return edicionDependencia;
	}

	public void setEdicionDependencia(SegDependencia edicionDependencia) {
		this.edicionDependencia = edicionDependencia;
	}

	public void setListaDependencias(List<SegDependencia> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public void setNuevoDependencia(SegDependencia nuevoDependencia) {
		this.nuevoDependencia = nuevoDependencia;
	}

}
