package sumakruray.model.seguridades.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.core.entities.SegAsignacion;
import sumakruray.model.core.entities.SegDependencia;
import sumakruray.model.core.entities.SegModulo;
import sumakruray.model.core.entities.SegPerfil;
import sumakruray.model.core.entities.SegRol;
import sumakruray.model.core.entities.SegUsuario;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.core.utils.ModelUtil;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Implementa la logica de manejo de usuarios y autenticacion.
 * Funcionalidades principales:
 * <ul>
 * 	<li>Verificacion de credenciales de usuario.</li>
 *  <li>Asignacion de modulos a un usuario.</li>
 * </ul>
 * 
 */
@Stateless
@LocalBean
public class ManagerSeguridades {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
    /**
     * Default constructor. 
     */
    public ManagerSeguridades() {
        
    }
    /**
     * Funcion de inicializacion de datos de usuarios.
     */
    public String inicializarDemo() throws Exception {
    	mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Inicializacion de bdd demo.");
    	int idSegUsuarioAdmin=0;
    	String mensaje="";//mensaje que se enviara al metodo que invoca a esta funcion.
    	//buscar el usuario admin (id igual a 1)
    	System.out.print("inicio");
    	SegUsuario admin=(SegUsuario) mDAO.findById(SegUsuario.class, 1);
    	SegRol rol=(SegRol) mDAO.findById(SegRol.class, 1);
    	System.out.print("rol");
    	Persona persona=(Persona) mDAO.findById(Persona.class, 1);
    	System.out.print("Persona");
    	SegDependencia dependencia=(SegDependencia) mDAO.findById(SegDependencia.class, 1);
    	System.out.print("dependencia");
    	if(rol==null) {
    		//creacion del usuario admin:
    		rol=new SegRol();
    		rol.setRolDescripcion("Administrador");
    		rol.setEstado(true);
    		mDAO.insertar(rol);
    	}
    	if(dependencia==null) {
    		//creacion del usuario admin:
    		dependencia=new SegDependencia();
    		dependencia.setDepDescripcion("Municipio");
    		dependencia.setEstado(true);
    		mDAO.insertar(dependencia);
    	}
    	//Creaci�n de persona
    	if(persona==null) {
    		
    		persona=new Persona();
    		persona.setPerApellidos("admin");
    		persona.setPerCorreo("admin@sumakruray.com");
    		persona.setPerNombres("admin");
    		persona.setPerCedula("100500472");
    		persona.setPerTelefono("0994625468");
    		persona.setPerDireccion("Otavalo");
    		persona.setPerEstado(true);
    		mDAO.insertar(persona);
    	}
    	if(admin==null) {
    		admin=new SegUsuario();
    		admin.setSegRol(rol);
    		admin.setPersona(persona);
    		admin.setSegDependencia(dependencia);
			admin.setEstado(true);
			admin.setClave("admin");
			mDAO.insertar(admin);
			
			idSegUsuarioAdmin=admin.getIdSegUsuario();
			
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Usuario administrador creado (id : "+idSegUsuarioAdmin+")");
    	}else {
    		idSegUsuarioAdmin=admin.getIdSegUsuario();
    		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Usuario administrador ya existe (id : "+idSegUsuarioAdmin+")");
    	}
    	mensaje="Id del usuario admin: "+idSegUsuarioAdmin;
    	
    	//verificar si ya existen los modulos iniciales:
    	int idSegModuloSeguridades=0;
    	int idSegModuloAuditoria=0;
    	int idSegPerfilAuditor=0;
    	int idSegPerfilSegAdministrador=0;
    	List<SegModulo> modulos=mDAO.findAll(SegModulo.class);
    	for(SegModulo m:modulos) {
    		if(m.getNombreModulo().equals("Seguridades"))
    			idSegModuloSeguridades=m.getIdSegModulo();
    		if(m.getNombreModulo().equals("Auditoría"))
    			idSegModuloAuditoria=m.getIdSegModulo();
    	}
    	
    	if(idSegModuloSeguridades==0) {
			//inicializacion de modulo:
			SegModulo modulo=new SegModulo();
			modulo.setNombreModulo("Seguridades");
			modulo.setIcono("pi pi-lock");
			modulo.setEstado(true);
			mDAO.insertar(modulo);
			idSegModuloSeguridades=modulo.getIdSegModulo();
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Creado módulo de seguridades (id : "+idSegModuloSeguridades+")");
			//ahora creamos el perfil para el acceso del usuario:
			SegPerfil perfil=new SegPerfil();
			perfil.setNombrePerfil("Administrador");
			perfil.setRutaAcceso("seguridades/administrador/menu");
			perfil.setSegModulo(modulo);
			perfil.setEstado(true);
			mDAO.insertar(perfil);
			idSegPerfilSegAdministrador=perfil.getIdSegPerfil();
    	}else {
    		//si ya existe el modulo, buscamos el perfil de Administrador de seguridad:
    		SegModulo m=(SegModulo) mDAO.findById(SegModulo.class, idSegModuloSeguridades);
    		for(SegPerfil p:m.getSegPerfils()) {
    			if(p.getRutaAcceso().equals("seguridades/administrador/menu"))
    				idSegPerfilSegAdministrador=p.getIdSegPerfil();
    		}
    	}
    	//asignacion de accesos:
    	try {
			asignarPerfil(idSegUsuarioAdmin, idSegPerfilSegAdministrador);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    	if(idSegModuloAuditoria==0) {
			SegModulo modulo=new SegModulo();
			modulo.setNombreModulo("Auditoría");
			modulo.setIcono("pi pi-eye");
			modulo.setEstado(true);
			mDAO.insertar(modulo);
			idSegModuloAuditoria=modulo.getIdSegModulo();
			mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Creado módulo de auditoría (id : "+idSegModuloAuditoria+")");
			//ahora creamos el perfil para el acceso del usuario:
			SegPerfil perfil=new SegPerfil();
			perfil.setNombrePerfil("Auditor");
			perfil.setRutaAcceso("auditoria/auditor/menu");
			perfil.setSegModulo(modulo);
			perfil.setEstado(true);
			mDAO.insertar(perfil);
			idSegPerfilAuditor=perfil.getIdSegPerfil();
    	}else {
    		//si ya existe el modulo, buscamos el perfil de Auditor:
    		SegModulo m=(SegModulo) mDAO.findById(SegModulo.class, idSegModuloAuditoria);
    		for(SegPerfil p:m.getSegPerfils()) {
    			if(p.getRutaAcceso().equals("auditoria/auditor/menu"))
    				idSegPerfilAuditor=p.getIdSegPerfil();
    		}
    	}
    	//asignacion de accesos:
    	try {
			asignarPerfil(idSegUsuarioAdmin, idSegPerfilAuditor);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
		mAuditoria.mostrarLog(getClass(), "inicializarDemo", "Inicializacion de bdd demo terminada.");
		return mensaje+" Inicialización de bdd demo terminada.";
    }
    
    /**
     * Funcion de autenticacion mediante el uso de credenciales.
     * @param idSegUsuario El codigo del usuario que desea autenticarse.
     * @param clave La contrasenia.
     * @param direccionIP La dirección IP V4 del cliente web.
     * @return La ruta de acceso al sistema.
     * @throws Exception
     */
    
  //Prueba
  //Prueba
  //Prueba
    public LoginDTO login(String correo,String clave,String direccionIP) throws Exception{
    	//crear DTO:
    	//String correo = new String(correo1);
		LoginDTO loginDTO=new LoginDTO();
		loginDTO.setCorreo(correo);
		loginDTO.setDireccionIP(direccionIP);
		List<SegUsuario> listausuarios = new ArrayList<>();
		SegUsuario usuario = new SegUsuario();
		
    	if(ModelUtil.isEmpty(clave)) {
    		mAuditoria.mostrarLog(getClass(), "login", "Debe indicar una clave "+correo);
    		throw new Exception("Debe indicar una clave");
    	}
    	//SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	listausuarios = (List<SegUsuario>) mDAO.findAll(SegUsuario.class);
    	for (SegUsuario segUsuario : listausuarios) {
			if(correo.equals(segUsuario.getPersona().getPerCorreo())) {
				usuario = (SegUsuario) mDAO.findById(SegUsuario.class, segUsuario.getIdSegUsuario());
				loginDTO.setIdSegUsuario(segUsuario.getPersona().getPerId());
			}
		}
    	
    	if(usuario==null) {
    		mAuditoria.mostrarLog(getClass(), "login", "No existe usuario "+correo);
    		throw new Exception("Error en credenciales.");
    	}
    		
    	if(usuario.getClave().equals(clave)) {
    		if(usuario.getEstado()==false) {
        		mAuditoria.mostrarLog(getClass(), "login", "Intento de login usuario desactivado "+correo);
        		throw new Exception("El usuario esta desactivado.");
        	}
    		mAuditoria.mostrarLog(loginDTO, getClass(), "login", "Login exitoso "+correo);
    		
    		loginDTO.setCorreo(usuario.getPersona().getPerCorreo());
    		//aqui puede realizarse el envio de correo de notificacion.
    		
    		//obtener la lista de perfiles a los que tiene acceso:
    		List<SegAsignacion> listaAsignaciones=findAsignacionByUsuario(usuario.getIdSegUsuario());
    		for(SegAsignacion asig:listaAsignaciones) {
    			SegPerfil perfil=asig.getSegPerfil();
    			loginDTO.getListaPerfiles().add(perfil);
    		}
    		return loginDTO;
    	}
    	mAuditoria.mostrarLog(getClass(), "login", "No coincide la clave "+correo);
    	throw new Exception("Error en credenciales");
    }
    
    public void cerrarSesion(String correo) {
    	mAuditoria.mostrarLog(getClass(), "cerrarSesion", "Cerrar sesión usuario: "+correo);
    }
    
    public void accesoNoPermitido(String correo,String ruta) {
    	mAuditoria.mostrarLog(getClass(), "accesoNoPermitido", "Usuario "+correo+" intento no autorizado a "+ruta);
    }
    
    public List<SegUsuario> findAllUsuarios(){
    	return mDAO.findAll(SegUsuario.class, null);
    }
    //Consulta a la Tabla de Rol
    public List<SegRol> findAllRoles(){
    	return mDAO.findAll(SegRol.class, "rolDescripcion");
    }
  //Consulta a la Tabla de Dependecia
    public List<SegDependencia> findAllDependencias(){
    	return mDAO.findAll(SegDependencia.class, "depDescripcion");
    }
    
    public SegUsuario findByIdSegUsuario(int idSegUsuario) throws Exception {
    	return (SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    }
    public SegRol findByIdSegRol(int idSegRol) throws Exception {
    	return (SegRol) mDAO.findById(SegRol.class, idSegRol);
    }
    
    public SegDependencia findByIdSegDependencia(int idSegDependencia) throws Exception {
    	return (SegDependencia) mDAO.findById(SegDependencia.class, idSegDependencia);
    }
    public void insertarUsuario(SegUsuario nuevoUsuario) throws Exception {
    	mDAO.insertar(nuevoUsuario);
    }
    public void insertarPersona(Persona nuevoPersona) throws Exception {
    	mDAO.insertar(nuevoPersona);
    }
    public void insertarRol(SegRol nuevoRol) throws Exception {
    	mDAO.insertar(nuevoRol);
    }
    public void insertarDependencia(SegDependencia nuevoDependencia) throws Exception {
    	mDAO.insertar(nuevoDependencia);
    }
    
    public void actualizarUsuario(LoginDTO loginDTO,SegUsuario edicionUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, edicionUsuario.getIdSegUsuario());
    	usuario.getPersona().setPerApellidos(edicionUsuario.getPersona().getPerApellidos());
    	usuario.setClave(edicionUsuario.getClave());
    	usuario.getPersona().setPerCorreo(edicionUsuario.getPersona().getPerCorreo());
    	usuario.getPersona().setPerCedula(edicionUsuario.getPersona().getPerCedula());
    	usuario.getPersona().setPerNombres(edicionUsuario.getPersona().getPerNombres());
    	usuario.getPersona().setPerTelefono(edicionUsuario.getPersona().getPerTelefono());
    	usuario.getPersona().setPerDireccion(edicionUsuario.getPersona().getPerDireccion());
    	usuario.setSegDependencia(edicionUsuario.getSegDependencia());
    	usuario.setSegRol(edicionUsuario.getSegRol());
    	mDAO.actualizar(usuario);
    	mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarUsuario", "se actualizó al usuario "+usuario.getPersona().getPerApellidos());
    }
    //Actualizar Rol
    public void actualizarRol(LoginDTO loginDTO,SegRol edicionRol) throws Exception {
    	SegRol rol=(SegRol) mDAO.findById(SegRol.class, edicionRol.getIdSegRol());
    	rol.setRolDescripcion(edicionRol.getRolDescripcion());
    	mDAO.actualizar(rol);
    	mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarRol", "se actualiz� al usuario "+rol.getRolDescripcion());
    }
  //Actualizar Dependencia
    public void actualizarDependencia(LoginDTO loginDTO,SegDependencia edicionDependencia) throws Exception {
    	SegDependencia dependencia=(SegDependencia) mDAO.findById(SegDependencia.class, edicionDependencia.getIdSegDependencia());
    	dependencia.setDepDescripcion(edicionDependencia.getDepDescripcion());
    	mDAO.actualizar(dependencia);
    	mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarRol", "se actualiz� al usuario "+dependencia.getDepDescripcion());
    }
    
    public void activarDesactivarUsuario(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(idSegUsuario==1)
    		throw new Exception("No se puede desactivar al usuario administrador.");
    	usuario.setEstado(!usuario.getEstado());
    	System.out.println("activar/desactivar "+usuario.getEstado());
    	mDAO.actualizar(usuario);
    }
    //Activar desactivar Rol
    public void activarDesactivarRol(int idSegRol) throws Exception {
    	SegRol rol=(SegRol) mDAO.findById(SegRol.class, idSegRol);
    	rol.setEstado(!rol.getEstado());
    	System.out.println("activar/desactivar "+rol.getEstado());
    	mDAO.actualizar(rol);
    }
    
  //Activar desactivar Dependencia
    public void activarDesactivarDependencia(int idSegDependencia) throws Exception {
    	SegDependencia dependencia=(SegDependencia) mDAO.findById(SegDependencia.class, idSegDependencia);
    	dependencia.setEstado(!dependencia.getEstado());
    	System.out.println("activar/desactivar "+dependencia.getEstado());
    	mDAO.actualizar(dependencia);
    }
    
    
    public void eliminarUsuario(int idSegUsuario) throws Exception {
    	SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
    	if(usuario.getIdSegUsuario()==1)
    		throw new Exception("No se puede eliminar el usuario administrador.");
    	if(usuario.getSegAsignacions().size()>0)
    		throw new Exception("No se puede elimininar el usuario porque tiene asignaciones de módulos.");
    	mDAO.eliminar(SegUsuario.class, usuario.getIdSegUsuario());
    	//TODO agregar uso de LoginDTO para auditar metodo.
    }
    //Eliminar Rol
    public void eliminarRol(int idSegRol) throws Exception {
    	SegRol rol=(SegRol) mDAO.findById(SegRol.class, idSegRol);
    	if(rol.getIdSegRol()==1)
    		throw new Exception("No se puede eliminar el usuario administrador.");
    	
    	//if(rol.getSegAsignacions().size()>0)
    		//throw new Exception("No se puede elimininar el usuario porque tiene asignaciones de módulos.");
    	mDAO.eliminar(SegRol.class, rol.getIdSegRol());
    	//TODO agregar uso de LoginDTO para auditar metodo.
    }
    
    public List<SegModulo> findAllModulos(){
    	return mDAO.findAll(SegModulo.class, "nombreModulo");
    }
    
    public SegModulo insertarModulo(SegModulo nuevoModulo) throws Exception {
    	SegModulo modulo=new SegModulo();
    	modulo.setNombreModulo(nuevoModulo.getNombreModulo());
    	modulo.setIcono(nuevoModulo.getIcono());
    	modulo.setEstado(true);
    	mDAO.insertar(modulo);
    	return modulo;
    }
    
    public void eliminarModulo(int idSegModulo) throws Exception {
    	SegModulo modulo=(SegModulo) mDAO.findById(SegModulo.class, idSegModulo);
    	if(modulo.getSegPerfils().size()>0)
    		throw new Exception("No se puede eliminar un módulo que tiene perfiles asignados.");
    	mDAO.eliminar(SegModulo.class, idSegModulo);
    }
    
    public void actualizarModulo(SegModulo edicionModulo) throws Exception {
    	SegModulo modulo=(SegModulo) mDAO.findById(SegModulo.class, edicionModulo.getIdSegModulo());
    	modulo.setNombreModulo(edicionModulo.getNombreModulo());
    	modulo.setIcono(edicionModulo.getIcono());
    	modulo.setEstado(true);
    	mDAO.actualizar(modulo);
    }
    
    public List<SegAsignacion> findAsignacionByUsuario(int idSegUsuario){
    	String consulta="o.segUsuario.idSegUsuario="+idSegUsuario;
		List<SegAsignacion> listaAsignaciones=mDAO.findWhere(SegAsignacion.class, consulta, "o.segPerfil.segModulo.nombreModulo,o.segPerfil.nombrePerfil");
		return listaAsignaciones;
    }
    
    //Buscar Rol para usuarios
    public List<SegUsuario> findAsignacionByRol(int idSegRol){
    	String consulta="o.segRol.idSegRol="+idSegRol;
		List<SegUsuario> listaUsuarios=mDAO.findWhere(SegUsuario.class, consulta,null);
		return listaUsuarios;
    }
    
    /**
     * Permite asignar el acceso a un perfil del inventario de sistemas.
     * @param idSegUsuario El codigo del usuario.
     * @param idSegPerfil El codigo del perfil que se va a asignar.
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	public void asignarPerfil(int idSegUsuario,int idSegPerfil) throws Exception{
    	//Buscar los objetos primarios:
    	SegUsuario usuario=(SegUsuario)mDAO.findById(SegUsuario.class, idSegUsuario);
    	SegPerfil perfil=(SegPerfil)mDAO.findById(SegPerfil.class, idSegPerfil);
    	//verificar si ya existe:
    	String consulta="o.segPerfil.idSegPerfil="+idSegPerfil+" and o.segUsuario.idSegUsuario="+idSegUsuario;
    	List<SegAsignacion> asignaciones=mDAO.findWhere(SegAsignacion.class, consulta, null);
    	if(asignaciones==null || asignaciones.size()==0) {
	    	//crear la relacion:
	    	SegAsignacion asignacion=new SegAsignacion();
	    	asignacion.setSegPerfil(perfil);
	    	asignacion.setSegUsuario(usuario);
	    	//guardar la asignacion:
	    	mDAO.insertar(asignacion);
	    	mAuditoria.mostrarLog(getClass(), "asignarPerfil", "Perfil "+idSegPerfil+" asignado a usuario "+idSegUsuario);
    	}else {
    		throw new Exception("Ya existe la asignación de usuario/perfil ("+idSegUsuario+"/"+idSegPerfil+")");
    	}
    }
    
    public void eliminarAsignacion(int idSegAsignacion) throws Exception {
    	mDAO.eliminar(SegAsignacion.class, idSegAsignacion);
    }
    
    public List<SegPerfil> findPerfilesByModulo(int idSegModulo){
    	List<SegPerfil> listado=mDAO.findWhere(SegPerfil.class, "o.segModulo.idSegModulo="+idSegModulo, "o.nombrePerfil");
    	return listado;
    }
    
    public SegPerfil insertarPerfil(SegPerfil nuevoPerfil) throws Exception {
    	SegPerfil perfil=new SegPerfil();
    	perfil.setNombrePerfil(nuevoPerfil.getNombrePerfil());
    	perfil.setRutaAcceso(nuevoPerfil.getRutaAcceso());
    	perfil.setSegModulo(nuevoPerfil.getSegModulo());
    	perfil.setEstado(true);
    	mDAO.insertar(perfil);
    	return perfil;
    }
    
    public void eliminarPerfil(int idSegPerfil) throws Exception {
    	mDAO.eliminar(SegPerfil.class, idSegPerfil);
    }
    
    public void actualizarPerfil(SegPerfil edicionPerfil) throws Exception {
    	SegPerfil perfil=(SegPerfil) mDAO.findById(SegPerfil.class, edicionPerfil.getIdSegPerfil());
    	perfil.setNombrePerfil(edicionPerfil.getNombrePerfil());
    	perfil.setRutaAcceso(edicionPerfil.getRutaAcceso());
    	mDAO.actualizar(perfil);
    }
    public Persona BuscarPersona(int id) throws Exception {
    	Persona persona = (Persona) mDAO.findById(Persona.class, id); 
    	return persona;
    }

}
