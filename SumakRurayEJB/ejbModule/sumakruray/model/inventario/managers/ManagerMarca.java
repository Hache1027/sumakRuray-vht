package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.Marca;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerMarca
 */
@Stateless
@LocalBean
public class ManagerMarca {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	/**
	 * Default constructor.
	 */
	public ManagerMarca() {
		// TODO Auto-generated constructor stub
	}

	// ********************************--__MARCA__--*******************************************************************************************
	/**
	 * Consulta todos los registros de la tabla Marca
	 */
	public List<Marca> findAllMarcas() {
		return mDAO.findAll(Marca.class, null);
	}

	/**
	 * Insertar un nuevo registro de Marca
	 */
	public void insertarMarca(LoginDTO loginDTO, Marca nuevoMarca) throws Exception {
		mDAO.insertar(nuevoMarca);
	}

	/**
	 * Consultra un registro de marca por su marId
	 */
	public Marca findByIdMarca(int marId) throws Exception {
		return (Marca) mDAO.findById(Marca.class, marId);
	}

}
