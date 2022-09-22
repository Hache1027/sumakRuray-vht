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
import sumakruray.model.core.entities.Persona;
import sumakruray.model.core.entities.SegDependencia;
import sumakruray.model.core.entities.SegRol;
import sumakruray.model.core.entities.SegUsuario;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanSegUsuarios implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerSeguridades managerSeguridades;
	
	private List<SegUsuario> listaUsuarios;
	private SegUsuario nuevoUsuario;
	private Persona nuevoPersona;
	private SegUsuario edicionUsuario;
	private List<SegRol> listaRoles;
	private List<SegDependencia> listaDependencias;
	private int idSegRolSeleccionado;
	private int idSegDependenciaSeleccionado;
	
	@Inject
	private BeanSegLogin beanSegLogin;
	
	
	public BeanSegUsuarios() {
		
	}
	@PostConstruct
	public void inicializar() {
		listaDependencias=managerSeguridades.findAllDependencias();
		
	}
	
	public String actionMenuUsuarios() {
		listaUsuarios=managerSeguridades.findAllUsuarios();
		listaRoles=managerSeguridades.findAllRoles();
		listaDependencias=managerSeguridades.findAllDependencias();
		return "usuarios";
	}
	
	
	public void actionListenerActivarDesactivarUsuario(int idSegUsuario) {
		try {
			managerSeguridades.activarDesactivarUsuario(idSegUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionMenuNuevoUsuario() {
		nuevoUsuario=new SegUsuario();
		nuevoPersona=new Persona();
		nuevoPersona.setPerEstado(true);
		System.out.print(".........................");
		nuevoUsuario.setEstado(true);
		return "usuarios_nuevo";
	}
	
	public void actionListenerInsertarNuevoUsuario() {
		try {
			managerSeguridades.insertarPersona(nuevoPersona);
			nuevoUsuario.setSegRol(managerSeguridades.findByIdSegRol(idSegRolSeleccionado));
			nuevoUsuario.setSegDependencia(managerSeguridades.findByIdSegDependencia(idSegDependenciaSeleccionado));
			nuevoUsuario.setPersona(nuevoPersona);
			managerSeguridades.insertarUsuario(nuevoUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			nuevoUsuario=new SegUsuario();
			nuevoUsuario.setEstado(true);
			JSFUtil.crearMensajeINFO("Usuario insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionSeleccionarEdicionUsuario(SegUsuario usuario) {
		edicionUsuario=usuario;
		return "usuarios_edicion";
	}
	
	public void actionListenerActualizarEdicionUsuario() {
		try {
			edicionUsuario.setSegDependencia(managerSeguridades.findByIdSegDependencia(idSegDependenciaSeleccionado));
			System.out.println(idSegDependenciaSeleccionado+"..."+edicionUsuario.getSegDependencia().getDepDescripcion());
			edicionUsuario.setSegRol(managerSeguridades.findByIdSegRol(idSegRolSeleccionado));
			managerSeguridades.actualizarUsuario(beanSegLogin.getLoginDTO(),edicionUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void actionListenerEliminarUsuario(int idSegUsuario) {
		try {
			managerSeguridades.eliminarUsuario(idSegUsuario);
			listaUsuarios=managerSeguridades.findAllUsuarios();
			JSFUtil.crearMensajeINFO("Usuario eliminado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	//Selecionar Roles
	
	public void actionListenerSeleccionarRol() {
		listaUsuarios=managerSeguridades.findAsignacionByRol(idSegRolSeleccionado);
	}

	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public SegUsuario getNuevoUsuario() {
		return nuevoUsuario;
	}

	public void setNuevoUsuario(SegUsuario nuevoUsuario) {
		this.nuevoUsuario = nuevoUsuario;
	}

	public SegUsuario getEdicionUsuario() {
		return edicionUsuario;
	}

	public void setEdicionUsuario(SegUsuario edicionUsuario) {
		this.edicionUsuario = edicionUsuario;
	}

	public BeanSegLogin getBeanSegLogin() {
		return beanSegLogin;
	}

	public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
		this.beanSegLogin = beanSegLogin;
	}

	public int getIdSegRolSeleccionado() {
		return idSegRolSeleccionado;
	}

	public void setIdSegRolSeleccionado(int idSegRolSeleccionado) {
		this.idSegRolSeleccionado = idSegRolSeleccionado;
	}

	public Persona getNuevoPersona() {
		return nuevoPersona;
	}

	public void setNuevoPersona(Persona nuevoPersona) {
		this.nuevoPersona = nuevoPersona;
	}
	public List<SegRol> getListaRoles() {
		return listaRoles;
	}

	public void setListaRoles(List<SegRol> listaRoles) {
		this.listaRoles = listaRoles;
	}

	public int getIdSegDependenciaSeleccionado() {
		return idSegDependenciaSeleccionado;
	}

	public void setIdSegDependenciaSeleccionado(int idSegDependenciaSeleccionado) {
		this.idSegDependenciaSeleccionado = idSegDependenciaSeleccionado;
	}

	public List<SegDependencia> getListaDependencias() {
		return listaDependencias;
	}

	public void setListaDependencias(List<SegDependencia> listaDependencias) {
		this.listaDependencias = listaDependencias;
	}
	
	//get y set
	
	

}
