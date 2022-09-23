package sumakruray.model.inventario.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioAtributo;
import sumakruray.model.core.entities.AccesorioMantenimiento;
import sumakruray.model.core.entities.Atributo;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoAccesorio;
import sumakruray.model.core.entities.EquipoAtributo;
import sumakruray.model.core.entities.ListaIp;
import sumakruray.model.core.entities.TipoEquipo;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerEquipo
 */
@Stateless
@LocalBean
public class ManagerEquipo {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
	@EJB
	private ManagerBitacora managerBitacora;

	/**
	 * Default constructor.
	 */
	public ManagerEquipo() {
		// TODO Auto-generated constructor stub
	}

	// *********************************EQUIPO*******************************************************************************************
	/**
	 * Conusltar todos los registros de la Tabla Equipo
	 */
	public List<Equipo> findAllEquipos() {
		return mDAO.findAll(Equipo.class, null);
	}

	/**
	 * Cousltar un registro equipo por 2 tipos de estados
	 */
	public List<Equipo> findWhereByEquiEstados(String Activo, String Nuevo_Activo) throws Exception {
		return mDAO.findWhere(Equipo.class, "equi_estado='" + Activo + "' or equi_estado= '" + Nuevo_Activo + "'",
				null);
	}

	/**
	 * Cousltar un registro equipo por un estado
	 */
	public List<Equipo> findWhereByEquiEstado(String estado) throws Exception {
		return mDAO.findWhere(Equipo.class, "equi_estado='" + estado + "'", null);
	}

	/**
	 * Metodo para Adicionar un Accesorio o un Atributo a las listas temprales
	 * creadas para cada uno
	 */
	public Equipo adicionarAccesorioAtributo(Equipo cabecera, Equipo nuevoEquipo, int acceIdSeleccionado,
			int atriIdSeleccionado, String valorAtributo, String tipoObjeto)

			throws Exception {
		// comprobar si es el primer producto:
		if (cabecera == null) {

			cabecera = new Equipo();
			cabecera.setEquipoAccesorios(new ArrayList<EquipoAccesorio>());
			cabecera.setEquipoAtributos(new ArrayList<EquipoAtributo>());
			cabecera.setProveedor(nuevoEquipo.getProveedor());
			cabecera.setSegDependencia(nuevoEquipo.getSegDependencia());
			cabecera.setEquiGarantia(nuevoEquipo.getEquiGarantia());
			cabecera.setEquiAnoVidaUtil(nuevoEquipo.getEquiAnoVidaUtil());
			cabecera.setTipoEquipo(nuevoEquipo.getTipoEquipo());
			cabecera.setResponsable(nuevoEquipo.getResponsable());
			cabecera.setMarca(nuevoEquipo.getMarca());
			cabecera.setEquiNroSerie(nuevoEquipo.getEquiNroSerie());
			cabecera.setEquiNombre(nuevoEquipo.getEquiNombre());
			cabecera.setEquiValor(nuevoEquipo.getEquiValor());
			cabecera.setEquiCodBodega(nuevoEquipo.getEquiCodBodega());
			cabecera.setEquiFechaCreacion(nuevoEquipo.getEquiFechaCreacion());
			cabecera.setEquiUsuarioCrea(nuevoEquipo.getEquiUsuarioCrea());
			cabecera.setEquiEstado(nuevoEquipo.getEquiEstado());

		}
		if (tipoObjeto.equals("Atributo")) {
			System.out.println(tipoObjeto + "2");
			if (atriIdSeleccionado > 0) {
				Atributo atributo = (Atributo) mDAO.findById(Atributo.class, atriIdSeleccionado);
				EquipoAtributo detalle = new EquipoAtributo();
				detalle.setAtributo(atributo);
				detalle.setEquipo(cabecera);
				detalle.setAtriDescripcion(valorAtributo);
				cabecera.getEquipoAtributos().add(detalle);
			}
		} else if (tipoObjeto.equals("Accesorio")) {
			System.out.println(tipoObjeto + "2");
			if (acceIdSeleccionado > 0) {
				Accesorio Accesorio = (Accesorio) mDAO.findById(Accesorio.class, acceIdSeleccionado);
				EquipoAccesorio detalle = new EquipoAccesorio();
				detalle.setAccesorio(Accesorio);
				detalle.setEquipo(cabecera);
				cabecera.getEquipoAccesorios().add(detalle);
			}
		}
		return cabecera;
	}

