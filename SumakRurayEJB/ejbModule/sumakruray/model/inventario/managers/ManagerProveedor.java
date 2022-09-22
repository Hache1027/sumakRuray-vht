package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Proveedor;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerProveedor
 */
@Stateless
@LocalBean
public class ManagerProveedor {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	/**
	 * Default constructor.
	 */
	public ManagerProveedor() {
		// TODO Auto-generated constructor stub
	}

	// *********************************PROVEEDOR*******************************************************************************************
	/**
	 * Consultar todo los registros de la tabla Proveedor
	 */
	public List<Proveedor> findAllProveedors() {
		return mDAO.findAll(Proveedor.class, null);
	}

	/**
	 * Actualizar los datos de un registro de Proveedor
	 */
	public void actualizarProveedor(LoginDTO loginDTO, Proveedor edicionProveedor) throws Exception {
		Proveedor proveedor = (Proveedor) mDAO.findById(Proveedor.class, edicionProveedor.getProId());
		proveedor.setProEmpresa(edicionProveedor.getProEmpresa());
		proveedor.setProTelefono(edicionProveedor.getProTelefono());
		proveedor.setProCorreo(edicionProveedor.getProCorreo());
		proveedor.setProDireccion(edicionProveedor.getProDireccion());
		proveedor.setProFechaModificacion(edicionProveedor.getProFechaModificacion());
		proveedor.setProUsuarioModifica(edicionProveedor.getProUsuarioModifica());
		mDAO.actualizar(proveedor);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarProveedor",
				"se actualizó al Proveedor " + proveedor.getProEmpresa());
	}

	/**
	 * Insertar un nuevo Registro de Proveedor
	 */
	public void insertarProveedor(Proveedor nuevoProveedor) throws Exception {
		mDAO.insertar(nuevoProveedor);
	}

	/**
	 * Consultar un registro de Proveedor por su proId
	 */
	public Proveedor findByIdProveedor(int proId) throws Exception {
		return (Proveedor) mDAO.findById(Proveedor.class, proId);
	}

	/**
	 * Actualizar el estado de un registro de un Proveedor
	 */
	public void activarDesactivarProveedor(int proId) throws Exception {
		Proveedor proveedor = (Proveedor) mDAO.findById(Proveedor.class, proId);
		proveedor.setProEstado(!proveedor.getProEstado());
		System.out.println("activar/desactivar " + proveedor.getProEstado());
		mDAO.actualizar(proveedor);
	}
	// *********************************PROVEEDOR*********************************************************************************************

}
