package sumakruray.controller.inventario;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import sumakruray.controller.JSFUtil;
import sumakruray.controller.seguridades.BeanSegLogin;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioBaja;
import sumakruray.model.core.entities.BodegaAccesorio;
import sumakruray.model.core.entities.BodegaEquipo;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoAccesorio;
import sumakruray.model.core.entities.EquipoBaja;
import sumakruray.model.core.entities.Persona;
import sumakruray.model.inventario.managers.ManagerAccesorio;
import sumakruray.model.inventario.managers.ManagerBaja;
import sumakruray.model.inventario.managers.ManagerBodega;
import sumakruray.model.inventario.managers.ManagerEquipo;
import sumakruray.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanBaja implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerSeguridades managerSeguridades;
	@EJB
	private ManagerAccesorio managerAccesorio;
	@EJB
	private ManagerEquipo managerEquipo;
	@EJB
	private ManagerBodega managerBodega;
	@EJB
	private ManagerBaja managerBaja;
	@Inject
	private BeanSegLogin beanSegLogin;
	@Inject
	private BeanBodega beanBodega;
	@Inject
	private BeanAccesorio beanAccesorio;
	// Baja a Accesorio
	private AccesorioBaja nuevoBajaAccesorio;
	private List<AccesorioBaja> listaAccesorioBajas;
	// Baja a Equipo
	private EquipoBaja nuevoBajaEquipo;
	private List<EquipoBaja> listaEquipoBajas;
	// Tiempo
	private Timestamp tiempo;
	// Accesorio
	private Accesorio SeleccionBajaAccesorio;
	// Equipo
	private Equipo SeleccionBajaEquipo;
	private List<EquipoAccesorio> listaAccesoriosEquipo;
	// BOdegaAccesorio
	private List<BodegaAccesorio> bodegaAccesorioBuscado;
	private BodegaAccesorio bodegaAccesorioSeleccionado;

	// BOdegaEquipo
	private List<BodegaEquipo> bodegaEquipoBuscado;
	private BodegaEquipo bodegaEquipoSeleccionado;
//Varios
	private String estado;

	public BeanBaja() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void inicializar() {
		listaAccesorioBajas = managerBaja.findAllAccesorioBajas();
		listaEquipoBajas = managerBaja.findAllEquipoBajas();
		// listaAccesorioBodegas =
		// managerInventario.findWhereByAcceBodEstado("Inactivo");
		tiempo = new Timestamp(System.currentTimeMillis());

	}

	// ****************---___BAJA_ACCESORIO__---**************

	// Insertar un Registro de Accesorio a la bodega

	public void actionSeleccisonAccesorioBaja(Accesorio accesorio) throws Exception {
		nuevoBajaAccesorio = new AccesorioBaja();
		bodegaAccesorioSeleccionado = new BodegaAccesorio();
		SeleccionBajaAccesorio = beanAccesorio.ConsultarAccesorioAtributoEquipo(accesorio);
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dialogoAccesorioBaja').show()");
	}
//Redireccionamiento a la pagina de baja_accesorios

	public String actionSelectionAccesoriosBajas() throws Exception {
		listaAccesorioBajas = managerBaja.findAllAccesorioBajas();
		return "baja_accesorios";
	}