	/**
	 * Insertar un nuevo Registro de Equipo con sus Accesorios o Atributos Sumar el
	 * tipo equipo segun el tipo de equipo escogido
	 */
	public void registrarEquipo(LoginDTO loginDTO, Equipo equipo) throws Exception {
		String enlace = "";
		if (equipo == null)
			throw new Exception("Debe Ingresar los datos Correspondientes");
		enlace += " con los Accesorios: ";
		for (int i = 0; i < equipo.getEquipoAccesorios().size(); i++) {
			// Auditoria del los eventos
			Accesorio accesorio = equipo.getEquipoAccesorios().get(i).getAccesorio();
			enlace += " <> " + accesorio.getAcceNombre() + " - " + accesorio.getAcceNroSerie();
			managerBitacora.mostrarLogAccesorio(loginDTO, accesorio, "Insertar nuevo Equipo",
					accesorio.getAcceNombre() + " añadido al Equipo Nuevo " + equipo.getEquiNombre());

		}
		enlace += " con las Cacareristicas : ";

		for (int i = 0; i < equipo.getEquipoAtributos().size(); i++) {
			// Auditoria del los eventos
			EquipoAtributo equipoAtributo = equipo.getEquipoAtributos().get(i);
			enlace += " <> " + equipoAtributo.getAtributo().getAtriNombre() + " - "
					+ equipoAtributo.getAtriDescripcion();
		}

		TipoEquipo tipoEquipo = new TipoEquipo();
		tipoEquipo = (TipoEquipo) mDAO.findById(TipoEquipo.class, equipo.getTipoEquipo().getTipEquiId());
		tipoEquipo.setTipEquiCantidad(tipoEquipo.getTipEquiCantidad() + 1);

		mDAO.actualizar(tipoEquipo);

		mDAO.insertar(equipo);

		managerBitacora.mostrarLogEquipo(loginDTO, equipo, "insertarEquipo", " Creación del Equipo " + enlace);
	}

	/**
	 * Actualizar un Registro Equipo
	 */
	public void actualizarEquipo(LoginDTO loginDTO, Equipo edicionEquipo) throws Exception {
		String enlace = "";
		Equipo Equipo = (Equipo) mDAO.findById(Equipo.class, edicionEquipo.getEquiId());

		Equipo.setProveedor(edicionEquipo.getProveedor());

		if (!Equipo.getResponsable().getRespApellidos().equals(edicionEquipo.getResponsable().getRespApellidos())) {
			enlace += "Modificado Responsable a : " + edicionEquipo.getResponsable().getRespApellidos();
		}

		Equipo.setResponsable(edicionEquipo.getResponsable());
		if (!Equipo.getSegDependencia().getDepDescripcion()
				.equals(edicionEquipo.getSegDependencia().getDepDescripcion())) {
			enlace += " <>  Modificado Dependencia a : " + edicionEquipo.getSegDependencia().getDepDescripcion();
		}

		Equipo.setSegDependencia(edicionEquipo.getSegDependencia());
		Equipo.setEquiNombre(edicionEquipo.getEquiNombre());
		Equipo.setEquiNroSerie(edicionEquipo.getEquiNroSerie());
		Equipo.setEquiValor(edicionEquipo.getEquiValor());
		Equipo.setEquiFechaModificacion(edicionEquipo.getEquiFechaModificacion());
		Equipo.setEquiUsuarioModifica(edicionEquipo.getEquiUsuarioModifica());
		Equipo.setEquiEstado(edicionEquipo.getEquiEstado());
		enlace += "Accesorios Anteriores : \n";
		for (int i = 0; i < Equipo.getEquipoAccesorios().size(); i++) {
			EquipoAccesorio equipoAccesorio = Equipo.getEquipoAccesorios().get(i);
			enlace += "   <> " + equipoAccesorio.getAccesorio().getAcceNombre() + " - "
					+ equipoAccesorio.getAccesorio().getAcceNroSerie();

		}
		enlace += "\n Accesorios Actuales Actualizados :\n";
		for (int i = 0; i < edicionEquipo.getEquipoAccesorios().size(); i++) {
			EquipoAccesorio edicionEquipoAccesorio = edicionEquipo.getEquipoAccesorios().get(i);
			enlace += "   <> " + edicionEquipoAccesorio.getAccesorio().getAcceNombre() + " - "
					+ edicionEquipoAccesorio.getAccesorio().getAcceNroSerie();

		}

		Equipo.setEquipoAccesorios(edicionEquipo.getEquipoAccesorios());
		Equipo.setEquipoAtributos(edicionEquipo.getEquipoAtributos());
		if (enlace.equals("")) {
			enlace = "Ninguna";
		}
		mDAO.actualizar(Equipo);
		managerBitacora.mostrarLogEquipo(loginDTO, Equipo, "ActualizarEquipo", " Cambios : " + enlace);
	}

