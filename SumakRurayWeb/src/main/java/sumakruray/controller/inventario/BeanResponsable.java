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
import sumakruray.model.core.entities.Atributo;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.core.entities.Proveedor;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.inventario.managers.ManagerResponsable;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanResponsable implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerResponsable managerResponsable;
	@EJB
	private ManagerSeguridades managerSeguridades;
	@Inject
	private BeanSegLogin beanSegLogin;
	// Responsables
	private List<Responsable> listaResponsables;
	private Responsable nuevoResponsable;
	private Responsable edicionResponsable;
	private Persona persona;
	//
	// Tiempo
	private Timestamp tiempo;
	//

	public BeanResponsable() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___RESPONSABLE__--******************************************
	@PostConstruct
	public void inicializar() {
		tiempo = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * devolver lista de Responsable
	 */
	public void actionConsultarAllResponsable() {
		listaResponsables = managerResponsable.findAllResponsables();
	}

	/**
	 * prepara variable para ventanas emergentes para un nuevo Responsable
	 */
	public void actionNuevoResponsable() {
		nuevoResponsable = new Responsable();
		nuevoResponsable.setRespEstado(true);
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
	 */

	// Preparar varibale para un nuevo Responsable y redireccionamiento a la pagina
	// de responsable_nuevo
	public String actionMenuNuevoResponsable() {
		nuevoResponsable = new Responsable();
		nuevoResponsable.setRespEstado(true);
		// nuevoResponsable.setRespEstado(true);
		return "responsables_nuevo";
	}

	// redireccionamiento a pagina menu de respobsales
	public String actionMenuResponsables() {
		listaResponsables = managerResponsable.findAllResponsables();
		return "responsables";
	}

	// Crear Responsables
	public void actionListenerInsertarNuevoResponsable() {
		try {
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoResponsable.setRespFechaCreacion(tiempo);
			nuevoResponsable.setRespUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			managerResponsable.insertarResponsable(nuevoResponsable);
			listaResponsables = managerResponsable.findAllResponsables();
			nuevoResponsable = new Responsable();
			nuevoResponsable.setRespEstado(true);
			JSFUtil.crearMensajeINFO("Responsable insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger Resposable para editar
	public void actionSeleccionarEdicionResponsable(Responsable responsable) {
		edicionResponsable = responsable;
	}

	// Actualizar el registro de Responsable
	public void actionListenerActualizarEdicionResponsable() {
		try {
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			edicionResponsable.setRespFechaModificacion(tiempo);
			edicionResponsable.setRespUsuarioModifica(persona.getPerNombres() + " " + persona.getPerApellidos());
			managerResponsable.actualizarResponsable(beanSegLogin.getLoginDTO(), edicionResponsable);
			listaResponsables = managerResponsable.findAllResponsables();
			JSFUtil.crearMensajeINFO("Responsable actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Actualizar el estado de un responsable
	public void actionListenerActivarDesactivarResponsable(int respId) {
		try {
			managerResponsable.activarDesactivarResponsable(respId);
			listaResponsables = managerResponsable.findAllResponsables();
			JSFUtil.crearMensajeINFO("Responsable activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	// *****************--__Getter and
	// Setter__--************************************+

	public List<Responsable> getListaResponsables() {
		return listaResponsables;
	}

	public void setListaResponsables(List<Responsable> listaResponsables) {
		this.listaResponsables = listaResponsables;
	}

	public Responsable getNuevoResponsable() {
		return nuevoResponsable;
	}

	public void setNuevoResponsable(Responsable nuevoResponsable) {
		this.nuevoResponsable = nuevoResponsable;
	}

	public Responsable getEdicionResponsable() {
		return edicionResponsable;
	}

	public void setEdicionResponsable(Responsable edicionResponsable) {
		this.edicionResponsable = edicionResponsable;
	}

}
