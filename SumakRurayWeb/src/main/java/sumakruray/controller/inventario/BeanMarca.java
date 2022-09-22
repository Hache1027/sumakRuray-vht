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
import sumakruray.model.core.entities.Marca;
import sumakruray.model.core.entities.Proveedor;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.core.entities.TipoAccesorio;
import sumakruray.model.inventario.managers.ManagerMarca;

@Named
@SessionScoped
public class BeanMarca implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerMarca managerMarca;
	@Inject
	private BeanSegLogin beanSegLogin;
	// Marca
	private List<Marca> listaMarcas;
	private Marca nuevoMarca;
	private Marca edicionMarca;
	// Tiempo
	private Timestamp tiempo;
	//

	public BeanMarca() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___Marca__--******************************************
	@PostConstruct
	public void inicializar() {
		tiempo = new Timestamp(System.currentTimeMillis());
	}

	/**
	 * devolver lista de Marcas
	 */
	public void actionConsultarAllMarca() {
		listaMarcas = managerMarca.findAllMarcas();
	}

	/**
	 * Preparacion de varibale para un nuevo rgistro de Marca
	 */
	public void actionNuevoMarca() {
		nuevoMarca = new Marca();
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

	// redireccionamiento a la pagina menu de Marca
	public String actionMenuNuevoMarcas() {
		nuevoMarca = new Marca();
		return "Marca";
	}

	// Insertar un nuevo registro de Maca
	public void actionListenerInsertarNuevoMarca() {
		try {
			managerMarca.insertarMarca(beanSegLogin.getLoginDTO(), nuevoMarca);
			listaMarcas = managerMarca.findAllMarcas();
			nuevoMarca = new Marca();
			JSFUtil.crearMensajeINFO("Marca insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger Marca para editar
	public void actionSeleccionarEdicionMarca(Marca Marca) {
		edicionMarca = Marca;
	}

	// *****************--__Getter and
	// Setter__--************************************+

	public List<Marca> getListaMarcas() {
		return listaMarcas;
	}

	public void setListaMarcas(List<Marca> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}

	public Marca getNuevoMarca() {
		return nuevoMarca;
	}

	public void setNuevoMarca(Marca nuevoMarca) {
		this.nuevoMarca = nuevoMarca;
	}

	public Marca getEdicionMarca() {
		return edicionMarca;
	}

	public void setEdicionMarca(Marca edicionMarca) {
		this.edicionMarca = edicionMarca;
	}

}
