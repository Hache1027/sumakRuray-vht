package sumakruray.controller.inventario;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import sumakruray.controller.JSFUtil;
import sumakruray.controller.seguridades.BeanSegLogin;
import sumakruray.model.core.entities.TipoEquipo;
import sumakruray.model.inventario.managers.ManagerTipoAE;

@Named
@SessionScoped
public class BeanTipoEquipo implements Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private ManagerTipoAE managerTipoAE;
	@Inject
	private BeanSegLogin beanSegLogin;

	// TipoEquipo
	private List<TipoEquipo> listaTipoEquipos;
	private TipoEquipo nuevoTipoEquipo;
	private TipoEquipo edicionTipoEquipo;

	//
	public BeanTipoEquipo() {
		// TODO Auto-generated constructor stub
	}

	// **********************--___TipoEquipo__--******************************************
	@PostConstruct
	public void inicializar() {
		listaTipoEquipos = managerTipoAE.findAllTipoEquipos();
		nuevoTipoEquipo = new TipoEquipo();
		nuevoTipoEquipo.setTipEquiCantidad(0);
	}

	// Crear TipoEquipo
	public String actionMenuNuevoTipoEquipos() {
		nuevoTipoEquipo = new TipoEquipo();

		nuevoTipoEquipo.setTipEquiCantidad(0);
		return "TipoEquipo";
	}

	// Crear TipoEquipos
	public void actionListenerInsertarNuevoTipoEquipo() {
		try {
			managerTipoAE.insertarTipoEquipo(beanSegLogin.getLoginDTO(), nuevoTipoEquipo);
			listaTipoEquipos = managerTipoAE.findAllTipoEquipos();
			nuevoTipoEquipo = new TipoEquipo();

			nuevoTipoEquipo.setTipEquiCantidad(0);
			JSFUtil.crearMensajeINFO("TipoEquipo insertado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	// Escoger TipoEquipo para editar
	public void actionSeleccionarEdicionTipoEquipo(TipoEquipo TipoEquipo) {
		edicionTipoEquipo = TipoEquipo;
	}

	// *****************--__Getter and
	// Setter__--************************************+

	public List<TipoEquipo> getListaTipoEquipos() {
		return listaTipoEquipos;
	}

	public void setListaTipoEquipos(List<TipoEquipo> listaTipoEquipos) {
		this.listaTipoEquipos = listaTipoEquipos;
	}

	public TipoEquipo getNuevoTipoEquipo() {
		return nuevoTipoEquipo;
	}

	public void setNuevoTipoEquipo(TipoEquipo nuevoTipoEquipo) {
		this.nuevoTipoEquipo = nuevoTipoEquipo;
	}

	public TipoEquipo getEdicionTipoEquipo() {
		return edicionTipoEquipo;
	}

	public void setEdicionTipoEquipo(TipoEquipo edicionTipoEquipo) {
		this.edicionTipoEquipo = edicionTipoEquipo;
	}

}
