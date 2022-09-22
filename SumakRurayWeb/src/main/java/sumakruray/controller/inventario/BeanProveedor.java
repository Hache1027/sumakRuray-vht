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
import sumakruray.model.core.entities.SegRol;
import sumakruray.model.inventario.managers.ManagerProveedor;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanProveedor implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerProveedor managerProveedor;
	@EJB
	private ManagerSeguridades managerSeguridades;
	// Proveedor
	private List<Proveedor> listaProveedors;
	private Proveedor nuevoProveedor;
	private Proveedor edicionProveedor;
	//
	// Tiempo
	private Timestamp tiempo;
	//
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanProveedor() {
		// TODO Auto-generated constructor stub
	}

	// **********************--__PROVEEDORS__--******************************************

	@PostConstruct
	public void inicializar() {
		tiempo = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * devolver lista de Proveedores
	 */
	public void actionConsultarAllProveedor() {
		listaProveedors = managerProveedor.findAllProveedors();
	}

	/**
	 * Prepara variable para ventanas emergentes de nuevo Proveedor
	 */
	public void actionNuevoProveedor() {
		nuevoProveedor = new Proveedor();
		nuevoProveedor.setProEstado(true);
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
	 */

	// Redireccionamiento a la pagina Proveedors
	public String actionMenuProveedor() {
		nuevoProveedor = new Proveedor();
		nuevoProveedor.setProEstado(true);
		return "proveedors";
	}

	// Insertar un nuevo registro de un proveedor
	public void actionListenerInsertarNuevoProveedor() {

		try {
			nuevoProveedor.setProFechaCreacion(tiempo);
			System.out.println(beanSegLogin.getLoginDTO().getIdSegUsuario()
					+ ".........................................................");
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			System.out.println(beanSegLogin.getLoginDTO().getIdSegUsuario()
					+ ".........................................................");
			nuevoProveedor.setProUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			managerProveedor.insertarProveedor(nuevoProveedor);
			listaProveedors = managerProveedor.findAllProveedors();
			nuevoProveedor = new Proveedor();
			JSFUtil.crearMensajeINFO("Proveedor insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger Proveedor para editar
	public void actionSeleccionarEdicionProveedor(Proveedor proveedor) {

		edicionProveedor = proveedor;
		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		System.out.println(edicionProveedor.getProEmpresa());
	}

	// Actualizar un registro de un Proveedor
	public void actionListenerActualizarEdicionProveedor() {
		try {

			edicionProveedor.setProFechaModificacion(tiempo);
			System.out.println(edicionProveedor.getProEmpresa() + "-********************************************");
			managerProveedor.actualizarProveedor(beanSegLogin.getLoginDTO(), edicionProveedor);
			listaProveedors = managerProveedor.findAllProveedors();
			JSFUtil.crearMensajeINFO("Proveedor actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Actualizar un estado de un Proveedor
	public void actionListenerActivarDesactivarProveedor(int proId) {
		try {
			managerProveedor.activarDesactivarProveedor(proId);
			listaProveedors = managerProveedor.findAllProveedors();
			JSFUtil.crearMensajeINFO("Proveedor activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// *****************--__Getter and
	// Setter__--************************************+

	public List<Proveedor> getListaProveedors() {
		return listaProveedors;
	}

	public void setListaProveedors(List<Proveedor> listaProveedors) {
		this.listaProveedors = listaProveedors;
	}

	public Proveedor getNuevoProveedor() {
		return nuevoProveedor;
	}

	public void setNuevoProveedor(Proveedor nuevoProveedor) {
		this.nuevoProveedor = nuevoProveedor;
	}

	public Proveedor getEdicionProveedor() {
		return edicionProveedor;
	}

	public void setEdicionProveedor(Proveedor edicionProveedor) {
		this.edicionProveedor = edicionProveedor;
	}

}