	/**
	 * Actualizar un Registro Equipo desde Mantenimiento
	 */
	public void actualizarEquipofromMantenimiento(LoginDTO loginDTO, Equipo edicionEquipo) throws Exception {
		String enlace = "";
		Equipo Equipo = (Equipo) mDAO.findById(Equipo.class, edicionEquipo.getEquiId());

		enlace += " Caractericticas anteriores: ";
		for (int i = 0; i < Equipo.getEquipoAtributos().size(); i++) {
			EquipoAtributo equipoAtributo = Equipo.getEquipoAtributos().get(i);
			enlace += " <> " + equipoAtributo.getAtributo().getAtriNombre() + " = "
					+ equipoAtributo.getAtriDescripcion();
		}
		enlace += " Caractericticas actuales: ";
		for (int i = 0; i < edicionEquipo.getEquipoAtributos().size(); i++) {
			EquipoAtributo edicionEquipoAtributo = edicionEquipo.getEquipoAtributos().get(i);
			enlace += " <> " + edicionEquipoAtributo.getAtributo().getAtriNombre() + " = "
					+ edicionEquipoAtributo.getAtriDescripcion();
		}

		Equipo.setEquipoAtributos(edicionEquipo.getEquipoAtributos());
		mDAO.actualizar(Equipo);
		managerBitacora.mostrarLogEquipo(loginDTO, Equipo, "ActualizarEquipo",
				" Cambios : " + enlace + "En mantenimiento");
	}

	/**
	 * Insertar un nuevo Equipo(no utilizado) y aditar todo los accesorios del
	 * Equipo
	 */
	public void insertarEquipo(LoginDTO loginDTO, Equipo nuevoEquipo) throws Exception {
		mDAO.insertar(nuevoEquipo);

	}

	// Observacion
	/**
	 * Eliminar un Accesorio de un Equipo
	 */
	public void eliminarAccesorioEquipo(int equiAceeId) throws Exception {
		System.out.println(equiAceeId + "zzzzzzzzzzzzzzzzzz--- metodo eliminar");
		EquipoAccesorio equipoAccesorio = (EquipoAccesorio) mDAO.findById(EquipoAccesorio.class, equiAceeId);
		System.out.println(equipoAccesorio.getEquiAccId() + "--- metodo eliminar");

	}

	/**
	 * Eliminar un deatlle EquipoAtributo de la tabla EquipoAtributo
	 */

	// Eliminar un Atributo de un Equipo
	public void eliminarAtributoEquipo(int atriId) throws Exception {
		mDAO.eliminar(EquipoAtributo.class, atriId);
	}

	/**
	 * Consultar un Resgistro de un Equipo por equiId
	 */

	public Equipo findByIdEquipo(int equiId) throws Exception {
		return (Equipo) mDAO.findById(Equipo.class, equiId);
	}

	/**
	 * Consulta de los registros EquiposAccesorios por equi Id de un Equipo de la
	 * tabla EquipoAccesorio
	 */
	public List<EquipoAccesorio> findByEquiIdSeleccionado(int equiIdSeleccionado) throws Exception {
		return mDAO.findWhere(EquipoAccesorio.class, "equi_id=" + equiIdSeleccionado + "", null);
	}

	/**
	 * Consulta de los registros EquiposAtributo por equi Id de un Equipo de la
	 * tabla EquipoAtributo
	 */