//Insertar un nuevo registri de Accesorio de Baja
	public void actionListenerInsertarNuevoAccesorioABaja() {
		try {

			nuevoBajaAccesorio.setBajaFechaCreacion(tiempo);
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoBajaAccesorio.setBajaUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			nuevoBajaAccesorio.setAccesorio(SeleccionBajaAccesorio);
			System.out.println("pppppppppppppppppp");

			SeleccionBajaAccesorio.setAcceEstado("De Baja");
			managerAccesorio.actualizarAccesorio(beanSegLogin.getLoginDTO(), SeleccionBajaAccesorio);
			// beanBodega.actionListenerActualizarBodegaPorAccesorio(bodegaAccesorioSeleccionado,
			// "De Baja");

			managerBaja.insertarAccesorioBaja(beanSegLogin.getLoginDTO(), nuevoBajaAccesorio);
			listaAccesorioBajas = managerBaja.findAllAccesorioBajas();
			nuevoBajaAccesorio = new AccesorioBaja();
			System.out.println("ooooooooooooooo");
			beanBodega.actionSelectionAccesoriosInactivos();
			JSFUtil.crearMensajeINFO("Accesorio dado de Baja");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	// ****************---___BAJA_ACCESORIO__---**************
	// Redireccionamiento a la pagina de baja_equipos
	public String actionSelectionEquiposBajas() throws Exception {
		listaEquipoBajas = managerBaja.findAllEquipoBajas();
		return "baja_equipos";
	}

	// Llenado de los datos del nuevo registro de BodegaEquipo
	public void actionSeleccisonEquipoBaja(Equipo Equipo) throws Exception {
		nuevoBajaEquipo = new EquipoBaja();
		SeleccionBajaEquipo = Equipo;
		listaAccesoriosEquipo = managerEquipo.findByEquiIdSeleccionado(Equipo.getEquiId());
		Equipo.setEquipoAccesorios(listaAccesoriosEquipo);
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dialogoEquipoBaja').show()");
	}

	// Insertar un Registro de Equipo a la bodega
	public void actionListenerInsertarNuevoEquipoABaja() {
		try {

			nuevoBajaEquipo.setBajaFechaCreacion(tiempo);
			int IdSegUsuario = beanSegLogin.getLoginDTO().getIdSegUsuario();
			Persona persona = managerSeguridades.BuscarPersona(IdSegUsuario);
			nuevoBajaEquipo.setBajaUsuarioCrea(persona.getPerNombres() + " " + persona.getPerApellidos());
			nuevoBajaEquipo.setEquipo(SeleccionBajaEquipo);

			if (SeleccionBajaEquipo.getEquipoAccesorios().size() > 0) {

				for (int i = 0; i < SeleccionBajaEquipo.getEquipoAccesorios().size(); i++) {

					SeleccionBajaAccesorio = SeleccionBajaEquipo.getEquipoAccesorios().get(i).getAccesorio();
					nuevoBajaAccesorio = new AccesorioBaja();
					nuevoBajaAccesorio.setBajaDescripcion(nuevoBajaEquipo.getBajaDescripcion());
					bodegaAccesorioBuscado = managerBodega
							.findWhereByAcceIdBodegaOne(SeleccionBajaAccesorio.getAcceId());
					bodegaAccesorioSeleccionado = bodegaAccesorioBuscado.get(0);

					actionListenerInsertarNuevoAccesorioABaja();

				}
			}
			SeleccionBajaEquipo.setEquiEstado("De Baja");
			managerEquipo.cambiarEstadoEquipo(beanSegLogin.getLoginDTO(), SeleccionBajaEquipo, "De Baja", "");
			managerBaja.insertarEquipoBaja(nuevoBajaEquipo);
			listaEquipoBajas = managerBaja.findAllEquipoBajas();
			nuevoBajaEquipo = new EquipoBaja();
			beanBodega.actionSelectionEquiposInactivos();
			JSFUtil.crearMensajeINFO("Equipos con sus Accesorios dado de Baja");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}

	}

	// ****************---___SETTERS AND GETTERS__---**************

	public List<AccesorioBaja> getListaAccesorioBajas() {
		return listaAccesorioBajas;
	}

	public void setListaAccesorioBajas(List<AccesorioBaja> listaAccesorioBajas) {
		this.listaAccesorioBajas = listaAccesorioBajas;
	}

	public AccesorioBaja getNuevoBajaAccesorio() {
		return nuevoBajaAccesorio;
	}

	public void setNuevoBajaAccesorio(AccesorioBaja nuevoBajaAccesorio) {
		this.nuevoBajaAccesorio = nuevoBajaAccesorio;
	}

	public EquipoBaja getNuevoBajaEquipo() {
		return nuevoBajaEquipo;
	}

	public void setNuevoBajaEquipo(EquipoBaja nuevoBajaEquipo) {
		this.nuevoBajaEquipo = nuevoBajaEquipo;
	}

	public List<EquipoBaja> getListaEquipoBajas() {
		return listaEquipoBajas;
	}

	public void setListaEquipoBajas(List<EquipoBaja> listaEquipoBajas) {
		this.listaEquipoBajas = listaEquipoBajas;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Accesorio getSeleccionBajaAccesorio() {
		return SeleccionBajaAccesorio;
	}

	public void setSeleccionBajaAccesorio(Accesorio seleccionBajaAccesorio) {
		SeleccionBajaAccesorio = seleccionBajaAccesorio;
	}

	public Equipo getSeleccionBajaEquipo() {
		return SeleccionBajaEquipo;
	}

	public void setSeleccionBajaEquipo(Equipo seleccionBajaEquipo) {
		SeleccionBajaEquipo = seleccionBajaEquipo;
	}

}
