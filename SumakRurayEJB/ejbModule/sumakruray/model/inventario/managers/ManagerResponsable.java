package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Responsable;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerResponsable
 */
@Stateless
@LocalBean
public class ManagerResponsable {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	/**
	 * Default constructor.
	 */
	public ManagerResponsable() {
		// TODO Auto-generated constructor stub
	}

	// *********************************RESPONSABLE*******************************************************************************************
	/**
	 * Consulta de todos los registros de la tabla Responsables
	 */
	// Consulta a la Tabla de Responsables
	public List<Responsable> findAllResponsables() {
		return mDAO.findAll(Responsable.class, null);
	}

	/**
	 * Actualizar un rgistro de un Responsable
	 */
	public void actualizarResponsable(LoginDTO loginDTO, Responsable edicionResponsable) throws Exception {
		Responsable responsable = (Responsable) mDAO.findById(Responsable.class, edicionResponsable.getRespId());
		responsable.setRespIdentificacion(edicionResponsable.getRespIdentificacion());
		responsable.setRespNombres(edicionResponsable.getRespNombres());
		responsable.setRespApellidos(edicionResponsable.getRespApellidos());
		responsable.setRespTelefono(edicionResponsable.getRespTelefono());
		responsable.setRespDireccionDomicilio(edicionResponsable.getRespDireccionDomicilio());
		responsable.setRespCargo(edicionResponsable.getRespCargo());
		responsable.setRespCorreo(edicionResponsable.getRespCorreo());
		responsable.setRespFechaModificacion(edicionResponsable.getRespFechaModificacion());
		responsable.setRespUsuarioModifica(edicionResponsable.getRespUsuarioModifica());
		mDAO.actualizar(responsable);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarResponsable",
				"se actualizó al Responsable " + responsable.getRespNombres() + " " + responsable.getRespApellidos());
	}

	/**
	 * insertar un nuevo resgistro de Responsable
	 */
	public void insertarResponsable(Responsable nuevoResponsable) throws Exception {
		mDAO.insertar(nuevoResponsable);
	}

	/**
	 * consultar un responsable por un respId
	 */
	public Responsable findByIdResponsable(int respId) throws Exception {
		return (Responsable) mDAO.findById(Responsable.class, respId);
	}

	/**
	 * Actualizar el estado de un Responsable
	 */
	public void activarDesactivarResponsable(int respId) throws Exception {
		Responsable responsable = (Responsable) mDAO.findById(Responsable.class, respId);
		responsable.setRespEstado(!responsable.getRespEstado());
		System.out.println("activar/desactivar " + responsable.getRespEstado());
		mDAO.actualizar(responsable);
	}
	// *********************************RESPONSABLE*********************************************************************************************

}
