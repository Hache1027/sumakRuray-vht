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
import sumakruray.model.core.entities.AccesorioAtributo;
import sumakruray.model.core.entities.Atributo;
import sumakruray.model.core.entities.EquipoAtributo;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.core.entities.SegRol;
import sumakruray.model.inventario.managers.ManagerAtributo;

@Named
@SessionScoped
public class BeanAtributo implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerAtributo managerAtributo;

	// Atributos
	private List<Atributo> listaAtributos;
	private Atributo nuevoAtributo;
	private Atributo edicionAtributo;
	// Accesorio Atributo
	private AccesorioAtributo edicionAccesorioAtributo;
	// Equipo Atributo
	private EquipoAtributo edicionEquipoAtributo;

	@Inject
	private BeanSegLogin beanSegLogin;
	@Inject
	private BeanMantenimiento beanMantenimiento;

	public BeanAtributo() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___ARIBUTOS__--******************************************
	@PostConstruct
	public void inicializar() {
	}

	/**
	 * devolver lista de Atributos
	 */
	public void actionConsultarAllAtributo() {
		listaAtributos = managerAtributo.findAllAtributos();
	}

	/**
	 * prepara variable para ventanas emergentes
	 */
	public void actionNuevoAtributos() {
		nuevoAtributo = new Atributo();
	}

	/**
	 * Escoger AccesorioAtributos para editar
	 * 
	 * @param atributo
	 */
	public void actionSeleccionarEdicionAccesorioAtributo(AccesorioAtributo accesorioAtributo) {
		System.out.println(accesorioAtributo.getAtriDescripcion() + "------------");
		edicionAccesorioAtributo = accesorioAtributo;
	}

	/**
	 * Escoger EquipoAtributos para editar
	 * 
	 * @param atributo
	 */
	public void actionSeleccionarEdicionEquipoAtributo(EquipoAtributo equipoAtributo) {
		System.out.println(equipoAtributo.getAtriDescripcion() + "------------");
		edicionEquipoAtributo = equipoAtributo;
	}

	/**
	 * Accion actualizar Atributo
	 */
	public void actionListenerActualizarEdicionAccesorioAtributo() {
		try {
			managerAtributo.actualizarAccesorioAtributo(beanSegLogin.getLoginDTO(), edicionAccesorioAtributo);
			beanMantenimiento.ActionfindAccesorio();
			JSFUtil.crearMensajeINFO("Caracteristica Actualizo ¡Exitosamente!");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Accion actualizar Atributo
	 */
	public void actionListenerActualizarEdicionEquipoAtributo() {
		try {
			managerAtributo.actualizarEquipoAtributo(beanSegLogin.getLoginDTO(), edicionEquipoAtributo);
			beanMantenimiento.ActionfindEquipo();
			JSFUtil.crearMensajeINFO("Caracteristica Actualizo ¡Exitosamente!");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
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

	// Crear Atributos
	public void actionListenerInsertarNuevoAtributo() {
		try {
			managerAtributo.insertarAtributo(nuevoAtributo);
			listaAtributos = managerAtributo.findAllAtributos();
			nuevoAtributo = new Atributo();
			JSFUtil.crearMensajeINFO("Atributo insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger Atributos para editar
	public void actionSeleccionarEdicionAtributo(Atributo atributo) {
		edicionAtributo = atributo;
	}

	// Accion actualizar Atributo
	public void actionListenerActualizarEdicionAtributo() {
		try {
			managerAtributo.actualizarAtributo(beanSegLogin.getLoginDTO(), edicionAtributo);
			listaAtributos = managerAtributo.findAllAtributos();
			JSFUtil.crearMensajeINFO("Rol actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	// *****************--__Getter and
	// Setter__--************************************+

	public List<Atributo> getListaAtributos() {
		return listaAtributos;
	}

	public void setListaAtributos(List<Atributo> listaAtributos) {
		this.listaAtributos = listaAtributos;
	}

	public Atributo getEdicionAtributo() {
		return edicionAtributo;
	}

	public void setEdicionAtributo(Atributo edicionAtributo) {
		this.edicionAtributo = edicionAtributo;
	}

	public void setNuevoAtributo(Atributo nuevoAtributo) {
		this.nuevoAtributo = nuevoAtributo;
	}

	public Atributo getNuevoAtributo() {
		return nuevoAtributo;
	}

	public AccesorioAtributo getEdicionAccesorioAtributo() {
		return edicionAccesorioAtributo;
	}

	public void setEdicionAccesorioAtributo(AccesorioAtributo edicionAccesorioAtributo) {
		this.edicionAccesorioAtributo = edicionAccesorioAtributo;
	}

	public EquipoAtributo getEdicionEquipoAtributo() {
		return edicionEquipoAtributo;
	}

	public void setEdicionEquipoAtributo(EquipoAtributo edicionEquipoAtributo) {
		this.edicionEquipoAtributo = edicionEquipoAtributo;
	}
	

}
