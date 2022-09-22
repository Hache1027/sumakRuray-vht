package sumakruray.model.inventario.managers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Accesorio;
import sumakruray.model.core.entities.AccesorioMantenimiento;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.entities.EquipoMantenimiento;
import sumakruray.model.core.entities.Mantenimiento;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerMantenimiento
 */
@Stateless
@LocalBean
public class ManagerMantenimiento {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;
	@EJB
	private ManagerBitacora managerBitacora;

	/**
	 * Default constructor.
	 */
	public ManagerMantenimiento() {
		// TODO Auto-generated constructor stub
	}

	// *********************************EquipoMantenimiento*******************************************************************************************
	/**
	 * Inseertra un nuevo registro de Mantenimiento
	 */
	public void insertarMantenimiento(Mantenimiento nuevoMantenimiento) throws Exception {
		mDAO.insertar(nuevoMantenimiento);
	}

	/**
	 * Consulta de registro de un MantenimientoAccesorio por acceId
	 */
	public List<AccesorioMantenimiento> findAccesorioMantenimientoByAcceId(int AcceId) throws Exception {
		return mDAO.findWhere(AccesorioMantenimiento.class, "acce_id=" + AcceId + "", null);

	}

	/**
	 * Consulta de registro de un MantenimientoEquipo por equiId
	 */
	public List<EquipoMantenimiento> findEquipoMantenimientoByEquiId(int equiId) throws Exception {
		return mDAO.findWhere(EquipoMantenimiento.class, "equi_id=" + equiId + "", null);

	}

	/**
	 * Consultar de registro de un MantenimientoEquipo por su manEquiId(primary key)
	 */
	public EquipoMantenimiento findEquipoMantenimientoById(int manEquiId) throws Exception {
		return (EquipoMantenimiento) mDAO.findById(EquipoMantenimiento.class, manEquiId);
	}

	/**
	 * Cambiar el Estado de un Registro de Mantenimiento directamente
	 */
	public void cambiarEstadoAccesorioDirecto(Mantenimiento mantenimiento) throws Exception {

		mDAO.actualizar(mantenimiento);

	}

	/**
	 * Cambiar el Estado de un Registro de Mantenimiento
	 */
	public void cambiarEstadoMantenimiento(int manId, String estado) throws Exception {
		Mantenimiento mantenimiento = (Mantenimiento) mDAO.findById(Mantenimiento.class, manId);
		mantenimiento.setManEstado(estado);
		System.out.println("cambio el Estado a" + estado);
		mDAO.actualizar(mantenimiento);
	}

	/**
	 * Consulta de todos los registro de Equipo Mantenimeinto
	 */
	public List<EquipoMantenimiento> findAllEquipoMantenimientos() {
		return mDAO.findAll(EquipoMantenimiento.class, null);
	}

	/**
	 * Consulta de todos los registro de Accesorio Mantenimeinto
	 */
	public List<AccesorioMantenimiento> findAllAccesorioMantenimientos() {
		return mDAO.findAll(AccesorioMantenimiento.class, null);
	}

	/**
	 * Consulta de todos los registro de Mantenimeinto
	 */
	public List<Mantenimiento> findAllMantenimientos() {
		return mDAO.findAll(Mantenimiento.class, null);
	}

	/**
	 * Actualizar un resgistro de Equipo Mantenimiento
	 */
	public void actualizarEquipoMantenimiento(LoginDTO loginDTO, EquipoMantenimiento edicionEquipoMantenimiento)
			throws Exception {

		EquipoMantenimiento EquipoMantenimiento = (EquipoMantenimiento) mDAO.findById(EquipoMantenimiento.class,
				edicionEquipoMantenimiento.getEquiManId());

		EquipoMantenimiento.getMantenimiento()
				.setManTipoIntExt(edicionEquipoMantenimiento.getMantenimiento().getManTipoIntExt());
		EquipoMantenimiento.getMantenimiento()
				.setManTipoPreCorr(edicionEquipoMantenimiento.getMantenimiento().getManTipoPreCorr());
		EquipoMantenimiento.getMantenimiento()
				.setManDescripcion(edicionEquipoMantenimiento.getMantenimiento().getManDescripcion());
		EquipoMantenimiento.getMantenimiento()
				.setManDiagnostico(edicionEquipoMantenimiento.getMantenimiento().getManDiagnostico());
		EquipoMantenimiento.getMantenimiento()
				.setManFuncionarioRecibe(edicionEquipoMantenimiento.getMantenimiento().getManFuncionarioRecibe());
		EquipoMantenimiento.getMantenimiento()
				.setManFuncionarioEntrega(edicionEquipoMantenimiento.getMantenimiento().getManFuncionarioEntrega());
		EquipoMantenimiento.getMantenimiento()
				.setManUsuarioRecibe(edicionEquipoMantenimiento.getMantenimiento().getManUsuarioRecibe());
		EquipoMantenimiento.getMantenimiento()
				.setManFechaModificacion(edicionEquipoMantenimiento.getMantenimiento().getManFechaModificacion());
		EquipoMantenimiento.getMantenimiento()
				.setManUsuarioModifica(edicionEquipoMantenimiento.getMantenimiento().getManUsuarioModifica());
		
		mDAO.actualizar(EquipoMantenimiento);
		managerBitacora.mostrarLogEquipo(loginDTO, edicionEquipoMantenimiento.getEquipo(), "ActualizarMantenimiento",
				" Hoja de Mantenimiento Actualizado ");
	}