	// Buscar un detalle de Equipo Atributo de un Equipo por EquiId
	public List<EquipoAtributo> findByEquiIdSeleccionadoAtributo(int equiIdSeleccionado) throws Exception {
		return mDAO.findWhere(EquipoAtributo.class, "equi_id=" + equiIdSeleccionado + "", null);
	}

	/**
	 * Consulta de un Resgistro de Equipo que contenga un Accesorio por
	 * AcceIdSeleccionado
	 */

	// Buscar Detalle EquipoAccesorio de un Equipo por acceId
	public List<EquipoAccesorio> findByAcceIdSeleccionadforEquipo(int AcceIdSeleccionado) throws Exception {
		return mDAO.findWhere(EquipoAccesorio.class, "acce_id=" + AcceIdSeleccionado + "", null);
	}

	/**
	 * Elimado de un Registro EquipoAccesorio de un Equipo de una lista
	 * EquipoAccesorio Temporal
	 */

	// Quitar un Accesorio de un Equipo
	public Equipo QuitarAccesorio(Equipo cabecera, int index) throws Exception {
		cabecera.getEquipoAccesorios().remove(index);
		return cabecera;
	}

	/**
	 * Eliminado Físico de un Resgitro de un Equipo(No utilizado)
	 */
	public void deleteEquiAccByAccId(EquipoAccesorio equipoAccesorio) throws Exception {
		mDAO.eliminar(EquipoAccesorio.class, equipoAccesorio.getEquiAccId());
	}

	/**
	 * Elimado de un Registro EquipoAtributo de un Equipo de una lista
	 * EquipoAtributo Temporal
	 */
	public Equipo QuitarAtributo(Equipo cabecera, int index) throws Exception {
		cabecera.getEquipoAtributos().remove(index);
		return cabecera;
	}

	/**
	 * Consultar un registro EquipoAccesorio de un Equipo por su equiId & acce_id
	 */

	// Buscar un Accesorio por Id
	public List<EquipoAccesorio> findWhereByEquiAcceId(int equiId, int acceId) throws Exception {
		return mDAO.findWhere(EquipoAccesorio.class, "equi_id=" + equiId + " and acce_id=" + acceId, null);
	}

	/**
	 * Consultar los registros de EquipoAccesorio de un Equipo por acce_id
	 */
	public List<EquipoAccesorio> findEquiAcceByOneAcce(int acceId) throws Exception {
		return mDAO.findWhere(EquipoAccesorio.class, "acce_id=" + acceId, null);
	}

	/**
	 * Consultar un registro EquipoAtributo de un Equipo por su equiId & AtriId
	 */

	// Buscar un Atributo por Id
	public List<EquipoAtributo> findWhereByEquiAtriId(int equiId, int atriId) throws Exception {
		return mDAO.findWhere(EquipoAtributo.class, "equi_id=" + equiId + " and atri_id=" + atriId, null);
	}

	/**
	 * Metodo para Modificar el estado de un Equipo
	 */

	// Cambiar de Estado de un Equipo
	public void cambiarEstadoEquipo(LoginDTO loginDTO, Equipo equipo, String estado, String observacion)
			throws Exception {

		if (estado.equals("Inactivo")) {
			managerBitacora.mostrarLogEquipo(loginDTO, equipo, "EnviarBodega",
					" Equipo enviado a Bodega por: " + observacion);
			mDAO.actualizar(equipo);
		} else if (estado.equals("Activo")) {
			managerBitacora.mostrarLogEquipo(loginDTO, equipo, "ActivarFuncionalidad",
					" Equipo Activado a Funcionalidad con los accesorios: " + observacion);
			mDAO.actualizar(equipo);
		} else if (estado.equals("Mantenimiento")) {
			managerBitacora.mostrarLogEquipo(loginDTO, equipo, "MantenimientoEquipo",
					" Equipo en Mantenimiento por:  " + observacion);
			mDAO.actualizar(equipo);
		}

	}

	/**
	 * Consultas de registros de los Equipos con un sin IP
	 */
	public List<Equipo> findEquiposSnId() throws Exception {
		return mDAO.findWheretwoClass(Equipo.class, ListaIp.class, "o.equiId =e.equipo.equiId", null);
	}

	// *********************************EquipoS*********************************************************************************************

}
