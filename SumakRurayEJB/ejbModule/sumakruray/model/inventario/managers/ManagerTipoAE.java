package sumakruray.model.inventario.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import sumakruray.model.auditoria.managers.ManagerAuditoria;
import sumakruray.model.core.entities.TipoAccesorio;
import sumakruray.model.core.entities.TipoEquipo;
import sumakruray.model.core.managers.ManagerDAO;
import sumakruray.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerTipoAE
 */
@Stateless
@LocalBean
public class ManagerTipoAE {
	@EJB
	private ManagerDAO mDAO;
	@EJB
	private ManagerAuditoria mAuditoria;

	/**
	 * Default constructor.
	 */
	public ManagerTipoAE() {
		// TODO Auto-generated constructor stub
	}

	// ********************************--__TipoAccesorio__--*******************************************************************************************
	/**
	 * Consulta de todos los registros de la tabla TipoAccesorio
	 */
	public List<TipoAccesorio> findAllTipoAccesorios() {
		return mDAO.findAll(TipoAccesorio.class, null);
	}
	/**
	 * Insertar un nuevo registro de TipoAccesorio
	 */
	public void insertarTipoAccesorio(LoginDTO loginDTO, TipoAccesorio nuevoTipoAccesorio) throws Exception {
		mDAO.insertar(nuevoTipoAccesorio);
	}
	/**
	 * Consultar un TipoAccesorio por su tipAccId
	 */
	public TipoAccesorio findByIdTipoAccesorio(int tipAccId) throws Exception {
		return (TipoAccesorio) mDAO.findById(TipoAccesorio.class, tipAccId);
	}

	// *********************************--__TipoAccesorio__-*********************************************************************************************
	// -------------------------------------------------------------------------------------------------------------------------------------
	// ********************************-__TIPO_ESQUIPO__--*******************************************************************************************
	/**
	 * Consulta de todos los registros de la tabla TipoEquipo
	 */
	public List<TipoEquipo> findAllTipoEquipos() {
		return mDAO.findAll(TipoEquipo.class, null);
	}
	/**
	 * Insertar un nuevo registro de TipoEquipo
	 */
	public void insertarTipoEquipo(LoginDTO loginDTO, TipoEquipo nuevoTipoEquipo) throws Exception {
		mDAO.insertar(nuevoTipoEquipo);
	}
	/**
	 * Consultar un TipoEquipo por su tipEquiId
	 */
	public TipoEquipo findByIdTipoEquipo(int tipEquiId) throws Exception {
		return (TipoEquipo) mDAO.findById(TipoEquipo.class, tipEquiId);
	}

	// *********************************--__TIPO_ESQUIPO__-*********************************************************************************************

}