	/**
	 * Actualizar un resgistro de AccesorioMantenimiento
	 */
	public void actualizarAccesorioMantenimiento(LoginDTO loginDTO,
			AccesorioMantenimiento edicionAccesorioMantenimiento) throws Exception {

		AccesorioMantenimiento AccesorioMantenimiento = (AccesorioMantenimiento) mDAO
				.findById(AccesorioMantenimiento.class, edicionAccesorioMantenimiento.getAcceManId());

		AccesorioMantenimiento.getMantenimiento()
				.setManTipoIntExt(edicionAccesorioMantenimiento.getMantenimiento().getManTipoIntExt());
		AccesorioMantenimiento.getMantenimiento()
				.setManTipoPreCorr(edicionAccesorioMantenimiento.getMantenimiento().getManTipoPreCorr());
		AccesorioMantenimiento.getMantenimiento()
				.setManDescripcion(edicionAccesorioMantenimiento.getMantenimiento().getManDescripcion());
		AccesorioMantenimiento.getMantenimiento()
				.setManDiagnostico(edicionAccesorioMantenimiento.getMantenimiento().getManDiagnostico());
		AccesorioMantenimiento.getMantenimiento()
				.setManFuncionarioRecibe(edicionAccesorioMantenimiento.getMantenimiento().getManFuncionarioRecibe());
		AccesorioMantenimiento.getMantenimiento()
				.setManFuncionarioEntrega(edicionAccesorioMantenimiento.getMantenimiento().getManFuncionarioEntrega());
		AccesorioMantenimiento.getMantenimiento()
				.setManUsuarioRecibe(edicionAccesorioMantenimiento.getMantenimiento().getManUsuarioRecibe());
		AccesorioMantenimiento.getMantenimiento()
				.setManFechaModificacion(edicionAccesorioMantenimiento.getMantenimiento().getManFechaModificacion());
		AccesorioMantenimiento.getMantenimiento()
				.setManUsuarioModifica(edicionAccesorioMantenimiento.getMantenimiento().getManUsuarioModifica());
		
		mDAO.actualizar(AccesorioMantenimiento);

		managerBitacora.mostrarLogAccesorio(loginDTO, edicionAccesorioMantenimiento.getAccesorio(),
				"ActualizarMantenimiento", " Hoja de Mantenimiento Actualizado ");
	}

	/**
	 * Insertar un nuevo resgitro de Equipo Mantenimiento
	 */
	public void insertarEquipoMantenimiento(Mantenimiento mantenimiento, Equipo equipoDevuelto) throws Exception {
		EquipoMantenimiento equipoMantenimiento = new EquipoMantenimiento();
		mantenimiento.setEquipoMantenimientos(new ArrayList<EquipoMantenimiento>());
		equipoMantenimiento.setEquipo(equipoDevuelto);
		equipoMantenimiento.setMantenimiento(mantenimiento);
		mantenimiento.getEquipoMantenimientos().add(equipoMantenimiento);
		mDAO.insertar(mantenimiento);
	}

	/**
	 * Insertar un nuevo resgitro de Accesorio Mantenimiento
	 */
	public void insertarAccesorioMantenimiento(Mantenimiento mantenimiento, Accesorio accesorioDevuelto)
			throws Exception {
		AccesorioMantenimiento accesorioMantenimiento = new AccesorioMantenimiento();
		mantenimiento.setAccesorioMantenimientos(new ArrayList<AccesorioMantenimiento>());
		accesorioMantenimiento.setAccesorio(accesorioDevuelto);
		accesorioMantenimiento.setMantenimiento(mantenimiento);
		mantenimiento.getAccesorioMantenimientos().add(accesorioMantenimiento);
		mDAO.insertar(mantenimiento);
	}

	/**
	 * Consultar un registro de Equipo Mantenimiento
	 */
	public List<EquipoMantenimiento> findByIdMantenimiento(int manId) throws Exception {
		return mDAO.findWhere(EquipoMantenimiento.class, "man_id=" + manId + "", null);
	}

	/**
	 * Cambiar el estado de un Equipo Mantenimiento
	 */
	public void cambiarEstadoEquipoMantenimiento(int manId, String estado) throws Exception {
		EquipoMantenimiento EquipoMantenimiento = (EquipoMantenimiento) mDAO.findById(EquipoMantenimiento.class, manId);
		EquipoMantenimiento.getMantenimiento().setManEstado(estado);
		System.out.println("cambiarEstado " + estado);
		mDAO.actualizar(EquipoMantenimiento);
	}

	/**
	 * Consultas de registros de un AccesorioMatenimiento con un Accesorio único y
	 * un estado
	 */
	public List<AccesorioMantenimiento> findManWhereByAcceManEstados(String estado) throws Exception {
		return mDAO.findWhere(AccesorioMantenimiento.class, " o.mantenimiento.manEstado = '" + estado + "'", null);
	}

	/**
	 * Consultas de registros de un AccesorioMatenimiento con un Accesorio único y
	 * un estado
	 */
	public List<EquipoMantenimiento> findManWhereByEquiManEstados(String estado) throws Exception {
		return mDAO.findWhere(EquipoMantenimiento.class, " o.mantenimiento.manEstado = '" + estado + "'", null);
	}

	// *********************************EquipoMantenimientoS*********************************************************************************************

}
