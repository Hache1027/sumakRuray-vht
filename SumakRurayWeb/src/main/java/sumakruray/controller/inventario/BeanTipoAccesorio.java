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
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.core.entities.TipoAccesorio;
import sumakruray.model.inventario.managers.ManagerTipoAE;

@Named
@SessionScoped
public class BeanTipoAccesorio implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerTipoAE managerTipoAE;
	@Inject
	private BeanSegLogin beanSegLogin;

	// TipoAccesorio
	private List<TipoAccesorio> listaTipoAccesorios;
	private TipoAccesorio nuevoTipoAccesorio;
	private TipoAccesorio edicionTipoAccesorio;

	//
	public BeanTipoAccesorio() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___TipoAccesorio__--******************************************

	/**
	 * devolver lista de TipoAccesorio
	 */
	public void actionConsultarAllTipoAccesorio() {
		listaTipoAccesorios = managerTipoAE.findAllTipoAccesorios();
	}

	/**
	 * prepara variable para ventanas emergentes para un nuevo Responsable
	 */
	public void actionNuevoTipoAccesorio() {
		nuevoTipoAccesorio = new TipoAccesorio();
		nuevoTipoAccesorio.setTipAccCantidad(0);
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
	 */

	// Inicaliza las variables para un nuevo tipo de Accesorio
	public void inicializarVaribalesTipoAccesorio() {
		listaTipoAccesorios = managerTipoAE.findAllTipoAccesorios();
		nuevoTipoAccesorio = new TipoAccesorio();
		nuevoTipoAccesorio.setTipAccCantidad(0);
	}

	// Redirección al menu de TipoAccesorio
	public String actionMenuNuevoTipoAccesorios() {
		inicializarVaribalesTipoAccesorio();
		return "tipoAccesorio";
	}

	// Redirección al menu de TipoAccesorio
	public List<TipoAccesorio> actionTipoAccesorios() {
		listaTipoAccesorios = managerTipoAE.findAllTipoAccesorios();
		return listaTipoAccesorios;
	}

	// Crear un nuevo TipoAccesorios
	public void actionListenerInsertarNuevoTipoAccesorio() {
		try {
			managerTipoAE.insertarTipoAccesorio(beanSegLogin.getLoginDTO(), nuevoTipoAccesorio);
			inicializarVaribalesTipoAccesorio();

			JSFUtil.crearMensajeINFO("TipoAccesorio insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger TipoAccesorio para editar
	public void actionSeleccionarEdicionTipoAccesorio(TipoAccesorio TipoAccesorio) {
		edicionTipoAccesorio = TipoAccesorio;
	}

	// *****************--__Getter and
	// Setter__--************************************+

	public List<TipoAccesorio> getListaTipoAccesorios() {
		return listaTipoAccesorios;
	}

	public void setListaTipoAccesorios(List<TipoAccesorio> listaTipoAccesorios) {
		this.listaTipoAccesorios = listaTipoAccesorios;
	}

	public TipoAccesorio getNuevoTipoAccesorio() {
		return nuevoTipoAccesorio;
	}

	public void setNuevoTipoAccesorio(TipoAccesorio nuevoTipoAccesorio) {
		this.nuevoTipoAccesorio = nuevoTipoAccesorio;
	}

	public TipoAccesorio getEdicionTipoAccesorio() {
		return edicionTipoAccesorio;
	}

	public void setEdicionTipoAccesorio(TipoAccesorio edicionTipoAccesorio) {
		this.edicionTipoAccesorio = edicionTipoAccesorio;
	}

}
