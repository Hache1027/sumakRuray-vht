package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.BodegaAccesorio;
import sumakruray.model.core.entities.BodegaEquipo;
import sumakruray.model.core.entities.Equipo;
import sumakruray.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerBodega
 */
@Stateless
@LocalBean
public class ManagerBodega {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	/**
	 * Default constructor.
	 */
	public ManagerBodega() {
		// TODO Auto-generated constructor stub
	}

	// **********************************BODEGA*******************************************************************************************
	// ----------------- Bodega Equipo-------------------
	/**
	 * Consulta de todos los registros de la tabla EquipoBodega
	 */
	public List<BodegaEquipo> findAllEquipoBodegas() {
		return mDAO.findAll(BodegaEquipo.class, null);
	}

	/**
	 * Insertar un nuevo Registro de EquipoBodega
	 */
	public void insertarEquipoBodega(BodegaEquipo nuevoBodegaEquipo) throws Exception {
		mDAO.insertar(nuevoBodegaEquipo);
	}

	/**
	 * Consultar BodegaEquipo por su equiId y el estado de la bodega
	 */
	public List<Equipo> findWhereByEquiIdBodega(int equiId, String estado) throws Exception {
		return mDAO.findWhere(Equipo.class, "equi_id=" + equiId + "and equi_estado='" + estado + "'", null);
	}

	/**
	 * Consultar BodegaEquipo por su estado de la bodega
	 * 
	 * public List<BodegaEquipo> findWhereByEquiBodEstado(String estado) throws
	 * Exception { return mDAO.findWhere(BodegaEquipo.class, "bod_estado='" + estado
	 * + "'", null); }
	 */

	/**
	 * Cambiar el estado de un bodegaEquipo
	 * 
	 * public void cambiarEstadoBodegaEquipo(int bodiId, String estado) throws
	 * Exception { BodegaEquipo bodegaEquipo = (BodegaEquipo)
	 * mDAO.findById(BodegaEquipo.class, bodiId); bodegaEquipo.setBodEstado(estado);
	 * System.out.println(bodegaEquipo.getBodEstado() + "uuu-------------3");
	 * System.out.println("cambio el Estado a" + estado);
	 * mDAO.actualizar(bodegaEquipo); }
	 */

	// ---------- Bodega Accesorio------------------------------
	/**
	 * Consultar todo los registros de la tabla BodegaAccesorio
	 */
	public List<BodegaAccesorio> findAllAccesorioBodegas() {
		return mDAO.findAll(BodegaAccesorio.class, null);
	}

	/**
	 * Insertar un nuevo registro de AccesrioBodega
	 */
	public void insertarAccesorioBodega(BodegaAccesorio nuevoBodegaAccesorio) throws Exception {
		mDAO.insertar(nuevoBodegaAccesorio);
	}

	/**
	 * consultar los registros de BodegaAccesorio por su estado
	 */
	public List<BodegaAccesorio> findWhereByAcceBodEstado(String estado) throws Exception {
		return mDAO.findWhere(BodegaAccesorio.class, "bod_estado='" + estado + "'", null);
	}

	/**
	 * Cambiar el estado de un BodegaAccesorio
	 * 
	 * public void cambiarEstadoBodegaAccesorio(int bodiId, String estado) throws
	 * Exception { BodegaAccesorio bodegaAccesorio = (BodegaAccesorio)
	 * mDAO.findById(BodegaAccesorio.class, bodiId);
	 * System.out.println(bodegaAccesorio.getBodEstado() + "uuu-------------3");
	 * bodegaAccesorio.setBodEstado(estado); System.out.println("cambio el Estado a"
	 * + estado); mDAO.actualizar(bodegaAccesorio); }
	 */

	/**
	 * onsultar BodegaEquipo por su acceId y el estado de la bodega
	 */
	public List<BodegaAccesorio> findWhereByAcceIdBodega(int acceId, String estado) throws Exception {
		return mDAO.findWhere(BodegaAccesorio.class, "acce_id=" + acceId + "and bod_estado='" + estado + "'", null);
	}

	/**
	 * Consultar los registrosde BodegaAccesorio
	 */
	public List<BodegaAccesorio> findWhereByAcceIdBodegaOne(int acceId) throws Exception {
		return mDAO.findWhere(BodegaAccesorio.class, "acce_id=" + acceId + "", null);
	}

	// *********************************BODEGA*********************************************************************************************

}
