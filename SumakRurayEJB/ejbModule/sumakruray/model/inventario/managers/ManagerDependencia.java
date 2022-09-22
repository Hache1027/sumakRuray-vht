package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.SegDependencia;
import sumakruray.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerDependencia
 */
@Stateless
@LocalBean
public class ManagerDependencia {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	/**
	 * Default constructor.
	 */
	public ManagerDependencia() {
		// TODO Auto-generated constructor stub
	}

	// ********************************--__SegDependencia__--*******************************************************************************************
	/**
	 * Consultar todos los registros de la tabla SegDependencia
	 */
	public List<SegDependencia> findAllSegDependencias() {
		return mDAO.findAll(SegDependencia.class, null);
	}

	/**
	 * Insertar un nuevo registro de SegDependencia
	 */
	public void insertarSegDependencia(SegDependencia nuevoSegDependencia) throws Exception {
		mDAO.insertar(nuevoSegDependencia);
	}

	/**
	 * Consultar una Dpendencia por su segDepId
	 */
	public SegDependencia findByIdSegDependencia(int id) throws Exception {
		return (SegDependencia) mDAO.findById(SegDependencia.class, id);
	}

	/**
	 * Actualizar un resgistro de Dependecia
	 */
	public void actualizarSegDependencia(SegDependencia edicionSegDependencia) throws Exception {
		SegDependencia SegDependencia = (SegDependencia) mDAO.findById(SegDependencia.class,
				edicionSegDependencia.getIdSegDependencia());
		SegDependencia.setDepDescripcion(edicionSegDependencia.getDepDescripcion());

		mDAO.actualizar(SegDependencia);

	}

	/**
	 * Actualizar el estado de un registro de Dependecnia
	 */
	public void activarDesactivarSegDependencia(int id) throws Exception {
		SegDependencia SegDependencia = (SegDependencia) mDAO.findById(SegDependencia.class, id);
		SegDependencia.setEstado(!SegDependencia.getEstado());
		System.out.println("activar/desactivar " + SegDependencia.getEstado());
		mDAO.actualizar(SegDependencia);
	}

	// *********************************--__SegDependencia__-*********************************************************************************************

}
