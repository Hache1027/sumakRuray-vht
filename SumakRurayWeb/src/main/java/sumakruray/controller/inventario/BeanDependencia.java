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
import sumakruray.model.core.entities.Persona;
import sumakruray.model.core.entities.Proveedor;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.core.entities.SegDependencia;
import sumakruray.model.inventario.managers.ManagerDependencia;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanDependencia implements Serializable {

	@EJB
	private ManagerSeguridades managerSeguridades;
	@Inject
	private BeanSegLogin beanSegLogin;
	@EJB
	private ManagerDependencia managerDependencia;

	// SegDependencia
	private List<SegDependencia> listaDependencias;
	private SegDependencia nuevoSegDependencia;
	private SegDependencia edicionSegDependencia;
	//
	// Tiempo
	private Timestamp tiempo;
	//

	public BeanDependencia() {
		// TODO Auto-generated constructor stub
	}

	// **********************--__SegDependenciaS__--******************************************

	@PostConstruct
	public void inicializar() {
		tiempo = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * devolver lista de Dependencias
	 */
	public void actionConsultarAllDependencias() {
		listaDependencias = managerSeguridades.findAllDependencias();
	}

	/**
	 * prepara variable para ventanas emergentes
	 */
	public void actionNuevoSegDependencia() {
		nuevoSegDependencia = new SegDependencia();
		nuevoSegDependencia.setEstado(true);
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
	 */
	// Redireccionamiento al pagina SegDependencia
	public String actionMenuSegDependencia() {
		listaDependencias = managerDependencia.findAllSegDependencias();
		tiempo = new Timestamp(System.currentTimeMillis());
		nuevoSegDependencia = new SegDependencia();
		nuevoSegDependencia.setEstado(true);
		return "SegDependencias";
	}

	// Insertar un nuevo registro de Dependencia
	public void actionListenerInsertarNuevoSegDependencia() {

		try {
			/*
			 * nuevoSegDependencia.setProFechaCreacion(tiempo);
			 * System.out.println(beanSegLogin.getLoginDTO().getIdSegUsuario() +
			 * "........................................................."); int
			 * IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario(); Persona persona
			 * = managerSeguridades.BuscarPersona(IdSegUsuario);
			 * System.out.println(beanSegLogin.getLoginDTO().getIdSegUsuario() +
			 * ".........................................................");
			 * nuevoSegDependencia.setProUsuarioCrea(persona.getPerNombres() + " " +
			 * persona.getPerApellidos());
			 */

			System.out.println(nuevoSegDependencia.getDepDescripcion() + "++++++++++++++++++++++++++++++");
			managerDependencia.insertarSegDependencia(nuevoSegDependencia);
			listaDependencias = managerDependencia.findAllSegDependencias();
			nuevoSegDependencia = new SegDependencia();
			JSFUtil.crearMensajeINFO("SegDependencia insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger SegDependencia para editar
	public void actionSeleccionarEdicionSegDependencia(SegDependencia SegDependencia) {

		edicionSegDependencia = SegDependencia;
	}

	// actualizar un registro de Dependencia
	public void actionListenerActualizarEdicionSegDependencia() {
		try {
			/*
			 * edicionSegDependencia.setProFechaModificacion(tiempo);
			 * System.out.println(edicionSegDependencia.getProEmpresa() +
			 * "-********************************************");
			 */
			managerDependencia.actualizarSegDependencia(edicionSegDependencia);
			listaDependencias = managerDependencia.findAllSegDependencias();
			JSFUtil.crearMensajeINFO("SegDependencia actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Activar Desactivar un resgistro de Dependencia
	public void actionListenerActivarDesactivarSegDependencia(int proId) {
		try {
			managerDependencia.activarDesactivarSegDependencia(proId);
			listaDependencias = managerDependencia.findAllSegDependencias();
			JSFUtil.crearMensajeINFO("SegDependencia activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// *****************--__Getter and
	// Setter__--************************************+

	public SegDependencia getNuevoSegDependencia() {
		return nuevoSegDependencia;
	}

	public List<SegDependencia> getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(List<SegDependencia> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}

	public void setNuevoSegDependencia(SegDependencia nuevoSegDependencia) {
		this.nuevoSegDependencia = nuevoSegDependencia;
	}

	public SegDependencia getEdicionSegDependencia() {
		return edicionSegDependencia;
	}

	public void setEdicionSegDependencia(SegDependencia edicionSegDependencia) {
		this.edicionSegDependencia = edicionSegDependencia;
	}

}
